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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.langs.jsons.*;
public class Scrib_lib_text_tst {
	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib_text lib;
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_text();
		lib.Init();
	}
	@Test  public void Unstrip() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_text.Invk_unstrip, Object_.Ary("a"), "a");
	}
	@Test  public void GetEntityTable() {
		KeyVal[] actl = fxt.Test_scrib_proc_rv_as_kv_ary(lib, Scrib_lib_text.Invk_getEntityTable, Object_.Ary());
		Tfds.Eq(1510, actl.length);	// large result; only test # of entries
	}
}
