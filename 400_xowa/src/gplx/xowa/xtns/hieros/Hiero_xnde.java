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
import gplx.xowa.parsers.logs.*;
import gplx.xowa.html.*;
public class Hiero_xnde implements Xox_xnde, Xop_xnde_atr_parser {
	private Hiero_xtn_mgr xtn_mgr;
	private Hiero_block[] blocks;
	public void Xatr_parse(Xowe_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_key_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		xtn_mgr = (Hiero_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Hiero_xtn_mgr.Xtn_key_static);
		xtn_mgr.Xtn_init_assert(wiki);
		ctx.Cur_page().Html_data().Module_mgr().Itm_hiero().Enabled_y_();
		blocks = xtn_mgr.Parser().Parse(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Cur_page(), Xop_log_basic_wkr.Tid_hiero, src, xnde);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
		switch (ctx.Cur_tkn_tid()) {
			case Xop_tkn_itm_.Tid_list:	// NOTE: if inside list, do not reenable para mode; questionable logic; PAGE:de.d:Damascus;DATE:2014-06-06
				break;
			default:
				ctx.Para().Process_nl(ctx, root, src, xnde.Tag_close_end(), xnde.Tag_close_end());	// NOTE: this should create an extra stub "p" so that remaining text gets enclosed in <p>; EX:w:Hieroglyphics;
				break;
		}
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		xtn_mgr.Html_wtr().Render_blocks(bfr, hctx, blocks, Hiero_html_mgr.scale, false);
	}
}
