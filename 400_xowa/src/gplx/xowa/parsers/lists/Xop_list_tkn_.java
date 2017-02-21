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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_list_tkn_ {
	public static final    byte[]
		  Hook_ul = new byte[] {Byte_ascii.Nl, Byte_ascii.Star}		, Hook_ol = new byte[] {Byte_ascii.Nl, Byte_ascii.Hash}
		, Hook_dt = new byte[] {Byte_ascii.Nl, Byte_ascii.Semic}	, Hook_dd = new byte[] {Byte_ascii.Nl, Byte_ascii.Colon};
	public static final byte List_itmTyp_null = 0, List_itmTyp_ul = Byte_ascii.Star, List_itmTyp_ol = Byte_ascii.Hash, List_itmTyp_dt = Byte_ascii.Semic, List_itmTyp_dd = Byte_ascii.Colon;
	public static final    String Str_li = "li", Str_ol = "ol", Str_ul = "ul", Str_dl = "dl", Str_dt = "dt", Str_dd = "dd";
	public static final    byte[] Byt_li = Bry_.new_a7(Str_li), Byt_ol = Bry_.new_a7(Str_ol), Byt_ul = Bry_.new_a7(Str_ul)
								, Byt_dl = Bry_.new_a7(Str_dl), Byt_dt = Bry_.new_a7(Str_dt), Byt_dd = Bry_.new_a7(Str_dd);
	public static byte[] XmlTag_lst(byte b) {
		switch (b) {
			case List_itmTyp_ul:	return Byt_ul;
			case List_itmTyp_ol:	return Byt_ol;
			case List_itmTyp_dt:
			case List_itmTyp_dd:	return Byt_dl;
			default:				throw Err_.new_unhandled(b);
		}
	}
	public static byte[] XmlTag_itm(byte b) {
		switch (b) {
			case List_itmTyp_ul:
			case List_itmTyp_ol:	return Byt_li;
			case List_itmTyp_dt:	return Byt_dt;
			case List_itmTyp_dd:	return Byt_dd;
			default:				throw Err_.new_unhandled(b);
		}
	}
	public static byte Char_lst(byte b) {
		switch (b) {
			case List_itmTyp_ul:	return Byte_ascii.Star;
			case List_itmTyp_ol:	return Byte_ascii.Hash;
			case List_itmTyp_dt:	return Byte_ascii.Semic;
			case List_itmTyp_dd:	return Byte_ascii.Colon;
			default:				throw Err_.new_unhandled(b);
		}
	}
}
