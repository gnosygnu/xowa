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
package gplx.xowa.htmls.bridges.dbuis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*;
public class Dbui_row_itm {
	public Dbui_row_itm(Dbui_tbl_itm tbl, byte[] pkey, Dbui_val_itm[] vals) {
		this.tbl = tbl; this.pkey = pkey; this.vals = vals;
	}
	public Dbui_tbl_itm Tbl() {return tbl;} private final Dbui_tbl_itm tbl;
	public byte[] Pkey() {return pkey;} private final byte[] pkey;
	public Dbui_val_itm[] Vals() {return vals;} private Dbui_val_itm[] vals;
}
