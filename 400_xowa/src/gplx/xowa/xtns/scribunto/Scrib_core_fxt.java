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
package gplx.xowa.xtns.scribunto;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.xowa.*;
import gplx.xowa.xtns.scribunto.engines.process.*; import gplx.xowa.parsers.tmpls.*;
public class Scrib_core_fxt {
	public Scrib_core_fxt() {}
	public Scrib_core_fxt(Xop_fxt fxt) {
		app = fxt.App();
		wiki = fxt.Wiki();
		core = wiki.Parser_mgr().Scrib().Core_init(wiki.Parser_mgr().Ctx());
		server = new Process_server_mock();
		core.Interpreter().Server_(server);
	}
	public Scrib_core_fxt Clear() {
		if (core == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			core = wiki.Parser_mgr().Scrib().Core_init(wiki.Parser_mgr().Ctx());
			server = new Process_server_mock();
			core.Interpreter().Server_(server);
		}
		server.Clear();
		core.Proc_mgr().Clear();
		core.Frame_parent_(null);
		core.When_page_changed(wiki.Parser_mgr().Ctx().Page());
		expd_server_rcvd_list.Clear();
		return this;
	}	private Xoae_app app; Xowe_wiki wiki; BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public Scrib_core Core() {return core;} private Scrib_core core;
	public Process_server_mock Server() {return server;} Process_server_mock server;
	public KeyVal NewKvInt(int key, Object val) {return KeyVal.NewInt(key, val);}
	public KeyVal NewKvStr(String key, Object val) {return KeyVal.NewStr(key, val);}
	public KeyVal[] kv_ary_(KeyVal... v) {return v;}
	public KeyVal kv_func_(String key, int id) {return KeyVal.NewStr(key, new Scrib_lua_proc(key, id));}
	public Scrib_core_fxt Init_lib_fil(String name, String text) {
		Io_url url = core.Fsys_mgr().Script_dir().GenSubFil(name);
		Io_mgr.Instance.SaveFilStr(url, text);
		return this;
	}
	public Scrib_core_fxt Init_server_prep_add(String v) {server.Prep_add(v); return this;}
	public Scrib_core_fxt Init_lib_mw() {
		if (core.Lib_mw().Mod() == null) {
			Scrib_lua_mod mod = new Scrib_lua_mod(core, "mw.lua");
			mod.Fncs_add(new Scrib_lua_proc("executeModule", 8));
			mod.Fncs_add(new Scrib_lua_proc("executeFunction", 9));
			core.Lib_mw().Mod_(mod);
		}
		return this;
	}
	public Scrib_core_fxt Init_cbks_add(String func, int idx) {
		core.Proc_mgr().Set(core.Lib_mw(), func, idx);
		return this;
	}
	public Scrib_core_fxt Expd_server_rcvd_add(String v) {expd_server_rcvd_list.Add(v); return this;} List_adp expd_server_rcvd_list = List_adp_.New();
	public Scrib_core_fxt Test_LoadString(String name, String text, int expd_id) {
		int actl_id = core.Interpreter().LoadString(name, text).Id();
		Test_server_logs();
		GfoTstr.EqObj(expd_id, actl_id);
		return this;
	}
	public Scrib_core_fxt Test_CallFunction(int prc_id, Object[] args, KeyVal... expd) {
		KeyVal[] actl = core.Interpreter().CallFunction(prc_id, Scrib_kv_utl_.base1_many_(args));
		Test_server_logs();
		GfoTstr.EqLines(KeyValUtl.AryToStr(expd), KeyValUtl.AryToStr(actl));
		return this;
	}
	public Scrib_core_fxt Test_RegisterLibrary(String[] proc_names, String[] expd_keys) {
		core.Lib_mw().Procs().Init_by_lib(core.Lib_mw(), proc_names);
		core.RegisterLibrary(core.Lib_mw().Procs());
		Test_server_logs();
		int len = core.Proc_mgr().Len();
		String[] actl_keys = new String[len];
		for (int i = 0; i < len; i++)
			actl_keys[i] = core.Proc_mgr().Get_at(i).Proc_key();
		GfoTstr.EqLines(expd_keys, actl_keys);
		return this;
	}
	public Scrib_core_fxt Test_LoadLibraryFromFile(String name, String text, KeyVal... expd) {
		Scrib_lua_mod actl_lib = core.LoadLibraryFromFile(name, text);
		int actl_len = actl_lib.Fncs_len();
		KeyVal[] actl = new KeyVal[actl_len];
		for (int i = 0; i < actl_len; i++) {
			Scrib_lua_proc itm = actl_lib.Fncs_get_at(i);
			actl[i] = KeyVal.NewStr(itm.Key(), itm.Id());
		}
		GfoTstr.EqLines(KeyValUtl.AryToStr(expd), KeyValUtl.AryToStr(actl));
		return this;
	}
	public Scrib_core_fxt Test_Module_GetInitChunk(String name, String text, int expd_id) {
		Scrib_lua_mod mod = new Scrib_lua_mod(core, name);
		int actl_id = mod.LoadString(text).Id();
		Test_server_logs();
		GfoTstr.EqObj(expd_id, actl_id);
		return this;
	}
	public Scrib_core_fxt Test_ExecuteModule(int mod_id, KeyVal... expd) {
		KeyVal[] values = core.Interpreter().ExecuteModule(mod_id);
		KeyVal[] actl = (KeyVal[])values[0].Val();
		Test_server_logs();
		GfoTstr.EqLines(KeyValUtl.AryToStr(expd), KeyValUtl.AryToStr(actl));
		return this;
	}
	public Scrib_core_fxt Test_GetExpandedArgument(KeyVal[] args, String arg, String expd) {// NOTE: test is rigidly defined; (a) always same 3 arguments in frame; (b) expd={"val_1", "val_2", "val_3", ""}
		this.Expd_server_rcvd_add("0000003D00000079{[\"op\"]=\"call\",[\"id\"]=8,[\"nargs\"]=1,[\"args\"]={[1]=chunks[9]}}")
			.Init_server_prep_add("a:4:{s:2:\"id\";s:32:\"mwInit|getExpandedArgument\";s:2:\"op\";s:4:\"call\";s:5:\"nargs\";i:2;s:4:\"args\";a:2:{i:1;s:7:\"current\";i:2;s:" + StringUtl.Len(arg) + ":\"" + arg + "\";}}");
		if (StringUtl.Eq(expd, ""))
			this.Expd_server_rcvd_add("0000002D00000059{[\"op\"]=\"return\",[\"nvalues\"]=0,[\"values\"]={}}");
		else
			this.Expd_server_rcvd_add("000000380000006F{[\"op\"]=\"return\",[\"nvalues\"]=1,[\"values\"]={[1]=\"" + expd + "\"}}");
		this.Init_server_prep_add("a:2:{s:6:\"values\";a:1:{i:1;s:6:\"ignore\";}s:2:\"op\";s:6:\"return\";}");
		core.Frame_current_(Xot_invk_mock.new_(Frame_ttl_test, args));
		core.Interpreter().ExecuteModule(9);
		Test_server_logs();
		return this;
	}
	public Scrib_core_fxt Test_GetAllExpandedArguments(KeyVal... args) {
		core.Frame_current_(Xot_invk_mock.new_(Frame_ttl_test, args));
		core.Interpreter().ExecuteModule(9);
		Test_server_logs();
		return this;
	}
	public Scrib_core_fxt Test_Invoke(String mod_name, String mod_code, String prc_name, KeyVal... args) {
		core.Invoke(wiki, core.Ctx(), BryUtl.Empty, Xot_invk_mock.Null, Xot_invk_mock.new_(Frame_ttl_test, args), tmp_bfr, BryUtl.NewU8(mod_name), BryUtl.NewU8(mod_code), BryUtl.NewU8(prc_name));
		Test_server_logs();
		return this;
	}	private static final byte[] Frame_ttl_test = BryUtl.NewA7("test");
	private void Test_server_logs() {
		if (expd_server_rcvd_list.Len() > 0) {
			GfoTstr.EqLines(expd_server_rcvd_list.ToStrAry(), server.Log_rcvd().ToStrAry());
			expd_server_rcvd_list.Clear();
			server.Log_rcvd().Clear();
		}
	}
	public String Encode(String v) {return StringUtl.Replace(v, "\n", "\\n");}
}
