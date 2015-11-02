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
//namespace gplx.xowa.htmls.core.to_delete {
//	using gplx.xowa.wikis.data; using gplx.xowa.wikis.data.tbls; using gplx.xowa.htmls.core.makes.imgs;
//	using gplx.xowa.guis; using gplx.xowa.wikis.pages; using gplx.xowa.parsers.lnkis.redlinks;
//	public class Xohd_page_html_mgr__save {
//		public void Update(Bry_bfr tmp_bfr, Xowd_html_tbl tbl, Xoae_page page) {
//			Xoh_page hpg = new Xoh_page().Ctor_by_page(tmp_bfr, page);
//			tbl.Delete(page.Revision_data().Id());
//			this.Insert(tmp_bfr, tbl, hpg, page.Hdump_data());
//		}
//		public int Insert(Bry_bfr tmp_bfr, Xowd_html_tbl tbl, Xoh_page hpg, Xopg_hdump_data hdump_data) {
//			int rv = 0;
//			int page_id = hpg.Page_id();
//			rv += Insert_row(tbl, page_id, Xowd_html_row.Tid__img		, Write_imgs(tmp_bfr, hdump_data.Imgs()));
//			rv += Insert_row(tbl, page_id, Xowd_html_row.Tid__redlink	, Write_redlinks(tmp_bfr, hdump_data.Redlink_mgr()));
//			return rv;
//		}
//		private int Insert_row(Xowd_html_tbl tbl, int page_id, int row_tid, byte[] bry) {return bry == null ? 0 : tbl.Insert(page_id, row_tid, bry);}
//		public static byte[] Write_redlinks(Bry_bfr bfr, Xopg_redlink_idx_list redlink_mgr) {
//			int len = redlink_mgr.Len(); if (len == 0) return null;
//			for (int i = 0; i < len; ++i)
//				bfr.Add_int_variable(redlink_mgr.Get_at(i)).Add_byte_pipe();
//			return bfr.To_bry_and_clear();
//		}
//		public static byte[] Write_imgs(Bry_bfr bfr, List_adp imgs) {
//			int len = imgs.Count(); if (len == 0) return null; // no images; exit early, else will write blank String
//			for (int i = 0; i < len; ++i) {
//				Xohd_img_itm itm = (Xohd_img_itm)imgs.Get_at(i);
//				itm.Data_write(bfr);
//			}
//			return bfr.To_bry_and_clear();
//		}
//	}
//}
