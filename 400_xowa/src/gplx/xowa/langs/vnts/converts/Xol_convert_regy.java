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
package gplx.xowa.langs.vnts.converts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
import gplx.xowa.apps.fsys.*;
public class Xol_convert_regy implements Gfo_invk {	// registry of convert_grp; EX: zh-hans;zh-hant;
	private final    Ordered_hash hash = Ordered_hash_.New_bry();		
	public Xol_convert_grp Get_or_null(byte[] key) {return (Xol_convert_grp)hash.Get_by(key);}
	public Xol_convert_grp Get_or_make(byte[] key) {
		Xol_convert_grp rv = (Xol_convert_grp)hash.Get_by(key);
		if (rv == null) {
			rv = new Xol_convert_grp(key);
			hash.Add(key, rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))				return Get_or_make(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get";
	public static Io_url Bld_url(Xoa_fsys_mgr app_fsys_mgr, String lang)	{return Bld_url(app_fsys_mgr.Cfg_lang_core_dir(), lang);}
	public static Io_url Bld_url(Io_url dir, String lang)					{return dir.GenSubFil_nest("variants", lang + ".gfs");}
}
