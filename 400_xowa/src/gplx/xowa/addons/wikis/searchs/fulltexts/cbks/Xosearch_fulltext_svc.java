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
package gplx.xowa.addons.wikis.searchs.fulltexts.cbks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.langs.jsons.*;
import gplx.dbs.*; import gplx.xowa.specials.xowa.diags.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.specials.*;
import gplx.xowa.wikis.data.tbls.*;
class Xosearch_fulltext_svc {
	private gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New(Xosearch_fulltext_special.Prototype.Special__meta().Ttl_bry());
	private final    Xoa_app app;
	public Xosearch_fulltext_svc(Xoa_app app) {
		this.app = app;
	}
	public void Search(Json_nde args) {
		String wikis = args.Get_as_str("wikis");
		byte[] wildcard = Bry_.new_a7("%");
		byte[] query = Bry_.Add(wildcard, args.Get_as_bry("query"), wildcard);

		String[] wikis_ary = String_.Split(wikis, "|");
		for (String wiki_domain : wikis_ary) {
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(Bry_.new_u8(wiki_domain));
			Search_wiki(wiki, query);

		}
	}
	private void Search_wiki(Xow_wiki wiki, byte[] query) {
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki.Domain_bry())
			.Add_long("page_count", wiki.Stats().Num_pages())
			);
		Xowd_text_row[] text_rows = wiki.Data__core_mgr().Db__text().Tbl__text().Select_where(query);
		for (Xowd_text_row text_row : text_rows) {
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__page__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_bry("wiki", wiki.Domain_bry())
				.Add_int("page", text_row.page_id)
				.Add_int("lines", text_row.text.length)
				);
		}
	}
}
//	class Xosearch_result_wiki {
//		public final    byte[] wiki;
//		public final    byte[] page_db;
//		public byte
//	}
