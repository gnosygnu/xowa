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
import gplx.threads.*; import gplx.ios.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.fsdb.data.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.gui.*;
import gplx.xowa.html.hdumps.core.*;
class Xof_redlink_wkr implements Gfo_thread_wkr {
	private Xog_js_wkr js_wkr; private int[] uids;
	public Xof_redlink_wkr(Xog_js_wkr js_wkr, int[] uids) {
		this.js_wkr = js_wkr; this.uids = uids;
	}
	public String Name() {return "xowa.redlinks";}
	public boolean Resume() {return true;}
	public void Exec() {
		int len = uids.length;
		for (int i = 0; i < len; ++i) {
			int uid = uids[i];
			js_wkr.Html_atr_set(Int_.Xto_str(uid), "", "");
		}
	}
}
public class Xof_file_wkr implements Gfo_thread_wkr {
	private final Xof_orig_mgr orig_mgr; private final Xof_bin_mgr bin_mgr; private final Fsm_mnt_mgr mnt_mgr; private final Xof_cache_mgr cache_mgr;
	private final Gfo_usr_dlg usr_dlg; private final Xow_repo_mgr repo_mgr; private final Xog_js_wkr js_wkr;
	private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_(); private final Xof_img_size img_size = new Xof_img_size(); 	
	private final Xoa_page hpg; private final ListAdp imgs; private final byte exec_tid;
	public Xof_file_wkr(Xof_orig_mgr orig_mgr, Xof_bin_mgr bin_mgr, Fsm_mnt_mgr mnt_mgr, Xof_cache_mgr cache_mgr, Xow_repo_mgr repo_mgr, Xog_js_wkr js_wkr, Xoa_page hpg, ListAdp imgs, byte exec_tid) {
		this.orig_mgr = orig_mgr; this.bin_mgr = bin_mgr; this.mnt_mgr = mnt_mgr; this.cache_mgr = cache_mgr;
		this.usr_dlg = Gfo_usr_dlg_.I; this.repo_mgr = repo_mgr; this.js_wkr = js_wkr;			
		this.hpg = hpg; this.imgs = imgs; this.exec_tid = exec_tid;
	}
	public String Name() {return "xowa.load_imgs";}
	public boolean Resume() {return true;}
	public void Exec() {
		int len = imgs.Count();
		for (int i = 0; i < len; ++i)
			Ctor_by_hdump(exec_tid, hpg, (Xohd_data_itm__base)imgs.FetchAt(i));
	}
	private void Ctor_by_hdump(byte exec_tid, Xoa_page hpg, Xohd_data_itm__base hdump) {
		Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
		fsdb.Ctor_by_lnki(hdump.Lnki_ttl(), hdump.Lnki_type(), hdump.Lnki_w(), hdump.Lnki_h(), Xof_patch_upright_tid_.Tid_all, hdump.Lnki_upright(), hdump.Lnki_time(), hdump.Lnki_page());
		fsdb.Lnki_ext_(Xof_ext_.new_by_id_(hdump.Lnki_ext()));
		fsdb.Html_uid_(hdump.Html_uid());
		fsdb.Orig_exists_n_();
		Xof_orig_itm orig = orig_mgr.Find_by_ttl_or_null(fsdb.Lnki_ttl()); if (orig == Xof_orig_itm.Null) return;
		Eval_orig(exec_tid, orig, fsdb, url_bldr, repo_mgr, img_size);
		Show_img(exec_tid, fsdb, usr_dlg, bin_mgr, mnt_mgr, cache_mgr, repo_mgr, js_wkr, img_size, url_bldr, hpg);
	}
	public static void Show_img(byte exec_tid, Xof_fsdb_itm fsdb, Gfo_usr_dlg usr_dlg, Xof_bin_mgr bin_mgr, Fsm_mnt_mgr mnt_mgr, Xof_cache_mgr cache_mgr, Xow_repo_mgr repo_mgr, Xog_js_wkr js_wkr, Xof_img_size img_size, Xof_url_bldr url_bldr, Xoa_page hpg) {
		try {
			if (fsdb.Orig_ext() < 0) {
				usr_dlg.Warn_many("", "", "file.missing.ext: file=~{0} width=~{1} page=~{2}", fsdb.Lnki_ttl(), fsdb.Lnki_w(), hpg.Ttl().Full_db());
				return;
			}
			Xof_repo_pair repo_pair = null;
			switch (fsdb.Orig_repo_id()) {
				case Xof_repo_itm.Repo_local:
				case Xof_repo_itm.Repo_remote:
					repo_pair = repo_mgr.Repos_get_by_id(fsdb.Orig_repo_id());
					break;
			}
			if (repo_pair == null) {
				usr_dlg.Warn_many("", "", "file.missing.repo: file=~{0} width=~{1} page=~{2}", fsdb.Lnki_ttl(), fsdb.Lnki_w(), hpg.Ttl().Full_db());
				return;
			}
			Xof_repo_itm repo = repo_pair.Trg();
			fsdb.Ctor_for_html(exec_tid, img_size, repo, url_bldr);
			if (fsdb.Lnki_ext().Is_not_viewable(exec_tid)) return;	// file not viewable; exit; EX: exec_tid = page and fsdb is audio
			if (!Io_mgr._.ExistsFil(fsdb.Html_view_url())) {
				if (bin_mgr.Find_to_url_as_bool(exec_tid, fsdb)) {
					if (fsdb.Fsdb_insert()) Save_bin(fsdb, mnt_mgr);
				}
				else {
					usr_dlg.Warn_many("", "", "file.missing.bin: file=~{0} width=~{1} page=~{2}", fsdb.Lnki_ttl(), fsdb.Lnki_w(), hpg.Ttl().Full_db());
					fsdb.File_exists_n_();
					// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, fsdb.Html_uid());	// TODO: update caption with "" if image is missing
					return;
				}
			}
			Js_img_mgr.Update_img(hpg, js_wkr, fsdb);
			cache_mgr.Reg_and_check_for_size_0(fsdb);
		} catch (Exception e) {
			usr_dlg.Warn_many("", "", "file.unknown: ~{0}", Err_.Message_gplx_brief(e));
		}
	}
	public static void Eval_orig(byte exec_tid, Xof_orig_itm orig, Xof_fsdb_itm fsdb, Xof_url_bldr url_bldr, Xow_repo_mgr repo_mgr, Xof_img_size img_size) {
		fsdb.Orig_exists_y_();
		byte repo_id = orig.Repo();
		Xof_repo_pair repo_pair = repo_mgr.Repos_get_by_id(repo_id);
		Xof_repo_itm repo_itm = repo_pair.Trg();
		fsdb.Ctor_by_orig(repo_id, repo_pair.Wiki_domain(), orig.Page(), orig.Ext(), orig.W(), orig.H(), orig.Redirect());
		fsdb.Ctor_for_html(exec_tid, img_size, repo_itm, url_bldr);
	}
	private static void Save_bin(Xof_fsdb_itm itm, Fsm_mnt_mgr mnt_mgr) {
		Io_url html_url = itm.Html_view_url();
		long rdr_len = Io_mgr._.QueryFil(html_url).Size();
		Io_stream_rdr rdr = gplx.ios.Io_stream_rdr_.file_(html_url);
		try {
			rdr.Open();
			Fsm_mnt_itm mnt_itm = mnt_mgr.Mnts__get_insert();
			Fsm_atr_fil atr_fil = mnt_itm.Atr_mgr().Db__core();
			Fsm_bin_fil bin_fil = mnt_itm.Bin_mgr().Dbs__get_nth();
			Xof_bin_updater bin_updater = new Xof_bin_updater();
			bin_updater.Save_bin(mnt_itm, atr_fil, bin_fil, itm, rdr, rdr_len);
		}
		catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to save file: ttl=~{0} url=~{1} err=~{2}", itm.Lnki_ttl(), html_url.Raw(), Err_.Message_gplx(e));
		}
		finally {rdr.Rls();}
	}
}
