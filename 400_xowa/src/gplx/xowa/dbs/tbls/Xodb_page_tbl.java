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
import gplx.dbs.*; import gplx.criterias.*; 
public class Xodb_page_tbl {
	public static final String Tbl_name = "page"
	, Fld_page_id = "page_id", Fld_page_ns = "page_namespace", Fld_page_title = "page_title"
	, Fld_page_is_redirect = "page_is_redirect", Fld_page_touched = "page_touched", Fld_page_len = "page_len"
	, Fld_page_random_int = "page_random_int", Fld_page_file_idx = "page_file_idx"
	, Fld_page_html_db_id = "page_html_db_id", Fld_page_redirect_id = "page_redirect_id";
	private static final String[] Select_by_id_flds__basic = new String[] {Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_touched, Fld_page_is_redirect, Fld_page_len, Fld_page_file_idx};
	private static final String[] Select_by_id_flds__hdump = new String[] {Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_touched, Fld_page_is_redirect, Fld_page_len, Fld_page_file_idx, Fld_page_html_db_id, Fld_page_redirect_id};
//		public boolean Select_by_id(Xodb_page rv, int page_id) {
//			Db_rdr rdr = Db_rdr_.Null; 
//			Db_stmt stmt = Db_stmt_.Null;
//			try {
//				stmt = Db_stmt_.new_select_as_rdr(provider, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_page_id), html_db_enabled ? Select_by_id_flds__hdump : Select_by_id_flds__basic));
//				rdr = stmt.Val_int_(page_id).Exec_select_as_rdr();
//				while (rdr.Move_next()) {
//					Read_page__all2(rv, rdr, html_db_enabled);
//					return true;
//				}
//			} finally {rdr.Close(); stmt.Rls();}
//			return false;		
//		}		
	private final Xow_ns_mgr ns_mgr; private final Xodb_ctx db_ctx;
	public Xodb_page_tbl(Xow_wiki wiki) {
		this.ns_mgr = wiki.Ns_mgr();
		this.db_ctx = wiki.Db_mgr().Db_ctx();
	}
	public void Html_db_enabled_(boolean v) {html_db_enabled = v;} private boolean html_db_enabled;
	public Db_provider Provider() {return provider;} public void Provider_(Db_provider provider) {this.provider = provider;} private Db_provider provider;
	public boolean Select_by_ttl(Xodb_page rv, Xow_ns ns, byte[] ttl) {
		Db_rdr rdr = Db_rdr_.Null; Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_as_rdr(provider, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_page_ns, Fld_page_title), html_db_enabled ? Select_by_id_flds__hdump : Select_by_id_flds__basic));
			rdr = stmt.Val_int_(ns.Id()).Val_str_(String_.new_utf8_(ttl)).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				Read_page__all2(rv, rdr, html_db_enabled);
				return true;
			}
		} finally {rdr.Close(); stmt.Rls();}
		return false;
	}
	public static void Read_page__all2(Xodb_page page, Db_rdr rdr, boolean html_db_enabled) {
		page.Id_			(rdr.Read_int(0));
		page.Ns_id_			(rdr.Read_int(1));
		page.Ttl_wo_ns_		(rdr.Read_bry_by_str(2));
		page.Modified_on_	(DateAdp_.parse_fmt(rdr.Read_str(3), Page_touched_fmt));
		page.Type_redirect_	(rdr.Read_byte(4) == 1);
		page.Text_len_		(rdr.Read_int(5));
		page.Text_db_id_	(rdr.Read_int(6));
		if (html_db_enabled) {
			page.Html_db_id_(rdr.Read_int(7));
			page.Redirect_id_(rdr.Read_int(8));
		}
	}
	public boolean Select_by_id(Xodb_page rv, int page_id) {
		DataRdr rdr = DataRdr_.Null; 
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_page_id));
			rdr = stmt
			.Val_int_(page_id)
			.Exec_select();
			while (rdr.MoveNextPeer()) {
				Read_page__all(rv, rdr, html_db_enabled);
				return true;
			}
		} finally {rdr.Rls(); stmt.Rls();}
		return false;		
	}
	public void Delete_all() {provider.Exec_qry(Db_qry_.delete_tbl_(Tbl_name));}		
	public Db_stmt Insert_stmt(Db_provider p) {
		return Db_stmt_.new_insert_(p, Tbl_name, html_db_enabled ? Flds_insert__html_y : Flds_insert__html_n);
	}
	public void Insert(Db_stmt stmt, int page_id, int ns_id, byte[] ttl_wo_ns, boolean redirect, DateAdp modified_on, int page_len, int random_int, int file_idx, int html_db_id) {
		stmt.Clear()
		.Val_int_(page_id)
		.Val_int_(ns_id)
		.Val_str_(String_.new_utf8_(ttl_wo_ns))
		.Val_byte_((byte)(redirect ? 1 : 0))
		.Val_str_(Xto_touched_str(modified_on))
		.Val_int_(page_len)
		.Val_int_(random_int)
		.Val_int_(file_idx);
		if (html_db_enabled)
			stmt.Val_int_(html_db_id);
		stmt.Exec_insert();
	}
	public int Select_id(int ns_id, byte[] ttl) {
		DataRdr rdr = DataRdr_.Null; 
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_page_ns, Fld_page_title), Fld_page_id);
			rdr = stmt
			.Val_int_(ns_id)
			.Val_str_by_bry_(ttl)
			.Exec_select();
			while (rdr.MoveNextPeer()) {
				return rdr.ReadInt(Fld_page_id);
			}
		} finally {rdr.Rls(); stmt.Rls();}
		return Xodb_mgr_sql.Page_id_null;
	}
	public DataRdr Select_all(Db_provider p) {
		Db_qry_select qry = Db_qry_select.new_().From_(Tbl_name).Cols_(Fld_page_id, Fld_page_title).OrderBy_asc_(Fld_page_id);
		return p.Exec_qry_as_rdr(qry);
	}
	private DataRdr Load_ttls_starting_with_rdr(int ns_id, byte[] ttl_frag, boolean include_redirects, int max_results, int min_page_len, int browse_len, boolean fwd, boolean search_suggest) {
		Criteria crt_ttl = fwd ? Db_crt_.mte_(Fld_page_title, String_.new_utf8_(ttl_frag)) : Db_crt_.lt_(Fld_page_title, String_.new_utf8_(ttl_frag));
		Criteria crt = Criteria_.And_many(Db_crt_.eq_(Fld_page_ns, ns_id), crt_ttl, Db_crt_.mte_(Fld_page_len, min_page_len));
		if (!include_redirects)
			crt = Criteria_.And(crt, Db_crt_.eq_(Fld_page_is_redirect, Byte_.Zero));
		String[] cols = search_suggest ? Flds_select_idx : html_db_enabled ? Flds_select_all__html_y : Flds_select_all__html_n;
		int limit = fwd ? max_results + 1 : max_results; // + 1 to get next item
		Db_qry_select select = Db_qry_.select_cols_(Tbl_name, crt, cols).Limit_(limit).OrderBy_(Fld_page_title, fwd);
		return select.Exec_qry_as_rdr(provider);
	}
	public void Load_ttls_for_all_pages(Cancelable cancelable, ListAdp rslt_list, Xodb_page rslt_nxt, Xodb_page rslt_prv, Int_obj_ref rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		DataRdr rdr = DataRdr_.Null;
		Xodb_page nxt_itm = null;
		int rslt_idx = 0;
		boolean max_val_check = max_results == Int_.MaxValue;
		try {
			rdr = Load_ttls_starting_with_rdr(ns.Id(), key, include_redirects, max_results, min_page_len, browse_len, true, true);
			while (rdr.MoveNextPeer()) {
				if (cancelable.Canceled()) return;
				Xodb_page page = new Xodb_page();
				Read_page__idx(page, rdr);
				if (max_val_check && !Bry_.HasAtBgn(page.Ttl_wo_ns(), key)) break;
				nxt_itm = page;
				if (rslt_idx == max_results) {}	// last item which is not meant for rslts, but only for nxt itm
				else {
					rslt_list.Add(page);
					++rslt_idx;
				}
			}
			if (rslt_nxt != null && nxt_itm != null)	// occurs when range is empty; EX: "Module:A" in simplewikibooks
				rslt_nxt.Copy(nxt_itm);
			if (fetch_prv_item) {						// NOTE: Special:AllPages passes in true, but Search_suggest passes in false
				if (cancelable.Canceled()) return;
				rdr = Load_ttls_starting_with_rdr(ns.Id(), key, include_redirects, max_results, min_page_len, browse_len, false, false);
				Xodb_page prv_itm = new Xodb_page();
				boolean found = false;
				while (rdr.MoveNextPeer()) {
					Read_page__all(prv_itm, rdr, html_db_enabled);
					found = true;
				}
				if (found)
					rslt_prv.Copy(prv_itm);
				else {	// at beginning of range, so no items found; EX: "Module:A" is search, but 1st Module is "Module:B"
					if (rslt_list.Count() > 0)	// use 1st item
						rslt_prv.Copy((Xodb_page)rslt_list.FetchAt(0));
				}
			}
		}
		finally {rdr.Rls();}
		rslt_count.Val_(rslt_idx);
	}
	public void Load_ttls_for_search_suggest(Cancelable cancelable, ListAdp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		String search_bgn = String_.new_utf8_(key);
		String search_end = String_.new_utf8_(gplx.intl.Utf8_.Increment_char_at_last_pos(key));
		Db_qry qry = Db_qry_sql.rdr_("SELECT page_id, page_namespace, page_title, page_len FROM page INDEXED BY page__title WHERE page_namespace = " + Int_.Xto_str(ns.Id()) + " AND page_title BETWEEN '" + search_bgn + "' AND '" + search_end + "' ORDER BY page_len DESC LIMIT " + Int_.Xto_str(max_results) + ";");
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = provider.Exec_qry_as_rdr(qry);
			while (rdr.MoveNextPeer()) {
				if (cancelable.Canceled()) return;
				Xodb_page page = new Xodb_page();
				Read_page__idx(page, rdr);
				rslt_list.Add(page);
			}
			rslt_list.SortBy(Xodb_page_sorter.TitleAsc);
		}
		finally {rdr.Rls();}
	}
	public boolean Select_by_id_list(Cancelable cancelable, ListAdp rv)						{return Select_by_id_list(cancelable, false, rv, 0, rv.Count());}
	public boolean Select_by_id_list(Cancelable cancelable, boolean skip_table_read, ListAdp rv)	{return Select_by_id_list(cancelable, skip_table_read, rv, 0, rv.Count());}
	public boolean Select_by_id_list(Cancelable cancelable, boolean skip_table_read, ListAdp rv, int bgn, int end) {
		Xodb_page[] page_ary = (Xodb_page[])rv.Xto_ary(Xodb_page.class);
		int len = page_ary.length; if (len == 0) return false;
		OrderedHash hash = OrderedHash_.new_();
		for (int i = 0; i < len; i++) {
			if (cancelable.Canceled()) return false;
			Xodb_page p = page_ary[i];
			if (!hash.Has(p.Id_val()))	// NOTE: must check if file already exists b/c dynamicPageList currently allows dupes; DATE:2013-07-22
				hash.Add(p.Id_val(), p);
		}
		Xodb_in_wkr_page_id wkr = new Xodb_in_wkr_page_id();
		wkr.Init(rv, hash);
		wkr.Select_in(provider, cancelable, db_ctx, bgn, end);
		return true;		
	}
	public byte[] Select_random(Xow_ns ns) {// ns should be ns_main
		int random_int = RandomAdp_.new_().Next(ns.Count());
		DataRdr rdr = DataRdr_.Null;
		Db_stmt stmt = Db_stmt_.Null;
		byte[] rv = null;
		try {
			stmt = Db_stmt_.new_select_(provider, Tbl_name, String_.Ary(Fld_page_ns, Fld_page_random_int), Fld_page_title);
			rdr = stmt
			.Val_int_(ns.Id())
			.Val_int_(random_int)
			.Exec_select();
			if (rdr.MoveNextPeer()) {
				rv = rdr.ReadBryByStr(Fld_page_title);
			}		
		} finally {rdr.Rls(); stmt.Rls();}
		return rv;
	}
	public void Select_by_search(Cancelable cancelable, ListAdp rv, byte[] search, int results_max) {
		if (Bry_.Len_eq_0(search)) return;	// do not allow empty search
		Criteria crt = gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_page_ns, Xow_ns_.Id_main), Db_crt_.like_(Fld_page_title, ""));
		Db_qry_select qry = Db_qry_.select_().From_(Tbl_name).Cols_(Fld_page_id, Fld_page_len, Fld_page_ns, Fld_page_title).Where_(crt);	// NOTE: use fields from main index only
		DataRdr rdr = DataRdr_.Null; 
		Db_stmt stmt = Db_stmt_.Null;
		search = Bry_.Replace(search, Byte_ascii.Asterisk, Byte_ascii.Percent);
		try {
			stmt = provider.Prepare(qry);				
			rdr = stmt.Clear().Val_int_(Xow_ns_.Id_main).Val_str_by_bry_(search).Exec_select();
			while (rdr.MoveNextPeer()) {
				if (cancelable.Canceled()) return;
				Xodb_page page = new Xodb_page();
				page.Id_			(rdr.ReadInt(Fld_page_id));
				page.Ns_id_			(rdr.ReadInt(Fld_page_ns));
				page.Ttl_wo_ns_		(rdr.ReadBryByStr(Fld_page_title));
				page.Text_len_		(rdr.ReadInt(Fld_page_len));
				rv.Add(page);
			}
		}	finally {rdr.Rls(); stmt.Rls();}
	}
	public static byte[] Find_search_end(byte[] orig) {
		byte[] rv = Bry_.Copy(orig);
		int rv_len = rv.length;
		int increment_pos = rv[rv_len - 1] == Byte_ascii.Percent ? rv_len - 2 : rv_len - 1;	// increment last char, unless it is %; if %, increment one before it
		return Bry_.Increment_last(rv, increment_pos);
	}
	public Db_stmt Select_for_parse_all_stmt(Db_provider p, int limit, byte redirect) {
		Criteria crt = gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_page_ns, -1), Db_crt_.mt_(Fld_page_title, ""));
		if (redirect != Bool_.__byte)
			crt = gplx.criterias.Criteria_.And(crt, Db_crt_.eq_(Fld_page_is_redirect, redirect));
		Db_qry_select qry = Db_qry_.select_().From_(Tbl_name).Cols_(html_db_enabled ? Flds_select_all__html_y : Flds_select_all__html_n)
			.Where_(crt)
			.Limit_(limit);
		return p.Prepare(qry);
	}
	public void Select_for_parse_all(Cancelable cancelable, OrderedHash rv, Db_stmt stmt, int ns, byte[] ttl, byte redirect) {
		String ttl_str = String_.new_utf8_(ttl);
		DataRdr rdr = DataRdr_.Null; 
		try {
			stmt.Clear().Val_int_(ns).Val_str_(ttl_str);
			if (redirect != Bool_.__byte) stmt.Val_byte_(redirect);
			rdr = stmt.Exec_select();
			while (rdr.MoveNextPeer()) {
				if (cancelable.Canceled()) return;
				Xodb_page page = new Xodb_page();
				Read_page__all(page, rdr, html_db_enabled);
				rv.Add(page.Id_val(), page);
			}
		}	finally {rdr.Rls();}
	}
	public void Select_by_ttl_in(Cancelable cancelable, OrderedHash rv, Xodb_ctx db_ctx, boolean fill_idx_fields_only, int bgn, int end) {
		Xodb_in_wkr_page_title_ns wkr = new Xodb_in_wkr_page_title_ns();
		wkr.Fill_idx_fields_only_(fill_idx_fields_only);
		wkr.Init(ns_mgr, rv);
		wkr.Select_in(provider, cancelable, db_ctx, bgn, end);
	}
	public void Select_by_ttl_in(Cancelable cancelable, OrderedHash rv, int ns_id, int bgn, int end) {
		Xodb_in_wkr_page_title wkr = new Xodb_in_wkr_page_title();
		wkr.Init(rv, ns_id);
		wkr.Select_in(provider, cancelable, db_ctx, bgn, end);
	}
	public void Update_html_db_id(int page_id, int html_db_id) {
		if (!html_db_enabled) throw Err_.new_("html_db not enabled");
		Db_stmt stmt = Db_stmt_.new_update_(provider, Tbl_name, String_.Ary(Fld_page_id), Fld_page_html_db_id);
		stmt.Val_int_(html_db_id).Val_int_(page_id).Exec_update();
	}
	private static final String Page_touched_fmt = "yyyyMMddHHmmss";
	private static String Xto_touched_str(DateAdp v) {return v.XtoStr_fmt(Page_touched_fmt);}
	public static final String[] 
	  Flds_insert__html_n		= String_.Ary(Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_is_redirect, Fld_page_touched, Fld_page_len, Fld_page_random_int, Fld_page_file_idx)
	, Flds_insert__html_y		= String_.Ary(Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_is_redirect, Fld_page_touched, Fld_page_len, Fld_page_random_int, Fld_page_file_idx, Fld_page_html_db_id, Fld_page_redirect_id) 
	, Flds_select_all__html_n	= String_.Ary(Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_touched, Fld_page_is_redirect, Fld_page_len, Fld_page_file_idx)
	, Flds_select_all__html_y	= String_.Ary(Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_touched, Fld_page_is_redirect, Fld_page_len, Fld_page_file_idx, Fld_page_html_db_id, Fld_page_redirect_id)
	, Flds_select_idx			= String_.Ary(Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_len)
	;
	public static final boolean Load_idx_flds_only_y = true;
	public static void Read_page__all(Xodb_page page, DataRdr rdr, boolean html_db_enabled) {
		page.Id_			(rdr.ReadInt(Fld_page_id));
		page.Ns_id_			(rdr.ReadInt(Fld_page_ns));
		page.Ttl_wo_ns_		(rdr.ReadBryByStr(Fld_page_title));
		page.Modified_on_	(DateAdp_.parse_fmt(rdr.ReadStr(Fld_page_touched), Page_touched_fmt));
		page.Type_redirect_	(rdr.ReadByte(Fld_page_is_redirect) == 1);
		page.Text_len_		(rdr.ReadInt(Fld_page_len));
		page.Text_db_id_	(rdr.ReadInt(Fld_page_file_idx));
		if (html_db_enabled) {
			page.Html_db_id_(rdr.ReadInt(Fld_page_html_db_id));
			page.Redirect_id_(rdr.ReadInt(Fld_page_redirect_id));
		}
	}
	public static void Read_page__idx(Xodb_page page, DataRdr rdr) {
		page.Id_			(rdr.ReadInt(Fld_page_id));
		page.Ns_id_			(rdr.ReadInt(Fld_page_ns));
		page.Ttl_wo_ns_		(rdr.ReadBryByStr(Fld_page_title));
		page.Text_len_		(rdr.ReadInt(Fld_page_len));
	}
}
