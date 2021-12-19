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
package gplx.core.flds;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_fld_base {
	public byte Row_dlm() {return row_dlm;} public Gfo_fld_base Row_dlm_(byte v) {row_dlm = v; return this;} protected byte row_dlm = AsciiByte.Nl;
	public byte Fld_dlm() {return fld_dlm;} public Gfo_fld_base Fld_dlm_(byte v) {fld_dlm = v; return this;} protected byte fld_dlm = AsciiByte.Pipe;
	public byte Escape_dlm() {return escape_dlm;} public Gfo_fld_base Escape_dlm_(byte v) {escape_dlm = v; return this;} protected byte escape_dlm = AsciiByte.Tilde;
	public byte Quote_dlm() {return quote_dlm;} public Gfo_fld_base Quote_dlm_(byte v) {quote_dlm = v; return this;} protected byte quote_dlm = AsciiByte.Null;
	public Gfo_fld_base Escape_reg(byte b) {return Escape_reg(b, b);} 
	public byte[] Escape_decode() {return decode_regy;}
	public Gfo_fld_base Escape_reg(byte key, byte val) {encode_regy[key] = val; decode_regy[val] = key; return this;} protected byte[] decode_regy = new byte[256]; protected byte[] encode_regy = new byte[256];
	public Gfo_fld_base Escape_clear() {
		for (int i = 0; i < 256; i++)
			decode_regy[i] = AsciiByte.Null;
		for (int i = 0; i < 256; i++)
			encode_regy[i] = AsciiByte.Null;
		return this;
	} 
	Gfo_fld_base Ini_common() {
		return	 Escape_reg(AsciiByte.Nl, AsciiByte.Ltr_n).Escape_reg(AsciiByte.Tab, AsciiByte.Ltr_t).Escape_reg(AsciiByte.Cr, AsciiByte.Ltr_r)
				.Escape_reg(AsciiByte.Backfeed, AsciiByte.Ltr_b);	// .Escape_reg(Byte_ascii.Null, Byte_ascii.Num_0)
	}
	protected Gfo_fld_base Ctor_xdat_base() {
		return 	Escape_clear().Ini_common()
				.Fld_dlm_(AsciiByte.Pipe).Row_dlm_(AsciiByte.Nl).Escape_dlm_(AsciiByte.Tilde).Quote_dlm_(AsciiByte.Null)
				.Escape_reg(AsciiByte.Pipe, AsciiByte.Ltr_p).Escape_reg(AsciiByte.Tilde);
	}
	protected Gfo_fld_base Ctor_sql_base() {
		return	Escape_clear().Ini_common()
				.Fld_dlm_(AsciiByte.Comma).Row_dlm_(AsciiByte.ParenEnd).Escape_dlm_(AsciiByte.Backslash).Quote_dlm_(AsciiByte.Apos)
				.Escape_reg(AsciiByte.Backslash).Escape_reg(AsciiByte.Quote).Escape_reg(AsciiByte.Apos); // , Escape_eof = Bry_.new_a7("\\Z")
	}
}
