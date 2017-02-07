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
package gplx.xowa.htmls.core.wkrs.xndes.dicts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
public class Xoh_xnde_dict_itm {
	public Xoh_xnde_dict_itm(int id, byte[] val) {this.id = id; this.val = val; count = 1;}
	public int Id() {return id;} private final int id;
	public byte[] Val() {return val;} private final byte[] val;
	public int Count() {return count;} private int count;
	public void Count_add_1() {++count;}
	public void Save(Xoh_hzip_bfr bfr, int id_len) {
		bfr.Add_hzip_int(id_len, id);
		bfr.Add(val).Add_byte_nl();
	}
}
