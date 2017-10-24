/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.parsers.mediawikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_mediawiki_wkr__tst {
	private final    Xop_mediawiki_wkr__fxt fxt = new Xop_mediawiki_wkr__fxt();
	@After public void term() {Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;}
	@Test 	public void Basic()	{
		fxt.Init__wkr("en.wikipedia.org", null);
		fxt.Test__parse("Page_1", "''{{PAGENAME}}''"
		, "<p><i>Page 1</i>"
		, "</p>"
		);
	}
	@Test 	public void Template()	{
		fxt.Init__wkr("en.wikipedia.org", new Xop_mediawiki_loader__mock());
		fxt.Test__parse("Page_1", "{{bold}}"
		, "<p><b>bold</b>"
		, "</p>"
		);
	}
}
class Xop_mediawiki_wkr__fxt {
	private final    Xop_mediawiki_mgr mgr = new Xop_mediawiki_mgr("mem/xowa/wiki/en.wikipedia.org/", false);
	private Xop_mediawiki_wkr wkr;
	public Xop_mediawiki_wkr__fxt() {
		gplx.dbs.Db_conn_bldr.Instance.Reg_default_mem();
	}
	public void Init__wkr(String wiki, Xop_mediawiki_loader cbk) {
		this.wkr = mgr.Make(wiki, cbk);
	}
	public void Test__parse(String page, String wtxt, String... expd) {
		Gftest.Eq__ary__lines(String_.Concat_lines_nl_skip_last(expd), wkr.Parse(page, wtxt), "parse failed; wtxt={0}", wtxt);
	}
}
class Xop_mediawiki_loader__mock implements Xop_mediawiki_loader {
	public String LoadWikitext(String page) {
		if (String_.Eq(page, "Template:Bold"))	return "'''bold'''";
		else									return "text";
	}
}