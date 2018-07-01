/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
//		private Bry_bfr key_bfr = Bry_bfr_.New();
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
