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
package gplx.xowa.xtns.proofreadPage; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.tmpls.*;
class Pp_index_parser {
	public static Pp_index_page Parse(Xowe_wiki wiki, Xop_ctx ctx, Xoa_ttl index_ttl, int ns_page_id) {
		byte[] src = wiki.Cache_mgr().Page_cache().Get_or_load_as_src(index_ttl);
		if (src == null) return Pp_index_page.Null;
		Xop_parser sub_parser = Xop_parser.new_(wiki, wiki.Parser_mgr().Main().Tmpl_lxr_mgr(), wiki.Parser_mgr().Main().Wtxt_lxr_mgr());
		Xop_ctx sub_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		Xop_tkn_mkr tkn_mkr = sub_ctx.Tkn_mkr();
		Xop_root_tkn index_root = tkn_mkr.Root(src);
		byte[] mid_text = sub_parser.Expand_tmpl(index_root, sub_ctx, tkn_mkr, src);
		Pp_index_page rv = new Pp_index_page();
		Inspect_tmpl(rv, src, index_root, index_root.Subs_len(), ns_page_id, 1);
		sub_parser.Parse_wtxt_to_wdom(index_root, sub_ctx, tkn_mkr, mid_text, Xop_parser_.Doc_bgn_bos);
		rv.Src_(mid_text);
		Inspect_wiki(rv, mid_text, index_root, index_root.Subs_len(), ns_page_id, 1);	// changed from src to mid_text; DATE:2014-07-14
		return rv;
	}
	private static void Inspect_tmpl(Pp_index_page rv, byte[] src, Xop_tkn_itm_base owner, int owner_len, int ns_page_id, int depth) {
		for (int i = 0; i < owner_len; i++) {
			Xop_tkn_itm sub = owner.Subs_get(i);
			int sub_tid = sub.Tkn_tid();
			switch (sub_tid) {
				case Xop_tkn_itm_.Tid_tmpl_invk: {
					if (depth == 1) { // NOTE: only look at tmpls directly beneath root; note that this should be fine b/c [[Index:]] pages have a constrained form-fields GUI; ProofreadPage takes the form fields, and builds a template from it; DATE:2014-01-25
						Xot_invk_tkn invk = (Xot_invk_tkn)sub;
						int args_len = invk.Args_len();
						for (int j = 0; j < args_len; j++) {
							Arg_nde_tkn nde_tkn = invk.Args_get_by_idx(j);
							byte[] key = Get_bry(src, nde_tkn.Key_tkn());
							byte[] val = Get_bry(src, nde_tkn.Val_tkn());
							rv.Invk_args().Add(new Pp_index_arg(key, val));
						}
					}
					break;
				}
			}
			int sub_subs_len = sub.Subs_len();
			if (sub_subs_len > 0)
				Inspect_tmpl(rv, src, (Xop_tkn_itm_base)sub, sub_subs_len, ns_page_id, depth + 1);
		}
	}
	private static void Inspect_wiki(Pp_index_page rv, byte[] src, Xop_tkn_itm_base owner, int owner_len, int ns_page_id, int depth) {
		for (int i = 0; i < owner_len; i++) {
			Xop_tkn_itm sub = owner.Subs_get(i);
			int sub_tid = sub.Tkn_tid();
			switch (sub_tid) {
				case Xop_tkn_itm_.Tid_lnki: {
					Xop_lnki_tkn lnki = (Xop_lnki_tkn)sub;
					int sub_ns_id = lnki.Ns_id();
					if		(sub_ns_id == ns_page_id)		rv.Page_ttls().Add(lnki.Ttl());
					else if	(sub_ns_id == Xow_ns_.Tid__main)	rv.Main_lnkis().Add(lnki);
					break;
				}
				case Xop_tkn_itm_.Tid_xnde: {
					Xop_xnde_tkn xnde = (Xop_xnde_tkn)sub;
					if (xnde.Tag().Id() == Xop_xnde_tag_.Tid__pagelist)
						rv.Pagelist_xndes().Add(xnde);
					break;
				}
			}
			int sub_subs_len = sub.Subs_len();
			if (sub_subs_len > 0)
				Inspect_wiki(rv, src, (Xop_tkn_itm_base)sub, sub_subs_len, ns_page_id, depth + 1);
		}
	}
	private static byte[] Get_bry(byte[] src, Arg_itm_tkn itm) {
		return Bry_.Mid(src, itm.Dat_bgn(), itm.Dat_end());
	}
}
class Pp_index_page {
	public Pp_index_page() {}
	public byte[] Src() {return src;} public Pp_index_page Src_(byte[] v) {src = v; return this;} private byte[] src;
	public List_adp		Pagelist_xndes()	{return pagelist_xndes;} private List_adp pagelist_xndes = List_adp_.New();
	public List_adp		Page_ttls()			{return page_ttls;} private List_adp page_ttls = List_adp_.New();
	public List_adp		Main_lnkis()		{return main_lnkis;} private List_adp main_lnkis = List_adp_.New();
	public List_adp		Invk_args()			{return invk_args;} private List_adp invk_args = List_adp_.New();
	public Xoa_ttl[] Get_ttls_rng(Xowe_wiki wiki, int ns_page_id, byte[] bgn_page_bry, byte[] end_page_bry, Int_obj_ref bgn_page_ref, Int_obj_ref end_page_ref) {
		int list_len = page_ttls.Count(); if (list_len == 0) return Pp_pages_nde.Ttls_null;
		List_adp rv = List_adp_.New();
		Xoa_ttl bgn_page_ttl = new_ttl_(wiki, ns_page_id, bgn_page_bry), end_page_ttl = new_ttl_(wiki, ns_page_id, end_page_bry);
		boolean add = bgn_page_ttl == Xoa_ttl.Null;		// if from is missing, default to bgn; EX: <pages index=A to="A/5"/>
		for (int i = 0; i < list_len; i++) {			// REF.MW:ProofreadPageRenderer|renderPages
			Xoa_ttl ttl = (Xoa_ttl)page_ttls.Get_at(i);
			if (	ttl.Eq_page_db(bgn_page_ttl))	{add = Bool_.Y; bgn_page_ref.Val_(i);}
			if (add) rv.Add(ttl);
			if (	end_page_ttl != Xoa_ttl.Null		// if to is missing default to end;
				&&	ttl.Eq_page_db(end_page_ttl)
				)									{add = Bool_.N; end_page_ref.Val_(i);}
		}
		if (bgn_page_ref.Val() == -1) bgn_page_ref.Val_(0);				// NOTE: set "from" which will be passed to {{MediaWiki:Proofreadpage_header_template}}; DATE:2014-05-21
		if (end_page_ref.Val() == -1) end_page_ref.Val_(list_len - 1);  // NOTE: set "to"   which will be passed to {{MediaWiki:Proofreadpage_header_template}}; DATE:2014-05-21
		if (rv.Count() == 0) return Pp_pages_nde.Ttls_null;
		return (Xoa_ttl[])rv.To_ary(Xoa_ttl.class);
	}
	private static Xoa_ttl new_ttl_(Xowe_wiki wiki, int ns_page_id, byte[] bry) {return bry == null ? Xoa_ttl.Null : Xoa_ttl.Parse(wiki, ns_page_id, bry);}
	public static final    Pp_index_page Null = new Pp_index_page();
}
class Pp_index_arg {
	public Pp_index_arg(byte[] key, byte[] val) {this.key = key; this.val = val;}
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Val() {return val;} private byte[] val;
}
