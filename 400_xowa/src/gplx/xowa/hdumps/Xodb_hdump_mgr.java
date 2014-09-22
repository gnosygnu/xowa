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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.html.*; import gplx.xowa.gui.*; 
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.saves.*; import gplx.xowa.pages.*; import gplx.xowa.hdumps.loads.*; import gplx.xowa.hdumps.htmls.*; import gplx.xowa.hdumps.dbs.*;
public class Xodb_hdump_mgr {
	private Xodb_file hdump_db_file;
	public Xodb_hdump_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		load_mgr = new Hdump_load_mgr();
		Tbl_(new Hdump_text_tbl());
		text_tbl.Init_by_wiki(wiki);
		Xoa_app app = wiki.App();
		html_mgr.Init_by_app(app.Usr_dlg(), app.Fsys_mgr(), app.Encoder_mgr().Fsys());
	}
	public Xow_wiki Wiki() {return wiki;} private final Xow_wiki wiki; 
	@gplx.Internal protected Hdump_load_mgr Load_mgr() {return load_mgr;} private Hdump_load_mgr load_mgr;
	@gplx.Internal protected Hdump_save_mgr Save_mgr() {return save_mgr;} private Hdump_save_mgr save_mgr = new Hdump_save_mgr();
	public Hdump_html_body Html_mgr() {return html_mgr;} private Hdump_html_body html_mgr = new Hdump_html_body();
	public Hdump_text_tbl Text_tbl() {return text_tbl;} private Hdump_text_tbl text_tbl;
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;}	private boolean enabled;
	@gplx.Internal protected void Tbl_mem_() {Tbl_(new Hdump_text_tbl_mem());}
	public int Html_db_id_default(int page_len) {
		return -1;
	}
	public Db_provider Db_provider_by_page(int page_id) {
		return text_tbl.Provider();
	}
	public void Save_if_missing(Xoa_page page) {
		if (page.Revision_data().Html_db_id() == -1) Save(page);
	}
	public void Save(Xoa_page page) {
		if (!Enabled_chk()) return;
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_m001();
		this.Write(tmp_bfr, page);
		save_mgr.Update(page);
		wiki.Db_mgr_as_sql().Tbl_page().Update_html_db_id(page.Revision_data().Id(), hdump_db_file.Id());
		tmp_bfr.Mkr_rls();
	}
	public void Write(Bry_bfr bfr, Xoa_page page) {
		page.File_queue().Clear();	// need to reset uid to 0, else xowa_file_# will resume from last
		Xoh_page_wtr_wkr wkr = wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read);
		wkr.Write_body(bfr, Xoh_wtr_ctx.Hdump, page);
		page.Hdump_data().Body_(bfr.XtoAryAndClear());
	}
	public void Load(Xow_wiki wiki, Xoa_page page, int html_db_id) {
		if (!Enabled_chk()) return;
		page.Root_(new Xop_root_tkn());
		Hdump_page hpg = new Hdump_page().Init(page.Revision_data().Id(), page.Url());
		load_mgr.Load(hpg, wiki.Db_mgr_as_sql().Fsys_mgr(), html_db_id, page.Revision_data().Id());
		Load_page(wiki, page, hpg);
	}
	private void Load_page(Xow_wiki wiki, Xoa_page page, Hdump_page hpg) {
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_m001();
		html_mgr.Init_by_page(wiki.Domain_bry(), hpg).Write(tmp_bfr);
		page.Hdump_data().Body_(tmp_bfr.XtoAryAndClear());
		Xopg_html_data html_data = page.Html_data();
		html_data.Display_ttl_(hpg.Display_ttl());
		html_data.Content_sub_(hpg.Content_sub());
		html_data.Xtn_skin_mgr().Add(new Xopg_xtn_skin_itm_mock(hpg.Sidebar_div()));
		Hdump_page_body_srl.Load_html_modules(html_data.Module_mgr(), hpg);
		tmp_bfr.Mkr_rls();
	}
	private void Tbl_(Hdump_text_tbl v) {
		text_tbl = v;
		save_mgr.Tbl_(text_tbl);
//			load_mgr.Tbl_(text_tbl);
	}
	private boolean Enabled_chk() {
		if (enabled && hdump_db_file == null) hdump_db_file = Xodb_hdump_mgr_setup.Hdump_db_file_init(this);
		return enabled;
	}
}
