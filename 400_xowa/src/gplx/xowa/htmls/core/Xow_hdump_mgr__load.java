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
package gplx.xowa.htmls.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.heads.*; import gplx.xowa.htmls.core.makes.*; import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.skins.*;
public class Xow_hdump_mgr__load {
	private final Xow_wiki wiki; private final Xow_hzip_mgr hzip_mgr;
	private final Xoh_page tmp_hpg; private final Xowd_page_itm tmp_dbpg = new Xowd_page_itm();
	public Xow_hdump_mgr__load(Xow_wiki wiki, Xow_hzip_mgr hzip_mgr, Xoh_page tmp_hpg, Bry_bfr tmp_bfr) {
		this.wiki = wiki; this.hzip_mgr = hzip_mgr; this.tmp_hpg = tmp_hpg;
		this.make_mgr = new Xoh_make_mgr(wiki.App().Usr_dlg(), wiki.App().Fsys_mgr(), Xoa_app_.Utl__encoder_mgr().Fsys(), wiki.Domain_bry());			
	}
	public Xoh_make_mgr Make_mgr() {return make_mgr;} private final Xoh_make_mgr make_mgr;
	public void Load(Xoae_page wpg) {
		Load(tmp_hpg, wpg.Ttl());
		wpg.Hdump_data().Body_(tmp_hpg.Body());
		// wpg.Root_(new Xop_root_tkn());
		Fill_page(wpg, tmp_hpg);
		// foreach (gplx.xowa.htmls.core.makes.imgs.Xohd_img_itm__base itm in hpg.Img_itms())
			// wpg.Hdump_data().Imgs_add(itm);
	}
	public boolean Load(Xoh_page hpg, Xoa_ttl ttl) {
		synchronized (tmp_dbpg) {
			if (!Load__dbpg(tmp_dbpg.Clear(), hpg, ttl)) return Load__fail(hpg);		// nothing in "page" table
			Xowd_db_file html_db = wiki.Data__core_mgr().Dbs__get_at(tmp_dbpg.Html_db_id());
			hpg.Init(hpg.Wiki(), hpg.Url(), ttl, tmp_dbpg.Id());
			if (!html_db.Tbl__html_page().Select_by_page(hpg)) return Load__fail(hpg);	// nothing in "html_page" table
			byte[] src = hzip_mgr.Parse(hpg.Url_bry_safe(), hpg.Body_flag(), hpg.Body());
			hpg.Body_(make_mgr.Parse(hpg, src));
			return true;
		}
	}
	private boolean Load__fail(Xoh_page hpg) {hpg.Exists_n_(); return false;}
	private boolean Load__dbpg(Xowd_page_itm dbpg, Xoh_page hpg, Xoa_ttl ttl) {
		wiki.Data__core_mgr().Tbl__page().Select_by_ttl(dbpg, ttl.Ns(), ttl.Page_db());
		if (dbpg.Redirect_id() != -1) Load__dbpg__redirects(dbpg);
		return dbpg.Html_db_id() != -1;
	}
	private void Load__dbpg__redirects(Xowd_page_itm dbpg) {
		int redirect_count = 0;
		while (redirect_count < 5) {
			int redirect_id = dbpg.Redirect_id();
			wiki.Data__core_mgr().Tbl__page().Select_by_id(dbpg, redirect_id);
			if (redirect_id == -1) break;
		}
	}
	private void Fill_page(Xoae_page wpg, Xoh_page hpg) {
		Xopg_html_data html_data = wpg.Html_data();
		html_data.Display_ttl_(tmp_hpg.Display_ttl());
		html_data.Content_sub_(tmp_hpg.Content_sub());			
		html_data.Xtn_skin_mgr().Add(new Xopg_xtn_skin_itm_stub(tmp_hpg.Sidebar_div()));
		Xoh_head_mgr wpg_head = html_data.Head_mgr();
		Xopg_module_mgr hpg_head = hpg.Head_mgr();			
		wpg_head.Itm__mathjax().Enabled_		(hpg_head.Math_exists());
		wpg_head.Itm__popups().Bind_hover_area_	(hpg_head.Imap_exists());
		wpg_head.Itm__gallery().Enabled_		(hpg_head.Gallery_packed_exists());
		wpg_head.Itm__hiero().Enabled_			(hpg_head.Hiero_exists());
	}
}
