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
package gplx.xowa.apps.servers; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.primitives.*; import gplx.core.js.*;
import gplx.gfui.*;
import gplx.xowa.apps.servers.tcp.*;
import gplx.xowa.apps.servers.http.*; import gplx.xowa.guis.views.*;
public class Gxw_html_server implements Gxw_html {
	private Xosrv_socket_wtr wtr; private Gfo_usr_dlg usr_dlg;
	private final Js_wtr js_wtr = new Js_wtr();
	public Gxw_html_server(Gfo_usr_dlg usr_dlg, Xosrv_socket_wtr wtr) {
		this.usr_dlg = usr_dlg; this.wtr = wtr;			
	} 
	public void			Html_doc_html_load_by_mem(String html) {Exec_as_str("location.reload(true);");}	// HACK: force reload of page
	public void			Html_doc_html_load_by_url(Io_url path, String html) {Exec_as_str("location.reload(true);");}	// HACK: force reload of page
	public byte			Html_doc_html_load_tid() {return html_doc_html_load_tid;} private byte html_doc_html_load_tid;
	public void			Html_doc_html_load_tid_(byte v) {html_doc_html_load_tid = v;}
	public void			Html_dispose() {}
	public void			Html_js_enabled_(boolean v) {}
	public String		Html_js_eval_proc_as_str(String name, Object... args)		{return Exec_as_str(js_wtr.Write_statement_return_func(name, args).To_str_and_clear());}	// TODO: add other params
	public boolean			Html_js_eval_proc_as_bool(String name, Object... args)	{return Exec_as_bool(js_wtr.Write_statement_return_func(name, args).To_str_and_clear());}
	public String		Html_js_eval_script(String script) {return Exec_as_str(script);}
	public void			Html_js_cbks_add(String js_func_name, GfoInvkAble invk) {}
	public void			Html_invk_src_(GfoEvObj v) {}
	public GxwCore_base	Core() {throw Err_.new_unimplemented();}
	public GxwCbkHost	Host() {throw Err_.new_unimplemented();} public void Host_set(GxwCbkHost host) {throw Err_.new_unimplemented();}
	public Object		UnderElem() {throw Err_.new_unimplemented();}
	public String		TextVal() {throw Err_.new_unimplemented();} public void TextVal_set(String v) {throw Err_.new_unimplemented();} 
			public void			EnableDoubleBuffering() {throw Err_.new_unimplemented();}
	private boolean Exec_as_bool(String s) {
		Exec_as_str(s);
		return true;	// NOTE: js is async, so immediate return value is not possible; return true for now;
	}
	private String Exec_as_str(String s) {
		if (wtr == null) return "";	// HACK: handles http_server
		s = "(function () {" + s + "})();"; // NOTE: dependent on firefox_addon which does 'var result = Function("with(arguments[0]){return "+cmd_text+"}")(session.window);'; DATE:2014-01-28
		gplx.core.threads.Thread_adp_.Sleep(50);	// NOTE: need to sleep, else images won't actually show up on screen; PAGE:nethackwiki.com:Weapons; DATE:2015-08-23
		Xosrv_msg msg = Xosrv_msg.new_(Xosrv_cmd_types.Browser_exec, Bry_.Empty, Bry_.Empty, Bry_.Empty, Bry_.Empty, Bry_.new_u8(s));
		usr_dlg.Note_many("", "", "sending browser.js: msg=~{0}", s);
		wtr.Write(msg);
		return "";
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set)) {}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_set = "set";
	public static void Init_gui_for_server(Xoae_app app, Xosrv_socket_wtr wtr) {
		Mem_kit mem_kit = (Mem_kit)gplx.gfui.Gfui_kit_.Mem();
		mem_kit.New_html_impl_prototype_(new Gxw_html_server(app.Usr_dlg(), wtr));	// NOTE: set prototype before calling Kit_
		app.Gui_mgr().Kit_(mem_kit);
	}
	public static void Assert_tab(Xoae_app app, Xoae_page page) {
		Xog_win_itm browser_win = app.Gui_mgr().Browser_win();
		if (browser_win.Active_tab() == null) {									// no active tab
			Xowe_wiki wiki = page.Wikie();										// take wiki from current page; NOTE: do not take from browser_win.Active_tab().Wiki(); DATE:2015-02-23
			browser_win.Tab_mgr().Tabs_new_init(wiki, page);					// create at least one active tab; DATE:2014-07-30
		}
	}
}
