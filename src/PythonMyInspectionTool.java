import com.intellij.openapi.editor.ex.DocumentEx;
import com.intellij.openapi.editor.impl.RangeMarkerImpl;
import com.intellij.ui.DocumentAdapter;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

/**
 * Created by Cegin on 22.2.2017.
 */
public class PythonMyInspectionTool extends RangeMarkerImpl{


    protected PythonMyInspectionTool(@NotNull DocumentEx document, int start, int end, boolean register) {
        super(document, start, end, register);
    }
}


