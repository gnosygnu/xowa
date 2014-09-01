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
package gplx.xowa.xtns.templateData; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class Xtn_templateData_nde implements Xox_xnde, Xop_xnde_atr_parser {
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde;
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_obj) {}
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		this.xnde = xnde;
	}
	public void Xtn_write(Bry_bfr bfr, Xoa_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		bfr.Add(Xoh_consts.Pre_bgn_overflow);
		Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		bfr.Add(Xoh_consts.Pre_end);
	}
}
