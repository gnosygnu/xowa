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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.files.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*;	
import gplx.xowa.wikis.domains.*;
public class Xobldr__orig_regy__create extends Xob_cmd__base {
	private boolean repo_0_is_remote = false;
	public Xobldr__orig_regy__create(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public void Cmd_run() {
		Db_conn conn = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Xob_orig_regy_tbl.Create_table(conn);
		Xowe_wiki commons_wiki = bldr.App().Wiki_mgr().Get_by_or_make(Xow_domain_itm_.Bry__commons).Init_assert();
		Xowe_wiki repo_0 = wiki, repo_1 = commons_wiki;
		if (repo_0_is_remote) {	// NOTE: default is false; local_wiki will be preferred over commons_wiki
			repo_0 = commons_wiki;
			repo_1 = wiki;
		}
		repo_0.Init_assert(); repo_1.Init_assert();
		Xob_db_file file_registry_db = Xob_db_file.New__page_regy(commons_wiki.Fsys_mgr().Root_dir());
		Xob_orig_regy_tbl.Create_data(bldr.Usr_dlg(), conn, file_registry_db, repo_0_is_remote, repo_0, repo_1, Xobldr__lnki_temp__create.Ns_file_is_case_match_all(wiki));
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repo_0_is_remote_))				this.repo_0_is_remote = m.ReadYn("v");
		else														return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_repo_0_is_remote_ = "repo_0_is_remote_";

	public static final String BLDR_CMD_KEY = "file.orig_regy";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final Xob_cmd Prototype = new Xobldr__orig_regy__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__orig_regy__create(bldr, wiki);}
}
