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
package gplx.xowa.htmls.core.wkrs.lnkis.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import org.junit.*; import gplx.xowa.parsers.*;
public class Xoh_lnki_title_bldr_tst {
	private final    Xoh_lnki_title_bldr_fxt fxt = new Xoh_lnki_title_bldr_fxt();
	@Test   public void Basic() {
		fxt.Test__parse("a b c", "a b c");
		fxt.Test__parse("a ''b'' c", "a b c");
		fxt.Test__parse("a <i>b</i> c", "a b c");
		fxt.Test__parse("a\nb", "a b");
		fxt.Test__parse("a\"b", "a&quot;b");
	}
	@Test   public void Lnki__quotes() {	// PURPOSE: handle titles with quotes; PAGE:s.w:Styx_(band) DATE:2015-11-29
		fxt.Test__parse("[[A\"B]]", "A&quot;B");
	}
}
class Xoh_lnki_title_bldr_fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public void Test__parse(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = fxt.Ctx().Tkn_mkr().Root(raw_bry);
		fxt.Parser().Parse_page_all_clear(root, fxt.Ctx(), fxt.Ctx().Tkn_mkr(), raw_bry);
		Xoh_lnki_title_bldr.Add(bfr, raw_bry, root);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
