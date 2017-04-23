package highlighter.highlighters;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import highlighter.util.StringPytestUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Inspired by https://github.com/oker1/phpunit_codecoverage_display
 */
public class MainHighlighter{
                private Hashtable highlights;
                private static MainHighlighter instance = null;

                public static MainHighlighter getInstance() {
                        if (instance == null) {
                            instance = new MainHighlighter();
                        }
                    return instance;
                }


                protected MainHighlighter() {
                    highlights = new Hashtable();
                }

                public void doHighlight(StringPytestUtil stringPytestUtil,Editor editor){
                    clear(editor);
                    List<Integer> allLines;
                    allLines = stringPytestUtil.getLineNumber();
                    Hashtable colors = new Hashtable();

                    for (Integer lineNumber : allLines){
                        if (colors.get(lineNumber) != null){
                            Color color = (Color) colors.get(lineNumber);
                            Color newColor = new Color((int) (color.getRed()*0.85), 0, 0, 255);
                            colors.remove(lineNumber);
                            colors.put(lineNumber,newColor);
                        }
                        else{
                            colors.put(lineNumber,new Color(255, 0, 0, 255));
                        }
                    }

                    Hashtable typesOferrors = new Hashtable();

                    int count = 0;

                    for (Integer lineNumber : allLines){
                        if (typesOferrors.get(lineNumber) != null){
                            List<String> typeOfErrors = (List<String>) typesOferrors.get(lineNumber);
                            typeOfErrors.add(stringPytestUtil.getTypeOfError().get(count++));
                            typesOferrors.remove(lineNumber);
                            typesOferrors.put(lineNumber,typeOfErrors);
                        }
                        else{
                            List<String> typeOfErrors = new ArrayList<>();
                            typeOfErrors.add(stringPytestUtil.getTypeOfError().get(count++));
                            typesOferrors.put(lineNumber,typeOfErrors);
                        }
                    }

                    List<Integer> lines = new ArrayList<>();
                    for (Integer line : allLines){
                        if (lines.contains(line)){continue;}
                        Color color = (Color) colors.get(line);
                        List<String> errorTypes = (List<String>) typesOferrors.get(line);
                        highlightLines(color, line, line, listToString(errorTypes), editor);
                        lines.add(line);
                    }
                }

                public void highlightLines(final Color color, int fromLine, int toLine, String testName, Editor editor) {
                    Document document = editor.getDocument();
                    SideHighlighter sideHighlighter = new SideHighlighter();

                    LineHighlighter lineHighlighter = new LineHighlighter();
                    ErrorStripeMarkHighlighter errorStripeMarkHighlighter = new ErrorStripeMarkHighlighter();
                    if (toLine <= document.getLineCount()) {
                        TextAttributes attributes = new TextAttributes();

                        RangeHighlighter highlighter = createRangeHighlighter(fromLine, toLine, attributes, editor);

                        lineHighlighter.highlight(highlighter, attributes, color);

                        errorStripeMarkHighlighter.highlight(highlighter, attributes, color, testName);

                        sideHighlighter.highlight(highlighter, color);
                        if (highlights.get(editor.getMarkupModel().toString())==null){
                            List<RangeHighlighter> rangeHighlighterList = new ArrayList<>();
                            rangeHighlighterList.add(highlighter);
                            highlights.put(editor.getMarkupModel().toString(), rangeHighlighterList);
                        }
                        else{
                            List<RangeHighlighter> rangeHighlighterList = (List<RangeHighlighter>) highlights.get(editor.getMarkupModel().toString());
                            rangeHighlighterList.add(highlighter);
                        }
                    }
                }

    private RangeHighlighter createRangeHighlighter(int fromLine, int toLine, TextAttributes attributes, Editor editor) {
        Document document = editor.getDocument();

        int lineStartOffset = document.getLineStartOffset(Math.max(0, fromLine - 1));
        int lineEndOffset = document.getLineEndOffset(Math.max(0, toLine - 1));

        return editor.getMarkupModel().addRangeHighlighter(
                lineStartOffset, lineEndOffset, 3333, attributes, HighlighterTargetArea.EXACT_RANGE
        );
    }

    public void clear(Editor editor) {
        MarkupModel model = editor.getMarkupModel();

        List<RangeHighlighter> rangeHighlighterList = (List<RangeHighlighter>) highlights.get(model.toString());
        if (rangeHighlighterList != null && rangeHighlighterList.size() != 0) {
            for (RangeHighlighter rangeHighlighter : rangeHighlighterList) {
                model.removeHighlighter(rangeHighlighter);
            }
        }
        highlights.remove(model.toString());
      //  highlights.clear();
    }

    private String listToString(List<String> lines){
        String str = "";
        for (String line : lines){
            str += line;
            str += '\n';
        }
        return str;
    }
}
