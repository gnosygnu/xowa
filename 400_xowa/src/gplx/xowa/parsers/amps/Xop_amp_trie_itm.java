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
package gplx.xowa.parsers.amps; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_amp_trie_itm {
	public Xop_amp_trie_itm(byte tid, int char_int, byte[] xml_name_bry) {
		this.tid = tid;
		this.char_int = char_int;
		this.utf8_bry = gplx.intl.Utf16_.Encode_int_to_bry(char_int);
		this.xml_name_bry = xml_name_bry; 
		this.key_name_len = xml_name_bry.length - 2;	// 2 for & and ;
	}
	public byte		Tid()			{return tid;}			private byte tid;
	public int		Char_int()		{return char_int;}		private int char_int;			// val; EX: 160
	public byte[]	Utf8_bry()		{return utf8_bry;}		private byte[] utf8_bry;		// EX: new byte[] {192, 160}; (C2, A0)
	public byte[]	Xml_name_bry()	{return xml_name_bry;}	private byte[] xml_name_bry;	// EX: "&nbsp;"
	public int		Key_name_len()	{return key_name_len;}	private int key_name_len;		// EX: "nbsp".Len
	public void Print_to_html(Bry_bfr bfr) {				// EX: "&#160;"
		switch (char_int) {
			case Byte_ascii.Lt: case Byte_ascii.Gt: case Byte_ascii.Quote: case Byte_ascii.Amp:
				bfr.Add(xml_name_bry);	// NOTE: never write actual char; EX: "&lt;" should be written as "&lt;", not "<"
				break;
			default:
				bfr.Add(gplx.xowa.html.Xohp_title_wkr.Escape_bgn);	// &#
				bfr.Add_int_variable(char_int);						// 160
				bfr.Add_byte(Byte_ascii.Semic);						// ;
				break;
		}			
	}
	public static final byte Tid_name = 1, Tid_num_hex = 2, Tid_num_dec = 3;
	public static final int Char_int_null = -1;
}
