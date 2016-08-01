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
public class Wdata_dict_datavalue {
	public static final byte
	  Tid_value									= 0
	, Tid_type									= 1
	, Tid_error									= 2
	;
	public static final String
	  Str_value									= "value"
	, Str_type									= "type"
	, Str_error									= "error"
	;
	public static byte[] 
	  Bry_value									= Bry_.new_a7(Str_value)
	, Bry_type									= Bry_.new_a7(Str_type)
	, Bry_error									= Bry_.new_a7(Str_error)
	;
	public static final    Hash_adp_bry Dict = Hash_adp_bry.cs()
	.Add_bry_byte(Bry_value						, Tid_value)
	.Add_bry_byte(Bry_type						, Tid_type)
	.Add_bry_byte(Bry_error						, Tid_error)
	;
}
