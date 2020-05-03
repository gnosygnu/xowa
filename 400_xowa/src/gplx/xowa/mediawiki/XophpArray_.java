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
package gplx.xowa.mediawiki;

import gplx.*;
import gplx.core.strings.*;
public class XophpArray_ {
	// REF.PHP:https://www.php.net/manual/en/function.array-merge.php
	public static XophpArray array_merge(XophpArray... vals) {
		XophpArray rv = new XophpArray();
		for (XophpArray ary : vals) {
			XophpArrayItm[] itms = ary.To_ary();
			for (XophpArrayItm itm : itms) {
				array_itm_add(rv, itm);
			}
		}
		return rv;
	}
	// REF.PHP:https://www.php.net/manual/en/function.array-merge.php
	// "If you want to append array elements from the second array to the first array while not overwriting the elements from the first array and not re-indexing, use the + array union operator:"
	public static XophpArray array_add(XophpArray lhs, XophpArray... vals) {
		for (XophpArray ary : vals) {
			XophpArrayItm[] itms = ary.To_ary();
			for (XophpArrayItm itm : itms) {
				if (lhs.Has(itm.Key())) {
					continue;
				}
				else {
					lhs.Add(itm.Key(), itm.Val());
				}
			}
		}
		return lhs;
	}
	private static void array_itm_add(XophpArray ary, XophpArrayItm itm) {
		if (itm.Key_is_int())
			ary.Add(itm.Val());
		else
			ary.Add(itm.Key(), itm.Val());
	}
	public static XophpArray array_splice(XophpArray src, int bgn)          {return array_splice(src, bgn, src.count(), null);}
	public static XophpArray array_splice(XophpArray src, int bgn, int len) {return array_splice(src, bgn, len        , null);}
	public static XophpArray array_splice(XophpArray src, int bgn, int len, XophpArray repl) {
		// get itms before clearing it
		int src_len = src.count();
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
			array_itm_add(src, itms[i]);
		}

		// add repl
		if (repl != null) {
			XophpArrayItm[] repl_itms = repl.To_ary();
			for (XophpArrayItm itm : repl_itms) {
				array_itm_add(src, itm);
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
			array_itm_add(src, itms[i]);
		}

		// add del to rv
		XophpArray rv = new XophpArray();
		for (int i = bgn; i < end; i++) {
			array_itm_add(rv, itms[i]);
		}
		return rv;
	}
	// ( array $array , int $offset [, int $length = NULL [, boolean $preserve_keys = FALSE ]] ) : 
	public static XophpArray array_slice(XophpArray array, int offset) {return array_slice(array, offset, array.count());}
	public static XophpArray array_slice(XophpArray array, int offset, int length) {
		XophpArray rv = new XophpArray();
		int end = offset + length;
		for (int i = offset; i< end; i++) {
			rv.Add(array.Get_at(i));
		}
		return rv;
	}

	public static XophpArray array_keys(XophpArray array) {
		XophpArray rv = XophpArray.New();
		int len = array.count();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);
			rv.Add(itm.Key());
		}
		return rv;
	}

	// DEPRECATE:use XophpArray
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

	public static boolean array_key_exists(String key, XophpArray array) {return array.Has(key);}
	public static boolean array_key_exists(int key, XophpArray array)    {return array.Has(Int_.To_str(key));}

	public static void unset(XophpArray array, String s) {array.unset(s);}
	public static void unset(XophpArray array, int i) {array.unset(i);}
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

	// REF.PHP:https://www.php.net/manual/en/function.array-map.php
	public static XophpArray array_map(XophpCallbackOwner callback_owner, String method, XophpArray array) {
		XophpArray rv = XophpArray.New();
		int len = array.count();
		for (int i = 0; i < len; i++) {
			String itm = array.Get_at_str(i);
			rv.Add((String)callback_owner.Call(method, itm));
		}
		return rv;
	}

	// REF.PHP:https://www.php.net/manual/en/function.array-flip.php
	public static XophpArray array_flip(XophpArray array) {
		XophpArray rv = XophpArray.New();
		int len = array.count();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);
			rv.Set(Object_.Xto_str_strict_or_null(itm.Val()), itm.Key());
		}
		return rv;
	}

	// REF.PHP:https://www.php.net/manual/en/function.implode.php
	public static String implode(String glue, XophpArray pieces) {
		String_bldr sb = String_bldr_.new_();
		int len = pieces.count();
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add(glue);
			sb.Add(pieces.Get_at_str(i));
		}
		return sb.To_str_and_clear();
	}

	public static int count(XophpArray array) {return array.count();}
	public static boolean count_bool(XophpArray array) {return array.count_bool();}
	public static Object array_pop(XophpArray array) {return array.pop();}
	public static boolean isset(XophpArray array, int key) {return XophpObject_.isset_obj(array.Get_at(key));}
	public static boolean isset(XophpArray array, String key) {return XophpObject_.isset_obj(array.Get_by(key));}
	public static boolean is_array(Object array) {return XophpType_.instance_of(array, XophpArray.class);}

	// REF.PHP: https://www.php.net/manual/en/function.in-array.php
	public static boolean in_array(Object needle, XophpArray haystack) {return in_array(needle, haystack, false);}
	public static boolean in_array(Object needle, XophpArray haystack, boolean strict) {
		// if strict, cache needleType
		Class<?> needleType = null;
		if (strict && needle != null) {
			needleType = Type_.Type_by_obj(needle);
		}

		// loop haystack to find match
		int haystack_len = haystack.Len();
		for (int i = 0; i < haystack_len; i++) {
			Object val = haystack.Get_at(i);

			// if strict, compare types
			if (strict) {
				if      (needle != null && val == null) {
					return false;
				}
				else if (needle == null && val != null) {
					return false;
				}
				else if (needle != null && val != null) {
					if (!Type_.Eq_by_obj(val, needleType)) {
						return false;
					}
				}
			}

			// compare objects
			if (Object_.Eq(needle, val)) {
				return true;
			}
		}
		return false;
	}

	// REF.PHP: https://www.php.net/manual/en/function.array-shift.php
	// Returns the shifted value, or NULL if array is empty or is not an array.
	public static Object array_shift(XophpArray array) {
		if (array == null) {
			return null;
		}
		int len = array.Len();
		if (len == 0) {
			return null;
		}
		XophpArrayItm[] itms = array.To_ary();
		array.Clear();
		int idx = 0;
		for (int i = 1; i < len; i++) {
			XophpArrayItm itm = itms[i];
			if (itm.Key_is_int()) {
				array.Add(idx++, itm.Val());
			}
			else {
				array.Add(itm.Key(), itm.Val());
			}
		}
		return itms[0].Val();
	}

	// REF.PHP: https://www.php.net/manual/en/function.array-filter.php
	public static final int ARRAY_FILTER_USE_BOTH = 1, ARRAY_FILTER_USE_KEY = 2, ARRAY_FILTER_USE_VAL = 0; // XO:USE_VAL is not PHP
	public static XophpArray array_filter(XophpArray array) {return array_filter(array, XophpArrayFilterNullCallback.Instance, 0);}
	public static XophpArray array_filter(XophpArray array, XophpCallback callback) {return array_filter(array, callback, 0);}
	public static XophpArray array_filter(XophpArray array, XophpCallback callback, int flag) {
		XophpArray rv = new XophpArray();
		int len = array.count();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);
			boolean filter = false;
			switch (flag) {
				case ARRAY_FILTER_USE_VAL:
					filter = Bool_.Cast(callback.Call(itm.Val()));
					break;
				case ARRAY_FILTER_USE_KEY:
					filter = Bool_.Cast(callback.Call(itm.Key()));
					break;
				case ARRAY_FILTER_USE_BOTH:
					filter = Bool_.Cast(callback.Call(itm.Key(), itm.Val()));
					break;
			}
			if (filter)
				rv.Add(itm.Key(), itm.Val());
		}
		return rv;
	}
}
class XophpArrayFilterNullCallback implements XophpCallbackOwner {
	public Object Call(String method, Object... args) {
		if (args.length != 1) throw new XophpRuntimeException("ArrayFilterNullCallback requires 1 arg");
		Object arg = args[0];
		return !XophpObject_.empty_obj(arg);
	}
	public static XophpCallback Instance = new XophpCallback(new XophpArrayFilterNullCallback(), "");
}
