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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class Math_nde implements Xox_xnde, Xop_xnde_atr_parser {
	public Xop_xnde_tkn Xnde() {throw Err_.not_implemented_();}
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_obj) {}
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xof_math_mgr math_mgr = wiki.App().File_mgr().Math_mgr();
		if (math_mgr.Enabled() && math_mgr.Renderer_is_mathjax())
			ctx.Cur_page().Html_data().Module_mgr().Itm_mathjax().Enabled_y_();
	}
	public void Xtn_write(Xoa_app app, Xoh_html_wtr html_wtr, Xoh_html_wtr_ctx hctx, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		app.File_mgr().Math_mgr().Html_wtr().Write(html_wtr, ctx, hctx, bfr, src, xnde);
	}
}
