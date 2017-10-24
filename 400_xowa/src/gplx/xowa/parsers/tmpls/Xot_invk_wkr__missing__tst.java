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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.wikis.nss.*;
public class Xot_invk_wkr__missing__tst {		
	@Before public void init() {fxt.Reset();} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Missing() {
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_template", "{{[[Template:{{{1}}}|{{{1}}}]]}}");
		fxt.Test_parse_tmpl_str("{{test_template|a}}", "{{[[Template:a|a]]}}");
		fxt.Init_defn_clear();
	}
	@Test  public void Missing__name_and_args() {	// PURPOSE: missing title should return name + args; used to only return name; PAGE:en.w:Flag_of_Greenland; DATE:2016-06-21
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_template", "{{ {{{1}}} | a | b }}");
		fxt.Test_parse_tmpl_str("{{test_template}}", "{{{{{1}}}| a | b }}");	// NOTE: this should include spaces (" {{{1}}} "), but for now, ignore
		fxt.Init_defn_clear();
	}
	@Test  public void Missing__evaluate_optional() {	// PURPOSE: missing title should still evaulate optional args; "{{{a|}}}" -> ""; PAGE:en.w:Europe; en.w:Template:Country_data_Guernsey DATE:2016-10-13
		fxt.Init_defn_clear();
		fxt.Init_defn_add("test_template", "{{ {{{1}}} | {{{a|}}} | b }}");
		fxt.Test_parse_tmpl_str("{{test_template}}", "{{{{{1}}}|  | b }}");	// NOTE: "|  |" not "| {{{a|}}} |"
		fxt.Init_defn_clear();
	}
	@Test  public void Missing_foreign() {
		Xow_ns ns = fxt.Wiki().Ns_mgr().Ns_template();
		byte[] old_ns = ns.Name_db();
		ns.Name_bry_(Bry_.new_a7("Template_foreign"));
		fxt.Test_parse_tmpl_str("{{Missing}}", "[[:Template_foreign:Missing]]");
		ns.Name_bry_(old_ns);
	}
}
