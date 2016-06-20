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
package gplx.xowa.apps.apis.xowa.addons.searchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.addons.*;
import gplx.xowa.addons.wikis.searchs.searchers.*; import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Xoapi_url_bar implements Gfo_invk, Gfo_evt_mgr_owner {
	public Xoapi_url_bar() {
		this.evt_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr		Evt_mgr()					{return evt_mgr;}				private final    Gfo_evt_mgr evt_mgr;
	public boolean		Enabled()				{return enabled;}			private boolean enabled = true;
	public int			Search_mode()			{return search_mode;}		private int search_mode = Xoapi_search_mode_.Tid__title_word;
	public int			Max_results()			{return max_results;}		private int max_results = 10;
	public boolean		Auto_wildcard()			{return auto_wildcard;}		private boolean auto_wildcard = true;
	public Srch_ns_mgr	Ns_mgr()				{return ns_mgr;}			private final    Srch_ns_mgr ns_mgr = new Srch_ns_mgr().Add_main_if_empty();
	public Srch_crt_scanner_syms Syms()			{return syms;}				private Srch_crt_scanner_syms syms = Srch_search_mgr.Scanner_syms;
	public Keyval[]   	Operators()				{return symbols;}			private Keyval[] symbols = Keyval_.Ary_empty;
	public int			Visible_rows()			{return visible_rows;}		private int visible_rows = 10;
	public int			Jump_len()				{return jump_len;}			private int jump_len = 5;
	private void Ns_ids_(String s) {
		int[] ns_ids = Int_.Ary_empty;
		if (String_.Eq(s, "*")) {}	// leave as int[0]; ns_mgr will interpret as wildcard
		else {
			ns_ids = Int_.Ary_parse(s, ",");
		}
		ns_mgr.Add_by_int_ids(ns_ids);
		Gfo_evt_mgr_.Pub(this, Evt__ns_ids_changed);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__enabled)) 						return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk__enabled_)) 						enabled = m.ReadBool("v");
		else if	(ctx.Match(k, Invk__search_mode)) 					return Xoapi_search_mode_.To_str(search_mode);
		else if	(ctx.Match(k, Invk__search_mode_)) 					search_mode = Xoapi_search_mode_.To_int(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk__max_results)) 					return Int_.To_str(max_results);
		else if	(ctx.Match(k, Invk__max_results_)) 					max_results = m.ReadInt("v");
		else if	(ctx.Match(k, Invk__auto_wildcard)) 				return Yn.To_str(auto_wildcard);
		else if	(ctx.Match(k, Invk__auto_wildcard_)) 				auto_wildcard = m.ReadBool("v");
		else if	(ctx.Match(k, Invk__ns_ids)) 						return Int_.To_str(ns_mgr.To_int_ary(), "|");
		else if	(ctx.Match(k, Invk__ns_ids_)) 						Ns_ids_(m.ReadStr("v"));
		else if	(ctx.Match(k, Invk__symbols)) 						return String_.new_u8(syms.To_bry());
		else if	(ctx.Match(k, Invk__symbols_)) 						syms.Parse(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk__visible_rows)) 					return Int_.To_str(visible_rows);
		else if	(ctx.Match(k, Invk__visible_rows_)) 				{visible_rows = m.ReadInt("v"); Gfo_evt_mgr_.Pub_val(this, Evt__visible_rows_changed, visible_rows);}
		else if	(ctx.Match(k, Invk__jump_len)) 						return Int_.To_str(jump_len);
		else if	(ctx.Match(k, Invk__jump_len_)) 					{jump_len = m.ReadInt("v"); Gfo_evt_mgr_.Pub_val(this, Evt__jump_len_changed, jump_len);}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Invk__enabled					= "enabled"						, Invk__enabled_				= "enabled_"
	, Invk__search_mode				= "search_mode"					, Invk__search_mode_			= "search_mode_"
	, Invk__max_results				= "max_results"					, Invk__max_results_			= "max_results_"
	, Invk__auto_wildcard			= "auto_wildcard"				, Invk__auto_wildcard_			= "auto_wildcard_"
	, Invk__ns_ids					= "ns_ids"						, Invk__ns_ids_					= "ns_ids_"
	, Invk__symbols					= "symbols"						, Invk__symbols_				= "symbols_"
	, Invk__visible_rows			= "visible_rows"				, Invk__visible_rows_			= "visible_rows_"
	, Invk__jump_len				= "jump_len"					, Invk__jump_len_				= "jump_len_"
	;
	public static final String 
	  Evt__visible_rows_changed		= "visible_rows_changed"
	, Evt__jump_len_changed			= "jump_len_changed"
	, Evt__ns_ids_changed			= "ns_ids_changed"
	;
}
