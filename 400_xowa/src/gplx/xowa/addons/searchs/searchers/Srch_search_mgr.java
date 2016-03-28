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
import gplx.xowa.addons.searchs.searchers.rslts.*; import gplx.xowa.addons.searchs.searchers.wkrs.*; import gplx.xowa.addons.searchs.parsers.*; import gplx.xowa.addons.searchs.searchers.crts.*;
import gplx.xowa.addons.searchs.searchers.crts.visitors.*;
public class Srch_search_mgr {
	private final    Srch_search_addon			addon;
	private final    Xow_wiki					wiki;
	private final    Srch_rslt_list				cache__page = new Srch_rslt_list();
	private final    Hash_adp_bry				cache__word_counts = Hash_adp_bry.cs();
	private final    Srch_rslt_cache			cache__rslts = new Srch_rslt_cache();
	private final    Srch_page_tbl_wkr			page_tbl_searcher = new Srch_page_tbl_wkr();
	private final    Srch_crt_parser			crt_parser;
	private final    Srch_search_cmd[]			cur_cmds;
	private final    Object						mutex = new Object();
	private int search_count;
	public Srch_search_mgr(Srch_search_addon addon, Xow_wiki wiki, Srch_text_parser parser) {
		this.addon = addon; this.wiki = wiki;
		crt_parser = new Srch_crt_parser(Scanner_syms);
		
		// init cur_cmds with Noop cmd to make cancel logic below easier
		int len = Srch_search_qry.Tid_len;
		this.cur_cmds = new Srch_search_cmd[Srch_search_qry.Tid_len];
		for (int i = 0; i < len; ++i)
			cur_cmds[i] = Srch_search_cmd.Noop();
	}
	public void Clear_rslts_cache() {cache__rslts.Clear();}
	public void Search_cancel() {
		cur_cmds[Srch_search_qry.Tid__suggest_box].Cancel();
	}
	public void Search(Srch_search_qry qry, Srch_rslt_cbk cbk) {
		if (qry.Phrase.Orig.length == 0) return;

		// handle obsolete search dbs;
		if (addon.Db_mgr().Cfg().Version_id__needs_upgrade()) {
			addon.Db_mgr().Upgrade_mgr.Upgrade();
			return;
		}

		// cancel existing cmd
		Srch_search_cmd cur_cmd = cur_cmds[qry.Tid];
		cur_cmd.Cancel();

		// create new one; run it;
		Srch_crt_mgr crt_mgr = crt_parser.Parse_or_invalid(qry.Phrase.Compiled, qry.Phrase.Wildcard);
		if (crt_mgr == Srch_crt_mgr.Invalid) return;	// handle "\\" which is invalid or other fatal errors
		Srch_rslt_list rslts_list = cache__rslts.Get_or_new(crt_mgr.Key);
		cur_cmd = new Srch_search_cmd(this, qry, crt_mgr, cbk, rslts_list);
		cur_cmds[qry.Tid] = cur_cmd;
		gplx.core.threads.Thread_adp_.invk_(gplx.xowa.apps.Xoa_thread_.Key_special_suggest, cur_cmd, Srch_search_cmd.Invk__search).Start();
	}
	public void Search_async(Cancelable cxl, Srch_search_qry qry, Srch_crt_mgr crt_mgr, Srch_rslt_cbk rslt_cbk, Srch_rslt_list rslts_list) {
		synchronized (mutex) {	// force only one search at a time; do not (a) place around Thread_sleep; (b) reuse for any other locks
			if (++search_count > 64) this.Clear();	// lazy way of clearing memory
			Srch_search_ctx ctx = new Srch_search_ctx(cxl, wiki, addon, cache__page, cache__word_counts, qry, Scanner_syms, crt_mgr, rslts_list);
			ctx.Score_rng.Select_init(ctx.Rslts_needed, rslts_list.Score_bgn, rslts_list.Score_len, Srch_link_wkr.Percentile_rng__calc_adj(crt_mgr.Words_nth__len()));
			page_tbl_searcher.Search(ctx, rslt_cbk);
			if (cxl.Canceled()) return;
			Srch_link_wkr link_wkr = new Srch_link_wkr();
			link_wkr.Search(rslts_list, rslt_cbk, ctx);
		}
	}
	private void Clear() {
		search_count = 0;
		cache__page.Clear();
		cache__word_counts.Clear();
		cache__rslts.Clear();
	}
	public static final    Srch_crt_scanner_syms Scanner_syms = Srch_crt_scanner_syms.Dflt;
}
