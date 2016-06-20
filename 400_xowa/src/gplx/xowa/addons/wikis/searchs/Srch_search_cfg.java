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
package gplx.xowa.addons.wikis.searchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.core.net.*; import gplx.core.net.qargs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.addons.wikis.searchs.searchers.cbks.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
public class Srch_search_cfg implements Gfo_invk {
	private final    Xoae_app app;
	private String args_default_str = "";// default args for search
	private byte search_mode = Tid_search_mode_all_pages_v2;
	private int all_pages_extend = 1000;		// look ahead by 1000
	private int all_pages_min = 10000;			// only look at pages > 10 kb
	private boolean log_enabled = false;
	public Srch_search_cfg(Xoae_app app) {
		this.app = app;
		ns_mgr.Add_main_if_empty();
	}
	public boolean				Enabled()			{return enabled;}		private boolean enabled = true;
	public int					Rslts_max()			{return results_max;}	private int results_max = 25;
	public boolean				Auto_wildcard()		{return auto_wildcard;} private boolean auto_wildcard = false;		// automatically add wild-card; EX: Earth -> *Earth*
	public Srch_ns_mgr			Ns_mgr()			{return ns_mgr;}		private final    Srch_ns_mgr ns_mgr = new Srch_ns_mgr();
	public Gfo_qarg_itm[]		Args_default()		{return args_default;}	private Gfo_qarg_itm[] args_default = Gfo_qarg_itm.Ary_empty;
	public void Args_default_str_(String v) {
		this.args_default_str = v;
		byte[] bry = Bry_.new_a7("http://x.org/a?" + v);
		Gfo_url tmp_url = new Gfo_url();
		app.User().Wikii().Utl__url_parser().Url_parser().Parse(tmp_url, bry, 0, bry.length);
		args_default = tmp_url.Qargs();
	}
	private Srch_search_addon addon;
	public void Search(Xowe_wiki wiki, byte[] search_bry, byte[] cbk_func) {
		if (	!enabled
			||	search_bry.length == 0
			) return;
		if (addon == null)
			addon = Srch_search_addon.Get(wiki);
		else {
			if (!Bry_.Eq(wiki.Domain_bry(), addon.Wiki_domain()))	// NOTE: suggest-box caches addon at wiki level; need to check if wiki has changed
				addon = Srch_search_addon.Get(wiki);
		}
		// tab_close_mgr.Add(this);
		Srch_search_qry qry = Srch_search_qry.New__suggest_box(wiki, this, search_bry);
		Srch_rslt_cbk__suggest_box cbk = new Srch_rslt_cbk__suggest_box(wiki.Appe(), cbk_func, search_bry);
		addon.Search(qry, cbk);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))				return Yn.To_str(enabled);
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
		else if	(ctx.Match(k, Invk_auto_wildcard))			return Yn.To_str(auto_wildcard);
		else if	(ctx.Match(k, Invk_auto_wildcard_))			auto_wildcard = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_log_enabled))			return Yn.To_str(log_enabled);
		else if	(ctx.Match(k, Invk_log_enabled_))			log_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_args_default))			return args_default_str;
		else if	(ctx.Match(k, Invk_args_default_))			Args_default_str_(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String 
	  Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_results_max = "results_max", Invk_results_max_ = "results_max_"
	, Invk_search_mode = "search_mode", Invk_search_mode_ = "search_mode_", Invk_search_mode_list = "search_mode_list"
	, Invk_all_pages_extend = "all_pages_extend", Invk_all_pages_extend_ = "all_pages_extend_"
	, Invk_all_pages_min = "all_pages_min", Invk_all_pages_min_ = "all_pages_min_"
	, Invk_auto_wildcard = "auto_wildcard", Invk_auto_wildcard_ = "auto_wildcard_"
	, Invk_log_enabled = "log_enabled", Invk_log_enabled_ = "log_enabled_"
	, Invk_args_default = "args_default", Invk_args_default_ = "args_default_"
	;
	private static final String Str_search_mode_search = "Search", Str_search_mode_all_pages_v1 = "AllPages", Str_search_mode_all_pages_v2 = "AllPages_(v2)";
	public static final    int[] Ns_default_main = new int[] {Xow_ns_.Tid__main};
	public static final byte Tid_search_mode_all_pages_v1 = 0, Tid_search_mode_search = 1, Tid_search_mode_all_pages_v2 = 2;
	private static Keyval[] Options_search_mode_list = Keyval_.Ary(Keyval_.new_(Str_search_mode_search), Keyval_.new_(Str_search_mode_all_pages_v1), Keyval_.new_(Str_search_mode_all_pages_v2)); 
	private static byte Search_mode_parse(String v) {
		if		(String_.Eq(v, Str_search_mode_search))			return Tid_search_mode_search;
		else if	(String_.Eq(v, Str_search_mode_all_pages_v1))	return Tid_search_mode_all_pages_v1;
		else if	(String_.Eq(v, Str_search_mode_all_pages_v2))	return Tid_search_mode_all_pages_v2;
		else													throw Err_.new_unhandled(v);
	}
	private static String Search_mode_str(byte v) {
		switch (v) {
			case Tid_search_mode_search:						return Str_search_mode_search;
			case Tid_search_mode_all_pages_v1:					return Str_search_mode_all_pages_v1;
			case Tid_search_mode_all_pages_v2:					return Str_search_mode_all_pages_v2;
			default:											throw Err_.new_unhandled(v);
		}
	}
}
