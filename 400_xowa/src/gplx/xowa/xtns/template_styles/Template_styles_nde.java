/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.template_styles;

import gplx.*;
import gplx.core.lists.hashs.Hash_adp__int;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.htmls.heads.Xoh_head_itm__css_dynamic;
import gplx.xowa.htmls.hxtns.blobs.Hxtn_blob_tbl;
import gplx.xowa.htmls.hxtns.pages.Hxtn_page_mgr;
import gplx.xowa.htmls.hxtns.wikis.Hxtn_wiki_itm;
import gplx.xowa.htmls.minifys.XoCssMin;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_owner2;
import gplx.xowa.parsers.xndes.Xop_xnde_tag;
import gplx.xowa.parsers.xndes.Xop_xnde_tkn;
import gplx.xowa.wikis.caches.Xow_page_cache_itm;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.xtns.Xox_xnde;
import gplx.xowa.xtns.Xox_xnde_;
public class Template_styles_nde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private byte[] css_ttl_bry;
	private byte[] css_src;
	private boolean css_ignore;
	private int css_page_id;
	private Xoa_ttl css_ttl;
	private static XoCssMin cssMin = new XoCssMin();
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Xatr__src:			css_ttl_bry = xatr.Val_as_bry(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);
		Xox_xnde_.Parse_xatrs(wiki, this, xatrs_hash, src, xnde);
		// get css_ttl
		css_ttl = css_ttl_bry == null ? null : wiki.Ttl_parse(css_ttl_bry); // must check for null ttl; EX:"<templatestyle src{{=}}A.css>"; PAGE:en.w:Switzerland; ISSUE#:416; DATE:2019-03-31
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
		Xow_page_cache_itm page_itm = wiki.Cache_mgr().Page_cache().Get_itm_else_load_or_null(css_ttl);
		if (page_itm != null) {
			css_src = page_itm.Wtxt__direct();
			css_page_id = page_itm.Page_id();

			// update css_page_ids
			Hash_adp__int css_page_ids = (Hash_adp__int)ctx.Page().Kv_data().Get_or_make(Template_styles_kv_itm.Instance);
			if (css_page_ids.Get_by_or_null(css_page_id) == null) {
				css_page_ids.Add(css_page_id, "");
			}
			else {
				css_ignore = true;
			}

		}
		if (css_src == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Template_styles_nde.page_not_found: wiki=~{0} page=~{1} css_ttl=~{2}", wiki.Domain_bry(), ctx.Page().Url_bry_safe(), css_ttl_bry);
		}
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (css_ttl == null) {
			bfr.Add_str_a7(formatTagError("Invalid title for TemplateStyles src attribute."));
			return;
		}
		if (css_src == null) {
			bfr.Add_str_a7(formatTagError("Page " + String_.new_u8(css_ttl_bry) + " has no content."));
			return;
		}

		if (!css_ignore) {
			Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_b512();
			try {
				html_head.Bld_many(tmp_bfr, css_page_id, Bry_.new_u8(cssMin.cssmin(String_.new_u8(css_src), -1)) );
				Xoh_head_itm__css_dynamic css_dynamic = ctx.Page().Html_data().Head_mgr().Itm__css_dynamic();
				css_dynamic.Enabled_y_();
				css_dynamic.Add(tmp_bfr.To_bry_and_clear());
			} finally {tmp_bfr.Mkr_rls();}

			if (hctx.Mode_is_hdump()) {
				int page_id = wpg.Db().Page().Id();
				Hxtn_page_mgr html_data_mgr = wpg.Wikie().Hxtn_mgr();
				html_data_mgr.Page_tbl__insert(page_id, Hxtn_page_mgr.Id__template_styles, css_page_id);
				html_data_mgr.Blob_tbl__insert(Hxtn_blob_tbl.Blob_tid__wtxt, Hxtn_wiki_itm.Tid__self, css_page_id, css_src);
			}
		}
	}
	private static String formatTagError(String msg) {
		// $parser->addTrackingCategory( 'templatestyles-page-error-category' );
		// + call_user_func_array( 'wfMessage', $msg )->inContentLanguage()->parse()
		return html_error.Bld_many_to_str_auto_bfr(msg);
	}

	public static final byte Xatr__src = 0;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7().Add_str_byte("src", Xatr__src);
	private static final    Bry_fmt
	  html_head  = Bry_fmt.Auto("\n/*TemplateStyles:r~{id}*/\n~{css}")
	, html_error = Bry_fmt.Auto("<strong class=\"error\">~{msg}</strong>")
	;
}
class Template_styles_kv_itm implements gplx.xowa.apps.kvs.Xoa_kv_itm {
	public String Kv__key() {return "TemplateStyles";}
	public Object Kv__val_make() {return new Hash_adp__int();}
        public static final    Template_styles_kv_itm Instance = new Template_styles_kv_itm(); Template_styles_kv_itm() {}
}
