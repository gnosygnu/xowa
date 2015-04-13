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
package gplx.web.js; import gplx.*; import gplx.web.*;
public class Js_wtr {
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public byte Quote_char() {return quote_char;} public Js_wtr Quote_char_(byte v) {quote_char = v; return this;} private byte quote_char = Byte_ascii.Quote;
	public void Clear() {bfr.Clear();}
	public String Xto_str()						{return bfr.Xto_str();}
	public String Xto_str_and_clear()			{return bfr.Xto_str_and_clear();}
	public Js_wtr Add_comma()					{bfr.Add_byte(Byte_ascii.Comma); return this;}
	public Js_wtr Add_paren_bgn()				{bfr.Add_byte(Byte_ascii.Paren_bgn); return this;}
	public Js_wtr Add_paren_end()				{bfr.Add_byte(Byte_ascii.Paren_end); return this;}
	public Js_wtr Add_brack_bgn()				{bfr.Add_byte(Byte_ascii.Brack_bgn); return this;}
	public Js_wtr Add_brack_end()				{bfr.Add_byte(Byte_ascii.Brack_end); return this;}
	public Js_wtr Add_str(byte[] v)				{bfr.Add(v); return this;}
	public Js_wtr Add_str(String v)				{bfr.Add_str(v); return this;}
	public Js_wtr Add_paren_end_semic()			{bfr.Add_byte(Byte_ascii.Paren_end); bfr.Add_byte(Byte_ascii.Semic); return this;}
	public Js_wtr Add_str_arg(int i, byte[] bry){
		if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
		Add_str_quote(bry);
		return this;
	}
	public Js_wtr Add_str_quote(byte[] bry) {
		bfr.Add_byte(quote_char);
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			if (b == quote_char) bfr.Add_byte(Byte_ascii.Backslash);
			bfr.Add_byte(b);
		}
		bfr.Add_byte(quote_char);
		return this;
	}
	public Js_wtr Add_str_quote_html(byte[] bry) {
		bfr.Add_byte(quote_char);
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			if (b == quote_char) {	// double up quotes
				bfr.Add_byte(Byte_ascii.Backslash);
				bfr.Add_byte(b);
			}
			else {
				switch (b) {
					case Byte_ascii.Backslash:			bfr.Add_byte_repeat(Byte_ascii.Backslash, 2); break;					// "\"	-> "\\"; needed else js will usurp \ as escape; EX: "\&" -> "&"; DATE:2014-06-24
					case Byte_ascii.NewLine:			bfr.Add_byte(Byte_ascii.Backslash).Add_byte(Byte_ascii.Ltr_n); break;	// "\n" -> "\\n"
					case Byte_ascii.CarriageReturn:		break;// skip
					default:							bfr.Add_byte(b); break;
				}
			}
		}
		bfr.Add_byte(quote_char);
		return this;
	}
}
