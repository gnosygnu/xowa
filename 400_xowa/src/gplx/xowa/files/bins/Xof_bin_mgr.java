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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.ios.*;
import gplx.fsdb.meta.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.cnvs.*; import gplx.xowa.files.caches.*;
import gplx.xowa.wmfs.*;
public class Xof_bin_mgr {		
	private final Fsm_mnt_mgr mnt_mgr;
	private final Gfo_usr_dlg usr_dlg; private final Xow_repo_mgr repo_mgr; private final Xof_cache_mgr cache_mgr; private final Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_();
	private Xof_bin_wkr[] wkrs = Xof_bin_wkr_.Ary_empty; private int wkrs_len;
	private final String_obj_ref resize_warning = String_obj_ref.null_(); private final Xof_img_size tmp_size = new Xof_img_size();
	public Xof_bin_mgr(Fsm_mnt_mgr mnt_mgr, Xow_repo_mgr repo_mgr, Xof_cache_mgr cache_mgr, Xof_img_wkr_resize_img resize_wkr) {
		this.mnt_mgr = mnt_mgr; this.repo_mgr = repo_mgr; this.cache_mgr = cache_mgr;
		this.usr_dlg = Gfo_usr_dlg_.I;			
		this.Resizer_(resize_wkr);
	}
	public void Resizer_(Xof_img_wkr_resize_img v) {resizer = v;} private Xof_img_wkr_resize_img resizer;
	public void Wkrs__del(String key) {
		ListAdp list = ListAdp_.new_();
		for (Xof_bin_wkr wkr : wkrs) {
			if (String_.Eq(key, wkr.Key())) continue;
			list.Add(wkr);
		}
		this.wkrs = (Xof_bin_wkr[])list.Xto_ary(Xof_bin_wkr.class);
		this.wkrs_len = wkrs.length;
	}
	public void Wkrs__add(Xof_bin_wkr v) {
		this.wkrs = (Xof_bin_wkr[])Array_.Resize_add_one(wkrs, wkrs_len, v);
		++this.wkrs_len;
	}
	public Xof_bin_wkr Wkrs__get_or_null(String key) {
		byte tid = Xof_bin_wkr_.X_key_to_tid(key);
		for (int i = 0; i < wkrs_len; ++i) {
			Xof_bin_wkr wkr = wkrs[i];
			if (wkr.Tid() == tid) return wkr;
		}
		return null;
	}
	public boolean Find_to_url_as_bool(byte exec_tid, Xof_fsdb_itm itm) {return Find_to_url(exec_tid, itm) != Io_url_.Null;}
	private Io_url Find_to_url(byte exec_tid, Xof_fsdb_itm itm) {
		Io_stream_rdr rdr = Find_as_rdr(exec_tid, itm);
		if (rdr == Io_stream_rdr_.Null) return Io_url_.Null;
		Io_url trg = itm.Html_view_url();
		if (itm.File_resized()) return trg;			// rdr is opened directly from trg; return its url; occurs when url goes through imageMagick / inkscape, or when thumb is already on disk;
		Io_stream_wtr_.Save_rdr(trg, rdr);			// rdr is stream; either from http_wmf or fsdb; save to trg and return;
		if (!Env_.Mode_testing()) cache_mgr.Reg(itm, rdr.Len());
		return trg;
	}
	public Io_stream_rdr Find_as_rdr(byte exec_tid, Xof_fsdb_itm fsdb) {
		Io_stream_rdr rv = Io_stream_rdr_.Null;
		Xof_repo_itm repo = repo_mgr.Repos_get_by_wiki(fsdb.Orig_repo_name()).Trg();
		boolean file_is_orig = fsdb.File_is_orig();
		if (file_is_orig || exec_tid == Xof_exec_tid.Tid_viewer_app) {			// orig or viewer_app; note that viewer_app always return orig
			Io_url trg = url_bldr.To_url_trg(repo, fsdb, Bool_.Y);
			fsdb.Html_view_url_(trg);
			for (int i = 0; i < wkrs_len; i++) {
				Xof_bin_wkr wkr = wkrs[i];
				rv = wkr.Get_as_rdr(fsdb, Bool_.N, fsdb.Html_w());
				if (rv == Io_stream_rdr_.Null) continue;						// orig not found; continue;
				fsdb.File_exists_y_();
				return rv;
			}
		}
		else {																	// thumb
			Io_url trg = url_bldr.To_url_trg(repo, fsdb, Bool_.N);
			fsdb.Html_view_url_(trg);
			for (int i = 0; i < wkrs_len; i++) {
				Xof_bin_wkr wkr = wkrs[i];
				rv = wkr.Get_as_rdr(fsdb, Bool_.Y, fsdb.Html_w());				// get thumb's bin
				if (rv != Io_stream_rdr_.Null) {								// thumb's bin exists;
					fsdb.File_exists_y_();
					return rv;
				}
				rv = wkr.Get_as_rdr(fsdb, Bool_.N, fsdb.Orig_w());				// thumb missing; get orig;
				if (rv == Io_stream_rdr_.Null) {
					usr_dlg.Log_direct(String_.Format("bin_mgr:thumb not found; wkr={0} ttl={1} w={2}", wkr.Key(), fsdb.Lnki_ttl(), fsdb.Lnki_w()));
					continue;													// nothing found; continue;
				}
				if (!wkr.Resize_allowed()) continue;
				Io_url orig = url_bldr.To_url_trg(repo, fsdb, Bool_.Y);			// get orig url
				Io_stream_wtr_.Save_rdr(orig, rv);
				boolean resized = Resize(exec_tid, fsdb, file_is_orig, orig, trg);
				if (!resized) continue;
				fsdb.File_exists_y_();
				rv = Io_stream_rdr_.file_(trg);									// return stream of resized url; (result of imageMagick / inkscape)
				rv.Open();
				return rv;
			}
		}
		return Io_stream_rdr_.Null;
	}
	private boolean Resize(byte exec_tid, Xof_fsdb_itm itm, boolean file_is_orig, Io_url src, Io_url trg) {			
		tmp_size.Html_size_calc(exec_tid, itm.Lnki_w(), itm.Lnki_h(), itm.Lnki_type(), mnt_mgr.Patch_upright(), itm.Lnki_upright(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), Xof_img_size.Thumb_width_img);
		boolean rv = resizer.Exec(src, trg, tmp_size.Html_w(), tmp_size.Html_h(), itm.Lnki_ext().Id(), resize_warning);
		itm.File_resized_y_();
		return rv;
	}
}
