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
package gplx.xowa.parsers.hdrs.sections;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.htmls.core.htmls.tidy.*;
public class Xop_section_list__slice__tst {
	private final Xop_section_list__fxt fxt = new Xop_section_list__fxt();
	@Test public void Basic() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		, ""
		, "== Hdr 3 =="
		, "Para 3"
		);
		fxt.Test__slice_bry_or_null("Hdr_1"
		, "== Hdr 1 =="
		, "Para 1"
		);
		fxt.Test__slice_bry_or_null("Hdr_2"
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__slice_bry_or_null("Hdr_3"
		, "== Hdr 3 =="
		, "Para 3"
		);
	}
	@Test public void Covering() {
		fxt.Exec__parse
		( "== Hdr 1 =="
		, "Para 1"
		, ""
		, "=== Hdr 1a ==="
		, "Para 1a"
		, ""
		, "=== Hdr 1b ==="
		, "Para 1b"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__slice_bry_or_null("Hdr_1"
		, "== Hdr 1 =="
		, "Para 1"
		, ""
		, "=== Hdr 1a ==="
		, "Para 1a"
		, ""
		, "=== Hdr 1b ==="
		, "Para 1b"
		);
	}
	@Test public void Xml() {
		fxt.Exec__parse
		( "== <i>Hdr 1</i> =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__slice_bry_or_null("Hdr_1", StringUtl.ConcatLinesNlSkipLast
		( "== <i>Hdr 1</i> =="
		, "Para 1"
		));
	}
	@Test public void Math() {
		fxt.Exec__parse
		( "== <math>\\delta</math> =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__slice_bry_or_null("\\delta", StringUtl.ConcatLinesNlSkipLast // ISSUE#:462; DATE:2019-05-12
		( "== <math>\\delta</math> =="
		, "Para 1"
		));
	}
	@Test public void Template() {
		fxt.Init__template("mock", "''{{{1}}}''");
		fxt.Exec__parse
		( "== {{mock|a}} =="
		, "Para 1"
		, ""
		, "== Hdr 2 =="
		, "Para 2"
		);
		fxt.Test__slice_bry_or_null("a", StringUtl.ConcatLinesNlSkipLast
		( "== {{mock|a}} =="
		, "Para 1"
		));
	}
	@Test public void Lead() {
		fxt.Exec__parse
		( "lead text"
		, ""
		, "== Hdr 1 =="
		, "Para 1"
		, ""
		);
		fxt.Test__slice_bry_or_null(""
		, "lead text"
		);
	}
	@Test public void Lead__none() {
		fxt.Exec__parse
		( ""
		, "== Hdr 1 =="
		, "Para 1"
		, ""
		);
		fxt.Test__slice_bry_or_null("");
	}
	@Test public void Lead__eos() {
		fxt.Exec__parse
		( "lead text"
		, ""
		, "para 1"
		, ""
		);
		fxt.Test__slice_bry_or_null(""
		, "lead text"
		, ""
		, "para 1"
		);
	}
}
class Xop_section_list__fxt {
	private final Xop_section_list list = new Xop_section_list();
	private final Xop_fxt parser_fxt = new Xop_fxt();
	public void Init__template(String page, String text) {parser_fxt.Init_defn_add(page, text);}
	public void Exec__parse(String... lines) {
		list.Parse(parser_fxt.Wiki(), Xow_tidy_mgr_interface_.Noop, Xoa_url.Test(), BryUtl.NewU8(StringUtl.ConcatLinesNlSkipLast(lines)));
	}
	public void Test__slice_bry_or_null(String key, String... lines) {
		String expd = StringUtl.ConcatLinesNlSkipLast(lines);
		byte[] actl = list.Slice_bry_or_null(BryUtl.NewU8(key));
		GfoTstr.EqLines(expd, actl, key);
	}
	public void Test__merge_bry_or_null(String key, String edit, String expd) {
		byte[] actl = list.Merge_bry_or_null(BryUtl.NewU8(key), BryUtl.NewU8(edit));
		GfoTstr.EqLines(expd, actl, key);
	}
}
