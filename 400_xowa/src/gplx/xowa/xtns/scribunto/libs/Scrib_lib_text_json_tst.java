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
package gplx.xowa.xtns.scribunto.libs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.KeyValUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_wtr;
import gplx.types.basics.utls.ArrayUtl;
import gplx.xowa.xtns.scribunto.Scrib_invoke_func_fxt;
import org.junit.Before;
import org.junit.Test;
public class Scrib_lib_text_json_tst {
	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib_text lib;
	private Scrib_lib_json_fxt json_fxt = new Scrib_lib_json_fxt();
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_text();
		lib.Init();
	}
	@Test public void Nde__key_obj__primitives() {	// NOTE: based on MW
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
		, KeyValUtl.Ary
		( KeyVal.NewStr("int", 1)
		, KeyVal.NewStr("String", "abc")
		, KeyVal.NewStr("true", true)
		, KeyVal.NewStr("array", new int[] {1, 2, 3})
		, KeyVal.NewStr("node", KeyValUtl.Ary(KeyVal.NewStr("key", "val")))
		));
	}
	@Test public void Nde__obj_in_obj() {
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
		, KeyValUtl.Ary
		( KeyVal.NewStr("x",	new Object[]
		{	1, 2, KeyValUtl.Ary
		(		KeyVal.NewStr("y", "x")
		)
		}
		)
		)
		);
	}
	@Test public void Nde__ary_in_obj() {	// NOTE: based on MW
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
		, KeyValUtl.Ary
		( KeyVal.NewStr("x"
		,	new Object[] {1, 2, KeyValUtl.Ary
		(		KeyVal.NewStr("y"
		,			new Object[] {3, 4}
		))}))			
		);
	}
	@Test public void Nde__key_int__mixed() {// NOTE: based on MW
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'x':'x'"
		, ", '1':1"
		, ", '2':2"
		, "}"
		)
		, KeyValUtl.Ary
		( KeyVal.NewStr("x", "x")
		, KeyVal.NewStr("1", 1)
		, KeyVal.NewStr("2", 2)
		));
	}
	@Test public void Nde__key_int__auto() {// NOTE: based on MW
		json_fxt.Test_json_encode(fxt, lib
		, Scrib_lib_text__json_util.Flag__preserve_keys
		, Kv_ary_utl.new_(BoolUtl.Y, new Object[]
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
		, Kv_ary_utl.new_(BoolUtl.Y, new Object[]
		{ 1
		, "abc"
		, true
		, false
		}
		));
	}
//		@Test public void Nde__empty() {	// NOTE: based on MW
//			json_fxt.Test_json_roundtrip(fxt, lib
//			, Json_doc.Make_str_by_apos
//			( "{"
//			, "}"
//			)
//			, Keyval_.Ary_empty
//			);
//		}
	@Test public void Ary__empty() {	// NOTE: based on MW
		json_fxt.Test_json_encode(fxt, lib, Scrib_lib_text__json_util.Flag__none
		, Kv_ary_utl.new_(BoolUtl.Y, new Object[] {})
		, Json_doc.Make_str_by_apos
		( "["
		, "]"
		)
		);
	}
	@Test public void Ary__obj() {	// NOTE: based on MW
		json_fxt.Test_json_encode(fxt, lib, Scrib_lib_text__json_util.Flag__none
		, Kv_ary_utl.new_(BoolUtl.Y, 1, "abc", true)
		, Json_doc.Make_str_by_apos
		( "[ 1"
		, ", 'abc'"
		, ", true"
		, "]"
		)
		);
	}
	@Test public void Ary__nested() {	// NOTE: based on MW
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
		, Kv_ary_utl.new_(BoolUtl.Y, new Object[] {1, 2, 3, new Object[] {4, 5, new Object[] {6, 7, 8}, 9}})
		);
	}
	@Test public void Nested__ary__nde() {
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'axes':"
		, "  ["
		, "    { 'type':'x'"
		, "    , 'name':'X'"
		, "    }"
		, "  , { 'type':'y'"
		, "    , 'name':'Y'"
		, "    }"
		, "  ]"
		, "}"
		)
		, KeyValUtl.Ary
		( KeyVal.NewStr("axes", KeyValUtl.Ary
		(	KeyVal.NewInt(1, KeyValUtl.Ary
		(		KeyVal.NewStr("type", "x")
		,       KeyVal.NewStr("name", "X")
		))
		,	KeyVal.NewInt(2, KeyValUtl.Ary
		(		KeyVal.NewStr("type", "y")
		,       KeyVal.NewStr("name", "Y")
		))
		))
		)
		);
	}
	@Test public void Nested__ary__ary() {
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'axes':"
		, "  [ "
		, "    [ 'a1'"
		, "    , 'a2'"
		, "    ]"
		, "  , "
		, "    [ 'b1'"
		, "    , 'b2'"
		, "    ]"
		, "  ]"
		, "}"
		)
		, KeyValUtl.Ary
		( KeyVal.NewStr("axes", KeyValUtl.Ary
		(	KeyVal.NewInt(1, new Object[]
		{		"a1"
		,       "a2"
		})
		,	KeyVal.NewInt(2, new Object[]
		{		"b1"
		,       "b2"
		})
		))
		)
		);
	}
	@Test public void Nested__ary__ary_nde() {
		json_fxt.Test_json_roundtrip(fxt, lib
		, Json_doc.Make_str_by_apos
		( "{ 'axes':"
		, "  [ "
		, "    ["
		, "      { 'type':'x1'"
		, "      , 'name':'X1'"
		, "      }"
		, "    , { 'type':'y1'"
		, "      , 'name':'Y1'"
		, "      }"
		, "    ]"
		, "  ]"
		, "}"
		)
		, KeyValUtl.Ary
		( KeyVal.NewStr("axes", KeyValUtl.Ary
		(	  KeyVal.NewInt(1, KeyValUtl.Ary
		(		  KeyVal.NewInt(1, KeyValUtl.Ary
		(			  KeyVal.NewStr("type", "x1")
		,			  KeyVal.NewStr("name", "X1")
		))
		, 		KeyVal.NewInt(2, KeyValUtl.Ary
		(			KeyVal.NewStr("type", "y1")
		,			KeyVal.NewStr("name", "Y1")
		))
		))
		)))
		);
	}
	@Test public void Decode__key__int() {
		KeyVal[] kv_ary = (KeyVal[])json_fxt.Test_json_decode(fxt, lib
		, Scrib_lib_text__json_util.Flag__none
		, Json_doc.Make_str_by_apos
		( "{ '1':"
		, "  { '11':'aa'"
		, "  }"
		, ", '2':'b'"
		, "}"
		)
		, KeyValUtl.Ary
		( KeyVal.NewInt(1, KeyValUtl.Ary
		(		KeyVal.NewInt(11, "aa")
		))
		, KeyVal.NewInt(2, "b")
		)
		);
		GfoTstr.EqObj(kv_ary[0].KeyAsObj(), 1);
		GfoTstr.EqObj(((KeyVal[])kv_ary[0].Val())[0].KeyAsObj(), 11);
	}
	@Test public void Primitives() {	// NOTE: based on MW; ISSUE#:329; DATE:2019-01-13
		json_fxt.Test_json_roundtrip_primitive(fxt, lib, "abc", "abc");
		json_fxt.Test_json_roundtrip_primitive(fxt, lib, true, "true");
		json_fxt.Test_json_roundtrip_primitive(fxt, lib, false, "false");
		json_fxt.Test_json_roundtrip_primitive(fxt, lib, 123, "123");
	}
}
class Scrib_lib_json_fxt {
	private final Json_wtr wtr = new Json_wtr();
	public void Test_json_roundtrip(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, String json, Object obj) {
		Test_json_decode(fxt, lib, Scrib_lib_text__json_util.Flag__none, json, obj);
		Test_json_encode(fxt, lib, Scrib_lib_text__json_util.Flag__none, obj, json);
	}
	public void Test_json_roundtrip_primitive(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, Object obj, String expd_encoded) {
		Object actl_encoded = fxt.Test_scrib_proc_rv_as_obj(lib, Scrib_lib_text.Invk_jsonEncode, ObjectUtl.Ary(obj, Scrib_lib_text__json_util.Flag__none));
		GfoTstr.Eq(ObjectUtl.ToStrLooseOr(actl_encoded, "failed"), expd_encoded);
		Object actl_decoded = fxt.Test_scrib_proc_rv_as_obj(lib, Scrib_lib_text.Invk_jsonDecode, ObjectUtl.Ary(expd_encoded, Scrib_lib_text__json_util.Flag__none));
		GfoTstr.Eq(ObjectUtl.ToStrLooseOr(obj, "failed"), ObjectUtl.ToStrLooseOr(actl_decoded, "failed"));
	}
	public Object Test_json_decode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, int flag, String raw, Object expd) {
		Object actl = fxt.Test_scrib_proc_rv_as_obj(lib, Scrib_lib_text.Invk_jsonDecode, ObjectUtl.Ary(raw, flag));
		GfoTstr.EqLines(To_str(expd), To_str(actl), raw);
		return actl;
	}
	public void Test_json_encode(Scrib_invoke_func_fxt fxt, Scrib_lib_text lib, int flag, Object raw, String expd) {
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_text.Invk_jsonEncode, ObjectUtl.Ary(raw, flag), "1=" + StringUtl.Replace(expd, "'", "\""));
	}
	private String To_str(Object o) {
		if	(o == null) return "<< NULL >>";
		Class<?> type = o.getClass();
		if		(ClassUtl.Eq(type, KeyVal[].class))
			return Kv_ary_utl.Ary_to_str(wtr, (KeyVal[])o);
		else if	(ClassUtl.IsArray(type))
			return ToStrNestedObj(o);
		else
			return ObjectUtl.ToStrOrNull(o);
	}
	private static String ToStrNestedObj(Object o) {
		BryWtr bfr = BryWtr.New();
		ToStrNestedAry(bfr, (Object)o, 0);
		return bfr.ToStrAndClear();
	}
	private static void ToStrNestedAry(BryWtr bfr, Object ary, int indent) {
		int len = ArrayUtl.Len(ary);
		for (int i = 0; i < len; i++) {
			Object itm = ArrayUtl.GetAt(ary, i);
			if (itm != null && ClassUtl.IsArray(itm.getClass()))
				ToStrNestedAry(bfr, (Object)itm, indent + 1);
			else {
				if (indent > 0) bfr.AddByteRepeat(AsciiByte.Space, indent * 2);
				bfr.AddStrU8(ObjectUtl.ToStrOrNullMark(itm)).AddByteNl();
			}
		}
	}
}
