package gplx.types.basics.utls;
public class ClassLni {
	public static final boolean IsArray(Class<?> t) {return t.isArray();}
	public static final boolean IsAssignableFromByObj(Object o, Class<?> generic) {return o == null ? false : IsAssignableFrom(generic, o.getClass());}
	public static final boolean IsAssignableFrom(Class<?> generic, Class<?> specific) {return generic.isAssignableFrom(specific);}
	public static final Class<?> TypeByObj(Object o) {return o.getClass();}
	public static final String NameByObj(Object obj) {return obj == null ? StringLni.NullMark : Name(TypeByObj(obj));}
	public static final String Name(Class<?> type) {return type.getName();}
	public static final String CanonicalNameByObj(Object o) {return CanonicalName(o.getClass());}
	public static final String CanonicalName(Class<?> type) {return type.getCanonicalName();}
	public static final String SimpleNameByObj(Object obj) {return obj == null ? StringLni.NullMark : TypeByObj(obj).getSimpleName();}
	public static final boolean EqByObj(Class<?> lhsType, Object rhsObj) {
		Class<?> rhsType = rhsObj == null ? null : TypeByObj(rhsObj);
		return Eq(lhsType, rhsType);
	}
	public static final boolean Eq(Class<?> lhs, Class<?> rhs) {// NOTE: same as ObjectUtl.Eq
		if      (lhs == null && rhs == null) return true;
		else if (lhs == null || rhs == null) return false;
		else                                 return lhs.equals(rhs);
	}
}
