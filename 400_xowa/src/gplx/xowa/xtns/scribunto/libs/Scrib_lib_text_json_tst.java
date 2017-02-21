/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*; import gplx.langs.jsons.*;
public class Scrib_lib_text_json_tst {
	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib_text lib;
	private Scrib_lib_json_fxt json_fxt = new Scrib_lib_json_fxt();
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_text();
		lib.Init();
	}
	@Test  public void Nde__key_obj__primitives() {	// NOTE: based on MW
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'int':1"
		, ", 'String':'abc'"
		, ", 'true':true"
		, ", 'array':"
		, "  [ 1"
		, "  , 2"
		, "  , 3"
		, "  ]"
		, ", 'node':"
		, "  { 'key':'val'"
		, "  }"
		, "}"
		)
		, Keyval_.Ary
		( Keyval_.new_("int", 1)
		, Keyval_.new_("String", "abc")
		, Keyval_.new_("true", true)
		, Keyval_.new_("array", new int[] {1, 2, 3})
		, Keyval_.new_("node", Keyval_.Ary(Keyval_.new_("key", "val")))
		));
	}
	@Test  public void Nde__obj_in_obj() {
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'x':"
		, "  [ 1"
		, "  , 2"
		, "  , { 'y':'x'"
		, "    }"
		, "  ]"
		, "}"
		)
		, Keyval_.Ary
		( Keyval_.new_("x",	new Object[] 
		{	1, 2, Keyval_.Ary
		(		Keyval_.new_("y", "x")
		)
		}
		)
		)
		);
	}
	@Test  public void Nde__ary_in_obj() {	// NOTE: based on MW
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'x':"
		, "  [ 1"
		, "  , 2"
		, "  , { 'y':"
		, "      [ 3"
		, "      , 4"
		, "      ]"
		, "    }"
		, "  ]"
		, "}"
		)
		, Keyval_.Ary
		( Keyval_.new_("x"
		,	new Object[] {1, 2, Keyval_.Ary
		(		Keyval_.new_("y"
		,			new Object[] {3, 4}
		))}))			
		);
	}
	@Test  public void Nde__key_int__mixed() {// NOTE: based on MW
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'x':'x'"
		, ", '1':1"
		, ", '2':2"
		, "}"
		)
		, Keyval_.Ary
		( Keyval_.new_("x", "x")
		, Keyval_.new_("1", 1)
		, Keyval_.new_("2", 2)
		));
	}
	@Test  public void Nde__key_int__auto() {// NOTE: based on MW
		json_fxt.Test_json_encode(fxt, lib
		, Scrib_lib_text__json_util.Flag__preserve_keys
		, Kv_ary_utl.new_(Bool_.Y, new Object[]
		{ 1
		, "abc"
		, true
		, false
		})
		, Json_doc.Make_str_by_apos	// NOTE: numbering is done by Kv_ary_utl, not Reindex_arrays
		( "{ '1':1"
		, ", '2':'abc'"
		, ", '3':true"
		, ", '4':false"
		, "}"
		)
		);
		json_fxt.Test_json_decode(fxt, lib
		, Scrib_lib_text__json_util.Flag__preserve_keys
		, Json_doc.Make_str_by_apos
		( "{ '1':1"
		, ", '2':'abc'"
		, ", '3':true"
		, ", '4':false"
		, "}"
		)
		, Kv_ary_utl.new_(Bool_.Y, new Object[]
		{ 1
		, "abc"
		, true
		, false
		}
		));
	}
//		@Test  public void Nde__empty() {	// NOTE: based on MW
//			json_fxt.Test_json_roundtrip(fxt, lib
//			, Json_doc.Make_str_by_apos
//			( "{"
//			, "}"
//			)
//			, Keyval_.Ary_empty
//			);
//		}
	@Test  public void Ary__empty() {	// NOTE: based on MW
		json_fxt.Test_json_encode(fxt, lib, Scrib_lib_text__json_util.Flag__none
		, Kv_ary_utl.new_(Bool_.Y, new Object[] {})
		, Json_doc.Make_str_by_apos
		( "["
		, "]"
		)
		);
	}
	@Test  public void Ary__obj() {	// NOTE: based on MW
		json_fxt.Test_json_encode(fxt, lib, Scrib_lib_text__json_util.Flag__none
		, Kv_ary_utl.new_(Bool_.Y, 1, "abc", true)
		, Json_doc.Make_str_by_apos
		( "[ 1"
		, ", 'abc'"
		, ", true"
		, "]"
		)
		);
	}
	@Test  public void Ary__nested() {	// NOTE: based on MW
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "[ 1"
		, ", 2"
		, ", 3"
		, ", "
		, "  [ 4"
		, "  , 5"
		, "  , "
		, "    [ 6"
		, "    , 7"
		, "    , 8"
		, "    ]"
		, "  , 9"
		, "  ]"
		, "]"
		)
		, Kv_ary_utl.new_(Bool_.Y, new Object[] {1, 2, 3, new Object[] {4, 5, new Object[] {6, 7, 8}, 9}})
		);
	}
	@Test   public void Nde__smoke() {
		json_fxt.Test_json_encode(fxt, lib
		, Scrib_lib_text__json_util.Flag__none
		, Keyval_.Ary
		( Keyval_.new_("axes", Keyval_.Ary
		(	Keyval_.int_(1, Keyval_.Ary
		(		Keyval_.new_("type", "x")
		))
		,	Keyval_.int_(2, Keyval_.Ary
		(		Keyval_.new_("type", "y")
		))
		))
		)
		, Json_doc.Make_str_by_apos
		( "{ 'axes':"
		, "  ["
		, "    { 'type':'x'"
		, "    }"
		, "  , { 'type':'y'"
		, "    }"
		, "  ]"
		, "}"
		)
		);
	}
	@Test   public void Decode__key__int() {
		Keyval[] kv_ary = (Keyval[])json_fxt.Test_json_decode(fxt, lib
		, Scrib_lib_text__json_util.Flag__none
		, Json_doc.Make_str_by_apos
		( "{ '1':"
		, "  { '11':'aa'"
		, "  }"
		, ", '2':'b'"
		, "}"
		)
		, Keyval_.Ary
		( Keyval_.int_(1, Keyval_.Ary
		(		Keyval_.int_(11, "aa")
		))
		, Keyval_.int_(2, "b")
		)
		);
		Tfds.Eq(kv_ary[0].Key_as_obj(), 1);
		Tfds.Eq(((Keyval[])kv_ary[0].Val())[0].Key_as_obj(), 11);
	}
}
class Scrib_lib_json_fxt {
	private final    Json_wtr wtr = new Json_wtr();
	public void Test_json_roundtrip(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, String json, Object obj) {
		Test_json_decode(fxt, lib, Scrib_lib_text__json_util.Flag__none, json, obj);
		Test_json_encode(fxt, lib, Scrib_lib_text__json_util.Flag__none, obj, json);
	}
	public Object Test_json_decode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, int flag, String raw, Object expd) {
		Object actl = fxt.Test_scrib_proc_rv_as_obj(lib, Scrib_lib_text.Invk_jsonDecode, Object_.Ary(raw, flag));
		Tfds.Eq_str_lines(To_str(expd), To_str(actl), raw);
		return actl;
	}
	public void Test_json_encode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, int flag, Object raw, String expd) {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_text.Invk_jsonEncode, Object_.Ary(raw, flag), "1=" + String_.Replace(expd, "'", "\""));
	}
	private String To_str(Object o) {
		if	(o == null) return "<< NULL >>";
		Class<?> type = o.getClass();
		if		(Type_adp_.Eq(type, Keyval[].class))		return Kv_ary_utl.Ary_to_str(wtr, (Keyval[])o);
		else if	(Type_adp_.Is_array(type))					return Array_.To_str_nested_obj(o);
		else												return Object_.Xto_str_strict_or_null(o);
	}
}
