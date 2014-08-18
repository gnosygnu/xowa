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
package gplx.xowa.files.fsdb; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.fsdb.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.qrys.*; import gplx.xowa.files.wiki_orig.*;
import gplx.xowa.files.fsdb.caches.*;
import gplx.xowa.files.gui.*;
public class Xof_fsdb_mgr_ {
	public void Fsdb_search(Xof_fsdb_mgr fsdb_mgr, Io_url file_dir, Xoa_page page, byte exec_tid, ListAdp itms, Xow_repo_mgr repo_mgr, Xof_url_bldr url_bldr) {
		ListAdp fsdb_list = ListAdp_.new_();
		int itms_len = itms.Count();
		Gfo_usr_dlg usr_dlg = fsdb_mgr.Wiki().App().Usr_dlg();
		for (int i = 0; i < itms_len; i++) {
			if (usr_dlg.Canceled()) return;
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.FetchAt(i);
			Itm_process(fsdb_mgr, file_dir, usr_dlg, itm, fsdb_list, repo_mgr, url_bldr, exec_tid);
		}
		itms_len = fsdb_list.Count(); if (itms_len == 0) return;	// all items found; return;
		Reg_search(fsdb_mgr, file_dir, usr_dlg, page, exec_tid, fsdb_list, itms_len, repo_mgr);
	}
	private void Reg_search(Xof_fsdb_mgr fsdb_mgr, Io_url file_dir, Gfo_usr_dlg usr_dlg, Xoa_page page, byte exec_tid, ListAdp itms_list, int itms_len, Xow_repo_mgr repo_mgr) {
		for (int i = 0; i < itms_len; i++) {
			if (usr_dlg.Canceled()) return;
			Xof_fsdb_itm itm = (Xof_fsdb_itm)itms_list.FetchAt(i);
			try {
				Reg_search_itm(fsdb_mgr, file_dir, usr_dlg, page, exec_tid, itms_list, itms_len, repo_mgr, itm );
			} catch (Exception e) {usr_dlg.Warn_many("", "", "file.search.error: page=~{0} img=~{1} err=~{2}", String_.new_utf8_(fsdb_mgr.Wiki().Ctx().Cur_page().Ttl().Raw()), String_.new_utf8_(itm.Lnki_ttl()), Err_.Message_gplx_brief(e));}
		}
	}
	private void Reg_search_itm(Xof_fsdb_mgr fsdb_mgr, Io_url file_dir, Gfo_usr_dlg usr_dlg, Xoa_page page, byte exec_tid, ListAdp itms_list, int itms_len, Xow_repo_mgr repo_mgr, Xof_fsdb_itm itm) {
		switch (itm.Rslt_reg()) {
			case Xof_wiki_orig_wkr_.Tid_missing_qry:
			case Xof_wiki_orig_wkr_.Tid_missing_bin:	return;	// already missing; do not try to find again
		}
		if (Is_not_viewable(exec_tid, itm.Lnki_ext())) {	// NOTE: was audio_strict, but v2 always redefines .ogg as .ogv; DATE:2014-02-02
			itm.Rslt_qry_(Xof_qry_wkr_.Tid_noop);
			return;
		}
		if (fsdb_mgr.Qry_mgr().Find(exec_tid, itm)) {
			Xof_repo_pair repo_pair = repo_mgr.Repos_get_by_wiki(itm.Orig_wiki());
			if (repo_pair == null) {
				fsdb_mgr.Reg_insert(itm, Xof_repo_itm.Repo_unknown, Xof_wiki_orig_wkr_.Tid_missing_qry);
				return;
			}
			byte orig_wiki = repo_pair.Repo_id();	// NOTE: should be itm.Orig_repo, but throws null refs
			if (Is_not_viewable(exec_tid, itm.Lnki_ext())) {
				itm.Rslt_qry_(Xof_qry_wkr_.Tid_mock);
				itm.Rslt_bin_(Xof_bin_wkr_.Tid_noop);
				fsdb_mgr.Reg_insert(itm, orig_wiki, Xof_wiki_orig_wkr_.Tid_noop);
				return;
			}
			if (fsdb_mgr.Bin_mgr().Find_to_url_as_bool(ListAdp_.Null, exec_tid, itm)) {
				fsdb_mgr.Reg_insert(itm, orig_wiki, Xof_wiki_orig_wkr_.Tid_found_orig);
				// TODO: this "breaks" tests b/c mock bin_wkr is fsdb; 
				if (itm.Rslt_bin() != Xof_bin_wkr_.Tid_fsdb_wiki)	// if bin is from fsdb, don't save it; occurs when page has new file listed twice; 1st file inserts into fsdb; 2nd file should find in fsdb and not save again
					Fsdb_save(fsdb_mgr, itm);
				Js_img_mgr.Update_img(page, itm);
			}
			else {
				usr_dlg.Warn_many("", "", "file not found: page=~{0} file=~{1} width=~{2}", page.Url().Xto_full_str_safe(), String_.new_utf8_(itm.Lnki_ttl()), itm.Lnki_w());
				itm.Rslt_bin_(Xof_bin_wkr_.Tid_not_found);
				fsdb_mgr.Reg_insert(itm, orig_wiki, Xof_wiki_orig_wkr_.Tid_missing_bin);
				// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, itm.Html_uid());	// TODO: update caption with "" if image is missing
			}
		}
		else {
			fsdb_mgr.Reg_insert(itm, Xof_repo_itm.Repo_unknown, Xof_wiki_orig_wkr_.Tid_missing_qry);
				// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, itm.Html_uid());	// TODO: update caption with "" if image is missing
		}
	}
	private static boolean Is_not_viewable(byte exec_tid, Xof_ext ext) {
		return	exec_tid != Xof_exec_tid.Tid_viewer_app		// only apply logic if !Tid_viewer_app; note that if Tid_viewer_app, then user clicked on file, so return true;
				&&	(	ext.Id_is_audio()					// NOTE: was audio_strict, but v2 always redefines .ogg as .ogv; DATE:2014-02-02
					||	ext.Id() == Xof_ext_.Id_unknown		// ignore unknown exts, else will download needlessly when viewing page; EX: .wav before .wav was registered; PAGE:pl.s:Śpiąca_królewna_(Oppman); DATE:2014-08-17
					);
	}
	private Xof_img_size img_size = new Xof_img_size();
	private void Itm_process(Xof_fsdb_mgr fsdb_mgr, Io_url file_dir, Gfo_usr_dlg usr_dlg, Xof_fsdb_itm itm, ListAdp fsdb_list, Xow_repo_mgr repo_mgr, Xof_url_bldr url_bldr, byte exec_tid) {
		switch (itm.Rslt_reg()) {
			case Xof_wiki_orig_wkr_.Tid_found_orig:
				itm.Html__init(repo_mgr, url_bldr, img_size, exec_tid);
				//	Js_img_mgr.Update_img(usr_dlg, itm);		// DELETE: DATE:2014-02-01
				if (!Env_.Mode_testing()) {
					Cache_fil_itm cache_fil_itm = fsdb_mgr.Cache_mgr().Reg(fsdb_mgr.Wiki(), itm, 0);
					if (cache_fil_itm.Fil_size() == 0) {
						long fil_size = Io_mgr._.QueryFil(itm.Html_url()).Size();
						cache_fil_itm.Fil_size_(fil_size);
					}
				}
				break;
			case Xof_wiki_orig_wkr_.Tid_missing_qry:
			case Xof_wiki_orig_wkr_.Tid_missing_bin:		break;
			case Xof_wiki_orig_wkr_.Tid_missing_reg:
			case Xof_wiki_orig_wkr_.Tid_noop:			// previous attempt was noop; only occurs if oga and exec_tid != viewer
			case Xof_wiki_orig_wkr_.Tid_null:			fsdb_list.Add(itm); break;
			default:									throw Err_.unhandled(itm.Rslt_reg());
		}
	}
	private void Fsdb_save(Xof_fsdb_mgr fsdb_mgr, Xof_fsdb_itm itm) {
		Io_url html_url = itm.Html_url();
		long bin_len = Io_mgr._.QueryFil(html_url).Size();
		gplx.ios.Io_stream_rdr bin_rdr = gplx.ios.Io_stream_rdr_.file_(html_url);
		try {
			bin_rdr.Open();
			if (itm.Lnki_ext().Id_is_thumbable_img()) {
				if (itm.File_is_orig()) {
					Fsdb_xtn_img_itm img_itm = new Fsdb_xtn_img_itm();
					fsdb_mgr.Img_insert(img_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Html_w(), itm.Html_h(), Fsdb_xtn_thm_tbl.Modified_null, Fsdb_xtn_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
				else {
					Fsdb_xtn_thm_itm thm_itm = Fsdb_xtn_thm_itm.new_();
					fsdb_mgr.Thm_insert(thm_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Html_w(), itm.Html_h(), itm.Lnki_thumbtime(), itm.Lnki_page(), Fsdb_xtn_thm_tbl.Modified_null, Fsdb_xtn_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
			}
			else {
				if (itm.Lnki_ext().Id_is_video() && !itm.File_is_orig()) {	// insert as thumbnail
					Fsdb_xtn_thm_itm thm_itm = Fsdb_xtn_thm_itm.new_();
					fsdb_mgr.Thm_insert(thm_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Html_w(), itm.Html_h(), itm.Lnki_thumbtime(), itm.Lnki_page(), Fsdb_xtn_thm_tbl.Modified_null, Fsdb_xtn_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
				else {
					Fsdb_fil_itm fil_itm = new Fsdb_fil_itm();
					fsdb_mgr.Fil_insert(fil_itm, itm.Orig_wiki(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), Fsdb_xtn_thm_tbl.Modified_null, Fsdb_xtn_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
			}
			if (!Env_.Mode_testing())
				fsdb_mgr.Cache_mgr().Reg(fsdb_mgr.Wiki(), itm, bin_len);
		}
		catch (Exception e) {
			fsdb_mgr.Usr_dlg().Warn_many("", "", "failed to save file: ttl=~{0} url=~{1} err=~{2}", String_.new_utf8_(itm.Lnki_ttl()), html_url.Raw(), Err_.Message_gplx(e));
		}
		finally {bin_rdr.Rls();}
	}
	public static final Xof_fsdb_mgr_ _ = new Xof_fsdb_mgr_(); Xof_fsdb_mgr_() {}
}
