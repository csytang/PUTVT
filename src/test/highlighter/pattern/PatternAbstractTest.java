package test.highlighter.pattern;


import java.util.ArrayList;
import java.util.Arrays;

public abstract class PatternAbstractTest {
    protected String testFile =  "============================= test session starts =============================\n" +
            "platform win32 -- Python 3.6.0a1, pytest-3.0.3, py-1.4.31, pluggy-0.4.0\n" +
            "rootdir: C:\\Users\\Cegin\\Desktop, inifile: \n" +
            "plugins: capturelog-0.7, PytestOutputCatcher-0.1.0\n" +
            "collected 1 items\n" +
            "\n" +
            "test_pytonak.py F\n" +
            "\n" +
            "Traceback (most recent call last):\n" +
            "  File test_pytonak.py, line 5, in <module>\n" +
            "    lumberjack()\n" +
            "  File sample.py, line 2, in lumberjack\n" +
            "    bright_side_of_death()\n" +
            "IndexError: tuple index out of range\n" +
            "\n" +
            "================================== FAILURES ===================================\n" +
            "_________________________________ test_answer _________________________________\n" +
            "\n" +
            "    def test_answer():\n" +
            ">       assert inc(3) == 5\n" +
            "E       assert 4 == 5\n" +
            "E        +  where 4 = inc(3)\n" +
            "\n" +
            "test_pytonak.py:5: AssertionError\n" +
            "_________________________________ test_answer _________________________________\n" +
            "\n" +
            "    def test_answer():\n" +
            ">       assert inc(3) == 5\n" +
            "E       assert 4 == 5\n" +
            "E        +  where 4 = inc(3)\n" +
            "\n" +
            "sample.py:1: AssertionError\n" +
            "=========================== pytest-warning summary ============================\n" +
            "WI1 C:\\Users\\Cegin\\AppData\\Local\\Programs\\Python\\Python36\\lib\\site-packages\\pytest_capturelog.py:171 'pytest_runtest_makereport' hook uses deprecated __multicall__ argument\n" +
            "WC1 None pytest_funcarg__caplog: declaring fixtures using \"pytest_funcarg__\" prefix is deprecated and scheduled to be removed in pytest 4.0.  Please remove the prefix and use the @pytest.fixture decorator instead.\n" +
            "WC1 None pytest_funcarg__capturelog: declaring fixtures using \"pytest_funcarg__\" prefix is deprecated and scheduled to be removed in pytest 4.0.  Please remove the prefix and use the @pytest.fixture decorator instead.\n" +
            "================= 1 failed, 3 pytest-warnings in 0.06 seconds =================";


    protected ArrayList<String> testFileNames = new ArrayList<String>(
            Arrays.asList("test_pytonak.py","sample.py"));
}
