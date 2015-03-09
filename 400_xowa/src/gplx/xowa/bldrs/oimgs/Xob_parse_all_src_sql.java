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
package gplx.xowa.bldrs.oimgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.dbs.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.dbs.tbls.*;
class Xob_dump_src_ttl implements Xob_parse_all_db {
	private Xodb_mgr_sql db_mgr; private Db_stmt page_stmt; private Xowd_db_file[] text_files_ary; private int text_files_len; private byte redirect;
	public Xob_dump_src_ttl Init(Xowe_wiki wiki, int limit, byte redirect) {
		this.db_mgr = wiki.Db_mgr_as_sql(); this.redirect = redirect;
		page_stmt = db_mgr.Tbl_page().Select_for_parse_all_stmt(db_mgr.Core_data_mgr().Conn_core(), limit, redirect);
		text_files_ary = Init_text_files_ary(db_mgr.Core_data_mgr());
		text_files_len = text_files_ary.length;
		return this;
	}
	public void Fetch_next(OrderedHash hash, int ns_id, byte[] ttl) {
		Cancelable cancelable = Cancelable_.Never;
		db_mgr.Tbl_page().Select_for_parse_all(cancelable, hash, page_stmt, ns_id, ttl, redirect);
		for (int i = 0; i < text_files_len; i++) {
			Xowd_db_file text_file = text_files_ary[i];
			db_mgr.Tbl_text().Select_in(cancelable, text_file, hash);
		}
	}
	public static Xowd_db_file[] Init_text_files_ary(Xowe_core_data_mgr core_data_mgr) {
		ListAdp text_files_list = ListAdp_.new_();
		int len = core_data_mgr.Dbs__len();
		if (len == 1) return new Xowd_db_file[] {core_data_mgr.Dbs__get_at(0)};	// single file: return core; note that there are no Tid = Text
		for (int i = 0; i < len; i++) {
			Xowd_db_file file = core_data_mgr.Dbs__get_at(i);
			if (file.Tid() == Xowd_db_file_.Tid_text)
				text_files_list.Add(file);
		}
		return (Xowd_db_file[])text_files_list.Xto_ary_and_clear(Xowd_db_file.class);
	}
}
class Xob_dump_src_id {
	private Xodb_mgr_sql db_mgr; private byte redirect;
	private String page_db_url; private int size_max;
	private Db_stmt text_stmt; int cur_text_db_idx = -1;
	public Xob_dump_src_id Init(Xowe_wiki wiki, byte redirect, int size_max) {
		this.db_mgr = wiki.Db_mgr_as_sql(); this.redirect = redirect;
		this.size_max = size_max;
		page_db_url = db_mgr.Core_data_mgr().Dbs__get_by_tid_1st(Xowd_db_file_.Tid_core).Url().Raw();
		return this;
	}
	public void Get_pages(ListAdp list, int text_db_idx, int cur_ns, int prv_id) {
		DataRdr rdr = DataRdr_.Null;
		int size_len = 0;
		list.Clear();
		try {
			rdr = New_rdr(db_mgr, page_db_url, text_db_idx, cur_ns, prv_id, redirect);
			while (rdr.MoveNextPeer()) {
				Xodb_page page = New_page(db_mgr, cur_ns, rdr);
				list.Add(page);
				size_len += page.Wtxt_len();
				if (size_len > size_max)
					break;
			}
		}
		finally {rdr.Rls();}
	}
	private DataRdr New_rdr(Xodb_mgr_sql db_mgr, String page_db_url, int text_db_idx, int cur_ns, int prv_id, byte redirect) {
		if (cur_text_db_idx != text_db_idx) {
			cur_text_db_idx = text_db_idx;
			Xowd_db_file text_db = db_mgr.Core_data_mgr().Dbs__get_at(text_db_idx);
			Db_conn conn = text_db.Conn();
			String sql = String_.Format(Sql_select, New_rdr__redirect_clause(redirect));
			text_stmt = conn.Stmt_new(Db_qry_sql.rdr_(sql));
		}
		return text_stmt.Clear().Val_int(prv_id).Val_int(cur_ns).Exec_select();
	}
	private static Xodb_page New_page(Xodb_mgr_sql db_mgr, int ns_id, DataRdr rdr) {
		Xodb_page rv = new Xodb_page();
		rv.Id_(rdr.ReadInt(Xodb_page_tbl.Fld_page_id));
		rv.Ns_id_(ns_id);
		rv.Ttl_page_db_(rdr.ReadBryByStr(Xodb_page_tbl.Fld_page_title));
		byte[] old_text = rdr.ReadBry(Xodb_text_tbl.Fld_old_text);
		old_text = db_mgr.Wiki().Appe().Zip_mgr().Unzip(db_mgr.Data_storage_format(), old_text);
		rv.Wtxt_(old_text);
		return rv;
	}
	private static String New_rdr__redirect_clause(byte redirect) {
		switch (redirect) {
			case Bool_.Y_byte:	return Sql_select__redirect_y;
			case Bool_.N_byte:	return Sql_select__redirect_n;
			case Bool_.__byte:	return Sql_select__redirect__;
			default:			throw Err_.unhandled(redirect);
		}
	}
	private static final String Sql_select = String_.Concat_lines_nl
	( "SELECT  p.page_id"
	, ",       p.page_title"
	, ",       t.old_text"
	, "FROM    page_dump p"
	, "        JOIN text t ON t.page_id = p.page_id"
	, "WHERE   p.page_id > ?"
	, "AND     p.page_namespace = ?{0}" 
	, "ORDER BY p.page_id"
	);
	private static final String
	  Sql_select__redirect_y = "\nAND     p.page_is_redirect = 1"
	, Sql_select__redirect_n = "\nAND     p.page_is_redirect = 0"
	, Sql_select__redirect__ = ""
	;
}
