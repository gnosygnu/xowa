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
public class Yn {
	public static final String Y = "y", N = "n";
	public static boolean parse_or_n_(String v) {return parse_or_(v, false);}
	public static int parse_as_int(String v) {
		if		(v == null)				return Bool_.__int;
		else if (String_.Eq(v, "y"))	return Bool_.Y_int;
		else if (String_.Eq(v, "n"))	return Bool_.N_int;
		else							return Bool_.__int;
	}
	public static boolean parse_or_(String v, boolean or) {
		int v_int = parse_as_int(v);
		switch (v_int) {
			case Bool_.N_int: return false;
			case Bool_.Y_int: return true;
			case Bool_.__int: return or;
			default: throw Err_mgr._.unhandled_(v_int);
		}
	}
	public static boolean parse_(String v) {
		int v_int = parse_as_int(v);
		if (v_int == Bool_.__int) Err_mgr._.unhandled_(v);
		return v_int == Bool_.Y_int;
	}
	public static String X_to_str(boolean v) {return v ? "y" : "n";}
	public static boolean store_bool_or(SrlMgr mgr, String key, boolean or) {
		String v = mgr.SrlStrOr(key, "");
		return mgr.Type_rdr() ? parse_or_(v, or) : or;
	}
	public static boolean coerce_(Object o) {String s = String_.as_(o); return s != null ? parse_or_(s, false) : Bool_.cast_(o);}
	public static boolean readOrFalse_(DataRdr rdr, String key) {return read_(rdr, key, false);}
	public static boolean readOrTrue_(DataRdr rdr, String key) {return read_(rdr, key, true);}
	static boolean read_(DataRdr rdr, String key, boolean or) {
		String v = rdr.ReadStrOr(key, null);
		return parse_or_(v, or);
	}
}