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
package gplx.xowa.xtns.scribunto.lib; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
public class Scrib_lib_text_tst {
	@Before public void init() {
		fxt.Clear();
		fxt.Init_page("{{#invoke:Mod_0|Func_0}}");
		lib = fxt.Core().Lib_text().Init();
	}	Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); Scrib_lib lib;
	@Test  public void Unstrip() {
		fxt.Test_lib_proc(lib, Scrib_lib_text.Invk_unstrip, Object_.Ary("a"), "a");
	}
	@Test  public void GetEntityTable() {
		byte[] rv = fxt.Test_lib_proc_rv(lib, Scrib_lib_text.Invk_getEntityTable, Object_.Ary());
		rv = Bry_.Replace(rv, new byte[] {Byte_ascii.Semic, Byte_ascii.Semic}, new byte[] {Byte_ascii.Semic});
		Tfds.Eq(1510, Bry_.Split(rv, Byte_ascii.Semic).length);
	}
}	
