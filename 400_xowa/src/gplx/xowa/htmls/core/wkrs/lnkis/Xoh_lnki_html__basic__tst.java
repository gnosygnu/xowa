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
