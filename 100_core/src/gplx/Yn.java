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
package gplx;
import gplx.core.stores.DataRdr;
import gplx.core.stores.SrlMgr;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Yn {
	public static final String Y = "y", N = "n";
	public static boolean parse_by_char_or(String v, boolean or) {
		if        (StringUtl.Eq(v, Y))    return true;
		else if    (StringUtl.Eq(v, N))    return false;
		else                        return or;
	}
	public static boolean parse_or_n_(String v) {return parse_or(v, false);}
	public static int parse_as_int(String v) {
		if        (v == null)                return BoolUtl.NullInt;
		else if (StringUtl.Eq(v, "y"))    return BoolUtl.YInt;
		else if (StringUtl.Eq(v, "n"))    return BoolUtl.NInt;
		else                            return BoolUtl.NullInt;
	}
	public static boolean parse_or(String v, boolean or) {
		int v_int = parse_as_int(v);
		switch (v_int) {
			case BoolUtl.NInt: return false;
			case BoolUtl.YInt: return true;
			case BoolUtl.NullInt: return or;
			default: throw ErrUtl.NewUnhandled(v_int);
		}
	}
	public static boolean parse(String v) {
		int v_int = parse_as_int(v);
		if (v_int == BoolUtl.NullInt) ErrUtl.NewUnhandled(v);
		return v_int == BoolUtl.YInt;
	}
	public static String To_str(boolean v) {return v ? "y" : "n";}
	public static String To_nullable_str(byte v) {
		switch (v) {
			case BoolUtl.YByte:        return "y";
			case BoolUtl.NByte:        return "n";
			case BoolUtl.NullByte:        return "?";
			default:                throw ErrUtl.NewUnhandled(v);
		}
	}
	public static byte To_nullable_byte(String v) {
		if (v != null && StringUtl.Len(v) == 1) {
			char c = StringUtl.CharAt(v, 0);
			switch (c) {
				case 'y':            return BoolUtl.YByte;
				case 'n':            return BoolUtl.NByte;
				case '?':            return BoolUtl.NullByte;
			}
		}
		throw ErrUtl.NewUnhandled(v);
	}
	public static boolean store_bool_or(SrlMgr mgr, String key, boolean or) {
		String v = mgr.SrlStrOr(key, "");
		return mgr.Type_rdr() ? parse_or(v, or) : or;
	}
	public static boolean coerce_(Object o) {String s = StringUtl.CastOrNull(o); return s != null ? parse_or(s, false) : BoolUtl.Cast(o);}
	public static boolean readOrFalse_(DataRdr rdr, String key) {return read_(rdr, key, false);}
	public static boolean readOrTrue_(DataRdr rdr, String key) {return read_(rdr, key, true);}
	static boolean read_(DataRdr rdr, String key, boolean or) {
		String v = rdr.ReadStrOr(key, null);
		return parse_or(v, or);
	}
}
