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
import gplx.core.primitives.*; import gplx.core.net.*;
import gplx.xowa.apps.*;
import gplx.xowa.html.js.*;
class Http_server_wkr_v2 implements GfoInvkAble {
	private final Http_server_wtr server_wtr;
	private final Http_client_wtr client_wtr = Http_client_wtr_.new_stream();
	private final Http_client_rdr client_rdr = Http_client_rdr_.new_stream();
	private final Http_request_parser request_parser;
	private final Url_encoder url_encoder;
	private final Xoae_app app;
	private final String root_dir_http;
	private final byte[] root_dir_fsys;
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(64);
	private Socket_adp socket;
	private Http_data__client data__client;
	public Http_server_wkr_v2(Http_server_mgr server_mgr){
		this.app = server_mgr.App(); this.server_wtr = server_mgr.Server_wtr(); this.url_encoder = server_mgr.Encoder();
		this.root_dir_http = app.Fsys_mgr().Root_dir().To_http_file_str();
		this.root_dir_fsys = Bry_.new_u8(app.Fsys_mgr().Root_dir().Raw());
		this.request_parser = server_mgr.Request_parser();
	}
	public void Init_by_thread(Socket_adp socket) {
		this.socket = socket;
	}
	public void Run(){
		Http_request_itm request = null;
		try {
			client_rdr.Stream_(socket.Get_input_stream());
			client_wtr.Stream_(socket.Get_output_stream());
			request = request_parser.Parse(client_rdr);
			this.data__client = new Http_data__client(request.Host(), socket.Ip_address());
			byte[] url_bry = request.Url();
			if (Bry_.Eq(url_bry, Url__home)) url_bry = Url__home__main_page;	// "localhost:8080" comes thru as url of "/"; transform to "home/wiki/Main_Page"
			switch (request.Type()) {
				case Http_request_itm.Type_get:		Process_get(request, url_bry); break;
				case Http_request_itm.Type_post:	Process_post(request); break;
			}
			client_wtr.Rls(); // client_rdr.Rls(); socket.Rls();
		}
		catch (Exception e) {
			String request_str = request == null ? "<<NULL>>" : request.To_str(tmp_bfr, Bool_.N);
			server_wtr.Write_str_w_nl(String_.Format("failed to process request;\nrequest={0}\nerr_msg={1}", request_str, Err_.Message_gplx_full(e)));
		}
	}
	private void Process_get(Http_request_itm request, byte[] url) {
		server_wtr.Write_str_w_nl(String_.new_u8(request.Host()) + "|GET|" + String_.new_u8(request.Url()));	// use request url
		if		(Bry_.Has_at_bgn(url, Url__fsys))	Serve_file(url);
		else if (Bry_.Has_at_bgn(url, Url__exec))	Exec_exec(url, Url__exec);
		else if (Bry_.Has_at_bgn(url, Url__exec_2))	Exec_exec(url, Url__exec_2);
		else										Write_wiki(url);
	}
	private void Serve_file(byte[] url) {
		tmp_bfr.Clear().Add(root_dir_fsys);	// add "C:\xowa\"
		int question_pos = Bry_finder.Find_fwd(url, Byte_ascii.Question);
		int url_bgn = Bry_.Has_at_bgn(url, Url__fsys) ? Url__fsys_len : 0;	// most files will have "/fsys/" at start, but Mathjax will not
		int url_end = question_pos == Bry_finder.Not_found ? url.length : question_pos;	// ignore files with query params; EX: /file/A.png?key=val
		url_encoder.Decode(url, url_bgn, url_end, tmp_bfr, false);		// decode url to actual chars; note that XOWA stores on fsys in UTF-8 chars; "�" not "%C3"
		byte[] path = tmp_bfr.Xto_bry_and_clear();
		client_wtr.Write_bry(Xosrv_http_wkr_.Rsp__http_ok);
		// 	client_wtr.Write_str("Expires: Sun, 17-Jan-2038 19:14:07 GMT\n");
		String mime_type = String_.new_u8(Http_file_utl.To_mime_type_by_path_as_bry(path));
		client_wtr.Write_str("Content-Type: " + mime_type + "\n\n");
		Io_stream_rdr file_stream = Io_stream_rdr_.new_by_url_(Io_url_.new_fil_(String_.new_u8(path))).Open();
		client_wtr.Write_stream(file_stream);
		file_stream.Rls(); client_rdr.Rls(); socket.Rls();
	}
	private void Exec_exec(byte[] url, byte[] tkn_bgn) {
		byte[] cmd = Bry_.Mid(url, tkn_bgn.length);
		app.Http_server().Run_xowa_cmd(app, String_.new_u8(cmd));
	}
	private void Write_wiki(byte[] req) {
		String wiki_domain = ""; String page_name = "";
		String[] req_split = String_.Split(String_.new_u8(req), "/");
		if(req_split.length >= 1){
			wiki_domain = req_split[1];
		}
		if(req_split.length >= 4){
			page_name = req_split[3];
			for(int i = 4; i <= req_split.length-1; i++){
				page_name += "/" + req_split[i];
			}
			page_name = url_encoder.Decode_str(page_name);
		}
		String page_html = app.Http_server().Parse_page_to_html(data__client, Bry_.new_u8(wiki_domain), Bry_.new_u8(page_name));
		page_html = Convert_page(page_html, root_dir_http, wiki_domain);
		Xosrv_http_wkr_.Write_response_as_html(client_wtr, Bool_.N, page_html);
	}
	private void Process_post(Http_request_itm request) {
		byte[] msg = request.Post_data_hash().Get_by(Key__msg).Val();
		byte[] app_mode = request.Post_data_hash().Get_by(Key__app_mode).Val();
		Xoa_app_type app_mode_itm = Xoa_app_type.parse(String_.new_u8(app_mode));
		server_wtr.Write_str_w_nl(String_.new_u8(request.Host()) + "|POST|" + String_.new_u8(msg));
		Object url_tid_obj = post_url_hash.Get_by_bry(request.Url()); if (url_tid_obj == null) throw Err_.new_wo_type("unknown url", "url", request.Url(), "request", request.To_str(tmp_bfr, Bool_.N));
		String rv = null;
		switch (((Int_obj_val)url_tid_obj).Val()) {
			case Tid_post_url_json:
				rv = app.Html__bridge_mgr().Cmd_mgr().Exec(msg);
				break;
			case Tid_post_url_gfs:
				rv = Object_.Xto_str_strict_or_null_mark(app.Gfs_mgr().Run_str(String_.new_u8(msg)));
				break;
		}
		if (app_mode_itm.Uid_is_http())
			rv = Convert_page(rv, root_dir_http			, "<<MISSING_WIKI>>");
		Xosrv_http_wkr_.Write_response_as_html(client_wtr, app_mode_itm.Uid() == Xoa_app_type.Itm_file.Uid(), rv);
	}
	private static final byte[] Key__msg = Bry_.new_a7("msg"), Key__app_mode = Bry_.new_a7("app_mode");
	private static final int Tid_post_url_json = 1, Tid_post_url_gfs = 2;
	private static final Hash_adp_bry post_url_hash = Hash_adp_bry.ci_a7()
	.Add_str_int("/exec/json"	, Tid_post_url_json)
	.Add_str_int("/exec/gfs"	, Tid_post_url_gfs)
	;
	private static String Convert_page(String page_html, String root_dir_http, String wiki_domain) {
		page_html = String_.Replace(page_html, root_dir_http		, "/fsys/");
		page_html = String_.Replace(page_html, "xowa-cmd:"			, "/exec/");
		page_html = String_.Replace(page_html, "<a href=\"/wiki/"	, "<a href=\"/" + wiki_domain + "/wiki/");
		page_html = String_.Replace(page_html, "<a href='/wiki/"	, "<a href='/" + wiki_domain + "/wiki/");
		page_html = String_.Replace(page_html, "action=\"/wiki/"	, "action=\"/" + wiki_domain + "/wiki/");
		page_html = String_.Replace(page_html, "/site"				, "");
		return page_html;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_run)) {this.Run();}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_run = "run";
	private static final byte[] 
	  Url__home = Bry_.new_a7("/"), Url__fsys = Bry_.new_a7("/fsys/")
	, Url__exec = Bry_.new_a7("/exec/"), Url__exec_2 = Bry_.new_a7("/xowa-cmd:")
	, Url__home__main_page = Bry_.new_a7("/home/wiki/Main_Page");
	private static final int Url__fsys_len = Url__fsys.length;
}
class Xosrv_http_wkr_ {
	public static void Write_response_as_html(Http_client_wtr client_wtr, boolean cross_domain, String html) {Write_response_as_html(client_wtr, cross_domain, Bry_.new_u8(html));}
	public static void Write_response_as_html(Http_client_wtr client_wtr, boolean cross_domain, byte[] html) {
		try{
			client_wtr.Write_bry(Rsp__http_ok);
			if (cross_domain)
				client_wtr.Write_str("Access-Control-Allow-Origin: *\n");	// No 'Access-Control-Allow-Origin' header is present on the requested resource.
			client_wtr.Write_bry(Rsp__content_type_html);
			client_wtr.Write_bry(Byte_ascii.Nl_bry);
			client_wtr.Write_bry(html);
		} catch (Exception err) {
			client_wtr.Write_str("Site not found. Check address please, or see console log.\n" + Err_.Message_lang(err));
			client_wtr.Rls();
		}		
	}
	public static final byte[]
	  Rsp__http_ok				= Bry_.new_a7("HTTP/1.1 200 OK:\n")
	, Rsp__content_type_html	= Bry_.new_a7("Content-Type: text/html; charset=utf-8\n")
	;
}
class Http_file_utl {
	public static byte[] To_mime_type_by_path_as_bry(byte[] path_bry) {
		int dot_pos = Bry_finder.Find_bwd(path_bry, Byte_ascii.Dot);
		return dot_pos == Bry_finder.Not_found ? Mime_octet_stream : To_mime_type_by_ext_as_bry(path_bry, dot_pos, path_bry.length);
	}
	public static byte[] To_mime_type_by_ext_as_bry(byte[] ext_bry, int bgn, int end) {
		Object o = mime_hash.Get_by_mid(ext_bry, bgn, end);
		return o == null ? Mime_octet_stream : (byte[])o;
	}
	private static final byte[] 
	  Mime_html				= Bry_.new_a7("text/html")
	, Mime_jpg				= Bry_.new_a7("image/jpeg")
	, Mime_png				= Bry_.new_a7("image/png")
	, Mime_gif				= Bry_.new_a7("image/gif")
	, Mime_css				= Bry_.new_a7("text/css")
	, Mime_js				= Bry_.new_a7("application/javascript")
	, Mime_octet_stream		= Bry_.new_a7("application/octet-stream")
	;
	private static final Hash_adp_bry mime_hash = Hash_adp_bry.ci_a7()
	.Add_str_obj(".htm"		, Mime_html)
	.Add_str_obj(".html"	, Mime_html)
	.Add_str_obj(".jpg"		, Mime_jpg)
	.Add_str_obj(".png"		, Mime_png)
	.Add_str_obj(".gif"		, Mime_gif)
	.Add_str_obj(".css"		, Mime_css)
	.Add_str_obj(".js"		, Mime_js)
	;
}
