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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.files.xfers.*;
public class Xoa_available_wikis_mgr implements Gfo_invk {
	private Bry_fmtr itms_as_html_fmtr = Bry_fmtr.new_("\n        <li><a href=\"/site/~{domain}/\"~{itm_cls}>~{domain}</a></li>", "domain", "itm_cls");
	public Xoa_available_wikis_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	public String Itms_as_html() {
		if (itms_as_html == null) {
			String itm_cls = app.Usere().Wiki().Html_mgr().Head_mgr().Popup_mgr().Enabled() ? " class='xowa-hover-off'" : "";	// always add popup-disabled class in sidebar, even if popups aren't enabled; not worth effort to check cfg for get "current wiki"; DATE:2016-12-13
			Bry_bfr tmp_bfr = Bry_bfr_.New();
			Xow_xwiki_mgr xwiki_mgr = app.Usere().Wiki().Xwiki_mgr();
			xwiki_mgr.Sort_by_key();
			int len = xwiki_mgr.Len();
			for (int i = 0; i < len; i++) {
				Xow_xwiki_itm itm = xwiki_mgr.Get_at(i);
				if (itm.Domain_tid() == Xow_domain_tid_.Tid__home) continue;// don't show home wiki
				if (!itm.Offline()) continue;	// only show items marked Offline (added by Available_from_fsys); DATE:2014-09-21
				itms_as_html_fmtr.Bld_bfr_many(tmp_bfr, itm.Domain_bry(), itm_cls);
			}
			itms_as_html = tmp_bfr.To_str();
		}
		return itms_as_html;
	}	private String itms_as_html;
	public void Itms_reset() {itms_as_html = null;}
	public boolean Visible() {return visible;} private boolean visible = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_itms_as_html))		return this.Itms_as_html();
		else if	(ctx.Match(k, Invk_itms_refresh))		Itms_reset();
		else if	(ctx.Match(k, Invk_visible))			return Yn.To_str(visible);
		else if	(ctx.Match(k, Invk_visible_))			visible = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_visible_toggle))		{visible = !visible; app.Gui_mgr().Browser_win().Active_html_box().Html_js_eval_proc_as_str("xowa-portal-wikis-visible-toggle", Bool_.To_str_lower(visible));}
		else if	(ctx.Match(k, Invk_itms_as_html_fmtr_))	itms_as_html_fmtr.Fmt_(m.ReadBry("v"));
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_visible = "visible", Invk_visible_ = "visible_", Invk_visible_toggle = "visible_toggle", Invk_itms_as_html = "itms_as_html", Invk_itms_as_html_fmtr_ = "itms_as_html_fmtr_", Invk_itms_refresh = "itms_refresh";
}
