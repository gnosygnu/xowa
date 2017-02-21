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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.utils.*;
import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.tdbs.hives.*; import gplx.xowa.wikis.tdbs.xdats.*;
public class Xodb_save_mgr_txt implements Xodb_save_mgr {
	public Xodb_save_mgr_txt(Xowe_wiki wiki, Xodb_load_mgr_txt load_mgr) {
		this.wiki = wiki;
		this.load_mgr = load_mgr;
		this.fsys_mgr = wiki.Tdb_fsys_mgr();
		this.redirect_mgr = wiki.Redirect_mgr();
	}	private Xowe_wiki wiki; private Xotdb_fsys_mgr fsys_mgr; private Xodb_load_mgr_txt load_mgr; private Xop_redirect_mgr redirect_mgr;
	public boolean Create_enabled() {return create_enabled;} public void Create_enabled_(boolean v) {create_enabled = v;} private boolean create_enabled;
	public boolean Update_modified_on_enabled() {return update_modified_on_enabled;} public void Update_modified_on_enabled_(boolean v) {update_modified_on_enabled = v;} private boolean update_modified_on_enabled;
	public int Page_id_next() {return page_id_next;} public void Page_id_next_(int v) {page_id_next = v;} private int page_id_next = 0;
	public void Clear() {page_id_next = 0;}	// TEST: needed for ctg_test		
	public int Data_create(Xoa_ttl ttl, byte[] text) {
		Xow_ns ns_itm = ttl.Ns(); byte[] ttl_bry = ttl.Page_db();
		Xowd_page_itm db_page = Xowd_page_itm.new_tmp();
		boolean found = load_mgr.Load_by_ttl(db_page, ns_itm, ttl_bry);
		if (found) throw Err_.new_wo_type("create requested but title already exists", "ttl", String_.new_u8(ttl_bry));
		int text_len = text.length;
		Bry_bfr tmp = wiki.Utl__bfr_mkr().Get_m001();
		int page_id = page_id_next++;
		int fil_idx = 0;
		int ns_id = ttl.Ns().Id();
		Xotdb_page_itm_.Txt_page_save(tmp, page_id, Datetime_now.Get(), ttl_bry, text, true);
		Io_url page_rdr_url = fsys_mgr.Url_ns_fil(Xotdb_dir_info_.Tid_page, ns_id, fil_idx);
		byte[] page_rdr_bry = Io_mgr.Instance.LoadFilBry(page_rdr_url);
		Xob_xdat_file page_rdr = new Xob_xdat_file();
		if (Bry_.Len_gt_0(page_rdr_bry)) page_rdr.Parse(page_rdr_bry, page_rdr_bry.length, page_rdr_url);
		int row_idx = page_rdr.Count();
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		page_rdr.Insert(tmp_bfr, tmp.To_bry_and_clear());
		this.Data_save(Xotdb_dir_info_.Tid_page, page_rdr, page_rdr_url, tmp_bfr);
		tmp_bfr.Mkr_rls();
		
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(text, text_len);
		db_page.Init(page_id, ttl.Page_db(), redirect_ttl != null, text_len, fil_idx, row_idx);
		Xotdb_page_itm_.Txt_ttl_save(tmp, db_page);
		byte[] ttl_row_bry = tmp.To_bry_and_rls();
		Xowd_hive_mgr ttl_hive = new Xowd_hive_mgr(wiki, Xotdb_dir_info_.Tid_ttl);
		ttl_hive.Create(ttl.Ns(), ttl.Page_db(), ttl_row_bry, Bry_comparer_fld_last.Instance);
		wiki.Db_mgr().Load_mgr().Clear();	// NOTE: need to clear cached regy_ary in load_mgr
		return page_id;
	}
	public void Data_update(Xoae_page page, byte[] text)		{Data_update_under(page, text, null);}
	public void Data_rename(Xoae_page page, int trg_ns, byte[] trg_ttl)	{
		if (wiki.Domain_tid() != Xow_domain_tid_.Tid__home) {
			wiki.Appe().Usr_dlg().Warn_many("", "", "Only pages in the home wiki can be renamed");
			return;
		}
		Data_update_under(page, null, trg_ttl);
	}
	private void Data_update_under(Xoae_page page, byte[] text, byte[] new_ttl) {
		Xoa_ttl ttl = page.Ttl();
		Xow_ns ns = ttl.Ns(); byte[] ttl_bry = ttl.Page_db();
		Xowd_page_itm db_page = Xowd_page_itm.new_tmp();
		if (!load_mgr.Load_by_ttl(db_page, ns, ttl_bry)) throw Err_.new_wo_type("update requested but title does not exist", "ttl", String_.new_u8(ttl_bry));
		byte[] old_ttl = ttl_bry;
		if (new_ttl != null) {
			ttl_bry = new_ttl;
			db_page.Ttl_page_db_(new_ttl);
		}
		// update page
		Xob_xdat_file page_rdr = new Xob_xdat_file(); Xob_xdat_itm page_itm = new Xob_xdat_itm();
		load_mgr.Load_page(tmp_page, db_page.Text_db_id(), db_page.Tdb_row_idx(), ns, true, page_rdr, page_itm);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		if (text == null) text = tmp_page.Text(); 
		int text_len = text.length;
		DateAdp modified_on = tmp_page.Modified_on();
		if (update_modified_on_enabled) {
			modified_on = Datetime_now.Get();
			page.Db().Page().Modified_on_(modified_on);
		}
		Xotdb_page_itm_.Txt_page_save(tmp_bfr, db_page.Id(), modified_on, ttl_bry, text, true);
		page_rdr.Update(tmp_bfr, page_itm, tmp_bfr.To_bry_and_clear());
		Io_url page_rdr_url = fsys_mgr.Url_ns_fil(Xotdb_dir_info_.Tid_page, ttl.Ns().Id(), db_page.Text_db_id());
		this.Data_save(Xotdb_dir_info_.Tid_page, page_rdr, page_rdr_url, tmp_bfr);
		tmp_bfr.Mkr_rls();
		// update ttl
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(text, text_len);
		db_page.Text_len_(text_len);
		db_page.Redirected_(redirect_ttl != null);
		Bry_bfr tmp = wiki.Utl__bfr_mkr().Get_b512();
		Xotdb_page_itm_.Txt_ttl_save(tmp, db_page);
		byte[] ttl_row_bry = tmp.To_bry_and_clear();
		tmp.Mkr_rls();
		Xowd_hive_mgr ttl_hive = new Xowd_hive_mgr(wiki, Xotdb_dir_info_.Tid_ttl);
		ttl_hive.Update(ns, old_ttl, new_ttl, ttl_row_bry, Xotdb_page_itm_.Txt_ttl_pos, Byte_ascii.Pipe, true, true);
	}
	private void Data_save(byte dir_tid, Xob_xdat_file xdat_file, Io_url url, Bry_bfr tmp_bfr) {
		xdat_file.Save(url);
	}
	private Xowd_page_itm tmp_page = new Xowd_page_itm(); 
	public static final int File_idx_unknown = -1;
}
class Bry_comparer_fld_last implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		byte[] lhs = (byte[])lhsObj, rhs = (byte[])rhsObj;
		int lhs_bgn = Bry_find_.Find_bwd(lhs, Byte_ascii.Pipe); if (lhs_bgn == Bry_find_.Not_found) lhs_bgn = -1;
		int rhs_bgn = Bry_find_.Find_bwd(rhs, Byte_ascii.Pipe); if (rhs_bgn == Bry_find_.Not_found) rhs_bgn = -1;
		return Bry_.Compare(lhs, lhs_bgn + 1, lhs.length, rhs, rhs_bgn + 1, rhs.length);
	}
	public static final    Bry_comparer_fld_last Instance = new Bry_comparer_fld_last(); 
}
