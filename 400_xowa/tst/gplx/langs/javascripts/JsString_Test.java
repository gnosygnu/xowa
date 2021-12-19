package gplx.langs.javascripts;

import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;

public class JsString_Test {
    private final JsString_Tstr tstr = new JsString_Tstr();
    @Test public void slice() {
        tstr.Test_slice("bgn.positive.basic", "bc" , "abc", 1);
        tstr.Test_slice("bgn.positive.large", ""   , "abc", 4);
        tstr.Test_slice("bgn.negative.basic", "c"  , "abc", -1);
        tstr.Test_slice("bgn.negative.large", "abc", "abc", -4);
        tstr.Test_slice("end.positive.basic", "b"  , "abc", 1, 2);
        tstr.Test_slice("end.positive.eos"  , "bc" , "abc", 1, 3);
        tstr.Test_slice("end.positive.large", "bc" , "abc", 1, 4);
        tstr.Test_slice("end.negative.basic", "b"  , "abc", 1, -1);
        tstr.Test_slice("end.negative.large", ""   , "abc", 1, -4);
    }
}
class JsString_Tstr {
    public void Test_slice(String note, String expd, String src, int bgn) {
        GfoTstr.Eq(expd, JsString_.slice(src, bgn), note);
    }
    public void Test_slice(String note, String expd, String src, int bgn, int end) {
        GfoTstr.Eq(expd, JsString_.slice(src, bgn, end), note);
    }
}