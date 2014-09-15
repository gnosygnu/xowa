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
import gplx.xowa.files.fsdb.*; import gplx.xowa.files.cnvs.*; import gplx.ios.*;
public class Xof_bin_mgr implements GfoInvkAble {
	private Xof_bin_wkr[] wkrs; private int wkrs_len;
	private Xof_url_bldr url_bldr = Xof_url_bldr.new_v2_();
	private Xof_fsdb_mgr_sql fsdb_mgr;
	private Xow_wiki wiki;
	private String_obj_ref resize_warning = String_obj_ref.null_(); private Xof_img_size tmp_size = new Xof_img_size();
	public Xof_bin_mgr(Xow_wiki wiki, Xof_fsdb_mgr_sql fsdb_mgr, Xow_repo_mgr repo_mgr) {this.Clear(); this.wiki = wiki; this.fsdb_mgr = fsdb_mgr; this.repo_mgr = repo_mgr;}
	public Xow_repo_mgr Repo_mgr() {return repo_mgr;} private Xow_repo_mgr repo_mgr;
	public void Resizer_(Xof_img_wkr_resize_img v) {resizer = v;} private Xof_img_wkr_resize_img resizer;
	void Wkrs_(Xof_bin_wkr... wkrs) {this.wkrs = wkrs; wkrs_len = wkrs.length;}
	public boolean Find_to_url_as_bool(ListAdp temp_files, byte exec_tid, Xof_fsdb_itm itm) {
		return Find_to_url(temp_files, exec_tid, itm) != Io_url_.Null;
	}
	public Io_url Find_to_url(ListAdp temp_files, byte exec_tid, Xof_fsdb_itm itm) {
		Io_stream_rdr rdr = Find_as_rdr(temp_files, exec_tid, itm);
		if (rdr == Io_stream_rdr_.Null) return Io_url_.Null;
		Io_url trg = itm.Html_url();
		if (itm.Rslt_fil_created()) return trg;	// rdr is opened directly from trg; return its url; occurs when url goes through imageMagick / inkscape, or when thumb is already in disk;
		Io_stream_wtr_.Save_rdr(trg, rdr);		// rdr is stream; either from http_wmf or fsdb; save to trg and return;
		if (Xof_bin_wkr_.Tid_is_fsdb(itm.Rslt_bin())) {	// rdr is coming from fsdb; register in cache
			if (!Env_.Mode_testing())
				fsdb_mgr.Cache_mgr().Reg(wiki, itm, rdr.Len());
		}
		return trg;
	}
	public Io_stream_rdr Find_as_rdr(ListAdp temp_files, byte exec_tid, Xof_fsdb_itm itm) {
		Io_stream_rdr rv = Io_stream_rdr_.Null;
		boolean file_is_orig = itm.File_is_orig();
		if (file_is_orig || exec_tid == Xof_exec_tid.Tid_viewer_app) {		// mode is viewer_app; always return orig
			Io_url trg = Get_url(itm, Xof_repo_itm.Mode_orig, Bool_.N);
			itm.Html_url_(trg);
			for (int i = 0; i < wkrs_len; i++) {
				Xof_bin_wkr wkr = wkrs[i];
				rv = wkr.Bin_wkr_get_as_rdr(temp_files, itm, Bool_.N, itm.Html_w());
				if (rv == Io_stream_rdr_.Null) continue;						// orig not found; continue;
				itm.Rslt_bin_(wkr.Bin_wkr_tid());
				return rv;
			}
		}
		else {	// thumb
			Io_url trg = Get_url(itm, Xof_repo_itm.Mode_thumb, Bool_.N);
			itm.Html_url_(trg);
			for (int i = 0; i < wkrs_len; i++) {
				Xof_bin_wkr wkr = wkrs[i];
				rv = wkr.Bin_wkr_get_as_rdr(temp_files, itm, Bool_.Y, itm.Html_w());		// get thumb's bin
				if (rv != Io_stream_rdr_.Null) {											// thumb's bin exists;
					itm.Rslt_bin_(wkr.Bin_wkr_tid());
					return rv;
				}
				wiki.App().Usr_dlg().Log_direct(String_.Format("thumb not found; ttl={0} w={1} ", String_.new_utf8_(itm.Lnki_ttl()), itm.Lnki_w()));
				rv = wkr.Bin_wkr_get_as_rdr(temp_files, itm, Bool_.N, itm.Orig_w());		// thumb missing; get orig;
				if (rv == Io_stream_rdr_.Null) {
					wiki.App().Usr_dlg().Log_direct(String_.Format("orig not found; "));
					continue;									// nothing found; continue;
				}
				if (!wkr.Bin_wkr_resize()) continue;
				Io_url orig = Get_url(itm, Xof_repo_itm.Mode_orig, Bool_.N);				// get orig url
				Io_stream_wtr_.Save_rdr(orig, rv);
				boolean resized = Resize(exec_tid, itm, file_is_orig, orig, trg);
				if (!resized) continue;
				itm.Rslt_bin_(wkr.Bin_wkr_tid());
				itm.Rslt_fil_created_(true);
				rv = Io_stream_rdr_.file_(trg);												// return stream of resized url; (result of imageMagick / inkscape)
				rv.Open();
				return rv;
			}
		}
		return Io_stream_rdr_.Null;
	}
	private boolean Resize(byte exec_tid, Xof_fsdb_itm itm, boolean file_is_orig, Io_url src, Io_url trg) {			
		tmp_size.Html_size_calc(exec_tid, itm.Lnki_w(), itm.Lnki_h(), itm.Lnki_type(), fsdb_mgr.Patch_upright(), itm.Lnki_upright(), itm.Lnki_ext().Id(), itm.Orig_w(), itm.Orig_h(), Xof_img_size.Thumb_width_img);
		boolean rv = resizer.Exec(src, trg, tmp_size.Html_w(), tmp_size.Html_h(), itm.Lnki_ext().Id(), resize_warning);
		itm.Rslt_cnv_(rv ? Xof_cnv_wkr_.Tid_y : Xof_cnv_wkr_.Tid_n);
		return rv;
	}
	private Io_url Get_url(Xof_fsdb_itm itm, byte mode, boolean src) {
		Xof_repo_pair repo = repo_mgr.Repos_get_by_wiki(itm.Orig_wiki());
		return src 
			? url_bldr.Init_for_src_file(mode, repo.Src(), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), itm.Html_w(), itm.Lnki_thumbtime(), itm.Lnki_page()).Xto_url()
			: url_bldr.Init_for_trg_file(mode, repo.Trg(), itm.Lnki_ttl(), itm.Lnki_md5(), itm.Lnki_ext(), itm.Html_w(), itm.Lnki_thumbtime(), itm.Lnki_page()).Xto_url()
			;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))		return Get_or_new(m.ReadStr("type"), m.ReadStrOr("key", null));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_add = "add";
	public Xof_bin_wkr Get_or_new(String type) {return Get_or_new(type, null);}
	public Xof_bin_wkr Get_or_new(String type, String key) {
		if (key == null) key = type;	// default empty key to type; EX: add('xowa.http.wmf') -> add('xowa.http.wmf', 'xowa.http.wmf')
		Xof_bin_wkr rv = Get_or_null(key);
		if (rv == null) {
			rv = Make(type, key);
			wkrs = (Xof_bin_wkr[])Array_.Resize_add(wkrs, new Xof_bin_wkr[] {rv});
			++wkrs_len;
		}
		return rv;
	}
	public void Add(Xof_bin_wkr v) {Add_many(v);}
	public void Add_many(Xof_bin_wkr... v) {
		wkrs = (Xof_bin_wkr[])Array_.Resize_add(wkrs, v);
		wkrs_len += v.length;
	}
	public void Clear() {wkrs = Xof_bin_wkr_.Ary_empty; wkrs_len = 0;}
	private Xof_bin_wkr Get_or_null(String key) {
		int wkrs_len = wkrs.length;
		byte tid = Xof_bin_wkr_.X_key_to_tid(key);
		for (int i = 0; i < wkrs_len; i ++) {
			Xof_bin_wkr wkr = wkrs[i];
			if (wkr.Bin_wkr_tid() == tid) return wkr;
		}
		return null;
	}
	private Xof_bin_wkr Make(String type, String key) {
		Xof_bin_wkr rv = null;
		if		(String_.Eq(type, Xof_bin_wkr_.Key_fsdb_wiki))		rv = new Xof_bin_wkr_fsdb_sql(fsdb_mgr);
		else if	(String_.Eq(type, Xof_bin_wkr_.Key_fsys_wmf))		rv = new Xof_bin_wkr_fsys_wmf();
		else if	(String_.Eq(type, Xof_bin_wkr_.Key_http_wmf))		rv = new Xof_bin_wkr_http_wmf(repo_mgr, wiki.App().File_mgr().Download_mgr().Download_wkr().Download_xrg());
		else														throw Err_.unhandled(type);
		return rv;
	}
}
