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
package gplx.xowa.bldrs.cmds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.dbs.*; import gplx.xowa.wikis.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.files.repos.*;
public class Xob_page_regy_cmd extends Xob_itm_basic_base implements Xob_cmd {
	public Xob_page_regy_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	private boolean build_commons = false;
	public String Cmd_key() {return Xob_cmd_keys.Key_file_page_regy;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		Xowe_wiki commons_wiki = bldr.App().Wiki_mgr().Get_by_key_or_make(Xow_domain_.Domain_bry_commons).Init_assert();
		Db_conn page_regy_provider = Xob_db_file.new__page_regy(commons_wiki.Fsys_mgr().Root_dir()).Conn();
		commons_wiki.Init_assert();
		if (build_commons) {
			Xob_page_regy_tbl.Reset_table(page_regy_provider);
			Xob_page_regy_tbl.Create_data(bldr.Usr_dlg(), page_regy_provider, Xof_repo_itm_.Repo_remote, commons_wiki);
			Sqlite_engine_.Idx_create(usr_dlg, page_regy_provider, "repo_page", Xob_page_regy_tbl.Idx_main);
		}
		else {
			if (!Bry_.Eq(commons_wiki.Domain_bry(), wiki.Domain_bry())) {	// skip local wiki if cur wiki is commons
				wiki.Init_assert();
				Xob_page_regy_tbl.Delete_local(page_regy_provider);
				Xob_page_regy_tbl.Create_data(bldr.Usr_dlg(), page_regy_provider, Xof_repo_itm_.Repo_local, wiki);
			}
		}
	}
	public void Cmd_run() {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_build_commons_))				build_commons = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_build_commons_ = "build_commons_";
}
