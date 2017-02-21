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
package gplx.xowa.addons.wikis.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.langs.cases.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.searchs.dbs.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*; import gplx.xowa.addons.wikis.searchs.searchers.rslts.*; import gplx.dbs.percentiles.*; import gplx.xowa.addons.wikis.searchs.searchers.wkrs.*;
import gplx.xowa.addons.wikis.searchs.parsers.*;
public class Srch_search_ctx {
	public Srch_search_ctx(Cancelable cxl, Xow_wiki wiki, Srch_search_addon addon
		, Srch_rslt_list cache__page, Hash_adp_bry cache__word_counts
		, Srch_search_qry qry, Srch_crt_scanner_syms scanner_syms, Srch_crt_mgr crt_mgr, Srch_rslt_list rslts_list) {
		this.Cxl  = cxl;
		this.Wiki = wiki;
		this.Wiki_domain = wiki.Domain_bry();
		this.Case_mgr = wiki.Case_mgr();
		this.Addon = addon;
		this.Cache__page = cache__page;
		this.Cache__word_counts = cache__word_counts;
		this.Qry = qry;
		this.Scanner_syms = scanner_syms;
		this.Crt_mgr = crt_mgr;
		this.Crt_mgr__root = crt_mgr.Root;
		this.Rslts_list = rslts_list;
		this.Db__core = wiki.Data__core_mgr().Db__core();
		this.Tbl__page = Db__core.Tbl__page();
		this.Tbl__word = addon.Db_mgr().Tbl__word();
		this.Tbl__link__ary = addon.Db_mgr().Tbl__link__ary();
		long page_count = wiki.Stats().Num_pages();
		this.Score_rng.Init(page_count, addon.Db_mgr().Cfg().Link_score_max());
		int rslts_needed = qry.Slab_end - rslts_list.Len();
		if (rslts_needed < 0) rslts_needed = 0;
		this.Rslts_needed = rslts_needed;
		this.Highlight_mgr = new Srch_highlight_mgr(this.Case_mgr).Search_(qry.Phrase.Orig);
	}
	public final    Cancelable					Cxl;
	public final    Xow_wiki					Wiki;
	public final    byte[]						Wiki_domain;
	public final    Srch_search_addon			Addon;
	public final    Xol_case_mgr				Case_mgr;
	public final    Srch_rslt_list				Cache__page;
	public final    Hash_adp_bry				Cache__word_counts;
	public final    Xow_db_file					Db__core;
	public final    Xowd_page_tbl				Tbl__page;
	public final    Srch_word_tbl				Tbl__word;
	public final    Srch_link_tbl[]				Tbl__link__ary;
	public final    Srch_search_qry				Qry;
	public final    Srch_crt_scanner_syms		Scanner_syms;
	public final    Srch_rslt_list				Rslts_list;
	public final    int							Rslts_needed;
	public final    Percentile_rng				Score_rng = new Percentile_rng();
	public final    Srch_crt_mgr				Crt_mgr;
	public final    Srch_crt_itm				Crt_mgr__root;
	public final    Srch_highlight_mgr			Highlight_mgr;
}
