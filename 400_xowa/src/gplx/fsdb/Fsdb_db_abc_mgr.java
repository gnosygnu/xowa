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
public class Fsdb_db_abc_mgr implements RlsAble {
	private Db_provider boot_provider;
	public int Next_id() {return cfg_mgr.Next_id();}
	public Fsdb_mnt_mgr Mnt_mgr() {return mnt_mgr;} private Fsdb_mnt_mgr mnt_mgr;
	public Fsdb_db_abc_mgr(Fsdb_mnt_mgr mnt_mgr) {this.mnt_mgr = mnt_mgr;}
	public Fsdb_db_atr_mgr Atr_mgr() {return atr_mgr;} private Fsdb_db_atr_mgr atr_mgr;
	public Fsdb_db_bin_mgr Bin_mgr() {return bin_mgr;} private Fsdb_db_bin_mgr bin_mgr;
	public Fsdb_cfg_mgr Cfg_mgr() {return cfg_mgr;} private Fsdb_cfg_mgr cfg_mgr;
	public Fsdb_db_abc_mgr Init(Io_url dir) {
		Io_url url = dir.GenSubFil("fsdb.abc.sqlite3");
		if (Io_mgr._.ExistsFil(url))
			Init_load(dir, url);
		else
			Init_make(dir, url);
		return this;
	}
	public void Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int bin_db_id = bin_mgr.Get_id_for_insert(bin_len);
		rv.Db_bin_id_(bin_db_id);
		int fil_id = atr_mgr.Fil_insert(rv, dir, fil, ext_id, modified, hash, bin_db_id, bin_len, bin_rdr);
		bin_len = bin_mgr.Insert(bin_db_id, fil_id, Fsdb_bin_tbl.Owner_tid_fil, bin_len, bin_rdr);
		bin_mgr.Increment(bin_len);
	}
	public void Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int w, int h, double thumbtime, int page, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		int bin_db_id = bin_mgr.Get_id_for_insert(bin_len);
		rv.Db_bin_id_(bin_db_id);
		int thm_id = atr_mgr.Thm_insert(rv, dir, fil, ext_id, w, h, thumbtime, page, modified, hash, bin_db_id, bin_len, bin_rdr);
		bin_len = bin_mgr.Insert(bin_db_id, thm_id, Fsdb_bin_tbl.Owner_tid_thm, bin_len, bin_rdr);
		bin_mgr.Increment(bin_len);
	}
	public void Img_insert(Fsdb_xtn_img_itm rv, byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, long bin_len, gplx.ios.Io_stream_rdr bin_rdr, int img_w, int img_h) {
		int bin_db_id = bin_mgr.Get_id_for_insert(bin_len);
		rv.Db_bin_id_(bin_db_id);
		int fil_id = atr_mgr.Img_insert(rv, String_.new_utf8_(dir), String_.new_utf8_(fil), ext_id, img_w, img_h, modified, hash, bin_db_id, bin_len, bin_rdr);
		bin_len = bin_mgr.Insert(bin_db_id, fil_id, Fsdb_bin_tbl.Owner_tid_fil, bin_len, bin_rdr);
		bin_mgr.Increment(bin_len);
	}
	public boolean Thm_select_bin(byte[] dir, byte[] fil, Fsdb_xtn_thm_itm thm) {
		Fsdb_fil_itm fil_itm = atr_mgr.Fil_select(dir, fil);
		return atr_mgr.Thm_select(fil_itm.Id(), thm);
	}
	public Fsdb_fil_itm Fil_select_bin(byte[] dir, byte[] fil, boolean is_thumb, int width, double thumbtime) {
		return atr_mgr.Fil_select(dir, fil);
	}
	public void Txn_open() {
		boot_provider.Txn_mgr().Txn_bgn_if_none();
		atr_mgr.Txn_open();
		bin_mgr.Txn_open();
	}
	public void Txn_save() {
		atr_mgr.Txn_save(boot_provider);
		bin_mgr.Txn_save();
		cfg_mgr.Txn_save();
	}
	public void Rls() {
		atr_mgr.Rls();
		bin_mgr.Rls();
		cfg_mgr.Rls();
		boot_provider.Rls();
	}
	private void Init_load(Io_url dir, Io_url boot_url) {
		Db_connect connect = Db_connect_sqlite.load_(boot_url);
		boot_provider = Db_provider_.new_(connect);
		atr_mgr = Fsdb_db_atr_mgr.load_(this, boot_provider, dir);
		bin_mgr = Fsdb_db_bin_mgr.load_(boot_provider, dir);
		cfg_mgr = Fsdb_cfg_mgr.load_(this, boot_provider);
		if (!cfg_mgr.Patch_next_id()) Fsdb_db_abc_mgr_.Patch_next_id(this, dir);
	}
	private void Init_make(Io_url dir, Io_url boot_url) {
		Db_connect connect = Db_connect_sqlite.make_(boot_url);
		boot_provider = Db_provider_.new_(connect);
		Sqlite_engine_.Pragma_page_size_4096(boot_provider);
		atr_mgr = Fsdb_db_atr_mgr.make_(this, boot_provider, dir);
		bin_mgr = Fsdb_db_bin_mgr.make_(boot_provider, dir);
		cfg_mgr = Fsdb_cfg_mgr.make_(this, boot_provider);
		this.Txn_save();	// immediately save new entries in atr,cfg
	}
}
class Fsdb_db_abc_mgr_ {
	public static void Patch_next_id(Fsdb_db_abc_mgr abc_mgr, Io_url dir) {
		if (!String_.Eq(dir.NameOnly(), "fsdb.user")) return;
		Fsdb_db_atr_mgr atr_mgr = abc_mgr.Atr_mgr();
		Fsdb_cfg_mgr cfg_mgr = abc_mgr.Cfg_mgr();
		int last_id = -1;
		if (atr_mgr.Len() > 0) {
			Fsdb_db_atr_fil atr_fil = atr_mgr.Get_at(0);
			int max_fil_id = Db_provider_.Select_fld0_as_int_or(atr_fil.Provider(), "SELECT Max(fil_id) AS MaxId FROM fsdb_fil;", -1);
			int max_thm_id = Db_provider_.Select_fld0_as_int_or(atr_fil.Provider(), "SELECT Max(thm_id) AS MaxId FROM fsdb_xtn_thm;", -1);
			last_id = max_fil_id > max_thm_id ? max_fil_id : max_thm_id;
		}
		cfg_mgr.Patch_next_id_exec(last_id);
	}
}
