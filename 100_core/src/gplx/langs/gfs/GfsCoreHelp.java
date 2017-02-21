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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
import gplx.core.strings.*;
class GfsCoreHelp implements Gfo_invk {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		String path = m.ReadStrOr("path", "");
		if (String_.Eq(path, "")) {
			String_bldr sb = String_bldr_.new_();
			for (int i = 0; i < core.Root_as_regy().Count(); i++) {
				GfsRegyItm itm = (GfsRegyItm)core.Root_as_regy().Get_at(i);
				sb.Add_spr_unless_first(itm.Key(), String_.CrLf, i);
			}
			return sb.To_str();
		}
		else return Exec(ctx, core.Root_as_regy(), path);
	}
	public static Err Err_Unhandled(String objPath, String key) {return Err_.new_wo_type("obj does not handle msgKey", "objPath", objPath, "key", key).Trace_ignore_add_1_();}
	static Err Err_Unhandled(String[] itmAry, int i) {
		String_bldr sb = String_bldr_.new_();
		for (int j = 0; j < i; j++)
			sb.Add_spr_unless_first(itmAry[j], ".", j);
		return Err_Unhandled(sb.To_str(), itmAry[i]);
	}
	static Object Exec(GfsCtx rootCtx, Gfo_invk rootInvk, String path) {
		String[] itmAry = String_.Split(path, ".");
		Gfo_invk invk = rootInvk;
		GfsCtx ctx = GfsCtx.new_();
		Object curRv = null;
		for (int i = 0; i < itmAry.length; i++) {
			String itm = itmAry[i];
			curRv = invk.Invk(ctx, 0, itm, GfoMsg_.Null);
			if (curRv == Gfo_invk_.Rv_unhandled) throw Err_Unhandled(itmAry, i);
			invk = (Gfo_invk)curRv;
		}
		GfsCoreHelp helpData = GfsCoreHelp.as_(curRv);
		if (helpData != null) { // last itm is actually Method
			return "";
		}
		else {
			ctx = GfsCtx.new_().Help_browseMode_(true);
			invk.Invk(ctx, 0, "", GfoMsg_.Null);
			String_bldr sb = String_bldr_.new_();
			for (int i = 0; i < ctx.Help_browseList().Count(); i++) {
				String s = (String)ctx.Help_browseList().Get_at(i);
				sb.Add_spr_unless_first(s, String_.CrLf, i);
			}
			return sb.To_str();
		}
	}
	public static GfsCoreHelp as_(Object obj) {return obj instanceof GfsCoreHelp ? (GfsCoreHelp)obj : null;}
        public static GfsCoreHelp new_(GfsCore core) {
		GfsCoreHelp rv = new GfsCoreHelp();
		rv.core = core;
		return rv;
	}	GfsCoreHelp() {}
	GfsCore core;
}
