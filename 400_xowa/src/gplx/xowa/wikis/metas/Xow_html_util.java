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
package gplx.xowa.wikis.metas; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_html_util implements Gfo_invk {
	public Xow_html_util(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_if_bool))			return If_bool(m.ReadStr("expr"), m.ReadStr("true_val"), m.ReadStr("false_val"));
		else if	(ctx.Match(k, Invk_if_yn))				return If_yn(m.ReadStr("expr"), m.ReadStr("true_val"), m.ReadStr("false_val"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_if_bool = "if_bool", Invk_if_yn = "if_yn";
	String If_bool(String expr, String true_val, String false_val) {
		Object o = wiki.Appe().Gfs_mgr().Run_str(expr);
		try {return Bool_.Cast(o) ? true_val : false_val;}
		catch (Exception e) {Err_.Noop(e); return "expr failed: " + expr;}
	}
	String If_yn(String expr, String true_val, String false_val) {
		String o = String_.as_(wiki.Appe().Gfs_mgr().Run_str(expr));
		try {return Yn.parse(o) ? true_val : false_val;}
		catch (Exception e) {Err_.Noop(e); return "expr failed: " + expr;}
	}
}
