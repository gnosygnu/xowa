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

import gplx.Bool_;
import gplx.Bry_;
import gplx.Decimal_adp;
import gplx.Decimal_adp_;
import gplx.Keyval;
import gplx.Keyval_;
import gplx.Tfds;
import org.junit.Test;

public class Json_kv_ary_srl_tst {
	private final Json_kv_ary_srl_fxt fxt = new Json_kv_ary_srl_fxt();
	@Test public void Null()           {fxt.Test_parse("{'k0':null}"                  , fxt.ary_(fxt.New_kv_str("k0", null)));}
	@Test public void Bool_n()         {fxt.Test_parse("{'k0':false}"                 , fxt.ary_(fxt.New_kv_bool("k0", false)));}
	@Test public void Num()            {fxt.Test_parse("{'k0':123}"                   , fxt.ary_(fxt.New_kv_int("k0", 123)));}
	@Test public void Num_exp()        {fxt.Test_parse("{'k0':1.23e2}"                , fxt.ary_(fxt.New_kv_int("k0", 123)));} // exponent can be either "e" or "E" in JSON, but Java decimal parse only takes "E"; ISSUE#:565; DATE:2020-03-25
	@Test public void Str()            {fxt.Test_parse("{'k0':'v0'}"                  , fxt.ary_(fxt.New_kv_str("k0", "v0")));}
	@Test public void Num_dec()        {fxt.Test_parse("{'k0':1.23}"                  , fxt.ary_(fxt.New_kv_dec("k0", Decimal_adp_.parse("1.23"))));}
	@Test public void Ary_int()        {fxt.Test_parse("{'k0':[1,2,3]}"               , fxt.ary_(fxt.New_kv_obj("k0", fxt.ary_(fxt.New_kv_int("1", 1), fxt.New_kv_int("2", 2), fxt.New_kv_int("3", 3)))));}
	@Test public void Ary_empty()      {fxt.Test_parse("{'k0':[]}"                    , fxt.ary_(fxt.New_kv_obj("k0", fxt.ary_())));}
	@Test public void Subs_int()       {fxt.Test_parse("{'k0':{'k00':1,'k01':2}}"     , fxt.ary_(fxt.New_kv_obj("k0", fxt.ary_(fxt.New_kv_int("k00", 1), fxt.New_kv_int("k01", 2)))));}
	@Test public void Subs_empty()     {fxt.Test_parse("{'k0':{}}"                    , fxt.ary_(fxt.New_kv_obj("k0", fxt.ary_())));}
}
class Json_kv_ary_srl_fxt {
	private final Json_parser parser = new Json_parser();
	public void Test_parse(String raw_str, Keyval[] expd) {
		byte[] raw_bry = Bry_.new_u8(Json_doc.Make_str_by_apos(raw_str));
		Json_doc doc = parser.Parse(raw_bry);
		Keyval[] actl = Json_kv_ary_srl.Val_by_itm_nde(doc.Root_nde());
		Tfds.Eq_str_lines(Keyval_.Ary_to_str(expd), Keyval_.Ary_to_str(actl));
	}
	public Keyval[] ary_(Keyval... ary) {return ary;}
	public Keyval New_kv_obj(String key, Object val)        {return Keyval_.new_(key, val);}
	public Keyval New_kv_str(String key, String val)        {return Keyval_.new_(key, val);}
	public Keyval New_kv_int(String key, int val)           {return Keyval_.new_(key, val);}
	public Keyval New_kv_bool(String key, boolean val)      {return Keyval_.new_(key, Bool_.To_str_lower(val));}
	public Keyval New_kv_dec(String key, Decimal_adp val)   {return Keyval_.new_(key, val.To_str());}
}
