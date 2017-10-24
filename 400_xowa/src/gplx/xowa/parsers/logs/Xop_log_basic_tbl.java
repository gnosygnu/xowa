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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
public class Xop_log_basic_tbl implements Db_tbl {		
	public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public final    String fld__log_id, fld__log_tid, fld__log_msg, fld__log_time, fld__page_id, fld__page_ttl, fld__args_len, fld__args_str, fld__src_len, fld__src_str;
	private Db_stmt stmt_insert;
	public Xop_log_basic_tbl(Db_conn conn){
		this.conn = conn;
		this.tbl_name				= "log_basic_temp";
		this.fld__log_id			= flds.Add_int_pkey_autonum("log_id");
		this.fld__log_tid			= flds.Add_int("log_tid");
		this.fld__log_msg			= flds.Add_str("log_msg", 255);
		this.fld__log_time			= flds.Add_int("log_time");
		this.fld__page_id			= flds.Add_int("page_id");
		this.fld__page_ttl			= flds.Add_str("page_ttl", 255);
		this.fld__args_len			= flds.Add_int("args_len");
		this.fld__args_str			= flds.Add_str("args_str", 4096);
		this.fld__src_len			= flds.Add_int("src_len");
		this.fld__src_str			= flds.Add_str("src_str", 4096);
		conn.Rls_reg(this);
		this.Create_tbl();
	} 
	public Db_conn				Conn()			{return conn;} private final    Db_conn conn; 
	public String				Tbl_name()		{return tbl_name;} private final    String tbl_name;
	public void					Create_tbl()	{conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void					Delete()		{conn.Exec_qry(Db_qry_delete.new_all_(tbl_name));}
	public void Insert(int log_tid, String log_msg, int log_time, int page_id, String page_ttl, int args_len, String args_str, int src_len, String src_str) {
		if (stmt_insert == null) stmt_insert = Db_stmt_.new_insert_(conn, tbl_name, fld__log_tid, fld__log_msg, fld__log_time, fld__page_id, fld__page_ttl, fld__args_len, fld__args_str, fld__src_len, fld__src_str);
		stmt_insert.Clear()
		.Val_int(fld__log_tid, log_tid)
		.Val_str(fld__log_msg, log_msg)
		.Val_int(fld__log_time, log_time)
		.Val_int(fld__page_id, page_id)
		.Val_str(fld__page_ttl, page_ttl)
		.Val_int(fld__args_len, args_len)
		.Val_str(fld__args_str, args_str)
		.Val_int(fld__src_len, src_len)
		.Val_str(fld__src_str, src_str)
		.Exec_insert();
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
}
