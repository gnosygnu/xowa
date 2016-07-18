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
package gplx.xowa.addons.bldrs.mass_parses.inits; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_init_mgr {
	private final    Xowe_wiki wiki;
	public Xomp_init_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public void Exec() {
		// init
		Xomp_db_core db_core = Xomp_db_core.New__make(wiki);
		Xomp_page_tbl page_tbl = db_core.Mgr_db().Page_tbl();

		// rebuild table
		Db_conn mgr_conn = db_core.Mgr_db().Conn();
		mgr_conn.Meta_tbl_remake(page_tbl);

		// fill table
		Db_attach_mgr attach_mgr = new Db_attach_mgr(mgr_conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		int[] ns_ary = new int[] {0, 4, 14};
		int len = ns_ary.length;
		String sql = String_.Concat_lines_nl_skip_last
		( "INSERT INTO xomp_page (page_id, page_ns, page_status, html_len, xomp_wkr_id)"
		, "SELECT p.page_id, p.page_namespace, 0, 0, 0"
		, "FROM   <page_db>page p"
		, "WHERE  p.page_namespace = {0}"
		, "AND    p.page_is_redirect = 0"
		); 
		for (int i = 0; i < len; ++i) {
			int ns_id = ns_ary[i];
			attach_mgr.Exec_sql_w_msg("adding rows for xomp_page: ns=" + ns_id, sql, ns_id);// ANSI.Y
		}
	}
}
