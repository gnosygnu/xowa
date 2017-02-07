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
import gplx.xowa.xtns.scribunto.procs.*;
public interface Scrib_lib {
	Scrib_proc_mgr	Procs();
	Scrib_lib		Init();
	Scrib_lua_mod	Register(Scrib_core core, Io_url script_dir);
	boolean			Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt);
	Scrib_lib		Clone_lib(Scrib_core core);
}
