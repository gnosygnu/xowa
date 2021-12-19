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
package gplx.fsdb.meta; import gplx.fsdb.*;
import gplx.dbs.*;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
public class Fsm_bin_tbl implements Db_tbl {
	private final DbmetaFldList flds = new DbmetaFldList();
	private final String fld_uid, fld_url, fld_bin_len, fld_bin_max;
	private final Db_conn conn; private int mnt_id;
	public Fsm_bin_tbl(Db_conn conn, boolean schema_is_1, int mnt_id) {
		this.conn = conn; this.mnt_id = mnt_id;
		String fld_prefix = "";
		if (schema_is_1)	{tbl_name = "fsdb_db_bin";}
		else				{tbl_name = "fsdb_dbb"; fld_prefix = "dbb_";}
		fld_uid				= flds.AddIntPkey(fld_prefix + "uid");
		fld_url				= flds.AddStr(fld_prefix + "url", 255);
		if (schema_is_1) {
			fld_bin_len = flds.AddLong("bin_len");
			fld_bin_max = flds.AddLong("bin_max");
		}
		else {
			fld_bin_len = DbmetaFldItm.KeyNull;
			fld_bin_max = DbmetaFldItm.KeyNull;
		}
	}
	public String Tbl_name() {return tbl_name;} private final String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Insert(int id, String url_rel) {
		conn.Stmt_insert(tbl_name, flds).Crt_int(fld_uid, id).Val_str(fld_url, url_rel).Val_long(fld_bin_len, 0).Val_long(fld_bin_max, 0).Exec_insert();
	}
	public Fsm_bin_fil[] Select_all(Fsdb_db_mgr db_conn_mgr) {
		List_adp rv = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select_order(tbl_name, flds, DbmetaFldItm.StrAryEmpty, fld_uid).Clear().Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int bin_id = rdr.Read_int(fld_uid);
				String bin_url = rdr.Read_str(fld_url);
				Fsdb_db_file bin_db = db_conn_mgr.File__bin_file__at(mnt_id, bin_id, bin_url);
				Fsm_bin_fil itm = new Fsm_bin_fil(db_conn_mgr.File__schema_is_1(), bin_id, bin_db.Url(), bin_url, bin_db.Conn(), Fsm_bin_fil.Bin_len_null);
				rv.Add(itm);
			}
		}	finally {rdr.Rls();}
		return (Fsm_bin_fil[])rv.ToAry(Fsm_bin_fil.class);
	}
	public void Rls() {}
}
