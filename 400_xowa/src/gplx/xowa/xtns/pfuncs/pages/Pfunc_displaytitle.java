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
package gplx.xowa.xtns.pfuncs.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.cases.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_displaytitle extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_page_displaytitle;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_displaytitle().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self);
		Xowe_wiki wiki = ctx.Wiki(); Xop_parser parser = wiki.Parser_mgr().Main();
		Xop_ctx display_ttl_ctx = Xop_ctx.New__sub__reuse_page(ctx);
		Xop_root_tkn display_ttl_root = parser.Parse_text_to_wdom(display_ttl_ctx, val_dat_ary, false);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		boolean restrict = wiki.Cfg_parser().Display_title_restrict();
		Xoh_wtr_ctx hctx = restrict ? Xoh_wtr_ctx.Display_title : Xoh_wtr_ctx.Basic;	// restrict removes certain HTML (display:none)
		wiki.Html_mgr().Html_wtr().Write_tkn_to_html(tmp_bfr, display_ttl_ctx, hctx, display_ttl_root.Data_mid(), display_ttl_root, 0, display_ttl_root);
		byte[] val_html = tmp_bfr.To_bry_and_clear();
		if (restrict) {	// restrict only allows displayTitles which have text similar to the pageTitle; PAGE:de.b:Kochbuch/_Druckversion; DATE:2014-08-18
			Xoae_page page = ctx.Page();
			wiki.Html_mgr().Html_wtr().Write_tkn_to_html(tmp_bfr, display_ttl_ctx, Xoh_wtr_ctx.Alt, display_ttl_root.Data_mid(), display_ttl_root, 0, display_ttl_root);
			byte[] val_html_lc = tmp_bfr.To_bry_and_clear();
			Xol_case_mgr case_mgr = wiki.Lang().Case_mgr();
			val_html_lc = Standardize_displaytitle_text(case_mgr, val_html_lc);
			byte[] page_ttl_lc = Standardize_displaytitle_text(case_mgr, page.Ttl().Page_db());
			if (!Bry_.Eq(val_html_lc, page_ttl_lc))
				val_html = null;
		}
		ctx.Page().Html_data().Display_ttl_(val_html);
		tmp_bfr.Mkr_rls();
	}
	private static byte[] Standardize_displaytitle_text(Xol_case_mgr case_mgr, byte[] val) {
		byte[] rv = case_mgr.Case_build_lower(val);							// lower-case
		return Bry_.Replace(rv, Byte_ascii.Space, Byte_ascii.Underline);	// force underline; PAGE:de.w:Mod_qos DATE:2014-11-06
	}
	public static final    Pfunc_displaytitle Instance = new Pfunc_displaytitle(); Pfunc_displaytitle() {}
}	
