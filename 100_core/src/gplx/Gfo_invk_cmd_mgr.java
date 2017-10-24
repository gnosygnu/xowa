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
package gplx;
import gplx.core.primitives.*;
public class Gfo_invk_cmd_mgr {
	public Gfo_invk_cmd_mgr Add_cmd_many(Gfo_invk invk, String... keys) {
		for (String key : keys)
			list.Add(GfoInvkCmdItm.new_(key, invk));
		return this;
	}
	public Gfo_invk_cmd_mgr Add_cmd(String key, Gfo_invk invk) {
		list.Add(GfoInvkCmdItm.new_(key, invk));
		return this;
	}
	public Gfo_invk_cmd_mgr Add_mgr(String key, Gfo_invk invk) {
		list.Add(GfoInvkCmdItm.new_(key, invk).Type_isMgr_(true));
		return this;
	}
	public Gfo_invk_cmd_mgr Add_xtn(Gfo_invk xtn) {
		list.Add(GfoInvkCmdItm.new_("xtn", xtn).Type_isXtn_(true));
		return this;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m, Object host) {
		for (int i = 0; i < list.Count(); i++) {
			GfoInvkCmdItm itm = (GfoInvkCmdItm)list.Get_at(i);
			if (itm.Type_isXtn()) {
				Object invkVal = itm.Invk().Invk(ctx, ikey, k, m);
				if (invkVal != Gfo_invk_.Rv_unhandled) return invkVal;
			}
			if (!ctx.Match(k, itm.Key())) continue;
			if (itm.Type_isMgr()) return itm.Invk();
			Object rv = null;
			m.Add("host", host);
			rv = itm.Invk().Invk(ctx, ikey, k, m);
			return rv == Gfo_invk_.Rv_host ? host : rv;	// if returning "this" return host
		}
		return Unhandled;
	}
	public static final    String_obj_val Unhandled = String_obj_val.new_("Gfo_invk_cmd_mgr Unhandled");
	List_adp list = List_adp_.New();
        public static Gfo_invk_cmd_mgr new_() {return new Gfo_invk_cmd_mgr();} Gfo_invk_cmd_mgr() {}
}
class GfoInvkCmdItm {
	public String Key() {return key;} private String key;
	public Gfo_invk Invk() {return invk;} Gfo_invk invk;
	public boolean Type_isMgr() {return type_isMgr;} public GfoInvkCmdItm Type_isMgr_(boolean v) {type_isMgr = v; return this;} private boolean type_isMgr;
	public boolean Type_isXtn() {return type_isXtn;} public GfoInvkCmdItm Type_isXtn_(boolean v) {type_isXtn = v; return this;} private boolean type_isXtn;
        public static GfoInvkCmdItm new_(String key, Gfo_invk invk) {
		GfoInvkCmdItm rv = new GfoInvkCmdItm();
		rv.key = key; rv.invk = invk;
		return rv;
	}	GfoInvkCmdItm() {}
}
