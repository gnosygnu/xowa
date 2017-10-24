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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_gfs_wtr_ {
	public static byte[] Escape(String v) {return Escape(Bry_.new_u8(v));}
	public static byte[] Escape(byte[] v) {
		return Bry_find_.Find_fwd(v, Byte_ascii.Apos) == Bry_find_.Not_found ? v : Bry_.Replace(v, Byte_ascii.Apos_bry, Bry__apos_escaped);
	}	private static final    byte[] Bry__apos_escaped = Bry_.new_a7("''");
	public static void Write_prop(Bry_bfr bfr, byte[] prop, byte[] val) {
		bfr.Add(prop).Add(Bry__val_bgn).Add(Xoa_gfs_wtr_.Escape(val)).Add(Bry__val_end);	// EX: "a_('b');\n"
	}	private static final    byte[] Bry__val_bgn = Bry_.new_a7("_('"), Bry__val_end = Bry_.new_a7("');\n");
	public static String Write_func_chain(String... ary) {	// EX: "a.b.c"
		Bry_bfr bfr = Bry_bfr_.New();
		try {
			int len = ary.length;
			for (int i = 0; i < len; ++i) {
				if (i != 0) bfr.Add_byte(Byte_ascii.Dot);
				bfr.Add_str_u8(ary[i]);				
			}
			return bfr.To_str_and_clear();
		} finally {bfr.Mkr_rls();}
	}
}
