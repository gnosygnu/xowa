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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.parsers.miscs.*;
public class Xop_vnt_parser__tkn__macro__tst {
	private final Xop_vnt_lxr_fxt fxt = new Xop_vnt_lxr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Basic() {
		fxt.Test_parse("-{H|A1=>zh-hans:B1}-", fxt.vnt_().Flags_codes_("H")
		.Rule_("A1", "zh-hans", "B1"));
	}
	@Test  public void Many() {
		fxt.Test_parse("-{H|A1=>zh-hans:B1;A2=>zh-hant:B2;A3=>zh-cn:B3;}-", fxt.vnt_().Flags_codes_("H")
		.Rule_("A1", "zh-hans", "B1")
		.Rule_("A2", "zh-hant", "B2")
		.Rule_("A3", "zh-cn"  , "B3")
		);
	}
	@Test  public void Mixed() {
		fxt.Test_parse("-{H|A1=>zh-hans:B1;zh-hant:B2;A3=>zh-cn:B3}-"
		, fxt.vnt_().Flags_codes_("H")
		.Rule_("A1"	, "zh-hans"	, "B1")
		.Rule_(		  "zh-hant"	, "B2")
		.Rule_("A3",  "zh-cn"	, "B3")
		);
	}
}
