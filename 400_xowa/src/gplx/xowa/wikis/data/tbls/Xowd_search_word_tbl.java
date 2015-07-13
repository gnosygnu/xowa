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
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.qrys.*;
public class Xowd_search_word_tbl implements RlsAble {
	private final String tbl_name; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_text; private String fld_page_count; private boolean page_count_exists;
	private final Db_conn conn; private Db_stmt stmt_insert, stmt_select_by, stmt_select_in;
	public Xowd_search_word_tbl(Db_conn conn, boolean schema_is_1, boolean page_count_exists) {
		this.conn = conn; this.page_count_exists = page_count_exists;
		String fld_prefix = "", fld_text_name = "word_text";
		if (schema_is_1)	{tbl_name = "search_title_word"; fld_prefix = "stw_"; fld_text_name = "stw_word";}
		else				{tbl_name = "search_word";}
		this.fld_id				= flds.Add_int_pkey(fld_prefix + "word_id");
		this.fld_text			= flds.Add_str(fld_text_name, 255);
		this.fld_page_count		= page_count_exists ? flds.Add_int_dflt("word_page_count", 0) : Db_meta_fld.Key_null;
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Create_idx() {conn.Ddl_create_idx(Xoa_app_.Usr_dlg(), Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_text, fld_id, fld_page_count));}
	public void Insert_bgn() {conn.Txn_bgn(); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int id, byte[] word, int page_count) {
		stmt_insert.Clear().Val_int(fld_id, id).Val_bry_as_str(fld_text, word).Val_int(fld_page_count, page_count).Exec_insert();
	}
	public Xowd_search_word_row Select_by_or_null(byte[] word) {
		if (stmt_select_by == null) stmt_select_by = conn.Stmt_select(tbl_name, flds, fld_text);
		Db_rdr rdr = stmt_select_by.Clear().Crt_bry_as_str(fld_text, word).Exec_select__rls_manual();
		try	{
			return rdr.Move_next() ? new_row(rdr) : Xowd_search_word_row.Null;
		}
		finally {rdr.Rls();}
	}
	private Xowd_search_word_row new_row(Db_rdr rdr) {
		int page_count = fld_page_count == Db_meta_fld.Key_null ? 0 : rdr.Read_int(fld_page_count);
		return new Xowd_search_word_row(rdr.Read_int(fld_id), rdr.Read_bry_by_str(fld_text), page_count);
	}
	public Xowd_search_word_row[] Select_in(Cancelable cxl, byte[] word) {
		if (stmt_select_in == null) {
			Db_qry__select_cmd qry = Db_qry_.select_().From_(tbl_name).OrderBy_(fld_page_count, Bool_.N).Where_(Db_crt_.like_(fld_text, ""));	// order by highest page count to look at most common words
			stmt_select_in = conn.Stmt_new(qry);
		}
		List_adp list = List_adp_.new_();
		Db_rdr rdr = stmt_select_in.Clear().Crt_bry_as_str(fld_text, Bry_.Replace(word, Byte_ascii.Star, Byte_ascii.Percent)).Exec_select__rls_manual();
		try	{
			int row_count = 0;
			while (rdr.Move_next()) {
				if (cxl.Canceled()) break;
				Xowd_search_word_row word_row = new_row(rdr);
				if (++row_count % 10 == 0)
					Xoa_app_.Usr_dlg().Prog_many("", "", "search; reading pages for word: word=~{0} pages=~{1}", word_row.Text(), word_row.Page_count());
				list.Add(word_row);
			}
		}
		finally {rdr.Rls();}
		return (Xowd_search_word_row[])list.To_ary_and_clear(Xowd_search_word_row.class);
	}
	public void Select_by_word(Cancelable cancelable, Xowd_search_link_tbl search_page_tbl, List_adp rv, byte[] search, int results_max) {
		gplx.core.criterias.Criteria crt = null; 
		if (Bry_.Has(search, Byte_ascii.Star)) {
			search = Bry_.Replace(search, Byte_ascii.Star, Byte_ascii.Percent);
			crt = Db_crt_.like_	(fld_text, String_.new_u8(search));
		}
		else
			crt = Db_crt_.eq_	(fld_text, String_.new_u8(search));
		Db_qry__select_cmd qry = Db_qry_.select_().Cols_(fld_id).From_(tbl_name).Where_(crt);

		List_adp words = List_adp_.new_();
		Db_rdr rdr = conn.Stmt_new(qry).Crt_bry_as_str(fld_text, search).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				words.Add(Int_obj_val.new_(rdr.Read_int(fld_id)));
		}
		finally {rdr.Rls();}

		search_page_tbl.Select_in(cancelable, rv, words);
	}
	public boolean Ddl__page_count() {return page_count_exists;}
	public void Ddl__page_count_y_() {	// needed for search_cmd;
		page_count_exists = true;
		if (!flds.Has("word_page_count"))
			flds.Add_int_dflt("word_page_count", 0);
	}
	public void Ddl__page_count__add(Xowd_search_link_tbl link_tbl, Db_cfg_tbl cfg_tbl) {
		Db_meta_fld page_count_fld = Db_meta_fld.new_int("word_page_count").Default_(0);
		conn.Txn_bgn();
		conn.Ddl_append_fld(tbl_name, page_count_fld);	// SQL: ALTER TABLE search_word ADD word_page_count integer NOT NULL DEFAULT 0;
		String sql = String_.Format(String_.Concat_lines_nl_skip_last
		( "REPLACE INTO {0} ({1}, {2}, word_page_count)"
		, "SELECT   w.{1}"
		, ",        w.{2}"
		, ",        Count(l.{4})"
		, "FROM     {0} w"
		, "         JOIN {3} l ON w.{1} = l.{4}"
		, "GROUP BY w.{1}"
		, ",        w.{2};"
		), tbl_name, fld_id, fld_text
		, link_tbl.Tbl_name(), link_tbl.Fld_word_id()
		);
		conn.Exec_sql_plog_ntx("calculating page count per word (please wait)", sql);
		Ddl__page_count__cfg(cfg_tbl);
		fld_page_count = page_count_fld.Name(); flds.Add(page_count_fld); this.Rls();
		conn.Txn_end();
	}
	public void Ddl__page_count__cfg(Db_cfg_tbl cfg_tbl) {
		cfg_tbl.Insert_yn(Xowd_db_file_schema_props.Grp, Xowd_db_file_schema_props.Key__col_search_word_page_count, Bool_.Y);
	}
	public void Rls() {
		stmt_insert		= Db_stmt_.Rls(stmt_insert);
		stmt_select_by	= Db_stmt_.Rls(stmt_select_by);
		stmt_select_in	= Db_stmt_.Rls(stmt_select_in);
	}
	public static final int Id_null = -1;
}
