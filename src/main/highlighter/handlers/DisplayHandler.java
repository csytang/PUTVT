package main.highlighter.handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import main.highlighter.highlighters.HighlightingMainController;

/**
 * Inspired by https://github.com/oker1/phpunit_codecoverage_display source code
 * Handles switching in between tabs
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
                addDisplayForEditor(editor);
            }
        }
    }

    public void addDisplayForEditor(Editor editor) {
        CoverageDisplay display = new CoverageDisplay();

        editor.getDocument().addDocumentListener(display);

        //update the display each time a file is opened
        HighlightingMainController highlightingMainController = HighlightingMainController.getInstance(null);

        highlightingMainController.finishVisualization(null, EditorFactory.getInstance().getAllEditors(), editor);
    }


}
