/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
	public static byte Parse_ordermethod(byte[] bry) {
		byte key = Dpl_itm_keys.Parse(bry, Dpl_itm_keys.Key_categoryadd);
		switch (key) {
			case Dpl_itm_keys.Key_lastedit: 		return Tid_lastedit;
			case Dpl_itm_keys.Key_length: 			return Tid_length;
			case Dpl_itm_keys.Key_created: 			return Tid_created;
			case Dpl_itm_keys.Key_sortkey: 			return Tid_sortkey;
			case Dpl_itm_keys.Key_categorysortkey: 	return Tid_categorysortkey;
			case Dpl_itm_keys.Key_popularity: 		return Tid_popularity;	// FUTURE: default to categoryadd if counters disabled
			case Dpl_itm_keys.Key_categoryadd: 		return Tid_categoryadd;
			default:								return Tid_categoryadd;	// NOTE: "ordermethod=ascending" will return Dpl_itm_keys.Key_ascending; default to Tid_categoryadd; PAGE:sr.d:Викиречник:Википројекат_1001_арапска_реч/Списак_уноса; DATE:2015-10-16
		}
	}
}
