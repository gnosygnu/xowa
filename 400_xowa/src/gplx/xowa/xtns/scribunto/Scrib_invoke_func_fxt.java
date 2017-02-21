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
import gplx.xowa.xtns.scribunto.libs.*; import gplx.xowa.xtns.scribunto.engines.process.*; import gplx.xowa.xtns.scribunto.procs.*;
import gplx.xowa.parsers.tmpls.*;	
public class Scrib_invoke_func_fxt {
	private Xop_fxt fxt; Bry_bfr tmp_bfr = Bry_bfr_.Reset(255); Scrib_core core; Process_server_mock server; Scrib_lua_rsp_bldr rsp_bldr = new Scrib_lua_rsp_bldr();
	public Xop_fxt Parser_fxt() {return fxt;}
	public Scrib_core_fxt Core_fxt() {return core_fxt;} Scrib_core_fxt core_fxt;
	public Scrib_core Core() {return core;}
	public void Clear_for_invoke() {
		fxt = new Xop_fxt();	// NOTE: don't try to cache fxt on func_fxt level; causes errors in Language_lib
		core_fxt = new Scrib_core_fxt(fxt);
		core_fxt.Clear();
		core_fxt.Init_lib_mw();
		core = core_fxt.Core();
		server = core_fxt.Server();
		core.Interpreter().Server_(server);
		Io_mgr.Instance.InitEngine_mem();
		fxt.Reset();
		core.When_page_changed(fxt.Page());
		init_tmpl = init_page = null;
		fxt.Init_page_create("Module:Mod_0");
		this.Init_lua_rcvd_loadModule(); 
	}
	public Scrib_invoke_func_fxt Init_cbk(String lib_name, Scrib_lib lib, String... proc_names) {
		int len = proc_names.length;
		for (int i = 0; i < len; i++) {
			String proc_name = proc_names[i];
			Scrib_proc proc = lib.Procs().Get_by_key(proc_name);
			core.Proc_mgr().Set(proc.Proc_key(), proc);	// NOTE: allow tests to call Init_cbk multiple times
		}
		return this;
	}
	public Scrib_invoke_func_fxt Init_tmpl(String v) {init_tmpl = v; return this;} private String init_tmpl;
	public Scrib_invoke_func_fxt Init_page(String v) {init_page = v; return this;} private String init_page;
	public Scrib_invoke_func_fxt Init_server_print_key_y_() {server.Print_key_(true); return this;}
	public Scrib_invoke_func_fxt Init_server_print_key_n_() {server.Print_key_(false); return this;}
	public Scrib_invoke_func_fxt Init_lua_rcvd_raw(String raw) {server.Prep_add(raw); return this;}
	public Scrib_invoke_func_fxt Init_lua_rcvd(String cbk_name, Keyval... ary) {
		server.Prep_add(rsp_bldr.Bld_mw_cbk(cbk_name, ary));
		return this;
	}
	public Scrib_invoke_func_fxt Init_lua_module() {
		server.Prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:2:{i:1;b:1;i:2;O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:14;}}}");
		return this;
	}
	private Scrib_invoke_func_fxt Init_lua_rcvd_loadModule() {
		server.Prep_add("a:3:{s:2:\"op\";s:6:\"return\";s:7:\"nvalues\";i:1;s:6:\"values\";a:1:{i:1;i:1;}}");
		return this;
	}
	public Scrib_invoke_func_fxt Init_lua_rcvd_preprocess(String frame, String cmd) {
		server.Prep_add(String_.Concat_any("a:4:{s:2:\"id\";s:23:\"mw_interface-preprocess\";s:2:\"op\";s:4:\"call\";s:5:\"nargs\";i:2;s:4:\"args\";a:2:{i:1;s:", String_.Len(frame), ":\"", frame, "\";i:2;s:", String_.Len(cmd), ":\"", cmd, "\";}}"));
		this.Init_lua_rcvd_rv();
		return this;
	}
	public Scrib_invoke_func_fxt Init_lua_rcvd_expandTemplate(String frame, String tmpl_ttl, Keyval... ary) {
		ary = new Keyval[] {Keyval_.int_(1, "current"), Keyval_.int_(2, tmpl_ttl), Keyval_.int_(3, ary)};
		server.Prep_add(rsp_bldr.Bld_mw_cbk("expandTemplate", ary));
		this.Init_lua_rcvd_rv();
		return this;
	}
	public Scrib_invoke_func_fxt Init_lua_rcvd_rv() {
		server.Prep_add_dynamic_val();
		return this;
	}
	public void Test_invoke(String expd) {
		if (init_tmpl != null) fxt.Init_defn_add("test", init_tmpl);
		fxt.Test_parse_tmpl_str(init_page, expd);
	}
	public void Test_parse_err(String raw, String expd_err_type) {
		Scrib_invoke_func.Error(tmp_bfr, fxt.Wiki().Msg_mgr(), expd_err_type);
		fxt.Test_parse_page_tmpl_str(raw, tmp_bfr.To_str_and_clear());
	}
	public void Test_error(Exception e, String expd) {
		Scrib_invoke_func.Error(tmp_bfr, fxt.Wiki().Msg_mgr(), Err_.Cast_or_make(e));
		Tfds.Eq_str(expd, tmp_bfr.To_str_and_clear(), "error");
	}
	public void Test_lib_proc(Scrib_lib lib, String func_name, Object[] args, String expd) {Test_lib_proc_kv(lib, func_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test_lib_proc_kv(Scrib_lib lib, String func_name, Keyval[] args, String expd) {
		Test_lib_proc_internal(lib, func_name, args);
		this.Test_invoke(expd);
	}
	private void Test_lib_proc_internal(Scrib_lib lib, String func_name, Keyval[] args) {
		Init_lua_module();
		this.Init_cbk(Scrib_core.Key_mw_interface, lib, func_name);
		this.Init_lua_rcvd(func_name, args);
		this.Init_lua_rcvd_rv();
	}
	public void Test_log_rcvd(int i, String expd) {
		Tfds.Eq(expd, (String)server.Log_rcvd().Get_at(i));
	}
	public void Init_frame_parent(String ttl, Keyval... ary) {
		core.Frame_parent_(Xot_invk_mock.test_(Bry_.new_u8(ttl), ary));
	}
	public void Init_frame_current(Keyval... ary) {
		core.Frame_current_(Xot_invk_mock.test_(Bry_.new_a7("Module:Mod_0"), ary));
	}
	public void Clear_for_lib() {Clear_for_lib("en.wikipedia.org", "en");}
	public void Clear_for_lib(String domain, String lang) {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		fxt = new Xop_fxt(app, Xoa_app_fxt.Make__wiki__edit(app, domain, app.Lang_mgr().Get_by_or_new(Bry_.new_u8(lang)))); // NOTE: don't try to cache fxt on func_fxt level; causes errors in Language_lib
		core_fxt = new Scrib_core_fxt(fxt);
		core = core_fxt.Core();
		Xot_invk parent_frame = new Xot_invk_temp(true); parent_frame.Frame_tid_(Scrib_frame_.Tid_null); 
		Xot_invk current_frame = Xot_invk_mock.test_(Bry_.new_a7("Module:Mod_0"));
		core.Invoke_init(core.Wiki(), core.Ctx(), Bry_.Empty, parent_frame, current_frame);
		core.When_page_changed(fxt.Page());
	}
	public void Test_scrib_proc_str(Scrib_lib lib, String proc_name, Object[] args, String expd) {Test_scrib_proc_str(lib, proc_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test_scrib_proc_str(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {
		Keyval[] actl = Test__lib_proc__core(lib, proc_name, args);
		Tfds.Eq(Object_.Xto_str_strict_or_null_mark(expd), Object_.Xto_str_strict_or_null_mark(actl[0].Val()));
	}
	public void Test__proc__kvps__flat(Scrib_lib lib, String proc_name, Object[] args, String expd) {Test__proc__kvps__flat(lib, proc_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test__proc__kvps__flat(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {
		Keyval[] actl_ary = Test__lib_proc__core(lib, proc_name, args);
		Tfds.Eq(expd, Kv_ary_to_kv_vals_str(actl_ary));
	}
	public void Test_scrib_proc_kv_objs(Scrib_lib lib, String proc_name, Keyval[] args, Object... expd_ary) {
		Keyval[] actl_kvs = Test__lib_proc__core(lib, proc_name, args);
		Object[] actl_ary = KeyVal_to_obj_ary(actl_kvs);
		Tfds.Eq_ary(expd_ary, actl_ary);
	}
	public void Test_scrib_proc_err(Scrib_lib lib, String proc_name, Object[] args, String expd) {
		Scrib_proc proc = lib.Procs().Get_by_key(proc_name);
		Scrib_proc_rslt rslt = new Scrib_proc_rslt();
		boolean exec_rslt = proc.Proc_exec(new Scrib_proc_args(Scrib_kv_utl_.base1_many_(args)), rslt);
		Tfds.Eq(Bool_.N, exec_rslt);
		Tfds.Eq(null, rslt.Ary());
		Tfds.Eq(expd, rslt.Fail_msg());
	}
	private static Object[] KeyVal_to_obj_ary(Keyval[] kv_ary) {
		int len = kv_ary.length;
		Object[] rv = new Object[len];
		for (int i = 0; i < len; ++i) {
			Keyval kv = kv_ary[i];
			rv[i] = kv.Val();
		}
		return rv;
	}
	private String Kv_ary_to_kv_vals_str(Keyval[] ary) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = ary.length;
		for (int i = 0; i < len; ++i) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Semic);
			Keyval kv = ary[i];
			bfr.Add_str_u8(Object_.Xto_str_strict_or_null_mark(kv.Val()));
		}
		return bfr.To_str_and_clear();
	}
	public void Test_scrib_proc_bool(Scrib_lib lib, String proc_name, Object[] args, boolean expd) {Test_scrib_proc_obj(lib, proc_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test_scrib_proc_int(Scrib_lib lib, String proc_name, Object[] args, int expd) {Test_scrib_proc_obj(lib, proc_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test_scrib_proc_obj(Scrib_lib lib, String proc_name, Object[] args, Object expd) {Test_scrib_proc_obj(lib, proc_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test_scrib_proc_obj(Scrib_lib lib, String proc_name, Keyval[] args, Object expd) {
		Keyval[] actl = Test__lib_proc__core(lib, proc_name, args);
		Tfds.Eq(expd, actl[0].Val());
	}
	public void Test_scrib_proc_empty(Scrib_lib lib, String proc_name, Object[] args) {Test_scrib_proc_empty(lib, proc_name, Scrib_kv_utl_.base1_many_(args));}
	public void Test_scrib_proc_empty(Scrib_lib lib, String proc_name, Keyval[] args) {
		Keyval[] actl = Test__lib_proc__core(lib, proc_name, args);
		Tfds.Eq(0, actl.length);
	}
	public void Test_scrib_proc_str_ary(Scrib_lib lib, String proc_name, Object[] args, String expd) {Test_scrib_proc_str_ary(lib, proc_name, Scrib_kv_utl_.base1_many_(args), expd);}
	public void Test_scrib_proc_str_ary(Scrib_lib lib, String proc_name, Keyval[] args, String expd) {
		Keyval[] actl_ary = Test__lib_proc__core(lib, proc_name, args);
		String actl = Keyval_.Ary__to_str__nest(actl_ary);
		Tfds.Eq_str_lines(expd, actl);
	}
	public Keyval[] Test_scrib_proc_rv_as_kv_ary(Scrib_lib lib, String proc_name, Object[] args) {
		Keyval[] actl = Test__lib_proc__core(lib, proc_name, Scrib_kv_utl_.base1_many_(args));
		return (Keyval[])actl[0].Val();
	}
	public Object Test_scrib_proc_rv_as_obj(Scrib_lib lib, String proc_name, Object[] args) {
		Keyval[] actl = Test__lib_proc__core(lib, proc_name, Scrib_kv_utl_.base1_many_(args));
		return actl[0].Val();
	}
	private Keyval[] Test__lib_proc__core(Scrib_lib lib, String proc_name, Keyval[] args) {
		Scrib_proc proc = lib.Procs().Get_by_key(proc_name);
		Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
		proc.Proc_exec(new Scrib_proc_args(args), proc_rslt);
		return proc_rslt.Ary();
	}
	public static final String Null_rslt		= "<<NULL>>";
	public static final String Null_rslt_ary	= "1=<<NULL>>";
}
class Scrib_lua_rsp_bldr {
	Bry_bfr bfr = Bry_bfr_.Reset(255);
	public String Bld_mw_cbk(String cbk_name, Keyval... ary) {
		cbk_name = "mw_interface-" + cbk_name;
		bfr.Add_str_a7("a:4:{s:2:\"id\";");
		Bld_str(bfr, cbk_name);
		bfr.Add_str_a7("s:2:\"op\";s:4:\"call\";s:5:\"nargs\";i:3;s:4:\"args\";");
		Bld_kv_ary(bfr, ary);
		bfr.Add_str_a7("}");
		return bfr.To_str_and_clear();
	}
	private void Bld_obj(Bry_bfr bfr, Object v) {
		Class<?> v_type = v.getClass();
		if		(Object_.Eq(v_type, Int_.Cls_ref_type))			Bld_int(bfr, Int_.cast(v));
		else if	(Object_.Eq(v_type, String_.Cls_ref_type))		Bld_str(bfr, String_.cast(v));
		else if	(Object_.Eq(v_type, Bool_.Cls_ref_type))		Bld_bool(bfr, Bool_.Cast(v));
		else if	(Object_.Eq(v_type, Double_.Cls_ref_type))		Bld_double(bfr, Double_.cast(v));
		else if	(Object_.Eq(v_type, Keyval[].class))			Bld_kv_ary(bfr, (Keyval[])v);
		else if	(Object_.Eq(v_type, Scrib_lua_proc.class))	Bld_fnc(bfr, (Scrib_lua_proc)v);
		else													throw Err_.new_unhandled(Type_adp_.NameOf_obj(v));
	}
	private void Bld_bool(Bry_bfr bfr, boolean v)		{bfr.Add_str_a7("b:").Add_int_fixed(v ? 1 : 0, 1).Add_byte(Byte_ascii.Semic);}
	private void Bld_int(Bry_bfr bfr, int v)		{bfr.Add_str_a7("i:").Add_int_variable(v).Add_byte(Byte_ascii.Semic);}
	private void Bld_double(Bry_bfr bfr, double v)	{bfr.Add_str_a7("d:").Add_double(v).Add_byte(Byte_ascii.Semic);}
	private void Bld_str(Bry_bfr bfr, String v)		{bfr.Add_str_a7("s:").Add_int_variable(Bry_.new_u8(v).length).Add_str_a7(":\"").Add_str_a7(v).Add_str_a7("\";");}	// NOTE: must use Bry_.new_u8(v).length to calculate full bry len
	private void Bld_fnc(Bry_bfr bfr, Scrib_lua_proc v)	{bfr.Add_str_a7("O:42:\"Scribunto_LuaStandaloneInterpreterFunction\":1:{s:2:\"id\";i:").Add_int_variable(v.Id()).Add_byte(Byte_ascii.Semic).Add_byte(Byte_ascii.Curly_end);}
	private void Bld_kv_ary(Bry_bfr bfr, Keyval[] ary) {
		int len = ary.length;
		bfr.Add_str_a7("a:").Add_int_variable(len).Add_str_a7(":{");
		for (int i = 0; i < len; i++) {
			Keyval kv = ary[i];
			Bld_obj(bfr, kv.Key_as_obj());
			Bld_obj(bfr, kv.Val());
		}
		bfr.Add_byte(Byte_ascii.Curly_end);
	}
}
