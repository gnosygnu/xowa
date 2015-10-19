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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.btries.*;
public class Xof_repo_itm_ {
	public static final int Dir_depth_null = -1, Dir_depth_wmf = 2, Dir_depth_xowa = 4;
	public static final byte Mode_orig = 0, Mode_thumb = 1, Mode_nil = Byte_.Max_value_127;
	public static byte Mode_by_bool(boolean is_thumb) {return is_thumb ? Mode_thumb : Mode_orig;}
	public static final byte[][] Mode_names_key = new byte[][] {Bry_.new_a7("orig"), Bry_.new_a7("thumb")};
	public static final byte Repo_remote = 0, Repo_local = 1, Repo_unknown = 126, Repo_null = Byte_.Max_value_127;
	public static byte Repo_by_bool(boolean is_commons) {return is_commons ? Repo_remote : Repo_local;}
	public static boolean Repo_is_known(byte repo) {
		switch (repo) {
			case Repo_remote:
			case Repo_local:	return true;
			default:			return false;
		}
	}
	public static byte[] Ttl_invalid_fsys_chars(Bry_bfr bfr, byte[] ttl) {
		int ttl_len = ttl.length;
		for (int i = 0; i < ttl_len; i++) {
			byte b = ttl[i];
			if (Op_sys_.Wnt_invalid_char(b)) b = Byte_ascii.Underline;
			bfr.Add_byte(b);
		}
		return bfr.To_bry_and_clear();
	}
	public static byte[] Ttl_shorten_ttl(Bry_bfr bfr, byte[] ttl, int ttl_max, byte[] md5, byte[] ext_bry) {
		byte[] rv = ttl;
		int exceed_len = rv.length - ttl_max;
		if (exceed_len > 0) {
			bfr.Add_mid(rv, 0, ttl_max - 33);							// add truncated title;		33=_.length + md5.length
			bfr.Add_byte(Byte_ascii.Underline);							// add underline;			EX: "_"
			bfr.Add(md5);												// add md5;					EX: "abcdefghijklmnopqrstuvwxyz0123456"
			bfr.Add_byte(Byte_ascii.Dot);								// add dot;					EX: "."
			bfr.Add(ext_bry);											// add ext;					EX: ".png"
			rv = bfr.To_bry_and_clear();
		}
		return rv;
	}
}
