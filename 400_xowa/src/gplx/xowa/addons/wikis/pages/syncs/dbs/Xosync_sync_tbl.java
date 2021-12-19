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
package gplx.xowa.addons.wikis.pages.syncs.dbs;
import gplx.dbs.*; import gplx.dbs.utls.*;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
public class Xosync_sync_tbl implements Db_tbl {
	private final DbmetaFldList flds = new DbmetaFldList();
	private final String fld_page_id, fld_sync_date;
	private final Db_conn conn;
	public Xosync_sync_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_page_id		= flds.AddIntPkey("page_id");
		this.fld_sync_date		= flds.AddStr("sync_date", 32);
		conn.Rls_reg(this);
	}
	public String Tbl_name() {return tbl_name;} private final String tbl_name = "sync";
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public GfoDate Select_sync_date_or_min(int page_id) {
		Db_rdr rdr = conn.Stmt_select(tbl_name, flds, fld_page_id).Crt_int(fld_page_id, page_id).Exec_select__rls_auto();
		try		{return rdr.Move_next() ? rdr.Read_date_by_str(fld_sync_date) : GfoDateUtl.MinValue;}
		finally {rdr.Rls();}
	}
	public void Upsert(int page_id, GfoDate sync_date) {
		Db_tbl__crud_.Upsert(conn, tbl_name, flds, StringUtl.Ary(fld_page_id), page_id, sync_date.ToStrFmt_yyyyMMdd_HHmmss());
	}
	public void Rls() {}
}
