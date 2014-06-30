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
public class Fsdb_db_bin_mgr implements RlsAble {
	private Io_url dir;
	private Fsdb_db_bin_fil[] itms = Fsdb_db_bin_fil.Ary_empty; private int itms_len = 0;
	private Fsdb_db_bin_fil itms_n;
	private Db_provider provider;
	private Fsdb_db_bin_mgr(Io_url dir) {this.dir = dir;}
	public int Len() {return itms.length;}
	public long Db_bin_max() {return db_bin_max;}
	public int Insert_to_bin() {return insert_to_bin;} public Fsdb_db_bin_mgr Insert_to_bin_(int v) {insert_to_bin = v; return this;} private int insert_to_bin = Fsdb_mnt_mgr.Insert_to_bin_null;
	public Fsdb_db_bin_mgr Db_bin_max_(long v) {
		db_bin_max = v;
		for (int i = 0; i < itms_len; i++)
			itms[i].Bin_max_(v);
		return this;
	}	private long db_bin_max = Io_mgr.Len_mb * Long_.X_by_int(188);
	public Fsdb_db_bin_fil Get_at(int i) {return itms[i];}
	private Fsdb_db_bin_fil Get_cur() {return itms_len == 0 ? null : itms[itms_len - 1];}
	public void Txn_open() {		
		Get_cur().Provider().Txn_mgr().Txn_bgn_if_none();
	}
	public void Txn_save() {		
		Fsdb_db_bin_tbl.Commit_all(provider, itms);
		Get_cur().Provider().Txn_mgr().Txn_end_all();
	}
	public void Rls() {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Fsdb_db_bin_fil itm = itms[i];
			itm.Rls();
		}
	}
	public int Get_id_for_insert(long bin_len) {
		if (insert_to_bin != Fsdb_mnt_mgr.Insert_to_bin_null) return insert_to_bin;	// insert_to_bin specified; return it
		if (itms_n.Bin_len() > itms_n.Bin_max())
			Itms_add(0);
		return itms_n.Id();
	}
	public void Increment(long bin_len) {
		long new_bin_len = itms_n.Bin_len() + bin_len;
		itms_n.Bin_len_(new_bin_len);
	}
	public long Insert(int db_id, int bin_id, byte owner_tid, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		Fsdb_db_bin_fil bin_fil = itms[db_id];
		return bin_fil.Insert(bin_id, owner_tid, bin_len, bin_rdr);
	}
	public static Fsdb_db_bin_mgr load_(Db_provider p, Io_url dir) {
		Fsdb_db_bin_mgr rv = new Fsdb_db_bin_mgr(dir);
		rv.provider = p;
		rv.itms = Fsdb_db_bin_tbl.Select_all(p, dir);
		rv.itms_len = rv.itms.length;
		rv.itms_n = rv.itms[rv.itms_len - 1];
		return rv;
	}
	public static Fsdb_db_bin_mgr make_(Db_provider p, Io_url dir) {
		Fsdb_db_bin_tbl.Create_table(p);
		Fsdb_db_bin_mgr rv = new Fsdb_db_bin_mgr(dir);
		rv.provider = p;
		rv.Itms_add(0);
		return rv;
	}
	private void Itms_add(long bin_len) {
		Fsdb_db_bin_fil cur = Get_cur();
		if (cur != null) {
			cur.Provider().Txn_mgr().Txn_end_all();
			cur.Provider().Rls();
		}
		int new_itms_len = itms_len + 1;
		Fsdb_db_bin_fil[] new_itms = new Fsdb_db_bin_fil[new_itms_len];
		for (int i = 0; i < itms_len; i++)
			new_itms[i] = itms[i];
		itms_n = Fsdb_db_bin_fil.make_(itms_len, url_(dir, itms_len), bin_len, db_bin_max);
		itms = new_itms;
		itms_len = new_itms_len;
		itms[itms_len - 1] = itms_n;
		this.Txn_open();
	}
	private static Io_url url_(Io_url dir, int id) {
		return dir.GenSubFil_ary("fsdb.bin.", Int_.XtoStr_PadBgn(id, 4), ".sqlite3");
	}
}
