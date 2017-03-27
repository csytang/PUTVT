package graph.visualization.controls;

import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import graph.enums.ResultPlanArgumentKeys;
import graph.results.api.ResultsPlan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static graph.enums.ResultPlanArgumentKeys.ESTIMATED_ROWS;
import static graph.enums.ResultPlanArgumentKeys.ROWS;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class ColumnDefinitions {
    public static ColumnInfo[] getQueryPlanColumns() {
        return new ColumnInfo[]{
                OPERATOR_COL,
                ARGUMENTS_COL,
                ESTIMATED_ROWS_COL,
                IDENTIFIERS_COL
        };
    }

    public static ColumnInfo[] getProfileQueryPlanColumns() {
        return new ColumnInfo[]{
                OPERATOR_COL,
                ARGUMENTS_COL,
                ROWS_COL,
                ESTIMATED_ROWS_COL,
                IDENTIFIERS_COL
        };
    }

    private static final NumberFormat FORMATTER = new DecimalFormat("#0.00");

    private static final Set<String> RESERVED_KEYS = Stream.of(ResultPlanArgumentKeys.values())
            .map(ResultPlanArgumentKeys::getKey)
            .collect(toSet());

    private static final ColumnInfo<DefaultMutableTreeNode, String> OPERATOR_COL =
            new ColumnInfo<DefaultMutableTreeNode, String>("Operator") {
                @Override
                public Class<?> getColumnClass() {
                    return TreeTableModel.class;
                }

                @Nullable
                @Override
                public String valueOf(DefaultMutableTreeNode o) {
                    return ((ResultsPlan) o.getUserObject()).getOperatorType();
                }
            };

    private static final ColumnInfo<DefaultMutableTreeNode, String> ESTIMATED_ROWS_COL =
            getArgumentColumn("Estimated rows", ESTIMATED_ROWS.getKey());

    private static final ColumnInfo<DefaultMutableTreeNode, String> ROWS_COL =
            getArgumentColumn("Rows", ROWS.getKey());

    private static final ColumnInfo<DefaultMutableTreeNode, String> ARGUMENTS_COL =
            new ColumnInfo<DefaultMutableTreeNode, String>("Arguments") {
                @Nullable
                @Override
                public String valueOf(DefaultMutableTreeNode o) {
                    return ((ResultsPlan) o.getUserObject()).getArguments().entrySet().stream()
                            .filter(e -> !RESERVED_KEYS.contains(e.getKey()))
                            .map(Map.Entry::getValue)
                            .map(Object::toString)
                            .collect(joining(", "));
                }
            };

    private static final ColumnInfo<DefaultMutableTreeNode, String> IDENTIFIERS_COL =
            new ColumnInfo<DefaultMutableTreeNode, String>("Identifiers") {
                @Nullable
                @Override
                public String valueOf(DefaultMutableTreeNode o) {
                    return ((ResultsPlan) o.getUserObject()).getIdentifiers().stream()
                            .collect(joining(", "));
                }
            };

    @NotNull
    private static ColumnInfo<DefaultMutableTreeNode, String> getArgumentColumn(String title, String argumentKey) {
        return new ColumnInfo<DefaultMutableTreeNode, String>(title) {
            @Nullable
            @Override
            public String valueOf(DefaultMutableTreeNode o) {
                return ((ResultsPlan) o.getUserObject()).getArguments().entrySet().stream()
                        .filter(e -> e.getKey().equalsIgnoreCase(argumentKey))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .map(val -> {
                            if (val instanceof Double) {
                                return FORMATTER.format(val);
                            }

                            return val.toString();
                        })
                        .orElse("0");
            }
        };
    }
}