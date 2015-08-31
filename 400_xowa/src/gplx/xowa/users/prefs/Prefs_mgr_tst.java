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
package gplx.xowa.users.prefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import org.junit.*;
import gplx.gfui.*; import gplx.xowa.apps.*;
import gplx.xowa.gui.views.*;
public class Prefs_mgr_tst {
	private Prefs_mgr_fxt fxt = new Prefs_mgr_fxt();
	@Before public void init() {fxt.Clear();}	
	@Test   public void Get_basic_pass() {
		fxt.Exec_get("<input xowa_prop='app.user.name'></input> text", "<input xowa_prop='app.user.name' id='xowa_prop_0' value='test_user'></input> text");
	}
	@Test   public void Get_basic_fail() {
		fxt.Exec_get("<input xowa_prop='fail.prop'></input>", "<input xowa_prop='fail.prop' id='xowa_prop_0' value='Error'></input>");
	}
	@Test   public void Get_eval_pass() {
		fxt.Exec_get("~{<>app.user.name;<>}", "test_user");
	}
	@Test   public void Get_eval_fail() {
		fxt.Exec_get_has("~{<>fail.prop;<>}", "~{<>fail.prop;<>}[err 0] <gplx> key not found: key=fail");
	}
	@Test   public void Set() {
		fxt.Init_elem_atr_val("xowa_prop_0", "abc");
		fxt.Test_set("<input xowa_prop='app.gui.html.auto_focus_id' id='xowa_prop_0'>abc</input>");
		Tfds.Eq("abc", fxt.App().Gui_mgr().Html_mgr().Auto_focus_id());
	}
	@Test   public void Get_checkbox() {
		fxt.App().File_mgr().Wmf_mgr().Enabled_(true);
		fxt.Exec_get("a <input type='checkbox' xowa_prop='app.files.download.enabled'></input> b", "a <input type='checkbox' xowa_prop='app.files.download.enabled' id='xowa_prop_0' checked='checked'></input> b");
		fxt.App().File_mgr().Wmf_mgr().Enabled_(false);
		fxt.Exec_get("a <input type='checkbox' xowa_prop='app.files.download.enabled'></input> b", "a <input type='checkbox' xowa_prop='app.files.download.enabled' id='xowa_prop_0'></input> b");
	}
	@Test   public void Set_checkbox() {
		fxt.Init_elem_atr_checked("xowa_prop_0", "true");
		fxt.App().File_mgr().Wmf_mgr().Enabled_(false);
		fxt.Test_set("<input type='checkbox' xowa_prop='app.files.download.enabled' checked='checked' id='xowa_prop_0'></input>");
		Tfds.Eq(true, fxt.App().File_mgr().Wmf_mgr().Enabled());
		fxt.Init_elem_atr_checked("xowa_prop_0", "false");
		fxt.Test_set("<input type='checkbox' xowa_prop='app.files.download.enabled' id='xowa_prop_0'></input>");
		Tfds.Eq(false, fxt.App().File_mgr().Wmf_mgr().Enabled());
	}
	@Test   public void Get_textarea() {
		fxt.Exec_get("<textarea xowa_prop='app.user.name'></textarea>", "<textarea xowa_prop='app.user.name' id='xowa_prop_0'>test_user</textarea>");
	}
	@Test   public void Get_textarea_escaped() {
		fxt.App().Api_root().Test_str_("<b>a</b>");
		fxt.Exec_get("<textarea xowa_prop='app.api.test_str'></textarea>", "<textarea xowa_prop='app.api.test_str' id='xowa_prop_0'>&lt;b&gt;a&lt;/b&gt;</textarea>");
	}
	@Test   public void Set_textarea() {
		fxt.Init_elem_atr_val("xowa_prop_0", "abc");
		fxt.Test_set("<textarea xowa_prop='app.gui.html.auto_focus_id' id='xowa_prop_0'>abc</textarea>");
		Tfds.Eq("abc", fxt.App().Gui_mgr().Html_mgr().Auto_focus_id());
	}
	@Test   public void Set_textarea_escaped() {
		fxt.Init_elem_atr_val("xowa_prop_0", "&lt;b&gt;a&lt;/b&gt;");
		fxt.Test_set("<textarea xowa_prop='app.gui.html.auto_focus_id' id='xowa_prop_0'>&lt;b&gt;a&lt;/b&gt;</textarea>");
		Tfds.Eq("<b>a</b>", fxt.App().Gui_mgr().Html_mgr().Auto_focus_id());
	}
	@Test   public void Get_select() {
		fxt.Exec_get
		(	"<select xowa_prop='app.files.math.renderer' xowa_prop_list='app.files.math.renderer_list'></select>", String_.Concat_lines_nl
		(	"<select xowa_prop='app.files.math.renderer' xowa_prop_list='app.files.math.renderer_list' id='xowa_prop_0'>"
		,	"  <option value='mathjax' selected='selected'>MathJax</option>"
		,	"  <option value='latex'>LaTeX</option>"
		,	"</select>"
		));
	}
	@Test   public void Set_select() {
		fxt.Init_elem_atr_val("xowa_prop_0", "mathjax");
		fxt.Test_set("<select xowa_prop='app.files.math.renderer' xowa_prop_list='app.files.math.renderer_list'></select>");
		Tfds.Eq(true, fxt.App().File_mgr().Math_mgr().Renderer_is_mathjax());
		fxt.Init_elem_atr_val("xowa_prop_0", "latex");
		fxt.Test_set("<select xowa_prop='app.files.math.renderer' xowa_prop_list='app.files.math.renderer_list'></select>");
		Tfds.Eq(false, fxt.App().File_mgr().Math_mgr().Renderer_is_mathjax());
	}
	@Test   public void Get_io_file() {
		fxt.Exec_get
		(	"<input type='xowa_io' xowa_prop='app.fsys.apps.media' xowa_io_msg='Select program for Web Browser'></input>", String_.Concat
		(	"<input type='xowa_io' xowa_prop='app.fsys.apps.media' xowa_io_msg='Select program for Web Browser' id='xowa_prop_0' value=''></input>"
		,	"<button id='xowa_prop_0_io' class='options_button' onclick='xowa_io_select(\"file\", \"xowa_prop_0\", \"Select program for Web Browser\");'>...</button>"
		));
	}
	@Test  public void Scrub_tidy_trailing_nl_in_textarea() {
		fxt.Test_Scrub_tidy_trailing_nl_in_textarea(Bool_.Y, Bool_.Y, "a\n", "a");
		fxt.Test_Scrub_tidy_trailing_nl_in_textarea(Bool_.Y, Bool_.Y, "a\n\n", "a\n");
		fxt.Test_Scrub_tidy_trailing_nl_in_textarea(Bool_.Y, Bool_.Y, "", "");
		fxt.Test_Scrub_tidy_trailing_nl_in_textarea(Bool_.N, Bool_.N, "a\n", "a\n");
		fxt.Test_Scrub_tidy_trailing_nl_in_textarea(Bool_.N, Bool_.Y, "a\n", "a\n");
		fxt.Test_Scrub_tidy_trailing_nl_in_textarea(Bool_.Y, Bool_.N, "a\n", "a\n");
	}
	@Test   public void Ref() {// PURPOSE: references were duplicating; DATE:2015-05-16
		fxt.Parser_fxt().Test_html_full_str
		( String_.Concat_lines_nl_skip_last
		( "<xowa_cmd>"
		, "app.user.prefs.exec_get("
		, "<:['"
		, "<ref name='a'/>"
		, "<references>"
		, "<ref name='a'>b</ref>"
		, "']:>"
		, ");"
		, "</xowa_cmd>"
		), String_.Concat_lines_nl_skip_last
		( "<sup id=\"cite_ref-a_0-0\" class=\"reference\"><a href=\"#cite_note-a-0\">[1]</a></sup>"
		, "<ol class=\"references\">"
		, "<li id=\"cite_note-a-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-a_0-0\">^</a></span> <span class=\"reference-text\">b</span></li>"
		, "</ol>"
		)
		);
//			(	"<ref name='a'><references><ref name='a'>a1</ref></references>", String_.Concat
//			(	"<input type='xowa_io' xowa_prop='app.fsys.apps.media' xowa_io_msg='Select program for Web Browser' id='xowa_prop_0' value=''></input>"
//			,	"<button id='xowa_prop_0_io' class='options_button' onclick='xowa_io_select(\"file\", \"xowa_prop_0\", \"Select program for Web Browser\");'>...</button>"
//			));
	}
}
class Prefs_mgr_fxt {
	public Xoae_app App() {return app;} private Xoae_app app; 
	private Prefs_mgr prefs_mgr; private Gfui_html_mok html_box;
	public void Clear() {
		if (app == null) {
			GfsCore._.Clear();	// NOTE: clear GfsCore, else Props test will fail for mass run
			Xoa_gfs_mgr.Msg_parser_init();
			app = Xoa_app_fxt.app_();
			prefs_mgr = new Prefs_mgr(app);	
			html_box = new Gfui_html_mok();
			prefs_mgr.Html_box_mok_(html_box);
		}
	}
	public Xop_fxt Parser_fxt() {return new Xop_fxt(app, app.Usere().Wiki());}
	public Prefs_mgr_fxt Init_elem_atr_val(String elem_id, String atr_val) {
		html_box.Html_elem_atr_add(elem_id, gplx.gfui.Gfui_html.Atr_value, atr_val);
		return this;
	}
	public Prefs_mgr_fxt Init_elem_atr_checked(String elem_id, String v) {
		html_box.Html_elem_atr_add(elem_id, "checked", v);
		return this;
	}
	public Prefs_mgr_fxt Init_elem_atr_innerHtml(String elem_id, String v) {
		html_box.Html_elem_atr_add(elem_id, gplx.gfui.Gfui_html.Atr_innerHTML, v);
		return this;
	}
	public Prefs_mgr_fxt Exec_get(String src_str, String expd) {
		String actl = String_.new_u8(prefs_mgr.Props_get(Bry_.new_u8(src_str)));
		Tfds.Eq_str_lines(expd, actl);
		return this;
	}
	public Prefs_mgr_fxt Exec_get_has(String src_str, String expd) {
		String actl = String_.new_u8(prefs_mgr.Props_get(Bry_.new_u8(src_str)));
		Tfds.Eq_true(String_.Has(actl, expd));
		return this;
	}
	public Prefs_mgr_fxt Test_set(String src_str) {
		prefs_mgr.Props_set(Bry_.new_u8(src_str));
		return this;
	}
	public void Test_Scrub_tidy_trailing_nl_in_textarea(boolean tidy_enabled, boolean elem_is_textarea, String val, String expd) {
		String actl = Prefs_mgr.Scrub_tidy_trailing_nl_in_textarea(tidy_enabled, elem_is_textarea ? Prefs_mgr.Elem_tid_textarea : Prefs_mgr.Elem_tid_input_text, val);
		Tfds.Eq(expd, actl);
	}
}
class Gfui_html_mok extends Xog_html_itm {	private Hash_adp elem_atrs = Hash_adp_.new_();
	public void Html_elem_atr_add(String elem_id, String atr_key, Object atr_val) {elem_atrs.Add_if_dupe_use_nth(elem_id + "." + atr_key, atr_val);}
	@Override public String	Html_elem_atr_get_str(String id, String atr_key) {
		return (String)elem_atrs.Get_by(id + "." + atr_key);
	}
	@Override public boolean		Html_elem_atr_get_bool(String id, String atr_key) {
		return Bool_.parse((String)elem_atrs.Get_by(id + "." + atr_key));
	}
}
