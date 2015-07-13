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
import gplx.core.strings.*;
class GfsCoreHelp implements GfoInvkAble {
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		String path = m.ReadStrOr("path", "");
		if (String_.Eq(path, "")) {
			String_bldr sb = String_bldr_.new_();
			for (int i = 0; i < core.Root_as_regy().Count(); i++) {
				GfsRegyItm itm = (GfsRegyItm)core.Root_as_regy().Get_at(i);
				sb.Add_spr_unless_first(itm.Key(), String_.CrLf, i);
			}
			return sb.XtoStr();
		}
		else return Exec(ctx, core.Root_as_regy(), path);
	}
	public static Exc Err_Unhandled(String objPath, String key) {return Exc_.new_("obj does not handle msgKey", "objPath", objPath, "key", key).Stack_erase_1_();}
	static Exc Err_Unhandled(String[] itmAry, int i) {
		String_bldr sb = String_bldr_.new_();
		for (int j = 0; j < i; j++)
			sb.Add_spr_unless_first(itmAry[j], ".", j);
		return Err_Unhandled(sb.XtoStr(), itmAry[i]);
	}
	static Object Exec(GfsCtx rootCtx, GfoInvkAble rootInvk, String path) {
		String[] itmAry = String_.Split(path, ".");
		GfoInvkAble invk = rootInvk;
		GfsCtx ctx = GfsCtx.new_();
		Object curRv = null;
		for (int i = 0; i < itmAry.length; i++) {
			String itm = itmAry[i];
			curRv = invk.Invk(ctx, 0, itm, GfoMsg_.Null);
			if (curRv == GfoInvkAble_.Rv_unhandled) throw Err_Unhandled(itmAry, i);
			invk = GfoInvkAble_.as_(curRv);
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
			return sb.XtoStr();
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
