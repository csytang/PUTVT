package handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import errors.ErrorManageFileControler;

/**
 * Created by Cegin on 20.2.2017.
 */
public class DisplayHandler {


    private Project project;

    public DisplayHandler(Project project) {
        this.project = project;

        initializeMap();
    }



    public void initializeMap() {


        for (VirtualFile file : FileEditorManager.getInstance(project).getOpenFiles()) {
            Editor editor = null;

            for (FileEditor fileEditor : FileEditorManager.getInstance(project).getEditors(file)) {
                if ((fileEditor instanceof TextEditor)) {
                    editor = ((TextEditor) fileEditor).getEditor();
                    break;
                }
            }

            if (editor != null) {
                addDisplayForEditor(editor, file.getPath());
            }
        }
    }

    public void addDisplayForEditor(Editor editor, String file) {
        CoverageDisplay display = new CoverageDisplay();

        editor.getDocument().addDocumentListener(display);
        //createErrorHover(editor,"msg");

        //update the display each time a file is opened
        ErrorManageFileControler errorManageFileControler = ErrorManageFileControler.getInstance(null);
        errorManageFileControler.decodeDTO(errorManageFileControler.getErrorManageFileTable(),editor);
    }

    /*private void createErrorHover(Editor e, String message){

        StatusBar statusBar = WindowManager.getInstance()
                .getIdeFrame(e.getProject()).getStatusBar();

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.ERROR, null)
                .setFadeoutTime(30000)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);

    }*/

}
