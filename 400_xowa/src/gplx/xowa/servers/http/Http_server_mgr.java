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
import gplx.ios.*; import gplx.json.*; import gplx.xowa.gui.*; import gplx.xowa.pages.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.StringTokenizer;
public class Http_server_mgr implements GfoInvkAble {
	private Gfo_usr_dlg usr_dlg;
	private byte retrieve_mode = File_retrieve_mode.Mode_wait;
	private boolean running;
	private Http_server_wkr wkr;
	public Http_server_mgr(Xoae_app app) {
		this.app = app;
		usr_dlg = app.Usr_dlg();
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public int Port() {return port;} public Http_server_mgr Port_(int v) {port = v; return this;} private int port = 8080;
	public String Home() {return home;} public void Home_(String v) {home = v;} private String home = "home/wiki/Main_Page";
	private boolean init_gui_done = false;
	private void Init_gui() {	// create a shim gui to automatically handle default XOWA gui JS calls
		if (init_gui_done) return;
		init_gui_done = true;
		Gxw_html_server.Init_gui_for_server(app, null);
	}
	public String Parse_page_to_html(Xoae_app app, String wiki_domain_str, String page_ttl_str) {
		Init_gui();
		byte[] wiki_domain = Bry_.new_utf8_(wiki_domain_str);
		byte[] page_ttl = Bry_.new_utf8_(page_ttl_str);
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(wiki_domain);							// get the wiki
		Xoa_url page_url = app.Url_parser().Parse(page_ttl);									// get the url (needed for query args)
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, page_ttl);											// get the ttl
		Xoae_page page = wiki.GetPageByTtl(page_url, ttl);										// get page and parse it
		Gxw_html_server.Assert_tab(app, page);													// HACK: assert at least 1 tab
		app.Gui_mgr().Browser_win().Active_page_(page);											// HACK: init gui_mgr's page for output (which server ordinarily doesn't need)
		if (page.Missing()) {																	// if page does not exist, replace with message; else null_ref error; DATE:2014-03-08
			page.Data_raw_(Bry_.new_ascii_("'''Page not found.'''"));
			wiki.ParsePage(page, false);			
		}
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
		return String_.new_utf8_(output_html);
	}
	private void Note(String s) {
		usr_dlg.Prog_many("", "", s);
		usr_dlg.Note_many("", "", s);
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
	public void Run_xowa_cmd(Xoae_app app, String url_encoded_str) {
		Url_encoder url_converter = Url_encoder.new_http_url_();	// create instance for each call
		String cmd = url_converter.Decode_str(url_encoded_str);
		app.Gfs_mgr().Run_str(cmd);
	}
	public void Run() {
		if (wkr == null)
			wkr = new Http_server_wkr(this, port);
				new Thread(wkr, "thread:xowa.http_server").start();
				Note("HTTP Server started: Navigate to http://localhost:" + Int_.Xto_str(port));
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
class Http_server_wkr implements Runnable {
	private Http_server_mgr webserver;
	private ServerSocket server_socket;
	private int port;
	public Http_server_wkr(Http_server_mgr webserver, int port) {
		this.webserver = webserver;
		this.port = port;
	}
	public boolean Canceled() {return canceled;}
	public void Canceled_(boolean v) {
		canceled = v;
		if (canceled) {
			try {
				if (server_socket != null)
					server_socket.close();
				server_socket = null;
			}
			catch (IOException e) {
				e.printStackTrace();
			}  
		}
	} private boolean canceled;
	public void run() {		
		try {
			if (server_socket == null)
				server_socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {// Listen for a TCP connection request.
			try {
				if (canceled) {
					if (server_socket != null)
						server_socket.close();
					server_socket = null;
					canceled = false;
					break;
				}
				Socket connectionSocket = server_socket.accept(); //Construct object to process HTTP request message
				HttpRequest request = new HttpRequest(connectionSocket, webserver.App());
				Thread thread = new Thread(request); //Create new thread to process	      
				thread.start(); //Start the thread	
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    }
	}
}
class HttpRequest implements Runnable{
	private static final String CRLF = "\r\n";
	private Socket socket;
	private Xoae_app app;
	private String app_root_dir;
	public HttpRequest(Socket socket, Xoae_app app){
		this.socket = socket;
		this.app = app;
		this.app_root_dir = app.Fsys_mgr().Root_dir().To_http_file_str();
	}
	public void run(){
		try {
			InputStream is = socket.getInputStream();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String request = br.readLine();
			//System.out.println(request);
			String req = request.substring(4, request.length() - 9).trim();
			String wiki_domain = "home";
			String page_name = "Main_Page";
			
			if(!req.contains("%file%")){
				if(req.equals("/")) {	// no page; EX:"localhost:8080" vs "localhost:8080/en.wikipedia.org/wiki/Earth"
					String home_url = app.Http_server().Home();;
					if (String_.HasAtBgn(home_url, "file://")) {
						Io_url file_url = Io_url_.http_any_(home_url, Op_sys.Cur().Tid_is_wnt());
						String page_html = Io_mgr._.LoadFilStr(file_url);
						Write_page(dos, page_html, app_root_dir, wiki_domain);
					}
					else
						req += app.Http_server().Home();
				}
				req = Http_server_wkr_.Assert_main_page(app, req);
			}
			
			if(req.contains("%xowa-cmd%") || req.contains("/xowa-cmd:")){
				System.out.println("Command output:");
				String cmd = "";
				if (req.contains("%xowa-cmd%"))
					cmd = req.substring(req.indexOf("%xowa-cmd%")+20);
				else
					cmd = req.substring(req.indexOf("/xowa-cmd:")+10);
				System.out.println(cmd);
				app.Http_server().Run_xowa_cmd(app, cmd);
				dos.writeBytes("Command sent, see console log for more details.");
				dos.close();
			}else
			if(req.contains("%file%")){
				String path = req.replace("/%file%/", app_root_dir);
				path = path.substring(path.indexOf(app_root_dir)+5);
				Url_encoder url_converter = Url_encoder.new_http_url_();
				path = url_converter.Decode_str(path);
				if(path.contains("?")){
					path = path.substring(0, path.indexOf("?"));
				}
				
				FileInputStream fis = new FileInputStream(path);
				
				dos.writeBytes("HTTP/1.1 200 OK: ");
				dos.writeBytes("Content-Type: " + contentType(path) + CRLF);
				dos.writeBytes(CRLF);
				
				sendBytes(fis, dos);
				fis.close();
				dos.close();
				br.close();
				socket.close();
			}else{
				String[] req_split = req.split("/");
				System.out.println("Request: " +request);
				if(req_split.length >= 1){
					wiki_domain = req_split[1];
				}
				if(req_split.length >= 4){
					page_name = req_split[3];
					for(int i = 4; i <= req_split.length-1; i++){
						page_name += "/"+req_split[i];
					}
					Url_encoder url_converter = Url_encoder.new_http_url_();
					page_name = url_converter.Decode_str(page_name);
					//page_name = app.Url_converter_url().Decode_str(page_name);
				}
				try{
					String page_html = app.Http_server().Parse_page_to_html(app, wiki_domain, page_name);
					Write_page(dos, page_html, app_root_dir, wiki_domain);
				}catch(Exception err) {
					dos.writeBytes("Site not found. Check address please, or see console log.\n"+err.getMessage());
					dos.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("error retrieving page. Please make sure your url is of the form: http://localhost:8080/home/wiki/Main_Page");
			e.printStackTrace();
		}
	}
	private static void Write_page(DataOutputStream strm, String page_html, String app_file_dir, String wiki_domain) {
		page_html = Convert_page(page_html, app_file_dir, wiki_domain);
		Write_to_stream(strm, page_html);		
	}
	private static String Convert_page(String page_html, String app_file_dir, String wiki_domain) {
		page_html = page_html.replaceAll(app_file_dir			, "%file%/");
		page_html = page_html.replaceAll("xowa-cmd"				, "%xowa-cmd%/xowa-cmd");
		page_html = page_html.replaceAll("<a href=\"/wiki/"		, "<a href=\"/"+wiki_domain+"/wiki/");
		page_html = page_html.replaceAll("<a href='/wiki/"		, "<a href='/"+wiki_domain+"/wiki/");
		page_html = page_html.replaceAll("action=\"/wiki/"		, "action=\"/"+wiki_domain+"/wiki/");
		page_html = page_html.replaceAll("/site"				, "");
		return page_html;
	}
	private static void Write_to_stream(DataOutputStream strm, String page_html) {
		try{
			strm.writeBytes("HTTP/1.1 200 OK: ");
			strm.writeBytes("Content-Type: text/html; charset=utf-8" + CRLF);
			strm.writeBytes(CRLF);				
			strm.write(page_html.getBytes(Charset.forName("UTF-8")));
			strm.close();
		} catch (Exception err) {
			try {
				strm.writeBytes("Site not found. Check address please, or see console log.\n"+err.getMessage());
				strm.close();
			}
			catch (Exception io_err) {}
		}		
	}
	private void sendBytes(FileInputStream fis, DataOutputStream dos) {
		byte[] buffer = new byte[1024];
		int bytes = 0;
		try {
			while((bytes= fis.read(buffer)) != -1){
				dos.write(buffer, 0, bytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String contentType(String fileName) {
		if(fileName.endsWith(".htm") || fileName.endsWith(".html"))
			return "text/html";
		if(fileName.endsWith(".jpg"))
			return "text/jpg";
		if(fileName.endsWith(".gif"))
			return "text/gif";
		return "application/octet-stream";
	}
}
