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
	public byte[]				Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte[]				Lnki_md5() {return lnki_md5;} private byte[] lnki_md5;
	public Xof_ext				Lnki_ext() {return lnki_ext;} private Xof_ext lnki_ext;		
	public byte					Lnki_type() {return lnki_type;} private byte lnki_type;
	public int					Lnki_w() {return lnki_w;} private int lnki_w;
	public int					Lnki_h() {return lnki_h;} private int lnki_h;
	public double				Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double				Lnki_time() {return lnki_time;} private double lnki_time = Xof_lnki_time.Null;
	public int					Lnki_page() {return lnki_page;} private int lnki_page = Xof_lnki_page.Null;
	public byte					Orig_repo_id() {return orig_repo_id;} private byte orig_repo_id = Xof_repo_itm.Repo_null;
	public byte[]				Orig_repo_name() {return orig_repo_name;} private byte[] orig_repo_name;
	public byte[]				Orig_ttl() {return orig_ttl;} private byte[] orig_ttl;
	public int					Orig_ext() {return orig_ext;} private int orig_ext;
	public int					Orig_w() {return orig_w;} private int orig_w = Xop_lnki_tkn.Width_null;
	public int					Orig_h() {return orig_h;} private int orig_h = Xop_lnki_tkn.Height_null;
	public byte[]				Orig_redirect() {return orig_redirect;} private byte[] orig_redirect = Bry_.Empty;
	public boolean					File_is_orig() {return file_is_orig;} private boolean file_is_orig;
	public int					File_w() {return file_w;} private int file_w;
	public int					Html_uid() {return html_uid;} private int html_uid;
	public byte					Html_elem_tid() {return html_elem_tid;} private byte html_elem_tid;
	public int					Html_w() {return html_w;} private int html_w;
	public int					Html_h() {return html_h;} private int html_h;
	public Io_url				Html_view_url() {return html_view_url;} private Io_url html_view_url;
	public Io_url				Html_orig_url() {return html_orig_url;} private Io_url html_orig_url = Io_url_.Null;
	public int					Gallery_mgr_h() {return gallery_mgr_h;} private int gallery_mgr_h = Int_.Neg1;
	public Js_img_wkr			Html_img_wkr() {return html_img_wkr;} private Js_img_wkr html_img_wkr;
	public int					Temp_file_w() {return temp_file_w;} private int temp_file_w;
	public long					Temp_file_size() {return temp_file_size;} private long temp_file_size;
	public Io_url				Temp_file_url() {return temp_file_url;} private Io_url temp_file_url;
	public boolean					Orig_exists() {return orig_exists;} public void Orig_exists_y_() {orig_exists = Bool_.Y;} public void Orig_exists_n_() {orig_exists = Bool_.N;} private boolean orig_exists;
	public boolean					File_exists() {return file_exists;} public void File_exists_y_() {file_exists = Bool_.Y;} public void File_exists_n_() {file_exists = Bool_.N;} public void File_exists_(boolean v) {file_exists = v;} private boolean file_exists;
	public boolean					File_resized() {return file_resized;} public void File_resized_y_() {file_resized = Bool_.Y;} private boolean file_resized;
	public boolean					Fsdb_insert() {return fsdb_insert;} public void Fsdb_insert_y_() {fsdb_insert = true;} private boolean fsdb_insert;
	public void	Ctor_by_lnki(byte[] lnki_ttl, byte lnki_type, int lnki_w, int lnki_h, int lnki_upright_patch, double lnki_upright, double lnki_time, int lnki_page) {
		this.lnki_type = lnki_type; this.lnki_w = lnki_w; this.lnki_h = lnki_h;
		this.lnki_upright_patch = lnki_upright_patch;
		this.lnki_upright = lnki_upright; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
		this.orig_ttl = lnki_ttl;			
		// this.file_is_orig = !(Xop_lnki_type.Id_defaults_to_thumb(lnki_type) || lnki_w != Xop_lnki_tkn.Width_null || lnki_h != Xop_lnki_tkn.Height_null); // DELETE: overriden below.
		this.Lnki_ttl_(lnki_ttl);
	}	private int lnki_upright_patch;
	public void	Ctor_by_orig(byte orig_repo_id, byte[] orig_repo_name, byte[] orig_ttl, int orig_ext, int orig_w, int orig_h, byte[] orig_redirect) {
		this.orig_repo_id = orig_repo_id; this.orig_repo_name = orig_repo_name;
		this.orig_ttl = orig_ttl; this.orig_ext = orig_ext;
		this.orig_w = orig_w; this.orig_h = orig_h; this.orig_redirect = orig_redirect;
		if (orig_ext != lnki_ext.Id())
			this.Lnki_ext_(Xof_ext_.new_by_id_(orig_ext));	// overwrite ext with whatever's in file_orig; needed for ogg -> oga / ogv
		if (Bry_.Len_gt_0(orig_redirect))					// redirect exists; EX: A.png redirected to B.png
			this.Lnki_ttl_(orig_redirect);					// update fsdb with atrs of B.png
	}
	public void			Ctor_for_html(byte exec_tid, Xof_img_size img_size, Xof_repo_itm repo, Xof_url_bldr url_bldr) {
		Calc_html_size(exec_tid, img_size);
		html_view_url = url_bldr.To_url_trg(repo, this, file_is_orig);
		html_orig_url = url_bldr.To_url_trg(repo, this, Bool_.Y);
	}
	public void			Ctor_by_fsdb_make
		( byte[] lnki_ttl, int lnki_ext, int lnki_w, int lnki_h, double lnki_time, int lnki_page
		, byte orig_repo_id, int orig_w, int orig_h, byte[] orig_redirect
		, boolean file_is_orig
		) {
		this.lnki_w = lnki_w; this.lnki_h = lnki_h; this.lnki_time = lnki_time; this.lnki_page = lnki_page;
		this.orig_repo_id = orig_repo_id; this.orig_ttl = lnki_ttl; this.orig_w = orig_w; this.orig_h = orig_h; this.orig_redirect = orig_redirect;
		this.file_is_orig = file_is_orig;
		this.html_w = lnki_w; this.html_h = lnki_h;	// set html_size as file_size (may try to optimize later by removing similar thumbs (EX: 220,221 -> 220))
		this.Lnki_ttl_(lnki_ttl);
		this.lnki_ext = Xof_ext_.new_by_id_(lnki_ext);	// NOTE: data in fsdb_make may override lnki_ext; EX: ttl=A.png; but ext=".jpg"
	}
	public void Init_temp(int temp_file_w, long temp_file_size, Io_url temp_file_url) {this.temp_file_w = temp_file_w; this.temp_file_size = temp_file_size; this.temp_file_url = temp_file_url;}
	public void Lnki_ext_(Xof_ext v) {lnki_ext = v;}
	public void Lnki_size_(int w, int h) {this.lnki_w = w; this.lnki_h = h;}
	public void Orig_repo_name_(byte[] v) {orig_repo_name = v;}
	public void Html_view_url_(Io_url v) {html_view_url = v;}
	public void Html_orig_url_(Io_url v) {html_orig_url = v;}
	public void Html_size_(int w, int h) {html_w = w; html_h = h;}
	public void Html_uid_(int v) {html_uid = v;}
	public void Html_elem_tid_(byte v) {html_elem_tid = v;}
	public void Html_img_wkr_(Js_img_wkr v) {html_img_wkr = v;}
	public void Gallery_mgr_h_(int v) {gallery_mgr_h = v;}
	private void Lnki_ttl_(byte[] v) {
		lnki_ttl = v;
		lnki_ext = Xof_ext_.new_by_ttl_(v);
		lnki_md5 = Xof_xfer_itm_.Md5_calc(v);
	}
	private void Calc_html_size(byte exec_tid, Xof_img_size img_size) {
		if (!lnki_ext.Id_is_media() && lnki_time != Xof_lnki_time.Null)	// file is not media, but has thumbtime; this check can't be moved to Lnki_time_() b/c it needs ext
			lnki_time = Xof_lnki_time.Null;								// set time to null; needed else url will reference thumbtime; PAGE:en.w:Moon; EX:[[File:Lunar libration with phase Oct 2007 450px.gif|thumbtime=0:02]]; DATE:2014-07-20
		if (lnki_ext.Id_is_audio_strict())								// audio does not have html size calculated; everything else does
			file_is_orig = Bool_.Y;
		else {
			img_size.Html_size_calc(exec_tid, lnki_w, lnki_h, lnki_type, lnki_upright_patch, lnki_upright, lnki_ext.Id(), orig_w, orig_h, Xof_img_size.Thumb_width_img);
			html_w = img_size.Html_w(); html_h = img_size.Html_h(); file_w = img_size.File_w();
			file_is_orig = img_size.File_is_orig();
		}
	}
}
