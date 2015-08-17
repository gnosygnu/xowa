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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.fsdb.meta.*;
public class Xob_xfer_regy_update_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_xfer_regy_update_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_file_xfer_regy_update;} 
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec_main();}
	public void Cmd_end() {}
	public void Cmd_term() {}
	private void Exec_main() {
		Db_conn make_db_provider = Xob_db_file.new__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		this.Copy_atrs_to_make_db(make_db_provider);
		this.Update_status(make_db_provider);
	}
	private void Copy_atrs_to_make_db(Db_conn make_db_provider) {
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
		Fsm_mnt_itm fsdb_abc_mgr = wiki.File_mgr().Fsdb_mgr().Mnt_mgr().Mnts__get_main();		// 0 = fsdb.main
		Db_conn conn = fsdb_abc_mgr.Atr_mgr().Db__core().Conn();								// 0 = fsdb.atr.00
		Io_url fsdb_atr_url = ((gplx.dbs.engines.sqlite.Sqlite_conn_info)conn.Conn_info()).Url();
		Sqlite_engine_.Tbl_create_and_delete(make_db_provider, Xob_fsdb_regy_tbl.Tbl_name, Xob_fsdb_regy_tbl.Tbl_sql);
		Sqlite_engine_.Db_attach(make_db_provider, "fsdb_db", fsdb_atr_url.Raw());
		make_db_provider.Txn_bgn("bldr__xfer_regy_update");
		make_db_provider.Exec_sql(Xob_fsdb_regy_tbl.Update_regy_nil);
		make_db_provider.Exec_sql(Xob_fsdb_regy_tbl.Insert_fsdb_fil);
		String fsdb_thm_tbl = fsdb_abc_mgr.Db_mgr().File__schema_is_1() ? "fsdb_xtn_thm" : "fsdb_thm";
		String insert_sql_fsdb_thm = wiki.File_mgr().Fsdb_mgr().Mnt_mgr().Mnts__get_main().Cfg_mgr().Schema_thm_page()	// Cfg_get(Fsm_cfg_mgr.Grp_core).Get_yn_or_n(Fsm_cfg_mgr.Key_schema_thm_page)
			? String_.Format(Xob_fsdb_regy_tbl.Insert_fsdb_thm, fsdb_thm_tbl)
			: Xob_fsdb_regy_tbl.Insert_fsdb_thm_v0
			;
		make_db_provider.Exec_sql(insert_sql_fsdb_thm);
		make_db_provider.Txn_end();
		Sqlite_engine_.Idx_create(make_db_provider, Xob_fsdb_regy_tbl.Idx_main);
		Sqlite_engine_.Db_detach(make_db_provider, "fsdb_db");
	}
	private void Update_status(Db_conn make_db_provider) {
		make_db_provider.Txn_bgn("bldr__xfer_regy_update_status");
		make_db_provider.Exec_sql(Xob_fsdb_regy_tbl.Update_regy_fil);
		make_db_provider.Exec_sql(Xob_fsdb_regy_tbl.Update_regy_thm);
		make_db_provider.Txn_end();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
}
class Xob_fsdb_regy_tbl {
	public static final String Tbl_name = "fsdb_regy";
	public static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE fsdb_regy "
	,	"( fsdb_id               integer             NOT NULL       PRIMARY KEY        AUTOINCREMENT"
	,	", fsdb_name             varchar(255)        NOT NULL"
	,	", fsdb_is_orig          tinyint             NOT NULL"
	,	", fsdb_repo             tinyint             NOT NULL"
	,	", fsdb_w                integer             NOT NULL"
	,	", fsdb_time		     double              NOT NULL"
	,	", fsdb_page		     integer             NOT NULL"
	,	", fsdb_db_id		     integer             NOT NULL"
	,	", fsdb_size		     bigint              NOT NULL"
	,	", fsdb_status		     tinyint             NOT NULL"
	,	", fsdb_fil_id           integer             NOT NULL"
	,	", fsdb_thm_id           integer             NOT NULL"
	,	");"
	);
	public static final Db_idx_itm Idx_main = Db_idx_itm.sql_("CREATE INDEX fsdb_regy__main ON fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page);");
	public static final String 
		Insert_fsdb_fil = String_.Concat_lines_nl
	(	"INSERT INTO fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page, fsdb_db_id, fsdb_size, fsdb_status, fsdb_fil_id, fsdb_thm_id)"
	,	"SELECT  f.fil_name"
	,	",       1"
	,	",       CASE WHEN d.dir_name = 'commons.wikimedia.org' THEN 0 ELSE 1 END"
	,	",       -1"
	,	",       -1"
	,	",       -1"
	,	",       f.fil_bin_db_id"
	,	",       f.fil_size"
	,	",       0"
	,	",       f.fil_id"
	,	",       -1"
	,	"FROM    fsdb_db.fsdb_fil f"
	,	"        JOIN fsdb_db.fsdb_dir d ON f.fil_owner_id = d.dir_id"
	,	"WHERE   f.fil_bin_db_id != -1"
	,	";"
	)
	,	Insert_fsdb_thm = String_.Concat_lines_nl
	(	"INSERT INTO fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page, fsdb_db_id, fsdb_size, fsdb_status, fsdb_fil_id, fsdb_thm_id)"
	,	"SELECT  f.fil_name"
	,	",       0"
	,	",       CASE WHEN d.dir_name = 'commons.wikimedia.org' THEN 0 ELSE 1 END"
	,	",       t.thm_w"
	,	",       t.thm_time"
	,	",       t.thm_page"
	,	",       t.thm_bin_db_id"
	,	",       t.thm_size"
	,	",       0"
	,	",       f.fil_id"
	,	",       t.thm_id"
	,	"FROM    fsdb_db.fsdb_fil f"
	,	"        JOIN fsdb_db.{0} t ON f.fil_id = t.thm_owner_id"
	,	"        JOIN fsdb_db.fsdb_dir d ON f.fil_owner_id = d.dir_id"
	,	";"
	)
	,	Insert_fsdb_thm_v0 = String_.Concat_lines_nl
	(	"INSERT INTO fsdb_regy (fsdb_name, fsdb_is_orig, fsdb_repo, fsdb_w, fsdb_time, fsdb_page, fsdb_db_id, fsdb_size, fsdb_status, fsdb_fil_id, fsdb_thm_id)"
	,	"SELECT  f.fil_name"
	,	",       0"
	,	",       CASE WHEN d.dir_name = 'commons.wikimedia.org' THEN 0 ELSE 1 END"
	,	",       t.thm_w"
	,	",       t.thm_thumbtime"
	,	",       -1"
	,	",       t.thm_bin_db_id"
	,	",       t.thm_size"
	,	",       0"
	,	",       f.fil_id"
	,	",       t.thm_id"
	,	"FROM    fsdb_db.fsdb_fil f"
	,	"        JOIN fsdb_db.{0} t ON f.fil_id = t.thm_owner_id"
	,	"        JOIN fsdb_db.fsdb_dir d ON f.fil_owner_id = d.dir_id"
	,	";"
	)
	,	Update_regy_nil = "UPDATE xfer_regy SET xfer_status = 0;"
	,	Update_regy_fil = String_.Concat_lines_nl
	(	"REPLACE INTO xfer_regy "
	,	"( lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	,	", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	,	", xfer_status"
	,	")"
	,	"SELECT "
	,	"  lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	,	", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	,	", CASE WHEN f.fsdb_name IS NOT NULL THEN 1 ELSE 0 END"
	,	"FROM    xfer_regy x"
	,	"        LEFT JOIN fsdb_regy f ON x.lnki_ttl = f.fsdb_name"
	,	"WHERE   x.file_is_orig = 1 AND f.fsdb_is_orig  = 1"
	,	"AND     x.orig_repo = f.fsdb_repo"
	,	";"
	)
	,	Update_regy_thm = String_.Concat_lines_nl
	(	"REPLACE INTO xfer_regy "
	,	"( lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	,	", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	,	", xfer_status"
	,	")"
	,	"SELECT "
	,	"  lnki_id, lnki_tier_id, lnki_page_id, orig_page_id, orig_repo, lnki_ttl, orig_redirect_src, lnki_ext, orig_media_type"
	,	", file_is_orig, orig_w, orig_h, file_w, file_h, lnki_time, lnki_page, lnki_count"
	,	", CASE WHEN f.fsdb_name IS NOT NULL THEN 1 ELSE 0 END"
	,	"FROM    xfer_regy x"
	,	"        LEFT JOIN fsdb_regy f ON x.lnki_ttl = f.fsdb_name AND x.file_w = f.fsdb_w"
	,   "          AND x.lnki_time = f.fsdb_time AND x.lnki_page = f.fsdb_page"
	,	"WHERE   x.file_is_orig = 0 AND f.fsdb_is_orig  = 0"
	,	"AND     x.orig_repo = f.fsdb_repo"
	,	";"
	)
	;
}
