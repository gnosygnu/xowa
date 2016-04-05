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
package gplx.xowa.addons.builds.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.builds.*; import gplx.xowa.addons.builds.files.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.addons.builds.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
import gplx.xowa.wikis.domains.*; import gplx.xowa.files.repos.*;
public class Xobldr__page_regy__create extends Xob_cmd__base {
	private boolean build_commons = false;
	public Xobldr__page_regy__create(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Xowe_wiki commons_wiki = bldr.App().Wiki_mgr().Get_by_or_make(Xow_domain_itm_.Bry__commons).Init_assert();
		Db_conn page_regy_provider = Xob_db_file.New__page_regy(commons_wiki.Fsys_mgr().Root_dir()).Conn();
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
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_build_commons_))				build_commons = m.ReadYn("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_build_commons_ = "build_commons_";

	public static final String BLDR_CMD_KEY = "file.page_regy";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__page_regy__create(null, null);
	@Override public Xob_cmd Cmd_new(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__page_regy__create(bldr, wiki);}
}
