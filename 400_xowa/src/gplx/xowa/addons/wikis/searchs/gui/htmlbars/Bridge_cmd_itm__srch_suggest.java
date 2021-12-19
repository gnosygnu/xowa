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
package gplx.xowa.addons.wikis.searchs.gui.htmlbars;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.*;
import gplx.langs.jsons.*;
import gplx.xowa.htmls.bridges.*;
public class Bridge_cmd_itm__srch_suggest implements Bridge_cmd_itm {
	private Xoa_app app;
	public byte[] Key() {return BryUtl.NewA7("xowa.search.ui.suggest");}
	public void	Init_by_app(Xoa_app app) {
		this.app = app;
	}
	public String Exec(Json_nde data) {
		// extract vars from json
		byte[] wiki_bry = data.Get_as_bry("wiki");
		byte[] search_bry = data.Get_as_bry("search");
		byte[] cbk_func = data.Get_as_bry("cbk");

		// build vars for search
		Xowe_wiki wiki = (Xowe_wiki)app.Wiki_mgri().Get_by_or_make_init_y(wiki_bry); // handle (a) HTTP_server stoped and (b) text entered directly into search-suggest; wiki not yet instantiated
		Srch_rslt_cbk__js cbk = new Srch_rslt_cbk__js(cbk_func, search_bry);

		// do search and return result
		app.Addon_mgr().Itms__search__htmlbar().Search(wiki, search_bry, cbk);
		return cbk.To_str_and_clear();
	}
}
