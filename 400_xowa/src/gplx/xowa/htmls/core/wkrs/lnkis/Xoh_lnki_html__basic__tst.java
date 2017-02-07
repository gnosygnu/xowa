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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*;
public class Xoh_lnki_html__basic__tst {
	@After public void term() {fxt.Init_para_n_(); fxt.Reset();} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Ns__title() {	// PURPOSE: title should have full ns; "title='Help talk:A'" not "title='A'" DATE:2015-11-16
		fxt.Wtr_cfg().Lnki__title_(Bool_.Y);
		fxt.Test__parse__wtxt_to_html("[[Help talk:A b]]"					, "<a href='/wiki/Help_talk:A_b' title='Help talk:A b'>Help talk:A b</a>");
		fxt.Wtr_cfg().Lnki__title_(Bool_.N);
	}
}
