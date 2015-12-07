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
import org.junit.*;
public class Xoh_lnki_hzip__site__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Basic() {				// EX: [[wikt:A]]
		fxt.Test__bicode("~${$3en.wiktionary.org~A~wikt:A~"					, "<a href='/site/en.wiktionary.org/wiki/A' title='wikt:A'>wikt:A</a>");
	}
	@Test   public void Capt__lower() {			// EX: [[wikt:A|a]]
		fxt.Test__bicode("~$5en.wiktionary.org~A~"							, "<a href='/site/en.wiktionary.org/wiki/A' title='A'>a</a>");
	}
	@Test   public void Capt__upper() {			// EX: [[wikt:a|A]]
		fxt.Test__bicode("~$9en.wiktionary.org~a~"							, "<a href='/site/en.wiktionary.org/wiki/a' title='a'>A</a>");
	}
	@Test   public void Ns__href() {			// EX: [[wikt:help:a]]
		fxt.Test__bicode("~${a2en.wiktionary.org~/help~a~wikt:help:a~"		, "<a href='/site/en.wiktionary.org/wiki/help:a' title='wikt:help:a'>wikt:help:a</a>");
	}
	@Test   public void Ns__capt() {			// EX: [[wikt:help:a|b]]
		fxt.Test__bicode("~${d3en.wiktionary.org~/help~a~b~wikt:help:a~"	, "<a href='/site/en.wiktionary.org/wiki/help:a' title='wikt:help:a'>b</a>");	// MW: also adds class="extiw"
	}
	@Test   public void Ns__anch() {			// EX: [[wikt:Help:A#b]]
		fxt.Test__bicode("~${'sen.wiktionary.org~/A#b~Help:A~"				, "<a href='/site/en.wiktionary.org/wiki/Help:A#b' title='Help:A'>A#b</a>");
	}
	@Test   public void Ns__more() {			// EX: [[wikt:Help:A]]b
		fxt.Test__bicode("~${#Ien.wiktionary.org~/A~b~"						, "<a href='/site/en.wiktionary.org/wiki/Help:A' title='Help:A'>Help:Ab</a>");
	}
	@Test   public void Ns__more__name() {		// EX: [[wikt:Help:A|Ab|]]
		fxt.Test__bicode("~$sen.wiktionary.org~/A~b~"						, "<a href='/site/en.wiktionary.org/wiki/Help:A' title='Help:A'>Ab</a>");
	}
	@Test   public void Ns__url_encoding() {	// EX: [[wikt:Category:A & B|]]
		fxt.Test__bicode("~${$sen.wiktionary.org~1A & B~A &amp; B~"			, "<a href='/site/en.wiktionary.org/wiki/Category:A_%26_B' title='Category:A &amp; B'>A &amp; B</a>");
	}
	@Test   public void Less__eq() {			// EX: [[wikt:Ab|A]]
		fxt.Test__bicode("~${*7en.wiktionary.org~A~b~"						, "<a href='/site/en.wiktionary.org/wiki/Ab'>A</a>");
	}
	@Test   public void Less__lo() {			// EX: [[wikt:Ab|a]]
		fxt.Test__bicode("~$8en.wiktionary.org~A~b~"						, "<a href='/site/en.wiktionary.org/wiki/Ab' title='Ab'>a</a>");
	}
	@Test   public void Less__hi() {			// EX: [[wikt:ab|A]]
		fxt.Test__bicode("~$<en.wiktionary.org~a~b~"						, "<a href='/site/en.wiktionary.org/wiki/ab' title='ab'>A</a>");
	}
	@Test   public void More__hi() {			// EX: [[wikt:a|Ab]]
		fxt.Test__bicode("~$;en.wiktionary.org~a~b~"						, "<a href='/site/en.wiktionary.org/wiki/a' title='a'>Ab</a>");
	}
	@Test   public void Encode__lnki() {		// EX: [[wikt:eorðe|eorðe]]
		fxt.Test__bicode("~$1en.wiktionary.org~eorðe~"						, "<a href='/site/en.wiktionary.org/wiki/eor%C3%B0e' title='eorðe'>eorðe</a>");
	}
//		@Test   public void Encode__lnke() {		// EX: [//en.wiktionary.org/wiki/eorðe eorðe]; NOTE:MW inconsistently does not URL-encode external links (but does URL-encode @gplx.Internal protected ones)
//			fxt.Test__bicode("~$)en.wiktionary.org~eorðe~"						, "<a href='/site/en.wiktionary.org/wiki/eorðe'>eorðe</a>");
//		}
	@Test   public void Lnke__ns() {
		fxt.Test__bicode("~$qen.wiktionary.org~/a~"							, "<a href='/site/en.wiktionary.org/wiki/Help:a' title='Help:a'>a</a>");
	}
	@Test   public void Qarg_lnke() {			// EX: [//en.wiktionary.org/wiki/A?b=c d]
		fxt.Test__bicode("~${*5en.wiktionary.org~A?b=c~d~"					, "<a href='/site/en.wiktionary.org/wiki/A?b=c'>d</a>");
	}
	@Test   public void Qarg_lnki() {			// EX: [[wikt:A?b=c|d]]
		fxt.Test__bicode("~$2en.wiktionary.org~A?b=c~d~"					, "<a href='/site/en.wiktionary.org/wiki/A?b=c' title='A?b=c'>d</a>");	// NOTE: mw encodes as A%3Fb%3Dc
	}
	@Test   public void Main_page() {			// EX: [[wikt:]]
		fxt.Test__bicode("~${<<en.wiktionary.org~~wikt:~"					, "<a href='/site/en.wiktionary.org/wiki/' title='wikt:'>wikt:</a>");
	}
}
