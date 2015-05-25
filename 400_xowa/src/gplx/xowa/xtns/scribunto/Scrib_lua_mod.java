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
public class Scrib_lua_mod {
	private Ordered_hash hash = Ordered_hash_.new_();
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
		Scrib_lua_proc fnc = Fncs_get_by_key(key); if (fnc == null) throw Err_.new_fmt_("Scrb_fnc does not exist: module={0} func={1}", name, key);
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
		KeyVal[] rslt = core.Interpreter().ExecuteModule(lua_id);
		if (rslt.length == 0) throw Err_.new_fmt_("module missing: name={0} lua_id={1}", name, lua_id);
		KeyVal[] prcs_ary = (KeyVal[])rslt[0].Val();
		int prcs_len = prcs_ary.length;
		for (int i = 0; i < prcs_len; i++) {
			KeyVal prc_kv = prcs_ary[i];
			String prc_key = prc_kv.Key();
			Object prc_val = prc_kv.Val();
			Scrib_lua_proc fnc = null;
			if (ClassAdp_.ClassOf_obj(prc_val) == Scrib_lua_proc.class)
				fnc = (Scrib_lua_proc)prc_val;
			else
				fnc = new Scrib_lua_proc(prc_key, -1);
			Fncs_add(fnc);
		}
	}
}
