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
package gplx.dbs; import gplx.*;
import gplx.dbs.metas.*; import gplx.dbs.sqls.*; import gplx.dbs.sqls.wtrs.*;
class Db_diff_bldr {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private final    Sql_schema_wtr sql_bldr = new Sql_schema_wtr();
	public Db_diff_bldr() {sql_bldr.Bfr_(bfr);}
	public String Compare_db(String src_str, String trg_str) {
//			Io_url src_url = Io_url_.new_fil_(src_str);
//			Io_url trg_url = Io_url_.new_fil_(trg_str);
//			Db_conn src_conn = Db_conn_bldr.Instance.Get_or_new(src_url).Conn();
//			Db_conn trg_conn = Db_conn_bldr.Instance.Get_or_new(trg_url).Conn();
		Dbmeta_tbl_mgr src_tbls = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);
		Dbmeta_tbl_mgr trg_tbls = new Dbmeta_tbl_mgr(Dbmeta_reload_cmd_.Noop);
		return Compare_tbls(src_tbls, trg_tbls);
	}
	public String Compare_tbls(Dbmeta_tbl_mgr src_tbls, Dbmeta_tbl_mgr trg_tbls) {
		int src_len = src_tbls.Len();
		for (int i = 0; i < src_len; ++i) {
			Dbmeta_tbl_itm src_tbl = src_tbls.Get_at(i);
			Dbmeta_tbl_itm trg_tbl = trg_tbls.Get_by(src_tbl.Name());
			if (trg_tbl == null) Tbl_delete(src_tbl);
		}
		int trg_len = trg_tbls.Len();
		for (int i = 0; i < trg_len; ++i) {
			Dbmeta_tbl_itm trg_tbl = src_tbls.Get_at(i);
			Dbmeta_tbl_itm src_tbl = trg_tbls.Get_by(trg_tbl.Name());
			if (src_tbl == null) Tbl_create(trg_tbl);
		}
		return bfr.To_str_and_clear();
	}
	private void Tbl_delete(Dbmeta_tbl_itm tbl) {
		bfr.Add_str_a7("DROP TABLE ").Add_str_u8(tbl.Name()).Add_byte_nl();
	}
	private void Tbl_create(Dbmeta_tbl_itm tbl) {
//			sql_bldr.Bld_create_tbl(tbl);
	}
}
