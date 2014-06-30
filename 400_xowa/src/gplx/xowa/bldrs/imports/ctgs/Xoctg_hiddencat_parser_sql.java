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
package gplx.xowa.bldrs.imports.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.imports.*;
import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.dbs.tbls.*;
public class Xoctg_hiddencat_parser_sql extends Xoctg_hiddencat_parser_base {
	public Xoctg_hiddencat_parser_sql(Xob_bldr bldr, Xow_wiki wiki) {this.Ctor(bldr, wiki);}
	@Override public String Cmd_key() {return KEY;} public static final String KEY = "import.sql.hiddencat";
	@Override public void Cmd_bgn_hook(Xob_bldr bldr, Sql_file_parser parser) {
		super.Cmd_bgn_hook(bldr, parser);
		Xodb_mgr_sql db_mgr = wiki.Db_mgr_as_sql();
		Xodb_fsys_mgr fsys_mgr = db_mgr.Fsys_mgr();
		Db_provider provider = fsys_mgr.Category_provider();
		provider.Txn_mgr().Txn_bgn_if_none();
		tbl = db_mgr.Tbl_category();
		stmt = tbl.Update_stmt(provider);
	}
	private Xodb_category_tbl tbl; private Db_stmt stmt;
	@Override public void Exec_hook(Bry_bfr file_bfr, int cur_id, boolean cur_is_hiddencat) {
		if (cur_is_hiddencat)
			tbl.Update(stmt, cur_id, cur_is_hiddencat ? Bool_.Y_byte : Bool_.N_byte);
	}
	@Override public void Cmd_end() {
		if (stmt == null) return;	// stmt is null when ctg fails (for example, category files not downloaded); DATE:2013-12-20
		stmt.Provider().Txn_mgr().Txn_end_all();
		if (!Env_.Mode_testing())	// NOTE: do not delete when testing
			Io_mgr._.DeleteDirDeep(wiki.Fsys_mgr().Tmp_dir());	// delete /wiki/wiki_name/tmp
		Io_url[] sql_files = Io_mgr._.QueryDir_args(wiki.Fsys_mgr().Root_dir()).FilPath_("*.sql.gz").ExecAsUrlAry();
		int len = sql_files.length;
		for (int i = 0; i < len; i++) {
			Io_url sql_file = sql_files[i];
			Io_mgr._.DeleteFil(sql_file);
		}
		Io_mgr._.DeleteFil_args(wiki.Fsys_mgr().Root_dir().GenSubFil("xowa_categorylinks.sql")).MissingFails_off().Exec();
	}
}
