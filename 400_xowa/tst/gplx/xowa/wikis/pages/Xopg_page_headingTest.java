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
package gplx.xowa.wikis.pages;

import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.tests.GfoTstr;
import gplx.xowa.Xop_fxt;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.pages.htmls.Xopg_html_data;
import org.junit.Test;

public class Xopg_page_headingTest {
    private Xopg_page_headingTstr tstr = new Xopg_page_headingTstr();
    @Test public void Basic() {
        tstr.Test("<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"en\">Page 1</h1>"
            , "Page_1", "Page 1", "en");
    }
}
class Xopg_page_headingTstr {
    private Xopg_page_heading heading = new Xopg_page_heading();
    private Xowe_wiki wiki;
    public Xopg_page_headingTstr() {
        Xop_fxt fxt = new Xop_fxt();
        wiki = fxt.Wiki();
    }

    public void Test(String expd, String ttlDb, String ttlTxt, String lang) {
        heading.Init(wiki, true, new Xopg_html_data(), BryUtl.NewU8(ttlDb), BryUtl.NewU8(ttlTxt), BryUtl.NewU8(lang));
        BryWtr bfr = BryWtr.New();
        heading.AddToBfr(bfr);
        GfoTstr.Eq(expd, bfr.ToStrAndClear());
    }
}