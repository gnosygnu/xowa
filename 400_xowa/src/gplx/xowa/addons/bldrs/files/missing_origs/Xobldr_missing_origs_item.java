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
package gplx.xowa.addons.bldrs.files.missing_origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.xowa.files.*;
class Xobldr_missing_origs_item {
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public int Page_id() {return page_id;} private int page_id;
	public byte Orig_repo() {return orig_repo;} private byte orig_repo;
	public int Orig_page_id() {return orig_page_id;} private int orig_page_id;
	public byte[] Orig_ttl() {return orig_ttl;} private byte[] orig_ttl;
	public byte[] Orig_timestamp() {return orig_timestamp;} private byte[] orig_timestamp;
	public long Orig_size() {return orig_size;} private long orig_size;
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public byte[] Orig_minor_mime() {return orig_minor_mime;} private byte[] orig_minor_mime;
	public byte[] Orig_media_type() {return orig_media_type;} private byte[] orig_media_type;
	public byte[] Orig_redirect_ttl() {return orig_redirect_ttl;} private byte[] orig_redirect_ttl;
	public int Lnki_ext() {return lnki_ext;} private int lnki_ext;
	public int Orig_redirect_ext() {return orig_redirect_ext;} private int orig_redirect_ext;

	public Xobldr_missing_origs_item Init_by_orig_tbl(byte[] lnki_ttl) {
		this.lnki_ttl = lnki_ttl;
		return this;
	}
	public Xobldr_missing_origs_item Init_by_api_page(byte orig_repo, int page_id, byte[] orig_ttl, byte[] orig_timestamp, long orig_size, int orig_w, int orig_h, byte[] orig_minor_mime, byte[] orig_media_type) {
		this.page_id = page_id;
		this.orig_page_id = page_id;
		this.orig_repo = orig_repo;
		this.orig_ttl = Normalize_ttl(orig_ttl);
		this.orig_timestamp = orig_timestamp;
		this.orig_size = orig_size;
		this.orig_w = orig_w;
		this.orig_h = orig_h;
		this.orig_minor_mime = orig_minor_mime;
		this.orig_media_type = orig_media_type;
		return this;
	}
	public Xobldr_missing_origs_item Init_by_api_redirect(byte[] from, byte[] to) {
		this.lnki_ttl = Normalize_ttl(from);
		this.orig_redirect_ttl = Normalize_ttl(to);
		return this;
	}
	private byte[] Normalize_ttl(byte[] v) {
		// remove "File:"
		if (Bry_.Has_at_bgn(v, Xobldr_missing_origs_wmfapi.FILE_NS_PREFIX)) {
			v = Bry_.Mid(v, Xobldr_missing_origs_wmfapi.FILE_NS_PREFIX.length);
		}
		else {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "wmf_api does not start with 'File:'; title=~{0}", v);
		}

		// convert spaces to unders
		v = Xoa_ttl.Replace_spaces(v);

		return v;
	}
	public void Copy_api_props(Xobldr_missing_origs_item src) {
		// page nde
		this.page_id = src.page_id;
		this.orig_page_id = src.orig_page_id;
		this.orig_ttl = src.orig_ttl;
		this.orig_timestamp = src.orig_timestamp;
		this.orig_size = src.orig_size;
		this.orig_w = src.orig_w;
		this.orig_h = src.orig_h;
		this.orig_minor_mime = src.orig_minor_mime;
		this.orig_media_type = src.orig_media_type;

		// revision nde
		this.orig_redirect_ttl = src.orig_redirect_ttl;

		// set ext_ids
		this.lnki_ext = Xof_ext_.new_by_ttl_(lnki_ttl).Id();
		this.orig_redirect_ext = Xof_ext_.new_by_ttl_(orig_redirect_ttl).Id();
	}
}
