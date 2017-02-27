package highlighters;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.LineMarkerRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;

import java.awt.*;

/**
 * @author Jan Cegin
 */
public class SideHighlighter {
    public void highlight(RangeHighlighter lineHighlighter, final Color color) {
        lineHighlighter.setLineMarkerRenderer(new LineMarkerRenderer() {
            public void paint(Editor editor, Graphics graphics, Rectangle rectangle) {
                Color origColor = graphics.getColor();
                try {
                    graphics.setColor(color);
                    int lineHeight = editor.getLineHeight();
                    graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height + lineHeight);
                    graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height + lineHeight);
                } finally {
                    graphics.setColor(origColor);
                }
            }
        });
    }
}
