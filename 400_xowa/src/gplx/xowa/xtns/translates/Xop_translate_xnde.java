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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class Xop_translate_xnde implements Xox_xnde, Xop_xnde_atr_parser {
	public Xop_root_tkn Xtn_root() {return xtn_root;} private Xop_root_tkn xtn_root;
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_obj) {}
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		byte[] translate_src = Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
		translate_src = Bry_.Trim(translate_src, 0, translate_src.length);
		Xop_ctx sub_ctx = Xop_ctx.new_sub_(wiki);
		xtn_root = wiki.Parser().Parse_text_to_wdom_old_ctx(sub_ctx, translate_src, true);
	}
	public void Xtn_write(Xoa_app app, Xoh_html_wtr html_wtr, Xoh_wtr_ctx opts, Xop_ctx ctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		html_wtr.Write_tkn(bfr, ctx, opts, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
	}
}
