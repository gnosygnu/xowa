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
import gplx.objects.strings.AsciiByte;
import gplx.String_;
import gplx.core.intls.Utf16_;
import gplx.langs.htmls.Gfh_utl;

public class Json_itm_str extends Json_itm_base {
	private final Json_doc doc;
	private final int src_bgn, src_end;
	private String data_str;
	private byte[] data_bry;
	private boolean data_needs_making;
	private boolean escaped;

	private Json_itm_str(Json_doc doc, int src_bgn, int src_end, String data_str, boolean escaped) {
		this.doc = doc;
		this.src_bgn = src_bgn;
		this.src_end = src_end;
		this.data_str = data_str;
		this.data_bry = null;
		this.data_needs_making = true;
		this.escaped = escaped;
	}
	@Override public byte Tid() {return Json_itm_.Tid__str;}
	@Override public Object Data() {return this.Data_as_str();}
	public void Overwrite_bry(byte[] v) {
		this.data_bry = v; //needed by MapLink/MapFrame
		this.data_needs_making = false;
	}
	@Override public byte[] Data_bry() {
		if (data_bry == null) {
			data_bry = Data_make_bry();
		}
		return data_bry;
	}
	public String Data_as_str() {
		if (data_str == null) {
			data_bry = Data_make_bry();
			data_str = String_.new_u8(data_bry);
		}
		return data_str;
	}
	@Override public boolean Data_eq(byte[] comp) {
		return Bry_.Match(this.Data_bry(), comp);
	}
	private byte[] Data_make_bry() {
		// data already made; return it;
		if (!data_needs_making)
			return data_bry;

		// mark data as made
		this.data_needs_making = false;

		// get src, bgn, end, depending on whether or not itm is from jdoc or standalone
		byte[] src;
		int bgn;
		int end;
		if (doc == null) {
			src = Bry_.new_u8_safe(this.data_str);
			bgn = 0;
			end = src == null ? 0 : src.length;
		}
		else {
			src = doc.Src();
			bgn = src_bgn;
			end = src_end;
		}

		// not escaped -> return the src
		if (!escaped) {
			this.data_bry = Bry_.Mid(src, bgn, end);
			return data_bry;
		}

		// escaped; get some temp vars
		Bry_bfr bfr;
		byte[] utf8_bry;
		if (doc == null) {
			bfr = Bry_bfr_.New();
			utf8_bry = new byte[6];
		}
		else { // PERF:reuse bfr / bry on jdoc itself
			bfr = doc.Bfr();
			utf8_bry = doc.Tmp_u8_bry();
		}

		// loop and unescape
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.Backslash:
					b = src[++i];
					switch (b) {	// NOTE: must properly unescape chars; EX:wd.q:2; DATE:2014-04-23
						case AsciiByte.Ltr_t:				bfr.Add_byte(AsciiByte.Tab); break;
						case AsciiByte.Ltr_n:				bfr.Add_byte(AsciiByte.Nl); break;
						case AsciiByte.Ltr_r:				bfr.Add_byte(AsciiByte.Cr); break;
						case AsciiByte.Ltr_b:				bfr.Add_byte(AsciiByte.Backfeed); break;
						case AsciiByte.Ltr_f:				bfr.Add_byte(AsciiByte.Formfeed); break;
						case AsciiByte.Ltr_u:
							i += 1; // +1 to skip "u"
							int utf8_val = gplx.core.encoders.Hex_utl_.Parse_or(src, i, i + 4, -1);
							// check for UTF surrogate-pairs; ISSUE#:487; DATE:2019-06-02
							// hi: 0xD800-0xDBFF; 55,296-56,319
							if (utf8_val >= Utf16_.Surrogate_hi_bgn && utf8_val <= Utf16_.Surrogate_hi_end) {
								int lo_bgn = i + 4;   // +4 to skip 4 hex-dec chars
								if (lo_bgn + 6 <= end // +6 to handle encoded String; EX: '\u0022'
									&& src[lo_bgn]     == AsciiByte.Backslash
									&& src[lo_bgn + 1] == AsciiByte.Ltr_u) {
									lo_bgn = lo_bgn + 2; // +2 to skip '\' and 'u'
									int lo = gplx.core.encoders.Hex_utl_.Parse_or(src, lo_bgn, lo_bgn + 4, -1);
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
						case AsciiByte.Backslash:
						case AsciiByte.Slash:
						default:
							bfr.Add_byte(b);	break;	// \?		" \ / b f n r t
					}
					break;
				default:
					bfr.Add_byte(b);
					break;
			}
		}
		this.data_bry = bfr.To_bry_and_clear();
		return data_bry;
	}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		bfr.Add_byte(AsciiByte.Quote);
		byte[] data_bry = this.Data_bry();
		int data_len = data_bry.length;
		Gfh_utl.Escape_html_to_bfr(bfr, data_bry, 0, data_len, true, true, true, true, false);	// false to apos for backwards compatibility
		bfr.Add_byte(AsciiByte.Quote);
	}

	public static Json_itm_str NewByDoc(Json_doc doc, int src_bgn, int src_end, boolean escaped) {return new Json_itm_str(doc, src_bgn + 1, src_end - 1, null, escaped);}
	public static Json_itm_str NewByVal(String val) {return new Json_itm_str(null, -1, -1, val, false);}
}