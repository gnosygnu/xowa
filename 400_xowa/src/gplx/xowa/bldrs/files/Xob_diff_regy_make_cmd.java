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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.dbs.*; import gplx.fsdb.*; import gplx.xowa.bldrs.oimgs.*;
public class Xob_diff_regy_make_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_diff_regy_make_cmd(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY_oimg;} public static final String KEY_oimg = "file.diff_regy.make";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec_main();}
	public void Cmd_end() {}
	public void Cmd_print() {}
	private void Exec_main() {
		Db_provider make_db_provider = Xodb_db_file.init__file_make(wiki.Fsys_mgr().Root_dir()).Provider();
		this.Make_join_indexes(make_db_provider);
		this.Make_diff_regy(make_db_provider);
		this.Make_delete_sql(make_db_provider);
	}
	private void Make_join_indexes(Db_provider make_db_provider) {
		Sqlite_engine_.Idx_create(make_db_provider, Xob_diff_regy_tbl.Idx_fsdb_regy__join);
		Sqlite_engine_.Idx_create(make_db_provider, Xob_diff_regy_tbl.Idx_xfer_regy__join);
	}
	private void Make_diff_regy(Db_provider make_db_provider) {
		Sqlite_engine_.Tbl_create_and_delete(make_db_provider, Xob_diff_regy_tbl.Tbl_name, Xob_diff_regy_tbl.Tbl_sql);
		make_db_provider.Exec_sql(Xob_diff_regy_tbl.Make_diff_regy);
		Sqlite_engine_.Idx_create(make_db_provider, Xob_diff_regy_tbl.Idx_xfer_regy__join);
	}
	private void Make_delete_sql(Db_provider make_db_provider) {
		DataRdr rdr = make_db_provider.Exec_sql_as_rdr(Xob_diff_regy_tbl.Make_deletes);
		int cur_db_id = -1, cur_count = 0;
		Bry_bfr atr_bfr = Bry_bfr.new_(), bin_bfr = Bry_bfr.new_();
		Io_url sql_tmp_dir = wiki.Ctx().App().Fsys_mgr().File_dir().GenSubDir_nest(wiki.Domain_str(), "tmp_sql");
		while (rdr.MoveNextPeer()) {
			byte	diff_is_orig	= rdr.ReadByte("diff_is_orig");
			int		diff_db_id		= rdr.ReadInt("diff_db_id");
			int		diff_fil_id		= rdr.ReadInt("diff_fil_id");
			int		diff_thm_id		= rdr.ReadInt("diff_thm_id");
			if (cur_db_id != diff_db_id) {
				Make_delete_sql_file(atr_bfr, bin_bfr, sql_tmp_dir, cur_db_id, cur_count);
				cur_db_id = diff_db_id;
				cur_count = 0;
			}
			Make_delete_sql_item(atr_bfr, bin_bfr, diff_is_orig, diff_db_id, diff_fil_id, diff_thm_id);
			++cur_count;
		}
	}
	private void Make_delete_sql_file(Bry_bfr atr_bfr, Bry_bfr bin_bfr, Io_url sql_tmp_dir, int cur_db_id, int cur_count) {
		Make_delete_sql_file(atr_bfr, sql_tmp_dir, cur_db_id, cur_count, Fsdb_db_tid_.Tid_atr);
		Make_delete_sql_file(bin_bfr, sql_tmp_dir, cur_db_id, cur_count, Fsdb_db_tid_.Tid_bin);
		app.Usr_dlg().Note_many("", "", "file.diff:sql generated: db_id=~{0} count=~{1}", Int_.XtoStr_PadBgn_space(cur_db_id, 3), Int_.XtoStr_PadBgn_space(cur_count, 7));
	}
	private void Make_delete_sql_file(Bry_bfr bfr, Io_url sql_dir, int db_id, int cur_count, byte db_tid) {
		if (db_id != -1 && cur_count > 0) { // do not write 1st bfr
			bfr.Add_str("COMMIT;\n");
			String sql_url_name = String_.Format("{0}-{1}-{2}.sql", wiki.Domain_str(), Int_.XtoStr_PadBgn(db_id, 3), Fsdb_db_tid_.Xto_key(db_tid));
			Io_url sql_url = sql_dir.GenSubFil(sql_url_name);
			Io_mgr._.SaveFilBfr(sql_url, bfr);
		}
		bfr.Clear();	// clear bfr if cur_count == 0
		bfr.Add_str("BEGIN TRANSACTION;\n");
	}
	private void Make_delete_sql_item(Bry_bfr atr_bfr, Bry_bfr bin_bfr, byte diff_is_orig, int diff_db_id, int diff_fil_id, int diff_thm_id) {
		if (diff_is_orig == Byte_.Zero) {
			atr_bfr.Add_str("DELETE FROM fsdb_xtn_thm WHERE thm_id = " + Int_.XtoStr(diff_thm_id) + ";\n");
			bin_bfr.Add_str("DELETE FROM fsdb_bin WHERE bin_owner_id = " + Int_.XtoStr(diff_thm_id) + ";\n");
		}
		else {
			atr_bfr.Add_str("UPDATE fsdb_fil SET fil_bin_db_id = -1 WHERE fil_id = " + Int_.XtoStr(diff_fil_id) + ";\n");
			bin_bfr.Add_str("DELETE FROM fsdb_bin WHERE bin_owner_id = " + Int_.XtoStr(diff_fil_id) + ";\n");
		}
	}
}
class Xob_diff_regy_tbl {
	public static final String Tbl_name = "diff_regy";
	public static final String Tbl_sql = String_.Concat_lines_nl
	(  "CREATE TABLE diff_regy"
	, "( diff_id             integer             NOT NULL PRIMARY KEY"
	, ", diff_is_orig        tinyint             NOT NULL "
	, ", diff_status         integer             NOT NULL"
	, ", diff_db_id          integer             NOT NULL"
	, ", diff_fil_id         integer             NOT NULL"
	, ", diff_thm_id         integer             NOT NULL"
	, ", diff_name           varchar(255)        NOT NULL"
	, ", diff_repo           tinyint             NOT NULL"
	, ", diff_w              integer             NOT NULL"
	, ", diff_time           double              NOT NULL"
	, ", diff_page           integer             NOT NULL"
	, ", diff_size           bigint              NOT NULL"
	, ");"
	);
	public static final Db_idx_itm Idx_fsdb_regy__join = Db_idx_itm.sql_("CREATE INDEX fsdb_regy__join ON fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page);");
	public static final Db_idx_itm Idx_xfer_regy__join = Db_idx_itm.sql_("CREATE INDEX xfer_regy__join ON xfer_regy (lnki_ttl , file_is_orig, orig_repo, file_w, lnki_time, lnki_page);");
	public static final Db_idx_itm Idx_diff_regy__load = Db_idx_itm.sql_("CREATE INDEX diff_regy__load ON diff_regy (diff_status, diff_db_id, diff_is_orig, diff_fil_id, diff_thm_id);");
	public static final String
	  Make_diff_regy = String_.Concat_lines_nl
	( "INSERT INTO diff_regy "
	, "        (diff_id, diff_is_orig, diff_db_id, diff_fil_id, diff_thm_id, diff_name, diff_repo, diff_w, diff_time, diff_page, diff_size, diff_status)"
	, "SELECT "
	, "        f.fsdb_id, f.fsdb_is_orig, f.fsdb_db_id, f.fsdb_fil_id, f.fsdb_thm_id, f.fsdb_name, f.fsdb_repo, f.fsdb_w, f.fsdb_time, f.fsdb_page, f.fsdb_size, CASE WHEN x.lnki_ttl IS NULL THEN 0 ELSE 1 END"
	, "FROM    fsdb_regy f"
	, "        LEFT JOIN xfer_regy x "
	, "            ON  f.fsdb_name     = x.lnki_ttl"
	, "            AND f.fsdb_is_orig  = x.file_is_orig"
	, "            AND f.fsdb_repo     = x.orig_repo"
	, "            AND f.fsdb_w        = x.file_w"
	, "            AND f.fsdb_time     = x.lnki_time"
	, "            AND f.fsdb_page     = x.lnki_page"
	, ";"
	)
	, Make_deletes = String_.Concat_lines_nl
	( "SELECT  diff_db_id, diff_is_orig, diff_fil_id, diff_thm_id"
	, "FROM    diff_regy"
	, "WHERE   diff_status = 0"
	, "ORDER BY diff_db_id, diff_is_orig, diff_fil_id, diff_thm_id"
	)
	;
}
