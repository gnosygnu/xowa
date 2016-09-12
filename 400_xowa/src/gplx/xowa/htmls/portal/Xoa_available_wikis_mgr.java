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
package gplx.xowa.htmls.portal; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.files.xfers.*;
public class Xoa_available_wikis_mgr implements Gfo_invk {
	private Bry_fmtr itms_as_html_fmtr = Bry_fmtr.new_("\n        <li><a href=\"/site/~{domain}/\"~{itm_cls}>~{domain}</a></li>", "domain", "itm_cls");
	public Xoa_available_wikis_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	public String Itms_as_html() {
		if (itms_as_html == null) {
			String itm_cls = app.Api_root().Html().Modules().Popups().Enabled() ? " class='xowa-hover-off'" : "";
			Bry_bfr tmp_bfr = Bry_bfr_.New(); // NOTE: do not use app.Utl__bfr_mkr().Get_k004() as it is being used simultaneously by another caller; TODO_OLD: find call
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
