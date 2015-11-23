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
import org.junit.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_lnki_hzip_tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt();
	@Test   public void Href__basic() {
		fxt.Test__bicode("~$!A~", Xoh_lnki_html__hdump__tst.Html__same);
	}
	@Test   public void Href__case_diff() {
		fxt.Test__bicode("~$!a~", "<a href='/wiki/A' id='xolnki_2' title='A'>a</a>");
	}
	@Test   public void Href__url_encoded() {
		String html = "<a href=\"/wiki/A%27s\" id=\"xolnki_2\" title=\"A's\">A's</a>";
		fxt.Test__bicode_raw("~$!A's~", html, html);
	}
	@Test   public void Ns__same() {	// EX: [[Help:A]]
		fxt.Test__bicode("~$A/A~", "<a href='/wiki/Help:A' id='xolnki_2' title='Help:A'>Help:A</a>");
	}
	@Test   public void Ns__diff() {	// EX: [[Help:A_b|c]]
		fxt.Test__bicode("~$B/A_b~c~", "<a href='/wiki/Help:A_b' id='xolnki_2' title='Help:A b'>c</a>");
	}
	@Test   public void Ns__space() {	// EX: [[Help talk:A b]]
		fxt.Test__bicode("~$A0A b~", "<a href='/wiki/Help_talk:A_b' id='xolnki_2' title='Help talk:A b'>Help talk:A b</a>");
	}
	@Test   public void Ns__under() {	// EX: [[Help_talk:A_b]]; rare; just make sure codec can handle it; 
		fxt.Test__bicode("~$B0A_b~Help_talk:A_b~", "<a href='/wiki/Help_talk:A_b' id='xolnki_2' title='Help talk:A b'>Help_talk:A_b</a>");
	}
	@Test   public void Ns__pipe() {	// EX: [[Help:A|]]
		fxt.Test__bicode("~$E/A~", "<a href='/wiki/Help:A' id='xolnki_2' title='Help:A'>A</a>");
	}
	@Test   public void Ns__pipe_w_words() {	// EX: [[Help:A b|]]
		fxt.Test__bicode("~$E/A b~", "<a href='/wiki/Help:A_b' id='xolnki_2' title='Help:A b'>A b</a>");
	}
	@Test   public void Anch__same() {
		fxt.Test__bicode("~$2a~#a~", "<a href='#a' id='xolnki_2'>#a</a>");
	}
	@Test   public void Anch__diff() {
		fxt.Test__bicode("~$2a~b~", "<a href='#a' id='xolnki_2'>b</a>");
	}
	@Test   public void Anch__diff__starts_w_same() {
		fxt.Test__bicode("~$2a~a~", "<a href='#a' id='xolnki_2'>a</a>");
	}
	@Test   public void Capt__basic() {	// EX: [[A|b]]
		fxt.Test__bicode("~$\"A~b~", Xoh_lnki_html__hdump__tst.Html__diff);
	}
	@Test   public void Capt__page_w_anch() {	// Ex: [[A#b|c]]
		fxt.Test__bicode("~$\"A#b~b~", "<a href='/wiki/A#b' id='xolnki_2' title='A'>b</a>");
	}
	@Test   public void Capt__nest() {
		fxt.Test__bicode
		( "~$\"A~<a href=\"/wiki/C\" id=\"xolnki_3\" title=\"C\">C1</a>D~"
		, "<a href=\"/wiki/A\" id=\"xolnki_2\" title=\"A\"><a href=\"/wiki/C\" id=\"xolnki_3\" title=\"C\">C1</a>D</a>"
		);
	}
	@Test   public void Capt__reparent() {	// PURPOSE: PAGE:en.w:Abyssal_plain; DATE:2015-06-02; DELETE: not needed in new dump format;
		fxt.Test__bicode
		( "$\"A<font color=\"white\">A1</font>"
		, "<a href=\"/wiki/A\" id=\"xolnki_2\" title=\"A\"><font color='white'>A1</font></a>"
		);
	}
	@Test   public void Capt__xwiki() {
		Xow_wiki wiki = fxt.Prep_create_wiki("wikt", "en.wiktionary.org");
		wiki.Ns_mgr().Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__bicode("$*en.wiktionary.orgawikt:a", Xoh_lnki_html__hdump__tst.Html__xwiki);
	}
	@Test   public void Capt__xwiki__qarg() {
		Xow_wiki wiki = fxt.Prep_create_wiki("wikt", "en.wiktionary.org");
		wiki.Ns_mgr().Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__bicode("$*en.wiktionary.orga?action=editwikt:a?action=edit", "<a href='/site/en.wiktionary.org/wiki/a?action=edit' id='xolnki_2' title='a?action=edit'>wikt:a?action=edit</a>");
	}
	@Test   public void Capt__xwiki__encode() {
		Xow_wiki wiki = fxt.Prep_create_wiki("wikt", "en.wiktionary.org");
		wiki.Ns_mgr().Ns_main().Case_match_(gplx.xowa.wikis.nss.Xow_ns_case_.Tid__all);
		fxt.Test__bicode("$)en.wiktionary.orgeorðe", "<a href='/site/en.wiktionary.org/wiki/eor%C3%B0e' id='xolnki_2' title='eorðe'>eorðe</a>");
	}
	@Test   public void Trail__basic() {
		fxt.Test__bicode("~$#A~b~", Xoh_lnki_html__hdump__tst.Html__trail);
	}
	@Test   public void Short__basic() {
		fxt.Test__bicode("~$$A~b~", "<a href='/wiki/Ab' id='xolnki_2' title='Ab'>A</a>");
	}
	@Test   public void Short__case() {
		fxt.Test__bicode("~$$a~b~", "<a href='/wiki/Ab' id='xolnki_2' title='Ab'>a</a>");
	}
	@Test   public void Site__main_page() {
		fxt.Test__bicode("~$)en.wikipedia.org~Main Page~"
		, "<a href='/site/en.wikipedia.org/wiki/' id='xolnki_2' title='Main Page'>Main Page</a>"
		, "<a href='/site/en.wikipedia.org/wiki/Main_Page' id='xolnki_2' title='Main Page'>Main Page</a>"
		);
	}
	@Test   public void Site__qarg() {
		fxt.Test__bicode("~$*en.wikipedia.org~A?b=c~d~", "<a href='/site/en.wikipedia.org/wiki/A?b=c' id='xolnki_2' title='A?b=c'>d</a>");
	}
	@Test   public void Inet__file() {
		fxt.Test__bicode("~$:file:///C://A.png~b~", "<a href='file:///C://A.png' id='xolnki_2' title='file:///C://A.png'>b</a>");
	}
}
