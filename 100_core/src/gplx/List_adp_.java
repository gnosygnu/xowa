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
import gplx.core.lists.*; /*EnumerAble,ComparerAble*/
public class List_adp_ {
	public static final List_adp Noop = new List_adp_noop();
	public static List_adp as_(Object obj) {return obj instanceof List_adp ? (List_adp)obj : null;}
	public static List_adp new_() {return new List_adp_obj();}
	public static List_adp size_(int v) {return new List_adp_obj(v);}
	public static List_adp many_(Object... ary) {
		List_adp rv = new List_adp_obj();
		rv.Add_many(ary);
		return rv;
	}
	public static void Add_list(List_adp rv, List_adp add) {
		int len = add.Count();
		for (int i = 0; i < len; ++i)
			rv.Add(add.Get_at(i));
	}
	public static void DelAt_last(List_adp list) {list.Del_at(list.Count() - 1);}
	public static Object Pop(List_adp list) {
		int lastIdx = list.Count() - 1;
		Object rv = list.Get_at(lastIdx);
		list.Del_at(lastIdx);
		return rv;
	}
	public static Object Pop_first(List_adp list) {	// NOTE: dirty way of implementing FIFO queue; should not be used with lists with many members
		Object rv = list.Get_at(0);
		list.Del_at(0);
		return rv;
	}
	public static Object Pop_last(List_adp list) {
		int last_idx = list.Count() - 1;
		Object rv = list.Get_at(last_idx);
		list.Del_at(last_idx);
		return rv;
	}
	public static Object Pop_or(List_adp list, Object or) {
		int list_len = list.Count(); if (list_len == 0) return or;
		int last_idx = list_len - 1;
		Object rv = list.Get_at(last_idx);
		list.Del_at(last_idx);
		return rv;
	}
	public static void DisposeAll(List_adp list) {
		for (int i = 0; i < list.Count(); i++)
			((RlsAble)list.Get_at(i)).Rls();
	}
	public static List_adp new_ary_(Object ary) {	
		int ary_len = Array_.Len(ary);
		List_adp rv = size_(ary_len);
		for (int i = 0; i < ary_len; i++)
			rv.Add(Array_.Get_at(ary, i));
		return rv;
	}
	public static final int Capacity_initial = 8;
	public static final int NotFound = -1, Base1 = 1, LastIdxOffset = 1, CountToPos = 1;
}
