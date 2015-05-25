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
package gplx.core.flds; import gplx.*; import gplx.core.*;
public class Gfo_fld_base {
	public byte Row_dlm() {return row_dlm;} public Gfo_fld_base Row_dlm_(byte v) {row_dlm = v; return this;} protected byte row_dlm = Byte_ascii.NewLine;
	public byte Fld_dlm() {return fld_dlm;} public Gfo_fld_base Fld_dlm_(byte v) {fld_dlm = v; return this;} protected byte fld_dlm = Byte_ascii.Pipe;
	public byte Escape_dlm() {return escape_dlm;} public Gfo_fld_base Escape_dlm_(byte v) {escape_dlm = v; return this;} protected byte escape_dlm = Byte_ascii.Tilde;
	public byte Quote_dlm() {return quote_dlm;} public Gfo_fld_base Quote_dlm_(byte v) {quote_dlm = v; return this;} protected byte quote_dlm = Byte_ascii.Nil;
	public Gfo_fld_base Escape_reg(byte b) {return Escape_reg(b, b);} 
	public byte[] Escape_decode() {return decode_regy;}
	public Gfo_fld_base Escape_reg(byte key, byte val) {encode_regy[key] = val; decode_regy[val] = key; return this;} protected byte[] decode_regy = new byte[256]; protected byte[] encode_regy = new byte[256];
	public Gfo_fld_base Escape_clear() {
		for (int i = 0; i < 256; i++)
			decode_regy[i] = Byte_ascii.Nil;
		for (int i = 0; i < 256; i++)
			encode_regy[i] = Byte_ascii.Nil;
		return this;
	} 
	Gfo_fld_base Ini_common() {
		return	 Escape_reg(Byte_ascii.NewLine, Byte_ascii.Ltr_n).Escape_reg(Byte_ascii.Tab, Byte_ascii.Ltr_t).Escape_reg(Byte_ascii.CarriageReturn, Byte_ascii.Ltr_r)
				.Escape_reg(Byte_ascii.Backfeed, Byte_ascii.Ltr_b);	// .Escape_reg(Byte_ascii.Nil, Byte_ascii.Num_0)
	}
	protected Gfo_fld_base Ctor_xdat_base() {
		return 	Escape_clear().Ini_common()
				.Fld_dlm_(Byte_ascii.Pipe).Row_dlm_(Byte_ascii.NewLine).Escape_dlm_(Byte_ascii.Tilde).Quote_dlm_(Byte_ascii.Nil)
				.Escape_reg(Byte_ascii.Pipe, Byte_ascii.Ltr_p).Escape_reg(Byte_ascii.Tilde);
	}
	protected Gfo_fld_base Ctor_sql_base() {
		return	Escape_clear().Ini_common()
				.Fld_dlm_(Byte_ascii.Comma).Row_dlm_(Byte_ascii.Paren_end).Escape_dlm_(Byte_ascii.Backslash).Quote_dlm_(Byte_ascii.Apos)
				.Escape_reg(Byte_ascii.Backslash).Escape_reg(Byte_ascii.Quote).Escape_reg(Byte_ascii.Apos); // , Escape_eof = Bry_.new_u8("\\Z") 
	}
}
