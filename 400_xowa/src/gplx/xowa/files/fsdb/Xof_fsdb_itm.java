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
import gplx.xowa.files.gui.*; import gplx.xowa.files.repos.*;
public class Xof_fsdb_itm {
	private int lnki_upright_patch;
	public byte[]		Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte[]		Lnki_md5() {return lnki_md5;} private byte[] lnki_md5;
	public Xof_ext		Lnki_ext() {return lnki_ext;} public void Lnki_ext_(Xof_ext v) {lnki_ext = v;} private Xof_ext lnki_ext;		
	public byte			Lnki_type() {return lnki_type;} private byte lnki_type;
	public byte			Lnki_type_as_mode() {return lnki_type_as_mode;} private byte lnki_type_as_mode;
	public int			Lnki_w() {return lnki_w;} private int lnki_w;
	public int			Lnki_h() {return lnki_h;} private int lnki_h;
	public Xof_fsdb_itm Lnki_size_(int w, int h) {lnki_w = w; lnki_h = h; return this;} 
	public double		Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double		Lnki_time() {return lnki_time;} public void Lnki_time_(double v) {lnki_time = v;} private double lnki_time = Xof_doc_thumb.Null;
	public int			Lnki_page() {return lnki_page;} public void Lnki_page_(int v) {lnki_page = v;} private int lnki_page = Xof_doc_page.Null;
	public byte			Orig_repo_id() {return orig_repo_id;} public void Orig_repo_id_(byte v) {orig_repo_id = v;} private byte orig_repo_id = Xof_repo_itm.Repo_null;
	public byte[]		Orig_repo_name() {return orig_repo_name;} public void Orig_repo_name_(byte[] v) {orig_repo_name = v;} private byte[] orig_repo_name;
	public byte[]		Orig_ttl() {return orig_ttl;} private byte[] orig_ttl;
	public int			Orig_w() {return orig_w;} private int orig_w = Xop_lnki_tkn.Width_null;
	public int			Orig_h() {return orig_h;} private int orig_h = Xop_lnki_tkn.Height_null;
	public Xof_fsdb_itm Orig_size_(int w, int h) {orig_w = w; orig_h = h; return this;} 
	public byte[]		Orig_redirect() {return orig_redirect;} public void Orig_redirect_(byte[] v) {orig_redirect = v;} private byte[] orig_redirect = Bry_.Empty;
	public byte			Orig_status() {return orig_status;} public void Orig_status_(byte v) {orig_status = v;} private byte orig_status = gplx.xowa.files.origs.Xof_orig_wkr_.Status_null;
	public boolean			File_is_orig() {return file_is_orig;} public void File_is_orig_(boolean v) {file_is_orig = v;} private boolean file_is_orig;
	public int			File_w() {return file_w;} private int file_w;
	public int			Html_uid() {return html_uid;} public void Html_uid_(int v) {html_uid = v;} private int html_uid;
	public byte			Html_elem_tid() {return html_elem_tid;} public void Html_elem_tid_(byte v) {html_elem_tid = v;} private byte html_elem_tid;
	public int			Html_w() {return html_w;} private int html_w;
	public int			Html_h() {return html_h;} private int html_h;
	public Xof_fsdb_itm Html_size_(int w, int h) {html_w = w; html_h = h; return this;} 
	public Js_img_wkr	Html_img_wkr() {return html_img_wkr;} public void Html_img_wkr_(Js_img_wkr v) {html_img_wkr = v;} private Js_img_wkr html_img_wkr;
	public Io_url		Html_view_url() {return html_view_url;} public void Html_view_url_(Io_url v) {html_view_url = v;} private Io_url html_view_url;
	public Io_url		Html_orig_url() {return html_orig_url;} public void Html_orig_url_(Io_url v) {html_orig_url = v;} private Io_url html_orig_url = Io_url_.Null;
	public int			Gallery_mgr_h() {return gallery_mgr_h;} public void Gallery_mgr_h_(int v) {gallery_mgr_h = v;} private int gallery_mgr_h = Int_.Neg1;
	public byte			Rslt_bin() {return rslt_bin;} public void Rslt_bin_(byte v) {rslt_bin = v;} private byte rslt_bin;
	public byte			Rslt_cnv() {return rslt_cnv;} public void Rslt_cnv_(byte v) {rslt_cnv = v;} private byte rslt_cnv;
	public boolean			Rslt_fil_created() {return rslt_fil_created;} public void Rslt_fil_created_(boolean v) {rslt_fil_created = v;} private boolean rslt_fil_created;
	public void			Ctor_by_orig(byte repo, byte[] ttl, int w, int h, byte[] redirect) {
		this.orig_repo_id = repo; this.orig_ttl = ttl; this.orig_w = w; this.orig_h = h; this.orig_redirect = redirect;
	}
	public void			Ctor_by_orig_redirect(byte[] redirect_ttl) {
		orig_redirect = redirect_ttl;
		lnki_ttl = redirect_ttl;
		lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl);
		lnki_md5 = Xof_xfer_itm_.Md5_calc(lnki_ttl);
	}
	public void			Ctor_by_html(Xof_repo_itm repo, Xof_url_bldr url_bldr, Xof_img_size img_size, byte exec_tid) {
		Html_size_calc(img_size, exec_tid);
		byte[] name_bry = Bry_.Len_eq_0(orig_redirect) ? lnki_ttl : orig_redirect; 
		if (!lnki_ext.Id_is_media() && lnki_time != Xof_doc_thumb.Null)	// file is not media, but has thumbtime; this check can't be moved to Lnki_time_() b/c it needs ext
			lnki_time = Xof_doc_thumb.Null;								// set time to null; needed else url will reference thumbtime; PAGE:en.w:Moon; EX:[[File:Lunar libration with phase Oct 2007 450px.gif|thumbtime=0:02]]; DATE:2014-07-20
		html_view_url = url_bldr.Init_for_trg_file(lnki_type_as_mode, repo, name_bry, lnki_md5, lnki_ext, html_w, lnki_time, lnki_page).Xto_url();
		html_orig_url = url_bldr.Init_for_trg_file(Xof_repo_itm.Mode_orig, repo, name_bry, lnki_md5, lnki_ext, Xof_img_size.Size_null_deprecated, Xof_doc_thumb.Null, Xof_doc_page.Null).Xto_url();
	}
	public void			Ctor_by_lnki(byte[] lnki_ttl, Xof_ext ext, byte[] md5, byte lnki_type, int lnki_w, int lnki_h, int lnki_upright_patch, double lnki_upright, double lnki_time, int lnki_page) {
		this.lnki_ttl = lnki_ttl; this.lnki_ext = ext; this.lnki_md5 = md5;
		this.lnki_w = lnki_w; this.lnki_h = lnki_h;
		this.lnki_upright_patch = lnki_upright_patch;
		this.lnki_upright = lnki_upright; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
		this.Lnki_type_(lnki_type);
		this.orig_ttl = lnki_ttl;
	}
	public void			Html_size_calc(Xof_img_size img_size, byte exec_tid) {
		if (!lnki_ext.Id_is_audio_strict()) {	// audio does not have html size calculated; everything else does
			img_size.Html_size_calc(exec_tid, lnki_w, lnki_h, lnki_type, lnki_upright_patch, lnki_upright, lnki_ext.Id(), orig_w, orig_h, Xof_img_size.Thumb_width_img);
			html_w = img_size.Html_w(); html_h = img_size.Html_h(); file_w = img_size.File_w();
			file_is_orig = img_size.File_is_orig();
			lnki_type_as_mode = file_is_orig ? Xof_repo_itm.Mode_orig : Xof_repo_itm.Mode_thumb;
		}
	}
	public void			Lnki_type_(byte v) {
		this.lnki_type = v;
		if (lnki_ext.Id_is_audio_strict())
			file_is_orig = true;
		else
			this.file_is_orig = !(Xop_lnki_type.Id_defaults_to_thumb(lnki_type) || lnki_w != Xop_lnki_tkn.Width_null || lnki_h != Xop_lnki_tkn.Height_null);
		this.lnki_type_as_mode = file_is_orig ? Xof_repo_itm.Mode_orig : Xof_repo_itm.Mode_thumb;
	} 
	public void			Lnki_ttl_(byte[] v) {
		lnki_ttl = v;
		lnki_ext = Xof_ext_.new_by_ttl_(v);
		lnki_md5 = Xof_xfer_itm_.Md5_calc(v);
	}
}
