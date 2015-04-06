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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.threads.*; import gplx.dbs.*;
public class Xoud_history_mgr implements GfoInvkAble {
	private Xoud_history_tbl history_tbl;
	public void Conn_(Db_conn new_conn, boolean created, int user_id) {
		history_tbl = new Xoud_history_tbl(new_conn);
		if (created) history_tbl.Create_tbl();
	}
	public void Update_async(Gfo_async_mgr async_mgr, Xoa_ttl ttl, Xoa_url url) {
//			if (Skip_history(ttl)) return;
//			async_mgr.Queue(this, Invk_update, "wiki", String_.new_utf8_(url.Wiki_bry()), "page", String_.new_utf8_(url.Page_bry()), "qarg", String_.new_utf8_(url.Args_all_as_bry()));
	}
	private void Update(String wiki, String page, String qarg) {
		Xoud_history_row row = history_tbl.Select_by_page(wiki, page, qarg);
		DateAdp time = DateAdp_.Now();
		if (row == null)
			history_tbl.Insert(wiki, page, qarg, time, 1);
		else
			history_tbl.Update(wiki, page, qarg, time, row.History_count() + 1);
	}
	public void Select(ListAdp rv, int top) {
		history_tbl.Select_by_top(rv, top);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_update))		Update(m.ReadStr("wiki"), m.ReadStr("page"), m.ReadStr("qarg"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_update = "update";
	public static boolean Skip_history(Xoa_ttl ttl) {
		byte[] page_db = ttl.Page_db();
		return	(	ttl.Ns().Id_special()
				&&	(	Bry_.Eq(page_db, gplx.xowa.users.history.Xou_history_mgr.Ttl_name)	// do not add XowaPageHistory to history
					||	Bry_.Eq(page_db, gplx.xowa.specials.xowa.popup_history.Popup_history_page.Ttl_name_bry)
					||	Bry_.Eq(page_db, gplx.xowa.specials.xowa.default_tab.Default_tab_page.Ttl_name_bry)
					||	Bry_.Eq(page_db, Xoud_history_special.Ttl_name)
					)
				);
	}
}
