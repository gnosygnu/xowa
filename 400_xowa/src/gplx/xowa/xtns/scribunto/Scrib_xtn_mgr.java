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
import gplx.xowa.parsers.logs.*;
import gplx.xowa.xtns.scribunto.engines.*;
public class Scrib_xtn_mgr extends Xox_mgr_base {
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_ascii_("scribunto");
	@Override public void Xtn_ctor_by_app(Xoa_app app) {this.app = app;} private Xoa_app app;
	@Override public Xox_mgr Clone_new() {return new Scrib_xtn_mgr();}
	public Scrib_lib_mgr Lib_mgr() {return lib_mgr;} private Scrib_lib_mgr lib_mgr = new Scrib_lib_mgr();
	public byte Engine_type() {return engine_type;} private byte engine_type = Scrib_engine_type.Type_luaj;
	public void Engine_type_(byte cmd) {
		engine_type = cmd;
		Scrib_core.Core_invalidate();
	}
	public int Lua_timeout() {return lua_timeout;} private int lua_timeout = 4000;
	public int Lua_timeout_polling() {return lua_timeout_polling;} private int lua_timeout_polling = 1;
	public int Lua_timeout_busy_wait() {return lua_timeout_busy_wait;} private int lua_timeout_busy_wait = 250;
	public int Lua_timeout_loop() {return lua_timeout_loop;} private int lua_timeout_loop = 10000000;
	public boolean Lua_log_enabled() {return lua_log_enabled;} private boolean lua_log_enabled;
	public boolean Luaj_debug_enabled() {return luaj_debug_enabled;} private boolean luaj_debug_enabled;
	public void Luaj_debug_enabled_(boolean v) {
		this.luaj_debug_enabled = v;
		Scrib_core.Core_invalidate();	// restart server in case luaj caches any debug data
	}
	public Xop_log_invoke_wkr Invoke_wkr() {return invoke_wkr;} private Xop_log_invoke_wkr invoke_wkr;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_engine_type))					return Scrib_engine_type.X_to_str(engine_type);
		else if	(ctx.Match(k, Invk_engine_type_))					Engine_type_(Scrib_engine_type.X_to_byte(m.ReadStr("v")));
		else if	(ctx.Match(k, Invk_engine_type_list))				return Scrib_engine_type.Options__list;
		else if	(ctx.Match(k, Invk_lua_timeout))					return lua_timeout;
		else if	(ctx.Match(k, Invk_lua_timeout_))					lua_timeout = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_lua_timeout_polling))			return lua_timeout_polling;
		else if	(ctx.Match(k, Invk_lua_timeout_polling_))			lua_timeout_polling = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_lua_log_enabled))				return Yn.X_to_str(lua_log_enabled);
		else if	(ctx.Match(k, Invk_lua_log_enabled_))				lua_log_enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk_lua_timeout_busy_wait))			return lua_timeout_busy_wait;
		else if	(ctx.Match(k, Invk_lua_timeout_busy_wait_))			lua_timeout_busy_wait = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_lua_timeout_loop))				return lua_timeout_loop;
		else if	(ctx.Match(k, Invk_lua_timeout_loop_))				lua_timeout_loop = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_luaj_debug_enabled))				return Yn.X_to_str(luaj_debug_enabled);
		else if	(ctx.Match(k, Invk_luaj_debug_enabled_))			Luaj_debug_enabled_(m.ReadBool("v"));
		else if	(ctx.Match(k, Invk_invoke_wkr))						return m.ReadYnOrY("v") ? Invoke_wkr_or_new() : GfoInvkAble_.Null;
		else														return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String 
	  Invk_engine_type = "engine_type", Invk_engine_type_ = "engine_type_", Invk_engine_type_list = "engine_type_list"
	, Invk_lua_timeout = "lua_timeout", Invk_lua_timeout_ = "lua_timeout_"
	, Invk_lua_timeout_polling = "lua_timeout_polling", Invk_lua_timeout_polling_ = "lua_timeout_polling_"
	, Invk_lua_log_enabled = "lua_log_enabled", Invk_lua_log_enabled_ = "lua_log_enabled_"
	, Invk_lua_timeout_loop = "lua_timeout_loop", Invk_lua_timeout_loop_ = "lua_timeout_loop_"
	, Invk_lua_timeout_busy_wait = "lua_timeout_busy_wait", Invk_lua_timeout_busy_wait_ = "lua_timeout_busy_wait_"
	, Invk_luaj_debug_enabled = "luaj_debug_enabled", Invk_luaj_debug_enabled_ = "luaj_debug_enabled_"
	, Invk_invoke_wkr = "invoke_wkr"
	;
	public Xop_log_invoke_wkr Invoke_wkr_or_new() {
		if (invoke_wkr == null) invoke_wkr = app.Log_mgr().Make_wkr_invoke();
		return invoke_wkr;
	}
	public static Err err_(String fmt, Object... args) {return Err_.new_fmt_(fmt, args);}
	public static Err err_(Exception e, String fmt, Object... args) {return Err_.new_fmt_(fmt, args);}
	public static final int Ns_id_module = 828, Ns_id_module_talk = 829;
	public static final String Ns_name_module = "Module", Ns_name_module_talk = "Module talk";
}
