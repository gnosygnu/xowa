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
import gplx.dbs.*; import gplx.cache.*;
public class Fsdb_db_atr_mgr implements RlsAble {
	private Fsdb_db_atr_fil[] itms; private Fsdb_db_atr_fil itms_0;
	public int Len() {return itms.length;}
	public Fsdb_db_atr_fil Get_at(int i) {return i == Id_0 ? itms_0 : itms[i];}
	public Fsdb_fil_itm Fil_select(byte[] dir, byte[] fil)							{return itms_0.Fil_select(dir, fil);}
	public boolean Thm_select(int owner_id, Fsdb_xtn_thm_itm thm)						{return itms_0.Thm_select(owner_id, thm);}
	public int Fil_insert(Fsdb_fil_itm rv    , byte[] dir, byte[] fil, int ext_id, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		return itms_0.Fil_insert(rv, String_.new_utf8_(dir), String_.new_utf8_(fil), ext_id, modified, hash, bin_db_id, bin_len, bin_rdr);
	}
	public int Img_insert(Fsdb_xtn_img_itm rv, String dir, String fil, int ext_id, int img_w, int img_h, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		return itms_0.Img_insert(rv, dir, fil, ext_id, img_w, img_h, modified, hash, bin_db_id, bin_len, bin_rdr);
	}
	public int Thm_insert(Fsdb_xtn_thm_itm rv, byte[] dir, byte[] fil, int ext_id, int width, int height, double thumbtime, int page, DateAdp modified, String hash, int bin_db_id, long bin_len, gplx.ios.Io_stream_rdr bin_rdr) {
		return itms_0.Thm_insert(rv, String_.new_utf8_(dir), String_.new_utf8_(fil), ext_id, width, height, thumbtime, page, modified, hash, bin_db_id, bin_len, bin_rdr);
	}
	public void Txn_open() {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Fsdb_db_atr_fil itm = itms[i];
			itm.Txn_open();
		}
	}
	public void Txn_save(Db_provider provider) {
		Fsdb_db_atr_tbl.Commit_all(provider, itms);
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Fsdb_db_atr_fil itm = itms[i];
			itm.Txn_save();
		}
	}
	public void Rls() {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Fsdb_db_atr_fil itm = itms[i];
			itm.Rls();
		}
	}
	public static Fsdb_db_atr_mgr load_(Fsdb_db_abc_mgr abc_mgr, Db_provider p, Io_url dir) {
		Fsdb_db_atr_mgr rv = new Fsdb_db_atr_mgr();
		rv.itms = Fsdb_db_atr_tbl.Select_all(abc_mgr, p, dir);
		rv.itms_0 = rv.itms[0];
		return rv;
	}
	public static Fsdb_db_atr_mgr make_(Fsdb_db_abc_mgr abc_mgr, Db_provider p, Io_url dir) {
		Fsdb_db_atr_tbl.Create_table(p);
		Fsdb_db_atr_mgr rv = new Fsdb_db_atr_mgr();
		Fsdb_db_atr_fil itm = Fsdb_db_atr_fil.make_(abc_mgr, Id_0, url_(dir, Id_0), Path_bgn_0);
		rv.itms_0 = itm;
		rv.itms = new Fsdb_db_atr_fil[] {itm};
		return rv;
	}
	private static Io_url url_(Io_url dir, int id) {
		return dir.GenSubFil_ary("fsdb.atr.", Int_.XtoStr_PadBgn(id, 2), ".sqlite3");
	}
	public static final int Id_0 = 0;
	public static final String Path_bgn_0 = "";
}
