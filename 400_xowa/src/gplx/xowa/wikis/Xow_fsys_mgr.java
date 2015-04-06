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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
public class Xow_fsys_mgr {		
	public Xow_fsys_mgr(Io_url root_dir, Io_url file_dir) {
		this.root_dir = root_dir; this.file_dir = file_dir; this.tmp_dir = root_dir.GenSubDir("tmp");			
	}
	public Io_url Root_dir()				{return root_dir;}		private final Io_url root_dir;
	public Io_url File_dir()				{return file_dir;}		private final Io_url file_dir;
	public Io_url Tmp_dir()					{return tmp_dir;}		private final Io_url tmp_dir;

	public static Io_url Find_core_fil(Xow_wiki wiki) {return Find_core_fil(wiki.Fsys_mgr().Root_dir(), wiki.Domain_str());}
	public static Io_url Find_core_fil(Io_url wiki_root_dir, String domain_str) {
		Io_url[] ary = Io_mgr._.QueryDir_fils(wiki_root_dir);
		int ary_len = ary.length; if (ary.length == 0) return null;
		Io_url rv = Find_core_fil__xowa(ary, ary_len, domain_str);
		return rv == null ? Find_core_fil__sqlite3(wiki_root_dir, ary, ary_len, domain_str) : rv;
	}
	private static Io_url Find_core_fil__xowa(Io_url[] ary, int ary_len, String domain_str) {
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if (!String_.Eq(itm.Ext(), ".xowa")) continue;
			if	(	String_.Eq(itm.NameOnly(), domain_str)				// EX: "en.wikipedia.org"
				||	String_.Eq(itm.NameOnly(), domain_str + "-text")	// EX: "en.wikipedia.org-text"
				||	String_.Eq(itm.NameOnly(), domain_str + "-core")	// EX: "en.wikipedia.org-core"
				) {
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.v2: url=~{0}", itm.Raw());
				return itm;
			}
		}
		return null;
	}
	private static Io_url Find_core_fil__sqlite3(Io_url wiki_root_dir, Io_url[] ary, int ary_len, String domain_str) {
		Io_url rv = null;
		String v0_str = domain_str + ".000";
		for (int i = 0; i < ary_len; i++) {
			Io_url itm = ary[i];
			if (!String_.Eq(itm.Ext(), ".sqlite3")) continue;
			if	(String_.Eq(itm.NameOnly(), v0_str)) {					// EX: "en.wikipedia.org.000"
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.v1: url=~{0}", itm.Raw());
				return itm;
			}
			if (ary_len == 1) {
				Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.custom: url=~{0}", itm.Raw());
				return rv;												// 1 folder and 1 sqlite file; return it; custom wikis?
			}
		}
		Xoa_app_.Usr_dlg().Log_many("", "", "wiki.db_core.none: dir=~{0}", wiki_root_dir.Raw());
		return rv;
	}
}
