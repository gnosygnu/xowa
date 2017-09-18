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
