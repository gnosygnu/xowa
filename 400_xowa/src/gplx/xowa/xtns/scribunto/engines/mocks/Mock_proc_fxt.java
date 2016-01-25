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
package gplx.xowa.xtns.scribunto.engines.mocks; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import gplx.core.primitives.*;
public abstract class Mock_proc_fxt {
	public Mock_proc_fxt(int id, String key) {this.id = id; this.key = key;}
	public int Id() {return id;} private final int id;
	public String Key() {return key;} private final String key;
	public Scrib_lua_proc To_scrib_lua_proc() {return new Scrib_lua_proc(key, id);}
	public abstract KeyVal[] Exec_by_scrib(KeyVal[] args);
}
class Mock_engine implements Scrib_engine {
	private final Hash_adp hash = Hash_adp_.new_();
	private final Int_obj_ref tmp_hash_id = Int_obj_ref.neg1_();
	public boolean				Dbg_print() {return false;}	public void Dbg_print_(boolean v) {}
	public Scrib_server		Server() {return server;} public void Server_(Scrib_server v) {} private final Mock_server server = new Mock_server();
	public Scrib_lua_proc	LoadString(String name, String text) {return null;}
	public KeyVal[]			CallFunction(int id, KeyVal[] args) {
		Mock_proc_fxt proc = (Mock_proc_fxt)hash.Get_by_or_fail(tmp_hash_id.Val_(id));
		return proc.Exec_by_scrib(args);
	}
	public void				RegisterLibrary(KeyVal[] functions_ary) {}
	public KeyVal[]			ExecuteModule(int mod_id) {return null;}
	public void				CleanupChunks(KeyVal[] ids) {}
	public void				Clear() {}
	public void				RegisterLibraryForTest(Mock_proc_fxt proc) {
		hash.Add(Int_obj_ref.new_(proc.Id()), proc);
	}
}
