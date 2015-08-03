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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.core.primitives.*;
public class Wdata_dict_value_entity_tid {
	public static final byte
	  Tid_item									= 0
	, Tid_property								= 1
	;
	public static final String 
	  Str_item									= "item"
	, Str_property								= "property"
	;
	public static final byte[]
	  Bry_item									= Bry_.new_a7(Str_item)
	, Bry_property								= Bry_.new_a7(Str_property)
	;
	private static Hash_adp_bry Dict = Hash_adp_bry.cs()
	.Add_bry_byte(Bry_item						, Tid_item)
	.Add_bry_byte(Bry_property					, Tid_property)
	;
	public static String Xto_str(byte v) {
		switch (v) {
			case Tid_item:						return Str_item;
			case Tid_property:					return Str_property;
			default: 							return null;
		}
	}
	public static byte[] Xto_bry(byte v) {
		switch (v) {
			case Tid_item:						return Bry_item;
			case Tid_property:					return Bry_property;
			default: 							return null;
		}
	}
	public static byte Xto_tid(byte[] v) {
		Object rv_obj = Dict.Get_by_bry(v); if (rv_obj == null) throw Err_.new_wo_type("unknown entity_tid", "val", v);
		return ((Byte_obj_val)rv_obj).Val();
	}
}
