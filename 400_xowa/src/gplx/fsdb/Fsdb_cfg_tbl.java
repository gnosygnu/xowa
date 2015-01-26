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
package gplx.fsdb; import gplx.*;
import gplx.dbs.*;
public interface Fsdb_cfg_tbl extends RlsAble {
	Fsdb_cfg_tbl Ctor(Db_conn conn, boolean created);
	void Insert(String grp, String key, String val);
	void Update(String grp, String key, String val);
	int Select_as_int_or(String grp, String key, int or);
	int Select_as_int_or_fail(String grp, String key);
	String Select_as_str_or(String grp, String key, String or);
	Fsdb_cfg_grp Select_as_grp(String grp);
}
abstract class Fsdb_cfg_tbl_base {
	public abstract int Select_as_int_or(String grp, String key, int or);
	public int Select_as_int_or_fail(String grp, String key) {
		int rv = Select_as_int_or(grp, key, Int_.MinValue);
		if (rv == Int_.MinValue) throw Err_.new_fmt_("fsdb_cfg did not have itm: grp={0} key={1}", grp, key);
		return rv;
	}
}
class Fsdb_cfg_tbl_mem extends Fsdb_cfg_tbl_base implements Fsdb_cfg_tbl {
	private HashAdp grps = HashAdp_.new_();
	public Fsdb_cfg_tbl Ctor(Db_conn conn, boolean created) {return this;}
	public void Insert(String grp, String key, String val) {
		Fsdb_cfg_grp grp_itm = Grps_get_or_make(grp);
		grp_itm.Insert(key, val);
	}
	public void Update(String grp, String key, String val) {
		Fsdb_cfg_grp grp_itm = Grps_get_or_make(grp);
		grp_itm.Update(key, val);
	}
	@Override public int Select_as_int_or(String grp, String key, int or) {
		Fsdb_cfg_grp grp_itm = Grps_get_or_null(grp);
		return grp_itm == null ? or : grp_itm.Get_int_or(grp, or);
	}
	public String Select_as_str_or(String grp, String key, String or) {
		Fsdb_cfg_grp grp_itm = Grps_get_or_null(grp);
		return grp_itm == null ? or : grp_itm.Get_str_or(grp, or);
	}
	public Fsdb_cfg_grp Select_as_grp(String grp) {return Grps_get_or_null(grp);}
	public void Rls() {}
	private Fsdb_cfg_grp Grps_get_or_make(String grp) {
		Fsdb_cfg_grp rv = (Fsdb_cfg_grp)grps.Fetch(grp);
		if (rv == null) {
			rv = new Fsdb_cfg_grp(grp);
			grps.Add(grp, rv);
		}
		return rv;
	}
	public Fsdb_cfg_grp Grps_get_or_null(String grp) {return (Fsdb_cfg_grp)grps.Fetch(grp);}
}
class Fsdb_cfg_tbl_sql extends Fsdb_cfg_tbl_base implements Fsdb_cfg_tbl {
	private Db_conn conn;
	private Db_stmt stmt_insert, stmt_update, stmt_select;
	public Fsdb_cfg_tbl Ctor(Db_conn conn, boolean created) {
		this.conn = conn;
		if (created) Create_table();
		return this;
	}
	private void Create_table() {
		Sqlite_engine_.Tbl_create(conn, Tbl_name, Tbl_sql);
		Sqlite_engine_.Idx_create(conn, Idx_main);
	}
	private Db_stmt Insert_stmt() {return Db_stmt_.new_insert_(conn, Tbl_name, Fld_cfg_grp, Fld_cfg_key, Fld_cfg_val);}
	public void Insert(String grp, String key, String val) {
		if (stmt_insert == null) stmt_insert = Insert_stmt();
		stmt_insert.Clear()
		.Val_str(grp)
		.Val_str(key)
		.Val_str(val)
		.Exec_insert();
	}	
	private Db_stmt Update_stmt() {return Db_stmt_.new_update_(conn, Tbl_name, String_.Ary(Fld_cfg_grp, Fld_cfg_key), Fld_cfg_val);}
	public void Update(String grp, String key, String val) {
		if (stmt_update == null) stmt_update = Update_stmt();
		stmt_update.Clear()
		.Val_str(val)
		.Val_str(grp)
		.Val_str(key)
		.Exec_update();
	}
	private Db_stmt Select_stmt() {
		Db_qry_select qry = Db_qry_.select_val_(Tbl_name, Fld_cfg_val, gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_cfg_grp, ""), Db_crt_.eq_(Fld_cfg_key, "")));
		return conn.New_stmt(qry);
	}
	@Override public int Select_as_int_or(String grp, String key, int or) {return Int_.parse_or_(Select_as_str_or(grp, key, null), or);}
	public String Select_as_str_or(String grp, String key, String or) {
		if (stmt_select == null) stmt_select = Select_stmt();
		Object rv = (String)stmt_select.Clear()
			.Val_str(grp)
			.Val_str(key)
			.Exec_select_val();
		return rv == null ? or : (String)rv;
	}
	public Fsdb_cfg_grp Select_as_grp(String grp) {
		Fsdb_cfg_grp rv = null;
		Db_qry_select qry = Db_qry_.select_cols_(Tbl_name, gplx.criterias.Criteria_.And_many(Db_crt_.eq_(Fld_cfg_grp, "")), Fld_cfg_key, Fld_cfg_val);
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = conn.New_stmt(qry).Clear().Val_str(grp).Exec_select();
			while (rdr.MoveNextPeer()) {
				if (rv == null) rv = new Fsdb_cfg_grp(grp);
				String key = rdr.ReadStr(Fld_cfg_key);
				String val = rdr.ReadStr(Fld_cfg_val);
				rv.Upsert(key, val);
			}
		}
		finally {rdr.Rls();}
		return rv == null ? Fsdb_cfg_grp.Null : rv;
	}
	public void Rls() {
		if (stmt_insert != null) {stmt_insert.Rls(); stmt_insert = null;}
		if (stmt_update != null) {stmt_update.Rls(); stmt_update = null;}
		if (stmt_select != null) {stmt_select.Rls(); stmt_select = null;}
	}
	private static final String Tbl_name = "fsdb_cfg", Fld_cfg_grp = "cfg_grp", Fld_cfg_key = "cfg_key", Fld_cfg_val = "cfg_val";
	private static final String Tbl_sql = String_.Concat_lines_nl
	(	"CREATE TABLE IF NOT EXISTS fsdb_cfg"
	,	"( cfg_grp           varchar(255)        NOT NULL" 
	,	", cfg_key           varchar(255)        NOT NULL"
	,	", cfg_val           varchar(1024)       NOT NULL"
	,	");"
	);
	private static final Db_idx_itm
	  Idx_main = Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS fsdb_cfg__main       ON fsdb_cfg (cfg_grp, cfg_key, cfg_val);")
	;
}
