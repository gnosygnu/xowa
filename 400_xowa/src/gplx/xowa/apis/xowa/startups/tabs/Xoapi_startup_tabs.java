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
package gplx.xowa.apis.xowa.startups.tabs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.startups.*;
import gplx.xowa.specials.*;
public class Xoapi_startup_tabs implements GfoInvkAble {
	public String Custom() {return custom;} private String custom;
	public boolean Custom_is_expr() {return custom_is_expr;} private boolean custom_is_expr;
	public String Previous() {return previous;} private String previous;
	public String Manual() {return manual;} public void Manual_(String v) {manual = v;} private String manual;
	public byte Type() {return type;} private byte type = Xoapi_startup_tabs_tid_.Tid_previous;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_type)) 								return Xoapi_startup_tabs_tid_.Xto_key(type);
		else if (ctx.Match(k, Invk_type_))								type = Xoapi_startup_tabs_tid_.Xto_tid(m.ReadStr("v"));
		else if (ctx.Match(k, Invk_type_list))							return Xoapi_startup_tabs_tid_.Options__list;
		else if	(ctx.Match(k, Invk_previous)) 							return previous;
		else if	(ctx.Match(k, Invk_previous_)) 							previous = m.ReadStr("v");
		else if	(ctx.Match(k, Invk_custom)) 							return custom;
		else if	(ctx.Match(k, Invk_custom_)) 							custom = m.ReadStr("v");
		else if (ctx.Match(k, Invk_custom_is_expr))						return Yn.Xto_str(custom_is_expr);
		else if (ctx.Match(k, Invk_custom_is_expr_))					custom_is_expr = m.ReadYn("v");
		else 															return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_type = "type", Invk_type_ = "type_", Invk_type_list = "type_list"
	, Invk_custom = "custom", Invk_custom_ = "custom_"
	, Invk_previous = "previous", Invk_previous_ = "previous_"
	, Invk_custom_is_expr = "custom_is_expr", Invk_custom_is_expr_ = "custom_is_expr_"
	;
	public String[] Calc_startup_strs(Xoae_app app) {
		List_adp rv = List_adp_.new_();
		String xowa_home = gplx.xowa.users.Xouc_pages_mgr.Page_xowa;
		if (manual == null) {
			switch (type) {
				case Xoapi_startup_tabs_tid_.Tid_blank:			rv.Add(Xows_special_meta_.Itm__default_tab.Ttl_str()); break;
				case Xoapi_startup_tabs_tid_.Tid_xowa:			rv.Add(xowa_home); break;
				case Xoapi_startup_tabs_tid_.Tid_custom:		Add_ary(rv, custom); break;
				case Xoapi_startup_tabs_tid_.Tid_previous:		Add_ary(rv, previous); break;
				default:										throw Err_.unhandled(type);
			}
		}
		else
			rv.Add(manual);
		Add_xowa_home_if_new_version(rv, app, xowa_home);
		return rv.To_str_ary();
	}
	private static void Add_ary(List_adp list, String s) {
		if (String_.Len_eq_0(s)) return;
		String[] ary = String_.SplitLines_nl(String_.Trim(s));
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			String itm = ary[i];
			if (String_.Len_eq_0(itm)) continue;
			list.Add(itm);
		}
	}
	private static void Add_xowa_home_if_new_version(List_adp rv, Xoae_app app, String xowa_home) {
		if (gplx.xowa.apps.versions.Xoa_version_.Compare(app.Api_root().App().Env().Version_previous(), Xoa_app_.Version) == CompareAble_.Less) {
			boolean xowa_home_exists = false;
			int len = rv.Count();
			for (int i = 0; i < len; ++i) {
				String itm = (String)rv.Get_at(i);
				if (String_.Eq(itm, xowa_home)) {
					xowa_home_exists = true;
					break;
				}
			}
			if (!xowa_home_exists)
				rv.Add(xowa_home);
		}
	}
}
