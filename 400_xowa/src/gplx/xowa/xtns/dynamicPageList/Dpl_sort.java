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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
class Dpl_sort {
	public static final byte Tid_null = 0, Tid_lastedit = 1, Tid_length = 2, Tid_created = 3, Tid_sortkey = 4, Tid_categorysortkey = 5, Tid_popularity = 6, Tid_categoryadd = 7;
	public static byte Parse_as_bool_byte(byte[] bry) {
		byte val = Dpl_itm_keys.Parse(bry, Dpl_itm_keys.Key_null);
		switch (val) {
		case Dpl_itm_keys.Key_ascending: 	return Bool_.Y_byte;  
		case Dpl_itm_keys.Key_descending: 	return Bool_.N_byte;
		case Dpl_itm_keys.Key_null:			
		default:							return Bool_.__byte;
		}
	}
	public static byte Parse(byte[] bry) {
		byte key = Dpl_itm_keys.Parse(bry, Dpl_itm_keys.Key_categoryadd);
		switch (key) {
		case Dpl_itm_keys.Key_lastedit: 		return Tid_lastedit;
		case Dpl_itm_keys.Key_length: 			return Tid_length;
		case Dpl_itm_keys.Key_created: 			return Tid_created;
		case Dpl_itm_keys.Key_sortkey: 			return Tid_sortkey;
		case Dpl_itm_keys.Key_categorysortkey: 	return Tid_categorysortkey;
		case Dpl_itm_keys.Key_popularity: 		return Tid_popularity;	// FUTURE: default to categoryadd if counters disabled
		case Dpl_itm_keys.Key_categoryadd: 		return Tid_categoryadd;
		default:								throw Err_mgr._.unhandled_(key);
		}
	}
}
