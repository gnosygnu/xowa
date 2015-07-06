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
package gplx.xowa.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.servers.*;
import gplx.ios.*;
import gplx.core.net.*;
import gplx.xowa.html.js.*;
class Xosrv_http_wkr implements GfoInvkAble {
	private final Http_server_wtr server_wtr = Http_server_wtr_.new_console();
	private final Http_client_wtr client_wtr = Http_client_wtr_.new_stream();
	private final Http_client_rdr client_rdr = Http_client_rdr_.new_stream();
	private Socket_adp socket;
	private Xoae_app app;
	private String app_root_dir;
	public Xosrv_http_wkr(Socket_adp socket, Xoae_app app){
		this.socket = socket;
		this.app = app;
		this.app_root_dir = app.Fsys_mgr().Root_dir().To_http_file_str();
	}
	public void run(){
		try {
			String req = Get_request();
			if (req == null) return;
			client_wtr.Stream_(socket.Get_output_stream());
			String wiki_domain = "home";
			String page_name = "Main_Page";
			
			if(!String_.Has(req, "%file%")){
				if(String_.Eq(req, "/")) {	// no page; EX:"localhost:8080" vs "localhost:8080/en.wikipedia.org/wiki/Earth"
					String home_url = app.Http_server().Home();
					if (String_.Has_at_bgn(home_url, "file://")) {
						Io_url file_url = Io_url_.http_any_(home_url, Op_sys.Cur().Tid_is_wnt());
						String page_html = Io_mgr.I.LoadFilStr(file_url);
						Write_page(client_wtr, page_html, app_root_dir, wiki_domain);
					}
					else {
						req += app.Http_server().Home();
					}
				}
				req = Http_server_wkr_.Assert_main_page(app, req);
			}
			
			if(String_.Has(req, "%xowa-cmd%") || String_.Has(req, "/xowa-cmd:")){
				Write_xocmd(req, server_wtr, client_wtr);
			}
			else if(String_.Has(req, "%file%")){
				Write_file(req, server_wtr, client_wtr);
				client_rdr.Rls();
				socket.Rls();		
			}else{
				Write_wiki(req, server_wtr, client_wtr, wiki_domain, page_name);
			}
		}
		catch (Exception e) {
			String err_msg = String_.Format("error retrieving page. Please make sure your url is of the form: http://localhost:8080/home/wiki/Main_Page; err_msg={0}", Err_.Message_gplx(e));
			server_wtr.Write_str_w_nl(err_msg);
		}
	}
	private void Write_xocmd(String req, Http_server_wtr server_wtr, Http_client_wtr client_wtr) {
		String cmd = "";
		if (String_.Has(req, "%xowa-cmd%"))
			cmd = String_.Mid(req, String_.FindFwd(req, "%xowa-cmd%") + 20);
		else
			cmd = String_.Mid(req, String_.FindFwd(req, "/xowa-cmd:") + 10);
		server_wtr.Write_str_w_nl("Command output:" + cmd);
		app.Http_server().Run_xowa_cmd(app, cmd);
		server_wtr.Write_str_w_nl("Command sent, see console log for more details.");
		client_wtr.Rls();
	}
	private void Write_file(String req, Http_server_wtr server_wtr, Http_client_wtr client_wtr) {
		String path = String_.Replace(req, "/%file%/", app_root_dir);
		path = String_.Mid(path, String_.FindFwd(path, app_root_dir)+5);
		Url_encoder url_converter = Url_encoder.new_http_url_();
		path = url_converter.Decode_str(path);
		if (String_.Has(path, "?")) {
			path = String_.Mid(path, 0, String_.FindFwd(path, "?"));
		}		
		client_wtr.Write_str("HTTP/1.1 200 OK: ");
		client_wtr.Write_str("Content-Type: " + contentType(path) + Str_nl);
		client_wtr.Write_str(Str_nl);
		Io_stream_rdr file_rdr = Io_stream_rdr_.new_by_url_(Io_url_.new_fil_(path)); 
		file_rdr.Open();
		Write_file_bytes(file_rdr, client_wtr);
		file_rdr.Rls();
		client_wtr.Rls();
	}
	private void Write_wiki(String req, Http_server_wtr server_wtr, Http_client_wtr client_wtr, String wiki_domain, String page_name) {
		String[] req_split = String_.Split(req, "/");
		server_wtr.Write_str_w_nl("Request: " + req);
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
			Write_page(client_wtr, page_html, app_root_dir, wiki_domain);
		}catch(Exception err) {
			client_wtr.Write_str("Site not found. Check address please, or see console log.\n" + Err_.Message_lang(err));
			client_wtr.Rls();
		}
	}
	private void Write_file_bytes(Io_stream_rdr file_rdr, Http_client_wtr client_wtr) {
		byte[] bry = new byte[1024];
		int read = 0;
		while (true) {
			read = file_rdr.Read(bry, 0, 1024);
			if (read == -1) break;
			client_wtr.Write_mid(bry, 0, read);
		}
	}	
	private static void Write_page(Http_client_wtr client_wtr, String page_html, String app_file_dir, String wiki_domain) {
		page_html = Convert_page(page_html, app_file_dir, wiki_domain);
		Write_to_stream(client_wtr, page_html);		
	}
	private static String Convert_page(String page_html, String app_file_dir, String wiki_domain) {
		page_html = String_.Replace(page_html, app_file_dir			, "%file%/");
		page_html = String_.Replace(page_html, "xowa-cmd"			, "%xowa-cmd%/xowa-cmd");
		page_html = String_.Replace(page_html, "<a href=\"/wiki/"	, "<a href=\"/"+wiki_domain+"/wiki/");
		page_html = String_.Replace(page_html, "<a href='/wiki/"	, "<a href='/"+wiki_domain+"/wiki/");
		page_html = String_.Replace(page_html, "action=\"/wiki/"	, "action=\"/"+wiki_domain+"/wiki/");
		page_html = String_.Replace(page_html, "/site"				, "");
		return page_html;
	}
	private String Get_request() {
		try {
			client_rdr.Stream_(socket.Get_input_stream());
			String rv = client_rdr.Read_line();
			if (String_.Has_at_bgn(rv, "POST")) {
				Bry_bfr tmp_bfr = Bry_bfr.new_(255);
				boolean body_seen = false;
				String line = "";
				while (true) {
					line = client_rdr.Read_line();
					if (body_seen) {
						if (String_.Has_at_bgn(line, "---")) { 
							byte[] bry = tmp_bfr.Xto_bry_and_clear();
							String resp = app.Html__json_exec().Exec_json(bry);
							resp = String_.Replace(resp, "file:///J:/gplx/xowa/", "%file%/");
							client_wtr.Stream_(socket.Get_output_stream());
							client_wtr.Write_str("HTTP/1.1 200 OK: ");
							client_wtr.Write_str("Content-Type: text/html; charset=utf-8" + Str_nl);
							client_wtr.Write_str(Str_nl);				
							client_wtr.Write_str(resp);
							client_wtr.Rls();
							// client_wtr.Write_str("Access-Control-Allow-Origin: *" + Str_nl);	// No 'Access-Control-Allow-Origin' header is present on the requested resource.
							break;
						}
						else {
							tmp_bfr.Add_str_u8(line);
						}
					}
					else {
						if (String_.Eq(line, "Content-Disposition: form-data; name=\"data\"")) {
							body_seen = true;
						}
					}
				}
				return null;
			}
			rv = String_.Trim(String_.Mid(rv, 4, String_.Len(rv) - 9));
			return rv;
		} 	catch (Exception exc) {throw Err_.err_(exc);}
	}
	private static void Write_to_stream(Http_client_wtr client_wtr, String page_html) {
		try{
			client_wtr.Write_str("HTTP/1.1 200 OK: ");
			client_wtr.Write_str("Content-Type: text/html; charset=utf-8" + Str_nl);
//				client_wtr.Write_str("Access-Control-Allow-Origin: *" + Str_nl);	// No 'Access-Control-Allow-Origin' header is present on the requested resource.
			client_wtr.Write_str(Str_nl);				
			client_wtr.Write_bry(Bry_.new_u8(page_html));
			client_wtr.Rls();
		} catch (Exception err) {
			client_wtr.Write_str("Site not found. Check address please, or see console log.\n" + Err_.Message_lang(err));
			client_wtr.Rls();
		}		
	}
	private String contentType(String fileName) {
		if(String_.Has_at_end(fileName, ".htm") || String_.Has_at_end(fileName, ".html"))
			return "text/html";
		if(String_.Has_at_end(fileName, ".jpg"))
			return "text/jpg";
		if(String_.Has_at_end(fileName, ".gif"))
			return "text/gif";
		return "application/octet-stream";
	}
	private static final String Str_nl = "\n";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run)) {this.run();}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_run = "run";
}
