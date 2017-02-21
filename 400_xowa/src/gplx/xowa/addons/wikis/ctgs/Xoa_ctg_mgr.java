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
package gplx.xowa.addons.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.addons.wikis.ctgs.htmls.*;
public class Xoa_ctg_mgr {
	public static final byte Version_null = Byte_.Zero, Version_1 = 1, Version_2 = 2;
	public static final byte Tid__subc = 0, Tid__file = 1, Tid__page = 2, Tid___max = 3;	// SERIALIZED; cat_link.cl_type_id
	public static final byte Hidden_n = Byte_.Zero, Hidden_y = (byte)1;
	public static final String Html__cls__str = "CategoryTreeLabel CategoryTreeLabelNs14 CategoryTreeLabelCategory";
	public static final    byte[] Html__cls__bry = Bry_.new_a7(Html__cls__str);

	public static byte To_tid_by_ns(int ns) {
		switch (ns) {
			case gplx.xowa.wikis.nss.Xow_ns_.Tid__category: return Tid__subc;
			case gplx.xowa.wikis.nss.Xow_ns_.Tid__file    : return Tid__file;
			default                                       : return Tid__page;
		}
	}
}
