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
package gplx.dbs.utls; import gplx.*; import gplx.dbs.*;
import gplx.core.stores.*;
import gplx.dbs.qrys.*;
public class PoolIds {
	public int FetchNext(Db_conn conn, String url) {
		Db_qry__select_cmd cmd = Db_qry_.select_().From_(Tbl_Name).Where_(Db_crt_.New_eq(Fld_id_path, url));
		int rv = 0;//boolean isNew = true;
		DataRdr rdr = DataRdr_.Null;
		try {
			rdr = conn.Exec_qry_as_old_rdr(cmd);
			if (rdr.MoveNextPeer()) {
				rv = rdr.ReadInt(Fld_id_next_id);
			}
		}
		finally {rdr.Rls();}
		return rv;
	}
	public int FetchNextAndCommit(String dbInfo, String url) {
		Db_conn conn = Db_conn_pool.Instance.Get_or_new(dbInfo);
		int rv = PoolIds.Instance.FetchNext(conn, url);
		PoolIds.Instance.Commit(conn, url, rv + 1);
		return rv;
	}
	public void Commit(Db_conn conn, String url, int val) {
		int rv = conn.Exec_qry(Db_qry_.update_(Tbl_Name, Db_crt_.New_eq(Fld_id_path, url)).Val_str(Fld_id_path, url).Val_int(Fld_id_next_id, val));
		if (rv == 0) {
			rv = conn.Exec_qry(Db_qry_.insert_(Tbl_Name).Val_str(Fld_id_path, url).Val_int(Fld_id_next_id, val));
		}
		if (rv != 1) throw Err_.new_wo_type("failed to update nextId", "url", url, "nextId", val);
	}
	public static final    String Tbl_Name					= "pool_ids";
	@gplx.Internal protected static final    String Fld_id_path				= "id_path";
	@gplx.Internal protected static final    String Fld_id_next_id			= "id_next_id";
	public static final    PoolIds Instance = new PoolIds(); PoolIds() {}
}
