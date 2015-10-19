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
package gplx.xowa.apps.apis.xowa.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*;
import gplx.xowa.apps.apis.xowa.gui.browsers.*;
public class Xoapi_browser implements GfoInvkAble {
	public void Init_by_kit(Xoae_app app) {
		url.Init_by_kit(app);
		search.Init_by_kit(app);
		tabs.Init_by_kit(app);
		html.Init_by_kit(app);
		find.Init_by_kit(app);
		prog.Init_by_kit(app);
		info.Init_by_kit(app);
		prog_log.Init_by_kit(app);
	}
	public Xoapi_url		Url()		{return url;} private Xoapi_url url = new Xoapi_url();
	public Xoapi_search		Search()	{return search;} private Xoapi_search search = new Xoapi_search();
	public Xoapi_tabs		Tabs()		{return tabs;} private Xoapi_tabs tabs = new Xoapi_tabs();
	public Xoapi_html_box	Html()		{return html;} private Xoapi_html_box html = new Xoapi_html_box();
	public Xoapi_find		Find()		{return find;} private Xoapi_find find = new Xoapi_find();
	public Xoapi_prog		Prog()		{return prog;} private Xoapi_prog prog = new Xoapi_prog();
	public Xoapi_info		Info()		{return info;} private Xoapi_info info = new Xoapi_info();
	public Xoapi_prog_log	Prog_log()	{return prog_log;} private Xoapi_prog_log prog_log = new Xoapi_prog_log();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_url)) 						return url;
		else if	(ctx.Match(k, Invk_search)) 					return search;
		else if	(ctx.Match(k, Invk_tabs)) 						return tabs;
		else if	(ctx.Match(k, Invk_html)) 						return html;
		else if	(ctx.Match(k, Invk_find)) 						return find;
		else if	(ctx.Match(k, Invk_prog)) 						return prog;
		else if	(ctx.Match(k, Invk_info)) 						return info;
		else if	(ctx.Match(k, Invk_prog_log)) 					return prog_log;
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String 
	  Invk_url = "url", Invk_search = "search", Invk_tabs = "tabs", Invk_html = "html"
	, Invk_find = "find", Invk_prog = "prog", Invk_info = "info", Invk_prog_log = "prog_log";
}
