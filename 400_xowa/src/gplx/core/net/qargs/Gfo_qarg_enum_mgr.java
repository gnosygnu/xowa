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
package gplx.core.net.qargs; import gplx.*; import gplx.core.*; import gplx.core.net.*;
public class Gfo_qarg_enum_mgr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public Gfo_qarg_enum_mgr(Gfo_qarg_enum_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_enum_itm itm = ary[i];
			hash.Add_bry_obj(itm.Key(), itm);
		}
	}
	public Gfo_qarg_enum_itm Get(byte[] key) {return (Gfo_qarg_enum_itm)hash.Get_by_bry(key);}
}
