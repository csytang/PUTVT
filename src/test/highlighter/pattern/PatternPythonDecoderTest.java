package test.highlighter.pattern;

import main.highlighter.pattern.PatternPythonDecoder;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class PatternPythonDecoderTest extends PatternAbstractTest{



    @Test
    public void patternDecodeShouldDecode() {
        //Before
        PatternPythonDecoder patternPythonDecoder = new PatternPythonDecoder();

        assertTrue(patternPythonDecoder.patternDecode(this.testFile));
    }
}
