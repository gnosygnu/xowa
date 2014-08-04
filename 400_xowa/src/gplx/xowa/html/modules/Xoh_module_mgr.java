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
	private Xoa_app app; private Xow_wiki wiki; private Xoa_page page; private Xoh_module_itm[] itms; private int itms_len;
	private Xoh_module_wtr wtr = new Xoh_module_wtr();
	public Xoh_module_mgr() {
		Itms_add(itm_css, itm_globals, itm_popups, itm_toc, itm_collapsible, itm_navframe, itm_gallery, itm_mathjax, itm_hiero, itm_top_icon);
	}
	public Xoh_module_itm__css			Itm_css() {return itm_css;} private Xoh_module_itm__css itm_css = new Xoh_module_itm__css();
	public Xoh_module_itm__globals		Itm_globals() {return itm_globals;} private Xoh_module_itm__globals itm_globals = new Xoh_module_itm__globals();
	public Xoh_module_itm__popups		Itm_popups() {return itm_popups;} private Xoh_module_itm__popups itm_popups = new Xoh_module_itm__popups();
	public Xoh_module_itm__toc			Itm_toc() {return itm_toc;} private Xoh_module_itm__toc itm_toc = new Xoh_module_itm__toc();
	public Xoh_module_itm__collapsible	Itm_collapsible() {return itm_collapsible;} private Xoh_module_itm__collapsible itm_collapsible = new Xoh_module_itm__collapsible();
	public Xoh_module_itm__navframe		Itm_navframe() {return itm_navframe;} private Xoh_module_itm__navframe itm_navframe = new Xoh_module_itm__navframe();
	public Xoh_module_itm__gallery		Itm_gallery() {return itm_gallery;} private Xoh_module_itm__gallery itm_gallery = new Xoh_module_itm__gallery();
	public Xoh_module_itm__mathjax		Itm_mathjax() {return itm_mathjax;} private Xoh_module_itm__mathjax itm_mathjax = new Xoh_module_itm__mathjax();
	public Xoh_module_itm__hiero		Itm_hiero() {return itm_hiero;} private Xoh_module_itm__hiero itm_hiero = new Xoh_module_itm__hiero();
	public Xoh_module_itm__top_icon		Itm_top_icon() {return itm_top_icon;} private Xoh_module_itm__top_icon itm_top_icon = new Xoh_module_itm__top_icon();
	public Xoh_module_mgr Init(Xoa_app app, Xow_wiki wiki, Xoa_page page) {
		this.app = app; this.wiki = wiki; this.page = page;
		return this;
	}
	public Xoh_module_mgr Init_dflts() {
		if (page.Hdr_mgr().Toc_enabled())							itm_toc.Enabled_y_();
		if (wiki.Html_mgr().Module_mgr().Itm_top_icon().Enabled())	itm_top_icon.Enabled_y_();
		itm_css.Enabled_y_();
		itm_globals.Enabled_y_();	// for now, always mark this and rest as exists; DATE:2014-06-09
		itm_collapsible.Enabled_y_();
		itm_navframe.Enabled_y_();
		itm_popups.Enabled_(app.Api_root().Html().Modules().Popups().Enabled());
		return this;
	}
	public void Clear() {
		for (int i = 0; i < itms_len; ++i)
			itms[i].Clear();
	}
	public void XferAry(Bry_bfr bfr, int idx) {Write(bfr, app, wiki, page);}
	public void Write(Bry_bfr bfr, Xoa_app app, Xow_wiki wiki, Xoa_page page) {
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
