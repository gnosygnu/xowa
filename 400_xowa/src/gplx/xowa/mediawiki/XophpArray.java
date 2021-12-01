/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.mediawiki;

import gplx.Bool_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Char_;
import gplx.Int_;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.Object_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.Type_;
import gplx.core.brys.Bry_bfr_able;
import gplx.core.strings.String_bldr;
import gplx.core.strings.String_bldr_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// REF.PHP:https://www.php.net/manual/en/language.types.array.php
// also has static functions; REF.PHP:https://www.php.net/manual/en/ref.array.php
public class XophpArray<T> implements Bry_bfr_able, Iterable<T> {
	private final Ordered_hash hash = Ordered_hash_.New();
	private int newMemberIdx;

	public void Clear() {
		hash.Clear();
		newMemberIdx = 0;
		internalPointerIndex = 0;
	}
	public int Len() {return hash.Len();}
	public boolean Has_obj(Object key) {return Has(Object_.Xto_str_strict_or_null(key));}
	public boolean Has(String key) {
		return hash.Has(key);
	}
	public XophpArray Add(T val) {
		int key = newMemberIdx++;
		Set(XophpArrayItm.NewInt(key, val));
		return this;
	}
	public XophpArray Add(int key, Object val) {
		newMemberIdx = key + 1;
		Set(XophpArrayItm.NewInt(key, val));
		return this;
	}
	public XophpArray Add(double key, Object val) {
		int key_as_int = (int)key;
		newMemberIdx = key_as_int + 1;
		Set(XophpArrayItm.NewInt(key_as_int, val));
		return this;
	}
	public XophpArray Add(boolean key, Object val) {
		int key_as_int = key ? 1 : 0;
		newMemberIdx = key_as_int + 1;
		Set(XophpArrayItm.NewInt(key_as_int, val));
		return this;
	}
	public XophpArray Add(String key, Object val) {
		int key_as_int = Int_.Parse_or(key, Int_.Min_value);
		if (key_as_int == Int_.Min_value) {
			Set(XophpArrayItm.NewStr(key, val));
		}
		else {
			Set(XophpArrayItm.NewInt(key_as_int, val));
			newMemberIdx = key_as_int + 1;
		}
		return this;
	}
	public XophpArray Add_as_key_and_val_many(String... val) {
		for (String itm : val) {
			Add(itm, itm);
		}
		return this;
	}
	public XophpArray Add_many(T... val) {
		for (T itm : val) {
			Add(itm);
		}
		return this;
	}
	public void Concat_str(int i, String s) {
		this.Set(i, this.Get_at_str(i) + s);
	}
	public XophpArray Get_at_ary_or_null(int i) {
		Object rv = Get_at(i);
		return Type_.Eq_by_obj(rv, XophpArray.class) ? (XophpArray)rv : null;
	}
	public XophpArray Get_at_ary(int i) {return (XophpArray)Get_at(i);}
	public int Get_at_int(int i) {return Int_.Cast(Get_at(i));}
	public String Get_at_str(int i) {return (String)Get_at(i);}
	public Object Get_at(int i) {
		if (i < 0 || i >= hash.Len()) return null;
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		return itm == null ? null : itm.Val();
	}
	public XophpArrayItm Get_at_itm(int i) {
		if (i < 0 || i >= hash.Len()) return null;
		return (XophpArrayItm)hash.Get_at(i);
	}
	public Object Get_by_obj(Object key) {return Get_by(Object_.Xto_str_strict_or_null(key));}
	public Object Get_by(int key) {return Get_by(Int_.To_str(key));}
	public boolean Get_by_bool_or(String key, boolean or) {Object rv = this.Get_by(key); return rv == null ? or : Bool_.Cast(rv);}
	public boolean Get_by_bool(String key) {return Bool_.Cast(this.Get_by(key));}
	public int Get_by_int_or(String key, int or) {Object rv = this.Get_by(key); return rv == null ? or : Int_.Cast(rv);}
	public int Get_by_int(String key) {return Int_.Cast(this.Get_by(key));}
	public XophpArray Xet_by_ary(String key) {
		XophpArrayItm itm = (XophpArrayItm)hash.GetByOrNull(key);
		if (itm == null) {
			XophpArray val = new XophpArray();
			this.Set(key, val);
			return val;
		}
		else {
			return (XophpArray)itm.Val();
		}
	}
	public XophpArray Get_by_ary_or(String key, XophpArray or) {Object rv = this.Get_by(key); return rv == null ? or : (XophpArray)rv;}
	public XophpArray Get_by_ary(String key) {return (XophpArray)this.Get_by(key);}
	public String Get_by_str(char key) {return (String)this.Get_by(Char_.To_str(key));}
	public String Get_by_str(int key) {return (String)this.Get_by(Int_.To_str(key));}
	public String Get_by_str(String key) {return (String)this.Get_by(key);}
	public T Get_by(String key) {
		XophpArrayItm itm = (XophpArrayItm)hash.GetByOrNull(key);
		return itm == null ? null : (T)itm.Val();
	}
	public XophpArrayItm Get_by_itm(String key) {
		return (XophpArrayItm)hash.GetByOrNull(key);
	}
	public void Set(int key, Object val) {
		this.Set(XophpArrayItm.NewInt(key, val));
	}
	public void Set(String key, Object val) {
		this.Set(XophpArrayItm.NewStr(key, val));
	}
	private void Set(XophpArrayItm itm) {
		String key = itm.Key();
		XophpArrayItm cur = (XophpArrayItm)hash.GetByOrNull(key);
		if (cur == null) {
			hash.Add(key, itm);
		}
		else {
			cur.Val_(itm.Val());
		}
	}
	public void Del_at(int i) {
		XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
		if (itm != null) {
			hash.Del(itm.Key());
		}
	}
	public void Del_by(String key) {
		hash.Del(key);
	}
	public XophpArrayItm[] To_ary() {
		return (XophpArrayItm[])hash.To_ary(XophpArrayItm.class);
	}
	public String To_str() {
		Bry_bfr bfr = Bry_bfr_.New();
		To_bfr(bfr);
		return bfr.To_str_and_clear();
	}
	public void To_bfr(Bry_bfr bfr) {
		XophpArrayItm[] itms = To_ary();
		for (XophpArrayItm itm : itms) {
			itm.To_bfr(bfr);
		}
	}
	public void Itm_str_concat_end(int idx, String v) {
		String itm = (String)this.Get_at(idx);
		itm += v;
		this.Set(idx, itm);
	}

	public XophpArray Clone() {
		XophpArray rv = new XophpArray();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = (XophpArrayItm)hash.Get_at(i);
			rv.Add(itm.Key(), itm.Val());
		}
		return rv;
	}
	@Override public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!Type_.Eq_by_obj(obj, XophpArray.class)) return false;
		XophpArray comp = (XophpArray)obj;

		// compare lens
		int this_len = this.Len();
		int comp_len = comp.Len();
		if (this_len != comp_len) return false;

		// loop items
		for (int i = 0; i < this_len; i++) {
			XophpArrayItm this_itm = this.Get_at_itm(i);
			XophpArrayItm comp_itm = comp.Get_at_itm(i);
			if (!Object_.Eq(this_itm, comp_itm))
				return false;
		}
		return true;
	}
	@Override public int hashCode() {
		int rv = 0;
		int len = this.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = this.Get_at_itm(i);
			rv = (31 * rv) + itm.hashCode();
		}
		return rv;
	}

	@Override
	public Iterator iterator() {
		return new XophpArrayIterator(hash);
	}
	class XophpArrayIterator implements Iterator<T> {
		private final Ordered_hash hash;
		private int curIdx;
		private int len;
		public XophpArrayIterator(Ordered_hash hash) {
			this.hash = hash;
			this.len = hash.Len();
		}
		@Override
		public boolean hasNext() {
			return curIdx < len;
		}

		@Override
		public T next() {
			return (T) ((XophpArrayItm)hash.Get_at(curIdx++)).Val();
		}

		@Override
		public void remove() {
			throw new XophpRuntimeException("remove not supported");
		}
	}
	public Iterable<XophpArrayItm<T>> IterateItms() {
		return new XophpArrayItmIterable(hash);
	}
	class XophpArrayItmIterable implements Iterable<XophpArrayItm<T>>, Iterator<XophpArrayItm<T>> {
		private final Ordered_hash hash;
		private int curIdx;
		private int len;
		public XophpArrayItmIterable(Ordered_hash hash) {
			this.hash = hash;
			this.len = hash.Len();
		}
		@Override
		public boolean hasNext() {
			return curIdx < len;
		}

		@Override
		public XophpArrayItm<T> next() {
			return (XophpArrayItm<T>) hash.Get_at(curIdx++);
		}

		@Override
		public void remove() {
			throw new XophpRuntimeException("remove not supported");
		}

		@Override
		public Iterator<XophpArrayItm<T>> iterator() {
			return this;
		}
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
	public static boolean empty(Ordered_hash array) {
		return array.Len() == 0;
	}

	// **********
	// * STATIC *
	// **********
	public static final XophpArray False = null; // handles code like "if ($var === false)" where var is an Object;
	public static <S> XophpArray<S> New(S... vals) {
		XophpArray rv = new XophpArray();
		for (S val : vals)
			rv.Add(val);
		return rv;
	}
	public static boolean Eq_to_new(XophpArray array) {return array.hash.Len() == 0;}// same as "array === []"

	public static boolean empty(XophpArray array) {return array.Len() == 0;}
	public static int count(XophpArray array) {return array.Len();}
	public static boolean count_bool(XophpArray array) {return array.Len() > 0;}

	public static boolean isset(XophpArray array, int key)    {return array.Get_at(key) != null;}
	public static boolean isset(XophpArray array, String key) {return array.Get_by(key) != null;}
	public static boolean is_array(Object array) {return XophpType_.instance_of(array, XophpArray.class);}
	public static void unset(XophpArray array, String key)   {array.hash.Del(key);}
	public static void unset(XophpArray array, int key)      {array.hash.Del(Int_.To_str(key));}
	public static void unset(Ordered_hash array, Object key) {array.Del(key);}
	public static boolean array_key_exists(String key, XophpArray array) {return array.Has(key);}
	public static boolean array_key_exists(int key, XophpArray array)    {return array.Has(Int_.To_str(key));}

	public static Object array_pop(XophpArray array) {// "array_pop"
		int pos = array.Len() - 1;
		if (pos < 0) return null;
		XophpArrayItm itm = (XophpArrayItm)array.hash.Get_at(pos);
		array.Del_at(pos);
		return itm.Val();
	}
	public static Object end(XophpArray array) {
		int len = array.Len();
		return len == 0 ? null : ((XophpArrayItm)array.hash.Get_at(len - 1)).Val();
	}

	// REF.PHP:https://www.php.net/manual/en/function.array-values.php
	public static XophpArray array_values(XophpArray array) {
		XophpArray rv = new XophpArray();
		int len = array.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);
			rv.Add(i, itm.Val());
		}
		return rv;
	}

	// REF.PHP:https://www.php.net/manual/en/function.reset.php
	private int internalPointerIndex = 0;
	private Object internalPointerAdd(int v) {
		internalPointerIndex += v;
		return internalPointerGetOrNull();
	}
	private Object internalPointerReset() {
		internalPointerIndex = 0;
		return internalPointerGetOrNull();
	}
	private Object internalPointerGetOrNull() {return hash.Len() == 0 ? null : this.Get_at(internalPointerIndex);}
	public static Object reset(XophpArray array)   {return array.internalPointerReset();}
	public static Object current(XophpArray array) {return array.internalPointerGetOrNull();}
	public static Object next(XophpArray array) {
		return array.internalPointerAdd(1);
	}

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
		if (itm.KeyIsInt())
			ary.Add(itm.Val());
		else
			ary.Add(itm.Key(), itm.Val());
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
	public static XophpArray array_slice(XophpArray array, int offset) {return array_slice(array, offset, array.Len());}
	public static XophpArray array_slice(XophpArray array, int offset, int length) {
		XophpArray rv = new XophpArray();
		int end = offset + length;
		for (int i = offset; i< end; i++) {
			rv.Add(array.Get_at(i));
		}
		return rv;
	}

	public static XophpArray<String> array_keys(XophpArray array) {
		XophpArray rv = XophpArray.New();
		int len = array.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);
			rv.Add(itm.Key());
		}
		return rv;
	}


	// REF.PHP:https://www.php.net/manual/en/function.array-map.php
	public static XophpArray array_map(XophpCallbackOwner callback_owner, String method, XophpArray array) {
		XophpArray rv = XophpArray.New();
		int len = array.Len();
		for (int i = 0; i < len; i++) {
			String itm = array.Get_at_str(i);
			rv.Add((String)callback_owner.Call(method, itm));
		}
		return rv;
	}

	// REF.PHP:https://www.php.net/manual/en/function.array-flip.php
	public static XophpArray array_flip(XophpArray array) {
		XophpArray rv = XophpArray.New();
		int len = array.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);
			rv.Set(Object_.Xto_str_strict_or_null(itm.Val()), itm.Key());
		}
		return rv;
	}

	// REF.PHP:https://www.php.net/manual/en/function.implode.php
	public static String implode(String glue, XophpArray pieces) {
		String_bldr sb = String_bldr_.new_();
		int len = pieces.Len();
		for (int i = 0; i < len; i++) {
			if (i != 0) sb.Add(glue);
			sb.Add(pieces.Get_at_str(i));
		}
		return sb.To_str_and_clear();
	}

	// REF.PHP:https://www.php.net/manual/en/function.in-array.php
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

	// REF.PHP:https://www.php.net/manual/en/function.array-shift.php
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
			if (itm.KeyIsInt()) {
				array.Add(idx++, itm.Val());
			}
			else {
				array.Add(itm.Key(), itm.Val());
			}
		}
		return itms[0].Val();
	}

	// REF.PHP:https://www.php.net/manual/en/function.array-filter.php
	public static final int ARRAY_FILTER_USE_BOTH = 1, ARRAY_FILTER_USE_KEY = 2, ARRAY_FILTER_USE_VAL = 0; // XO:USE_VAL is not PHP
	public static XophpArray array_filter(XophpArray array) {return array_filter(array, NULL_CALLBACK, 0);}
	public static XophpArray array_filter(XophpArray array, XophpCallback callback) {return array_filter(array, callback, 0);}
	public static XophpArray array_filter(XophpArray array, XophpCallback callback, int flag) {
		XophpArray rv = new XophpArray();
		int len = array.Len();
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
	public static XophpCallback NULL_CALLBACK = new XophpCallback(new XophpArray.XophpArrayFilterNullCallback(), "");
	static class XophpArrayFilterNullCallback implements XophpCallbackOwner {
		public Object Call(String method, Object... args) {
			if (args.length != 1) throw new XophpRuntimeException("ArrayFilterNullCallback requires 1 arg");
			Object arg = args[0];
			return !XophpObject_.empty_obj(arg);
		}
	}
	// REF.PHP:https://www.php.net/manual/en/function.array-diff.php
	public static XophpArray array_diff(XophpArray array1, XophpArray array2, XophpArray... arrays) {
		// remove elements
		Map<String, List<String>> valMap = array_diff__build_val_map(array1);
		int array1_len = array1.Len();
		array_diff__remove_common_vals(valMap, array1, array2);
		for (XophpArray array : arrays) {
			// update map, if array contents changed
			if (array1_len != array1.Len()) {
				valMap = array_diff__build_val_map(array1);
				array1_len = array1.Len();
			}
			array_diff__remove_common_vals(valMap, array1, array);
		}
		return array1;
	}
	private static Map<String, List<String>> array_diff__build_val_map(XophpArray array) {
		Map<String, List<String>> map = new HashMap<>();
		int len = array.Len();
		for (int i = 0; i < len; i++) {
			XophpArrayItm itm = array.Get_at_itm(i);

			// get val as String
			String valStr = XophpObject_.ToStr(itm.Val());
			List<String> keyList = map.get(valStr);
			if (keyList == null) {
				keyList = new ArrayList<>();
				map.put(valStr, keyList);
			}
			keyList.add(itm.Key());
		}
		return map;
	}
	private static void array_diff__remove_common_vals(Map<String, List<String>> lhsValMap, XophpArray lhs, XophpArray rhs) {
		int rhsLen = rhs.Len();
		for (int i = 0; i < rhsLen; i++) {
			XophpArrayItm rhsItm = rhs.Get_at_itm(i);
			String rhsVal = XophpObject_.ToStr(rhsItm.Val());
			List<String> lhsKeyList = lhsValMap.get(rhsVal);
			if (lhsKeyList != null) {
				for (String lhsKey : lhsKeyList) {
					lhs.Del_by(lhsKey);
				}
			}
		}
	}
	
	// REF.PHP:https://www.php.net/manual/en/function.array-diff-key
	public static XophpArray array_diff_key(XophpArray array1, XophpArray array2, XophpArray... arrays) {
		// remove elements
		array_diff_key__remove_common_key(array1, array2);
		for (XophpArray array : arrays) {
			array_diff_key__remove_common_key(array1, array);
		}
		return array1;
	}
	private static void array_diff_key__remove_common_key(XophpArray lhs, XophpArray rhs) {
		int rhsLen = rhs.Len();
		for (int i = 0; i < rhsLen; i++) {
			XophpArrayItm rhsItm = rhs.Get_at_itm(i);
			String rhsKey = rhsItm.Key();
			if (lhs.Has(rhsKey)) {
				lhs.Del_by(rhsKey);
			}
		}
	}
}
