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
import gplx.core.primitives.*; import gplx.core.caches.*; import gplx.core.ios.*;
import gplx.dbs.*; import gplx.dbs.engines.sqlite.*; import gplx.fsdb.data.*;
public class Fsm_atr_fil {
	private final Fsm_mnt_itm mnt_itm; private final int mnt_id;
	private Fsd_dir_tbl tbl_dir; private Fsd_fil_tbl tbl_fil; private Fsd_thm_tbl tbl_thm;
	private final Gfo_cache_mgr_bry dir_cache = new Gfo_cache_mgr_bry(); private Gfo_cache_mgr_bry fil_cache; private Bry_bfr fil_cache_key_bfr;
	public Fsm_atr_fil(Fsm_mnt_itm mnt_itm, int id, String url_rel, Db_conn conn, boolean schema_is_1, boolean schema_thm_page) {
		this.mnt_itm = mnt_itm; this.mnt_id = mnt_itm.Id();
		this.id = id; this.url_rel = url_rel; this.conn = conn;
		this.tbl_dir = new Fsd_dir_tbl(conn, schema_is_1);
		this.tbl_fil = new Fsd_fil_tbl(conn, schema_is_1, mnt_id);
		this.tbl_thm = new Fsd_thm_tbl(conn, schema_is_1, mnt_id, schema_thm_page);
	}
	public int				Id() {return id;} private final int id;
	public String			Url_rel() {return url_rel;} private final String url_rel;
	public Db_conn			Conn() {return conn;} private final Db_conn conn;
	public Fsd_fil_itm		Select_fil_or_null(byte[] dir, byte[] fil) {
		int dir_id = Get_dir_id_or_neg1(dir); 
		return dir_id == Int_.Neg1 ? Fsd_fil_itm.Null : tbl_fil.Select_or_null(dir_id, fil);
	}
	public boolean				Select_thm(boolean exact, Fsd_thm_itm rv, int dir_id, int fil_id) {
		return exact ? tbl_thm.Select_itm_by_w_exact(dir_id, fil_id, rv) : tbl_thm.Select_itm_by_w_near(dir_id, fil_id, rv);
	}
	public int				Insert_fil(Fsd_fil_itm rv, byte[] dir, byte[] fil, int ext_id, int bin_db_id, long bin_len, Io_stream_rdr bin_rdr) {
		int dir_id = Get_dir_id_or_make(dir);
		int fil_id = Get_fil_id_or_make(Tid_none, dir_id, fil, ext_id, bin_db_id, bin_len);
		rv.Ctor(mnt_id, fil_id, dir_id, bin_db_id, fil, ext_id);
		return fil_id;
	}
	public int				Insert_img(Fsd_img_itm rv, byte[] dir, byte[] fil, int ext_id, int img_w, int img_h, int bin_db_id, long bin_len, Io_stream_rdr bin_rdr) {
		int dir_id = Get_dir_id_or_make(dir);
		int fil_id = Get_fil_id_or_make(Tid_img, dir_id, fil, ext_id, bin_db_id, bin_len);
		rv.Ctor(mnt_id, dir_id, fil_id, bin_db_id);
		return fil_id;
	}
	public int				Insert_thm(Fsd_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int w, int h, double time, int page, int bin_db_id, long bin_len, Io_stream_rdr bin_rdr) {
		int dir_id = Get_dir_id_or_make(dir);
		int fil_id = Get_fil_id_or_make(Tid_thm, dir_id, fil, ext_id, Fsd_bin_tbl.Bin_db_id_null, Fsd_bin_tbl.Size_null);	// NOTE: bin_db_id must be set to NULL
		int thm_id = mnt_itm.Next_id();
		tbl_thm.Insert(thm_id, fil_id, w, h, time, page, bin_db_id, bin_len);
		rv.Ctor(mnt_id, dir_id, fil_id, thm_id, bin_db_id, w, h, time, page, bin_len, Fsd_thm_tbl.Modified_null_str, Fsd_thm_tbl.Hash_null);
		return thm_id;
	}
	public void Fil_cache_enabled_y_() {
		fil_cache = new Gfo_cache_mgr_bry();
		fil_cache_key_bfr = Bry_bfr.reset_(255);
		tbl_fil.Select_all(fil_cache_key_bfr, fil_cache);
	}
	private int Get_dir_id_or_neg1(byte[] dir_bry) {
		Object rv_obj = dir_cache.Get_or_null(dir_bry);
		if (rv_obj == null) {										// not in mem
			Fsd_dir_itm itm = tbl_dir.Select_or_null(dir_bry);		// try db
			if (itm == Fsd_dir_itm.Null) return -1;					// not in db
			int dir_id = itm.Dir_id();
			dir_cache.Add(dir_bry, Int_obj_ref.new_(dir_id));		// add to mem
			return dir_id;
		}
		else
			return ((Int_obj_ref)rv_obj).Val();
	}
	private int Get_dir_id_or_make(byte[] dir_bry) {
		int rv = Get_dir_id_or_neg1(dir_bry);
		if (rv == -1) {
			rv = mnt_itm.Next_id();
			tbl_dir.Insert(rv, dir_bry, Fsd_dir_itm.Owner_root);
			dir_cache.Add(dir_bry, Int_obj_ref.new_(rv));
		}
		return rv;
	}
	private int Get_fil_id_or_make(int xtn_tid, int dir_id, byte[] fil, int ext_id, int bin_db_id, long bin_len) {
		if (fil_cache != null) {
			byte[] cache_key = Fsd_fil_itm.Gen_cache_key(fil_cache_key_bfr, dir_id, fil);
			Object cache_obj = fil_cache.Get_or_null(cache_key);
			if (cache_obj != null) return ((Fsd_fil_itm)cache_obj).Fil_id();
		}
		Fsd_fil_itm fil_itm = tbl_fil.Select_or_null(dir_id, fil);
		int fil_id = -1;
		if (fil_itm == Fsd_fil_itm.Null) {		// new item
			fil_id = mnt_itm.Next_id();
			tbl_fil.Insert(fil_id, dir_id, fil, xtn_tid, ext_id, bin_len, bin_db_id);
		}
		else {									// existing item
			fil_id = fil_itm.Fil_id();
			if (	fil_itm.Bin_db_id() == Fsd_bin_tbl.Bin_db_id_null	// prv row was previously inserted by thumb
				&&	xtn_tid != Tid_thm									// cur row is not thumb
				) {
				tbl_fil.Update(fil_id, dir_id, fil, xtn_tid, ext_id, bin_len, bin_db_id);	// update props; note that thumb inserts null props, whereas file will insert real props (EX: bin_db_id)
			}
		}
		return fil_id;
	}
	private static final int Tid_none = 0, Tid_thm = 1, Tid_img = 2;
}
