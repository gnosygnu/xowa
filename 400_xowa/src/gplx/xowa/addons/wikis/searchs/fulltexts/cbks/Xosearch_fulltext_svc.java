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
import gplx.dbs.*;
import gplx.xowa.addons.wikis.searchs.fulltexts.specials.*;
import gplx.xowa.wikis.data.tbls.*;
class Xosearch_fulltext_svc {
	private gplx.xowa.guis.cbks.Xog_cbk_trg cbk_trg = gplx.xowa.guis.cbks.Xog_cbk_trg.New(Xosearch_fulltext_special.Prototype.Special__meta().Ttl_bry());
	private final    Xoa_app app;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Xosearch_fulltext_svc(Xoa_app app) {
		this.app = app;
	}
	public void Search(Json_nde args) {
		String wikis = args.Get_as_str("wikis");
		byte[] wildcard = Bry_.new_a7("%");
		byte[] query_raw = args.Get_as_bry("query");
		byte[] query_sql = Bry_.Add(wildcard, query_raw, wildcard);

		String[] wikis_ary = String_.Split(wikis, "|");
		for (String wiki_domain : wikis_ary) {
			Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(Bry_.new_u8(wiki_domain));
			Search_wiki(wiki, query_raw, query_sql);
		}
	}
	private void Search_wiki(Xow_wiki wiki, byte[] query_raw, byte[] query_sql) {
		Db_conn page_conn = wiki.Data__core_mgr().Tbl__page().Conn();
		Db_rdr page_rdr = page_conn.Stmt_sql("SELECT * FROM page WHERE page_namespace IN (0) ORDER BY page_score DESC").Exec_select__rls_auto();
		
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki.Domain_bry())
			.Add_long("page_count", wiki.Stats().Num_pages())
			);

		try {
			int found =0;
			while (page_rdr.Move_next()) {
				int page_id = page_rdr.Read_int("page_id");
				int text_db_id = page_rdr.Read_int("page_text_db_id");
				byte[] text = wiki.Data__core_mgr().Dbs__get_by_id_or_fail(text_db_id).Tbl__text().Select(page_id);
				if (Bry_.Has(text, query_raw)) {
					Xowd_text_row text_row = new Xowd_text_row(page_id, text);
					int ns_id = page_rdr.Read_int("page_namespace");
					byte[] ttl_bry = page_rdr.Read_bry_by_str("page_title");
					app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__wiki__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
						.Add_bry("wiki", wiki.Domain_bry())
						.Add_int("found", ++found)
						);
					Write(wiki, query_raw, wiki.Ttl_parse(ns_id, ttl_bry), text_row);
				}
			}
		} finally {
			page_rdr.Rls();
		}
	}
	private void Write(Xow_wiki wiki, byte[] query_raw, Xoa_ttl ttl, Xowd_text_row text_row) {
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__page__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki.Domain_bry())
			.Add_bry("page", ttl.Full_db())
			.Add_int("found", 0)
			);
		byte[] text_orig = text_row.text;
		byte[] text_lcase = wiki.Lang().Case_mgr().Case_build_lower(text_orig);
		int pos = 0;
		int found = 0;
		while (true) {
			int find_bgn = Bry_find_.Find_fwd(text_lcase, query_raw, pos);
			if (find_bgn == Bry_find_.Not_found)
				break;

			int snip_bgn = find_bgn - 50;
			if (snip_bgn < 0)
				snip_bgn = 0;
			else {
				snip_bgn = Bry_find_.Find_bwd_ws(text_orig, snip_bgn, 0) + 1;
			}
			int find_end = find_bgn + query_raw.length;
			int snip_end = find_end + 50;
			if (snip_end >= text_lcase.length)
				snip_end = text_lcase.length;
			else
				snip_end = Bry_find_.Find_fwd_until_ws(text_orig, snip_end, text_orig.length);

			Add_snip(tmp_bfr, text_orig, text_lcase, snip_bgn, snip_end, query_raw);
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__line__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_bry("wiki", wiki.Domain_bry())
				.Add_bry("page", ttl.Full_db())
				.Add_int("line", ++found)
				.Add_bry("html", tmp_bfr.To_bry_and_clear())
				);
			pos = snip_end;

			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__page__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_bry("wiki", wiki.Domain_bry())
				.Add_bry("page", ttl.Full_db())
				.Add_int("found", found)
				);
		}
	}
	private void Add_snip(Bry_bfr bfr, byte[] src_orig, byte[] src_lcase, int bgn, int end, byte[] qry) {
		for (int i = bgn; i < end; i++) {
			byte b = src_orig[i];
			if (b == Byte_ascii.Nl)
				bfr.Add(gplx.langs.htmls.Gfh_tag_.Br_inl);
			else {
				int qry_end = i + qry.length;
				if (Bry_.Eq(src_lcase, i, qry_end, qry)) {
					bfr.Add_str_a7("<span class='snip_highlight'>");
					bfr.Add_mid(src_orig, i, qry_end);
					bfr.Add_str_a7("</span>");
					i = qry_end - 1;
				}
				else
					bfr.Add_byte(b);
			}
		}
	}
}
//	class Xosearch_result_wiki {
//		public final    byte[] wiki;
//		public final    byte[] page_db;
//		public byte
//	}
