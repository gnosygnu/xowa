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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*; import gplx.core.criterias.*;
class Xodb_in_wkr_page_id extends Xodb_in_wkr_page_base {
	private ListAdp list;		// list is original list of ids which may have dupes; needed to fill statement (which takes range of bgn - end); DATE:2013-12-08
	private OrderedHash hash;	// hash is unique list of ids; needed for fetch from rdr (which indexes by id)
	public void Init(ListAdp list, OrderedHash hash) {this.list = list; this.hash = hash; this.Fill_idx_fields_only_(true);}
	@Override public int Interval() {return 990;}
	@Override public String In_fld_name() {return Xodb_page_tbl.Fld_page_id;}
	@Override public Criteria In_filter(Object[] part_ary) {
		return Db_crt_.in_(this.In_fld_name(), part_ary);
	}
	@Override public void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xodb_page page = (Xodb_page)list.FetchAt(i);
			stmt.Val_int(page.Id());		
		}
	}
	@Override public Xodb_page Eval_rslts_key(Xodb_page rdr_page) {return (Xodb_page)hash.Fetch(rdr_page.Id_val());}
}
class Xodb_in_wkr_page_title extends Xodb_in_wkr_page_base {
	private OrderedHash hash;
	private int in_ns;
	@Override public int Interval() {return 64;}	// NOTE: 96+ overflows; EX: w:Space_Liability_Convention; DATE:2013-10-24
	public void Init(OrderedHash hash, int in_ns) {this.hash = hash; this.in_ns = in_ns;}
	@Override public String In_fld_name() {return Xodb_page_tbl.Fld_page_title;}
	@Override public Criteria In_filter(Object[] part_ary) {
		int len = part_ary.length;
		Criteria[] crt_ary = new Criteria[len];
		for (int i = 0; i < len; i++)
			crt_ary[i] = Criteria_.And(Db_crt_.eq_(Xodb_page_tbl.Fld_page_ns, in_ns), Db_crt_.eq_(Xodb_page_tbl.Fld_page_title, Bry_.Empty));
		return Criteria_.Or_many(crt_ary);
	}
	@Override public void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xodb_page page = (Xodb_page)hash.FetchAt(i);
			stmt.Val_int(in_ns);
			stmt.Val_bry_as_str(page.Ttl_page_db());
		}
	}
	@Override public Xodb_page Eval_rslts_key(Xodb_page rdr_page) {return (Xodb_page)hash.Fetch(rdr_page.Ttl_page_db());}
}
class Xodb_in_wkr_page_title_ns extends Xodb_in_wkr_page_base {
	private Xow_ns_mgr ns_mgr;
	private OrderedHash hash;
	@Override public int Interval() {return 64;}	// NOTE: 96+ overflows; PAGE:en.w:Space_Liability_Convention; DATE:2013-10-24
	public void Init(Xow_ns_mgr ns_mgr, OrderedHash hash) {this.ns_mgr = ns_mgr; this.hash = hash;}
	@Override public String In_fld_name() {return Xodb_page_tbl.Fld_page_title;}
	@Override public Criteria In_filter(Object[] part_ary) {
		int len = part_ary.length;
		Criteria[] crt_ary = new Criteria[len];
		for (int i = 0; i < len; i++)
			crt_ary[i] = Criteria_.And(Db_crt_.eq_(Xodb_page_tbl.Fld_page_ns, 0), Db_crt_.eq_(Xodb_page_tbl.Fld_page_title, Bry_.Empty));
		return Criteria_.Or_many(crt_ary);
	}
	@Override public void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xodb_page page = (Xodb_page)hash.FetchAt(i);
			stmt.Val_int(page.Ns_id());
			stmt.Val_bry_as_str(page.Ttl_page_db());
		}
	}
	@Override public Xodb_page Eval_rslts_key(Xodb_page rdr_page) {
		Xow_ns ns = ns_mgr.Ids_get_or_null(rdr_page.Ns_id());
		if (ns == null) return null;	// NOTE: ns seems to "randomly" be null when threading during redlinks; guard against null; DATE:2014-01-03
		byte[] ttl_wo_ns = rdr_page.Ttl_page_db();
		rdr_page.Ttl_(ns, ttl_wo_ns);
		return (Xodb_page)hash.Fetch(rdr_page.Ttl_full_db());
	}
}
abstract class Xodb_in_wkr_page_base extends Xodb_in_wkr_base {
	public String Tbl_name() {return Xodb_page_tbl.Tbl_name;}
	public abstract String In_fld_name();
	public abstract Criteria In_filter(Object[] part_ary);
	public abstract Xodb_page Eval_rslts_key(Xodb_page rdr_page);
	public boolean Fill_idx_fields_only() {return fill_idx_fields_only;} public Xodb_in_wkr_page_base Fill_idx_fields_only_(boolean v) {fill_idx_fields_only = v; return this;} private boolean fill_idx_fields_only;
	@Override public Db_qry Build_qry(Xodb_ctx db_ctx, int bgn, int end) {
		Object[] part_ary = Xodb_in_wkr_base.In_ary(end - bgn);
		return Db_qry_.select_cols_
		(	this.Tbl_name()
		, 	In_filter(part_ary)
		, 	fill_idx_fields_only ? Xodb_page_tbl.Flds_select_idx : db_ctx.Html_db_enabled() ? Xodb_page_tbl.Flds_select_all__html_y : Xodb_page_tbl.Flds_select_all__html_n
		)
		;
	}
	@Override public void Eval_rslts(Cancelable cancelable, Xodb_ctx db_ctx, DataRdr rdr) {
		Xodb_page temp = new Xodb_page();
		boolean html_db_enabled = db_ctx.Html_db_enabled();
		while (rdr.MoveNextPeer()) {
			if (cancelable.Canceled()) return;
			if (fill_idx_fields_only)
				Xodb_page_tbl.Read_page__idx(temp, rdr);
			else
				Xodb_page_tbl.Read_page__all(temp, rdr, html_db_enabled);
			Xodb_page page = Eval_rslts_key(temp);
			if (page == null) continue; // page not found
			temp.Exists_(true);
			page.Copy(temp);
		}
	}
}
class Xodb_in_wkr_category_id extends Xodb_in_wkr_base {
	private OrderedHash hash;
	@Override public int Interval() {return 990;}
	public void Init(OrderedHash hash) {this.hash = hash;}
	@Override public Db_qry Build_qry(Xodb_ctx db_ctx, int bgn, int end) {
		Object[] part_ary = Xodb_in_wkr_base.In_ary(end - bgn);
		String in_fld_name = Xodb_category_tbl.Fld_cat_id; 
		return Db_qry_.select_cols_
		(	Xodb_category_tbl.Tbl_name
		, 	Db_crt_.in_(in_fld_name, part_ary)
		)
		;
	}
	@Override public void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xodb_page page = (Xodb_page)hash.FetchAt(i);
			stmt.Val_int(page.Id());		
		}
	}
	@Override public void Eval_rslts(Cancelable cancelable, Xodb_ctx db_ctx, DataRdr rdr) {
		while (rdr.MoveNextPeer()) {
			if (cancelable.Canceled()) return;
			Xodb_category_itm ctg_data = Xodb_category_tbl.Read_ctg(rdr);
			Xodb_page page = (Xodb_page)hash.Fetch(ctg_data.Id_val());
			page.Xtn_(ctg_data);
		}
	}
}
