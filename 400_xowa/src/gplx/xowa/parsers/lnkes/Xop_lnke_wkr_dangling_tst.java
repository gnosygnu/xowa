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
package gplx.xowa.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_lnke_wkr_dangling_tst {
	@Before public void init() {fxt.Reset();} private Xop_fxt fxt = new Xop_fxt();
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