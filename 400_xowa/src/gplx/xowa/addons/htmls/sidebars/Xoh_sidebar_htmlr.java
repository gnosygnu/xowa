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
package gplx.xowa.addons.htmls.sidebars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.core.brys.*; import gplx.core.brys.fmts.*;
class Xoh_sidebar_htmlr {
	public static byte[] To_html(Bry_bfr bfr, Xowe_wiki wiki, List_adp grps) {
		Xoh_sidebar_itms_fmtr itms_fmtr = new Xoh_sidebar_itms_fmtr();
		int len = grps.Count();
		boolean popups_enabled = wiki.Html_mgr().Head_mgr().Popup_mgr().Enabled();
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
