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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
import gplx.dbs.*;
public class Xobc_lang_regy_tbl implements Db_tbl {		
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld__lang_id, fld__lang_key, fld__lang_name;
	private final    Db_conn conn;
	public Xobc_lang_regy_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "lang_regy";
		this.fld__lang_id			= flds.Add_int_pkey("lang_id");
		this.fld__lang_key			= flds.Add_str("lang_key", 255);
		this.fld__lang_name			= flds.Add_str("lang_name", 255);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public Xobc_lang_regy_itm[] Select_all() {
		List_adp list = List_adp_.New();
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				list.Add(new Xobc_lang_regy_itm(rdr.Read_int(fld__lang_id), rdr.Read_str(fld__lang_key), rdr.Read_str(fld__lang_name)));
		}
		finally {rdr.Rls();}
		return (Xobc_lang_regy_itm[])list.To_ary_and_clear(Xobc_lang_regy_itm.class);
	}
	public void Rls() {}
}
