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
import gplx.xowa.parsers.logs.*;
import gplx.xowa.xtns.scribunto.engines.*;
public class Scrib_xtn_mgr extends Xox_mgr_base {
	private Xowe_wiki wiki;
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final    byte[] XTN_KEY = Bry_.new_a7("scribunto");
	@Override public void Xtn_ctor_by_app(Xoae_app app) {this.app = app;} private Xoae_app app;
	@Override public Xox_mgr Xtn_clone_new() {return new Scrib_xtn_mgr();}
	public Scrib_lib_mgr Lib_mgr() {return lib_mgr;} private Scrib_lib_mgr lib_mgr = new Scrib_lib_mgr();
	public byte Engine_type() {return engine_type;} private byte engine_type = Scrib_engine_type.Type_luaj;
	public int Lua_timeout() {return lua_timeout;} private int lua_timeout = 4000;
	public int Lua_timeout_polling() {return lua_timeout_polling;} private int lua_timeout_polling = 1;
	public int Lua_timeout_busy_wait() {return lua_timeout_busy_wait;} private int lua_timeout_busy_wait = 250;
	public int Lua_timeout_loop() {return lua_timeout_loop;} private int lua_timeout_loop = 10000000;
	public boolean Lua_log_enabled() {return lua_log_enabled;} private boolean lua_log_enabled = false;
	public boolean Luaj_debug_enabled() {return luaj_debug_enabled;} private boolean luaj_debug_enabled;
	public void Luaj_debug_enabled_(boolean v) {
		this.luaj_debug_enabled = v;
		gplx.xowa.xtns.scribunto.Scrib_core_mgr.Term_all(app);// restart server in case luaj caches any debug data
	}
	public Xop_log_invoke_wkr Invoke_wkr() {return invoke_wkr;} private Xop_log_invoke_wkr invoke_wkr;
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		this.wiki = wiki;
		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__enabled, Cfg__engine, Cfg__lua__timeout, Cfg__lua__timeout_busy_wait, Cfg__lua__timeout_polling);
	}

	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_invoke_wkr))						return m.ReadYnOrY("v") ? Invoke_wkr_or_new() : Gfo_invk_.Noop;
		else if	(ctx.Match(k, Cfg__enabled))						{Enabled_(m.ReadYn("v")); wiki.Parser_mgr().Scrib().Core_term();}
		else if	(ctx.Match(k, Cfg__engine))							{engine_type = Scrib_engine_type.Xto_byte(m.ReadStr("v")); wiki.Parser_mgr().Scrib().Core_term();}
		else if	(ctx.Match(k, Cfg__lua__timeout))					{lua_timeout = m.ReadInt("v"); wiki.Parser_mgr().Scrib().Core_term();}
		else if	(ctx.Match(k, Cfg__lua__timeout_polling))			{lua_timeout_polling = m.ReadInt("v"); wiki.Parser_mgr().Scrib().Core_term();}
		else if	(ctx.Match(k, Cfg__lua__timeout_busy_wait))			{lua_timeout_busy_wait = m.ReadInt("v"); wiki.Parser_mgr().Scrib().Core_term();}
		else														return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_invoke_wkr = "invoke_wkr";
	public Xop_log_invoke_wkr Invoke_wkr_or_new() {
		if (invoke_wkr == null) invoke_wkr = app.Log_mgr().Make_wkr_invoke();
		return invoke_wkr;
	}
	public static Err err_(String fmt, Object... args)						{return Err_.new_wo_type(fmt, args).Trace_ignore_add_1_();}
	public static Err err_(Exception e, String msg, Object... args)	{return Err_.new_exc(e, "xo", msg, args).Trace_ignore_add_1_();}
	private static final String 
	  Cfg__enabled					= "xowa.addon.scribunto.enabled"
	, Cfg__engine					= "xowa.addon.scribunto.engine"
	, Cfg__lua__timeout				= "xowa.addon.scribunto.lua.timeout"
	, Cfg__lua__timeout_polling		= "xowa.addon.scribunto.lua.timeout_polling"
	, Cfg__lua__timeout_busy_wait	= "xowa.addon.scribunto.lua.timeout_busy_wait"
	;
}
