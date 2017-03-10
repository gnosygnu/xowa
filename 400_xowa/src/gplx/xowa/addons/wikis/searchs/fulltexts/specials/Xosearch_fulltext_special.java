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
package gplx.xowa.addons.wikis.searchs.fulltexts.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.xowa.specials.*; import gplx.core.net.qargs.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xosearch_fulltext_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		// get qry if any
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());
		byte[] query = url_args.Read_bry_or("query", Bry_.Empty);

		// get options and create page
		Xocfg_mgr cfg_mgr = wiki.App().Cfg();
		new Xosearch_fulltext_html
		( query
		, cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.case_match", false)
		, cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.auto_wildcard_bgn", false)
		, cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.auto_wildcard_end", false)
		, cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.expand_matches_section", false)
		, cfg_mgr.Get_bool_app_or("xowa.addon.search.fulltext.special.show_all_matches", false)
		, cfg_mgr.Get_int_app_or ("xowa.addon.search.fulltext.special.max_pages_per_wiki", 100)
		, wiki.Domain_str()
		, cfg_mgr.Get_str_app_or ("xowa.addon.search.fulltext.special.namespaces", "0|4")
		).Bld_page_by_mustache(wiki.App(), page, this);
	}
	Xosearch_fulltext_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xosearch_fulltext_special(Xow_special_meta.New_xo("XowaSearch", "Search"));
}
