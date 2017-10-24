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
package gplx.xowa.htmls.core.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.htmls.core.wkrs.*;
public class Xoh_make_mgr {
	private final    Xoh_hzip_bfr bfr = Xoh_hzip_bfr.New_txt(255);
	private final    Xoh_hdoc_ctx hctx = new Xoh_hdoc_ctx();
	private final    Xoh_hdoc_parser make_parser = new Xoh_hdoc_parser(new Xoh_hdoc_wkr__make());
	public byte[] Parse(byte[] src, Xoh_page hpg, Xow_wiki wiki) {
		hctx.Init_by_page(wiki, hpg);
		hpg.Section_mgr().Add(0, 2, Bry_.Empty, Bry_.Empty).Content_bgn_(0);	// +1 to skip \n
		make_parser.Parse(bfr, hpg, hctx, src);
		hpg.Section_mgr().Set_content(hpg.Section_mgr().Len() - 1, src, src.length);
		return bfr.To_bry_and_clear();
	}
}
