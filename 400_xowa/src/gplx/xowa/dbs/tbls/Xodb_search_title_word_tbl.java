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
import gplx.core.primitives.*; import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.dbs.engines.sqlite.*;
public class Xodb_search_title_word_tbl {
	public static void Create_table(Db_conn p)						{Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);}
	public static void Create_index(Gfo_usr_dlg usr_dlg, Db_conn p)	{Sqlite_engine_.Idx_create(usr_dlg, p, "search", Indexes_main);}
	public static Db_stmt Insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, Tbl_name, Fld_stw_word_id, Fld_stw_word);}
	public static void Insert(Db_stmt stmt, int word_id, byte[] word) {
		stmt.Clear()
		.Val_int(word_id)
		.Val_bry_as_str(word)
		.Exec_insert();
	}	
	public static void Select_by_word(Cancelable cancelable, ListAdp rv, Xodb_ctx db_ctx, byte[] search, int results_max, Db_conn p) {
		Db_qry_select qry = Db_qry_.select_()
			.Cols_(Xodb_search_title_word_tbl.Fld_stw_word_id)
			.From_(Xodb_search_title_word_tbl.Tbl_name, "w")
			;
		gplx.core.criterias.Criteria crt = null; 
		if (Bry_.Has(search, Byte_ascii.Asterisk)) {
			search = Bry_.Replace(search, Byte_ascii.Asterisk, Byte_ascii.Percent);
			crt = Db_crt_.like_	(Xodb_search_title_word_tbl.Fld_stw_word, String_.new_utf8_(search));
		}
		else
			crt = Db_crt_.eq_	(Xodb_search_title_word_tbl.Fld_stw_word, String_.new_utf8_(search));
		qry.Where_(crt);

		DataRdr rdr = DataRdr_.Null;
		ListAdp words = ListAdp_.new_();
		try {
			rdr = qry.Exec_qry_as_rdr(p);
			while (rdr.MoveNextPeer()) {
				int word_id = rdr.ReadInt(Xodb_search_title_word_tbl.Fld_stw_word_id);
				words.Add(Int_obj_val.new_(word_id));
			}
		}
		finally {rdr.Rls();}

		Xodb_in_wkr_search_title_id wkr = new Xodb_in_wkr_search_title_id();
		wkr.Init(words, rv);
		wkr.Select_in(p, cancelable, db_ctx, 0, words.Count());
	}
	public static final String Tbl_name = "search_title_word", Fld_stw_word_id = "stw_word_id", Fld_stw_word = "stw_word";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS search_title_word"
	,	"( stw_word_id         integer             NOT NULL    PRIMARY KEY"
	,	", stw_word            varchar(255)        NOT NULL"
	,	");"
	);
	private static final Db_idx_itm
	Indexes_main						= Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS search_title_word__main       ON search_title_word (stw_word, stw_word_id);");
}
class Xodb_in_wkr_search_title_id extends Xodb_in_wkr_base {
	private ListAdp words, pages;
	@Override public int Interval() {return 990;}
	public void Init(ListAdp words, ListAdp pages) {this.words = words; this.pages = pages;}
	@Override public Db_qry Build_qry(Xodb_ctx db_ctx, int bgn, int end) {
		Object[] part_ary = Xodb_in_wkr_base.In_ary(end - bgn);
		String in_fld_name = Xodb_search_title_page_tbl.Fld_stp_word_id; 
		return Db_qry_.select_cols_
		(	Xodb_search_title_page_tbl.Tbl_name
		, 	Db_crt_.in_(in_fld_name, part_ary)
		)
		;
	}
	@Override public void Fill_stmt(Db_stmt stmt, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Int_obj_val word_id = (Int_obj_val)words.FetchAt(i);
			stmt.Val_int(word_id.Val());		
		}
	}
	@Override public void Eval_rslts(Cancelable cancelable, Xodb_ctx db_ctx, DataRdr rdr) {
		while (rdr.MoveNextPeer()) {
			if (cancelable.Canceled()) return;
			int page_id = rdr.ReadInt(Xodb_search_title_page_tbl.Fld_stp_page_id);
			Xodb_page page = new Xodb_page().Id_(page_id);
			pages.Add(page);
		}
	}
}
