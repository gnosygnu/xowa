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
public class Http_request_itm {
	public Http_request_itm(int type, byte[] url, byte[] protocol, byte[] host, byte[] user_agent
		, byte[] accept, byte[] accept_language, byte[] accept_encoding, boolean dnt, byte[] x_requested_with, byte[] cookie, byte[] referer
		, int content_length, byte[] content_type, byte[] content_type_boundary
		, byte[] connection, byte[] pragma, byte[] cache_control, byte[] origin
		, Http_post_data_hash post_data_hash
		) {
		this.type = type; this.url = url; this.protocol = protocol; this.host = host; this.user_agent = user_agent;
		this.accept = accept; this.accept_language = accept_language; this.accept_encoding = accept_encoding; this.dnt = dnt; this.x_requested_with = x_requested_with; this.cookie = cookie; this.referer = referer;
		this.content_length = content_length; this.content_type = content_type; this.content_type_boundary = content_type_boundary;
		this.connection = connection; this.pragma = pragma; this.cache_control = cache_control; this.origin = origin;
		this.post_data_hash = post_data_hash;
	}
	public int Type() {return type;} private final int type;
	public byte[] Url() {return url;} private final byte[] url;
	public byte[] Protocol() {return protocol;} private final byte[] protocol;
	public byte[] Host() {return host;} private final byte[] host;
	public byte[] User_agent() {return user_agent;} private final byte[] user_agent;
	public byte[] Accept() {return accept;} private final byte[] accept;
	public byte[] Accept_language() {return accept_language;} private final byte[] accept_language;
	public byte[] Accept_encoding() {return accept_encoding;} private final byte[] accept_encoding;
	public boolean Dnt() {return dnt;} private final boolean dnt;
	public byte[] X_requested_with() {return x_requested_with;} private byte[] x_requested_with;
	public byte[] Cookie() {return cookie;} private final byte[] cookie;
	public byte[] Referer() {return referer;} private final byte[] referer;
	public int Content_length() {return content_length;} private final int content_length;
	public byte[] Content_type() {return content_type;} private final byte[] content_type;
	public byte[] Content_type_boundary() {return content_type_boundary;} private final byte[] content_type_boundary;
	public byte[] Connection() {return connection;} private final byte[] connection;
	public byte[] Pragma() {return pragma;} private final byte[] pragma;
	public byte[] Cache_control() {return cache_control;} private final byte[] cache_control;
	public byte[] Origin() {return origin;} private final byte[] origin;
	public Http_post_data_hash Post_data_hash() {return post_data_hash;} private final Http_post_data_hash post_data_hash;
	public String To_str(Bry_bfr bfr, boolean line) {
		bfr	.Add_kv_dlm(line, "type"					, type == Type_get ? "GET" : "POST")
			.Add_kv_dlm(line, "url"						, url)
			.Add_kv_dlm(line, "protocol"				, protocol)
			.Add_kv_dlm(line, "host"					, host)
			.Add_kv_dlm(line, "user_agent"				, user_agent)
			.Add_kv_dlm(line, "accept"					, accept)
			.Add_kv_dlm(line, "accept_encoding"			, accept_encoding)
			.Add_kv_dlm(line, "dnt"						, dnt)
			.Add_kv_dlm(line, "x_requested_with"		, x_requested_with)
			.Add_kv_dlm(line, "cookie"					, cookie)
			.Add_kv_dlm(line, "referer"					, referer)
			.Add_kv_dlm(line, "content_length"			, content_length)
			.Add_kv_dlm(line, "content_type"			, content_type)
			.Add_kv_dlm(line, "content_type_boundary"	, content_type_boundary)
			.Add_kv_dlm(line, "connection"				, connection)
			.Add_kv_dlm(line, "pragma"					, pragma)
			.Add_kv_dlm(line, "cache_control"			, cache_control)
			;
		if (post_data_hash != null) {
			int len = post_data_hash.Len();
			for (int i = 0; i < len; ++i) {
				Http_post_data_itm itm = post_data_hash.Get_at(i);
				bfr.Add_byte_repeat(Byte_ascii.Space, 2);
				bfr.Add_kv_dlm(line, String_.new_u8(itm.Key()), itm.Val());
			}
		}
		return bfr.To_str_and_clear();
	}
	public static final int Type_get = 1, Type_post = 2;
}
