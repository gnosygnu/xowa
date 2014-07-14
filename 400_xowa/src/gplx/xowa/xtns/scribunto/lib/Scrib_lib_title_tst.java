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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
public class Scrib_lib_title_tst {
	@Before public void init() {
		fxt.Init_scrib_proc();
		lib = fxt.Core().Lib_title().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void NewTitle() {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("Page_0")				, ttl_data_(0	, "", "Page 0", "", "", "Page_0"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_newTitle, Object_.Ary("A", "Template")		, ttl_data_(10	, "Template", "A"));
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
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Module", "A")									, ttl_data_(828, "Module", "A"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary(828, "A")										, ttl_data_(828, "Module", "A"));
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "A", "b")							, ttl_data_(10, "Template", "A", "b"));
		fxt.Parser_fxt().Wiki().Xwiki_mgr().Add_full("fr", "fr.wikipedia.org");
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "A", "b", "fr")					, ttl_data_(0, "", "Template:A", "b", "fr"));
		fxt.Parser_fxt().Init_log_(Xop_ttl_log.Invalid_char);
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_title.Invk_makeTitle, Object_.Ary("Template", "a[b"), Scrib_invoke_func_fxt.Null_rslt_ary);	// PURPOSE: handle bad MakeTitle cmds; PAGE:en.w:Disney; DATE:2013-10-15
	}
	@Test   public void FileExists() {
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_title.Invk_fileExists, Object_.Ary("A")											, false);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_title.Invk_fileExists, Object_.Ary("Template:A")								, false);
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_title.Invk_fileExists, Object_.Ary("File:A.png")								, false);
		fxt.Parser_fxt().Init_page_create("File:A.png");
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_title.Invk_fileExists, Object_.Ary("File:A.png")								, true);
	}
	@Test   public void FileExists_commons() {	// PURPOSE: check that Scribunto FileExists calls filepath.FileExists; DATE:2014-01-07
		Xow_wiki commons_wiki = fxt.Parser_fxt().Wiki().App().Wiki_mgr().Get_by_key_or_make(Xow_wiki_.Domain_commons_bry).Init_assert();
		fxt.Parser_fxt().Init_page_create(commons_wiki, "File:A.png", "text_is_blank");
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_title.Invk_fileExists, Object_.Ary("File:A.png")								, true);
	}
	@Test   public void FileExists_media() {	// PURPOSE: [[Media:]] ns should find entries in [[File:]]; DATE:2014-01-07
		fxt.Parser_fxt().Init_page_create("File:A.png");
		fxt.Test_scrib_proc_bool(lib, Scrib_lib_title.Invk_fileExists, Object_.Ary("Media:A.png")								, true);
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
	private static String ttl_data_(int ns_id, String ns_str, String ttl) {return ttl_data_(ns_id, ns_str, ttl, "", "", ttl);}
	private static String ttl_data_(int ns_id, String ns_str, String ttl, String anchor) {return ttl_data_(ns_id, ns_str, ttl, anchor, "", ttl);}
	private static String ttl_data_(int ns_id, String ns_str, String ttl, String anchor, String xwiki) {return ttl_data_(ns_id, ns_str, ttl, anchor, xwiki, ttl);}
	private static String ttl_data_(int ns_id, String ns_str, String ttl, String anchor, String xwiki, String partial_url) {
		return String_.Concat_lines_nl_skip_last
		( "1="
		, "  isLocal=true"
		, "  isRedirect=false"
		, "  interwiki=" + xwiki
		, "  namespace=" + Int_.XtoStr(ns_id)
		, "  nsText=" + ns_str
		, "  text=" + ttl
		, "  id=0"
		, "  fragment=" + anchor
		, "  contentModel=wikitext"
		, "  thePartialUrl=" + partial_url
		, "  exists=false"
		, "  fileExists=false"
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
