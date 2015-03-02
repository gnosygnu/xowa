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
import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*; import gplx.xowa.dbs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa2.gui.*;
public class Xohd_hdump_wtr {
	private final Bry_bfr_mkr bfr_mkr; private final Xoh_page_wtr_mgr page_wtr_mgr;
	private final Xohd_page_html_mgr__save save_mgr;  private Xowe_core_data_mgr core_data_mgr;
	public Xohd_hdump_wtr(Xoa_app app, Xowe_wiki wiki) {
		this.bfr_mkr = app.Utl__bfr_mkr(); this.page_wtr_mgr = wiki.Html_mgr().Page_wtr_mgr();
		save_mgr = new Xohd_page_html_mgr__save();			
	}
	public void Init_by_db(Xowe_core_data_mgr core_data_mgr) {
		this.core_data_mgr = core_data_mgr;
	}
	public void Save(Xoae_page page) {
		Bry_bfr tmp_bfr = bfr_mkr.Get_m001();
		Generate_hdump(tmp_bfr, page);
		int html_db_id = page.Revision_data().Html_db_id();
		Xowd_db_file hdump_db = Xowd_db_file.Null;
		if (html_db_id == -1) {
			hdump_db = core_data_mgr.Dbs__get_by_tid_nth_or_new(Xowd_db_file_.Tid_html);
			html_db_id = hdump_db.Id();
			page.Revision_data().Html_db_id_(html_db_id);
			core_data_mgr.Tbl__pg().Update_html_db_id(page.Revision_data().Id(), html_db_id);
		}
		else {
			return;
		}
		save_mgr.Update(tmp_bfr, Xohd_page_html_tbl.Get_from_db(core_data_mgr, hdump_db), page);
		tmp_bfr.Mkr_rls();
	}
	public void Generate_hdump(Bry_bfr tmp_bfr, Xoae_page page) {
		page.File_queue().Clear();	// need to reset uid to 0, else xowa_file_# will resume from last
		page_wtr_mgr.Wkr(Xopg_view_mode.Tid_read).Write_body(tmp_bfr, Xoh_wtr_ctx.Hdump, page);
		page.Hdump_data().Body_(tmp_bfr.Xto_bry_and_clear());
	}
}