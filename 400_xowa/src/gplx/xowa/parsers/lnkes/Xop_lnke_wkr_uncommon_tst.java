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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_lnke_wkr_uncommon_tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Err_multiple() {
		fxt.Test_parse_page_wiki("[irc://a][irc://b]"
			, fxt.tkn_lnke_(0,  9)
			, fxt.tkn_lnke_(9, 18)
			);
	}
	@Test  public void Err_txt_is_protocol() {
		fxt.Test_parse_page_wiki("[irc://a irc://b]"
			, fxt.tkn_lnke_(0, 17).Lnke_rng_(1, 8).Subs_(fxt.tkn_txt_(9, 16))
			);
	}
	@Test  public void Lnke_should_precede_lnki() { // PURPOSE: [[ should not be interpreted as lnki if [irc is available
		fxt.Test_parse_page_wiki("[[irc://a/b c]]"
			,	fxt.tkn_txt_(0, 1)
			,	fxt.tkn_lnke_(1, 14).Subs_
				(	fxt.tkn_txt_(12, 13)
				)
			,	fxt.tkn_txt_(14, 15)
			);
	}
	@Test  public void Defect_2nd_consecutive_lnke() {	// PURPOSE: bad code that was causing lnkes to show up; PAGE:en.w:Template:Infobox_country;
		fxt.Test_parse_page_wiki_str(String_.Concat_lines_nl_skip_last
		(	"[[http://a.org a]] [[http://b.org b]]"
		), String_.Concat_lines_nl_skip_last
		(	"[<a href=\"http://a.org\" rel=\"nofollow\" class=\"external text\">a</a>] [<a href=\"http://b.org\" rel=\"nofollow\" class=\"external text\">b</a>]"
		));
	}
}
