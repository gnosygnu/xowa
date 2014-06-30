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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.dbs.*;
public class Xob_term_sql extends Xob_term_base {
	public Xob_term_sql(Xob_bldr bldr, Xow_wiki wiki) {this.Ctor(bldr, wiki); this.wiki = wiki;} private Xow_wiki wiki;
	@Override public String Cmd_key() {return KEY;} public static final String KEY = "import.sql.term";
	@Override public void Cmd_end_hook() {
		Io_mgr._.DeleteDirDeep(wiki.Fsys_mgr().Tmp_dir());
		Xodb_mgr_sql db_mgr = wiki.Db_mgr_as_sql();
		db_mgr.Tbl_xowa_cfg().Insert_str_by_bry	(Xodb_mgr_sql.Grp_wiki_init, "props.bldr_version", wiki.Props().Bldr_version());
		db_mgr.Tbl_xowa_cfg().Insert_str_by_bry	(Xodb_mgr_sql.Grp_wiki_init, "props.main_page", wiki.Props().Main_page());
		db_mgr.Tbl_xowa_cfg().Insert_str_by_bry	(Xodb_mgr_sql.Grp_wiki_init, "props.siteinfo_misc", wiki.Props().Siteinfo_misc());
		db_mgr.Tbl_xowa_cfg().Insert_str_by_bry	(Xodb_mgr_sql.Grp_wiki_init, "props.siteinfo_mainpage", wiki.Props().Siteinfo_mainpage());
		wiki.Db_mgr_as_sql().Fsys_mgr().Rls();
	}
}
