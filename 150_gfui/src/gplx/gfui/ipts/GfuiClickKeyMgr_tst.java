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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import org.junit.*; import gplx.gfui.ipts.*; import gplx.gfui.controls.windows.*;
public class GfuiClickKeyMgr_tst {
	@Test  public void ExtractKeyFromText() {
		tst_ExtractKey("&click", IptKey_.C);
		tst_ExtractKey("&", IptKey_.None);
		tst_ExtractKey("trailing &", IptKey_.None);
		tst_ExtractKey("me & you", IptKey_.None);
	}
	void tst_ExtractKey(String text, IptKey expd) {
		IptKey actl = GfuiWinKeyCmdMgr.ExtractKeyFromText(text);
		Tfds.Eq(expd, actl);
	}
}	
