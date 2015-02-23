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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
import gplx.dbs.*;
public class Orig_fil_tbl {
	private String tbl_name = "orig_fil"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_uid, fld_name, fld_ext_id, fld_w, fld_h, fld_dir_url;		
	private Db_conn conn; private Db_stmt stmt_insert, stmt_select;
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (version_is_1) {
			fld_prefix		= "fil_";
		}
		fld_uid				= flds.Add_int(fld_prefix + "uid");
		fld_name			= flds.Add_str(fld_prefix + "name", 1024);
		fld_ext_id			= flds.Add_int(fld_prefix + "ext_id");
		fld_w				= flds.Add_int(fld_prefix + "w");
		fld_h				= flds.Add_int(fld_prefix + "h");
		fld_dir_url			= flds.Add_str(fld_prefix + "dir_url", 1024);	// NOTE: don't put dir in separate table; note that entire root_dir_wkr is not built to scale due to need for recursively loading all files
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_name)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
		stmt_insert = stmt_select = null;
	}
	public Orig_fil_itm Select_itm(byte[] ttl) {
		if (stmt_select == null) stmt_select = conn.Rls_reg(conn.Stmt_select(tbl_name, flds, fld_name));
		Orig_fil_itm rv = Orig_fil_itm.Null;
		Db_rdr rdr = Db_rdr_.Null;
		try {
			rdr = stmt_select.Clear().Crt_bry_as_str(fld_name, ttl).Exec_select_as_rdr();
			if (rdr.Move_next())
				rv = Load_itm(rdr);
			return rv;
		}
		finally {rdr.Rls();}
	}
	private Orig_fil_itm Load_itm(Db_rdr rdr) {
		return new Orig_fil_itm
		( rdr.Read_int(fld_uid)
		, rdr.Read_bry_by_str(fld_name)
		, rdr.Read_int(fld_ext_id)
		, rdr.Read_int(fld_w)
		, rdr.Read_int(fld_h)
		, rdr.Read_bry_by_str(fld_dir_url)
		);
	}
	public void Insert(Orig_fil_itm fil_itm) {
		if (stmt_insert == null) stmt_insert = conn.Rls_reg(conn.Stmt_insert(tbl_name, flds));
		stmt_insert.Clear()
		.Val_int(fld_uid, fil_itm.Fil_uid())
		.Val_bry_as_str(fld_name, fil_itm.Fil_name())
		.Val_int(fld_ext_id, fil_itm.Fil_ext_id())
		.Val_int(fld_w, fil_itm.Fil_w())
		.Val_int(fld_h, fil_itm.Fil_h())
		.Val_bry_as_str(fld_dir_url, fil_itm.Fil_dir_url())
		.Exec_insert();
	}	
	public void Rls() {}
}
