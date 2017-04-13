package highlighter.listeners;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;
import highlighter.handlers.DisplayHandler;


public class EditorManagerListener implements FileEditorManagerListener {
    private DisplayHandler displayHandler;

    public EditorManagerListener(DisplayHandler displayHandler) {
        this.displayHandler = displayHandler;
    }

    public void fileOpened(FileEditorManager source, VirtualFile file) {
    }

    public void fileClosed(FileEditorManager source, VirtualFile file) {

    }

    public void selectionChanged(FileEditorManagerEvent event) {
        Editor editor = null;

        FileEditor fileEditor = event.getNewEditor();
            if ((fileEditor instanceof TextEditor)) {
                editor = ((TextEditor) fileEditor).getEditor();
            }


        if (editor != null) {
            this.displayHandler.addDisplayForEditor(editor);
        }
    }
}