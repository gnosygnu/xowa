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
package gplx.xowa.xtns.wbases.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
public class Wdata_dict_claim {
	public static final byte
	  Tid_mainsnak								= 0
	, Tid_type									= 1
	, Tid_id									= 2
	, Tid_rank									= 3
	, Tid_references							= 4
	, Tid_qualifiers							= 5
	, Tid_qualifiers_order						= 6
	;
	public static String
	  Str_mainsnak								= "mainsnak"
	, Str_type									= "type"
	, Str_id									= "id"
	, Str_rank									= "rank"
	, Str_references							= "references"
	, Str_qualifiers							= "qualifiers"
	, Str_qualifiers_order						= "qualifiers-order"
	;
	public static byte[] 
	  Bry_mainsnak								= Bry_.new_a7(Str_mainsnak)
	, Bry_type									= Bry_.new_a7(Str_type)
	, Bry_id									= Bry_.new_a7(Str_id)
	, Bry_rank									= Bry_.new_a7(Str_rank)
	, Bry_references							= Bry_.new_a7(Str_references)
	, Bry_qualifiers							= Bry_.new_a7(Str_qualifiers)
	, Bry_qualifiers_order						= Bry_.new_a7(Str_qualifiers_order)
	;
	public static final    Hash_adp_bry Dict = Hash_adp_bry.cs()
	.Add_bry_byte(Bry_mainsnak					, Tid_mainsnak)
	.Add_bry_byte(Bry_type						, Tid_type)
	.Add_bry_byte(Bry_id						, Tid_id)
	.Add_bry_byte(Bry_rank						, Tid_rank)
	.Add_bry_byte(Bry_references				, Tid_references)
	.Add_bry_byte(Bry_qualifiers				, Tid_qualifiers)
	.Add_bry_byte(Bry_qualifiers_order			, Tid_qualifiers_order)
	;
}
