/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_list_tkn_ {
	public static final byte[]
		  Hook_ul = new byte[] {Byte_ascii.NewLine, Byte_ascii.Asterisk}, Hook_ol = new byte[] {Byte_ascii.NewLine, Byte_ascii.Hash}
		, Hook_dt = new byte[] {Byte_ascii.NewLine, Byte_ascii.Semic}	, Hook_dd = new byte[] {Byte_ascii.NewLine, Byte_ascii.Colon};
	public static final byte List_itmTyp_null = 0, List_itmTyp_ul = Byte_ascii.Asterisk, List_itmTyp_ol = Byte_ascii.Hash, List_itmTyp_dt = Byte_ascii.Semic, List_itmTyp_dd = Byte_ascii.Colon;
	public static final String Str_li = "li", Str_ol = "ol", Str_ul = "ul", Str_dl = "dl", Str_dt = "dt", Str_dd = "dd";
	public static final byte[] Byt_li = Bry_.new_a7(Str_li), Byt_ol = Bry_.new_a7(Str_ol), Byt_ul = Bry_.new_a7(Str_ul)
								, Byt_dl = Bry_.new_a7(Str_dl), Byt_dt = Bry_.new_a7(Str_dt), Byt_dd = Bry_.new_a7(Str_dd);
	public static byte[] XmlTag_lst(byte b) {
		switch (b) {
			case List_itmTyp_ul:	return Byt_ul;
			case List_itmTyp_ol:	return Byt_ol;
			case List_itmTyp_dt:
			case List_itmTyp_dd:	return Byt_dl;
			default:				throw Err_.unhandled(b);
		}
	}
	public static byte[] XmlTag_itm(byte b) {
		switch (b) {
			case List_itmTyp_ul:
			case List_itmTyp_ol:	return Byt_li;
			case List_itmTyp_dt:	return Byt_dt;
			case List_itmTyp_dd:	return Byt_dd;
			default:				throw Err_.unhandled(b);
		}
	}
	public static byte Char_lst(byte b) {
		switch (b) {
			case List_itmTyp_ul:	return Byte_ascii.Asterisk;
			case List_itmTyp_ol:	return Byte_ascii.Hash;
			case List_itmTyp_dt:	return Byte_ascii.Semic;
			case List_itmTyp_dd:	return Byte_ascii.Colon;
			default:				throw Err_.unhandled(b);
		}
	}
}
