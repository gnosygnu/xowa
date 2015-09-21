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
import gplx.xowa.nss.*;
class Xowd_page_tbl__ttl_ns extends Xowd_page_tbl__in_wkr__base {
	private Xow_ns_mgr ns_mgr; private Ordered_hash hash;
	@Override protected int Interval() {return 64;}	// NOTE: 96+ overflows; PAGE:en.w:Space_Liability_Convention; DATE:2013-10-24
	public void Init(Xow_ns_mgr ns_mgr, Ordered_hash hash) {this.ns_mgr = ns_mgr; this.hash = hash;}
	@Override protected Criteria In_filter(Object[] part_ary) {
		int len = part_ary.length;
		Criteria[] crt_ary = new Criteria[len];
		String fld_ns = tbl.Fld_page_ns(); String fld_ttl = tbl.Fld_page_title();
		for (int i = 0; i < len; i++)
			crt_ary[i] = Criteria_.And(Db_crt_.eq_(fld_ns, 0), Db_crt_.eq_(fld_ttl, Bry_.Empty));
		return Criteria_.Or_many(crt_ary);
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xowd_page_itm page = (Xowd_page_itm)hash.Get_at(i);
			stmt.Val_int(page.Ns_id());
			stmt.Val_bry_as_str(page.Ttl_page_db());
		}
	}
	@Override public Xowd_page_itm Read_data_to_page(Xowd_page_itm rdr_page) {
		Xow_ns ns = ns_mgr.Ids_get_or_null(rdr_page.Ns_id());
		if (ns == null) return null;	// NOTE: ns seems to "randomly" be null when threading during redlinks; guard against null; DATE:2014-01-03
		byte[] ttl_wo_ns = rdr_page.Ttl_page_db();
		rdr_page.Ttl_(ns, ttl_wo_ns);
		return (Xowd_page_itm)hash.Get_by(rdr_page.Ttl_full_db());
	}
}
class Xowd_page_tbl__ttl extends Xowd_page_tbl__in_wkr__base {
	private Ordered_hash hash; private int in_ns;
	@Override protected int Interval() {return 64;}	// NOTE: 96+ overflows; EX: w:Space_Liability_Convention; DATE:2013-10-24
	public void Init(Ordered_hash hash, int in_ns) {this.hash = hash; this.in_ns = in_ns;}
	@Override protected Criteria In_filter(Object[] part_ary) {
		int len = part_ary.length;
		Criteria[] crt_ary = new Criteria[len];
		String fld_ns = tbl.Fld_page_ns(); String fld_ttl = tbl.Fld_page_title();
		for (int i = 0; i < len; i++)
			crt_ary[i] = Criteria_.And(Db_crt_.eq_(fld_ns, in_ns), Db_crt_.eq_(fld_ttl, Bry_.Empty));
		return Criteria_.Or_many(crt_ary);
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xowd_page_itm page = (Xowd_page_itm)hash.Get_at(i);
			stmt.Val_int(in_ns);
			stmt.Val_bry_as_str(page.Ttl_page_db());
		}
	}
	@Override public Xowd_page_itm Read_data_to_page(Xowd_page_itm rdr_page) {return (Xowd_page_itm)hash.Get_by(rdr_page.Ttl_page_db());}
}
class Xowd_page_tbl__id extends Xowd_page_tbl__in_wkr__base {
	private List_adp list;		// list is original list of ids which may have dupes; needed to fill statement (which takes range of bgn - end); DATE:2013-12-08
	private Ordered_hash hash;	// hash is unique list of ids; needed for fetch from rdr (which indexes by id)
	public void Init(List_adp list, Ordered_hash hash) {this.list = list; this.hash = hash; this.Fill_idx_fields_only_(true);}
	@Override protected boolean		Show_progress() {return true;}
	@Override protected Criteria In_filter(Object[] part_ary) {
		return Db_crt_.in_(this.In_fld_name(), part_ary);
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Xowd_page_itm page = (Xowd_page_itm)list.Get_at(i);
			stmt.Val_int(page.Id());		
		}
	}
	@Override public Xowd_page_itm Read_data_to_page(Xowd_page_itm rdr_page) {return (Xowd_page_itm)hash.Get_by(rdr_page.Id_val());}
}
abstract class Xowd_page_tbl__in_wkr__base extends Db_in_wkr__base {
	protected Xowd_page_tbl tbl; private String tbl_name, fld_in_name;
	public String Tbl_name() {return tbl_name;}
	public void Ctor(Xowd_page_tbl tbl, String tbl_name, String fld_in_name) {this.tbl = tbl; this.tbl_name = tbl_name; this.fld_in_name = fld_in_name;}
	public String In_fld_name() {return  fld_in_name;}
	protected abstract Criteria In_filter(Object[] part_ary);
	public abstract Xowd_page_itm Read_data_to_page(Xowd_page_itm rdr_page);
	public boolean Fill_idx_fields_only() {return fill_idx_fields_only;} public void Fill_idx_fields_only_(boolean v) {fill_idx_fields_only = v;} private boolean fill_idx_fields_only;
	@Override protected Db_qry Make_qry(int bgn, int end) {
		Object[] part_ary = In_ary(end - bgn);
		return Db_qry_.select_cols_
		(	this.Tbl_name()
		, 	In_filter(part_ary)
		, 	fill_idx_fields_only ? tbl.Flds_select_idx() : tbl.Flds_select_all()
		)
		;
	}
	@Override protected void Read_data(Cancelable cancelable, Db_rdr rdr) {
		Xowd_page_itm temp = new Xowd_page_itm();
		while (rdr.Move_next()) {
			if (cancelable.Canceled()) return;
			if (fill_idx_fields_only)
				tbl.Read_page__idx(temp, rdr);
			else
				tbl.Read_page__all(temp, rdr);
			Xowd_page_itm page = Read_data_to_page(temp);
			if (page == null) continue; // page not found
			temp.Exists_(true);
			page.Copy(temp);
		}
	}
}
