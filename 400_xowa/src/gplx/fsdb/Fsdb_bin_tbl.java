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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*; import gplx.ios.*;
public class Fsdb_bin_tbl {
	public static void Create_table(Db_conn p) {Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);}
	public static Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_bin_owner_id, Fld_bin_owner_tid, Fld_bin_part_id, Fld_bin_data_url, Fld_bin_data);}
	public static void Insert_rdr(Db_conn p, int id, byte tid, long bin_len, Io_stream_rdr bin_rdr) {
		Db_stmt stmt = Insert_stmt(p);
		try {Insert_rdr(stmt, id, tid, bin_len, bin_rdr);}
		finally {stmt.Rls();}
	}
	public static long Insert_rdr(Db_stmt stmt, int id, byte tid, long bin_len, Io_stream_rdr bin_rdr) {
		long rv = bin_len;
		stmt.Clear()
		.Val_int(id)
		.Val_byte(tid)
		.Val_int(Null_part_id)
		.Val_str(Null_data_url)
		;
		if (Sqlite_engine_.Supports_read_binary_stream)
			stmt.Val_rdr_(bin_rdr, bin_len);
		else {
			byte[] bin_ary = Io_stream_rdr_.Load_all_as_bry(Bry_bfr.new_(), bin_rdr);
			stmt.Val_bry(bin_ary);
			rv = bin_ary.length;
		}
		stmt.Exec_insert();
		return rv;
	}	
	public static void Delete(Db_conn p, int id) {
		Db_stmt stmt = Delete_stmt(p);
		try {Delete(stmt, id);}
		finally {stmt.Rls();}
	}
	private static Db_stmt Delete_stmt(Db_conn p) {return Db_stmt_.new_delete_(p, Tbl_name, Fld_bin_owner_id);}
	private static void Delete(Db_stmt stmt, int id) {
		stmt.Clear()
		.Val_int(id)
		.Exec_delete();
	}	
	public static Io_stream_rdr Select_as_rdr(Db_conn p, int owner) {
		Db_qry qry = Db_qry_.select_().From_(Tbl_name).Cols_(Fld_bin_data).Where_(Db_crt_.eq_(Fld_bin_owner_id, owner));
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = p.Exec_qry_as_rdr(qry);
			if (rdr.MoveNextPeer()) {
				if (Sqlite_engine_.Supports_read_binary_stream)
					return rdr.ReadRdr(Fld_bin_data);
				else
					return gplx.ios.Io_stream_rdr_.mem_(Read_bin_data(rdr));
			}
			else
				return gplx.ios.Io_stream_rdr_.Null;
		}
		finally {rdr.Rls();}
	}
	public static boolean Select_to_url(Db_conn p, int owner, Io_url url, byte[] bin_bfr, int bin_flush_when) {
		Db_qry qry = Db_qry_.select_().From_(Tbl_name).Cols_(Fld_bin_data).Where_(Db_crt_.eq_(Fld_bin_owner_id, owner));
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = p.Exec_qry_as_rdr(qry);
			if (rdr.MoveNextPeer()) {
				if (Sqlite_engine_.Supports_read_binary_stream)
					return Select_to_fsys__stream(rdr, url, bin_bfr, bin_flush_when);
				else {
					byte[] bry = Read_bin_data(rdr);
					Io_mgr._.SaveFilBry(url, bry);
					return true;
				}
			}
			else
				return false;
		}
		finally {rdr.Rls();}
	}
	public static boolean Select_to_fsys__stream(DataRdr rdr, Io_url url, byte[] bin_bfr, int bin_flush_when) {
		Io_stream_rdr db_stream = Io_stream_rdr_.Null;
		IoStream fs_stream = IoStream_.Null;
		try {
			db_stream = rdr.ReadRdr(Fld_bin_data); if (db_stream == Io_stream_rdr_.Null) return false;
			fs_stream = Io_mgr._.OpenStreamWrite(url);
			int pos = 0, flush_nxt = bin_flush_when;
			while (true) {
				int read = db_stream.Read(bin_bfr, pos, bin_bfr.length); if (read == Io_stream_rdr_.Read_done) break;
				fs_stream.Write(bin_bfr, 0, read);
				if (pos > flush_nxt) {
					fs_stream.Flush();
					flush_nxt += bin_flush_when;
				}
			}
			fs_stream.Flush();
			return true;
		} finally {
			db_stream.Rls();
			fs_stream.Rls();
		}
	}
	private static byte[] Read_bin_data(DataRdr rdr) {
		byte[] rv = rdr.ReadBry(Fld_bin_data);
		return rv == null ? Bry_.Empty : rv;	// NOTE: bug in v0.10.1 where .ogg would save as null; return Bry_.Empty instead, else java.io.ByteArrayInputStream would fail on null
	}
	public static final String Tbl_name = "fsdb_bin", Fld_bin_owner_id = "bin_owner_id", Fld_bin_owner_tid = "bin_owner_tid", Fld_bin_part_id = "bin_part_id", Fld_bin_data_url = "bin_data_url", Fld_bin_data = "bin_data";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_bin"
	,	"( bin_owner_id          integer             NOT NULL    PRIMARY KEY"
	,	", bin_owner_tid         byte                NOT NULL"
	,	", bin_part_id           integer             NOT NULL"
	,	", bin_data_url          varchar(255)        NOT NULL"
	,	", bin_data              mediumblob          NOT NULL"
	,	");"
	);
	public static final byte Owner_tid_fil = 1, Owner_tid_thm = 2;
	public static final int Null_db_bin_id = -1, Null_size = -1, Null_part_id = -1;
	public static final String Null_data_url = "";
}
