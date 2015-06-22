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
import gplx.ios.*; /*IoUrlInfo_*/
public class Io_url_ {
	public static final Io_url Empty = new Io_url("", IoUrlInfo_.Nil);
	public static final Io_url NullPtr = null;
	public static final Io_url Parser = new Io_url("", IoUrlInfo_.Nil);
	public static Io_url as_(Object obj) {return obj instanceof Io_url ? (Io_url)obj : null;}
	public static Io_url cast_(Object obj) {try {return (Io_url)obj;} catch(Exception exc) {throw Err_.type_mismatch_exc_(exc, Io_url.class, obj);}}
	public static Io_url Usr() {
		if (usr_dir == null) {
			switch (Op_sys.Cur().Tid()) {
				case Op_sys.Tid_wnt: usr_dir = Io_url_.new_inf_("C:\\", IoUrlInfo_.Wnt); break;
				case Op_sys.Tid_lnx: usr_dir = Io_url_.new_inf_(String_.Format("/home/{0}/", Env_.UserName()), IoUrlInfo_.Lnx); break;
				case Op_sys.Tid_osx: usr_dir = Io_url_.new_inf_(String_.Format("/Users/{0}/", Env_.UserName()), IoUrlInfo_.Lnx); break;
				case Op_sys.Tid_drd: usr_dir = Io_url_.new_inf_(String_.Format("/mnt/{0}/", Env_.UserName()), IoUrlInfo_.Lnx); break;
				default: throw Err_.unhandled(Op_sys.Cur().Tid());
			}
		}
		return usr_dir;
	}	static Io_url usr_dir;
	public static Io_url Usr_Gplx() {return Usr().GenSubDir("gplx");}
	public static Io_url mem_dir_(String raw) {
		raw = EndsWith_or_add(raw, Op_sys.Lnx.Fsys_dir_spr_str());
		return new Io_url(raw, IoUrlInfoRegy._.Match(raw));
	}
	public static Io_url mem_fil_(String raw) {return new_inf_(raw, IoUrlInfoRegy._.Match(raw));}
	public static Io_url wnt_fil_(String raw) {return new_inf_(raw, IoUrlInfo_.Wnt);}
	public static Io_url wnt_dir_(String raw) {return new_inf_(EndsWith_or_add(raw, Op_sys.Wnt.Fsys_dir_spr_str()), IoUrlInfo_.Wnt);}
	public static Io_url lnx_fil_(String raw) {return new_inf_(raw, IoUrlInfo_.Lnx);}
	public static Io_url lnx_dir_(String raw) {return new_inf_(EndsWith_or_add(raw, Op_sys.Lnx.Fsys_dir_spr_str()), IoUrlInfo_.Lnx);}
	public static Io_url new_fil_(String raw) {return new_any_(raw);}
	public static Io_url new_dir_(String raw) {return new_any_(raw);}	// NOTE: for now, same as new_fil; stack overflow when doing new_dir
	public static Io_url new_any_(String raw) {return new_inf_(raw, IoUrlInfoRegy._.Match(raw));}
	public static Io_url new_inf_(String raw, IoUrlInfo info) {return String_.Eq(raw, "") ? Io_url_.Empty : new Io_url(raw, info);}
	public static Io_url http_any_(String src, boolean wnt) {
		return new_any_(parse_http_file(src, wnt));
	}
	private static String parse_http_file(String v, boolean wnt) {
		byte[] v_bry = Bry_.new_u8(v);
		int v_len = v_bry.length;
		if (Bry_.Has_at_bgn(v_bry, Io_url.Http_file_bry, 0, v_len)) {
			byte[] rv = new byte[v_len - Io_url.Http_file_len];
			for (int i = 0; i < rv.length; i++) {
				byte b = v_bry[i + Io_url.Http_file_len];
				if (wnt && b == Byte_ascii.Slash) b = Byte_ascii.Backslash;
				rv[i] = b;
			}
			return String_.new_u8(rv);
		}
		return v;
	}

	public static Io_url store_orFail_(SrlMgr mgr, String key, Io_url v) {
		String s = mgr.SrlStrOr(key, v.Raw());
		return (mgr.Type_rdr()) ? Io_url_.new_any_(s) : v;
	}
	public static Io_url store_orSelf_(SrlMgr mgr, String key, Io_url v) {
		String s = mgr.SrlStrOr(key, v.Raw());
		return (mgr.Type_rdr()) ? Io_url_.new_any_(s) : v;
	}
	public static Io_url rdrOr_(DataRdr rdr, String key, Io_url or) {
		String val = rdr.ReadStrOr(key, null); if (val == null) return or; // NOTE: val == null also checks for rdr == DataRdr_.Null
		return Io_url_.new_any_(val);
	}
	static String EndsWith_or_add(String raw, String endsWith) {
		if (String_.Has_at_end(raw, endsWith)) return raw;
		return raw += endsWith;
	}
	public static Io_url Rel_dir(String s) {return IsAbs(s) ? Io_url_.new_dir_(s) : Env_.AppUrl().OwnerDir().GenSubDir(s);}
	public static Io_url Rel_fil(String s) {return IsAbs(s) ? Io_url_.new_fil_(s) : Env_.AppUrl().OwnerDir().GenSubFil(s);}
	static boolean IsAbs(String s) {
		return String_.Has_at_bgn(s, Op_sys.Lnx.Fsys_dir_spr_str())
			|| (String_.Len(s) > 2
				&& (	(String_.CharAt(s, 1) == ':'  && String_.CharAt(s, 2) == '\\')
					||	(String_.CharAt(s, 1) == '\\' && String_.CharAt(s, 2) == '\\')
					)
				);
	}
}
