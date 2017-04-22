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
package gplx.xowa.addons.bldrs.mass_parses.parses.pools; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.dbs.*;
import gplx.xowa.addons.bldrs.mass_parses.parses.wkrs.*; import gplx.xowa.addons.bldrs.mass_parses.parses.utls.*; import gplx.xowa.addons.bldrs.mass_parses.parses.locks.*;
public class Xomp_page_pool_loader {
	private final    Xow_wiki wiki;
	private final    int num_pages_per_load;
	private final    Db_attach_mgr attach_mgr;
	private final    boolean show_msg__fetched_pool;
	public Xomp_page_pool_loader(Xow_wiki wiki, Db_conn make_conn, int num_pages_per_load, boolean show_msg__fetched_pool) {
		this.wiki = wiki;
		this.make_conn = make_conn;
		this.attach_mgr = new Db_attach_mgr(make_conn);
		this.num_pages_per_load  = num_pages_per_load;
		this.show_msg__fetched_pool = show_msg__fetched_pool;
	}
	public Db_conn Conn() {return make_conn;} private final    Db_conn make_conn;
	public int Get_pending_count() {
		Db_rdr rdr = make_conn.Stmt_sql("SELECT Count(*) AS Count_of FROM xomp_page mp WHERE mp.page_status = 0").Exec_select__rls_auto();
		try {
			return rdr.Move_next() ? rdr.Read_int("Count_of") : 0;
		} finally {rdr.Rls();}
	}
	public List_adp Load(Xomp_mgr_db mgr_db, String machine_name, List_adp list, int list_idx, int list_len) {
		List_adp rv = List_adp_.New();

		// add remaining pages from old pool to new_pool;
		for (int i = list_idx; i < list_len; ++i) {
			rv.Add((Xomp_page_itm)list.Get_at(i));
		}
		
		// load pages into new pool
		Xomp_lock_mgr lock_mgr = mgr_db.Lock_mgr();
		int uid_db = lock_mgr.Uid_prv__get(machine_name);
		if (uid_db == Xomp_lock_mgr__fsys.Uid__eos) return rv; // assert that uids must be incrementally larger; handle one machine reaching end, and putting -1 in queue;
		int uid_new = 0;
		try {uid_new = this.Load_from_db(rv, uid_db);}
		finally {lock_mgr.Uid_prv__rls(machine_name, uid_new);}
		if (show_msg__fetched_pool)
			Gfo_usr_dlg_.Instance.Note_many("", "", "fetched new pool: old=~{0} new=~{1}", uid_db, uid_new);
		return rv;
	}
	private int Load_from_db(List_adp list, int uid_prv) {
		// prepare for page_tbl
		String sql = String_.Format(String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  mp.xomp_uid"
		, ",       pp.page_id"
		, ",       pp.page_namespace"
		, ",       pp.page_title"
		, ",       pp.page_text_db_id"
		, ",       pp.page_score"
		, "FROM    xomp_page mp"
		, "        JOIN <page_db>page pp ON mp.page_id = pp.page_id"
		, "WHERE   mp.xomp_uid > {0}"
		, "AND     mp.page_status = 0"
		, "LIMIT   {1}"
		), uid_prv, num_pages_per_load);
		this.attach_mgr.Conn_links_(new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		sql = attach_mgr.Resolve_sql(sql);

		// run page_tbl
		int rv = -1;
		Xomp_text_db_loader text_db_loader = new Xomp_text_db_loader(wiki);
		attach_mgr.Attach();
		Db_rdr rdr = make_conn.Stmt_sql(sql).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				rv = rdr.Read_int("xomp_uid");
				int text_db_id = rdr.Read_int("page_text_db_id");
				Xomp_page_itm ppg = new Xomp_page_itm(rdr.Read_int("page_id"));
				ppg.Init_by_page
				( rdr.Read_int("page_namespace")
				, rdr.Read_bry_by_str("page_title")
				, text_db_id
				, rdr.Read_int("page_score")
				);
				list.Add(ppg);
				text_db_loader.Add(text_db_id, ppg);
			}
		} finally {rdr.Rls();}
		attach_mgr.Detach();

		text_db_loader.Load();
		return rv;
	}
}
