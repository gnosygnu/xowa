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
package gplx.xowa.html.hdumps.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.hdumps.*;
import gplx.dbs.*; import gplx.xowa.html.hdumps.data.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.oimgs.*; import gplx.xowa.html.hdumps.core.*;
import gplx.xowa.files.*;
import gplx.xowa2.gui.*;
class Xob_hdump_img_cmd extends Xob_itm_basic_base implements Xob_cmd {
	private Xohd_page_html_tbl text_tbl = new Xohd_page_html_tbl();
	public Xob_hdump_img_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return Key_const;} public static final String Key_const = "hdump.make.imgs";
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_run() {Exec_main();}
	public void Cmd_end() {}
	public void Cmd_print() {}
	private void Exec_main() {
		Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
		Db_conn conn = Xodb_db_file.init__file_make(wiki.Fsys_mgr().Root_dir()).Conn();
		Db_stmt stmt = Db_stmt_.new_select_as_rdr(conn, Sql_select);
		Db_rdr rdr = stmt.Exec_select_as_rdr();
		int cur_page_id = -1;
		while (rdr.Move_next()) {
			int lnki_page_id		= rdr.Read_int(0);
			if (lnki_page_id != cur_page_id) {
				Save(cur_page_id, bfr.Xto_bry_and_clear());
				cur_page_id = lnki_page_id;
			}
//				int html_uid			= rdr.Read_int(1);
//				byte[] lnki_ttl			= rdr.Read_bry(2);
//				int html_w				= rdr.Read_int(3);
//				int html_h				= rdr.Read_int(4);
//				int file_repo_id		= rdr.Read_int(5);
//				int file_ext_id			= rdr.Read_int(6);
//				boolean file_is_orig		= rdr.Read_int(7) == 1;
//				double file_time		= rdr.Read_double(8);
//				int file_page			= rdr.Read_int(9);
//				Xohd_data_itm__base.Data_write_static(bfr, 0, lnki_ttl, Byte_.Zero, 0, 0, Xof_img_size.Upright_null, html_uid, html_w, html_h, file_repo_id, file_ext_id, file_is_orig, html_w, file_time, file_page);
		}
		Save(cur_page_id, bfr.Xto_bry_and_clear());;
	}
	private void Save(int page_id, byte[] data) {
		if (page_id == -1 || data.length == 0) return;
		text_tbl.Insert(page_id, Xohd_data_tid.Tid_img, data);
	}
	private static final String Sql_select = String_.Concat_lines_nl_skip_last
	( "SELECT  lt.lnki_page_id"
	, ",       lt.html_uid"
	, ",       lt.lnki_ttl"
	, ",       xr.file_w"
	, ",       xr.file_h"
	, ",       xr.orig_repo"
	, ",       xr.lnki_ext"
	, ",       xr.file_is_orig"
	, ",       xr.lnki_time"
	, ",       xr.lnki_page"
	, "FROM    xfer_regy xr"
	, "        JOIN lnki_temp lt ON xr.file_ttl = lt.lnki_ttl"
	// , "        LEFT JOIN xtn_gallery lt ON xr.file_ttl = lt.lnki_ttl"
	// , "        LEFT JOIN xtn_imap lt ON xr.file_ttl = lt.lnki_ttl"
	, "ORDER BY "
	, "        lt.lnki_page_id"
	, ",       lt.lnki_uid"
	);
}
interface Page_async_cmd {
	void Prep();
	void Exec();
}
class Page_async_cmd__img implements Page_async_cmd {
	private Xog_page hpg;
	private ListAdp missing = ListAdp_.new_();
	public Page_async_cmd__img(Xog_page hpg) {this.hpg = hpg;}
	public void Prep() {
		int len = hpg.Img_count();
		Xohd_data_itm__base[] ary = hpg.Img_itms();
		missing.Clear();
		for (int i = 0; i < len; ++i) {
			Xohd_data_itm__base itm = ary[i];
			boolean exists = Io_mgr._.ExistsFil(itm.Html_view_url());
			if (!exists) missing.Add(itm);
		}
	}
	public void Exec() {
		int len = missing.Count();
		for (int i = 0; i < len; ++i) {
//				Xohd_data_itm__base itm = (Xohd_data_itm__base)missing.FetchAt(i);
//				byte[] bytes = null; //fsdb.Db_get()ttl, file_w,....):
//				Write file(bytes);
		}
	}
}
/*		
CREATE TABLE xtn_gallery
( src_page_id		integer		NOT NULL
, html_uid			integer		NOT NULL
, box_max			integer		NOT NULL
, box_w				integer		NOT NULL
, img_w				integer		NOT NULL
, img_pad			integer		NOT NULL
);	
*/
