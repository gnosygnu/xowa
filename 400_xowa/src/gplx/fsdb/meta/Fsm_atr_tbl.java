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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Fsm_atr_tbl {
	private String tbl_name = "file_meta_atr"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_uid, fld_url, fld_path_bgn;
	private Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		if (version_is_1) {
			tbl_name		= "fsdb_db_atr";
		}
		fld_uid				= flds.Add_int("uid");
		fld_url				= flds.Add_str("url", 255);
		fld_path_bgn		= flds.Add_str("path_bgn", 255);
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_uid)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_uid);
	}
	public Fsm_atr_fil[] Select_all(Fsm_abc_mgr abc_mgr, Io_url dir) {
		ListAdp rv = ListAdp_.new_();
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = conn.Stmt_select(tbl_name, flds, Db_meta_fld.Ary_empy).Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Io_url url = dir.GenSubFil(rdr.Read_str(fld_url));
				Fsm_atr_fil itm = new Fsm_atr_fil(abc_mgr, url);
				itm.Ctor_by_load
				( rdr.Read_int(fld_uid)
				, url
				, rdr.Read_str(fld_path_bgn)
				, Db_cmd_mode.Tid_ignore
				);
				rv.Add(itm);
			}
		}
		finally {rdr.Rls();}
		return (Fsm_atr_fil[])rv.Xto_ary(Fsm_atr_fil.class);
	}
	public void Commit_all(Fsm_atr_fil[] ary) {
		stmt_bldr.Batch_bgn();
		try {
			int len = ary.length;
			for (int i = 0; i < len; i++)
				Commit_itm(ary[i]);
		}	finally {stmt_bldr.Batch_end();}
	}
	private void Commit_itm(Fsm_atr_fil itm) {
		Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
		switch (itm.Cmd_mode()) {
			case Db_cmd_mode.Tid_create:	stmt.Clear().Val_int(fld_uid, itm.Id())	.Val_str(fld_url, itm.Url().NameAndExt()).Val_str(fld_path_bgn, itm.Path_bgn()).Exec_insert(); break;
			case Db_cmd_mode.Tid_update:	stmt.Clear()							.Val_str(fld_url, itm.Url().NameAndExt()).Val_str(fld_path_bgn, itm.Path_bgn()).Crt_int(fld_uid, itm.Id()).Exec_update(); break;
			case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_uid, itm.Id()).Exec_delete();	break;
			case Db_cmd_mode.Tid_ignore:	break;
			default:						throw Err_.unhandled(itm.Cmd_mode());
		}
		itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
	}
}
