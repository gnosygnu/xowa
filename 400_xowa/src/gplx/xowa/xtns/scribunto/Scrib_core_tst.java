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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Scrib_core_tst {
	@Before public void init() {fxt.Clear();} Scrib_core_fxt fxt = new Scrib_core_fxt();
	@Test  public void LoadString() {
		String mod_text = Mod_basic();
		fxt	.Expd_server_rcvd_add("00000067000000CD{[\"op\"]=\"loadString\",[\"text\"]=\"" + fxt.Encode(mod_text) + "\",[\"chunkName\"]=\"lib_name\"}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:1;}}")
			.Test_LoadString("lib_name", mod_text, 1)
			;
	}
	@Test  public void CallFunction() {
		fxt	.Expd_server_rcvd_add("000000300000005F{[\"op\"]=\"call\",[\"id\"]=1,[\"nargs\"]=0,[\"args\"]={}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;a:1:{s:4:\"noop\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:2;}}}}")
			.Test_CallFunction(1, Object_.Ary_empty, fxt.kv_ary_(fxt.kv_(1, fxt.kv_func_("noop", 2))))
			;
	}
	@Test  public void CallFunction_args() {
		fxt	.Expd_server_rcvd_add("0000004100000081{[\"op\"]=\"call\",[\"id\"]=1,[\"nargs\"]=1,[\"args\"]={[1]={[\"key\"]=123}}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;a:1:{s:4:\"noop\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:2;}}}}")
			.Test_CallFunction(1, Object_.Ary(fxt.kv_("key", 123)), fxt.kv_ary_(fxt.kv_(1, fxt.kv_func_("noop", 2))))
			;
	}
	@Test  public void RegisterLibrary() {
		fxt	.Expd_server_rcvd_add("00000080000000FF{[\"op\"]=\"registerLibrary\",[\"name\"]=\"mw_interface\",[\"functions\"]={[\"prc_0\"]=\"mw_interface-prc_0\",[\"prc_1\"]=\"mw_interface-prc_1\"}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:0;s:6:\"values\";a:0:{}}")
			.Test_RegisterLibrary(String_.Ary("prc_0", "prc_1"), String_.Ary("mw_interface-prc_0", "mw_interface-prc_1"))
			;
	}
	@Test  public void LoadLibraryFromFile() {
		fxt	.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:15;}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;a:2:{s:5:\"prc_2\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:2;}s:5:\"prc_3\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:3;}}}}")
			.Test_LoadLibraryFromFile("lib_name", "doesn't matter", fxt.kv_("prc_2", 2), fxt.kv_("prc_3", 3));
			;
	}
	@Test  public void LoadLibraryFromFile__rv_has_no_values() {	// PURPOSE: "package.lua" does not return any prc_ids
		fxt	.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:15;}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:0;s:6:\"values\";a:0:{}}")
			.Test_LoadLibraryFromFile("lib_name", "doesn't matter");
			;
	}
	@Test  public void CallFunction_cbk() {	// PURPOSE: 'simulates interpreter.CallFunction(mw_lib.Fncs_get_by_key("setup").Id(), "allowEnvFuncs", allow_env_funcs);'
		fxt	.Expd_server_rcvd_add("0000008C00000117{[\"op\"]=\"registerLibrary\",[\"name\"]=\"mw_interface\",[\"functions\"]={[\"loadPackage\"]=\"mw_interface-loadPackage\",[\"prc_1\"]=\"mw_interface-prc_1\"}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:0;s:6:\"values\";a:0:{}}")
			.Test_RegisterLibrary(String_.Ary("loadPackage", "prc_1"), String_.Ary("mw_interface-loadPackage", "mw_interface-prc_1"))
			;		
		fxt	.Init_lib_fil("package.lua", "package_text")
			.Expd_server_rcvd_add("000000470000008D{[\"op\"]=\"call\",[\"id\"]=2,[\"nargs\"]=1,[\"args\"]={[1]={[\"key_0\"]=\"val_0\"}}}")
			.Init_server_prep_add("a:4:{s:2:\"id\";s:24:\"mw_interface-loadPackage\";s:2:\"op\";s:4:\"call\";s:5:\"nargs\";i:1;s:4:\"args\";a:1:{i:1;s:7:\"package\";}}")
			.Expd_server_rcvd_add("0000004A00000093{[\"op\"]=\"loadString\",[\"text\"]=\"package_text\",[\"chunkName\"]=\"@package.lua\"}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:14;}}")
			.Expd_server_rcvd_add("0000003B00000075{[\"op\"]=\"return\",[\"nvalues\"]=1,[\"values\"]={[1]=chunks[14]}}")				                       
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:0;s:6:\"values\";a:0:{}}")
			.Test_CallFunction(2, Object_.Ary(fxt.kv_("key_0", "val_0")));
	}
	@Test  public void Module_GetInitChunk() {	// PURPOSE: similar to LoadString except name is prepended with "="
		String mod_text = Mod_basic();
		fxt	.Expd_server_rcvd_add("0000006F000000DD{[\"op\"]=\"loadString\",[\"text\"]=\"" + fxt.Encode(mod_text) + "\",[\"chunkName\"]=\"=Module:Mod_name\"}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:1;}}")
			.Test_Module_GetInitChunk("Module:Mod_name", mod_text, 1)
			;
	}
	@Test  public void Module_GetInitChunk_tabs() {	// PURPOSE: swap out xowa's &#09; with literal tabs, else will break lua
		fxt	.Expd_server_rcvd_add("0000004500000089{[\"op\"]=\"loadString\",[\"text\"]=\"" + "a\tb" + "\",[\"chunkName\"]=\"=Module:Mod_name\"}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:1;}}")
			.Test_Module_GetInitChunk("Module:Mod_name", "a&#09;b", 1)
			;
	}
	@Test  public void ExecuteModule() {
		fxt	.Init_lib_mw();
		fxt	.Expd_server_rcvd_add("0000003E0000007B{[\"op\"]=\"call\",[\"id\"]=8,[\"nargs\"]=1,[\"args\"]={[1]=chunks[14]}}")
			.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;a:2:{s:5:\"prc_0\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:15;}s:5:\"prc_1\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:16;}}}}")
			.Test_ExecuteModule(14, fxt.kv_func_("prc_0", 15), fxt.kv_func_("prc_1", 16))
			;
	}
//		@Test  public void Invoke() {
//			fxt	.Init_lib_mw();
//			fxt	.Init_cbks_add("getExpandedArgument", gplx.xowa.xtns.scribunto.libs.Scrib_lib_mw.Proc_getExpandedArgument);
//			fxt	.Expd_server_rcvd_add("0000004900000091{[\"op\"]=\"loadString\",[\"text\"]=\"Mod_0_code\",[\"chunkName\"]=\"=Module:Mod_0\"}")
//				.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:13;}}")			// NOTE: 13=id of "Module:Mod_0"
//				.Expd_server_rcvd_add("0000003E0000007B{[\"op\"]=\"call\",[\"id\"]=8,[\"nargs\"]=1,[\"args\"]={[1]=chunks[13]}}")	// NOTE: 8=executeModule; 13=id of "Module:Mod_0"
//				.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;a:1:{s:5:\"Prc_0\";O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:14;}}}}")	// NOTE: 14=id of "Prc_0"
//				.Expd_server_rcvd_add("0000003E0000007B{[\"op\"]=\"call\",[\"id\"]=9,[\"nargs\"]=1,[\"args\"]={[1]=chunks[14]}}")	// NOTE: 9=executeFunction; 14=id of "Prc_0"
//				.Init_server_prep_add("a:4:{s:2:\"id\";s:32:\"mw_interface-getExpandedArgument\";s:2:\"op\";s:4:\"call\";s:5:\"nargs\";i:2;s:4:\"args\";a:2:{i:1;s:7:\"current\";i:2;s:1:\"1\";}}")
//				.Expd_server_rcvd_add("000000380000006F{[\"op\"]=\"return\",[\"nvalues\"]=1,[\"values\"]={[1]=\"arg_0\"}}")
//				.Init_server_prep_add("a:4:{s:2:\"id\";s:32:\"mw_interface-getExpandedArgument\";s:2:\"op\";s:4:\"call\";s:5:\"nargs\";i:2;s:4:\"args\";a:2:{i:1;s:7:\"current\";i:2;s:1:\"2\";}}")
//				.Expd_server_rcvd_add("000000380000006F{[\"op\"]=\"return\",[\"nvalues\"]=1,[\"values\"]={[1]=\"arg_1\"}}")
//				.Init_server_prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;s:11:\"arg_0,arg_1\";}}")
//				.Test_Invoke("Mod_0", "Mod_0_code", "Prc_0", Scrib_kv_utl_.base1_many_("arg_0", "arg_1"))
//				;
//		}
	String Mod_basic() {
		return String_.Concat
		(	"local p = {}"
		,	"function p.noop(frame)"
		,	"end"
		,	"return p"
		);
	}
}
