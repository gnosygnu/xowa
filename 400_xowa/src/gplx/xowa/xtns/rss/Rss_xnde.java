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
package gplx.xowa.xtns.rss;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*;
import gplx.xowa.parsers.xndes.*;
public class Rss_xnde implements Xox_xnde {
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {}
	public void Xtn_write(BryWtr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		Xox_mgr_base.Xtn_write_unsupported(app, ctx, bfr, src, xnde, Xox_mgr_base.Parse_content_tid_html);
	}
}
