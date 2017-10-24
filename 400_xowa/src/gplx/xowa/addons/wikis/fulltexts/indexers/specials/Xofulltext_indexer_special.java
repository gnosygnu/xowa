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
package gplx.xowa.addons.wikis.fulltexts.indexers.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.indexers.*;
import gplx.xowa.specials.*; import gplx.core.net.qargs.*;
import gplx.xowa.addons.apps.cfgs.*;
public class Xofulltext_indexer_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {
		// get qry if any
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		// get options and create page
		// Xocfg_mgr cfg_mgr = wiki.App().Cfg();
		new Xofulltext_indexer_html
		( url_args.Read_str_or("wikis", wiki.Domain_str())
		, url_args.Read_str_or("ns_ids", "0")
		, url_args.Read_str_or("idx_opt", gplx.gflucene.indexers.Gflucene_idx_opt.Docs_and_freqs.Key())
		).Bld_page_by_mustache(wiki.App(), page, this);
	}
	Xofulltext_indexer_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xofulltext_indexer_special(Xow_special_meta.New_xo("XowaSearchBuilder", "Indexer"));
}
