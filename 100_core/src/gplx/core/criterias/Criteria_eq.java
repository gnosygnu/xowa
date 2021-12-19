/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.criterias;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.errs.ErrUtl;
public class Criteria_eq implements Criteria {
	public Criteria_eq(boolean neg, Object val) {this.neg = neg; this.val = val;}
	public byte            Tid()        {return Criteria_.Tid_eq;}
	public boolean            Neg()        {return neg;} private final boolean neg;
	public Object        Val()        {return val;} private Object val;
	public void            Val_as_obj_(Object v) {this.val = v;}
	public void            Val_from_args(Hash_adp args) {throw ErrUtl.NewUnimplemented();}
	public boolean Matches(Object comp) {
		Class<?> val_type = ClassUtl.TypeByObj(val);
		if (!ClassUtl.EqByObj(val_type, comp)) throw ErrUtl.NewCast(val_type, comp);
		boolean rv = ObjectUtl.Eq(val, comp);
		return neg ? !rv : rv;
	}
	public String ToStr() {return StringUtl.ConcatObjs("= ", val);}
	public static Criteria_eq as_(Object obj) {return obj instanceof Criteria_eq ? (Criteria_eq)obj : null;}
}
