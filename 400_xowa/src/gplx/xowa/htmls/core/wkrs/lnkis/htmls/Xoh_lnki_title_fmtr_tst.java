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
public class Xoh_lnki_title_fmtr_tst {
	@Before public void init() {fxt.Clear();} private Xoh_lnki_title_fmtr_fxt fxt = new Xoh_lnki_title_fmtr_fxt();
	@Test   public void Basic() {
		fxt.Test_parse("a b c", "a b c");
		fxt.Test_parse("a ''b'' c", "a b c");
		fxt.Test_parse("a <i>b</i> c", "a b c");
		fxt.Test_parse("a\nb", "a b");
		fxt.Test_parse("a\"b", "a&quot;b");
	}
	@Test   public void Lnki__quotes() {	// PURPOSE: handle titles with quotes; PAGE:s.w:Styx_(band) DATE:2015-11-29
		fxt.Test_parse("[[A\"B]]", "A&quot;B");
	}
}
class Xoh_lnki_title_fmtr_fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	Bry_bfr bfr = Bry_bfr_.New();
	Xoh_lnki_title_fmtr title_wkr = new Xoh_lnki_title_fmtr();
	public Xoh_lnki_title_fmtr_fxt Clear() {return this;}
	public void Test_parse(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = fxt.Ctx().Tkn_mkr().Root(raw_bry);
		fxt.Parser().Parse_page_all_clear(root, fxt.Ctx(), fxt.Ctx().Tkn_mkr(), raw_bry);
		title_wkr.Set(raw_bry, root).Bld_recurse(bfr, root);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
