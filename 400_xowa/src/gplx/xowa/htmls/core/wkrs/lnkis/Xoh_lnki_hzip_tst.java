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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.tests.*;
public class Xoh_lnki_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Noop() {
		String html = "<a href='/wiki/A'>A";
		fxt.Test__bicode(html, html);
	}
	@Test   public void Lnki__basic() {
		fxt.Test__bicode("~$0!#A~", Xoh_lnki_html__hdump__tst.Html__same);
	}
	@Test   public void Lnki__alt_case() {
		fxt.Test__bicode("~$0!#a~", "<a data-xotype='lnki0' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\">a</a>");
	}
	@Test   public void Lnki__ns() {
		fxt.Test__bicode("~$0+#A~", "<a data-xotype='lnki0' href=\"/wiki/Template:A\" id=\"xowa_lnki_2\" title=\"Template:A\">Template:A</a>");
	}
//		@Test   public void Lnki__apos() {
//			fxt.Test__bicode("~$0+#A'b~", "<a data-xotype='lnki0' href=\"/wiki/Template:A&#39;b\" id=\"xowa_lnki_2\" title=\"Template:A&#39;b\">Template:A'b</a>");
//		}
	@Test   public void Lnki__xwiki() {
		fxt.Parser_fxt().Init_xwiki_add_wiki_and_user_("wikt", "en.wiktionary.org");
		fxt.Test__bicode("~$0!#~en.wiktionary.org~wikt:A~", Xoh_lnki_html__hdump__tst.Html__xwiki);
	}
	@Test   public void Caption__basic() {
		fxt.Test__bicode("~$1!#A~b</a>", Xoh_lnki_html__hdump__tst.Html__diff);
	}
	@Test   public void Caption__nest() {
		fxt.Test__bicode("~$1!#A~~$1!#C~C1</a>D</a>", "<a data-xotype='lnki1' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\"><a data-xotype='lnki1' href=\"/wiki/C\" id=\"xowa_lnki_2\" title=\"C\">C1</a>D</a>");
	}
//		@Test   public void Caption__multiple() {	// PURPOSE: if id is missing from 1st anchor, do not get from second
//			String hzip = Xoh_hzip_fxt.Escape("$0!#A");
//			String html = "<a data-xotype='lnki0' href=\"/wiki/A\" title=\"A\">A</a><a data-xotype='lnki0' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"B\">B</a>";
//			fxt.Test__encode(hzip, html);
////			fxt.Test_save(brys, "<a xtid='a_lnki_text_n' href=\"/wiki/A\" title=\"A\">A</a><a xtid='a_lnki_text_n' href=\"/wiki/B\" id=\"xowa_lnki_3\" title=\"B\">B</a>");
////			fxt.Test_load(brys, "<a xtid='a_lnki_text_n' href=\"/wiki/A\" title=\"A\">A</a><a href='/wiki/B' id='xowa_lnki_3' title='B'>B</a>");
//		}
	@Test   public void Caption__reparent() {	// PURPOSE: PAGE:en.w:Abyssal_plain; DATE:2015-06-02
		String hzip = Xoh_hzip_fxt.Escape("~$1!#A~<font color='white'>A1</font></a>");
		fxt.Test__encode(hzip, "<a data-xotype='lnki0' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\"><font color='white'>A1</font></a>");
		fxt.Test__decode(hzip, "<a data-xotype='lnki1' href=\"/wiki/A\" id=\"xowa_lnki_2\" title=\"A\"><font color='white'>A1</font></a>");
	}
}
