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
public class Xof_url_bldr {
	private final Bry_bfr bfr = Bry_bfr.reset_(400);
	private byte[] ttl; private byte[] md5; private Xof_ext ext; private boolean file_is_thumb; private int file_w;
	private double time = Xof_doc_thumb.Null; private int page = Xof_doc_page.Null; private byte time_dlm = Byte_ascii.At;		
	private byte[] root; private byte dir_spr; private boolean fsys_tid_is_wnt; private boolean wmf_dir_hive; private boolean wmf_protocol_is_file; private int md5_dir_depth; private byte[] area;
	public Xof_url_bldr Root_(byte[] v) {root = v; return this;}
	public Xof_url_bldr Init_by_root(byte[] root, byte dir_spr, boolean wmf_dir_hive, boolean wmf_protocol_is_file, int md5_dir_depth) {
		this.root = root; this.dir_spr = dir_spr; this.wmf_dir_hive = wmf_dir_hive; this.wmf_protocol_is_file = wmf_protocol_is_file; this.md5_dir_depth = md5_dir_depth;
		this.fsys_tid_is_wnt = Op_sys.Cur().Tid_is_wnt();
		return this;
	}
	public Xof_url_bldr Init_by_itm(byte mode, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		this.ttl = ttl; this.md5 = md5;	this.ext = ext; this.file_w = file_w; this.time = time; this.page = page;
		if (wmf_protocol_is_file && fsys_tid_is_wnt) this.ttl = Xof_repo_itm.Ttl_invalid_fsys_chars(ttl); // NOTE: changed ttl does not change md5
		this.file_is_thumb = mode == Xof_repo_itm.Mode_thumb;
		this.area = Xof_repo_itm.Mode_names_key[mode];
		return this;
	}
	public Xof_url_bldr Init_for_src_file(byte mode, Xof_repo_itm repo, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		this.wmf_dir_hive = Bool_.Y; this.file_is_thumb = mode == Xof_repo_itm.Mode_thumb;
		this.dir_spr = repo.Dir_spr(); this.root = repo.Root(); this.ttl = repo.Gen_name_src(ttl); this.area = repo.Mode_names()[mode];
		this.md5 = md5; this.ext = ext; this.file_w = file_w; this.time = time; this.page = page;
		this.wmf_protocol_is_file = repo.Tarball();
		return this;
	}
	public Xof_url_bldr Init_for_trg_file(byte mode, Xof_repo_itm repo, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		this.wmf_dir_hive = Bool_.N; this.file_is_thumb = mode == Xof_repo_itm.Mode_thumb;
		this.dir_spr = repo.Dir_spr(); this.root = repo.Root(); this.ttl = repo.Gen_name_trg(ttl, md5, ext); this.area = repo.Mode_names()[mode];
		this.md5 = md5; this.ext = ext; this.file_w = file_w; this.time = time; this.page = page;
		this.md5_dir_depth = repo.Dir_depth();
		return this;
	}
	public Xof_url_bldr Init_for_trg_html(byte mode, Xof_repo_itm repo, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		this.wmf_dir_hive = Bool_.N; this.file_is_thumb = mode == Xof_repo_itm.Mode_thumb;
		this.dir_spr = Byte_ascii.Slash; this.root = repo.Root_http(); this.ttl = repo.Gen_name_trg(ttl, md5, ext); this.area = repo.Mode_names()[mode];
		this.md5 = md5; this.ext = ext; this.file_w = file_w; this.time = time; this.page = page;
		this.md5_dir_depth = repo.Dir_depth();
		return this;
	}
	public byte[] Xto_bry() {Bld(); byte[] rv = bfr.XtoAryAndClear(); Clear(); return rv;}
	public String Xto_str() {Bld(); String rv = bfr.XtoStr(); Clear(); return rv;}
	public Io_url Xto_url() {Bld(); Io_url rv = Io_url_.new_fil_(bfr.XtoStr()); Clear(); return rv;}
	private void Bld() {
		Add_core();
		if (file_is_thumb) {
			if (wmf_dir_hive)	Add_thumb_wmf();
			else				Add_thumb_xowa();
		}
	}
	private Xof_url_bldr Add_core() {
		bfr.Add(root);																	// add root;				EX: "C:\xowa\file\"; assume trailing dir_spr
		if (area != null && !(wmf_dir_hive && !file_is_thumb))							// !(wmf_dir_hive && !thumb) means never add if wmf_dir_hive and orig
			bfr.Add(area).Add_byte(dir_spr);											// add area;				EX: "thumb\"
		byte b0 = md5[0];
		if (wmf_dir_hive) {
			bfr.Add_byte(b0).Add_byte(dir_spr);											// add md5_0				EX: "0/"
			bfr.Add_byte(b0).Add_byte(md5[1]).Add_byte(dir_spr);						// add md5_01				EX: "01/"
		}
		else {
			for (int i = 0; i < md5_dir_depth; i++)
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
		bfr.Add_int_variable(file_w).Add(Bry_px);										// add file_w;				EX: "220px"
		if (Xof_doc_thumb.Null_n(time))
			bfr.Add_byte(time_dlm).Add_str(Xof_doc_thumb.X_str(time));					// add time					EX: "@5"
		else if (page != Xof_doc_page.Null)
			bfr.Add_byte(Byte_ascii.Dash).Add_int_variable(page);						// add page					EX: "-5"
		bfr.Add_byte(Byte_ascii.Dot);													// add .					EX: "."
		if (file_is_thumb)
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
				if (Xof_doc_thumb.Null_n(time))
					bfr.Add(Bry_seek).Add_str(Xof_doc_thumb.X_str(time)).Add_byte(Byte_ascii.Dash);// add seek;				EX: "seek%3D5-"
				else
					bfr.Add(Bry_mid);													// add mid;					EX: "mid-"
				break;
			case Xof_ext_.Id_tif:
			case Xof_ext_.Id_tiff:
				Add_thumb_wmf_page(Bry_lossy_page1, Bry_lossy_page);
				bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"
				bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
			case Xof_ext_.Id_pdf:
			case Xof_ext_.Id_djvu:
				Add_thumb_wmf_page(Bry_page1, Bry_page);
				bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"
				bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
			default:
				bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"
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
			bfr.Add(bry_page_1);														// add "lossy-page1-"		EX: "lossy-page1-"
		else {
			bfr.Add(bry_page);															// add "lossy-page"			EX: "lossy-page"
			bfr.Add_int_variable(page);													// add page					EX: 123
			bfr.Add_byte(Byte_ascii.Dash);												// add -					EX: "-"
		}
	}
	private Xof_url_bldr Clear() {
		root = area = ttl = md5 = null;
		file_w = 0; time = Xof_doc_thumb.Null;
		ext = null;
		bfr.Clear();
		return this;
	}
	public static final byte[]
	  Bry_reg = Bry_.new_ascii_("reg.csv")
	, Bry_px = Bry_.new_ascii_("px"), Bry_px_dash = Bry_.new_ascii_("px-")
	, Bry_thumb = Bry_.new_ascii_("thumb"), Bry_mid = Bry_.new_ascii_("mid-")
	;
	private static final byte[]
	  Bry_lossy_page  = Bry_.new_ascii_("lossy-page"), Bry_page = Bry_.new_ascii_("page")
	, Bry_lossy_page1 = Bry_.new_ascii_("lossy-page1-"), Bry_page1 = Bry_.new_ascii_("page1-"), Bry_seek = Bry_.new_ascii_("seek%3D");
	public static final Xof_url_bldr Temp = new Xof_url_bldr();
	private static final Url_encoder encoder_src_http = Url_encoder.new_http_url_(); // NOTE: changed from new_html_href_mw_ to new_url_ on 2012-11-19; issues with A%2Cb becoming A%252Cb
	public static Xof_url_bldr new_v2_() {
		Xof_url_bldr rv = new Xof_url_bldr();
		rv.time_dlm = Byte_ascii.Dash;
		return rv;
	}
}
