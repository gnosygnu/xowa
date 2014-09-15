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
import gplx.xowa.files.gui.*;
public class Xof_fsdb_itm {
	public byte[]		Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte[]		Lnki_md5() {return lnki_md5;} private byte[] lnki_md5;
	public Xof_ext		Lnki_ext() {return lnki_ext;} private Xof_ext lnki_ext;
	public void			Lnki_ext_(Xof_ext v) {lnki_ext = v;}
	public byte			Lnki_type() {return lnki_type;} private byte lnki_type;
	public byte			Lnki_type_as_mode() {return lnki_type_as_mode;} private byte lnki_type_as_mode;
	public int			Lnki_w() {return lnki_w;} public Xof_fsdb_itm Lnki_w_(int v) {lnki_w = v; return this;} private int lnki_w;
	public int			Lnki_h() {return lnki_h;} public Xof_fsdb_itm Lnki_h_(int v) {lnki_h = v; return this;} private int lnki_h;
	public Xof_fsdb_itm Lnki_size_(int w, int h) {lnki_w = w; lnki_h = h; return this;} 
	public double		Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double		Lnki_thumbtime() {return lnki_thumbtime;} public Xof_fsdb_itm Lnki_thumbtime_(double v) {lnki_thumbtime = v; return this;} private double lnki_thumbtime = Xof_doc_thumb.Null;
	public int			Lnki_page() {return lnki_page;} public Xof_fsdb_itm Lnki_page_(int v) {lnki_page = v; return this;} private int lnki_page = Xof_doc_page.Null;
	public Xof_fsdb_itm	Init_by_lnki(byte[] lnki_ttl, Xof_ext ext, byte[] md5, byte lnki_type, int lnki_w, int lnki_h, int lnki_upright_patch, double lnki_upright, double lnki_thumbtime, int lnki_page) {
		this.lnki_ttl = lnki_ttl; this.lnki_ext = ext; this.lnki_md5 = md5;
		this.lnki_w = lnki_w; this.lnki_h = lnki_h;
		this.lnki_upright_patch = lnki_upright_patch;
		this.lnki_upright = lnki_upright; this.lnki_thumbtime = lnki_thumbtime; this.lnki_page = lnki_page;
		this.Lnki_type_(lnki_type);
		this.orig_ttl = lnki_ttl;
		return this;
	}
	private int lnki_upright_patch;
	public void Lnki_type_(byte v) {
		this.lnki_type = v;
		if (lnki_ext.Id_is_audio_strict())
			file_is_orig = true;
		else
			this.file_is_orig = !(Xop_lnki_type.Id_defaults_to_thumb(lnki_type) || lnki_w != Xop_lnki_tkn.Width_null || lnki_h != Xop_lnki_tkn.Height_null);
		this.lnki_type_as_mode = file_is_orig ? Xof_repo_itm.Mode_orig : Xof_repo_itm.Mode_thumb;
	} 
	public Xof_fsdb_itm	Lnki_ttl_(byte[] v) {
		lnki_ttl = v;
		lnki_ext = Xof_ext_.new_by_ttl_(v);
		lnki_md5 = Xof_xfer_itm_.Md5_calc(v);
		return this;
	}
	public Xof_fsdb_itm	Init_by_redirect(byte[] redirect_ttl) {
		orig_redirect = redirect_ttl;
		lnki_ttl = redirect_ttl;
		lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl);
		lnki_md5 = Xof_xfer_itm_.Md5_calc(lnki_ttl);
		return this;
	}
	public int			Orig_w() {return orig_w;} private int orig_w = Xop_lnki_tkn.Width_null;
	public int			Orig_h() {return orig_h;} private int orig_h = Xop_lnki_tkn.Height_null;
	public Xof_fsdb_itm Orig_size_(int w, int h) {orig_w = w; orig_h = h; return this;} 
	public byte[]		Orig_wiki() {return orig_wiki;} public Xof_fsdb_itm Orig_wiki_(byte[] v) {orig_wiki = v; return this;} private byte[] orig_wiki;
	public byte[]		Orig_ttl() {return orig_ttl;} public Xof_fsdb_itm Orig_ttl_(byte[] v) {orig_ttl = v; return this;} private byte[] orig_ttl;
	public byte[]		Orig_redirect() {return orig_redirect;} public Xof_fsdb_itm Orig_redirect_(byte[] v) {orig_redirect = v; return this;} private byte[] orig_redirect = Bry_.Empty;
	public byte			Orig_repo() {return orig_repo;} public Xof_fsdb_itm Orig_repo_(byte v) {orig_repo = v; return this;} private byte orig_repo = Xof_repo_itm.Repo_null;
	public boolean			File_is_orig() {return file_is_orig;} public Xof_fsdb_itm File_is_orig_(boolean v) {file_is_orig = v; return this;} private boolean file_is_orig;
	public int			File_w() {return file_w;} private int file_w;
	public int			Html_uid() {return html_uid;} public Xof_fsdb_itm Html_uid_(int v) {html_uid = v; return this;} private int html_uid;
	public byte			Html_elem_tid() {return html_elem_tid;} public Xof_fsdb_itm Html_elem_tid_(byte v) {html_elem_tid = v; return this;} private byte html_elem_tid;
	public Io_url		Html_url() {return html_url;} public Xof_fsdb_itm Html_url_(Io_url v) {html_url = v; return this;} private Io_url html_url;
	public int			Html_w() {return html_w;} private int html_w;
	public int			Html_h() {return html_h;} private int html_h;
	public Xof_fsdb_itm Html_size_(int w, int h) {html_w = w; html_h = h; return this;} 
	public Js_img_wkr	Html_img_wkr() {return html_img_wkr;} public void Html_img_wkr_(Js_img_wkr v) {html_img_wkr = v;} private Js_img_wkr html_img_wkr;
	public void			Html_size_calc(Xof_img_size img_size, byte exec_tid) {
		if (!lnki_ext.Id_is_audio_strict()) {	// audio does not have html size calculated; everything else does
			img_size.Html_size_calc(exec_tid, lnki_w, lnki_h, lnki_type, lnki_upright_patch, lnki_upright, lnki_ext.Id(), orig_w, orig_h, Xof_img_size.Thumb_width_img);
			html_w = img_size.Html_w(); html_h = img_size.Html_h(); file_w = img_size.File_w();
			file_is_orig = img_size.File_is_orig();
			lnki_type_as_mode = file_is_orig ? Xof_repo_itm.Mode_orig : Xof_repo_itm.Mode_thumb;
		}
	}
	public void			Html__init(Xow_repo_mgr repo_mgr, Xof_url_bldr url_bldr, Xof_img_size img_size, byte exec_tid) {this.Html__init(repo_mgr.Repos_get_by_wiki(orig_wiki).Trg(), url_bldr, img_size, exec_tid);}
	public void			Html__init(Xof_repo_itm repo, Xof_url_bldr url_bldr, Xof_img_size img_size, byte exec_tid) {
		Html_size_calc(img_size, exec_tid);
		byte[] name_bry = Bry_.Len_eq_0(orig_redirect) ? lnki_ttl : orig_redirect; 
		if (!lnki_ext.Id_is_media() && lnki_thumbtime != Xof_doc_thumb.Null)	// file is not media, but has thumbtime; this check can't be moved to Lnki_thumbtime_() b/c it needs ext
			lnki_thumbtime = Xof_doc_thumb.Null;								// set thumbtime to null; needed else url will reference thumbtime; PAGE:en.w:Moon; EX:[[File:Lunar libration with phase Oct 2007 450px.gif|thumbtime=0:02]]; DATE:2014-07-20
		html_url = url_bldr.Init_for_trg_file(lnki_type_as_mode, repo, name_bry, lnki_md5, lnki_ext, html_w, lnki_thumbtime, lnki_page).Xto_url();
		html_orig_url = url_bldr.Init_for_trg_file(Xof_repo_itm.Mode_orig, repo, name_bry, lnki_md5, lnki_ext, Xof_img_size.Size_null_deprecated, Xof_doc_thumb.Null, Xof_doc_page.Null).Xto_url();
	}
	public Io_url Html_orig_url() {return html_orig_url;} public Xof_fsdb_itm Html_orig_url_(Io_url v) {this.html_orig_url = v; return this;} private Io_url html_orig_url = Io_url_.Null;
	public int Gallery_mgr_h() {return gallery_mgr_h;} public Xof_fsdb_itm Gallery_mgr_h_(int v) {gallery_mgr_h = v; return this;} private int gallery_mgr_h = Int_.Neg1;
	public byte Rslt_reg() {return rslt_reg;} public Xof_fsdb_itm Rslt_reg_(byte v) {this.rslt_reg = v; return this;} private byte rslt_reg = gplx.xowa.files.wiki_orig.Xof_wiki_orig_wkr_.Tid_null;
	public byte Rslt_qry() {return rslt_qry;} public Xof_fsdb_itm Rslt_qry_(byte v) {this.rslt_qry = v; return this;} private byte rslt_qry;
	public byte Rslt_bin() {return rslt_bin;} public Xof_fsdb_itm Rslt_bin_(byte v) {this.rslt_bin = v; return this;} private byte rslt_bin;
	public byte Rslt_cnv() {return rslt_cnv;} public Xof_fsdb_itm Rslt_cnv_(byte v) {this.rslt_cnv = v; return this;} private byte rslt_cnv;
	public boolean Rslt_fil_created() {return rslt_fil_created;} public Xof_fsdb_itm Rslt_fil_created_(boolean v) {rslt_fil_created = v; return this;} private boolean rslt_fil_created;
}
