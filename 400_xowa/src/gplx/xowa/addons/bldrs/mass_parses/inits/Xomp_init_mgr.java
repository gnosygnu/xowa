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
