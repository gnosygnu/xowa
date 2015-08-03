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
package gplx.php; import gplx.*;
public class Php_itm_ {
	public static final byte Tid_null = 0, Tid_bool_false = 1, Tid_bool_true = 2, Tid_int = 3, Tid_quote = 4, Tid_ary = 5, Tid_kv = 6, Tid_var = 7;
	public static int Parse_int_or(Php_itm itm, int or) {
		int rv = -1;
		switch (itm.Itm_tid()) {
			case Php_itm_.Tid_int:
				rv = ((Php_itm_int)itm).Val_obj_int();
				return rv;
			case Php_itm_.Tid_quote:
				byte[] bry = ((Php_itm_quote)itm).Val_obj_bry();
				rv = Bry_.To_int_or(bry, -1);
				return (rv == -1) ? or : rv;
			default:
				return or;
		}
	}
	public static byte[] Parse_bry(Php_itm itm) {
		switch (itm.Itm_tid()) {
			case Php_itm_.Tid_kv:
			case Php_itm_.Tid_ary:
				throw Err_.new_unhandled(itm.Itm_tid());
			default:
				return itm.Val_obj_bry();
		}
	}
}
