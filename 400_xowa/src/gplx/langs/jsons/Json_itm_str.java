/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Byte_ascii;
import gplx.String_;
import gplx.core.intls.Utf16_;

public class Json_itm_str extends Json_itm_base {
	private final boolean exact;
	private final Json_doc doc;
	private String data_str;
	private byte[] data_bry = null;
	private byte[] src = null;

	public Json_itm_str(Json_doc doc, int src_bgn, int src_end, boolean exact) {
		this.Ctor(src_bgn + 1, src_end - 1);
		this.doc = doc;
		this.exact = exact;
	}
	public Json_itm_str(byte[] src, boolean exact) {
		this.doc = null;
		this.src = src;
		this.exact = exact;
	}
	@Override public byte Tid() {return Json_itm_.Tid__str;}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		bfr.Add_byte(Byte_ascii.Quote);
		byte[] src_doc;
		int bgn;
		int end;
		if (src == null) {
			src_doc = doc.Src();
			bgn = this.Src_bgn();
			end = this.Src_end();
		}
		else {
			src_doc = src;
			bgn = 0;
			end = src.length;
		}
		gplx.langs.htmls.Gfh_utl.Escape_html_to_bfr(bfr, src_doc, bgn, end, true, true, true, true, false);	// false to apos for backwards compatibility
		bfr.Add_byte(Byte_ascii.Quote);
	}
	@Override public Object Data() {return this.Data_as_str();}
	public void Overwrite_bry(byte[] v) {data_bry = v;} //needed by MapLink/MapFrame
	public String Data_as_str() {
		if (data_str == null) {
			if (data_bry == null)
				data_bry = Data_make_bry();
			data_str = String_.new_u8(data_bry);
		}
		return data_str;
	}
	@Override public byte[] Data_bry() {if (data_bry == null) data_bry = Data_make_bry(); return data_bry;}
	@Override public boolean Data_eq(byte[] comp) {
		if (exact) return Bry_.Eq(doc.Src(), this.Src_bgn(), this.Src_end(), comp);
		if (data_bry == null) data_bry = Data_make_bry();
		return Bry_.Match(data_bry, comp);
	}
	private byte[] Data_make_bry() {
		byte[] src_doc;
		int bgn;
		int end;
		Bry_bfr bfr;
		byte[] utf8_bry;
		if (src == null) {
			src_doc = doc.Src();
			bgn = this.Src_bgn();
			end = this.Src_end();
			if (exact) return Bry_.Mid(src_doc, bgn, end);
			bfr = doc.Bfr();
			utf8_bry = doc.Tmp_u8_bry();
		}
		else {
			if (exact) return src;
			src_doc = src;
			bgn = 0;
			end = src.length;
			bfr = Bry_bfr_.New();
			utf8_bry = new byte[6];
		}
		for (int i = bgn; i < end; i++) {
			byte b = src_doc[i];
			switch (b) {
				case Byte_ascii.Backslash:
					b = src_doc[++i];
					switch (b) {	// NOTE: must properly unescape chars; EX:wd.q:2; DATE:2014-04-23
						case Byte_ascii.Ltr_t:				bfr.Add_byte(Byte_ascii.Tab); break;
						case Byte_ascii.Ltr_n:				bfr.Add_byte(Byte_ascii.Nl); break;
						case Byte_ascii.Ltr_r:				bfr.Add_byte(Byte_ascii.Cr); break;
						case Byte_ascii.Ltr_b:				bfr.Add_byte(Byte_ascii.Backfeed); break;
						case Byte_ascii.Ltr_f:				bfr.Add_byte(Byte_ascii.Formfeed); break;
						case Byte_ascii.Ltr_u:
							i += 1; // +1 to skip "u"
							int utf8_val = gplx.core.encoders.Hex_utl_.Parse_or(src_doc, i, i + 4, -1);
							// check for UTF surrogate-pairs; ISSUE#:487; DATE:2019-06-02
							// hi: 0xD800-0xDBFF; 55,296-56,319
							if (utf8_val >= Utf16_.Surrogate_hi_bgn && utf8_val <= Utf16_.Surrogate_hi_end) {
								int lo_bgn = i + 4;   // +4 to skip 4 hex-dec chars
								if (lo_bgn + 6 <= end // +6 to handle encoded String; EX: '\u0022'
									&& src_doc[lo_bgn]     == Byte_ascii.Backslash
									&& src_doc[lo_bgn + 1] == Byte_ascii.Ltr_u) {
									lo_bgn = lo_bgn + 2; // +2 to skip '\' and 'u'
									int lo = gplx.core.encoders.Hex_utl_.Parse_or(src_doc, lo_bgn, lo_bgn + 4, -1);
									// lo: 0xDC00-0xDFFF; 56,320-57,343
									if (lo >= Utf16_.Surrogate_lo_bgn && lo <= Utf16_.Surrogate_lo_end) {
										utf8_val = Utf16_.Surrogate_merge(utf8_val, lo);
										i += 6; // +6 to skip entire lo-String; EX: '\u0022'
									}
								}
							}
							int len = gplx.core.intls.Utf16_.Encode_int(utf8_val, utf8_bry, 0);
							bfr.Add_mid(utf8_bry, 0, len);
							i += 3; // +3 b/c for-loop will do another +1 to bring total to 4; EX: '0022'
							break;
						case Byte_ascii.Backslash:
						case Byte_ascii.Slash:
						default:
							bfr.Add_byte(b);	break;	// \?		" \ / b f n r t
					}
					break;
				default:
					bfr.Add_byte(b);
					break;
			}
		}
		return bfr.To_bry_and_clear();
	}
}