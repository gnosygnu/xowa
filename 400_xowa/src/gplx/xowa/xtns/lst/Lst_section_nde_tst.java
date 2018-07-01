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
package gplx.xowa.xtns.lst; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Lst_section_nde_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		fxt.Test_parse_page_all_str("a<section name=\"b\">c</section>d", "ad");
	}
	@Test  public void German() {	// PURPOSE: non-english tags for section DATE:2014-07-18
		fxt.Lang_by_id_(Xol_lang_stub_.Id_de);
		fxt.Test_parse_page_all_str("a<abschnitt name=\"b\">c</abschnitt>d"	, "ad");		// check that German works
		fxt.Test_parse_page_all_str("a<section name=\"b\">c</section>d"		, "ad");		// check that English still works
		fxt.Test_parse_page_all_str("a<trecho name=\"b\">c</trecho>d"		, "a&lt;trecho name=&quot;b&quot;&gt;c&lt;/trecho&gt;d");		// check that Portuguese does not work
	}
}
