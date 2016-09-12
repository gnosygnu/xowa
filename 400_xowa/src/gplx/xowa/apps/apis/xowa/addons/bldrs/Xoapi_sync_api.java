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
package gplx.xowa.apps.apis.xowa.addons.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.addons.*;
public class Xoapi_sync_api implements Gfo_invk {
	public boolean				Manual_enabled()		{return manual_enabled;}		private boolean manual_enabled = false;
	public boolean				Auto_enabled()			{return auto_enabled;}			private boolean auto_enabled = false;
	public int				Auto_interval()			{return auto_interval;}			private int auto_interval = 60 * 24;	// in minutes
	public String			Auto_scope()			{return auto_scope;}			private String auto_scope = "Main_Page";
	public Xopg_match_mgr	Auto_page_matcher() {return auto_page_matcher;} private final    Xopg_match_mgr auto_page_matcher = new Xopg_match_mgr();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__manual_enabled)) 						return Yn.To_str(manual_enabled);
		else if	(ctx.Match(k, Invk__manual_enabled_)) 						manual_enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk__auto_enabled)) 							return Yn.To_str(auto_enabled);
		else if	(ctx.Match(k, Invk__auto_enabled_)) 						auto_enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk__auto_interval)) 						return Int_.To_str(auto_interval);
		else if	(ctx.Match(k, Invk__auto_interval_)) 						auto_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__auto_scope)) 							return auto_scope;
		else if	(ctx.Match(k, Invk__auto_scope_)) 							auto_scope = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk__manual_enabled				= "manual_enabled"			, Invk__manual_enabled_				= "manual_enabled_"
	, Invk__auto_enabled				= "auto_enabled"			, Invk__auto_enabled_				= "auto_enabled_"
	, Invk__auto_interval				= "auto_interval"			, Invk__auto_interval_				= "auto_interval_"
	, Invk__auto_scope					= "auto_scope"				, Invk__auto_scope_					= "auto_scope_"
	;
}
