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
public class Xow_cfg_wiki_core_tst {
	Xow_cfg_wiki_core_fxt fxt = new Xow_cfg_wiki_core_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Save() {
		fxt.Save_tst(Xoa_app_.Version, "Main_Page", "Wikipedia|MediaWiki 1.21wmf5|first-letter|", 0, "User test", Const_wiki_core_cfg);
	}
	@Test  public void Load_and_save() {
		fxt.Load_and_save_tst(Const_wiki_core_cfg);
	}
	@Test  public void Load() {
		fxt.Load_tst(String_.Concat_lines_nl
		(	"ns_mgr.clear.load("
		,	"<:['"
		,	"0|0|"				// DEFECT: test that 0 sets case_match to case-sensitive; empty name was causing it to "default" to 1; DATE:2013-01-30
		,	""					// test to make sure blank line doesn't fail
		,	"4|1|Wikipedia"		// test to make sure that values are updated after blank line
		,	""
		,	"']:>"
		,	");"
		)
		,	fxt.ns_(Xow_ns_.Id_main		, true, "")
		,	fxt.ns_(Xow_ns_.Id_project	, false, "Wikipedia")
		);
	}
	public static final String Const_wiki_core_cfg = String_.Concat_lines_nl
	(	"props"
	,	".bldr_version_('" + Xoa_app_.Version + "')"
	,	".main_page_('Main_Page')"
	,	".siteinfo_misc_('Wikipedia|MediaWiki 1.21wmf5|first-letter|')"
	,	".siteinfo_mainpage_('')"
	,	";"
	,	"ns_mgr"
	,	".clear"
	,	".load("
	,	"<:['"
	,	"-2|1|Media"
	,	"-1|1|Special"
	,	"0|1|"
	,	"1|1|Talk"
	,	"2|0|User test"	// NOTE: intentionally changing this to "0|User test" to differ from existing
	,	"3|1|User talk"
	,	"4|1|Wikipedia"
	,	"5|1|Wikipedia talk"
	,	"6|1|File"
	,	"7|1|File talk"
	,	"8|1|MediaWiki"
	,	"9|1|MediaWiki talk"
	,	"10|1|Template"
	,	"11|1|Template talk"
	,	"12|1|Help"
	,	"13|1|Help talk"
	,	"14|1|Category"
	,	"15|1|Category talk"
	,	"100|1|Portal"
	,	"101|1|Portal talk"
	,	"108|1|Book"
	,	"109|1|Book talk"
	,	"828|1|Module"
	,	"829|1|Module talk"
	,	"']:>"
	,	");"
	);
}
class Xow_cfg_wiki_core_fxt {
	Xoae_app app; Xowe_wiki wiki;
	public Xowe_wiki Wiki() {return wiki;}
	public void Clear() {
		app = Xoa_app_fxt.app_();
		wiki = Xoa_app_fxt.wiki_tst_(app);
	}
	public void Save_tst(String bldr_version, String main_page, String siteinfo_misc, int ns_user_case_match, String ns_user_name, String expd) {
		wiki.Props().Bldr_version_(Bry_.new_ascii_(bldr_version)).Main_page_(Bry_.new_ascii_(main_page)).Siteinfo_misc_(Bry_.new_ascii_(siteinfo_misc));
		Xow_ns ns_user = wiki.Ns_mgr().Ids_get_or_null(Xow_ns_.Id_user);
		ns_user.Case_match_((byte)ns_user_case_match); ns_user.Name_bry_(Bry_.new_ascii_(ns_user_name));
		Tfds.Eq_str_lines(expd, String_.new_ascii_(wiki.Cfg_wiki_core().Build_gfs()));
	}
	public void Load_and_save_tst(String raw) {
		wiki.Cfg_wiki_core().Load(raw);
		Tfds.Eq_str_lines(raw, String_.new_ascii_(wiki.Cfg_wiki_core().Build_gfs()));
	}
	public Xow_ns ns_(int id, boolean case_match, String name) {return new Xow_ns(id, case_match ? Xow_ns_case_.Id_all : Xow_ns_case_.Id_1st, Bry_.new_utf8_(name), false);}
	public void Load_tst(String raw, Xow_ns... expd_ary) {
		wiki.Cfg_wiki_core().Load(raw);
		int expd_len = expd_ary.length;
		for (int i = 0; i < expd_len; i++) {
			Xow_ns expd = expd_ary[i];
			Xow_ns actl = wiki.Ns_mgr().Ids_get_or_null(expd.Id());
			Tfds.Eq(expd.Case_match(), actl.Case_match(), Int_.Xto_str(expd.Id()));
			Tfds.Eq(expd.Name_str(), actl.Name_str(), Int_.Xto_str(expd.Id()));
		}
	}
}
