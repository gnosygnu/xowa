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
import gplx.dbs.*;
import gplx.xowa.wikis.domains.*;
public class Xob_orig_regy_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private boolean repo_0_is_remote = false;
	public Xob_orig_regy_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Xob_cmd_keys.Key_file_orig_regy;}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		Db_conn conn = Xob_db_file.new__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_orig_regy_tbl.Create_table(conn);
		Xowe_wiki commons_wiki = bldr.App().Wiki_mgr().Get_by_key_or_make(Xow_domain_itm_.Bry__commons).Init_assert();
		Xowe_wiki repo_0 = wiki, repo_1 = commons_wiki;
		if (repo_0_is_remote) {	// NOTE: default is false; local_wiki will be preferred over commons_wiki
			repo_0 = commons_wiki;
			repo_1 = wiki;
		}
		repo_0.Init_assert(); repo_1.Init_assert();
		Xob_db_file file_registry_db = Xob_db_file.new__page_regy(commons_wiki.Fsys_mgr().Root_dir());
		Xob_orig_regy_tbl.Create_data(bldr.Usr_dlg(), conn, file_registry_db, repo_0_is_remote, repo_0, repo_1, Xob_lnki_temp_wkr.Ns_file_is_case_match_all(wiki));
	}
	public void Cmd_run() {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repo_0_is_remote_))				repo_0_is_remote = m.ReadYn("v");
		else														return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_repo_0_is_remote_ = "repo_0_is_remote_";
}
