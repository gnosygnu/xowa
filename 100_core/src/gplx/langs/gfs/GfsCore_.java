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
public class GfsCore_ {
	public static final    String Arg_primitive = "v";
	public static Object Exec(GfsCtx ctx, Gfo_invk owner_invk, GfoMsg owner_msg, Object owner_primitive, int depth) {
		if (owner_msg.Args_count() == 0 && owner_msg.Subs_count() == 0 && String_.Eq(owner_msg.Key(), "")) {UsrDlg_.Instance.Warn("empty msg"); return Gfo_invk_.Rv_unhandled;}
		if (owner_primitive != null) owner_msg.Parse_(false).Add(GfsCore_.Arg_primitive, owner_primitive);
		Object rv = owner_invk.Invk(ctx, 0, owner_msg.Key(), owner_msg);
		if		(rv == Gfo_invk_.Rv_cancel)		return rv;
		else if (rv == Gfo_invk_.Rv_unhandled)	{
			if (ctx.Fail_if_unhandled())
				throw Err_.new_wo_type("Object does not support key", "key", owner_msg.Key(), "ownerType", Type_adp_.FullNameOf_obj(owner_invk));
			else {
				Gfo_usr_dlg usr_dlg = ctx.Usr_dlg();
				if (usr_dlg != null) usr_dlg.Warn_many(GRP_KEY, "unhandled_key", "Object does not support key: key=~{0} ownerType=~{1}", owner_msg.Key(), Type_adp_.FullNameOf_obj(owner_invk));
				return Gfo_invk_.Noop;
			}
		}
		if (owner_msg.Subs_count() == 0) {					// msg is leaf
			GfsRegyItm regyItm = GfsRegyItm.as_(rv);
			if (regyItm == null) return rv;					// rv is primitive or other non-regy Object
			if (regyItm.IsCmd())							// rv is cmd; invk cmd
				return regyItm.InvkAble().Invk(ctx, 0, owner_msg.Key(), owner_msg);
			else											// rv is host
				return regyItm.InvkAble();
		}
		else {												// intermediate; cast to invk and call Exec
			Gfo_invk invk = Gfo_invk_.as_(rv);
			Object primitive = null;
			if (invk == null) {								// rv is primitive; find appropriate mgr
				throw Err_.new_wo_type("unknown primitive", "type", Type_adp_.NameOf_type(rv.getClass()), "obj", Object_.Xto_str_strict_or_null_mark(rv));
			}
			Object exec_rv = null;
			int len = owner_msg.Subs_count();
			for (int i = 0; i < len; i++)	// iterate over subs; needed for a{b;c;d;}
				exec_rv = Exec(ctx, invk, owner_msg.Subs_getAt(i), primitive, depth + 1); 
			return exec_rv;
		}
	}
	static final String GRP_KEY = "gplx.gfs_core";
}
//	class GfsRegyMgr : Gfo_invk {
//		public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
//			if		(ctx.Match(k, Invk_Add)) {
//				String libKey = m.ReadStr("libKey"), regKey = m.ReadStr("regKey");
//				if (ctx.Deny()) return this;
//				GfsRegyItm itm = regy.Get_by(libKey);
//				if (regy.Has(regKey)) {ctx.Write_warn("'{0}' already exists", regKey); return this;}
//				regy.Add(regKey, itm.InvkAble(), itm.Type_cmd());
//				ctx.Write_note("added '{0}' as '{1}'", regKey, libKey);
//			}
//			else if	(ctx.Match(k, Invk_Del)) {
//				String regKey = m.ReadStr("regKey");
//				if (ctx.Deny()) return this;
//				if (!regy.Has(regKey)) {ctx.Write_warn("{0} does not exist", regKey); return this;}
//				regy.Del(regKey);
//				ctx.Write_note("removed '{0}'", regKey);
//			}
//			else if	(ctx.Match(k, Invk_Load)) {
//				Io_url url = (Io_url)m.ReadObj("url", Io_url_.Parser);
//				if (ctx.Deny()) return this;
//				String loadText = Io_mgr.Instance.LoadFilStr(url);
//				GfoMsg loadMsg = core.MsgParser().ParseToMsg(loadText);
//				return core.Exec(ctx, loadMsg);
//			}
//			else return Gfo_invk_.Rv_unhandled;
//			return this;
//		}	public static final    String Invk_Add = "Add", Invk_Del = "Del", Invk_Load = "Load";
//		GfsCore core; GfsRegy regy;
//        public static GfsRegyMgr new_(GfsCore core, GfsRegy regy) {
//			GfsRegyMgr rv = new GfsRegyMgr();
//			rv.core = core; rv.regy = regy;
//			return rv;
//		}	GfsRegyMgr() {}
//	}
