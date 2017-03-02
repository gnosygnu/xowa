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
public class Json_wtr_tst {
	@Before public void init() {fxt.Clear();} private final    Json_wtr_fxt fxt = new Json_wtr_fxt();
	@Test   public void Root() {
		fxt.Wtr().Doc_nde_bgn().Doc_nde_end();
		fxt.Test
		( "{"
		, "}"
		);
	}
	@Test   public void Kv() {
		fxt.Wtr()
			.Doc_nde_bgn()
			.Kv_str("k0", "v0")
			.Kv_str("k1", "v1")
			.Doc_nde_end();
		fxt.Test
		( "{ 'k0':'v0'"
		, ", 'k1':'v1'"
		, "}"
		);
	}
	@Test   public void Escaped() {
		fxt.Wtr()
			.Doc_nde_bgn()
			.Kv_str("backslash", "\\")
			.Kv_str("quote", "\"")
			.Kv_str("apos", "'")
			.Kv_str("nl", "\n")
			.Kv_str("cr", "\r")
			.Kv_str("tab", "\t")
			.Doc_nde_end();
		fxt.Test
		( "{ 'backslash':'\\\\'"
		, ", 'quote':'\\\"'"
		, ", 'apos':'\''"
		, ", 'nl':'\\\\n'"
		, ", 'cr':'\\\\r'"
		, ", 'tab':'\\\\t'"
		, "}"
		);
	}
	@Test   public void Nde() {
		fxt.Wtr()
			.Doc_nde_bgn()
				.Nde_bgn("s0")
					.Nde_bgn("s00")
					.Nde_end()
				.Nde_end()
				.Nde_bgn("s1")
					.Nde_bgn("s10")
					.Nde_end()
				.Nde_end()
			.Doc_nde_end();
		fxt.Test
		( "{ 's0':"
		, "  { 's00':"
		, "    {"
		, "    }"
		, "  }"
		, ", 's1':"
		, "  { 's10':"
		, "    {"
		, "    }"
		, "  }"
		, "}"
		);
	}
	@Test   public void Ary() {
		fxt.Wtr()
			.Doc_nde_bgn()
			.Ary_bgn("a0")
			.Ary_itm_str("v0")
			.Ary_itm_str("v1")
			.Ary_end()
			.Doc_nde_end();
		fxt.Test
		( "{ 'a0':"
		, "  [ 'v0'"
		, "  , 'v1'"
		, "  ]"
		, "}"
		);
	}
	@Test   public void Nde__nested() {
		fxt.Wtr()
			.Doc_nde_bgn()
			.Ary_bgn("a0")
				.Ary_itm_obj(Keyval_.Ary
				(	Keyval_.new_("k1", "v1")
				,	Keyval_.new_("k2", "v2")
				))
			.Ary_end()
			.Doc_nde_end();
		fxt.Test
		( "{ 'a0':"
		, "  ["
		, "    { 'k1':'v1'"
		, "    , 'k2':'v2'"
		, "    }"
		, "  ]"
		, "}"
		);
	}
}
class Json_wtr_fxt {
	private final    Json_wtr wtr = new Json_wtr().Opt_quote_byte_(Byte_ascii.Apos);
	public void Clear() {wtr.Clear();}
	public Json_wtr Wtr() {return wtr;}
	public void Test(String... expd) {
		Tfds.Eq_ary_str
		( String_.Ary_add(expd, String_.Ary(""))		// json_wtr always ends with "}\n"; rather than add "\n" to each test, just add it here
		, String_.SplitLines_nl(String_.new_u8(wtr.To_bry_and_clear()))
		);
	}
}
