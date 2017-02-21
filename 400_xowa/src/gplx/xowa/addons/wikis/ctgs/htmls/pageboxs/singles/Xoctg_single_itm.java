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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.singles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*;
class Xoctg_single_itm implements Bfr_arg {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private Xow_wiki wiki;
	private Xoctg_pagebox_itm[] itms;
	public void Init_by_wiki(Xow_wiki wiki) {this.wiki = wiki;}
	public void Init_by_page(Xoctg_pagebox_itm[] itms) {this.itms = itms;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = itms.length;
		for (int i = 0; i < len; ++i) {
			Xoa_ttl ctg_ttl = itms[i].Ttl();
			wiki.Html__href_wtr().Build_to_bfr(tmp_bfr, wiki.App(), wiki.Domain_bry(), ctg_ttl);
			byte[] ctg_page_txt = ctg_ttl.Page_txt();
			Fmt__itm.Bld_many(bfr, tmp_bfr.To_bry_and_clear(), ctg_page_txt, ctg_page_txt);
		}
	}
	private static final    Bry_fmt Fmt__itm = Bry_fmt.Auto_nl_skip_last
	( ""
	,       "<li>"
	,         "<a href=\"~{itm_href}\" class=\"internal\" title=\"~{itm_title}\">~{itm_text}</a>"	
	,       "</li>"
	);
}
