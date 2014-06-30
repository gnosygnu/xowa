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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Pf_url_filepath_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {
		fxt.Reset();
		Io_mgr._.InitEngine_mem();
		Xoa_app app = fxt.App(); en_wiki = fxt.Wiki();
		mock_wkr.Clear_commons();	// assume all files are in repo 0
		en_wiki.File_mgr().Repo_mgr().Page_finder_(mock_wkr);
		commons_wiki = Xoa_app_fxt.wiki_(app, Xow_wiki_.Domain_commons_str);
		commons_wiki.Db_mgr().Load_mgr().Clear();
		en_wiki.Db_mgr().Load_mgr().Clear();
		app.Wiki_mgr().Add(commons_wiki);
		app.File_mgr().Repo_mgr().Set("src_commons", "mem/xowa/file/commons/src/", commons_wiki.Domain_str()).Ext_rules_(Xoft_rule_grp.Grp_app_default);
		app.File_mgr().Repo_mgr().Set("trg_commons", "mem/xowa/file/commons/trg/", commons_wiki.Domain_str()).Ext_rules_(Xoft_rule_grp.Grp_app_default);
		en_wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_utf8_("src_commons"), Bry_.new_utf8_("trg_commons"));
		Io_mgr._.CreateDir(Io_url_.new_dir_("mem/xowa/wiki/commons.wikimedia.org/ns/000/page/"));	// HACK: create page_dir so Scan_dirs_zip will not identify commons as zipped; FIX: remove; WHEN: after redoing commons.css download logic
	}	private Xow_wiki en_wiki, commons_wiki; Xofw_wiki_wkr_mock mock_wkr = new Xofw_wiki_wkr_mock(); 
	@Test  public void Wiki_is_local() {
		fxt.Init_page_create(en_wiki, "File:A.png", "");
		mock_wkr.Redirect_("A.png", "A.png").Repo_idx_(0);
		fxt.Test_parse_tmpl_str_test("{{filepath:A.png}}", "{{test}}", "file:///mem/wiki/repo/trg/orig/7/0/A.png");
	}
	@Test  public void Wiki_is_commons() {
		fxt.Init_page_create(commons_wiki, "File:A.png", "");
		commons_wiki.Fsys_mgr().Dir_regy()[Xow_dir_info_.Tid_page].Ext_tid_(gplx.ios.Io_stream_.Tid_file);	
		mock_wkr.Redirect_("A.png", "A.png").Repo_idx_(1);
		fxt.Test_parse_tmpl_str_test("{{filepath:A.png}}", "{{test}}", "file:///mem/xowa/file/commons/trg/orig/7/0/1/c/A.png");
	}
	@Test  public void Not_found() {
		fxt.Test_parse_tmpl_str_test("{{filepath:B.png}}", "{{test}}", "");
	}
	@Test  public void Invalid() {	// PURPOSE: handle invalid ttls; EX:w:Germicidin
		fxt.Test_parse_tmpl_str_test("{{filepath:{{{ImageFile}}}}}", "{{test}}", "");
	}
}
