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
package gplx.xowa.addons.bldrs.exports.splits.srchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
class Split_meta_wkr__word extends Split_meta_wkr_base {
	private Srch_word_tbl tbl;
	private Db_stmt stmt;
	private final    Split_rslt_wkr__word rslt_wkr = new Split_rslt_wkr__word();
	public Split_meta_wkr__word(Split_ctx ctx) {
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	@Override public byte Tid() {return Split_page_list_type_.Tid__srch_word;}
	@Override public void On_nth_new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Srch_word_tbl(trg_conn);
		tbl.Create_tbl();
		this.stmt = trg_conn.Stmt_insert(tbl.tbl_name, tbl.flds);
	}
	@Override public void On_nth_rls(Split_ctx ctx, Db_conn trg_conn) {this.stmt = Db_stmt_.Rls(stmt);}
	@Override protected String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end) {
		return String_.Concat_lines_nl
		( "SELECT  sw.word_id, sw.word_text, sw.link_count, sw.link_count_score, sw.link_score_min, sw.link_score_max, sw.page_id"
		, "FROM    split_search_word sw"
		, "WHERE   sw.page_score >= {0}"
		, "AND     sw.page_score <  {1}"
		, "AND     sw.page_ns = {2}"
		, "ORDER BY page_id"
		);
	}
	@Override protected Object Load_itm(Db_rdr rdr) {return tbl.New_row(rdr);}
	@Override protected void Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm_obj) {
		Srch_word_row itm = (Srch_word_row)itm_obj;
		tbl.Insert_by_itm(stmt, itm);
		rslt_wkr.On__nth__itm(itm.Db_row_size(), itm.Id);
	}
}
