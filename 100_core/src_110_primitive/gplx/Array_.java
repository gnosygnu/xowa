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
import java.lang.reflect.Array;
import gplx.core.strings.*;
public class Array_ {
	public static void Sort(Object[] obj) {List_adp_sorter.new_().Sort(obj, obj.length);}
	public static void Sort(Object[] obj, gplx.lists.ComparerAble comparer) {List_adp_sorter.new_().Sort(obj, obj.length, true, comparer);}
	public static List_adp XtoList(Object ary) {	
		int aryLen = Array_.Len(ary);
		List_adp rv = List_adp_.new_();
		for (int i = 0; i < aryLen; i++)
			rv.Add(Array_.Get(ary, i));
		return rv;
	}
	public static Object[] Insert(Object[] cur, Object[] add, int addPos) {
		int curLen = cur.length, addLen = add.length;
		Object[] rv = (Object[])Array_.Create(Array_.ComponentType(cur), curLen + addLen);
		for (int i = 0; i < addPos; i++)			// copy old up to addPos
			rv[i] = cur[i];
		for (int i = 0; i < addLen; i++)			// insert add
			rv[i + addPos] = add[i];
		for (int i = addPos; i < curLen; i++)		// copy old after addPos
			rv[i + addLen] = cur[i];
		return rv;
	}
	public static Object[] ReplaceInsert(Object[] cur, Object[] add, int curReplacePos, int addInsertPos) {
		int curLen = cur.length, addLen = add.length; int newLen = addLen - addInsertPos;
		Object[] rv = (Object[])Array_.Create(Array_.ComponentType(cur), curLen + newLen);
		for (int i = 0; i < curReplacePos; i++)							// copy old up to curInsertPos; EX: curReplacePos=5, addInsertPos=2; copy up to element 3; 4, 5 are dropped
			rv[i] = cur[i];
		for (int i = 0; i < addLen; i++)								// insert add
			rv[i + curReplacePos] = add[i];
		for (int i = curReplacePos + addInsertPos; i < curLen; i++)		// copy old after curReplacePos
			rv[i + newLen] = cur[i];
		return rv;
	}
	public static Object Resize_add_one(Object src, int src_len, Object new_obj) {
		Object rv = Resize(src, src_len + 1);	
		Set(rv, src_len, new_obj);
		return rv;
	}
	public static Object Resize(Object src, int trg_len) {		
		Object trg = Create(ComponentType(src), trg_len);		
		int src_len = Array.getLength(src);								
		int copy_len = src_len > trg_len ? trg_len : src_len;	// trg_len can either expand or shrink
		CopyTo(src, 0, trg, 0, copy_len);
		return trg;
	}
	public static String To_str(Object ary) {	
		String_bldr sb = String_bldr_.new_();
		int ary_len = Len(ary);
		for (int i = 0; i < ary_len; i++)
			sb.Add_obj(Get(ary, i)).Add_char_nl();
		return sb.To_str();
	}
		public static int Len(Object ary) {return Array.getLength(ary);}
	public static final int LenAry(Object[] ary) {return ary == null ? 0 : ary.length;}
	public static Object Get_at(Object ary, int i) {return Array.get(ary, i);	}
	public static Object Create(Class<?> t, int count) {return Array.newInstance(t, count);}
	public static Object Get(Object ary, int i) {return Array.get(ary, i);}
	public static void Set(Object ary, int i, Object o) {Array.set(ary, i, o);}
	public static Object Expand(Object src, Object trg, int src_len) {
		try					{System.arraycopy(src, 0, trg, 0, src_len);}
		catch (Exception e)	{throw Err_.new_exc(e, "core", "Array_.Expand failed", "src_len", src_len);}
		return trg;
	}
	public static void Copy(Object src, Object trg) {System.arraycopy(src, 0, trg, 0, Len(src));}
	public static void CopyTo(Object src, Object trg, int trgPos) {System.arraycopy(src, 0, trg, trgPos, Len(src));}
	public static void CopyTo(Object src, int srcBgn, Object trg, int trgBgn, int srcLen) {System.arraycopy(src, srcBgn, trg, trgBgn, srcLen);}
	public static Class<?> ComponentType(Object ary) {
		if (ary == null) throw Err_.new_null();
		return ary.getClass().getComponentType();
	}
	public static Object Resize_add(Object src, Object add) {
		int srcLen = Len(src);
		int trgLen = srcLen + Len(add);
		Object trg = Create(ComponentType(src), trgLen);
		Copy(src, trg);
		for (int i = srcLen; i < trgLen; i++)
			Set(trg, i, Get(add, i - srcLen));
		return trg;
	}
	}
