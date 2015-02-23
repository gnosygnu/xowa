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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.ios.*;
import gplx.dbs.engines.sqlite.*;
public class Fsd_bin_tbl {
	private String tbl_name = "file_data_bin"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private String fld_owner_id, fld_owner_tid, fld_part_id, fld_data_url, fld_data;		
	private Db_conn conn;
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
	public void Conn_(Db_conn new_conn, boolean created, boolean version_is_1) {
		this.conn = new_conn; flds.Clear();
		String fld_prefix = "";
		if (version_is_1) {
			tbl_name		= "fsdb_bin";
			fld_prefix		= "bin_";
		}
		fld_owner_id		= flds.Add_int(fld_prefix + "owner_id");
		fld_owner_tid		= flds.Add_byte(fld_prefix + "owner_tid");
		fld_part_id			= flds.Add_int(fld_prefix + "part_id");
		fld_data_url		= flds.Add_str(fld_prefix + "data_url", 255);
		fld_data			= flds.Add_bry(fld_prefix + "data");	// mediumblob
		if (created) {
			Db_meta_tbl meta = Db_meta_tbl.new_(tbl_name, flds
			, Db_meta_idx.new_unique_by_tbl(tbl_name, "pkey", fld_owner_id)
			);
			conn.Exec_create_tbl_and_idx(meta);
		}
	}
	public long Insert_rdr(int id, byte tid, long bin_len, Io_stream_rdr bin_rdr) {
		synchronized (tmp_bfr) {
			Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
			byte[] bin_ary = Io_stream_rdr_.Load_all_as_bry(tmp_bfr, bin_rdr);
			long rv = bin_ary.length;
			stmt.Clear()
			.Val_int(fld_owner_id, id)
			.Val_byte(fld_owner_tid, tid)
			.Val_int(fld_part_id, Null_part_id)
			.Val_str(fld_data_url, Null_data_url)
			.Val_bry(bin_ary)
			.Exec_insert();
			return rv;
		}
	}
	public Io_stream_rdr Select_as_rdr(Db_conn conn, int owner_id) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			Db_stmt stmt = conn.Stmt_select(tbl_name, String_.Ary(fld_data), fld_owner_id);
			rdr = stmt.Clear().Crt_int(fld_owner_id, owner_id).Exec_select_as_rdr();
			return rdr.Move_next()
				? Io_stream_rdr_.mem_(Read_bin_data(rdr))
				: Io_stream_rdr_.Null;
		}
		finally {rdr.Rls();}
	}
	public boolean Select_to_url(int owner_id, Io_url url) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			Db_stmt stmt = conn.Stmt_select(tbl_name, String_.Ary(fld_data), fld_owner_id);
			rdr = stmt.Clear().Crt_int(fld_owner_id, owner_id).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				byte[] bry = Read_bin_data(rdr);
				Io_mgr._.SaveFilBry(url, bry);
				return true;
			}
			else
				return false;
		}
		finally {rdr.Rls();}
	}
	private byte[] Read_bin_data(Db_rdr rdr) {
		byte[] rv = rdr.Read_bry(fld_data);
		return rv == null ? Bry_.Empty : rv;	// NOTE: bug in v0.10.1 where .ogg would save as null; return Bry_.Empty instead, else java.io.ByteArrayInputStream would fail on null
	}
	public static final byte Owner_tid_fil = 1, Owner_tid_thm = 2;
	public static final int Null_db_bin_id = -1, Null_size = -1, Null_part_id = -1;
	public static final String Null_data_url = "";
}
