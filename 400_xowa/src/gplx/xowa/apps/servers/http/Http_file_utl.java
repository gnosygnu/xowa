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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.xowa.files.*;
class Http_file_utl {
	public static byte[] To_mime_type_by_path_as_bry(byte[] path_bry) {
		int dot_pos = Bry_find_.Find_bwd(path_bry, Byte_ascii.Dot);
		return dot_pos == Bry_find_.Not_found ? Mime_octet_stream : To_mime_type_by_ext_as_bry(path_bry, dot_pos, path_bry.length);
	}
	public static byte[] To_mime_type_by_ext_as_bry(byte[] ext_bry, int bgn, int end) {
		Object o = mime_hash.Get_by_mid(ext_bry, bgn, end);
		return o == null ? Mime_octet_stream : (byte[])o;
	}
	private static final    byte[] 
	  Mime_octet_stream		= Xof_ext_.Mime_type__ary[Xof_ext_.Id_unknown]
	, Mime_html				= Bry_.new_a7("text/html")
	, Mime_css				= Bry_.new_a7("text/css")
	, Mime_js				= Bry_.new_a7("application/javascript")		
	;
	private static final    Hash_adp_bry mime_hash = Mime_hash__new();
	private static Hash_adp_bry Mime_hash__new() {
		Hash_adp_bry rv = Hash_adp_bry.ci_a7();
		int len = Xof_ext_.Id__max;
		for (int i = 0; i < len; ++i) {
			rv.Add_bry_obj
			( Bry_.Add(Byte_ascii.Dot, Xof_ext_.Bry__ary[i])
			, Xof_ext_.Mime_type__ary[i]);
		}
		rv.Add_str_obj(".htm"	, Mime_html);
		rv.Add_str_obj(".html"	, Mime_html);
		rv.Add_str_obj(".css"	, Mime_css);
		rv.Add_str_obj(".js"	, Mime_js);
		return rv;
	}
}
