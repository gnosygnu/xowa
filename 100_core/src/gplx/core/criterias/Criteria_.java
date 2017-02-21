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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
import gplx.core.texts.*; /*RegxPatn_cls_like*/
public class Criteria_ {
	public static final    Criteria All		= new Criteria_const(true);
	public static final    Criteria None	= new Criteria_const(false);
	public static Criteria Not(Criteria arg) {return new Criteria_not(arg);}
	public static Criteria And(Criteria lhs, Criteria rhs) {return new Criteria_and(lhs, rhs);}
	public static Criteria And_many(Criteria... ary) {
		int len = Array_.Len(ary); if (len == 0) throw Err_.new_wo_type("cannot AND 0 criterias;");
		Criteria rv = ary[0];
		for (int i = 1; i < len; i++)
			rv = And(rv, ary[i]);
		return rv;
	}
	public static Criteria Or(Criteria lhs, Criteria rhs) {return new Criteria_or(lhs, rhs);}
	public static Criteria Or_many(Criteria... ary) {
		int len = Array_.Len(ary); if (len == 0) throw Err_.new_wo_type("cannot OR 0 criterias;");
		Criteria rv = ary[0];
		for (int i = 1; i < len; i++)
			rv = Or(rv, ary[i]);
		return rv;
	}
	public static Criteria eq_(Object arg) {return new Criteria_eq(false, arg);}
	public static Criteria eqn_(Object arg) {return new Criteria_eq(true, arg);}
	public static Criteria in_(Object... array) {return new Criteria_in(false, array);}
	public static Criteria inn_(Object... array) {return new Criteria_in(true, array);}
	public static Criteria lt_(Comparable val) {return new Criteria_comp(CompareAble_.Less, val);}
	public static Criteria lte_(Comparable val) {return new Criteria_comp(CompareAble_.Less_or_same, val);}
	public static Criteria mt_(Comparable val) {return new Criteria_comp(CompareAble_.More, val);}
	public static Criteria mte_(Comparable val) {return new Criteria_comp(CompareAble_.More_or_same, val);}
	public static Criteria between_(Comparable lhs, Comparable rhs) {return new Criteria_between(false, lhs, rhs);}
	public static Criteria between_(boolean negated, Comparable lhs, Comparable rhs) {return new Criteria_between(negated, lhs, rhs);}
	public static Criteria like_(String pattern) {return new Criteria_like(false, RegxPatn_cls_like_.parse(pattern, RegxPatn_cls_like.EscapeDefault));}
	public static Criteria liken_(String pattern) {return new Criteria_like(true, RegxPatn_cls_like_.parse(pattern, RegxPatn_cls_like.EscapeDefault));}
	public static final byte Tid_custom = 0, Tid_const = 1, Tid_not = 2, Tid_and = 3, Tid_or = 4, Tid_eq = 5, Tid_between = 6, Tid_in = 7, Tid_like = 8, Tid_comp = 9, Tid_wrapper = 10, Tid_iomatch = 11, Tid_db_obj_ary = 12;
}
