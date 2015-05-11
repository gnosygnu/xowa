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
package gplx;
import gplx.core.strings.*;
import gplx.lists.*; /*EnumerAble,ComparerAble*/
public class OrderedHash_base extends HashAdp_base implements OrderedHash, GfoInvkAble {
	@Override protected void Add_base(Object key, Object val) {
		super.Add_base(key, val);
		ordered.Add(val);
		AssertCounts();
	}
	@Override public void Del(Object key) {
		if (!this.Has_base(key)) return;
		Object val = this.Fetch_base(key);
		this.Del_base(key);
		ordered.Del(val);
		AssertCounts();
	}
	protected Object FetchAt_base(int index) {return ordered.FetchAt(index);}
	protected int IndexOf_base(Object obj) {return ordered.IndexOf(obj);}
	@Override public void Clear() {
		if (locked) Lock_fail();
		super.Clear();
		ordered.Clear();
	}
	public Object Xto_ary(Class<?> type)			{return ordered.Xto_ary(type);}
	public Object Xto_ary_and_clear(Class<?> t)	{
		Object rv = Xto_ary(t);
		this.Clear();
		return rv;
	}
	@gplx.Virtual public void Sort()						{if (locked) Lock_fail(); ordered.Sort();}	// NOTE: uses item's .compareTo
	public void SortBy(ComparerAble comparer)		{if (locked) Lock_fail(); ordered.SortBy(comparer);}
	@Override public java.util.Iterator iterator() {return ordered.iterator();}
	public void AddAt(int i, Object key, Object val) {
		if (locked) Lock_fail(); 
		super.Add_base(key, val);
		ordered.AddAt(i, val);
		AssertCounts();
	}
	void AssertCounts() {
		if (super.Count() != ordered.Count()) throw Err_.new_("counts do not match").Add("hash", super.Count()).Add("list", ordered.Count());
	}
	public void ResizeBounds(int i) {if (locked) Lock_fail(); ordered.ResizeBounds(i);}
	public void Lock() {locked = true;} private boolean locked = false;
	void Lock_fail() {throw Err_mgr._.fmt_(GRP_KEY, "locked", "collection is locked");}
	static final String GRP_KEY = "gplx.lists.ordered_hash";
	public void AddAt(int i, Object o) {if (locked) Lock_fail(); ordered.AddAt(i, o);}
	public Object FetchAt(int i) {return FetchAt_base(i);}
	public Object FetchAtOr(int i, Object or) {return ordered.FetchAtOr(i, or);}
	public int IndexOf(Object obj) {return this.IndexOf_base(obj);}
	public void MoveTo(int src, int trg) {if (locked) Lock_fail(); ordered.MoveTo(src, trg);}
	public String XtoStr_ui() {
		String_bldr sb = String_bldr_.new_();
		int count = ordered.Count();
		int pad = String_.Len(Int_.Xto_str(count));
		for (int i = 0; i < count; i++) {
			sb	.Add(Int_.Xto_str_pad_bgn_zero(i, pad))
				.Add(":").Add(ordered.FetchAt(i).toString())
				.Add(Op_sys.Cur().Nl_str());
		}
		return sb.XtoStr();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_SetKeyOnly)) {
			String s = m.ReadStr("v");
			if (ctx.Deny()) return this;
			this.Add(s, s);
		}
		else if	(ctx.Match(k, Invk_Print)) {
			if (ctx.Deny()) return this;
			return XtoStr_ui();
		}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	static final String Invk_SetKeyOnly = "SetKeyOnly", Invk_Print = "Print";
	final ListAdp ordered = ListAdp_.new_();
	@Override public int Count() {return ordered.Count();}
	public OrderedHash_base() {}
}
