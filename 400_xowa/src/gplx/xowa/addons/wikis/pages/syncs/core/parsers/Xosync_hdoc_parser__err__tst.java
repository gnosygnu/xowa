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
public class Xosync_hdoc_parser__err__tst {
	@Before public void init() {fxt.Clear();} private final    Xosync_hdoc_parser__fxt fxt = new Xosync_hdoc_parser__fxt();
	@Test   public void Url_does_not_start_with_upload_wikimedia_org() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//fail/wikipedia/commons/thumb/7/70/A.png/220px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:img src does not start with known sequence--><img src='//fail/wikipedia/commons/thumb/7/70/A.png/220px-A.png'>"));
	}
	@Test   public void Unknown_repo() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wiktionary/fr/thumb/7/70/A.png/220px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:unknown repo--><img src='//upload.wikimedia.org/wiktionary/fr/thumb/7/70/A.png/220px-A.png'>"));
	}
	@Test   public void Bad_md5() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/fail/A.png/220px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:invalid md5--><img src='//upload.wikimedia.org/wikipedia/commons/thumb/fail/A.png/220px-A.png'>"));
	}
	@Test   public void Missing_px() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:missing px--><img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220-A.png'>"));
	}
	@Test   public void Bad_file_w() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220_fail_px-A.png'>"))
			.Test__html(Gfh_utl.Replace_apos("<!--wm.parse:invalid file_w--><img src='//upload.wikimedia.org/wikipedia/commons/thumb/7/70/A.png/220_fail_px-A.png'>"));
	}
	@Test   public void Comment() {
		fxt.Exec__parse(Gfh_utl.Replace_apos("<i>a<!-- - --></i><b>b</b><i>c</i>"))
			.Test__html(Gfh_utl.Replace_apos("<i>a</i><b>b</b><i>c</i>"));
	}
}
