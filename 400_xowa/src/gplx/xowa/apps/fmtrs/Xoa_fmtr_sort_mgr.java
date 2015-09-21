/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.apps.fmtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_fmtr_sort_mgr implements GfoInvkAble {
	private Ordered_hash itms = Ordered_hash_.new_();
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
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_clear = "clear", Invk_add_many = "add_many", Invk_exec = "exec";
}
class Xoa_fmtr_sort_itm {
	public Xoa_fmtr_sort_itm(String key, boolean asc) {this.key = key; this.comp_mult = asc ? CompareAble_.More : CompareAble_.Less;}
	public int Comp_mult() {return comp_mult;} private int comp_mult;
	public String Key() {return key;} private String key;
}
class Xoa_fmtr_sort_wkr implements gplx.lists.ComparerAble {
	public Xoa_fmtr_sort_itm[] Itms() {return itms;} public void Itms_(Xoa_fmtr_sort_itm[] v) {itms = v; itms_len = v.length;} private Xoa_fmtr_sort_itm[] itms; private int itms_len;
	public int compare(Object lhsObj, Object rhsObj) {
		GfoInvkAble lhs_invk = (GfoInvkAble)lhsObj;
		GfoInvkAble rhs_invk = (GfoInvkAble)rhsObj;
		for (int i = 0; i < itms_len; i++) {
			Xoa_fmtr_sort_itm itm = itms[i];
			String itm_key = itm.Key();
			Object lhs_val = GfoInvkAble_.InvkCmd(lhs_invk, itm_key);
			Object rhs_val = GfoInvkAble_.InvkCmd(rhs_invk, itm_key);
			int compare = CompareAble_.Compare_obj(lhs_val, rhs_val) * itm.Comp_mult();
			if (compare != CompareAble_.Same)
				return compare;
		}
		return CompareAble_.Same;
	}
}
