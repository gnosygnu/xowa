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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.gfui.*; import gplx.core.threads.*; import gplx.xowa.gui.*; import gplx.xowa.gui.views.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.core.js.*;
public class Xog_search_suggest_mgr implements GfoInvkAble {
	public Xog_search_suggest_mgr(Xoa_gui_mgr gui_mgr) {
		this.app = gui_mgr.App();
		this.main_win = gui_mgr.Browser_win();
		cur_cmd = new Xog_search_suggest_cmd(app, this);
	}	private Xoae_app app; private Xog_win_itm main_win; private Js_wtr wtr = new Js_wtr();
	private int results_max = 10; private boolean log_enabled = false;
	public boolean Enabled() {return enabled;} private boolean enabled = true;
	public byte Search_mode() {return search_mode;} public Xog_search_suggest_mgr Search_mode_(byte v) {search_mode = v; return this;} private byte search_mode = Tid_search_mode_all_pages_v2;
	public int All_pages_extend() {return all_pages_extend;} private int all_pages_extend = 1000;	// look ahead by 1000
	public int All_pages_min() {return all_pages_min;} private int all_pages_min = 10000;			// only look at pages > 10 kb
	public boolean Auto_wildcard() {return auto_wildcard;} private boolean auto_wildcard = false;			// automatically add wild-card; EX: Earth -> *Earth*
	public Gfo_url_arg[] Args_default() {return args_default;} private Gfo_url_arg[] args_default = Gfo_url_arg.Ary_empty;
	public void Args_default_str_(String v) {
		this.args_default_str = v;
		byte[] bry = Bry_.new_u8("http://x.org/a?" + v);
		Gfo_url tmp_url = new Gfo_url();
		app.Utl__url_parser().Url_parser().Parse(tmp_url, bry, 0, bry.length);
		args_default = tmp_url.Args();
	}
	private String args_default_str = "";// default args for search
	public static final int[] Ns_default_main = new int[] {Xow_ns_.Id_main};
	public void Cancel() {
		cur_cmd.Cancel();
		long prv_time = Env_.TickCount();
		while (cur_cmd.Working()) {
			if (Env_.TickCount() - prv_time > 4000) {
				if (log_enabled) app.Usr_dlg().Log_many("", "", "search cancel timeout: word=~{0}", String_.new_u8(search_bry));
				cur_cmd.Working_(false);
				break;
			}
		}
	}
	public void Search(Xowe_wiki wiki, byte[] search_bry, byte[] cbk_func) {
		this.wiki = wiki; this.search_bry = search_bry; this.cbk_func = cbk_func;
		Thread_adp_.invk_(gplx.xowa.apps.Xoa_thread_.Key_special_suggest, this, Invk_search_async).Start();
	}	private Xowe_wiki wiki; private byte[] search_bry, cbk_func;
	private Object thread_lock = new Object();
	private void Search_async() {
		if (!enabled) return;
		if (search_bry.length == 0) return;
		this.Cancel();
		synchronized (thread_lock) {
			if (Bry_.Eq(search_bry , last_search_bry)) {
				if (log_enabled) app.Usr_dlg().Log_many("", "", "search repeated?: word=~{0}", String_.new_u8(search_bry));
				return;
			}
			cur_cmd.Init(wiki, search_bry, results_max, search_mode, all_pages_extend, all_pages_min);
			this.last_search_bry = search_bry;
			if (log_enabled) app.Usr_dlg().Log_many("", "", "search bgn: word=~{0}", String_.new_u8(search_bry));
			cur_cmd.Search();
		}
	}	private Xog_search_suggest_cmd cur_cmd; byte[] last_search_bry;
	public void Notify() {// EX: receiveSuggestions('search_word', ['result_1', 'result_2']);
		// synchronized (thread_lock) NOTE: never use synchronized here; will synchronized search; DATE:2013-09-24
		byte[] search_bry = cur_cmd.Search_bry();
		if (!Bry_.Eq(search_bry, last_search_bry)) {
			if (log_enabled) app.Usr_dlg().Log_many("", "", "search does not match?: expd=~{0} actl=~{1}", String_.new_u8(last_search_bry), String_.new_u8(search_bry));
			return;	// do not notify if search terms do not match
		}
		List_adp found = cur_cmd.Results();
		wtr.Func_init(cbk_func);
		wtr.Prm_bry(search_bry);
		wtr.Prm_spr();
		wtr.Ary_init();
		int len = found.Count();
		for (int i = 0; i < len; i++) {
			Xowd_page_itm p = (Xowd_page_itm)found.Get_at(i);
			Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(p.Ns_id());
			byte[] ttl = Xoa_ttl.Replace_unders(ns.Gen_ttl(p.Ttl_page_db()));
			wtr.Ary_bry(ttl);
		}
		wtr.Ary_term();
		wtr.Func_term();
		if (log_enabled) app.Usr_dlg().Log_many("", "", "search end: word=~{0}", String_.new_u8(search_bry));
		main_win.Active_html_box().Html_js_eval_script(wtr.To_str_and_clear());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_search_async))			Search_async();
		else if	(ctx.Match(k, Invk_notify))					Notify();
		else if	(ctx.Match(k, Invk_enabled))				return Yn.Xto_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))				enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_results_max))			return results_max;
		else if	(ctx.Match(k, Invk_results_max_))			results_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_search_mode))			return Search_mode_str(search_mode);
		else if	(ctx.Match(k, Invk_search_mode_))			search_mode = Search_mode_parse(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk_search_mode_list))		return Options_search_mode_list;
		else if	(ctx.Match(k, Invk_all_pages_extend))		return all_pages_extend;
		else if	(ctx.Match(k, Invk_all_pages_extend_))		all_pages_extend = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_all_pages_min))			return all_pages_min;
		else if	(ctx.Match(k, Invk_all_pages_min_))			all_pages_min = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_auto_wildcard))			return Yn.Xto_str(auto_wildcard);
		else if	(ctx.Match(k, Invk_auto_wildcard_))			auto_wildcard = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_log_enabled))			return Yn.Xto_str(log_enabled);
		else if	(ctx.Match(k, Invk_log_enabled_))			log_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_args_default))			return args_default_str;
		else if	(ctx.Match(k, Invk_args_default_))			Args_default_str_(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_search_async = "search_async", Invk_notify = "notify", Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_results_max = "results_max", Invk_results_max_ = "results_max_"
	, Invk_search_mode = "search_mode", Invk_search_mode_ = "search_mode_", Invk_search_mode_list = "search_mode_list"
	, Invk_all_pages_extend = "all_pages_extend", Invk_all_pages_extend_ = "all_pages_extend_"
	, Invk_all_pages_min = "all_pages_min", Invk_all_pages_min_ = "all_pages_min_"
	, Invk_auto_wildcard = "auto_wildcard", Invk_auto_wildcard_ = "auto_wildcard_"
	, Invk_log_enabled = "log_enabled", Invk_log_enabled_ = "log_enabled_"
	, Invk_args_default = "args_default", Invk_args_default_ = "args_default_"
	;
	private static final String Str_search_mode_search = "Search", Str_search_mode_all_pages_v1 = "AllPages", Str_search_mode_all_pages_v2 = "AllPages_(v2)";
	public static final byte Tid_search_mode_all_pages_v1 = 0, Tid_search_mode_search = 1, Tid_search_mode_all_pages_v2 = 2;
	private static KeyVal[] Options_search_mode_list = KeyVal_.Ary(KeyVal_.new_(Str_search_mode_search), KeyVal_.new_(Str_search_mode_all_pages_v1), KeyVal_.new_(Str_search_mode_all_pages_v2)); 
	private static byte Search_mode_parse(String v) {
		if		(String_.Eq(v, Str_search_mode_search))			return Tid_search_mode_search;
		else if	(String_.Eq(v, Str_search_mode_all_pages_v1))	return Tid_search_mode_all_pages_v1;
		else if	(String_.Eq(v, Str_search_mode_all_pages_v2))	return Tid_search_mode_all_pages_v2;
		else													throw Exc_.new_unhandled(v);
	}
	private static String Search_mode_str(byte v) {
		switch (v) {
			case Tid_search_mode_search:						return Str_search_mode_search;
			case Tid_search_mode_all_pages_v1:					return Str_search_mode_all_pages_v1;
			case Tid_search_mode_all_pages_v2:					return Str_search_mode_all_pages_v2;
			default:											throw Exc_.new_unhandled(v);
		}
	}
}
