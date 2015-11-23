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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.nss.*;
public class Xoctg_html_mgr implements GfoInvkAble {
	@gplx.Internal protected Xoctg_fmtr_grp Fmtr_grp() {return fmtr_grp;} private Xoctg_fmtr_grp fmtr_grp = new Xoctg_fmtr_grp();
	private final Xoctg_fmtr_all mgr_subcs = new Xoctg_fmtr_all(Xoa_ctg_mgr.Tid_subc);
	private final Xoctg_fmtr_all mgr_pages = new Xoctg_fmtr_all(Xoa_ctg_mgr.Tid_page);
	private final Xoctg_fmtr_all mgr_files = new Xoctg_fmtr_all(Xoa_ctg_mgr.Tid_file);
	public Xoctg_data_cache Data_cache() {return data_cache;} private Xoctg_data_cache data_cache = new Xoctg_data_cache(); 
	public void Bld_html(Xowe_wiki wiki, Xoae_page page, Bry_bfr bfr) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		try {
			if (wiki.Db_mgr().Category_version() == Xoa_ctg_mgr.Version_2)
				Bld_html_v2(wiki, page, tmp_bfr);
			else
				Bld_html_v1(wiki, page, tmp_bfr);
			bfr.Add_bfr_and_preserve(tmp_bfr.Mkr_rls());
		}
		catch (Exception e) { // ctg error should never cause page to fail
			tmp_bfr.Mkr_rls();
			Xoa_app_.Usr_dlg().Warn_many("", "", "failed to generate category: title=~{0} err=~{1}", String_.new_u8(page.Ttl().Full_txt()), Err_.Message_gplx_full(e));
		}
	}	private Xoctg_url url_ctg = new Xoctg_url();
	private void Bld_html_v2(Xowe_wiki wiki, Xoae_page page, Bry_bfr bfr) {
		byte[] ttl_bry = page.Ttl().Page_db();
		Xoctg_view_ctg view_ctg = new Xoctg_view_ctg().Name_(page.Ttl().Page_txt());
		url_ctg.Parse(wiki.Appe().Usr_dlg(), page.Url());
		wiki.Db_mgr().Load_mgr().Load_ctg_v2a(view_ctg, url_ctg, ttl_bry, Grp_max_default);
		Bld_all(bfr, wiki, page.Lang(), view_ctg, Xoa_ctg_mgr.Tid_subc);
		Bld_all(bfr, wiki, page.Lang(), view_ctg, Xoa_ctg_mgr.Tid_page);
		Bld_all(bfr, wiki, page.Lang(), view_ctg, Xoa_ctg_mgr.Tid_file);
	}
	public void Get_titles(Gfo_usr_dlg usr_dlg, Xowe_wiki wiki, Xoctg_view_ctg ctg) {
		title_list.Clear();
		Add_titles(title_list, ctg.Subcs());
		Add_titles(title_list, ctg.Files());
		Add_titles(title_list, ctg.Pages());
		title_list.Sort_by(Xoctg_view_itm_sorter_id.Instance);
		int len = title_list.Count();
		int pct = len / 16;
		Xowd_page_itm dbo_page = new Xowd_page_itm();
		for (int i = 0; i < len; i++) {
			Xoctg_view_itm itm = (Xoctg_view_itm)title_list.Get_at(i);
			if (pct != 0 && i % pct == 0) usr_dlg.Prog_many("", "", "loading title data: ~{0} / ~{1} -- ~{2}", i, len, String_.new_u8(itm.Sort_key()));
			boolean id_exists = wiki.Db_mgr().Load_mgr().Load_by_id(dbo_page, itm.Page_id());
			Xoa_ttl itm_ttl = null;
			if (id_exists)
				itm_ttl = Xoa_ttl.parse(wiki, dbo_page.Ns_id(), dbo_page.Ttl_page_db());
			else {
				itm_ttl = Xoa_ttl.parse(wiki, itm.Sort_key());
				if (itm_ttl == null)
					itm_ttl = Xoa_ttl.parse(wiki, Bry_missing);
				itm.Missing_y_();
			}
			itm.Set__ttl__sortkey(itm_ttl, itm.Sort_key());
		}
	}	List_adp title_list = List_adp_.new_(); static final byte[] Bry_missing = Bry_.new_a7("missing");
	private void Add_titles(List_adp title_list, Xoctg_view_grp grp) {
		int len = grp.Itms().length;
		for (int i = 0; i < len; i++) {
			Xoctg_view_itm itm = grp.Itms()[i];
			title_list.Add(itm);
		}
	}
	private void Bld_html_v1(Xowe_wiki wiki, Xoae_page page, Bry_bfr bfr) {
		Xoctg_view_ctg ctg = new Xoctg_view_ctg().Name_(page.Ttl().Page_txt());
		boolean found = wiki.Db_mgr().Load_mgr().Load_ctg_v1(ctg, page.Ttl().Page_db()); if (!found) return;
		Bld_all(bfr, wiki, page.Lang(), ctg, Xoa_ctg_mgr.Tid_subc);
		Bld_all(bfr, wiki, page.Lang(), ctg, Xoa_ctg_mgr.Tid_page);
		Bld_all(bfr, wiki, page.Lang(), ctg, Xoa_ctg_mgr.Tid_file);
	}
	@gplx.Internal protected void Bld_all(Bry_bfr bfr, Xowe_wiki wiki, Xol_lang_itm lang, Xoctg_view_ctg view_ctg, byte tid) {
		Xoctg_view_grp view_grp = view_ctg.Grp_by_tid(tid);
		int view_grp_len = view_grp.Itms().length; if (view_grp_len == 0) return;
		view_grp.End_(view_grp_len);
		Xoctg_fmtr_all fmtr_all = Fmtr(tid);			
		Xow_msg_mgr msg_mgr = wiki.Msg_mgr();
		byte[] all_label = msg_mgr.Val_by_id_args(fmtr_all.Msg_id_label(), view_ctg.Name());
		byte[] all_stats = msg_mgr.Val_by_id_args(fmtr_all.Msg_id_stats(), view_grp.Len(), view_grp.Total());
		Xoa_ttl ctg_ttl = Xoa_ttl.parse(wiki, Xow_ns_.Tid__category, view_ctg.Name());
		byte[] all_navs = fmtr_all.Bld_bwd_fwd(wiki, ctg_ttl, view_grp);
		Array_.Sort(view_grp.Itms(), Xoctg_view_itm_sorter_sortkey.Instance);
		fmtr_grp.Init_from_all(wiki, lang, view_ctg, fmtr_all, view_grp);	// NOTE: must assert sorted for v1; PAGE:s.w:Category:Computer_science; DATE:2015-11-22
		fmtr_all.Html_all().Bld_bfr_many(bfr, fmtr_all.Div_id(), all_label, all_stats, all_navs, lang.Key_bry(), lang.Dir_ltr_bry(), fmtr_grp);
	}
	public static final int Cols_max = 3;
	@gplx.Internal protected Xoctg_fmtr_all Fmtr(byte tid) {
		switch (tid) {
			case Xoa_ctg_mgr.Tid_subc: return mgr_subcs;
			case Xoa_ctg_mgr.Tid_page: return mgr_pages;
			case Xoa_ctg_mgr.Tid_file: return mgr_files;
			default: throw Err_.new_unhandled(tid);
		}
	}
	public static final int Grp_max_default = 200;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_grp_max))		return mgr_subcs.Grp_max();
		else if	(ctx.Match(k, Invk_grp_max_))		{int grp_max = m.ReadInt("v"); mgr_subcs.Grp_max_(grp_max); mgr_files.Grp_max_(grp_max); mgr_pages.Grp_max_(grp_max);}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_grp_max = "grp_max", Invk_grp_max_ = "grp_max_";
}
class Xoctg_view_itm_sorter_id implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_view_itm lhs = (Xoctg_view_itm)lhsObj;
		Xoctg_view_itm rhs = (Xoctg_view_itm)rhsObj;
		return Int_.Compare(lhs.Page_id(), rhs.Page_id());
	}
	public static final Xoctg_view_itm_sorter_id Instance = new Xoctg_view_itm_sorter_id(); 
}
class Xoctg_view_itm_sorter_sortkey implements gplx.core.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Xoctg_view_itm lhs = (Xoctg_view_itm)lhsObj;
		Xoctg_view_itm rhs = (Xoctg_view_itm)rhsObj;
		return Bry_.Compare(lhs.Sort_key(), rhs.Sort_key());
	}
	public static final Xoctg_view_itm_sorter_sortkey Instance = new Xoctg_view_itm_sorter_sortkey(); 
}
