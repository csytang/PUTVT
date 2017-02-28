package handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import errors.ErrorManageFileControler;
import highlighters.HighlightingMainController;

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

        //update the display each time a file is opened
        HighlightingMainController highlightingMainController = HighlightingMainController.getInstance(null);
        ErrorManageFileControler errorManageFileControler = ErrorManageFileControler.getInstance(null);

        highlightingMainController.finishVisualization(errorManageFileControler.getErrorManageFileTable(),editor);
    }


}
