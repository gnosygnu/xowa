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
import gplx.core.criterias.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
class Xowd_page_tbl__id extends Xowd_page_tbl__in_wkr__base {
	private final    List_adp list;		// list is original list of ids which may have dupes; needed to fill statement (which takes range of bgn - end); DATE:2013-12-08
	private final    Ordered_hash hash;	// hash is unique list of ids; needed for fetch from rdr (which indexes by id)
	public Xowd_page_tbl__id(List_adp list, Ordered_hash hash, boolean show_progress) {
		this.show_progress = show_progress;
		this.list = list; this.hash = hash;
		this.Fill_idx_fields_only_(true);
	}
	@Override protected boolean Show_progress() {return show_progress;} private final    boolean show_progress;
	@Override protected Criteria In_filter(Object[] part_ary) {
		return Db_crt_.New_in(this.In_fld_name(), part_ary);
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xowd_page_itm page = (Xowd_page_itm)list.Get_at(i);
			stmt.Val_int(page.Id());		
		}
	}
	@Override protected Xowd_page_itm Get_page_or_null(Xowd_page_itm rdr_page) {return (Xowd_page_itm)hash.Get_by(rdr_page.Id_val());}
}
