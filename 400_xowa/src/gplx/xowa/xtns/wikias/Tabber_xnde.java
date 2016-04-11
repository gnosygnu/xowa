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
package gplx.xowa.xtns.wikias; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.htmls.*; import gplx.langs.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class Tabber_xnde implements Xox_xnde {
	private static final    byte[] Spr__tab_itms = Bry_.new_a7("|-|");
	private byte[] html;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);

		Bry_bfr tmp_bfr = Bry_bfr.new_();
		tmp_bfr.Add_str_a7("<div id=\"tabber-\" ");	// TODO: add key which is md5 of inner src
		tmp_bfr.Add_str_a7("class=\"tabber\">\n");

		// split on "|-|"; EX: "A|-|B" -> tab_1='A'; tab_2='B'
		byte[][] tab_itms = Bry_split_.Split(src, xnde.Tag_open_end(), xnde.Tag_close_bgn(), Spr__tab_itms);
		for (int i = 0; i < tab_itms.length; ++i) {
			byte[] tab_itm = tab_itms[i];
			tab_itm = Bry_.Trim(tab_itm);
			int tab_itm_len = tab_itm.length; if (tab_itm_len == 0) continue;

			// split on "="; EX: A=B -> tab_name='A'; tab_body = 'B'
			byte[][] tab_parts = Bry_split_.Split(tab_itm, Byte_ascii.Eq);
			byte[] tab_head = tab_parts[0];
			byte[] tab_body = tab_parts.length == 1 ? Bry_.Empty : Xop_parser_.Parse_text_to_html(wiki, ctx.Page(), ctx.Page().Ttl(), tab_parts[1], false);

			tmp_bfr.Add_str_a7("<div class=\"tabbertab\" title=\"");
			tmp_bfr.Add(Gfh_utl.Escape_html_as_bry(tab_head));
			tmp_bfr.Add_str_a7("\">\n");
			tmp_bfr.Add(Gfh_tag_.P_lhs);
			tmp_bfr.Add(tab_body);
			tmp_bfr.Add(Gfh_tag_.P_rhs).Add_byte_nl();
			tmp_bfr.Add(Gfh_tag_.Div_rhs).Add_byte_nl();
		}
		tmp_bfr.Add(Gfh_tag_.Div_rhs);
		html = tmp_bfr.To_bry_and_clear();

		ctx.Page().Html_data().Head_mgr().Itm__tabber().Enabled_y_();
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		if (html != null) bfr.Add(html);
	}
}
