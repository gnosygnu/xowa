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
package gplx.xowa.drds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
import gplx.core.threads.*;
import gplx.xowa.drds.pages.*;
import gplx.xowa.files.*; import gplx.xowa.guis.cbks.js.*;
import gplx.xowa.htmls.*;
public class Xod_file_mgr {
	private final    Gfo_thread_pool thread_pool = new Gfo_thread_pool();
	public void Load_files(Xow_wiki wiki, Xod_page_itm pg, Xog_js_wkr js_wkr) {
		Xoh_page hpg = pg.Hpg();
		List_adp img_list = To_img_list(hpg.Img_mgr());
		Xof_file_wkr img_wkr = new Xof_file_wkr(wiki.File__orig_mgr(), wiki.File__bin_mgr(), wiki.File__mnt_mgr(), wiki.App().User().User_db_mgr().Cache_mgr(), wiki.File__repo_mgr(), js_wkr, hpg, img_list);
		thread_pool.Add_at_end(img_wkr);
		thread_pool.Run();			
	}
	private static List_adp To_img_list(Xoh_img_mgr img_mgr) {
		List_adp rv = List_adp_.New();
		int len = img_mgr.Len();
		for (int i = 0; i < len; ++i)
			rv.Add(img_mgr.Get_at(i));
		return rv;
	}
}
