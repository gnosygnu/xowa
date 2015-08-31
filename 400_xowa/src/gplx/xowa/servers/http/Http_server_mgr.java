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
/*
This file is part of XOWA: the XOWA Offline Wiki Application
Copyright (C) 2013 matthiasjasny@gmail.com

This file is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This file is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.servers.*;
import gplx.core.threads.*; import gplx.core.net.*; import gplx.core.json.*;
import gplx.xowa.pages.*;
public class Http_server_mgr implements GfoInvkAble {
	private final Object thread_lock = new Object();
	private final Gfo_usr_dlg usr_dlg;
	private Http_server_socket wkr;
	private byte retrieve_mode = File_retrieve_mode.Mode_wait;
	private boolean running, init_gui_done;
	public Http_server_mgr(Xoae_app app) {
		this.app = app;
		this.usr_dlg = app.Usr_dlg();
		this.request_parser = new Http_request_parser(server_wtr, false);
	}
	public Xoae_app App() {return app;} private final Xoae_app app;
	public Http_server_wtr Server_wtr() {return server_wtr;} private final Http_server_wtr server_wtr = Http_server_wtr_.new_console();
	public Http_request_parser Request_parser() {return request_parser;} private final Http_request_parser request_parser;
	public Url_encoder Encoder() {return encoder;} private final Url_encoder encoder = Url_encoder.new_http_url_();
	public int Port() {return port;} public Http_server_mgr Port_(int v) {port = v; return this;} private int port = 8080;
	public String Home() {return home;} public void Home_(String v) {home = v;} private String home = "home/wiki/Main_Page";
	private void Init_gui() {	// create a shim gui to automatically handle default XOWA gui JS calls
		if (init_gui_done) return;
		init_gui_done = true;
		Gxw_html_server.Init_gui_for_server(app, null);
	}
	private void Running_(boolean val) {
		if (val) {
			if (running)
				Note("HTTP Server already started");
			else {
				Run();
			}
		}
		else {
			if (running) {
				wkr.Canceled_(true);
				wkr = null;
				Note("HTTP Server stopped");
			}
			else
				Note("HTTP Server not started");
		}
		running = val;
	}
	public void Run() {
		if (wkr == null) wkr = new Http_server_socket(this);
		Thread_adp_.invk_("thread:xowa.http_server.server", wkr, Http_server_socket.Invk_run).Start();
		Note("HTTP Server started: Navigate to http://localhost:" + Int_.Xto_str(port));
	}
	public void Run_xowa_cmd(Xoae_app app, String url_encoded_str) {
		Url_encoder url_converter = Url_encoder.new_http_url_();	// create instance for each call
		String cmd = url_converter.Decode_str(url_encoded_str);
		app.Gfs_mgr().Run_str(cmd);
	}
	public String Parse_page_to_html(Http_data__client data__client, byte[] wiki_domain, byte[] page_ttl) {
		synchronized (thread_lock) {
			Init_gui();
			Xowe_wiki wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_key_or_make_init_y(wiki_domain);		// get the wiki; assert init for Main_Page; EX:click zh.w on wiki sidebar; DATE:2015-07-19
			if (Bry_.Len_eq_0(page_ttl)) page_ttl = wiki.Props().Main_page();
			Xoa_url page_url = wiki.Utl__url_parser().Parse(page_ttl);								// get the url (needed for query args)
			Xoa_ttl ttl = Xoa_ttl.parse(wiki, page_ttl);											// get the ttl
			Xoae_page page = wiki.Load_page_by_ttl(page_url, ttl);									// get page and parse it
			Gxw_html_server.Assert_tab(app, page);													// HACK: assert at least 1 tab
			app.Gui_mgr().Browser_win().Active_page_(page);											// HACK: init gui_mgr's page for output (which server ordinarily doesn't need)
			if (page.Missing()) {																	// if page does not exist, replace with message; else null_ref error; DATE:2014-03-08
				page.Data_raw_(Bry_.new_a7("'''Page not found.'''"));
				wiki.ParsePage(page, false);			
			}
			page.Html_data().Head_mgr().Itm__server().Init_by_http(data__client).Enabled_y_();
			byte[] output_html = wiki.Html_mgr().Page_wtr_mgr().Gen(page, Xopg_view_mode.Tid_read);		// write html from page data
			switch (retrieve_mode) {
				case File_retrieve_mode.Mode_skip:				break;	// noop
				case File_retrieve_mode.Mode_async_server:		app.Gui_mgr().Browser_win().Page__async__bgn(page.Tab_data().Tab()); break;
				case File_retrieve_mode.Mode_wait:
					if (page.File_queue().Count() > 0) {
						app.Gui_mgr().Browser_win().Active_tab().Async();
						output_html = wiki.Html_mgr().Page_wtr_mgr().Gen(page, Xopg_view_mode.Tid_read);
					}
					break;
			}
			return String_.new_u8(output_html);
		}
	}
	private void Note(String s) {
		// usr_dlg.Prog_many("", "", s); // messages should write to progress bar for gui
		usr_dlg.Note_many("", "", s);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_port))					return port;
		else if	(ctx.Match(k, Invk_port_))					Port_(m.ReadInt("v"));
		else if	(ctx.Match(k, Invk_running))				return Yn.Xto_str(running);
		else if	(ctx.Match(k, Invk_running_))				Running_(m.ReadYn("v"));
		else if	(ctx.Match(k, Invk_retrieve_mode))			return File_retrieve_mode.Xto_str(retrieve_mode);
		else if	(ctx.Match(k, Invk_retrieve_mode_))			retrieve_mode = File_retrieve_mode.Xto_byte(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_retrieve_mode_list))		return File_retrieve_mode.Options__list;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String 
	  Invk_port = "port", Invk_port_ = "port_"
	, Invk_running = "running", Invk_running_ = "running_"
	, Invk_retrieve_mode = "retrieve_mode", Invk_retrieve_mode_ = "retrieve_mode_", Invk_retrieve_mode_list = "retrieve_mode_list"
	;
}
