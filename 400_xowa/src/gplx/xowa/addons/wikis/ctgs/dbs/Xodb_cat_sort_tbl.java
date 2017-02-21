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
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.addons.wikis.ctgs.*; 
public class Xodb_cat_sort_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_key;
	public Xodb_cat_sort_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "cat_sort";
		flds.Add_int_pkey_autonum("cs_id");
		this.fld_key			= flds.Add_str	("cs_key", 255);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final    Db_conn conn; 
	public String Tbl_name() {return tbl_name;} private final    String tbl_name; 
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__key() {conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, fld_key, fld_key));}
	public void Delete_idx__key() {conn.Meta_idx_delete(tbl_name, fld_key);}
	public void Insert_by_select(Db_conn tmp_conn) {
		Db_attach_mgr attach_mgr = new Db_attach_mgr(conn, new Db_attach_itm("temp_db", tmp_conn));
		attach_mgr.Exec_sql(String_.Concat_lines_nl
		( "INSERT INTO cat_sort (cs_key)"
		, "SELECT DISTINCT cl_sortkey"
		, "FROM   <temp_db>tmp_cat_link"
		));
	}
	public void Rls() {}
}
