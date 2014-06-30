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
public class Scrib_lib_mgr {
	private ListAdp list = ListAdp_.new_();
	public int Len() {return list.Count();}
	public void Add(Scrib_lib v) {list.Add(v); v.Init();}
	public Scrib_lib Get_at(int i) {return (Scrib_lib)list.FetchAt(i);}
	public void Init_for_core(Scrib_core core, Io_url script_dir) {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Scrib_lib lib = Get_at(i);
			lib.Register(core, script_dir);
		}
	}
}
