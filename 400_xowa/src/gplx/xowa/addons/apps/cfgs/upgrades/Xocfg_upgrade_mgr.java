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
package gplx.xowa.addons.apps.cfgs.upgrades; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.dbs.*;
public class Xocfg_upgrade_mgr {
	public static void Convert(Xoae_app app) {
		// get cfg_fil; if empty, exit
		Io_url cfg_fil = app.Fsys_mgr().Root_dir().GenSubFil_nest("user", "anonymous", "app", "data", "cfg", "xowa_user_cfg.gfs");
		byte[] cfg_raw = Io_mgr.Instance.LoadFilBryOrNull(cfg_fil);
		if (cfg_raw == null) return;

		// log and rename file
		Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:old cfg found; converting");
		Io_mgr.Instance.MoveFil(cfg_fil, cfg_fil.GenNewExt(".bak"));

		// parse, remap, and update
		Keyval[] kvs = Parse(cfg_raw);

		// get mappings
		Ordered_hash mappings = Ordered_hash_.New();
		Db_conn conn = app.Cfg().Cache_mgr().Db_app().Conn();
		Db_rdr rdr = conn.Stmt_sql("SELECT * FROM cfg_upgrade").Exec_select__rls_auto();
		try {
			while (rdr.Move_next()) {
				String cfg_old = rdr.Read_str("cfg_old");
				String cfg_new = rdr.Read_str("cfg_new");
				mappings.Add(cfg_old, Keyval_.new_(cfg_old, cfg_new));
			}
		}
		finally {rdr.Rls();}

		// remap
		for (Keyval kv : kvs) {
			Keyval mapping = (Keyval)mappings.Get_by(kv.Key());
			if (mapping == null) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:could not find mapping; key=~{0} val=~{1}", kv.Key(), kv.Val());
				kv.Key_("");
				continue;
			}
			kv.Key_(mapping.Val());
		}

		// apply
		for (Keyval kv : kvs) {
			if (String_.Eq(kv.Key(), "")) continue;
			app.Cfg().Set_str_app(kv.Key(), kv.Val_to_str_or_empty());
		}
	}
	public static Keyval[] Parse(byte[] src) {
		int pos = 0; int len = src.length;
		byte[] key_bgn_bry = Bry_.new_a7("app.cfgs.get('");
		byte[] key_end_bry = Bry_.new_a7("', 'app').val = '");
		byte[] val_end_bry = Bry_.new_a7("';\n");
		byte[] apos_2_bry = Bry_.new_a7("''");

		// main parse
		Ordered_hash hash = Ordered_hash_.New();
		while (true) {
			// find "app.cfgs.get('"
			int key_bgn = Bry_find_.Find_fwd(src, key_bgn_bry, pos, len);
			if (key_bgn == Bry_find_.Not_found) break;	// no more cfgs; break;

			// update key_bgn; find key_end
			key_bgn += key_bgn_bry.length; 
			int key_end = Bry_find_.Find_fwd(src, key_end_bry, key_bgn, len);
			if (key_end == Bry_find_.Not_found) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:could not find key_end; src=~{0}", Bry_.Mid(src, key_bgn, len));
				break;
			}

			// look for val_end
			int val_bgn = key_end + key_end_bry.length;
			int val_end = Bry_find_.Find_fwd(src, val_end_bry, val_bgn, len);
			if (val_end == Bry_find_.Not_found) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:could not find val_bgn; src=~{0}", Bry_.Mid(src, val_bgn, len));
				break;
			}

			byte[] key = Bry_.Replace(Bry_.Mid(src, key_bgn, key_end), apos_2_bry, Byte_ascii.Apos_bry);
			byte[] val = Bry_.Replace(Bry_.Mid(src, val_bgn, val_end), apos_2_bry, Byte_ascii.Apos_bry);
			String key_str = String_.new_u8(key);
			hash.Add_if_dupe_use_nth(key_str, Keyval_.new_(key_str, String_.new_u8(val)));
			pos = val_end + val_end_bry.length;
		}

		// consolidate io.cmd
		List_adp deleted = List_adp_.New();
		len = hash.Len();
		for (int i = 0; i < len; i++) {
			Keyval args_kv = (Keyval)hash.Get_at(i);
			String args_kv_key = args_kv.Key();
			String args_kv_val = args_kv.Val_to_str_or_empty();
			if (String_.Has_at_end(args_kv_key, ".args")) {
				Keyval cmd_kv = (Keyval)hash.Get_by(String_.Replace(args_kv_key, ".args", ".cmd"));
				if (cmd_kv == null) {
					Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:could not find cmd; key=~{0} val=~{1}", args_kv_key, args_kv.Val());
					continue;
				}
				String cmd_kv_val = cmd_kv.Val_to_str_or_empty();
				if (!String_.Has_at_end(cmd_kv_val, "|")) cmd_kv_val += "|";
				cmd_kv.Val_(cmd_kv_val + args_kv_val);
				deleted.Add(args_kv_key);
			}
			else if (String_.Has_at_bgn(args_kv_key, "app.cfg.get.gui.bnds.init('")) {
				args_kv_key = String_.Replace(args_kv_key, "app.cfg.get.gui.bnds.init('", "");
				args_kv_key = String_.Replace(args_kv_key, "').src", "");
				args_kv_key = "xowa.gui.shortcuts." + args_kv_key;
				args_kv.Key_(args_kv_key);

				args_kv_val = String_.Replace(args_kv_val, "box='", "");
				args_kv_val = String_.Replace(args_kv_val, "';ipt='", "|");
				args_kv_val = String_.Replace(args_kv_val, "';", "");
				args_kv.Val_(args_kv_val);
			}
		}

		// delete args
		len = deleted.Len();
		for (int i = 0; i < len; i++) {
			String deleted_key = (String)deleted.Get_at(i);
			hash.Del(deleted_key);
		}

		return (Keyval[])hash.To_ary_and_clear(Keyval.class);
	}
}
