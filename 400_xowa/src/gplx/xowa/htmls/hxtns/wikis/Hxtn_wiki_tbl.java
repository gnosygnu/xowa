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
package gplx.xowa.htmls.hxtns.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hxtns.*;
import gplx.dbs.*;
public class Hxtn_wiki_tbl implements Rls_able {
	private static final String tbl_name = "hxtn_wiki"; private static final Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private static final String
	  fld_wiki_id = flds.Add_int_pkey("wiki_id"), fld_wiki_domain = flds.Add_str("wiki_domain", 255)
	;		
	private final Db_conn conn;
	public Hxtn_wiki_tbl(Db_conn conn) {
		this.conn = conn;
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
	}
	public void Rls() {}
	public void Insert(int wiki_id, String wiki_domain) {
		conn.Stmt_insert(tbl_name, flds).Clear()
			.Val_int(fld_wiki_id     , wiki_id)
			.Val_str(fld_wiki_domain , wiki_domain)
		.Exec_insert();
	}
	public Hash_adp Select() {
		Hash_adp rv = Hash_adp_.New();
		Db_rdr rdr = conn.Stmt_select_all(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				Hxtn_wiki_itm itm = new Hxtn_wiki_itm(rdr.Read_int(fld_wiki_id), rdr.Read_str(fld_wiki_domain));
				rv.Add(itm.Domain(), itm);
			}
		} finally {
			rdr.Rls();
		}
		return rv;
	}
}
