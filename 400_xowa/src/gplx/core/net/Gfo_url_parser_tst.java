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
package gplx.core.net; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Gfo_url_parser_tst {
	private final    Gfo_url_parser_fxt tstr = new Gfo_url_parser_fxt();
	@Test  public void Protocol__relative() {
		tstr.Exec__parse("//en.wikipedia.org").Test__protocol_tid(Gfo_protocol_itm.Tid_relative_1).Test__protocol_bry("//").Test__site("en.wikipedia.org");
	}
	@Test  public void Protocol__none() {
		tstr.Exec__parse("en.wikipedia.org/wiki/A").Test__protocol_tid(Gfo_protocol_itm.Tid_unknown).Test__segs("en.wikipedia.org", "wiki", "A");
	}
	@Test  public void Site__parts__3() {
		tstr.Exec__parse("https://en.wikipedia.org").Test__protocol_tid(Gfo_protocol_itm.Tid_https).Test__protocol_bry("https://").Test__segs("en.wikipedia.org");
	}
	@Test  public void Site__parts__2() {
		tstr.Exec__parse("https://wikipedia.org").Test__protocol_tid(Gfo_protocol_itm.Tid_https).Test__segs("wikipedia.org");
	}
	@Test  public void Site__parts__1() {
		tstr.Exec__parse("https://wikipedia").Test__protocol_tid(Gfo_protocol_itm.Tid_https).Test__segs("wikipedia");
	}
	@Test   public void Site__slash__none() {
		tstr.Exec__parse("https:site").Test__protocol_tid(Gfo_protocol_itm.Tid_https).Test__site("site");
	}
	@Test   public void Site__slash__eos() {
		tstr.Exec__parse("https://en.wikipedia.org/").Test__protocol_tid(Gfo_protocol_itm.Tid_https).Test__site("en.wikipedia.org");
	}
	@Test  public void Paths__1() {
		tstr.Exec__parse("https://site/A").Test__segs("site", "A");
	}
	@Test  public void Paths__2() {
		tstr.Exec__parse("https://site/wiki/A").Test__segs("site", "wiki", "A");
	}
	@Test  public void Paths__n() {
		tstr.Exec__parse("https://site/wiki/A/B/C/D").Test__segs("site", "wiki", "A", "B", "C", "D");
	}
	@Test  public void Qargs__1() {
		tstr.Exec__parse("https://site/A?B=C").Test__page("A").Test__qargs("B", "C");
	}
	@Test  public void Qargs__2() {
		tstr.Exec__parse("https://site/A?B=C&D=E").Test__page("A").Test__qargs("B", "C", "D", "E");
	}
	@Test  public void Qargs__3() {
		tstr.Exec__parse("https://site/A?B=C&D=E&F=G").Test__page("A").Test__qargs("B", "C", "D", "E", "F", "G");
	}
	@Test  public void Qargs__ques__dupe__ques() {
		tstr.Exec__parse("https://site/A?B?Y=Z").Test__page("A?B").Test__qargs("Y", "Z");
	}
	@Test  public void Qargs__ques__dupe__amp() {
		tstr.Exec__parse("https://site/A?B=C&D?Y=Z").Test__page("A?B=C&D").Test__qargs("Y", "Z");
	}
	@Test  public void Qargs__ques__dupe__eq() {
		tstr.Exec__parse("https://site/A?B=C?Y=Z").Test__page("A?B=C").Test__qargs("Y", "Z");
	}
	@Test  public void Qargs__amp__dupe__ques() {
		tstr.Exec__parse("https://site/A?B&Y=Z").Test__page("A").Test__qargs("B", null, "Y", "Z");
	}
	@Test  public void Qargs__amp__dupe__amp() {
		tstr.Exec__parse("https://site/A?B=C&D&Y=Z").Test__page("A").Test__qargs("B", "C", "D", null, "Y", "Z");
	}
	@Test  public void Qargs__missing_val__0() {
		tstr.Exec__parse("https://site/A?").Test__page("A?").Test__qargs();
	}
	@Test  public void Qargs__missing_val__2() {
		tstr.Exec__parse("https://site/A?B=C&D&F=G").Test__page("A").Test__qargs("B", "C", "D", null, "F", "G");
	}
	@Test  public void Qargs__missing_val__n() {
		tstr.Exec__parse("https://site/A?B=C&D=E&F").Test__page("A").Test__qargs("B", "C", "D", "E", "F", null);
	}
	@Test  public void Qargs__site_less__missing__0() {
		tstr.Exec__parse("A?B").Test__segs("A?B").Test__qargs();
	}
	@Test  public void Qargs__site_less() {
		tstr.Exec__parse("A?B=C&D=E").Test__site("A").Test__qargs("B", "C", "D", "E");
	}
	@Test  public void Anch__basic() {
		tstr.Exec__parse("https://site/A#B").Test__page("A").Test__anch("B");
	}
	@Test  public void Anch__repeat__2() {
		tstr.Exec__parse("https://site/A#B#C").Test__page("A").Test__anch("B#C");
	}
	@Test  public void Anch__repeat__3() {
		tstr.Exec__parse("https://site/A#B#C#D").Test__page("A").Test__anch("B#C#D");
	}
	@Test  public void Anch__missing() {
		tstr.Exec__parse("https://site/A#").Test__page("A#").Test__anch(null);
	}
	@Test  public void Anch__missing__eos() {
		tstr.Exec__parse("https://site/A#B#").Test__page("A").Test__anch("B#");
	}
	@Test  public void Anch__qargs__basic() {
		tstr.Exec__parse("https://site/A?B=C&D=E#F").Test__page("A").Test__qargs("B", "C", "D", "E").Test__anch("F");
	}
	@Test  public void Anch__site_less() {
		tstr.Exec__parse("A#B").Test__site("A").Test__anch("B");
	}
	@Test  public void Encode__page() {
		tstr.Exec__parse("http://site/A%27s").Test__site("site").Test__page("A's");
	}
	@Test  public void Protocol_less__qargs() {
		tstr.Exec__parse("Special:Search/Earth?fulltext=yes").Test__segs("Special:Search", "Earth").Test__page("Earth").Test__qargs("fulltext", "yes");
	}
	@Test  public void Parse_site_fast() {
		tstr.Test_Parse_site_fast("http://a.org/B"		, "a.org");
		tstr.Test_Parse_site_fast("http://a.org"		, "a.org");
		tstr.Test_Parse_site_fast("//a.org/B"			, "a.org");
		tstr.Test_Parse_site_fast("//a.org/B:C"			, "a.org");
	}
	// DELETED: logic isn't right; anch is first # not last; EX: https://en.wikipedia.org/w/index.php?title=Category:2001_albums&pagefrom=Beautiful+#View#mw-pages; DATE:2016-10-10
	// @Test  public void Anch__qargs__repeat() {
	//	tstr.Exec__parse("https://site/A?B=C#&D=E#F").Test__page("A").Test__qargs("B", "C#", "D", "E").Test__anch("F");
	// }
}
