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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.dbs.*;
public class Imglnk_tmp_tbl implements Db_tbl {
	private final    String tbl_name = "imglnk_tmp"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__page_id, fld__img_name;
	private final    Db_conn conn;
	private Db_stmt stmt__insert;
	public Imglnk_tmp_tbl(Db_conn conn) {
		this.conn = conn;
		flds.Add_int_pkey_autonum("img_uid");
		this.fld__page_id			= flds.Add_int("page_id");
		this.fld__img_name			= flds.Add_str("img_name", 255);
		flds.Add_int_dflt("img_wiki", -1);
		flds.Add_int_dflt("img_id", -1);
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert_bgn() {
		stmt__insert = conn.Stmt_insert(tbl_name, fld__page_id, fld__img_name);
		conn.Txn_bgn(tbl_name);
	}
	public void Insert_by_batch(int page_id, byte[] img_name) {
		stmt__insert.Clear().Val_int(fld__page_id, page_id).Val_bry_as_str(fld__img_name, img_name).Exec_insert();
	}
	public void Insert_end() {
		conn.Txn_end();
		stmt__insert.Rls();
	}
	public void Create_idx__img_ttl() {
		conn.Meta_idx_create(tbl_name, fld__img_name, fld__img_name);
	}
	public void Rls() {
		stmt__insert = Db_stmt_.Rls(stmt__insert);
	}
}
