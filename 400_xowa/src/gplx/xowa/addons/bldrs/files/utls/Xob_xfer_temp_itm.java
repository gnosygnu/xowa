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
package gplx.xowa.addons.bldrs.files.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.stores.*;
import gplx.dbs.*; import gplx.xowa.files.*;
import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.lnkis.files.*;
import gplx.xowa.addons.bldrs.files.dbs.*;
public class Xob_xfer_temp_itm {
	public int Lnki_id() {return lnki_id;} private int lnki_id;
	public int Lnki_tier_id() {return lnki_tier_id;} private int lnki_tier_id;
	public byte Orig_repo() {return orig_repo;} private byte orig_repo;
	public int Lnki_ext() {return lnki_ext;} private int lnki_ext;
	public byte[] Orig_file_ttl() {return orig_file_ttl;} private byte[] orig_file_ttl;
	public String Orig_media_type() {return orig_media_type;} private String orig_media_type;
	public String Orig_minor_mime() {return orig_minor_mime;} private String orig_minor_mime;
	public byte Orig_media_type_tid() {return orig_media_type_tid;} private byte orig_media_type_tid;
	public int Orig_page_id() {return orig_page_id;} private int orig_page_id;
	public String Join_ttl() {return join_ttl;} private String join_ttl;
	public String Redirect_src() {return redirect_src;} private String redirect_src;
	public byte Lnki_type() {return lnki_type;} private byte lnki_type;
	public byte Lnki_src_tid() {return lnki_src_tid;} private byte lnki_src_tid;
	public int Lnki_w() {return lnki_w;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} private int lnki_h;
	public int Lnki_count() {return lnki_count;} private int lnki_count;
	public int Lnki_page_id() {return lnki_page_id;} private int lnki_page_id;
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public int Orig_ext_id() {return orig_ext_id;} private int orig_ext_id;
	public double Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double Lnki_thumbtime() {return lnki_thumbtime;} private double lnki_thumbtime;
	public int Lnki_page() {return lnki_page;} private int lnki_page;
	public void Clear() {
		orig_file_ttl = null;
		lnki_ext = lnki_type = lnki_src_tid
				= orig_repo = orig_media_type_tid = Byte_.Max_value_127;
		chk_tid = Chk_tid_none;
		lnki_id = lnki_tier_id = lnki_w = lnki_h = lnki_count =  lnki_page_id
				= orig_w = orig_h = orig_page_id = Int_.Neg1;
		join_ttl =  redirect_src = orig_media_type = null;
		lnki_upright = Xop_lnki_tkn.Upright_null;
		lnki_thumbtime = Xof_lnki_time.Null;
		lnki_page = Xof_lnki_page.Null;
	}
	public void Load(DataRdr rdr) {
		lnki_id			= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_id);
		lnki_tier_id	= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_tier_id);
		lnki_page_id	= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_page_id);
		lnki_ext		= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_ext);
		lnki_type		= rdr.ReadByte(Xob_lnki_regy_tbl.Fld_lnki_type);
		lnki_src_tid	= rdr.ReadByte(Xob_lnki_regy_tbl.Fld_lnki_src_tid);
		lnki_w			= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_w);
		lnki_h			= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_h);
		lnki_upright	= rdr.ReadDouble(Xob_lnki_regy_tbl.Fld_lnki_upright);
		lnki_thumbtime	= Xof_lnki_time.Db_load_double(rdr, Xob_lnki_regy_tbl.Fld_lnki_time);
		lnki_page		= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_page);
		lnki_count		= rdr.ReadInt(Xob_lnki_regy_tbl.Fld_lnki_count);
		orig_file_ttl	= rdr.ReadBryByStr(Xob_lnki_regy_tbl.Fld_lnki_ttl);
		orig_repo		= rdr.ReadByte(Xob_orig_regy_tbl.Fld_orig_repo);
		orig_page_id	= rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_page_id, -1);
		join_ttl		= rdr.ReadStr(Xob_orig_regy_tbl.Fld_orig_file_ttl);
		redirect_src	= rdr.ReadStr(Xob_orig_regy_tbl.Fld_lnki_ttl);
		orig_w			= rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_w, -1);
		orig_h			= rdr.ReadIntOr(Xob_orig_regy_tbl.Fld_orig_h, -1);
		orig_media_type = rdr.ReadStrOr(Xob_orig_regy_tbl.Fld_orig_media_type, "");	// convert nulls to ""
		orig_minor_mime = rdr.ReadStrOr(Xob_orig_regy_tbl.Fld_orig_minor_mime, "");	// convert nulls to ""
		orig_ext_id		= rdr.ReadInt(Xob_orig_regy_tbl.Fld_orig_file_ext);
	}
	public static final    byte 
	  Chk_tid_none = 0
	, Chk_tid_orig_page_id_is_null = 1
	, Chk_tid_orig_media_type_is_audio = 2
	, Chk_tid_ns_is_media = 3
	, Chk_tid_orig_w_is_0 = 4
	;
	public byte Chk_tid() {return chk_tid;} private byte chk_tid;
	public boolean Chk(Xof_img_size img_size) {
		if (String_.Eq(join_ttl, redirect_src)) // join_ttl is same as redirect_src; not a redirect; EX:(direct) join="A.png";redirect_src="A.png"; (redirect) join="A.png";redirect_src="B.png" (i.e.: B redirects to A)
			redirect_src = "";
//			else {	// redirect; make sure extension matches; EX: A.png redirects to B.png; lnki_ext will be .png (the lnki's ext); should be .png (the actual file's ext)
//				Xof_ext join_ext = Xof_ext_.new_by_ttl_(Bry_.new_u8(join_ttl));
//				lnki_ext = join_ext.Id();
//			}
		lnki_ext = orig_ext_id;
		orig_media_type_tid = Xof_media_type.Xto_byte(orig_media_type);
		if (	Xof_lnki_time.Null_n(lnki_thumbtime)				// thumbtime defined
			&&	orig_media_type_tid != Xof_media_type.Tid_video 	// video can have thumbtime
			)
			lnki_thumbtime = Xof_lnki_time.Null;					// set thumbtime to NULL; actually occurs for one file: [[File:Crash.arp.600pix.jpg|thumb|thumbtime=2]]
		if (	Xof_lnki_page.Null_n(lnki_page)
			&&	!Xof_ext_.Id_supports_page(orig_ext_id))			// djvu / pdf can have page parameters, which are currently being stored in thumbtime; DATE:2014-01-18
			lnki_page = Xof_lnki_page.Null;
		if (orig_page_id == -1) {	// no orig found (i.e.: not in local's / remote's image.sql);
			chk_tid = Chk_tid_orig_page_id_is_null;
			return false;
		}
		if (orig_media_type_tid == Xof_media_type.Tid_audio) {		// ignore: audio will never have thumbs
			chk_tid = Chk_tid_orig_media_type_is_audio;
			return false;
		}
		if (orig_w <= 0) {	// ignore files that have an orig_w of 0; note that ogg files that are sometimes flagged as VIDEO; EX:2009_10_08_Marc_Randazza_interview.ogg; DATE:2014-08-20
			chk_tid = Chk_tid_orig_w_is_0;
			return false;
		}
		if (lnki_ext == Xof_ext_.Id_mid) {	// NOTE: .mid does not have orig_media_type of "AUDIO"
			chk_tid = Chk_tid_orig_media_type_is_audio;
			return false;
		}
		if (lnki_src_tid == Xop_file_logger_.Tid__media) {
			chk_tid = Chk_tid_ns_is_media;
			return false;
		}
		int upright_patch = Xof_patch_upright_tid_.Tid_all;	// all future blds will have upright_patch
		img_size.Html_size_calc(Xof_exec_tid.Tid_wiki_page, lnki_w, lnki_h, lnki_type, upright_patch, lnki_upright, lnki_ext, orig_w, orig_h, Xof_img_size.Thumb_width_img);
		return true;
	}
	public void Insert(Db_stmt stmt, Xof_img_size img_size) {
		boolean file_is_orig = img_size.File_is_orig();
		int file_w = img_size.File_w();
		int file_h = img_size.File_h();
		if (file_is_orig)
			file_w = file_h = -1;
		Xob_xfer_temp_tbl.Insert(stmt, lnki_id, lnki_tier_id, lnki_page_id, orig_repo, orig_page_id, join_ttl, redirect_src, lnki_ext, lnki_type, orig_media_type
			, file_is_orig, orig_w, orig_h, file_w, file_h, img_size.Html_w(), img_size.Html_h(), lnki_w, lnki_h
			, lnki_upright, lnki_thumbtime, lnki_page, lnki_count);
	}
}
