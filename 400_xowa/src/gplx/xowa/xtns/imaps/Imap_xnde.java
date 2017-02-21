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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.xndes.*;
public class Imap_xnde implements Xox_xnde {
	private Imap_xtn_mgr xtn_mgr;
	private Imap_map imap_data;
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		// init
		xtn_mgr = wiki.Xtn_mgr().Xtn_imap().Xtn_assert();
		Xoae_page page = ctx.Page();
		page.Html_data().Xtn_imap_exists_y_();
		page.Html_data().Head_mgr().Itm__popups().Bind_hover_area_(true);

		// parse imap
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
		imap_data = xtn_mgr.Parser().Parse(wiki, ctx, root, src, xnde);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);			
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_imageMap, src, xnde);
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;

	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (imap_data.Invalid()) return;
		html_wtr.Write_tkn_to_html(bfr, ctx, hctx, imap_data.Img_src(), xnde, Xoh_html_wtr.Sub_idx_null, imap_data.Img().Img_link());
	}
}
