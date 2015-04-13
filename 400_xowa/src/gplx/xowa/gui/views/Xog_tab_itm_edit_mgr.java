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
package gplx.xowa.gui.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
import gplx.gfui.*; import gplx.xowa.html.*; import gplx.xowa.pages.*;
public class Xog_tab_itm_edit_mgr {
	public static void Save(Xog_tab_itm tab, boolean quick_save) {
		if (tab.View_mode() != Xopg_view_mode.Tid_edit) return;	// exit if not edit; handles ctrl+s being pressed in read/html modes
		Xoae_page page = tab.Page(); Xowe_wiki wiki = tab.Wiki(); Xog_win_itm win_itm = tab.Tab_mgr().Win();
		byte[] new_text = Get_new_text(tab);
		if (page.Edit_mode() == Xoa_page_.Edit_mode_create) {
			wiki.Db_mgr().Save_mgr().Data_create(page.Ttl(), new_text);
			page.Edit_mode_update_();	// set to update so that next save does not try to create
		}
		else
			wiki.Db_mgr().Save_mgr().Data_update(page, new_text);
		Invalidate(wiki);
		page.Data_raw_(new_text);
		wiki.ParsePage_root(page, true);			// refresh html
		win_itm.Usr_dlg().Prog_one("", "", "saved page ~{0}", String_.new_utf8_(page.Ttl().Full_txt_raw()));	// NOTE: show message after ParsePage_root, b/c ParsePage_root will flash "Loading page"; DATE:2014-05-17
		if (!quick_save) {							// full_save; save page and go to read mode
			page.Html_data().Edit_preview_(Bry_.Empty);
			Xoae_page stack_page = tab.History_mgr().Cur_page(wiki);			// NOTE: must be to CurPage() else changes will be lost when going Bwd,Fwd
			stack_page.Data_raw_(page.Data_raw());							// NOTE: overwrite with "saved" changes
			stack_page.Wikie().ParsePage_root(page, true);					// NOTE: must reparse page if (a) Edit -> Read; or (b) "Options" save
			win_itm.Page__mode_(Xopg_view_mode.Tid_read);
			win_itm.Page__async__bgn(tab);
		}
//			wiki.Db_mgr().Hdump_mgr().Save(page);
	}
	public static void Preview(Xog_tab_itm tab) {
		if (tab.View_mode() != Xopg_view_mode.Tid_edit) return;	// exit if not edit; handles preview somehow being called?
		Xoae_page page = tab.Page(); Xowe_wiki wiki = tab.Wiki(); Xog_win_itm win_itm = tab.Tab_mgr().Win();
		Xog_html_itm html_itm = tab.Html_itm();

		byte[] new_text = Get_new_text(tab);
		Xoae_page new_page = Xoae_page.new_(wiki, page.Ttl());
		new_page.Revision_data().Id_(page.Revision_data().Id());	// NOTE: page_id needed for sqlite (was not needed for xdat)
		new_page.Data_raw_(new_text);
		wiki.ParsePage_root(new_page, true);						// refresh html
		tab.Page_(new_page); new_page.Tab_data().Tab_(tab);			// replace old page with new_page; DATE:2014-10-09

		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		Xoh_page_wtr_wkr wkr = wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read);
		wkr.Page_(new_page);
		wkr.XferAry(tmp_bfr, 0);
		byte[] new_html = tmp_bfr.To_bry_and_rls();
		new_page.Html_data().Edit_preview_(new_html);

		Invalidate(wiki);
		win_itm.Page__mode_(Xopg_view_mode.Tid_edit);
		html_itm.Scroll_page_by_id_gui(Xog_html_itm.Elem_id__first_heading);// NOTE: was originally directly; changed to call on thread; DATE:2014-05-03
		win_itm.Page__async__bgn(tab);	// NOTE: needed to show images during preview; DATE:2014-06-21
	}
	public static void Rename(Xog_tab_itm tab) {
		if (tab.View_mode() != Xopg_view_mode.Tid_edit) return;	// exit if not edit; handles ctrl+r being pressed
		Xoae_page page = tab.Page(); Xowe_wiki wiki = tab.Wiki(); Xog_win_itm win_itm = tab.Tab_mgr().Win();
		if (Bry_.Eq(page.Ttl().Page_db(), wiki.Props().Main_page())) {
			win_itm.Usr_dlg().Warn_many("", "", "The Main Page cannot be renamed");
			win_itm.Kit().Ask_ok("", "", "The Main Page cannot be renamed");
			return;
		}
		byte[] new_text = Bry_.new_utf8_(tab.Html_itm().Get_elem_value(Xog_html_itm.Elem_id__xowa_edit_rename_box));
		if (Bry_.Len_eq_0(new_text)) return;		// no ttl given; exit
		new_text = Xoa_ttl.Replace_spaces(new_text);	// ttls cannot have spaces; only underscores
		Xoa_ttl new_ttl = Xoa_ttl.parse_(wiki, new_text);
		int new_ns_id = new_ttl.Ns().Id();
		if (new_ns_id != Xow_ns_.Id_main) {
			win_itm.Usr_dlg().Warn_many("", "", "The new page name must remain in the same namespace");
			return;
		}
		wiki.Db_mgr().Save_mgr().Data_rename(page, new_ns_id, new_text);
		page.Ttl_(Xoa_ttl.parse_(wiki, Bry_.Add(page.Ttl().Ns().Name_db_w_colon(), new_text)));
		win_itm.Page__mode_(Xopg_view_mode.Tid_read);
		win_itm.Usr_dlg().Prog_one("", "", "renamed page to {0}", String_.new_utf8_(page.Ttl().Full_txt_raw()));
	}
	public static void Focus(Xog_win_itm win, String elem_focus_id) {
		Gfui_html html_box = win.Active_html_box();
		html_box.Focus();
		html_box.Html_elem_focus(elem_focus_id);
	}
	public static void Debug(Xog_win_itm win, byte view_tid) {
		Xog_tab_itm tab = win.Tab_mgr().Active_tab(); Xoae_page page = tab.Page();
		Xowe_wiki wiki = tab.Wiki(); Xop_ctx ctx = wiki.Ctx();
		ctx.Defn_trace().Clear(); // TODO: move_me
		ctx.Defn_trace_(Xot_defn_trace_dbg._);
		Xoa_ttl ttl = page.Ttl();
		Xoae_page new_page = Xoae_page.new_(wiki, ttl);
		byte[] data = tab.Html_itm().Get_elem_value_for_edit_box_as_bry();
		new_page.Data_raw_(data);
		wiki.ParsePage_root(new_page, true);
		Bry_bfr bfr = win.App().Utl__bfr_mkr().Get_m001();
		bfr.Add(new_page.Root().Root_src());
		wiki.Ctx().Defn_trace().Print(data, bfr);
		new_page.Data_raw_(bfr.To_bry_and_rls());
		byte old = tab.View_mode();
		tab.View_mode_(view_tid);
		Xog_tab_itm_read_mgr.Show_page(tab, new_page, false);
		tab.History_mgr().Add(new_page);
		tab.View_mode_(old);
	}
	private static void Invalidate(Xowe_wiki wiki) {// invalidate everything on updates; especially needed for page transclusion; {{/my_subpage}} DATE:2014-04-10
		gplx.xowa.xtns.scribunto.Scrib_core.Core_invalidate();
		wiki.Cache_mgr().Free_mem_all();
	}
	private static byte[] Get_new_text(Xog_tab_itm tab) {
		byte[] rv = tab.Html_itm().Get_elem_value_for_edit_box_as_bry();
		rv = Bry_.Trim(rv, 0, rv.length, false, true, Bry_.Trim_ary_ws);	// MW: trim all trailing ws; REF:EditPage.php!safeUnicodeInput; DATE:2014-04-25
		return rv;
	}
}
