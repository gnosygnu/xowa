package gplx.types.commons;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
public class XoKeyvalUtl {
	public static Object AryGetByKeyOrNull(KeyVal[] ary, String key) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			KeyVal kv = ary[i];
			if (StringUtl.Eq(kv.KeyToStr(), key)) return kv.Val();
		}
		return null;
	}
	public static String AryToStrNest(KeyVal... ary) {
		BryWtr bfr = BryWtr.New();
		AryToStrNestAry(bfr, 0, true, ary);
		return bfr.ToStrAndClear();
	}
	public static Object[] AryToObjAryVal(KeyVal[] ary) {
		int ary_len = ary.length;
		Object[] rv = new Object[ary_len];
		for (int i = 0; i < ary_len; i++) {
			rv[i] = ary[i].Val();
		}
		return rv;
	}
	private static void AryToStrNestAry(BryWtr bfr, int indent, boolean is_kv, Object[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			AryToStrNestVal(bfr, indent, is_kv, i, ary[i]);
		}
	}
	private static void AryToStrNestVal(BryWtr bfr, int indent, boolean is_kv, int idx, Object val) {
		if (indent > 0)
			bfr.AddByteRepeat(AsciiByte.Space, indent * 2);                  // add indent; EX: "  "
		String key = null;
		if (is_kv) {
			KeyVal kv = (KeyVal)val;
			key = ObjectUtl.ToStrOrEmpty(kv.KeyToStr());
			val = kv.Val();
		}
		else {
			key = IntUtl.ToStr(idx + 1);
		}
		bfr.AddStrU8(key).AddByteEq();                                      // add key + eq : "key="
		if (val == null)
			bfr.AddStrA7(StringUtl.NullMark);
		else {
			Class<?> val_type = ClassUtl.TypeByObj(val);
			if        (ClassUtl.Eq(val_type, KeyVal[].class)) {                    // val is Keyval[]; recurse
				bfr.AddByteNl();                                              // add nl: "\n"
				AryToStrNestAry(bfr, indent + 1, true, (KeyVal[])val);
				return;                                                         // don't add \n below
			}
			else if (ClassUtl.Eq(val_type, KeyVal.class)) {                      // val is Keyval; recurse
				bfr.AddByteNl();                                              // add nl: "\n"
				AryToStrNestVal(bfr, indent + 1, true, 1, (KeyVal)val);
				return;                                                         // don't add \n below
			}
			else if (ClassUtl.Eq(val_type, Object[].class)) {                    // val is Object[]
				bfr.AddByteNl();
				AryToStrNestAry(bfr, indent + 1, false, (Object[])val);
				return;                                                         // don't add \n below
			}
			else if (ClassUtl.Eq(val_type, BoolUtl.ClsRefType)) {                  // val is boolean
				boolean val_as_bool = BoolUtl.Cast(val);
				bfr.Add(val_as_bool ? BoolUtl.TrueBry : BoolUtl.FalseBry);        // add "true" or "false"; don't call toString
			}
			else
				bfr.AddStrU8(ObjectUtl.ToStrOrNullMark(val));       // call toString()
		}
		bfr.AddByteNl();
	}
}
