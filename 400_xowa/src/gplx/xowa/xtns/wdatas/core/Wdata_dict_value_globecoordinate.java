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
public class Wdata_dict_value_globecoordinate {
	public static final byte
	  Tid_latitude								= 0
	, Tid_longitude								= 1
	, Tid_altitude								= 2
	, Tid_precision								= 3
	, Tid_globe									= 4
	;
	public static final String
	  Str_latitude								= "latitude"
	, Str_longitude								= "longitude"
	, Str_altitude								= "altitude"
	, Str_precision								= "precision"
	, Str_globe									= "globe"
	;
	public static byte[] 
	  Bry_latitude								= Bry_.new_a7(Str_latitude)
	, Bry_longitude								= Bry_.new_a7(Str_longitude)
	, Bry_altitude								= Bry_.new_a7(Str_altitude)
	, Bry_precision								= Bry_.new_a7(Str_precision)
	, Bry_globe									= Bry_.new_a7(Str_globe)
	;
	public static final Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_latitude					, Tid_latitude)
	.Add_bry_byte(Bry_longitude					, Tid_longitude)
	.Add_bry_byte(Bry_altitude					, Tid_altitude)
	.Add_bry_byte(Bry_precision					, Tid_precision)
	.Add_bry_byte(Bry_globe						, Tid_globe)
	;
	public static String
	  Val_globe_dflt_str						= "http:\\/\\/www.wikidata.org\\/entity\\/Q2"
	;
	public static byte[]
	  Val_globe_dflt_bry						= Bry_.new_a7(Val_globe_dflt_str)
	;
}
