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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
import gplx.threads.*;
import gplx.xowa.gui.views.*;
import gplx.xowa.specials.search.*;
import gplx.xowa.apis.xowa.html.modules.*;
public class Xow_popup_mgr implements GfoInvkAble, GfoEvObj {
	private Xoa_app app; private Xow_wiki wiki; private Js_wtr js_wtr = new Js_wtr();
	private int show_init_word_count = Xoapi_popups.Dflt_show_init_word_count, show_more_word_count = Xoapi_popups.Dflt_show_more_word_count;
	private Xoh_href temp_href = new Xoh_href(); 
	private Object async_thread_guard = new Object(); private Xow_popup_itm async_itm; private GfoInvkAble async_cmd_show; private int async_id_next = 1;
	public Xow_popup_mgr(Xow_wiki wiki) {
		this.wiki = wiki; this.app = wiki.App();
		ev_mgr = GfoEvMgr.new_(this);
	}
	public GfoEvMgr EvMgr() {return ev_mgr;} private GfoEvMgr ev_mgr;
	public Xow_popup_parser Parser() {return parser;} private Xow_popup_parser parser = new Xow_popup_parser();
	public void Init_by_wiki(Xow_wiki wiki) {
		parser.Init_by_wiki(wiki);
		Xoapi_popups api_popups = app.Api_root().Html().Modules().Popups();
		show_init_word_count = api_popups.Show_init_word_count();
		show_more_word_count = api_popups.Show_more_word_count();
		Ns_allowed_(api_popups.Ns_allowed());
		parser.Cfg().Show_all_if_less_than_(api_popups.Show_all_if_less_than());
		parser.Cfg().Tmpl_read_len_(api_popups.Scan_len());
		parser.Cfg().Tmpl_read_max_(api_popups.Scan_max());
		parser.Cfg().Read_til_stop_fwd_(api_popups.Read_til_stop_fwd());
		parser.Cfg().Read_til_stop_bwd_(api_popups.Read_til_stop_bwd());
		parser.Cfg().Stop_if_hdr_after_(api_popups.Stop_if_hdr_after());
		parser.Tmpl_tkn_max_(api_popups.Tmpl_tkn_max());
		if (!Env_.Mode_testing())
			parser.Tmpl_keeplist_init_(api_popups.Tmpl_keeplist());
		parser.Wrdx_mkr().Xnde_ignore_ids_(api_popups.Xnde_ignore_ids());
		parser.Html_mkr().Fmtr_popup().Fmt_(api_popups.Html_fmtr_popup());
		parser.Html_mkr().Fmtr_viewed().Fmt_(api_popups.Html_fmtr_viewed());
		parser.Html_mkr().Fmtr_wiki().Fmt_(api_popups.Html_fmtr_wiki());
		parser.Html_mkr().Fmtr_next_sect().Fmt_(api_popups.Html_fmtr_next_sect_fmt());
		GfoEvMgr_.SubSame_many(api_popups, this
		, Xoapi_popups.Evt_show_init_word_count_changed, Xoapi_popups.Evt_show_more_word_count_changed , Xoapi_popups.Evt_show_all_if_less_than_changed
		, Xoapi_popups.Evt_scan_len_changed, Xoapi_popups.Evt_scan_max_changed
		, Xoapi_popups.Evt_read_til_stop_fwd_changed, Xoapi_popups.Evt_read_til_stop_bwd_changed, Xoapi_popups.Evt_stop_if_hdr_after_changed
		, Xoapi_popups.Evt_ns_allowed_changed
		, Xoapi_popups.Evt_xnde_ignore_ids_changed, Xoapi_popups.Evt_tmpl_tkn_max_changed, Xoapi_popups.Evt_tmpl_keeplist_changed
		, Xoapi_popups.Evt_html_fmtr_popup_changed, Xoapi_popups.Evt_html_fmtr_viewed_changed, Xoapi_popups.Evt_html_fmtr_wiki_changed, Xoapi_popups.Evt_html_fmtr_next_sect_changed
		);
	}
	public String Show_init(int id, byte[] href, byte[] tooltip) {
		Xoa_page cur_page = Cur_page();
		Xog_tab_itm tab = cur_page.Tab();
		if (tab != null && tab.Tab_is_loading()) return "";	// NOTE: tab is null when previewing
		Xow_popup_itm itm = new Xow_popup_itm(id, href, tooltip, show_init_word_count);
		String rv = String_.new_utf8_(Get_popup_html(cur_page, itm));
		return tab != null && tab.Tab_is_loading() ? "" : rv;
	}
	public void Show_more(String popup_id) {
		Xoa_page cur_page = Cur_page();
		Xow_popup_itm popup_itm = Itms_get_or_null(cur_page, popup_id).Mode_more_(show_more_word_count);
		popup_itm.Popup_html_(Get_popup_html(cur_page, popup_itm));
		Show_popup_html(Cbk_xowa_popups_show_update, Mode_show_more, popup_itm);
	}
	public void Show_all(String popup_id) {
		Xoa_page cur_page = Cur_page();
		Xow_popup_itm popup_itm = Itms_get_or_null(cur_page, popup_id).Mode_all_();
		popup_itm.Popup_html_(Get_popup_html(cur_page, popup_itm));
		Show_popup_html(Cbk_xowa_popups_show_update, Mode_show_all, popup_itm);
	}
	public String Get_async_bgn(byte[] js_cbk, byte[] href) {
		if (Bry_.HasAtBgn(href, gplx.xowa.parsers.lnkes.Xop_lnke_wkr.Bry_xowa_protocol)) return null; // ignore xowa-cmd
		synchronized (async_thread_guard) {
			if (async_itm != null) async_itm.Cancel();
			async_itm = new Xow_popup_itm(++async_id_next, href, Bry_.Empty, show_init_word_count);
			String id_str = async_itm.Popup_id();
			ThreadAdp_.invk_(id_str, this, Invk_show_popup_async).Start();
			return id_str;
		}
	}
	private byte[] Get_popup_html(Xoa_page cur_page, Xow_popup_itm itm) {
		try {
			synchronized (async_thread_guard) {	// queue popups to reduce contention with Load_page_wkr; DATE:2014-08-24
//					Load_popup_wkr load_popup_wkr = new Load_popup_wkr(wiki, cur_page, itm, temp_href, ns_allowed_regy, ns_allowed_regy_key);
//					app.Thread_mgr().Page_load_mgr().Add_at_end(load_popup_wkr);
//					load_popup_wkr.Exec();
//					while (!load_popup_wkr.Rslt_done()) {
//						ThreadAdp_.Sleep(100);
//					}
//					return load_popup_wkr.Rslt_bry();
				if (itm.Canceled()) return null;
				cur_page.Popup_mgr().Itms().AddReplace(itm.Popup_id(), itm);
				app.Href_parser().Parse(temp_href, itm.Page_href(), wiki, cur_page.Ttl().Full_url());	// NOTE: use Full_url, not Page_url, else anchors won't work for non-main ns; PAGE:en.w:Project:Sandbox; DATE:2014-08-07
				Xow_wiki popup_wiki = app.Wiki_mgr().Get_by_key_or_null(temp_href.Wiki());
				popup_wiki.Init_assert();
				Xoa_ttl popup_ttl = Xoa_ttl.parse_(popup_wiki, temp_href.Page_and_anchor());
				if (ns_allowed_regy.Count() > 0 && !ns_allowed_regy.Has(ns_allowed_regy_key.Val_(popup_ttl.Ns().Id()))) return Bry_.Empty;
				itm.Init(popup_wiki.Domain_bry(), popup_ttl);
				Xoa_page popup_page = popup_wiki.Data_mgr().Get_page(popup_ttl, false);
				byte[] rv = popup_wiki.Html_mgr().Module_mgr().Popup_mgr().Parser().Parse(wiki, popup_page, cur_page.Tab(), itm);
				Update_progress_bar(app, cur_page, itm);
				return rv;
			}
		}
		catch(Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to get popup: href=~{0} err=~{1}", String_.new_utf8_(itm.Page_href()), Err_.Message_gplx_brief(e));
			return null;
		}
	}
	public static void Update_progress_bar(Xoa_app app, Xoa_page cur_page, Xow_popup_itm itm) {
		byte[] href = itm.Page_href();
		byte[] tooltip = itm.Tooltip();
		if (Bry_.Len_gt_0(tooltip))
			href = Bry_.Add(tooltip);
		Xog_win_itm__prog_href_mgr.Hover(app, cur_page, String_.new_utf8_(href)); // set page ttl again in prog bar; DATE:2014-06-28
	}
	public void Show_popup_html(String cbk, byte[] mode, Xow_popup_itm popup_itm) {
		Xog_tab_itm cur_tab = app.Gui_mgr().Browser_win().Active_tab();
		cur_tab.Html_box().Html_js_eval_script(Xow_popup_mgr_.Bld_js_cmd(js_wtr, cbk, mode, popup_itm.Page_href(), popup_itm.Popup_html()));
	}
	private void Show_popup_async() {
		try {
			synchronized (async_thread_guard) {
				Xoa_page cur_page = app.Gui_mgr().Browser_win().Active_page();
				async_itm.Popup_html_(Get_popup_html(cur_page, async_itm));
			}
			if (async_cmd_show == null)
				async_cmd_show = app.Gui_mgr().Kit().New_cmd_sync(this);
			GfoInvkAble_.InvkCmd(async_cmd_show, Invk_show_popup);
		}
		catch(Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to get popup: href=~{0} err=~{1}", String_.new_utf8_(async_itm.Page_href()), Err_.Message_gplx_brief(e));
		}
	}
	private void Show_popup() {
		if (async_itm.Canceled()) return;
		Show_popup_html(Cbk_xowa_popups_show_create, Bry_.Empty, async_itm);
	}
	public void Ns_allowed_(byte[] raw) {
		ns_allowed_regy.Clear();
		Int_obj_ref[] ns_ids = Ns_allowed_parse(wiki, raw);
		int len = ns_ids.length;
		for (int i = 0; i < len; i++) {
			Int_obj_ref ns_id = ns_ids[i];
			ns_allowed_regy.Add(ns_id, ns_id);
		}
	}
	public static Int_obj_ref[] Ns_allowed_parse(Xow_wiki wiki, byte[] raw) {
		ListAdp rv = ListAdp_.new_();
		byte[][] ary = Bry_.Split(raw, Byte_ascii.Pipe);
		int ary_len = ary.length;
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		for (int i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			int bry_len = bry.length; if (bry_len == 0) continue;	// ignore empty entries; EX: "0|"
			Xow_ns ns = Bry_.Eq(bry, Xow_ns_.Ns_name_main_bry) 
				? ns_mgr.Ns_main()
				: ns_mgr.Names_get_or_null(bry)
				;
			if (ns == null) {
				wiki.App().Usr_dlg().Log_many("", "", "popup.ns_allowed: ns not in wiki: ns=~{0} wiki=~{1}", String_.new_utf8_(bry), wiki.Domain_str());	// ns may not be in wiki; EX: Portal and www.wikidata.org
				continue;
			}
			Int_obj_ref ns_id_itm = Int_obj_ref.new_(ns.Id());
			rv.Add(ns_id_itm);
		}
		return (Int_obj_ref[])rv.Xto_ary(Int_obj_ref.class);
	}	private HashAdp ns_allowed_regy = HashAdp_.new_(); private Int_obj_ref ns_allowed_regy_key = Int_obj_ref.zero_();
	private Xoa_page Cur_page() {return app.Gui_mgr().Browser_win().Active_page();}
	private Xow_popup_itm Itms_get_or_null(Xoa_page page, String popup_id) {return (Xow_popup_itm)page.Popup_mgr().Itms().Fetch(popup_id);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_show_popup_async))								Show_popup_async();
		else if	(ctx.Match(k, Invk_show_popup))										Show_popup();
		else if	(ctx.Match(k, Xoapi_popups.Evt_show_init_word_count_changed))		show_init_word_count = m.ReadInt("v");
		else if	(ctx.Match(k, Xoapi_popups.Evt_show_more_word_count_changed))		show_more_word_count = m.ReadInt("v");
		else if	(ctx.Match(k, Xoapi_popups.Evt_show_all_if_less_than_changed))		parser.Cfg().Show_all_if_less_than_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_xnde_ignore_ids_changed))			parser.Wrdx_mkr().Xnde_ignore_ids_(m.ReadBry("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_scan_len_changed))					parser.Cfg().Tmpl_read_len_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_scan_max_changed))					parser.Cfg().Tmpl_read_max_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_read_til_stop_bwd_changed))			parser.Cfg().Read_til_stop_bwd_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_read_til_stop_fwd_changed))			parser.Cfg().Read_til_stop_fwd_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_stop_if_hdr_after_changed))			parser.Cfg().Stop_if_hdr_after_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_ns_allowed_changed))					Ns_allowed_(m.ReadBry("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_tmpl_tkn_max_changed))				parser.Tmpl_tkn_max_(m.ReadInt("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_tmpl_keeplist_changed))				parser.Tmpl_keeplist_init_(m.ReadBry("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_html_fmtr_popup_changed))			parser.Html_mkr().Fmtr_popup().Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_html_fmtr_viewed_changed))			parser.Html_mkr().Fmtr_viewed().Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_html_fmtr_wiki_changed))				parser.Html_mkr().Fmtr_wiki().Fmt_(m.ReadBry("v"));
		else if	(ctx.Match(k, Xoapi_popups.Evt_html_fmtr_next_sect_changed))		parser.Html_mkr().Fmtr_next_sect().Fmt_(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_show_popup_async = "show_popup_async", Invk_show_popup = "show_popup";
	private static final String 
	  Cbk_xowa_popups_show_update = "xowa_popups_show_update"
	, Cbk_xowa_popups_show_create = "xowa_popups_show_create"
	;
	private static final byte[]
	  Mode_show_more	= Bry_.new_ascii_("more")
	, Mode_show_all		= Bry_.new_ascii_("all")
	;
}
class Xow_popup_mgr_ {
	public static String Bld_js_cmd(Js_wtr js_wtr, String cbk, byte[] mode, byte[] href, byte[] html) {
		js_wtr.Add_str(cbk);
		js_wtr.Add_paren_bgn();
		js_wtr.Add_str_quote(mode).Add_comma();
		js_wtr.Add_str_quote(href).Add_comma();
		js_wtr.Add_str_quote_html(html);
		js_wtr.Add_paren_end_semic();
		return js_wtr.Xto_str_and_clear();
	}
}
class Load_popup_wkr implements Gfo_thread_wkr {
	private Xow_popup_itm itm; private Xoa_page cur_page; private Xoh_href temp_href;
	private HashAdp ns_allowed_regy; 
	private Int_obj_ref ns_allowed_regy_key = Int_obj_ref.zero_();
	public Load_popup_wkr(Xow_wiki wiki, Xoa_page cur_page, Xow_popup_itm itm, Xoh_href temp_href, HashAdp ns_allowed_regy, Int_obj_ref ns_allowed_regy_key) {
		this.wiki = wiki; this.cur_page = cur_page; this.itm = itm; this.temp_href = temp_href; this.ns_allowed_regy = ns_allowed_regy; this.ns_allowed_regy_key = ns_allowed_regy_key;
	}
	public String Name() {return "xowa.load_popup_wkr";}
	public boolean Resume() {return false;}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public byte[] Rslt_bry() {return rslt_bry;} private byte[] rslt_bry;
	public boolean Rslt_done() {return rslt_done;} private boolean rslt_done;
	public void Rslt_(byte[] bry) {this.rslt_done = true; rslt_bry = bry;}
	public void Exec() {
		Xoa_app app = wiki.App();
		try {
			if (itm.Canceled()) {Rslt_(null); return;}
			cur_page.Popup_mgr().Itms().AddReplace(itm.Popup_id(), itm);
			app.Href_parser().Parse(temp_href, itm.Page_href(), wiki, cur_page.Ttl().Full_url());	// NOTE: use Full_url, not Page_url, else anchors won't work for non-main ns; PAGE:en.w:Project:Sandbox; DATE:2014-08-07
			Xow_wiki popup_wiki = app.Wiki_mgr().Get_by_key_or_null(temp_href.Wiki());
			popup_wiki.Init_assert();
			Xoa_ttl popup_ttl = Xoa_ttl.parse_(popup_wiki, temp_href.Page_and_anchor());
			if (ns_allowed_regy.Count() > 0 && !ns_allowed_regy.Has(ns_allowed_regy_key.Val_(popup_ttl.Ns().Id()))) {Rslt_(Bry_.Empty); return;}
			itm.Init(popup_wiki.Domain_bry(), popup_ttl);
			Xoa_page popup_page = popup_wiki.Data_mgr().Get_page(popup_ttl, false);
			byte[] rv = popup_wiki.Html_mgr().Module_mgr().Popup_mgr().Parser().Parse(wiki, popup_page, cur_page.Tab(), itm);
			Xow_popup_mgr.Update_progress_bar(app, cur_page, itm);
			Rslt_(rv);
		}
		catch(Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to get popup: href=~{0} err=~{1}", String_.new_utf8_(itm.Page_href()), Err_.Message_gplx_brief(e));
			Rslt_(null);
		}
		finally {
			app.Thread_mgr().Page_load_mgr().Resume();
		}
	}
}
