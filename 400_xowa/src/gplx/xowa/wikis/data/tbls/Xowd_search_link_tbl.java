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
import gplx.core.primitives.*;
import gplx.dbs.*; import gplx.dbs.utls.*;
public class Xowd_search_link_tbl {
	private final String tbl_name; private final Dbmeta_fld_list flds = Dbmeta_fld_list.new_();
	private final String fld_word_id, fld_page_id;
	private final Db_conn conn; private Db_stmt stmt_insert; private final Xowd_search_page_tbl__in_wkr in_wkr = new Xowd_search_page_tbl__in_wkr();
	public Xowd_search_link_tbl(Db_conn conn, boolean schema_is_1) {
		this.conn = conn;
		String fld_prefix = "";
		if (schema_is_1)	{tbl_name = "search_title_page"; fld_prefix = "stp_";}
		else				{tbl_name = "search_link";}
		fld_word_id			= flds.Add_int(fld_prefix + "word_id");
		fld_page_id			= flds.Add_int(fld_prefix + "page_id");
		in_wkr.Ctor(tbl_name, flds, fld_page_id, fld_word_id);
	}
	public String Tbl_name()		{return tbl_name;}
	public String Fld_word_id()		{return fld_word_id;}
	public String Fld_page_id()		{return fld_page_id;}
	public void Create_tbl()		{conn.Ddl_create_tbl(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx_unique() {conn.Ddl_create_idx(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "main", fld_word_id, fld_page_id));}
	public void Create_idx_normal() {conn.Ddl_create_idx(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "main", fld_word_id, fld_page_id));}
	public void Insert_bgn() {conn.Txn_bgn("schema__search_link__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int word_id, int page_id) {
		stmt_insert.Clear().Val_int(fld_word_id, word_id).Val_int(fld_page_id, page_id).Exec_insert();
	}
	public void Select_in(Cancelable cancelable, List_adp rv, List_adp words) {
		in_wkr.Init(words, rv);
		in_wkr.Select_in(cancelable, conn, 0, words.Count());
	}
}
class Xowd_search_page_tbl__in_wkr extends Db_in_wkr__base {
	private String tbl_name; private Dbmeta_fld_list flds; private String fld_page_id, fld_word_id;
	private List_adp words, pages;		
	public void Ctor(String tbl_name, Dbmeta_fld_list flds, String fld_page_id, String fld_word_id) {
		this.tbl_name = tbl_name; this.flds = flds; this.fld_page_id = fld_page_id; this.fld_word_id = fld_word_id;
	}
	public void Init(List_adp words, List_adp pages) {this.words = words; this.pages = pages;}
	@Override protected Db_qry Make_qry(int bgn, int end) {
		Object[] part_ary = In_ary(end - bgn);			
		return Db_qry_.select_cols_(tbl_name, Db_crt_.in_(fld_word_id, part_ary), flds.To_str_ary());
	}
	@Override protected void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Int_obj_val word_id = (Int_obj_val)words.Get_at(i);
			stmt.Crt_int(fld_word_id, word_id.Val());		
		}
	}
	@Override protected void Read_data(Cancelable cancelable, Db_rdr rdr) {
		while (rdr.Move_next()) {
			if (cancelable.Canceled()) return;
			int page_id = rdr.Read_int(fld_page_id);
			Xowd_page_itm page = new Xowd_page_itm().Id_(page_id);
			pages.Add(page);
		}
	}
}
