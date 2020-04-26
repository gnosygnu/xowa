package gplx.xowa.xtns.template_styles;

import gplx.core.tests.Gftest;
import org.junit.Test;

public class XoCssTransformerTest {
    private final XoCssTranformerTstr tstr = new XoCssTranformerTstr();

    @Test
    public void Minify() {
        tstr.Test_Minify("basic"  ,"rgb (128,128,128)", "#808080");
    }

    @Test
    public void Prepend() {
        tstr.Test_Prepend("cls.1.1"  ,"x {}"     , ".a"      , ".a x {}");
        tstr.Test_Prepend("cls.n.1"  ,"x {}y {}", ".a"      , ".a x {} .a y {}");
    }

    @Test
    public void Url() {
        tstr.Test_Url("match.y"    , "url(//site/a.png)", "site", "prepend/site", "url(//prepend/site/a.png)");
        tstr.Test_Url("match.y.any", "//site/abc"       , "site", "prepend/site", "//prepend/site/abc"); // NOTE: matches any "//"
        tstr.Test_Url("match.n"    , "url(/-site/a.png)", "site", "prepend/site", "url(/-site/a.png)");
    }
}
class XoCssTranformerTstr {
    public void Test_Url(String note, String css, String src, String trg, String expd) {
        XoCssTransformer transformer = new XoCssTransformer(css);
        String actl = transformer.Url(src, trg).ToStr();
        Gftest.Eq__str(expd, actl, note);
    }
    public void Test_Prepend(String note, String css, String prepend, String expd) {
        XoCssTransformer transformer = new XoCssTransformer(css);
        String actl = transformer.Prepend(prepend).ToStr();
        Gftest.Eq__str(expd, actl, note);
    }
    public void Test_Minify(String note, String css, String expd) {
        XoCssTransformer transformer = new XoCssTransformer(css);
        String actl = transformer.Minify().ToStr();
        Gftest.Eq__str(expd, actl, note);
    }
}
