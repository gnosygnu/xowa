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
package gplx.xowa.hdumps.saves; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.dbs.*; import gplx.xowa.files.*; import gplx.xowa.hdumps.dbs.*;
import gplx.xowa.hdumps.pages.*; import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*;
public class Hdump_save_mgr {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(10 * Io_mgr.Len_mb);
	private Hdump_text_tbl text_tbl;
	public void Tbl_(Hdump_text_tbl v) {text_tbl = v;}
	public void Update(Xoa_page page) {
		int page_id = page.Revision_data().Id();
		text_tbl.Delete_by_page(page_id);
		this.Insert(page);
	}
	public void Insert(Xoa_page page) {
		tmp_bfr.Clear();
		int page_id = page.Revision_data().Id();
		Xopg_html_data html_data = page.Html_data();
		Xopg_hdump_data hdump_data = page.Hdump_data();
		text_tbl.Insert(page_id, Hdump_text_row_tid.Tid_body, hdump_data.Body());
		Insert_files(page_id, hdump_data.Imgs());
		Insert_if_exists(page_id, Hdump_text_row_tid.Tid_display_ttl, html_data.Display_ttl());
		Insert_if_exists(page_id, Hdump_text_row_tid.Tid_content_sub, html_data.Content_sub());
		Insert_sidebars(page_id, page, html_data.Xtn_skin_mgr());
	}
	private void Insert_files(int page_id, ListAdp imgs) {
		byte[] imgs_bry = Write_imgs(tmp_bfr, imgs);
		if (imgs_bry != null)
			text_tbl.Insert(page_id, Hdump_text_row_tid.Tid_img, imgs_bry);
	}
	private void Insert_if_exists(int page_id, int tid, byte[] val) {
		if (Bry_.Len_gt_0(val))
			text_tbl.Insert(page_id, tid, val);
	}
	private void Insert_sidebars(int page_id, Xoa_page page, Xopg_xtn_skin_mgr xtn_skin_mgr) {
		int len = xtn_skin_mgr.Count();
		for (int i = 0; i < len; ++i) {
			Xopg_xtn_skin_itm itm = xtn_skin_mgr.Get_at(i);
			if (itm.Tid() == Xopg_xtn_skin_itm_tid.Tid_sidebar) {
				itm.Write(tmp_bfr, page);
				text_tbl.Insert(page_id, Hdump_text_row_tid.Tid_sidebar_div, tmp_bfr.XtoAryAndClear());
			}
		}
	}
	public static byte[] Write_imgs(Bry_bfr bfr, ListAdp imgs) {
		int len = imgs.Count(); if (len == 0) return null; // no images; exit early, else will write blank String
		for (int i = 0; i < len; ++i) {
			Xof_xfer_itm img = (Xof_xfer_itm)imgs.FetchAt(i);
			Write_img(bfr, img.Html_uid(), img.Html_w(), img.Html_h(), img.Lnki_ttl(), img.Html_view_src_rel());
		}
		return bfr.XtoAryAndClear();
	}
	private static void Write_img(Bry_bfr bfr, int uid, int img_w, int img_h, byte[] lnki_ttl, byte[] img_src_rel) {
		bfr					.Add_int_variable(uid)
			.Add_byte_pipe().Add_int_variable(img_w)
			.Add_byte_pipe().Add_int_variable(img_h)
			.Add_byte_pipe().Add(lnki_ttl)
			.Add_byte_pipe().Add(img_src_rel)
			.Add_byte_nl()
			;
	}
}
/*
<0/>|0|metadata
<1/>|title
<2/>|content_sub
<3/>|sidebar
<4/>|body
*/