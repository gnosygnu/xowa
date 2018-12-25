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
package gplx.xowa.xtns.template_styles; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.caches.*;
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.parsers.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*;
import gplx.xowa.wikis.nss.*;
public class Template_styles_nde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte[] css_ttl_bry;
	private byte[] css_src;
	private Xoa_ttl css_ttl;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Xatr__src:			css_ttl_bry = xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		Xox_xnde_.Parse_xatrs(wiki, this, xatrs_hash, src, xnde);
		// get css_ttl
		css_ttl = wiki.Ttl_parse(css_ttl_bry);
		if (css_ttl == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Template_styles_nde.invalid_ttl: wiki=~{0} page=~{1} css_ttl=~{2}", wiki.Domain_bry(), ctx.Page().Url_bry_safe(), css_ttl_bry);
			return;
		}

		// assume "Template:" if no explicit ns and no ":"
		if (!css_ttl.ForceLiteralLink()  // no initial ":"
			&& css_ttl.Ns().Id_is_main()) {
			css_ttl = wiki.Ttl_parse(Bry_.Add(Xow_ns_.Bry__template_w_colon, css_ttl_bry));
		}

		// get page
		Xow_page_cache_itm page_itm = wiki.Cache_mgr().Page_cache().Get_or_load_as_itm_2(css_ttl);
		css_src = page_itm == null ? null : page_itm.Wtxt__direct();
		if (css_src == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Template_styles_nde.page_not_found: wiki=~{0} page=~{1} css_ttl=~{2}", wiki.Domain_bry(), ctx.Page().Url_bry_safe(), css_ttl_bry);
		}			
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (css_ttl == null) {
			bfr.Add_str_a7(formatTagError("Invalid title for TemplateStyles src attribute."));
		}
		else if (css_src == null) {
			bfr.Add_str_a7(formatTagError("Page " + String_.new_u8(css_ttl_bry) + " has no content."));
		}
		else {
			bfr.Add_str_a7("<style>");
			bfr.Add(css_src);
			bfr.Add_str_a7("</style>");
		}
	}
	private static String formatTagError(String msg) {
		// $parser->addTrackingCategory( 'templatestyles-page-error-category' );
		return "<strong class=\"error\">" 
			// + call_user_func_array( 'wfMessage', $msg )->inContentLanguage()->parse()
			+ msg
			+ "</strong>";
	}
	public static final byte Xatr__src = 0;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7().Add_str_byte("src", Xatr__src);
}
