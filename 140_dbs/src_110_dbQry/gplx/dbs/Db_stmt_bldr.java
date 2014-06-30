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
package gplx.dbs; import gplx.*;
public class Db_stmt_bldr implements RlsAble {
	private Db_provider provider;
	private Db_stmt create, update, delete;
	private String tbl_name; private String[] flds_keys, flds_vals, flds_all;
	public Db_stmt_bldr(String tbl_name, String[] flds_keys, String... flds_vals) {
		this.tbl_name = tbl_name; this.flds_keys = flds_keys; this.flds_vals = flds_vals;
		flds_all = String_.Ary_add(flds_keys, flds_vals);
	}
	public Db_stmt_bldr Init(Db_provider v) {
		this.provider = v;
		provider.Txn_mgr().Txn_bgn_if_none();
		return this;
	}
	public Db_stmt Get(byte cmd_mode) {
		switch (cmd_mode) {
			case Db_cmd_mode.Create:	if (create == null) create = Db_stmt_.new_insert_(provider, tbl_name, flds_all);				return create;
			case Db_cmd_mode.Update:	if (update == null) update = Db_stmt_.new_update_(provider, tbl_name, flds_keys, flds_vals);	return update;
			case Db_cmd_mode.Delete:	if (delete == null) delete = Db_stmt_.new_delete_(provider, tbl_name, flds_keys);				return delete;
			case Db_cmd_mode.Ignore:	return Db_stmt_.Null;
			default:					throw Err_.unhandled(cmd_mode);
		}
	}
	public void Commit() {
		provider.Txn_mgr().Txn_end_all();
	}
	public void Rls() {
		provider = null;
		create = Rls(create);
		update = Rls(update);
		delete = Rls(delete);
	}
	private Db_stmt Rls(Db_stmt stmt) {
		if (stmt != null) stmt.Rls();
		return null;
	}
}
