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
public class Wdata_dict_reference {
	public static final byte
	  Tid_hash									= 0
	, Tid_snaks									= 1
	, Tid_snaks_order							= 2
	;
	public static final String 
	  Str_hash									= "hash"
	, Str_snaks									= "snaks"
	, Str_snaks_order							= "snaks-order"
	;
	public static final byte[]
	  Bry_hash									= Bry_.new_a7(Str_hash)
	, Bry_snaks									= Bry_.new_a7(Str_snaks)
	, Bry_snaks_order							= Bry_.new_a7(Str_snaks_order)
	;
	public static Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_hash						, Tid_hash)
	.Add_bry_byte(Bry_snaks						, Tid_snaks)
	.Add_bry_byte(Bry_snaks_order				, Tid_snaks_order)
	;
	public static String Xto_str(byte v) {
		switch (v) {
			case Tid_hash:						return Str_hash;
			case Tid_snaks:						return Str_snaks;
			case Tid_snaks_order:				return Str_snaks_order;
			default: 							throw Exc_.new_unhandled(v);
		}
	}
	public static byte[] Xto_bry(byte v) {
		switch (v) {
			case Tid_hash:						return Bry_hash;
			case Tid_snaks:						return Bry_snaks;
			case Tid_snaks_order:				return Bry_snaks_order;
			default: 							throw Exc_.new_unhandled(v);
		}
	}
}
