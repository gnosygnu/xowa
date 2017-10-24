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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xowd_text_tbl implements Db_tbl {
	private final    Object thread_lock = new Object();
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_page_id, fld_text_data;
	private final    Db_conn conn; private Db_stmt stmt_select, stmt_insert;
	private final    Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr(); private final    byte zip_tid;
	public String Fld_text_data() {return fld_text_data;}
	public Xowd_text_tbl(Db_conn conn, boolean schema_is_1, byte zip_tid) {
		this.conn = conn; this.zip_tid = zip_tid;
		String fld_text_data_name = "";
		fld_text_data_name = schema_is_1 ? "old_text" : "text_data";
		fld_page_id			= flds.Add_int_pkey("page_id");
		fld_text_data		= flds.Add_bry(fld_text_data_name);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name = TBL_NAME; 
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn() {conn.Txn_bgn("schema__text__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int page_id, byte[] text_data) {
		stmt_insert.Clear().Val_int(fld_page_id, page_id).Val_bry(fld_text_data, text_data).Exec_insert();
	}
	public void Update(int page_id, byte[] text) {
		Db_stmt stmt = conn.Stmt_update_exclude(tbl_name, flds, fld_page_id);
		text = zip_mgr.Zip(zip_tid, text);
		stmt.Clear().Val_bry(fld_text_data, text).Crt_int(fld_page_id, page_id).Exec_update();
	}
	public void Delete(int page_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.text: delete started: db=~{0} page_id=~{1}", conn.Conn_info().Raw(), page_id);
		conn.Stmt_delete(tbl_name, fld_page_id).Crt_int(fld_page_id, page_id).Exec_delete();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.text: delete done");
	}
	public void Update_page_id(int old_id, int new_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.text: update page_id started: db=~{0} old_id=~{1} new_id=~{2}", conn.Conn_info().Raw(), old_id, new_id);
		conn.Stmt_update(tbl_name, String_.Ary(fld_page_id), fld_page_id).Val_int(fld_page_id, new_id).Crt_int(fld_page_id, old_id).Exec_update();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.text: update page_id done");
	}
	public byte[] Select(int page_id) {
		synchronized (thread_lock) {	// LOCK:stmt-rls; DATE:2016-07-06
			if (stmt_select == null) stmt_select = conn.Stmt_select(tbl_name, flds, fld_page_id);
			Db_rdr rdr = stmt_select.Clear().Crt_int(fld_page_id, page_id).Exec_select__rls_manual();
			try {
				byte[] rv = Bry_.Empty;
				if (rdr.Move_next()) {
					rv = rdr.Read_bry(fld_text_data);
					if (rv == null) rv = Bry_.Empty;	// NOTE: defect wherein blank page inserts null not ""; for now always convert null to empty String; DATE:2015-11-08
					rv = zip_mgr.Unzip(zip_tid, rv);
				}
				return rv;
			}	finally {rdr.Rls();}
		}
	}
	public Xowd_text_row[] Select_where(byte[] query) {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_sql(Db_sql_.Make_by_fmt(String_.Ary("SELECT * FROM text WHERE text_data LIKE '{0}'") , query)).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int page_id = rdr.Read_int(fld_page_id);
				byte[] text = rdr.Read_bry(fld_text_data);
				if (text == null) text = Bry_.Empty;	// NOTE: defect wherein blank page inserts null not ""; for now always convert null to empty String; DATE:2015-11-08
				text = zip_mgr.Unzip(zip_tid, text);
				list.Add(new Xowd_text_row(page_id, text));
			}
		}	finally {rdr.Rls();}
		return (Xowd_text_row[])list.To_ary_and_clear(Xowd_text_row.class);
	}
	public byte[] Zip(byte[] data) {return zip_mgr.Zip(zip_tid, data);}
	public void Rls() {
		synchronized (thread_lock) {	// LOCK:stmt-rls; DATE:2016-07-06
			stmt_select = Db_stmt_.Rls(stmt_select);
			stmt_insert = Db_stmt_.Rls(stmt_insert);
		}
	}

	public static final String TBL_NAME = "text";
	public static Xowd_text_tbl Get_by_key(Db_tbl_owner owner) {return (Xowd_text_tbl)owner.Tbls__get_by_key(TBL_NAME);}
}
