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
package gplx.core.net; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*;
public class Http_request_parser_tst {
	@Before public void init() {fxt.Clear();} private final    Http_request_parser_fxt fxt = new Http_request_parser_fxt();
	@Test   public void Type_post()	{
		fxt.Test_type_post("POST /url HTTP/1.1", Http_request_itm.Type_post, "/url", "HTTP/1.1");
	}
	@Test   public void Type_content_type()	{
		fxt.Test_content_type("Content-Type: multipart/form-data; boundary=---------------------------72432484930026", "multipart/form-data", "-----------------------------72432484930026");
	}
	@Test   public void Type_content_type__x_www_form_url_encoded()	{	// PURPOSE: ignore content-type for GET calls like by Mathematica server; DATE:2015-08-04
		fxt.Test_content_type("Content-Type: application/x-www-form-urlencoded", null, null);
	}
	@Test   public void Type_form_data() {
		fxt.Test_form_data(String_.Ary
		( "POST /url HTTP/1.1"
		, "Content-Type: multipart/form-data; boundary=---------------------------12345678901234"
		, ""
		, "-----------------------------12345678901234"
		, "Content-Disposition: form-data; name=\"key0\""
		, ""
		, "val0"
		, "-----------------------------12345678901234"
		, "Content-Disposition: form-data; name=\"key1\""
		, ""
		, "val1"
		, "-----------------------------12345678901234--"
		)
		, fxt.Make_post_data_itm("key0", "val0")
		, fxt.Make_post_data_itm("key1", "val1")
		);
	}
	@Test   public void Type_accept_charset()	{
		fxt.Test_ignore("Accept-Charset: ISO-8859-1,utf-8;q=0.7");
	}
}
class Http_request_parser_fxt {
	private final    Http_request_parser parser;
	private final    Http_client_rdr client_rdr = Http_client_rdr_.new_mem();
	private final    Http_server_wtr__mock server_wtr = new Http_server_wtr__mock();
	public Http_request_parser_fxt() {
		this.parser = new Http_request_parser(server_wtr, false);
	}
	public void Clear() {
		parser.Clear();
		server_wtr.Clear();
	}
	public Http_post_data_itm Make_post_data_itm(String key, String val) {return new Http_post_data_itm(Bry_.new_u8(key), Bry_.new_u8(val));}
	public void Test_type_post(String line, int expd_type, String expd_url, String expd_protocol) {
		client_rdr.Stream_(String_.Ary(line));
		Http_request_itm req = parser.Parse(client_rdr);
		Tfds.Eq(expd_type		, req.Type());
		Tfds.Eq(expd_url		, String_.new_u8(req.Url()));
		Tfds.Eq(expd_protocol	, String_.new_u8(req.Protocol()));
	}
	public void Test_content_type(String line, String expd_content_type, String expd_content_boundary) {
		client_rdr.Stream_(String_.Ary(line));
		Http_request_itm req = parser.Parse(client_rdr);
		Tfds.Eq(expd_content_type		, String_.new_u8(req.Content_type()));
		Tfds.Eq(expd_content_boundary	, String_.new_u8(req.Content_type_boundary()));
	}
	public void Test_form_data(String[] ary, Http_post_data_itm... expd) {
		client_rdr.Stream_(ary);
		Http_request_itm req = parser.Parse(client_rdr);
		Http_post_data_hash hash = req.Post_data_hash();
		int len = hash.Len();
		for (int i = 0; i < len; ++i) {
			Http_post_data_itm itm = hash.Get_at(i);
			Tfds.Eq_bry(itm.Key(), expd[i].Key());
			Tfds.Eq_bry(itm.Val(), expd[i].Val());
		}
	}
	public void Test_ignore(String line) {
		client_rdr.Stream_(String_.Ary(line));
		parser.Parse(client_rdr);
		Gftest.Eq__str(null, server_wtr.Data());
	}
}	
