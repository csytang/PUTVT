package test.highlighter.util;

import main.highlighter.pattern.PatternPytestDecoder;
import main.highlighter.pattern.PatternPythonDecoder;
import main.highlighter.util.HashtableCombineUtil;
import main.highlighter.util.StringPytestUtil;
import org.junit.Before;
import org.junit.Test;
import test.highlighter.pattern.PatternAbstractTest;

import java.util.Hashtable;

import static org.junit.Assert.*;

public class HashtableCombineUtilTest extends PatternAbstractTest{
    private Hashtable pythonHashtable = new Hashtable();
    private Hashtable pytestHashtable = new Hashtable();

    @Before
    public void prepareHashtables(){
        PatternPythonDecoder patternPythonDecoder = new PatternPythonDecoder();
        PatternPytestDecoder patternPytestDecoder = new PatternPytestDecoder();
        assertTrue(patternPythonDecoder.patternDecode(this.testFile));
        assertTrue(patternPytestDecoder.patternDecode(this.testFile));
        pythonHashtable=patternPythonDecoder.getFileKeyDTOObject();
        pytestHashtable=patternPytestDecoder.getFileKeyDTOObject();
    }

    @Test
    public void hashtablesShouldCombineIntoOneSuccesfuly(){
        //Do
        Hashtable finalHashtable = HashtableCombineUtil.combineHashTables(pythonHashtable,pytestHashtable,this.testFileNames);

        assertEquals(finalHashtable.size(),2);

        StringPytestUtil stringPytestUtil = (StringPytestUtil) finalHashtable.get(this.testFileNames.get(0));
        assertEquals(stringPytestUtil.getLineNumber().size(),2);
        assertEquals(stringPytestUtil.getTypeOfError().size(),2);
        assertNotEquals(stringPytestUtil.getLinesDuringRuntime().size(),0);
    }

    @Test
    public void hashtablesShouldCombineIntoOneSuccesfulyOneFileName(){
        //Do
        Hashtable finalHashtable = HashtableCombineUtil.combineHashTablesOneFileOnly(pythonHashtable,pytestHashtable,this.testFileNames.get(0));

        assertEquals(finalHashtable.size(),2);

        StringPytestUtil stringPytestUtil = (StringPytestUtil) finalHashtable.get(this.testFileNames.get(0));
        assertEquals(stringPytestUtil.getLineNumber().size(),2);
        assertEquals(stringPytestUtil.getTypeOfError().size(),2);
        assertNotEquals(stringPytestUtil.getLinesDuringRuntime().size(),0);
    }

}
