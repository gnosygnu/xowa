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
import gplx.core.envs.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.scribunto.libs.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_core {
	private Hash_adp_bry mods = Hash_adp_bry.cs();
	private int expensive_function_count;
	public Scrib_core(Xoae_app app, Xop_ctx ctx) {// NOTE: ctx needed for language reg
		this.app = app; this.ctx = ctx;
		this.wiki = ctx.Wiki(); this.page = ctx.Page();	// NOTE: wiki / page needed for title reg; DATE:2014-02-05
		this.lang = wiki.Lang();
		this.Engine_(Scrib_engine_type.Type_lua, false);	// TEST: default to lua
		fsys_mgr.Root_dir_(app.Fsys_mgr().Bin_xtns_dir().GenSubDir_nest("Scribunto"));
		lib_mw = new Scrib_lib_mw(this);
		lib_uri = new Scrib_lib_uri(this); 
		lib_ustring = new Scrib_lib_ustring(this);
		lib_language = new Scrib_lib_language(this);
 		lib_site = new Scrib_lib_site(this);
 		lib_title = new Scrib_lib_title(this);
 		lib_message = new Scrib_lib_message(this);
 		lib_text = new Scrib_lib_text(this);
		lib_html = new Scrib_lib_html(this);
		lib_wikibase = new Scrib_lib_wikibase(this);
		lib_wikibase_entity = new Scrib_lib_wikibase_entity(this);
	}
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xol_lang_itm Lang() {return lang;} private Xol_lang_itm lang;
	@gplx.Internal protected void Wiki_(Xowe_wiki v) {this.wiki = v;} // TEST:
	public Xoae_page Page() {return page;} private Xoae_page page;
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public void Engine_(Scrib_engine v) {this.engine = v;}
	private void Engine_(byte type, boolean luaj_debug_enabled) {
		if		(type == Scrib_engine_type.Type_lua)
			engine = new gplx.xowa.xtns.scribunto.engines.process.Process_engine(app, wiki, this);
		else if (type == Scrib_engine_type.Type_luaj)
			engine = new gplx.xowa.xtns.scribunto.engines.luaj.Luaj_engine(app, this, luaj_debug_enabled);
	}
	public Scrib_fsys_mgr Fsys_mgr() {return fsys_mgr;} private Scrib_fsys_mgr fsys_mgr = new Scrib_fsys_mgr();
	public Scrib_proc_mgr Proc_mgr() {return proc_mgr;} private Scrib_proc_mgr proc_mgr = new Scrib_proc_mgr();
	public Scrib_engine Interpreter() {return engine;} private Scrib_engine engine;
	public Scrib_lib_mw Lib_mw() {return lib_mw;} private Scrib_lib_mw lib_mw;
	public Scrib_lib_uri Lib_uri() {return lib_uri;} private Scrib_lib_uri lib_uri;
	public Scrib_lib_ustring Lib_ustring() {return lib_ustring;} private Scrib_lib_ustring lib_ustring;
	public Scrib_lib_language Lib_language() {return lib_language;} private Scrib_lib_language lib_language;
	public Scrib_lib_site Lib_site() {return lib_site;} private Scrib_lib_site lib_site;
	public Scrib_lib_title Lib_title() {return lib_title;} private Scrib_lib_title lib_title;
	public Scrib_lib_message Lib_message() {return lib_message;} private Scrib_lib_message lib_message;
	public Scrib_lib_text Lib_text() {return lib_text;} private Scrib_lib_text lib_text;
	public Scrib_lib_html Lib_html() {return lib_html;} private Scrib_lib_html lib_html;
	public Scrib_lib_wikibase Lib_wikibase() {return lib_wikibase;} private Scrib_lib_wikibase lib_wikibase;
	public Scrib_lib_wikibase_entity Lib_wikibase_entity() {return lib_wikibase_entity;} private Scrib_lib_wikibase_entity lib_wikibase_entity;
	public Scrib_core Init() {	// REF:LuaCommon.php!Load
		Scrib_xtn_mgr xtn_mgr = (Scrib_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Scrib_xtn_mgr.XTN_KEY);
		Engine_(xtn_mgr.Engine_type(), xtn_mgr.Luaj_debug_enabled());
		engine.Server().Server_timeout_(xtn_mgr.Lua_timeout()).Server_timeout_polling_(xtn_mgr.Lua_timeout_polling()).Server_timeout_busy_wait_(xtn_mgr.Lua_timeout_busy_wait());
		enabled = xtn_mgr.Enabled();
		Io_url root_dir = fsys_mgr.Root_dir(), script_dir = fsys_mgr.Script_dir();
		engine.Server().Init
		(	app.Prog_mgr().App_lua().Exe_url().Raw()
		,	root_dir.GenSubFil_nest("engines", "LuaStandalone", "mw_main.lua").Raw()
		,	root_dir.Raw()
		);
		Init_register(script_dir, lib_mw, lib_uri, lib_ustring, lib_language, lib_site, lib_title, lib_text, lib_html, lib_message, lib_wikibase, lib_wikibase_entity);
		xtn_mgr.Lib_mgr().Init_for_core(this, script_dir);
		return this;
	}
	private void Init_register(Io_url script_dir, Scrib_lib... ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++)
			ary[i].Register(this, script_dir);
	}
	public void Term() {engine.Server().Term(); mods.Clear();}
	public void When_page_changed(Xoae_page page) {
		mods.Clear();	// clear any loaded modules
		Xow_wiki wiki = page.Wiki();
		this.page = page;
		byte[] new_wiki = wiki.Domain_bry();
		if (!Bry_.Eq(cur_wiki, new_wiki)) {
			cur_wiki = new_wiki;
			lib_site.Notify_wiki_changed();
			lib_text.Notify_wiki_changed();
		}
		byte[] new_lang = wiki.Lang().Key_bry();
		if (!Bry_.Eq(cur_lang, new_lang)) {
			cur_lang = new_lang;
			lib_message.Notify_lang_changed();
			lib_language.Notify_lang_changed();
		}
		lib_uri.Notify_page_changed();
		lib_title.Notify_page_changed();
		lib_wikibase.Notify_page_changed();
		expensive_function_count = 0;
	}	
	public byte[] Cur_wiki() {return cur_wiki;} private byte[] cur_wiki = Bry_.Empty; 
	public byte[] Cur_lang() {return cur_lang;} private byte[] cur_lang = Bry_.Empty;
	public void Increment_expensive_function_count() {
		++expensive_function_count;
		if (expensive_function_count > 255) {}
	}
	public Scrib_lua_mod RegisterInterface(Scrib_lib lib, Io_url url, Keyval... args) {
		this.RegisterLibrary(lib.Procs());
		Scrib_lua_mod rv = this.LoadLibraryFromFile(url.NameAndExt(), Io_mgr.Instance.LoadFilStr(url));
		Scrib_lua_proc setupInterface_func = rv.Fncs_get_by_key("setupInterface");
		if (setupInterface_func != null)
			engine.CallFunction(setupInterface_func.Id(), Scrib_kv_utl_.base1_obj_(args));
		return rv;
	}
	public void RegisterLibrary(Scrib_proc_mgr lib_proc_mgr) {
		int len = lib_proc_mgr.Len();
		Keyval[] functions_ary = new Keyval[len];
		for (int i = 0; i < len; i++) {
			Scrib_proc lib_proc = lib_proc_mgr.Get_at(i);
			String lib_proc_key = lib_proc.Proc_key();
			this.proc_mgr.Set(lib_proc_key, lib_proc);
			functions_ary[i] = Keyval_.new_(lib_proc.Proc_name(), lib_proc_key);
		}
		engine.RegisterLibrary(functions_ary);
	}
	@gplx.Internal protected Scrib_lua_mod LoadLibraryFromFile(String name, String text) {
		int lib_id = engine.LoadString("@" + name, text).Id();	// NOTE: 'Prepending an "@" to the chunk name makes Lua think it is a filename'
		Keyval[] values = engine.CallFunction(lib_id, Keyval_.Ary_empty);
		Scrib_lua_mod rv = new Scrib_lua_mod(this, name);
		if (values.length > 0) {	// NOTE: values.length == 0 for "package.lua" (no fnc_ids returned);
			Keyval[] fncs = Scrib_kv_utl_.Val_to_KeyVal_ary(values, 0);
			int len = fncs.length;
			for (int i = 0; i < len; i++) {
				Keyval itm = fncs[i];
				Scrib_lua_proc fnc = Scrib_lua_proc.cast_or_null_(itm.Val());
				if (fnc != null) rv.Fncs_add(fnc);	// NOTE: some lua funcs will return INF; EX: stringLengthLimit
			}
		}
		return rv;
	}
	public Ordered_hash Frame_created_list() {return frame_created_list;} private Ordered_hash frame_created_list = Ordered_hash_.New();	// created by NewChildFrame
	public Xot_invk Frame_current() {return frame_current;} private Xot_invk frame_current;
	public Xot_invk Frame_parent() {return frame_parent;} private Xot_invk frame_parent;
	@gplx.Internal protected void Frame_current_(Xot_invk v) {frame_current = v;} // TEST:
	@gplx.Internal protected void Frame_parent_(Xot_invk v) {frame_parent = v;}	// TEST:
	public Xop_ctx Ctx() {return ctx;} private Xop_ctx ctx;
	public byte[] Cur_src() {return cur_src;} private byte[] cur_src; // only used for error reporting
	public void Invoke_init(Xowe_wiki wiki, Xop_ctx ctx, byte[] src, Xot_invk parent_frame, Xot_invk current_frame) {	// TEST
		this.wiki = wiki; this.ctx = ctx; this.cur_src = src;
		lib_mw.Invoke_bgn(wiki, ctx, src);
		this.frame_parent = parent_frame; this.frame_current = current_frame;
	}
	public void Invoke(Xowe_wiki wiki, Xop_ctx ctx, byte[] src, Xot_invk parent_frame, Xot_invk current_frame, Bry_bfr bfr, byte[] mod_name, byte[] mod_text, byte[] fnc_name) {
		// save current values for restoring later
		Xot_invk old_frame_parent = this.frame_parent; Xot_invk old_frame_current = this.frame_current;
		byte[] old_src = cur_src;

		// init
		this.wiki = wiki; this.ctx = ctx; this.cur_src = src;
		lib_mw.Invoke_bgn(wiki, ctx, src);
		this.frame_parent = parent_frame; this.frame_current = current_frame;
		parent_frame.Frame_tid_(Scrib_frame_.Tid_parent); current_frame.Frame_tid_(Scrib_frame_.Tid_current);

		try {
			Scrib_lua_mod mod = Mods_get_or_new(mod_name, mod_text);
			Keyval[] func_args = Scrib_kv_utl_.base1_many_(mod.Init_chunk_func(), String_.new_u8(fnc_name));
			Keyval[] func_rslt = engine.CallFunction(lib_mw.Mod().Fncs_get_id("executeModule"), func_args);			// call init_chunk to get proc dynamically; DATE:2014-07-12
			if (func_rslt == null || func_rslt.length < 2) throw Err_.new_wo_type("lua.error:function did not return a value", "fnc_name", String_.new_u8(fnc_name)); // must return at least 2 items for func_rslt[1] below; DATE:2014-09-22
			Scrib_lua_proc proc = (Scrib_lua_proc)func_rslt[1].Val();												// note that init_chunk should have: [0]:true/false result; [1]:proc
			func_args = Scrib_kv_utl_.base1_many_(proc);
			func_rslt = engine.CallFunction(lib_mw.Mod().Fncs_get_id("executeFunction"), func_args);				// call function now
			String rslt = Scrib_kv_utl_.Val_to_str(func_rslt, 0);													// rslt expects an array with 1 scalar value
			bfr.Add_str_u8(rslt);
			//byte[] rslt_bry = Bry_.new_u8(rslt);	// CHART
			//gplx.xowa.parsers.xndes.Xop_xnde_tkn.Hack_ctx = ctx;
			//bfr.Add(rslt_bry);
			if (!Env_.Mode_testing())
				engine.CleanupChunks(Keyval_.Ary(Keyval_.int_(proc.Id(), "")));										// cleanup chunk immediately; needed for heavy pages like en.d:water; DATE:2014-08-07
		}
		finally {
			lib_mw.Invoke_end();
			parent_frame.Frame_tid_(Scrib_frame_.Tid_null); current_frame.Frame_tid_(Scrib_frame_.Tid_null);
			this.frame_parent = old_frame_parent; this.frame_current = old_frame_current;	// NOTE: reset template frame; PAGE:en.w:Constantine_the_Great {{Christianity}}; DATE:2014-06-26
			this.cur_src = old_src;
			frame_created_list.Clear();
		}
	}
	public Scrib_lua_mod Mods_get(byte[] mod_name) {return (Scrib_lua_mod)mods.Get_by(mod_name);}
	private Scrib_lua_mod Mods_get_or_new(byte[] mod_name, byte[] mod_text) {
		Scrib_lua_mod rv = (Scrib_lua_mod)mods.Get_by(mod_name);
		if (rv == null) {
			rv = new Scrib_lua_mod(this, "Module:" + String_.new_u8(mod_name));
			rv.LoadString(String_.new_u8(mod_text));
			mods.Add(mod_name, rv);
		}
		return rv;
	}
	public void Handle_error(String err) {
		String excerpt = "";
		try {
			Xot_invk src_frame = frame_current;
			if (src_frame != null)
				excerpt = String_.new_u8(cur_src, src_frame.Src_bgn(), src_frame.Src_end());
		} catch (Exception e) {Err_.Noop(e);}
		throw Err_.new_wo_type(err, "ttl", page.Ttl().Page_db_as_str(), "excerpt", excerpt);
	}
	public static final String Frame_key_module = "current", Frame_key_template = "parent";
	public static final int Base_1 = 1;
	public static final String Key_mw_interface = "mw_interface";
}
