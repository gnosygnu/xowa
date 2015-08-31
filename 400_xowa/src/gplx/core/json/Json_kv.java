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
public class Json_kv extends Json_itm_base {
	public Json_kv(Json_itm key, Json_itm val) {this.key = key; this.val = val;}
	@Override public byte Tid() {return Json_itm_.Tid__kv;}
	public Json_itm Key() {return key;} private final Json_itm key;
	public Json_itm Val() {return val;} private final Json_itm val;
	public byte[] Key_as_bry() {return key.Data_bry();}
	public String Key_as_str() {return (String)key.Data();}
	public byte[] Val_as_bry() {return val.Data_bry();}
	public Json_nde Val_as_nde() {return Json_nde.cast(val);}
	public Json_ary Val_as_ary() {return Json_ary.cast(val);}
	public boolean Key_eq(byte[] comp) {return ((Json_itm_str)key).Data_eq(comp);}
	@Override public Object Data() {return null;}
	@Override public byte[] Data_bry() {return null;}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {
		key.Print_as_json(bfr, depth);
		bfr.Add_byte(Byte_ascii.Colon);
		val.Print_as_json(bfr, depth);
	}
	public static final Json_kv[] Ary_empty = new Json_kv[0];
	public static Json_kv cast(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid__kv ? null : (Json_kv)v;}
}
