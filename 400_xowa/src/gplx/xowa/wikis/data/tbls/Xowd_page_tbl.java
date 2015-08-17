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
import gplx.core.primitives.*; import gplx.core.criterias.*;
import gplx.dbs.*; import gplx.xowa.*; import gplx.xowa.dbs.*; import gplx.dbs.qrys.*;
public class Xowd_page_tbl implements RlsAble {
	private final String tbl_name = "page";
	private String fld_id, fld_ns, fld_title, fld_is_redirect, fld_touched, fld_len, fld_random_int, fld_text_db_id, fld_html_db_id, fld_redirect_id;
	private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final Db_conn conn; private Db_stmt stmt_select_all_by_ttl, stmt_select_all_by_id, stmt_select_id_by_ttl, stmt_insert; private boolean hdump_enabled;
	private final String[] flds_select_all, flds_select_idx;
	public String Tbl_name()					{return tbl_name;}
	public String Fld_page_id()					{return fld_id;}
	public String Fld_page_ns()					{return fld_ns;}
	public String Fld_page_title()				{return fld_title;}
	public String Fld_page_len()				{return fld_len;}
	public String Fld_html_db_id()				{return fld_html_db_id;}
	public String Fld_redirect_id()				{return fld_redirect_id;}
	public String[] Flds_select_idx()			{return flds_select_idx;}
	public String[] Flds_select_all()			{return flds_select_all;}
	public Db_conn Conn() {return conn;}
	public Xowd_page_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		String fld_text_db_id_name = "";
		if (schema_is_1)	{fld_text_db_id_name = "page_file_idx";}
		else				{fld_text_db_id_name = "page_text_db_id";}
		fld_id				= flds.Add_int_pkey("page_id");				// int(10); unsigned -- MW:same
		fld_ns				= flds.Add_int("page_namespace");		// int(11);          -- MW:same
		fld_title			= flds.Add_str("page_title", 255);			// varbinary(255);   -- MW:blob
		fld_is_redirect		= flds.Add_int("page_is_redirect");			// tinyint(3);       -- MW:same
		fld_touched			= flds.Add_str("page_touched", 14);			// binary(14);       -- MW:blob; NOTE: should be revision!rev_timestamp, but needs extra join
		fld_len				= flds.Add_int("page_len");					// int(10); unsigned -- MW:same except NULL REF: WikiPage.php!updateRevisionOn;"
		fld_random_int		= flds.Add_int("page_random_int");			// MW:XOWA
		fld_text_db_id		= flds.Add_int(fld_text_db_id_name);		// MW:XOWA
		Hdump_enabled_(!schema_is_1);
		flds_select_all	= String_.Ary_wo_null(fld_id, fld_ns, fld_title, fld_touched, fld_is_redirect, fld_len, fld_text_db_id, fld_html_db_id, fld_redirect_id);
		flds_select_idx	= String_.Ary(fld_ns, fld_title, fld_id, fld_len);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds.To_fld_ary()));}
	public void Insert_bgn() {conn.Txn_bgn("schema__page__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int page_id, int ns_id, byte[] ttl_wo_ns, boolean page_is_redirect, DateAdp modified_on, int page_len, int random_int, int text_db_id, int html_db_id) {
		stmt_insert.Clear()
		.Val_int(fld_id, page_id)
		.Val_int(fld_ns, ns_id)
		.Val_bry_as_str(fld_title, ttl_wo_ns)
		.Val_bool_as_byte(fld_is_redirect, page_is_redirect)
		.Val_str(fld_touched, modified_on.XtoStr_fmt(Page_touched_fmt))
		.Val_int(fld_len, page_len)
		.Val_int(fld_random_int, random_int)
		.Val_int(fld_text_db_id, text_db_id)
		.Val_int(fld_html_db_id, html_db_id)
		.Val_int(fld_redirect_id, -1)
		.Exec_insert();
	}
	public void Hdump_enabled_(boolean v) {
		this.hdump_enabled = v;
		if (v) {
			fld_html_db_id		= flds.Add_int_dflt("page_html_db_id", -1);		// MW:XOWA
			fld_redirect_id		= flds.Add_int_dflt("page_redirect_id", -1);	// MW:XOWA
		}
		else {
			fld_html_db_id		= Db_meta_fld.Key_null;
			fld_redirect_id		= Db_meta_fld.Key_null;
		}
	}
	public boolean Select_by_ttl(Xowd_page_itm rv, Xow_ns ns, byte[] ttl) {
		if (stmt_select_all_by_ttl == null) stmt_select_all_by_ttl = conn.Stmt_select(tbl_name, flds, String_.Ary(fld_ns, fld_title));
		Db_rdr rdr = stmt_select_all_by_ttl.Clear().Crt_int(fld_ns, ns.Id()).Crt_bry_as_str(fld_title, ttl).Exec_select__rls_manual();
		try {
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		}
		finally {rdr.Rls();}
		return false;
	}
	public boolean Select_by_id(Xowd_page_itm rv, int page_id) {
		if (stmt_select_all_by_id == null) stmt_select_all_by_id = conn.Stmt_select(tbl_name, flds_select_all, fld_id);
		Db_rdr rdr = stmt_select_all_by_id.Clear().Crt_int(fld_id, page_id).Exec_select__rls_manual();
		try {
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		}
		finally {rdr.Rls();}
		return false;
	}
	public Db_rdr Select_all() {
		Db_qry__select_cmd qry = Db_qry__select_cmd.new_().From_(tbl_name).Cols_(fld_id, fld_title).OrderBy_asc_(fld_id);
		return conn.Stmt_new(qry).Exec_select__rls_auto();
	}
	public int Select_id(int ns_id, byte[] ttl) {
		if (stmt_select_id_by_ttl == null) stmt_select_id_by_ttl = conn.Stmt_select(tbl_name, flds_select_all, fld_ns, fld_title);
		Db_rdr rdr = stmt_select_id_by_ttl.Clear().Crt_int(fld_ns, ns_id).Crt_bry_as_str(fld_title, ttl).Exec_select__rls_manual(); 
		try {
			return rdr.Move_next() ? rdr.Read_int(fld_id) : Xowd_page_itm.Id_null;
		}	finally {rdr.Rls();}
	}
	public void Select_in__ttl(Cancelable cancelable, Ordered_hash rv, int ns_id, int bgn, int end) {
		Xowd_page_tbl__ttl wkr = new Xowd_page_tbl__ttl();
		wkr.Ctor(this, tbl_name, fld_title);
		wkr.Init(rv, ns_id);
		wkr.Select_in(cancelable, conn, bgn, end);
	}
	public void Select_in__ns_ttl(Cancelable cancelable, Ordered_hash rv, Xow_ns_mgr ns_mgr, boolean fill_idx_fields_only, int bgn, int end) {
		Xowd_page_tbl__ttl_ns wkr = new Xowd_page_tbl__ttl_ns();
		wkr.Fill_idx_fields_only_(fill_idx_fields_only);
		wkr.Ctor(this, tbl_name, fld_title);
		wkr.Init(ns_mgr, rv);
		wkr.Select_in(cancelable, conn, bgn, end);
	}
	public boolean Select_in__id(Cancelable cancelable, List_adp rv)						{return Select_in__id(cancelable, false, rv, 0, rv.Count());}
	public boolean Select_in__id(Cancelable cancelable, boolean skip_table_read, List_adp rv)	{return Select_in__id(cancelable, skip_table_read, rv, 0, rv.Count());}
	public boolean Select_in__id(Cancelable cancelable, boolean skip_table_read, List_adp rv, int bgn, int end) {
		Xowd_page_itm[] page_ary = (Xowd_page_itm[])rv.To_ary(Xowd_page_itm.class);
		int len = page_ary.length; if (len == 0) return false;
		Ordered_hash hash = Ordered_hash_.new_();
		for (int i = 0; i < len; i++) {
			if (cancelable.Canceled()) return false;
			Xowd_page_itm p = page_ary[i];
			if (!hash.Has(p.Id_val()))	// NOTE: must check if file already exists b/c dynamicPageList currently allows dupes; DATE:2013-07-22
				hash.Add(p.Id_val(), p);
		}
		hash.Sort_by(Xowd_page_itm_sorter.IdAsc);	// sort by ID to reduce disk thrashing; DATE:2015-03-31
		conn.Txn_bgn("schema__page__select_in");
		try {
			Xowd_page_tbl__id wkr = new Xowd_page_tbl__id();
			wkr.Ctor(this, tbl_name, fld_id);
			wkr.Init(rv, hash);
			wkr.Select_in(cancelable, conn, bgn, end);
		} finally {conn.Txn_end();}
		return true;		
	}
	public byte[] Select_random(Xow_ns ns) {// ns should be ns_main
		int random_int = RandomAdp_.new_().Next(ns.Count());
		Db_rdr rdr = conn.Stmt_select(tbl_name, String_.Ary(fld_title), fld_ns, fld_random_int)
				.Crt_int(fld_ns, ns.Id()).Crt_int(fld_random_int, random_int)
				.Exec_select__rls_auto();
		try {					
			return rdr.Move_next() ? rdr.Read_bry_by_str(fld_title) : null;
		}	finally {rdr.Rls();}
	}
	public void Select_by_search(Cancelable cancelable, List_adp rv, byte[] search, int results_max) {
		if (Bry_.Len_eq_0(search)) return;	// do not allow empty search
		Criteria crt = Criteria_.And_many(Db_crt_.eq_(fld_ns, Xow_ns_.Id_main), Db_crt_.like_(fld_title, ""));
		Db_qry__select_cmd qry = Db_qry_.select_().From_(tbl_name).Cols_(fld_id, fld_len, fld_ns, fld_title).Where_(crt);	// NOTE: use fields from main index only
		search = Bry_.Replace(search, Byte_ascii.Star, Byte_ascii.Percent);
		Db_rdr rdr = conn.Stmt_new(qry).Clear().Crt_int(fld_ns, Xow_ns_.Id_main).Val_bry_as_str(fld_title, search).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				if (cancelable.Canceled()) return;
				Xowd_page_itm page = new Xowd_page_itm();
				page.Id_			(rdr.Read_int(fld_id));
				page.Ns_id_			(rdr.Read_int(fld_ns));
				page.Ttl_page_db_	(rdr.Read_bry_by_str(fld_title));
				page.Text_len_		(rdr.Read_int(fld_len));
				rv.Add(page);
			}
		}	finally {rdr.Rls();}
	}
	public void Select_for_search_suggest(Cancelable cancelable, List_adp rslt_list, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		String search_bgn = String_.new_u8(key);
		String search_end = String_.new_u8(gplx.intl.Utf8_.Increment_char_at_last_pos(key));
		String sql = String_.Format
		( "SELECT {0}, {1}, {2}, {3} FROM {4} INDEXED BY {4}__title WHERE {1} = {5} AND {2} BETWEEN '{6}' AND '{7}' ORDER BY {3} DESC LIMIT {8};"
		, fld_id, fld_ns, fld_title, fld_len
		, tbl_name
		, Int_.Xto_str(ns.Id()), search_bgn, search_end, Int_.Xto_str(max_results)
		);
		Db_qry qry = Db_qry_sql.rdr_(sql);
		Db_rdr rdr = conn.Stmt_new(qry).Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				if (cancelable.Canceled()) return;
				Xowd_page_itm page = new Xowd_page_itm();
				Read_page__idx(page, rdr);
				rslt_list.Add(page);
			}
			rslt_list.Sort_by(Xowd_page_itm_sorter.TitleAsc);
		}
		finally {rdr.Rls();}
	}
	private Db_rdr Load_ttls_starting_with_rdr(int ns_id, byte[] ttl_frag, boolean include_redirects, int max_results, int min_page_len, int browse_len, boolean fwd, boolean search_suggest) {
		String ttl_frag_str = String_.new_u8(ttl_frag);
		Criteria crt_ttl = fwd ? Db_crt_.mte_(fld_title, ttl_frag_str) : Db_crt_.lt_(fld_title, ttl_frag_str);
		Criteria crt = Criteria_.And_many(Db_crt_.eq_(fld_ns, ns_id), crt_ttl, Db_crt_.mte_(fld_len, min_page_len));
		if (!include_redirects)
			crt = Criteria_.And(crt, Db_crt_.eq_(fld_is_redirect, Byte_.Zero));
		String[] cols = search_suggest 
			? flds_select_idx 
			: flds_select_all 
			;
		int limit = fwd ? max_results + 1 : max_results; // + 1 to get next item
		Db_qry__select_cmd qry = Db_qry_.select_cols_(tbl_name, crt, cols).Limit_(limit).OrderBy_(fld_title, fwd);
		Db_stmt stmt = conn.Stmt_new(qry).Crt_int(fld_ns, ns_id).Crt_str(fld_title, ttl_frag_str).Crt_int(fld_len, min_page_len);
		if (!include_redirects)
			stmt.Crt_bool_as_byte(fld_is_redirect, include_redirects);
		return stmt.Exec_select__rls_auto();
	}
	public void Select_for_special_all_pages(Cancelable cancelable, List_adp rslt_list, Xowd_page_itm rslt_nxt, Xowd_page_itm rslt_prv, Int_obj_ref rslt_count, Xow_ns ns, byte[] key, int max_results, int min_page_len, int browse_len, boolean include_redirects, boolean fetch_prv_item) {
		Xowd_page_itm nxt_itm = null;
		int rslt_idx = 0;
		boolean max_val_check = max_results == Int_.MaxValue;
		Db_rdr rdr = Load_ttls_starting_with_rdr(ns.Id(), key, include_redirects, max_results, min_page_len, browse_len, true, true);
		try {
			while (rdr.Move_next()) {
				if (cancelable.Canceled()) return;
				Xowd_page_itm page = new Xowd_page_itm();
				Read_page__idx(page, rdr);
				if (max_val_check && !Bry_.Has_at_bgn(page.Ttl_page_db(), key)) break;
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
				Xowd_page_itm prv_itm = new Xowd_page_itm();
				boolean found = false;
				while (rdr.Move_next()) {
					Read_page__all(prv_itm, rdr);
					found = true;
				}
				if (found)
					rslt_prv.Copy(prv_itm);
				else {	// at beginning of range, so no items found; EX: "Module:A" is search, but 1st Module is "Module:B"
					if (rslt_list.Count() > 0)	// use 1st item
						rslt_prv.Copy((Xowd_page_itm)rslt_list.Get_at(0));
				}
			}
		}
		finally {rdr.Rls();}
		rslt_count.Val_(rslt_idx);
	}
	public void Read_page__idx(Xowd_page_itm page, Db_rdr rdr) {
		page.Id_			(rdr.Read_int(fld_id));
		page.Ns_id_			(rdr.Read_int(fld_ns));
		page.Ttl_page_db_	(rdr.Read_bry_by_str(fld_title));
		page.Text_len_		(rdr.Read_int(fld_len));
	}
	public void Read_page__all(Xowd_page_itm page, Db_rdr rdr) {
		int html_db_id = -1, redirected_id = -1;
		if (hdump_enabled) {
			html_db_id = rdr.Read_int(fld_html_db_id);
			redirected_id = rdr.Read_int(fld_redirect_id);
		}
		page.Init_by_sql
		( rdr.Read_int(fld_id)
		, rdr.Read_int(fld_ns)
		, rdr.Read_bry_by_str(fld_title)
		, DateAdp_.parse_fmt(rdr.Read_str(fld_touched), Page_touched_fmt)
		, rdr.Read_bool_by_byte(fld_is_redirect)
		, rdr.Read_int(fld_len)
		, rdr.Read_int(fld_text_db_id)
		, html_db_id
		, redirected_id
		);
	}
	public void Update__html_db_id(int page_id, int html_db_id) {
		Db_stmt stmt = conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_html_db_id);
		stmt.Val_int(fld_html_db_id, html_db_id).Crt_int(fld_id, page_id).Exec_update();
	}
	public void Update__ns__ttl(int page_id, int trg_ns, byte[] trg_ttl) {
		for (int i = 0; i < 2; ++i) {
			try {
				conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_ns, fld_title)
					.Val_int(fld_ns, trg_ns).Val_bry_as_str(fld_title, trg_ttl)
					.Crt_int(fld_id, page_id)
					.Exec_update();
				break;
			} catch (Exception exc) {
				if (String_.Has(Err_.Message_gplx_full(exc), "columns page_namespace, page_random_int are not unique")) {	// HACK: terrible hack, but moving pages across ns will break UNIQUE index
					conn.Exec_sql_args("DROP INDEX {0}__name_random;", tbl_name); // is UNIQUE by default
					conn.Exec_sql_args("CREATE INDEX {0}__name_random ON {0} ({1}, {2});", tbl_name, fld_ns, fld_random_int);
				}
			}
		}
	}
	public void Update__redirect__modified(int page_id, boolean redirect, DateAdp modified) {
		conn.Stmt_update(tbl_name, String_.Ary(fld_id), fld_is_redirect, fld_touched)
			.Crt_int(fld_id, page_id)
			.Val_int(fld_is_redirect, redirect ? 1 : 0).Val_str(fld_touched, modified.XtoStr_fmt(Page_touched_fmt))
			.Exec_update()
			;
	}
	public void Create_index() {
		conn.Ddl_create_idx(Xoa_app_.Usr_dlg()
		, Db_meta_idx.new_normal_by_tbl(tbl_name, "title"		, fld_ns, fld_title, fld_id, fld_len, fld_is_redirect)
		, Db_meta_idx.new_normal_by_tbl(tbl_name, "random"		, fld_ns, fld_random_int)
		);
	}
	public void Rls() {
		stmt_select_all_by_ttl = Db_stmt_.Rls(stmt_select_all_by_ttl);
		stmt_select_all_by_id = Db_stmt_.Rls(stmt_select_all_by_id);
		stmt_select_id_by_ttl = Db_stmt_.Rls(stmt_select_id_by_ttl);
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	private static final String Page_touched_fmt = "yyyyMMddHHmmss";
}
