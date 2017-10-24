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
public class Ref_nde implements Xox_xnde, Mwh_atr_itm_owner1 {
	public byte[] Name() {return name;} public Ref_nde Name_(byte[] v) {name = v; return this;} private byte[] name = Bry_.Empty;
	public byte[] Group() {return group;} private byte[] group = Bry_.Empty;
	public byte[] Follow() {return follow;} private byte[] follow = Bry_.Empty;
	public boolean Follow_y() {return follow != Bry_.Empty;}
	public int Uid() {return uid;} public Ref_nde Uid_(int v) {uid = v; return this;} private int uid;
	public boolean Head() {return head;} private boolean head;
	public boolean Nested() {return nested;} private boolean nested;
	public int Idx_major() {return idx_major;} public Ref_nde Idx_major_(int v) {idx_major = v; return this;} private int idx_major;
	public int Idx_minor() {return idx_minor;} public Ref_nde Idx_minor_(int v) {idx_minor = v; return this;} private int idx_minor;
	public Xop_xnde_tkn Xnde() {return xnde;} private Xop_xnde_tkn xnde;
	public Xop_root_tkn Body() {return body;} private Xop_root_tkn body;
	public boolean Exists_in_lnki_title() {return exists_in_lnki_title;} public Ref_nde Exists_in_lnki_title_(boolean v) {exists_in_lnki_title = v; return this;} private boolean exists_in_lnki_title;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		Byte_obj_val xatr_id = (Byte_obj_val)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Xatr_id_name:		name = wiki.Sanitizer().Escape_id(xatr.Val_as_bry()); break;
			case Xatr_id_follow:	follow = xatr.Val_as_bry(); break;
			case Xatr_id_group:		group = xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		if (ctx.Tid_is_popup()) return;
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		if (xnde.CloseMode() == Xop_xnde_tkn.CloseMode_pair)
			body = wiki.Parser_mgr().Main().Parse_text_to_wdom_old_ctx(ctx, Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn()), false);
		byte[] references_group = ctx.References_group();	// set by <references>
		if (references_group != null) {
			group = references_group;		// override <ref group> with <references group>; note that MW throws an error if nested <ref> has different group than outer <references>; Cite error: <ref> tag in <references> has conflicting group attribute "a".
			head = true;
			nested = true;					// refs nested in references don't show <a> entry in <references>
		}
		if (!ctx.Ref_ignore())				// sub_ctx may be marked to ignore <ref>; EX: <pages>,{{#lst}}; DATE:2014-04-24
			ctx.Page().Ref_mgr().Grps_add(group, name, follow, this);
		this.xnde = xnde;
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		html_wtr.Ref_wtr().Xnde_ref(hctx, bfr, src, xnde);
	}
	public Ref_nde[] Related() {return related;} Ref_nde[] related = Ary_empty;
	public int Related_len() {return related_len;} private int related_len;
	public Ref_nde Related_get(int i) {return related[i];}
	public void Related_add(Ref_nde itm, int idx_minor) {
		int new_len = related_len + 1;
		int related_max = related.length;
		if (new_len > related_max)
			related = (Ref_nde[])Array_.Resize(related, related_max == 0 ? 1 : related_max * 2);
		itm.Idx_minor_(idx_minor);
		related[related_len] = itm;
		related_len = new_len;
	}
	public static final int Idx_minor_follow = -2;
	public static final byte Xatr_id_name = 0, Xatr_id_group = 1, Xatr_id_follow = 2;
	private static final    Ref_nde[] Ary_empty = new Ref_nde[0];
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7()
	.Add_str_obj("name", Byte_obj_val.new_(Ref_nde.Xatr_id_name))
	.Add_str_obj("group", Byte_obj_val.new_(Ref_nde.Xatr_id_group))
	.Add_str_obj("follow", Byte_obj_val.new_(Ref_nde.Xatr_id_follow));
}
