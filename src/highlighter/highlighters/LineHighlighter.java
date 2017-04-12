package highlighter.highlighters;

import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;

import java.awt.*;

public class LineHighlighter {
    public void highlight(RangeHighlighter lineHighlighter, TextAttributes textAttributes, Color color) {
        textAttributes.setBackgroundColor(color);
    }
}
