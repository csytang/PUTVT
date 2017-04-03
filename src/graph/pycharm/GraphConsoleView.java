package graph.pycharm;

import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.JBTabsPaneImpl;
import com.intellij.ui.border.CustomLineBorder;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.impl.JBTabsImpl;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.ui.UIUtil;
import graph.constants.GraphConstants;
import graph.pycharm.api.LookAndFeelService;
import graph.pycharm.console.GraphPanel;
import graph.results.api.GraphCoverageResult;
import graph.results.api.ResultsPlanEvent;
import graph.visualization.layouts.ResultsPlanPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoField.*;

public class GraphConsoleView implements Disposable {

    public static final String PROFILE_PLAN_TITLE = "Profile";
    public static final String EXPLAIN_PLAN_TITLE = "Explain";
    private boolean initialized;

    private JPanel consoleToolWindowContent;
    private JBTabsPaneImpl consoleTabsPane;
    private JBTabsImpl consoleTabs;

    // Graph
    private JPanel consoleToolbarPanel;
    private JPanel graphCanvas;
    private JBScrollPane entityDetailsScrollPane;
    private Tree entityDetailsTree;

    // Table
    private JBScrollPane tableScrollPane;
    private JBTable tableExecuteResults;
    private JPanel entityDetailsScrollContent;
    private JPanel graphTab;
    private JBTabbedPane defaultTabContainer;
    private JBSplitter graphSplitter;

    private LookAndFeelService lookAndFeelService;

    private GraphPanel graphPanel;


    private static final DateTimeFormatter QUERY_PLAN_TIME_FORMAT = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();

    public GraphConsoleView() {
        initialized = false;
        graphPanel = new GraphPanel();


        lookAndFeelService = ServiceManager.getService(LookAndFeelService.class);
    }

    public void initToolWindow(Project project, ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(consoleToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);

        if (!initialized) {
            updateLookAndFeel();
            initializeWidgets(project);
            initializeUiComponents(project);

            // Hide standard tabs
            defaultTabContainer.setVisible(false);

            // Tabs
            consoleTabs.setFirstTabOffset(0);
            consoleTabs.addTab(new TabInfo(graphTab)
                    .setText("Graph"));
            consoleTabs.addTab(new TabInfo(tableScrollPane)
                    .setText("Table"));
            consoleTabs.setSelectionChangeHandler((info, requestFocus, doChangeSelection) -> {
                ActionCallback callback = doChangeSelection.run();
                graphPanel.resetPan();
                return callback;
            });

            AtomicInteger tabId = new AtomicInteger(0);
            project.getMessageBus().connect().subscribe(ResultsPlanEvent.QUERY_PLAN_EVENT,
                    (query, result) -> createNewQueryPlanTab(query, result, tabId.incrementAndGet()));

            // Actions
            final ActionGroup consoleActionGroup = (ActionGroup)
                    ActionManager.getInstance().getAction(GraphConstants.Actions.CONSOLE_ACTIONS);
            ActionToolbar consoleToolbar = ActionManager.getInstance()
                    .createActionToolbar(GraphConstants.ToolWindow.CONSOLE_TOOL_WINDOW, consoleActionGroup, false);
            consoleToolbarPanel.add(consoleToolbar.getComponent(), BorderLayout.CENTER);
            consoleToolbarPanel.setBorder(new CustomLineBorder(0, 0, 0, 1));
            consoleToolbarPanel.validate();

            initialized = true;
        }
    }

    private void createUIComponents() {
        graphCanvas = new JPanel(new GridLayout(0, 1));
        consoleTabsPane = new JBTabsPaneImpl(null, SwingConstants.TOP, this);
        consoleTabs = (JBTabsImpl) consoleTabsPane.getTabs();

        consoleTabs.addTabMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (UIUtil.isCloseClick(e, MouseEvent.MOUSE_RELEASED)) {
                    final TabInfo info = consoleTabs.findInfo(e);
                    if (info != null) {
                        String tabTitle = info.getText();
                        if (tabTitle.startsWith(PROFILE_PLAN_TITLE) || tabTitle.startsWith(EXPLAIN_PLAN_TITLE)) {
                            IdeEventQueue.getInstance().blockNextEvents(e);
                            consoleTabs.removeTab(info);
                        }
                    }
                }
            }
        });
    }

    private void createNewQueryPlanTab(String originalQuery,
                                       GraphCoverageResult result, int tabId) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 3));

        ResultsPlanPanel qpPanel = new ResultsPlanPanel(originalQuery, result);
        qpPanel.initialize(panel);

        TabInfo tabInfo = new TabInfo(panel);
        DefaultActionGroup tabActions = new DefaultActionGroup(new ResultsPlanPanel.CloseTab() {
            @Override
            public void actionPerformed(AnActionEvent e) {
                super.actionPerformed(e);
                consoleTabs.removeTab(tabInfo);
            }
        });
        tabInfo.setTabLabelActions(tabActions, ActionPlaces.EDITOR_TAB);

        String planType = result.isProfilePlan() ? PROFILE_PLAN_TITLE : EXPLAIN_PLAN_TITLE;
        consoleTabs.addTab(tabInfo.setText(String.format("%1s %2d - %3s", planType, tabId,
                LocalDateTime.now().format(QUERY_PLAN_TIME_FORMAT))));
    }

    private void updateLookAndFeel() {
        tableScrollPane.setBorder(IdeBorderFactory.createEmptyBorder());
        entityDetailsScrollPane.setBorder(IdeBorderFactory.createEmptyBorder());
        graphTab.setBorder(IdeBorderFactory.createEmptyBorder());
    }

    private void initializeUiComponents(Project project) {
        graphPanel.initialize(this, project);
    }

    private void initializeWidgets(Project project) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    }



    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public LookAndFeelService getLookAndFeelService() {
        return lookAndFeelService;
    }

    public JPanel getGraphCanvas() {
        return graphCanvas;
    }

    public Tree getEntityDetailsTree() {
        return entityDetailsTree;
    }

    public JBTable getTableExecuteResults() {
        return tableExecuteResults;
    }

    @Override
    public void dispose() {
    }
}
