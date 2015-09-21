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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
public class Json_itm_ {
	public static final Json_itm[] Ary_empty = new Json_itm[0];
	public static final byte Tid__unknown = 0, Tid__null = 1, Tid__bool = 2, Tid__int = 3, Tid__decimal = 4, Tid__str = 5, Tid__kv = 6, Tid__ary = 7, Tid__nde = 8;
	public static final byte[] Bry__true = Bool_.True_bry, Bry__false = Bool_.False_bry, Bry__null = Object_.Bry__null;
	public static byte[] To_bry(Bry_bfr bfr, Json_itm itm) {
		if (itm == null) return Bry_.Empty;
		itm.Print_as_json(bfr, 0);
		return bfr.Xto_bry_and_clear();
	}
}
