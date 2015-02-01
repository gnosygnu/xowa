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
package gplx.xowa.xtns.indicators; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.xowa.html.*; import gplx.xowa.pages.skins.*;
public class Indicator_xnde implements Xox_xnde, Xop_xnde_atr_parser {
	public byte[] Name() {return name;} private byte[] name;
	public byte[] Html() {return html;} private byte[] html;
	public void Init_for_test(byte[] name, byte[] html) {this.name = name; this.html = html;}	// TEST
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_key_obj) {
		if (xatr_key_obj == null) return;
		Byte_obj_val xatr_key = (Byte_obj_val)xatr_key_obj;
		switch (xatr_key.Val()) {
			case Xatr_name:		name = xatr.Val_as_bry(src); break;
		}
	}
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xop_xatr_itm.Xatr_parse(wiki.App(), this, xatrs_hash, wiki, src, xnde);
		html = Xop_parser_.Parse_text_to_html(wiki, ctx.Cur_page().Ttl(), Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn()), false);
		Indicator_html_bldr html_bldr = ctx.Cur_page().Html_data().Indicators_or_new();
		html_bldr.Add(this);
	}
	public void Xtn_write(Bry_bfr bfr, Xoa_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
	}
	private static final byte Xatr_name = 0;
	private static final Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_ascii_()
	.Add_str_obj("name", Byte_obj_val.new_(Xatr_name));
}
