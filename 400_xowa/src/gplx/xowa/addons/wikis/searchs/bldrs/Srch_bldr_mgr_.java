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
package gplx.xowa.addons.wikis.searchs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
public class Srch_bldr_mgr_ {
	public static void Setup(Xowe_wiki wiki) {
		Xoae_app app = wiki.Appe();
		Xob_bldr bldr = app.Bldr();

		bldr.Cmd_mgr().Add_many(wiki, Xob_cmd_keys.Key_text_search_cmd);
		int page_rank_iterations = app.Cfg().Get_int_app_or("xowa.bldr.import.page_rank.iteration_max", 0);
		boolean page_rank_enabled = page_rank_iterations > 0;
		if (page_rank_enabled) {
			bldr.Cmd_mgr().Add(new gplx.xowa.bldrs.cmds.utils.Xob_download_cmd(bldr, wiki).Dump_type_(gplx.xowa.addons.bldrs.wmdumps.pagelinks.bldrs.Pglnk_bldr_cmd.Dump_type_key));
			bldr.Cmd_mgr().Add_many(wiki, gplx.xowa.addons.bldrs.wmdumps.pagelinks.bldrs.Pglnk_bldr_cmd.BLDR_CMD_KEY);
		}
		bldr.Cmd_mgr().Add(new Xobldr__page__page_score(bldr, wiki).Iteration_max_(page_rank_iterations));
		bldr.Cmd_mgr().Add(new Xobldr__link__link_score(bldr, wiki).Page_rank_enabled_(page_rank_enabled).Delete_plink_db_(Bool_.Y));
		bldr.Cmd_mgr().Add(new Xobldr__word__link_count(bldr, wiki));			
		bldr.Cmd_mgr().Add(new gplx.xowa.bldrs.cmds.utils.Xob_delete_cmd(bldr, wiki).Patterns_ary_("*pagelinks.sql", "*pagelinks.sql.gz", "*pagelinks.sqlite3"));
	}
}
