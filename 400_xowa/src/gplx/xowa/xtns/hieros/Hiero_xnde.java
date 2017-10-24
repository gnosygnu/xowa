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
package gplx.xowa.xtns.hieros; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Hiero_xnde implements Xox_xnde {
	private Hiero_xtn_mgr xtn_mgr;
	private Hiero_block[] blocks;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		xtn_mgr = (Hiero_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Hiero_xtn_mgr.Xtn_key_static);
		xtn_mgr.Xtn_init_assert(wiki);
		ctx.Page().Html_data().Head_mgr().Itm__hiero().Enabled_y_();
		blocks = xtn_mgr.Parser().Parse(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_hiero, src, xnde);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
		switch (ctx.Cur_tkn_tid()) {
			case Xop_tkn_itm_.Tid_list:	// NOTE: if inside list, do not reenable para mode; questionable logic; PAGE:de.d:Damascus;DATE:2014-06-06
				break;
			default:
				ctx.Para().Process_nl(ctx, root, src, xnde.Tag_close_end(), xnde.Tag_close_end());	// NOTE: this should create an extra stub "p" so that remaining text gets enclosed in <p>; EX:w:Hieroglyphics;
				break;
		}
	}
	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		xtn_mgr.Html_wtr().Render_blocks(bfr, hctx, blocks, Hiero_html_mgr.scale, false);
	}
}
