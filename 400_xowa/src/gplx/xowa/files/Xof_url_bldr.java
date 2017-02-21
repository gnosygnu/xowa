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
import gplx.core.envs.*;
import gplx.langs.htmls.encoders.*;
import gplx.xowa.files.repos.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.imgs.*;
public class Xof_url_bldr {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(400);
	private final    Gfo_url_encoder encoder_src_http = Gfo_url_encoder_.New__http_url().Make(); // NOTE: changed from new_html_href_mw_ to new_url_ on 2012-11-19; issues with A%2Cb becoming A%252Cb
	private byte[] ttl; private byte[] md5; private Xof_ext ext; private boolean file_is_thumb; private int file_w;
	private double time = Xof_lnki_time.Null; private int page = Xof_lnki_page.Null; private byte time_dlm = Byte_ascii.At;
	private byte repo_tid;
	private byte[] root; private byte dir_spr; private boolean fsys_is_wnt; private boolean wmf_dir_hive; private boolean wmf_protocol_is_file; private int md5_dir_depth; private byte[] area;
	public Xof_url_bldr Root_(byte[] v) {root = v; return this;}
	public Xof_url_bldr Init_by_repo(byte repo_tid, byte[] root, boolean fsys_is_wnt, byte dir_spr, boolean wmf_dir_hive, boolean wmf_protocol_is_file, int md5_dir_depth) {
		this.repo_tid = repo_tid;
		this.root = root; this.dir_spr = dir_spr; this.wmf_dir_hive = wmf_dir_hive; this.wmf_protocol_is_file = wmf_protocol_is_file; this.md5_dir_depth = md5_dir_depth;
		this.fsys_is_wnt = fsys_is_wnt;
		return this;
	}
	public Xof_url_bldr Init_by_itm(byte mode, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		this.ttl = ttl; this.md5 = md5;	this.ext = ext; this.file_w = file_w; this.time = time; this.page = page;
		if (wmf_protocol_is_file && fsys_is_wnt) this.ttl = Xof_itm_ttl_.Remove_invalid(tmp_bfr, ttl); // NOTE: changed ttl does not change md5
		this.file_is_thumb = mode == Xof_img_mode_.Tid__thumb;
		this.area = Xof_img_mode_.Names_ary[mode];
		return this;
	}
	public Xof_url_bldr Init_for_src_file(Xof_repo_itm repo, byte mode, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		this.repo_tid = repo.Tid();
		this.wmf_dir_hive = Bool_.Y; this.wmf_protocol_is_file = repo.Tarball();
		this.dir_spr = repo.Dir_spr(); this.root = repo.Root_bry(); this.area = repo.Mode_names()[mode];
		this.ttl = repo.Gen_name_src(tmp_bfr, ttl); this.md5 = md5; this.ext = ext;
		this.file_is_thumb = mode == Xof_img_mode_.Tid__thumb; this.file_w = file_w; this.time = time; this.page = page;
		return this;
	}
	public Xof_url_bldr Init_for_trg_file(Xof_repo_itm repo, byte mode, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		return Init(repo.Tid(), Bool_.N, Bool_.N, repo.Dir_spr(), repo.Root_bry()
			, repo.Mode_names()[mode], repo.Dir_depth(), repo.Gen_name_trg(tmp_bfr, ttl, md5, ext), md5, ext, mode, file_w, time, page);
	}
	public Xof_url_bldr Init_for_trg_html(Xof_repo_itm repo, byte mode, byte[] ttl, byte[] md5, Xof_ext ext, int file_w, double time, int page) {
		return Init(repo.Tid(), Bool_.N, Bool_.N, Byte_ascii.Slash, repo.Root_http()
			, repo.Mode_names()[mode], repo.Dir_depth(), repo.Gen_name_trg(tmp_bfr, ttl, md5, ext), md5, ext, mode, file_w, time, page);
	}
	private Xof_url_bldr Init(byte repo_tid, boolean wmf_dir_hive, boolean wmf_protocol_is_file, byte dir_spr
		, byte[] root, byte[] area, int md5_dir_depth
		, byte[] ttl, byte[] md5, Xof_ext ext
		, byte file_mode, int file_w, double time, int page
		) {
		this.repo_tid = repo_tid;
		this.wmf_dir_hive = wmf_dir_hive; this.wmf_protocol_is_file = wmf_protocol_is_file; this.dir_spr = dir_spr;
		this.root = root;  this.area = area; this.md5_dir_depth = md5_dir_depth;
		this.ttl = ttl; this.md5 = md5; this.ext = ext;
		this.file_is_thumb = file_mode == Xof_img_mode_.Tid__thumb; this.file_w = file_w; this.time = time; this.page = page;
		return this;
	}
	public byte[] Xto_bry() {Bld(); byte[] rv = tmp_bfr.To_bry_and_clear(); Clear(); return rv;}
	public String Xto_str() {Bld(); String rv = tmp_bfr.To_str(); Clear(); return rv;}
	public Io_url Xto_url() {Bld(); Io_url rv = Io_url_.new_fil_(tmp_bfr.To_str()); Clear(); return rv;}
	public Io_url Xto_url_by_http() {Bld(); Io_url rv = Io_url_.New__http_or_fail(tmp_bfr.To_str()); Clear(); return rv;}
	public Io_url To_url_trg(Xof_repo_itm repo_itm, Xof_fsdb_itm itm, boolean orig) {
		byte mode = orig ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb;
		return this.Init_for_trg_file(repo_itm, mode, itm.Orig_ttl(), itm.Orig_ttl_md5(), itm.Orig_ext(), itm.File_w(), itm.Lnki_time(), itm.Lnki_page()).Xto_url();
	}
	public Io_url To_url_trg(Xof_repo_itm repo_itm, Xof_file_itm itm, boolean orig) {
		byte mode = orig ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb;
		return this.Init_for_trg_file(repo_itm, mode, itm.Orig_ttl(), itm.Orig_ttl_md5(), itm.Orig_ext(), itm.File_w(), itm.Lnki_time(), itm.Lnki_page()).Xto_url();
	}
	public Io_url To_url_trg(Xof_repo_itm repo_itm, gplx.xowa.files.caches.Xou_cache_itm itm, boolean orig) {
		byte mode = orig ? Xof_img_mode_.Tid__orig : Xof_img_mode_.Tid__thumb;
		return this.Init_for_trg_file(repo_itm, mode, itm.Orig_ttl(), itm.Orig_ttl_md5(), itm.Orig_ext_itm(), itm.File_w(), itm.Lnki_time(), itm.Lnki_page()).Xto_url();
	}
	private static final    byte[] Bry__http = Bry_.new_a7("http");
	private void Bld() {
		if (repo_tid == Xof_repo_tid_.Tid__math) {
			tmp_bfr.Add(root);																// add root;				EX: "C:\xowa\file\"; assume trailing dir_spr
			boolean root_is_http = Bry_.Has_at_bgn(root, Bry__http);
			if (root_is_http)
				tmp_bfr.Add_mid(ttl, 0, ttl.length - 4);	// -4 to remove ".svg". note that XO stores ".svg", but WM doesn't; EX: "596f8baf206a81478afd4194b44138715dc1a05c.svg"
			else
				tmp_bfr.Add(ttl);
		}
		else {
			Add_core();
			if (file_is_thumb) {
				if (wmf_dir_hive)	Add_thumb_wmf();
				else				Add_thumb_xowa();
			}
		}
	}
	private Xof_url_bldr Add_core() {
		tmp_bfr.Add(root);																	// add root;				EX: "C:\xowa\file\"; assume trailing dir_spr
		if (area != null && !(wmf_dir_hive && !file_is_thumb))								// !(wmf_dir_hive && !thumb) means never add if wmf_dir_hive and orig
			tmp_bfr.Add(area).Add_byte(dir_spr);											// add area;				EX: "thumb\"
		byte b0 = md5[0];
		if (wmf_dir_hive) {
			tmp_bfr.Add_byte(b0).Add_byte(dir_spr);											// add md5_0				EX: "0/"
			tmp_bfr.Add_byte(b0).Add_byte(md5[1]).Add_byte(dir_spr);						// add md5_01				EX: "01/"
		}
		else {
			for (int i = 0; i < md5_dir_depth; i++)
				tmp_bfr.Add_byte(md5[i]).Add_byte(dir_spr);									// add md5_0123				EX: "0/1/2/3"
		}
		if (wmf_dir_hive) {
			if (wmf_protocol_is_file)														// sitting on local file system (as opposed to http)
				tmp_bfr.Add(ttl);															// NOTE: file_names are not url-encoded; this includes symbols (') and foreign characters (ö)
			else																			// wmf_http
				tmp_bfr.Add(encoder_src_http.Encode(ttl));									// NOTE: file_names must be url-encoded; JAVA will default to native charset which on Windows will be 1252; foreign character urls will fail due to conversion mismatch (1252 on windows; UTF-8 on WMF); PAGE:en.w:Möbius strip
		}
		else
			tmp_bfr.Add(ttl);																// add title;				EX: "A.png"
		return this;
	}
	private Xof_url_bldr Add_thumb_xowa() {
		tmp_bfr.Add_byte(dir_spr);															// add dir_spr;				EX: "\"
		tmp_bfr.Add_int_variable(file_w).Add(Bry_px);										// add file_w;				EX: "220px"
		if (Xof_lnki_time.Null_n(time))
			tmp_bfr.Add_byte(time_dlm).Add_str_a7(Xof_lnki_time.X_str(time));				// add time					EX: "@5"
		else if (page != Xof_lnki_page.Null)
			tmp_bfr.Add_byte(Byte_ascii.Dash).Add_int_variable(page);						// add page					EX: "-5"
		tmp_bfr.Add_byte(Byte_ascii.Dot);													// add .					EX: "."
		if (file_is_thumb)
			tmp_bfr.Add(ext.Ext_view());													// add view_ext				EX: ".png"
		else
			tmp_bfr.Add(ext.Ext());															// add orig_ext				EX: ".svg"
		return this;
	}
	private Xof_url_bldr Add_thumb_wmf() {
		tmp_bfr.Add_byte(dir_spr);															// add dir_spr;				EX: "\"
		int file_ext_id = ext.Id();
		switch (file_ext_id) {
			case Xof_ext_.Id_ogg:
			case Xof_ext_.Id_ogv:
			case Xof_ext_.Id_webm:
				tmp_bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"; PAGE:en.w:Alice_Brady; DATE:2015-08-06
				tmp_bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				if (Xof_lnki_time.Null_n(time))
					tmp_bfr.Add(Bry_seek).Add_str_a7(Xof_lnki_time.X_str(time)).Add_byte(Byte_ascii.Dash);// add seek;		EX: "seek%3D5-"
				else
					tmp_bfr.Add_byte(Byte_ascii.Dash);										// add mid;					EX: "-"; NOTE: was "mid-"; DATE:2015-08-06
				break;
			case Xof_ext_.Id_tif:
			case Xof_ext_.Id_tiff:
				Add_thumb_wmf_page(Bry_lossy_page1, Bry_lossy_page);
				tmp_bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"
				tmp_bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
			case Xof_ext_.Id_pdf:
			case Xof_ext_.Id_djvu:
				Add_thumb_wmf_page(Bry_page1, Bry_page);
				tmp_bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"
				tmp_bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
			default:
				tmp_bfr.Add_int_variable(file_w);											// add file_w;				EX: "220"
				tmp_bfr.Add(Bry_px_dash);													// add px;					EX: "px-"
				break;
		}
		int ttl_len = ttl.length;
		if (ttl_len > 160) {																// long file name
			tmp_bfr.Add(Bry_thumnbail_w_dot);
			tmp_bfr.Add(ext.Ext());
		}
		else
			tmp_bfr.Add(encoder_src_http.Encode(ttl));										// add ttl again;			EX: "A.png"
		switch (file_ext_id) {
			case Xof_ext_.Id_svg:
			case Xof_ext_.Id_bmp:
			case Xof_ext_.Id_xcf:
				tmp_bfr.Add_byte(Byte_ascii.Dot).Add(Xof_ext_.Bry_png);						// add .png;				EX: "A.svg" -> "A.svg.png"		NOTE: MediaWiki always adds as lowercase
				break;
			case Xof_ext_.Id_pdf:
			case Xof_ext_.Id_tif:															// add .jpg					EX: "A.tif" -> "A.tif.jpg"		NOTE: MediaWiki always adds as lowercase
			case Xof_ext_.Id_tiff:
			case Xof_ext_.Id_ogg:
			case Xof_ext_.Id_ogv:						
			case Xof_ext_.Id_djvu:
			case Xof_ext_.Id_webm:
				tmp_bfr.Add_byte(Byte_ascii.Dot).Add(Xof_ext_.Bry_jpg);
				break;
		}
		return this;
	}
	private void Add_thumb_wmf_page(byte[] bry_page_1, byte[] bry_page) {
		if (Xof_lnki_time.Null_y(page))
			tmp_bfr.Add(bry_page_1);														// add "lossy-page1-"		EX: "lossy-page1-"
		else {
			tmp_bfr.Add(bry_page);															// add "lossy-page"			EX: "lossy-page"
			tmp_bfr.Add_int_variable(page);													// add page					EX: 123
			tmp_bfr.Add_byte(Byte_ascii.Dash);												// add -					EX: "-"
		}
	}
	private Xof_url_bldr Clear() {
		root = area = ttl = md5 = null;
		file_w = Xof_img_size.Null;
		time = Xof_lnki_time.Null;
		ext = null;
		tmp_bfr.Clear();
		repo_tid = Xof_repo_tid_.Tid__null;
		return this;
	}
	public static final    byte[]
	  Bry_reg = Bry_.new_a7("reg.csv")
	, Bry_px = Bry_.new_a7("px"), Bry_px_dash = Bry_.new_a7("px-")
	, Bry_thumb = Bry_.new_a7("thumb")
	, Bry_thumnbail_w_dot = Bry_.new_a7("thumbnail.")
	;
	private static final    byte[]
	  Bry_lossy_page  = Bry_.new_a7("lossy-page"), Bry_page = Bry_.new_a7("page")
	, Bry_lossy_page1 = Bry_.new_a7("lossy-page1-"), Bry_page1 = Bry_.new_a7("page1-"), Bry_seek = Bry_.new_a7("seek%3D");
	public static Xof_url_bldr new_v2() {
		Xof_url_bldr rv = new Xof_url_bldr();
		rv.time_dlm = Byte_ascii.Dash;
		return rv;
	}
	public static final int Md5_dir_depth_2 = 2;
}
