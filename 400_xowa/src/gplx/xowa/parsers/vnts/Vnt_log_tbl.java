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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*;
public class Vnt_log_tbl implements RlsAble {
	private final String tbl_name = "log_vnt"; private final Db_meta_fld_list flds = Db_meta_fld_list.new_();
	private final String fld_uid, fld_page_id, fld_rule_idx
	, fld_flag_count, fld_lang_count, fld_undi_count, fld_bidi_count
	, fld_flag_add, fld_flag_del, fld_flag_aout, fld_flag_hide, fld_flag_raw, fld_flag_show, fld_flag_descrip, fld_flag_name, fld_flag_title, fld_flag_err
	, fld_vnt_0, fld_vnt_1, fld_vnt_2, fld_vnt_3, fld_vnt_4, fld_vnt_5, fld_vnt_6, fld_vnt_7, fld_vnt_8, fld_vnt_9
	, fld_src_bgn, fld_src_end, fld_src_txt;
	private Db_stmt stmt_insert;
	public Vnt_log_tbl(Db_conn conn) {
		this.conn = conn;
		this.fld_uid = flds.Add_int("uid");
		this.fld_page_id = flds.Add_int("page_id");
		this.fld_rule_idx = flds.Add_int("rule_idx");
		this.fld_flag_count = flds.Add_int("flag_count");
		this.fld_lang_count = flds.Add_int("lang_count");
		this.fld_undi_count = flds.Add_int("undi_count");
		this.fld_bidi_count = flds.Add_int("bidi_count");
		this.fld_flag_add = flds.Add_int("flag_add");
		this.fld_flag_del = flds.Add_int("flag_del");
		this.fld_flag_aout = flds.Add_int("flag_aout");
		this.fld_flag_hide = flds.Add_int("flag_hide");
		this.fld_flag_raw = flds.Add_int("flag_raw");
		this.fld_flag_show = flds.Add_int("flag_show");
		this.fld_flag_descrip = flds.Add_int("flag_descrip");
		this.fld_flag_name = flds.Add_int("flag_name");
		this.fld_flag_title = flds.Add_int("flag_title");
		this.fld_flag_err = flds.Add_int("flag_err");
		this.fld_vnt_0 = flds.Add_int("vnt_0");
		this.fld_vnt_1 = flds.Add_int("vnt_1");
		this.fld_vnt_2 = flds.Add_int("vnt_2");
		this.fld_vnt_3 = flds.Add_int("vnt_3");
		this.fld_vnt_4 = flds.Add_int("vnt_4");
		this.fld_vnt_5 = flds.Add_int("vnt_5");
		this.fld_vnt_6 = flds.Add_int("vnt_6");
		this.fld_vnt_7 = flds.Add_int("vnt_7");
		this.fld_vnt_8 = flds.Add_int("vnt_8");
		this.fld_vnt_9 = flds.Add_int("vnt_9");
		this.fld_src_bgn = flds.Add_int("src_bgn");
		this.fld_src_end = flds.Add_int("src_end");
		this.fld_src_txt = flds.Add_text("src_txt");
		conn.Rls_reg(this);
	}
	public Db_conn Conn() {return conn;} private final Db_conn conn; 
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Create_tbl() {conn.Ddl_create_tbl(Db_meta_tbl.new_(tbl_name, flds));}
	public void Insert(int uid, int page_id, int rule_idx, int flag_count, int lang_count, int undi_count, int bidi_count
		, boolean flag_add, boolean flag_del, boolean flag_aout, boolean flag_hide, boolean flag_raw, boolean flag_show, boolean flag_descrip, boolean flag_name, boolean flag_title, boolean flag_err
		, int vnt_0, int vnt_1, int vnt_2, int vnt_3, int vnt_4, int vnt_5, int vnt_6, int vnt_7, int vnt_8, int vnt_9
		, int src_bgn, int src_end, byte[] src_txt
		) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_uid, uid)
		.Val_int(fld_page_id, page_id)
		.Val_int(fld_rule_idx, rule_idx)
		.Val_int(fld_flag_count, flag_count)
		.Val_int(fld_lang_count, lang_count)
		.Val_int(fld_undi_count, undi_count)
		.Val_int(fld_bidi_count, bidi_count)
		.Val_int_by_bool(fld_flag_add, flag_add)
		.Val_int_by_bool(fld_flag_del, flag_del)
		.Val_int_by_bool(fld_flag_aout, flag_aout)
		.Val_int_by_bool(fld_flag_hide, flag_hide)
		.Val_int_by_bool(fld_flag_raw, flag_raw)
		.Val_int_by_bool(fld_flag_show, flag_show)
		.Val_int_by_bool(fld_flag_descrip, flag_descrip)
		.Val_int_by_bool(fld_flag_name, flag_name)
		.Val_int_by_bool(fld_flag_title, flag_title)
		.Val_int_by_bool(fld_flag_err, flag_err)
		.Val_int(fld_vnt_0, vnt_0)
		.Val_int(fld_vnt_1, vnt_1)
		.Val_int(fld_vnt_2, vnt_2)
		.Val_int(fld_vnt_3, vnt_3)
		.Val_int(fld_vnt_4, vnt_4)
		.Val_int(fld_vnt_5, vnt_5)
		.Val_int(fld_vnt_6, vnt_6)
		.Val_int(fld_vnt_7, vnt_7)
		.Val_int(fld_vnt_8, vnt_8)
		.Val_int(fld_vnt_9, vnt_9)
		.Val_int(fld_src_bgn, src_bgn)
		.Val_int(fld_src_end, src_end)
		.Val_bry_as_str(fld_src_txt, src_txt)
		.Exec_insert();
	}
}
