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
import gplx.core.primitives.*; import gplx.cache.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
public class Fsdb_db_atr_fil implements RlsAble {
	private Gfo_cache_mgr_bry dir_cache = new Gfo_cache_mgr_bry();		
	private Fsdb_dir_tbl tbl_dir; private Fsdb_fil_tbl tbl_fil; private Fsdb_xtn_thm_tbl tbl_thm;
	public Fsdb_db_atr_fil(Fsdb_db_abc_mgr abc_mgr, Io_url url, boolean create) {
		this.abc_mgr = abc_mgr;
		Db_url connect = create ? Db_url__sqlite.make_(url) : Db_url__sqlite.load_(url);
		conn = Db_conn_.new_and_open_(connect);
		Sqlite_engine_.Pragma_page_size_4096(conn);
		tbl_dir = new Fsdb_dir_tbl(conn, create);
		tbl_fil = new Fsdb_fil_tbl(conn, create);
		tbl_thm = new Fsdb_xtn_thm_tbl(this, conn, create);
	}
	public Fsdb_db_abc_mgr Abc_mgr() {return abc_mgr;} private Fsdb_db_abc_mgr abc_mgr;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public int Id() {return id;} private int id;
	public Io_url Url() {return url;} private Io_url url;
	public String Path_bgn() {return path_bgn;} private String path_bgn;
	public byte Cmd_mode() {return cmd_mode;} public Fsdb_db_atr_fil Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public void Rls() {
		tbl_dir.Rls();
		tbl_fil.Rls();
		tbl_thm.Rls();
		conn.Txn_mgr().Txn_end_all();
		conn.Conn_term();
	}
	public void Txn_open() {
		conn.Txn_mgr().Txn_bgn_if_none();
	}
	public void Txn_save() {
		conn.Txn_mgr().Txn_end_all();
	}
	public Fsdb_fil_itm Fil_select(byte[] dir, byte[] fil) {
		Int_obj_ref dir_id_obj = (Int_obj_ref)dir_cache.Get_or_null(dir);
		int dir_id = -1;
		if (dir_id_obj == null) {
			Fsdb_dir_itm dir_itm = tbl_dir.Select_itm(String_.new_utf8_(dir));
			dir_id = dir_itm == Fsdb_dir_itm.Null ? -1 : dir_itm.Id();
			dir_cache.Add(dir, Int_obj_ref.new_(dir_id));
		}
		else
			dir_id = dir_id_obj.Val();
		if (dir_id == Int_.Neg1) return Fsdb_fil_itm.Null;
		return tbl_fil.Select_itm_by_name(dir_id, String_.new_utf8_(fil));
	}
	public boolean Thm_select(int owner_id, Fsdb_xtn_thm_itm thm) {
		return tbl_thm.Select_itm_by_fil_width(owner_id, thm);
	}
	public int Fil_insert(Fsdb_fil_itm rv, String dir, String fil, int ext_id, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int dir_id = Dir_id__get_or_insert(dir);
		int fil_id = Fil_id__get_or_insert(Fsdb_xtn_tid_.Tid_none, dir_id, fil, ext_id, modified, hash, bin_db_id, bin_len);
		rv.Id_(fil_id).Owner_(dir_id);
		return fil_id;
	}
	public int Img_insert(Fsdb_xtn_img_itm rv, String dir, String fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int dir_id = Dir_id__get_or_insert(dir);
		int fil_id = Fil_id__get_or_insert(Fsdb_xtn_tid_.Tid_img, dir_id, fil, ext_id, modified, hash, bin_db_id, bin_len);
		rv.Id_(fil_id);
		return fil_id;
	}
	public int Thm_insert(Fsdb_xtn_thm_itm rv, String dir, String fil, int ext_id, int thm_w, int thm_h, double thumbtime, int page, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int dir_id = Dir_id__get_or_insert(dir);
		int fil_id = Fil_id__get_or_insert(Fsdb_xtn_tid_.Tid_thm, dir_id, fil, ext_id, modified, hash, Fsdb_bin_tbl.Null_db_bin_id, Fsdb_bin_tbl.Null_size);	// NOTE: bin_db_id must be set to NULL
		int thm_id = abc_mgr.Next_id();
		tbl_thm.Insert(thm_id, fil_id, thm_w, thm_h, thumbtime, page, bin_db_id, bin_len, modified, hash);
		rv.Id_(thm_id).Owner_id_(fil_id).Dir_id_(dir_id);
		return thm_id;
	}
	public static Fsdb_db_atr_fil load_(Fsdb_db_abc_mgr abc_mgr, DataRdr rdr, Io_url dir) {
		Io_url url = dir.GenSubFil(rdr.ReadStr(Fsdb_db_atr_tbl.Fld_url));
		Fsdb_db_atr_fil rv = new Fsdb_db_atr_fil(abc_mgr, url, false);
		rv.id = rdr.ReadInt(Fsdb_db_atr_tbl.Fld_uid);
		rv.url = url;
		rv.path_bgn = rdr.ReadStr(Fsdb_db_atr_tbl.Fld_path_bgn);
		rv.cmd_mode = Db_cmd_mode.Ignore;
		return rv;
	}
	public static Fsdb_db_atr_fil make_(Fsdb_db_abc_mgr abc_mgr, int id, Io_url url, String path_bgn) {
		Fsdb_db_atr_fil rv = new Fsdb_db_atr_fil(abc_mgr, url, true);
		rv.id = id;
		rv.url = url;
		rv.path_bgn = path_bgn;
		rv.cmd_mode = Db_cmd_mode.Create;
		return rv;
	}
	private int Dir_id__get_or_insert(String dir_str) {
		byte[] dir_bry = Bry_.new_utf8_(dir_str);
		Object rv_obj = dir_cache.Get_or_null(dir_bry);
		int rv = -1;
		if (rv_obj != null) {	// item found
			rv = ((Int_obj_ref)rv_obj).Val();
			if (rv == -1)		// dir was previously -1; occurs when doing select on empty db (no dir, so -1 added) and then doing insert (-1 now needs to be dropped)
				dir_cache.Del(dir_bry);
		}
		if (rv == -1) {
			Fsdb_dir_itm itm = tbl_dir.Select_itm(dir_str);
			if (itm == Fsdb_dir_itm.Null) {
				rv = abc_mgr.Next_id();
				tbl_dir.Insert(rv, dir_str, 0);	// 0: always assume root owner
			}
			else {
				rv = itm.Id();
			}
			dir_cache.Add(dir_bry, Int_obj_ref.new_(rv));
		}
		return rv;
	}
	private int Fil_id__get_or_insert(int xtn_tid, int dir_id, String fil, int ext_id, DateAdp modified, String hash, int bin_db_id, long bin_len) {
		Fsdb_fil_itm fil_itm = tbl_fil.Select_itm_by_name(dir_id, fil);
		int fil_id = fil_itm.Id();
		if (fil_id == Fsdb_fil_itm.Null_id) {	// new item
			fil_id = abc_mgr.Next_id();				
			tbl_fil.Insert(fil_id, dir_id, fil, xtn_tid, ext_id, bin_len, modified, hash, bin_db_id);
		}
		else {									// existing item				
			if (	fil_itm.Db_bin_id() == Fsdb_bin_tbl.Null_db_bin_id	// prv row was previously inserted by thumb
				&&	xtn_tid != Fsdb_xtn_tid_.Tid_thm					// cur row is not thumb
				) {
				tbl_fil.Update(fil_id, dir_id, fil, xtn_tid, ext_id, bin_len, modified, hash, bin_db_id);	// update props; note that thumb inserts null props, whereas file will insert real props (EX: bin_db_id)
			}
		}
		return fil_id;
	}
}
