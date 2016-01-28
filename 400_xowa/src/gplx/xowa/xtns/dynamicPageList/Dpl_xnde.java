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
package gplx.xowa.xtns.dynamicPageList; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.ctgs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*; import gplx.xowa.parsers.amps.*;
public class Dpl_xnde implements Xox_xnde {
	private Dpl_itm itm = new Dpl_itm(); private List_adp pages = List_adp_.new_();
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {} // NOTE: <dynamicPageList> has no attributes
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		itm.Parse(wiki, ctx, ctx.Page().Ttl().Full_txt(), src, xnde);
		Dpl_page_finder.Find_pages(pages, wiki, itm);
		if (itm.Sort_ascending() != Bool_.__byte)
			pages.Sort_by(new Dpl_page_sorter(itm));
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		Xowe_wiki wiki = ctx.Wiki();
		Dpl_html_data html_mode = Dpl_html_data.new_(Dpl_itm_keys.Key_unordered);
		int itms_len = pages.Count();
		if (itms_len == 0) {
			if (!itm.Suppress_errors())
				bfr.Add_str_a7("No pages meet these criteria.");
			return;
		}
		int itms_bgn = 0;
		if (itm.Offset() != Int_.Min_value) {
			itms_bgn = itm.Offset();
		}
		if (itm.Count() != Int_.Min_value && itms_bgn + itm.Count() < itms_len) {
			itms_len = itms_bgn + itm.Count();
		}
		boolean show_ns = itm.Show_ns();
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		Xop_amp_mgr amp_mgr = wiki.Appe().Parser_amp_mgr();
		try {
			bfr.Add(html_mode.Grp_bgn()).Add_byte_nl();
			for (int i = itms_bgn; i < itms_len; i++) {
				Xowd_page_itm page = (Xowd_page_itm)pages.Get_at(i);
				Xoa_ttl ttl = Xoa_ttl.parse(wiki, page.Ns_id(), page.Ttl_page_db());
				byte[] ttl_page_txt = show_ns ? ttl.Full_txt() : ttl.Page_txt();
				if (ttl_page_txt == null) continue;	// NOTE: apparently DynamicPageList allows null pages; DATE:2013-07-22
				switch (html_mode.Tid()) {
					case Dpl_html_data.Tid_list_ul:
					case Dpl_html_data.Tid_list_ol:
						bfr.Add(Xoh_consts.Space_2).Add(html_mode.Itm_bgn()).Add(Gfh_bldr_.Bry__a_lhs_w_href);
						bfr.Add_str_a7("/wiki/").Add(Gfo_url_encoder_.Href.Encode(ttl.Full_db())).Add_byte_quote();	// NOTE: Full_db to encode spaces as underscores; PAGE:en.q:Wikiquote:Speedy_deletions DATE:2016-01-19
						Gfh_atr_.Add(bfr, Gfh_atr_.Bry__title, Xoh_html_wtr_escaper.Escape(amp_mgr, tmp_bfr, ttl.Full_txt()));			// NOTE: Full_txt b/c title always includes ns, even if show_ns is off; PAGE:en.b:Wikibooks:WikiProject DATE:2016-01-20
						if (itm.No_follow()) bfr.Add(Bry_nofollow);
						bfr.Add_byte(Byte_ascii.Gt);
						Xoh_html_wtr_escaper.Escape(amp_mgr, bfr, ttl_page_txt, 0, ttl_page_txt.length, false, false);
						bfr.Add(Gfh_bldr_.Bry__a_rhs).Add(html_mode.Itm_end()).Add_byte_nl();
						// TODO: lnki_wtr.Clear().Href_wiki_(ttl).Title_(ttl).Nofollow_().Write_head(bfr).Write_text(bfr).Write_tail(bfr)
						break;
					default:
						break;
				}
			}
			bfr.Add(html_mode.Grp_end()).Add_byte_nl();
		} finally {tmp_bfr.Mkr_rls();}
	}
	private static byte[] Bry_nofollow = Bry_.new_a7(" rel=\"nofollow\"");  
}
class Dpl_page_finder {
	public static void Find_pages(List_adp rv, Xowe_wiki wiki, Dpl_itm itm) {
		rv.Clear();
		List_adp includes = itm.Ctg_includes(); if (includes == null) return;
		int includes_len = includes.Count();
		Ordered_hash old_regy = Ordered_hash_.New(), new_regy = Ordered_hash_.New(), cur_regy = Ordered_hash_.New();
		Xodb_load_mgr load_mgr = wiki.Db_mgr().Load_mgr();
		Xowd_page_itm tmp_page = new Xowd_page_itm();
		Int_obj_ref tmp_id = Int_obj_ref.zero_();
		List_adp del_list = List_adp_.new_();
		int ns_filter = itm.Ns_filter();
		Ordered_hash exclude_pages = Ordered_hash_.New();
		Find_excludes(exclude_pages, load_mgr, tmp_page, tmp_id, itm.Ctg_excludes());

		for (int i = 0; i < includes_len; i++) {	// loop over includes
			byte[] include = (byte[])includes.Get_at(i);
			cur_regy.Clear(); del_list.Clear();
			Find_pages_in_ctg(cur_regy, load_mgr, tmp_page, tmp_id, include);
			Del_old_pages_not_in_cur(i, tmp_id, old_regy, cur_regy, del_list);
			Add_cur_pages_also_in_old(i, tmp_id, old_regy, cur_regy, new_regy, exclude_pages, ns_filter);
			old_regy = new_regy;
			new_regy = Ordered_hash_.New();
		}			
		int pages_len = old_regy.Count();
		for (int i = 0; i < pages_len; i++) {		// loop over old and create pages
			Int_obj_ref old_id = (Int_obj_ref)old_regy.Get_at(i);
			rv.Add(new Xowd_page_itm().Id_(old_id.Val()));
		}			
		wiki.Db_mgr().Load_mgr().Load_by_ids(Cancelable_.Never, rv, 0, pages_len);
		rv.Sort_by(Xowd_page_itm_sorter.IdAsc);
	}
	private static void Find_excludes(Ordered_hash exclude_pages, Xodb_load_mgr load_mgr, Xowd_page_itm tmp_page, Int_obj_ref tmp_id, List_adp exclude_ctgs) {
		if (exclude_ctgs == null) return;
		int exclude_ctgs_len = exclude_ctgs.Count();
		for (int i = 0; i < exclude_ctgs_len; i++) {
			byte[] exclude_ctg = (byte[])exclude_ctgs.Get_at(i);
			Find_pages_in_ctg(exclude_pages, load_mgr, tmp_page, tmp_id, exclude_ctg);
		}
	}
	private static void Find_pages_in_ctg(Ordered_hash list, Xodb_load_mgr load_mgr, Xowd_page_itm tmp_page, Int_obj_ref tmp_id, byte[] ctg_ttl) {
		Xoctg_view_ctg ctg = new Xoctg_view_ctg().Name_(ctg_ttl);
		load_mgr.Load_ctg_v1(ctg, ctg_ttl);

		for (byte ctg_tid = 0; ctg_tid < Xoa_ctg_mgr.Tid__max; ctg_tid++) {
			Xoctg_view_grp ctg_mgr = ctg.Grp_by_tid(ctg_tid); if (ctg_mgr == null) continue;
			int itms_len = ctg_mgr.Total();
			for (int i = 0; i < itms_len; i++) {
				Xoctg_view_itm ctg_itm = ctg_mgr.Itms()[i];					
				int ctg_itm_id = ctg_itm.Page_id();
				if (list.Has(tmp_id.Val_(ctg_itm_id))) continue;
				list.Add(Int_obj_ref.new_(ctg_itm_id), ctg_itm);
//					if (ctg_tid == Xoa_ctg_mgr.Tid_subc) {	// recurse subcategories
//						load_mgr.Load_by_id(tmp_page, ctg_itm_id);
//						Find_pages_in_ctg(list, load_mgr, tmp_page, tmp_id, tmp_page.Ttl_wo_ns());
//					}
			}
		}
	}
	private static void Del_old_pages_not_in_cur(int i, Int_obj_ref tmp_id, Ordered_hash old_regy, Ordered_hash cur_regy, List_adp del_list) {
		if (i == 0) return;						// skip logic for first ctg (which doesn't have a predecessor)
		int old_len = old_regy.Count();
		for (int j = 0; j < old_len; j++) {		// if cur is not in new, del it
			Int_obj_ref old_id = (Int_obj_ref)old_regy.Get_at(j);
			if (!cur_regy.Has(tmp_id.Val_(old_id.Val())))	// old_itm does not exist in cur_regy
				del_list.Add(old_id);						// remove; EX: (A,B) in old; B only in cur; old should now be (A) only				
		}
		int del_len = del_list.Count();
		for (int j = 0; j < del_len; j++) {
			Int_obj_ref old_itm = (Int_obj_ref)del_list.Get_at(j);
			old_regy.Del(tmp_id.Val_(old_itm.Val()));
		}
	}
	private static void Add_cur_pages_also_in_old(int i, Int_obj_ref tmp_id, Ordered_hash old_regy, Ordered_hash cur_regy, Ordered_hash new_regy, Ordered_hash exclude_pages, int ns_filter) {
		int found_len = cur_regy.Count();
		for (int j = 0; j < found_len; j++) {			// if new_page is in cur, add it
			Xoctg_view_itm cur_itm = (Xoctg_view_itm)cur_regy.Get_at(j);
			if (ns_filter != Dpl_itm.Ns_filter_null && ns_filter != cur_itm.Ttl().Ns().Id()) continue;
			tmp_id.Val_(cur_itm.Page_id());				// set tmp_id, since it will be used at least once
			if (exclude_pages.Has(tmp_id)) continue;	// ignore excluded pages
			if (i != 0) {								// skip logic for first ctg (which doesn't have a predecessor)
				if (!old_regy.Has(tmp_id)) continue;	// cur_itm not in old_regy; ignore
			}
			new_regy.Add_as_key_and_val(Int_obj_ref.new_(cur_itm.Page_id()));
		}
	}
}
