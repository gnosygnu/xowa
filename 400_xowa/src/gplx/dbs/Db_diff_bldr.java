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
package gplx.dbs;
import gplx.dbs.metas.*;
import gplx.dbs.sqls.wtrs.*;
import gplx.types.custom.brys.wtrs.BryWtr;
class Db_diff_bldr {
	private final BryWtr bfr = BryWtr.New();
	private final Sql_schema_wtr sql_bldr = new Sql_schema_wtr();
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
		return bfr.ToStrAndClear();
	}
	private void Tbl_delete(Dbmeta_tbl_itm tbl) {
		bfr.AddStrA7("DROP TABLE ").AddStrU8(tbl.Name()).AddByteNl();
	}
	private void Tbl_create(Dbmeta_tbl_itm tbl) {
//			sql_bldr.Bld_create_tbl(tbl);
	}
}
