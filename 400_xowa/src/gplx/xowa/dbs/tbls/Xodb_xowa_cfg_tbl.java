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
package gplx.xowa.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
import gplx.dbs.*;
public class Xodb_xowa_cfg_tbl {
	public Db_provider Provider() {return provider;} public Xodb_xowa_cfg_tbl Provider_(Db_provider provider) {this.provider = provider; return this;} Db_provider provider;
	private DataRdr Select(String grp) {
		Db_qry qry = Db_qry_.select_cols_(Tbl_name, Db_crt_.eq_(Fld_cfg_grp, grp), Fld_cfg_key, Fld_cfg_val);
		return provider.Exec_qry_as_rdr(qry);
	}
	public long Select_val_as_long_or(String grp, String key, long or) {return Long_.parse_or_(Select_val(grp, key), or);}
	public int Select_val_as_int(String grp, String key) {return Int_.parse_(Select_val(grp, key));}
	public String Select_val(String grp, String key) {return Select_val_or(grp, key, null);}
	public String Select_val_or(String grp, String key, String or) {
		Db_qry_select qry = Db_qry_.select_val_(Tbl_name, Fld_cfg_val, Where_grp_key(grp, key));
		String rv = (String)qry.ExecRdr_val(provider);
		return rv == null ? or : rv;
	}
	public String Select_val_or_make(String grp, String key, String or) {
		String rv = Select_val_or(grp, key, null);
		if (rv == null) {
			rv = or;
			Insert_str(grp, key, rv);
		}
		return rv;
	}
	public KeyVal[] Select_kvs(String grp, String match_key, String_obj_ref match_val) {
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = this.Select(grp);
			while (rdr.MoveNextPeer()) {
				String key = rdr.ReadStr(Fld_cfg_key);
				String val = rdr.ReadStr(Fld_cfg_val);
				if (String_.Eq(key, match_key)) match_val.Val_(val);
				KeyVal kv = KeyVal_.new_(key, val);
				tmp_list.Add(kv);
			}
			return (KeyVal[])tmp_list.Xto_ary(KeyVal.class);
		}
		finally {rdr.Rls(); tmp_list.Clear();}		
	}	ListAdp tmp_list = ListAdp_.new_();
	public void Delete(String grp, String key) {
		Db_stmt stmt = Db_stmt_.Null;
		try {
			stmt = Db_stmt_.new_delete_(provider, Tbl_name, String_.Ary(Fld_cfg_grp, Fld_cfg_key));
			stmt.Val_str_(grp).Val_str_(key).Exec_delete();
		}	finally {stmt.Rls();}		
	}
	public void Insert_byte(String grp, String key, byte val)			{Insert_str(grp, key, Byte_.Xto_str(val));}
	public void Insert_int(String grp, String key, int val)				{Insert_str(grp, key, Int_.Xto_str(val));}
	public void Insert_str_by_bry(String grp, String key, byte[] val)	{Insert_str(grp, key, String_.new_utf8_(val));}
	public void Insert_str(String grp, String key, String val)			{Insert_str(provider, grp, key, val);}
	public static void Insert_str(Db_provider p, String grp, String key, String val) {
		Db_qry qry = Db_qry_.insert_(Tbl_name)
			.Arg_(Fld_cfg_grp     , grp)
			.Arg_(Fld_cfg_key     , key)
			.Arg_(Fld_cfg_val     , val)
		; 
		p.Exec_qry(qry);
	}
	public Db_stmt Update_stmt() {return Db_stmt_.new_update_(provider, Tbl_name, String_.Ary(Fld_cfg_grp, Fld_cfg_key), Fld_cfg_val);}
	public void Update(Db_stmt stmt, String grp, String key, long val) {Update(stmt, grp, key, Long_.Xto_str(val));}
	public void Update(Db_stmt stmt, String grp, String key, int val) {Update(stmt, grp, key, Int_.Xto_str(val));}
	public void Update(Db_stmt stmt, String grp, String key, String val) {
		stmt.Clear()
			.Val_str_(val)
			.Val_str_(grp)
			.Val_str_(key)
			.Exec_update();
	}
	public void Update(String grp, String key, int val) {Update(grp, key, Int_.Xto_str(val));}
	public void Update(String grp, String key, String val) {
		Db_qry qry = Db_qry_.update_common_(Tbl_name, Where_grp_key(grp, key), KeyVal_.new_(Fld_cfg_val, val));
		provider.Exec_qry(qry);
	}
	private gplx.criterias.Criteria Where_grp_key(String grp, String key) {return Db_crt_.eqMany_(KeyVal_.new_(Fld_cfg_grp, grp), KeyVal_.new_(Fld_cfg_key, key));}
	public static final String Tbl_name = "xowa_cfg", Fld_cfg_grp = "cfg_grp", Fld_cfg_key = "cfg_key", Fld_cfg_val = "cfg_val";
	public static void Create_table(Db_provider p) {Sqlite_engine_.Tbl_create(p, Tbl_name, Tbl_sql);}
	public static void Create_index(Db_provider p)	{Sqlite_engine_.Idx_create(p, Idx_select);}
	private static final String Tbl_sql = String_.Concat_lines_nl
	(  "CREATE TABLE IF NOT EXISTS xowa_cfg"
	,	"( cfg_grp             varchar(1024)       NOT NULL"
	,	", cfg_key             varchar(1024)       NOT NULL"
	,	", cfg_val             blob                NOT NULL"
	,	");"
	);
	private static final Db_idx_itm
		Idx_select	= Db_idx_itm.sql_("CREATE UNIQUE INDEX IF NOT EXISTS xowa_cfg__grp_key             ON xowa_cfg (cfg_grp, cfg_key);")
	;
}
