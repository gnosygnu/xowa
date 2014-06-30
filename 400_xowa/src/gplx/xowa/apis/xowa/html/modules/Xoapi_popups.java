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
	private Xoa_app app;
	public Xoapi_popups() {
		evMgr = GfoEvMgr.new_(this);
	}
	public GfoEvMgr EvMgr() {return evMgr;} private GfoEvMgr evMgr;
	public void Init_by_app(Xoa_app app) {this.app = app;}
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = false;
	public int Show_init_word_count() {return show_init_word_count;} private int show_init_word_count = Dflt_show_init_word_count;
	public int Show_more_word_count() {return show_more_word_count;} private int show_more_word_count = Dflt_show_more_word_count;
	public int Show_all_if_less_than() {return show_all_if_less_than;} public void Show_all_if_less_than_(int v) {show_all_if_less_than = v;} private int show_all_if_less_than = Dflt_show_all_if_less_than;
	public byte[] Html_fmt() {return html_fmt;} private byte[] html_fmt = Dflt_html_fmt;
	public byte[] Html_fmt_dflt() {return html_fmt_dflt;} private byte[] html_fmt_dflt = Dflt_html_fmt;
	public int Win_show_delay() {return win_show_delay;} private int win_show_delay = Dflt_win_show_delay;
	public int Win_hide_delay() {return win_hide_delay;} private int win_hide_delay = Dflt_win_hide_delay;
	public int Win_max_w() {return win_max_w;} private int win_max_w = Dflt_win_max_w;
	public int Win_max_h() {return win_max_h;} private int win_max_h = Dflt_win_max_h;
	public int Win_show_all_max_w() {return win_show_all_max_w;} private int win_show_all_max_w = Dflt_win_show_all_max_w;
	public boolean Win_bind_focus_blur() {return win_bind_focus_blur;} private boolean win_bind_focus_blur = Dflt_win_bind_focus_blur;
	public byte[] Xnde_ignore_ids() {return xnde_ignore_ids;} private byte[] xnde_ignore_ids = Dflt_coordinates;
	public int Scan_len() {return scan_len;} private int scan_len = Dflt_scan_len;
	public int Scan_max() {return scan_max;} private int scan_max = Dflt_scan_max;
	public byte[] Ns_allowed() {return ns_allowed;} private byte[] ns_allowed = Dflt_ns_allowed;
	public int Read_til_stop_fwd() {return read_til_stop_fwd;} private int read_til_stop_fwd = Dflt_read_til_stop_fwd;
	public int Read_til_stop_bwd() {return read_til_stop_bwd;} private int read_til_stop_bwd = Dflt_read_til_stop_bwd;
	public int Tmpl_tkn_max() {return tmpl_tkn_max;} private int tmpl_tkn_max = Dflt_tmpl_tkn_max;
	public void Show_more(String popup_id) {
		Xow_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Page().Wiki();
		wiki.Html_mgr().Module_mgr().Popup_mgr().Show_more(popup_id);
	}
	public void Show_all(String popup_id) {
		Xow_wiki wiki = app.Gui_mgr().Browser_win().Active_tab().Page().Wiki();
		wiki.Html_mgr().Module_mgr().Popup_mgr().Show_all(popup_id);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))						return Yn.X_to_str(enabled);
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
		else if	(ctx.Match(k, Invk_win_bind_focus_blur))		 	return Yn.X_to_str(win_bind_focus_blur);
		else if	(ctx.Match(k, Invk_win_bind_focus_blur_))	 		win_bind_focus_blur = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_xnde_ignore_ids))	 			return String_.new_utf8_(xnde_ignore_ids);
		else if	(ctx.Match(k, Invk_xnde_ignore_ids_))	 			{xnde_ignore_ids = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_xnde_ignore_ids_changed, xnde_ignore_ids);}
		else if	(ctx.Match(k, Invk_scan_len))	 					return scan_len;
		else if	(ctx.Match(k, Invk_scan_len_))	 					{scan_len = Set_int_gt_0(m, scan_len, Evt_scan_len_changed);}
		else if	(ctx.Match(k, Invk_scan_max))	 					return scan_max;
		else if	(ctx.Match(k, Invk_scan_max_))	 					{scan_max = Set_int_gt_0(m, scan_max, Evt_scan_max_changed);}
		else if	(ctx.Match(k, Invk_html_fmt))	 					return String_.new_utf8_(html_fmt);
		else if	(ctx.Match(k, Invk_html_fmt_))	 					{html_fmt = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_html_fmt_changed, html_fmt);}
		else if	(ctx.Match(k, Invk_html_fmt_dflt))	 				return String_.new_utf8_(html_fmt_dflt);
		else if	(ctx.Match(k, Invk_html_fmt_dflt_))	 				{html_fmt_dflt = m.ReadBry("v");}
		else if	(ctx.Match(k, Invk_read_til_stop_fwd))	 			return read_til_stop_fwd;
		else if	(ctx.Match(k, Invk_read_til_stop_fwd_))	 			{read_til_stop_fwd = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_read_til_stop_fwd_changed, read_til_stop_fwd);}
		else if	(ctx.Match(k, Invk_read_til_stop_bwd))	 			return read_til_stop_bwd;
		else if	(ctx.Match(k, Invk_read_til_stop_bwd_))	 			{read_til_stop_bwd = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_read_til_stop_bwd_changed, read_til_stop_bwd);}
		else if	(ctx.Match(k, Invk_ns_allowed))	 					return String_.new_utf8_(ns_allowed);
		else if	(ctx.Match(k, Invk_ns_allowed_))	 				{ns_allowed = m.ReadBry("v"); GfoEvMgr_.PubVal(this, Evt_ns_allowed_changed, ns_allowed);}
		else if	(ctx.Match(k, Invk_tmpl_tkn_max))	 					return tmpl_tkn_max;
		else if	(ctx.Match(k, Invk_tmpl_tkn_max_))	 					{tmpl_tkn_max = m.ReadInt("v"); GfoEvMgr_.PubVal(this, Evt_tmpl_tkn_max_changed, tmpl_tkn_max);}
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
	, Invk_html_fmt = "html_fmt", Invk_html_fmt_ = "html_fmt_"
	, Invk_html_fmt_dflt = "html_fmt_dflt", Invk_html_fmt_dflt_ = "html_fmt_dflt_"
	, Invk_win_show_delay = "win_show_delay", Invk_win_show_delay_ = "win_show_delay_"
	, Invk_win_hide_delay = "win_hide_delay", Invk_win_hide_delay_ = "win_hide_delay_"
	, Invk_win_bind_focus_blur = "win_bind_focus_blur", Invk_win_bind_focus_blur_ = "win_bind_focus_blur_"
	, Invk_win_max_w = "win_max_w", Invk_win_max_w_ = "win_max_w_"
	, Invk_win_max_h = "win_max_h", Invk_win_max_h_ = "win_max_h_"
	, Invk_win_show_all_max_w = "win_show_all_max_w", Invk_win_show_all_max_w_ = "win_show_all_max_w_"
	, Invk_read_til_stop_fwd = "read_til_stop_fwd", Invk_read_til_stop_fwd_ = "read_til_stop_fwd_"
	, Invk_read_til_stop_bwd = "read_til_stop_bwd", Invk_read_til_stop_bwd_ = "read_til_stop_bwd_"
	, Invk_ns_allowed = "ns_allowed", Invk_ns_allowed_ = "ns_allowed_"
	, Invk_tmpl_tkn_max = "tmpl_tkn_max", Invk_tmpl_tkn_max_ = "tmpl_tkn_max_"
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
	, Evt_html_fmt_changed = "html_fmt_changed"
	, Evt_read_til_stop_fwd_changed = "read_til_stop_fwd_changed"
	, Evt_read_til_stop_bwd_changed = "read_til_stop_bwd_changed"
	, Evt_ns_allowed_changed = "ns_allowed_changed"
	, Evt_tmpl_tkn_max_changed = "tmpl_tkn_max_changed"
	;
	public static final byte[]
	  Dflt_coordinates = Bry_.new_ascii_("coordinates")
	, Dflt_html_fmt = Bry_.new_ascii_(String_.Concat_lines_nl_skip_last
	( "<div dir=~{page_lang_ltr}>"
	, "  <div>~{content}"
	, "  </div>"
	, "  <hr/>"
	, "  <div>"
	, "    <span class='data_val'><b>~{page_title}</b></span>~{wiki_item}"
	, "    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.size-name');<>}:</span><span class='data_val'>~{page_size}</span>"
	, "    <span class='data_key'>~{<>msgs.get('api-xowa.html.modules.popups.msgs.edited-name');<>}:</span><span class='data_val'>~{edit_time}</span>~{view_time_item}"
	, "  </div>"
	, "  <hr/>"
	, "  <div style='float:bottom;'>"
	, "    <span><a href='xowa-cmd:xowa.api.nav.goto(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.gui.browser.url.exec-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/page/open.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.gui.browser.tabs.new_link__at_dflt__focus_y(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.gui.browser.tabs.new_link__at_dflt__focus_y-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/tabs/new.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.gui.browser.tabs.new_link__at_dflt__focus_n(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.gui.browser.tabs.new_link__at_dflt__focus_n-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/tabs/new_background.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.usr.bookmarks.add(\"~{page_url}\");' title='~{<>msgs.get('api-xowa.usr.bookmarks.add-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/bookmarks/add.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.html.modules.popups.show_more(\"~{popup_id}\");' title='~{<>msgs.get('api-xowa.html.modules.popups.show_more-tip');<>}'><img src='~{xowa_root_dir}bin/any/xowa/html/modules/xowa.popups/imgs/show_more.png'></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.html.modules.popups.show_all (\"~{popup_id}\");' title='~{<>msgs.get('api-xowa.html.modules.popups.show_all-tip');<>}'> <img src='~{xowa_root_dir}bin/any/xowa/html/modules/xowa.popups/imgs/show_all.png' ></a></span>"
	, "    <span><a href='xowa-cmd:xowa.api.gui.browser.tabs.new_link__at_dflt__focus_y(\"home/wiki/Help:Options/Popups\");' title='~{<>msgs.get('api-xowa.nav.cfg.main-name');<>}'><img src='~{xowa_root_dir}user/anonymous/app/img/window/menu/tools/options.png'></a></span>"
	, "  </div>"
	, "</div>"
	))
	;
	public static final String[] Dflt_html_fmt_keys = String_.Ary("content", "page_lang_ltr", "page_url", "page_title", "wiki_domain", "wiki_item", "page_size", "edit_time", "view_time_item", "xowa_root_dir", "popup_id");
	public static final byte[]
	  Dflt_ns_allowed = Bry_.Empty
	;
	public static final int
	  Dflt_show_init_word_count = 64
	, Dflt_show_more_word_count = 64
	, Dflt_show_all_if_less_than = -1
	, Dflt_show_all_win_max_w = -1
	, Dflt_win_show_delay = 400, Dflt_win_hide_delay = 400
	, Dflt_win_max_w = -1, Dflt_win_max_h = -1
	, Dflt_win_show_all_max_w = -1
	, Dflt_scan_len =  1 * Io_mgr.Len_kb
	, Dflt_scan_max = 32 * Io_mgr.Len_kb
	, Dflt_read_til_stop_fwd = -1
	, Dflt_read_til_stop_bwd = -1
	, Dflt_tmpl_tkn_max = 8192
	;
	public static final boolean
	  Dflt_win_bind_focus_blur = false
	;
}
