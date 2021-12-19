package gplx.types.basics.utls;
public class ObjectLni {
	public static final String ClsValName = "Object";
	public static final Object[] AryEmpty = new Object[0];
	public static final byte[] NullBry = BryLni.NewA7("null");

	public static boolean Eq(Object lhs, Object rhs) {
		if      (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		else                                 return lhs.equals(rhs);
	}
	public static final String ToStrOrNullMark(Object v) {return v == null ? StringLni.NullMark : ToStr(v);}
	public static final String ToStr(Object v) {
		Class<?> c = v.getClass();
		if      (ClassLni.Eq(c, String.class)) return (String)v;
		else if (ClassLni.Eq(c, byte[].class)) return StringLni.NewByBry((byte[])v);
		else                                   return v.toString();
	}
}
