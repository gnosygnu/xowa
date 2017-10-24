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
package gplx.dbs; import gplx.*;
public class Db_stmt_bldr {
	private Db_conn conn; private Db_stmt create, update, delete;
	private String tbl_name; private String[] flds_keys, flds_vals, flds_all;
	public void Conn_(Db_conn v, String tbl_name, Dbmeta_fld_list flds, String... flds_keys) {
		Conn_(v, tbl_name, flds.To_str_ary(), flds.To_str_ary_exclude(flds_keys), flds_keys);
	}
	public void Conn_(Db_conn v, String tbl_name, String[] flds_vals, String... flds_keys) {
		Conn_(v, tbl_name, String_.Ary_add(flds_keys, flds_vals), flds_vals, flds_keys);
	}
	private void Conn_(Db_conn v, String tbl_name, String[] flds_all, String[] flds_vals, String... flds_keys) {
		this.conn = v; this.tbl_name = tbl_name;
		this.flds_all = flds_all; this.flds_vals = flds_vals; this.flds_keys = flds_keys;
	}
	public Db_stmt Get(byte cmd_mode) {
		switch (cmd_mode) {
			case Db_cmd_mode.Tid_create:	if (create == null) create = conn.Stmt_insert(tbl_name, flds_all);				return create;
			case Db_cmd_mode.Tid_update:	if (update == null) update = conn.Stmt_update(tbl_name, flds_keys, flds_vals);	return update;
			case Db_cmd_mode.Tid_delete:	if (delete == null) delete = conn.Stmt_delete(tbl_name, flds_keys);				return delete;
			case Db_cmd_mode.Tid_ignore:	return Db_stmt_.Null;
			default:						throw Err_.new_unhandled(cmd_mode);
		}
	}
	public void Batch_bgn() {conn.Txn_bgn(tbl_name);}
	public void Batch_end() {conn.Txn_end();}
	public void Rls() {
		create = Db_stmt_.Rls(create);
		update = Db_stmt_.Rls(update);
		delete = Db_stmt_.Rls(delete);
	}
}
