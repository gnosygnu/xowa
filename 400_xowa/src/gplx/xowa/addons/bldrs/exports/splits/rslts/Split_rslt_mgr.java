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
package gplx.xowa.addons.bldrs.exports.splits.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
import gplx.dbs.*;
public class Split_rslt_mgr {
	private final    Db_conn wkr_conn;
	private Db_stmt rslt_type_stmt, rslt_db_stmt;
	private Io_url db_url; private int db_id, ns_id;
	private int score_min, score_max;		
	private Split_rslt_wkr[] rslt_ary = new Split_rslt_wkr[Split_rslt_tid_.Tid_max];
	public Split_rslt_mgr(Db_conn wkr_conn) {
		this.wkr_conn = wkr_conn;
		for (int i = 0; i < Split_rslt_tid_.Tid_max; ++i)
			rslt_ary[i] = Split_rslt_wkr_.Noop;
	}
	public void Init() {
		// init rslt_db
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("rslt_db"
		, Dbmeta_fld_itm.new_int("db_id")
		, Dbmeta_fld_itm.new_long("db_size")
		, Dbmeta_fld_itm.new_long("obj_size")
		, Dbmeta_fld_itm.new_int("ns_id")
		, Dbmeta_fld_itm.new_int("score_min")
		, Dbmeta_fld_itm.new_int("score_max")
		));
		this.rslt_db_stmt = wkr_conn.Stmt_insert("rslt_db", "db_id", "db_size", "obj_size", "ns_id", "score_min", "score_max");

		// init rslt_type
		wkr_conn.Meta_tbl_remake(Dbmeta_tbl_itm.New("rslt_type"
		, Dbmeta_fld_itm.new_int("db_id")
		, Dbmeta_fld_itm.new_byte("tbl_id")
		, Dbmeta_fld_itm.new_int("row_count")
		, Dbmeta_fld_itm.new_long("obj_size")
		));
		this.rslt_type_stmt = wkr_conn.Stmt_insert("rslt_type", "db_id", "tbl_id", "row_count", "obj_size");
	}
	public void Reg_wkr(Split_rslt_wkr wkr) {
		rslt_ary[wkr.Tid()] = wkr;
		wkr.On__init(this, wkr_conn);
	}
	public long Db_size() {return db_size;} private long db_size;
	public void Db_size_add_(int v) {db_size += v;}
	public void On__nth__new(int db_id, Io_url db_url, int ns_id) {
		this.db_id = db_id; this.db_size = 0; this.db_url = db_url;
		this.ns_id = ns_id;
		this.score_min = Int_.Max_value;
		this.score_max = Int_.Min_value;
		for (Split_rslt_wkr wkr : rslt_ary)
			wkr.On__nth__new(db_id);
	}
	public void Score_set(int score)	{
		if (score < score_min) score_min = score;
		if (score > score_max) score_max = score;
	}
	public void On__nth__rls(Db_conn trg_conn) {
		Trg_stats(trg_conn, rslt_ary);
		wkr_conn.Txn_bgn("wkr_rslts");
		rslt_db_stmt.Clear().Val_int("db_id", db_id)
			.Val_long("db_size", Io_mgr.Instance.QueryFil(db_url).Size())
			.Val_long("obj_size", db_size)
			.Val_int("ns_id", ns_id)
			.Val_int("score_min", score_min).Val_int("score_max", score_max).Exec_insert();
		for (int i = 0; i < Split_rslt_tid_.Tid_max; ++i)
			rslt_type_stmt.Clear().Val_int("db_id", db_id).Val_int("tbl_id", i)
				.Val_int("row_count", rslt_ary[i].Row_count())
				.Val_long("obj_size", rslt_ary[i].Obj_size())
				.Exec_insert();
		for (Split_rslt_wkr wkr : rslt_ary)
			wkr.On__nth__rls();
		wkr_conn.Txn_end();
	}
	private static void Trg_stats(Db_conn trg_conn, Split_rslt_wkr[] rlst_ary) {
		Wkr_stats_tbl tbl = new Wkr_stats_tbl(trg_conn);
		tbl.Create_tbl();
		Db_stmt stmt = tbl.Insert_stmt();
		for (int i = 0 ; i < Split_rslt_tid_.Tid_max; ++i) {
			Split_rslt_wkr rslt_wkr = rlst_ary[i];
			tbl.Insert(stmt, rslt_wkr.Tid(), rslt_wkr.Row_count(), rslt_wkr.Obj_size());
		}
	}
	public void Term() {
		rslt_db_stmt = Db_stmt_.Rls(rslt_db_stmt);
		rslt_type_stmt = Db_stmt_.Rls(rslt_type_stmt);
		for (Split_rslt_wkr wkr : rslt_ary)
			wkr.On_term();
	}
}
