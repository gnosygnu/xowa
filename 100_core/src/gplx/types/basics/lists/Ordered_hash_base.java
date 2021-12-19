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
package gplx.types.basics.lists;
import gplx.core.envs.Op_sys;
import gplx.core.lists.Hash_adp_base;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.lists.ComparerAble;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import java.util.Iterator;
public class Ordered_hash_base extends Hash_adp_base implements Ordered_hash, Gfo_invk {
	private final List_adp ordered = List_adp_.New();
	@Override protected void Add_base(Object key, Object val) {
		super.Add_base(key, val);
		ordered.Add(val);
		AssertCounts("Add_base", key);
	}
	@Override public void Del(Object key) {
		if (!this.Has_base(key)) return;
		Object val = this.Fetch_base(key);
		this.Del_base(key);
		ordered.Del(val);
		AssertCounts("Del", key);
	}
	protected Object Get_at_base(int index) {return ordered.GetAt(index);}
	protected int IndexOf_base(Object obj) {return ordered.IdxOf(obj);}
	@Override public void Clear() {
		if (locked) Lock_fail();
		super.Clear();
		ordered.Clear();
	}
	public Object ToAry(Class<?> type)            {return ordered.ToAry(type);}
	public Object ToAryAndClear(Class<?> t)    {
		Object rv = ToAry(t);
		this.Clear();
		return rv;
	}
	public void Sort()                        {if (locked) Lock_fail(); ordered.Sort();}    // NOTE: uses item's .compareTo
	public void SortBy(ComparerAble comparer)        {if (locked) Lock_fail(); ordered.SortBy(comparer);}
	@Override public Iterator iterator() {return ordered.iterator();}
	public void Add_at(int i, Object key, Object val) {
		if (locked) Lock_fail(); 
		super.Add_base(key, val);
		ordered.AddAt(i, val);
		AssertCounts("Add_at", key);
	}
	public Ordered_hash AddManyStr(String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			byte[] bry = BryUtl.NewU8(itm);
			this.Add(bry, bry);
		}
		return this;
	}
	private void AssertCounts(String proc, Object key) {
		if (super.Len() != ordered.Len()) throw ErrUtl.NewArgs("counts do not match; same key is either added twice, or delete failed", "proc", proc, "key", ObjectUtl.ToStrOrNullMark(key), "hash", super.Len(), "list", ordered.Len());
	}
	public void Resize_bounds(int i) {if (locked) Lock_fail(); ordered.ResizeBounds(i);}
	public void Lock() {locked = true;} private boolean locked = false;
	void Lock_fail() {throw ErrUtl.NewArgs("collection is locked");}
	static final String GRP_KEY = "gplx.core.lists.ordered_hash";
	public void AddAt(int i, Object o) {if (locked) Lock_fail(); ordered.AddAt(i, o);}
	public Object GetAt(int i) {return Get_at_base(i);}
	public int IdxOf(Object obj) {return this.IndexOf_base(obj);}
	public void MoveTo(int src, int trg) {if (locked) Lock_fail(); ordered.MoveTo(src, trg);}
	private String To_str_ui() {
		String_bldr sb = String_bldr_.new_();
		int count = ordered.Len();
		int pad = StringUtl.Len(IntUtl.ToStr(count));
		for (int i = 0; i < count; i++) {
			sb    .Add(IntUtl.ToStrPadBgnZero(i, pad))
				.Add(":").Add(ordered.GetAt(i).toString())
				.Add(Op_sys.Cur().Nl_str());
		}
		return sb.ToStr();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if        (ctx.Match(k, Invk_SetKeyOnly)) {
			String s = m.ReadStr("v");
			if (ctx.Deny()) return this;
			this.Add(s, s);
		}
		else if    (ctx.Match(k, Invk_Print)) {
			if (ctx.Deny()) return this;
			return To_str_ui();
		}
		else    return Gfo_invk_.Rv_unhandled;
		return this;
	}   static final String Invk_SetKeyOnly = "SetKeyOnly", Invk_Print = "Print";
	@Override public int Len() {return ordered.Len();}
	public Ordered_hash_base() {}
}
