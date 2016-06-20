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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.dbs.*;
public class Fsm_bin_mgr {
	private final    Fsdb_db_mgr core_mgr; private final    int mnt_id; private final    Fsm_bin_tbl tbl;
	private final    Ordered_hash db_hash = Ordered_hash_.New();
	private Fsm_bin_fil nth_db;
	public Fsm_bin_mgr(Fsdb_db_mgr core_mgr, Db_conn conn, int mnt_id) {
		this.core_mgr = core_mgr; this.mnt_id = mnt_id; 
		this.tbl = new Fsm_bin_tbl(conn, core_mgr.File__schema_is_1(), mnt_id);
	}
	public void Ctor_by_load() {
		Fsm_bin_fil[] db_ary = tbl.Select_all(core_mgr);
		int len = db_ary.length;
		for (int i = 0; i < len; ++i) {
			Fsm_bin_fil db_fil = db_ary[i];
			db_hash.Add(db_fil.Id(), db_fil);
		}
		if (len > 0) this.nth_db = db_ary[len - 1];
	}
	public int Dbs__len()							{return db_hash.Len();}
	public Fsm_bin_fil Dbs__get_nth()				{return nth_db;}
	public Fsm_bin_fil Dbs__get_at(int i)			{return (Fsm_bin_fil)db_hash.Get_at(i);}
	public Fsm_bin_fil Dbs__get_by_or_null(int i)	{return (Fsm_bin_fil)db_hash.Get_by(i);}
	public Fsm_bin_fil Dbs__make(String file_name) {return Dbs__make(db_hash.Len(), file_name);}
	public Fsm_bin_fil Dbs__make(int id, String file_name) {
		Fsdb_db_file db = core_mgr.File__bin_file__new(mnt_id, file_name);
		Fsm_bin_fil rv = new Fsm_bin_fil(core_mgr.File__schema_is_1(), id, db.Url(), db.Url().NameAndExt(), db.Conn(), Fsm_bin_fil.Bin_len_null);
		tbl.Insert(id, rv.Url_rel());
		db_hash.Add(id, rv);
		this.nth_db = rv;
		return rv;
	}
	public void Insert(int db_id, int bin_id, byte owner_tid, long bin_len, Io_stream_rdr bin_rdr) {
		Fsm_bin_fil fil = (Fsm_bin_fil)db_hash.Get_by(db_id);
		fil.Insert(bin_id, owner_tid, bin_len, bin_rdr);
		fil.Insert(bin_id, owner_tid, bin_len, bin_rdr);
	}
	public void Txn_bgn() {
		int len = db_hash.Len();
		for (int i = 0; i < len; ++i)
			this.Dbs__get_at(i).Conn().Txn_bgn("fsdb__meta__bin");
	}
	public void Txn_end() {
		int len = db_hash.Len();
		for (int i = 0; i < len; ++i)
			this.Dbs__get_at(i).Conn().Txn_end();
	}
}
