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
import gplx.xowa.xtns.scribunto.procs.*;
public abstract class Mock_proc_stub {
	public Mock_proc_stub(int id, String key) {this.id = id; this.key = key;}
	public int Id() {return id;} private final int id;
	public String Key() {return key;} private final String key;
	public Scrib_lua_proc To_scrib_lua_proc() {return new Scrib_lua_proc(key, id);}
	public abstract Keyval[] Exec_by_scrib(Keyval[] args);
}
class Mock_exec_module extends Mock_proc_stub { 	private final Mock_engine engine;
	public Mock_exec_module(int mod_id, Mock_engine engine) {super(mod_id, "mockExecuteModule");
		this.engine = engine;
	}
	@Override public Keyval[] Exec_by_scrib(Keyval[] args) {
		Scrib_lua_proc mod_proc = (Scrib_lua_proc)args[0].Val();
		String fnc_name = (String)args[1].Val();
		return new Keyval[] {Keyval_.int_(0, true), Keyval_.int_(1, engine.Get_module_func(mod_proc.Id(), fnc_name))};
	}
}
class Mock_exec_function extends Mock_proc_stub { 	private final Mock_engine engine;
	public Mock_exec_function(int func_id, Mock_engine engine) {super(func_id, "mockExecuteFuntion");
		this.engine = engine;
	}
	@Override public Keyval[] Exec_by_scrib(Keyval[] args) {
		Scrib_lua_proc mod_proc = (Scrib_lua_proc)args[0].Val();
		return engine.CallFunction(mod_proc.Id(), args);
	}
}
class Mock_exec_lib extends Mock_proc_stub { 	private Scrib_lib lib;
	private String proc_name;
	private Keyval[] proc_args;
	public Mock_exec_lib(int fnc_id, String fnc_name, Scrib_lib lib, String proc_name, Object... proc_obj_args) {super(fnc_id, fnc_name);
		this.lib = lib;
		this.proc_name = proc_name;
		int len = proc_obj_args.length;
		this.proc_args = new Keyval[len];
		for (int i = 0; i < len; i++) {
			proc_args[i] = Keyval_.int_(i + 1, proc_obj_args[i]);
		}
	}
	@Override public Keyval[] Exec_by_scrib(Keyval[] args) {
		Scrib_proc proc = lib.Procs().Get_by_key(proc_name);
		Scrib_proc_rslt proc_rslt = new Scrib_proc_rslt();
		proc.Proc_exec(new Scrib_proc_args(proc_args), proc_rslt);
		return Extract_rslt(proc_rslt);
	}
	private Keyval[] Extract_rslt(Scrib_proc_rslt proc_rslt) {
		return new Keyval[] {Keyval_.int_(1, Object_.Xto_str_strict_or_null_mark(proc_rslt.Ary()[0].Val()))};
	}
}
