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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
public class Xoh_module_mgr implements Bry_fmtr_arg {
	private Xoae_app app; private Xowe_wiki wiki; private Xoae_page page; private Xoh_module_itm[] itms; private int itms_len;
	private Xoh_module_wtr wtr = new Xoh_module_wtr();
	public Xoh_module_mgr() {
		Itms_add(itm__css, itm__globals, itm__popups, itm__toc, itm__collapsible, itm__navframe, itm__gallery
		, itm__mathjax, itm__hiero, itm__top_icon, itm__title_rewrite, itm__search_suggest, itm__timeline
		, itm__xoui
		);
	}
	public Xoh_module_itm__css					Itm__css() {return itm__css;} private Xoh_module_itm__css itm__css = new Xoh_module_itm__css();
	public Xoh_module_itm__globals				Itm__globals() {return itm__globals;} private Xoh_module_itm__globals itm__globals = new Xoh_module_itm__globals();
	public Xoh_module_itm__popups				Itm__popups() {return itm__popups;} private Xoh_module_itm__popups itm__popups = new Xoh_module_itm__popups();
	public Xoh_module_itm__toc					Itm__toc() {return itm__toc;} private Xoh_module_itm__toc itm__toc = new Xoh_module_itm__toc();
	public Xoh_module_itm__collapsible			Itm__collapsible() {return itm__collapsible;} private Xoh_module_itm__collapsible itm__collapsible = new Xoh_module_itm__collapsible();
	public Xoh_module_itm__navframe				Itm__navframe() {return itm__navframe;} private Xoh_module_itm__navframe itm__navframe = new Xoh_module_itm__navframe();
	public Xoh_module_itm__gallery				Itm__gallery() {return itm__gallery;} private Xoh_module_itm__gallery itm__gallery = new Xoh_module_itm__gallery();
	public Xoh_module_itm__mathjax				Itm__mathjax() {return itm__mathjax;} private Xoh_module_itm__mathjax itm__mathjax = new Xoh_module_itm__mathjax();
	public Xoh_module_itm__hiero				Itm__hiero() {return itm__hiero;} private Xoh_module_itm__hiero itm__hiero = new Xoh_module_itm__hiero();
	public Xoh_module_itm__top_icon				Itm__top_icon() {return itm__top_icon;} private Xoh_module_itm__top_icon itm__top_icon = new Xoh_module_itm__top_icon();
	public Xoh_module_itm__search_suggest		Itm__search_suggest() {return itm__search_suggest;} private Xoh_module_itm__search_suggest itm__search_suggest = new Xoh_module_itm__search_suggest();
	public Xoh_module_itm__timeline				Itm__timeline() {return itm__timeline;} private Xoh_module_itm__timeline itm__timeline = new Xoh_module_itm__timeline();
	public Xoh_module_itm__title_rewrite		Itm__title_rewrite() {return itm__title_rewrite;} private Xoh_module_itm__title_rewrite itm__title_rewrite = new Xoh_module_itm__title_rewrite();
	public Xoh_module_itm__xoui					Itm__xoui() {return itm__xoui;} private Xoh_module_itm__xoui itm__xoui = new Xoh_module_itm__xoui();
	public Xoh_module_mgr Init(Xoae_app app, Xowe_wiki wiki, Xoae_page page) {
		this.app = app; this.wiki = wiki; this.page = page;
		return this;
	}
	public Xoh_module_mgr Init_dflts() {
		if (page.Hdr_mgr().Toc_enabled())									itm__toc.Enabled_y_();
		if (wiki.Html_mgr().Module_mgr().Itm__top_icon().Enabled_y())		itm__top_icon.Enabled_y_();
		if (wiki.Html_mgr().Module_mgr().Itm__title_rewrite().Enabled_y())	itm__title_rewrite.Enabled_y_();
		if (app.Gui_mgr().Search_suggest_mgr().Enabled())					itm__search_suggest.Enabled_y_();
		itm__css.Enabled_y_();
		itm__globals.Enabled_y_();	// for now, always mark this and rest as exists; DATE:2014-06-09
		itm__collapsible.Enabled_y_();
		itm__navframe.Enabled_y_();
		itm__popups.Enabled_(app.Api_root().Html().Modules().Popups().Enabled());
		return this;
	}
	public void Clear() {
		for (int i = 0; i < itms_len; ++i)
			itms[i].Clear();
	}
	public void XferAry(Bry_bfr bfr, int idx) {Write(bfr, app, wiki, page);}
	public void Write(Bry_bfr bfr, Xoae_app app, Xowe_wiki wiki, Xoae_page page) {
		wtr.Init(bfr);
		wtr.Indent_add();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_module_itm itm = itms[i];
			itm.Write_css_include(app, wiki, page, wtr);
		}
		wtr.Write_css_style_bgn();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_module_itm itm = itms[i];
			itm.Write_css_script(app, wiki, page, wtr);
		}
		wtr.Write_css_style_end();
		int reset_bgn = wtr.Bfr().Len();
		wtr.Write_js_script_bgn();	// write <script> before <script src=""> b/c <script> will have cfg values that other scripts will use; EX: xowa_root_dir
		int reset_end = wtr.Bfr().Len();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_module_itm itm = itms[i];
			itm.Write_js_head_script(app, wiki, page, wtr);
		}
		wtr.Write_js_head_global_bgn();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_module_itm itm = itms[i];
			itm.Write_js_head_global(app, wiki, page, wtr);
		}
		wtr.Write_js_head_global_end();
		if (wtr.Bfr().Len() == reset_end) {			// no itms wrote to js_head
			wtr.Bfr().Delete_rng_to_end(reset_bgn);	// delete <script> declaration
			wtr.Indent_del();
		}
		else
			wtr.Write_js_script_end();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_module_itm itm = itms[i];
			itm.Write_js_include(app, wiki, page, wtr);
		}
		wtr.Write_js_script_bgn();
		for (int i = 0; i < itms_len; ++i) {
			Xoh_module_itm itm = itms[i];
			itm.Write_js_tail_script(app, wiki, page, wtr);
		}
		wtr.Write_js_script_end();
		wtr.Indent_del();
		wtr.Term();
	}
	private void Itms_add(Xoh_module_itm... ary) {
		itms_len = ary.length;
		itms = ary;
	}
}
