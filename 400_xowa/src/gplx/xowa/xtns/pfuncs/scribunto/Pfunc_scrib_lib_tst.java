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
package gplx.xowa.xtns.pfuncs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.lib.*;
public class Pfunc_scrib_lib_tst {
	@Before public void init() {
		fxt.Init_scrib_proc();
		lib = new Pfunc_scrib_lib();
		lib.Init();
		lib.Core_(fxt.Core());
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Pfunc_scrib_lib lib;
	@Test   public void Expr() {
		fxt.Test_scrib_proc_str(lib, Pfunc_scrib_lib.Invk_expr, Object_.Ary("1 + 2")						, "3");
	}
}	
