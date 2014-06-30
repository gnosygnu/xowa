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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.*;
public class Xodb_save_mgr_txt implements Xodb_save_mgr {
	public Xodb_save_mgr_txt(Xow_wiki wiki, Xodb_load_mgr_txt load_mgr) {
		this.wiki = wiki;
		this.load_mgr = load_mgr;
		this.fsys_mgr = wiki.Fsys_mgr();
		this.redirect_mgr = wiki.Redirect_mgr();
	}	private Xow_wiki wiki; private Xow_fsys_mgr fsys_mgr; private Xodb_load_mgr_txt load_mgr; private Xop_redirect_mgr redirect_mgr;
	public boolean Create_enabled() {return create_enabled;} public void Create_enabled_(boolean v) {create_enabled = v;} private boolean create_enabled;
	public boolean Update_modified_on_enabled() {return update_modified_on_enabled;} public void Update_modified_on_enabled_(boolean v) {update_modified_on_enabled = v;} private boolean update_modified_on_enabled;
	public int Page_id_next() {return page_id_next;} public void Page_id_next_(int v) {page_id_next = v;} private int page_id_next = 0;
	public void Clear() {page_id_next = 0;}	// TEST: needed for ctg_test		
	public void Data_create(Xoa_ttl ttl, byte[] text) {
		Xow_ns ns_itm = ttl.Ns(); byte[] ttl_bry = ttl.Page_db();
		Xodb_page db_page = Xodb_page.tmp_();
		boolean found = load_mgr.Load_by_ttl(db_page, ns_itm, ttl_bry);
		if (found) throw Err_mgr._.fmt_(GRP_KEY, "title_exists", "create requested but title already exists: ~{0}", String_.new_utf8_(ttl_bry));
		int text_len = text.length;
		Bry_bfr tmp = wiki.Utl_bry_bfr_mkr().Get_m001();
		int page_id = page_id_next++;
		int fil_idx = 0;
		int ns_id = ttl.Ns().Id();
		Xodb_page_.Txt_page_save(tmp, page_id, DateAdp_.Now(), ttl_bry, text, true);
		Io_url page_rdr_url = fsys_mgr.Url_ns_fil(Xow_dir_info_.Tid_page, ns_id, fil_idx);
		byte[] page_rdr_bry = gplx.ios.Io_stream_rdr_.Load_all(page_rdr_url);
		Xob_xdat_file page_rdr = new Xob_xdat_file();
		if (Bry_.Len_gt_0(page_rdr_bry)) page_rdr.Parse(page_rdr_bry, page_rdr_bry.length, page_rdr_url);
		int row_idx = page_rdr.Count();
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_b512();
		page_rdr.Insert(tmp_bfr, tmp.XtoAryAndClear());
		this.Data_save(Xow_dir_info_.Tid_page, page_rdr, page_rdr_url, tmp_bfr);
		tmp_bfr.Mkr_rls();
		
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(text, text_len);
		db_page.Set_all_(page_id, fil_idx, row_idx, redirect_ttl != null, text_len, ttl.Page_db());
		Xodb_page_.Txt_ttl_save(tmp, db_page);
		byte[] ttl_row_bry = tmp.Mkr_rls().XtoAryAndClear();
		Xowd_hive_mgr ttl_hive = new Xowd_hive_mgr(wiki, Xow_dir_info_.Tid_ttl);
		ttl_hive.Create(ttl.Ns(), ttl.Page_db(), ttl_row_bry, Bry_comparer_fld_last._);
		wiki.Db_mgr().Load_mgr().Clear();	// NOTE: need to clear cached regy_ary in load_mgr
	}
	public void Data_update(Xoa_page page, byte[] text)		{Data_update_under(page, text, null);}
	public void Data_rename(Xoa_page page, int trg_ns, byte[] trg_ttl)	{
		if (wiki.Domain_tid() != Xow_wiki_domain_.Tid_home) {
			wiki.App().Gui_wtr().Warn_many("", "", "Only pages in the home wiki can be renamed");
			return;
		}
		Data_update_under(page, null, trg_ttl);
	}
	private void Data_update_under(Xoa_page page, byte[] text, byte[] new_ttl) {
		Xoa_ttl ttl = page.Ttl();
		Xow_ns ns = ttl.Ns(); byte[] ttl_bry = ttl.Page_db();
		Xodb_page db_page = Xodb_page.tmp_();
		if (!load_mgr.Load_by_ttl(db_page, ns, ttl_bry)) throw Err_mgr._.fmt_(GRP_KEY, "title_missing", "update requested but title does not exist: ~{0}", String_.new_utf8_(ttl_bry));
		byte[] old_ttl = ttl_bry;
		if (new_ttl != null) {
			ttl_bry = new_ttl;
			db_page.Ttl_wo_ns_(new_ttl);
		}
		// update page
		Xob_xdat_file page_rdr = new Xob_xdat_file(); Xob_xdat_itm page_itm = new Xob_xdat_itm();
		load_mgr.Load_page(tmp_page, db_page.Db_file_idx(), db_page.Db_row_idx(), ns, true, page_rdr, page_itm);
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_b512();
		if (text == null) text = tmp_page.Text(); 
		int text_len = text.length;
		DateAdp modified_on = tmp_page.Modified_on();
		if (update_modified_on_enabled) {
			modified_on = DateAdp_.Now();
			page.Revision_data().Modified_on_(modified_on);
		}
		Xodb_page_.Txt_page_save(tmp_bfr, db_page.Id(), modified_on, ttl_bry, text, true);
		page_rdr.Update(tmp_bfr, page_itm, tmp_bfr.XtoAryAndClear());
		Io_url page_rdr_url = fsys_mgr.Url_ns_fil(Xow_dir_info_.Tid_page, ttl.Ns().Id(), db_page.Db_file_idx());
		this.Data_save(Xow_dir_info_.Tid_page, page_rdr, page_rdr_url, tmp_bfr);
		tmp_bfr.Mkr_rls();
		// update ttl
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(text, text_len);
		db_page.Text_len_(text_len);
		db_page.Type_redirect_(redirect_ttl != null);
		Bry_bfr tmp = wiki.Utl_bry_bfr_mkr().Get_b512();
		Xodb_page_.Txt_ttl_save(tmp, db_page);
		byte[] ttl_row_bry = tmp.XtoAryAndClear();
		tmp.Mkr_rls();
		Xowd_hive_mgr ttl_hive = new Xowd_hive_mgr(wiki, Xow_dir_info_.Tid_ttl);
		ttl_hive.Update(ns, old_ttl, new_ttl, ttl_row_bry, Xodb_page_.Txt_ttl_pos, Byte_ascii.Pipe, true, true);
	}
	private void Data_save(byte dir_tid, Xob_xdat_file xdat_file, Io_url url, Bry_bfr tmp_bfr) {
		xdat_file.Save(url);
	}
	private Xodb_page tmp_page = new Xodb_page(); 
	public static final int File_idx_unknown = -1;
	private static final String GRP_KEY = "xowa.wiki.db.save";
}
