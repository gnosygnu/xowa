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
package gplx.xowa.htmls.bridges.dbuis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*;
public class Dbui_row_itm {
	public Dbui_row_itm(Dbui_tbl_itm tbl, byte[] pkey, Dbui_val_itm[] vals) {
		this.tbl = tbl; this.pkey = pkey; this.vals = vals;
	}
	public Dbui_tbl_itm Tbl() {return tbl;} private final Dbui_tbl_itm tbl;
	public byte[] Pkey() {return pkey;} private final byte[] pkey;
	public Dbui_val_itm[] Vals() {return vals;} private Dbui_val_itm[] vals;
}
