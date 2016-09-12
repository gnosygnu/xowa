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
package gplx.xowa.addons.htmls.sidebars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*;
class Xoh_sidebar_htmlr {
	public static byte[] To_html(Bry_bfr bfr, Xowe_wiki wiki, List_adp grps) {
		Xoh_sidebar_itms_fmtr itms_fmtr = new Xoh_sidebar_itms_fmtr();
		int len = grps.Count();
		boolean popups_enabled = wiki.Appe().Api_root().Html().Modules().Popups().Enabled();
		for (int i = 0; i < len; ++i) {
			Xoh_sidebar_itm grp = (Xoh_sidebar_itm)grps.Get_at(i);
			itms_fmtr.Init_by_grp(popups_enabled, grp);
			fmt.Bld_many(bfr, grp.Id(), grp.Text(), itms_fmtr);
		}
		return bfr.To_bry_and_clear();
	}
	private static final    Bry_fmt fmt = Bry_fmt.Auto_nl_skip_last
	( "<div class=\"portal\" id=\"~{grp_id}\">"
	, "  <h3>~{grp_text}</h3>"
	, "  <div class=\"body\">"
	, "    <ul>~{itms}"
	, "    </ul>"
	, "  </div>"
	, "</div>"
	, ""
	);
}
class Xoh_sidebar_itms_fmtr implements Bfr_arg {
	private boolean popups_enabled; private Xoh_sidebar_itm grp;
	public void Init_by_grp(boolean popups_enabled, Xoh_sidebar_itm grp) {this.popups_enabled = popups_enabled; this.grp = grp;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		String itm_cls = popups_enabled ? " class='xowa-hover-off'" : "";
		int len = grp.Subs__len();
		for (int i = 0; i < len; ++i) {
			Xoh_sidebar_itm itm = grp.Subs__get_at(i);
			fmt.Bld_many(bfr, itm.Id(), itm.Href(), itm_cls, itm.Atr_accesskey_and_title(), itm.Text());
		}		
	}
	private final    Bry_fmt fmt = Bry_fmt.Auto_nl_skip_last
	( ""
	, "      <li id=\"~{itm_id}\"><a href=\"~{itm_href}\"~{itm_cls}~{itm_accesskey_and_title}>~{itm_text}</a></li>"
	); 
}
