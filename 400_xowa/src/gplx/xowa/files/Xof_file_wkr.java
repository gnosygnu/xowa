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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.threads.*; import gplx.core.ios.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.fsdb.data.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.gui.*;
import gplx.xowa.htmls.core.makes.imgs.*;
public class Xof_file_wkr implements Gfo_thread_wkr {
	private final Xof_orig_mgr orig_mgr; private final Xof_bin_mgr bin_mgr; private final Fsm_mnt_mgr mnt_mgr; private final Xou_cache_mgr cache_mgr;
	private final Gfo_usr_dlg usr_dlg; private final Xow_repo_mgr repo_mgr; private final Xog_js_wkr js_wkr;
	private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2(); private final Xof_img_size img_size = new Xof_img_size(); 	
	private final Xoa_page hpg; private final List_adp imgs;
	public Xof_file_wkr(Xof_orig_mgr orig_mgr, Xof_bin_mgr bin_mgr, Fsm_mnt_mgr mnt_mgr, Xou_cache_mgr cache_mgr, Xow_repo_mgr repo_mgr, Xog_js_wkr js_wkr, Xoa_page hpg, List_adp imgs) {
		this.orig_mgr = orig_mgr; this.bin_mgr = bin_mgr; this.mnt_mgr = mnt_mgr; this.cache_mgr = cache_mgr;
		this.usr_dlg = Gfo_usr_dlg_.Instance; this.repo_mgr = repo_mgr; this.js_wkr = js_wkr;			
		this.hpg = hpg; this.imgs = imgs;
	}
	public String Name() {return "xowa.load_imgs";}
	public boolean Resume() {return true;}
	public void Exec() {
		int len = imgs.Count();
		for (int i = 0; i < len; ++i)
			Exec_by_fsdb((Xof_fsdb_itm)imgs.Get_at(i));
		Xoa_app_.Usr_dlg().Prog_none("", "", "");
	}
	public void Exec_old() {
		int len = imgs.Count();
		for (int i = 0; i < len; ++i)
			Ctor_by_hdump(hpg, (Xohd_img_itm__base)imgs.Get_at(i));
		Xoa_app_.Usr_dlg().Prog_none("", "", "");
	}
	private void Ctor_by_hdump(Xoa_page hpg, Xohd_img_itm__base hdump) {
		Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
		fsdb.Init_at_lnki(Xof_exec_tid.Tid_wiki_page, hpg.Wiki().Domain_itm().Abrv_xo(), hdump.Lnki_ttl(), hdump.Lnki_type(), hdump.Lnki_upright(), hdump.Lnki_w(), hdump.Lnki_h(), hdump.Lnki_time(), hdump.Lnki_page(), Xof_patch_upright_tid_.Tid_all);
		fsdb.Init_at_hdoc(hdump.Html_uid(), hdump.Html_elem_tid());
		Exec_by_fsdb(fsdb);
	}
	private void Exec_by_fsdb(Xof_fsdb_itm fsdb) {
		try {
			if (fsdb.File_exists_in_cache()) return;
			fsdb.Orig_exists_n_();
			Xof_orig_itm orig = orig_mgr.Find_by_ttl_or_null(fsdb.Lnki_ttl()); if (orig == Xof_orig_itm.Null) return;
			Eval_orig(orig, fsdb, url_bldr, repo_mgr, img_size);
			Show_img(fsdb, usr_dlg, bin_mgr, mnt_mgr, cache_mgr, repo_mgr, js_wkr, img_size, url_bldr, hpg);
		} catch (Exception e) {
			usr_dlg.Warn_many("", "", "file.unknown: err=~{0}", Err_.Message_gplx_full(e));
		}
	}
	public static boolean Show_img(Xof_fsdb_itm fsdb, Gfo_usr_dlg usr_dlg, Xof_bin_mgr bin_mgr, Fsm_mnt_mgr mnt_mgr, Xou_cache_mgr cache_mgr, Xow_repo_mgr repo_mgr, Xog_js_wkr js_wkr, Xof_img_size img_size, Xof_url_bldr url_bldr, Xoa_page page) {
		try {
			usr_dlg.Log_many("", "", "file.get: file=~{0} width=~{1} page=~{2}", fsdb.Orig_ttl(), fsdb.Lnki_w(), page.Ttl().Full_db());
			if (fsdb.Orig_ext() == null) {
				usr_dlg.Warn_many("", "", "file.missing.ext: file=~{0} width=~{1} page=~{2}", fsdb.Orig_ttl(), fsdb.Lnki_w(), page.Ttl().Full_db());
				return false;
			}
			Xof_repo_itm repo = repo_mgr.Get_trg_by_id_or_null(fsdb.Orig_repo_id(), fsdb.Orig_ttl(), page.Url_bry_safe());
			if (repo == null) return false;
			fsdb.Init_at_html(fsdb.Lnki_exec_tid(), img_size, repo, url_bldr);
			if (fsdb.Orig_ext().Is_not_viewable(fsdb.Lnki_exec_tid())) return false;	// file not viewable; exit; EX: exec_tid = page and fsdb is audio
			IoItmFil file = Io_mgr.Instance.QueryFil(fsdb.Html_view_url());
			if (!file.Exists()) {
				if (bin_mgr.Find_to_url_as_bool(fsdb.Lnki_exec_tid(), fsdb)) {
					if (fsdb.Fsdb_insert()) Save_bin(fsdb, mnt_mgr, fsdb.Html_view_url());
				}
				else {
					boolean pass = false;
					if (fsdb.Lnki_exec_tid() == Xof_exec_tid.Tid_wiki_file) {
						pass = Show_img_near(fsdb, bin_mgr, repo_mgr, page, img_size, url_bldr, js_wkr);
					}
					if (!pass) {
						usr_dlg.Warn_many("", "", "file.missing.bin: file=~{0} width=~{1} page=~{2}", fsdb.Orig_ttl(), fsdb.Lnki_w(), page.Ttl().Full_db());
						fsdb.File_exists_n_();
						// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, fsdb.Html_uid());	// TODO: update caption with "" if image is missing
						return false;
					}
				}
			}
			else {
				fsdb.File_exists_y_();
				fsdb.File_size_(file.Size());
			}
			Js_img_mgr.Update_img(page, js_wkr, fsdb);
			cache_mgr.Update(fsdb);
			return true;
		} catch (Exception e) {
			usr_dlg.Warn_many("", "", "file.unknown: err=~{0}", Err_.Message_gplx_full(e));
			return false;
		}
	}
	private static boolean Show_img_near(Xof_fsdb_itm fsdb, Xof_bin_mgr bin_mgr, Xow_repo_mgr repo_mgr, Xoa_page page, Xof_img_size img_size, Xof_url_bldr url_bldr, Xog_js_wkr js_wkr) {
		Xof_bin_wkr__fsdb_sql fsdb_sql_wkr = (Xof_bin_wkr__fsdb_sql)bin_mgr.Wkrs__get_or_null(Xof_bin_wkr_.Key_fsdb_wiki);
		if (fsdb_sql_wkr != null) {
			Io_stream_rdr file_rdr = fsdb_sql_wkr.Get_to_fsys_near(fsdb, fsdb.Orig_repo_name(), fsdb.Orig_ttl(), fsdb.Orig_ext(), fsdb.Lnki_time(), fsdb.Lnki_page());
			try {
				if (file_rdr != Io_stream_rdr_.Noop) {
					Xof_repo_itm repo = repo_mgr.Get_trg_by_id_or_null(fsdb.Orig_repo_id(), fsdb.Lnki_ttl(), page.Url_bry_safe());
					Io_url file_url = url_bldr.Init_for_trg_file(Xof_repo_itm_.Mode_by_bool(!fsdb.File_is_orig()), repo, fsdb.Orig_ttl(), fsdb.Orig_ttl_md5(), fsdb.Orig_ext(), fsdb.File_w(), fsdb.Lnki_time(), fsdb.Lnki_page()).Xto_url();
					Io_stream_wtr_.Save_rdr(file_url, file_rdr, Io_download_fmt.Null);
					fsdb.File_size_(file_rdr.Len());			// must update file size for cache
					fsdb.Init_at_lnki_by_near(fsdb.File_w());	// change lnki to be file_w,-1
					fsdb.Init_at_html(fsdb.Lnki_exec_tid(), img_size, repo, url_bldr);
					return true;
				}
			} finally {
				file_rdr.Rls();
			}
		}
		return false;
	}
	public static void Eval_orig(Xof_orig_itm orig, Xof_fsdb_itm fsdb, Xof_url_bldr url_bldr, Xow_repo_mgr repo_mgr, Xof_img_size img_size) {
		fsdb.Orig_exists_y_();
		byte repo_id = orig.Repo();
		Xof_repo_itm repo_itm = repo_mgr.Get_trg_by_id_or_null(repo_id, fsdb.Lnki_ttl(), Bry_.Empty);
		if (repo_itm == null) return;
		fsdb.Init_at_orig(repo_id, repo_itm.Wiki_domain(), orig.Ttl(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
		fsdb.Init_at_html(fsdb.Lnki_exec_tid(), img_size, repo_itm, url_bldr);
	}
	public static void Save_bin(Xof_fsdb_itm itm, Fsm_mnt_mgr mnt_mgr, Io_url html_url) {
		long rdr_len = Io_mgr.Instance.QueryFil(html_url).Size();
		Io_stream_rdr rdr = gplx.core.ios.Io_stream_rdr_.file_(html_url);
		try {
			rdr.Open();
			Fsm_mnt_itm mnt_itm = mnt_mgr.Mnts__get_insert();
			Fsm_atr_fil atr_fil = mnt_itm.Atr_mgr().Db__core();
			Fsm_bin_fil bin_fil = mnt_itm.Bin_mgr().Dbs__get_nth();
			Xof_bin_updater bin_updater = new Xof_bin_updater();
			bin_updater.Save_bin(mnt_itm, atr_fil, bin_fil, itm, rdr, rdr_len);
		}
		catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to save file: ttl=~{0} url=~{1} err=~{2}", itm.Orig_ttl(), html_url.Raw(), Err_.Message_gplx_full(e));
		}
		finally {rdr.Rls();}
	}
}
