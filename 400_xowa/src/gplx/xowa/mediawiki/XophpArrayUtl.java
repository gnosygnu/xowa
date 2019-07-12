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
public class XophpArrayUtl {
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
	public static Object[] unset_by_idx(Object[] ary, int idx) {
		int ary_len = ary.length;
		Object[] rv = new Object[ary_len];
		for (int i = 0; i < idx; i++)
			rv[i] = ary[i];
		for (int i = idx + 1; i < ary_len; i++)
			rv[i - 1] = ary[i];
		return rv;
	}
	public static boolean in_array(String needle, String[] haystack) {
		for (String hay : haystack)
			if (String_.Eq(hay, needle))
				return true;
		return false;
	}
	public static XophpArray array_merge(XophpArray... vals) {
		XophpArray rv = new XophpArray();
		for (XophpArray ary : vals) {
			XophpArrayItm[] itms = ary.To_ary();
			for (XophpArrayItm itm : itms) {
				array_add(rv, itm);
			}
		}
		return rv;
	}
	private static void array_add(XophpArray ary, XophpArrayItm itm) {
		if (itm.Key_as_str() == null)
			ary.Add(itm.Val());
		else
			ary.Add(itm.Key_as_str(), itm.Val());
	}
	public static XophpArray array_splice(XophpArray src, int bgn)          {return array_splice(src, bgn, src.Len(), null);}
	public static XophpArray array_splice(XophpArray src, int bgn, int len) {return array_splice(src, bgn, len        , null);}
	public static XophpArray array_splice(XophpArray src, int bgn, int len, XophpArray repl) {
		// get itms before clearing it
		int src_len = src.Len();
		XophpArrayItm[] itms = src.To_ary();
		src.Clear();

		// calc bgn
		if (bgn < 0) { // negative bgn should be adusted from src_len; EX: -1 means start at last item
			bgn += src_len;
			if (bgn < 0) // large negative bgn should be capped at 0; EX: -99
				bgn = 0;
		}
		else if (bgn > src_len) // large positive bgn should be capped at src_len; EX: 99
			bgn = src_len;

		// add src from 0 to bgn
		for (int i = 0; i < bgn; i++) {
			array_add(src, itms[i]);
		}

		// add repl
		if (repl != null) {
			XophpArrayItm[] repl_itms = repl.To_ary();
			for (XophpArrayItm itm : repl_itms) {
				array_add(src, itm);
			}
		}

		// calc end
		int end;
		if (len < 0) { // negative len should be adjusted from src_len; EX: -1 means stop at last itm
			end = src_len + len;
			if (end < 0) // large_negative len should be capped at 0; EX: -99
				end = 0;
		}
		else { // positive len should be added to bgn to find end
			end = bgn + len;
		}
		if (end < bgn) // end should never be less than bgn; EX: splice(1, -99)
			end = bgn;
		else if (end > src_len) // end should not be more then end; 
			end = src_len;

		// add src from end to len
		for (int i = end; i < src_len; i++) {
			array_add(src, itms[i]);
		}

		// add del to rv
		XophpArray rv = new XophpArray();
		for (int i = bgn; i < end; i++) {
			array_add(rv, itms[i]);
		}
		return rv;
	}
}
