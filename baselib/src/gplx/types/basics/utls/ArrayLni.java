package gplx.types.basics.utls;
import java.lang.System;
import java.lang.reflect.Array;
public class ArrayLni {
	public static final Object Cast(Object o) {return o;} // NOTE: leftover from .NET; casts System.Object to System.Array
	public static final int Len(Object ary)                     {return Array.getLength(ary);}
	public static final Object GetAt(Object ary, int i)         {return Array.get(ary, i);}
	public static final void SetAt(Object ary, int i, Object o) {Array.set(ary, i, o);}
	public static final Object Create(Class<?> c, int count)    {return Array.newInstance(c, count);}
	public static final Class<?> ComponentType(Object ary)      {return ary.getClass().getComponentType();}
	public static final void CopyTo(Object src, int srcBgn
			, Object trg, int trgBgn, int srcLen)               {System.arraycopy(src, srcBgn, trg, trgBgn, srcLen);}

	public static final Object Resize(Object src, int trgLen) {
		Object trg = Create(ComponentType(src), trgLen);
		int srcLen = Len(src);
		int copyLen = srcLen > trgLen ? trgLen : srcLen;    // trgLen can either expand or shrink
		CopyTo(src, 0, trg, 0, copyLen);
		return trg;
	}
	public static final Object ResizeAddAry(Object src, Object... add) {
		int srcLen = Len(src);
		int addLen = add == null ? 0 : add.length;
		int trgLen = srcLen + addLen;
		Object trg = Create(ComponentType(src), trgLen);
		CopyTo(src, 0, trg, 0, srcLen);
		for (int i = 0; i < addLen; i++) {
			SetAt(trg, i + srcLen, add[i]);
		}
		return trg;
	}
}
