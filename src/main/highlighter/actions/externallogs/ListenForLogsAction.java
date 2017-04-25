package main.highlighter.actions.externallogs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import main.highlighter.util.ExternalLogsUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//Starts listening for logs from external sources
public class ListenForLogsAction extends AnAction {

    private int port = 9876; //Port to be used

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ExternalLogsUtil.getInstane().setBeingUsed(true);
        ApplicationManager.getApplication()
                .executeOnPooledThread(executeResults());
    }

    /**
     * Runnable so we dont block the main IDE thread, listens for UDP packets with 1024 bit size
     * @return
     */
    private Runnable executeResults() {
        return () -> {
            DatagramSocket welcomeSocket = null;
            try {
                welcomeSocket = new DatagramSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            ExternalLogsUtil instance = ExternalLogsUtil.getInstane();
            while (instance.getBeingUsed()) {
                byte[] receiveData = new byte[1024];
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    welcomeSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData());
                    ExternalLogsUtil.getInstane().concatLogs(sentence);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            welcomeSocket.close();
        };
    }

    public void update(AnActionEvent e) {
        e.getPresentation().setVisible(!ExternalLogsUtil.getInstane().getBeingUsed());
    }
}
