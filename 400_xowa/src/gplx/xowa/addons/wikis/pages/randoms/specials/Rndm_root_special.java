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
package gplx.xowa.addons.wikis.pages.randoms.specials;

import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoa_page;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoa_url;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.specials.Xow_special_meta;
import gplx.xowa.specials.Xow_special_meta_;
import gplx.xowa.specials.Xow_special_page;
import gplx.xowa.wikis.data.tbls.Xowd_page_tbl;
import gplx.xowa.wikis.nss.Xow_ns;

public class Rndm_root_special implements Xow_special_page {
	@Override public Xow_special_meta Special__meta() {return new Xow_special_meta(Xow_special_meta_.Src__mw, SPECIAL_KEY);}
	@Override public Xow_special_page Special__clone() {return this;}
	@Override public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		// get ns
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		Xow_ns ns = wiki.Ns_mgr().Names_get_or_main(ttl.Rest_txt());

		// get random ttl
		String randomTitleString = selectRandomTitle(wiki, ns.Id());
		wiki.Data_mgr().RedirectWithoutLoading(page, ns.Gen_ttl(BryUtl.NewU8(randomTitleString)));
	}

	private String selectRandomTitle(Xow_wiki wiki, int ns_id) {
		// ISSUE#:719; find pages without "/" and not redirect
		// REF.MW:https://github.com/wikimedia/mediawiki/blob/master/includes/specials/SpecialRandomrootpage.php
		Xowd_page_tbl pageTbl = wiki.Data__core_mgr().Db__core().Tbl__page();
		String where = StringUtl.Format
		("p.{0} = {1} AND p.{2} = {3} AND p.{4} NOT LIKE '%/%'"
		, pageTbl.Fld_page_ns(), ns_id
		, pageTbl.Fld_redirect_id(), Xowd_page_tbl.INVALID_PAGE_ID
		, pageTbl.Fld_page_title()
		);

		return (String)pageTbl.Conn().WkrMgr().ExecRandomObj
		( pageTbl.Fld_page_title()
		, pageTbl.Tbl_name() + " p"
		, where
		);
	}

	public static final String SPECIAL_KEY = "RandomRootPage";
	public static final byte[] Display_ttl = BryUtl.NewA7("Random Root Page");
	public static final Xow_special_page Prototype = new Rndm_root_special();
}
