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
public class Db_engine__mem implements Db_engine {
	private final HashAdp tbl_hash = HashAdp_.new_();
	public Db_engine__mem(Db_url url, Db_meta_tbl[] meta_tbls) {
		this.url = url;
		int tbls_len = meta_tbls.length;
		for (int i = 0; i < tbls_len; ++i) {
			Db_meta_tbl meta_tbl = meta_tbls[i];
			Mem_tbl tbl = new Mem_tbl();
			tbl_hash.Add(meta_tbl.Name(), tbl);
		}
	}
	public String Tid() {return Db_url__mem.Tid_const;}
	public Db_url Url() {return url;} private Db_url url;
	public Db_engine	New_clone(Db_url url) {return this;}
	public Db_stmt		New_stmt_prep(Db_qry qry) {return new Db_stmt__mem(this, qry);}
	public Mem_tbl	Tbls_get(String name) {return (Mem_tbl)tbl_hash.Fetch(name);}
	public void			Txn_bgn() {++txn_count;} private int txn_count = 0;
	public void			Txn_end() {--txn_count;}
	public Object		Exec_as_obj(Db_qry qry) {throw Err_.not_implemented_();}
	public void			Conn_open() {}
	public void			Conn_term() {
		if (txn_count != 0) throw Err_.new_("Conn_term.txns still open; txn_count={0}", txn_count);
	}
	public Db_rdr		New_rdr_by_obj(Object rdr, String sql) {throw Err_.not_implemented_();}
	public DataRdr		New_rdr(java.sql.ResultSet rdr, String sql) {throw Err_.not_implemented_();} 
	public Object		New_stmt_prep_as_obj(String sql) {throw Err_.not_implemented_();}
        public static final Db_engine__mem _ = new Db_engine__mem(); Db_engine__mem() {}
}
