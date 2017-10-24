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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.threads.*; import gplx.dbs.*;
import gplx.xowa.specials.*;
public class Xoud_history_mgr implements Gfo_invk {
	private Xoud_history_tbl history_tbl;
	public void Conn_(Db_conn conn, boolean created) {
		this.history_tbl = new Xoud_history_tbl(conn);
		if (!conn.Meta_tbl_exists(history_tbl.Tbl_name())) history_tbl.Create_tbl();
	}
	public void Update_async(Gfo_async_mgr async_mgr, Xoa_ttl ttl, Xoa_url url) {
//			if (Skip_history(ttl)) return;
//			async_mgr.Queue(this, Invk_update, "wiki", String_.new_u8(url.Wiki_bry()), "page", String_.new_u8(url.Page_bry()), "qarg", String_.new_u8(url.Args_all_as_bry()));
	}
	private void Update(String wiki, String page, String qarg) {
//			Xoud_history_row row = history_tbl.Select_by_page(wiki, page, qarg);
//			DateAdp time = Datetime_now.Get();
//			if (row == null)
//				history_tbl.Insert(wiki, page, qarg, time, 1);
//			else
//				history_tbl.Update(wiki, page, qarg, time, row.Count() + 1);
	}
	public void Select(List_adp rv, int top) {
		history_tbl.Select_by_top(rv, top);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_update))		Update(m.ReadStr("wiki"), m.ReadStr("page"), m.ReadStr("qarg"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_update = "update";
	public static boolean Skip_history(Xoa_ttl ttl) {
		byte[] page_db = ttl.Page_db();
		return	(	ttl.Ns().Id_is_special()
				&&	(	Bry_.Eq(page_db, gplx.xowa.users.history.Xou_history_mgr.Ttl_name)	// do not add XowaPageHistory to history
					||	Bry_.Eq(page_db, Xow_special_meta_.Itm__popup_history.Key_bry())
					||	Bry_.Eq(page_db, Xow_special_meta_.Itm__default_tab.Key_bry())
					||	Bry_.Eq(page_db, Xow_special_meta_.Itm__page_history.Key_bry())
					)
				);
	}
}
