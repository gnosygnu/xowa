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
import gplx.dbs.*; import gplx.core.threads.utils.*; import gplx.xowa.addons.bldrs.mass_parses.parses.utls.*;
public class Xomp_load_wkr implements Gfo_invk {
	private final    Object thread_lock = new Object();
	private final    Xow_wiki wiki;
	private final    Db_conn mgr_conn;
	private final    Db_attach_mgr attach_mgr;
	private final    Gfo_blocking_queue queue;
	private final    int num_wkrs;

	private final    Bry_bfr prog_bfr = Bry_bfr_.New();
	private int pages_done, pages_total;
	private long time_bgn, time_prv, time_done;
	public Xomp_load_wkr(Xow_wiki wiki, Db_conn mgr_conn, int num_pages_in_pool, int num_wkrs) {
		this.wiki = wiki;
		this.mgr_conn = mgr_conn;
		this.attach_mgr = new Db_attach_mgr(mgr_conn);
		this.queue = new Gfo_blocking_queue(num_pages_in_pool);
		this.num_wkrs = num_wkrs;
		this.time_bgn = this.time_prv = gplx.core.envs.System_.Ticks();
		this.pages_total = this.Get_pending_count();
	}
	public int Get_pending_count() {
		Db_rdr rdr = mgr_conn.Stmt_sql("SELECT Count(*) AS Count_of FROM xomp_page mp WHERE mp.page_status = 0").Exec_select__rls_auto();
		try {return rdr.Move_next() ? rdr.Read_int("Count_of") : 0;}
		finally {rdr.Rls();}
	}
	public Xomp_page_itm Take() {return (Xomp_page_itm)queue.Take();}
	private void Exec() {
		int prv_page_id = 0;
		while (prv_page_id != -1) {
			prv_page_id = Load_pages(prv_page_id);
		}
		for (int i = 0; i < num_wkrs; ++i)
			queue.Put(Xomp_page_itm.Null);
	}
	private int Load_pages(int prv_page_id) {
		// page_tbl.prep_sql
		String sql = String_.Format(String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "SELECT  mp.page_id"
		, ",       pp.page_namespace"
		, ",       pp.page_title"
		, ",       pp.page_text_db_id"
		, ",       pp.page_score"
		, "FROM    xomp_page mp"
		, "        JOIN <page_db>page pp ON mp.page_id = pp.page_id"
		, "WHERE   mp.page_id > {0}"
		, "AND     mp.page_status = 0"
		, "LIMIT   {1}"
		), prv_page_id, queue.Capacity());
		this.attach_mgr.Conn_links_(new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Conn()));
		sql = attach_mgr.Resolve_sql(sql);

		// page_tbl.load_sql
		Xomp_text_db_loader text_db_loader = new Xomp_text_db_loader(wiki);
		attach_mgr.Attach();
		Db_rdr rdr = mgr_conn.Stmt_sql(sql).Exec_select__rls_auto();
		List_adp list = List_adp_.New();
		int count = 0;
		try {
			while (rdr.Move_next()) {
				prv_page_id = rdr.Read_int("page_id");
				int text_db_id = rdr.Read_int("page_text_db_id");
				Xomp_page_itm ppg = new Xomp_page_itm(prv_page_id);
				ppg.Init_by_page
				( rdr.Read_int("page_namespace")
				, rdr.Read_bry_by_str("page_title")
				, text_db_id
				, rdr.Read_int("page_score")
				);
				list.Add(ppg);
				text_db_loader.Add(text_db_id, ppg);
				++count;
			}
		} finally {rdr.Rls();}
		attach_mgr.Detach();

		text_db_loader.Load();

		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			queue.Put((Xomp_page_itm)list.Get_at(i));
		}

		return count == 0 ? -1 : prv_page_id;
	}
	public void Mark_done(int id) {
		synchronized (thread_lock) {
			pages_done += 1;
			if (pages_done % 1000 == 0) {
				long time_cur = gplx.core.envs.System_.Ticks();
				int pages_left = pages_total - pages_done;
				time_done += (time_cur - time_prv);
				double rate_cur = pages_done / (time_done / Time_span_.Ratio_f_to_s);
				String time_past = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(prog_bfr, (int)((time_cur - time_bgn) / 1000), true, 0);
				String time_left = gplx.xowa.addons.bldrs.centrals.utils.Time_dhms_.To_str(prog_bfr, (int)(pages_left / rate_cur), true, 0);
				Gfo_usr_dlg_.Instance.Prog_many("", "", "done=~{0} left=~{1} rate=~{2} time_past=~{3} time_left=~{4}", pages_done, pages_left, (int)rate_cur, time_past, time_left);
				time_prv = time_cur;
			}
		}
	}
	public void Rls() {
		mgr_conn.Rls_conn();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__exec))		this.Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk__exec = "exec";
}
