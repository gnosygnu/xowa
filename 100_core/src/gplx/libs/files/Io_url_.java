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
package gplx.libs.files;
import gplx.core.envs.Env_;
import gplx.core.envs.Op_sys;
import gplx.core.envs.SystemUtl;
import gplx.core.ios.IoUrlInfo;
import gplx.core.ios.IoUrlInfoRegy;
import gplx.core.ios.IoUrlInfo_;
import gplx.core.stores.DataRdr;
import gplx.core.stores.SrlMgr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
public class Io_url_ {
	public static final Io_url Empty = new Io_url("", IoUrlInfo_.Nil);
	public static final Io_url NullPtr = null;
	public static final Io_url Parser = new Io_url("", IoUrlInfo_.Nil);
	public static Io_url as_(Object obj) {return obj instanceof Io_url ? (Io_url)obj : null;}
	public static Io_url cast(Object obj) {try {return (Io_url)obj;} catch(Exception exc) {throw ErrUtl.NewCast(exc, Io_url.class, obj);}}
	public static Io_url Usr() {
		if (usr_dir == null) {
			switch (Op_sys.Cur().Tid()) {
				case Op_sys.Tid_wnt: usr_dir = Io_url_.new_inf_("C:\\", IoUrlInfo_.Wnt); break;
				case Op_sys.Tid_lnx: usr_dir = Io_url_.new_inf_(StringUtl.Format("/home/{0}/", SystemUtl.Prop__user_name()), IoUrlInfo_.Lnx); break;
				case Op_sys.Tid_osx: usr_dir = Io_url_.new_inf_(StringUtl.Format("/Users/{0}/", SystemUtl.Prop__user_name()), IoUrlInfo_.Lnx); break;
				case Op_sys.Tid_drd: usr_dir = Io_url_.new_inf_(StringUtl.Format("/mnt/{0}/", SystemUtl.Prop__user_name()), IoUrlInfo_.Lnx); break;
				default: throw ErrUtl.NewUnhandled(Op_sys.Cur().Tid());
			}
		}
		return usr_dir;
	}   static Io_url usr_dir;
	public static Io_url Usr_Gplx() {return Usr().GenSubDir("gplx");}
	public static Io_url mem_dir_(String raw) {
		raw = EndsWith_or_add(raw, Op_sys.Lnx.Fsys_dir_spr_str());
		return new Io_url(raw, IoUrlInfoRegy.Instance.Match(raw));
	}
	public static Io_url mem_fil_(String raw) {return new_inf_(raw, IoUrlInfoRegy.Instance.Match(raw));}
	public static Io_url wnt_fil_(String raw) {return new_inf_(raw, IoUrlInfo_.Wnt);}
	public static Io_url wnt_dir_(String raw) {return new_inf_(EndsWith_or_add(raw, Op_sys.Wnt.Fsys_dir_spr_str()), IoUrlInfo_.Wnt);}
	public static Io_url lnx_fil_(String raw) {return new_inf_(raw, IoUrlInfo_.Lnx);}
	public static Io_url lnx_dir_(String raw) {return new_inf_(EndsWith_or_add(raw, Op_sys.Lnx.Fsys_dir_spr_str()), IoUrlInfo_.Lnx);}
	public static Io_url new_fil_(String raw) {return new_any_(raw);}
	public static Io_url new_dir_(String raw) {return new_any_(raw);}    // NOTE: for now, same as new_fil; stack overflow when doing new_dir
	public static Io_url new_dir_infer(String raw) {return Op_sys.Cur().Tid_is_wnt() ? wnt_dir_(raw) : lnx_dir_(raw);}
	public static Io_url new_any_(String raw) {return new_inf_(raw, IoUrlInfoRegy.Instance.Match(raw));}
	public static Io_url new_inf_(String raw, IoUrlInfo info) {return StringUtl.Eq(raw, "") ? Io_url_.Empty : new Io_url(raw, info);}
	public static Io_url New__http_or_fail(String raw) {return New__http_or_fail(BryUtl.NewU8(raw));}
	public static Io_url New__http_or_fail(byte[] raw) {
		Io_url rv = New__http_or_null(raw);
		if (rv == null) throw ErrUtl.NewArgs("url:invalid http_file raw", "raw", raw);
		return rv;
	}
	public static Io_url New__http_or_null(String raw) {return New__http_or_null(BryUtl.NewU8(raw));}
	public static Io_url New__http_or_null(byte[] raw) {
		int len = raw.length;
		if (!BryUtl.HasAtBgn(raw, Io_url.Http_file_bry, 0, len)) return null;    // doesn't start with "file:///"; return null;

		// bld rv; note that wnt has to convert / to \
		byte[] rv = null;
		if (Op_sys.Cur().Tid_is_wnt()) {
			int rv_len = len - Io_url.Http_file_len;
			rv = new byte[rv_len];
			for (int i = 0; i < rv_len; ++i) {
				byte b = raw[i + Io_url.Http_file_len];
				if (b == Op_sys.Dir_spr__lnx) b = Op_sys.Dir_spr__wnt;
				rv[i] = b;
			}
		}
		else
			rv = BryLni.Mid(raw, Io_url.Http_file_len);
		return rv == null ? null : new_any_(StringUtl.NewU8(rv));
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
		if (StringUtl.HasAtEnd(raw, endsWith)) return raw;
		return raw += endsWith;
	}
	public static Io_url Rel_dir(String s) {return IsAbs(s) ? Io_url_.new_dir_(s) : Env_.AppUrl().OwnerDir().GenSubDir(s);}
	public static Io_url Rel_fil(String s) {return IsAbs(s) ? Io_url_.new_fil_(s) : Env_.AppUrl().OwnerDir().GenSubFil(s);}
	static boolean IsAbs(String s) {
		return StringUtl.HasAtBgn(s, Op_sys.Lnx.Fsys_dir_spr_str())
			|| (StringUtl.Len(s) > 2
				&& (    (StringUtl.CharAt(s, 1) == ':'  && StringUtl.CharAt(s, 2) == '\\')
					||    (StringUtl.CharAt(s, 1) == '\\' && StringUtl.CharAt(s, 2) == '\\')
					)
				);
	}
	public static Io_url[] Ary(String... ary) {
		int len = ary.length;
		Io_url[] rv = new Io_url[len];
		for (int i = 0; i < len; ++i)
			rv[i] = Io_url_.new_any_(ary[i]);
		return rv;
	}
}
