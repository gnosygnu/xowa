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
public class Scrib_lib_site_tst {
	@Before public void init() {
		fxt.Init_scrib_proc();
		lib = fxt.Core().Lib_site().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test   public void GetNsIndex() {
		fxt.Test_scrib_proc_int(lib, Scrib_lib_site.Invk_getNsIndex, Object_.Ary("Help"), 12);
	}
	@Test   public void GetNsIndex_invalid() {
		fxt.Test_scrib_proc_empty(lib, Scrib_lib_site.Invk_getNsIndex, Object_.Ary("Helpx"));	// unknown ns; return empty String
	}
	@Test   public void UsersInGroup() {
		fxt.Test_scrib_proc_int(lib, Scrib_lib_site.Invk_usersInGroup, Object_.Ary("sysop"), 0); // SELECT * FROM user_groups;
	}
	@Test   public void PagesInCategory() {
		fxt.Test_scrib_proc_int(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A"), 0);
	}
	@Test   public void PagesInNs() {
		fxt.Test_scrib_proc_int(lib, Scrib_lib_site.Invk_pagesInNs, Object_.Ary("12"), 0);
	}
	@Test   public void Init_lib_site() {
		Xow_ns_mgr ns_mgr = new Xow_ns_mgr(fxt.Core().Wiki().Lang().Case_mgr());
		ns_mgr.Add_new(Scrib_xtn_mgr.Ns_id_module, "Module");
		ns_mgr.Add_new(Scrib_xtn_mgr.Ns_id_module_talk, "Module talk");
		ns_mgr.Add_new(Xow_ns_.Id_special, "Special");
		ns_mgr.Add_new(Xow_ns_.Id_main, "");
		ns_mgr.Add_new(Xow_ns_.Id_talk, "Talk");
		ns_mgr.Init_w_defaults();
		Xow_wiki wiki = fxt.Parser_fxt().Wiki();
		fxt.Parser_fxt().Wiki().Stats().NumPages_(1).NumArticles_(2).NumFiles_(3).NumEdits_(4).NumViews_(5).NumUsers_(6).NumUsersActive_(7).NumAdmins_(8);
		wiki.Ns_mgr_(ns_mgr);
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_site.Invk_init_site_for_wiki, Object_.Ary_empty, String_.Concat_lines_nl_skip_last
		( "1="
		, "  siteName=Wikipedia"
		, "  server=http://en.wikipedia.org"
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
