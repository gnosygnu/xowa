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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Hiero_parser_tst {
	@Before public void init() {fxt.Reset();} private Hiero_parser_fxt fxt = new Hiero_parser_fxt();
	@Test  public void Separator() {
		String[][] expd = new String[][] {fxt.block_("A1"), fxt.block_("B1")};
		fxt.Test_parse("A1 B1"			, expd);	// space
		fxt.Test_parse("A1-B1"			, expd);	// dash
		fxt.Test_parse("A1\tB1"			, expd);	// tab
		fxt.Test_parse("A1\nB1"			, expd);	// nl
		fxt.Test_parse("A1\rB1"			, expd);	// cr
		fxt.Test_parse("A1  B1"			, expd);	// many: space
		fxt.Test_parse("A1 \t\nB1"		, expd);	// many: mixed
	}
}
class Hiero_parser_fxt {
	private Hiero_parser parser;
	public void Reset() {
		parser = new Hiero_parser();
		parser.Init();
	}
	public String[] block_(String... v) {return v;}
	public void Test_parse(String raw, String[]... expd) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Hiero_block[] actl = parser.Parse(raw_bry, 0, raw_bry.length);
		Tfds.Eq_ary(String_.Ary_flatten(expd), String_.Ary_flatten(Xto_str(actl)));
	}
	private String[][] Xto_str(Hiero_block[] ary) {
		int len = ary.length;
		String[][] rv = new String[len][];
		for (int i = 0; i < len; i++) {
			Hiero_block itm = ary[i];
			int itm_len = itm.Len();
			String[] rv_sub = new String[itm_len];
			rv[i] = rv_sub;
			for (int j = 0; j < itm_len; j++) {
				rv_sub[j] = String_.new_u8(itm.Get_at(j));
			}
		}
		return rv;
	}
}
