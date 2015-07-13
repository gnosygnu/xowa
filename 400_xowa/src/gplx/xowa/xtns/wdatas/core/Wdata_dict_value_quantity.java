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
public class Wdata_dict_value_quantity {
	public static final byte
	  Tid_amount								= 0
	, Tid_unit									= 1
	, Tid_upperbound							= 2
	, Tid_lowerbound							= 3
	;
	public static final String
	  Str_amount								= "amount"
	, Str_unit									= "unit"
	, Str_upperbound							= "upperBound"
	, Str_lowerbound							= "lowerBound"
	;
	public static byte[] 
	  Bry_amount								= Bry_.new_a7(Str_amount)
	, Bry_unit									= Bry_.new_a7(Str_unit)
	, Bry_upperbound							= Bry_.new_a7(Str_upperbound)
	, Bry_lowerbound							= Bry_.new_a7(Str_lowerbound)
	;
	public static final Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_amount					, Tid_amount)
	.Add_bry_byte(Bry_unit						, Tid_unit)
	.Add_bry_byte(Bry_upperbound				, Tid_upperbound)
	.Add_bry_byte(Bry_lowerbound				, Tid_lowerbound)
	;
}
