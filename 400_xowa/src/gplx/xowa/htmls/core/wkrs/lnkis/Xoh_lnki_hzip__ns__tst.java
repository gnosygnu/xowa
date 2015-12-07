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
public class Xoh_lnki_hzip__ns__tst {
	private final Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Test   public void Ns__same() {			// EX: [[Help:A]]
		fxt.Test__bicode("~${#7/A~", "<a href='/wiki/Help:A' title='Help:A'>Help:A</a>");
	}
	@Test   public void Ns__diff() {			// EX: [[Help:A_b|c]]
		fxt.Test__bicode("~$b/A b~c~", "<a href='/wiki/Help:A_b' title='Help:A b'>c</a>");
	}
	@Test   public void Ns__more() {			// EX: [[Help:A|a b]]
		fxt.Test__bicode("~$g/A~ b~", "<a href='/wiki/Help:A' title='Help:A'>a b</a>");
	}
	@Test   public void Ns__less() {			// EX: [[Help:A_b|a]]
		fxt.Test__bicode("~$h/A~ b~", "<a href='/wiki/Help:A_b' title='Help:A b'>a</a>");
	}
	@Test   public void Ns__talk() {			// EX: [[Help talk:A b]]
		fxt.Test__bicode("~${#70A b~", "<a href='/wiki/Help_talk:A_b' title='Help talk:A b'>Help talk:A b</a>");
	}
	@Test   public void Ns__talk__diff() {		// EX: [[Help talk:A b|cde]]
		fxt.Test__bicode("~$b0A b~cde~", "<a href='/wiki/Help_talk:A_b' title='Help talk:A b'>cde</a>");
	}
	@Test   public void Ns__under() {			// EX: [[Help_talk:A_b]]; rare; just make sure codec can handle it; 
		fxt.Test__bicode("~$b0A b~Help_talk:A_b~", "<a href='/wiki/Help_talk:A_b' title='Help talk:A b'>Help_talk:A_b</a>");
	}
	@Test   public void Ns__pipe() {			// EX: [[Help:A|]]
		fxt.Test__bicode("~$a/A~", "<a href='/wiki/Help:A' title='Help:A'>A</a>");
	}
	@Test   public void Ns__pipe_w_words() {	// EX: [[Help:A b|]]
		fxt.Test__bicode("~$a/A b~", "<a href='/wiki/Help:A_b' title='Help:A b'>A b</a>");
	}
	@Test   public void Ns__anch() {			// EX: [[Help:A_b#c|a]]
		fxt.Test__bicode("~${'j/A~ b#c~Help:A b~", "<a href='/wiki/Help:A_b#c' title='Help:A b'>a</a>");
	}
	@Test   public void Ns__anch__alias() {		// EX: [[Help:A_b#c|a]]
		fxt.Test__bicode("~${3h)Image~A.png#b~c~Image:A.png~", "<a href='/wiki/Image:A.png#b' title='Image:A.png'>c</a>");
	}
	@Test   public void Fake__ns() {			// EX: [[Fake:A]]
		fxt.Test__bicode("~$!Fake:A~", "<a href='/wiki/Fake:A' title='Fake:A'>Fake:A</a>");
	}
	@Test   public void Alias__basic() {		// EX: [[Image:A]]
		fxt.Test__bicode("~${-f)Image~A~B~", "<a href='/wiki/Image:A' title='Image:A'>B</a>");
	}
	@Test   public void Alias__talk() {			// EX: [[Image talk:A]]
		fxt.Test__bicode("~${/;*Image talk~Human-woman.png~", "<a href='/wiki/Image_talk:Human-woman.png' title='Image talk:Human-woman.png'>Image talk:Human-woman.png</a>");
	}
	@Test   public void Alias__words() {		// EX: [[Image:A b]]
		fxt.Test__bicode("~${/;)Image~A b~", "<a href='/wiki/Image:A_b' title='Image:A b'>Image:A b</a>");
	}
	@Test   public void Alias__url_encoding() {	// EX: [[Image:Aü.png|b]]
		fxt.Test__bicode("~${-f)Image~Aü.png~b~", "<a href='/wiki/Image:A%C3%BC.png' title='Image:Aü.png'>b</a>");
	}
}
