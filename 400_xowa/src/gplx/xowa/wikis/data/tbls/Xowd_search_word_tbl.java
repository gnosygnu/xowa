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
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.qrys.*;
public class Xowd_search_word_tbl implements RlsAble {
	private final String tbl_name; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_id, fld_text;
	private final Db_conn conn; private Db_stmt stmt_insert, stmt_select_id;
	public Xowd_search_word_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		String fld_prefix = "", fld_text_name = "word_text";
		if (schema_is_1)	{tbl_name = "search_title_word"; fld_prefix = "stw_"; fld_text_name = "stw_word";}
		else				{tbl_name = "search_word";}
		fld_id				= flds.Add_int_pkey(fld_prefix + "word_id");
		fld_text			= flds.Add_str(fld_text_name, 255);
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Create_idx() {conn.Ddl_create_idx(Xoa_app_.Usr_dlg(), Db_meta_idx.new_unique_by_tbl(tbl_name, "main", fld_text, fld_id));}
	public void Insert_bgn() {conn.Txn_bgn(); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int id, byte[] word) {
		stmt_insert.Clear().Val_int(fld_id, id).Val_bry_as_str(fld_text, word).Exec_insert();
	}
	public int Select_id(byte[] word) {
		if (stmt_select_id == null) stmt_select_id = conn.Stmt_select(tbl_name, flds, fld_text);
		Db_rdr rdr = stmt_select_id.Clear().Crt_bry(fld_text, word).Exec_select__rls_auto();
		try		{return rdr.Move_next() ? rdr.Read_int(fld_id) : Id_null;}
		finally {rdr.Rls();}
	}
	public void Select_by_word(Cancelable cancelable, Xowd_search_link_tbl search_page_tbl, ListAdp rv, byte[] search, int results_max) {
		gplx.core.criterias.Criteria crt = null; 
		if (Bry_.Has(search, Byte_ascii.Asterisk)) {
			search = Bry_.Replace(search, Byte_ascii.Asterisk, Byte_ascii.Percent);
			crt = Db_crt_.like_	(fld_text, String_.new_utf8_(search));
		}
		else
			crt = Db_crt_.eq_	(fld_text, String_.new_utf8_(search));
		Db_qry__select_cmd qry = Db_qry_.select_().Cols_(fld_id).From_(tbl_name).Where_(crt);

		ListAdp words = ListAdp_.new_();
		Db_rdr rdr = conn.Stmt_new(qry).Crt_bry_as_str(fld_text, search).Exec_select__rls_auto();
		try {
			while (rdr.Move_next())
				words.Add(Int_obj_val.new_(rdr.Read_int(fld_id)));
		}
		finally {rdr.Rls();}

		search_page_tbl.Select_in(cancelable, rv, words);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
		stmt_select_id = Db_stmt_.Rls(stmt_select_id);
	}
	public static final int Id_null = -1;
}
