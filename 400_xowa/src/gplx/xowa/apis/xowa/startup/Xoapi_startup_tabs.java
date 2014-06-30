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
package gplx.xowa.apis.xowa.startup; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*;
class Xoapi_startup_tabs implements GfoInvkAble {
	public String[] Calc_startup_strs() {
		switch (type) {
			case Xoapi_startup_tabs_type_.Tid_blank:		return new String[] {gplx.xowa.specials.xowa.default_tab.Default_tab_page.Ttl_full_str};
			case Xoapi_startup_tabs_type_.Tid_xowa:			return new String[] {gplx.xowa.users.Xouc_pages_mgr.Page_xowa};
			case Xoapi_startup_tabs_type_.Tid_custom:		return String_.SplitLines_nl(custom);
			case Xoapi_startup_tabs_type_.Tid_previous:		return String_.SplitLines_nl(previous);
			default:										throw Err_.unhandled(type);
		}
	}
	public String Custom() {return custom;} private String custom;
	public boolean Custom_is_expr() {return custom_is_expr;} private boolean custom_is_expr;
	public String Previous() {return previous;} public Xoapi_startup_tabs Previous_(String v) {previous = v; return this;} private String previous = "";
	public byte Type() {return type;} private byte type = Xoapi_startup_tabs_type_.Tid_xowa;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_type)) 								return Xoapi_startup_tabs_type_.X_to_key(type);
		else if (ctx.Match(k, Invk_type_))								type = Xoapi_startup_tabs_type_.X_to_tid(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_custom)) 							return custom;
		else if	(ctx.Match(k, Invk_custom_)) 							custom = m.ReadStr("v");
		else if (ctx.Match(k, Invk_custom_is_expr))						return Yn.X_to_str(custom_is_expr);
		else if (ctx.Match(k, Invk_custom_is_expr_))					custom_is_expr = m.ReadYn("v");
		else 															return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk_type = "type", Invk_type_ = "type_"
	, Invk_custom = "custom", Invk_custom_ = "custom_"
	, Invk_custom_is_expr = "custom_is_expr", Invk_custom_is_expr_ = "custom_is_expr_"
	;
}
class Xoapi_startup_tabs_type_ {
	public static final byte Tid_blank = 0, Tid_xowa = 1, Tid_previous = 2, Tid_custom = 3;
	public static final String Key_blank = "blank", Key_xowa = "xowa", Key_previous = "previous", Key_custom = "custom";
	public static String X_to_key(byte v) {
		switch (v) {
			case Tid_blank:							return Key_blank;
			case Tid_xowa:							return Key_xowa;
			case Tid_previous:						return Key_previous;
			case Tid_custom:						return Key_custom;
			default:								throw Err_.not_implemented_();
		}
	}
	public static byte X_to_tid(String s) {
		if		(String_.Eq(s, Key_blank))			return Tid_blank;
		else if	(String_.Eq(s, Key_xowa))		return Tid_xowa;
		else if	(String_.Eq(s, Key_previous))		return Tid_previous;
		else if	(String_.Eq(s, Key_custom))			return Tid_custom;
		else										throw Err_.not_implemented_();
	}
	public static KeyVal[] Options__list = KeyVal_.Ary(KeyVal_.new_(Key_blank), KeyVal_.new_(Key_xowa), KeyVal_.new_(Key_previous), KeyVal_.new_(Key_custom));
}
/*
startup {
tabs {
	type = 'previous|blank|custom|xowa_home';
	custom = 'en.wikipedia.org/wiki/{{{MONTHNAME}}} {{{DAY}}';
	custom_has_wikitext = 'n';
}
}
*/
