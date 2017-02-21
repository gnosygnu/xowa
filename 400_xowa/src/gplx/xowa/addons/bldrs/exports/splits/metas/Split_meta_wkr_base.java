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
package gplx.xowa.addons.bldrs.exports.splits.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
public abstract class Split_meta_wkr_base {
	private final    Db_attach_mgr attach_mgr = new Db_attach_mgr();
	public void Load(Db_conn wkr_conn, Split_page_mgr page_mgr, int ns_id, int score_bgn, int score_end) {
		attach_mgr.Conn_main_(wkr_conn);
		String sql_fmt = Load_sql(attach_mgr, ns_id, score_bgn, score_end);
		String sql = attach_mgr.Resolve_sql(String_.Format(sql_fmt, score_bgn, score_end, ns_id));
		attach_mgr.Attach();
		Db_rdr rdr = wkr_conn.Stmt_sql(sql).Exec_select__rls_auto();
		Split_page_itm page = null;
		byte tid = this.Tid();
		while (rdr.Move_next()) {
			int page_id	= rdr.Read_int("page_id");
			if (page == null || page.Page_id() != page_id) {
				page = page_mgr.Get_by_or_null(page_id);
				if (page == null) {
					page = new Split_page_itm(true, page_id);
					page_mgr.Add(page);
				}
			}
			Split_page_list list = page.Get_by_or_make(tid);
			list.Add(Load_itm(rdr));
		}
		attach_mgr.Detach();
	}
	public void Save(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Split_page_itm page_itm) {
		Split_page_list list = page_itm.Get_by_or_null(this.Tid()); if (list == null) return;
		int len = list.Len();
		for (int i = 0; i < len; ++i)
			this.Save_itm(ctx, rslt_mgr, list.Get_at(i));
	}
	public abstract byte Tid();
	public abstract void On_nth_new(Split_ctx ctx, Db_conn wkr_conn);
	public abstract void On_nth_rls(Split_ctx ctx, Db_conn trg_conn);
	protected abstract String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end);	// gen sql and attach_itms
	protected abstract Object Load_itm(Db_rdr rdr);
	protected abstract void   Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm);
}
