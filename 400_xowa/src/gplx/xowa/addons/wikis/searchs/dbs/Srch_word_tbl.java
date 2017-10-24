/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.wikis.searchs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.dbs.*;
public class Srch_word_tbl implements Rls_able {
	public final    String tbl_name;
	public final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public final    String fld_id, fld_text, fld_link_count, fld_link_count_score, fld_link_score_min, fld_link_score_max;
	public final    Db_conn conn; private Db_stmt stmt_insert, stmt_select_by;
	public Srch_word_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name				= TABLE_NAME;
		this.fld_id					= flds.Add_int_pkey("word_id");
		this.fld_text				= flds.Add_str("word_text", 255);
		this.fld_link_count			= flds.Add_int("link_count");
		this.fld_link_count_score	= Dbmeta_fld_itm.Make_or_null(conn, flds, tbl_name, Dbmeta_fld_tid.Tid__int, 0, "link_count_score");
		this.fld_link_score_min		= Dbmeta_fld_itm.Make_or_null(conn, flds, tbl_name, Dbmeta_fld_tid.Tid__int, Int_.Max_value__31, "link_score_min");
		this.fld_link_score_max		= Dbmeta_fld_itm.Make_or_null(conn, flds, tbl_name, Dbmeta_fld_tid.Tid__int, 0, "link_score_max");
		conn.Rls_reg(this);
	}
	public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx() {
		// idx for rng_bgn, rng_end
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "word_text__link_score_max__link_score_min", fld_text, Fld_link_score_max, Fld_link_score_min));

		// idx for like
		conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "link_score_max__link_score_min", Fld_link_score_max, Fld_link_score_min));
	}
	public void Insert_bgn() {conn.Txn_bgn("schema__search_word__insert"); stmt_insert = conn.Stmt_insert(tbl_name, flds);}
	public void Insert_end() {conn.Txn_end(); stmt_insert = Db_stmt_.Rls(stmt_insert);}
	public void Insert_cmd_by_batch(int id, byte[] word, int page_count) {
		stmt_insert.Clear().Val_int(fld_id, id).Val_bry_as_str(fld_text, word).Val_int(fld_link_count, page_count).Exec_insert();
	}
	public void Insert_by_itm(Db_stmt stmt, Srch_word_row row) {
		stmt.Clear()
			.Val_int(fld_id, row.Id).Val_bry_as_str(fld_text, row.Text).Val_int(fld_link_count, row.Link_count)
			.Val_int(fld_link_count_score, row.Link_count_score).Val_int(fld_link_score_min, row.Link_score_min).Val_int(fld_link_score_max, row.Link_score_max)
			.Exec_insert();
	}
	public Srch_word_row Select_or_empty(byte[] word) {
		if (stmt_select_by == null) stmt_select_by = conn.Stmt_select(tbl_name, flds, fld_text);
		Db_rdr rdr = stmt_select_by.Clear().Crt_bry_as_str(fld_text, word).Exec_select__rls_manual();
		try		{return rdr.Move_next() ? New_row(rdr) : Srch_word_row.Empty;}
		finally {rdr.Rls();}
	}
	public Srch_word_row New_row(Db_rdr rdr) {
		int page_count			= fld_link_count		== Dbmeta_fld_itm.Key_null ? 0 : rdr.Read_int(fld_link_count);
		int link_score_min		= fld_link_score_min	== Dbmeta_fld_itm.Key_null ? page_count : rdr.Read_int(fld_link_score_min);
		int link_score_max		= fld_link_score_max	== Dbmeta_fld_itm.Key_null ? page_count : rdr.Read_int(fld_link_score_max);
		int link_count_score	= 0;
		if (fld_link_count_score != Dbmeta_fld_itm.Key_null) {
			try {link_count_score = rdr.Read_int(fld_link_count_score);}
			catch (Exception e) {// handle 2016-05 and earlier wikis which stored value as double instead of int
				Err_.Noop(e);
				link_count_score = (int)rdr.Read_double(fld_link_count_score);
			}
		}
		return new Srch_word_row(rdr.Read_int(fld_id), rdr.Read_bry_by_str(fld_text), page_count, link_count_score, link_score_min, link_score_max);
	}
	public void Rls() {
		stmt_insert				= Db_stmt_.Rls(stmt_insert);
		stmt_select_by			= Db_stmt_.Rls(stmt_select_by);
	}
	public static final String Fld_link_score_min = "link_score_min", Fld_link_score_max = "link_score_max";
	public static final String TABLE_NAME = "search_word";
}
