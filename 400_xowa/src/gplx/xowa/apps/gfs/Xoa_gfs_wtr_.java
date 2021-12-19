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
package gplx.xowa.apps.gfs;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
public class Xoa_gfs_wtr_ {
	public static byte[] Escape(String v) {return Escape(BryUtl.NewU8(v));}
	public static byte[] Escape(byte[] v) {
		return BryFind.FindFwd(v, AsciiByte.Apos) == BryFind.NotFound ? v : BryUtlByWtr.Replace(v, AsciiByte.AposBry, Bry__apos_escaped);
	}	private static final byte[] Bry__apos_escaped = BryUtl.NewA7("''");
	public static void Write_prop(BryWtr bfr, byte[] prop, byte[] val) {
		bfr.Add(prop).Add(Bry__val_bgn).Add(Xoa_gfs_wtr_.Escape(val)).Add(Bry__val_end);	// EX: "a_('b');\n"
	}	private static final byte[] Bry__val_bgn = BryUtl.NewA7("_('"), Bry__val_end = BryUtl.NewA7("');\n");
	public static String Write_func_chain(String... ary) {	// EX: "a.b.c"
		BryWtr bfr = BryWtr.New();
		try {
			int len = ary.length;
			for (int i = 0; i < len; ++i) {
				if (i != 0) bfr.AddByte(AsciiByte.Dot);
				bfr.AddStrU8(ary[i]);
			}
			return bfr.ToStrAndClear();
		} finally {bfr.MkrRls();}
	}
}
