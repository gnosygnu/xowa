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
package gplx.xowa2.gui; import gplx.*; import gplx.xowa2.*;
import gplx.threads.*;
import gplx.xowa.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.bins.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.caches.*; import gplx.xowa.files.gui.*;
import gplx.xowa.html.hdumps.core.*;
public class Xogv_img_wkr implements Gfo_thread_wkr {
	private final Xof_orig_mgr orig_mgr; private final Xof_bin_mgr bin_mgr; private final Xof_cache_mgr cache_mgr;
	private final Gfo_usr_dlg usr_dlg; private final Xow_repo_mgr repo_mgr; private final Xog_js_wkr js_wkr;
	private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_(); private final Xof_img_size img_size = new Xof_img_size(); 	
	private final Xoa_page hpg; private final ListAdp imgs; private final byte exec_tid;
	public Xogv_img_wkr(Xof_orig_mgr orig_mgr, Xof_bin_mgr bin_mgr, Xof_cache_mgr cache_mgr, Xow_repo_mgr repo_mgr, Xog_js_wkr js_wkr, Xoa_page hpg, ListAdp imgs, byte exec_tid) {
		this.usr_dlg = Gfo_usr_dlg_._;
		this.orig_mgr = orig_mgr; this.bin_mgr = bin_mgr; this.cache_mgr = cache_mgr; this.repo_mgr = repo_mgr; this.js_wkr = js_wkr;			
		this.hpg = hpg; this.imgs = imgs; this.exec_tid = exec_tid;
	}
	public String Name() {return "xowa.load_imgs.wkr";}
	public boolean Resume() {return true;}
	public void Exec() {
		Show_imgs();
	}
	public void Show_imgs() {
		int len = imgs.Count();
		for (int i = 0; i < len; ++i)
			Show_img(exec_tid, hpg, (Xohd_data_itm__base)imgs.FetchAt(i));
	}
	private void Show_img(byte exec_tid, Xoa_page hpg, Xohd_data_itm__base hdump) {
		Xof_fsdb_itm fsdb = new Xof_fsdb_itm();
		fsdb.Ctor_by_lnki(hdump.Lnki_ttl(), Xof_ext_.new_by_id_(hdump.Lnki_ext()), Xof_xfer_itm_.Md5_(hdump.Lnki_ttl()), hdump.Lnki_type(), hdump.Lnki_w(), hdump.Lnki_h(), Xof_patch_upright_tid_.Tid_all, hdump.Lnki_upright(), hdump.Lnki_time(), hdump.Lnki_page());
		fsdb.Html_uid_(hdump.Html_uid());
		if (!Ctor_by_orig(exec_tid, fsdb)) return;
		if (fsdb.Lnki_ext().Is_not_viewable(exec_tid)) return;	// file not viewable; exit; EX: exec_tid = page and fsdb is audio
		if (!Io_mgr._.ExistsFil(fsdb.Html_view_url())) {
			if (bin_mgr.Find_to_url_as_bool(exec_tid, fsdb)) {
				// if (fsdb.Insert()) Save_itm(fsdb);
			}
			else {
				usr_dlg.Warn_many("", "", "file not found: page=~{0} file=~{1} width=~{2}", hpg.Url().Xto_full_str_safe(), String_.new_utf8_(fsdb.Lnki_ttl()), fsdb.Lnki_w());
				fsdb.Rslt_bin_(Xof_bin_wkr_.Tid_not_found);
				// gplx.xowa.files.gui.Js_img_mgr.Update_img_missing(usr_dlg, fsdb.Html_uid());	// TODO: update caption with "" if image is missing
				return;
			}
		}
		Js_img_mgr.Update_img(hpg, js_wkr, fsdb);
		cache_mgr.Reg_and_check_for_size_0(fsdb);
	}
	private boolean Ctor_by_orig(byte exec_tid, Xof_fsdb_itm fsdb) {
		fsdb.Orig_status_(Xof_orig_wkr_.Status_missing_orig);
		Xof_orig_itm orig = orig_mgr.Find_by_ttl_or_null(fsdb.Lnki_ttl()); if (orig == Xof_orig_itm.Null) return false;
		// if (orig.Insert())	// set by wmf
		//	orig_mgr.Insert(repo_tid, fsdb.Orig_ttl(), fsdb.Lnki_ext().Id(), fsdb.Orig_w(), fsdb.Orig_h(), fsdb.Orig_redirect(), Xof_orig_wkr_.Status_found);
		fsdb.Orig_status_(Xof_orig_wkr_.Status_found);
		byte repo_id = orig.Repo();
		Xof_repo_pair repo_pair = repo_mgr.Repos_get_by_id(repo_id);
		fsdb.Orig_repo_id_(repo_id);
		fsdb.Orig_repo_name_(repo_pair.Wiki_domain());
		fsdb.Orig_size_(orig.W(), orig.H());
		fsdb.Lnki_ext_(Xof_ext_.new_by_id_(orig.Ext()));	// overwrite ext with whatever's in file_orig; needed for ogg -> oga / ogv
		if (Bry_.Len_gt_0(orig.Redirect()))					// redirect exists; EX: A.png redirected to B.png
			fsdb.Ctor_by_orig_redirect(orig.Redirect());	// update fsdb with atrs of B.png
		fsdb.Html_size_calc(img_size, exec_tid);
		fsdb.Html_view_url_(url_bldr.To_url(repo_pair, fsdb, Bool_.N));
		fsdb.Html_orig_url_(url_bldr.To_url(repo_pair, fsdb, Bool_.Y));
		return true;
	}
}
