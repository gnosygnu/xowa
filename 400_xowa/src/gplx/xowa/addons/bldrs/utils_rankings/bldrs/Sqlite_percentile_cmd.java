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
package gplx.xowa.addons.bldrs.utils_rankings.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.utils_rankings.*;
import gplx.dbs.*; import gplx.dbs.qrys.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;
public class Sqlite_percentile_cmd extends Xob_cmd__base implements Xob_cmd {
	private String db_rel_url, tbl_name = "tmp_score"; private int score_max = 100000000; private String select_sql;
	private Db_conn conn;
	public Sqlite_percentile_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Sqlite_percentile_cmd Init_by_rel_url(String db_rel_url, String tbl_name, int score_max, String select_sql) {
		this.db_rel_url = db_rel_url; this.tbl_name = tbl_name; this.score_max = score_max; this.select_sql = select_sql;
		return this;
	}
	public Sqlite_percentile_cmd Init_by_conn(Db_conn conn, String tbl_name, int score_max, String select_sql) {this.conn = conn; return this.Init_by_rel_url(null, tbl_name, score_max, select_sql);}
	public int count;
	@Override public void Cmd_run() {
		wiki.Init_assert();
		if (conn == null) {
			if (db_rel_url == null) throw Err_.new_("bldr", "db_rel_url can not be empty; EX: 'xowa.page_rank.sqlite3'");
			conn = Db_conn_bldr.Instance.Get_or_autocreate(false, wiki.Fsys_mgr().Root_dir().GenSubFil(db_rel_url));
		}
		Xoa_app_.Usr_dlg().Prog_many("", "", "creating temp_table: tbl=~{0}", tbl_name);
		conn.Meta_tbl_delete(tbl_name);
		conn.Meta_tbl_create
		( Dbmeta_tbl_itm.New(tbl_name
		,	Dbmeta_fld_itm.new_int("row_rank").Primary_y_().Autonum_y_()
		,	Dbmeta_fld_itm.new_int("row_key")
		,	Dbmeta_fld_itm.new_double("row_val")
		,	Dbmeta_fld_itm.new_double("row_score").Default_(-1)
		));
		Xoa_app_.Usr_dlg().Prog_many("", "", "filling temp_table: tbl=~{0} sql=~{1}", tbl_name, select_sql);
		new Db_attach_mgr(conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Tbl__page().Conn()))
			.Exec_sql(Bry_fmt.Make_str("INSERT INTO ~{tbl} (row_key, row_val) ~{select}", tbl_name, select_sql));
		Xoa_app_.Usr_dlg().Prog_many("", "", "updating row_score: tbl=~{0}", tbl_name);
		String score_max_as_str = Dbmeta_fld_itm.To_double_str_by_int(score_max);
		this.count = conn.Exec_select_as_int("SELECT Count(*) FROM " + tbl_name, -1); if (count == -1) throw Err_.new_("bldr", "failed to get count; tbl=~{0}", tbl_name);
		String count_as_str = Dbmeta_fld_itm.To_double_str_by_int(count);
		conn.Exec_sql(Bry_fmt.Make_str("UPDATE ~{tbl} SET row_score = (row_rank * ~{score_max}) / ~{count}", tbl_name, score_max_as_str, count_as_str));
		Xoa_app_.Usr_dlg().Prog_many("", "", "resolving ties: tbl=~{0}", tbl_name);
		conn.Meta_tbl_remake
		( Dbmeta_tbl_itm.New(tbl_name + "_avg"
		,	Dbmeta_fld_itm.new_double("row_val").Primary_y_()
		,	Dbmeta_fld_itm.new_double("row_score")
		));
		conn.Exec_sql(Bry_fmt.Make_str(String_.Concat_lines_nl_skip_last
		( "INSERT INTO ~{tbl}_avg (row_val, row_score)"
		, "SELECT   row_val"
		, ",        (Avg(row_rank) * ~{score_max} / ~{count}) AS row_score"
		, "FROM     ~{tbl}"
		, "GROUP BY row_val"
		, "HAVING   Count(row_val > 1)"
		), tbl_name, score_max_as_str, count_as_str));
		conn.Exec_sql(Bry_fmt.Make_str(String_.Concat_lines_nl_skip_last
		( "UPDATE   ~{tbl}"
		, "SET      row_score = (SELECT row_score FROM ~{tbl}_avg t2 WHERE t2.row_val = ~{tbl}.row_val)"
		, "WHERE    row_val IN (SELECT row_val FROM ~{tbl}_avg t2)"
		), tbl_name));
		conn.Meta_tbl_delete(tbl_name + "_avg");
		conn.Meta_idx_create(Xoa_app_.Usr_dlg(), Dbmeta_idx_itm.new_normal_by_tbl(tbl_name, "row_score", "row_key", "row_score"));
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__db_rel_url_))			db_rel_url = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__select_sql_))			select_sql = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__tbl_name_))				tbl_name = m.ReadStr("v");
		else if	(ctx.Match(k, Invk__score_max_))			score_max = m.ReadInt("v");
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__db_rel_url_ = "db_rel_url_", Invk__select_sql_ = "select_sql_", Invk__tbl_name_ = "tbl_name_", Invk__score_max_ = "score_max_";

	public static final String BLDR_CMD_KEY = "util.sqlite.normalize";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Sqlite_percentile_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Sqlite_percentile_cmd(bldr, wiki);}
}
