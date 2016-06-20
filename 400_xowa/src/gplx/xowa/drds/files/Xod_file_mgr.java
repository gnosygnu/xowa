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
package gplx.xowa.drds.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
import gplx.core.threads.*;
import gplx.xowa.drds.pages.*;
import gplx.xowa.files.*; import gplx.xowa.files.gui.*;
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
