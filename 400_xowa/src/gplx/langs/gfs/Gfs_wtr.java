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
package gplx.langs.gfs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Gfs_wtr {		
	public byte Quote_char() {return quote_char;} public Gfs_wtr Quote_char_(byte v) {quote_char = v; return this;} private byte quote_char = AsciiByte.Apos;
	public BryWtr Bfr() {return bfr;} private BryWtr bfr = BryWtr.NewAndReset(255);
	public void Add_grp_bgn(byte[] key) {
		bfr.Add(key);							// key
		bfr.AddByte(AsciiByte.CurlyBgn);		// {
	}
	public void Add_grp_end(byte[] key) {
		bfr.AddByte(AsciiByte.CurlyEnd);		// }
	}
	public void Add_set_eq(byte[] key, byte[] val) {
		bfr.Add(key);							// key
		bfr.AddByteEq();						// =
		bfr.AddByte(quote_char);				// '
		Write_val(val);
		bfr.AddByte(quote_char);				// '
		bfr.AddByte(AsciiByte.Semic);			// ;
	}
	private void Write_val(byte[] bry) {
		int bry_len = bry.length;
		for (int i = 0; i < bry_len; i++) {
			byte b = bry[i];
			if (b == quote_char)	// byte is quote
				bfr.AddByte(b);	// double up
			bfr.AddByte(b);
		}
	}
}
