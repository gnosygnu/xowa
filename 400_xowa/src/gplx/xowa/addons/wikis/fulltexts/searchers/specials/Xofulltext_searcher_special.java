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
package gplx.xowa.addons.wikis.fulltexts.searchers.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.xowa.specials.*; import gplx.core.net.qargs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xofulltext_searcher_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		// get url_args, cfg_mgr
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());
		Xocfg_mgr cfg_mgr = wiki.App().Cfg();

		// redirect to Special:Search if index doesn't exist
		boolean lucene_exists = Io_mgr.Instance.ExistsDir(gplx.xowa.addons.wikis.fulltexts.Xosearch_fulltext_addon.Get_index_dir(wiki));
		if (   !gplx.core.envs.Op_sys.Cur().Tid_is_drd()  // desktop
			&& !lucene_exists // no lucene index
			&& !Int_.In(wiki.Domain_tid(), Xow_domain_tid_.Tid__home, Xow_domain_tid_.Tid__other) // not home or personal wiki
			&& !Bry_.Eq(url_args.Read_bry_or_null("force"), Bool_.Y_bry) // force=y is not present in url
			&& wiki.App().Cfg().Get_bool_app_or(gplx.xowa.apps.cfgs.Xocfg_win.Cfg__search__fallback_to_title, true) // cfg.fallback is enabled
			) {
			Xoa_ttl redirect_ttl = wiki.Ttl_parse(Bry_.new_u8("Special:Search?fulltext=y&search=" + url_args.Read_str_or("search", "")));
			Xoa_url redirect_url = wiki.Utl__url_parser().Parse(redirect_ttl.Full_db());
			page.Redirect_trail().Itms__add__article(redirect_url, redirect_ttl, null);
			return;
		}

		// create page
		Xofulltext_searcher_html html = new Xofulltext_searcher_html(cfg_mgr, url_args, wiki, page.Page_guid(), lucene_exists);
		html.Bld_page_by_mustache(wiki.App(), page, this);
	}
	Xofulltext_searcher_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xofulltext_searcher_special(Xow_special_meta.New_xo("XowaSearch", "Search"));
}
