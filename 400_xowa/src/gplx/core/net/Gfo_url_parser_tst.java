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
package gplx.core.net; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Gfo_url_parser_tst {
	private final Gfo_url_parser_fxt tstr = new Gfo_url_parser_fxt();
	@Test  public void Protocol__relative() {
		tstr.Run_parse("//en.wikipedia.org").Chk_protocol_tid(Gfo_protocol_itm.Tid_relative_1).Chk_protocol_bry("//").Chk_site("en.wikipedia.org");
	}
	@Test  public void Protocol__none() {
		tstr.Run_parse("en.wikipedia.org/wiki/A").Chk_protocol_tid(Gfo_protocol_itm.Tid_unknown).Chk_segs("en.wikipedia.org", "wiki", "A");
	}
	@Test  public void Site__parts__3() {
		tstr.Run_parse("https://en.wikipedia.org").Chk_protocol_tid(Gfo_protocol_itm.Tid_https).Chk_protocol_bry("https://").Chk_segs("en.wikipedia.org");
	}
	@Test  public void Site__parts__2() {
		tstr.Run_parse("https://wikipedia.org").Chk_protocol_tid(Gfo_protocol_itm.Tid_https).Chk_segs("wikipedia.org");
	}
	@Test  public void Site__parts__1() {
		tstr.Run_parse("https://wikipedia").Chk_protocol_tid(Gfo_protocol_itm.Tid_https).Chk_segs("wikipedia");
	}
	@Test   public void Site__slash__none() {
		tstr.Run_parse("https:site").Chk_protocol_tid(Gfo_protocol_itm.Tid_https).Chk_site("site");
	}
	@Test  public void Paths__1() {
		tstr.Run_parse("https://site/A").Chk_segs("site", "A");
	}
	@Test  public void Paths__2() {
		tstr.Run_parse("https://site/wiki/A").Chk_segs("site", "wiki", "A");
	}
	@Test  public void Paths__n() {
		tstr.Run_parse("https://site/wiki/A/B/C/D").Chk_segs("site", "wiki", "A", "B", "C", "D");
	}
	@Test  public void Qargs__1() {
		tstr.Run_parse("https://site/A?B=C").Chk_page("A").Chk_qargs("B", "C");
	}
	@Test  public void Qargs__2() {
		tstr.Run_parse("https://site/A?B=C&D=E").Chk_page("A").Chk_qargs("B", "C", "D", "E");
	}
	@Test  public void Qargs__3() {
		tstr.Run_parse("https://site/A?B=C&D=E&F=G").Chk_page("A").Chk_qargs("B", "C", "D", "E", "F", "G");
	}
	@Test  public void Qargs__ques__dupe__ques() {
		tstr.Run_parse("https://site/A?B?Y=Z").Chk_page("A?B").Chk_qargs("Y", "Z");
	}
	@Test  public void Qargs__ques__dupe__amp() {
		tstr.Run_parse("https://site/A?B=C&D?Y=Z").Chk_page("A?B=C&D").Chk_qargs("Y", "Z");
	}
	@Test  public void Qargs__ques__dupe__eq() {
		tstr.Run_parse("https://site/A?B=C?Y=Z").Chk_page("A?B=C").Chk_qargs("Y", "Z");
	}
	@Test  public void Qargs__amp__dupe__ques() {
		tstr.Run_parse("https://site/A?B&Y=Z").Chk_page("A").Chk_qargs("B", null, "Y", "Z");
	}
	@Test  public void Qargs__amp__dupe__amp() {
		tstr.Run_parse("https://site/A?B=C&D&Y=Z").Chk_page("A").Chk_qargs("B", "C", "D", null, "Y", "Z");
	}
	@Test  public void Qargs__missing_val__0() {
		tstr.Run_parse("https://site/A?").Chk_page("A?").Chk_qargs();
	}
	@Test  public void Qargs__missing_val__2() {
		tstr.Run_parse("https://site/A?B=C&D&F=G").Chk_page("A").Chk_qargs("B", "C", "D", null, "F", "G");
	}
	@Test  public void Qargs__missing_val__n() {
		tstr.Run_parse("https://site/A?B=C&D=E&F").Chk_page("A").Chk_qargs("B", "C", "D", "E", "F", null);
	}
	@Test  public void Qargs__site_less__missing__0() {
		tstr.Run_parse("A?B").Chk_segs("A?B").Chk_qargs();
	}
	@Test  public void Qargs__site_less() {
		tstr.Run_parse("A?B=C&D=E").Chk_site("A").Chk_qargs("B", "C", "D", "E");
	}
	@Test  public void Anch__basic() {
		tstr.Run_parse("https://site/A#B").Chk_page("A").Chk_anch("B");
	}
	@Test  public void Anch__repeat__2() {
		tstr.Run_parse("https://site/A#B#C").Chk_page("A").Chk_anch("B#C");
	}
	@Test  public void Anch__repeat__3() {
		tstr.Run_parse("https://site/A#B#C#D").Chk_page("A").Chk_anch("B#C#D");
	}
	@Test  public void Anch__missing() {
		tstr.Run_parse("https://site/A#").Chk_page("A#").Chk_anch(null);
	}
	@Test  public void Anch__missing__eos() {
		tstr.Run_parse("https://site/A#B#").Chk_page("A").Chk_anch("B#");
	}
	@Test  public void Anch__qargs__basic() {
		tstr.Run_parse("https://site/A?B=C&D=E#F").Chk_page("A").Chk_qargs("B", "C", "D", "E").Chk_anch("F");
	}
	@Test  public void Anch__qargs__repeat() {
		tstr.Run_parse("https://site/A?B=C#&D=E#F").Chk_page("A").Chk_qargs("B", "C#", "D", "E").Chk_anch("F");
	}
	@Test  public void Anch__site_less() {
		tstr.Run_parse("A#B").Chk_site("A").Chk_anch("B");
	}
	@Test  public void Encode__page() {
		tstr.Run_parse("http://site/A%27s").Chk_site("site").Chk_page("A's");
	}
	@Test  public void Protocol_less__qargs() {
		tstr.Run_parse("Special:Search/Earth?fulltext=yes").Chk_segs("Special:Search", "Earth").Chk_page("Earth").Chk_qargs("fulltext", "yes");
	}
	@Test  public void Parse_site_fast() {
		tstr.Test_Parse_site_fast("http://a.org/B"		, "a.org");
		tstr.Test_Parse_site_fast("http://a.org"		, "a.org");
		tstr.Test_Parse_site_fast("//a.org/B"			, "a.org");
		tstr.Test_Parse_site_fast("//a.org/B:C"			, "a.org");
	}
}
