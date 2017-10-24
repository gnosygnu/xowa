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
package gplx.xowa.addons.wikis.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
import org.junit.*; import gplx.xowa.addons.wikis.searchs.parsers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
public class Srch_link_wkr_sql_tst {
	private final    Srch_link_wkr_sql_fxt fxt = new Srch_link_wkr_sql_fxt();
	@Test   public void Rng() {
		fxt.Run__search("a").Test__eq
		( "select"
		);
	}
}
class Srch_link_wkr_sql_fxt {
	private final    Srch_link_wkr_sql link_wkr = new Srch_link_wkr_sql();
	public Srch_link_wkr_sql_fxt Run__search(String search) {
		// attach_mgr.Init(cur_link_tbl.conn, new Db_attach_itm("page_db", ctx.Db__core.Conn()), new Db_attach_itm("word_db", ctx.Tbl__word.conn));
		// link_wkr.Init(ctx, attach_mgr);
		// this.actl_sql = link_wkr.Write(ctx, attach_mgr);
		link_wkr.Clear();
		return this;
	}
	public void Test__eq(String... v) {
	}
}
