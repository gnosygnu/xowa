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
package gplx.xowa.bldrs.cmds.texts.xmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xob_siteinfo_parser__tst {
	private final    Xob_siteinfo_parser__fxt fxt = new Xob_siteinfo_parser__fxt();
	@Test   public void Basic__simplewikt() {	// PURPOSE: basic test of siteinfo parse; DATE:2015-11-01
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "  <siteinfo>"
		, "    <sitename>Wiktionary</sitename>"
		, "    <dbname>simplewiktionary</dbname>"
		, "    <super>https://simple.wiktionary.org/wiki/Main_Page</super>"
		, "    <generator>MediaWiki 1.27.0-wmf.3</generator>"
		, "    <case>case-sensitive</case>"
		, "    <namespaces>"
		, "      <namespace key=\"-2\" case=\"case-sensitive\">Media</namespace>"
		, "      <namespace key=\"-1\" case=\"first-letter\">Special</namespace>"
		, "      <namespace key=\"0\" case=\"case-sensitive\" />"
		, "      <namespace key=\"1\" case=\"case-sensitive\">Talk</namespace>"
		, "      <namespace key=\"2\" case=\"first-letter\">User</namespace>"
		, "      <namespace key=\"3\" case=\"first-letter\">User talk</namespace>"
		, "      <namespace key=\"4\" case=\"case-sensitive\">Wiktionary</namespace>"
		, "      <namespace key=\"5\" case=\"case-sensitive\">Wiktionary talk</namespace>"
		, "      <namespace key=\"6\" case=\"case-sensitive\">File</namespace>"
		, "      <namespace key=\"7\" case=\"case-sensitive\">File talk</namespace>"
		, "      <namespace key=\"8\" case=\"first-letter\">MediaWiki</namespace>"
		, "      <namespace key=\"9\" case=\"first-letter\">MediaWiki talk</namespace>"
		, "      <namespace key=\"10\" case=\"case-sensitive\">Template</namespace>"
		, "      <namespace key=\"11\" case=\"case-sensitive\">Template talk</namespace>"
		, "      <namespace key=\"12\" case=\"case-sensitive\">Help</namespace>"
		, "      <namespace key=\"13\" case=\"case-sensitive\">Help talk</namespace>"
		, "      <namespace key=\"14\" case=\"case-sensitive\">Category</namespace>"
		, "      <namespace key=\"15\" case=\"case-sensitive\">Category talk</namespace>"
		, "      <namespace key=\"828\" case=\"case-sensitive\">Module</namespace>"
		, "      <namespace key=\"829\" case=\"case-sensitive\">Module talk</namespace>"
		, "      <namespace key=\"2300\" case=\"case-sensitive\">Gadget</namespace>"
		, "      <namespace key=\"2301\" case=\"case-sensitive\">Gadget talk</namespace>"
		, "      <namespace key=\"2302\" case=\"case-sensitive\">Gadget definition</namespace>"
		, "      <namespace key=\"2303\" case=\"case-sensitive\">Gadget definition talk</namespace>"
		, "      <namespace key=\"2600\" case=\"first-letter\">Topic</namespace>"
		, "    </namespaces>"
		, "  </siteinfo>"
		), String_.Concat_lines_nl
		( "Main_Page|case-sensitive|Wiktionary|simplewiktionary|MediaWiki 1.27.0-wmf.3"
		, "-2|case-sensitive|Media"
		, "-1|first-letter|Special"
		, "0|case-sensitive|"
		, "1|case-sensitive|Talk"
		, "2|first-letter|User"
		, "3|first-letter|User talk"
		, "4|case-sensitive|Wiktionary"
		, "5|case-sensitive|Wiktionary talk"
		, "6|case-sensitive|File"
		, "7|case-sensitive|File talk"
		, "8|first-letter|MediaWiki"
		, "9|first-letter|MediaWiki talk"
		, "10|case-sensitive|Template"
		, "11|case-sensitive|Template talk"
		, "12|case-sensitive|Help"
		, "13|case-sensitive|Help talk"
		, "14|case-sensitive|Category"
		, "15|case-sensitive|Category talk"
		, "828|case-sensitive|Module"
		, "829|case-sensitive|Module talk"
		, "2300|case-sensitive|Gadget"
		, "2301|case-sensitive|Gadget talk"
		, "2302|case-sensitive|Gadget definition"
		, "2303|case-sensitive|Gadget definition talk"
		, "2600|first-letter|Topic"
		, "2601|first-letter|2601"	// NOTE: Topic_talk doesn't exist in <siteinfo>, but added by XOWA b/c every subj ns must have a talk ns
		));
	}
	@Test   public void Case_dflt() {	// PURPOSE: missing case should use dflt DATE:2015-11-01
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "  <siteinfo>"
		, "    <case>case-sensitive</case>"
		, "    <namespaces>"
		, "      <namespace key=\"-2\">Media</namespace>"
		, "    </namespaces>"
		, "  </siteinfo>"
		), String_.Concat_lines_nl
		( "Main_Page|case-sensitive|||"
		, "-2|case-sensitive|Media"
		));
	}
}
class Xob_siteinfo_parser__fxt {
	private final    Xow_ns_mgr ns_mgr = new Xow_ns_mgr(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Test__parse(String src_str, String expd) {
		Xob_siteinfo_nde nde = Xob_siteinfo_parser_.Parse(src_str, ns_mgr);
		nde.To_bfr(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
