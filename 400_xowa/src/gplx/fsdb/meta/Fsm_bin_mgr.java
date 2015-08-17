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
import gplx.ios.*; import gplx.dbs.*;
public class Fsm_bin_mgr {
	private final Fsdb_db_mgr core_mgr; private final int mnt_id; private final Fsm_bin_tbl tbl;
	private Fsm_bin_fil[] dbs__ary = Fsm_bin_fil.Ary_empty; private int dbs__ary_len = 0; private Fsm_bin_fil nth_db;		
	public Fsm_bin_mgr(Fsdb_db_mgr core_mgr, Db_conn conn, int mnt_id) {
		this.core_mgr = core_mgr; this.mnt_id = mnt_id; 
		this.tbl = new Fsm_bin_tbl(conn, core_mgr.File__schema_is_1(), mnt_id);
	}
	public void Ctor_by_load() {
		this.dbs__ary = tbl.Select_all(core_mgr);
		this.dbs__ary_len = dbs__ary.length;
		if (dbs__ary_len > 0) this.nth_db = dbs__ary[dbs__ary_len - 1];
	}
	public int Dbs__len()					{return dbs__ary_len;}
	public Fsm_bin_fil Dbs__get_nth()		{return nth_db;}
	public Fsm_bin_fil Dbs__get_at(int i)	{return dbs__ary[i];}
	public Fsm_bin_fil Dbs__make(String file_name) {
		Fsdb_db_file db = core_mgr.File__bin_file__new(mnt_id, file_name);
		Fsm_bin_fil rv = new Fsm_bin_fil(core_mgr.File__schema_is_1(), dbs__ary_len, db.Url(), db.Url().NameAndExt(), db.Conn(), Fsm_bin_fil.Bin_len_null);
		tbl.Insert(rv.Id(), rv.Url_rel());
		Dbs__add(rv);
		return rv;
	}
	public void Insert(int db_id, int bin_id, byte owner_tid, long bin_len, Io_stream_rdr bin_rdr) {
		Fsm_bin_fil fil = dbs__ary[db_id];
		fil.Insert(bin_id, owner_tid, bin_len, bin_rdr);
		dbs__ary[db_id].Insert(bin_id, owner_tid, bin_len, bin_rdr);
	}
	public void Txn_bgn() {
		for (int i = 0; i < dbs__ary_len; ++i)
			dbs__ary[i].Conn().Txn_bgn("fsdb__meta__bin");
	}
	public void Txn_end() {
		for (int i = 0; i < dbs__ary_len; ++i)
			dbs__ary[i].Conn().Txn_end();
	}
	private void Dbs__add(Fsm_bin_fil fil) {
		this.dbs__ary = (Fsm_bin_fil[])Array_.Resize(dbs__ary, dbs__ary_len + 1);
		this.dbs__ary[dbs__ary_len++] = fil;
		this.nth_db = fil;
	}
}
