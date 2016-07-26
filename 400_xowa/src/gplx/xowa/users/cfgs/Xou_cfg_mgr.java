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
package gplx.xowa.users.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*;
public class Xou_cfg_mgr {
	private Xou_cfg_tbl tbl;
	private final    Hash_adp hash = Hash_adp_.New();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public void Init_by_app(Db_conn conn) {
		tbl = new Xou_cfg_tbl(conn);
		tbl.Conn().Meta_tbl_assert(tbl);
		this.Reg(tbl.Select_by_usr_ctx(Usr__anonymous, Ctx__app));
	}
	public String Get_app_str_or(String key, String or) {	// NOTE: app-level is always loaded at start; don't check db
		synchronized (hash) {	// LOCK:app-level
			String uid = Bld_uid(tmp_bfr, Usr__anonymous, Ctx__app, key);
			Xou_cfg_itm itm = (Xou_cfg_itm)hash.Get_by(uid);
			return itm == null ? or : itm.Val();
		}
	}
	public void Set_app_bry(String key, byte[] val) {this.Set_app_str(key, String_.new_u8(val));}
	public void Set_app_str(String key, String val) {
		synchronized (hash) {	// LOCK:app-level
			// update val in reg
			String uid = Bld_uid(tmp_bfr, Usr__anonymous, Ctx__app, key);
			boolean insert = false;
			Xou_cfg_itm itm = (Xou_cfg_itm)hash.Get_by(uid);
			if (itm == null) {
				itm = new Xou_cfg_itm(Usr__anonymous, Ctx__app, key, val);
				hash.Add(uid, itm);
				insert = true;
			}
			itm.Val_(val);

			// save to db
			tbl.Upsert(insert, itm.Usr(), itm.Ctx(), itm.Key(), itm.Val());
		}
	}

	private void Reg(Xou_cfg_itm[] itms) {
		synchronized (hash) {	// LOCK:app-level
			for (Xou_cfg_itm itm : itms)
				hash.Add(itm.Uid(), itm);
		}
	}

	private static final int Usr__anonymous = 1;
	private static final String Ctx__app = "app";
	public static String Bld_uid(int usr, String ctx, String key) {
		return String_.Concat(Int_.To_str(usr), "|", ctx, "|", key);
	}
	private static String Bld_uid(Bry_bfr tmp_bfr, int usr, String ctx, String key) {
		tmp_bfr.Add_int_variable(usr).Add_byte_pipe();
		tmp_bfr.Add_str_a7(ctx).Add_byte_pipe();
		tmp_bfr.Add_str_u8(key);
		return tmp_bfr.To_str_and_clear();
	}
}
