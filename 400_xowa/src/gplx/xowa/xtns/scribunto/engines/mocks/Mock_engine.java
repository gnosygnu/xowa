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
import gplx.core.primitives.*;
import gplx.core.lists.hashs.*;
public class Mock_engine implements Scrib_engine {
	private final    Hash_adp__int fnc_hash = new Hash_adp__int();
	private final    Hash_adp mod_init_hash = Hash_adp_.New();
	private final    Hash_adp__int mod_fnc_hash = new Hash_adp__int();

	public boolean Dbg_print() {return false;}	public void Dbg_print_(boolean v) {}
	public Scrib_server Server() {return server;} public void Server_(Scrib_server v) {} private final    Mock_server server = new Mock_server();

	public Scrib_lua_proc LoadString(String name, String text) {
		return (Scrib_lua_proc)mod_init_hash.Get_by(name);
	}
	public Keyval[] CallFunction(int id, Keyval[] args) {
		Mock_proc_fxt fnc = (Mock_proc_fxt)fnc_hash.Get_by_or_fail(id);
		return fnc.Exec_by_scrib(args);
	}
	public void RegisterLibrary(Keyval[] functions_ary) {}
	public Keyval[] ExecuteModule(int mod_id) {throw Err_.new_unimplemented();}
	public void CleanupChunks(Keyval[] ids) {}
	public void	Clear() {fnc_hash.Clear();}

	public void	InitFunctionForTest(Mock_proc_fxt proc) {
		fnc_hash.Add(proc.Id(), proc);
	}

	public void Init_module(String mod_name, int mod_id) {
		mod_init_hash.Add_if_dupe_use_nth(mod_name, new Scrib_lua_proc(mod_name, mod_id));
	}
	public void Init_module_func(int mod_id, Mock_proc_fxt fnc) {
		Hash_adp funcs = (Hash_adp)mod_fnc_hash.Get_by_or_null(mod_id);
		if (funcs == null) {
			funcs = Hash_adp_.New();
			mod_fnc_hash.Add(mod_id, funcs);
		}
		fnc_hash.Add(fnc.Id(), fnc);
		funcs.Add(fnc.Key(), fnc.To_scrib_lua_proc());
	}
	public Scrib_lua_proc Get_module_func(int mod_id, String fnc_name) {
		Hash_adp funcs = (Hash_adp)mod_fnc_hash.Get_by_or_fail(mod_id);
		return (Scrib_lua_proc)funcs.Get_by_or_fail(fnc_name);
	}
}
