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
package gplx.xowa.addons.wikis.pages.syncs.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.pages.*; import gplx.xowa.addons.wikis.pages.syncs.*; import gplx.xowa.addons.wikis.pages.syncs.core.*;
import org.junit.*;
import gplx.langs.htmls.*;
public class Xosync_hdoc_parser__tst {
	@Before public void init() {fxt.Clear();} private final    Xosync_hdoc_parser__fxt fxt = new Xosync_hdoc_parser__fxt();
	@Test   public void Remove_edit() {
		fxt.Exec__parse(Gfh_utl.Replace_apos_concat_lines
		( "<h2><span class='mw-headline' id='Section_1'>Section_1</span>"
		, "<span class='mw-editsection'>"
		, "<span class='mw-editsection-bracket'>[</span><a href='/w/index.php?title=Page_1&amp;action=edit&amp;section=1' title='Edit section: Section_1'>edit</a>"
		, "<span class='mw-editsection-bracket'>]</span>"
		, "</span>"
		, "</h2>"
		)).Test__html(Gfh_utl.Replace_apos_concat_lines
		( "<h2><span class='mw-headline' id='Section_1'>Section_1</span>"
		, ""
		, "</h2>"
		));
	}
//		@Test   public void Smoke() {
//			fxt.Exec__parse(Io_mgr.Instance.LoadFilStr("C:\\xowa\\dev\\wm.updater.src.html"));
//			Io_mgr.Instance.SaveFilBry("C:\\xowa\\dev\\wm.updater.trg.html", fxt.Hdoc().Converted());
//		}
}
