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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.dbs.cfgs.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.data.*;
public class Xob_term_cmd extends Xob_term_base {
	public Xob_term_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Ctor(bldr, wiki); this.wiki = wiki;} private Xowe_wiki wiki;
	@Override public String Cmd_key() {return KEY;} public static final    String KEY = "text.term";
	@Override public void Cmd_end_hook() {
		// delete wiki's temp dir
		Io_mgr.Instance.DeleteDirDeep(wiki.Fsys_mgr().Tmp_dir());

		// build fsdb
		gplx.fsdb.Fsdb_db_mgr__v2_bldr.Get_or_make(wiki, false);// always build file.user db; DATE:2015-05-12

		// dansguardian
		if (wiki.App().Cfg().Get_bool_wiki_or(wiki, gplx.xowa.bldrs.filters.dansguardians.Dg_match_mgr.Cfg__enabled, false))
			new Xob_page_delete_cmd(wiki.Appe().Bldr(), wiki).Cmd_run();

		wiki.Data__core_mgr().Rls();
	}
}
