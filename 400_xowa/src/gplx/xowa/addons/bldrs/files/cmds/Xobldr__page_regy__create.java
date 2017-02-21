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
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.addons.bldrs.files.dbs.*;
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
			Xob_page_regy_tbl.Create_data(bldr.Usr_dlg(), page_regy_provider, Xof_repo_tid_.Tid__remote, commons_wiki);
			Sqlite_engine_.Idx_create(usr_dlg, page_regy_provider, "repo_page", Xob_page_regy_tbl.Idx_main);
		}
		else {
			if (!Bry_.Eq(commons_wiki.Domain_bry(), wiki.Domain_bry())) {	// skip local wiki if cur wiki is commons
				wiki.Init_assert();
				Xob_page_regy_tbl.Delete_local(page_regy_provider);
				Xob_page_regy_tbl.Create_data(bldr.Usr_dlg(), page_regy_provider, Xof_repo_tid_.Tid__local, wiki);
			}
		}
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_build_commons_))				build_commons = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_build_commons_ = "build_commons_";

	public static final String BLDR_CMD_KEY = "file.page_regy";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__page_regy__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__page_regy__create(bldr, wiki);}
}
