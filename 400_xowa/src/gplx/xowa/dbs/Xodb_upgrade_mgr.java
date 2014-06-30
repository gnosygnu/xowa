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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.xowa.dbs.tbls.*;
class Xodb_upgrade_mgr {
	public static void Upgrade(Xodb_mgr_sql db_mgr, KeyVal[] kv_ary, String version_key, String version_val) {
//			String version_new = null;
//			if	(String_.Eq(version_val, "0.6.2.0")) {
//				Xodb_upgrade_mgr_v0_6_2_0.Upgrade(db_mgr, kv_ary);
//				version_new = "0.6.2.1";
//			}
//			if (version_new != null) {
//				db_mgr.Tbl_xowa_cfg().Update(Xodb_mgr_sql.Grp_wiki_init, version_key, version_new);
//			}
	}
}
//	class Xodb_upgrade_mgr_v0_6_2_0 {
//		public static void Upgrade(Xodb_mgr_sql db_mgr, KeyVal[] kv_ary) {
//			Db_provider p = db_mgr.Fsys_mgr().Core_provider();
//			Fix_storage_format(p, db_mgr, kv_ary);
//			Fix_category_version(p, db_mgr);
//		}
//		private static void Fix_storage_format(Db_provider p, Xodb_mgr_sql db_mgr, KeyVal[] kv_ary) {	// storage_format saved incorrectly as int
//			int len = kv_ary.length;
//			String gfs_data_storage_format = Xoa_gfs_mgr.Build_code(Xow_wiki.Invk_db_mgr, Xodb_mgr_sql.Invk_data_storage_format);
//			for (int i = 0; i < len; i++) {
//				KeyVal kv = kv_ary[i];
//				String kv_key = kv.Key();
//				if (String_.Eq(kv_key, gfs_data_storage_format)) {
//					byte data_storage_format_byte = Byte_.parse_(kv.Val_to_str_or_empty());
//					String data_storage_format_name = Xoi_dump_mgr.Wtr_tid_to_str(data_storage_format_byte);
//					kv.Val_(data_storage_format_name);	// update memory
//					db_mgr.Tbl_xowa_cfg().Update(Xodb_mgr_sql.Grp_wiki_init, gfs_data_storage_format, data_storage_format_name); // update_database
//					break;
//				}
//			}			
//		}
//		private static void Fix_category_version(Db_provider p, Xodb_mgr_sql db_mgr) {
//			Db_qry qry = Db_qry_.select_().From_(Xodb_categorylinks_tbl.Tbl_name).Cols_(Xodb_categorylinks_tbl.Fld_cl_type_id).Where_(Db_crt_.eq_(Xodb_categorylinks_tbl.Fld_cl_type_id, ));
//			Db_stmt stmt = Db_stmt_.Null;
//			DataRdr rdr = DataRdr_.Null; 
//			int types = 0;
//			try {
//				stmt = db_mgr.Fsys_mgr().Category_provider().Prepare(qry);
//				rdr = stmt.Exec_select();
//				while (rdr.MoveNextPeer()) {
//					++types;
//				}
//			}	finally {rdr.Rls(); stmt.Rls();}
//			boolean version_is_1 = types <= 1;	// if 0 or 1 types assume version_1 (1=page only; 0=not set up)
//			db_mgr.Category_version_update(true);	// assume version_1; will be wrong if user actually did version_2, but currently version_1 vs version_2 has no 
//		}
//	}
