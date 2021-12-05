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
package gplx.objects.arrays;
import gplx.objects.errs.ErrUtl;
import gplx.objects.lists.ComparerAble;
import gplx.objects.lists.ComparerAbleSorter;
import java.lang.reflect.Array;
public class ArrayUtl {
	public static int Len(Object ary) {return Array.getLength(ary);}
	public static int LenObjAry(Object[] ary) {return ary == null ? 0 : ary.length;}
	public static Object GetAt(Object ary, int i) {return Array.get(ary, i);}
	public static void SetAt(Object ary, int i, Object o) {Array.set(ary, i, o);}
	public static Object Create(Class<?> t, int count) {return Array.newInstance(t, count);}
	public static Object Expand(Object src, Object trg, int srcLen) {
		try                    {System.arraycopy(src, 0, trg, 0, srcLen);}
		catch (Exception e)    {throw ErrUtl.NewFmt(e, "ArrayUtl.Expand failed; srcLen={0}", srcLen);}
		return trg;
	}
	public static void Copy(Object src, Object trg) {System.arraycopy(src, 0, trg, 0, Len(src));}
	public static void CopyTo(Object src, Object trg, int trgPos) {System.arraycopy(src, 0, trg, trgPos, Len(src));}
	public static void CopyTo(Object src, int srcBgn, Object trg, int trgBgn, int srcLen) {System.arraycopy(src, srcBgn, trg, trgBgn, srcLen);}
	public static Object Clone(Object src)             {return Clone(src, 0, Len(src));}
	public static Object Clone(Object src, int srcBgn) {return Clone(src, srcBgn, Array.getLength(src));}
	private static Object Clone(Object src, int srcBgn, int srcEnd) {
		int trgLen = srcEnd - srcBgn;
		Object trg = Create(ComponentType(src), trgLen);
		CopyTo(src, srcBgn, trg, 0, trgLen);
		return trg;
	}
	public static Object Resize(Object src, int trgLen) {
		Object trg = Create(ComponentType(src), trgLen);
		int srcLen = Array.getLength(src);
		int copyLen = srcLen > trgLen ? trgLen : srcLen;    // trgLen can either expand or shrink
		CopyTo(src, 0, trg, 0, copyLen);
		return trg;
	}
	public static Object Append(Object src, Object add) {
		int srcLen = Len(src);
		int trgLen = srcLen + Len(add);
		Object trg = Create(ComponentType(src), trgLen);
		Copy(src, trg);
		for (int i = srcLen; i < trgLen; i++)
			SetAt(trg, i, GetAt(add, i - srcLen));
		return trg;
	}
	public static Object AppendOne(Object src, int srcLen, Object newObj) {
		Object rv = Resize(src, srcLen + 1);
		SetAt(rv, srcLen, newObj);
		return rv;
	}
	public static Object[] Insert(Object[] cur, Object[] add, int addPos) {
		int curLen = cur.length, addLen = add.length;
		Object[] rv = (Object[])Create(ComponentType(cur), curLen + addLen);
		for (int i = 0; i < addPos; i++)      // copy old up to addPos
			rv[i] = cur[i];
		for (int i = 0; i < addLen; i++)      // insert add
			rv[i + addPos] = add[i];
		for (int i = addPos; i < curLen; i++) // copy old after addPos
			rv[i + addLen] = cur[i];
		return rv;
	}
	public static void Sort(Object[] obj) {new ComparerAbleSorter().Sort(obj, obj.length);}
	public static void Sort(Object[] obj, ComparerAble comparer) {new ComparerAbleSorter().Sort(obj, obj.length, true, comparer);}
	public static Object Cast(Object o) {return o;} // NOTE: leftover from .NET; casts System.Object to System.Array
	private static Class<?> ComponentType(Object ary) {
		if (ary == null) throw ErrUtl.NewMsg("Array is null");
		return ary.getClass().getComponentType();
	}
}
