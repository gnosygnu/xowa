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
import gplx.core.primitives.*; import gplx.cache.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*;
import gplx.fsdb.data.*;
public class Fsm_atr_fil implements RlsAble {
	private Gfo_cache_mgr_bry dir_cache = new Gfo_cache_mgr_bry();		
	private final Fsd_dir_tbl tbl_dir = new Fsd_dir_tbl(); private final Fsd_fil_tbl tbl_fil = new Fsd_fil_tbl(); private Fsd_thm_tbl tbl_thm = new Fsd_thm_tbl();
	private static final String Db_conn_bldr_type = "gplx.fsdb.fsm_atr_fil";
	public Fsm_atr_fil(Fsm_abc_mgr abc_mgr, Io_url io_url) {
		this.abc_mgr = abc_mgr;
		Db_conn_bldr_data conn_data = Db_conn_bldr.I.Get_or_new(Db_conn_bldr_type, io_url);
		boolean created = conn_data.Created(); conn = conn_data.Conn();
		boolean version_is_1 = Bool_.Y; 
		tbl_dir.Conn_(conn, created, version_is_1);
		tbl_fil.Conn_(conn, created, version_is_1);
		tbl_thm.Conn_(conn, created, version_is_1, this);
	}
	public Fsm_abc_mgr Abc_mgr() {return abc_mgr;} private Fsm_abc_mgr abc_mgr;
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public int Id() {return id;} private int id;
	public Io_url Url() {return url;} private Io_url url;
	public String Path_bgn() {return path_bgn;} private String path_bgn;
	public byte Cmd_mode() {return cmd_mode;} public Fsm_atr_fil Cmd_mode_(byte v) {cmd_mode = v; return this;} private byte cmd_mode;
	public void Ctor_by_load(int id, Io_url url, String path_bgn, byte cmd_mode) {
		this.id = id;
		this.url = url;
		this.path_bgn = path_bgn;
		this.cmd_mode = cmd_mode;
	}
	public void Rls() {
		conn.Txn_mgr().Txn_end_all();
		conn.Conn_term();
	}
	public void Txn_open() {
		conn.Txn_mgr().Txn_bgn_if_none();
	}
	public void Txn_save() {
		conn.Txn_mgr().Txn_end_all();
	}
	public Fsd_fil_itm Fil_select(byte[] dir, byte[] fil) {
		Int_obj_ref dir_id_obj = (Int_obj_ref)dir_cache.Get_or_null(dir);
		int dir_id = -1;
		if (dir_id_obj == null) {
			Fsd_dir_itm dir_itm = tbl_dir.Select_itm(String_.new_utf8_(dir));
			dir_id = dir_itm == Fsd_dir_itm.Null ? -1 : dir_itm.Id();
			dir_cache.Add(dir, Int_obj_ref.new_(dir_id));
		}
		else
			dir_id = dir_id_obj.Val();
		if (dir_id == Int_.Neg1) return Fsd_fil_itm.Null;
		return tbl_fil.Select_itm_by_name(dir_id, String_.new_utf8_(fil));
	}
	public boolean Thm_select(int owner_id, Fsd_thm_itm thm) {
		return tbl_thm.Select_itm_by_fil_width(owner_id, thm);
	}
	public int Fil_insert(Fsd_fil_itm rv, String dir, String fil, int ext_id, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int dir_id = Dir_id__get_or_insert(dir);
		int fil_id = Fil_id__get_or_insert(Xtn_tid_none, dir_id, fil, ext_id, modified, hash, bin_db_id, bin_len);
		rv.Id_(fil_id).Owner_(dir_id);
		return fil_id;
	}
	public int Img_insert(Fsd_img_itm rv, String dir, String fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int dir_id = Dir_id__get_or_insert(dir);
		int fil_id = Fil_id__get_or_insert(Xtn_tid_img, dir_id, fil, ext_id, modified, hash, bin_db_id, bin_len);
		rv.Id_(fil_id);
		return fil_id;
	}
	public int Thm_insert(Fsd_thm_itm rv, String dir, String fil, int ext_id, int thm_w, int thm_h, double thumbtime, int page, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int dir_id = Dir_id__get_or_insert(dir);
		int fil_id = Fil_id__get_or_insert(Xtn_tid_thm, dir_id, fil, ext_id, modified, hash, Fsd_bin_tbl.Null_db_bin_id, Fsd_bin_tbl.Null_size);	// NOTE: bin_db_id must be set to NULL
		int thm_id = abc_mgr.Next_id();
		tbl_thm.Insert(thm_id, fil_id, thm_w, thm_h, thumbtime, page, bin_db_id, bin_len, modified, hash);
		rv.Id_(thm_id).Owner_id_(fil_id).Dir_id_(dir_id);
		return thm_id;
	}
	public static Fsm_atr_fil make_(Fsm_abc_mgr abc_mgr, int id, Io_url url, String path_bgn) {
		Fsm_atr_fil rv = new Fsm_atr_fil(abc_mgr, url);
		rv.id = id;
		rv.url = url;
		rv.path_bgn = path_bgn;
		rv.cmd_mode = Db_cmd_mode.Tid_create;
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
			Fsd_dir_itm itm = tbl_dir.Select_itm(dir_str);
			if (itm == Fsd_dir_itm.Null) {
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
		Fsd_fil_itm fil_itm = tbl_fil.Select_itm_by_name(dir_id, fil);
		int fil_id = fil_itm.Id();
		if (fil_id == Fsd_fil_itm.Null_id) {	// new item
			fil_id = abc_mgr.Next_id();				
			tbl_fil.Insert(fil_id, dir_id, fil, xtn_tid, ext_id, bin_len, modified, hash, bin_db_id);
		}
		else {									// existing item				
			if (	fil_itm.Db_bin_id() == Fsd_bin_tbl.Null_db_bin_id	// prv row was previously inserted by thumb
				&&	xtn_tid != Xtn_tid_thm					// cur row is not thumb
				) {
				tbl_fil.Update(fil_id, dir_id, fil, xtn_tid, ext_id, bin_len, modified, hash, bin_db_id);	// update props; note that thumb inserts null props, whereas file will insert real props (EX: bin_db_id)
			}
		}
		return fil_id;
	}
	private static final int Xtn_tid_none = 0, Xtn_tid_thm = 1, Xtn_tid_img = 2;
}
