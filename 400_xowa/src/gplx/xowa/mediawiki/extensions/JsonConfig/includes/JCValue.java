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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.TypeIds;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.XophpStdClass;
public class JCValue {
	private Ordered_hash value = Ordered_hash_.New();
	private int statusVal;
	private XophpStdClass value_as_obj;
	private XophpArray value_as_ary;
	private Object value_as_prim;
	private int value_tid;
	private boolean sameAsDefaultVal = false;
	private boolean defaultUsedVal = false;
	private Object errorVal = null;

	private static final int NULL = -1;

	/** Value has not been checked */
	public static final int UNCHECKED = 0;
	/** Value was explicitly checked (might be an error) */
//		private static final int CHECKED = 1;
	/** field is missing in the data, but is being explicitly tested for.
	* This value should never be stored in JCObjContent::validationData.
	* Setting this value for any field in validator will delete it. */
	private static final int MISSING = 2;
	/** field was not explicitly tested, but it was listed as a parent of one of the tested fields */
//		private static final int VISITED = 3;

	private static final int Value_tid__obj = 1, Value_tid__ary = 2, Value_tid__prim = 3;

	/** @param int status
	* @param mixed value
	*/
	public JCValue(int status, XophpStdClass value_as_obj, XophpArray value_as_ary, Object value_as_prim) {
		this.statusVal = status;
		this.value_as_obj = value_as_obj;
		this.value_as_ary = value_as_ary;
		this.value_as_prim = value_as_prim;
		if (value_as_obj != null) {
			value_tid = Value_tid__obj;
		}
		else if (value_as_ary != null) {
			value_tid = Value_tid__ary;
		}
		else {
			value_tid = Value_tid__prim;
		}
	}

	/** @return mixed */
	public Object getValue() {
		switch (value_tid) {
			case Value_tid__obj:
				return value_as_obj;
			case Value_tid__ary:
				return value_as_ary;
			case Value_tid__prim:
				return value_as_prim;
			default:
				throw ErrUtl.NewUnhandled(value_tid);
		}
	}

	public void setValue(Ordered_hash value) {this.setValue(value, NULL);}
	public void setValue(Ordered_hash value, int status) {
		this.value = value;
		if (status != NULL) {
			this.status(status);
		} else if (this.isMissing()) {
			// Convenience - if we are setting a new value, assume we are setting a default
			this.status(JCValue.UNCHECKED);
			this.defaultUsed(true);
		}
	}

	public int status() {return status(NULL);}
	public int status(int o) {
		int val = this.statusVal;
		if (o != NULL) {
			this.statusVal = o;
		}
		return val;
	}

	public boolean sameAsDefault() {return sameAsDefault(null);}
	public boolean sameAsDefault(Object o) {
		boolean val = this.sameAsDefaultVal;
		if (o != null) {
			this.sameAsDefaultVal = BoolUtl.Cast(o);
		}
		return val;
	}

	public boolean defaultUsed() {return defaultUsed(null);}
	public boolean defaultUsed(Object o) {
		boolean val = this.defaultUsedVal;
		if (o != null) {
			this.defaultUsedVal = BoolUtl.Cast(o);
		}
		return val;
	}

	public boolean isMissing() {
		return this.statusVal == JCValue.MISSING;
	}

	public boolean isUnchecked() {
		return this.statusVal == JCValue.UNCHECKED;
	}

	/** Helper function - same arguments as wfMessage, or true if message was already added.
	* false clears this message status, and null returns current state without changing it
	* @param null|boolean|String $key message id, or if boolean, sets/removes error status
	* @param array $fieldPath path to the erroneous field. Will be converted to a a/b/c[0]/d style
	* @return boolean|Message
	*/
//		public String error($key = null, $fieldPath = null /*...*/)
	public Object error(Object key, String... fieldPath) {
		if (ClassUtl.TypeByObj(key) == BoolUtl.ClsRefType) {
			this.errorVal = BoolUtl.Cast(key);
		}
		else if (key != null) {
//				$args = func_get_args();
//				if (is_array($fieldPath)) {
//					// Convert field path to a printable String
//					$args[1] = JCUtils::fieldPathToString($fieldPath);
//				}
//				$this.errorVal = call_user_func_array('wfMessage', $args);
		}
		return this.errorVal;
	}

	/**
	* @param String|int $fld
	* @param mixed value
	* @throws Exception
	*/
	public void setField(Object fld, Object o) {
		int fld_type = To_type_id(fld);
		if (value_tid == Value_tid__obj && fld_type == TypeIds.IdStr) {
			value_as_obj.Add_by_as_obj((String)fld, o);
		}
		else if (value_tid == Value_tid__ary && (fld_type == TypeIds.IdStr || fld_type == TypeIds.IdInt)) {
			if (fld_type == TypeIds.IdStr)
				value_as_ary.Add((String)fld, o);
			else
				value_as_ary.Add(IntUtl.Cast(fld), o);
		}
		else {
			throw ErrUtl.NewArgs("Type mismatch for field " + fld);
		}
	}

	/**
	* @param String|int $fld
	* @throws \Exception
	* @return mixed
	*/
	public Object deleteField(Object fld) {
		int fld_type = To_type_id(fld);
		Object tmp = null;
		if (value_tid == Value_tid__obj && fld_type == TypeIds.IdStr) {
			String key = (String)fld;
			tmp = value_as_obj.Get_by_as_obj(key);
			value_as_obj.Del_by(key);
		}
		else if (value_tid == Value_tid__ary && (fld_type == TypeIds.IdStr || fld_type == TypeIds.IdInt)) {
			tmp = value_as_ary.Get_by_obj(fld);
			if (fld_type == TypeIds.IdStr)
				XophpArray.unset(value_as_ary, (String)fld);
			else
				XophpArray.unset(value_as_ary, IntUtl.Cast(fld));
			value.Del(fld);
		}
		else {
			throw ErrUtl.NewArgs("Type mismatch for field " + fld);
		}
		return tmp;
	}

	/**
	* @param String|int $fld
	* @throws \Exception
	* @return boolean
	*/
	public boolean fieldExists(Object fld) {
		int fld_type = To_type_id(fld);
		if (value_tid == Value_tid__obj && fld_type == TypeIds.IdStr) {
			return value_as_obj.Has((String)fld);
		}
		else if (value_tid == Value_tid__ary && (fld_type == TypeIds.IdStr || fld_type == TypeIds.IdInt)) {
			return value_as_ary.Has_obj(fld);
		}
		throw ErrUtl.NewArgs("Type mismatch for field " + fld);
	}

	/**
	* @param String|int $fld
	* @throws \Exception
	* @return mixed
	*/
	public Object getField(Object fld) {
		int fld_type = To_type_id(fld);
		if (value_tid == Value_tid__obj && fld_type == TypeIds.IdStr) {
			return value_as_obj.Get_by_as_obj((String)fld);
		}
		else if (value_tid == Value_tid__ary && (fld_type == TypeIds.IdStr || fld_type == TypeIds.IdInt)) {
			return value_as_ary.Get_by_obj(fld);
		}
		throw ErrUtl.NewArgs("Type mismatch for field " + fld);
	}

	public static int To_type_id(Object o) {
		Class<?> type = ClassUtl.TypeByObj(o);
		if      (ClassUtl.Eq(type, String.class))
			return TypeIds.IdStr;
		else if (ClassUtl.Eq(type, int.class))
			return TypeIds.IdInt;
		else
			return TypeIds.IdNull;
	}
}
class XomwTypeUtl {
	public static int To_type_id(Object o) {
		if       (o == null)
			return TypeIds.IdNull;
		Class<?> type = ClassUtl.TypeByObj(o);
		if      (ClassUtl.Eq(type, String.class))
			return TypeIds.IdStr;
		else if (ClassUtl.Eq(type, int.class))
			return TypeIds.IdInt;
		else if (ClassUtl.IsArray(type))
			return TypeIds.IdArray;
		else
			return TypeIds.IdObj;

	}
}
