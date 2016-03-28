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
import gplx.core.primitives.*; import gplx.core.envs.*;
import gplx.dbs.*; import gplx.core.ios.*;
import gplx.dbs.engines.sqlite.*;
public class Fsd_bin_tbl implements Rls_able {
	private final String tbl_name = "fsdb_bin"; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_owner_id, fld_owner_tid, fld_part_id, fld_data_url, fld_data;
	private Db_conn conn; private Db_stmt stmt_insert, stmt_select; private Bry_bfr tmp_bfr;
	private final Bool_obj_ref saved_in_parts = Bool_obj_ref.n_();
	public Fsd_bin_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		fld_owner_id		= flds.Add_int_pkey	("bin_owner_id");
		fld_owner_tid		= flds.Add_byte		("bin_owner_tid");
		fld_part_id			= flds.Add_int		("bin_part_id");
		fld_data_url		= flds.Add_str		("bin_data_url", 255);
		fld_data			= flds.Add_bry		("bin_data");	// mediumblob
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select = Db_stmt_.Rls(stmt_select);
	}
	public void Create_tbl()	{conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn()	{conn.Txn_bgn("fsdb_bin__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_commit()	{conn.Txn_sav();}
	public void Insert_end()	{conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_rdr(int id, byte tid, long bin_len, Io_stream_rdr bin_rdr) {
		if (stmt_insert == null) {
			stmt_insert = conn.Stmt_insert(tbl_name, flds);
			tmp_bfr = Bry_bfr.reset_(Io_mgr.Len_kb);
		}
		byte[] bin_ary = null;
		bin_ary = Io_stream_rdr_.Load_all_as_bry(tmp_bfr, bin_rdr);
		stmt_insert.Clear()
		.Val_int(fld_owner_id, id)
		.Val_byte(fld_owner_tid, tid)
		.Val_int(fld_part_id, Part_id_null)
		.Val_str(fld_data_url, Data_url_null)
		.Val_bry(fld_data, bin_ary)
		.Exec_insert();
	}
	public Io_stream_rdr Select_as_rdr(int owner_id) {
		byte[] rv = Select(owner_id, null);
		return rv == null
			? Io_stream_rdr_.Noop
			: Io_stream_rdr_.mem_(rv);
	}
	public boolean Select_to_url(int owner_id, Io_url url) {
		saved_in_parts.Val_n();
		byte[] rv = Select(owner_id, url);
		if (rv == null) return false;
		if (saved_in_parts.Val_y()) return true;
		Io_mgr.Instance.SaveFilBry(url, rv);
		return true;
	}
	private byte[] Select(int owner_id, Io_url url) {
		if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, String_.Ary(fld_data), fld_owner_id);
		Db_rdr rdr = stmt_select.Clear().Crt_int(fld_owner_id, owner_id).Exec_select__rls_manual();
		try {
			if (rdr.Move_next()) {
				byte[] rv = null;
				try {rv = rdr.Read_bry(fld_data);}
				catch (Exception e) {
					if (	Op_sys.Cur().Tid_is_drd() 
						&&	url != null														// called by Select_to_url
						&&	String_.Has(Err_.Message_lang(e), "get field slot from row")	// get field slot from row 0 col 0 failed
						) { 	
						rdr.Save_bry_in_parts(url, tbl_name, fld_data, fld_owner_id, owner_id);
						saved_in_parts.Val_y_();
					}
				}
				return rv == null ? Bry_.Empty : rv;	// NOTE: bug in v0.10.1 where .ogg would save as null; return Bry_.Empty instead, else java.io.ByteArrayInputStream would fail on null
			}
			else
				return null;
		}
		finally {rdr.Rls();}
	}
	public static final byte Owner_tid_fil = 1, Owner_tid_thm = 2;
	public static final int Bin_db_id_null = -1, Size_null = -1;
	private static final int Part_id_null = -1;
	private static final String Data_url_null = "";
}
