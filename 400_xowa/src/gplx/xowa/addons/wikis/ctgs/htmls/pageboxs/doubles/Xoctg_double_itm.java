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
package gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.doubles; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*;
import gplx.core.brys.fmts.*; import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.hrefs.*; import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.users.history.*;
public class Xoctg_double_itm implements gplx.core.brys.Bfr_arg {
	private final    List_adp itms = List_adp_.New();
	private Xow_wiki wiki; private Xoh_href_wtr href_wtr; private Xou_history_mgr history_mgr;
	public void Init_by_wiki(Xow_wiki wiki, Xou_history_mgr history_mgr) {
		this.wiki = wiki;
		this.href_wtr = wiki.Html__href_wtr();
		this.history_mgr = history_mgr;
	}
	public void Itms__clear() 						{itms.Clear();}
	public void Itms__add(Xoctg_pagebox_itm page) 	{itms.Add(page);}	
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = itms.Count();
		for (int i = 0; i < len; ++i) {
			Xoctg_pagebox_itm itm = (Xoctg_pagebox_itm)itms.Get_at(i);
			Xoa_ttl ttl = itm.Ttl();
			byte[] lnki_cls = Xoh_lnki_wtr.Lnki_cls_visited(history_mgr, wiki.Domain_bry(), ttl.Page_txt());// NOTE: must be ttl.Page_txt() in order to match Xou_history_mgr.Add
			byte[] lnki_href = href_wtr.Build_to_bry(wiki, ttl);
			byte[] lnki_text = ttl.Page_txt();
			byte[] lnki_ttl = ttl.Full_txt_w_ttl_case();

			// build title
//				tmp_bfr.Add(ttl.Full_txt_w_ttl_case());
//				tmp_bfr.Add_byte_space().Add_byte(Byte_ascii.Paren_bgn)	.Add_byte(Byte_ascii.Ltr_S).Add_byte_colon().Add_int_variable(itm.Count__subcs());
//				tmp_bfr.Add_byte_space().Add_byte(Byte_ascii.Comma)		.Add_byte(Byte_ascii.Ltr_P).Add_byte_colon().Add_int_variable(itm.Count__pages());
//				tmp_bfr.Add_byte_space().Add_byte(Byte_ascii.Comma)		.Add_byte(Byte_ascii.Ltr_F).Add_byte_colon().Add_int_variable(itm.Count__files());
//				tmp_bfr.Add_byte(Byte_ascii.Paren_end);
//				byte[] lnki_ttl = tmp_bfr.To_bry_and_clear();

			Fmt__itm.Bld_many(bfr, lnki_cls, lnki_href, lnki_ttl, lnki_text);
		}
	}
	private static final    Bry_fmt Fmt__itm = Bry_fmt.Auto_nl_skip_last
	( ""
	,     "<li>"
	,       "<a~{lnki_cls} href=\"~{lnki_href}\" title=\"~{lnki_ttl}\">~{lnki_text}</a>"
	,     "</li>"
	);
}
