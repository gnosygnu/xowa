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
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public interface Json_grp extends Json_itm {
	void Src_end_(int v);
	int Len();
	Json_itm Get_at(int i);
	Json_nde Get_as_nde(int i);
	void Add(Json_itm itm);
}
class Json_grp_ {
	public static final Json_grp[] Ary_empty = new Json_grp[0];
	public static void Print_nl(BryWtr bfr) {								// \n\n can be caused by nested groups (EX: "[[]]"); only print 1
		if (bfr.Bry()[bfr.Len() - 1] != AsciiByte.Nl)
			bfr.AddByteNl();
	}
	public static void Print_indent(BryWtr bfr, int depth) {
		if (depth > 0) bfr.AddByteRepeat(AsciiByte.Space, depth * 2);	// indent
	}
}
