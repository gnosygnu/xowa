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
public class Criteria_in implements Criteria {
	public Criteria_in(boolean neg, Object[] ary) {this.neg = neg; Ary_(ary);}
	public byte            Tid() {return Criteria_.Tid_in;}
	public boolean            Neg() {return neg;} private final boolean neg;
	public Object[]        Ary() {return ary;} private Object[] ary;
	public int            Ary_len() {return ary_len;} private int ary_len;
	public Class<?>    Itm_type() {return itm_type;} private Class<?> itm_type;
	private void        Ary_(Object[] v) {
		this.ary = v;
		this.ary_len = ary.length;
		this.itm_type = ary_len == 0 ? Object.class : ClassUtl.TypeByObj(ary[0]);
	}
	public void            Val_as_obj_(Object v) {Ary_((Object[])v);}
	public void            Val_from_args(Hash_adp args) {throw ErrUtl.NewUnimplemented();}
	public boolean Matches(Object comp) {
		if (ary_len == 0) return false;    // empty array never matches
		if (!ClassUtl.EqByObj(itm_type, comp)) throw ErrUtl.NewCast(itm_type, comp);
		boolean rv = false;
		for (int i = 0; i < ary_len; ++i) {
			Object val = ary[i];
			if (ObjectUtl.Eq(val, comp)) {
				rv = true;
				break;
			}
		}
		return neg ? !rv : rv;
	}

	public String ToStr() {return StringUtl.ConcatObjs("IN ", StringUtl.ConcatObjs(ary));}
}
