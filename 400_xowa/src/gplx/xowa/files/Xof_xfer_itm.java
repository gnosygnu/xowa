/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
import gplx.xowa.guis.cbks.js.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.wikis.tdbs.metas.*;
import gplx.xowa.parsers.utils.*;
public class Xof_xfer_itm implements Xof_file_itm {
	private Xof_url_bldr tmp_url_bldr = dflt_url_bldr;
	public Xof_xfer_itm() {
		lnki_type = orig_repo_id = Byte_.Max_value_127;
		lnki_w = lnki_h = file_w = orig_w = orig_h = html_w = html_h = html_gallery_mgr_h = Int_.Neg1;
		orig_ext = null;
		lnki_upright = Int_.Neg1; lnki_time = Xof_lnki_time.Null; lnki_page = Xof_lnki_page.Null;
		file_exists = false; file_is_orig = true;
		orig_file_len = 0;	// NOTE: cannot be -1, or else will always download orig; see ext rule chk and (orig_file_len < 0)
		orig_repo_name = orig_ttl = orig_redirect = null; lnki_ttl = null; orig_ttl_md5 = null;
		html_orig_url = html_view_url = Io_url_.Empty;
		meta_itm = null;
		html_uid = Int_.Neg1; html_elem_tid = Xof_html_elem.Tid_none;
	}
	public int					Lnki_exec_tid()				{return lnki_exec_tid;} private int lnki_exec_tid;
	public byte[]				Lnki_wiki_abrv()			{return lnki_wiki_abrv;} private byte[] lnki_wiki_abrv;
	public byte[]				Lnki_ttl()					{return lnki_ttl;} private byte[] lnki_ttl;
	public byte					Lnki_type()					{return lnki_type;} private byte lnki_type;
	public double				Lnki_upright()				{return lnki_upright;} private double lnki_upright;
	public int					Lnki_w()					{return lnki_w;} private int lnki_w;
	public int					Lnki_h()					{return lnki_h;} private int lnki_h;
	public double				Lnki_time()					{return lnki_time;} private double lnki_time;
	public int					Lnki_page()					{return lnki_page;} private int lnki_page;
	public boolean				Orig_exists()				{return orig_exists;} private boolean orig_exists;
	public byte					Orig_repo_id()				{return orig_repo_id;} private byte orig_repo_id;
	public byte[]				Orig_repo_name()			{return orig_repo_name;} private byte[] orig_repo_name;
	public byte[]				Orig_ttl()					{return orig_ttl;} private byte[] orig_ttl;
	public byte[]				Orig_ttl_md5()				{return orig_ttl_md5;} private byte[] orig_ttl_md5;
	public Xof_ext				Orig_ext()					{return orig_ext;} private Xof_ext orig_ext;
	public int					Orig_w()					{return orig_w;} private int orig_w;
	public int					Orig_h()					{return orig_h;} private int orig_h;
	public byte[]				Orig_redirect()				{return orig_redirect;} private byte[] orig_redirect;
	public long					Orig_file_len()				{return orig_file_len;} private long orig_file_len;	// used for filtering downloads by file_max
	public boolean				File_is_orig()				{return file_is_orig;} private boolean file_is_orig; // SEE:NOTE_1:Lnki_thumbable
	public int					File_w()					{return file_w == -1 ? html_w : file_w;} private int file_w = -1;	// NOTE: for itm_meta, file_w == html_w
	public int					Html_uid()					{return html_uid;} private int html_uid;
	public byte					Html_elem_tid()				{return html_elem_tid;} private byte html_elem_tid;
	public int					Html_w()					{return html_w;} private int html_w;
	public int					Html_h()					{return html_h;} private int html_h;
	public Io_url				Html_view_url()				{return html_view_url;} private Io_url html_view_url = Io_url_.Empty;	// needed else null_err
	public Io_url				Html_orig_url()				{return html_orig_url;} private Io_url html_orig_url = Io_url_.Empty;	// needed else null_err
	public int					Html_gallery_mgr_h()		{return html_gallery_mgr_h;} private int html_gallery_mgr_h;
	public Js_img_wkr			Html_img_wkr()				{return html_img_wkr;} private Js_img_wkr html_img_wkr;
	public boolean				File_exists()				{return file_exists;} private boolean file_exists;
	public boolean   			File_exists_in_cache()		{return false;}
	public boolean				Dbmeta_is_new()				{return meta_itm.State_new();}
	public void					Html_elem_tid_(byte v)		{html_elem_tid = v;}
	public void					Html_size_(int w, int h)	{this.html_w = w; this.html_h = h;}
	public void					Html_gallery_mgr_h_(int v)	{html_gallery_mgr_h = v;} 
	public void					Html_img_wkr_(Js_img_wkr v)	{html_img_wkr = v;}
	public int					Hdump_mode()				{return hdump_mode;} private int hdump_mode = Xof_fsdb_itm.Hdump_mode__null;

	public void File_exists_y_() {file_exists = Bool_.Y;} public void File_exists_n_() {file_exists = Bool_.N;} public void File_exists_(boolean v) {file_exists = v;}
	public void Html_orig_url_(Io_url v) {html_orig_url = v;}
	public void Init_at_lnki(int exec_tid, byte[] wiki_abrv, byte[] ttl, byte lnki_type, double upright, int w, int h, double time, int page, int lnki_upright_patch) {
		this.lnki_exec_tid = exec_tid; this.lnki_wiki_abrv = wiki_abrv;
		this.lnki_type = lnki_type; this.lnki_upright = upright; this.lnki_w = w; this.lnki_h = h; this.lnki_time = time; this.lnki_page = page;
		this.file_is_orig = !Xof_xfer_itm_.Lnki_thumbable_calc(lnki_type, lnki_w, lnki_h);
		this.lnki_ttl = Xof_file_wkr_.Md5_decoder.Decode(Xof_file_wkr_.Ttl_standardize(ttl));
		this.Orig_ttl_(ttl);
		this.orig_ext = Xof_ext_.new_by_ttl_(ttl);
		if (lnki_time != Xof_lnki_time.Null && !orig_ext.Id_is_media())	// thumbtime is set, but ext is not media; PAGE:en.w:Moon; EX:[[File:A.png|thumbtime=0:02]] DATE:2014-07-22
			lnki_time = Xof_lnki_time.Null;								// disable thumbtime
	}
	public void Init_at_orig(byte orig_repo_id, byte[] orig_repo_name, byte[] orig_ttl, Xof_ext orig_ext, int orig_w, int orig_h, byte[] orig_redirect) {
		this.orig_repo_id = orig_repo_id; this.orig_repo_name = orig_repo_name;
		this.orig_ttl = orig_ttl; this.orig_ttl_md5 = Xof_file_wkr_.Md5(orig_ttl);
		this.orig_w = orig_w; this.orig_h = orig_h; this.orig_redirect = orig_redirect;
		if		(Bry_.Len_gt_0(orig_redirect))				// redirect exists; EX: A.png redirected to B.png
			this.Orig_ttl_(orig_redirect);					// update fsdb with atrs of B.png
		else if	(!Bry_.Eq(lnki_ttl, orig_ttl))				// ttls differ; EX: "A_.png" vs "A.png"
			this.Orig_ttl_(orig_ttl);
		else
			this.Orig_ttl_(orig_ttl);
		this.orig_ext = orig_ext;							// overwrite ext with whatever's in file_orig; needed for ogg -> oga / ogv
		this.orig_exists = true;
	}
	public void Init_at_gallery_bgn(int html_w, int html_h, int file_w) {
		this.html_w = html_w; this.html_h = html_h; 
		this.file_w = file_w;
	}
	public void Init_at_gallery_end(int html_w, int html_h, Io_url html_view_url, Io_url html_orig_url) {
		this.html_w = html_w; this.html_h = html_h; 
		this.html_view_url = html_view_url;
		this.html_orig_url = html_orig_url;
		this.file_exists = true;
	}
	public void			Calc_by_fsdb(int html_w, int html_h, Io_url view_url, Io_url orig_url) {
		this.html_w = html_w;
		this.html_h = html_h;
		this.html_orig_url = orig_url;
		this.html_view_url = view_url;
	}
	public void Orig_ttl_and_redirect_(byte[] ttl, byte[] redirect) {
		this.orig_redirect = redirect;
		this.lnki_ttl = orig_redirect == Xop_redirect_mgr.Redirect_null_bry ? Bry_.Copy(ttl) : orig_redirect;
		this.lnki_ttl = Xof_file_wkr_.Md5_decoder.Decode(Xof_file_wkr_.Ttl_standardize(lnki_ttl));	// NOTE: this line is repeated in static method below
		this.orig_ttl = lnki_ttl;
		this.orig_ttl_md5 = Xof_file_wkr_.Md5_fast(lnki_ttl);	// NOTE: md5 is calculated off of url_decoded ttl; EX: A%2Cb is converted to A,b and then md5'd. note that A%2Cb still remains the title
		this.orig_ext = Xof_ext_.new_by_ttl_(lnki_ttl);
	}
	private void Orig_ttl_(byte[] v) {
		this.orig_ttl = Xof_file_wkr_.Ttl_standardize(v);
		this.orig_ttl_md5 = Xof_file_wkr_.Md5_fast(v);
	}

	public Xof_meta_itm			Dbmeta_itm() {return meta_itm;} private Xof_meta_itm meta_itm;
	public void					Trg_repo_itm_(Xof_repo_itm v) {
		trg_repo_itm = v;
		trg_repo_root = trg_repo_itm == null ? Bry_.Empty : trg_repo_itm.Root_http();
	} private Xof_repo_itm trg_repo_itm;
	public byte[]		Trg_repo_root() {return trg_repo_root;} private byte[] trg_repo_root = Bry_.Empty;	// HACK: needed for hdump
	public void			Ctor_for_html(int exec_tid, int lnki_upright_patch, Xof_img_size img_size, Xof_repo_itm repo, Xof_url_bldr url_bldr) {
		Calc_html_size(exec_tid, lnki_upright_patch, img_size);
		this.html_view_url = url_bldr.To_url_trg(repo, this, file_is_orig);
		this.html_orig_url = url_bldr.To_url_trg(repo, this, Bool_.Y);
	}
	private void Calc_html_size(int exec_tid, int lnki_upright_patch, Xof_img_size img_size) {
		if (!orig_ext.Id_is_media() && lnki_time != Xof_lnki_time.Null)	// file is not media, but has thumbtime; this check can't be moved to Lnki_time_() b/c it needs ext
			lnki_time = Xof_lnki_time.Null;								// set time to null; needed else url will reference thumbtime; PAGE:en.w:Moon; EX:[[File:Lunar libration with phase Oct 2007 450px.gif|thumbtime=0:02]]; DATE:2014-07-20
		if (orig_ext.Id_is_audio_strict())								// audio does not have html size calculated; everything else does
			this.file_is_orig = Bool_.Y;
		else {
			img_size.Html_size_calc(exec_tid, lnki_w, lnki_h, lnki_type, lnki_upright_patch, lnki_upright, orig_ext.Id(), orig_w, orig_h, Xof_img_size.Thumb_width_img);
			html_w = img_size.Html_w(); html_h = img_size.Html_h(); file_w = img_size.File_w();
			this.file_is_orig = img_size.File_is_orig();
		}
	}
	public void Init_by_orig_old(int w, int h, int orig_file_len) {
		this.orig_w = w; this.orig_h = h; this.orig_file_len = orig_file_len;
	}
	public void Orig_repo_id_(int v) {this.orig_repo_id = (byte)v;} 
	public void File_w_(int v) {file_w = v;}
	public void	Init_for_test__img(int html_w, int html_h, Io_url html_view_url, Io_url html_orig_url) {
		this.html_w = html_w; this.html_h = html_h; this.html_view_url = html_view_url; this.html_orig_url = html_orig_url;
	}
	public void Init_at_hdoc(int html_uid, byte html_elem_tid) {
		this.html_uid = html_uid; this.html_elem_tid = html_elem_tid;
	}

	public void			Set__meta(Xof_meta_itm meta_itm, Xof_repo_itm trg_repo_itm, int thumb_w_img) {
		this.meta_itm = meta_itm; Trg_repo_itm_(trg_repo_itm); this.thumb_w_img = thumb_w_img;
		this.orig_w = meta_itm.Orig_w(); this.orig_h = meta_itm.Orig_h();		// orig_w / orig_h needed for imap; DATE:2014-08-08
	}	private int thumb_w_img;
	public void			Set__meta_only(Xof_meta_itm meta_itm) {this.meta_itm = meta_itm; Orig_ttl_and_redirect_(meta_itm.Ttl(), meta_itm.Ptr_ttl());}
	public void	Init_at_html(int exec_tid, Xof_img_size img_size, Xof_repo_itm repo, Xof_url_bldr url_bldr) {
		Calc_html_size(exec_tid, img_size);
		this.html_view_url = url_bldr.To_url_trg(repo, this, file_is_orig);
		this.html_orig_url = url_bldr.To_url_trg(repo, this, Bool_.Y);
	}
	private void Calc_html_size(int exec_tid, Xof_img_size img_size) {
		if (!orig_ext.Id_is_media() && lnki_time != Xof_lnki_time.Null)	// file is not media, but has thumbtime; this check can't be moved to Lnki_time_() b/c it needs ext
			lnki_time = Xof_lnki_time.Null;								// set time to null; needed else url will reference thumbtime; PAGE:en.w:Moon; EX:[[File:Lunar libration with phase Oct 2007 450px.gif|thumbtime=0:02]]; DATE:2014-07-20
		if (orig_ext.Id_is_audio_strict())								// audio does not have html size calculated; everything else does
			file_is_orig = Bool_.Y;
		else {
			img_size.Html_size_calc(exec_tid, lnki_w, lnki_h, lnki_type, Xof_patch_upright_tid_.Tid_all, lnki_upright, orig_ext.Id(), orig_w, orig_h, Xof_img_size.Thumb_width_img);
			html_w = img_size.Html_w(); html_h = img_size.Html_h();
			file_w = img_size.File_w();
			file_is_orig = img_size.File_is_orig();
		}
	}
	private Io_url		Trg_view_url(byte mode_id, int width)	{return tmp_url_bldr.Init_for_trg_file(trg_repo_itm, mode_id, lnki_ttl, orig_ttl_md5, orig_ext, width, lnki_time, lnki_page).Xto_url();}
	public Io_url		Trg_orig_url(byte mode_id, int width)	{return tmp_url_bldr.Init_for_trg_file(trg_repo_itm, mode_id, lnki_ttl, orig_ttl_md5, orig_ext, width, lnki_time, lnki_page).Xto_url();}
	public boolean			Calc_by_meta() {return Calc_by_meta(false);}
	public boolean			Calc_by_meta(boolean caller_is_file_page) {
		file_exists = false;
		html_orig_url = html_view_url = Io_url_.Empty;
		html_w = lnki_w; html_h = lnki_h;
		if (meta_itm == null || trg_repo_itm == null) return false;
		if (meta_itm.Ptr_ttl_exists()) {
			lnki_ttl = meta_itm.Ptr_ttl();
			orig_ttl_md5 = Xof_file_wkr_.Md5(lnki_ttl);
		}
		boolean limit_size = !orig_ext.Id_is_svg() || (orig_ext.Id_is_svg() && caller_is_file_page);
		if (orig_ext.Id_is_media() && html_w < 1)		// if media and no width, set to default; NOTE: must be set or else dynamic download will resize play button to small size; DATE:20121227
			html_w = Xof_img_size.Thumb_width_ogv;	
		if (!file_is_orig) {				// file is thumb
			if (orig_ext.Id_is_video()) {		// video is a special case; src is thumb_w but html_w / html_h is based on calc
				html_orig_url = Trg_view_url(Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
				if (meta_itm.Thumbs_indicates_oga() && orig_ext.Id_is_ogv()) {orig_ext = Xof_ext_.new_by_ext_(Xof_ext_.Bry_oga); return true;}	// if audio, do not thumb; NOTE: must happen after html_orig_bry, b/c html must still be generated to auto-download files; NOTE: must change ext to oga b/c ogg may trigger video code elsewhere
				Xof_meta_thumb thumb = meta_itm.Thumbs_get_vid(Xof_lnki_time.X_int(lnki_time));
				if (thumb != null) {
					Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, thumb.Width(), thumb.Height(), html_w, html_h, !file_is_orig, lnki_upright);
					html_w = calc_size.Val_0(); html_h = calc_size.Val_1(); 
					html_view_url = Trg_view_url(Xof_img_mode_.Tid__thumb, thumb.Width());	// NOTE: must pass thumb.Width() not html_w b/c only one thumb generated for a video file
					file_exists = true;
					return true;
				}
			}
			else {							// regular thumb
				html_orig_url = Trg_view_url(Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
				if (orig_ext.Id_is_audio()) return true;	// if audio, do not thumb; even if user requests thumb;
				Xof_meta_thumb[] thumbs = meta_itm.Thumbs(); int thumbs_len = thumbs.length; Xof_meta_thumb thumb = null;
				if (lnki_h > 0 && orig_w < 1 && thumbs_len > 0) {		// if height is specified and no orig, then iterate over thumbs to find similar height; NOTE: this is a fallback case; orig_w/h is optimal; EX: c:Jacques-Louis David and <gallery>
					Xof_meta_thumb largest = meta_itm.Thumbs_get_largest(thumbs_len);	// get largest thumb
					Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, largest.Width(), largest.Height(), html_w, html_h, !file_is_orig, lnki_upright, false); // use largest to calc correct width/height; note that this is needed for gallery which passes in 120,120; EX:c:Yellowstone Park
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
				
				Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, meta_itm.Orig_w(), meta_itm.Orig_h(), html_w, html_h, !file_is_orig, lnki_upright, limit_size); // calc html_h and html_w; can differ from lnki_w, lnki_h; note that -1 width is handled by thumb_w_img
				html_w = calc_size.Val_0();
				if (html_h != -1) html_h = calc_size.Val_1(); 	// NOTE: if -1 (no height specified) do not set height; EX:Tokage_2011-07-15.jpg; DATE:2013-06-03
				html_view_url = Trg_view_url(Xof_img_mode_.Tid__thumb, this.File_w());
				thumb = meta_itm.Thumbs_get_img(html_w, 0);
				if (thumb == null) {						// exact thumb not found
					if (html_w == meta_itm.Orig_w()			// html_w matches orig_w; occurs when thumb,upright requested, but upright size is larger than orig; PAGE:en.w:St. Petersburg
						&& !orig_ext.Id_needs_convert()		// but ext cannot be something that needs conversion; EX: 120,90 svg may match thumb of 120,90, but .png still needs to be generated
						&& meta_itm.Orig_exists() == Xof_meta_itm.Exists_y
						) {	
						html_h = meta_itm.Orig_h();
						html_view_url = Trg_view_url(Xof_img_mode_.Tid__orig, -1);
						file_exists = true;
						return true;
					}
					if (orig_ext.Id_is_djvu()) {			// NOTE: exact djvu w thumbs are not on server; always seems to be 1 off; EX: 90 requested, but 90 doesn't exist; 89 does
						thumb = meta_itm.Thumbs_get_img(html_w, 1);
						if (thumb != null) return Calc_by_meta_found(lnki_type, thumb.Width(), thumb.Height());	// thumb found
					}
				}
				else {
					html_h = thumb.Height();
					file_exists = true;
					return true;
				}
			}
		}
		else {								// file is orig
			byte mode_id = orig_ext.Id_is_svg() ? Xof_img_mode_.Tid__thumb : Xof_img_mode_.Tid__orig;	// svgs will always return thumb; EX:[[A.svg]] -> A.svg.png
			html_view_url = html_orig_url = Trg_view_url(mode_id, this.File_w());
			if (meta_itm.Thumbs_indicates_oga() && orig_ext.Id_is_ogv()) {orig_ext = Xof_ext_.new_by_ext_(Xof_ext_.Bry_oga); return true;}	// if audio, do not thumb; NOTE: must happen after html_orig_bry, b/c html must still be generated to auto-download files; NOTE: must change ext to oga b/c ogg may trigger video code elsewhere
			if		(orig_ext.Id_is_audio()) return true;	// if audio, return true; SEE:NOTE_2
			else if (orig_ext.Id_is_video()) {
				Xof_meta_thumb thumb = meta_itm.Thumbs_get_vid(Xof_lnki_time.X_int(lnki_time));	// get thumb at lnki_time; NOTE: in most cases this will just be the 1st thumb; note that orig video files don't have an official thumb
				if (thumb != null) {
					html_w = thumb.Width(); html_h = thumb.Height();	// NOTE: take thumb_size; do not rescale to html_w, html_h b/c html_w will default to 220; native width of thumbnail should be used; DATE:2013-04-11
					html_view_url = Trg_view_url(Xof_img_mode_.Tid__thumb, thumb.Width());	// NOTE: must pass thumb.Width() not html_w b/c only one thumb generated for a video file
					file_exists = true;
					return true;
				}
			}
			if (meta_itm.Orig_exists() == Xof_meta_itm.Exists_y) {	// file found previously >>> gen html
				html_w = meta_itm.Orig_w(); html_h = meta_itm.Orig_h();
				html_view_url = Trg_view_url(mode_id, this.File_w());
				file_exists = true;
				return true;
			}
		}
		// file not found >>> set size to 0 and byte[] to empty
		html_w = lnki_w < 0 ? 0 : lnki_w;
		html_h = lnki_h < 0 ? 0 : lnki_h;
		return false;
	}	private Int_2_ref calc_size = new Int_2_ref();
	private boolean		Calc_by_meta_found(byte lnki_type, int model_w, int model_h) {
		Xof_xfer_itm_.Calc_xfer_size(calc_size, lnki_type, thumb_w_img, model_w, model_h, html_w, html_h, !file_is_orig, lnki_upright, false);	// recalc html_w, html_h; note that false passed b/c truncation is not needed
		html_w = calc_size.Val_0(); html_h = calc_size.Val_1();
		html_view_url = Trg_view_url(Xof_img_mode_.Tid__thumb, model_w);	// note that thumb.Width is used (the actual file width), not html_w
		file_exists = true;
		return true;
	}
	private static final    Xof_url_bldr dflt_url_bldr = new Xof_url_bldr();	// NOTE: only used by v1
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