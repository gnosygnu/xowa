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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*; import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*;
class Hdump_page_itm_save {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(10 * Io_mgr.Len_mb);
	private Hdump_text_tbl text_tbl = new Hdump_text_tbl();
	private Hdump_db_mgr text_db_mgr = new Hdump_db_mgr();
	public void Insert(Db_provider provider, Xoa_page page) {
		int page_id = page.Revision_data().Id();
		Xopg_html_data html_data = page.Html_data();
		text_tbl.Insert(provider, text_db_mgr.Next_insert_id(), page_id, Hdump_text_row_tid.Tid_body, Version_id, Bry_.Empty, Bry_.Empty);
		Insert_if_exists(provider, page_id, Hdump_text_row_tid.Tid_display_ttl, html_data.Display_ttl());
		Insert_if_exists(provider, page_id, Hdump_text_row_tid.Tid_content_sub, html_data.Content_sub());
		Insert_sidebars(provider, page_id, page, html_data.Xtn_skin_mgr());
	}
	private void Insert_if_exists(Db_provider provider, int page_id, int tid, byte[] val) {
		if (Bry_.Len_gt_0(val))
			text_tbl.Insert(provider, text_db_mgr.Next_insert_id(), page_id, tid, Version_id, Bry_.Empty, val);
	}
	private void Insert_sidebars(Db_provider provider, int page_id, Xoa_page page, Xopg_xtn_skin_mgr xtn_skin_mgr) {
		int len = xtn_skin_mgr.Count();
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = xtn_skin_mgr.Get_at(i);
			if (itm.Tid() == Xopg_xtn_skin_itm_tid.Tid_sidebar) {
				itm.Write(tmp_bfr, page);
				text_tbl.Insert(provider, text_db_mgr.Next_insert_id(), page_id, Hdump_text_row_tid.Tid_sidebar_div, Version_id, Bry_.Empty, tmp_bfr.XtoAryAndClear());
			}
		}
	}
	private static final int Version_id = 0;
}
