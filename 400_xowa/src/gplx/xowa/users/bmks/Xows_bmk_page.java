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
package gplx.xowa.users.bmks; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.core.primitives.*;
import gplx.xowa.htmls.bridges.dbuis.tbls.*;
import gplx.xowa.users.data.*; import gplx.xowa.specials.*;
import gplx.xowa.wikis.pages.*;
public class Xows_bmk_page implements Xow_special_page {
	public Xow_special_meta Special__meta() {return Xow_special_meta_.Itm__bookmarks;}
	public void Special__gen(Xow_wiki wikii, Xoa_page pagei, Xoa_url url, Xoa_ttl ttl) {
		Xowe_wiki wiki = (Xowe_wiki)wikii; Xoae_page page = (Xoae_page)pagei;
		Xoa_app app = wiki.App();
		Dbui_tbl_itm__bmk ui_tbl = Dbui_tbl_itm__bmk.get_or_new(app, app.User().User_db_mgr().Bmk_mgr().Tbl__itm());
		page.Html_data().Head_mgr().Itm__dbui().Init(app).Enabled_y_();
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_m001();
		ui_tbl.Select(bfr, Xoud_bmk_mgr.Owner_root);

		Xopage_html_data page_data = new Xopage_html_data(this.Special__meta().Display_ttl(), bfr.To_bry_and_rls());
		page_data.Apply(page);
	}
	public Xow_special_page Special__clone() {return this;}
}
