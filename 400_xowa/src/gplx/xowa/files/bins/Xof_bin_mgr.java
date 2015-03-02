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
public class Xof_bin_mgr implements GfoInvkAble {		
	private final Gfo_usr_dlg usr_dlg; private final Fsm_mnt_mgr mnt_mgr; private final Xow_repo_mgr repo_mgr; private final Xof_cache_mgr cache_mgr; private final Xowmf_mgr wmf_mgr; private final Xof_url_bldr url_bldr;
	private Xof_bin_wkr[] wkrs; private int wkrs_len;		
	private final String_obj_ref resize_warning = String_obj_ref.null_(); private final Xof_img_size tmp_size = new Xof_img_size();
	public Xof_bin_mgr(Fsm_mnt_mgr mnt_mgr, Xow_repo_mgr repo_mgr, Xof_cache_mgr cache_mgr, Xowmf_mgr wmf_mgr, Xof_url_bldr url_bldr) {
		this.mnt_mgr = mnt_mgr; this.repo_mgr = repo_mgr; this.cache_mgr = cache_mgr; this.wmf_mgr = wmf_mgr; this.url_bldr = url_bldr;
		this.usr_dlg = Gfo_usr_dlg_._;			
		Wkrs__clear();
	}
	public void Resizer_(Xof_img_wkr_resize_img v) {resizer = v;} private Xof_img_wkr_resize_img resizer;
	public void Init_by_wiki(Xof_img_wkr_resize_img resize_wkr) {
		this.Wkrs__get_or_new(Xof_bin_wkr_.Key_fsdb_wiki);
		this.Wkrs__get_or_new(Xof_bin_wkr_.Key_http_wmf);
		this.Resizer_(resize_wkr);
	}
	public boolean Find_to_url_as_bool(byte exec_tid, Xof_fsdb_itm itm) {return Find_to_url(exec_tid, itm) != Io_url_.Null;}
	private Io_url Find_to_url(byte exec_tid, Xof_fsdb_itm itm) {
		Io_stream_rdr rdr = Find_as_rdr(exec_tid, itm);
		if (rdr == Io_stream_rdr_.Null) return Io_url_.Null;
		Io_url trg = itm.Html_view_url();
		if (itm.Rslt_fil_created()) return trg;	// rdr is opened directly from trg; return its url; occurs when url goes through imageMagick / inkscape, or when thumb is already on disk;
		Io_stream_wtr_.Save_rdr(trg, rdr);		// rdr is stream; either from http_wmf or fsdb; save to trg and return;
		if (itm.Rslt_bin() == Xof_bin_wkr_.Tid_fsdb_xowa) {	// rdr is coming from fsdb; register in cache
			if (!Env_.Mode_testing())
				cache_mgr.Reg(itm, rdr.Len());
		}
		return trg;
	}
	public Io_stream_rdr Find_as_rdr(byte exec_tid, Xof_fsdb_itm itm) {
		Io_stream_rdr rv = Io_stream_rdr_.Null;
		boolean file_is_orig = itm.File_is_orig();
		if (file_is_orig || exec_tid == Xof_exec_tid.Tid_viewer_app) {			// orig or viewer_app; note that viewer_app always return orig
			Io_url trg = url_bldr.To_url(repo_mgr, itm, Bool_.Y);
			itm.Html_view_url_(trg);
			for (int i = 0; i < wkrs_len; i++) {
				Xof_bin_wkr wkr = wkrs[i];
				rv = wkr.Get_as_rdr(itm, Bool_.N, itm.Html_w());
				if (rv == Io_stream_rdr_.Null) continue;						// orig not found; continue;
				itm.Rslt_bin_(wkr.Tid());
				return rv;
			}
		}
		else {																	// thumb
			Io_url trg = url_bldr.To_url(repo_mgr, itm, Bool_.N);
			itm.Html_view_url_(trg);
			for (int i = 0; i < wkrs_len; i++) {
				Xof_bin_wkr wkr = wkrs[i];
				rv = wkr.Get_as_rdr(itm, Bool_.Y, itm.Html_w());				// get thumb's bin
				if (rv != Io_stream_rdr_.Null) {								// thumb's bin exists;
					itm.Rslt_bin_(wkr.Tid());
					return rv;
				}
				usr_dlg.Log_direct(String_.Format("thumb not found; ttl={0} w={1} ", String_.new_utf8_(itm.Lnki_ttl()), itm.Lnki_w()));
				rv = wkr.Get_as_rdr(itm, Bool_.N, itm.Orig_w());				// thumb missing; get orig;
				if (rv == Io_stream_rdr_.Null) {
					usr_dlg.Log_direct(String_.Format("orig not found;"));
					continue;													// nothing found; continue;
				}
				if (!wkr.Resize_allowed()) continue;
				Io_url orig = url_bldr.To_url(repo_mgr, itm, Bool_.Y);			// get orig url
				Io_stream_wtr_.Save_rdr(orig, rv);
				boolean resized = Resize(exec_tid, itm, file_is_orig, orig, trg);
				if (!resized) continue;
				itm.Rslt_bin_(wkr.Tid());
				itm.Rslt_fil_created_(true);
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
		itm.Rslt_cnv_(rv ? Xof_cnv_wkr_.Tid_y : Xof_cnv_wkr_.Tid_n);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))		return Wkrs__get_or_new(m.ReadStr("type"), m.ReadStrOr("key", null));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_add = "add";
	public void Wkrs__clear() {this.wkrs = Xof_bin_wkr_.Ary_empty; this.wkrs_len = 0;}
	public Xof_bin_wkr Wkrs__get_or_new(String type) {return Wkrs__get_or_new(type, null);}
	private Xof_bin_wkr Wkrs__get_or_new(String type, String key) {
		if (key == null) key = type;	// default empty key to type; EX: add('xowa.http.wmf') -> add('xowa.http.wmf', 'xowa.http.wmf')
		Xof_bin_wkr rv = Wkrs__get_or_null(key);
		if (rv == null) {
			rv = Wkrs__new(type);
			Wkrs__add(rv);
		}
		return rv;
	}
	public void Wkrs__add(Xof_bin_wkr v) {Wkrs__add_many(v);}
	private void Wkrs__add_many(Xof_bin_wkr... v) {
		wkrs = (Xof_bin_wkr[])Array_.Resize_add(wkrs, v);
		wkrs_len += v.length;
	}
	private Xof_bin_wkr Wkrs__get_or_null(String key) {
		int wkrs_len = wkrs.length;
		byte tid = Xof_bin_wkr_.X_key_to_tid(key);
		for (int i = 0; i < wkrs_len; ++i) {
			Xof_bin_wkr wkr = wkrs[i];
			if (wkr.Tid() == tid) return wkr;
		}
		return null;
	}
	private Xof_bin_wkr Wkrs__new(String type) {
		Xof_bin_wkr rv = null;
		if		(String_.Eq(type, Xof_bin_wkr_.Key_fsdb_wiki))		rv = new Xof_bin_wkr__fsdb_sql(mnt_mgr);
		else if	(String_.Eq(type, Xof_bin_wkr_.Key_fsys_wmf))		rv = new Xof_bin_wkr__fsys_wmf();
		else if	(String_.Eq(type, Xof_bin_wkr_.Key_http_wmf))		rv = new Xof_bin_wkr__http_wmf(repo_mgr, wmf_mgr.Download_wkr().Download_xrg());
		else														throw Err_.unhandled(type);
		return rv;
	}
}
