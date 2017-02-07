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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xol_vnt_regy_tst {
	private final Xol_vnt_regy_fxt fxt = new Xol_vnt_regy_fxt();
	@Test  public void Calc() {
		fxt.Test_calc(String_.Ary("zh")				, 1);
		fxt.Test_calc(String_.Ary("zh", "zh-hans")	, 3);
		fxt.Test_calc(String_.Ary("zh", "bad")		, 1);
	}
	@Test  public void Match() {
		String[] lang_chain = fxt.Make_lang_chain_cn();	// zh;zh-hans;zh-hant;zh-cn
		fxt.Test_match_any(Bool_.Y, lang_chain
		, String_.Ary("zh")
		, String_.Ary("zh-hans")
		, String_.Ary("zh-hant")
		, String_.Ary("zh-cn")
		, String_.Ary("zh", "zh-hans")
		, String_.Ary("zh-cn", "zh-hk")
		);
		fxt.Test_match_any(Bool_.N, lang_chain
		, String_.Ary_empty
		, String_.Ary("bad")
		, String_.Ary("zh-hk")
		, String_.Ary("zh-hk", "zh-sg")
		);
	}
	@Test   public void Match_2() {
		fxt.Test_match_any(Bool_.Y, String_.Ary("zh-hans")
		, String_.Ary("zh", "zh-hant", "zh-hans")
		);
	}
}
