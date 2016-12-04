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
package gplx.xowa.apps.apis.xowa; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*;
import gplx.xowa.apps.apis.xowa.addons.*;
public class Xoapi_addon implements Gfo_invk {
	public void Ctor_by_app(Xoa_app app) {}
	public Xoapi_addon_search		Search()	{return search;}	private final    Xoapi_addon_search search = new Xoapi_addon_search();
	public Xoapi_addon_bldr			Bldr()		{return bldr;}		private final    Xoapi_addon_bldr bldr = new Xoapi_addon_bldr();
	public boolean						Wikis__ctgs__hidden_enabled() {return wikis__ctgs__hidden_enabled;} private boolean wikis__ctgs__hidden_enabled = false;
	public boolean						App__scripting__enabled() {return app__scripting__enabled;} private boolean app__scripting__enabled = false;
	public String					App__update__restart_cmd()		{return app__update__restart_cmd;} private String app__update__restart_cmd = "";
	public String					App__update__update_db_src()	{return app__update__update_db_src;} private String app__update__update_db_src = "http://xowa.org";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__search)) 								return search;
		else if	(ctx.Match(k, Invk__bldr)) 									return bldr;
		else if	(ctx.Match(k, Invk__wikis__ctgs__hidden_enabled)) 			return Yn.To_str(wikis__ctgs__hidden_enabled);
		else if	(ctx.Match(k, Invk__wikis__ctgs__hidden_enabled_)) 			wikis__ctgs__hidden_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__app__scripting__enabled)) 				return Yn.To_str(app__scripting__enabled);
		else if	(ctx.Match(k, Invk__app__scripting__enabled_)) 				app__scripting__enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__app__page_history__log_all)) 			return Yn.To_str(app__page_history__log_all);
		else if	(ctx.Match(k, Invk__app__page_history__log_all_)) 			app__page_history__log_all = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__app__update__restart_cmd)) 				return app__update__restart_cmd;
		else if	(ctx.Match(k, Invk__app__update__restart_cmd_)) 			app__update__restart_cmd = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__app__update__update_db_src)) 			return app__update__update_db_src;
		else if	(ctx.Match(k, Invk__app__update__update_db_src_)) 			app__update__update_db_src = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__search = "search", Invk__bldr = "bldr"
	, Invk__wikis__ctgs__hidden_enabled		= "wikis__ctgs__hidden_enabled"
	, Invk__wikis__ctgs__hidden_enabled_	= "wikis__ctgs__hidden_enabled_"
	, Invk__app__scripting__enabled			= "app__scripting__enabled"
	, Invk__app__scripting__enabled_		= "app__scripting__enabled_"
	, Invk__app__page_history__log_all		= "app__page_history__log_all"
	, Invk__app__page_history__log_all_		= "app__page_history__log_all_"
	, Invk__app__update__restart_cmd		= "app__update__restart_cmd"
	, Invk__app__update__restart_cmd_		= "app__update__restart_cmd_"
	, Invk__app__update__update_db_src		= "app__update__update_db_src"
	, Invk__app__update__update_db_src_		= "app__update__update_db_src_"
	;
	public static boolean app__page_history__log_all;
}
