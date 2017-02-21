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
public class Srch_link_tbl {
	public final    String fld_word_id, fld_page_id, fld_link_score;
	public final    Db_conn conn;
	public Srch_link_tbl(Db_conn conn) {
		this.conn = conn;
		this.tbl_name = "search_link";
		fld_word_id			= flds.Add_int("word_id");
		fld_page_id			= flds.Add_int("page_id");
		fld_link_score		= flds.Add_int_dflt(Fld_link_score, 0);
	}
	public String Tbl_name() {return tbl_name;} private final    String tbl_name;
	public Dbmeta_fld_list Flds() {return flds;} private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
	public int Id;
	public void Create_tbl()				{conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
	public void Create_idx__page_id()		{}	// TODO_OLD: conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "page_id", fld_page_id));}
	public void Create_idx__link_score()	{conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "word_id__link_score", fld_word_id, Fld_link_score));}

	public Srch_link_row New_row(Db_rdr rdr) {
		return new Srch_link_row(rdr.Read_int(fld_word_id), rdr.Read_int(fld_page_id), rdr.Read_int(fld_link_score));
	}
	public void Fill_for_insert(Db_stmt stmt, Srch_link_row row) {
		stmt.Val_int(fld_word_id, row.Word_id).Val_int(fld_page_id, row.Page_id).Val_int(fld_link_score, row.Link_score);
	}
	public void Delete(int page_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.search_link: delete started: db=~{0} page_id=~{1}", conn.Conn_info().Raw(), page_id);
		conn.Stmt_delete(tbl_name, fld_page_id).Crt_int(fld_page_id, page_id).Exec_delete();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.search_link: delete done");
	}
	public void Update_page_id(int old_id, int new_id) {
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.search_link: update_page_id started: db=~{0} old_id=~{1} new_id=~{2}", conn.Conn_info().Raw(), old_id, new_id);
		conn.Stmt_update(tbl_name, String_.Ary(fld_page_id), fld_page_id).Val_int(fld_page_id, old_id).Crt_int(fld_page_id, new_id).Exec_update();
		Gfo_usr_dlg_.Instance.Log_many("", "", "db.search_link: update done");
	}

	public static final    Srch_link_tbl[] Ary_empty = new Srch_link_tbl[0];
	public static final String Fld_link_score = "link_score";
}
