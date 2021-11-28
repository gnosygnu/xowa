package gplx.xowa.xtns.scribunto.libs;

import gplx.Err_;
import gplx.Keyval_;
import gplx.Object_;
import gplx.String_;
import gplx.Tfds;
import gplx.xowa.xtns.scribunto.Scrib_core;
import gplx.xowa.xtns.scribunto.Scrib_invoke_func;
import gplx.xowa.xtns.scribunto.Scrib_invoke_func_fxt;
import gplx.xowa.xtns.scribunto.Scrib_kv_utl_;
import gplx.xowa.xtns.scribunto.Scrib_lib;
import org.junit.Before;
import org.junit.Test;

public class Scrib_lib_mw__invoke_tst {
	@Before public void init() {
		fxt.Clear_for_invoke();
		lib = fxt.Core().Lib_mw().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test public void GetAllExpandedArguments_ws_prm_key_exists() {	// PURPOSE: trim val if key exists; parameterized value ("key={{{1}}}")
		fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|key={{{1}}}}}");
		fxt.Init_page("{{test| a }}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("current"),  "\n  a");				// " a " -> "a"
	}
	@Test public void GetAllExpandedArguments_ws_prm_key_missing() {	// PURPOSE: do not trim val if key missing; parameterized value ("{{{1}}}")
		fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|{{{1}}}}}");
		fxt.Init_page("{{test| a }}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("current"),  "\n   a ");				// " a " -> " a "
	}
	@Test public void GetAllExpandedArguments__ignore_empty_key() {// PURPOSE: ignore arguents that have an empty key (|=8|); EX:w:Fool's_mate; DATE:2014-03-05
		fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0}}");
		fxt.Init_page("{{test|a1||a2|=a3|a4}}");
		fxt.Init_server_print_key_y_();
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getAllExpandedArguments, Object_.Ary("parent"), "\n  1:a1;2:;3:a2;4:a4");	// NOTE: || is not ignored but |=a3| is
		fxt.Init_server_print_key_n_();
	}
	@Test public void GetExpandedArgument_ws_key_exists() {	// PURPOSE: trim val if key exists; literal value
		fxt.Init_page("{{#invoke:Mod_0|Prc_0| key1 = val1 }}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key1")	, "val1");			// "key1" -> "key1"
	}
	@Test public void GetExpandedArgument_ws_key_missing() {	// PURPOSE: do not trim val if key missing; literal value
		fxt.Init_page("{{#invoke:Mod_0|Prc_0| a }}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, " a ");			// " a " -> " a "
	}
	@Test public void GetExpandedArgument_ws_key_prm_key_exists() {	// PURPOSE: trim val if key exists; parameterized value ("key={{{1}}}")
		fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|key1={{{1}}}}}");
		fxt.Init_page("{{test| a }}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "key1")	, "a");				// " a " -> "a"
	}
	@Test public void GetExpandedArgument_ws_key_prm_key_missing() {	// PURPOSE: do not trim val if key missing; parameterized value ("{{{1}}}")
		fxt.Init_tmpl("{{#invoke:Mod_0|Prc_0|{{{1}}}}}");
		fxt.Init_page("{{test| a }}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_getExpandedArgument, Object_.Ary("current", "1")		, " a ");			// " a " -> " a "
	}
	@Test public void Preprocess() {
		this.Init_preprocess();
		this.Exec_preprocess(Scrib_core.Frame_key_module	, "1", "c");
		this.Exec_preprocess(Scrib_core.Frame_key_module	, "2", "d");
		this.Exec_preprocess(Scrib_core.Frame_key_template	, "1", "a");
		this.Exec_preprocess(Scrib_core.Frame_key_template	, "2", "b");
	}
	@Test public void Preprocess_duplicate_key() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0|key1=a|key2=b|key1=c}}");	// add key1 twice
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_preprocess, Object_.Ary("current", "{{#ifeq:1|1|{{{key1}}}|{{{key2}}}}}"), "c");
	}
	@Test public void CallParserFunction() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.base1_many_ary_("current", "#expr", "1")												, "1");		// named: args is scalar
		fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.base1_many_ary_("current", "#if", Scrib_kv_utl_.base1_many_("", "y", "n"))			, "n");		// named: args is table
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_callParserFunction, Object_.Ary("current", "#if", "", "y", "n")							, "n");	// list: args is ary
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_callParserFunction, Object_.Ary("current", "#if", Scrib_kv_utl_.base1_many_("", "y", "n"))	, "n");	// list: args is table
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_callParserFunction, Object_.Ary("current", "#if:", "y", "n")								, "n");	// colon_in_name
	}
	@Test public void CallParserFunction_tag() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.flat_many_(1, "current", 2, "#tag", 3, Scrib_kv_utl_.flat_many_("3", "id=1", "2", "text", "1", "pre")), "<pre 3=\"id=1\">2=text</pre>");// named: sort args; NOTE: keys should probably be stripped; // ISSUE#:462; DATE:2019-05-12
	}
	@Test public void CallParserFunction__no_args() {	// PURPOSE.fix: 0 args should not fail
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.flat_many_(1, "current", 2, "#tag", 3, Keyval_.Ary_empty), "");// failed with "Script error: index is out of bounds"
	}
	@Test public void CallParserFunction_displayTitle() {	// PURPOSE: DISPLAYTITLE not being set when called through CallParserFunction; DATE:2013-08-05
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.base1_many_ary_("current", "DISPLAYTITLE", "''a''"), "");
		Tfds.Eq("<i>a</i>", String_.new_a7(fxt.Parser_fxt().Ctx().Page().Html_data().Display_ttl()));
	}
	@Test public void CallParserFunction__null() {	// PURPOSE.fix: null arg should not fail; PAGE:en.w:Abziri DATE:2017-11-29
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Test_lib_proc_kv(lib, Scrib_lib_mw.Invk_callParserFunction, Scrib_kv_utl_.flat_many_(1, "current", 2, "#coordinates", 3, Keyval_.Ary(Keyval_.int_(1, "a"), Keyval_.int_(3, "b"), null)), "");// failed with NullPointerException
	}
	@Test public void ExpandTemplate_tmpl() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Parser_fxt().Data_create("Template:A", "b{{{key1}}}c");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", "A", Scrib_kv_utl_.flat_many_("key1", "val1"))				, "bval1c");	// list: args is ary
	}
	@Test public void ExpandTemplate() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Parser_fxt().Init_page_create("Template:Format", "{{{1}}}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", "Format", Scrib_kv_utl_.base1_many_("a")), "a");
	}
	@Test public void ExpandTemplate_ns_specified() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Parser_fxt().Init_page_create("Template:Format", "{{{1}}}");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", "Template:Format", Scrib_kv_utl_.base1_many_("a")), "a");	// NOTE: "Template:" specified
	}
	@Test public void ExpandTemplate_tmpl_bool() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Parser_fxt().Data_create("Template:Scribunto_bool", "bool_true={{{bool_true}}};bool_false={{{bool_false}}};");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", "Scribunto_bool", Scrib_kv_utl_.flat_many_("bool_true", true, "bool_false", false)), "bool_true=1;bool_false={{{bool_false}}};");
	}
	@Test public void ExpandTemplate_page() {
		fxt.Init_page("{{#invoke:Mod_0|Prc_0}}");
		fxt.Parser_fxt().Data_create("A", "b{{{key1}}}c");
		fxt.Test_lib_proc(lib, Scrib_lib_mw.Invk_expandTemplate, Object_.Ary("current", ":A", Scrib_kv_utl_.flat_many_("key1", "val1"))				, "bval1c");	// list: args is ary
	}
	@Test public void Err_mod_blank()				{fxt.Test_parse_err("{{#invoke:}}"			, Scrib_invoke_func.Err_mod_missing);}
	@Test public void Err_mod_missing()			{fxt.Test_parse_err("{{#invoke:Missing}}"	, Scrib_invoke_func.Err_mod_missing);}
	@Test public void Err_mod_custom()	{
		fxt.Test_error(Err_.new_("err_type", "fail", "key0", "val0"), "<strong class=\"error\"><span class=\"scribunto-error\" id=\"mw-scribunto-error-0\">Script error: fail</span></strong>");
	}
	private void Init_preprocess() {
		fxt.Init_tmpl("{{#invoke:Mod_0|Func_0|1|c|d}}");	// current
		fxt.Init_page("{{test|1|a|b|c}}");					// parent
		fxt.Init_cbk(fxt.Core().Lib_mw(), Scrib_lib_mw.Invk_preprocess);
	}
	private void Exec_preprocess(String frame, String arg_idx, String expd) {
		fxt.Init_lua_module();
		fxt.Init_lua_rcvd_preprocess(frame, "{{#ifeq:" + arg_idx + "|{{{1}}}|{{{2}}}|{{{3}}}}}");
		fxt.Test_invoke(expd);
	}
}
