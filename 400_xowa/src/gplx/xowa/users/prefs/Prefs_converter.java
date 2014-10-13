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
package gplx.xowa.users.prefs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.xowa.cfgs.*;
public class Prefs_converter {
	private Bry_bfr bfr = Bry_bfr.new_();
	private ListAdp list = ListAdp_.new_();
	public void Check(Xoa_app app) {
		int options_version = app.Sys_cfg().Options_version();
		if (options_version == 1) {
			Io_url cfg_dir = app.User().Fsys_mgr().App_data_cfg_dir();
			Io_url cfg_fil = cfg_dir.GenSubFil("user_system_cfg.gfs");
			Io_url trg_fil = cfg_fil.GenNewNameAndExt(gplx.xowa.cfgs.Xoa_cfg_db_txt.File_name);
			if (!Io_mgr._.ExistsFil(trg_fil)) {	// do not overwrite file if it is there (i.e.: it's already converted); needed when running in app_mode = cmd (see HACK in Xob_bldr.Run)
				String old_str = Io_mgr._.LoadFilStr_args(cfg_fil).MissingIgnored_(true).Exec();
				String new_str = Convert(old_str);
				Io_mgr._.SaveFilStr(trg_fil, new_str);
				app.Cfg_mgr().Db_load_txt();
				app.Cfg_mgr().Set_by_app("app.setup.dumps.wiki_storage_type", "sqlite");
				app.Log_wtr().Log_msg_to_session_fmt("converted options to v2");
			}
		}
	}
	public String Convert(String raw) {
		list.Clear();
		GfoMsg root_msg = gplx.gfs.Gfs_msg_bldr._.ParseToMsg(raw);
		int len = root_msg.Subs_count();		
		for (int i = 0; i < len; i++) {
			GfoMsg msg = root_msg.Subs_getAt(i);
			Convert_msg(msg, 0);
		}
		return Convert_to_stage2(list);
	}
	public String Convert_to_stage2(ListAdp list) {
		bfr.Add_str("app.cfgs.get('app.sys_cfg.options_version', 'app').val = '2';").Add_byte_nl();
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Prefs_converter_itm itm = (Prefs_converter_itm)list.FetchAt(i);
			bfr.Add_str("app.cfgs.get('");
			Write_escaped_str(bfr, itm.Key());
			bfr.Add_str("', '" + Xoa_cfg_grp_tid.Key_app_str + "'");
			bfr.Add_str(").val = '");
			Write_escaped_str(bfr, itm.Val());
			bfr.Add_str("';\n");
		}
		return bfr.Xto_str_and_clear();
	} 
	private void Write_escaped_str(Bry_bfr bfr, String str) {
		byte[] bry = Bry_.new_utf8_(str);
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			if	(b == Byte_ascii.Apos)
				bfr.Add_byte_repeat(Byte_ascii.Apos, 2);
			else
				bfr.Add_byte(b);
		}
	}
	private void Convert_msg(GfoMsg m, int depth) {
		int subs_len = m.Subs_count();
		if (subs_len == 0) {	
			bfr.Add_byte(Byte_ascii.Dot);
			byte[] prop_set_key = Bry_.new_utf8_(m.Key());
			int prop_set_key_len = prop_set_key.length;
			if (prop_set_key_len == 0) return; // empty key; return now, else error			
			if (prop_set_key[prop_set_key_len - 1] != Byte_ascii.Underline) return; // doesn't end with _
			bfr.Add_mid(prop_set_key, 0, prop_set_key_len - 1);
			Prefs_converter_itm itm = new Prefs_converter_itm();
			itm.Key_(bfr.Xto_str_and_clear());
			String prop_val = m.Args_getAt(0).Val_to_str_or_empty();
			itm.Val_(prop_val);
			list.Add(itm);
		}
		else {
			if (depth != 0) bfr.Add_byte(Byte_ascii.Dot);
			if (String_.Eq(m.Key(), "scripts")) {bfr.Clear(); return;}
			bfr.Add_str(m.Key());
			int args_count = m.Args_count();
			if (args_count > 0) {
				bfr.Add_byte(Byte_ascii.Paren_bgn);
				for (int i = 0; i < args_count; i++) {
					if (i != 0) bfr.Add_byte(Byte_ascii.Comma);
					KeyVal kv = m.Args_getAt(i);
					bfr.Add_byte(Byte_ascii.Quote);
					bfr.Add_str(kv.Val_to_str_or_empty());
					bfr.Add_byte(Byte_ascii.Quote);
				}
				bfr.Add_byte(Byte_ascii.Paren_end);
			}
			Convert_msg(m.Subs_getAt(0), depth + 1);
		}
	}
	public static final Prefs_converter _ = new Prefs_converter(); Prefs_converter() {}
}
class Prefs_converter_itm {
	public String Key() {return key;} public void Key_(String v) {this.key = v;} private String key; 
	public String Val() {return val;} public void Val_(String v) {this.val = v;} private String val; 
}
