package test.highlighter.util;


import main.highlighter.util.ExternalLogsUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExternalLogsUtilTest {

    @Test
    public void testThatLogsConcat(){
        //Before
        ExternalLogsUtil.getInstane().concatLogs("");

        assertEquals(ExternalLogsUtil.getInstane().getLogs(),"\n");
    }
}
