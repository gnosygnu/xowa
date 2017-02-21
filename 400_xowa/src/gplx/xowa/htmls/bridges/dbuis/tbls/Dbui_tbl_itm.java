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
public interface Dbui_tbl_itm {
	byte[] Key();
	Dbui_col_itm[] Cols();
	Dbui_btn_itm[] View_btns();
	Dbui_btn_itm[] Edit_btns();
	String Del (byte[] row_id, byte[] row_pkey);
	String Edit(byte[] row_id, byte[] row_pkey);
	String Save(byte[] row_id, byte[] row_pkey, Dbui_val_hash vals);
	String Reorder(byte[][] pkeys, int owner);
}
