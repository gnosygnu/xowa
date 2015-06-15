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
public class Wdata_dict_value_entity {
	public static final byte
	  Tid_entity_type							= 0
	, Tid_numeric_id							= 1
	;
	public static final String
	  Str_entity_type							= "entity-type"
	, Str_numeric_id							= "numeric-id"
	;
	public static byte[] 
	  Bry_entity_type							= Bry_.new_a7(Str_entity_type)
	, Bry_numeric_id							= Bry_.new_a7(Str_numeric_id)
	;
	public static final Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_entity_type				, Tid_entity_type)
	.Add_bry_byte(Bry_numeric_id				, Tid_numeric_id)
	;
}
