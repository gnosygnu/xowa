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
public class Wdata_dict_rank {
	public static final byte	// SERIALIZED:
	  Tid_preferred								= 2
	, Tid_normal								= 1
	, Tid_deprecated							= 0
	, Tid_unknown								= Byte_.Max_value_127
	;
	public static final String
	  Str_preferred								= "preferred"
	, Str_normal								= "normal"
	, Str_deprecated							= "deprecated"
	;
	public static byte[] 
	  Bry_preferred								= Bry_.new_a7(Str_preferred)
	, Bry_normal								= Bry_.new_a7(Str_normal)
	, Bry_deprecated							= Bry_.new_a7(Str_deprecated)
	;
	public static final Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_preferred					, Tid_preferred)
	.Add_bry_byte(Bry_normal					, Tid_normal)
	.Add_bry_byte(Bry_deprecated				, Tid_deprecated)
	;
	public static String Xto_str(byte tid) {
		switch (tid) {
			case Tid_preferred					: return Str_preferred;
			case Tid_normal						: return Str_normal;
			case Tid_deprecated					: return Str_deprecated;
			default								: throw Err_.new_unhandled(tid);
		}
	}
	public static byte Xto_tid(byte[] v) {
		Object rv_obj = Dict.Get_by_bry(v); if	(rv_obj == null) throw Err_.new_wo_type("unknown rank", "val", String_.new_u8(v));
		return ((Byte_obj_val)rv_obj).Val();
	}
}