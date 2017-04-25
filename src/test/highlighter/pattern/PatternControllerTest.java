package test.highlighter.pattern;


import main.highlighter.pattern.PatternController;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class PatternControllerTest extends PatternAbstractTest{


    @Test
    public void hashtableCombineWithLogsShouldCreateHashtable(){
        PatternController patternController = new PatternController();

        Hashtable hashtable = patternController.patternDecode(this.testFile);
        assertEquals(hashtable.size(),2);
    }
}
