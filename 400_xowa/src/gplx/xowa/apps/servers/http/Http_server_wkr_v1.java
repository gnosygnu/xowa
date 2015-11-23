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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.envs.Op_sys;
import gplx.core.net.*; import gplx.core.threads.*; import gplx.langs.htmls.encoders.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
class Http_server_wkr_v1 implements Runnable{
	private static final String CRLF = "\r\n";
	private Socket socket;
	private Xoae_app app;
	private String app_root_dir;
	public Http_server_wkr_v1(Socket socket, Xoae_app app){
		this.socket = socket;
		this.app = app;
		this.app_root_dir = app.Fsys_mgr().Root_dir().To_http_file_str();
	}
	public void run(){
		String err_line = "00";
		try {
			InputStream is = socket.getInputStream(); err_line = "01";
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); err_line = "02";
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))); err_line = "03";
			String request = br.readLine(); err_line = "04";
			//System.out.println(request); err_line = "03";
			String req = request.substring(4, request.length() - 9).trim(); err_line = "05";
			String wiki_domain = "home"; err_line = "06";
			String page_name = "Main_Page"; err_line = "07";
			
			if(!req.contains("%file%")){
				if(req.equals("/")) {	// no page; EX:"localhost:8080" vs "localhost:8080/en.wikipedia.org/wiki/Earth"
					String home_url = String_.new_u8(app.Http_server().Home());  err_line = "08";
					if (String_.Has_at_bgn(home_url, "file://")) {
						Io_url file_url = Io_url_.http_any_(home_url, Op_sys.Cur().Tid_is_wnt()); err_line = "09";
						String page_html = Io_mgr.Instance.LoadFilStr(file_url); err_line = "10";
						Write_page(dos, page_html, app_root_dir, wiki_domain); err_line = "11";
					}
					else {
						req += app.Http_server().Home(); err_line = "12";
					}
				}
				req = Http_server_wkr_.Assert_main_page(app, req); err_line = "13";
			}
			
			if(req.contains("%xowa-cmd%") || req.contains("/xowa-cmd:")){
				System.out.println("Command output:"); err_line = "14";
				String cmd = ""; err_line = "15";
				if (req.contains("%xowa-cmd%"))
					cmd = req.substring(req.indexOf("%xowa-cmd%")+20);
				else
					cmd = req.substring(req.indexOf("/xowa-cmd:")+10);
				System.out.println(cmd); err_line = "15";
				app.Http_server().Run_xowa_cmd(app, cmd); err_line = "16";
				dos.writeBytes("Command sent, see console log for more details."); err_line = "17";
				dos.close(); err_line = "18";
			}
			else if(req.contains("%file%")){
				String path = req.replace("/%file%/", app_root_dir); err_line = "19";
				path = path.substring(path.indexOf(app_root_dir)+5); err_line = "20";
				Gfo_url_encoder url_converter = Gfo_url_encoder_.Http_url; err_line = "21";
				path = url_converter.Decode_str(path); err_line = "22";
				if(path.contains("?")){
					path = path.substring(0, path.indexOf("?")); err_line = "23";
				}
				
				FileInputStream fis = new FileInputStream(path); err_line = "24";
				
				dos.writeBytes("HTTP/1.1 200 OK: "); err_line = "25";
				dos.writeBytes("Content-Type: " + contentType(path) + CRLF); err_line = "26";
				dos.writeBytes(CRLF); err_line = "27";
				
				sendBytes(fis, dos); err_line = "28";
				fis.close(); err_line = "28";
				dos.close(); err_line = "29";
				br.close(); err_line = "30";
				socket.close(); err_line = "31";
			}else{
				String[] req_split = req.split("/"); err_line = "32";
				System.out.println("Request: " +request); err_line = "33";
				if(req_split.length >= 1){
					wiki_domain = req_split[1]; err_line = "34";
				}
				if(req_split.length >= 4){
					page_name = req_split[3]; err_line = "35";
					for(int i = 4; i <= req_split.length-1; i++){
						page_name += "/"+req_split[i]; err_line = "36";
					}
					Gfo_url_encoder url_converter = Gfo_url_encoder_.Http_url; err_line = "37";
					page_name = url_converter.Decode_str(page_name); err_line = "38";
					//page_name = app.Url_converter_url().Decode_str(page_name);
				}
				try{
//					String page_html = app.Http_server().Parse_page_to_html(app, wiki_domain, page_name); err_line = "39";
//					Write_page(dos, page_html, app_root_dir, wiki_domain); err_line = "40";
				}catch(Exception err) {
					dos.writeBytes("Site not found. Check address please, or see console log.\n"+err.getMessage()); err_line = "41";
					dos.close(); err_line = "42";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String err_msg = String_.Format("error retrieving page. Please make sure your url is of the form: http://localhost:8080/home/wiki/Main_Page; err_msg={0} err_line={1}", e.toString(), err_line);
			System.out.println(err_msg);
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
//				strm.writeBytes("Access-Control-Allow-Origin: *" + CRLF);	// No 'Access-Control-Allow-Origin' header is present on the requested resource.
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
