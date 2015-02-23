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
public class Fsm_bin_tbl {
	private String tbl_name = "file_meta_bin"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_uid, fld_url, fld_bin_len, fld_bin_max;		
	private Db_conn conn; private final Db_stmt_bldr stmt_bldr = new Db_stmt_bldr();
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		if (version_is_1) {
			tbl_name		= "fsdb_db_bin";
		}
		fld_uid				= flds.Add_int("uid");
		fld_url				= flds.Add_str("url", 255);
		fld_bin_len			= flds.Add_long("bin_len");
		fld_bin_max			= flds.Add_long("bin_max");
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_uid)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_bldr.Conn_(conn, tbl_name, flds, fld_uid);
	}
	public Fsm_bin_fil[] Select_all(Io_url dir) {
		ListAdp rv = ListAdp_.new_();
		Db_qry qry = Db_qry_select.new_().From_(tbl_name).Cols_all_().Where_(Db_crt_.eq_many_(Db_meta_fld.Ary_empy)).OrderBy_asc_(fld_uid);
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = conn.Stmt_new(qry).Clear().Exec_select_as_rdr();
			while (rdr.Move_next()) {
				Fsm_bin_fil itm = new Fsm_bin_fil
				( rdr.Read_int(fld_uid)
				, dir.GenSubFil(rdr.Read_str(fld_url))
				, rdr.Read_long(fld_bin_len)
				, rdr.Read_long(fld_bin_max)
				, Db_cmd_mode.Tid_ignore
				);
				rv.Add(itm);
			}
		} finally {rdr.Rls();}
		return (Fsm_bin_fil[])rv.Xto_ary(Fsm_bin_fil.class);
	}
	public void Commit_all(Fsm_bin_fil[] ary) {
		stmt_bldr.Batch_bgn();
		try {
			int len = ary.length;
			for (int i = 0; i < len; i++)
				Commit_itm(ary[i]);
		}	finally {stmt_bldr.Batch_end();}
	}
	private void Commit_itm(Fsm_bin_fil itm) {
		Db_stmt stmt = stmt_bldr.Get(itm.Cmd_mode());
		switch (itm.Cmd_mode()) {
			case Db_cmd_mode.Tid_create:	stmt.Clear().Crt_int(fld_uid, itm.Id())	.Val_str(fld_url, itm.Url().NameAndExt()).Val_long(fld_bin_len, itm.Bin_len()).Val_long(fld_bin_max, itm.Bin_max()).Exec_insert(); break;
			case Db_cmd_mode.Tid_update:	stmt.Clear()							.Val_str(fld_url, itm.Url().NameAndExt()).Val_long(fld_bin_len, itm.Bin_len()).Val_long(fld_bin_max, itm.Bin_max()).Crt_int(fld_uid, itm.Id()).Exec_update(); break;
			case Db_cmd_mode.Tid_delete:	stmt.Clear().Crt_int(fld_uid, itm.Id()).Exec_delete();	break;
			case Db_cmd_mode.Tid_ignore:	break;
			default:					throw Err_.unhandled(itm.Cmd_mode());
		}
		itm.Cmd_mode_(Db_cmd_mode.Tid_ignore);
	}
}
