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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_bin_wkr_ {
	public static final Xof_bin_wkr[] Ary_empty = new Xof_bin_wkr[0];
	public static final byte Tid_null = Byte_.MaxValue_127, Tid_noop = 1, Tid_not_found = 2
	, Tid_fsdb_wiki = 3, Tid_fsdb_regy = 4
	, Tid_http_wmf = 5
	, Tid_fsys_wmf = 6, Tid_fsys_xowa = 7
	, Tid_root_dir = 8;
	public static final String 
	  Key_fsdb_wiki = "xowa.fsdb.wiki", Key_fsdb_regy = "xowa.fsdb.regy"
	, Key_http_wmf = "xowa.http.wmf"
	, Key_fsys_wmf = "xowa.fsys.wmf", Key_fsys_xowa = "xowa.fsys.xowa"
	, Key_root_dir = "xowa.root_dir"
	;
	public static byte X_key_to_tid(String key) {
		if		(String_.Eq(key, Key_fsdb_wiki))	return Tid_fsdb_wiki;
		else if	(String_.Eq(key, Key_fsdb_regy))	return Tid_fsdb_regy;
		else if	(String_.Eq(key, Key_http_wmf))		return Tid_http_wmf;
		else if	(String_.Eq(key, Key_fsys_wmf))		return Tid_fsys_wmf;
		else if	(String_.Eq(key, Key_fsys_xowa))	return Tid_fsys_xowa;
		else if	(String_.Eq(key, Key_root_dir))		return Tid_root_dir;
		else										return Tid_null;
	}
	public static boolean Tid_is_fsdb(byte v) {
		switch (v) {
			case Tid_fsdb_wiki: case Tid_fsdb_regy:
				return true;
			default:
				return false;
		}
	}
}
