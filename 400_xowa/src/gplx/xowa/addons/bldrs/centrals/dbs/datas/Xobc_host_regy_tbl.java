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
public class Xobc_host_regy_tbl implements Db_tbl {
	private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_host_id, fld_host_domain, fld_host_data_dir, fld_host_update_dir;
	private final    Db_conn conn;
	public Xobc_host_regy_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= "host_regy";
		this.fld_host_id			= flds.Add_int_pkey("host_id");
		this.fld_host_domain		= flds.Add_str("host_domain", 255);		// EX: archive.org
		this.fld_host_data_dir		= flds.Add_str("host_data_dir", 255);	// EX: download/Xowa_~{host_regy|wiki_abrv}_latest
		this.fld_host_update_dir	= flds.Add_str("host_update_dir", 255);	// EX: download/Xowa_app_support
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void Create_tbl() {
		conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));
		conn.Stmt_insert(tbl_name, flds)
			.Val_int(fld_host_id, Host_id__archive_org).Val_str(fld_host_domain, "archive.org")
			.Val_str(fld_host_data_dir, "download/Xowa_~{host_regy|wiki_abrv}_latest").Val_str(fld_host_update_dir, "download/Xowa_app_support")
			.Exec_insert();
	}
	public Xobc_host_regy_itm Select(int host_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_host_id).Crt_int(fld_host_id, host_id).Exec_select__rls_auto();
		try		{return rdr.Move_next() ? Load_itm(rdr) : null;}
		finally {rdr.Rls();}
	}
	private Xobc_host_regy_itm Load_itm(Db_rdr rdr) {
		return new Xobc_host_regy_itm(rdr.Read_int(fld_host_id), rdr.Read_str(fld_host_domain), rdr.Read_str(fld_host_data_dir), rdr.Read_str(fld_host_update_dir));
	}
	public void Rls() {}
	public static final int Host_id__archive_org = 1;
}
