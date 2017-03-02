package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import errors.ErrorManageFileControler;

import java.util.Hashtable;

/**
 * Created by Cegin on 3.11.2016.
 */
public class ClearAllAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Hashtable hashtable = new Hashtable();
        hashtable.put(0, 0);
        ErrorManageFileControler.getInstance(hashtable);
    }

}


