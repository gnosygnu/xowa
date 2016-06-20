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
package gplx.xowa.addons.bldrs.exports.splits.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
import gplx.xowa.files.origs.*;
import gplx.xowa.addons.bldrs.exports.splits.metas.*; import gplx.xowa.addons.bldrs.exports.splits.rslts.*;
class Split_meta_wkr__org extends Split_meta_wkr_base {
	private final    Split_rslt_wkr__org rslt_wkr = new Split_rslt_wkr__org();
	private final    Db_conn atr_conn;
	private Xof_orig_tbl tbl; private Db_stmt stmt;
	public Split_meta_wkr__org(Split_ctx ctx, Db_conn atr_conn) {
		this.atr_conn = atr_conn;
		ctx.Rslt_mgr().Reg_wkr(rslt_wkr);
	}
	@Override public byte Tid() {return Split_page_list_type_.Tid__fsdb_org;}
	@Override public void On_nth_new(Split_ctx ctx, Db_conn trg_conn) {
		this.tbl = new Xof_orig_tbl(trg_conn, Bool_.N);
		tbl.Create_tbl();
		this.stmt = trg_conn.Stmt_insert(tbl.tbl_name, tbl.flds);
	}
	@Override public void On_nth_rls(Split_ctx ctx, Db_conn trg_conn) {this.stmt = Db_stmt_.Rls(stmt);}
	@Override protected String Load_sql(Db_attach_mgr attach_mgr, int ns_id, int score_bgn, int score_end) {
		attach_mgr.Conn_others_(new Db_attach_itm("atr_db", atr_conn));
		return String_.Concat_lines_nl
		( "SELECT  o.orig_ttl, o.orig_repo, o.orig_status, o.orig_ext, o.orig_w, o.orig_h, o.orig_redirect, fir.page_id"
		, "FROM    <atr_db>orig_reg o"
		, "        JOIN <atr_db>fsdb_fil f ON o.orig_ttl = f.fil_name"
		, "                JOIN fsdb_fil_regy fir ON f.fil_id = fir.fil_id"
		, "WHERE   fir.page_score >= {0}"
		, "AND     fir.page_score <  {1}"
		, "AND     fir.page_ns = {2}"
		, "ORDER BY page_id"
		);
	}
	@Override protected Object Load_itm(Db_rdr rdr) {return tbl.Load_by_rdr(rdr);}
	@Override protected void Save_itm(Split_ctx ctx, Split_rslt_mgr rslt_mgr, Object itm_obj) {
		Xof_orig_itm itm = (Xof_orig_itm)itm_obj;
		tbl.Insert(stmt, itm.Repo(), itm.Ttl(), itm.Ext().Id(), itm.W(), itm.H(), itm.Redirect());
		rslt_wkr.On__nth__itm(itm.Db_row_size(), String_.new_u8(itm.Ttl()), itm.Repo());
	}
}
