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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
class Xowd_cat_core_tbl__in_wkr extends Db_in_wkr__base {
	private Xowd_cat_core_tbl tbl;
	private String tbl_name, fld_cat_id; private Dbmeta_fld_list flds; private Ordered_hash hash;
	public void Ctor(Xowd_cat_core_tbl tbl, String tbl_name, Dbmeta_fld_list flds, String fld_cat_id) {
		this.tbl = tbl; this.tbl_name = tbl_name; this.flds = flds; this.fld_cat_id = fld_cat_id;
	}
	public void Init(Ordered_hash hash) {this.hash = hash;}
	@Override protected Db_qry Make_qry(int bgn, int end) {
		Object[] part_ary = In_ary(end - bgn);			
		return Db_qry_.select_cols_(tbl_name, Db_crt_.New_in(fld_cat_id, part_ary), flds.To_str_ary());
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xowd_page_itm itm = (Xowd_page_itm)hash.Get_at(i);
			stmt.Crt_int(fld_cat_id, itm.Id());
		}
	}
	@Override protected void Read_data(Cancelable cancelable, Db_rdr rdr) {
		while (rdr.Move_next()) {
			if (cancelable.Canceled()) return;
			Xowd_category_itm ctg_data = tbl.Load_itm(rdr);
			Xowd_page_itm page = (Xowd_page_itm)hash.Get_by(ctg_data.Id_val());
			page.Xtn_(ctg_data);
		}
	}
}
