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
package gplx.xowa.addons.wikis.pages.randoms.specials;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.*;
import gplx.xowa.specials.*; import gplx.xowa.wikis.nss.*;
public class Rndm_page_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		Xow_ns ns = wiki.Ns_mgr().Names_get_or_main(ttl.Rest_txt());
		// Rndm_addon.Get(wiki).Mgr().Get_rndm_page_by_ns(ns);

		byte[] random_ttl_bry = wiki.Db_mgr().Load_mgr().Find_random_ttl(ns);
		wiki.Data_mgr().Redirect(page, ns.Gen_ttl(random_ttl_bry)); // REDIRECT:as per Special:Random
	}

	public static final String SPECIAL_KEY = "Randompage";	// NOTE: needs to match lang.gfs
	public static final byte[] Display_ttl = BryUtl.NewA7("Random Page");
	public Xow_special_meta Special__meta() {return new Xow_special_meta(Xow_special_meta_.Src__mw, SPECIAL_KEY, "random");}
	public static final Xow_special_page Prototype = new Rndm_page_special();
	public Xow_special_page Special__clone() {return this;}
}
