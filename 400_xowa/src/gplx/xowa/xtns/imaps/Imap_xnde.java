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
package gplx.xowa.xtns.imaps; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*; import gplx.xowa.parsers.logs.*;
public class Imap_xnde implements Xox_xnde {
	private Imap_xtn_mgr xtn_mgr;
	private Imap_map imap_data;
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		xtn_mgr = wiki.Xtn_mgr().Xtn_imap();
		xtn_mgr.Xtn_assert();
		Xoa_page page = ctx.Cur_page();
		page.Html_data().Module_mgr().Itm_popups().Bind_hover_area_(true);
		page.Html_data().Xtn_imap_exists_y_();
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
		imap_data = xtn_mgr.Parser().Parse(wiki, ctx, root, src, xnde);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);			
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Cur_page(), Xop_log_basic_wkr.Tid_imageMap, src, xnde);
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	public void Xtn_write(Xoa_app app, Xoh_html_wtr html_wtr, Xoh_html_wtr_ctx opts, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		html_wtr.Write_tkn(bfr, ctx, opts, imap_data.Img_src(), xnde, Xoh_html_wtr.Sub_idx_null, imap_data.Img().Img_link());
	}
}
