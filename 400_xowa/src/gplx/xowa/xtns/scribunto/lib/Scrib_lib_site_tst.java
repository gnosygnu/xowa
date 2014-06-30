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
		fxt.Clear();
		fxt.Init_page("{{#invoke:Mod_0|Func_0}}");
		lib = fxt.Core().Lib_site().Init();
	}	Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); Scrib_lib lib;
	@Test   public void GetNsIndex() {
		fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_getNsIndex, Object_.Ary("Help"), "12");
		fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_getNsIndex, Object_.Ary("Helpx"), "");	// unknown ns; return empty String
	}
	@Test   public void UsersInGroup() {
		fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_usersInGroup, Object_.Ary("sysop"), "0"); // SELECT * FROM user_groups;
	}
	@Test   public void PagesInCategory() {
		fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_pagesInCategory, Object_.Ary("A"), "0");
	}
	@Test   public void PagesInNs() {
		fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_pagesInNs, Object_.Ary("12"), "0");
	}
//		@Test   public void LoadSiteStats() {	// deprecated by Scribunto; DATE:2013-04-12
//			fxt.Parser_fxt().Wiki().Stats().NumPages_(1).NumArticles_(2).NumFiles_(3).NumEdits_(4).NumViews_(5).NumUsers_(6).NumUsersActive_(7);
//			fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_loadSiteStats, Object_.Ary_empty, "1;2;3;4;5;6;7");
//		}
	@Test   public void Init_lib_site() {
		Xow_ns_mgr ns_mgr = new Xow_ns_mgr();
		ns_mgr.Add_new(Scrib_xtn_mgr.Ns_id_module, "Module");
		ns_mgr.Add_new(Scrib_xtn_mgr.Ns_id_module_talk, "Module talk");
		ns_mgr.Add_new(Xow_ns_.Id_special, "Special");
		ns_mgr.Add_new(Xow_ns_.Id_main, "");
		ns_mgr.Add_new(Xow_ns_.Id_talk, "Talk");
		ns_mgr.Init_w_defaults();
		Xow_wiki wiki = fxt.Parser_fxt().Wiki();
		fxt.Parser_fxt().Wiki().Stats().NumPages_(1).NumArticles_(2).NumFiles_(3).NumEdits_(4).NumViews_(5).NumUsers_(6).NumUsersActive_(7).NumAdmins_(8);
		wiki.Ns_mgr_(ns_mgr);
		fxt.Test_lib_proc(lib, Scrib_lib_site.Invk_init_site_for_wiki, Object_.Ary_empty, String_.Concat_lines_nl
		(	""
		,	"  Wikipedia;http://en.wikipedia.org;/wiki;/wiki/skins;1.21wmf11;"
		,	"    "	// namespaces
		,	"      -1;Special;Special;false;false;false;false;true;false;true;false;null;{};-1;"
		,	"      0;;;false;false;false;true;true;true;true;false;null;{};0;1;1;(Main);"
		,	"      1;Talk;Talk;false;false;false;false;true;true;false;true;null;{};1;1;0;"
		,	"      828;Module;Module;false;false;false;false;true;true;true;false;null;{};828;829;829;"
		,	"      829;Module talk;Module_talk;false;false;false;false;true;true;false;true;null;{};829;829;828;"
		,	"    1;2;3;4;5;6;7;8"
		));
	}
}	
