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
package gplx.xowa.addons.wikis.searchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.addons.wikis.searchs.dbs.*; import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.parsers.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*; import gplx.xowa.addons.wikis.searchs.searchers.cbks.*;
import gplx.xowa.addons.wikis.searchs.gui.urlbars.*;
import gplx.xowa.langs.cases.*;
public class Srch_search_addon implements Xoax_addon_itm, Srch_search_addon_api {
	private final    Srch_search_mgr search_mgr;
	public Srch_search_addon(Xow_wiki wiki) {
		this.wiki_domain = wiki.Domain_bry();
		this.db_mgr = new Srch_db_mgr(wiki.Data__core_mgr()).Init(wiki.Stats().Num_pages());
		this.ttl_parser = new Srch_text_parser().Init_for_ttl(wiki.Case_mgr());
		this.search_mgr = new Srch_search_mgr(this, wiki, ttl_parser);
	}
	public byte[]				Wiki_domain()	{return wiki_domain;}	private final    byte[] wiki_domain;
	public Srch_db_mgr			Db_mgr()		{return db_mgr;}		private final    Srch_db_mgr db_mgr;
	public Srch_text_parser		Ttl_parser()	{return ttl_parser;}	private final    Srch_text_parser ttl_parser;

	public void Search(Srch_search_qry qry, Srch_rslt_cbk cbk) {search_mgr.Search(qry, cbk);}
	public void Clear_rslts_cache() {search_mgr.Clear_rslts_cache();}
	public void Delete_links(int ns_id, int page_id) {
		if (!db_mgr.Tbl__word().conn.Meta_tbl_exists(Srch_word_tbl.TABLE_NAME)) return; // NOTE: personal_wikis may not have search_link; exit early else assert will fail; DATE:2017-02-15
		int search_link_db_id = db_mgr.Tbl__link__get_idx(ns_id);
		Srch_link_tbl search_link_tbl = db_mgr.Tbl__link__get_at(search_link_db_id);
		search_link_tbl.Delete(page_id);
		this.Clear_rslts_cache();
	}

	public static final int Score_max = 1000000;
	public static final byte Wildcard__star = Byte_ascii.Star;
	public static Srch_search_addon Get(Xow_wiki wiki) {
		Srch_search_addon rv = (Srch_search_addon)wiki.Addon_mgr().Itms__get_or_null(ADDON_KEY);
		if (rv == null) {
			rv = new Srch_search_addon(wiki);
			wiki.Addon_mgr().Itms__add(rv);
		}
		return rv;
	}
	
	public String Addon__key() {return ADDON_KEY;} private static final String ADDON_KEY = "xowa.apps.search";
}
