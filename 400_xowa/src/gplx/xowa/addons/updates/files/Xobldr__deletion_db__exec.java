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
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.addons.builds.files.cmds.*;
public class Xobldr__deletion_db__exec extends Xob_cmd__base {
	private Io_url deletion_db_url;
	public Xobldr__deletion_db__exec(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, null);}
	@Override public void Cmd_run() {
		if (deletion_db_url == null) throw Err_.new_("bldr", "no file specified");
		Db_conn conn = Db_conn_bldr.Instance.Get_or_noop(deletion_db_url);
		if (conn == Db_conn_.Noop) throw Err_.new_("bldr", "file does not exist", "file", deletion_db_url.Raw());

		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.Get_or_null(conn);
		if (cfg_tbl == null) throw Err_.new_("bldr", "xowa_cfg tbl does not exist", "file", deletion_db_url.Raw());
		byte[] domain_bry = cfg_tbl.Select_bry_or("", Xobldr__deletion_db__make.Cfg__deletion_db__domain, null);
		if (domain_bry == null) throw Err_.new_("bldr", "no domain found in deletion db", "file", deletion_db_url.Raw());
		this.wiki = bldr.App().Wiki_mgr().Get_by_or_make(domain_bry);
		wiki.Init_assert();
		Delete_by_url(wiki, deletion_db_url);
		conn.Rls_conn();
		Io_mgr.Instance.DeleteFil(deletion_db_url);			
	}
	private void Delete_by_url(Xowe_wiki wiki, Io_url delete_url) {
		Xof_fsdb_mgr fsdb_mgr = wiki.File_mgr().Fsdb_mgr();
		Fsm_mnt_itm mnt_itm = fsdb_mgr.Mnt_mgr().Mnts__get_main();
		Db_conn core_conn = mnt_itm.Atr_mgr().Db__core().Conn();
		Db_conn delete_conn = Db_conn_bldr.Instance.Get_or_new(delete_url).Conn();
		core_conn.Env_db_attach("delete_db", delete_conn);
		int dbs_len = mnt_itm.Bin_mgr().Dbs__len();
		String dbs_len_str = Int_.To_str(dbs_len - Int_.Base1);
		for (int i = 0; i < dbs_len; ++i) {
			Fsm_bin_fil bin_db = mnt_itm.Bin_mgr().Dbs__get_at(i);
			Delete_by_db(core_conn, bin_db, dbs_len_str);
		}
		core_conn.Env_db_detach("delete_db");
	}
	private void Delete_by_db(Db_conn conn, Fsm_bin_fil bin_db, String dbs_len_str) {
		Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
		// get rows to delete in db
		List_adp list = List_adp_.new_();
		int bin_db_id = bin_db.Id();
		String bin_db_id_str = Int_.To_str(bin_db_id);
		usr_dlg.Prog_many("", "", "processing files for deletion in database " + bin_db_id_str + " of " + dbs_len_str);
		Db_rdr rdr = conn.Exec_rdr(String_.Concat_lines_nl
		( "SELECT  ff.fil_id    AS item_id"
		, ",       1            AS item_is_orig"
		, "FROM    fsdb_fil ff"
		, "JOIN    delete_db.delete_regy dr ON ff.fil_id = dr.fil_id AND dr.thm_id = -1"
		, "WHERE   ff.fil_bin_db_id = " + bin_db_id_str
		, "UNION"
		, "SELECT  ft.thm_id    AS item_id"
		, ",       0            AS item_is_orig"
		, "FROM    fsdb_thm ft"
		, "        JOIN delete_db.delete_regy dr ON ft.thm_owner_id = dr.fil_id AND ft.thm_id = dr.thm_id"
		, "WHERE   ft.thm_bin_db_id = " + bin_db_id_str
		));
		try {
			while (rdr.Move_next()) {
				int item_id = rdr.Read_int("item_id");
				int item_is_orig = rdr.Read_int("item_is_orig");
				list.Add(new Xob_img_prune_itm(item_id, item_is_orig == 1));
			}
		}	finally {rdr.Rls();}

		int len = list.Count();
		if (len == 0) return;	// no files; exit, else will vacuum below

		conn.Env_db_attach("bin_db", bin_db.Conn());
		conn.Txn_bgn("img_prune__" + bin_db_id_str);
		// delete bin
		Db_stmt delete_bin_stmt = conn.Stmt_sql("DELETE FROM bin_db.fsdb_bin WHERE bin_owner_id = ?");
		for (int i = 0; i < len; ++i) {
			Xob_img_prune_itm itm = (Xob_img_prune_itm)list.Get_at(i);
			delete_bin_stmt.Clear().Crt_int("bin_owner_id", itm.Item_id);
			delete_bin_stmt.Exec_delete();

			if (i % 10000 == 0) usr_dlg.Prog_many("", "", "deleting data in database " + bin_db_id_str + " of " + dbs_len_str);
		}
		delete_bin_stmt.Rls();

		// delete meta
		Db_stmt delete_fil_stmt = conn.Stmt_sql("DELETE FROM fsdb_fil WHERE fil_id = ?");
		Db_stmt delete_thm_stmt = conn.Stmt_sql("DELETE FROM fsdb_thm WHERE thm_id = ?");
		for (int i = 0; i < len; ++i) {
			Xob_img_prune_itm itm = (Xob_img_prune_itm)list.Get_at(i);
			if (itm.Item_is_orig) {
				delete_fil_stmt.Clear().Crt_int("fil_id", itm.Item_id);
				delete_fil_stmt.Exec_delete();
			}
			else {
				delete_thm_stmt.Clear().Crt_int("thm_id", itm.Item_id);
				delete_thm_stmt.Exec_delete();
			}
			if (i % 10000 == 0) usr_dlg.Prog_many("", "", "deleting meta in database " + bin_db_id_str + " of " + dbs_len_str);
		}
		delete_fil_stmt.Rls();
		delete_thm_stmt.Rls();

		conn.Txn_end();
		conn.Env_db_detach("bin_db");
		bin_db.Conn().Env_vacuum();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if	(ctx.Match(k, Invk__file_))			this.deletion_db_url = Io_url_.new_any_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk__file_ = "file_";

	public static final String BLDR_CMD_KEY = "fsdb.deletion_db.exec";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__deletion_db__exec(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__deletion_db__exec(bldr, wiki);}
}
class Xob_img_prune_itm {
	public Xob_img_prune_itm(int item_id, boolean item_is_orig) {
		this.Item_id = item_id;
		this.Item_is_orig = item_is_orig;
	}
	public final    int Item_id;
	public final    boolean Item_is_orig;
}
