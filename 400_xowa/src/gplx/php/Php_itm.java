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
public interface Php_itm {
	byte Itm_tid();
	byte[] Val_obj_bry();
}
class Php_itm_null implements Php_itm, Php_itm_sub {
	public byte Itm_tid() {return Php_itm_.Tid_null;}
	public byte[] Val_obj_bry() {return null;}
	public static final Php_itm_null _ = new Php_itm_null(); Php_itm_null() {}
}
class Php_itm_bool_true implements Php_itm, Php_itm_sub {
	public byte Itm_tid() {return Php_itm_.Tid_bool_true;}
	public byte[] Val_obj_bry() {return Bry_true;}
	public static final Php_itm_bool_true _ = new Php_itm_bool_true(); Php_itm_bool_true() {}
	private static final byte[] Bry_true = Bry_.new_ascii_("true");
}
class Php_itm_bool_false implements Php_itm, Php_itm_sub {
	public byte Itm_tid() {return Php_itm_.Tid_bool_false;}
	public byte[] Val_obj_bry() {return Bry_true;}
	public static final Php_itm_bool_false _ = new Php_itm_bool_false(); Php_itm_bool_false() {}
	private static final byte[] Bry_true = Bry_.new_ascii_("false");
}
class Php_itm_var implements Php_itm, Php_itm_sub, Php_key {
	public Php_itm_var(byte[] v) {this.val_obj_bry = v;}
	public byte Itm_tid() {return Php_itm_.Tid_var;}
	public byte[] Val_obj_bry() {return val_obj_bry;} private byte[] val_obj_bry;	
}
