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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_itm_str extends Json_itm_base {
	private final    boolean exact; private final    Json_doc doc;
	private String data_str; private byte[] data_bry = null;
	public Json_itm_str(Json_doc doc, int src_bgn, int src_end, boolean exact) {this.Ctor(src_bgn + 1, src_end - 1); this.doc = doc; this.exact = exact;}
	@Override public byte Tid() {return Json_itm_.Tid__str;}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		bfr.Add_byte(Byte_ascii.Quote);
		gplx.langs.htmls.Gfh_utl.Escape_html_to_bfr(bfr, doc.Src(), this.Src_bgn(), this.Src_end(), true, true, true, true, false);	// false to apos for backwards compatibility
		bfr.Add_byte(Byte_ascii.Quote);
	}
	@Override public Object Data() {return this.Data_as_str();}
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
		byte[] src = doc.Src(); int bgn = this.Src_bgn(), end = this.Src_end();
		if (exact) return Bry_.Mid(src, bgn, end);
		Bry_bfr bfr = doc.Bfr();
		byte[] utf8_bry = doc.Tmp_u8_bry();
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Backslash:
					b = src[++i];
					switch (b) {	// NOTE: must properly unescape chars; EX:wd.q:2; DATE:2014-04-23
						case Byte_ascii.Ltr_t:				bfr.Add_byte(Byte_ascii.Tab); break;
						case Byte_ascii.Ltr_n:				bfr.Add_byte(Byte_ascii.Nl); break;
						case Byte_ascii.Ltr_r:				bfr.Add_byte(Byte_ascii.Cr); break;
						case Byte_ascii.Ltr_b:				bfr.Add_byte(Byte_ascii.Backfeed); break;
						case Byte_ascii.Ltr_f:				bfr.Add_byte(Byte_ascii.Formfeed); break;
						case Byte_ascii.Ltr_u:
							int utf8_val = gplx.core.encoders.Hex_utl_.Parse_or(src, i + 1, i + 5, -1);
							int len = gplx.core.intls.Utf16_.Encode_int(utf8_val, utf8_bry, 0);
							bfr.Add_mid(utf8_bry, 0, len);
							i += 4;
							break;	// \uFFFF	4 hex-dec
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
