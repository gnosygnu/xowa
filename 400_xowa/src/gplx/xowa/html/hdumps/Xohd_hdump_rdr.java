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
package gplx.xowa.html.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.html.hdumps.data.*; import gplx.xowa.html.hzips.*; import gplx.xowa.html.hdumps.abrvs.*; import gplx.xowa.html.hdumps.pages.*;
import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*; import gplx.xowa.html.modules.*;
import gplx.xowa.wikis.data.*; import gplx.xowa2.gui.*;
public class Xohd_hdump_rdr {
	private final Bry_bfr_mkr bfr_mkr; private final Xow_hzip_mgr hzip_mgr;
	private final Xohd_abrv_mgr abrv_mgr; private final Xohd_page_html_mgr__load load_mgr; 
	private Xow_core_data_mgr core_data_mgr; private final Xodb_page dbpg = new Xodb_page(); 
	public Xohd_hdump_rdr(Xoa_app app, Xow_wiki wiki) {
		this.bfr_mkr = app.Utl__bfr_mkr(); this.hzip_mgr = wiki.Html_mgr__hzip_mgr();
		abrv_mgr = new Xohd_abrv_mgr(app.Usr_dlg(), app.Fsys_mgr(), app.Utl__encoder_mgr().Fsys(), wiki.Domain_bry());
		load_mgr = new Xohd_page_html_mgr__load();	// TODO: get db_id
	}
	public void Init_by_db(Xow_core_data_mgr core_data_mgr) {this.core_data_mgr = core_data_mgr;}
	public void Get_by_ttl(Xoae_page page) {
		Xog_page hpg = new Xog_page();
		Get_by_ttl(hpg, page.Ttl());
		page.Hdump_data().Body_(hpg.Page_body());
		page.Root_(new Xop_root_tkn());
		Xopg_html_data html_data = page.Html_data();
		html_data.Display_ttl_(hpg.Display_ttl());
		html_data.Content_sub_(hpg.Content_sub());
		html_data.Xtn_skin_mgr().Add(new Xopg_xtn_skin_itm_stub(hpg.Sidebar_div()));
		Load_module_mgr(html_data.Module_mgr(), hpg);
		for (gplx.xowa.html.hdumps.core.Xohd_data_itm__base itm : hpg.Img_itms())
			page.Hdump_data().Imgs_add(itm);
	}
	public void Get_by_ttl(Xog_page rv, Xoa_ttl ttl) {
		synchronized (dbpg) {
			dbpg.Clear();
			if (!Get_by_ttl__fill_hpg(rv, ttl)) {
				rv.Exists_n_();
				return;
			}
			Bry_bfr bfr = bfr_mkr.Get_m001();
			byte[] body_bry = abrv_mgr.Parse(bfr, rv);
			body_bry = hzip_mgr.Parse(bfr, ttl.Page_db(), body_bry);
			bfr.Mkr_rls();
			rv.Page_body_(body_bry);
		}
	}
	private boolean Get_by_ttl__fill_hpg(Xog_page rv, Xoa_ttl ttl) {
		core_data_mgr.Tbl__pg().Select_by_ttl(dbpg, ttl.Ns(), ttl.Page_db());	// get rows from db
		if (dbpg.Redirect_id() != -1) Get_by_ttl__resolve_redirect(dbpg, rv);
		if (dbpg.Html_db_id() == -1) return false;								// dbpg does not hdump; exit;
		rv.Init(dbpg.Id(), null, ttl);	// FIXME
		Xowd_db_file html_db = core_data_mgr.Dbs__get_at(dbpg.Html_db_id());
		load_mgr.Load_page(rv, Xohd_page_html_tbl.Get_from_db(core_data_mgr, html_db), dbpg.Id(), ttl);
		return true;
	}
	private void Get_by_ttl__resolve_redirect(Xodb_page dbpg, Xog_page hpg) {
		int redirect_count = 0;
		while (redirect_count < 5) {
			int redirect_id = dbpg.Redirect_id();
			core_data_mgr.Tbl__pg().Select_by_id(dbpg, redirect_id);
			if (redirect_id == -1) break;
		}
	}
	public static void Load_module_mgr(Xoh_module_mgr page_module_mgr, Xog_page hpg) {
		Xopg_module_mgr dump_module_mgr = hpg.Module_mgr();
		page_module_mgr.Itm_mathjax().Enabled_			(dump_module_mgr.Math_exists());
		page_module_mgr.Itm_popups().Bind_hover_area_	(dump_module_mgr.Imap_exists());
		page_module_mgr.Itm_gallery().Enabled_			(dump_module_mgr.Gallery_packed_exists());
		page_module_mgr.Itm_hiero().Enabled_			(dump_module_mgr.Hiero_exists());
	}
}
