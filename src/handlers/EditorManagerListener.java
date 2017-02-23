package handlers;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created by Cegin on 20.2.2017.
 */
public class EditorManagerListener implements FileEditorManagerListener {
    private DisplayHandler displayHandler;

    public EditorManagerListener(DisplayHandler displayHandler) {
        this.displayHandler = displayHandler;
    }

    public void fileOpened(FileEditorManager source, VirtualFile file) {
        Editor editor = null;

        for (FileEditor fileEditor : source.getEditors(file)) {
            if ((fileEditor instanceof TextEditor)) {
                editor = ((TextEditor) fileEditor).getEditor();
                break;
            }
        }

        if (editor != null) {
            this.displayHandler.addDisplayForEditor(editor, file.getPath());
        }
    }

    public void fileClosed(FileEditorManager source, VirtualFile file) {

    }

    public void selectionChanged(FileEditorManagerEvent event) {
    }
}