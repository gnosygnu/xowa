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
package gplx.xowa.xtns.cite; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.html.*;
public class References_nde implements Xox_xnde, Xop_xnde_atr_parser {
	public byte[] Group() {return group;} public References_nde Group_(byte[] v) {group = v; return this;} private byte[] group = Bry_.Empty;
	public int List_idx() {return list_idx;} public References_nde List_idx_(int v) {list_idx = v; return this;} private int list_idx;
	public void Xatr_parse(Xow_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_key_obj) {
		if (xatr_key_obj == null) return;
		Byte_obj_val xatr_key = (Byte_obj_val)xatr_key_obj;
		switch (xatr_key.Val()) {
			case Xatr_id_group:		group = xatr.Val_as_bry(src); break;
		}
	}
	public void Xtn_parse(Xow_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (ctx.Tid_is_popup()) return;
		Ref_itm_mgr ref_mgr = ctx.Cur_page().Ref_mgr();
		if (ref_mgr.References__recursing()) return;	// skip nested <references> else refs will be lost; EX:"<references><references/></references>"; PAGE:en.w:Hwair; DATE:2014-06-27
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag_div);	// xnde generates <block_node>; <references> -> <ol>; close any blocks; PAGE:fr.w:Heidi_(roman); DATE:2014-02-17
		Xop_xatr_itm.Xatr_parse(wiki.App(), this, xatrs_hash, wiki, src, xnde);
		if (xnde.CloseMode() == Xop_xnde_tkn.CloseMode_pair) {	// "<references>", "</references>"; parse anything in between but only to pick up <ref> tags; discard everything else; DATE:2014-06-27
			int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
			Xop_ctx references_ctx = Xop_ctx.new_sub_(wiki).References_group_(group);
			references_ctx.Para().Enabled_n_();	// disable para during <references> parsing
			byte[] references_src = Bry_.Mid(src, itm_bgn, itm_end);
			Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
			Xop_root_tkn sub_root = tkn_mkr.Root(src);
			boolean prv_recursing = ref_mgr.References__recursing();
			ref_mgr.References__recursing_(true);
			wiki.Parser().Parse_text_to_wdom(sub_root, references_ctx, tkn_mkr, references_src, Xop_parser_.Doc_bgn_char_0);	// NOTE: parse @gplx.Internal protected contents, but root will be discarded; only picking up <ref> tags; DATE:2014-06-27
			ref_mgr.References__recursing_(prv_recursing);
		}
		list_idx = ref_mgr.Grps_get(group).Grp_seal();	// NOTE: needs to be sealed at end; else inner refs will end up in new group; EX: <references><ref>don't seal prematurely</ref></references>
	}
	public void Xtn_write(Bry_bfr bfr, Xoa_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		html_wtr.Ref_wtr().Xnde_references(html_wtr, ctx, hctx, bfr, src, xnde);
	}
	private static final byte Xatr_id_group = 0;
	public static boolean Enabled = true;
	private static final Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_ascii_()
	.Add_str_obj("group", Byte_obj_val.new_(References_nde.Xatr_id_group));
}
