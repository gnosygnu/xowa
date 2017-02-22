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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Json_kv_ary_srl_tst {
	@Before public void init() {fxt.Clear();} private Json_kv_ary_srl_fxt fxt = new Json_kv_ary_srl_fxt();
	@Test   public void Null()					{fxt.Test_parse("{'k0':null}"					, fxt.ary_(fxt.kv_str_("k0", null)));}
	@Test   public void Bool_n()				{fxt.Test_parse("{'k0':false}"					, fxt.ary_(fxt.kv_bool_("k0", false)));}
	@Test   public void Num()					{fxt.Test_parse("{'k0':123}"					, fxt.ary_(fxt.kv_int_("k0", 123)));}
	@Test   public void Str()					{fxt.Test_parse("{'k0':'v0'}"					, fxt.ary_(fxt.kv_str_("k0", "v0")));}
	@Test   public void Num_dec()				{fxt.Test_parse("{'k0':1.23}"					, fxt.ary_(fxt.kv_dec_("k0", Decimal_adp_.parse("1.23"))));}
	@Test   public void Ary_int()				{fxt.Test_parse("{'k0':[1,2,3]}"				, fxt.ary_(fxt.kv_obj_("k0", fxt.ary_(fxt.kv_int_("1", 1), fxt.kv_int_("2", 2), fxt.kv_int_("3", 3)))));}
	@Test   public void Ary_empty()				{fxt.Test_parse("{'k0':[]}"						, fxt.ary_(fxt.kv_obj_("k0", fxt.ary_())));}
	@Test   public void Subs_int()				{fxt.Test_parse("{'k0':{'k00':1,'k01':2}}"		, fxt.ary_(fxt.kv_obj_("k0", fxt.ary_(fxt.kv_int_("k00", 1), fxt.kv_int_("k01", 2)))));}
	@Test   public void Subs_empty()			{fxt.Test_parse("{'k0':{}}"						, fxt.ary_(fxt.kv_obj_("k0", fxt.ary_())));}
}
class Json_kv_ary_srl_fxt {
	public void Clear() {
		if (parser == null) {
			parser = new Json_parser();
		}
	}	private Json_parser parser;
	public void Test_parse(String raw_str, Keyval[] expd) {
		byte[] raw_bry = Json_parser_tst.Replace_apos(Bry_.new_u8(raw_str));
		Json_doc doc = parser.Parse(raw_bry);
		Keyval[] actl = Json_kv_ary_srl.Val_by_itm_nde(doc.Root_nde());
		Tfds.Eq_str_lines(Keyval_.Ary_to_str(expd), Keyval_.Ary_to_str(actl));
	}
	public Keyval[] ary_(Keyval... ary) {return ary;}
	public Keyval kv_obj_(String key, Object val)		{return Keyval_.new_(key, val);}
	public Keyval kv_str_(String key, String val)		{return Keyval_.new_(key, val);}
	public Keyval kv_int_(String key, int val)			{return Keyval_.new_(key, val);}
	public Keyval kv_bool_(String key, boolean val)		{return Keyval_.new_(key, Bool_.To_str_lower(val));}
	public Keyval kv_dec_(String key, Decimal_adp val)	{return Keyval_.new_(key, val.To_str());}
}
