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
package gplx.xowa.hdumps.loads; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.dbs.*; import gplx.ios.*;
import gplx.xowa.dbs.*; import gplx.xowa.pages.*; import gplx.xowa.hdumps.core.*; import gplx.xowa.hdumps.dbs.*; import gplx.xowa.hdumps.pages.*; import gplx.xowa.pages.skins.*;
public class Hdump_load_mgr {
	private Hdump_text_tbl text_tbl = new Hdump_text_tbl(); private Bry_rdr rdr = new Bry_rdr(); // private Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	private ListAdp tmp_rows = ListAdp_.new_(), img_itms = ListAdp_.new_();
	public Hdump_load_mgr() {}
	public byte Zip_tid() {return zip_tid;} public void Zip_tid_(byte v) {zip_tid = v;} private byte zip_tid = gplx.ios.Io_stream_.Tid_file;
	public void Load2(Hdump_page hpg, Db_provider provider, int page_id) {
		text_tbl.Provider_(provider).Select_by_page(tmp_rows, page_id);
		Load_rows(hpg, page_id, Xoa_url.blank_(), tmp_rows);
	}
	public void Load(Hdump_page hpg, Xodb_fsys_mgr db_fsys_mgr, int html_db_id, int page_id) {
		Db_provider provider = db_fsys_mgr.Get_by_idx(html_db_id).Provider();
		text_tbl.Provider_(provider).Select_by_page(tmp_rows, page_id);
		Load_rows(hpg, page_id, hpg.Page_url(), tmp_rows);
	}
	public void Load_rows(Hdump_page hpg, int page_id, Xoa_url page_url, ListAdp rows) {
		hpg.Init(page_id, page_url);
		img_itms.Clear();
		int len = rows.Count();
		for (int i = 0; i < len; ++i) {
			Hdump_text_row row = (Hdump_text_row)rows.FetchAt(i);
			switch (row.Tid()) {
				case Hdump_text_row_tid.Tid_body:			Hdump_page_body_srl.Load(hpg, rdr, row.Data()); break;
				case Hdump_text_row_tid.Tid_data:			Load_data(hpg, row); break;
			}
		}
		rows.Clear();
	}
	public void Load_data(Hdump_page hpg, Hdump_text_row row) {
		rdr.Src_(row.Data());
		while (!rdr.Pos_is_eos()) {
			int tid = rdr.Read_int_to_pipe();
			switch (tid) {
				case Hdump_data_tid.Tid_img		: Load_data_img(); break;				// 1|0|A.png|0|220|110|...
				case Hdump_data_tid.Tid_gallery	: Load_data_gallery(hpg); break;		// 3|0|800
				case Hdump_data_tid.Tid_redlink	: Load_data_redlink(hpg); break;		// 2|2|0|1
			}
		}
		if (img_itms.Count() > 0) hpg.Img_itms_((Hdump_data_img__base[])img_itms.XtoAryAndClear(Hdump_data_img__base.class));
	}
	private void Load_data_img() {
		int tid = rdr.Read_int_to_pipe();
		byte[] lnki_ttl = rdr.Read_bry_to_pipe();
		int html_uid = rdr.Read_int_to_pipe();
		int html_w = rdr.Read_int_to_pipe();
		int html_h = rdr.Read_int_to_pipe();
		int file_repo_id = rdr.Read_int_to_pipe();
		int file_ext_id = rdr.Read_int_to_pipe();
		boolean file_is_orig = rdr.Read_yn_to_pipe();
		int file_w = rdr.Read_int_to_pipe();
		double file_time = rdr.Read_double_to_pipe();
		int file_page = rdr.Read_int_to_pipe();
		Hdump_data_img__base img_itm = null;
		switch (tid) {
			case Hdump_data_img__base.Tid_basic		: img_itm = new Hdump_data_img__basic(); break;
			case Hdump_data_img__base.Tid_gallery	: img_itm = new Hdump_data_img__gallery().Init_by_gallery(rdr.Read_int_to_pipe(), rdr.Read_int_to_pipe(), rdr.Read_int_to_pipe()); break;
		}
		img_itm.Init_by_base(lnki_ttl, html_uid, html_w, html_h, file_repo_id, file_ext_id, file_is_orig, file_w, file_time, file_page);
		rdr.Pos_add_one();
		img_itms.Add(img_itm);
	}
	public void Load_data_redlink(Hdump_page hpg) {
		int len = rdr.Read_int_to_pipe();
		int[] redlink_uids = new int[len];
		for (int i = 0; i < len; ++i)
			redlink_uids[i] = rdr.Read_int_to_pipe();
		hpg.Redlink_uids_(redlink_uids);
	}
	public void Load_data_gallery(Hdump_page hpg) {
		int uid = rdr.Read_int_to_pipe();
		int box_max = rdr.Read_int_to_pipe();
		hpg.Gly_itms().Add(uid, new Hdump_data_gallery(uid, box_max));
	}
}
