/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.htmls.entitys;
import gplx.types.basics.strings.unicodes.Utf16Utl;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Gfh_entity_itm {    // TS:immutable
	public Gfh_entity_itm(byte tid, int char_int, byte[] xml_name_bry) {
		this.tid = tid;
		this.char_int = char_int;
		this.u8_bry = Utf16Utl.EncodeIntToBry(char_int);
		this.xml_name_bry = xml_name_bry; 
		this.key_name_len = xml_name_bry.length - 2;    // 2 for & and ;
	}
	public byte        Tid()            {return tid;}            private final byte tid;
	public int        Char_int()        {return char_int;}        private final int char_int;            // val; EX: 160
	public byte[]    U8_bry()        {return u8_bry;}        private final byte[] u8_bry;            // EX: new byte[] {192, 160}; (C2, A0)
	public byte[]    Xml_name_bry()    {return xml_name_bry;}    private final byte[] xml_name_bry;    // EX: "&nbsp;"
	public int        Key_name_len()    {return key_name_len;}    private final int key_name_len;        // EX: "nbsp".Len

	public void Print_ncr(BryWtr bfr) {
		switch (char_int) {
			case AsciiByte.Lt: case AsciiByte.Gt: case AsciiByte.Quote: case AsciiByte.Amp:
				bfr.Add(xml_name_bry);                            // NOTE: never write actual char; EX: "&lt;" should be written as "&lt;", not "<"
				break;
			default:
				bfr.Add(Escape_bgn);            // &#
				bfr.AddIntVariable(char_int);    // 160
				bfr.AddByte(AsciiByte.Semic);    // ;
				break;
		}           
	}
	public void Print_literal(BryWtr bfr) {
		switch (char_int) {
			case AsciiByte.Lt:            bfr.Add(HtmlEntityCodes.LtBry); break; // NOTE: never write actual char; EX: "&lt;" should be written as "&lt;", not "<"; MW does same; DATE:2014-11-07
			case AsciiByte.Gt:            bfr.Add(HtmlEntityCodes.GtBry); break;
			case AsciiByte.Quote:        bfr.Add(HtmlEntityCodes.QuoteBry); break;
			case AsciiByte.Amp:        bfr.Add(HtmlEntityCodes.AmpBry); break;
			default:                    bfr.Add(u8_bry); break;                // write literal; EX: "[" not "&#91;"
		}           
	}
	private static final byte[] Escape_bgn = BryUtl.NewA7("&#");
	public static final byte Tid_name_std = 1, Tid_name_xowa = 2, Tid_num_hex = 3, Tid_num_dec = 4;
	public static final int Char_int_null = -1;
}
