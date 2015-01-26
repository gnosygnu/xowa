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
package gplx.xowa2.wikis.data.tbls; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.wikis.*; import gplx.xowa2.wikis.data.*;
import gplx.dbs.*; import gplx.xowa.*;
public class Xow_page_regy_tbl implements Db_conn_itm {
	private final Db_conn conn; private Db_stmt stmt_select_by_ttl, stmt_select_by_id, stmt_insert;
	public Xow_page_regy_tbl(boolean version_is_1, Db_url url) {
		conn = Db_conn_pool.I.Get_or_new(url);
		conn.Itms_add(this);
		Ctor_for_meta(version_is_1);
	}
	public void Conn_term() {
		if (stmt_select_by_ttl	!= null)	{stmt_select_by_ttl.Rls();	stmt_select_by_ttl = null;}
		if (stmt_select_by_id	!= null)	{stmt_select_by_id.Rls();	stmt_select_by_id = null;}
		if (stmt_insert			!= null)	{stmt_insert.Rls();			stmt_insert = null;}
	}
	public boolean Select_by_ttl(Xodb_page rv, Xow_ns ns, byte[] ttl) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			if (stmt_select_by_ttl == null) stmt_select_by_ttl = conn.New_stmt_select_all_where(tbl_name, Flds.Xto_str_ary(), Fld_page_ns, Fld_page_title);
			rdr = stmt_select_by_ttl.Clear().Val_int(ns.Id()).Val_str(String_.new_utf8_(ttl)).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		}
		catch (Exception exc) {stmt_select_by_ttl = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
		finally {rdr.Rls();}
		return false;
	}
	public boolean Select_by_id(Xodb_page rv, int page_id) {
		Db_rdr rdr = Db_rdr_.Null;
		try {
			if (stmt_select_by_id == null) stmt_select_by_id = conn.New_stmt_select_all_where(tbl_name, Flds_select(), Fld_page_id);
			rdr = stmt_select_by_id.Clear().Val_int(page_id).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		}
		catch (Exception exc) {stmt_select_by_id = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
		finally {rdr.Rls();}
		return false;
	}
	public void Insert(int page_id, int ns_id, byte[] ttl_wo_ns, boolean page_is_redirect, DateAdp modified_on, int page_len, int random_int, int file_idx, int html_db_id) {
		try {
			if (stmt_insert == null) stmt_insert = conn.New_stmt_insert(tbl_name, Flds.Xto_str_ary());
			stmt_insert.Clear()
			.Val_int(page_id)
			.Val_int(ns_id)
			.Val_str(String_.new_utf8_(ttl_wo_ns))
			.Val_byte((byte)(page_is_redirect ? 1 : 0))
			.Val_str(modified_on.XtoStr_fmt(Page_touched_fmt))
			.Val_int(page_len)
			.Val_int(random_int)
			.Val_int(file_idx)
			.Exec_insert();
		}
		catch (Exception exc) {stmt_select_by_ttl = null; throw Err_.err_(exc, "stmt failed");} // must reset stmt, else next call will fail
		finally {}
	}
	private void Read_page__all(Xodb_page page, Db_rdr rdr) {
		page.Id_			(rdr.Read_int(Fld_page_id));
		page.Ns_id_			(rdr.Read_int(Fld_page_ns));
		page.Ttl_wo_ns_		(rdr.Read_bry_by_str(Fld_page_title));
		page.Modified_on_	(DateAdp_.parse_fmt(rdr.Read_str(Fld_page_touched), Page_touched_fmt));
		page.Type_redirect_	(rdr.Read_bool_by_byte(Fld_page_is_redirect));
		page.Text_len_		(rdr.Read_int(Fld_page_len));
		page.Text_db_id_	(rdr.Read_int(Fld_page_text_db_id));
		page.Html_db_id_	(rdr.Read_int(Fld_page_html_db_id));
		page.Redirect_id_	(rdr.Read_int(Fld_page_redirect_id));
	}
	private static final String Page_touched_fmt = "yyyyMMddHHmmss";
	private String tbl_name = "wiki_page_regy";
	private String Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_is_redirect, Fld_page_touched, Fld_page_len, Fld_page_random_int, Fld_page_text_db_id, Fld_page_html_db_id, Fld_page_redirect_id;
	private final Db_meta_fld_list Flds = Db_meta_fld_list.new_();
	private void Ctor_for_meta(boolean version_is_1) {
		String page_text_db_id_key = "page_text_db_id";
		if (version_is_1) {
			tbl_name = "page";
			page_text_db_id_key = "page_file_idx";
		}
		Fld_page_id				= Flds.Add_int_pkey("page_id");			// int(10); unsigned -- MW:same
		Fld_page_ns				= Flds.Add_int("page_namespace");		// int(11);          -- MW:same
		Fld_page_title			= Flds.Add_str("page_title", 255);		// varbinary(255);   -- MW:blob
		Fld_page_is_redirect	= Flds.Add_int("page_is_redirect");		// tinyint(3);       -- MW:same
		Fld_page_touched		= Flds.Add_str("page_touched", 14);		// binary(14);       -- MW:blob; NOTE: should be revision!rev_timestamp, but needs extra join
		Fld_page_len			= Flds.Add_int("page_len");				// int(10); unsigned -- MW:same except NULL REF: WikiPage.php!updateRevisionOn;"
		Fld_page_random_int		= Flds.Add_int("page_random_int");		// MW:XOWA
		Fld_page_text_db_id		= Flds.Add_int(page_text_db_id_key);	// MW:XOWA
		Fld_page_html_db_id		= Flds.Add_int("page_html_db_id");		// MW:XOWA
		Fld_page_redirect_id	= Flds.Add_int("page_redirect_id");		// MW:XOWA
	}
	private String[] Flds_select() {return String_.Ary(Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_touched, Fld_page_is_redirect, Fld_page_len, Fld_page_text_db_id, Fld_page_html_db_id, Fld_page_redirect_id);}
	public Db_meta_tbl new_meta() {
		return Db_meta_tbl.new_(tbl_name, Flds.Xto_fld_ary()
		, Db_meta_idx.new_normal(tbl_name, "title"		, Fld_page_ns, Fld_page_title, Fld_page_id, Fld_page_len, Fld_page_is_redirect)
		, Db_meta_idx.new_normal(tbl_name, "random"		, Fld_page_ns, Fld_page_random_int)
		);
	} 
}
