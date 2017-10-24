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
package gplx.xowa.apps.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_fmtr_sort_mgr implements Gfo_invk {
	private Ordered_hash itms = Ordered_hash_.New();
	private Xoa_fmtr_sort_wkr wkr = new Xoa_fmtr_sort_wkr();
	private Gfo_sort_able sort_able;
	public Xoa_fmtr_sort_mgr(Gfo_sort_able sort_able) {this.sort_able = sort_able;}
	public void Clear() {itms.Clear();}
	public void Add_many(String[] keys) {
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; i++) {
			Xoa_fmtr_sort_itm itm = new Xoa_fmtr_sort_itm(keys[i], true);
			itms.Add_if_dupe_use_1st(itm.Key(), itm);
		}
	}
	public void Exec() {
		wkr.Itms_((Xoa_fmtr_sort_itm[])itms.To_ary(Xoa_fmtr_sort_itm.class));
		sort_able.Sort(wkr);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_clear))					this.Clear();
		else if	(ctx.Match(k, Invk_add_many)) 				this.Add_many(m.ReadStrAry("k", "|"));
		else if	(ctx.Match(k, Invk_exec)) 					this.Exec();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_clear = "clear", Invk_add_many = "add_many", Invk_exec = "exec";
}
class Xoa_fmtr_sort_itm {
	public Xoa_fmtr_sort_itm(String key, boolean asc) {this.key = key; this.comp_mult = asc ? CompareAble_.More : CompareAble_.Less;}
	public int Comp_mult() {return comp_mult;} private int comp_mult;
	public String Key() {return key;} private String key;
}
class Xoa_fmtr_sort_wkr implements gplx.core.lists.ComparerAble {
	public Xoa_fmtr_sort_itm[] Itms() {return itms;} public void Itms_(Xoa_fmtr_sort_itm[] v) {itms = v; itms_len = v.length;} private Xoa_fmtr_sort_itm[] itms; private int itms_len;
	public int compare(Object lhsObj, Object rhsObj) {
		Gfo_invk lhs_invk = (Gfo_invk)lhsObj;
		Gfo_invk rhs_invk = (Gfo_invk)rhsObj;
		for (int i = 0; i < itms_len; i++) {
			Xoa_fmtr_sort_itm itm = itms[i];
			String itm_key = itm.Key();
			Object lhs_val = Gfo_invk_.Invk_by_key(lhs_invk, itm_key);
			Object rhs_val = Gfo_invk_.Invk_by_key(rhs_invk, itm_key);
			int compare = CompareAble_.Compare_obj(lhs_val, rhs_val) * itm.Comp_mult();
			if (compare != CompareAble_.Same)
				return compare;
		}
		return CompareAble_.Same;
	}
}
