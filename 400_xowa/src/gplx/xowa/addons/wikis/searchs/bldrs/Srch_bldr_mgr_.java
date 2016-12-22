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
package gplx.xowa.addons.wikis.searchs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.addons.wikis.searchs.bldrs.cmds.*;
public class Srch_bldr_mgr_ {
	public static void Setup(Xowe_wiki wiki) {
		Xoae_app app = wiki.Appe();
		Xob_bldr bldr = app.Bldr();

		bldr.Cmd_mgr().Add_many(wiki, Xob_cmd_keys.Key_text_search_cmd);
		int page_rank_iterations = app.Cfg().Get_int_app_or("xowa.wiki.import.page_rank.iteration_max", 0);
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
