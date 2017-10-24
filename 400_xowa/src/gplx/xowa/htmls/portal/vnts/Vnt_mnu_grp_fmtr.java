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
package gplx.xowa.htmls.portal.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.portal.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.langs.vnts.*;
public class Vnt_mnu_grp_fmtr implements gplx.core.brys.Bfr_arg {
	private final    Xolg_vnt_itm_fmtr itm_fmtr = new Xolg_vnt_itm_fmtr();
	private Xol_vnt_regy mgr; private byte[] page_vnt;
	public void Init(Xol_vnt_regy mgr, byte[] wiki_domain, byte[] page_href, byte[] page_vnt) {
		this.mgr = mgr; this.page_vnt = page_vnt;
		itm_fmtr.Init(mgr, wiki_domain, page_href, page_vnt);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		Xol_vnt_itm mnu_itm = mgr.Get_by(page_vnt);
		fmtr.Bld_bfr_many(bfr, mnu_itm == null ? Bry_.Empty : mnu_itm.Name(), itm_fmtr);
	}
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div id='p-variants' role='navigation' class='vectorMenu' aria-labelledby='p-variants-label'>"
	, "      <h3 id='p-variants-label'><span>~{grp_text}</span><a href='#'></a></h3>"
	, "      <div class='menu'>"
	, "        <ul>~{itms}"
	, "        </ul>"
	, "      </div>"
	, "    </div>"
	), "grp_text", "itms"
	);
}
class Xolg_vnt_itm_fmtr implements gplx.core.brys.Bfr_arg {
	private Xol_vnt_regy mgr; private byte[] wiki_domain, page_href, page_vnt;
	public void Init(Xol_vnt_regy mgr, byte[] wiki_domain, byte[] page_href, byte[] page_vnt) {this.mgr = mgr; this.wiki_domain = wiki_domain; this.page_href = page_href; this.page_vnt = page_vnt;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = mgr.Len();
		for (int i = 0; i < len; ++i) {
			Xol_vnt_itm itm = mgr.Get_at(i); if (!itm.Visible()) continue;
			boolean itm_is_selected = Bry_.Eq(itm.Key(), page_vnt);
			byte[] itm_cls_selected = itm_is_selected ? Itm_cls_selected_y : Bry_.Empty;
			fmtr.Bld_bfr_many(bfr, i, itm_cls_selected, wiki_domain, itm.Key(), itm.Name(), page_href);
		}
	}
	private static final    byte[] Itm_cls_selected_y = Bry_.new_a7(" class='selected'");
	private static final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last	// NOTE: using "/site/zh.w/zh-hans/A" instead of "/zh-hans/A" b/c it is easier for href_parser; if /site/ ever needs to truly mean "not-this-site", then change this to "/lang/"; DATE:2015-07-30
	( ""
	, "          <li id='ca-varlang-~{itm_idx}'~{itm_cls_selected}><a href='/site/~{wiki_domain}/~{itm_lang}/~{itm_href}' lang='~{itm_lang}' hreflang='~{itm_lang}' class='xowa-hover-off'>~{itm_text}</a></li>"
	), "itm_idx", "itm_cls_selected", "wiki_domain", "itm_lang", "itm_text", "itm_href"
	);
}
