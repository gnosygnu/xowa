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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*;
public class Xoh_lnki_hzip__ns__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
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
		fxt.Test__bicode("~${3h)Image~A%C3%BC.png~b~Image:Aü.png~", "<a href='/wiki/Image:A%C3%BC.png' title='Image:Aü.png'>b</a>");
	}
	@Test   public void Ctg__main() {		// links at bottom of pages in main ns; DATE:2015-12-28
		fxt.Test__bicode("~$|%\"(1A~", "<a href='/wiki/Category:A' class='inte" + "rnal' title='A'>A</a>");
		fxt.Test__decode("~$|$t'1A~", "<a href='/wiki/Category:A' class='inte" + "rnal' title='A'>A</a>");	// NOTE:backward compatibility for en.w:2015-12; delete after 2016-01 is uploaded
	}
	@Test   public void Ctg__tree() {		// links on Category pages;
		fxt.Test__bicode("~$|&3J1A~", "<a href='/wiki/Category:A' class='CategoryTreeLabel CategoryTreeLabelNs14 CategoryTreeLabelCategory'>A</a>");
	}
	@Test   public void Ctg__xnav__space() {	// previous / next 200 links on Category pages
		fxt.Test__bicode("~$|&`Z1A B?pageuntil=C, D#mw-pages~previous 200~Category:A_B~", "<a href='/wiki/Category:A_B?pageuntil=C, D#mw-pages' class='xowa_nav' title='Category:A_B'>previous 200</a>");
	}
	@Test   public void Ctg__xnav__under() {	// previous / next 200 links on Category pages; PAGE:en.w:Category:Public_transit_articles_with_unsupported_infobox_fields; DATE:2016-01-14
		fxt.Test__bicode("~$|&`Z1A B?pageuntil=C,_D#mw-pages~previous 200~Category:A_B~", "<a href='/wiki/Category:A_B?pageuntil=C,_D#mw-pages' class='xowa_nav' title='Category:A_B'>previous 200</a>");
	}
	@Test   public void Outlier__title_wo_ns() {// should not happen, but handle situation wherein title doesnot have ns PAGE:en.b:Wikibooks:WikiProject DATE:2016-01-20
		fxt.Test__bicode("~${Tr/A B~", "<a href='/wiki/Help:A_B' title='A B'>A B</a>");
	}
}
