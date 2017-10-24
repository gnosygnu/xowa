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
import org.junit.*; import gplx.xowa.wikis.nss.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Scrib_lib_site_tst {
	private final    Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = fxt.Core().Lib_site().Init();
	}
	@Test   public void GetNsIndex__valid() {
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_getNsIndex, Object_.Ary("Help"), 12);
	}
	@Test   public void GetNsIndex__invalid() {
		fxt.Test__proc__objs__empty(lib, Scrib_lib_site.Invk_getNsIndex, Object_.Ary("Helpx"));	// unknown ns; return empty String
	}
	@Test   public void UsersInGroup() {
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_usersInGroup, Object_.Ary("sysop"), 0); // SELECT * FROM user_groups;
	}
	@Test   public void PagesInCategory__invalid() {
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A|"), 0);
	}
	@Test   public void PagesInCategory__exists() {
		gplx.xowa.addons.wikis.ctgs.Xoax_ctg_addon.Get(fxt.Core().Wiki()).Itms__add(Bry_.new_a7("A"), 3, 2, 1);
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A", "pages")	, 3);
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A", "subcats")	, 2);
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A", "files")	, 1);
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A", "all")		, 6);
		fxt.Test__proc__objs__nest(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A", "*")	, String_.Concat_lines_nl_skip_last
		( "1="
		, "  all=6"
		, "  pages=3"
		, "  subcats=2"
		, "  files=1"
		));
	}
	@Test   public void PagesInNs() {
		fxt.Test__proc__ints(lib, Scrib_lib_site.Invk_pagesInNs, Object_.Ary("12"), 0);
	}
	@Test   public void InterwikiMap() {
		String key = "scribunto.interwikimap.en.wikipedia.org.!local";
		fxt.Core().Wiki().Cache_mgr().Misc_cache().Add(key, new Keyval[] 
		{ Keyval_.new_("en"
		, Scrib_lib_site.InterwikiMap_itm("en.wikipedia.org", false, "https://en.wikipedia.org/wiki/$1"))
		});
		fxt.Test__proc__objs__nest(lib, Scrib_lib_site.Invk_interwikiMap, Object_.Ary("!local"), String_.Concat_lines_nl_skip_last
		( "1="
		, "  en="
		, "    prefix=en.wikipedia.org"
		, "    url=https://en.wikipedia.org/wiki/$1"
		, "    isProtocolRelative=false"
		, "    isLocal=false"
		, "    isTranscludable=false"
		, "    isCurrentWiki=false"
		, "    isExtraLanguageLink=false"
		));
	}
	@Test   public void Init_lib_site() {
		Xowe_wiki wiki = fxt.Core().Wiki();
		wiki.Stats().Load_by_db(1, 2, 3, 4, 5, 6, 7, 8);
		wiki.Ns_mgr()
			.Clear()
			.Add_new(Xow_ns_.Tid__module		, Xow_ns_.Key__module)
			.Add_new(Xow_ns_.Tid__module_talk	, Xow_ns_.Key__module_talk)
			.Add_new(Xow_ns_.Tid__special		, Xow_ns_.Key__special)
			.Add_new(Xow_ns_.Tid__main			, "")
			.Add_new(Xow_ns_.Tid__talk			, Xow_ns_.Key__talk)
			.Init_w_defaults()
			;
		fxt.Test__proc__objs__nest(lib, Scrib_lib_site.Invk_init_site_for_wiki, Object_.Ary_empty, String_.Concat_lines_nl_skip_last
		( "1="
		, "  siteName=Wikipedia"
		, "  server=//en.wikipedia.org"
		, "  scriptPath=/wiki"
		, "  stylePath=/wiki/skins"
		, "  currentVersion=1.21wmf11"
		, "  namespaces="
		, "    -1="
		, "      id=-1"
		, "      name=Special"
		, "      canonicalName=Special"
		, "      hasSubpages=false"
		, "      hasGenderDistinction=false"
		, "      isCapitalized=false"
		, "      isContent=false"
		, "      isIncludable=true"
		, "      isMovable=false"
		, "      isSubject=true"
		, "      isTalk=false"
		, "      defaultContentModel=<<NULL>>"
		, "      aliases="
		, "      subject=-1"
		, "    0="
		, "      id=0"
		, "      name="
		, "      canonicalName="
		, "      hasSubpages=false"
		, "      hasGenderDistinction=false"
		, "      isCapitalized=false"
		, "      isContent=true"
		, "      isIncludable=true"
		, "      isMovable=true"
		, "      isSubject=true"
		, "      isTalk=false"
		, "      defaultContentModel=<<NULL>>"
		, "      aliases="
		, "      subject=0"
		, "      talk=1"
		, "      associated=1"
		, "      displayName=(Main)"
		, "    1="
		, "      id=1"
		, "      name=Talk"
		, "      canonicalName=Talk"
		, "      hasSubpages=false"
		, "      hasGenderDistinction=false"
		, "      isCapitalized=false"
		, "      isContent=false"
		, "      isIncludable=true"
		, "      isMovable=true"
		, "      isSubject=false"
		, "      isTalk=true"
		, "      defaultContentModel=<<NULL>>"
		, "      aliases="
		, "      subject=1"
		, "      talk=1"
		, "      associated=0"
		, "    828="
		, "      id=828"
		, "      name=Module"
		, "      canonicalName=Module"
		, "      hasSubpages=false"
		, "      hasGenderDistinction=false"
		, "      isCapitalized=false"
		, "      isContent=false"
		, "      isIncludable=true"
		, "      isMovable=true"
		, "      isSubject=true"
		, "      isTalk=false"
		, "      defaultContentModel=<<NULL>>"
		, "      aliases="
		, "      subject=828"
		, "      talk=829"
		, "      associated=829"
		, "    829="
		, "      id=829"
		, "      name=Module talk"
		, "      canonicalName=Module_talk"
		, "      hasSubpages=false"
		, "      hasGenderDistinction=false"
		, "      isCapitalized=false"
		, "      isContent=false"
		, "      isIncludable=true"
		, "      isMovable=true"
		, "      isSubject=false"
		, "      isTalk=true"
		, "      defaultContentModel=<<NULL>>"
		, "      aliases="
		, "      subject=829"
		, "      talk=829"
		, "      associated=828"
		, "  stats="
		, "    pages=1"
		, "    articles=2"
		, "    files=3"
		, "    edits=4"
		, "    views=5"
		, "    users=6"
		, "    activeUsers=7"
		, "    admins=8"
		));
	}
//		@Test   public void LoadSiteStats() {	// deprecated by Scribunto; DATE:2013-04-12
//			fxt.Parser_fxt().Wiki().Stats().NumPages_(1).NumArticles_(2).NumFiles_(3).NumEdits_(4).NumViews_(5).NumUsers_(6).NumUsersActive_(7);
//			fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_site.Invk_loadSiteStats, Object_.Ary_empty, "1;2;3;4;5;6;7");
//		}
}	
