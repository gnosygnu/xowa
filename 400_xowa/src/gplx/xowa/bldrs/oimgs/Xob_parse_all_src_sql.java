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
import gplx.xowa.dbs.*; import gplx.dbs.*; import gplx.xowa.dbs.tbls.*;
class Xob_dump_src_ttl implements Xob_parse_all_db {
	private Xodb_mgr_sql db_mgr; private Db_stmt page_stmt; private Xodb_file[] text_files_ary; private int text_files_len; private byte redirect;
	public Xob_dump_src_ttl Init(Xow_wiki wiki, int limit, byte redirect) {
		this.db_mgr = wiki.Db_mgr_as_sql(); this.redirect = redirect;
		page_stmt = db_mgr.Tbl_page().Select_for_parse_all_stmt(db_mgr.Fsys_mgr().Core_provider(), limit, redirect);
		text_files_ary = Init_text_files_ary(db_mgr.Fsys_mgr());
		text_files_len = text_files_ary.length;
		return this;
	}
	public void Fetch_next(OrderedHash hash, int ns_id, byte[] ttl) {
		Cancelable cancelable = Cancelable_.Never;
		db_mgr.Tbl_page().Select_for_parse_all(cancelable, hash, page_stmt, ns_id, ttl, redirect);
		for (int i = 0; i < text_files_len; i++) {
			Xodb_file text_file = text_files_ary[i];
			db_mgr.Tbl_text().Select_in(cancelable, text_file, hash);
		}
	}
	public static Xodb_file[] Init_text_files_ary(Xodb_fsys_mgr fsys_mgr) {
		ListAdp text_files_list = ListAdp_.new_();
		Xodb_file[] file_ary = fsys_mgr.Ary();
		int len = file_ary.length;
		if (len == 1) return new Xodb_file[] {file_ary[0]};	// single file: return core; note that there are no Tid = Text
		for (int i = 0; i < len; i++) {
			Xodb_file file = file_ary[i];
			if (file.Tid() == Xodb_file_tid_.Tid_text)
				text_files_list.Add(file);
		}
		return (Xodb_file[])text_files_list.XtoAryAndClear(Xodb_file.class);
	}
}
class Xob_dump_src_id {
	private Xodb_mgr_sql db_mgr; private byte redirect;
	private String page_db_url; private int size_max;
	private Db_stmt text_stmt; int cur_text_db_idx = -1;
	public Xob_dump_src_id Init(Xow_wiki wiki, byte redirect, int size_max) {
		this.db_mgr = wiki.Db_mgr_as_sql(); this.redirect = redirect;
		this.size_max = size_max;
		page_db_url = db_mgr.Fsys_mgr().Get_tid_root(Xodb_file_tid_.Tid_core).Url().Raw();
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
				size_len += page.Text_len();
				if (size_len > size_max)
					break;
			}
		}
		finally {rdr.Rls();}
	}
	private DataRdr New_rdr(Xodb_mgr_sql db_mgr, String page_db_url, int text_db_idx, int cur_ns, int prv_id, byte redirect) {
		if (cur_text_db_idx != text_db_idx) {
			cur_text_db_idx = text_db_idx;
			Xodb_file text_db = db_mgr.Fsys_mgr().Get_by_db_idx(text_db_idx);
			Db_provider provider = text_db.Provider();
			String sql = String_.Format(Sql_select, New_rdr__redirect_clause(redirect));
			text_stmt = provider.Prepare(Db_qry_sql.rdr_(sql));
		}
		return text_stmt.Clear().Val_int_(prv_id).Val_int_(cur_ns).Exec_select();
	}
	private static Xodb_page New_page(Xodb_mgr_sql db_mgr, int ns_id, DataRdr rdr) {
		Xodb_page rv = new Xodb_page();
		rv.Ns_id_(ns_id);
		rv.Id_(rdr.ReadInt(Xodb_page_tbl.Fld_page_id));
		rv.Ttl_wo_ns_(rdr.ReadBryByStr(Xodb_page_tbl.Fld_page_title));
		byte[] old_text = rdr.ReadBry(Xodb_text_tbl.Fld_old_text);
		old_text = db_mgr.Wiki().App().Zip_mgr().Unzip(db_mgr.Data_storage_format(), old_text);
		rv.Text_(old_text);
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
