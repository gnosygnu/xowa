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
package gplx.xowa.addons.bldrs.files.missing_origs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.bldrs.files.missing_origs.*;
import gplx.xowa.files.*;
public class Xowmf_imageinfo_item {
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte Orig_repo() {return orig_repo;} private byte orig_repo;
	public int Orig_page_id() {return orig_page_id;} private int orig_page_id = -1;
	public int Orig_file_id() {return orig_page_id;}
	public byte[] Orig_file_ttl() {return orig_file_ttl;} private byte[] orig_file_ttl;
	public byte[] Orig_timestamp() {return orig_timestamp;} private byte[] orig_timestamp;
	public int Orig_size() {return orig_size;} private int orig_size;
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public byte[] Orig_minor_mime() {return orig_minor_mime;} private byte[] orig_minor_mime;
	public byte[] Orig_media_type() {return orig_media_type;} private byte[] orig_media_type;
	public byte[] Orig_redirect_ttl() {return orig_redirect_ttl;} private byte[] orig_redirect_ttl;
	public int Lnki_ext() {return lnki_ext;} private int lnki_ext;
	public int Orig_file_ext() {return orig_file_ext;} private int orig_file_ext;
	public int Orig_redirect_ext() {return orig_redirect_ext;} private int orig_redirect_ext;
	public int Orig_redirect_id() {return orig_redirect_id;} private int orig_redirect_id;

	public Xowmf_imageinfo_item Init_by_orig_tbl(byte[] lnki_ttl) {
		this.lnki_ttl = lnki_ttl;
		return this;
	}
	public Xowmf_imageinfo_item Init_by_api_page(byte orig_repo, int orig_page_id, byte[] orig_file_ttl, int orig_size, int orig_w, int orig_h, byte[] orig_media_type, byte[] orig_minor_mime, byte[] orig_timestamp) {
		this.orig_repo = orig_repo;
		this.orig_page_id = orig_page_id;
		this.orig_file_ttl = Normalize_ttl(orig_file_ttl);
		this.orig_file_ext = Xof_ext_.new_by_ttl_(orig_file_ttl).Id();
		this.orig_size = orig_size;
		this.orig_w = orig_w;
		this.orig_h = orig_h;
		this.orig_media_type = orig_media_type;
		this.orig_minor_mime = Normalize_minor_mime(orig_minor_mime);
		this.orig_timestamp = Normalize_timestamp(orig_timestamp); 
		return this;
	}
	public Xowmf_imageinfo_item Init_by_api_redirect(byte[] from, byte[] to) {
		this.lnki_ttl = Normalize_ttl(from);
		this.orig_redirect_ttl = Normalize_ttl(to);
		// page_id is always redirect_id
		this.orig_redirect_id = orig_page_id;
		// orig_page_id is unknown; need to make 2nd call;
		this.orig_page_id = -987;
		return this;
	}
	public void Copy_api_props(Xowmf_imageinfo_item src) {
		// page nde
		this.orig_repo = src.orig_repo;
		this.orig_page_id = src.orig_page_id;
		this.orig_file_ttl = src.orig_file_ttl;
		this.orig_file_ext = src.orig_file_ext;
		this.orig_size = src.orig_size;
		this.orig_w = src.orig_w;
		this.orig_h = src.orig_h;
		this.orig_media_type = src.orig_media_type;
		this.orig_minor_mime = src.orig_minor_mime;
		this.orig_timestamp = src.orig_timestamp;

		// revision nde
		this.orig_redirect_ttl = src.orig_redirect_ttl;

		// set ext_ids
		this.lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl).Id();
		this.orig_redirect_ext = Xof_ext_.new_by_ttl_(orig_redirect_ttl).Id();
	}
	public static byte[] Normalize_ttl(byte[] v) {
		// remove "File:"
		if (Bry_.Has_at_bgn(v, Xowmf_imageinfo_api.FILE_NS_PREFIX)) {
			v = Bry_.Mid(v, Xowmf_imageinfo_api.FILE_NS_PREFIX.length);
		}
		else {
			throw Err_.new_wo_type("wmf_api does not start with 'File:'", "title", v);
		}

		// convert spaces to unders
		v = Xoa_ttl.Replace_spaces(v);

		return v;
	}
	public static byte[] Normalize_minor_mime(byte[] src) {
		// convert "image/svg+xml" to "svg+xml"
		int src_len = src.length;
		int slash_pos = Bry_find_.Find_fwd(src, Byte_ascii.Slash, 0, src_len);
		if (slash_pos == Bry_find_.Not_found) {
			throw Err_.new_wo_type("wmf_api minor_mime does not have slash;", "minor_mime", src);
		}
		return Bry_.Mid(src, slash_pos + 1, src_len);
	}
	public static byte[] Normalize_timestamp(byte[] src) {
		// convert 2017-03-06T08:09:10Z to 20170306080910
		byte[] rv = new byte[14];
		int rv_idx = 0;
		for (byte b : src) {
			if (Byte_ascii.Is_num(b)) {
				rv[rv_idx++] = b;
			}
		}
		return rv;
	}
}
