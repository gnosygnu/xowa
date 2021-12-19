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
package gplx.xowa.htmls.hxtns.wkrs;
import gplx.dbs.*;
import gplx.core.lists.hashs.*;
import gplx.frameworks.objects.Rls_able;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.utls.IntUtl;
public class Hxtn_wkr_tbl implements Rls_able {
	private static final String tbl_name = "hxtn_wkr"; private static final DbmetaFldList flds = new DbmetaFldList();
	private static final String
	  fld_wkr_id = flds.AddIntPkey("wkr_id"), fld_wkr_key = flds.AddStr("wkr_key", 255)
	;		
	private final Db_conn conn;
	public Hxtn_wkr_tbl(Db_conn conn) {
		this.conn = conn;
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
	}
	public void Rls() {}
	public int Select_max() {
		return IntUtl.Cast(conn.Stmt_select_max(tbl_name, fld_wkr_id).Exec_select_val());
	}
	public void Insert(int wkr_id, String wkr_key) {
		conn.Stmt_insert(tbl_name, flds).Clear()
			.Val_int(fld_wkr_id  , wkr_id)
			.Val_str(fld_wkr_key , wkr_key)
		.Exec_insert();
	}
	public void Select(Hash_adp__int wkrs_by_id, Hash_adp wkrs_by_key) {
		wkrs_by_id.Clear();
		wkrs_by_key.Clear();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				int wkr_id = rdr.Read_int(fld_wkr_id);
				String wkr_key = rdr.Read_str(fld_wkr_key);
				wkrs_by_id.Add(wkr_id, wkr_key);
				wkrs_by_key.Add(wkr_key, wkr_id);
			}
		} finally {
			rdr.Rls();
		}
	}
}
