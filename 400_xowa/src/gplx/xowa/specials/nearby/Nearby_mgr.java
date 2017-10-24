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
package gplx.xowa.specials.nearby; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
public class Nearby_mgr implements Xow_special_page {
	Xowe_wiki wiki; byte[] trg;
	private Hash_adp_bry excluded = Hash_adp_bry.ci_a7();
	private Hash_adp_bry visited = Hash_adp_bry.ci_a7();
	List_adp trail = List_adp_.New();
	List_adp results = List_adp_.New();	
	int results_cur = 0;
//		int depth_max = 5;
//		int pages_count = 0;
	Bry_bfr tmp_bfr = Bry_bfr_.New();
	public int Results_max() {return results_max;} public Nearby_mgr Results_max_(int v) {results_max = v; return this;} private int results_max = 1;
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__nearby;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		page.Db().Text().Text_bry_(Bld_html(wiki));
		page.Html_data().Html_restricted_n_();		// [[Special:]] pages allow all HTML
//			wiki.Parser_mgr().Parse(page, false);	// do not clear else previous Search_text will be lost		
	}
	byte[] Bld_html(Xowe_wiki wiki) {
		form_fmtr.Bld_bfr_many(tmp_bfr);
		List_adp list = Find_from_to(wiki, Bry_.new_a7("Earth"), Bry_.new_a7("Atom"), excluded);
		tmp_bfr.Add_str_a7("<table>");
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Nearby_rslt rslt = (Nearby_rslt)list.Get_at(i);
			tmp_bfr.Add_str_a7("<tr>");
			int cell_len = rslt.Len();
			for (int j = 0; j < cell_len; j++) {
				Xoa_ttl ttl = (Xoa_ttl)rslt.Get_at(j);
				tmp_bfr.Add_str_a7("<td>[[");
				tmp_bfr.Add(ttl.Page_db());
				tmp_bfr.Add_str_a7("]]</td>");
			}
			tmp_bfr.Add_str_a7("</tr>");
		}
		tmp_bfr.Add_str_a7("</table>");
		return tmp_bfr.To_bry_and_clear();
	}
	Bry_fmtr form_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl
		(	"<form id='xowa_nearby_form' action='/wiki/Special:XowaNearby'>"
		,	"<table>"
		,	"  <tr><td>From:</td><td><input name='xowa_bgn' value='Earth' /></td></tr>"
		,	"  <tr><td>To:  </td><td><input name='xowa_end' value='Atom' /></td></tr>"
		,	"  <tr><td>Max: </td><td><input name='xowa_max' value='1' /></td></tr>"
		,	"  <tr><td>Skip:</td><td><input name='xowa_skip' value='' /></td></tr>"
		,	"  <tr><td/><td align='right'><input type='submit' value='go' /></td></tr>"
		,	"</table>"
		,	"</form>"
		));
	Xoa_ttl trg_ttl;
	Ordered_hash src_pool = Ordered_hash_.New_bry();
	public List_adp Find_from_to(Xowe_wiki wiki, byte[] src_bry, byte[] trg_bry, Hash_adp_bry excluded) {
		this.wiki = wiki; this.excluded = excluded;
		Xoa_ttl src_ttl = Xoa_ttl.Parse(wiki, src_bry); if (src_ttl == null) return List_adp_.Noop;
		trg_ttl = Xoa_ttl.Parse(wiki, trg_bry); if (trg_ttl == null) return List_adp_.Noop;
		trg = trg_ttl.Page_db();
		trail.Clear();
		results.Clear();
		results_cur = 0;
//			pages_count = 0;
		visited.Clear();
		src_pool.Clear();
		src_pool.Add(src_ttl.Page_db(), new Nearby_itmx(trail, src_ttl));
		Examine_page(src_pool);
		return results;
	}
	private void Examine_page(Ordered_hash src_pool){
		int len = src_pool.Count();
		Ordered_hash next_pool = Ordered_hash_.New_bry();
		for (int i = 0; i < len; i++) {
			Nearby_itmx itmx = (Nearby_itmx)src_pool.Get_at(i);
			Xoa_ttl ttl = itmx.Ttl();
			byte[] ttl_bry = ttl.Page_db();
			if (excluded.Has(ttl_bry)) continue;
			if (visited.Has(ttl_bry)) continue;
			visited.Add_bry_bry(ttl_bry);
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);
			if (page.Db().Page().Exists_n()) continue;
			wiki.Parser_mgr().Parse(page, true);
			Ordered_hash lnkis = Ordered_hash_.New_bry();
			Collect_lnkis(lnkis, page.Root());
			if (lnkis.Has(trg)) {
				++results_cur;
				results.Add(new Nearby_rslt(itmx.Trail(), trg_ttl));
				if (results_cur == results_max) return;
			}
			int lnkis_len = lnkis.Count();
			for (int j = 0; j < lnkis_len; j++) {
				Xoa_ttl lnki_ttl = (Xoa_ttl)lnkis.Get_at(j);
				if (next_pool.Has(lnki_ttl.Page_db())) continue;
				Nearby_itmx next_itmx = new Nearby_itmx(itmx.Trail(), lnki_ttl);
				next_pool.Add(lnki_ttl.Page_db(), next_itmx);
			}
		}
		if (next_pool.Count() > 0)
			Examine_page(next_pool);
//			++pages_count;
//			wiki.Parser_mgr().Parse(page, true);
//			Ordered_hash lnkis = Ordered_hash_.New_bry();
//			int len = lnkis.Count();
//			for (int i = 0; i < len; i++) {
//				Xoa_ttl lnki_ttl = (Xoa_ttl)lnkis.Get_at(i);
//				if (!lnki_ttl.Ns().Id_main()) continue;
//				if (Bry_.Eq(lnki_ttl.Page_db(), trg)) continue;	// skip trg page 
//				trail.Add(lnki_ttl);
//				Examine_page(wiki, lnki_ttl, trail);
//				List_adp_.Del_at_last(trail);
//				if (results_cur == results_max) return;
//			}
	}
//		private void Examine_page(Xowe_wiki wiki, Xoa_ttl ttl, List_adp trail){
//			byte[] ttl_bry = ttl.Page_db();
//			if (excluded.Has(ttl_bry)) return;
//			if (visited.Has(ttl_bry)) return;
//			visited.Add_bry_bry(ttl_bry);
//			Xoae_page page = wiki.Data_mgr().Get_page(ttl, false);
//			if (page.Missing()) return;
//			++pages_count;
//			wiki.Parser_mgr().Parse(page, true);
//			Ordered_hash lnkis = Ordered_hash_.New_bry();
//			Collect_lnkis(lnkis, page.Root());
//			if (lnkis.Has(trg)) {
//				++results_cur;
//				results.Add(new Nearby_rslt(trail, trg_ttl));
//				if (results_cur == results_max) return;
//			}
//			int len = lnkis.Count();
//			for (int i = 0; i < len; i++) {
//				Xoa_ttl lnki_ttl = (Xoa_ttl)lnkis.Get_at(i);
//				if (!lnki_ttl.Ns().Id_main()) continue;
//				if (Bry_.Eq(lnki_ttl.Page_db(), trg)) continue;	// skip trg page 
//				trail.Add(lnki_ttl);
//				Examine_page(wiki, lnki_ttl, trail);
//				List_adp_.Del_at_last(trail);
//				if (results_cur == results_max) return;
//			}
//		}
	private void Collect_lnkis(Ordered_hash lnkis, Xop_tkn_itm tkn) {
		if (tkn.Tkn_tid() == Xop_tkn_itm_.Tid_lnki) {
			Xop_lnki_tkn lnki_tkn = (Xop_lnki_tkn)tkn;
			Xoa_ttl lnki_ttl = lnki_tkn.Ttl();
			if (!lnkis.Has(lnki_ttl.Page_db()))
				lnkis.Add(lnki_ttl.Page_db(), lnki_ttl);
		}
		else {
			int len = tkn.Subs_len();
			for (int i = 0; i < len; i++) {
				Xop_tkn_itm sub = tkn.Subs_get(i);
				Collect_lnkis(lnkis, sub);
			}
		}
	}

	public Xow_special_page Special__clone() {return this;}
}
class Nearby_rslt {
	public Nearby_rslt(List_adp trail, Xoa_ttl trg_ttl) {
		int len = trail.Count();
		for (int i = 0; i < len; i++) {
			Xoa_ttl ttl = (Xoa_ttl)trail.Get_at(i);
			list.Add(ttl);
		}
		list.Add(trg_ttl);
	}
	public int Len() {return list.Count();}
	public Xoa_ttl Get_at(int i) {return (Xoa_ttl)list.Get_at(i);}
	List_adp list = List_adp_.New();	
}
class Nearby_itmx {
	public Nearby_itmx(List_adp v, Xoa_ttl ttl) {
		int len = v.Count();
		for (int i = 0; i < len; i++) {
			Xoa_ttl v_ttl = (Xoa_ttl)v.Get_at(i);
			trail.Add(v_ttl);
		}
		trail.Add(ttl);
		this.ttl = ttl;
	}
	public Xoa_ttl Ttl() {return ttl;} private Xoa_ttl ttl;
	public List_adp Trail() {return trail;} List_adp trail = List_adp_.New();	
}
