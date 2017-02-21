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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
import gplx.dbs.*;
import gplx.xowa.wikis.data.*;
import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
public class Xodb_cat_link_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__from, fld__to_id, fld__type_id, fld__timestamp_unix, fld__sortkey, fld__sortkey_prefix;
	private Db_stmt stmt_insert;
	public Xodb_cat_link_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = TBL_NAME;
		this.fld__from				= flds.Add_int	("cl_from");                    // page_id
		this.fld__to_id				= flds.Add_int	("cl_to_id");                   // cat_id 
		this.fld__type_id			= flds.Add_byte	("cl_type_id");                 // page,file,subc
		this.fld__timestamp_unix	= flds.Add_long	("cl_timestamp_unix");
		this.fld__sortkey			= flds.Add_bry	("cl_sortkey");                 // uca key
		this.fld__sortkey_prefix	= flds.Add_str	(FLD__cl_sortkey_prefix, 255);  // page_title; needed for sorting under letter on catpage
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn; 
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__catbox()	{conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "catbox", fld__from));}
	public void Create_idx__catpage()	{conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "catpage", fld__to_id, fld__type_id, fld__sortkey));}
	public void Insert_bgn() {conn.Txn_bgn("cl__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int from, int to_id, byte type_id, long timestamp_unix, byte[] sortkey, byte[] sortkey_prefix) {
		this.Insert_cmd_by_batch(stmt_insert, from, to_id, type_id, timestamp_unix, sortkey, sortkey_prefix);
	}
	public void Insert_(int from, int to_id, byte type_id, long timestamp_unix, byte[] sortkey, byte[] sortkey_prefix) {
		Db_stmt stmt = conn.Stmt_insert(tbl_name, flds);
		this.Insert_cmd_by_batch(stmt, from, to_id, type_id, timestamp_unix, sortkey, sortkey_prefix);
	}
	private void Insert_cmd_by_batch(Db_stmt stmt, int from, int to_id, byte type_id, long timestamp_unix, byte[] sortkey, byte[] sortkey_prefix) {
		stmt.Clear()
			.Val_int(fld__from					, from)
			.Val_int(fld__to_id					, to_id)
			.Val_byte(fld__type_id				, type_id)
			.Val_long(fld__timestamp_unix		, timestamp_unix)
			.Val_bry(fld__sortkey				, sortkey)
			.Val_bry_as_str(fld__sortkey_prefix	, sortkey_prefix)
			.Exec_insert();
	}
	public Xodb_cat_link_row[] Select_by_page_id(int page_id) {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld__from).Crt_int(fld__from, page_id).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Xodb_cat_link_row row = new Xodb_cat_link_row
				( rdr.Read_int(fld__from)
				, rdr.Read_int(fld__to_id)
				, rdr.Read_byte(fld__type_id)
				, rdr.Read_long(fld__timestamp_unix)
				, rdr.Read_bry(fld__sortkey)
				, rdr.Read_bry_by_str(fld__sortkey_prefix)
				);
				list.Add(row);
			}
		} finally {
			rdr.Rls();
		}
		return (Xodb_cat_link_row[])list.To_ary_and_clear(Xodb_cat_link_row.class);
	}
	public void Delete_pages(int page_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: delete pages started: db=~{0} page_id=~{1}", conn.Conn_info().Raw(), page_id);
		conn.Stmt_delete(tbl_name, fld__from)
			.Crt_int(fld__from, page_id)
			.Exec_delete();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: delete pages done");
	}
	public void Delete_cats(int page_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: delete cats started: db=~{0} page_id=~{1}", conn.Conn_info().Raw(), page_id);
		conn.Stmt_delete(tbl_name, fld__to_id)
			.Crt_int(fld__to_id, page_id)
			.Exec_delete();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: delete cats done");
	}
	public void Update_page_id_for_pages(int old_id, int new_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: update cl_from started: db=~{0} old_id=~{1} new_id=~{2}", conn.Conn_info().Raw(), old_id, new_id);
		conn.Stmt_update(tbl_name, String_.Ary(fld__from), fld__from).Val_int(fld__from, new_id).Crt_int(fld__from, old_id).Exec_update();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: update cl_from done");
	}
	public void Update_page_id_for_cats(int old_id, int new_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: update cl_to started: db=~{0} old_id=~{1} new_id=~{2}", conn.Conn_info().Raw(), old_id, new_id);
		conn.Stmt_update(tbl_name, String_.Ary(fld__to_id), fld__to_id).Val_int(fld__to_id, new_id).Crt_int(fld__to_id, old_id).Exec_update();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.cat_link: update cl_to done");
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}

	public static final String TBL_NAME = "cat_link", FLD__cl_sortkey_prefix = "cl_sortkey_prefix";
}
/*
NOTE_1: categorylinks row size: 34 + 20 + 12 + (cat_sortkey.length * 2)
row length (data)		: 34=8+4+4+14+4				ROWID, cl_from, cl_to_id, cl_timestamp, cl_type_id
cl_main length (idx)	: 20=8+4+4+4				ROWID, cl_from, cl_to_id, cl_type_id
cl_from length (idx)	: 12=8+4					ROWID, cl_from
variable_data length	: cat_sortkey.length * 2	sortkey is used for row and cl_main

Note the following
. ints are 4 bytes
. tinyint is assumed to be 4 bytes (should be 1, but sqlite only has one numeric datatype, so using all 4?)
. varchar(14) is assumed to be 14 bytes (should be 15? +1 for length of varchar?)
. calculations work out "too well". comparing 4 databases gets +/- .25 bytes per row. however
.. - bytes should not be possible
.. +.25 bytes is too low (18 MB out of 5.5 GB); there must be other bytes used for page breaks / fragmentation
*/
