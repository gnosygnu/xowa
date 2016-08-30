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
class Xowd_page_tbl__ttl extends Xowd_page_tbl__in_wkr__base {
	private Ordered_hash hash; private int in_ns;
	@Override protected int Interval() {return 64;}	// NOTE: 96+ overflows; EX: w:Space_Liability_Convention; DATE:2013-10-24
	public void Init(Ordered_hash hash, int in_ns) {this.hash = hash; this.in_ns = in_ns;}
	@Override protected Criteria In_filter(Object[] part_ary) {
		int len = part_ary.length;
		Criteria[] crt_ary = new Criteria[len];
		String fld_ns = tbl.Fld_page_ns(); String fld_ttl = tbl.Fld_page_title();
		for (int i = 0; i < len; i++)
			crt_ary[i] = Criteria_.And(Db_crt_.New_eq(fld_ns, in_ns), Db_crt_.New_eq(fld_ttl, Bry_.Empty));
		return Criteria_.Or_many(crt_ary);
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xowd_page_itm page = (Xowd_page_itm)hash.Get_at(i);
			stmt.Val_int(in_ns);
			stmt.Val_bry_as_str(page.Ttl_page_db());
		}
	}
	@Override protected Xowd_page_itm Get_page_or_null(Xowd_page_itm rdr_page) {return (Xowd_page_itm)hash.Get_by(rdr_page.Ttl_page_db());}
}
