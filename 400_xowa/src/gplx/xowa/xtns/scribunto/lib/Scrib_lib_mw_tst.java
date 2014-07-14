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
public class Scrib_lib_mw_tst {
	@Test  public void Stub() {}
//		@Before public void init() {
//			fxt.Clear();
//			lib = fxt.Core().Lib_mw().Init();
//		}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
//		@Test  public void ParentFrameExists() {
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Init_page("{{test}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_parentFrameExists, Object_.Ary_empty, "true");
//		}
//		@Test  public void ParentFrameExists_false() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_parentFrameExists, Object_.Ary_empty, "false");
//		}
//		@Test  public void GetAllExpandedArguments() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0|v0|k1=v1}}");
//			fxt.Init_server_print_key_y_();
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("current"), "\n  1:v0;k1:v1");
//			fxt.Init_server_print_key_n_();
//		}
//		@Test  public void GetAllExpandedArguments_parent() {
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|b1}}");
//			fxt.Init_page("{{test|a1|a2}}");
//			fxt.Init_server_print_key_y_();
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("parent"), "\n  1:a1;2:a2");
//			fxt.Init_server_print_key_n_();
//		}
//		@Test  public void GetAllExpandedArguments_ws_prm_key_exists() {	// PURPOSE: trim val if key exists; parameterized value ("key={{{1}}}")
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|key={{{1}}}}}");
//			fxt.Init_page("{{test| a }}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("current"),  "\n  a");				// " a " -> "a"
//		}
//		@Test  public void GetAllExpandedArguments_ws_prm_key_missing() {	// PURPOSE: do not trim val if key missing; parameterized value ("{{{1}}}")
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|{{{1}}}}}");
//			fxt.Init_page("{{test| a }}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("current"),  "\n   a ");				// " a " -> " a "
//		}
//		@Test  public void GetAllExpandedArguments__ignore_empty_key() {// PURPOSE: ignore arguents that have an empty key (|=8|); EX:w:Fool's_mate; DATE:2014-03-05
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Init_page("{{test|a1||a2|=a3|a4}}");
//			fxt.Init_server_print_key_y_();
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("parent"), "\n  1:a1;2:;3:a2;4:a4");	// NOTE: || is not ignored but |=a3| is
//			fxt.Init_server_print_key_n_();
//		}
//		@Test  public void GetExpandedArgument() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0|val_1|key_2=val_2|val_3}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, "val_1");			// get 1st by idx
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "2")		, "val_3");			// get 2nd by idx (which is "3", not "key_2)
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "3")		, "");				// get 3rd by idx (which is n/a, not "3")
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key_2")	, "val_2");			// get key_2
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key_3")	, "");				// key_3 n/a
//		}
//		@Test  public void GetExpandedArgument_ws_key_exists() {	// PURPOSE: trim val if key exists; literal value
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0| key1 = val1 }}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key1")	, "val1");			// "key1" -> "key1"
//		}
//		@Test  public void GetExpandedArgument_ws_key_missing() {	// PURPOSE: do not trim val if key missing; literal value
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0| a }}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, " a ");			// " a " -> " a "
//		}
//		@Test  public void GetExpandedArgument_ws_key_prm_key_exists() {	// PURPOSE: trim val if key exists; parameterized value ("key={{{1}}}")
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|key1={{{1}}}}}");
//			fxt.Init_page("{{test| a }}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key1")	, "a");				// " a " -> "a"
//		}
//		@Test  public void GetExpandedArgument_ws_key_prm_key_missing() {	// PURPOSE: do not trim val if key missing; parameterized value ("{{{1}}}")
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|{{{1}}}}}");
//			fxt.Init_page("{{test| a }}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, " a ");			// " a " -> " a "
//		}
//		@Test  public void GetExpandedArgument_parent() {
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|b1}}");
//			fxt.Init_page("{{test|a1|a2}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("parent", "1"), "a1");
//		}
//		@Test  public void GetExpandedArgument_numeric_key() {		// PURPOSE.FIX: frame.args[1] was ignoring "1=val_1" b/c it was looking for 1st unnamed arg (and 1 is the name for "1=val_1")
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0|1=val_1}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, "val_1");			// get 1st by idx, even though idx is String
//		}
//		@Test  public void GetExpandedArgument_numeric_key_2() {	// PURPOSE.FIX: same as above, but for parent context; DATE:2013-09-23
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|b2}}");	// current
//			fxt.Init_page("{{test|2=b1}}");					// parent
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("parent", "2")		, "b1");			// get 1st by idx, even though idx is String
//		}
//		@Test  public void GetExpandedArgument_out_of_bounds() {
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|b1}}");
//			fxt.Init_page("{{test}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("parent", "2")		, "");
//		}
//		@Test  public void Preprocess() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0|key1=a|key2=b|key1=c}}");	// add key1 twice
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_preprocess, Object_.Ary("current", "{{#ifeq:1|1|{{{key1}}}|{{{key2}}}}}"), "c");
//		}
//		@Test  public void CallParserFunction() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.base1_many_ary_("current", "#expr", "1")												, "1");		// named: args is scalar
//			fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.base1_many_ary_("current", "#if", Scrib_kv_utl_.base1_many_("", "y", "n"))			, "n");		// named: args is table
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_callParserFunction, Object_.Ary("current", "#if", "", "y", "n")							, "n");	// list: args is ary
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_callParserFunction, Object_.Ary("current", "#if", Scrib_kv_utl_.base1_many_("", "y", "n"))	, "n");	// list: args is table
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_callParserFunction, Object_.Ary("current", "#if:", "y", "n")								, "n");	// colon_in_name
//		}
//		@Test  public void CallParserFunction_tag() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.flat_many_(1, "current", 2, "#tag", 3, Scrib_kv_utl_.flat_many_("3", "id=1", "2", "text", "1", "pre")), "<pre 3=\"id=1\">2=text</pre>");// named: sort args; NOTE: keys should probably be stripped
//		}
//		@Test  public void CallParserFunction_displayTitle() {	// PURPOSE: DISPLAYTITLE not being set when called through CallParserFunction; DATE:2013-08-05
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.base1_many_ary_("current", "DISPLAYTITLE", "''a''"), "");
//			Tfds.Eq("<i>a</i>", String_.new_ascii_(fxt.Parser_fxt().Ctx().Cur_page().Display_ttl()));
//		}
//		@Test  public void IsSubsting() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_isSubsting, Object_.Ary_empty, "false");
//		}
//		@Test  public void ExpandTemplate_tmpl() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Parser_fxt().Data_create("Template:A", "b{{{key1}}}c");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", "A", Scrib_kv_utl_.flat_many_("key1", "val1"))				, "bval1c");	// list: args is ary
//		}
//		@Test  public void ExpandTemplate_tmpl_bool() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Parser_fxt().Data_create("Template:Scribunto_bool", "bool_true={{{bool_true}}};bool_false={{{bool_false}}};");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", "Scribunto_bool", Scrib_kv_utl_.flat_many_("bool_true", true, "bool_false", false)), "bool_true=1;bool_false={{{bool_false}}};");
//		}
//		@Test  public void ExpandTemplate_page() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Parser_fxt().Data_create("A", "b{{{key1}}}c");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", ":A", Scrib_kv_utl_.flat_many_("key1", "val1"))				, "bval1c");	// list: args is ary
//		}
//		@Test  public void GetFrameTitle_current() {
//			fxt.Parser_fxt().Wiki().Cache_mgr().Free_mem_all();
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");		// current
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getFrameTitle, Object_.Ary("current")	, "Module:Mod_0");
//		}
//		@Test  public void GetFrameTitle_owner() {
//			fxt.Parser_fxt().Wiki().Cache_mgr().Free_mem_all();
//			fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0}}");		// current
//			fxt.Init_page("{{test}}");						// parent
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getFrameTitle, Object_.Ary("parent")	, "Template:Test");
//		}
//		@Test  public void GetFrameTitle_empty() {
//			fxt.Parser_fxt().Wiki().Cache_mgr().Free_mem_all();
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");		// current
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getFrameTitle, Object_.Ary("empty")	, Scrib_invoke_func_fxt.Null_rslt);
//		}
//		@Test  public void NewChildFrame() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_newChildFrame, Object_.Ary("current", "Page_0", Scrib_kv_utl_.flat_many_("key1", "val1")), "frame0");
//		}
//		@Test  public void SetTTL() {
//			fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
//			fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_setTTL, Object_.Ary(123), "");	// smoke test; difficult to get member reference to current_frame since it's freed automatically in invoke; DATE:2014-07-12
//		}
}
