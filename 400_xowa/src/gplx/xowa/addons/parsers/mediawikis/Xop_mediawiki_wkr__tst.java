/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.parsers.mediawikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_mediawiki_wkr__tst {
	private final    Xop_mediawiki_wkr__fxt fxt = new Xop_mediawiki_wkr__fxt();
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
	private final    Xop_mediawiki_mgr mgr = new Xop_mediawiki_mgr("mem/xowa/wiki/en.wikipedia.org/");
	private Xop_mediawiki_wkr wkr;
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