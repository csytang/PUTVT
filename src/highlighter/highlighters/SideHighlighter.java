package highlighter.highlighters;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.LineMarkerRenderer;
import com.intellij.openapi.editor.markup.RangeHighlighter;

import java.awt.*;

/**
 * Taken from https://github.com/oker1/phpunit_codecoverage_display
 */
public class SideHighlighter {
    public void highlight(RangeHighlighter lineHighlighter, final Color color) {
        lineHighlighter.setLineMarkerRenderer(new LineMarkerRenderer() {
            public void paint(Editor editor, Graphics graphics, Rectangle rectangle) {
                Color origColor = graphics.getColor();
                try {
                    graphics.setColor(color);
                    graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                } finally {
                    graphics.setColor(origColor);
                }
            }
        });
    }
}
