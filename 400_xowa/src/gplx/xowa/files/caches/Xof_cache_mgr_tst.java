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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import org.junit.*; import gplx.dbs.*;
public class Xof_cache_mgr_tst {
//		@Before public void init() {fxt.Reset();} private Xof_cache_mgr_fxt fxt = new Xof_cache_mgr_fxt();
	@Test  	public void Basic() {
//			Xou_cache_itm itm = fxt.Bldr_itm("A.png").Make();
//			fxt.Test_get_n(itm);
//			fxt.Exec_update(itm);
//			fxt.Test_get_y(itm);
//			fxt.Test_viewed_data(1, 123);
//			fxt.Exec_update(itm);
//			fxt.Test_viewed_data(2, 124);
//			fxt.Exec_db_save(itm);
//			fxt.Test_db_itms(itm);
	}
}
//	class Xof_cache_mgr_fxt {
//		public Xof_cache_mgr_fxt Reset() {
//			return this;
//		}
//	}
//	class Xof_cache_itm_mkr {
////		private byte[] dir; private byte[] ttl; private boolean is_orig; private int w, h; private double time; private int page; private long size;
//		private byte db_state;
//		private int lnki_site; private byte[] lnki_ttl; private int lnki_type; private double lnki_upright; private int lnki_w; private int lnki_h; private double lnki_time; private int lnki_page;
//		private int orig_wiki; private byte[] orig_ttl; private int orig_ext; private int file_w; private int file_h; private double file_time; private int file_page;
//		private long temp_file_size; private int temp_view_count; private long temp_view_date; private int temp_w;
//		private Bry_bfr key_bfr = Bry_bfr.new_();
//		public Xof_cache_itm_mkr() {this.Reset();}
//		private void Reset() {
//			db_state = Db_cmd_mode.Tid_ignore;
//			lnki_site = orig_wiki = -1;
//			lnki_ttl = orig_ttl = null;
//			lnki_type = Byte_.Max_value_127;
//			lnki_upright = Xof_img_size.Upright_null;
//			lnki_w = lnki_h = file_w = file_h = temp_w = Xof_img_size.Size_null;
//			lnki_time = file_time = Xof_lnki_time.Null;
//			lnki_page = file_page = Xof_lnki_page.Null;
//			orig_ext = Xof_ext_.Id_unknown;
//			temp_file_size = -1;
//			temp_view_count = -1;
//			temp_view_date = -1;			
//		}
//		public Xof_cache_itm_mkr Init(String dir_str, String ttl_str, boolean is_orig, int w) {
////			this.dir = Bry_.new_u8(dir_str);
////			this.ttl = Bry_.new_u8(ttl_str);
////			this.is_orig = is_orig;
////			this.w = w;
//			return this;
//		}
//		public Xou_cache_itm Make() {
//			return new Xou_cache_itm(key_bfr, db_state, lnki_site, lnki_ttl, lnki_type, lnki_upright, lnki_w, lnki_h, file_w, file_h, );
//			this.Reset();
//		}
//	}
