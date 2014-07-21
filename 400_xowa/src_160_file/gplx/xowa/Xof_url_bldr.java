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
package gplx.xowa; import gplx.*;
import gplx.gfui.*;
import gplx.xowa.files.*;
public class Xof_url_bldr {
	private Bry_bfr bfr = Bry_bfr.reset_(400);
	private byte[] area; private boolean wmf_protocol_is_file;
	public byte Dir_spr() {return dir_spr;} public Xof_url_bldr Dir_spr_(byte v) {dir_spr = v; return this;} private byte dir_spr;
	public byte[] Root() {return root;} public Xof_url_bldr Root_(byte[] v) {root = v; return this;} private byte[] root;
	public byte[] Ttl() {return ttl;} public Xof_url_bldr Ttl_(byte[] v) {ttl = v; return this;} private byte[] ttl;
	public byte[] Md5() {return md5;} public Xof_url_bldr Md5_(byte[] v) {md5 = v; return this;} private byte[] md5;
	public Xof_ext Ext() {return ext;} public Xof_url_bldr Ext_(Xof_ext v) {ext = v; return this;} private Xof_ext ext;
	public int Width() {return width;} public Xof_url_bldr Width_(int v) {width = v; return this;} private int width;
	public Xof_url_bldr Thumbtime_(double v) {thumbtime = v; return this;} private double thumbtime = Xof_doc_thumb.Null;
	public Xof_url_bldr Page_(int v) {page = v; return this;} private int page = Xof_doc_page.Null;
	public Xof_url_bldr Wmf_dir_hive_(boolean v) {wmf_dir_hive = v; return this;} private boolean wmf_dir_hive;
	public boolean Thumb() {return thumb;} public Xof_url_bldr Thumb_(boolean v) {thumb = v; return this;} private boolean thumb;
	public byte Thumbtime_dlm() {return thumbtime_dlm;} private byte thumbtime_dlm = Byte_ascii.At;
	public Xof_url_bldr Thumbtime_dlm_dash_() {thumbtime_dlm = Byte_ascii.Dash; return this;}
	public Xof_url_bldr Set_trg_html_(byte mode, Xof_repo_itm repo, byte[] ttl, byte[] md5, Xof_ext ext, int width, double thumbtime, int page) {
		this.wmf_dir_hive = false; this.thumb = mode == Xof_repo_itm.Mode_thumb;
		this.dir_spr = Byte_ascii.Slash; this.root = repo.Root_http(); this.ttl = repo.Gen_name_trg(ttl, md5, ext); this.area = repo.Mode_names()[mode];
		this.md5 = md5; this.ext = ext; this.width = width; this.thumbtime = thumbtime; this.page = page;
		this.repo_dir_depth = repo.Dir_depth();
		return this;
	}	private int repo_dir_depth;
	public Xof_url_bldr Set_src_file_(byte mode, Xof_repo_itm repo, byte[] ttl, byte[] md5, Xof_ext ext, int width, double thumbtime, int page) {
		this.wmf_dir_hive = true; this.thumb = mode == Xof_repo_itm.Mode_thumb;
		this.dir_spr = repo.Dir_spr(); this.root = repo.Root(); this.ttl = repo.Gen_name_src(ttl); this.area = repo.Mode_names()[mode];
		this.md5 = md5; this.ext = ext; this.width = width; this.thumbtime = thumbtime; this.page = page;
		this.wmf_protocol_is_file = repo.Tarball();
		return this;
	}
	public Xof_url_bldr Set_trg_file_(byte mode, Xof_repo_itm repo, byte[] ttl, byte[] md5, Xof_ext ext, int width, double thumbtime, int page) {
		this.wmf_dir_hive = false; this.thumb = mode == Xof_repo_itm.Mode_thumb;
		this.dir_spr = repo.Dir_spr(); this.root = repo.Root(); this.ttl = repo.Gen_name_trg(ttl, md5, ext); this.area = repo.Mode_names()[mode];
		this.md5 = md5; this.ext = ext; this.width = width; this.thumbtime = thumbtime; this.page = page;
		this.repo_dir_depth = repo.Dir_depth();
		return this;
	}
	public Xof_url_bldr Init_by_root(byte[] root, byte dir_spr, boolean wmf_dir_hive, boolean wmf_protocol_is_file, int repo_dir_depth) {
		this.root = root; this.dir_spr = dir_spr; this.wmf_dir_hive = wmf_dir_hive; this.wmf_protocol_is_file = wmf_protocol_is_file; this.repo_dir_depth = repo_dir_depth;
		this.fsys_tid_is_wnt = Op_sys.Cur().Tid_is_wnt();
		return this;
	}	private boolean fsys_tid_is_wnt;
	public Xof_url_bldr Init_by_itm(byte mode, byte[] ttl, byte[] md5, Xof_ext ext, int width, double thumbtime) {
		this.thumb = mode == Xof_repo_itm.Mode_thumb;
		this.area = Xof_repo_itm.Mode_names_key[mode];
		this.ttl = ttl;
		if (wmf_protocol_is_file && fsys_tid_is_wnt)
			this.ttl = Xof_repo_itm.Ttl_invalid_fsys_chars(ttl); 			
		this.md5 = md5;	// NOTE: the md5 is the orig ttl, even if ttl gets changed b/c of invalid_chars or exceeds_len
		this.ext = ext; this.width = width; this.thumbtime = thumbtime;
		return this;
	}
	public byte[] Xto_bry() {Bld(); byte[] rv = bfr.XtoAryAndClear(); Clear(); return rv;}
	public String Xto_str() {Bld(); String rv = bfr.XtoStr(); Clear(); return rv;}
	public Io_url Xto_url() {Bld(); Io_url rv = Io_url_.new_fil_(bfr.XtoStr()); Clear(); return rv;}
	private void Bld() {
		Add_core();
		if (thumb) {
			if (wmf_dir_hive)	Add_thumb_wmf();
			else				Add_thumb_xowa();
		}
	}
	private Xof_url_bldr Add_core() {
		bfr.Add(root);																	// add root;				EX: "C:\xowa\file\"; assume trailing dir_spr
		if (area != null && !(wmf_dir_hive && !thumb))									// !(wmf_dir_hive && !thumb) means never add if wmf_dir_hive and orig
			bfr.Add(area).Add_byte(dir_spr);											// add area;				EX: "thumb\"
		byte b0 = md5[0];
		if (wmf_dir_hive) {
			bfr.Add_byte(b0).Add_byte(dir_spr);											// add md5_0				EX: "0/"
			bfr.Add_byte(b0).Add_byte(md5[1]).Add_byte(dir_spr);						// add md5_01				EX: "01/"
		}
		else {
			for (int i = 0; i < repo_dir_depth; i++)
				bfr.Add_byte(md5[i]).Add_byte(dir_spr);									// add md5_0123				EX: "0/1/2/3"
		}
		if (wmf_dir_hive) {
			if (wmf_protocol_is_file)													// sitting on local file system (as opposed to http)
				bfr.Add(ttl);															// NOTE: file_names are not url-encoded; this includes symbols (') and foreign characters (ö)
			else																		// wmf_http
				bfr.Add(encoder_src_http.Encode(ttl));									// NOTE: file_names must be url-encoded; JAVA will default to native charset which on Windows will be 1252; foreign character urls will fail due to conversion mismatch (1252 on windows; UTF-8 on WMF); PAGE:en.w:Möbius strip
		}
		else
			bfr.Add(ttl);																// add title;				EX: "A.png"
		return this;
	}
	private Xof_url_bldr Add_thumb_xowa() {
		bfr.Add_byte(dir_spr);															// add dir_spr;				EX: "\"
		bfr.Add_int_variable(width).Add(Bry_px);										// add width;				EX: "220px"
		if (Xof_doc_thumb.Null_n(thumbtime))
			bfr.Add_byte(thumbtime_dlm).Add_str(Xof_doc_thumb.X_str(thumbtime));		// add thumbtime			EX: "@5"
		else if (page != Xof_doc_page.Null)
			bfr.Add_byte(Byte_ascii.Dash).Add_int_variable(page);						// add page					EX: "-5"
		bfr.Add_byte(Byte_ascii.Dot);													// add .					EX: "."
		if (thumb)
			bfr.Add(ext.Ext_view());													// add view_ext				EX: ".png"
		else
			bfr.Add(ext.Ext());															// add orig_ext				EX: ".svg"
		return this;
	}
	private Xof_url_bldr Add_thumb_wmf() {
		bfr.Add_byte(dir_spr);															// add dir_spr;				EX: "\"
		int file_ext_id = ext.Id();
		switch (file_ext_id) {
			case Xof_ext_.Id_ogg:
			case Xof_ext_.Id_ogv:
			case Xof_ext_.Id_webm:
				if (Xof_doc_thumb.Null_n(thumbtime))
					bfr.Add(Bry_seek).Add_str(Xof_doc_thumb.X_str(thumbtime)).Add_byte(Byte_ascii.Dash);// add seek;				EX: "seek%3D5-"
				else
					bfr.Add(Bry_mid);													// add mid;					EX: "mid-"
				break;
			case Xof_ext_.Id_tif:
			case Xof_ext_.Id_tiff:
				Add_thumb_wmf_page(Bry_lossy_page1, Bry_lossy_page);
				bfr.Add_int_variable(width);											// add file_w;				EX: "220"
				bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
			case Xof_ext_.Id_pdf:
			case Xof_ext_.Id_djvu:
				Add_thumb_wmf_page(Bry_page1, Bry_page);
				bfr.Add_int_variable(width);											// add file_w;				EX: "220"
				bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
			default:
				bfr.Add_int_variable(width);											// add file_w;				EX: "220"
				bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
		}
		bfr.Add(encoder_src_http.Encode(ttl));											// add ttl again;			EX: "A.png"
		switch (file_ext_id) {
			case Xof_ext_.Id_svg:
			case Xof_ext_.Id_bmp:
			case Xof_ext_.Id_xcf:
				bfr.Add_byte(Byte_ascii.Dot).Add(Xof_ext_.Bry_png);						// add .png;				EX: "A.svg" -> "A.svg.png"		NOTE: MediaWiki always adds as lowercase
				break;
			case Xof_ext_.Id_pdf:
			case Xof_ext_.Id_tif:														// add .jpg					EX: "A.tif" -> "A.tif.jpg"		NOTE: MediaWiki always adds as lowercase
			case Xof_ext_.Id_tiff:
			case Xof_ext_.Id_ogg:
			case Xof_ext_.Id_ogv:						
			case Xof_ext_.Id_djvu:
			case Xof_ext_.Id_webm:
				bfr.Add_byte(Byte_ascii.Dot).Add(Xof_ext_.Bry_jpg);
				break;
		}
		return this;
	}
	private void Add_thumb_wmf_page(byte[] bry_page_1, byte[] bry_page) {
		if (Xof_doc_thumb.Null_y(page))
			bfr.Add(bry_page_1);												// add "lossy-page1-"		EX: "lossy-page1-"
		else {
			bfr.Add(bry_page);													// add "lossy-page"			EX: "lossy-page"
			bfr.Add_int_variable(page);											// add page					EX: 123
			bfr.Add_byte(Byte_ascii.Dash);										// add -					EX: "-"
		}
	}
	private Xof_url_bldr Clear() {
		root = area = ttl = md5 = null;
		width = 0; thumbtime = Xof_doc_thumb.Null;
		ext = null;
		bfr.Clear();
		return this;
	}
	public static final byte[] Bry_reg = Bry_.new_ascii_("reg.csv")
	, Bry_px = Bry_.new_ascii_("px"), Bry_px_dash = Bry_.new_ascii_("px-")
	, Bry_thumb = Bry_.new_ascii_("thumb"), Bry_mid = Bry_.new_ascii_("mid-")
	;
	private static final byte[]
	  Bry_lossy_page  = Bry_.new_ascii_("lossy-page"), Bry_page = Bry_.new_ascii_("page")
	, Bry_lossy_page1 = Bry_.new_ascii_("lossy-page1-"), Bry_page1 = Bry_.new_ascii_("page1-"), Bry_seek = Bry_.new_ascii_("seek%3D");
	public static final Xof_url_bldr Temp = new Xof_url_bldr();
	private static final Url_encoder encoder_src_http = Url_encoder.new_http_url_(); // NOTE: changed from new_html_href_mw_ to new_url_ on 2012-11-19; issues with A%2Cb becoming A%252Cb
	public static Xof_url_bldr new_v2_() {return new Xof_url_bldr().Thumbtime_dlm_dash_();}
}
