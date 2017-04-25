package test.highlighter.pattern;

import main.highlighter.pattern.PatternPythonDecoder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PatternPytestDecoderTest extends PatternAbstractTest{

    @Test
    public void patternDecodeShouldDecode() {
        //Before
        PatternPythonDecoder patternPythonDecoder = new PatternPythonDecoder();

        assertTrue(patternPythonDecoder.patternDecode(this.testFile));
    }
}
