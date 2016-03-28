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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.dbs.*;
public class Xob_exec_sql_cmd implements Xob_cmd {
	private Xowe_wiki wiki; private int file_idx = -1; private String sql;
	public Xob_exec_sql_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.wiki = wiki;}
	public String Cmd_key() {return Xob_cmd_keys.Key_exec_sql;}
	public void Cmd_run() {
		Xoae_app app = wiki.Appe();
		wiki.Init_assert();	// force load; needed to pick up MediaWiki ns for MediaWiki:mainpage
		Xodb_mgr_sql db_mgr = wiki.Db_mgr_as_sql();
		Xowd_db_mgr fsys_mgr = db_mgr.Core_data_mgr();
		Xowd_db_file file = fsys_mgr.Dbs__get_by_id(file_idx);
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
