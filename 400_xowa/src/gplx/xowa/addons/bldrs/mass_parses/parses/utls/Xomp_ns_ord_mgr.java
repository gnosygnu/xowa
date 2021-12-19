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
package gplx.xowa.addons.bldrs.mass_parses.parses.utls;
import gplx.core.lists.hashs.*;
import gplx.types.basics.wrappers.IntVal;
public class Xomp_ns_ord_mgr {
	private final Hash_adp__int hash = new Hash_adp__int();
	public Xomp_ns_ord_mgr(int[] ns_id_ary) {
		int len = ns_id_ary.length;
		for (int i = 0; i < len; ++i) {
			hash.Add(ns_id_ary[i], new IntVal(i));
		}
	}
	public int Get_ord_by_ns_id(int ns_id) {return ((IntVal)hash.Get_by_or_fail(ns_id)).Val();}
}
