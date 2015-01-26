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
import gplx.dbs.*; import gplx.xowa.files.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.srls.*; import gplx.xowa.html.hzips.*;
import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.pages.*; import gplx.xowa.pages.skins.*; import gplx.xowa.hdumps.loads.*;
import gplx.xowa2.gui.*;
public class Hdump_save_mgr {
	private Bry_bfr tmp_bfr = Bry_bfr.reset_(1 * Io_mgr.Len_mb); private Xow_hzip_stats hzip_stats = new Xow_hzip_stats();
	public Xodb_wiki_page_html_tbl Tbl() {return text_tbl;} public void Tbl_(Xodb_wiki_page_html_tbl v) {text_tbl = v;} private Xodb_wiki_page_html_tbl text_tbl;
	public void Update(Xoa_page page) {
		int page_id = page.Revision_data().Id();
		text_tbl.Delete(page_id);
		this.Insert(page, hzip_stats);
	}
	public void Hdump_stats_enable_y_(Db_conn p) {hdump_stats_tbl = new Hdump_stats_tbl().Conn_(p).Create_tbl();} private Hdump_stats_tbl hdump_stats_tbl;
	public void Insert(Xoa_page page, Xow_hzip_stats hzip_stats) {
		int page_id = page.Revision_data().Id();
		Insert_body(page, hzip_stats, page_id);
		byte[] redlinks_bry = Write_redlinks(tmp_bfr, page.Hdump_data().Redlink_mgr());
		if (redlinks_bry != null)	text_tbl.Insert(page_id, Hdump_data_tid.Tid_redlink, redlinks_bry);
		byte[] imgs_bry = Write_imgs(tmp_bfr, page.Hdump_data().Data());
		if (imgs_bry != null)		text_tbl.Insert(page_id, Hdump_data_tid.Tid_img, imgs_bry);
	}
	public int Insert_body(Xoa_page page, Xow_hzip_stats hzip_stats, int page_id) {
		hpg.Init(tmp_bfr, page);
		srl_mgr.Save(hpg, tmp_bfr);
		byte[] body_bry = tmp_bfr.Xto_bry_and_clear();
		int insert_len = text_tbl.Insert(page_id, Xodb_wiki_page_html_row.Tid_page, body_bry);
		if (hdump_stats_tbl != null) hdump_stats_tbl.Insert(hpg, hzip_stats, page.Root().Root_src().length, body_bry.length, insert_len);
		return insert_len;
	}	private Hpg_srl_mgr srl_mgr = Hpg_srl_mgr._i_; private Xog_page hpg = new Xog_page();
	public static byte[] Write_imgs(Bry_bfr bfr, ListAdp imgs) {
		int len = imgs.Count(); if (len == 0) return null; // no images; exit early, else will write blank String
		for (int i = 0; i < len; ++i) {
			Hdump_data_itm itm = (Hdump_data_itm)imgs.FetchAt(i);
			itm.Data_write(bfr);
		}
		return bfr.Xto_bry_and_clear();
	}
	public static byte[] Write_redlinks(Bry_bfr bfr, Xopg_redlink_mgr redlink_mgr) {
		int len = redlink_mgr.Len(); if (len == 0) return null;
		bfr.Add_int_variable(redlink_mgr.Max());
		for (int i = 0; i < len; ++i) {
			bfr.Add_byte_pipe().Add_int_variable(redlink_mgr.Get_at(i));
		}
		return bfr.Xto_bry_and_clear();
	}
}
