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
package gplx.xowa.addons.bldrs.files.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*;
public class Page_file_map_tbl implements Db_tbl {		
	public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public final    String fld_page_id, fld_fil_id, fld_thm_id, fld_sort_id, fld_count_of;
	public final    Db_conn conn;
	public Page_file_map_tbl(Db_conn conn, String tbl_name) {
		this.conn = conn;
		this.tbl_name = tbl_name;
		this.fld_page_id		= flds.Add_int("page_id");
		this.fld_fil_id			= flds.Add_int("fil_id");
		this.fld_thm_id			= flds.Add_int("thm_id");
		this.fld_sort_id		= flds.Add_int("sort_id");
		this.fld_count_of		= flds.Add_int("count_of");
		this.meta				= Dbmeta_tbl_itm.New(tbl_name, flds);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public void	Create_tbl() {conn.Meta_tbl_create(meta);}
	public Dbmeta_tbl_itm Meta() {return meta;} private final    Dbmeta_tbl_itm meta;
	public void Rls() {}
}
