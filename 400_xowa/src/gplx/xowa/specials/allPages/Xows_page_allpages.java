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
package gplx.xowa.specials.allPages; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.net.qargs.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.apps.urls.*;
public class Xows_page_allpages implements gplx.core.brys.Bfr_arg, Gfo_invk, Xow_special_page {
	public Xows_page_allpages(Xowe_wiki wiki) {
		this.wiki = wiki;
		html_itm_fmtr = new Xos_pagelist_html_itm_fmtr(this, wiki);
	}	private Xos_pagelist_html_itm_fmtr html_itm_fmtr;
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__all_pages;}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Bry_fmtr Html_all() {return html_all;} Bry_fmtr html_all = Bry_fmtr.new_(String_.Concat_lines_nl
		(	"<table class=\"mw-allpages-table-form\">"
		,	"  <tr>"
		,	"    <td class=\"mw-allpages-nav\">"
		,	"~{anchor_prv} |"
		,	"~{anchor_nxt}"
		,	"    </td>"
		,	"  </tr>"
		,	"</table>"
		,	"<table class=\"mw-allpages-table-chunk\">"
		,	"<tbody>~{grps}"
		,	"</tbody>"
		,	"</table>"
		,	"<hr/>"
		,	"<div class=\"mw-allpages-nav\">"
		,	"~{anchor_prv} |"
		,	"~{anchor_nxt}"
		,	"</div>"
		),	"grps", "anchor_prv", "anchor_nxt");
	public Bry_fmtr Html_list_grp() {return html_list_grp;} Bry_fmtr html_list_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
		(	""
		,	"  <tr>~{itms}"
		,	"  </tr>"
		),	"itms");
	public Bry_fmtr Html_list_itm_normal() {return html_list_itm_normal;} Bry_fmtr html_list_itm_normal = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
		(	""
		,	"    <td style=\"width:~{itm_pct}%\"><a~{lnki_atr_cls} href=\"~{lnki_href}\" title=\"~{lnki_title}\">~{lnki_text}</a></td>"
		),	"itm_pct", "lnki_href", "lnki_title", "lnki_text", "lnki_atr_cls");
	public Bry_fmtr Html_list_itm_redirect() {return html_list_itm_redirect;} Bry_fmtr html_list_itm_redirect = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
		(	""
		,	"    <td style=\"width:~{itm_pct}%\"><div class=\"allpagesredirect\"><a href=\"~{lnki_href}\" class=\"mw-redirect\" title=\"~{lnki_title}\">~{lnki_text}</a></div></td>"
		),	"itm_pct", "lnki_href", "lnki_title", "lnki_text");
	public Bry_fmtr Html_list_end() {return html_list_end;} Bry_fmtr html_list_end = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
		(	"<a href=\"/wiki/Special:AllPages?from=~{arg_from}~{args__rest}\" title=\"Special:AllPages\">~{lbl_text}</a>"
		),	"arg_from", "args__rest", "lbl_text");
	public int Itms_per_page() {return itms_per_page;} private int itms_per_page = 345;
	public void Itms_per_page_(int v) {itms_per_page = v; rslt_list_ttls = new Xowd_page_itm[v];}
	public int Itms_per_grp() {return itms_per_grp;} private int itms_per_grp = 3;
	public boolean List_include_redirects() {return show_redirects;} private boolean show_redirects = true;
	public Xowd_page_itm Rslt_prv() {return rslt_prv;} public void Rslt_prv_(Xowd_page_itm v) {rslt_prv = v;} private Xowd_page_itm rslt_prv;
	public Xowd_page_itm Rslt_nxt() {return rslt_nxt;} public void Rslt_nxt_(Xowd_page_itm v) {rslt_nxt = v;} private Xowd_page_itm rslt_nxt;
	public int Rslt_list_len() {return rslt_list_len;} public void Rslt_list_len_(int v) {rslt_list_len = v;} private int rslt_list_len;
	public Xowd_page_itm[] Rslt_list_ttls() {return rslt_list_ttls;} private Xowd_page_itm[] rslt_list_ttls;
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		wiki.Parser_mgr().Ctx().Page().Html_data().Display_ttl_(wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_sp_allpages_hdr));
		url.Page_bry_(Bry_.Add(Bry_.new_a7("Special:"), ttl.Page_txt_wo_qargs()));	// HACK: need to re-set Page b/c href_wtr does not eliminate qargs; DATE:2013-02-08
		if (rslt_list_ttls == null) this.Itms_per_page_(itms_per_page);
		boolean found = Build_data(url, ttl); if (!found) return;
		Build_html(page);
	}
	private static byte[] Get_from(Gfo_qarg_mgr_old arg_hash, Xowe_wiki wiki, Xoa_url url, Xoa_ttl ttl) {
		return ttl.Leaf_bgn() == -1
			? arg_hash.Get_val_bry_or(Bry_arg_from, null)
			: ttl.Leaf_url()
			;
	}
	public boolean Build_data(Xoa_url url, Xoa_ttl ttl) {
		init_ns = wiki.Ns_mgr().Ns_main();
		arg_hash.Load(url.Qargs_ary());
		byte[] from_val = Get_from(arg_hash, wiki, url, ttl); if (from_val == null) return false;
		from_val = gplx.langs.htmls.encoders.Gfo_url_encoder_.Id.Decode(from_val);
		int ns_val = arg_hash.Get_val_int_or(Bry_arg_ns, init_ns.Id()); init_ns = wiki.Ns_mgr().Ids_get_or_null(ns_val);
		boolean hide_redirects_val = arg_hash.Get_val_int_or(Bry_arg_hideredirects, 0) == 1; 
		for (int i = 0; i < itms_per_page; i++)
			rslt_list_ttls[i] = null;
		rslt_list_len = 0;
		rslt_nxt = rslt_prv = null;
		Xoa_ttl from_ttl = Xoa_ttl.Parse(wiki, from_val); if (from_ttl == null) return false;
		if (!from_ttl.Ns().Id_is_main()) {	// ns specified in title
			init_ns = from_ttl.Ns();
			arg_hash.Set_val_by_int(Bry_arg_ns, init_ns.Id());
			arg_hash.Set_val_by_bry(Bry_arg_from, from_ttl.Page_db());
			url.Qargs_ary_(arg_hash.To_ary());
		}
		Int_obj_ref rslt_len = Int_obj_ref.New(rslt_list_len);
		Xowd_page_itm rslt_nxt2 = new Xowd_page_itm();
		Xowd_page_itm rslt_prv2 = new Xowd_page_itm();
		int all_pages_min = 0;// no minimum for all pages
		List_adp rslt_list = List_adp_.New();
		wiki.Db_mgr().Load_mgr().Load_ttls_for_all_pages(Cancelable_.Never, rslt_list, rslt_nxt2, rslt_prv2, rslt_len, init_ns, from_ttl.Page_db(), itms_per_page, all_pages_min, itms_per_page, !hide_redirects_val, true);
		rslt_list_len = rslt_len.Val();
		for (int i = 0; i < rslt_list_len; i++)
			rslt_list_ttls[i] = (Xowd_page_itm)rslt_list.Get_at(i);
		rslt_nxt = rslt_nxt2;
		rslt_prv = rslt_prv2;
		return true;
	}	private Gfo_qarg_mgr_old arg_hash = new Gfo_qarg_mgr_old();
	private static final    byte[] Bry_arg_from = Bry_.new_a7("from"), Bry_arg_ns = Bry_.new_a7("namespace"), Bry_arg_hideredirects = Bry_.new_a7("hideredirects");
	public Xow_ns Init_ns() {return init_ns;} private Xow_ns init_ns;
	public void Build_html(Xoae_page page) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_m001();
		Bry_fmtr fmtr = Bry_fmtr.New__tmp();

		byte[] anchor_prv = Build_html_end(tmp_bfr, fmtr, rslt_prv, false);
		byte[] anchor_nxt = Build_html_end(tmp_bfr, fmtr, rslt_nxt, true);
		html_all.Bld_bfr_many(tmp_bfr, this, anchor_prv, anchor_nxt);
		page.Db().Text().Text_bry_(tmp_bfr.To_bry_and_clear());
		tmp_bfr.Mkr_rls();
		page.Html_data().Html_restricted_n_();
	}
	byte[] Build_html_end(Bry_bfr bfr, Bry_fmtr fmtr, Xowd_page_itm itm, boolean fwd) {
		Xoa_ttl ttl = Xows_page_allpages.ttl_(wiki, init_ns, itm); if (ttl == null) return Bry_.Empty;	// occurs when range is empty; EX: Module:A in simplewikibooks
		int msg_id = fwd ? Xol_msg_itm_.Id_sp_allpages_fwd : Xol_msg_itm_.Id_sp_allpages_bwd;
		Xol_msg_itm msg_itm = wiki.Lang().Msg_mgr().Itm_by_id_or_null(msg_id);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		try {
			byte[] lbl_text = msg_itm.Fmt(tmp_bfr, fmtr, ttl.Full_txt_w_ttl_case());
			byte[] args__rest = arg_hash.Concat(tmp_bfr, Bry_arg_ns, Bry_arg_hideredirects);
			byte[] arg_from = gplx.langs.htmls.encoders.Gfo_url_encoder_.Id.Encode(ttl.Page_txt_wo_qargs());
			return html_list_end.Bld_bry_many(bfr, arg_from, args__rest, lbl_text);
		} finally {tmp_bfr.Mkr_rls();}
	}
	public static Xoa_ttl ttl_(Xowe_wiki wiki, Xow_ns ns, Xowd_page_itm itm) {
		byte[] ttl_bry = itm.Ttl_page_db();
		if (!ns.Id_is_main()) ttl_bry = Bry_.Add(ns.Name_db_w_colon(), ttl_bry);
		return Xoa_ttl.Parse(wiki, ttl_bry);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = rslt_list_ttls.length;
		html_itm_fmtr.XferAry_bgn();
		for (int i = 0; i < len; i += itms_per_grp) {
			html_itm_fmtr.Itm_idx_(i);
			html_list_grp.Bld_bfr_many(bfr, html_itm_fmtr);
		}
		html_itm_fmtr.XferAry_end();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_html_all_)) 					html_all.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_list_grp_)) 			html_list_grp.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_list_itm_normal_)) 		html_list_itm_normal.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_html_list_itm_redirect_)) 	html_list_itm_redirect.Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_itms_per_page_)) 			Itms_per_page_(m.ReadInt("v"));
		else if	(ctx.Match(k, Invk_itms_per_grp_)) 				itms_per_grp = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_show_redirects_)) 			show_redirects = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_html_all_ = "html_all_", Invk_html_list_grp_ = "html_list_grp_", Invk_html_list_itm_normal_ = "html_list_itm_normal_", Invk_html_list_itm_redirect_ = "html_list_itm_redirect_"
	, Invk_itms_per_page_ = "itms_per_page_", Invk_itms_per_grp_ = "itms_per_grp_", Invk_show_redirects_ = "show_redirects_";
	public static final    String GRP_KEY = "xowa.special.allpages";

	public Xow_special_page Special__clone() {return this;}
}
class Xos_pagelist_html_itm_fmtr implements gplx.core.brys.Bfr_arg {
	public Xos_pagelist_html_itm_fmtr(Xows_page_allpages mgr, Xowe_wiki wiki) {
		this.mgr = mgr; this.wiki = wiki; this.href_wtr = wiki.Html__href_wtr(); this.wiki_key = wiki.Domain_bry();
		this.itm_normal = mgr.Html_list_itm_normal(); this.itm_redirect = mgr.Html_list_itm_redirect();
		history_mgr = wiki.Appe().Usere().History_mgr();
	}	private Xows_page_allpages mgr; Xowe_wiki wiki; Xoh_href_wtr href_wtr; Bry_fmtr itm_normal, itm_redirect; byte[] wiki_key; gplx.xowa.users.history.Xou_history_mgr history_mgr;
	public void Itm_idx_(int v) {itm_idx = v;} private int itm_idx;
	public void XferAry_bgn() {
		itms_per_grp = mgr.Itms_per_grp();
		ttls = mgr.Rslt_list_ttls();
		ttls_len = ttls.length;
		itm_pct = 100 / itms_per_grp;
		init_ns = mgr.Init_ns();
	} 	int itms_per_grp, ttls_len, itm_pct; Xowd_page_itm[] ttls; Xow_ns init_ns;
	public void XferAry_end() {ttls = null;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int itm_end = itm_idx + itms_per_grp;
		for (int i = itm_idx; i < itm_end; i++) {
			if (i >= ttls_len) break;	// handle odd number of itms; EX: interval=3; items=4; proc gets called on 0 (0-2) and 3 (3-5); ArrayIndex for 4, 5 
			Xowd_page_itm ttl_itm = ttls[i];
			if (ttl_itm == null) break; // ttl_itm can be null at bgn or end of title list; EX: list=A-Z; count=5; key=Z; itms=X,Y,Z,null,null
			Xoa_ttl ttl = Xows_page_allpages.ttl_(wiki, init_ns, ttl_itm);
			byte[] href = href_wtr.Build_to_bry(wiki, ttl);
			byte[] title = ttl.Full_txt_w_ttl_case();
			byte[] cls = Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki_key, ttl.Page_txt());	// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
			Bry_fmtr fmtr = ttl_itm.Redirected() ? itm_redirect : itm_normal;
			fmtr.Bld_bfr_many(bfr, itm_pct, href, title, ttl.Full_txt_w_ttl_case(), cls);
		}
	}
}
