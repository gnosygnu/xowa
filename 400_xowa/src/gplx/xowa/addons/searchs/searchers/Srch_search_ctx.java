/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*;
import gplx.xowa.langs.cases.*;
import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.searchs.dbs.*;
import gplx.xowa.addons.searchs.searchers.crts.*; import gplx.xowa.addons.searchs.searchers.rslts.*; import gplx.dbs.percentiles.*; import gplx.xowa.addons.searchs.searchers.wkrs.*;
import gplx.xowa.addons.searchs.parsers.*;
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
	public final    Xowd_db_file				Db__core;
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
