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
