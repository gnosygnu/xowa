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
package gplx.xowa.wikis.xwikis.sitelinks.htmls;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.*;
import gplx.xowa.wikis.xwikis.sitelinks.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.domains.*;
class Xoa_sitelink_itm_wtr implements BryBfrArg {
	private final BryWtr tmp_bfr = BryWtr.NewWithSize(255);
	private Xoa_app app; private Xoa_sitelink_grp grp;
	public void Init_by_app(Xoa_app app) {this.app = app;}
	public Xoa_sitelink_itm_wtr Fmt__init(Xoa_sitelink_grp grp) {this.grp = grp; return this;}
	public void AddToBfr(BryWtr bfr) {
		int len = grp.Len();
		boolean tr_opened = false; int td_idx = 0;
		for (int i = 0; i < len; ++i) {
			Xoa_sitelink_itm itm = grp.Get_at(i);
			if (BryUtl.IsNullOrEmpty(itm.Page_name())) continue;		// xwiki does not exist for current page
			if (td_idx == 0) {
				bfr.Add(tr_bgn);
				tr_opened = true;
			}
			Xow_domain_itm domain_itm = itm.Site_domain();
			byte[] domain_bry = domain_itm.Domain_bry();
			byte[] page_name = itm.Page_name();
			byte[] url_protocol = app.Xwiki_mgr__missing(domain_bry) ? Xoh_href_.Bry__https : Xoh_href_.Bry__site;	// if wiki exists, "/site/", else "https://"
			byte[] url_page = itm.Page_name_is_empty() ? BryUtl.Empty : page_name;
			byte[] url = BryUtl.Add(url_protocol, domain_bry, Xoh_href_.Bry__wiki, url_page);
			td_fmtr.BldToBfrMany(bfr, domain_itm.Lang_actl_key(), domain_itm.Domain_bry(), itm.Name(), url, page_name, Xoa_sitelink_itm_wtr__badge.Bld_badge_class(tmp_bfr, itm.Page_badges()));
			++td_idx;
			if (td_idx == td_max) {
				tr_opened = false;
				bfr.Add(tr_end);
				td_idx = 0;
			}
		}
		if (tr_opened) {
			for (int i = td_idx; i < td_max; ++i) 
				bfr.Add(td_nil);
			bfr.Add(tr_end);
		}
	}
	private static final int td_max = 3;
	private static final byte[] tr_bgn = BryUtl.NewA7("\n    <tr>");
	private static final byte[] td_nil = BryUtl.NewA7("\n      <td/>");
	private static final byte[] tr_end = BryUtl.NewA7("\n    </tr>");
	private static final BryFmtr td_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "      <td style='width: 10%; padding-bottom: 5px;'>~{lang_name}</td><td style='width: 20%; padding-bottom: 5px;'><li~{page_badge}><a hreflang=\"~{lang_code}\" title=\"~{pagename_translation}\" href=\"~{lang_href}\">~{pagename_translation}</a></li></td><td style='width: 3%; padding-bottom: 5px;'></td>"
	), "lang_code", "lang_domain", "lang_name", "lang_href", "pagename_translation", "page_badge");
}
