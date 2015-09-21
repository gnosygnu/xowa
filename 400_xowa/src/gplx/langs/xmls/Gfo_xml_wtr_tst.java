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
package gplx.langs.xmls; import gplx.*; import gplx.langs.*;
import org.junit.*;
public class Gfo_xml_wtr_tst {
	private final Gfo_xml_wtr_fxt fxt = new Gfo_xml_wtr_fxt();
	@Before public void init() {}
	@Test   public void Root() {
		fxt.Wtr().Nde_lhs("a").Nde_rhs();
		fxt.Test_bld("<a>", "</a>");
	}
	@Test   public void Nest() {
		fxt.Wtr()
			.Nde_lhs("a")
			.	Nde_lhs("a_a")
			.		Nde_lhs("a_a_a")
			.		Nde_rhs()
			.	Nde_rhs()
			.Nde_rhs()
			;
		fxt.Test_bld
		( "<a>"
		, "  <a_a>"
		, "    <a_a_a>"
		, "    </a_a_a>"
		, "  </a_a>"
		, "</a>"
		);
	}
	@Test   public void Atrs() {
		fxt.Wtr()
			.Nde_lhs_bgn_itm("a")
			.Atr_kv_str_a7("b", "b1")
			.Nde_lhs_end()
			.Nde_rhs()
		;
		fxt.Test_bld("<a b='b1'></a>");
	}
	@Test   public void Atrs_escape() {
		fxt.Wtr()
			.Nde_lhs_bgn_itm("a")
			.Atr_kv_str_a7("b", "'\"<>&")
			.Nde_lhs_end()
			.Nde_rhs()
		;
		fxt.Test_bld("<a b='&apos;\"<>&'></a>");
	}
	@Test   public void Nde_txt() {
		fxt.Wtr()
			.Nde_txt_str("a", "a123")
		;
		fxt.Test_bld("<a>a123</a>");
	}
	@Test   public void Nde_txt_escape() {
		fxt.Wtr()
			.Nde_txt_str("a", "'\"<>&x")
		;
		fxt.Test_bld("<a>'\"&lt;&gt;&amp;x</a>");
	}
}
class Gfo_xml_wtr_fxt {		
	public Gfo_xml_wtr Wtr() {return wtr;} private final Gfo_xml_wtr wtr = new Gfo_xml_wtr();
	public void Test_bld(String... lines) {
		Tfds.Eq_str_lines(String_.Concat_lines_nl_skip_last(lines), wtr.Bld_str());
	}
}
