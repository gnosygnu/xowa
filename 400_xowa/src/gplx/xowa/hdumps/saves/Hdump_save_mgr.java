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
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*; import gplx.xowa.hdumps.loads.*;
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
		Hdump_page_body_srl.Save(tmp_bfr, page);
		int page_id = page.Revision_data().Id();
		text_tbl.Insert(page_id, Hdump_text_row_tid.Tid_body, tmp_bfr.XtoAryAndClear());
		byte[] redlinks_bry = Write_redlinks(tmp_bfr, page.Html_data().Redlink_mgr());
		if (redlinks_bry != null)	text_tbl.Insert(page_id, Hdump_data_tid.Tid_redlink, redlinks_bry);
		byte[] imgs_bry = Write_imgs(tmp_bfr, page.Hdump_data().Imgs());
		if (imgs_bry != null)		text_tbl.Insert(page_id, Hdump_data_tid.Tid_img, imgs_bry);
	}
	public static byte[] Write_imgs(Bry_bfr bfr, ListAdp imgs) {
		int len = imgs.Count(); if (len == 0) return null; // no images; exit early, else will write blank String
		for (int i = 0; i < len; ++i) {
			Hdump_data_img__base img = (Hdump_data_img__base)imgs.FetchAt(i);
			img.Write(bfr);
		}
		return bfr.XtoAryAndClear();
	}
	private static byte[] Write_redlinks(Bry_bfr bfr, Int_list redlink_mgr) {
		int len = redlink_mgr.Len(); if (len == 0) return null;
		bfr.Add_int_variable(len);
		for (int i = 0; i < len; ++i) {
			bfr.Add_byte_pipe().Add_int_variable(redlink_mgr.Get_at(i));
		}
		return bfr.XtoAryAndClear();
	}
}
