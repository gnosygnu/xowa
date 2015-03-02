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
package gplx.xowa.html.hdumps.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.xowa.html.hdumps.core.*; import gplx.xowa.html.hdumps.data.srl.*;
import gplx.xowa2.gui.*; import gplx.xowa.html.hdumps.pages.*; import gplx.xowa.parsers.lnkis.redlinks.*;
public class Xohd_page_html_mgr__save {
	private Xohd_page_srl_mgr srl_mgr = Xohd_page_srl_mgr.I;
	public void Update(Bry_bfr tmp_bfr, Xohd_page_html_tbl tbl, Xoae_page page) {
		Xog_page hpg = new Xog_page();
		hpg.Ctor_from_page(tmp_bfr, page);
		tbl.Delete(page.Revision_data().Id());
		this.Insert(tmp_bfr, tbl, hpg, page.Hdump_data());
	}
	public int Insert(Bry_bfr tmp_bfr, Xohd_page_html_tbl tbl, Xog_page hpg, Xopg_hdump_data hdump_data) {
		int rv = 0;
		int page_id = hpg.Page_id();
		rv += Insert_row(tbl, page_id, Xohd_page_html_row.Tid_html		, srl_mgr.Save(hpg, tmp_bfr));
		rv += Insert_row(tbl, page_id, Xohd_page_html_row.Tid_img		, Write_imgs(tmp_bfr, hdump_data.Imgs()));
		rv += Insert_row(tbl, page_id, Xohd_page_html_row.Tid_redlink	, Write_redlinks(tmp_bfr, hdump_data.Redlink_mgr()));
		return rv;
	}
	private int Insert_row(Xohd_page_html_tbl tbl, int page_id, int row_tid, byte[] bry) {return bry == null ? 0 : tbl.Insert(page_id, row_tid, bry);}
	public static byte[] Write_redlinks(Bry_bfr bfr, Xopg_redlink_idx_list redlink_mgr) {
		int len = redlink_mgr.Len(); if (len == 0) return null;
		bfr.Add_int_variable(redlink_mgr.Max());
		for (int i = 0; i < len; ++i) {
			bfr.Add_byte_pipe().Add_int_variable(redlink_mgr.Get_at(i));
		}
		return bfr.Xto_bry_and_clear();
	}
	public static byte[] Write_imgs(Bry_bfr bfr, ListAdp imgs) {
		int len = imgs.Count(); if (len == 0) return null; // no images; exit early, else will write blank String
		for (int i = 0; i < len; ++i) {
			Xohd_data_itm itm = (Xohd_data_itm)imgs.FetchAt(i);
			itm.Data_write(bfr);
		}
		return bfr.Xto_bry_and_clear();
	}
}
