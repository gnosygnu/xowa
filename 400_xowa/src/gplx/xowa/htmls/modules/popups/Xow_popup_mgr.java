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
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.threads.*;
import gplx.core.js.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.guis.views.*;
import gplx.xowa.specials.*;
public class Xow_popup_mgr implements Gfo_invk, Gfo_evt_itm {
	private Xoae_app app; private Xowe_wiki wiki; private Js_wtr js_wtr = new Js_wtr();
	private int show_init_word_count, show_more_word_count;
	private Xoa_url tmp_url = Xoa_url.blank();
	private static final Object thread_lock = new Object();
	public Xow_popup_mgr(Xowe_wiki wiki) {
		this.wiki = wiki; this.app = wiki.Appe();
		ev_mgr = new Gfo_evt_mgr(this);
	}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr;
	public Xow_popup_parser Parser() {return parser;} private Xow_popup_parser parser = new Xow_popup_parser();
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled = true;	// TEST: false will fail Xob_init_base_tst; DATE:2016-12-13
	public void Init_by_wiki(Xowe_wiki wiki) {
		parser.Init_by_wiki(wiki);
		wiki.App().Cfg().Bind_many_wiki(this, wiki
		, Cfg__enabled
		, Cfg__show_init_word_count, Cfg__show_more_word_count
		, Cfg__show_all_if_less_than, Cfg__read_til_stop_fwd, Cfg__read_til_stop_bwd, Cfg__stop_if_hdr_after
		, Cfg__tmpl_tkn_max, Cfg__tmpl_keeplist
		, Cfg__ns_allowed, Cfg__xnde_ignore_ids, Cfg__scan_len, Cfg__scan_max
		);
	}
	public String Show_init(String popup_id, byte[] href, byte[] tooltip) {
		Xoae_page cur_page = Cur_page();
		Xog_tab_itm tab = cur_page.Tab_data().Tab();
		if (tab != null && tab.Tab_is_loading()) return "";	// NOTE: tab is null when previewing
		Xow_popup_itm itm = new Xow_popup_itm(popup_id, href, tooltip, show_init_word_count);
		String rv = String_.new_u8(Get_popup_html(Cur_wiki(), cur_page, itm));
		return tab != null && tab.Tab_is_loading() ? "" : rv;
	}
	public String Show_more(String popup_id) {
		Xoae_page cur_page = Cur_page();
		Xow_popup_itm popup_itm = Itms_get_or_null(cur_page, popup_id).Mode_more_(show_more_word_count);
		byte[] html = Get_popup_html(Cur_wiki(), cur_page, popup_itm);
		popup_itm.Popup_html_(html);
		Show_popup_html(popup_itm, Cbk_update_popup_html, Mode_show_more);
		return String_.new_u8(html);
	}
	public void Show_all(String popup_id) {
		Xoae_page cur_page = Cur_page();
		Xow_popup_itm popup_itm = Itms_get_or_null(cur_page, popup_id).Mode_all_();
		popup_itm.Popup_html_(Get_popup_html(Cur_wiki(), cur_page, popup_itm));
		Show_popup_html(popup_itm, Cbk_update_popup_html, Mode_show_all);
	}
	public static boolean Running() {
		boolean rv = false;
		synchronized (thread_lock) {
			rv = running;
		}
		return rv;
	}	private static boolean running = false;
	private static void Running_(boolean v) {
		synchronized (thread_lock) {
			running = v;
		}
	}
	private byte[] Get_popup_html(Xowe_wiki cur_wiki, Xoae_page cur_page, Xow_popup_itm itm) {
		try {
			synchronized (thread_lock) {	// queue popups to reduce contention with Load_page_wkr; DATE:2014-08-24
//					Load_popup_wkr load_popup_wkr = new Load_popup_wkr(wiki, cur_page, itm, temp_href, ns_allowed_regy, ns_allowed_regy_key);
//					app.Thread_mgr().Page_load_mgr().Add_at_end(load_popup_wkr);
//					load_popup_wkr.Exec();
//					while (!load_popup_wkr.Rslt_done()) {
//						Thread_adp_.Sleep(100);
//					}
//					return load_popup_wkr.Rslt_bry();
				Running_(true);
				if (itm.Canceled()) return null;
				cur_page.Popup_mgr().Itms().AddIfDupeUseNth(itm.Popup_id(), itm);
				app.Html__href_parser().Parse_as_url(tmp_url, itm.Page_href(), wiki, cur_page.Ttl().Full_url());	// NOTE: use Full_url, not Page_url, else anchors won't work for non-main ns; PAGE:en.w:Project:Sandbox; DATE:2014-08-07
				if (!Xoa_url_.Tid_is_pagelike(tmp_url.Tid())) return Bry_.Empty;		// NOTE: do not get popups for "file:///"; DATE:2015-04-05
				Xowe_wiki popup_wiki = (Xowe_wiki)app.Wiki_mgr().Get_by_or_null(tmp_url.Wiki_bry());
				popup_wiki.Init_assert();
				Xoa_ttl popup_ttl = Xoa_ttl.Parse(popup_wiki, tmp_url.To_bry_page_w_anch());
				switch (popup_ttl.Ns().Id()) {
					case Xow_ns_.Tid__media:
					case Xow_ns_.Tid__file:
						return Bry_.Empty;		// do not popup for media or file
					case Xow_ns_.Tid__special:
						if (!Xow_special_meta_.Itm__popup_history.Match_ttl(popup_ttl)) return Bry_.Empty;	// do not popup for special, unless popupHistory; DATE:2015-04-20
						break;
				}
				if (ns_allowed_regy.Len() > 0 && !ns_allowed_regy.Has(ns_allowed_regy_key.Val_(popup_ttl.Ns().Id()))) return Bry_.Empty;
				itm.Init(popup_wiki.Domain_bry(), popup_ttl);
				int wait_count = 0;
				while (gplx.xowa.guis.views.Load_page_wkr.Running() && ++wait_count < 100) {
					Thread_adp_.Sleep(10);
				}
				Xoae_page popup_page = popup_wiki.Data_mgr().Load_page_by_ttl(popup_ttl);
				byte[] rv = popup_wiki.Html_mgr().Head_mgr().Popup_mgr().Parser().Parse(wiki, popup_page, cur_page.Tab_data().Tab(), itm);
				Update_progress_bar(app, cur_wiki, cur_page, itm);
				return rv;
			}
		}
		catch(Exception e) {
			app.Usr_dlg().Warn_many("", "", "failed to get popup: href=~{0} err=~{1}", itm.Page_href(), Err_.Message_gplx_full(e));
			return null;
		}
		finally {
			Running_(false);
		}
	}
	public static void Update_progress_bar(Xoae_app app, Xowe_wiki cur_wiki, Xoae_page cur_page, Xow_popup_itm itm) {
		byte[] href = itm.Page_href();
		byte[] tooltip = itm.Tooltip();
		if (Bry_.Len_gt_0(tooltip))
			href = Bry_.Add(tooltip);
		Xog_win_itm__prog_href_mgr.Hover(app, app.Gui_mgr().Browser_win().Cfg().Status__show_short_url(), cur_wiki, cur_page, String_.new_u8(href)); // set page ttl again in prog bar; DATE:2014-06-28
	}
	private void Show_popup_html(Xow_popup_itm popup_itm, String cbk, byte[] mode) {
		// build js cmd
		js_wtr.Func_init(cbk);
		js_wtr.Prm_str(popup_itm.Popup_id());
		js_wtr.Prm_bry(mode);
		js_wtr.Prm_bry(popup_itm.Popup_html());
		js_wtr.Func_term();
		String js_cmd = js_wtr.To_str_and_clear();

		// send it
		Xog_tab_itm cur_tab = app.Gui_mgr().Browser_win().Active_tab();
		cur_tab.Html_box().Html_js_eval_script(js_cmd);
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
	public static Int_obj_ref[] Ns_allowed_parse(Xowe_wiki wiki, byte[] raw) {
		List_adp rv = List_adp_.New();
		byte[][] ary = Bry_split_.Split(raw, Byte_ascii.Pipe);
		int ary_len = ary.length;
		Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		for (int i = 0; i < ary_len; i++) {
			byte[] bry = ary[i];
			int bry_len = bry.length; if (bry_len == 0) continue;	// ignore empty entries; EX: "0|"
			Xow_ns ns = Bry_.Eq(bry, Xow_ns_.Bry__main) 
				? ns_mgr.Ns_main()
				: ns_mgr.Names_get_or_null(bry)
				;
			if (ns == null) {
				wiki.Appe().Usr_dlg().Log_many("", "", "popup.ns_allowed: ns not in wiki: ns=~{0} wiki=~{1}", String_.new_u8(bry), wiki.Domain_str());	// ns may not be in wiki; EX: Portal and www.wikidata.org
				continue;
			}
			Int_obj_ref ns_id_itm = Int_obj_ref.New(ns.Id());
			rv.Add(ns_id_itm);
		}
		return (Int_obj_ref[])rv.ToAry(Int_obj_ref.class);
	}	private Hash_adp ns_allowed_regy = Hash_adp_.New(); private Int_obj_ref ns_allowed_regy_key = Int_obj_ref.New_zero();
	private Xoae_page Cur_page() {return app.Gui_mgr().Browser_win().Active_page();}
	private Xowe_wiki Cur_wiki() {return app.Gui_mgr().Browser_win().Active_tab().Wiki();}
	private Xow_popup_itm Itms_get_or_null(Xoae_page page, String popup_id) {return (Xow_popup_itm)page.Popup_mgr().Itms().GetByOrNull(popup_id);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if      (ctx.Match(k, Cfg__enabled))										enabled = m.ReadYn("v");
		else if (ctx.Match(k, Cfg__show_init_word_count))							show_init_word_count = m.ReadInt("v");
		else if (ctx.Match(k, Cfg__show_more_word_count))							show_more_word_count = m.ReadInt("v");
		else if (ctx.Match(k, Cfg__show_all_if_less_than))							parser.Cfg().Show_all_if_less_than_(m.ReadInt("v"));
		else if (ctx.Match(k, Cfg__read_til_stop_fwd))								parser.Cfg().Read_til_stop_fwd_(m.ReadInt("v"));
		else if (ctx.Match(k, Cfg__read_til_stop_bwd))								parser.Cfg().Read_til_stop_bwd_(m.ReadInt("v"));
		else if (ctx.Match(k, Cfg__stop_if_hdr_after))								parser.Cfg().Stop_if_hdr_after_(m.ReadInt("v"));
		else if	(ctx.Match(k, Cfg__tmpl_tkn_max))									parser.Tmpl_tkn_max_(m.ReadInt("v"));
		else if	(ctx.Match(k, Cfg__tmpl_keeplist))									parser.Tmpl_keeplist_init_(m.ReadBry("v"));
		else if	(ctx.Match(k, Cfg__ns_allowed))										Ns_allowed_(m.ReadBry("v"));
		else if	(ctx.Match(k, Cfg__xnde_ignore_ids))								parser.Wrdx_mkr().Xnde_ignore_ids_(m.ReadBry("v"));
		else if	(ctx.Match(k, Cfg__scan_len))										parser.Cfg().Tmpl_read_len_(m.ReadInt("v"));
		else if	(ctx.Match(k, Cfg__scan_max))										parser.Cfg().Tmpl_read_max_(m.ReadInt("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String
	  Cbk_update_popup_html = "window.xowa.popups.UpdatePopupHtml"
	;
	private static final byte[]
	  Mode_show_more	= Bry_.new_a7("more")
	, Mode_show_all		= Bry_.new_a7("all")
	;
	private static final String
	  Cfg__enabled					= "xowa.addon.popups.enabled"
	, Cfg__show_init_word_count		= "xowa.addon.popups.content.show_init_word_count"
	, Cfg__show_more_word_count		= "xowa.addon.popups.content.show_more_word_count"
	, Cfg__show_all_if_less_than	= "xowa.addon.popups.content.show_all_if_less_than"
	, Cfg__read_til_stop_fwd		= "xowa.addon.popups.content.read_til_stop_fwd"
	, Cfg__read_til_stop_bwd		= "xowa.addon.popups.content.read_til_stop_bwd"
	, Cfg__stop_if_hdr_after		= "xowa.addon.popups.content.stop_if_hdr_after"
	, Cfg__tmpl_tkn_max				= "xowa.addon.popups.wtxt.tmpl_tkn_max"
	, Cfg__tmpl_keeplist			= "xowa.addon.popups.wtxt.tmpl_keeplist"
	, Cfg__ns_allowed				= "xowa.addon.popups.ns_allowed"
	, Cfg__xnde_ignore_ids			= "xowa.addon.popups.content.xnde_ignore_ids"
	, Cfg__scan_len					= "xowa.addon.popups.scanner.scan_len"
	, Cfg__scan_max					= "xowa.addon.popups.scanner.scan_max"
	;
	public static final String
	  Cfg__win_show_delay			= "xowa.addon.popups.window.show_delay"
	, Cfg__win_hide_delay			= "xowa.addon.popups.window.hide_delay"
	, Cfg__win_max_w				= "xowa.addon.popups.window.max_w"
	, Cfg__win_max_h				= "xowa.addon.popups.window.max_h"
	, Cfg__win_show_all_max_w		= "xowa.addon.popups.window.show_all_max_w"
	, Cfg__win_bind_focus_blur		= "xowa.addon.popups.window.bind_focus_blur"
	;
}
