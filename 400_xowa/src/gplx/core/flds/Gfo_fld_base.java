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
package gplx.core.flds; import gplx.*; import gplx.core.*;
public class Gfo_fld_base {
	public byte Row_dlm() {return row_dlm;} public Gfo_fld_base Row_dlm_(byte v) {row_dlm = v; return this;} protected byte row_dlm = Byte_ascii.Nl;
	public byte Fld_dlm() {return fld_dlm;} public Gfo_fld_base Fld_dlm_(byte v) {fld_dlm = v; return this;} protected byte fld_dlm = Byte_ascii.Pipe;
	public byte Escape_dlm() {return escape_dlm;} public Gfo_fld_base Escape_dlm_(byte v) {escape_dlm = v; return this;} protected byte escape_dlm = Byte_ascii.Tilde;
	public byte Quote_dlm() {return quote_dlm;} public Gfo_fld_base Quote_dlm_(byte v) {quote_dlm = v; return this;} protected byte quote_dlm = Byte_ascii.Null;
	public Gfo_fld_base Escape_reg(byte b) {return Escape_reg(b, b);} 
	public byte[] Escape_decode() {return decode_regy;}
	public Gfo_fld_base Escape_reg(byte key, byte val) {encode_regy[key] = val; decode_regy[val] = key; return this;} protected byte[] decode_regy = new byte[256]; protected byte[] encode_regy = new byte[256];
	public Gfo_fld_base Escape_clear() {
		for (int i = 0; i < 256; i++)
			decode_regy[i] = Byte_ascii.Null;
		for (int i = 0; i < 256; i++)
			encode_regy[i] = Byte_ascii.Null;
		return this;
	} 
	Gfo_fld_base Ini_common() {
		return	 Escape_reg(Byte_ascii.Nl, Byte_ascii.Ltr_n).Escape_reg(Byte_ascii.Tab, Byte_ascii.Ltr_t).Escape_reg(Byte_ascii.Cr, Byte_ascii.Ltr_r)
				.Escape_reg(Byte_ascii.Backfeed, Byte_ascii.Ltr_b);	// .Escape_reg(Byte_ascii.Null, Byte_ascii.Num_0)
	}
	protected Gfo_fld_base Ctor_xdat_base() {
		return 	Escape_clear().Ini_common()
				.Fld_dlm_(Byte_ascii.Pipe).Row_dlm_(Byte_ascii.Nl).Escape_dlm_(Byte_ascii.Tilde).Quote_dlm_(Byte_ascii.Null)
				.Escape_reg(Byte_ascii.Pipe, Byte_ascii.Ltr_p).Escape_reg(Byte_ascii.Tilde);
	}
	protected Gfo_fld_base Ctor_sql_base() {
		return	Escape_clear().Ini_common()
				.Fld_dlm_(Byte_ascii.Comma).Row_dlm_(Byte_ascii.Paren_end).Escape_dlm_(Byte_ascii.Backslash).Quote_dlm_(Byte_ascii.Apos)
				.Escape_reg(Byte_ascii.Backslash).Escape_reg(Byte_ascii.Quote).Escape_reg(Byte_ascii.Apos); // , Escape_eof = Bry_.new_a7("\\Z") 
	}
}
