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
package gplx.xowa.xtns.titleBlacklists; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.lib.*;
public class Blacklist_scrib_lib_tst {
	@Before public void init() {
		fxt.Init_scrib_proc();
		lib = new Blacklist_scrib_lib().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test   public void Exec_test() {
		fxt.Test_scrib_proc_str(lib, Blacklist_scrib_lib.Invk_test, Object_.Ary("title")						, String_.Null_mark);
	}
}	
