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
import org.junit.*;
public class Scrib_lib_text_tst {
	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib_text lib;
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_text();
		lib.Init();
		lib.Init_for_tests();
	}
	@Test  public void Unstrip() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_text.Invk_unstrip, Object_.Ary("a"), "a");
	}
	@Test  public void GetEntityTable() {
		KeyVal[] actl = fxt.Test_scrib_proc_rv_as_kv_ary(lib, Scrib_lib_text.Invk_getEntityTable, Object_.Ary());
		Tfds.Eq(1510, actl.length);	// large result; only test # of entries
	}
//		@Test  public void JsonEncode() {
//			fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_text.Invk_jsonEncode, Object_.Ary((Object)Kv_ary_utl.new_
//			(	true
//			,	true
//			,	1
//			,	"a"
//			,	new int[] {1, 2, 3}
//			,	Kv_ary_utl.new_(true, "b")
//			)), String_.Concat_lines_nl_skip_last
//			(	"1="
//			+	"{ '1':true"
//			,	", '2':1"
//			,	", '3':'a'"
//			,	", '4':"
//			,	"  [ 1"
//			,	"  , 2"
//			,	"  , 3"
//			,	"  ]"
//			,	", '5':"
//			,	"  { '1':'b'"
//			,	"  }"
//			,	"}"
//			));
//		}
}
class Kv_ary_utl {
	public static KeyVal[] new_(boolean base_1, Object... vals) {
		int len = vals.length;
		KeyVal[] rv = new KeyVal[len];
		for (int i = 0; i < len; ++i)
			rv[i] = KeyVal_.int_(i + (base_1 ? 1 : 0), vals[i]);
		return rv;
	}
}
//	class Scrib_lib_text_fxt {
//		public void Test_json_decode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, KeyVal[] kv_ary, String expd) {
//			fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_text.Invk_jsonEncode, Object_.Ary((Object)Scrib_lib_language_tst.Kv_ary_("a")), String_.Concat_lines_nl_skip_last
//			( "1={ '0':'a'"
//			, "}"
//			));
//		}
//	}
