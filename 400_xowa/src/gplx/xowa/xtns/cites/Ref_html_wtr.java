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
import gplx.core.brys.*; import gplx.core.brys.fmtrs.*; import gplx.core.brys.args.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
public class Ref_html_wtr {
	private final    Xoh_ref_list_fmtr grp_list_fmtr = new Xoh_ref_list_fmtr();
	private final    Bfr_arg__bry_fmtr grp_key_fmtr = Bfr_arg_.New_bry_fmtr__null(), itm_id_fmtr = Bfr_arg_.New_bry_fmtr__null(), grp_id_fmtr = Bfr_arg_.New_bry_fmtr__null();
	public Ref_html_wtr(Xowe_wiki wiki) {
		cfg = Ref_html_wtr_cfg.new_();
	}
	public void Xnde_ref(Xoh_wtr_ctx opts, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		Ref_nde itm = (Ref_nde)xnde.Xnde_xtn();
		if (itm == null) return;
		if (itm.Follow_y()) return;	// NOTE: "follow" is always appended to preceding ref; will never generate its own ^ a  
		byte[] itm_group = itm.Group();
		boolean itm_group_is_default = Bry_.Eq(itm_group, Bry_.Empty) || Bry_.Eq(itm_group, Cite_xtn_mgr.Group_default_name());	// do not show "lower-alpha"; PAGE:en.w:Moon; DATE:2014-07-21
		cfg.Itm_html().Bld_bfr_many(bfr
			, Itm_id(itm, true)
			, Grp_id(itm)
			, itm_group_is_default
			? itm.Idx_major() + 1
			: (Object)grp_key_fmtr.Set(cfg.Itm_grp_text(), itm.Group(), itm.Idx_major() + 1)
			);
	}
	public Ref_html_wtr_cfg Cfg() {return cfg;} private Ref_html_wtr_cfg cfg;
	public void Init_by_wiki(Xowe_wiki wiki) {
		cfg.Init_by_wiki(wiki);
	}
	private Bfr_arg Itm_id(Ref_nde itm, boolean caller_is_ref) {
		if (itm.Name() == Bry_.Empty)
			return itm_id_fmtr.Set(cfg.Itm_id_uid(), itm.Uid());
		else {
			if (caller_is_ref)
				return itm_id_fmtr.Set(cfg.Itm_id_key_one(), itm.Name(), itm.Idx_major(), itm.Idx_minor());
			else
				return itm_id_fmtr.Set(cfg.Itm_id_key_many(), itm.Name(), itm.Idx_major());
		}
	}
	private Bfr_arg Grp_id(Ref_nde itm) {
		return itm.Name() == Bry_.Empty	// name is blank >>> uid 
			? grp_id_fmtr.Set(cfg.Grp_id_uid(), itm.Uid())
			: grp_id_fmtr.Set(cfg.Grp_id_key(), itm.Name(), itm.Idx_major());
	}
	private int List_len(Ref_nde itm) {
		int len = itm.Related_len();
		int rv = len;
		for (int i = 0; i < len; i++) {
			Ref_nde list_itm = itm.Related_get(i);
			if (list_itm.Nested()) --rv;
		}
		return rv;
	}
	public void Xnde_references(Xoh_html_wtr wtr, Xop_ctx ctx, Xoh_wtr_ctx opts, Xoae_page wpg, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		References_nde references = (References_nde)xnde.Xnde_xtn();
		Ref_itm_lst lst = wpg.Ref_mgr().Lst_get(references.Group(), references.List_idx());	// get group; EX: <references group="note"/>
		if (lst == null) return;	// NOTE: possible to have a grouped references without references; EX: Infobox planet; <references group=note> in sidebar, but no refs 
		if (lst.Itms_len() == 0) return;
		bfr.Add(cfg.Grp_bgn());
		int itms_len = lst.Itms_len();
		for (int j = 0; j < itms_len; j++) {	// iterate over itms in grp
			Ref_nde head_itm = lst.Itms_get_at(j);
			Bry_bfr tmp = Bry_bfr_.New();
			int list_len = List_len(head_itm);
			grp_list_fmtr.Init(ctx.Wiki(), cfg, head_itm);
			Ref_nde text_itm = grp_list_fmtr.IdentifyTxt();	// find the item that has the text (there should only be 0 or 1)
			if (text_itm.Body() != null)
				wtr.Write_tkn_to_html(tmp, ctx, opts, text_itm.Body().Root_src(), null, Xoh_html_wtr.Sub_idx_null, text_itm.Body());

			// add follows
			int related_len = head_itm.Related_len();
			for (int k = 0; k < related_len; k++) {
				Ref_nde related_itm = head_itm.Related_get(k);
				if (related_itm.Follow_y()) {	// NOTE: both follow and related are in the related list; only add follow
					tmp.Add_byte_space();	// always add space; REF.MW:Cite_body.php;$this->mRefs[$group][$follow]['text'] = $this->mRefs[$group][$follow]['text'] . ' ' . $str;
					wtr.Write_tkn_to_html(tmp, ctx, opts, related_itm.Body().Root_src(), null, Xoh_html_wtr.Sub_idx_null, related_itm.Body());
				}
			}

			if (list_len == 0) {		// ref has 0 list_itms or 1 list_itm but nested; EX: "123 ^ text"
				cfg.Grp_html_one().Bld_bfr_many(bfr
					, Grp_id(head_itm)	// NOTE: use head_itm for back ref to work (^ must link to same id)
					, Itm_id(head_itm, true)
					, tmp
					);
			}
			else {							// ref has 1+ itms; EX: "123 ^ a b c text"
				cfg.Grp_html_many().Bld_bfr_many(bfr
					, Itm_id(text_itm, false)
					, grp_list_fmtr
					, tmp
					);
			}
		}
		bfr.Add(cfg.Grp_end());
	}
}
