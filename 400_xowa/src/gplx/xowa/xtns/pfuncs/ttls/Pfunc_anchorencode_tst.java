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
package gplx.xowa.xtns.pfuncs.ttls;
import gplx.core.consoles.Console_adp__sys;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xop_fxt;
import org.junit.Before;
import org.junit.Test;
public class Pfunc_anchorencode_tst {
	private final Pfunc_anchorenchode_fxt fxt = new Pfunc_anchorenchode_fxt(BoolUtl.N);
	@Before public void init() {fxt.Reset();}
	@Test public void Text_apos() {
		fxt.Test("{{anchorencode:a 'b c}}", "a_'b_c");
	}
	@Test public void Apos_bold() {
		fxt.Test("{{anchorencode:a ''b'' c}}", "a_b_c");
	}
	@Test public void Html_ncr() {
		fxt.Test("{{anchorencode:a &#34; b}}", "a_&quot;_b");
	}
	@Test public void Html_ref() {
		fxt.Test("{{anchorencode:a &quot; b}}", "a_&quot;_b");
	}
	@Test public void Xnde() {
		fxt.Test("{{anchorencode:a <i>b</i> c}}", "a_b_c");
	}
	@Test public void Lnke() {
		fxt.Test("{{anchorencode:[irc://a b c]}}", "b_c");
	}
	@Test public void Lnki_trg() {
		fxt.Test("{{anchorencode:a [[b]] c}}", "a_b_c");
	}
	@Test public void Lnki_caption() {
		fxt.Test("{{anchorencode:a [[b|c]] c}}", "a_c_c");
	}
	@Test public void Lnki_file() {
		fxt.Test("{{anchorencode:a [[Image:b|thumb|123px|c]] d}}", "a_thumb|123px|c_d");
	}
	@Test public void Lnki_trailing() {
		fxt.Test("{{anchorencode:a [[b]]c d}}", "a_bc_d");
	}
	@Test public void Lnki_literal() {
		fxt.Test("{{anchorencode:[[:a]]}}", "a");
	}
	@Test public void Lnki_caption_html() { // ISSUE#:460
		fxt.Test("{{anchorencode:[[a|<span style=\"color:red\">b</span>]]}}", "b");
	}
	@Test public void Lnki_missing_basic() {
		fxt.Test("{{anchorencode:{{Xowa_missing}}}}", "Template:Xowa_missing");
	}
	@Test public void Lnki_missing_colon() {
		fxt.Test("{{anchorencode:{{:Xowa_missing}}}}", "Xowa_missing");
	}
	@Test public void Tmpl() {
		fxt.Make_page("Template:Xowa1", "a<span>b</span>c");
		fxt.Test(false, "{{anchorencode:{{Xowa1}}}}", "abc");
	}
}
class Pfunc_anchorenchode_fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	private final boolean dbg;
	public Pfunc_anchorenchode_fxt(boolean dbg) {
		this.dbg = dbg;
	}
	public void Reset() {
		fxt.Reset();
	}
	public void Make_page(String ttl, String text) {
		fxt.Init_page_create(ttl, text);
	}
	public void Test(String raw, String expd) {this.Test(dbg, raw, expd);}
	public void Test(boolean dbg, String raw, String expd) {
		if (dbg) Console_adp__sys.Instance.Write_str(fxt.Make__test_string(raw, expd));
		fxt.Test_str_full(raw, expd, fxt.Exec_parse_page_all_as_str(raw));
	}
}
