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
package gplx.core.json; import gplx.*; import gplx.core.*;
public class Json_itm_ {
	public static final Json_itm[] Ary_empty = new Json_itm[0];
	public static final byte Tid_unknown = 0, Tid_null = 1, Tid_bool = 2, Tid_int = 3, Tid_decimal = 4, Tid_string = 5, Tid_kv = 6, Tid_array = 7, Tid_nde = 8;
	public static final byte[][] Names = Bry_.Ary("unknown", "null", "boolean", "int", "decimal", "string", "keyval", "array", "nde");
	public static final byte[] Const_true = Bry_.new_a7("true"), Const_false = Bry_.new_a7("false"), Const_null = Bry_.new_a7("null");
}
