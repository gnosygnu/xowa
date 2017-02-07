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
