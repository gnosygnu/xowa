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
public class Xop_lnke_wkr_dangling_tst {
	@Before public void init() {fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Dangling_eos() {
		fxt.Test_parse_page_wiki("[irc://a b"
			,	fxt.tkn_lnke_(0, 8).Lnke_typ_(Xop_lnke_tkn.Lnke_typ_brack_dangling)
			,	fxt.tkn_txt_(9, 10)
			);
	}
	@Test  public void Dangling_newLine() {
		fxt.Test_parse_page_wiki("[irc://a b\nc]"
			,	fxt.tkn_lnke_(0, 8).Lnke_typ_(Xop_lnke_tkn.Lnke_typ_brack_dangling)
			,	fxt.tkn_txt_(9, 10)
			,	fxt.tkn_nl_char_len1_(10)
			,	fxt.tkn_txt_(11, 13)
			);
	}
	@Test  public void Dangling_gt() {
		fxt.Test_parse_page_wiki("[irc://a>b c]", fxt.tkn_lnke_(0, 13).Lnke_typ_(Xop_lnke_tkn.Lnke_typ_brack).Subs_(fxt.tkn_txt_(8, 10), fxt.tkn_space_(10, 11), fxt.tkn_txt_(11, 12)));
	}
}
