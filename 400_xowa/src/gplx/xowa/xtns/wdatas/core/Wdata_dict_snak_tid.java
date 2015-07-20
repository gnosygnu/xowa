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
public class Wdata_dict_snak_tid {
	public static final byte
	  Tid_novalue								= 0
	, Tid_value									= 1
	, Tid_somevalue								= 2
	;
	public static final String 
	  Str_novalue								= "novalue"
	, Str_value									= "value"
	, Str_somevalue								= "somevalue"
	;
	public static final byte[]
	  Bry_novalue								= Bry_.new_a7(Str_novalue)
	, Bry_value									= Bry_.new_a7(Str_value)
	, Bry_somevalue								= Bry_.new_a7(Str_somevalue)
	;
	private static Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_novalue					, Tid_novalue)
	.Add_bry_byte(Bry_value						, Tid_value)
	.Add_bry_byte(Bry_somevalue					, Tid_somevalue)
	;
	public static String Xto_str(byte v) {
		switch (v) {
			case Tid_novalue:					return Str_novalue;
			case Tid_value:						return Str_value;
			case Tid_somevalue:					return Str_somevalue;
			default: 							return "unknown";
		}
	}
	public static byte[] Xto_bry(byte v) {
		switch (v) {
			case Tid_novalue:					return Bry_novalue;
			case Tid_value:						return Bry_value;
			case Tid_somevalue:					return Bry_somevalue;
			default: 							return null;
		}
	}
	public static byte Xto_tid(byte[] v) {
		Object rv_obj = Dict.Get_by_bry(v); if	(rv_obj == null) throw Err_.new_wo_type("unknown snak tid", "val", String_.new_u8(v));
		return ((Byte_obj_val)rv_obj).Val();
	}
}
