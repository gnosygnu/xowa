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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.lib.*;
//	public class Scrib_invoke_func_tst {
//		@Before public void init() {fxt.Clear();} private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt();
//		@Test  public void Err_mod_blank()				{fxt.Test_parse_err("{{#invoke:}}", Scrib_invoke_func.Err_mod_missing);}
//		@Test  public void Err_mod_missing()			{fxt.Test_parse_err("{{#invoke:Missing}}", Scrib_invoke_func.Err_mod_missing);}
//		@Test  public void Preprocess() {
//			this.Init_preprocess();
//			this.Exec_preprocess(Scrib_core.Frame_key_module	, "1", "c");
//			this.Exec_preprocess(Scrib_core.Frame_key_module	, "2", "d");
//			this.Exec_preprocess(Scrib_core.Frame_key_template	, "1", "a");
//			this.Exec_preprocess(Scrib_core.Frame_key_template	, "2", "b");
//		}
//		@Test  public void ExpandTemplate() {
//			this.Init_expandTemplate();
//			fxt.Parser_fxt().Init_page_create("Template:Format", "{{{1}}}");
//			fxt.Init_lua_module();
//			fxt.Init_lua_rcvd_expandTemplate(Scrib_core.Frame_key_module	, "Format", KeyVal_.int_(1, "a"));
//			fxt.Test_invoke("a");
//		}
//		@Test  public void ExpandTemplate_ns_name() {
//			this.Init_expandTemplate();
//			fxt.Parser_fxt().Init_page_create("Template:Format", "{{{1}}}");
//			fxt.Init_lua_module();
//			fxt.Init_lua_rcvd_expandTemplate(Scrib_core.Frame_key_module	, "Template:Format", KeyVal_.int_(1, "a"));
//			fxt.Test_invoke("a");
//		}
//		private void Init_preprocess() {
//			fxt.Init_tmpl("{{#invoke:Mod_0|Func_0|1|c|d}}");	// current
//			fxt.Init_page("{{test|1|a|b|c}}");					// parent
//			fxt.Core().Lib_mw().Init();
//			fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_mw(), Scrib_lib_mw.Invk_preprocess);
//		}
//		private void Exec_preprocess(String frame, String arg_idx, String expd) {
//			fxt.Parser_fxt().Wiki().Cache_mgr().Tmpl_result_cache().Clear();
//			fxt.Init_lua_module();
//			fxt.Init_lua_rcvd_preprocess(frame, "{{#ifeq:" + arg_idx + "|{{{1}}}|{{{2}}}|{{{3}}}}}");
//			fxt.Test_invoke(expd);
//		}
//		private void Init_expandTemplate() {
//			fxt.Init_tmpl("{{#invoke:Mod_0|Func_0|1|c|d}}");	// current
//			fxt.Init_page("{{test|null|1|a|b}}");				// parent
//			fxt.Core().Lib_mw().Init();
//			fxt.Init_cbk(Scrib_core.Key_mw_interface, fxt.Core().Lib_mw(), Scrib_lib_mw.Invk_expandTemplate);
//		}
//	}	
