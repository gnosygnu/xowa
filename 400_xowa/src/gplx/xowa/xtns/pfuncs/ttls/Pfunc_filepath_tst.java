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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.dbs.*;
import gplx.xowa.wikis.tdbs.*;
import gplx.xowa.files.*; import gplx.xowa.files.exts.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.repos.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.ttls.*;
public class Pfunc_filepath_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	private final    Xofw_wiki_wkr_mock mock_wkr = new Xofw_wiki_wkr_mock(); 
	private Xowe_wiki en_wiki, commons_wiki;
	@Before public void init() {
		fxt.Reset();
		Io_mgr.Instance.InitEngine_mem();
		Db_conn_bldr.Instance.Reg_default_mem();
		Xoae_app app = fxt.App();
		en_wiki = fxt.Wiki();
		// Init_orig_mgr(en_wiki);
		commons_wiki = Xoa_app_fxt.Make__wiki__edit(app, Xow_domain_itm_.Str__commons);
		mock_wkr.Clear_commons();	// assume all files are in repo 0
		en_wiki.File_mgr().Repo_mgr().Page_finder_(mock_wkr);
		commons_wiki.Db_mgr().Load_mgr().Clear();
		en_wiki.Db_mgr().Load_mgr().Clear();
		app.Wiki_mgr().Add(commons_wiki);
		app.File_mgr().Repo_mgr().Set("src_commons", "mem/xowa/file/commons/src/", commons_wiki.Domain_str());
		app.File_mgr().Repo_mgr().Set("trg_commons", "mem/xowa/file/commons/trg/", commons_wiki.Domain_str());
		en_wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src_commons"), Bry_.new_a7("trg_commons"));
		Io_mgr.Instance.CreateDir(Io_url_.new_dir_("mem/xowa/wiki/commons.wikimedia.org/ns/000/page/"));	// HACK: create page_dir so Scan_dirs_zip will not identify commons as zipped; FIX: remove; WHEN: after redoing commons.css download logic
	}
	@Test  public void Wiki_is_local() {
		fxt.Init_page_create(en_wiki, "File:A.png", "");
		mock_wkr.Redirect_("A.png", "A.png").Repo_idx_(0);
		fxt.Test_parse_tmpl_str_test("{{filepath:A.png}}", "{{test}}", "file:///mem/wiki/repo/trg/orig/7/0/A.png");
	}
	@Test  public void Wiki_is_commons() {
		fxt.Init_page_create(commons_wiki, "File:A.png", "");
		commons_wiki.Tdb_fsys_mgr().Tdb_dir_regy()[Xotdb_dir_info_.Tid_page].Ext_tid_(gplx.core.ios.streams.Io_stream_tid_.Tid__raw);	
		mock_wkr.Redirect_("A.png", "A.png").Repo_idx_(1);
		fxt.Test_parse_tmpl_str_test("{{filepath:A.png}}", "{{test}}", "file:///mem/xowa/file/commons/trg/orig/7/0/1/c/A.png");
	}
	@Test  public void Not_found() {
		fxt.Test_parse_tmpl_str_test("{{filepath:B.png}}", "{{test}}", "");
	}
	@Test  public void Invalid() {	// PURPOSE: handle invalid ttls; EX:w:Germicidin
		fxt.Test_parse_tmpl_str_test("{{filepath:{{{ImageFile}}}}}", "{{test}}", "");
	}
//		private static void Init_orig_mgr(Xow_wiki wiki) {
//			Xof_orig_tbl orig_tbl = null;
//			wiki.File__orig_mgr().Init_by_wiki(wiki, Xof_fsdb_mode.New__v2__gui(), new Xof_orig_tbl[] {orig_tbl}, Xof_url_bldr.new_v2());
//		}
}
