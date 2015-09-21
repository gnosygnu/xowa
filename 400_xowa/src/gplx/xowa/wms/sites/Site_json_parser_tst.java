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
package gplx.xowa.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
import org.junit.*; import gplx.langs.jsons.*; import gplx.xowa.nss.*;
public class Site_json_parser_tst {
	private final Site_json_parser_fxt fxt = new Site_json_parser_fxt();
	@Before public void init() {Gfo_usr_dlg_.I = Gfo_usr_dlg_.Test_console();}
	@After public void term() {Gfo_usr_dlg_.I = Gfo_usr_dlg_.Noop;}
	@Test  public void General() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'general':"
		, "  { 'mainpage': 'Main Page'"
		, "  , 'imagewhitelistenabled': ''"
		, "  , 'timeoffset': 0"
		, "  , 'thumblimits': "
		, "    [ 120"
		, "    , 150"
		, "    ]"
		, "  , 'imagelimits': "
		, "    [ "
		, "      { 'width': 320"
		, "      , 'height': 240"
		, "      }"
		, "    , "
		, "      { 'width': 640"
		, "      , 'height': 480"
		, "      }"
		, "    ]"
		, "  }"
		, "}"
		));
		fxt.Test_general(KeyVal_.new_("mainpage", "Main Page"), KeyVal_.new_("imagewhitelistenabled", ""), KeyVal_.new_("timeoffset", "0"), KeyVal_.new_("thumblimits", "120|150"), KeyVal_.new_("imagelimits", "320=240|640=480"));
	}
	@Test  public void Namespace() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'namespaces':"
		, "  { '0':"
		, "    { 'id': 0"
		, "    , 'case': 'first-letter'"
		, "    , 'content': ''"
		, "    , '*': ''"
		, "    }"
		, "  , '2': "
		, "   { 'id': 2"
		, "    , 'case': 'first-letter'"
		, "    , 'subpages': ''"
		, "    , 'canonical': 'User'"
		, "    , '*': 'User'"
		, "    }"
		, "  ,'4': "
		, "    { 'id': 4"
		, "    , 'case': 'first-letter'"
		, "    , 'subpages': ''"
		, "    , 'canonical': 'Project'"
		, "    , '*': 'Wikipedia'"
		, "    }"
		, "  ,'2600': "
		, "    { 'id': 2600"
		, "    , 'case': 'case-sensitive'"
		, "    , 'canonical': 'Topic'"
		, "    , 'defaultcontentmodel': 'flow-board'"
		, "    , '*': 'Topic'"
		, "    }"
		, "  }"
		, "}"
		));
		fxt.Test_namespace
		( fxt.Make_namespace(0		, Bool_.N, null			, ""			, Bool_.N, Bool_.Y, null)
		, fxt.Make_namespace(2		, Bool_.N, "User"		, "User"		, Bool_.Y, Bool_.N, null)
		, fxt.Make_namespace(4		, Bool_.N, "Project"	, "Wikipedia"	, Bool_.Y, Bool_.N, null)
		, fxt.Make_namespace(2600	, Bool_.Y, "Topic"		, "Topic"		, Bool_.N, Bool_.N, "flow-board")
		);
	}
	@Test  public void Statistic() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'statistics':"
		, "  { 'pages': 1"
		, "  , 'articles': 2"
		, "  , 'edits': 3"
		, "  , 'images': 4"
		, "  , 'users': 5"
		, "  , 'activeusers': 6"
		, "  , 'admins': 7"
		, "  , 'jobs': 8"
		, "  , 'queued-massmessages': 9"
		, "  }"
		, "}"
		));
		fxt.Test_statistic
		( fxt.Make_statistic(1, 2, 3, 4, 5, 6, 7, 8, 9)
		);
	}
	@Test  public void Interwikimap() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'interwikimap':"
		, "  ["
		, "    { 'prefix': 'aquariumwiki'"
		, "    , 'url': 'http://www.theaquariumwiki.com/$1'"
		, "    }"
		, "  , { 'prefix': 'ar'"
		, "    , 'local': ''"
		, "    , 'language': '\u0627\u0644\u0639\u0631\u0628\u064a\u0629'"
		, "    , 'url': 'https://ar.wikipedia.org/wiki/$1'"
		, "    , 'protorel': ''"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_interwikimap
		( fxt.Make_interwikimap("aquariumwiki"	, Bool_.N, null			, Bool_.N, "http://www.theaquariumwiki.com/$1"	, Bool_.N)
		, fxt.Make_interwikimap("ar"			, Bool_.Y, "العربية"	, Bool_.N, "https://ar.wikipedia.org/wiki/$1"	, Bool_.Y)
		);
	}
	@Test  public void Namespacealias() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'namespacealiases':"
		, "  [ "
		, "    { 'id': 4"
		, "    , '*': 'WP'"
		, "    }"
		, "  , "
		, "    { 'id': 7"
		, "    , '*': 'Image talk'"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_namespacealias
		( fxt.Make_namespacealias(4	,"WP")
		, fxt.Make_namespacealias(7	, "Image talk")
		);
	}
	@Test  public void Specialpagealias() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'specialpagealiases':"
		, "  [ "
		, "    { 'realname': 'BrokenRedirects'"
		, "    , 'aliases': "
		, "      [ 'BrokenRedirects'"
		, "      ]"
		, "    }"
		, "  , "
		, "    { 'realname': 'Lonelypages'"
		, "    , 'aliases': "
		, "      [ 'LonelyPages'"
		, "      , 'OrphanedPages'"
		, "      ]"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_specialpagealias
		( fxt.Make_specialpagealias("BrokenRedirects"	, "BrokenRedirects")
		, fxt.Make_specialpagealias("Lonelypages"		, "LonelyPages", "OrphanedPages")
		);
	}
	@Test  public void Library() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'libraries':"
		, "  [ "
		, "    { 'name': 'cssjanus/cssjanus'"
		, "    , 'version': '1.1.1'"
		, "    }"
		, "  , "
		, "    { 'name': 'leafo/lessphp'"
		, "    , 'version': '0.5.0'"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_library
		( fxt.Make_library("cssjanus/cssjanus"	, "1.1.1")
		, fxt.Make_library("leafo/lessphp"		, "0.5.0")
		);
	}
	@Test  public void Extension() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'extensions':"
		, "  [ "
		, "    { 'type': 'media'"
		, "    , 'name': 'PagedTiffHandler'"
		, "    , 'descriptionmsg': 'tiff-desc'"
		, "    , 'author': '[http://www.hallowelt.biz HalloWelt! Medienwerkstatt GmbH], Sebastian Ulbricht, Daniel Lynge, Marc Reymann, Markus Glaser for Wikimedia Deutschland'"
		, "    , 'url': 'https://www.mediawiki.org/wiki/Extension:PagedTiffHandler'"
		, "    , 'vcs-system': 'git'"
		, "    , 'vcs-version': 'b4a6c2077e3ea70cb0295b2282fe45d2e9ae06ba'"
		, "    , 'vcs-url': 'https://git.wikimedia.org/tree/mediawiki%2Fextensions%2FPagedTiffHandler.git/b4a6c2077e3ea70cb0295b2282fe45d2e9ae06ba'"
		, "    , 'vcs-date': '2015-03-27T16:44:25Z'"
		, "    , 'license-name': 'GPL-2.0+'"
		, "    , 'license': '/wiki/Special:Version/License/PagedTiffHandler'"
		, "    }"
		, "  , "
		, "    { 'type': 'media'"
		, "    , 'name': 'A'"
		, "    , 'namemsg': 'A-name'"
		, "    , 'description': 'desc'"
		, "    , 'descriptionmsg': 'A-desc'"
		, "    , 'author': 'B'"
		, "    , 'url': 'C'"
		, "    , 'version': '0.1.0'"
		, "    , 'vcs-system': 'git'"
		, "    , 'vcs-version': 'd'"
		, "    , 'vcs-url': 'e'"
		, "    , 'vcs-date': '2015-03-27T16:44:25Z'"
		, "    , 'license-name': 'f'"
		, "    , 'license': 'g'"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_extension
		( fxt.Make_extension("media", "PagedTiffHandler", "", "", "tiff-desc", "[http://www.hallowelt.biz HalloWelt! Medienwerkstatt GmbH], Sebastian Ulbricht, Daniel Lynge, Marc Reymann, Markus Glaser for Wikimedia Deutschland", "https://www.mediawiki.org/wiki/Extension:PagedTiffHandler", null, "git", "b4a6c2077e3ea70cb0295b2282fe45d2e9ae06ba", "https://git.wikimedia.org/tree/mediawiki%2Fextensions%2FPagedTiffHandler.git/b4a6c2077e3ea70cb0295b2282fe45d2e9ae06ba", "2015-03-27T16:44:25Z", "GPL-2.0+", "/wiki/Special:Version/License/PagedTiffHandler", null)
		, fxt.Make_extension("media", "A", "A-name", "desc", "A-desc", "B", "C", "0.1.0", "git", "d", "e", "2015-03-27T16:44:25Z", "f", "g", null)
		);
	}
	@Test  public void Skin() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'skins':"
		, "  [ "
		, "    { 'code': 'vector'"
		, "    , 'default': ''"
		, "    , '*': 'Vector'"
		, "    }"
		, "  , "
		, "    { 'code': 'monobook'"
		, "    , '*': 'MonoBook'"
		, "    }"
		, "  , "
		, "    { 'code': 'fallback'"
		, "    , 'unusable': ''"
		, "    , '*': 'Fallback'"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_skin
		( fxt.Make_skin("vector"		, Bool_.Y, "Vector"		, Bool_.N)
		, fxt.Make_skin("monobook"		, Bool_.N, "MonoBook"	, Bool_.N)
		, fxt.Make_skin("fallback"		, Bool_.N, "Fallback"	, Bool_.Y)
		);
	}
	@Test  public void Magicword() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'magicwords':"
		, "  [ "
		, "    { 'name': 'expr'"
		, "    , 'aliases': "
		, "      [ 'expr'"
		, "      ]"
		, "    }"
		, "  , "
		, "    { 'name': 'currentmonth'"
		, "    , 'aliases': "
		, "      [ 'CURRENTMONTH'"
		, "      , 'CURRENTMONTH2'"
		, "      ]"
		, "    , 'case-sensitive': ''"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_magicword
		( fxt.Make_magicword("expr"				, Bool_.N, "expr")
		, fxt.Make_magicword("currentmonth"		, Bool_.Y, "CURRENTMONTH", "CURRENTMONTH2")
		);
	}
	@Test  public void Functionhook() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'functionhooks':"
		, "  [ 'ns'"
		, "  , 'nse'"
		, "  ]"
		, "}"
		));
		fxt.Test_functionhook("ns", "nse");
	}
	@Test  public void Showhook() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'showhooks':"
		, "  [ "
		, "    { 'name': 'APIAfterExecute'"
		, "    , 'subscribers': "
		, "      [ 'ApiParseExtender::onAPIAfterExecute'"
		, "      , 'ZeroBanner\\\\MccMncLogging::onAPIAfterExecute'"
		, "      , 'XAnalytics::onAPIAfterExecute'"
		, "      ]"
		, "    }"
		, "  , "
		, "    { 'name': 'ParserLimitReport'"
		, "    , 'subscribers': "
		, "      { 'scribunto': 'ScribuntoHooks::reportLimits'"
		, "      }"
		, "    }"
		, "  ]"
		, "}"		
		));
		fxt.Test_showhook
		( fxt.Make_showhook("APIAfterExecute"		, "", "ApiParseExtender::onAPIAfterExecute", "ZeroBanner\\MccMncLogging::onAPIAfterExecute", "XAnalytics::onAPIAfterExecute")
		, fxt.Make_showhook("ParserLimitReport"		, "ScribuntoHooks::reportLimits")
		);
	}
	@Test  public void Extensiontag() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'extensiontags':"
		, "  [ '<pre>'"
		, "  , '<nowiki>'"
		, "  ]"
		, "}"
		));
		fxt.Test_extensiontag("<pre>", "<nowiki>");
	}
	@Test  public void Protocol() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'protocols':"
		, "  [ 'bitcoin:'"
		, "  , 'ftp://'"
		, "  ]"
		, "}"
		));
		fxt.Test_protocol("bitcoin:", "ftp://");
	}
	@Test  public void Defaultoption() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'defaultoptions':"
		, "  { 'globaluserpage': true"
		, "  , 'cols': 80"
		, "  , 'echo-email-format': 'html'"
		, "  }"
		, "}"
		));
		fxt.Test_defaultoption(KeyVal_.new_("globaluserpage", "true"), KeyVal_.new_("cols", "80"), KeyVal_.new_("echo-email-format", "html"));
	}
	@Test  public void Language() {
		fxt.Exec_parse(String_.Concat_lines_nl_skip_last
		( "{ 'languages':"
		, "  [ "
		, "    { 'code': 'aa'"
		, "    , '*': 'Qaf\u00e1r af'"
		, "    }"
		, "  , "
		, "    { 'code': 'ab'"
		, "    , '*': '\u0410\u04a7\u0441\u0448\u04d9\u0430'"
		, "    }"
		, "  ]"
		, "}"
		));
		fxt.Test_language
		( fxt.Make_language("aa"	, "Qafár af")
		, fxt.Make_language("ab"	, "Аҧсшәа")
		);
	}
//		@Test   public void Smoke() {
//			Io_url json_url = Tfds.RscDir.GenSubFil_nest("400_xowa", "site_meta__en.wikipedia.org.json");
//			byte[] src = Io_mgr.I.LoadFilBry(json_url);
//			Site_json_parser parser = new Site_json_parser();
//			Site_meta_itm meta_itm = new Site_meta_itm();
//			parser.Parse_root(meta_itm, "en.wikipedia.org", src);
//			gplx.dbs.Db_conn_bldr.I.Reg_default_sqlite();
//			Site_core_db core_db = new Site_core_db(Tfds.RscDir.GenSubFil_nest("400_xowa", "site_meta.sqlite3"));
//			core_db.Save(meta_itm, Bry_.new_a7("en.w"));
//		}
}
class Site_json_parser_fxt {
	private final Json_parser json_parser = new Json_parser();
	private final Site_json_parser site_meta_parser;
	private Site_meta_itm site_meta;
	public Site_json_parser_fxt() {
		this.site_meta_parser = new Site_json_parser(json_parser);
	}
	public void Exec_parse(String raw) {
		Json_doc jdoc = json_parser.Parse_by_apos(raw);
		site_meta = new Site_meta_itm();
		site_meta_parser.Parse_root(site_meta, "en.wikipedia.org", jdoc.Root_nde());
	}
	public Site_namespace_itm Make_namespace(int id, boolean case_tid_is_cs, String canonical, String localized, boolean subpages, boolean content, String defaultcontentmodel) {
		return new Site_namespace_itm(id, case_tid_is_cs ? Xow_ns_case_.Bry__all : Xow_ns_case_.Bry__1st, Bry_.new_u8_safe(canonical), Bry_.new_u8_safe(localized), subpages, content, Bry_.new_u8_safe(defaultcontentmodel));
	}
	public Site_statistic_itm Make_statistic(long pages, long articles, long edits, long images, long users, long activeusers, long admins, long jobs, long queued_massmessages) {return new Site_statistic_itm().Ctor(pages, articles, edits, images, users, activeusers, admins, jobs, queued_massmessages);}
	public Site_interwikimap_itm Make_interwikimap(String prefix, boolean local, String language, boolean localinterwiki, String url, boolean protorel) {return new Site_interwikimap_itm(Bry_.new_u8_safe(prefix), local, Bry_.new_u8_safe(language), localinterwiki, Bry_.new_u8_safe(url), protorel);}
	public Site_namespacealias_itm Make_namespacealias(int id, String alias) {return new Site_namespacealias_itm(id, Bry_.new_u8_safe(alias));}
	public Site_specialpagealias_itm Make_specialpagealias(String realname, String... aliases) {return new Site_specialpagealias_itm(Bry_.new_u8_safe(realname), Bry_.Ary(aliases));}
	public Site_library_itm Make_library(String name, String version) {return new Site_library_itm(Bry_.new_u8_safe(name), Bry_.new_u8_safe(version));}
	public Site_extension_itm Make_extension
		( String type, String name, String namemsg, String description, String descriptionmsg, String author, String url, String version
		, String vcs_system, String vcs_version, String vcs_url, String vcs_date, String license_name, String license, String credits) {
		return new Site_extension_itm
		( Bry_.new_u8_safe(type), Bry_.new_u8_safe(name), Bry_.new_u8_safe(namemsg), Bry_.new_u8_safe(description), Bry_.new_u8_safe(descriptionmsg), Bry_.new_u8_safe(author), Bry_.new_u8_safe(url), Bry_.new_u8_safe(version)
		, Bry_.new_u8_safe(vcs_system), Bry_.new_u8_safe(vcs_version), Bry_.new_u8_safe(vcs_url), Bry_.new_u8_safe(vcs_date), Bry_.new_u8_safe(license_name), Bry_.new_u8_safe(license), Bry_.new_u8_safe(credits)
		);
	}
	public Site_skin_itm Make_skin(String code, boolean dflt, String name, boolean unusable) {return new Site_skin_itm(Bry_.new_u8_safe(code), dflt, Bry_.new_u8_safe(name), unusable);}
	public Site_magicword_itm Make_magicword(String name, boolean case_match, String... aliases) {return new Site_magicword_itm(Bry_.new_u8_safe(name), case_match, Bry_.Ary(aliases));}
	public Site_showhook_itm Make_showhook(String name, String scribunto, String... subscribers) {return new Site_showhook_itm(Bry_.new_u8_safe(name), Bry_.new_u8_safe(scribunto), Bry_.Ary(subscribers));}
	public Site_language_itm Make_language(String code, String name) {return new Site_language_itm(Bry_.new_u8_safe(code), Bry_.new_u8_safe(name));}
	public void Test_general(KeyVal... expd) {Tfds.Eq_ary_str(expd, (KeyVal[])site_meta.General_list().To_ary(KeyVal.class));}
	public void Test_namespace(Site_namespace_itm... expd) {Tfds.Eq_ary_str(expd, (Site_namespace_itm[])site_meta.Namespace_list().To_ary(Site_namespace_itm.class));}
	public void Test_statistic(Site_statistic_itm expd) {Tfds.Eq_str_intf(expd, site_meta.Statistic_itm());}
	public void Test_interwikimap(Site_interwikimap_itm... expd) {Tfds.Eq_ary_str(expd, (Site_interwikimap_itm[])site_meta.Interwikimap_list().To_ary(Site_interwikimap_itm.class));}
	public void Test_namespacealias(Site_namespacealias_itm... expd) {Tfds.Eq_ary_str(expd, (Site_namespacealias_itm[])site_meta.Namespacealias_list().To_ary(Site_namespacealias_itm.class));}
	public void Test_specialpagealias(Site_specialpagealias_itm... expd) {Tfds.Eq_ary_str(expd, (Site_specialpagealias_itm[])site_meta.Specialpagealias_list().To_ary(Site_specialpagealias_itm.class));}
	public void Test_library(Site_library_itm... expd) {Tfds.Eq_ary_str(expd, (Site_library_itm[])site_meta.Library_list().To_ary(Site_library_itm.class));}
	public void Test_extension(Site_extension_itm... expd) {Tfds.Eq_ary_str(expd, (Site_extension_itm[])site_meta.Extension_list().To_ary(Site_extension_itm.class));}
	public void Test_skin(Site_skin_itm... expd) {Tfds.Eq_ary_str(expd, (Site_skin_itm[])site_meta.Skin_list().To_ary(Site_skin_itm.class));}
	public void Test_magicword(Site_magicword_itm... expd) {Tfds.Eq_ary_str(expd, (Site_magicword_itm[])site_meta.Magicword_list().To_ary(Site_magicword_itm.class));}
	public void Test_functionhook(String... expd) {Tfds.Eq_ary_str(expd, String_.Ary((byte[][])site_meta.Functionhook_list().To_ary(byte[].class)));}
	public void Test_showhook(Site_showhook_itm... expd) {Tfds.Eq_ary_str(expd, (Site_showhook_itm[])site_meta.Showhook_list().To_ary(Site_showhook_itm.class));}
	public void Test_extensiontag(String... expd) {Tfds.Eq_ary_str(expd, String_.Ary((byte[][])site_meta.Extensiontag_list().To_ary(byte[].class)));}
	public void Test_protocol(String... expd) {Tfds.Eq_ary_str(expd, String_.Ary((byte[][])site_meta.Protocol_list().To_ary(byte[].class)));}
	public void Test_defaultoption(KeyVal... expd) {Tfds.Eq_ary_str(expd, (KeyVal[])site_meta.Defaultoption_list().To_ary(KeyVal.class));}
	public void Test_language(Site_language_itm... expd) {Tfds.Eq_ary_str(expd, (Site_language_itm[])site_meta.Language_list().To_ary(Site_language_itm.class));}
}
