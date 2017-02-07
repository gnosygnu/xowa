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
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
class Xomp_init_mgr {
	public Xomp_init_mgr_cfg Cfg() {return cfg;} private final    Xomp_init_mgr_cfg cfg = new Xomp_init_mgr_cfg();
	public void Exec(Xowe_wiki wiki) {
		// init vars
		cfg.Init(wiki);
		Xomp_mgr_db mgr_db = Xomp_mgr_db.New__make(wiki);
		Db_conn mgr_conn = mgr_db.Conn();

		// remake all tbls
		mgr_db.Remake();

		// insert ns into cfg; need for lnki_temp.tier in xomp.make
		mgr_db.Tbl__cfg().Insert_str("", gplx.xowa.addons.bldrs.mass_parses.parses.wkrs.Xomp_parse_wkr.Cfg__ns_ids, Int_.Ary_concat("|", cfg.Ns_ids()));

		// fill page tbl
		Db_attach_mgr attach_mgr = new Db_attach_mgr(mgr_conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		int[] ns_ary = cfg.Ns_ids();
		int len = ns_ary.length;
		String sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "INSERT INTO xomp_page (page_id, page_ns, page_status, html_len, xomp_wkr_id)"
		, "SELECT p.page_id, p.page_namespace, 0, 0, 0"
		, "FROM   <page_db>page p"
		, "WHERE  p.page_namespace = {0}"
		, "AND    p.page_is_redirect = 0"
		, "ORDER BY p.page_id"
		); 
		for (int i = 0; i < len; ++i) {
			int ns_id = ns_ary[i];
			attach_mgr.Exec_sql_w_msg("adding rows for xomp_page: ns=" + ns_id, sql, ns_id);
		}
	}
}
