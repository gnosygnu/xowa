/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.jsons;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDecimal;
import gplx.types.commons.GfoDecimalUtl;
import org.junit.Test;

public class Json_parser_tst {
	private final Json_parser_fxt fxt = new Json_parser_fxt();
	@Test public void Null()                   {fxt.Test_parse_obj("{'k0':null}"              , null);}
	@Test public void Bool_n()                 {fxt.Test_parse_obj("{'k0':false}"             , false);}
	@Test public void Bool_y()                 {fxt.Test_parse_obj("{'k0':true}"              , true);}
	@Test public void Num()                    {fxt.Test_parse_obj("{'k0':123}"               , 123);}
	@Test public void Num_neg()                {fxt.Test_parse_obj("{'k0':-123}"              , -123);}
	@Test public void Str()                    {fxt.Test_parse_obj("{'k0':'v0'}"              , "v0");}
	@Test public void Str_esc_quote()          {fxt.Test_parse_obj("{'k0':'a\\\"b'}"          , "a\"b");}
	@Test public void Str_encoded_basic()      {fxt.Test_parse_obj("{'k0':'a\\u0021b'}"       , "a!b");}
	@Test public void Str_encoded_surrogate()  {fxt.Test_parse_obj("{'k0':'a\\ud83c\\udf0eb'}", "a🌎b");} // check for UTF surrogate-pairs; symbol is earth globe americas (U+1F30E); ISSUE#:487; DATE:2019-06-02
	@Test public void Num_dec()                {fxt.Test_parse_dec("{'k0':1.23}"              , GfoDecimalUtl.Parse("1.23"));}
	@Test public void Num_exp()                {fxt.Test_parse_obj("{'k0':1e+2}"              , 100);}
	@Test public void Num_mix()                {fxt.Test_parse_dec("{'k0':-1.23e-1}"          , GfoDecimalUtl.Parse("-1.23e-1"));}
	@Test public void Str_many()               {fxt.Test_parse("{'k0':'v0','k1':'v1','k2':'v2'}"     , fxt.Init_nde().Add_many(fxt.Init_kv("k0", "v0"), fxt.Init_kv("k1", "v1"), fxt.Init_kv("k2", "v2")));}
	@Test public void Ary_empty()              {fxt.Test_parse("{'k0':[]}"                           , fxt.Init_nde().Add_many(fxt.Init_kv_ary_int("k0")));}
	@Test public void Ary_int()                {fxt.Test_parse("{'k0':[1,2,3]}"                      , fxt.Init_nde().Add_many(fxt.Init_kv_ary_int("k0", 1, 2, 3)));}
	@Test public void Ary_str()                {fxt.Test_parse("{'k0':['a','b','c']}"                , fxt.Init_nde().Add_many(fxt.Init_kvary_str_("k0", "a", "b", "c")));}
	@Test public void Ary_ws()                 {fxt.Test_parse("{'k0': [ 1 , 2 , 3 ] }"              , fxt.Init_nde().Add_many(fxt.Init_kv_ary_int("k0", 1, 2, 3)));}
	@Test public void Subs_int()               {fxt.Test_parse("{'k0':{'k00':1}}"                    , fxt.Init_nde().Add_many(fxt.Init_kv("k0", fxt.Init_nde().Add_many(fxt.Init_kv("k00", 1)))));}
	@Test public void Subs_empty()             {fxt.Test_parse("{'k0':{}}"                           , fxt.Init_nde().Add_many(fxt.Init_kv("k0", fxt.Init_nde())));}
	@Test public void Subs_ws()                {fxt.Test_parse("{'k0': { 'k00' : 1 } }"              , fxt.Init_nde().Add_many(fxt.Init_kv("k0", fxt.Init_nde().Add_many(fxt.Init_kv("k00", 1)))));}
	@Test public void Ws()                     {fxt.Test_parse(" { 'k0' : 'v0' } "                   , fxt.Init_nde().Add_many(fxt.Init_kv("k0", "v0")));}
	@Test public void Root_is_ary()            {fxt.Test_parse("[1,2,3]"                             , fxt.Init_ary().Add_many(fxt.Init_int(1), fxt.Init_int(2), fxt.Init_int(3)));}
}
class Json_parser_fxt {
	private final Json_parser parser = new Json_parser();
	private final BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public Json_itm Init_int(int v)                     {return Json_itm_int.NewByVal(v);}
	public Json_itm Init_str(String v)                  {return Json_itm_str.NewByVal(v);}
	public Json_ary Init_ary()                          {return Json_ary.NewByVal();}
	public Json_nde Init_nde()                          {return Json_nde.NewByVal();}
	public Json_kv Init_kv_null(String k)               {return new Json_kv(Init_str(k), Json_itm_null.Null);}
	public Json_kv Init_kv(String k, String v)          {return new Json_kv(Init_str(k), Init_str(v));}
	public Json_kv Init_kv(String k, int v)             {return new Json_kv(Init_str(k), Init_int(v));}
	public Json_kv Init_kv(String k, boolean v)         {return new Json_kv(Init_str(k), v ? Json_itm_bool.Bool_y : Json_itm_bool.Bool_n);}
	public Json_kv Init_kv(String k, Json_nde v)        {return new Json_kv(Init_str(k), v);}
	public Json_kv Init_kv_ary_int(String k, int... v) {
		Json_ary ary = Json_ary.NewByVal();
		int len = v.length;
		for (int i = 0; i < len; i++)
			ary.Add(Init_int(v[i]));
		return new Json_kv(Init_str(k), ary);
	}
	public Json_kv Init_kvary_str_(String k, String... v) {
		Json_ary ary = Json_ary.NewByVal();
		int len = v.length;
		for (int i = 0; i < len; i++)
			ary.Add(Init_str(v[i]));
		return new Json_kv(Init_str(k), ary);
	}
	public void Test_parse(String raw_str, Json_itm... expd_ary) {
		byte[] raw = BryUtl.NewU8(Json_doc.Make_str_by_apos(raw_str));
		Json_doc doc = parser.Parse(raw);
		doc.Root_grp().Print_as_json(tmp_bfr, 0);
		String actl = tmp_bfr.ToStrAndClear();
		String expd = Xto_str(raw, doc, expd_ary, 0, expd_ary.length);
		GfoTstr.EqLines(expd, actl, actl);
	}
	public void Test_parse_obj(String raw_str, Object expd) {
		Json_kv kv = Parse_and_get_kv0(raw_str);
		Object actl = kv.Val().Data();	 // NOTE: Data_bry is escaped val; EX: a\"b has DataBry of a"b
		GfoTstr.EqObjToStr(expd, actl);
	}
	public void Test_parse_dec(String raw_str, GfoDecimal expd) {
		Json_kv kv = Parse_and_get_kv0(raw_str);
		Json_itm_decimal decimal_itm = (Json_itm_decimal)kv.Val();
		GfoTstr.Eq(true, decimal_itm.Data_as_decimal().Eq(expd));
	}
	private Json_kv Parse_and_get_kv0(String raw_str) {
		byte[] raw = BryUtl.NewU8(Json_doc.Make_str_by_apos(raw_str));
		Json_doc doc = parser.Parse(raw);
		return Json_kv.Cast(doc.Root_nde().Get_at(0));	// assume root has kv as first sub; EX: {"a":"b"}
	}
	private String Xto_str(byte[] raw, Json_doc doc, Json_itm[] ary, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			Json_itm itm = ary[i];
			itm.Print_as_json(tmp_bfr, 0);
		}
		return tmp_bfr.ToStrAndClear();
	}
}
