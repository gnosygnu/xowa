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
public class Scrib_lua_mod {
	private Ordered_hash hash = Ordered_hash_.New();
	public Scrib_lua_mod(Scrib_core core, String name) {this.name = name; this.core = core;} private Scrib_core core;
	public int Lua_id() {return lua_id;} private int lua_id = -1;
	public String Name() {return name;} private String name;
	public Scrib_lua_proc Init_chunk_func() {return init_chunk_func;} private Scrib_lua_proc init_chunk_func;
	public byte[] Text_bry() {return text_bry;} private byte[] text_bry;
	public void Fncs_clear() {hash.Clear();}
	public int Fncs_len() {return hash.Count();}
	public Scrib_lua_proc Fncs_get_at(int i) {return (Scrib_lua_proc)hash.Get_at(i);}
	public Scrib_lua_proc Fncs_get_by_key(String key) {return (Scrib_lua_proc)hash.Get_by(key);}
	public void Fncs_add(Scrib_lua_proc prc) {hash.Add(prc.Key(), prc);}
	public int Fncs_get_id(String key) {
		Scrib_lua_proc fnc = Fncs_get_by_key(key); if (fnc == null) throw Err_.new_wo_type("Scrb_fnc does not exist", "module", name, "func", key);
		return fnc.Id();
	}
	public Scrib_lua_proc LoadString(String text) {
		if (lua_id != -1) return init_chunk_func;
		text = String_.Replace(text, "&#09;", "\t");	// NOTE: this should only get called once per module
		text_bry = Bry_.new_u8(text);
		init_chunk_func = core.Interpreter().LoadString("=" + name, text);	// MW: Scribunto: Prepending an "=" to the chunk name avoids truncation or a "[string" prefix;
		lua_id = init_chunk_func.Id();
		return init_chunk_func;
	}
	public void Execute() {
		hash.Clear();	// NOTE: questionable. should probably be removed, as it forces all modules to be "loadString"'d again; DATE:2013-10-16
		this.LoadString(name);	// assert lua_id;
		Keyval[] rslt = core.Interpreter().ExecuteModule(lua_id);
		if (rslt.length == 0) throw Err_.new_wo_type("module missing", "name", name, "lua_id", lua_id);
		Keyval[] prcs_ary = (Keyval[])rslt[0].Val();
		int prcs_len = prcs_ary.length;
		for (int i = 0; i < prcs_len; i++) {
			Keyval prc_kv = prcs_ary[i];
			String prc_key = prc_kv.Key();
			Object prc_val = prc_kv.Val();
			Scrib_lua_proc fnc = null;
			if (Type_adp_.ClassOf_obj(prc_val) == Scrib_lua_proc.class)
				fnc = (Scrib_lua_proc)prc_val;
			else
				fnc = new Scrib_lua_proc(prc_key, -1);
			Fncs_add(fnc);
		}
	}
}
