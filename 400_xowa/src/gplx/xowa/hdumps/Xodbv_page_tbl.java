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
package gplx.xowa.hdumps; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
public class Xodbv_page_tbl {
	public static final String Tbl_name = "page"
	, Fld_page_id = "page_id", Fld_page_ns = "page_namespace", Fld_page_title = "page_title"
	, Fld_page_is_redirect = "page_is_redirect", Fld_page_touched = "page_touched", Fld_page_len = "page_len"
	, Fld_page_random_int = "page_random_int", Fld_page_file_idx = "page_file_idx"
	, Fld_page_html_db_id = "page_html_db_id";
	private static final String[] Select_by_id_flds__hdump = new String[] {Fld_page_id, Fld_page_ns, Fld_page_title, Fld_page_touched, Fld_page_is_redirect, Fld_page_len, Fld_page_file_idx, Fld_page_html_db_id};
	public boolean Select_by_ttl(Xodb_page rv, Db_provider provider, Xow_ns ns, byte[] ttl) {
		Db_rdr rdr = Db_rdr_.Null; Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_select_as_rdr(provider, Db_qry__select_in_tbl.new_(Tbl_name, String_.Ary(Fld_page_ns, Fld_page_title), Select_by_id_flds__hdump));
			rdr = stmt.Val_int_(ns.Id()).Val_str_(String_.new_utf8_(ttl)).Exec_select_as_rdr();
			if (rdr.Move_next()) {
				Read_page__all(rv, rdr);
				return true;
			}
		} finally {rdr.Close(); stmt.Rls();}
		return false;
	}
	public static void Read_page__all(Xodb_page page, Db_rdr rdr) {
		page.Id_			(rdr.Read_int(0));
		page.Ns_id_			(rdr.Read_int(1));
		page.Ttl_wo_ns_		(rdr.Read_bry_by_str(2));
		page.Modified_on_	(DateAdp_.parse_fmt(rdr.Read_str(3), Page_touched_fmt));
		page.Type_redirect_	(rdr.Read_byte(4) == 1);
		page.Text_len_		(rdr.Read_int(5));
		page.Db_file_idx_	(rdr.Read_int(6));
		page.Html_db_id_	(rdr.Read_int(7));
	}
	private static final String Page_touched_fmt = "yyyyMMddHHmmss";
}
