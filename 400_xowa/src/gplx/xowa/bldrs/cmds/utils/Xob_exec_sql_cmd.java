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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.dbs.*;
public class Xob_exec_sql_cmd implements Xob_cmd {
	private Xowe_wiki wiki; private int file_idx = -1; private String sql;
	public Xob_exec_sql_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.wiki = wiki;}
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public String Cmd_key() {return Xob_cmd_keys.Key_exec_sql;}
	public void Cmd_run() {
		Xoae_app app = wiki.Appe();
		wiki.Init_assert();	// force load; needed to pick up MediaWiki ns for MediaWiki:mainpage
		Xodb_mgr_sql db_mgr = wiki.Db_mgr_as_sql();
		Xow_db_mgr fsys_mgr = db_mgr.Core_data_mgr();
		Xow_db_file file = fsys_mgr.Dbs__get_by_id_or_fail(file_idx);
		app.Usr_dlg().Plog_many("", "", "exec_sql: running sql; file_idx=~{0} sql=~{1}", file_idx, sql);
		file.Conn().Exec_sql(sql);
	}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_file_idx_))				file_idx = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_sql_))					sql = m.ReadStr("v");
		return this;
	}
	private static final String Invk_file_idx_ = "file_idx_", Invk_sql_ = "sql_";
}
