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
import org.junit.*; import gplx.core.json.*;
public class Scrib_lib_text_tst {
	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib_text lib;
	private Scrib_lib_json_fxt json_fxt = new Scrib_lib_json_fxt();
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
	@Test  public void JsonEncode() {
		json_fxt.Test_json_encode(fxt, lib, Kv_ary_utl.new_
		(	true
		,	true
		,	1
		,	"a"
		,	new int[] {1, 2, 3}
		,	Kv_ary_utl.new_(true, "b")
		),	String_.Concat_lines_nl_skip_last
		(	"1="
		+	"{ '1':true"
		,	", '2':1"
		,	", '3':'a'"
		,	", '4':"
		,	"  [ 1"
		,	"  , 2"
		,	"  , 3"
		,	"  ]"
		,	", '5':"
		,	"  { '1':'b'"
		,	"  }"
		,	"}"
		));
	}
	@Test  public void JsonDecode() {
		json_fxt.Test_json_decode(fxt, lib
		,	String_.Concat_lines_nl_skip_last
		(	"{ '1':true"
		,	", '2':1"
		,	", '3':'a'"
		,	", '4':"
		,	"  [ 1"
		,	"  , 2"
		,	"  , 3"
		,	"  ]"
		,	", '5':"
		,	"  { '1':'b'"
		,	"  }"
		,	"}"
		)
		,	Kv_ary_utl.new_
		(	true
		,	true
		,	1
		,	"a"
		,	new int[] {1, 2, 3}
		,	Kv_ary_utl.new_(true, "b")
		));
	}
	@Test  public void JsonDecode__primitives() {
		json_fxt.Test_json_decode(fxt, lib
		, String_.Concat_lines_nl_skip_last
		(	"{ 'int':1"
		,	", 'String':'abc'"
		,	", 'true':true"
		,	"}"
		)
		, Kv_ary_utl.new_kvs
		(	KeyVal_.new_("int", 1)
		,	KeyVal_.new_("String", "abc")
		,	KeyVal_.new_("true", true)
		));
	}
	@Test  public void JsonDecode__numeric_keys() {
		json_fxt.Test_json_decode(fxt, lib
		, String_.Concat_lines_nl_skip_last
		(	"{ 'x':'x'"
		,	", '1':1"
		,	", '2':2"
		,	"}"
		)
		, Kv_ary_utl.new_kvs
		(	KeyVal_.new_("x", "x")
		,	KeyVal_.new_("1", 1)
		,	KeyVal_.new_("2", 2)
		));
	}
//		@Test  public void JsonDecode__array() {
//			json_fxt.Test_json_decode(fxt, lib
//			, String_.Concat_lines_nl_skip_last
//			(	"[ 1"
//			,	", 2"
//			,	", 3"
//			,	"]"
//			)
//			, Kv_ary_utl.new_kvs
//			(	KeyVal_.new_("int", 1)
//			,	KeyVal_.new_("String", "abc")
//			,	KeyVal_.new_("true", true)
//			));
//		}
}
class Scrib_lib_json_fxt {
	private final Json_wtr wtr = new Json_wtr().Opt_quote_byte_(Byte_ascii.Apos);
	public void Test_json_decode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, String raw, KeyVal[] expd) {
		raw = String_.Replace(raw, "'", "\"");
		KeyVal[] actl = fxt.Test_scrib_proc_rv_as_kv_ary(lib, Scrib_lib_text.Invk_jsonDecode, Object_.Ary(raw));
		Tfds.Eq_str_lines(Kv_ary_utl.Ary_to_str(wtr, expd), Kv_ary_utl.Ary_to_str(wtr, actl), raw);
	}
	public void Test_json_encode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, KeyVal[] raw, String expd) {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_text.Invk_jsonEncode, Object_.Ary((Object)raw), expd);
	}
}
