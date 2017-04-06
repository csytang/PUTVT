package highlighter.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenForLogsAction extends AnAction {

    private int port = 9876;


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        ApplicationManager.getApplication()
                .executeOnPooledThread(executeResults());
    }


    private Runnable executeResults() {
        return () -> {
            DatagramSocket welcomeSocket = null;
            try {
                welcomeSocket = new DatagramSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            while (true) {
                byte[] receiveData = new byte[1024];
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    welcomeSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData());
                    System.out.println("RECEIVED: " + sentence);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
