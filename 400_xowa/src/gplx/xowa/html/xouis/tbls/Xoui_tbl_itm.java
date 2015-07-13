/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.html.xouis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.xouis.*;
public interface Xoui_tbl_itm {
	byte[] Key();
	Xoui_col_itm[] Cols();
	Xoui_row_itm[] Rows(); void Rows_(Xoui_row_itm[] v);
	Xoui_btn_itm[] View_btns();
	Xoui_btn_itm[] Edit_btns();
	void Del(byte[] row_pkey_bry);
	String Edit(byte[] row_key, byte[] row_pkey_bry);
	String Save(Xow_wiki wiki, byte[] row_key, byte[] row_pkey_bry, Xoui_val_hash ary);
	void Set_order_adj(byte[] row_pkey, boolean adj_down);
}
