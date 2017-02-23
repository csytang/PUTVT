package handlers;

import com.intellij.openapi.vfs.*;

import java.io.File;

/**
 * Created by Cegin on 20.2.2017.
 */
public class FileOperationListener implements VirtualFileListener {
    private DisplayHandler displayHandler;

    public FileOperationListener(DisplayHandler displayHandler) {
        this.displayHandler = displayHandler;
    }

    public void beforePropertyChange(VirtualFilePropertyEvent event) {


    }

    public void beforeFileMovement(VirtualFileMoveEvent event) {

    }

    public void contentsChanged(VirtualFileEvent event) {

    }

    public void fileMoved(VirtualFileMoveEvent event) {
    }

    public void propertyChanged(VirtualFilePropertyEvent event) {
    }

    public void fileCreated(VirtualFileEvent event) {
    }

    public void fileDeleted(VirtualFileEvent event) {
    }

    public void fileCopied(VirtualFileCopyEvent event) {
    }

    public void beforeContentsChange(VirtualFileEvent event) {
    }

    public void beforeFileDeletion(VirtualFileEvent event) {
    }
}
