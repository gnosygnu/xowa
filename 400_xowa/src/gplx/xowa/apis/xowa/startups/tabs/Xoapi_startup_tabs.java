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
public class Xoapi_startup_tabs implements GfoInvkAble {
	public String Custom() {return custom;} private String custom;
	public boolean Custom_is_expr() {return custom_is_expr;} private boolean custom_is_expr;
	public String Previous() {return previous;} public Xoapi_startup_tabs Previous_(String v) {previous = v; return this;} private String previous = "";
	public String Manual() {return manual;} public void Manual_(String v) {manual = v;} private String manual;
	public byte Type() {return type;} private byte type = Xoapi_startup_tabs_tid_.Tid_xowa;
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
	public String[] Calc_startup_strs() {
		if (manual != null) return new String[] {manual};
		switch (type) {
			case Xoapi_startup_tabs_tid_.Tid_blank:			return new String[] {gplx.xowa.specials.xowa.default_tab.Default_tab_page.Ttl_full_str};
			case Xoapi_startup_tabs_tid_.Tid_xowa:			return new String[] {gplx.xowa.users.Xouc_pages_mgr.Page_xowa};
			case Xoapi_startup_tabs_tid_.Tid_custom:		return String_.SplitLines_nl(String_.Trim(custom));
			case Xoapi_startup_tabs_tid_.Tid_previous:		return String_.SplitLines_nl(String_.Trim(previous));
			default:										throw Err_.unhandled(type);
		}
	}
}
