/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.hxtns.blobs;

import gplx.Bry_bfr;
import gplx.Hash_adp_bry;
import gplx.Rls_able;
import gplx.String_;
import gplx.core.ios.Io_stream_zip_mgr;
import gplx.dbs.Db_conn;
import gplx.dbs.Db_rdr;
import gplx.dbs.Db_rdr_;
import gplx.dbs.Db_stmt;
import gplx.dbs.Db_stmt_;
import gplx.dbs.Dbmeta_fld_list;
import gplx.dbs.Dbmeta_idx_itm;
import gplx.dbs.Dbmeta_tbl_itm;

public class Hxtn_blob_tbl implements Rls_able {
	private static final String tbl_name = "hxtn_blob"; private static final Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private static final String
	  fld_blob_tid = flds.Add_int("blob_tid"), fld_wiki_id = flds.Add_int("wiki_id"), fld_blob_id = flds.Add_int("blob_id")
	, fld_zip_tid = flds.Add_byte("zip_tid"), fld_blob_data = flds.Add_bry("blob_data");
	private final Db_conn conn; private Db_stmt stmt_insert;
	private final byte zip_tid_default;
	private final Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	public Hxtn_blob_tbl(Db_conn conn, byte zip_tid_default) {
		this.conn = conn;
		conn.Rls_reg(this);
		this.zip_tid_default = zip_tid_default;
	}
	public String Tbl_name() {return tbl_name;}
	public Db_conn Conn() {return conn;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Stmt_bgn() {
		stmt_insert = conn.Stmt_insert(tbl_name, flds);
	}
	public void Stmt_end() {
		this.Rls();
		if (!conn.Meta_idx_exists(tbl_name, "pkey"))
			conn.Meta_idx_create(Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_blob_id, fld_wiki_id, fld_blob_tid));
	}
	public void Insert_by_rdr(Db_rdr rdr) {
		Db_stmt_.Insert_by_rdr(flds, rdr, stmt_insert);
	}
	public void Insert_exec(int blob_tid, int wiki_id, int blob_id, byte[] blob_data) {
		blob_data = zip_mgr.Zip(zip_tid_default, blob_data);
		stmt_insert.Clear()
			.Val_int(fld_blob_tid   , blob_tid)
			.Val_int(fld_wiki_id    , wiki_id)
			.Val_int(fld_blob_id    , blob_id)
			.Val_byte(fld_zip_tid   , zip_tid_default)
			.Val_bry(fld_blob_data  , blob_data)
		.Exec_insert();
	}
	public boolean Exists(int blob_tid, int wiki_id, int blob_id) {
		Db_rdr rdr = Db_rdr_.Empty;
		try {
			rdr = conn.Stmt_select(tbl_name, flds, fld_blob_tid, fld_wiki_id, fld_blob_id)
				.Crt_int(fld_blob_tid, blob_tid)
				.Crt_int(fld_wiki_id, wiki_id)
				.Crt_int(fld_blob_id, blob_id)
				.Exec_select__rls_auto();
			return rdr.Move_next();
		} finally {
			rdr.Rls();
		}
	}
	public void Select_to_regy(Bry_bfr temp_bfr, Hash_adp_bry blob_data_hash) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, String_.Ary(fld_wiki_id, fld_blob_id, fld_blob_tid))
			.Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				byte[] key = Make_key(temp_bfr, rdr.Read_int(fld_wiki_id), rdr.Read_int(fld_blob_id), rdr.Read_int(fld_blob_tid));
				blob_data_hash.Add_as_key_and_val(key);
			}
		} finally {
			rdr.Rls();
		}
	}
	public byte[] Select_text(int blob_tid, int wiki_id, int blob_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_blob_id, fld_wiki_id, fld_blob_tid)
			.Crt_int(fld_blob_id, blob_id)
			.Crt_int(fld_wiki_id, wiki_id)
			.Crt_int(fld_blob_tid, blob_tid)
			.Exec_select__rls_auto();
		try {
			if (rdr.Move_next()) {
				byte[] rv = rdr.Read_bry(fld_blob_data);
				byte zip_type = rdr.Read_byte(fld_zip_tid);
				rv = zip_mgr.Unzip(zip_type, rv);
				return rv;
			}
			else {
				return null;
			}
		} finally {
			rdr.Rls();
		}
	}

	public static byte[] Make_key(Bry_bfr temp_bfr, int blob_id, int wiki_id, int blob_tid) {
		return temp_bfr.Add_int_variable(blob_id).Add_byte_pipe().Add_int_variable(wiki_id).Add_byte_pipe().Add_int_variable(blob_tid).To_bry_and_clear();
	}

	public static final int Blob_tid__wtxt = 0, Blob_tid__html = 1;
}