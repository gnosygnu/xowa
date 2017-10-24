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
public class Criteria_fld implements Criteria {
	Criteria_fld(String pre, String key, Criteria crt) {this.pre = pre; this.key = key; this.crt = crt;}
	public byte			Tid() {return Criteria_.Tid_wrapper;}
	public String		Pre() {return pre;} private final    String pre;
	public String		Key() {return key;} private final    String key;
	public Criteria		Crt() {return crt;} private final    Criteria crt;
	public String		Pre_w_key() {return pre == Pre_null ? key : String_.Concat(pre, ".", key);}
	public void			Val_as_obj_(Object v) {throw Err_.new_unimplemented();}
	public void			Val_from_args(Hash_adp args) {
		List_adp list = (List_adp)args.Get_by(key); if (list == null) throw Err_.new_wo_type("criteria.fld key not found", "key", key);
		Object o = Fill_val(key, crt.Tid(), list);
		crt.Val_as_obj_(o);
	}
	public boolean			Matches(Object invkObj) {			
		Gfo_invk invk = (Gfo_invk)invkObj;
		if (key == Criteria_fld.Key_null) return crt.Matches(invkObj);
		Object comp = Gfo_invk_.Invk_by_key(invk, this.Pre_w_key());			
		return crt.Matches(comp);
	}
	public String		To_str() {return String_.Concat(key, " ", crt.To_str());}

	public static final String Key_null = null, Pre_null = null;
	public static Criteria_fld as_(Object obj) {return obj instanceof Criteria_fld ? (Criteria_fld)obj : null;}
	public static Criteria_fld new_(String pre, String key, Criteria crt)	{return new Criteria_fld(pre, key, crt);}
	public static Criteria_fld new_(String key, Criteria crt)				{return new Criteria_fld(Pre_null, key, crt);}
	public static Object Fill_val(String key, byte tid, List_adp list) {
		int len = list.Count();
		switch (tid) {
			case Criteria_.Tid_eq:			
			case Criteria_.Tid_comp:
			case Criteria_.Tid_like:
			case Criteria_.Tid_iomatch:
				if (len != 1) throw Err_.new_wo_type("list.len should be 1", "key", key, "tid", tid, "len", len);
				return list.Get_at(0);
			case Criteria_.Tid_between:
				if (len != 2) throw Err_.new_wo_type("list.len should be 2", "key", key, "tid", tid, "len", len);
				return new Object[] {list.Get_at(0), list.Get_at(1)};
			case Criteria_.Tid_in:
				if (len == 0) throw Err_.new_wo_type("list.len should be > 0", "key", key, "tid", tid, "len", len);
				return list.To_obj_ary();
			case Criteria_.Tid_const:
			case Criteria_.Tid_not:
			case Criteria_.Tid_and:
			case Criteria_.Tid_or:
				if (len != 0) throw Err_.new_wo_type("list.len should be 0", "key", key, "tid", tid, "len", len);
				return key;					// no values to fill in; return back key
			case Criteria_.Tid_wrapper:		// not recursive
			case Criteria_.Tid_db_obj_ary:	// unsupported
			case Criteria_.Tid_custom:
			default: throw Err_.new_unhandled(tid);
		}
	}
}
