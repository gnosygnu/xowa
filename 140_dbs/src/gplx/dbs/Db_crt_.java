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
package gplx.dbs; import gplx.*;
import gplx.core.criterias.*;
public class Db_crt_ {
	public static final Criteria Wildcard = Criteria_.All;
	public static Criteria     New_and			(Criteria lhs, Criteria rhs)					{return Criteria_.And(lhs, rhs);}
	public static Criteria_fld New_eq			(String key, Object val)						{return Criteria_fld.new_(key, Criteria_.eq_(val));}
	public static Criteria_fld New_eq			(String pre, String key, Object val)			{return Criteria_fld.new_(pre, key, Criteria_.eq_(val));}
	public static Criteria_fld New_eq_not		(String key, Object val)						{return Criteria_fld.new_(key, Criteria_.eqn_(val));}
	public static Criteria_fld New_lt			(String key, Comparable val)					{return Criteria_fld.new_(key, Criteria_.lt_(val));}
	public static Criteria_fld New_lte			(String key, Comparable val)					{return Criteria_fld.new_(key, Criteria_.lte_(val));}
	public static Criteria_fld New_mt			(String key, Comparable val)					{return Criteria_fld.new_(key, Criteria_.mt_(val));}
	public static Criteria_fld New_mte			(String key, Comparable val)					{return Criteria_fld.new_(key, Criteria_.mte_(val));}
	public static Criteria_fld New_between		(String key, Comparable lo, Comparable hi)	{return Criteria_fld.new_(key, Criteria_.between_(lo, hi));}
	public static Criteria_fld New_in			(String key, Object... vals)				{return Criteria_fld.new_(key, Criteria_.in_(vals));}
	public static Criteria_fld New_like			(String key, String pattern)					{return Criteria_fld.new_(key, Criteria_.like_(pattern));}

	public static Criteria eq_many_(String... ary) {
		Criteria rv = null;
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Criteria crt = Db_crt_.New_eq(ary[i], null);
			rv = (i == 0)? crt : Criteria_.And(rv, crt);
		}
		return rv;
	}
	public static Criteria eq_many_wo_null(String... ary) {
		Criteria rv = null;
		int len = ary.length;
		int crt_idx = 0;
		for (int i = 0; i < len; i++) {
			String itm = ary[i]; if (itm == Dbmeta_fld_itm.Key_null) continue;
			Criteria crt = Db_crt_.New_eq(itm, null);
			rv = (crt_idx == 0) ? crt : Criteria_.And(rv, crt);
			++crt_idx;
		}
		return rv;
	}
	public static Criteria eq_many_(Keyval... array) {
		Criteria rv = null;
		for (int i = 0; i < array.length; i++) {
			Keyval pair = array[i];
			Criteria crt = Db_crt_.New_eq(pair.Key(), pair.Val());
			rv = (i == 0)? crt : Criteria_.And(rv, crt);
		}
		return rv;
	}
	public static Criteria_fld wrap_(String key, Criteria crt) {return Criteria_fld.new_(key, crt);}
}
