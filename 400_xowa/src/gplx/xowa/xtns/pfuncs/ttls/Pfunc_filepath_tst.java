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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.dbs.*;
import gplx.xowa.tdbs.*;
import gplx.xowa.files.*; import gplx.xowa.files.exts.*; import gplx.xowa.files.origs.*;
import gplx.xowa.wikis.*;
public class Pfunc_filepath_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	private final Xofw_wiki_wkr_mock mock_wkr = new Xofw_wiki_wkr_mock(); 
	private Xowe_wiki en_wiki, commons_wiki;
	@Before public void init() {
		fxt.Reset();
		Io_mgr.I.InitEngine_mem();
		Db_conn_bldr.I.Reg_default_mem();
		Xoae_app app = fxt.App();
		en_wiki = fxt.Wiki();
		// Init_orig_mgr(en_wiki);
		commons_wiki = Xoa_app_fxt.wiki_(app, Xow_domain_.Domain_str_commons);
		mock_wkr.Clear_commons();	// assume all files are in repo 0
		en_wiki.File_mgr().Repo_mgr().Page_finder_(mock_wkr);
		commons_wiki.Db_mgr().Load_mgr().Clear();
		en_wiki.Db_mgr().Load_mgr().Clear();
		app.Wiki_mgr().Add(commons_wiki);
		app.File_mgr().Repo_mgr().Set("src_commons", "mem/xowa/file/commons/src/", commons_wiki.Domain_str());
		app.File_mgr().Repo_mgr().Set("trg_commons", "mem/xowa/file/commons/trg/", commons_wiki.Domain_str());
		en_wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src_commons"), Bry_.new_a7("trg_commons"));
		Io_mgr.I.CreateDir(Io_url_.new_dir_("mem/xowa/wiki/commons.wikimedia.org/ns/000/page/"));	// HACK: create page_dir so Scan_dirs_zip will not identify commons as zipped; FIX: remove; WHEN: after redoing commons.css download logic
	}
	@Test  public void Wiki_is_local() {
		fxt.Init_page_create(en_wiki, "File:A.png", "");
		mock_wkr.Redirect_("A.png", "A.png").Repo_idx_(0);
		fxt.Test_parse_tmpl_str_test("{{filepath:A.png}}", "{{test}}", "file:///mem/wiki/repo/trg/orig/7/0/A.png");
	}
	@Test  public void Wiki_is_commons() {
		fxt.Init_page_create(commons_wiki, "File:A.png", "");
		commons_wiki.Tdb_fsys_mgr().Tdb_dir_regy()[Xotdb_dir_info_.Tid_page].Ext_tid_(gplx.ios.Io_stream_.Tid_raw);	
		mock_wkr.Redirect_("A.png", "A.png").Repo_idx_(1);
		fxt.Test_parse_tmpl_str_test("{{filepath:A.png}}", "{{test}}", "file:///mem/xowa/file/commons/trg/orig/7/0/1/c/A.png");
	}
	@Test  public void Not_found() {
		fxt.Test_parse_tmpl_str_test("{{filepath:B.png}}", "{{test}}", "");
	}
	@Test  public void Invalid() {	// PURPOSE: handle invalid ttls; EX:w:Germicidin
		fxt.Init_log_(Xop_ttl_log.Invalid_char);
		fxt.Test_parse_tmpl_str_test("{{filepath:{{{ImageFile}}}}}", "{{test}}", "");
	}
//		private static void Init_orig_mgr(Xow_wiki wiki) {
//			Xof_orig_tbl orig_tbl = null;
//			wiki.File__orig_mgr().Init_by_wiki(wiki, Xof_fsdb_mode.new_v2_gui(), new Xof_orig_tbl[] {orig_tbl}, Xof_url_bldr.new_v2());
//		}
}
