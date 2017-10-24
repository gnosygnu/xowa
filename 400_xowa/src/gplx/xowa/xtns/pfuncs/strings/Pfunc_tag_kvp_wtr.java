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
package gplx.xowa.xtns.pfuncs.strings; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
class Pfunc_tag_kvp_wtr {
	private int key_bgn, key_end;
	private int val_bgn, val_end;
	public void Write_as_html_atr(Bry_bfr tmp, byte[] kvp_bry) {
		Parse(kvp_bry);

		// write as html
		if (val_bgn == -1) return;	// ignore atrs with empty vals: EX:{{#tag:ref||group=}} PAGE:ru.w:Колчак,_Александр_Васильевич DATE:2014-07-03
		if (key_bgn != -1)
			tmp.Add(Bry_.Mid(kvp_bry, key_bgn, key_end));
		if (val_bgn != -1) {
			if (key_bgn != -1)
				tmp.Add_byte(Byte_ascii.Eq);
			tmp.Add_byte(Byte_ascii.Quote);
			gplx.langs.htmls.encoders.Gfo_url_encoder_.Id.Encode(tmp, kvp_bry, val_bgn, val_end);// PURPOSE: escape html in atrs; PAGE:fr.v:France; DATE:2017-06-01
			tmp.Add_byte(Byte_ascii.Quote);
		}
	}
	private void Parse(byte[] src) {
		this.key_bgn = this.key_end = this.val_bgn = this.val_end = -1;	// NOTE: must clear; DATE:2014-07-20
		int itm_bgn = -1, itm_end = -1, src_len = src.length;
		byte quote_byte = Byte_ascii.Null;
		boolean mode_is_key = true;
		for (int i = 0; i < src_len; ++i) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Eq:
					if (mode_is_key) {
						mode_is_key = false;
						if (itm_end == -1) itm_end = i;
						this.key_bgn = itm_bgn;
						this.key_end = itm_end;
						itm_bgn = itm_end = -1;
					}					
					break;
				// quote-char encountered ...
				// NOTE: quotes cannot be escaped; also, in case of multiple quotes (a="b"c") regx uses first two quotes; REF:MW:CoreParserFunctions.php|tagObj
				case Byte_ascii.Quote:
				case Byte_ascii.Apos:
					if (itm_bgn == -1) {	// ... quote hasn't started; start quote
						itm_bgn = i + 1;
						quote_byte = b;
					}
					else if (itm_end == -1	// ... quote has started and quote hasn't ended; note that this ends quote immediately; EX: 'id="a"b"' -> 'id=a' x> 'id=a"b'
						&& b == quote_byte)	// handle alternating quotes; EX: id="a'b" -> id=a'b x> id=a; PAGE:en.s:The_formative_period_in_Colby%27s_history; DATE:2016-06-23
						itm_end = i;
					break;
				case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl:// NOTE: do not need to handle ws, b/c argBldr will trim it EX: {{#tag|a| b = c }}; " b " and " c " are automatically trimmed
					break;
				default:
					if		(itm_bgn == -1)	itm_bgn = i;
					break;
			}
		}
		if (itm_end == -1) itm_end = src_len;
		this.val_bgn = itm_bgn;
		this.val_end = itm_end;
	}
}
