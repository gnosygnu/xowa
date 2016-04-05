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
package gplx.xowa.addons.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.updates.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.addons.builds.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.fsdb.meta.*;
public class Xobldr__deletion_db__make extends Xob_cmd__base implements Xob_cmd {
	public Xobldr__deletion_db__make(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		wiki.Init_assert();
		// mark the records deleted
		Db_conn make_conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		make_conn.Exec_sql_concat_w_msg
		( "marking items deleted"
		, "UPDATE  fsdb_regy"
		, "SET     fsdb_deleted = 1"
		, "WHERE   NOT EXISTS "
		, "("
		, "SELECT  1"
		, "FROM    xfer_regy xr"
		, "WHERE   xr.lnki_ttl     = fsdb_regy.fsdb_name"
		, "AND     xr.file_is_orig = fsdb_regy.fsdb_is_orig"
		, "AND     xr.orig_repo    = fsdb_regy.fsdb_repo"
		, "AND     xr.file_w       = fsdb_regy.fsdb_w"
		, "AND     xr.lnki_time    = fsdb_regy.fsdb_time"
		, "AND     xr.lnki_page    = fsdb_regy.fsdb_page"
		, ")"
		 );

		// create deletion db
		Xob_db_file deletion_db = Xob_db_file.New__deletion_db(wiki);
		Db_conn deletion_conn = deletion_db.Conn();
		deletion_db.Tbl__cfg().Upsert_str("", Xobldr__deletion_db__make.Cfg__deletion_db__domain, wiki.Domain_str());

		// copy records over to it
		Xob_delete_regy delete_regy_tbl = new Xob_delete_regy(deletion_conn);
		deletion_conn.Meta_tbl_remake(delete_regy_tbl.Meta());
		deletion_conn.Env_db_attach("make_db", make_conn);
		deletion_conn.Exec_sql_concat_w_msg
		( "inserting into delete_regy"
		, "INSERT INTO delete_regy (fil_id, thm_id, reason)"
		, "SELECT  fsdb_fil_id"
		, ",       fsdb_thm_id"
		, ",       ''"
		, "FROM    make_db.fsdb_regy"
		, "WHERE   fsdb_deleted = 1"
		, ";"
		);
		deletion_conn.Env_db_detach("make_db");
		deletion_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(delete_regy_tbl.tbl_name, "main", delete_regy_tbl.fld_fil_id, delete_regy_tbl.fld_thm_id));
	}
	public static final String Cfg__deletion_db__domain = "file.deletion_db.domain";

	public static final String BLDR_CMD_KEY = "file.deletion_db.make";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__deletion_db__make(null, null);
	@Override public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__deletion_db__make(bldr, wiki);}
}
class Xob_delete_regy {
	public final    String tbl_name = "delete_regy";
	public final    Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	public final    String fld_fil_id, fld_thm_id, fld_reason;
	public final    Db_conn conn;
	public Xob_delete_regy(Db_conn conn) {
		this.conn = conn;
		this.fld_fil_id			= flds.Add_int("fil_id");
		this.fld_thm_id			= flds.Add_int("thm_id");
		this.fld_reason			= flds.Add_str("reason", 255);
		this.meta				= Dbmeta_tbl_itm.New(tbl_name, flds);
	}
	public Dbmeta_tbl_itm Meta() {return meta;} private final    Dbmeta_tbl_itm meta;
}
