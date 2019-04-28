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
package gplx.objects.arrays; import gplx.*; import gplx.objects.*;
import java.lang.reflect.Array;
import gplx.objects.errs.*;
public class Array_ {
		public static int Len(Object ary) {return Array.getLength(ary);}
	public static final int Len_obj(Object[] ary) {return ary == null ? 0 : ary.length;}
	public static Object Get_at(Object ary, int i) {return Array.get(ary, i);}
	public static void Set_at(Object ary, int i, Object o) {Array.set(ary, i, o);}
	public static Object Create(Class<?> t, int count) {return Array.newInstance(t, count);}
	public static Object Expand(Object src, Object trg, int src_len) {
		try					{System.arraycopy(src, 0, trg, 0, src_len);}
		catch (Exception e)	{throw Err_.New_fmt(e, "Array_.Expand failed; src_len={0}", src_len);}
		return trg;
	}
	public static void Copy(Object src, Object trg) {System.arraycopy(src, 0, trg, 0, Len(src));}
	public static void Copy_to(Object src, Object trg, int trgPos) {System.arraycopy(src, 0, trg, trgPos, Len(src));}
	public static void Copy_to(Object src, int srcBgn, Object trg, int trgBgn, int srcLen) {System.arraycopy(src, srcBgn, trg, trgBgn, srcLen);}
	private static Class<?> Component_type(Object ary) {
		if (ary == null) throw Err_.New_msg("Array is null");
		return ary.getClass().getComponentType();
	}
	public static Object Resize_add(Object src, Object add) {
		int srcLen = Len(src);
		int trgLen = srcLen + Len(add);
		Object trg = Create(Component_type(src), trgLen);
		Copy(src, trg);
		for (int i = srcLen; i < trgLen; i++)
			Set_at(trg, i, Get_at(add, i - srcLen));
		return trg;
	}
	public static Object     Clone(Object src) {
		Object trg = Create(Component_type(src), Len(src));
		Copy(src, trg);
		return trg;
	}
	}
