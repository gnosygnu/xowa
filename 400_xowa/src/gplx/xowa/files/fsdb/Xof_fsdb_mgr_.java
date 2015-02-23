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
import gplx.fsdb.*; import gplx.fsdb.data.*; import gplx.xowa.files.caches.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.gui.*; import gplx.xowa.wmfs.apis.*;
class Xof_fsdb_wkr {
	private final Gfo_usr_dlg usr_dlg; private final Xof_fsdb_mgr fsdb_mgr; private final Xof_cache_mgr cache_mgr; private final Xow_repo_mgr repo_mgr; private final Xof_url_bldr url_bldr;
	private final Xof_img_size img_size = new Xof_img_size(); private final ListAdp get_list = ListAdp_.new_();
	public Xof_fsdb_wkr(Xof_fsdb_mgr fsdb_mgr, Gfo_usr_dlg usr_dlg, Xof_cache_mgr cache_mgr, Xow_repo_mgr repo_mgr, Xof_url_bldr url_bldr) {
		this.fsdb_mgr = fsdb_mgr; this.usr_dlg = usr_dlg; this.cache_mgr = cache_mgr; this.repo_mgr = repo_mgr; this.url_bldr = url_bldr;
	}
	public void Fsdb_search(byte exec_tid, ListAdp itms, Xoae_page page) {
		synchronized (get_list) {
			get_list.Clear();
			int itms_len = itms.Count();
			for (int i = 0; i < itms_len; i++) {
				if (usr_dlg.Canceled()) return;
				Xof_fsdb_itm itm = (Xof_fsdb_itm)itms.FetchAt(i);
				switch (itm.Orig_status()) {
					case Xof_orig_wkr_.Status_found:			// item is on disk and in orig_regy; just update data, html_view and cache_mgr;
						Xof_repo_itm repo_itm = repo_mgr.Repos_get_by_wiki(itm.Orig_repo_name()).Trg();
						itm.Ctor_by_html(repo_itm, url_bldr, img_size, exec_tid);
						Js_img_mgr.Update_img(page, itm);		// NOTE: needed when opening 2+ tabs and missing image is on 2+ pages; 2nd page will have img as Xof_orig_wkr_.Status_found; DATE:2014-10-20
						cache_mgr.Reg_and_check_for_size_0(itm);
						break;
					case Xof_orig_wkr_.Status_missing_orig:	
					case Xof_orig_wkr_.Status_noop:				// NOTE: previous attempt was noop; only occurs if oga and exec_tid != viewer
					case Xof_orig_wkr_.Status_null:				get_list.Add(itm); break;	// item is either not on disk, or not in orig_regy; add to list and try to get from fsdb_mgr
					case Xof_orig_wkr_.Status_missing_qry:		
					case Xof_orig_wkr_.Status_missing_bin:		break;						// item is missing; just exit
					default:									throw Err_.unhandled(itm.Orig_status());
				}
			}
			int get_len = get_list.Count();
			for (int i = 0; i < get_len; i++) {
				if (usr_dlg.Canceled()) return;
				Xof_fsdb_itm itm = (Xof_fsdb_itm)get_list.FetchAt(i);
				try {
					Get_itm(exec_tid, itm, page);
				} catch (Exception e) {usr_dlg.Warn_many("", "", "file.search.error: page=~{0} img=~{1} err=~{2}", String_.new_utf8_(page.Ttl().Raw()), String_.new_utf8_(itm.Lnki_ttl()), Err_.Message_gplx_brief(e));}
			}
		}
	}
	private void Get_itm(byte exec_tid, Xof_fsdb_itm itm, Xoae_page page) {
		if (Ext_is_not_viewable_in_exec_tid(exec_tid, itm.Lnki_ext())) return;	// do not get if not needed; EX: exec_tid = page and itm is audio
		if (Find_by_itm(exec_tid, itm, fsdb_mgr.Orig_mgr(), img_size)) {		// itm exists in orig_regy
			Xof_repo_pair repo_pair = repo_mgr.Repos_get_by_wiki(itm.Orig_repo_name());
			if (repo_pair == null) {
				fsdb_mgr.Orig_mgr().Insert(Xof_repo_itm.Repo_unknown, itm.Orig_ttl(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), itm.Orig_redirect(), Xof_orig_wkr_.Status_missing_qry);
				return;
			}
			byte repo_tid = repo_pair.Repo_idx();								// NOTE: should be itm.Orig_repo, but throws null refs
			if (Ext_is_not_viewable_in_exec_tid(exec_tid, itm.Lnki_ext())) {	// check viewable again b/c orig_mgr may have changed ext; EX: ogg -> oga
				itm.Rslt_bin_(Xof_bin_wkr_.Tid_noop);
				fsdb_mgr.Orig_mgr().Insert(repo_tid, itm.Orig_ttl(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), itm.Orig_redirect(), Xof_orig_wkr_.Status_noop);
				return;
			}
			if (fsdb_mgr.Bin_mgr().Find_to_url_as_bool(exec_tid, itm)) {
				fsdb_mgr.Orig_mgr().Insert(repo_tid, itm.Orig_ttl(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), itm.Orig_redirect(), Xof_orig_wkr_.Status_found);
				// TODO: this "breaks" tests b/c mock bin_wkr is fsdb; 
				if (itm.Rslt_bin() != Xof_bin_wkr_.Tid_fsdb_xowa)	// if bin is from fsdb, don't save it; occurs when page has new file listed twice; 1st file inserts into fsdb; 2nd file should find in fsdb and not save again
					Save_itm(itm);
				Js_img_mgr.Update_img(page, itm);
			}
			else {
				usr_dlg.Warn_many("", "", "file not found: page=~{0} file=~{1} width=~{2}", page.Url().Xto_full_str_safe(), String_.new_utf8_(itm.Lnki_ttl()), itm.Lnki_w());
				itm.Rslt_bin_(Xof_bin_wkr_.Tid_not_found);
				fsdb_mgr.Orig_mgr().Insert(repo_tid, itm.Orig_ttl(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), itm.Orig_redirect(), Xof_orig_wkr_.Status_missing_bin);
				// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, itm.Html_uid());	// TODO: update caption with "" if image is missing
			}
		}
		else {
			fsdb_mgr.Orig_mgr().Insert(Xof_repo_itm.Repo_unknown, itm.Orig_ttl(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), itm.Orig_redirect(), Xof_orig_wkr_.Status_missing_qry);
				// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, itm.Html_uid());	// TODO: update caption with "" if image is missing
		}
	}
	private void Save_itm(Xof_fsdb_itm itm) {
		Io_url html_url = itm.Html_view_url();
		long bin_len = Io_mgr._.QueryFil(html_url).Size();
		gplx.ios.Io_stream_rdr bin_rdr = gplx.ios.Io_stream_rdr_.file_(html_url);
		try {
			bin_rdr.Open();
			if (itm.Lnki_ext().Id_is_thumbable_img()) {
				if (itm.File_is_orig()) {
					Fsd_img_itm img_itm = new Fsd_img_itm();
					fsdb_mgr.Mnt_mgr().Img_insert(img_itm, itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Html_w(), itm.Html_h(), Fsd_thm_tbl.Modified_null, Fsd_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
				else {
					Fsd_thm_itm thm_itm = Fsd_thm_itm.new_();
					fsdb_mgr.Mnt_mgr().Thm_insert(thm_itm, itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Html_w(), itm.Html_h(), itm.Lnki_time(), itm.Lnki_page(), Fsd_thm_tbl.Modified_null, Fsd_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
			}
			else {
				if (itm.Lnki_ext().Id_is_video() && !itm.File_is_orig()) {	// insert as thumbnail
					Fsd_thm_itm thm_itm = Fsd_thm_itm.new_();
					fsdb_mgr.Mnt_mgr().Thm_insert(thm_itm, itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), itm.Html_w(), itm.Html_h(), itm.Lnki_time(), itm.Lnki_page(), Fsd_thm_tbl.Modified_null, Fsd_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
				else {
					Fsd_fil_itm fil_itm = new Fsd_fil_itm();
					fsdb_mgr.Mnt_mgr().Fil_insert(fil_itm, itm.Orig_repo_name(), itm.Lnki_ttl(), itm.Lnki_ext().Id(), Fsd_thm_tbl.Modified_null, Fsd_thm_tbl.Hash_null, bin_len, bin_rdr);
				}
			}
			if (!Env_.Mode_testing())
				cache_mgr.Reg(itm, bin_len);
		}
		catch (Exception e) {
			usr_dlg.Warn_many("", "", "failed to save file: ttl=~{0} url=~{1} err=~{2}", String_.new_utf8_(itm.Lnki_ttl()), html_url.Raw(), Err_.Message_gplx(e));
		}
		finally {bin_rdr.Rls();}
	}
	private static boolean Ext_is_not_viewable_in_exec_tid(byte exec_tid, Xof_ext ext) {
		return	exec_tid != Xof_exec_tid.Tid_viewer_app		// only apply logic if !Tid_viewer_app; note that if Tid_viewer_app, then user clicked on file, so return true;
				&&	(	ext.Id_is_audio()					// NOTE: was audio_strict, but v2 always redefines .ogg as .ogv; DATE:2014-02-02
					||	ext.Id() == Xof_ext_.Id_unknown		// ignore unknown exts, else will download needlessly when viewing page; EX: .wav before .wav was registered; PAGE:pl.s:Śpiąca_królewna_(Oppman); DATE:2014-08-17
					);
	}
	private static boolean Find_by_itm(byte get_exec_tid, Xof_fsdb_itm itm, Xof_orig_mgr orig_mgr, Xof_img_size img_size) {
		byte[] itm_ttl = itm.Lnki_ttl();
		Xof_orig_itm orig = orig_mgr.Find_by_ttl_or_null(itm_ttl);
		if (orig == Xof_orig_itm.Null) {		// not found in any wkr
			itm.Orig_status_(Xof_orig_wkr_.Status_missing_orig);
			return false;						// exit now
		}
		itm.Orig_status_(Xof_orig_wkr_.Status_found);
		byte[] orig_redirect = orig.Redirect();
		if (Bry_.Len_gt_0(orig_redirect))		// redirect found
			itm.Ctor_by_orig_redirect(orig_redirect);
		itm.Orig_size_(orig.W(), orig.H());
		itm.Html_size_calc(img_size, get_exec_tid);	// orig found; recalc size
		return true;
	}
}
