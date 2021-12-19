/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.net;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
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
	public String To_str(BryWtr bfr, boolean line) {
		bfr	.AddKvDlm(line, "type"					, type == Type_get ? "GET" : "POST")
			.AddKvDlm(line, "url"						, url)
			.AddKvDlm(line, "protocol"				, protocol)
			.AddKvDlm(line, "host"					, host)
			.AddKvDlm(line, "user_agent"				, user_agent)
			.AddKvDlm(line, "accept"					, accept)
			.AddKvDlm(line, "accept_encoding"			, accept_encoding)
			.AddKvDlm(line, "dnt"						, dnt)
			.AddKvDlm(line, "x_requested_with"		, x_requested_with)
			.AddKvDlm(line, "cookie"					, cookie)
			.AddKvDlm(line, "referer"					, referer)
			.AddKvDlm(line, "content_length"			, content_length)
			.AddKvDlm(line, "content_type"			, content_type)
			.AddKvDlm(line, "content_type_boundary"	, content_type_boundary)
			.AddKvDlm(line, "connection"				, connection)
			.AddKvDlm(line, "pragma"					, pragma)
			.AddKvDlm(line, "cache_control"			, cache_control)
			;
		if (post_data_hash != null) {
			int len = post_data_hash.Len();
			for (int i = 0; i < len; ++i) {
				Http_post_data_itm itm = post_data_hash.Get_at(i);
				bfr.AddByteRepeat(AsciiByte.Space, 2);
				bfr.AddKvDlm(line, StringUtl.NewU8(itm.Key()), itm.Val());
			}
		}
		return bfr.ToStrAndClear();
	}
	public static final int Type_get = 1, Type_post = 2;
}
