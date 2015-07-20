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
package gplx.xowa.html.bridges.dbuis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.bridges.*; import gplx.xowa.html.bridges.dbuis.*;
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
