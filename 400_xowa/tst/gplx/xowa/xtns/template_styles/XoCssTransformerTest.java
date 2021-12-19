/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.template_styles;

import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;

public class XoCssTransformerTest {
    private final XoCssTranformerTstr tstr = new XoCssTranformerTstr();

    @Test
    public void Minify() {
        tstr.Test_Minify("basic"  ,"rgb (128,128,128)", "#808080");
    }

    @Test
    public void PrependBasic() {
        tstr.Test_Prepend(".x {}", ".a", ".a .x{}");
    }

    @Test
    public void PrependManyClasses() {
        tstr.Test_Prepend("x,y,z {}", ".a", ".a x,.a y,.a z{}");
    }

    @Test
    public void PrependManyNodes() {
        tstr.Test_Prepend("x {} y {} z {}", ".a", ".a x{}.a y{}.a z{}");
    }

    @Test
    public void PrependEmpty() {
        tstr.Test_Prepend("x {}{}", ".a", ".a x{}{}");
    }

    @Test
    public void PrependMediaAtBos() {
        tstr.Test_Prepend("@media {} x {}", ".a", "@media {}.a x{}");
    }

    @Test
    public void PrependMediaAtMid() {
        tstr.Test_Prepend("x {} @media {} y {}", ".a", ".a x{} @media {}.a y{}");
    }

    @Test
    public void PrependIgnoreBody() {
        tstr.Test_Prepend("body {}", ".a", "body{}");
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
        GfoTstr.Eq(expd, actl, note);
    }
    public void Test_Prepend(String css, String selector, String expd) {
        XoCssTransformer transformer = new XoCssTransformer(css);
        String actl = transformer.Prepend(selector).ToStr();
        GfoTstr.Eq(expd, actl);
    }
    public void Test_Minify(String note, String css, String expd) {
        XoCssTransformer transformer = new XoCssTransformer(css);
        String actl = transformer.Minify().ToStr();
        GfoTstr.Eq(expd, actl, note);
    }
}
