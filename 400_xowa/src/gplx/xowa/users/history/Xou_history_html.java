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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.specials.*;
public class Xou_history_html implements gplx.core.brys.Bfr_arg, Xow_special_page {
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__page_history;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		this.app = wiki.Appe(); this.mgr = app.Usere().History_mgr();
		mgr.Sort();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_m001(); 
		html_grp.Bld_bfr_many(bfr, this);
		page.Db().Text().Text_bry_(bfr.To_bry_and_rls());
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = mgr.Len();
		for (int i = 0; i < len; i++) {
			Xou_history_itm itm = mgr.Get_at(i);
			html_itm.Bld_bfr_many(bfr, itm.Wiki(), itm.Page(), itm.View_count(), itm.View_end().XtoStr_fmt_yyyy_MM_dd_HH_mm());
		}		
	}	private Xou_history_mgr mgr; Xoae_app app;
	public Bry_fmtr Html_grp() {return html_grp;} Bry_fmtr html_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
		(	"<table class='sortable'>"
		,	"  <tr>"
		,	"    <th>page</th>"
		,	"    <th>wiki</th>"
		,	"    <th>views</th>"
		,	"    <th>time</th>"
		,	"  </tr>~{itms}"
		,	"</table>"
		), "itms");
	public Bry_fmtr Html_itm() {return html_itm;} Bry_fmtr html_itm = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
		(	""
		,	"  <tr>"
		,	"    <td>[[~{itm_wiki}:~{itm_page}|~{itm_page}]]</td>"
		,	"    <td>~{itm_wiki}</td>"
		,	"    <td>~{itm_count}</td>"
		,	"    <td>~{itm_last}</td>"
		,	"  </tr>"
		), "itm_wiki", "itm_page", "itm_count", "itm_last");

	public Xow_special_page Special__clone() {return this;}
}
