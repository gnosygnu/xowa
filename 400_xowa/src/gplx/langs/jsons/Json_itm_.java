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
package gplx.langs.jsons;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.BoolUtl;
public class Json_itm_ {
	public static final Json_itm[] Ary_empty = new Json_itm[0];
	public static final byte Tid__unknown = 0, Tid__null = 1, Tid__bool = 2, Tid__int = 3, Tid__long = 4, Tid__decimal = 5, Tid__str = 6, Tid__kv = 7, Tid__ary = 8, Tid__nde = 9;
	public static final byte[] Bry__true = BoolUtl.TrueBry, Bry__false = BoolUtl.FalseBry, Bry__null = ObjectUtl.NullBry;
	public static byte[] To_bry(BryWtr bfr, Json_itm itm) {
		if (itm == null) return BryUtl.Empty;
		itm.Print_as_json(bfr, 0);
		return bfr.ToBryAndClear();
	}
}
