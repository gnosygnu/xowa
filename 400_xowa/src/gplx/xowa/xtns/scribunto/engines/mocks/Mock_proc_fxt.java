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
public abstract class Mock_proc_fxt {
	public Mock_proc_fxt(int id, String key) {this.id = id; this.key = key;}
	public int Id() {return id;} private final    int id;
	public String Key() {return key;} private final    String key;
	public Scrib_lua_proc To_scrib_lua_proc() {return new Scrib_lua_proc(key, id);}
	public abstract Keyval[] Exec_by_scrib(Keyval[] args);
}
class Mock_engine implements Scrib_engine {
	private final    Hash_adp hash = Hash_adp_.New();
	private final    Int_obj_ref tmp_hash_id = Int_obj_ref.New_neg1();
	public boolean				Dbg_print() {return false;}	public void Dbg_print_(boolean v) {}
	public Scrib_server		Server() {return server;} public void Server_(Scrib_server v) {} private final    Mock_server server = new Mock_server();
	public Scrib_lua_proc	LoadString(String name, String text) {return null;}
	public Keyval[]			CallFunction(int id, Keyval[] args) {
		Mock_proc_fxt proc = (Mock_proc_fxt)hash.Get_by_or_fail(tmp_hash_id.Val_(id));
		return proc.Exec_by_scrib(args);
	}
	public void				RegisterLibrary(Keyval[] functions_ary) {}
	public Keyval[]			ExecuteModule(int mod_id) {return null;}
	public void				CleanupChunks(Keyval[] ids) {}
	public void				Clear() {hash.Clear();}
	public void				RegisterLibraryForTest(Mock_proc_fxt proc) {
		hash.Add(Int_obj_ref.New(proc.Id()), proc);
	}
}
