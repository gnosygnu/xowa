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
