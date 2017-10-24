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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.langs.kwds.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.caches.*;
public class Xot_invk_tkn_ {
	public static void Eval_func(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk invk, Bry_bfr bfr, Xot_defn defn, byte[] argx_ary) {
		Pf_func_base defn_func = (Pf_func_base)defn;
		int defn_func_id = defn_func.Id();
		defn_func = (Pf_func_base)defn_func.New(defn_func_id, defn_func.Name());	// NOTE: always make copy b/c argx_ary may be dynamic
		if (argx_ary != Bry_.Empty) defn_func.Argx_dat_(argx_ary);
		defn_func.Eval_argx(ctx, src, caller, invk);
		if (defn_func_id == Xol_kwd_grp_.Id_invoke)	// NOTE: if #invoke, set frame_ttl to argx, not name; EX:{{#invoke:A}}
			invk.Frame_ttl_(Bry_.Add(Xow_ns_.Bry__module_w_colon, Xoa_ttl.Replace_unders(defn_func.Argx_dat())));	// NOTE: always prepend "Module:" to frame_ttl; DATE:2014-06-13; NOTE: always use spaces; DATE:2014-08-14; always use canonical English "Module"; DATE:2015-11-09
		Bry_bfr bfr_func = Bry_bfr_.New();
		defn_func.Func_evaluate(bfr_func, ctx, caller, invk, src);
		if (caller.Rslt_is_redirect())			// do not prepend if page is redirect; EX:"#REDIRECT" x> "\n#REDIRECT" DATE:2014-07-11
			caller.Rslt_is_redirect_(false);	// reset flag; needed for TEST; kludgy, but Rslt_is_redirect is intended for single use
		else
			ctx.Page().Tmpl_prepend_mgr().End(ctx, bfr, bfr_func.Bfr(), bfr_func.Len(), Bool_.N);
		bfr.Add_bfr_and_clear(bfr_func);
	}
	public static void Bld_key(Xot_invk invk, byte[] name_ary, Bry_bfr bfr) {
		bfr.Clear();
		bfr.Add(name_ary);
		int args_len = invk.Args_len();
		for (int i = 0; i < args_len; i++) {
			Arg_nde_tkn nde = invk.Args_get_by_idx(i);
			bfr.Add_byte(Byte_ascii.Pipe);
			if (nde.KeyTkn_exists()) {
				bfr.Add(nde.Key_tkn().Dat_ary());
				bfr.Add_byte(Byte_ascii.Eq);
			}
			bfr.Add(nde.Val_tkn().Dat_ary());
		}
	}
	public static Xot_defn_tmpl Load_defn(Xowe_wiki wiki, Xop_ctx ctx, Xot_invk_tkn invk_tkn, Xoa_ttl ttl, byte[] name_ary) {	// recursive loading of templates
		Xow_page_cache_itm tmpl_page_itm = wiki.Cache_mgr().Page_cache().Get_or_load_as_itm(ttl);
		byte[] tmpl_page_bry = tmpl_page_itm == null ? null : tmpl_page_itm.Wtxt__direct();
		Xot_defn_tmpl rv = null;
		if (tmpl_page_bry != null) {
			byte old_parse_tid = ctx.Parse_tid(); // NOTE: reusing ctxs is a bad idea; will change Parse_tid and cause strange errors; however, keeping for PERF reasons
			Xow_ns ns_tmpl = wiki.Ns_mgr().Ns_template();
			rv = wiki.Parser_mgr().Main().Parse_text_to_defn_obj(Xop_ctx.New__sub(wiki, ctx, ctx.Page()), ctx.Tkn_mkr(), ns_tmpl, name_ary, tmpl_page_bry); // create new ctx so __NOTOC__ only applies to template, not page; PAGE:de.w:13._Jahrhundert DATE:2017-06-17
			Xoa_ttl tmpl_page_ttl = tmpl_page_itm.Ttl();
			byte[] frame_ttl = Bry_.Add(tmpl_page_ttl.Ns().Name_db(), Byte_ascii.Colon_bry, tmpl_page_ttl.Page_txt());	// NOTE: (1) must have ns (Full); (2) must be txt (space, not underscore); EX:Template:Location map+; DATE:2014-08-22; (3) must be local language; Russian "Шаблон" not English "Template"; PAGE:ru.w:Королевство_Нидерландов DATE:2016-11-23
			rv.Frame_ttl_(frame_ttl);								// set defn's frame_ttl; needed for redirect_trg; PAGE:en.w:Statutory_city; DATE:2014-08-22
			ctx.Parse_tid_(old_parse_tid);
			wiki.Cache_mgr().Defn_cache().Add(rv, ns_tmpl.Case_match());
		}
		return rv;
	}
	public static void Print_not_found__by_transclude(Bry_bfr bfr, Xow_ns_mgr ns_mgr, boolean name_has_template, byte[] name_ary) {// print missing as [[Missing]]; PAGE:en.d:a DATE:2016-06-24
		bfr.Add(Xop_tkn_.Lnki_bgn);
		if (name_has_template)
			bfr.Add(ns_mgr.Ns_template().Name_db()).Add_byte(Byte_ascii.Colon);
		bfr.Add(name_ary);
		bfr.Add(Xop_tkn_.Lnki_end);
	}
	public static void Print_not_found__w_template(Bry_bfr bfr, Xow_ns_mgr ns_mgr, byte[] name_ary) {	// print missing as [[:Template:Missing]]; REF:MW: Parser.php|braceSubstitution|$text = "[[:$titleText]]"; EX:en.d:Kazakhstan; DATE:2014-03-25
		bfr.Add(Xop_tkn_.Lnki_bgn).Add_byte(Byte_ascii.Colon);
		bfr.Add(ns_mgr.Ns_template().Name_db()).Add_byte(Byte_ascii.Colon);
		bfr.Add(name_ary).Add(Xop_tkn_.Lnki_end);
	}
}
