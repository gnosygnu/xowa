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
public class Ref_html_wtr {
	public Ref_html_wtr(Xow_wiki wiki) {
		cfg = Ref_html_wtr_cfg.new_();
	}
	public void Xnde_ref(Xoh_html_wtr_ctx opts, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
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
			: (Object)grp_key_fmtr.Atrs_(cfg.Itm_grp_text(), itm.Group(), itm.Idx_major() + 1)
			);
	}	private Bry_fmtr_arg_fmtr_objs grp_key_fmtr = Bry_fmtr_arg_.fmtr_null_();
	public Ref_html_wtr_cfg Cfg() {return cfg;} private Ref_html_wtr_cfg cfg;
	public void Init_by_wiki(Xow_wiki wiki) {
		cfg.Init_by_wiki(wiki);
	}
	private Bry_fmtr_arg Itm_id(Ref_nde itm, boolean caller_is_ref) {
		if (itm.Name() == Bry_.Empty)
			return itm_id_fmtr.Atrs_(cfg.Itm_id_uid(), itm.Uid());
		else {
			if (caller_is_ref)
				return itm_id_fmtr.Atrs_(cfg.Itm_id_key_one(), itm.Name(), itm.Idx_major(), itm.Idx_minor());
			else
				return itm_id_fmtr.Atrs_(cfg.Itm_id_key_many(), itm.Name(), itm.Idx_major());
		}
	}	private Bry_fmtr_arg_fmtr_objs itm_id_fmtr = Bry_fmtr_arg_.fmtr_null_();
	private Bry_fmtr_arg Grp_id(Ref_nde itm) {
		return itm.Name() == Bry_.Empty	// name is blank >>> uid 
			? grp_id_fmtr.Atrs_(cfg.Grp_id_uid(), itm.Uid())
			: grp_id_fmtr.Atrs_(cfg.Grp_id_key(), itm.Name(), itm.Idx_major());
	}	private Bry_fmtr_arg_fmtr_objs grp_id_fmtr = Bry_fmtr_arg_.fmtr_null_();
	private int List_len(Ref_nde itm) {
		int len = itm.Related_len();
		int rv = len;
		for (int i = 0; i < len; i++) {
			Ref_nde list_itm = itm.Related_get(i);
			if (list_itm.Nested()) --rv;
		}
		return rv;
	}
	public void Xnde_references(Xoh_html_wtr wtr, Xop_ctx ctx, Xoh_html_wtr_ctx opts, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		References_nde references = (References_nde)xnde.Xnde_xtn();
		Ref_itm_lst lst = ctx.Cur_page().Ref_mgr().Lst_get(references.Group(), references.List_idx());	// get group; EX: <references group="note"/>
		if (lst == null) return;	// NOTE: possible to have a grouped references without references; EX: Infobox planet; <references group=note> in sidebar, but no refs 
		if (lst.Itms_len() == 0) return;
		bfr.Add(cfg.Grp_bgn());
		int itms_len = lst.Itms_len();
		for (int j = 0; j < itms_len; j++) {	// iterate over itms in grp
			Ref_nde head_itm = lst.Itms_get_at(j);
			Bry_bfr tmp = Bry_bfr.new_();
			int list_len = List_len(head_itm);
			grp_list_fmtr.Init(ctx.Wiki(), cfg, head_itm);
			Ref_nde text_itm = grp_list_fmtr.IdentifyTxt();	// find the item that has the text (there should only be 0 or 1)
			if (text_itm.Body() != null)
				wtr.Write_tkn(tmp, ctx, opts, text_itm.Body().Root_src(), null, Xoh_html_wtr.Sub_idx_null, text_itm.Body());

			// add follows
			int related_len = head_itm.Related_len();
			for (int k = 0; k < related_len; k++) {
				Ref_nde related_itm = head_itm.Related_get(k);
				if (related_itm.Follow_y()) {	// NOTE: both follow and related are in the related list; only add follow
					tmp.Add_byte_space();	// always add space; REF.MW:Cite_body.php;$this->mRefs[$group][$follow]['text'] = $this->mRefs[$group][$follow]['text'] . ' ' . $str;
					wtr.Write_tkn(tmp, ctx, opts, related_itm.Body().Root_src(), null, Xoh_html_wtr.Sub_idx_null, related_itm.Body());
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
	private static Xoh_ref_list_fmtr grp_list_fmtr = new Xoh_ref_list_fmtr();
}
class Xoh_ref_list_fmtr implements Bry_fmtr_arg {
	public void Init(Xow_wiki wiki, Ref_html_wtr_cfg cfg, Ref_nde itm) {
		this.wiki = wiki; this.cfg = cfg; this.itm = itm;
	} private Xow_wiki wiki; private Ref_nde itm; private Ref_html_wtr_cfg cfg;
	public Ref_nde IdentifyTxt() {
		if (HasTxt(itm)) return itm;
		int itm_related_len = itm.Related_len();
		for (int i = 0; i < itm_related_len; i++) {
			Ref_nde rel = itm.Related_get(i);
			if (HasTxt(rel)) return rel;
		}
		return itm; // no itm has text; TODO:WARN
	}
	private boolean HasTxt(Ref_nde v) {return v.Body() != null && v.Body().Root_src().length > 0;}
	public void XferAry(Bry_bfr trg, int idx) {
		int related_len = itm.Related_len();
		Bry_fmtr itm_fmtr = cfg.Grp_html_list();
		Fmt(itm_fmtr, wiki, trg, itm);
		for (int i = 0; i < related_len; i++) {
			Ref_nde link_itm = itm.Related_get(i);
			if (link_itm.Nested()) continue;
			Fmt(itm_fmtr, wiki, trg, link_itm);
		}
	}
	private void Fmt(Bry_fmtr itm_fmtr, Xow_wiki wiki, Bry_bfr trg, Ref_nde itm) {
		int itm_idx_minor = itm.Idx_minor();
		if (itm_idx_minor < 0) return;	// HACK: <ref follow created a negative index; ignore these references for now; de.wikisource.org/wiki/Seite:Die Trunksucht.pdf/63; DATE:2013-06-22
		byte[] backlabel 
			= itm_idx_minor < cfg.Backlabels_len()
			? cfg.Backlabels()[itm.Idx_minor()]
			: wiki.Parser().Parse_text_to_html(wiki.Ctx(), wiki.Msg_mgr().Val_by_key_args(Ref_html_wtr_cfg.Msg_backlabels_err, itm.Idx_minor()))
			;
		itm_fmtr.Bld_bfr_many(trg
			, fmtr.Atrs_(cfg.Itm_id_key_one(), itm.Name(), itm.Idx_major(), itm.Idx_minor())
			, backlabel
			);
	}
	private Bry_fmtr_arg_fmtr_objs fmtr = Bry_fmtr_arg_.fmtr_null_();
}
