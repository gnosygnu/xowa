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
import gplx.xowa.files.gui.*; import gplx.xowa.files.repos.*;
public class Xof_xfer_itm implements Xof_file_itm {
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
	public Xof_ext				Orig_ext() {return orig_ext;} private Xof_ext orig_ext;
	public int					Orig_w() {return orig_w;} private int orig_w;
	public int					Orig_h() {return orig_h;} private int orig_h;
	public byte[]				Orig_redirect() {return orig_redirect;} private byte[] orig_redirect;
	public int					Orig_file_len() {return orig_file_len;} private int orig_file_len;
	public int					File_w() {return file_w == -1 ? html_w : file_w;} public void File_w_(int v) {file_w = v;} private int file_w = -1;	// NOTE: for itm_meta, file_w == html_w
	public int					Html_uid() {return html_uid;} private int html_uid = -1;
	public byte					Html_elem_tid() {return html_elem_tid;} private byte html_elem_tid = Xof_html_elem.Tid_none;
	public int					Html_w() {return html_w;} private int html_w;
	public int					Html_h() {return html_h;} private int html_h;
	public byte[]				Html_view_url() {return html_view_url;} private byte[] html_view_url = Bry_.Empty;
	public byte[]				Html_orig_url() {return html_orig_url;} public void Html_orig_url_(byte[] v) {this.html_orig_url = v;} private byte[] html_orig_url = Bry_.Empty;
	public int					Gallery_mgr_h() {return gallery_mgr_h;} public Xof_xfer_itm Gallery_mgr_h_(int v) {gallery_mgr_h = v; return this;} private int gallery_mgr_h = Int_.Neg1;
	public Js_img_wkr			Html_img_wkr() {return html_img_wkr;} public Xof_xfer_itm Html_img_wkr_(Js_img_wkr v) {html_img_wkr = v; return this;} private Js_img_wkr html_img_wkr;
	public boolean					Html_pass() {return html_pass;} private boolean html_pass;
	public boolean					Img_is_thumbable() {return img_is_thumbable;} private boolean img_is_thumbable;	// SEE:NOTE_1:Lnki_thumbable
	public boolean					Img_is_orig()		{return !img_is_thumbable;}
	public boolean					File_found() {return file_found;} public Xof_xfer_itm File_found_(boolean v) {file_found = v; return this;} 
	public boolean					File_exists() {return file_exists;} public void File_exists_y_() {file_exists = Bool_.Y;} public void File_exists_n_() {file_exists = Bool_.N;} private boolean file_exists;
	private boolean file_found;
	public Xof_meta_itm Meta_itm() {return meta_itm;} private Xof_meta_itm meta_itm; 
	public Xof_repo_itm Trg_repo() {return trg_repo;}
	public Xof_xfer_itm Trg_repo_(Xof_repo_itm v) {
		trg_repo = v;
		trg_repo_root = trg_repo == null ? Bry_.Empty : trg_repo.Root_http();
		return this;
	}	private Xof_repo_itm trg_repo;
	public int			Trg_repo_idx() {return trg_repo_idx;} public Xof_xfer_itm Trg_repo_idx_(int trg_repo_idx) {this.trg_repo_idx = trg_repo_idx; return this;} private int trg_repo_idx = Xof_meta_itm.Repo_unknown;
	public byte[]		Trg_repo_root() {return trg_repo_root;} private byte[] trg_repo_root = Bry_.Empty;	// HACK: needed for hdump
	private byte[]		Trg_html(byte mode_id, int width)	{return url_bldr.Init_for_trg_html(mode_id, trg_repo, lnki_ttl, lnki_md5, lnki_ext, width, lnki_time, lnki_page).Xto_bry();}
	public Io_url		Trg_file(byte mode_id, int width)	{return url_bldr.Init_for_trg_file(mode_id, trg_repo, lnki_ttl, lnki_md5, lnki_ext, width, lnki_time, lnki_page).Xto_url();}
	public byte			Lnki_exec_tid() {return lnki_exec_tid;} public void Lnki_exec_tid_(byte v) {lnki_exec_tid = v;} private byte lnki_exec_tid = Xof_exec_tid.Tid_wiki_page;
	public Xof_url_bldr Url_bldr(){ return url_bldr;}
	public Xof_xfer_itm Url_bldr_(Xof_url_bldr v) {url_bldr = v; return this;} private Xof_url_bldr url_bldr = Xof_url_bldr.Temp;
	public Xof_xfer_itm Clear() {
		lnki_type = orig_repo_id = Byte_.Max_value_127;
		lnki_w = lnki_h = file_w = orig_w = orig_h = html_w = html_h = gallery_mgr_h = Int_.Neg1;
		orig_ext = null;
		lnki_upright = Int_.Neg1; lnki_time = Xof_lnki_time.Null; lnki_page = Xof_lnki_page.Null;
		file_found = file_exists = img_is_thumbable = false;
		orig_file_len = 0;	// NOTE: cannot be -1, or else will always download orig; see ext rule chk and (orig_file_len < 0)
		orig_repo_name = orig_ttl = orig_redirect = null; lnki_ttl = null; lnki_md5 = null; lnki_ext = null;
		html_orig_url = html_view_url = Bry_.Empty;
		trg_repo_idx = Int_.Neg1; meta_itm = null;
		html_uid = Int_.Neg1; html_elem_tid = Xof_html_elem.Tid_none;
		return this;
	}
	public Xof_xfer_itm Clone() {
		Xof_xfer_itm rv = new Xof_xfer_itm();
		rv.lnki_type = lnki_type; rv.lnki_w = lnki_w; rv.lnki_h = lnki_h; rv.lnki_upright = lnki_upright; rv.lnki_time = lnki_time; rv.lnki_page = lnki_page;
		rv.img_is_thumbable = img_is_thumbable;
		rv.orig_repo_id = orig_repo_id; rv.orig_repo_name = orig_repo_name; rv.orig_ttl = orig_ttl; rv.orig_ext = orig_ext; rv.orig_w = orig_w; rv.orig_h = orig_h; rv.orig_redirect = orig_redirect; 
		rv.orig_file_len = orig_file_len;
		rv.lnki_ttl = lnki_ttl; rv.lnki_md5 = lnki_md5; rv.lnki_ext = lnki_ext;
		rv.html_w = html_w; rv.html_h = html_h; rv.html_view_url = html_view_url; rv.html_orig_url = html_orig_url;
		rv.file_w = file_w;
		rv.trg_repo_idx = trg_repo_idx;
		rv.trg_repo_root = trg_repo_root;
		rv.meta_itm = meta_itm;	 // NOTE: shared reference
		rv.html_uid = html_uid; rv.html_elem_tid = html_elem_tid;
		rv.gallery_mgr_h = gallery_mgr_h;
		rv.file_exists = file_exists;
		return rv;
	}
	public Xof_xfer_itm Init_by_lnki(byte[] ttl, byte[] redirect, byte lnki_type, int w, int h, double upright, double time, int page) {
		this.Set__ttl(ttl, redirect);
		this.lnki_type = lnki_type; this.lnki_w = w; this.lnki_h = h; this.lnki_upright = upright; this.lnki_time = time; this.lnki_page = page;
		img_is_thumbable = Xof_xfer_itm_.Lnki_thumbable_calc(lnki_type, lnki_w, lnki_h);
		if (lnki_time != Xof_lnki_time.Null && !lnki_ext.Id_is_media())	// thumbtime is set, but ext is not media; PAGE:en.w:Moon; EX:[[File:A.png|thumbtime=0:02]] DATE:2014-07-22
			lnki_time = Xof_lnki_time.Null;								// disable thumbtime
		return this;
	}
	public void Init_by_orig_old(int w, int h, int orig_file_len) {
		this.orig_w = w; this.orig_h = h; this.orig_file_len = orig_file_len;
	}
	public void Init_by_orig(byte orig_repo_id, byte[] orig_repo_name, byte[] orig_ttl, Xof_ext orig_ext, int orig_w, int orig_h, byte[] orig_redirect, int orig_file_len) {
		this.orig_repo_id = orig_repo_id; this.orig_repo_name = orig_repo_name;
		this.orig_ttl = orig_ttl; this.orig_ext = orig_ext;
		this.orig_w = orig_w; this.orig_h = orig_h; this.orig_redirect = orig_redirect;
		if (orig_ext.Id() != lnki_ext.Id())
			this.Lnki_ext_(orig_ext);						// overwrite ext with whatever's in file_orig; needed for ogg -> oga / ogv
		if		(Bry_.Len_gt_0(orig_redirect))				// redirect exists; EX: A.png redirected to B.png
			this.Lnki_ttl_(orig_redirect);					// update fsdb with atrs of B.png
		else if	(!Bry_.Eq(lnki_ttl, orig_ttl))				// ttls differ; EX: "A_.png" vs "A.png"
			this.Lnki_ttl_(orig_ttl);
		this.orig_file_len = orig_file_len;
	}
	private void Lnki_ttl_(byte[] v) {
		lnki_ttl = v;
		lnki_ext = Xof_ext_.new_by_ttl_(v);
		lnki_md5 = Xof_xfer_itm_.Md5_calc(v);
	}
	public void Init_for_gallery(int html_w, int html_h, int file_w) {
		this.html_w = html_w; this.html_h = html_h; 
		this.file_w = file_w;
	}
	public void Init_for_gallery_update(int html_w, int html_h, String view_src, String orig_src) {
		this.html_w = html_w; this.html_h = html_h; 
		this.html_view_url = Bry_.new_utf8_(view_src);
		this.html_orig_url = Bry_.new_utf8_(orig_src);
		this.html_pass = true;
		this.file_found = true;
	}
	public void	Init_for_test__img(int html_w, int html_h, byte[] html_view_url, byte[] html_orig_url) {this.html_w = html_w; this.html_h = html_h; this.html_view_url = html_view_url; this.html_orig_url = html_orig_url;}
	public Xof_xfer_itm Set__ttl(byte[] ttl, byte[] redirect) {
		this.orig_redirect = redirect;
		this.lnki_ttl = orig_redirect == Xop_redirect_mgr.Redirect_null_bry ? Bry_.Copy(ttl) : orig_redirect;
		this.lnki_ttl = Xof_xfer_itm_.Md5_decoder.Decode_lax(Xof_xfer_itm_.Ttl_standardize(lnki_ttl));	// NOTE: this line is repeated in static method below
		this.lnki_md5 = Xof_xfer_itm_.Md5_calc(lnki_ttl);	// NOTE: md5 is calculated off of url_decoded ttl; EX: A%2Cb is converted to A,b and then md5'd. note that A%2Cb still remains the title
		this.lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl);
		return this;
	}
	public void			Set__html_size(int html_w, int html_h)			{this.html_w = html_w; this.html_h = html_h; }
	public Xof_xfer_itm Set__html_uid_tid(int uid, byte tid)			{html_uid = uid; html_elem_tid = tid; return this;} 
	public void			Set__meta(Xof_meta_itm meta_itm, Xof_repo_itm trg_repo, int thumb_w_img) {
		this.meta_itm = meta_itm; Trg_repo_(trg_repo); this.thumb_w_img = thumb_w_img;
		this.orig_w = meta_itm.Orig_w(); this.orig_h = meta_itm.Orig_h();		// orig_w / orig_h needed for imap; DATE:2014-08-08
	}	private int thumb_w_img;
	public void			Set__meta_only(Xof_meta_itm meta_itm) {this.meta_itm = meta_itm; Set__ttl(meta_itm.Ttl(), meta_itm.Ptr_ttl());}
	public void			Calc_by_fsdb(int html_w, int html_h, Io_url view_url, Io_url orig_url) {
		html_pass = true;
		this.html_w = html_w;
		this.html_h = html_h;
		this.html_orig_url = Bry_.new_utf8_(orig_url.To_http_file_str());
		this.html_view_url = Bry_.new_utf8_(view_url.To_http_file_str());
	}
	public void Lnki_ext_(Xof_ext v) {lnki_ext = v;}
	public Xof_xfer_itm Html_elem_tid_(byte v) {html_elem_tid = v; return this;}
	public boolean			Calc_by_meta() {return Calc_by_meta(false);}
	public boolean			Calc_by_meta(boolean caller_is_file_page) {
		html_pass = false;
		html_orig_url = html_view_url = Bry_.Empty;
		html_w = lnki_w; html_h = lnki_h;
		if (meta_itm == null || trg_repo == null) return false;
		if (meta_itm.Ptr_ttl_exists()) {
			lnki_ttl = meta_itm.Ptr_ttl();
			lnki_md5 = Xof_xfer_itm_.Md5_(lnki_ttl);
		}
		boolean limit_size = !lnki_ext.Id_is_svg() || (lnki_ext.Id_is_svg() && caller_is_file_page);
		if (lnki_ext.Id_is_media() && html_w < 1)		// if media and no width, set to default; NOTE: must be set or else dynamic download will resize play button to small size; DATE:20121227
			html_w = Xof_img_size.Thumb_width_ogv;	
		if (img_is_thumbable) {				// file is thumb
			if (lnki_ext.Id_is_video()) {		// video is a special case; src is thumb_w but html_w / html_h is based on calc
				html_orig_url = Trg_html(Xof_repo_itm.Mode_orig, Xof_img_size.Size_null_deprecated);
				if (meta_itm.Thumbs_indicates_oga() && lnki_ext.Id_is_ogv()) {lnki_ext = Xof_ext_.new_by_ext_(Xof_ext_.Bry_oga); return true;}	// if audio, do not thumb; NOTE: must happen after html_orig_url, b/c html must still be generated to auto-download files; NOTE: must change ext to oga b/c ogg may trigger video code elsewhere
				Xof_meta_thumb thumb = meta_itm.Thumbs_get_vid(Xof_lnki_time.X_int(lnki_time));
				if (thumb != null) {
					Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, thumb.Width(), thumb.Height(), html_w, html_h, img_is_thumbable, lnki_upright);
					html_w = calc_size.Val_0(); html_h = calc_size.Val_1(); 
					html_view_url = Trg_html(Xof_repo_itm.Mode_thumb, thumb.Width());	// NOTE: must pass thumb.Width() not html_w b/c only one thumb generated for a video file
					html_pass = true;
					return true;
				}
			}
			else {							// regular thumb
				html_orig_url = Trg_html(Xof_repo_itm.Mode_orig, Xof_img_size.Size_null_deprecated);
				if (lnki_ext.Id_is_audio()) return true;	// if audio, do not thumb; even if user requests thumb;
				Xof_meta_thumb[] thumbs = meta_itm.Thumbs(); int thumbs_len = thumbs.length; Xof_meta_thumb thumb = null;
				if (lnki_h > 0 && orig_w < 1 && thumbs_len > 0) {		// if height is specified and no orig, then iterate over thumbs to find similar height; NOTE: this is a fallback case; orig_w/h is optimal; EX: c:Jacques-Louis David and <gallery>
					Xof_meta_thumb largest = meta_itm.Thumbs_get_largest(thumbs_len);	// get largest thumb
					Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, largest.Width(), largest.Height(), html_w, html_h, img_is_thumbable, lnki_upright, false); // use largest to calc correct width/height; note that this is needed for gallery which passes in 120,120; EX:c:Yellowstone Park
					int comp_height = calc_size.Val_1();
					for (int i = 0; i < thumbs_len; i++) {
						Xof_meta_thumb tmp_thumb = thumbs[i];
						if (Int_.Between(tmp_thumb.Height(), comp_height - 1, comp_height + 1)) {
							thumb = tmp_thumb;
							break;
						}
					}
					if (thumb != null) return Calc_by_meta_found(lnki_type, thumb.Width(), thumb.Height());	// thumb found
				}
				
				Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, meta_itm.Orig_w(), meta_itm.Orig_h(), html_w, html_h, img_is_thumbable, lnki_upright, limit_size); // calc html_h and html_w; can differ from lnki_w, lnki_h; note that -1 width is handled by thumb_w_img
				html_w = calc_size.Val_0();
				if (html_h != -1) html_h = calc_size.Val_1(); 	// NOTE: if -1 (no height specified) do not set height; EX:Tokage_2011-07-15.jpg; DATE:2013-06-03
				html_view_url = Trg_html(Xof_repo_itm.Mode_thumb, this.File_w());
				thumb = meta_itm.Thumbs_get_img(html_w, 0);
				if (thumb == null) {						// exact thumb not found
					if (html_w == meta_itm.Orig_w()			// html_w matches orig_w; occurs when thumb,upright requested, but upright size is larger than orig; PAGE:en.w:St. Petersburg
						&& !lnki_ext.Id_needs_convert()		// but ext cannot be something that needs conversion; EX: 120,90 svg may match thumb of 120,90, but .png still needs to be generated
						&& meta_itm.Orig_exists() == Xof_meta_itm.Exists_y
						) {	
						html_h = meta_itm.Orig_h();
						html_view_url = Trg_html(Xof_repo_itm.Mode_orig, -1);
						html_pass = true;
						return true;
					}
					if (lnki_ext.Id_is_djvu()) {			// NOTE: exact djvu w thumbs are not on server; always seems to be 1 off; EX: 90 requested, but 90 doesn't exist; 89 does
						thumb = meta_itm.Thumbs_get_img(html_w, 1);
						if (thumb != null) return Calc_by_meta_found(lnki_type, thumb.Width(), thumb.Height());	// thumb found
					}
				}
				else {
					html_h = thumb.Height();
					html_pass = true;
					return true;
				}
			}
		}
		else {								// file is orig
			byte mode_id = lnki_ext.Id_is_svg() ? Xof_repo_itm.Mode_thumb : Xof_repo_itm.Mode_orig;	// svgs will always return thumb; EX:[[A.svg]] -> A.svg.png
			html_view_url = html_orig_url = Trg_html(mode_id, this.File_w());
			if (meta_itm.Thumbs_indicates_oga() && lnki_ext.Id_is_ogv()) {lnki_ext = Xof_ext_.new_by_ext_(Xof_ext_.Bry_oga); return true;}	// if audio, do not thumb; NOTE: must happen after html_orig_url, b/c html must still be generated to auto-download files; NOTE: must change ext to oga b/c ogg may trigger video code elsewhere
			if		(lnki_ext.Id_is_audio()) return true;	// if audio, return true; SEE:NOTE_2
			else if (lnki_ext.Id_is_video()) {
				Xof_meta_thumb thumb = meta_itm.Thumbs_get_vid(Xof_lnki_time.X_int(lnki_time));	// get thumb at lnki_time; NOTE: in most cases this will just be the 1st thumb; note that orig video files don't have an official thumb
				if (thumb != null) {
					html_w = thumb.Width(); html_h = thumb.Height();	// NOTE: take thumb_size; do not rescale to html_w, html_h b/c html_w will default to 220; native width of thumbnail should be used; DATE:2013-04-11
					html_view_url = Trg_html(Xof_repo_itm.Mode_thumb, thumb.Width());	// NOTE: must pass thumb.Width() not html_w b/c only one thumb generated for a video file
					html_pass = true;
					return true;
				}
			}
			if (meta_itm.Orig_exists() == Xof_meta_itm.Exists_y) {	// file found previously >>> gen html
				html_w = meta_itm.Orig_w(); html_h = meta_itm.Orig_h();
				html_view_url = Trg_html(mode_id, this.File_w());
				html_pass = true;
				return true;
			}
		}
		// file not found >>> set size to 0 and byte[] to empty
		html_w = lnki_w < 0 ? 0 : lnki_w;
		html_h = lnki_h < 0 ? 0 : lnki_h;
		return false;
	}	private Int_2_ref calc_size = new Int_2_ref();
	private boolean		Calc_by_meta_found(byte lnki_type, int model_w, int model_h) {
		Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, model_w, model_h, html_w, html_h, img_is_thumbable, lnki_upright, false);	// recalc html_w, html_h; note that false passed b/c truncation is not needed
		html_w = calc_size.Val_0(); html_h = calc_size.Val_1();
		html_view_url = Trg_html(Xof_repo_itm.Mode_thumb, model_w);	// note that thumb.Width is used (the actual file width), not html_w
		html_pass = true;
		return true;
	}
}
/*
NOTE_1:Lnki_thumbable
. false only if following form
[[A.png]]		-> must get orig
. true in almost all other cases, especially if (a) type is thumb; (b) size exists; (c) upright;
. basically, indicates that image will be stored on wmf server as "/thumb/" url
[[A.png|thumb]] -> default to 220 and check for 220px
[[A.png|40px]]  -> check for 40px
[[A.png|x40px]] -> calc n width and check for npx

NOTE_2:return true if media
. this seems hackish, but if Atrs_calc_for_html returns false, then file is generally added to the queue
. the problem is that media/audio is usually not found
.. so, for example, when a wiki page does pronunciation [[File:A.oga]], A.oga will return false (since it's not there)
.. however, unlike images which xowa will try to fetch the thumb, xowa will never fetch audios (since audios are not "visible", most will not be played, and some audios are big)
... the corollary is that audios are only fetched when the play buton is clicked (and via code in Xog_win_itm)
.. so, if false is returned, then A.oga gets added to the queue, only to be ignored by queue rules later
... the problem here is that the status bar will flash "downloading: File:A.oga" which is misleading
. so, return true so that it is never added to the queue. this depends on the "click" of the play button code to actually download the file
. note that video doesn't suffer from this issue, b/c video has thumbs which can or can not be found
*/