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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.bits.*;
public class Xoh_head_mgr implements gplx.core.brys.Bfr_arg {
	private Xoae_app app; private Xowe_wiki wiki; private Xoae_page page; 
	private Xoh_head_itm__base[] itms; private int itms_len;
	private Xoh_head_wtr wtr = new Xoh_head_wtr();
	private final    Xoh_head_wkr
	  list__css_include			= new Xoh_head_wkr()
	, list__css_text			= new Xoh_head_wkr()
	, list__js_include			= new Xoh_head_wkr()
	, list__js_head_global		= new Xoh_head_wkr()
	, list__js_head_script		= new Xoh_head_wkr()
	, list__js_tail_script		= new Xoh_head_wkr()
	, list__js_window_onload	= new Xoh_head_wkr()
	;
	public Xoh_head_mgr() {
		Itms_add(itm__css, itm__globals, itm__xo_elem, itm__server, itm__popups, itm__toc, itm__collapsible, itm__navframe, itm__gallery, itm__gallery_styles
		, itm__mathjax, itm__graph, itm__hiero, itm__top_icon, itm__title_rewrite, itm__search_suggest, itm__timeline
		, itm__dbui, itm__pgbnr, itm__tabber, itm__page_cfg
		);
	}
	public Xoh_head_itm__css					Itm__css() {return itm__css;} private final    Xoh_head_itm__css itm__css = new Xoh_head_itm__css();
	public Xoh_head_itm__globals				Itm__globals() {return itm__globals;} private final    Xoh_head_itm__globals itm__globals = new Xoh_head_itm__globals();
	public Xoh_head_itm__xo_elem				Itm__xo_elem() {return itm__xo_elem;} private final    Xoh_head_itm__xo_elem itm__xo_elem = new Xoh_head_itm__xo_elem();
	public Xoh_head_itm__server					Itm__server() {return itm__server;} private final    Xoh_head_itm__server itm__server = new Xoh_head_itm__server();
	public Xoh_head_itm__popups					Itm__popups() {return itm__popups;} private final    Xoh_head_itm__popups itm__popups = new Xoh_head_itm__popups();
	public Xoh_head_itm__toc					Itm__toc() {return itm__toc;} private final    Xoh_head_itm__toc itm__toc = new Xoh_head_itm__toc();
	public Xoh_head_itm__collapsible			Itm__collapsible() {return itm__collapsible;} private final    Xoh_head_itm__collapsible itm__collapsible = new Xoh_head_itm__collapsible();
	public Xoh_head_itm__navframe				Itm__navframe() {return itm__navframe;} private final    Xoh_head_itm__navframe itm__navframe = new Xoh_head_itm__navframe();
	public Xoh_head_itm__top_icon				Itm__top_icon() {return itm__top_icon;} private final    Xoh_head_itm__top_icon itm__top_icon = new Xoh_head_itm__top_icon();
	public Xoh_head_itm__gallery				Itm__gallery() {return itm__gallery;} private final    Xoh_head_itm__gallery itm__gallery = new Xoh_head_itm__gallery();
	public Xoh_head_itm__gallery_styles			Itm__gallery_styles() {return itm__gallery_styles;} private final    Xoh_head_itm__gallery_styles itm__gallery_styles = new Xoh_head_itm__gallery_styles();
	public Xoh_head_itm__title_rewrite			Itm__title_rewrite() {return itm__title_rewrite;} private final    Xoh_head_itm__title_rewrite itm__title_rewrite = new Xoh_head_itm__title_rewrite();
	public Xoh_head_itm__mathjax				Itm__mathjax() {return itm__mathjax;} private final    Xoh_head_itm__mathjax itm__mathjax = new Xoh_head_itm__mathjax();
	public Xoh_head_itm__hiero					Itm__hiero() {return itm__hiero;} private final    Xoh_head_itm__hiero itm__hiero = new Xoh_head_itm__hiero();
	public Xoh_head_itm__graph					Itm__graph() {return itm__graph;} private final    Xoh_head_itm__graph itm__graph = new Xoh_head_itm__graph();
	public Xoh_head_itm__timeline				Itm__timeline() {return itm__timeline;} private final    Xoh_head_itm__timeline itm__timeline = new Xoh_head_itm__timeline();
	public Xoh_head_itm__search_suggest			Itm__search_suggest() {return itm__search_suggest;} private final    Xoh_head_itm__search_suggest itm__search_suggest = new Xoh_head_itm__search_suggest();
	public Xoh_head_itm__dbui					Itm__dbui() {return itm__dbui;} private final    Xoh_head_itm__dbui itm__dbui = new Xoh_head_itm__dbui();
	public Xoh_head_itm__pgbnr					Itm__pgbnr() {return itm__pgbnr;} private final    Xoh_head_itm__pgbnr itm__pgbnr = new Xoh_head_itm__pgbnr();
	public Xoh_head_itm__tabber					Itm__tabber() {return itm__tabber;} private final    Xoh_head_itm__tabber itm__tabber = new Xoh_head_itm__tabber();
	public Xoh_head_itm__page_cfg				Itm__page_cfg() {return itm__page_cfg;} private final    Xoh_head_itm__page_cfg itm__page_cfg = new Xoh_head_itm__page_cfg();
	public Xoh_head_mgr Init(Xoae_app app, Xowe_wiki wiki, Xoae_page page) {
		this.app = app; this.wiki = wiki; this.page = page;
		return this;
	}
	public Xoh_head_mgr Init_dflts() {
		if (page.Wtxt().Toc().Enabled())									itm__toc.Enabled_y_();
		if (wiki.Html_mgr().Head_mgr().Itm__top_icon().Enabled_y())			itm__top_icon.Enabled_y_();
		if (wiki.Html_mgr().Head_mgr().Itm__title_rewrite().Enabled_y())	itm__title_rewrite.Enabled_y_();
		if (app.Addon_mgr().Itms__search__htmlbar().Enabled())				itm__search_suggest.Enabled_y_();
		if (!wiki.Html_mgr().Html_wtr().Lnki_wtr().File_wtr().File_wtr().Alt_show_in_html()) itm__page_cfg.Enabled_y_();
		itm__css.Enabled_y_();
		itm__globals.Enabled_y_();	// for now, always mark this and rest as exists; DATE:2014-06-09
		itm__xo_elem.Enabled_y_();
		itm__collapsible.Enabled_y_();
		itm__navframe.Enabled_y_();
		boolean popups_enabled 
			=	!app.Mode().Tid_is_http()		// do not enable if http_server, else js errors when calling xowa_exec; DATE:2016-06-22
			&&	wiki.Html_mgr().Head_mgr().Popup_mgr().Enabled();	// check user_cfg
		itm__popups.Enabled_(popups_enabled);
		return this;
	}
	public void Clear() {
		for (int i = 0; i < itms_len; ++i)
			itms[i].Clear();
	}
	public void Bfr_arg__add(Bry_bfr bfr) {Write(bfr, app, wiki, page);}
	public void Write(Bry_bfr bfr, Xoae_app app, Xowe_wiki wiki, Xoae_page page) {
		Set_wkrs();
		wtr.Init(bfr);
		wtr.Indent_add();
		int len = 0;
		len = list__css_include.Len();
		for (int i = 0; i < len; ++i) {
			Xoh_head_itm__base itm = list__css_include.Get_at(i);
			itm.Write_css_include(app, wiki, page, wtr);
		}
		len = list__css_text.Len();
		if (len > 0) {
			wtr.Write_css_style_bgn();
			for (int i = 0; i < len; ++i) {
				Xoh_head_itm__base itm = list__css_text.Get_at(i);
				itm.Write_css_text(app, wiki, page, wtr);
			}
			wtr.Write_css_style_end();
		}
		int head_global_len = list__js_head_global.Len();
		int head_script_len = list__js_head_script.Len();
		if (head_global_len + head_script_len > 0) {
			len = head_script_len;
			wtr.Write_js_script_bgn();	// write <script> before <script src=""> b/c <script> will have cfg values that other scripts will use; EX: xowa_root_dir
			for (int i = 0; i < head_script_len; ++i) {
				Xoh_head_itm__base itm = list__js_head_script.Get_at(i);
				itm.Write_js_head_script(app, wiki, page, wtr);
			}
			wtr.Write_js_head_global_bgn();
			len = head_global_len;
			for (int i = 0; i < head_global_len; ++i) {
				Xoh_head_itm__base itm = list__js_head_global.Get_at(i);
				itm.Write_js_head_global(app, wiki, page, wtr);
			}
			wtr.Write_js_head_global_end();
			wtr.Write_js_script_end();
		}
		len = list__js_include.Len();
		for (int i = 0; i < len; ++i) {
			Xoh_head_itm__base itm = list__js_include.Get_at(i);
			itm.Write_js_include(app, wiki, page, wtr);
		}
		int tail_script_len = list__js_tail_script.Len();
		int window_onload_len = list__js_window_onload.Len();
		if (tail_script_len + window_onload_len > 0) {
			wtr.Write_js_script_bgn();
			for (int i = 0; i < tail_script_len; ++i) {
				Xoh_head_itm__base itm = list__js_tail_script.Get_at(i);
				itm.Write_js_tail_script(app, wiki, page, wtr);
			}
			if (window_onload_len > 0) {
				wtr.Write_js_line(Js__window_onload__bgn);
				for (int i = 0; i < window_onload_len; ++i) {
					Xoh_head_itm__base itm = list__js_window_onload.Get_at(i);
					itm.Write_js_window_onload(app, wiki, page, wtr);
				}
				wtr.Write_js_line(Js__window_onload__end);
			}
			wtr.Write_js_script_end();
		}
		wtr.Indent_del();
		wtr.Term();
	}
	private static final    byte[]
	  Js__window_onload__bgn = Bry_.new_a7("window.onload = function() {")
	, Js__window_onload__end = Bry_.new_a7("};")
	;
	private void Itms_add(Xoh_head_itm__base... ary) {
		this.itms_len = ary.length;
		this.itms = ary;
	}
	private void Set_wkrs() {
		list__css_include.Clear();
		list__css_text.Clear();
		list__js_include.Clear();
		list__js_head_global.Clear();
		list__js_head_script.Clear();
		list__js_tail_script.Clear();
		list__js_window_onload.Clear();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_head_itm__base itm = itms[i];
			boolean enabled = itm.Enabled();
			if (enabled) {
				int flag = itms[i].Flags();
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__css_include))		list__css_include.Add(itm);
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__css_text))			list__css_text.Add(itm);
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__js_include))		list__js_include.Add(itm);
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__js_head_global))	list__js_head_global.Add(itm);
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__js_head_script))	list__js_head_script.Add(itm);
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__js_tail_script))	list__js_tail_script.Add(itm);
				if (Bitmask_.Has_int(flag, Xoh_head_itm__base.Flag__js_window_onload))	list__js_window_onload.Add(itm);
			}
		}
	}
}
class Xoh_head_wkr {
	private final    List_adp list = List_adp_.New_w_size(Xoh_head_itm__base.Idx__max);
	public int Len() {return list.Count();}
	public void Clear() {list.Clear();}
	public void Add(Xoh_head_itm__base itm) {list.Add(itm);}
	public Xoh_head_itm__base Get_at(int i) {return (Xoh_head_itm__base)list.Get_at(i);}
}
