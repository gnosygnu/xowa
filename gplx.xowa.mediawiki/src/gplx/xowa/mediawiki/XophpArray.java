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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
public class XophpArray {
	public static boolean popBoolOrN(List_adp list)           {return Bool_.Cast(List_adp_.Pop_or(list, false));}
	public static byte[] popBryOrNull(List_adp list)       {return (byte[])List_adp_.Pop_or(list, null);}
	public static String[] array_keys_str(Ordered_hash array) {
		int len = array.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			 rv[i] = (String)array.Get_at(i);
		}
		return rv;
	}
	public static byte[][] array_keys_bry(Ordered_hash array) {
		int len = array.Len();
		byte[][] rv = new byte[len][];
		for (int i = 0; i < len; i++) {
			 rv[i] = (byte[])array.Get_at(i);
		}
		return rv;
	}
	public static boolean array_key_exists(int key, Ordered_hash array)    {return array.Has(key);}
	public static boolean array_key_exists(String key, Ordered_hash array) {return array.Has(key);}
	public static boolean array_key_exists(byte[] key, Ordered_hash array) {return array.Has(key);}
	public static boolean array_is_empty(Ordered_hash array) {
		return array.Len() == 0;
	}
	public static void unset(Ordered_hash array, Object key) {
		array.Del(key);
	}
}
