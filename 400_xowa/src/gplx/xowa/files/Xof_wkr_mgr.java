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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.xowa.files.fsdb.*;
import gplx.xowa.files.fsdb.fs_roots.*;
class Xof_wkr_mgr implements Gfo_invk {
	private Xow_file_mgr file_mgr;
	public Xof_wkr_mgr(Xow_file_mgr file_mgr) {this.file_mgr = file_mgr;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_new(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_get = "get";
	private Xof_fsdb_mgr Get_or_new(String key) {
		if (String_.Eq(key, "fs.dir")) {
			return Fs_root_core.Set_fsdb_mgr(file_mgr, file_mgr.Wiki());
		}
		else
			throw Err_.new_unhandled(key);
	}
}
