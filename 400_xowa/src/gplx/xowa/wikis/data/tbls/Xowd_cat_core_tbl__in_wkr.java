/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
		return Db_qry_.select_cols_(tbl_name, Db_crt_.in_(fld_cat_id, part_ary), flds.To_str_ary());
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
			Xowd_category_itm ctg_data = tbl.new_itm(rdr);
			Xowd_page_itm page = (Xowd_page_itm)hash.Get_by(ctg_data.Id_val());
			page.Xtn_(ctg_data);
		}
	}
}
