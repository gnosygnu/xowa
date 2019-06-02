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
package gplx.xowa.xtns.scribunto.engines.mocks; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Mock_scrib_fxt {		 
	private final    Mock_engine engine = new Mock_engine();
	private final    Mock_server server = new Mock_server();		
	private int mock_mod_id_next = 10;
	public Scrib_core Core() {return core;} private Scrib_core core;
	public Mock_engine Engine() {return engine;}
	public Xop_fxt Parser_fxt() {return parser_fxt;} private Xop_fxt parser_fxt; 
	public void Clear() {Clear("en.wikipedia.org", "en");}
	public void Clear(String domain, String lang) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app, domain, app.Lang_mgr().Get_by_or_new(Bry_.new_u8(lang)));
		parser_fxt = new Xop_fxt(app, wiki); // NOTE: always new(); don't try to cache; causes errors in Language_lib
		core = wiki.Parser_mgr().Scrib().Core_init(wiki.Parser_mgr().Ctx());
		core.Engine_(engine); engine.Clear();
		core.Interpreter().Server_(server);
		Xot_invk parent_frame = Xot_invk_temp.New_root(); parent_frame.Frame_tid_(Scrib_frame_.Tid_null); 
		Xot_invk current_frame = Xot_invk_mock.test_(Bry_.new_a7("Module:Mod_0"));
		core.Invoke_init(core.Wiki(), core.Ctx(), Bry_.Empty, parent_frame, current_frame);
		core.When_page_changed(parser_fxt.Page());
	}
	public void Init__cbk(Mock_proc_stub... ary) {
		engine.Clear();
		for (Mock_proc_stub proc : ary)
			engine.InitFunctionForTest(proc);
	}
	public void Init__page(String ttl, String txt) {parser_fxt.Init_page_create(ttl, txt);}
	public void Init__mock_mod_system() {
		Scrib_lua_mod mod = new Scrib_lua_mod(core, "mw.lua");
		core.Lib_mw().Mod_(mod);
		mod.Fncs_add(new Scrib_lua_proc("executeModule", 8));
		mod.Fncs_add(new Scrib_lua_proc("executeFunction", 9));
		engine.InitFunctionForTest(new Mock_exec_module(8, engine));
		engine.InitFunctionForTest(new Mock_exec_function(9, engine));
	}
	public void Init__mock_mod(Scrib_lib lib, String mod_name, Mock_proc_stub... prc_ary) {
		int mod_id = mock_mod_id_next++;
		String mod_text = "";
		engine.Init_module("=" + mod_name, mod_id);
		for (Mock_proc_stub prc : prc_ary) {
			mod_text = mod_text + prc.Key() + "\n";
			engine.Init_module_func(mod_id, prc);
		}
		parser_fxt.Init_page_create(mod_name, mod_text);
	}
	public Mock_proc_stub Init__mock_fnc_for_lib(String fnc_name, Scrib_lib lib, String proc_name, Object... proc_args) {
		return new Mock_exec_lib(mock_mod_id_next++, fnc_name, lib, proc_name, proc_args);
	}
	public void Test__proc__ints      (Scrib_lib lib, String proc_name, Object[] args, int expd)		{Test__proc__kvps(lib, proc_name, Bool_.Y, Int_.To_str(expd), Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__objs__flat(Scrib_lib lib, String proc_name, Object[] args, String expd)		{Test__proc__kvps(lib, proc_name, Bool_.Y, expd, Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__objs__nest(Scrib_lib lib, String proc_name, Object[] args, Keyval[] expd)	{Test__proc__kvps(lib, proc_name, Bool_.N, Keyval_.Ary__to_str__nest(new Keyval[] {Keyval_.int_(Scrib_core.Base_1, expd)}), Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__objs__nest(Scrib_lib lib, String proc_name, Object[] args, String expd)		{Test__proc__kvps(lib, proc_name, Bool_.N, expd, Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__kvps__flat(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {Test__proc__kvps(lib, proc_name, Bool_.Y, expd, args);}
	public void Test__proc__kvps__nest(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {Test__proc__kvps(lib, proc_name, Bool_.N, expd, args);}
	private static void Test__proc__kvps(Scrib_lib lib, String proc_name, boolean flat, String expd, Keyval[] args) {
		Keyval[] actl_ary = Mock_scrib_fxt_.Test__lib_proc__core(lib, proc_name, args);
		if (flat)
			Tfds.Eq(expd, Mock_scrib_fxt_.Kvp_vals_to_str(actl_ary));
		else
			Tfds.Eq_str_lines(expd, Keyval_.Ary__to_str__nest(actl_ary));
	}
	public void Test__proc__objs__empty(Scrib_lib lib, String proc_name, Object[] args) {Test__proc__kvps__empty(lib, proc_name, Scrib_kv_utl_.base1_many_(args));}
	public void Test__proc__kvps__empty(Scrib_lib lib, String proc_name, Keyval[] args) {
		Tfds.Eq(0, Mock_scrib_fxt_.Test__lib_proc__core(lib, proc_name, args).length);
	}
	public void Test__proc__kvps__vals(Scrib_lib lib, String proc_name, Keyval[] args, Object... expd_ary) {
		Keyval[] actl_kvs = Mock_scrib_fxt_.Test__lib_proc__core(lib, proc_name, args);
		Object[] actl_ary = Mock_scrib_fxt_.Kvp_vals_to_objs(actl_kvs);
		Tfds.Eq_ary(expd_ary, actl_ary);
	}
	public void Test__parse__tmpl_to_html(String raw, String expd) {parser_fxt.Test__parse__tmpl_to_html(raw, expd);}
}
class Mock_scrib_fxt_ {
	public static Keyval[] Test__lib_proc__core(Scrib_lib lib, String proc_name, Keyval[] args) {
		Scrib_proc proc = lib.Procs().Get_by_key(proc_name);
		Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
		proc.Proc_exec(new Scrib_proc_args(args), proc_rslt);
		return proc_rslt.Ary();
	}
	public static Object[] Kvp_vals_to_objs(Keyval[] kvps) {
		int len = kvps.length;
		Object[] rv = new Object[len];
		for (int i = 0; i < len; ++i)
			rv[i] = kvps[i].Val();
		return rv;
	}
	public static String Kvp_vals_to_str(Keyval[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Semic);
			Keyval kv = ary[i];
			bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(kv.Val()));
		}
		return bfr.To_str_and_clear();
	}
}
