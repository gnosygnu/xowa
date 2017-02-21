/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.addons.apps.cfgs.upgrades; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.dbs.*;
import gplx.langs.gfs.*;
public class Xocfg_upgrade_mgr {
	public static void Upgrade_cfg_0(Xoae_app app) {
		try {
			// get cfg_fil; if empty, exit
			Io_url cfg_fil = app.Fsys_mgr().Root_dir().GenSubFil_nest("user", "anonymous", "app", "data", "cfg", "xowa_user_cfg.gfs");
			byte[] cfg_raw = Io_mgr.Instance.LoadFilBryOrNull(cfg_fil);
			if (cfg_raw == null) return;

			// log and rename file
			Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:old cfg found; converting");
			Io_mgr.Instance.MoveFil_args(cfg_fil, cfg_fil.GenNewExt(".bak"), true).Exec();

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
			app.Cfg().Cache_mgr().Db_usr().Conn().Txn_bgn("convert");
			for (Keyval kv : kvs) {
				if (String_.Eq(kv.Key(), "")) continue;
				Gfo_usr_dlg_.Instance.Log_many("", "", "cfg.convert:converting; key=~{0} val=~{1}", kv.Key(), kv.Val());
				app.Cfg().Set_str_app(kv.Key(), kv.Val_to_str_or_empty());
			}
			app.Cfg().Cache_mgr().Db_usr().Conn().Txn_end();
		} catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to convert old cfg; err=~{0}", Err_.Message_gplx_log(exc));
		}
	}
	public static Keyval[] Parse(byte[] src) {
		// main parse
		Gfs_parser parser = new Gfs_parser();
		Ordered_hash hash = Ordered_hash_.New();
		Gfs_nde root = parser.Parse(src);
		int root_len = root.Subs_len();
		for (int i = 0; i < root_len; i++) {
			Gfs_nde app_nde = root.Subs_get_at(i);
			Gfs_nde cfgs_nde = app_nde.Subs_get_at(0);
			Gfs_nde get_nde = cfgs_nde.Subs_get_at(0);
			Gfs_nde key_atr = get_nde.Atrs_get_at(0);
			Gfs_nde val_nde = get_nde.Subs_get_at(0);
			Gfs_nde val_atr = val_nde.Atrs_get_at(0);
			String key = String_.new_u8(key_atr.Name_bry(src));
			hash.Add(key, Keyval_.new_(key, String_.new_u8(val_atr.Name_bry(src))));
		}

		// consolidate io.cmd
		List_adp deleted = List_adp_.New();
		int len = hash.Len();
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
