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
package gplx.langs.jsons; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Json_wtr_tst {
	@Before public void init() {fxt.Clear();} private final Json_wtr_fxt fxt = new Json_wtr_fxt();
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
				.Ary_itm_obj(KeyVal_.Ary
				(	KeyVal_.new_("k1", "v1")
				,	KeyVal_.new_("k2", "v2")
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
	private final Json_wtr wtr = new Json_wtr().Opt_quote_byte_(Byte_ascii.Apos);
	public void Clear() {wtr.Clear();}
	public Json_wtr Wtr() {return wtr;}
	public void Test(String... expd) {
		Tfds.Eq_ary_str
		( String_.Ary_add(expd, String_.Ary(""))		// json_wtr always ends with "}\n"; rather than add "\n" to each test, just add it here
		, String_.SplitLines_nl(String_.new_u8(wtr.To_bry_and_clear()))
		);
	}
}
