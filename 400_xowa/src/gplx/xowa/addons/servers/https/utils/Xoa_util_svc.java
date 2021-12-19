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
package gplx.xowa.addons.servers.https.utils;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.errs.ErrUtl;
import gplx.xowa.*;
import gplx.langs.jsons.*;
import gplx.xowa.wikis.caches.*;
import gplx.xowa.guis.cbks.*;
public class Xoa_util_svc {
	private final Xoa_app app;
	private final Xog_cbk_mgr cbk_mgr;
	public Xoa_util_svc(Xoa_app app) {
		this.app = app;
		this.cbk_mgr = app.Gui__cbk_mgr();
	}
	public void Page_get(Json_nde args) {
		// get args
		String page_get_cbk = args.Get_as_str("page_get_cbk");
		byte[] protocol = args.Get_as_bry("protocol");
		byte[] wiki_bry = args.Get_as_bry("wiki");
		byte[] page_bry = args.Get_as_bry("page");
		String vega_cbk_guid = args.Get_as_str("vega_cbk_guid");
		String page_guid = args.Get_as_str("page_guid");

		// get wiki
		Xow_wiki wiki_base = app.Wiki_mgri().Get_by_or_make_init_y(wiki_bry);
		if (!wiki_base.Type_is_edit()) {
			throw ErrUtl.NewUnimplemented();
		}
		Xowe_wiki wiki = (Xowe_wiki)wiki_base;

		// get page
		Xoa_ttl ttl = wiki.Ttl_parse(page_bry);
		Xow_page_cache_itm page_itm = wiki.Cache_mgr().Page_cache().Get_itm_else_load_or_null(ttl);
		byte[] page_text = page_itm == null ? null : page_itm.Wtxt__direct();
		if (page_text == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Xoa_utl_svc:page not found: wiki=~{0} page=~{1}", wiki_bry, page_bry);
		}

		Xog_cbk_trg cbk_trg = Xog_cbk_trg.New_by_guid(page_guid);
		cbk_mgr.Send_json(cbk_trg, page_get_cbk, gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("protocol", protocol)
			.Add_bry("wiki", wiki_bry)
			.Add_bry("page", page_bry)
			.Add_str("vega_cbk_guid", vega_cbk_guid)
			.Add_bry("page_text", page_text)
			);
	}
}
