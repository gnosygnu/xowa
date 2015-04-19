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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.dbs.cfgs.*; import gplx.xowa.dbs.*; import gplx.xowa.wikis.*;
public class Xob_term_cmd extends Xob_term_base {
	public Xob_term_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Ctor(bldr, wiki); this.wiki = wiki;} private Xowe_wiki wiki;
	@Override public String Cmd_key() {return KEY;} public static final String KEY = "text.term";
	@Override public void Cmd_end_hook() {
		Io_mgr._.DeleteDirDeep(wiki.Fsys_mgr().Tmp_dir());
		Db_cfg_tbl cfg_tbl = wiki.Data_mgr__core_mgr().Tbl__cfg();
		cfg_tbl.Insert_bry(Xow_cfg_consts.Grp_wiki_init, "props.bldr_version", wiki.Props().Bldr_version());
		cfg_tbl.Insert_bry(Xow_cfg_consts.Grp_wiki_init, "props.main_page", wiki.Props().Main_page());
		cfg_tbl.Insert_bry(Xow_cfg_consts.Grp_wiki_init, "props.siteinfo_misc", wiki.Props().Siteinfo_misc());
		cfg_tbl.Insert_bry(Xow_cfg_consts.Grp_wiki_init, "props.siteinfo_mainpage", wiki.Props().Siteinfo_mainpage());
//			gplx.fsdb.Fsdb_db_mgr__v2_bldr.I.Make(wiki);// bld wiki
		wiki.Data_mgr__core_mgr().Rls();
	}
}
