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
package gplx.xowa.apis.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.apis.*; import gplx.xowa.apis.xowa.*; import gplx.xowa.apis.xowa.html.*;
import gplx.xowa.html.modules.popups.*;
public class Xoapi_popups implements GfoInvkAble, GfoEvMgrOwner {
	private Xoae_app app;
	public Xoapi_popups() {
		evMgr = GfoEvMgr.new_(this);
	}
	public GfoEvMgr EvMgr() {return evMgr;} private GfoEvMgr evMgr;
	public void Init_by_app(Xoae_app app) {this.app = app;}
	public boolean  Enabled() {return enabled;}			public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public int		Show_init_word_count()				{return show_init_word_count;}			private int show_init_word_count				= Dflt_show_init_word_count;
	public int		Show_more_word_count()				{return show_more_word_count;}			private int show_more_word_count				= Dflt_show_more_word_count;
	public int		Show_all_if_less_than()				{return show_all_if_less_than;}			private int show_all_if_less_than				= Dflt_show_all_if_less_than;
	public int		Win_show_delay()					{return win_show_delay;}				private int win_show_delay						= Dflt_win_show_delay;
	public int		Win_hide_delay()					{return win_hide_delay;}				private int win_hide_delay						= Dflt_win_hide_delay;
	public int		Win_max_w()							{return win_max_w;}						private int win_max_w							= Dflt_win_max_w;
	public int		Win_max_h()							{return win_max_h;}						private int win_max_h							= Dflt_win_max_h;
	public int		Win_show_all_max_w()				{return win_show_all_max_w;}			private int win_show_all_max_w					= Dflt_win_show_all_max_w;
	public boolean  Win_bind_focus_blur()				{return win_bind_focus_blur;}			private boolean win_bind_focus_blur				= Dflt_win_bind_focus_blur;
	public byte[]	Xnde_ignore_ids()					{return xnde_ignore_ids;}				private byte[] xnde_ignore_ids					= Dflt_xnde_ignore_ids;
	public int		Scan_len()							{return scan_len;}						private int scan_len							= Dflt_scan_len;
	public int		Scan_max()							{return scan_max;}						private int scan_max							= Dflt_scan_max;
	public byte[]	Ns_allowed()						{return ns_allowed;}					private byte[] ns_allowed						= Dflt_ns_allowed;
	public int		Read_til_stop_fwd()					{return read_til_stop_fwd;}				private int read_til_stop_fwd					= Dflt_read_til_stop_fwd;
	public int		Read_til_stop_bwd()					{return read_til_stop_bwd;}				private int read_til_stop_bwd					= Dflt_read_til_stop_bwd;
	public int		Stop_if_hdr_after()					{return stop_if_hdr_after;}				private int stop_if_hdr_after					= Dflt_stop_if_hdr_after;
	public int		Tmpl_tkn_max()						{return tmpl_tkn_max;}					private int tmpl_tkn_max						= Dflt_tmpl_tkn_max;
	public byte[]	Tmpl_keeplist()						{return tmpl_keeplist;}					private byte[] tmpl_keeplist					= Dflt_tmpl_keeplist;
	public byte[]	Html_fmtr_popup()					{return html_fmtr_popup;}				private byte[] html_fmtr_popup					= Dflt_html_fmtr_popup;
	public byte[]	Html_fmtr_popup_dflt()				{return html_fmtr_popup_dflt;}			private byte[] html_fmtr_popup_dflt				= Dflt_html_fmtr_popup;
	public byte[]	Html_fmtr_viewed()					{return html_fmtr_viewed;}				private byte[] html_fmtr_viewed					= Dflt_html_fmtr_viewed;
	public byte[]	Html_fmtr_viewed_dflt()				{return html_fmtr_viewed_dflt;}			private byte[] html_fmtr_viewed_dflt			= Dflt_html_fmtr_viewed;
	public byte[]	Html_fmtr_wiki()					{return html_fmtr_wiki;}				private byte[] html_fmtr_wiki					= Dflt_html_fmtr_wiki;
	public byte[]	Html_fmtr_wiki_dflt()				{return html_fmtr_wiki_dflt;}			private byte[] html_fmtr_wiki_dflt				= Dflt_html_fmtr_wiki;
	public byte[]	Html_fmtr_next_sect_fmt()			{return html_fmtr_next_sect;}			private byte[] html_fmtr_next_sect				= Dflt_html_fmtr_next_sect;
	public byte[]	Html_fmtr_next_sect_fmt_dflt()		{return html_fmtr_next_sect_dflt;}		private byte[] html_fmtr_next_sect_dflt			= Dflt_html_fmtr_next_sect;
	public void Show_more(String popup_id) {
		Xowe_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Wiki();
		wiki.Html_mgr().Module_mgr().Popup_mgr().Show_more(popup_id);
	}
	public void Show_all(String popup_id) {
		Xowe_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Wiki();
		wiki.Html_mgr().Module_mgr().Popup_mgr().Show_all(popup_id);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))						return Yn.Xto_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))						enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_show_more))	 					Show_more(m.ReadStr("popup_id"));
		else if	(ctx.Match(k, Invk_show_all))	 					Show_all (m.ReadStr("popup_id"));
		else if	(ctx.Match(k, Invk_show_init_word_count))			return show_init_word_count;
		else if	(ctx.Match(k, Invk_show_init_word_count_))			{show_init_word_count = Set_int_gt_0(m, show_init_word_count, Evt_show_init_word_count_changed);}
		else if	(ctx.Match(k, Invk_show_more_word_count))	 		return show_more_word_count;
		else if	(ctx.Match(k, Invk_show_more_word_count_))	 		{show_more_word_count = Set_int_gt_0(m, show_more_word_count, Evt_show_more_word_count_changed);}
		else if	(ctx.Match(k, Invk_show_all_if_less_than))	 		return show_all_if_less_than;
		else if	(ctx.Match(k, Invk_show_all_if_less_than_))	 		{show_all_if_less_than = Set_int(m, show_all_if_less_than, Evt_show_all_if_less_than_changed);}
		else if	(ctx.Match(k, Invk_win_show_delay))		 			return win_show_delay;
		else if	(ctx.Match(k, Invk_win_show_delay_))	 			{win_show_delay = Set_int(m, win_show_delay, Evt_win_show_delay_changed);}
		else if	(ctx.Match(k, Invk_win_hide_delay))	 				return win_hide_delay;
		else if	(ctx.Match(k, Invk_win_hide_delay_))	 			{win_hide_delay = Set_int(m, win_hide_delay, Evt_win_hide_delay_changed);}
		else if	(ctx.Match(k, Invk_win_max_w))	 					return win_max_w;
		else if	(ctx.Match(k, Invk_win_max_w_))	 					{win_max_w = Set_int(m, win_max_w, Evt_win_max_w_changed);}
		else if	(ctx.Match(k, Invk_win_max_h))	 					return win_max_h;
		else if	(ctx.Match(k, Invk_win_max_h_))	 					{win_max_h = Set_int(m, win_max_h, Evt_win_max_h_changed);}
		else if	(ctx.Match(k, Invk_win_show_all_max_w))	 			return win_show_all_max_w;
		else if	(ctx.Match(k, Invk_win_show_all_max_w_))	 		{win_show_all_max_w = m.ReadInt("v");}
		else if	(ctx.Match(k, Invk_win_bind_focus_blur))		 	return Yn.Xto_str(win_bind_focus_blur);
		else if	(ctx.Match(k, Invk_win_bind_focus_blur_))	 		win_bind_focus_blur = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_xnde_ignore_ids))	 			return String_.new_utf8_(xnde_ignore_ids);
		else if	(ctx.Match(k, Invk_xnde_ignore_ids_))	 			{xnde_ignore_ids = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_xnde_ignore_ids_changed, xnde_ignore_ids);}
		else if	(ctx.Match(k, Invk_scan_len))	 					return scan_len;
		else if	(ctx.Match(k, Invk_scan_len_))	 					{scan_len = Set_int_gt_0(m, scan_len, Evt_scan_len_changed);}
		else if	(ctx.Match(k, Invk_scan_max))	 					return scan_max;
		else if	(ctx.Match(k, Invk_scan_max_))	 					{scan_max = Set_int_gt_0(m, scan_max, Evt_scan_max_changed);}
		else if	(ctx.Match(k, Invk_read_til_stop_fwd))	 			return read_til_stop_fwd;
		else if	(ctx.Match(k, Invk_read_til_stop_fwd_))	 			{read_til_stop_fwd = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_read_til_stop_fwd_changed, read_til_stop_fwd);}
		else if	(ctx.Match(k, Invk_read_til_stop_bwd))	 			return read_til_stop_bwd;
		else if	(ctx.Match(k, Invk_read_til_stop_bwd_))	 			{read_til_stop_bwd = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_read_til_stop_bwd_changed, read_til_stop_bwd);}
		else if	(ctx.Match(k, Invk_stop_if_hdr_after))	 			return stop_if_hdr_after;
		else if	(ctx.Match(k, Invk_stop_if_hdr_after_))	 			{stop_if_hdr_after = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_stop_if_hdr_after_changed, stop_if_hdr_after);}
		else if	(ctx.Match(k, Invk_ns_allowed))	 					return String_.new_utf8_(ns_allowed);
		else if	(ctx.Match(k, Invk_ns_allowed_))	 				{ns_allowed = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_ns_allowed_changed, ns_allowed);}
		else if	(ctx.Match(k, Invk_tmpl_tkn_max))	 				return tmpl_tkn_max;
		else if	(ctx.Match(k, Invk_tmpl_tkn_max_))	 				{tmpl_tkn_max = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_tmpl_tkn_max_changed, tmpl_tkn_max);}
		else if	(ctx.Match(k, Invk_tmpl_keeplist))	 				return String_.new_utf8_(tmpl_keeplist);
		else if	(ctx.Match(k, Invk_tmpl_keeplist_))	 				{tmpl_keeplist = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_tmpl_keeplist_changed, tmpl_keeplist);}
		else if	(ctx.Match(k, Invk_html_fmtr_popup))	 			return String_.new_utf8_(html_fmtr_popup);
		else if	(ctx.Match(k, Invk_html_fmtr_popup_))	 			{html_fmtr_popup = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_html_fmtr_popup_changed, html_fmtr_popup);}
		else if	(ctx.Match(k, Invk_html_fmtr_popup_dflt))	 		return String_.new_utf8_(html_fmtr_popup_dflt);
		else if	(ctx.Match(k, Invk_html_fmtr_popup_dflt_))	 		{html_fmtr_popup_dflt = m.ReadBry("v");}
		else if	(ctx.Match(k, Invk_html_fmtr_viewed))				return String_.new_utf8_(html_fmtr_viewed);
		else if	(ctx.Match(k, Invk_html_fmtr_viewed_))			 	{html_fmtr_viewed = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_html_fmtr_viewed_changed, html_fmtr_viewed);}
		else if	(ctx.Match(k, Invk_html_fmtr_viewed_dflt))			return String_.new_utf8_(html_fmtr_viewed_dflt);
		else if	(ctx.Match(k, Invk_html_fmtr_viewed_dflt_))			{html_fmtr_viewed_dflt = m.ReadBry("v");}
		else if	(ctx.Match(k, Invk_html_fmtr_wiki))	 				return String_.new_utf8_(html_fmtr_wiki);
		else if	(ctx.Match(k, Invk_html_fmtr_wiki_))			 	{html_fmtr_wiki = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_html_fmtr_wiki_changed, html_fmtr_wiki);}
		else if	(ctx.Match(k, Invk_html_fmtr_wiki_dflt))			return String_.new_utf8_(html_fmtr_wiki_dflt);
		else if	(ctx.Match(k, Invk_html_fmtr_wiki_dflt_))			{html_fmtr_wiki_dflt = m.ReadBry("v");}
		else if	(ctx.Match(k, Invk_html_fmtr_next_sect))	 		return String_.new_utf8_(html_fmtr_next_sect);
		else if	(ctx.Match(k, Invk_html_fmtr_next_sect_))			{html_fmtr_next_sect = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_html_fmtr_next_sect_changed, html_fmtr_next_sect);}
		else if	(ctx.Match(k, Invk_html_fmtr_next_sect_dflt))		return String_.new_utf8_(html_fmtr_next_sect_dflt);
		else if	(ctx.Match(k, Invk_html_fmtr_next_sect_dflt_))		{html_fmtr_next_sect_dflt = m.ReadBry("v");}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private int Set_int_gt_0(GfoMsg m, int cur_val, String evt) {
		int tmp = m.ReadInt("v");
		if (tmp < 1) return cur_val;
		GfoEvMgr_.PubVal(this, evt, tmp);
		return tmp;
	}
	private int Set_int(GfoMsg m, int cur_val, String evt) {
		int tmp = m.ReadInt("v");
		GfoEvMgr_.PubVal(this, evt, tmp);
		return tmp;
	}
	private static final String
	  Invk_enabled = "enabled", Invk_enabled_ = "enabled_"
	, Invk_show_init_word_count = "show_init_word_count", Invk_show_init_word_count_ = "show_init_word_count_"
	, Invk_show_more_word_count = "show_more_word_count", Invk_show_more_word_count_ = "show_more_word_count_"
	, Invk_show_all_if_less_than = "show_all_if_less_than", Invk_show_all_if_less_than_ = "show_all_if_less_than_"
	, Invk_xnde_ignore_ids = "xnde_ignore_ids", Invk_xnde_ignore_ids_ = "xnde_ignore_ids_"
	, Invk_scan_len = "scan_len", Invk_scan_len_ = "scan_len_"
	, Invk_scan_max = "scan_max", Invk_scan_max_ = "scan_max_"
	, Invk_show_more = "show_more", Invk_show_all = "show_all"
	, Invk_html_fmtr_popup				= "html_fmtr_popup"				, Invk_html_fmtr_popup_				= "html_fmtr_popup"
	, Invk_html_fmtr_viewed				= "html_fmtr_viewed"			, Invk_html_fmtr_viewed_			= "html_fmtr_viewed_"
	, Invk_html_fmtr_wiki				= "html_fmtr_wiki"				, Invk_html_fmtr_wiki_				= "html_fmtr_wiki_"
	, Invk_html_fmtr_next_sect			= "html_fmtr_next_sect"			, Invk_html_fmtr_next_sect_			= "html_fmtr_next_sect_"
	, Invk_html_fmtr_popup_dflt			= "html_fmtr_popup_dflt"		, Invk_html_fmtr_popup_dflt_		= "html_fmtr_popup_dflt_"
	, Invk_html_fmtr_viewed_dflt		= "html_fmtr_viewed_dflt"		, Invk_html_fmtr_viewed_dflt_		= "html_fmtr_viewed_dflt_"
	, Invk_html_fmtr_wiki_dflt			= "html_fmtr_wiki_dflt"			, Invk_html_fmtr_wiki_dflt_			= "html_fmtr_wiki_dflt_"
	, Invk_html_fmtr_next_sect_dflt		= "html_fmtr_next_sect_dflt"	, Invk_html_fmtr_next_sect_dflt_	= "html_fmtr_next_sect_dflt_"
	, Invk_win_show_delay = "win_show_delay", Invk_win_show_delay_ = "win_show_delay_"
	, Invk_win_hide_delay = "win_hide_delay", Invk_win_hide_delay_ = "win_hide_delay_"
	, Invk_win_bind_focus_blur = "win_bind_focus_blur", Invk_win_bind_focus_blur_ = "win_bind_focus_blur_"
	, Invk_win_max_w = "win_max_w", Invk_win_max_w_ = "win_max_w_"
	, Invk_win_max_h = "win_max_h", Invk_win_max_h_ = "win_max_h_"
	, Invk_win_show_all_max_w = "win_show_all_max_w", Invk_win_show_all_max_w_ = "win_show_all_max_w_"
	, Invk_read_til_stop_fwd = "read_til_stop_fwd", Invk_read_til_stop_fwd_ = "read_til_stop_fwd_"
	, Invk_read_til_stop_bwd = "read_til_stop_bwd", Invk_read_til_stop_bwd_ = "read_til_stop_bwd_"
	, Invk_stop_if_hdr_after = "stop_if_hdr_after", Invk_stop_if_hdr_after_ = "stop_if_hdr_after_"
	, Invk_ns_allowed = "ns_allowed", Invk_ns_allowed_ = "ns_allowed_"
	, Invk_tmpl_tkn_max = "tmpl_tkn_max", Invk_tmpl_tkn_max_ = "tmpl_tkn_max_"
	, Invk_tmpl_keeplist = "tmpl_keeplist", Invk_tmpl_keeplist_ = "tmpl_keeplist_"
	;
	public static final String 
	  Evt_show_init_word_count_changed = "show_init_word_count_changed"
	, Evt_show_more_word_count_changed = "show_more_word_count_changed"
	, Evt_show_all_if_less_than_changed = "show_all_if_less_than_changed"
	, Evt_win_show_delay_changed = "win_show_delay_changed"
	, Evt_win_hide_delay_changed = "win_hide_delay_changed"
	, Evt_win_max_w_changed = "win_max_w_changed"
	, Evt_win_max_h_changed = "win_max_h_changed"
	, Evt_xnde_ignore_ids_changed = "xnde_ignore_ids_changed"
	, Evt_scan_len_changed = "scan_len_changed"
	, Evt_scan_max_changed = "scan_max_changed"
	, Evt_read_til_stop_fwd_changed = "read_til_stop_fwd_changed"
	, Evt_read_til_stop_bwd_changed = "read_til_stop_bwd_changed"
	, Evt_stop_if_hdr_after_changed = "stop_if_hdr_after_changed"
	, Evt_ns_allowed_changed = "ns_allowed_changed"
	, Evt_tmpl_tkn_max_changed = "tmpl_tkn_max_changed"
	, Evt_tmpl_keeplist_changed = "tmpl_keeplist"
	, Evt_html_fmtr_popup_changed		= "html_fmtr_popup_changed"
	, Evt_html_fmtr_viewed_changed		= "html_fmtr_viewed_changed"
	, Evt_html_fmtr_wiki_changed		= "html_fmtr_wiki_changed"
	, Evt_html_fmtr_next_sect_changed	= "html_fmtr_next_sect_changed"
	;
	public static final byte[]
	  Dflt_xnde_ignore_ids		= Bry_.new_ascii_("coordinates")
	, Dflt_tmpl_keeplist		= Bry_.new_ascii_("en.wikipedia.org|formatnum;age;age_in_days;age_in_years_and_days*;nts;number_table_sorting*;as_of;oldstyledatedy;gregorian_serial_date;currentminute;currentsecond;dmca;spaced_ndash;trim;month*;convert*;worldpop*;ipa*;lang*;nowrap*;h:*;mvar;math;vgy;audio;iso_639_name;transl;translate;linktext;zh;nihongo*;japanese_name;ko-hhrm|\n")
	, Dflt_html_fmtr_popup = Bry_.new_ascii_(String_.Concat_lines_nl_skip_last
	( "<div dir=~{page_lang_ltr}>"
	, "  <div>~{content}"
	, "  </div>"
	, "  <hr/>"
	, "  <div>"
	, "    <span class='data_val'><b>~{page_title}</b></span>~{wiki_item}"
	, "    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.size-name');<>}</span><span class='data_val'>~{page_size}</span>"
	, "    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.edited-name');<>}</span><span class='data_val'>~{edit_time}</span>~{view_time_item}"
	, "  </div>"
	, "  <hr/>"
	, "  <div style='float:bottom;'>"
	, "    <span><a href='xowa-cmd:xowa.api.nav.goto(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.gui.browser.url.exec-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/page/open.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.gui.browser.tabs.new_link__at_dflt__focus_y(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.gui.browser.tabs.new_link__at_dflt__focus_y-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/tabs/new.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.gui.browser.tabs.new_link__at_dflt__focus_n(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.gui.browser.tabs.new_link__at_dflt__focus_n-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/tabs/new_background.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.usr.bookmarks.add(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.usr.bookmarks.add-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/bookmarks/add.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.html.modules.popups.show_more(\"~{popup_id}\");' title='~{<>msgs.get('api-xowa.html.modules.popups.show_more-tip');<>}'><img src='~{xowa_root_dir}bin/any/xowa/html/res/src/xowa/popups/imgs/show_more.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.html.modules.popups.show_all (\"~{popup_id}\");' title='~{<>msgs.get('api-xowa.html.modules.popups.show_all-tip');<>}'> <img src='~{xowa_root_dir}bin/any/xowa/html/res/src/xowa/popups/imgs/show_all.png' ></a></span>"
	, "    <span><a href='/wiki/Special:XowaPopupHistory' title='~{<>msgs.get('api-xowa.html.modules.popups.history-tip');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/history/show.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.gui.browser.tabs.new_link__at_dflt__focus_y(\"home/wiki/Help:Options/Popups\");' title='~{<>msgs.get('api-xowa.nav.cfg.main-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/tools/options.png'></a></span>"
	, "  </div>"
	, "</div>"
	))
	, Dflt_html_fmtr_viewed			= Bry_.new_ascii_("\n    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.view_time-name');<>}</span><span class='data_val'>~{viewed_val}</span>")
	, Dflt_html_fmtr_wiki			= Bry_.new_ascii_("\n    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.wiki-name');<>}</span><span class='data_val'>~{wiki_val}</span>")
	, Dflt_html_fmtr_next_sect		= Bry_.new_ascii_("\n\n<span class='next_sect'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.next_sect-name');<>}~{next_sect_val}</span>")
	;
	public static final String[]
	  Dflt_html_fmtr_popup_keys			= String_.Ary("content", "page_lang_ltr", "page_url", "page_title", "popup_id", "wiki_item", "page_size", "edit_time", "view_time_item", "xowa_root_dir")
	, Dflt_html_fmtr_viewed_keys		= String_.Ary("viewed_val")
	, Dflt_html_fmtr_wiki_keys			= String_.Ary("wiki_val")
	, Dflt_html_fmtr_next_sect_keys		= String_.Ary("next_sect_val")
	;
	public static final byte[]
	  Dflt_ns_allowed = Bry_.Empty
	;
	public static final int
	  Dflt_show_init_word_count = 128
	, Dflt_show_more_word_count = 192
	, Dflt_show_all_if_less_than = -1
	, Dflt_show_all_win_max_w = -1
	, Dflt_win_show_delay = 600, Dflt_win_hide_delay = 400
	, Dflt_win_max_w = -1, Dflt_win_max_h = -1
	, Dflt_win_show_all_max_w = 800
	, Dflt_scan_len =  1 * Io_mgr.Len_kb
	, Dflt_scan_max = 32 * Io_mgr.Len_kb
	, Dflt_read_til_stop_fwd = 32
	, Dflt_read_til_stop_bwd = 16
	, Dflt_stop_if_hdr_after = 96
	, Dflt_tmpl_tkn_max = 8 * Io_mgr.Len_kb
	;
	public static final boolean
	  Dflt_win_bind_focus_blur = false
	;
}
