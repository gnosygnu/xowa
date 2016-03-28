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
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
import gplx.core.btries.*;
public class Random_selection_xnde implements Xox_xnde {
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
//			Btrie_slim_mgr sub_xnde_trie = Btrie_slim_mgr.cs(); sub_xnde_trie.Add_obj("<option", 1).Add_obj("<choicetemplate", 2);
//			int src_bgn = xnde.Src_bgn(); int src_end = xnde.Src_end();
//			for (int i = src_bgn; i < src_end; ++i) {
//				Object sub_xnde = sub_xnde_trie.Match_bgn(src, i, src_end); if (sub_xnde == null) continue;
//				int pos = sub_xnde_trie.Match_pos();
//				switch (src[pos]) {
//					case Byte_ascii.Angle_end:
//						break;
//					case Byte_ascii.Space: case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:
//						// find sub_xnde.Atrs()
//						break;
//				}
//				int end_pos = Bry_find_.Find_fwd(src, Byte_ascii.Angle_bgn);	// search for close tab
//				i = end_pos;
//			}
		/*
		int weight_total = 0;
		int[] weight_nodes = new int[options_len];
		for (int i = 0; i < options_len; ++i) {
			Xnde x = get_option(i);
			int weight_itm = x.Atr_int_or("weight", 1);
			weight_total += 1;
		}
		if (weight_total == 0) return;	// ignore empty <choose>
		int rnd = RandomAdp_.new_().Next(weight_total);
		byte[] option_bry = null;
		for (int i = 0; i < options_len; ++i) {
			r -= weight_nodes[i];
			if (r <= 0) {
				Xnde x = get_option(i);
				option_bry = x.Text;
			}
		}
		if (templates_len > 0) {
			Xnde x = get_option(0);
			option_bry = "{{" + x.Text + "|" + option_bry + "}}";
		}
		if (atrs(before) != null)
			atrs(before) = before + option_bry;
		if (atrs(after) != null)
			atrs(after) = option_bry + after;
		this.val = ctx.Parse(option_bry)			
		*/			
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
//			bfr.Add(val);
	}
}
