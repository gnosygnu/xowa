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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
import gplx.dbs.*; import gplx.xowa2.files.commons.*; import gplx.xowa.wikis.data.*;
import gplx.fsdb.*; import gplx.xowa.wikis.*; import gplx.xowa.files.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.repos.*;
public class Scrib_lib_title_tst {
	@Before public void init() {
		Db_conn_bldr.I.Reg_default_mem();
		fxt.Clear_for_lib();
		fxt.Core().Wiki().File__fsdb_mode().Tid_v2_bld_y_();
		lib = fxt.Core().Lib_title().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void NewTitle() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("Page_0")				, ttl_fast(0	, "", "Page 0", "", "", "Page_0"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("A", "Template")		, ttl_fast(10	, "Template", "A"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("a[b")					, Scrib_invoke_func_fxt.Null_rslt_ary);	// invalid
	}
	@Test   public void GetUrl() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "fullUrl")							, "//en.wikipedia.org/wiki/Main_Page");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "fullUrl", "action=edit")			, "//en.wikipedia.org/wiki/Main_Page?action=edit");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "localUrl")							, "/wiki/Main_Page");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "canonicalUrl")						, "http://en.wikipedia.org/wiki/Main_Page");
		// fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "fullUrl", "", "http")			, "http://en.wikipedia.org/wiki/Main_Page");	// TODO
	}
	@Test  public void GetUrl__args_many() {	// PUPROSE: GetUrl sometimes passes in kvs for qry_args; fr.w:Wikip�dia:Image_du_jour/Date; DATE:2013-12-24
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "canonicalUrl", KeyVal_.Ary(KeyVal_.new_("action", "edit"), KeyVal_.new_("preload", "b"))), "http://en.wikipedia.org/wiki/Main_Page?action=edit&preload=b");
	}
	@Test   public void MakeTitle() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Module", "A")									, ttl_fast(828, "Module", "A"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary(828, "A")										, ttl_fast(828, "Module", "A"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "A", "b")							, ttl_fast(10, "Template", "A", "b"));
		fxt.Parser_fxt().Wiki().Xwiki_mgr().Add_full("fr", "fr.wikipedia.org");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "A", "b", "fr")					, ttl_fast(0, "", "Template:A", "b", "fr"));
		fxt.Parser_fxt().Init_log_(Xop_ttl_log.Invalid_char);
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "a[b"), Scrib_invoke_func_fxt.Null_rslt_ary);	// PURPOSE: handle bad MakeTitle cmds; PAGE:en.w:Disney; DATE:2013-10-15
	}
	@Test   public void GetExpensiveData_absent() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getExpensiveData, Object_.Ary("A")									, ttl_slow(Bool_.N, 0, Bool_.N));
	}
	@Test   public void GetExpensiveData_exists() {
		fxt.Parser_fxt().Init_page_create("A");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getExpensiveData, Object_.Ary("A")									, ttl_slow(Bool_.Y, 0, Bool_.N));
	}
	@Test   public void GetFileInfo() {
		Wiki_orig_tbl__create(fxt.Core().Wiki());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("A")											, file_info_absent());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("Template:A")								, file_info_absent());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("File:A.png")								, file_info_absent());
		fxt.Parser_fxt().Init_page_create("File:A.png");
		Wiki_orig_tbl__insert(fxt.Core().Wiki(), "A.png", 220, 200);
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("File:A.png")								, file_info_exists("A.png", 220, 200));
	}
	@Test   public void GetFileInfo_commons() {	// PURPOSE: check that Scribunto GetFileInfo calls filepath.FileExists; DATE:2014-01-07
		Xowe_wiki commons_wiki = fxt.Parser_fxt().Wiki().Appe().Wiki_mgr().Get_by_key_or_make(Xow_domain_.Domain_bry_commons).Init_assert();
		Wiki_orig_tbl__create(fxt.Core().Wiki());
		Wiki_orig_tbl__insert(fxt.Core().Wiki(), "A.png", 220, 200);
		fxt.Parser_fxt().Init_page_create(commons_wiki, "File:A.png", "text_is_blank");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("File:A.png")							, file_info_exists("A.png", 220, 200));
	}
	@Test   public void GetFileInfo_media() {	// PURPOSE: [[Media:]] ns should find entries in [[File:]]; DATE:2014-01-07
		Wiki_orig_tbl__create(fxt.Core().Wiki());
		Wiki_orig_tbl__insert(fxt.Core().Wiki(), "A.png", 220, 200);
		fxt.Parser_fxt().Init_page_create("File:A.png");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("Media:A.png")							, file_info_exists("A.png", 220, 200));
	}
	@Test   public void GetContent() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")											, Scrib_invoke_func_fxt.Null_rslt);
		fxt.Parser_fxt().Init_page_create("A", "test");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")											, "test");
	}
	@Test   public void GetContent_redirect() {// PURPOSE: GetContent should return source text for redirect, not target; PAGE:de.w:Wikipedia:Autorenportal DATE:2014-07-11
		fxt.Parser_fxt().Init_page_create("A", "#REDIRECT [[B]]");
		fxt.Parser_fxt().Init_page_create("B", "C");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")											, "#REDIRECT [[B]]");	// should not be "C"
	}
	@Test   public void ProtectionLevels() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_protectionLevels, Object_.Ary("A")									, "");
	}
	@Test   public void CascadingProtection() {
		fxt.Test_scrib_proc_obj(lib, Scrib_lib_title.Invk_cascadingProtection, Object_.Ary("A")									, Scrib_lib_title.CascadingProtection_rv);
	}
	private static void Wiki_orig_tbl__create(Xowe_wiki wiki) {
		Xowe_wiki_bldr.Create(wiki, 1, "dump.xml");
		Xowd_db_file text_db = wiki.Data_mgr__core_mgr().Dbs__make_by_tid(Xowd_db_file_.Tid_text); text_db.Tbl__text().Create_tbl();
		Fsdb_db_mgr__v2_bldr.I.Get_or_make(wiki, Bool_.Y);
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
	}
	private static void Wiki_orig_tbl__insert(Xowe_wiki wiki, String ttl_str, int w, int h) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		wiki.File__orig_mgr().Insert(Xof_repo_itm_.Repo_remote, ttl_bry, Xof_ext_.new_by_ttl_(ttl_bry).Id(), w, h, Bry_.Empty);
	}
//		private static void Init_page_regy(Xowe_wiki wiki, String ttl, int id, boolean is_redirect) {
//			String url_str = "test/en.wikipedia.org/wiki_page_regy";
//			Db_meta_tbl meta = new Xowd_page_tbl().new_meta();
//			Db_conn_pool.I.Set_mem(url_str, meta);
//			Db_conn_info url = Db_conn_info_.mem_(url_str);
//			Xowd_page_tbl tbl = new Xowd_page_tbl(Bool_.N, url);
//			tbl.Insert(id, ns_id, Bry_.new_u8(ttl), is_redirect, modified_on, page_len, random_int, text_db_id, html_db_id);
//		}
	private static String ttl_fast(int ns_id, String ns_str, String ttl) {return ttl_fast(ns_id, ns_str, ttl, "", "", ttl);}
	private static String ttl_fast(int ns_id, String ns_str, String ttl, String anchor) {return ttl_fast(ns_id, ns_str, ttl, anchor, "", ttl);}
	private static String ttl_fast(int ns_id, String ns_str, String ttl, String anchor, String xwiki) {return ttl_fast(ns_id, ns_str, ttl, anchor, xwiki, ttl);}
	private static String ttl_fast(int ns_id, String ns_str, String ttl, String anchor, String xwiki, String partial_url) {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  isLocal=true"
		, "  interwiki=" + xwiki
		, "  namespace=" + Int_.Xto_str(ns_id)
		, "  nsText=" + ns_str
		, "  text=" + ttl
		, "  fragment=" + anchor
		, "  thePartialUrl=" + partial_url
		, "  file=false"
		);
	}
	private static String ttl_slow(boolean exists, int ttl_id, boolean redirect) {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  isRedirect=" + Bool_.Xto_str_lower(redirect)
		, "  id=" + Int_.Xto_str(ttl_id)
		, "  contentModel=" + Scrib_lib_title.Key_wikitexet
		, "  exists=" + Bool_.Xto_str_lower(exists)
		);
	}
	private static String file_info_absent() {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  exists=false"
		);
	}
	private static String file_info_exists(String ttl, int w, int h) {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  exists=true"
		, "  width=" + Int_.Xto_str(w)
		, "  height=" + Int_.Xto_str(h)
		, "  pages=<<NULL>>"
		);
	}
}	
//0000:                                                     '' !=        '1=isLocal=True'
//0001: '  true;false;;828;Module;A;0;;wikitext;A;false;false' !=      'isRedirect=False'
//0002:                                                <<N/A>> !=            'interwiki='
//0003:                                                <<N/A>> !=         'namespace=828'
//0004:                                                <<N/A>> !=         'nsText=Module'
//0005:                                                <<N/A>> !=                'text=A'
//0006:                                                <<N/A>> !=                  'id=0'
//0007:                                                <<N/A>> !=             'fragment='
//0008:                                                <<N/A>> != 'contentModel=wikitext'
//0009:                                                <<N/A>> !=       'thePartialUrl=A'
//0010:                                                <<N/A>> !=          'exists=False'
//0011:                                                <<N/A>> !=      'fileExists=False'
