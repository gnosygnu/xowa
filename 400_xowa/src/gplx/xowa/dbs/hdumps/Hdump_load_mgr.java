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
import gplx.dbs.*; import gplx.ios.*;
import gplx.core.btries.*; import gplx.xowa.pages.*;
class Hdump_load_mgr {
	private Hdump_db_mgr db_mgr;
	private Io_stream_zip_mgr zip_mgr = new Io_stream_zip_mgr();
	private int page_version;
	private byte[] page_text, display_ttl, content_sub;
	private ListAdp sidebar_divs = ListAdp_.new_(), img_itms = ListAdp_.new_();
	private Hdump_text_tbl text_tbl = new Hdump_text_tbl(); private ListAdp tmp_text_itms = ListAdp_.new_();
	private Bry_rdr bry_rdr = new Bry_rdr();
	public Hdump_load_mgr(Hdump_db_mgr db_mgr) {this.db_mgr = db_mgr;}
	public void Clear() {
		page_version = -1;
		page_text = display_ttl = content_sub = null;
		sidebar_divs.Clear();
		img_itms.Clear();
	}
	public void Load(Hdump_page_itm page, int page_id, byte[] page_url) {
		Db_provider provider = db_mgr.Db_provider_by_page(page_id);
		text_tbl.Select_by_page(tmp_text_itms, provider, page_id);
		Load_itm(page, page_id, page_url, tmp_text_itms);
	}
	public void Load_itm(Hdump_page_itm page, int page_id, byte[] page_url, ListAdp itms) {
		this.Clear();
		int len = itms.Count();
		for (int i = 0; i < len; ++i) {
			Hdump_text_row itm = (Hdump_text_row)itms.FetchAt(i);
			switch (itm.Tid()) {
				case Hdump_text_row_tid.Tid_body:			Load_itm_body(itm); break;
				case Hdump_text_row_tid.Tid_img:			Load_itm_img(itm); break;
				case Hdump_text_row_tid.Tid_sidebar_div:	sidebar_divs.Add(zip_mgr.Unzip(Io_stream_.Tid_gzip, itm.Data())); break;
				case Hdump_text_row_tid.Tid_display_ttl:	display_ttl = zip_mgr.Unzip(Io_stream_.Tid_gzip, itm.Data()); break;
				case Hdump_text_row_tid.Tid_content_sub:	content_sub = zip_mgr.Unzip(Io_stream_.Tid_gzip, itm.Data()); break;
			}
		}
		page.Init(page_id, page_url, page_version, display_ttl, content_sub, page_text
		, (byte[][])sidebar_divs.XtoAryAndClear(byte[].class)
		, (Hdump_img_itm[])img_itms.XtoAryAndClear(Hdump_img_itm.class)
		);
	}
	public void Load_itm_body(Hdump_text_row itm) {
		page_version = Bry_.Xto_int(itm.Meta());
		page_text = zip_mgr.Unzip(Io_stream_.Tid_gzip, itm.Data());
	}
	public void Load_itm_img(Hdump_text_row itm) {
		bry_rdr.Src_(itm.Meta());
		int img_id = itm.Sub_id();
		byte[] img_src = bry_rdr.Read_bry_to_pipe();
		int img_w = bry_rdr.Read_int_to_pipe();
		int img_h = bry_rdr.Read_int_to_pipe();
		Hdump_img_itm img_itm = new Hdump_img_itm(img_id, img_src, img_w, img_h);
		img_itms.Add(img_itm);
	}
}
