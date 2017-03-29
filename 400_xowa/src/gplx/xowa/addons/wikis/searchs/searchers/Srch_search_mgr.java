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
import gplx.xowa.addons.wikis.searchs.dbs.*;
import gplx.xowa.addons.wikis.searchs.searchers.rslts.*; import gplx.xowa.addons.wikis.searchs.searchers.wkrs.*; import gplx.xowa.addons.wikis.searchs.parsers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.visitors.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
public class Srch_search_mgr implements Gfo_invk {
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
	private boolean upgrade_prompted;
	public Srch_search_mgr(Srch_search_addon addon, Xow_wiki wiki, Srch_text_parser parser) {
		this.addon = addon; this.wiki = wiki;
		crt_parser = new Srch_crt_parser(Srch_crt_scanner_syms.Dflt);	// NOTE: hard-coded to dflt; should change to use qry.Phrase.Syms, but requires more work
		
		// init cur_cmds with Noop cmd to make cancel logic below easier
		int len = Srch_search_qry.Tid_len;
		this.cur_cmds = new Srch_search_cmd[Srch_search_qry.Tid_len];
		for (int i = 0; i < len; ++i)
			cur_cmds[i] = Srch_search_cmd.Noop();

		wiki.App().Cfg().Bind_many_wiki(this, wiki, Cfg__args_default);
	}
	public void Clear_rslts_cache() {cache__rslts.Clear();}
	public void Search_cancel() {
		cur_cmds[Srch_search_qry.Tid__suggest_box].Cancel();
	}
	public void Search(Srch_search_qry qry, Srch_rslt_cbk cbk) {	// NOTE: main entry point for search
		if (qry.Phrase.Orig.length == 0) return;

		// handle obsolete search dbs;
		if (addon.Db_mgr().Cfg().Version_id__needs_upgrade()
			&& !upgrade_prompted) {
			upgrade_prompted = true;
			Srch_db_upgrade upgrade_mgr = new Srch_db_upgrade(wiki, addon.Db_mgr());
			upgrade_mgr.Upgrade();
			return;
		}

		// cancel existing cmd
		Srch_search_cmd cur_cmd = cur_cmds[qry.Tid];
		cur_cmd.Cancel();

		// create new one; run it;
		Srch_crt_mgr crt_mgr = crt_parser.Parse_or_invalid(qry.Phrase.Compiled);
		if (crt_mgr == Srch_crt_mgr.Invalid) return;	// handle "\\" which is invalid or other fatal errors
		Srch_rslt_list rslts_list = cache__rslts.Get_or_new(crt_mgr.Key);
		cur_cmd = new Srch_search_cmd(this, qry, crt_mgr, cbk, rslts_list);
		cur_cmds[qry.Tid] = cur_cmd;
		if (wiki.App().Mode().Tid_is_http())	// FUTURE: use api async flag instead; WHEN: long polling support
			cur_cmd.Search();
		else
			gplx.core.threads.Thread_adp_.Start_by_key(gplx.xowa.apps.Xoa_thread_.Key_special_suggest, cur_cmd, Srch_search_cmd.Invk__search);
	}
	public void Search_async(Cancelable cxl, Srch_search_qry qry, Srch_crt_mgr crt_mgr, Srch_rslt_cbk rslt_cbk, Srch_rslt_list rslts_list) {
		synchronized (mutex) {	// force only one search at a time; do not (a) place around Thread_sleep; (b) reuse for any other locks
			if (++search_count > 64) this.Clear();	// lazy way of clearing memory
			Srch_search_ctx ctx = new Srch_search_ctx(cxl, wiki, addon, cache__page, cache__word_counts, qry, qry.Phrase.Syms, crt_mgr, rslts_list);
			ctx.Score_rng.Select_init(ctx.Rslts_needed, rslts_list.Score_bgn, rslts_list.Score_len, Srch_link_wkr.Percentile_rng__calc_adj(crt_mgr.Words_nth__len()));
			page_tbl_searcher.Search(ctx, rslt_cbk);
			if (cxl.Canceled()) return;
			Srch_link_wkr link_wkr = new Srch_link_wkr();
			link_wkr.Search(rslts_list, rslt_cbk, ctx);
		}
	}
	private void Clear() {
		Gfo_usr_dlg_.Instance.Log_many("", "", "search.clear");
		search_count = 0;
		cache__page.Clear();
		cache__word_counts.Clear();
		cache__rslts.Clear();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Cfg__args_default))		this.Clear_rslts_cache();	// NOTE: must clear cache after args_dflt changed
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Cfg__args_default			= "xowa.addon.search.special.args_default";
}
