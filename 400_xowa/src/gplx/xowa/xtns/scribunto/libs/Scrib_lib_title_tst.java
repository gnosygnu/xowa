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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Scrib_lib_title_tst {
	private final    Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		gplx.dbs.Db_conn_bldr.Instance.Reg_default_mem();
		fxt.Clear();
		fxt.Core().Wiki().File__fsdb_mode().Tid__v2__bld__y_();
		lib = fxt.Core().Lib_title().Init();
	}
	@Test  public void NewTitle() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("Page_0")				, ttl_fast(0	, "", "Page 0", "", "", "Page_0"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("A", "Template")			, ttl_fast(10	, "Template", "A"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("a[b")					, Scrib_invoke_func_fxt.Null_rslt_ary);	// invalid
	}
	@Test  public void NewTitle_int() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary(1234)					, ttl_fast(0	, "", "1234", "", "", "1234"));
	}
	@Test  public void NewTitle__foreign() {// PURPOSE: must be local language's version; Russian "Шаблон" not English "Template"; PAGE:ru.w:Королевство_Нидерландов DATE:2016-11-23
		fxt.Core().Wiki().Ns_mgr().Ns_template().Name_bry_(Bry_.new_a7("Template_in_nonenglish_name"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("A", "Template")			, ttl_fast(10	, "Template_in_nonenglish_name", "A"));	// "Template_in_nonenglish_name" not "Template"
	}
	@Test   public void GetUrl() {
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "fullUrl")						, "//en.wikipedia.org/wiki/Main_Page");
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "fullUrl", "action=edit")			, "//en.wikipedia.org/wiki/Main_Page?action=edit");
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "localUrl")						, "/wiki/Main_Page");
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "canonicalUrl")					, "https://en.wikipedia.org/wiki/Main_Page");
		// fxt.Test_scrib_proc_str(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "fullUrl", "", "http")			, "http://en.wikipedia.org/wiki/Main_Page");	// TODO_OLD
	}
	@Test  public void GetUrl__args_many() {	// PUPROSE: GetUrl sometimes passes in kvs for qry_args; fr.w:Wikipédia:Image_du_jour/Date; DATE:2013-12-24
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getUrl, Object_.Ary("Main_Page", "canonicalUrl", Keyval_.Ary(Keyval_.new_("action", "edit"), Keyval_.new_("preload", "b"))), "https://en.wikipedia.org/wiki/Main_Page?action=edit&preload=b");
	}
	@Test   public void MakeTitle() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Module", "A")								, ttl_fast(828, "Module", "A"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary(828, "A")									, ttl_fast(828, "Module", "A"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "A", "b")						, ttl_fast(10, "Template", "A", "b"));
		fxt.Parser_fxt().Wiki().Xwiki_mgr().Add_by_atrs("fr", "fr.wikipedia.org");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "A", "b", "fr")					, ttl_fast(0, "", "Template:A", "b", "fr"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "a[b"), Scrib_invoke_func_fxt.Null_rslt_ary);	// PURPOSE: handle bad MakeTitle cmds; PAGE:en.w:Disney; DATE:2013-10-15
	}
	@Test   public void GetExpensiveData_absent() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getExpensiveData, Object_.Ary("A")									, ttl_slow(Bool_.N, 0, Bool_.N));
	}
	@Test   public void GetExpensiveData_exists() {
		fxt.Parser_fxt().Init_page_create("A");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getExpensiveData, Object_.Ary("A")									, ttl_slow(Bool_.Y, 0, Bool_.N));
	}
	@Test   public void GetFileInfo() {
		Wiki_orig_tbl__create(fxt.Core().Wiki());
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("A")										, file_info_absent());
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("Template:A")								, file_info_absent());
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("File:A.png")								, file_info_absent());
		fxt.Parser_fxt().Init_page_create("File:A.png");
		Wiki_orig_tbl__insert(fxt.Core().Wiki(), "A.png", 220, 200);
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("File:A.png")								, file_info_exists("A.png", 220, 200));
	}
	@Test   public void GetFileInfo_commons() {	// PURPOSE: check that Scribunto GetFileInfo calls filepath.FileExists; DATE:2014-01-07
		Xowe_wiki commons_wiki = fxt.Parser_fxt().Wiki().Appe().Wiki_mgr().Get_by_or_make(gplx.xowa.wikis.domains.Xow_domain_itm_.Bry__commons).Init_assert();
		Wiki_orig_tbl__create(fxt.Core().Wiki());
		Wiki_orig_tbl__insert(fxt.Core().Wiki(), "A.png", 220, 200);
		fxt.Parser_fxt().Init_page_create(commons_wiki, "File:A.png", "text_is_blank");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("File:A.png")							, file_info_exists("A.png", 220, 200));
	}
	@Test   public void GetFileInfo_media() {	// PURPOSE: [[Media:]] ns should find entries in [[File:]]; DATE:2014-01-07
		Wiki_orig_tbl__create(fxt.Core().Wiki());
		Wiki_orig_tbl__insert(fxt.Core().Wiki(), "A.png", 220, 200);
		fxt.Parser_fxt().Init_page_create("File:A.png");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_getFileInfo, Object_.Ary("Media:A.png")							, file_info_exists("A.png", 220, 200));
	}
	@Test   public void GetContent() {
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")										, Scrib_invoke_func_fxt.Null_rslt);

		fxt.Parser_fxt().Ctx().Wiki().Cache_mgr().Page_cache().Free_mem(true);
		fxt.Parser_fxt().Init_page_create("A", "test");
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")										, "test");
	}
	@Test   public void GetContent_redirect() {// PURPOSE: GetContent should return source text for redirect, not target; PAGE:de.w:Wikipedia:Autorenportal DATE:2014-07-11
		fxt.Parser_fxt().Init_page_create("A", "#REDIRECT [[B]]");
		fxt.Parser_fxt().Init_page_create("B", "C");
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")										, "#REDIRECT [[B]]");	// should not be "C"
	}
	@Test   public void GetContent__redirect_missing() {// PURPOSE: // page redirects to missing page; note that page.Missing == true and page.Redirected_src() != null; PAGE: en.w:Shah_Rukh_Khan; DATE:2016-05-02
		fxt.Parser_fxt().Init_page_create("A", "#REDIRECT [[B]]");
		fxt.Test__proc__objs__flat(lib, Scrib_lib_title.Invk_getContent, Object_.Ary("A")										, "#REDIRECT [[B]]");	// fails with null
	}
	@Test   public void ProtectionLevels() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_protectionLevels, Object_.Ary("A")									, String_.Concat_lines_nl_skip_last("1=", "  move=1=sysop", "  edit=1=sysop"));
	}
	@Test   public void CascadingProtection() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_cascadingProtection, Object_.Ary("A")								, Scrib_lib_title.CascadingProtection_rv);
	}
	@Test   public void RedirectTarget() {
		fxt.Parser_fxt().Init_page_create("A", "#REDIRECT [[B]]");
		fxt.Parser_fxt().Init_page_create("B", "C");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_redirectTarget, Object_.Ary("A")									, ttl_fast(0	, "", "B", "", "", "B"));
		fxt.Test__proc__objs__nest(lib, Scrib_lib_title.Invk_redirectTarget, Object_.Ary("A1")									, Scrib_invoke_func_fxt.Null_rslt_ary);
	}

	private static void Wiki_orig_tbl__create(Xowe_wiki wiki) {
		Xowe_wiki_.Create(wiki, 1, "dump.xml");
		gplx.xowa.wikis.data.Xow_db_file text_db = wiki.Data__core_mgr().Dbs__make_by_tid(gplx.xowa.wikis.data.Xow_db_file_.Tid__text); text_db.Tbl__text().Create_tbl();
		gplx.fsdb.Fsdb_db_mgr__v2_bldr.Get_or_make(wiki, Bool_.Y);
		wiki.File_mgr().Init_file_mgr_by_load(wiki);
	}
	private static void Wiki_orig_tbl__insert(Xowe_wiki wiki, String ttl_str, int w, int h) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		wiki.File__orig_mgr().Insert(gplx.xowa.files.repos.Xof_repo_tid_.Tid__remote, ttl_bry, gplx.xowa.files.Xof_ext_.new_by_ttl_(ttl_bry).Id(), w, h, Bry_.Empty);
	}
	private static String ttl_fast(int ns_id, String ns_str, String ttl) {return ttl_fast(ns_id, ns_str, ttl, "", "", ttl);}
	private static String ttl_fast(int ns_id, String ns_str, String ttl, String anchor) {return ttl_fast(ns_id, ns_str, ttl, anchor, "", ttl);}
	private static String ttl_fast(int ns_id, String ns_str, String ttl, String anchor, String xwiki) {return ttl_fast(ns_id, ns_str, ttl, anchor, xwiki, ttl);}
	private static String ttl_fast(int ns_id, String ns_str, String ttl, String anchor, String xwiki, String partial_url) {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  isLocal=true"
		, "  interwiki=" + xwiki
		, "  namespace=" + Int_.To_str(ns_id)
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
		, "  isRedirect=" + Bool_.To_str_lower(redirect)
		, "  id=" + Int_.To_str(ttl_id)
		, "  contentModel=" + Scrib_lib_title.Key_wikitext
		, "  exists=" + Bool_.To_str_lower(exists)
		);
	}
	private static String file_info_absent() {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  exists=false"
		, "  width=0"
		, "  height=0"
		);
	}
	private static String file_info_exists(String ttl, int w, int h) {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  exists=true"
		, "  width=" + Int_.To_str(w)
		, "  height=" + Int_.To_str(h)
		, "  pages=<<NULL>>"
		);
	}
}
