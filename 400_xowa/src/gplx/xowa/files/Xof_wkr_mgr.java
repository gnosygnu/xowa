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
class Xof_wkr_mgr implements GfoInvkAble {
	private Xow_file_mgr file_mgr;
	public Xof_wkr_mgr(Xow_file_mgr file_mgr) {this.file_mgr = file_mgr;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_new(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_get = "get";
	private Xof_fsdb_mgr Get_or_new(String key) {
		Xof_fsdb_mgr rv = null;
		if (String_.Eq(key, "fs.dir"))
			rv = new gplx.xowa.files.fsdb.fs_roots.Fs_root_fsdb_mgr(file_mgr.Wiki());
		else
			throw Exc_.new_unhandled(key);
		file_mgr.Fsdb_mgr_(rv);
		return rv;
	}
}
