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
public class Wdata_dict_sitelink {
	public static final byte
	  Tid_site									= 0
	, Tid_title									= 1
	, Tid_badges								= 2
	;
	public static final String
	  Str_site									= "site"
	, Str_title									= "title"
	, Str_badges								= "badges"
	;
	public static byte[] 
	  Bry_site									= Bry_.new_a7(Str_site)
	, Bry_title									= Bry_.new_a7(Str_title)
	, Bry_badges								= Bry_.new_a7(Str_badges)
	;
	public static final Hash_adp_bry Dict = Hash_adp_bry.cs()
	.Add_bry_byte(Bry_site						, Tid_site)
	.Add_bry_byte(Bry_title						, Tid_title)
	.Add_bry_byte(Bry_badges					, Tid_badges)
	;
}
