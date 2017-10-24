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
package gplx.xowa.addons.bldrs.wmdumps.pagelinks.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*; import gplx.xowa.addons.bldrs.wmdumps.pagelinks.*;
import gplx.core.ios.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.dbs.*; import gplx.dbs.cfgs.*;
public class Pglnk_page_link_tbl implements Rls_able {
	private final    String tbl_name = "page_link"; private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	private final    String fld_src_id, fld_trg_id;
	private final    Db_conn conn;
	public Pglnk_page_link_tbl(Db_conn conn) {
		this.conn = conn;
		fld_src_id			= flds.Add_int("src_id");
		fld_trg_id			= flds.Add_int("trg_id");
		flds.Add_int("trg_count");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;}
	public String Tbl_name() {return tbl_name;}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__src_trg() {conn.Meta_idx_create(Gfo_usr_dlg_.Instance, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "src_trg", fld_src_id, fld_trg_id));}
	public void Create_idx__trg_src() {conn.Meta_idx_create(Gfo_usr_dlg_.Instance, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "trg_src", fld_trg_id, fld_src_id));}
	public void Rls() {}
}
