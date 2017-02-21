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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
public class References_nde implements Xox_xnde, Mwh_atr_itm_owner1 {
	public byte[] Group() {return group;} public References_nde Group_(byte[] v) {group = v; return this;} private byte[] group = Bry_.Empty;
	public int List_idx() {return list_idx;} public References_nde List_idx_(int v) {list_idx = v; return this;} private int list_idx;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		Byte_obj_val xatr_id = (Byte_obj_val)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Xatr_id_group:		group = xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (ctx.Tid_is_popup()) return;
		Ref_itm_mgr ref_mgr = ctx.Page().Ref_mgr();
		if (ref_mgr.References__recursing()) {
			xnde.Tag_visible_(false);	// NOTE:do not print empty <references/> tag; especially necessary for recursing references; PAGE:cs.s:Page:Hejčl,_Jan_-_Pentateuch.pdf/128 DATE:2016-09-01
			return;	// skip nested <references> else refs will be lost; EX:"<references><references/></references>"; PAGE:en.w:Hwair; DATE:2014-06-27
		}
		ctx.Para().Process_block__bgn_n__end_y(Xop_xnde_tag_.Tag__div);	// xnde generates <block_node>; <references> -> <ol>; close any blocks; PAGE:fr.w:Heidi_(roman); DATE:2014-02-17
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		if (xnde.CloseMode() == Xop_xnde_tkn.CloseMode_pair) {	// "<references>", "</references>"; parse anything in between but only to pick up <ref> tags; discard everything else; DATE:2014-06-27
			int itm_bgn = xnde.Tag_open_end(), itm_end = xnde.Tag_close_bgn();
			Xop_ctx references_ctx = Xop_ctx.New__sub__reuse_lst(wiki, ctx, ctx.Lst_page_regy()).References_group_(group);	// changed from following: "Xop_ctx references_ctx = Xop_ctx.New__sub(wiki).References_group_(group);"; DATE:2015-05-16;
			references_ctx.Para().Enabled_n_();	// disable para during <references> parsing
			byte[] references_src = Bry_.Mid(src, itm_bgn, itm_end);
			Xop_tkn_mkr tkn_mkr = ctx.Tkn_mkr();
			Xop_root_tkn sub_root = tkn_mkr.Root(src);
			boolean prv_recursing = ref_mgr.References__recursing();
			ref_mgr.References__recursing_(true);
			wiki.Parser_mgr().Main().Parse_text_to_wdom(sub_root, references_ctx, tkn_mkr, references_src, Xop_parser_.Doc_bgn_char_0);	// NOTE: parse inner contents, but root will be discarded; only picking up <ref> tags; DATE:2014-06-27
			ref_mgr.References__recursing_(prv_recursing);
		}
		list_idx = ref_mgr.Grps_get(group).Grp_seal();	// NOTE: needs to be sealed at end; else inner refs will end up in new group; EX: <references><ref>don't seal prematurely</ref></references>
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (xnde.Tag_visible())
			html_wtr.Ref_wtr().Xnde_references(html_wtr, ctx, hctx, wpg, bfr, src, xnde);
	}
	private static final byte Xatr_id_group = 0;
	public static boolean Enabled = true;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7()
	.Add_str_obj("group", Byte_obj_val.new_(References_nde.Xatr_id_group));
}
