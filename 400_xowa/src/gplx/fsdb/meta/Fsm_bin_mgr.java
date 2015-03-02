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
import gplx.dbs.*;
public class Fsm_bin_mgr implements RlsAble {
	private Io_url dir;
	private final Fsm_bin_tbl tbl = new Fsm_bin_tbl();
	private Fsm_bin_fil[] fil_ary = Fsm_bin_fil.Ary_empty; private int fil_ary_len = 0;
	private Fsm_bin_fil fil_cur;
	public int Len()			{return fil_ary.length;}
	public long Db_bin_max()	{return db_bin_max;}
	public int Insert_to_bin() {return insert_to_bin;} public Fsm_bin_mgr Insert_to_bin_(int v) {insert_to_bin = v; return this;} private int insert_to_bin = Fsm_mnt_mgr.Insert_to_bin_null;
	public Fsm_bin_mgr Db_bin_max_(long v) {
		db_bin_max = v;
		for (int i = 0; i < fil_ary_len; i++)
			fil_ary[i].Bin_max_(v);
		return this;
	}	private long db_bin_max = Io_mgr.Len_mb * Long_.Xby_int(188);
	public Fsm_bin_fil Get_at(int i) {return fil_ary[i];}
	private Fsm_bin_fil Get_cur() {return fil_ary_len == 0 ? null : fil_ary[fil_ary_len - 1];}
	public void Txn_open() {
		Get_cur().Conn().Txn_mgr().Txn_bgn_if_none();
	}
	public void Txn_save() {		
		tbl.Commit_all(fil_ary);
		Get_cur().Conn().Txn_mgr().Txn_end_all();
	}
	public void Rls() {
		int len = fil_ary.length;
		for (int i = 0; i < len; i++) {
			Fsm_bin_fil itm = fil_ary[i];
			itm.Rls();
		}
	}
	public int Get_id_for_insert(long bin_len) {
		if (insert_to_bin != Fsm_mnt_mgr.Insert_to_bin_null) return insert_to_bin;	// insert_to_bin specified; return it
		if (fil_cur.Bin_len() > fil_cur.Bin_max())
			Itms_add(0);
		return fil_cur.Id();
	}
	public void Increment(long bin_len) {
		long new_bin_len = fil_cur.Bin_len() + bin_len;
		fil_cur.Bin_len_(new_bin_len);
	}
	public long Insert(int db_id, int bin_id, byte owner_tid, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		Fsm_bin_fil bin_fil = fil_ary[db_id];
		return bin_fil.Insert(bin_id, owner_tid, bin_len, bin_rdr);
	}
	public void Init_for_db(Db_conn conn, boolean created, boolean schema_is_1, Io_url dir) {
		this.dir = dir;
		tbl.Conn_(conn, created, schema_is_1);
		if (created)
			this.Itms_add(0);
		else {
			fil_ary = tbl.Select_all(dir);
			fil_ary_len = this.fil_ary.length;
			fil_cur = this.fil_ary[fil_ary_len - 1];
		}
	}
	private void Itms_add(long bin_len) {
		Fsm_bin_fil cur = Get_cur();
		if (cur != null) {
			cur.Conn().Txn_mgr().Txn_end_all();
			cur.Conn().Conn_term();
		}
		int new_itms_len = fil_ary_len + 1;
		Fsm_bin_fil[] new_itms = new Fsm_bin_fil[new_itms_len];
		for (int i = 0; i < fil_ary_len; i++)
			new_itms[i] = fil_ary[i];
		fil_cur = Fsm_bin_fil.make_(fil_ary_len, url_(dir, fil_ary_len), bin_len, db_bin_max);
		fil_ary = new_itms;
		fil_ary_len = new_itms_len;
		fil_ary[fil_ary_len - 1] = fil_cur;
		this.Txn_open();
	}
	private static Io_url url_(Io_url dir, int id) {
		return dir.GenSubFil_ary("fsdb.bin.", Int_.Xto_str_pad_bgn(id, 4), ".sqlite3");
	}
}
