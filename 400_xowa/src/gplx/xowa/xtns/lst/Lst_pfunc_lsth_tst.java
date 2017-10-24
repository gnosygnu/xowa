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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Lst_pfunc_lsth_tst {
	private final    Lst_pfunc_lst_fxt fxt = new Lst_pfunc_lst_fxt();
	private final    String src_str_dflt = String_.Concat_lines_nl_skip_last
	( "txt_0"
	, "== hdr_1 =="
	, "txt_1"
	, "== hdr_2 =="
	, "txt_2"
	, "== hdr_3 =="
	, "txt_3"
	);
	@Before public void init() {fxt.Clear();}
	@Test  public void Bgn__missing() {	// PURPOSE: return ""
		fxt.Page_txt_(src_str_dflt).Test_lst("{{#lsth:section_test|hdr_x}}", "");
	}
	@Test  public void End__exists() {
		fxt.Page_txt_(src_str_dflt).Test_lst("{{#lsth:section_test|hdr_1|hdr_3}}", String_.Concat_lines_nl_skip_last
		( "txt_1"
		, ""
		, "<h2> hdr_2 </h2>"
		, "txt_2"
		));
	}
	@Test  public void End__missing() {	// PURPOSE: read to end of next section
		String expd = String_.Concat_lines_nl_skip_last
		( "txt_1"
		);
		fxt.Clear().Page_txt_(src_str_dflt).Test_lst("{{#lsth:section_test|hdr_1}}"		, expd);	// argument not given
		fxt.Clear().Page_txt_(src_str_dflt).Test_lst("{{#lsth:section_test|hdr_1|hdr_x}}", expd);	// argument is wrong
	}
	@Test  public void End__missing_eos() {	// PURPOSE: read to EOS if last
		String expd = String_.Concat_lines_nl_skip_last
		( "txt_3"
		);
		fxt.Clear().Page_txt_(src_str_dflt).Test_lst("{{#lsth:section_test|hdr_3}}"		, expd);	// argument not given
		fxt.Clear().Page_txt_(src_str_dflt).Test_lst("{{#lsth:section_test|hdr_3|hdr_x}}", expd);	// argument is wrong
	}
	@Test  public void End__missing__match__len() {	// PURPOSE:match next hdr with same length; PAGE:en.w:10s_BC; DATE:2016-08-13
		String src_str = String_.Concat_lines_nl_skip_last
		( "txt_0"
		, "== hdr_1 =="
		, "txt_1"
		, "=== hdr_1a ==="
		, "txt_1a"
		, "== hdr_2 =="
		, "txt_2"
		);
		fxt.Page_txt_(src_str).Test_lst("{{#lsth::section_test|hdr_1}}", String_.Concat_lines_nl_skip_last
		( "txt_1"
		, ""
		, "<h3> hdr_1a </h3>"
		, "txt_1a"
		));
	}
	@Test  public void Extra_nl() {	// PURPOSE: hdr.Src_end() includes trailing nl; PAGE:en.w:10s_BC; DATE:2016-08-13
		String src_str = String_.Concat_lines_nl_skip_last
		( "txt_0"
		, "== hdr_1 =="
		, ""
		, "txt_1"
		, "== hdr_2 =="
		, "txt_2"
		);
		fxt.Clear().Page_txt_(src_str).Test_lst("{{#lsth:section_test|hdr_1}}"		, "txt_1");
	}
	@Test  public void Only_include() {	// PAGE:en.w:10s_BC; DATE:2016-08-13
		String src_str = String_.Concat_lines_nl_skip_last
		( "txt_0"
		, "== hdr_1 =="
		, "<onlyinclude>txt_1</onlyinclude>"
		, "== hdr_2 =="
		, "txt_2"
		, "== hdr_3 =="
		, "txt_3"
		);
		fxt.Page_txt_(src_str).Test_lst("{{#lsth::section_test|hdr_1}}", "txt_1");
	}
	@Test   public void Bos() {	// PURPOSE.defensive:handle == at BOS; DATE:2016-08-13
		String src_str = String_.Concat_lines_nl_skip_last
		( "==hdr_1 =="
		, "txt_1"
		, "== hdr_2 =="
		, "txt_2"
		);
		fxt.Clear().Page_txt_(src_str).Test_lst("{{#lsth:section_test|hdr_1}}", "txt_1");
	}
	@Test  public void Nested__lst() {	// PURPOSE:lst inside lsth will add its toc_mgr to lsth; PAGE:en.w:Germany_national_football_team; DATE:2016-08-13
		fxt.Fxt().Init_page_create("Nested_lst", String_.Concat_lines_nl_skip_last
		( "test"
		, "==hdr_1=="
		, "txt_1"
		));
		String src_str = String_.Concat_lines_nl_skip_last
		( "{{#lst:Nested_lst}}"
		, "==hdr_2=="
		, "txt_2"
		, "==hdr_3=="
		, "txt_3"
		);
		fxt.Page_txt_(src_str).Test_lst("{{#lsth::section_test|hdr_1}}", "txt_1");	// will fail with idx_out_of_bounds b/c hdr_1.Src_bgn / hdr_1.Src_end will be for Nested_lst's src
	}
	@Test  public void Tmpl_w_nowiki() {	// ISSUE:nowiki inside template can cause wrong offsets; PAGE:en.w:Germany_national_football_team; DATE:2016-08-13
		fxt.Fxt().Init_page_create("Template:Nested_nowiki", "<nowiki>test</nowiki>");
		String src_str = String_.Concat_lines_nl_skip_last
		( "{{Nested_nowiki}}"
		, "==hdr_2=="
		, "txt_2"
		, "==hdr_3=="
		, "txt_3"
		);
		fxt.Page_txt_(src_str).Test_lst("{{#lsth::section_test|hdr_2}}", "txt_2");	// will fail with "" b/c <nowiki> requires a 2nd "sub_src = root.Data_mid()"
	}
}
