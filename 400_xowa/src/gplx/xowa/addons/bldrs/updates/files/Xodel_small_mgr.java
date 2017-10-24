/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.bldrs.updates.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.updates.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*; import gplx.xowa.files.*;
import gplx.xowa.bldrs.wkrs.*;	
class Xodel_small_mgr {
	public void Exec(Xowe_wiki wiki, int[] ext_max_ary) {
		wiki.Init_assert();
		// get atr_conn
		Fsdb_db_mgr db_core_mgr = Fsdb_db_mgr_.new_detect(wiki, wiki.Fsys_mgr().Root_dir(), wiki.Fsys_mgr().File_dir());
		Fsdb_db_file atr_db = db_core_mgr.File__atr_file__at(Fsm_mnt_mgr.Mnt_idx_main);
		Db_conn atr_conn = atr_db.Conn();

		// get deletion_db
		Xob_db_file deletion_db = Xob_db_file.New__deletion_db(wiki);
		atr_conn.Env_db_attach("deletion_db", deletion_db.Conn());

		// insert into deletion_db if too small
		int len = ext_max_ary.length;
		for (int i = 0; i < len; ++i) {
			Find_small_files(atr_conn, i, ext_max_ary[i]);
		}

		atr_conn.Env_db_detach("deletion_db");
	}
	private static void Find_small_files(Db_conn conn, int ext_id, int max) {
		String ext_name = String_.new_u8(Xof_ext_.Get_ext_by_id_(ext_id));
		String reason = "small:" + ext_name;
		conn.Exec_sql_concat_w_msg
		( String_.Format("finding small files; ext={0} max={1}", ext_name, max)
		, "INSERT  INTO deletion_db.delete_regy (fil_id, thm_id, reason)"
		, "SELECT  t.thm_owner_id, t.thm_id, '" + reason + "'"
		, "FROM    fsdb_thm t"
		, "        JOIN fsdb_fil f ON t.thm_owner_id = f.fil_id"
		, "WHERE   f.fil_ext_id = " + Int_.To_str(ext_id)
		, "AND     t.thm_size <= " + Int_.To_str(max)
		);
	}
} 
