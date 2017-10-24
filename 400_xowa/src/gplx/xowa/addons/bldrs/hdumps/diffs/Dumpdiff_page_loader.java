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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
import gplx.dbs.*;
class Dumpdiff_page_loader {
	private final    Db_attach_mgr attach_mgr = new Db_attach_mgr();
	private int select_count, cutoff_page_id;
	public Dumpdiff_page_loader(Xowe_wiki cur_wiki, Xowe_wiki prv_wiki, int select_count) {
		this.select_count = select_count;
		attach_mgr.Conn_main_(cur_wiki.Data__core_mgr().Db__core().Conn());
		attach_mgr.Conn_links_(new Db_attach_itm("prv_db", prv_wiki.Data__core_mgr().Db__core().Conn()));
	}
	public void Load(List_adp rv) {
		String sql = String_.Format(String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  cur.page_id"
		, ",       cur.page_namespace  AS ns_id"
		, ",       cur.page_title      AS ttl_bry"
		, ",       cur.page_html_db_id AS cur_html_db_id"
		, ",       prv.page_html_db_id AS prv_html_db_id"
		, "FROM    page cur"
		, "        JOIN <prv_db>page prv ON cur.page_id = prv.page_id"
		, "WHERE   cur.page_id > {0}"
		, "AND     cur.page_html_db_id != -1"
		, "LIMIT {1}"
		), cutoff_page_id, select_count); 
		sql = attach_mgr.Resolve_sql(sql);

		attach_mgr.Attach();
		Db_rdr rdr = attach_mgr.Conn_main().Stmt_sql(sql).Exec_select__rls_auto();
		while (rdr.Move_next()) {
			cutoff_page_id = rdr.Read_int("page_id");
			rv.Add(new Dumpdiff_page_itm(cutoff_page_id
				, rdr.Read_int("ns_id"), rdr.Read_bry_by_str("ttl_bry")
				, rdr.Read_int("cur_html_db_id"), rdr.Read_int("prv_html_db_id")
				));
		}
		attach_mgr.Detach();
	}
}
