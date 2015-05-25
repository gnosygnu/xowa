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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.dbs.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
class Xows_core {
	private final Xoae_wiki_mgr wiki_mgr;
	private final Hash_adp_bry cache_hash = Hash_adp_bry.cs_(); private final Hash_adp_bry cmd_hash = Hash_adp_bry.cs_();
	private boolean ask_for_upgrade = true; private final Hash_adp_bry upgraded_wikis = Hash_adp_bry.cs_();		
	public Xows_core(Xoae_wiki_mgr wiki_mgr) {this.wiki_mgr = wiki_mgr;}
	private final Xows_html_wkr html_wkr = new Xows_html_wkr();
	public Xows_db_cache Get_cache_or_new(byte[] key) {
		Xows_db_cache cache = (Xows_db_cache)cache_hash.Get_by_bry(key);
		if (cache == null) {
			cache = new Xows_db_cache();
			cache_hash.Add_bry_obj(key, cache);
		}
		return cache;
	}
	public void Search(Xow_wiki search_wiki, Xoae_page page, Xows_ui_qry qry) {
		// generate 1 cmd per wiki
		Xow_domain[] domain_ary = qry.Wiki_domains(); int domain_ary_len = domain_ary.length;
		for (int i = 0; i < domain_ary_len; ++i) {
			Xow_domain domain = domain_ary[i];
			try {
				Xowe_wiki wiki = wiki_mgr.Get_by_key_or_make(domain.Domain_bry()); wiki.Init_assert();
				Assert_page_count(wiki);
				Xows_ui_cmd cmd = new Xows_ui_cmd(this, qry, wiki, page, page.Tab_data().Close_mgr(), page.Tab_data().Tab().Html_itm());
				qry.Cmds__add(cmd);
			} catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "search:wiki failed; wiki=~{0} err=~{1}", domain.Domain_str(), Err_.Message_lang(e));}	// handle bad wikis, like "en.wikipedia.org-old"; DATE:2015-04-24
		}
		qry.Page_max_(Int_.MaxValue);
		// do search and generate html
		html_wkr.Init_by_wiki(search_wiki, search_wiki.Lang().Num_mgr(), qry);
		int cmds_len = qry.Cmds__len();
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		for (int i = 0; i < cmds_len; ++i) {
			Xows_ui_cmd cmd = qry.Cmds__get_at(i); byte[] cmd_key = cmd.Key();
			cmd_hash.Add_if_dupe_use_nth(cmd_key, cmd);
			boolean searching_db = cmd.Search();				
			html_wkr.Gen_tbl(tmp_bfr, cmd.Rslt(), cmd_key, cmd.Wiki().Domain_bry(), searching_db);
		}
		page.Data_raw_(html_wkr.Gen_page(tmp_bfr.Xto_bry_and_clear()));
	}
	public void Search_end(Xows_ui_cmd cmd) {
		cmd_hash.Del(cmd.Key());
	}
	public void Cancel(byte[] cmd_key) {
		Xows_ui_cmd cmd = (Xows_ui_cmd)cmd_hash.Get_by_bry(cmd_key);
		if (cmd == null) return;
		cmd.Cancel();
		cmd_hash.Del(cmd.Key());
	}
	private void Assert_page_count(Xowe_wiki wiki) {
		Xowd_db_file search_db = wiki.Data_mgr__core_mgr().Db__search();
		if (ask_for_upgrade
			&& wiki.App().App_type().Uid_is_gui()
			&& !search_db.Tbl__search_word().Ddl__page_count()
			&& !upgraded_wikis.Has(wiki.Domain_bry()) ) {
			ask_for_upgrade = false;
			upgraded_wikis.Add_as_key_and_val(wiki.Domain_bry());
			boolean ok = wiki.Appe().Gui_mgr().Kit().Ask_ok_cancel("", "", String_.Concat_lines_nl_skip_last
			( "XOWA would like to upgrade your search database for " + wiki.Domain_str() + "."
			, "* Press OK to upgrade. This may take a few minutes."
			, "* Press Cancel to skip. You will be able to search, but the results may be slower for multi-word searches (EX: 'The Earth')."
			, "If you cancel, XOWA will ask you to upgrade this wiki again next time you restart the application."
			));
			if (ok)
				search_db.Tbl__search_word().Ddl__page_count__add(search_db.Tbl__search_link(), search_db.Tbl__cfg());
		}
	}
}
