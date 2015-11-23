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
package gplx.dbs.engines.mems; import gplx.*; import gplx.dbs.*; import gplx.dbs.engines.*;
import gplx.core.stores.*;
public class Db_engine__mem implements Db_engine {
	private final Hash_adp tbl_hash = Hash_adp_.new_();
	Db_engine__mem(Db_conn_info conn_info) {this.conn_info = conn_info;}
	public String		Tid() {return Db_conn_info__mem.Tid_const;}
	public Db_conn_info	Conn_info() {return conn_info;} private Db_conn_info conn_info;
	public Db_engine	New_clone(Db_conn_info conn_info) {return new Db_engine__mem(conn_info);}
	public Db_stmt		New_stmt_prep(Db_qry qry) {return new Db_stmt__mem(this, qry);}
	public Mem_tbl		Tbls_get(String name) {return (Mem_tbl)tbl_hash.Get_by(name);}
	public void			Txn_bgn(String name)	{++txn_count;} private int txn_count = 0;
	public String		Txn_end()				{--txn_count; return "";}
	public void			Txn_cxl()				{--txn_count;}
	public void			Txn_sav()				{this.Txn_end(); this.Txn_bgn("");}
	public Object		Exec_as_obj(Db_qry qry) {throw Err_.new_unimplemented();}
	public void			Conn_open() {}
	public void			Conn_term() {
		if (txn_count != 0) throw Err_.new_wo_type("Conn_term.txns still open", "txn_count", txn_count);
	}
	public Db_rdr		New_rdr__rls_manual(Object rdr_obj, String sql) {throw Err_.new_unimplemented();}
	public Db_rdr		New_rdr__rls_auto(Db_stmt stmt, Object rdr_obj, String sql) {throw Err_.new_unimplemented();}
	public DataRdr		New_rdr(java.sql.ResultSet rdr, String sql) {throw Err_.new_unimplemented();} 
	public Object		New_stmt_prep_as_obj(String sql) {throw Err_.new_unimplemented();}
	public void			Ddl_create_tbl(Db_meta_tbl meta) {
		Mem_tbl mem_tbl = new Mem_tbl(meta);
		tbl_hash.Add_if_dupe_use_nth(meta.Name(), mem_tbl);
	}
	public void			Ddl_create_idx(Gfo_usr_dlg usr_dlg, Db_meta_idx... ary) {}	// TODO: implement unique index
	public void			Ddl_append_fld(String tbl, Db_meta_fld fld)	{}
	public void			Ddl_delete_tbl(String tbl)						{}
	public void			Env_db_attach(String alias, Io_url db_url)		{}
	public void			Env_db_detach(String alias)						{}
	public boolean			Meta_tbl_exists(String tbl)						{return tbl_hash.Has(tbl);}
	public boolean			Meta_fld_exists(String tbl, String fld)			{
		Mem_tbl mem_tbl = (Mem_tbl)tbl_hash.Get_by(tbl); if (mem_tbl == null) return false;
		return mem_tbl.Meta().Flds_has(fld);
	}
//		public boolean			Meta_fld_exists(String name)					{return tbl_hash.Has(name);}
        public static final Db_engine__mem Instance = new Db_engine__mem(); Db_engine__mem() {}
}
