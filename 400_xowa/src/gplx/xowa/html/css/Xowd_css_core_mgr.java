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
package gplx.xowa.html.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
public class Xowd_css_core_mgr {
	public static void Set(Xowd_css_core_tbl core_tbl, Xowd_css_file_tbl file_tbl, Io_url css_dir) {Set(core_tbl, file_tbl, css_dir, Key_default);}
	public static void Set(Xowd_css_core_tbl core_tbl, Xowd_css_file_tbl file_tbl, Io_url css_dir, String key) {
		Db_conn conn = core_tbl.Conn();
		Io_url[] file_list = Io_mgr._.QueryDir_args(css_dir).Recur_().ExecAsUrlAry();
		try {
			conn.Txn_bgn();
			int css_id = core_tbl.Select_id_by_key(key);
			DateAdp updated_on = DateAdp_.Now().XtoUtc();
			if (css_id == -1)
				css_id = core_tbl.Insert(key, updated_on);
			else {
				core_tbl.Update(css_id, key, updated_on);
				file_tbl.Delete(css_id);
			}
			for (Io_url file : file_list) {
				String path = Op_sys.Fsys_path_to_lnx(file.GenRelUrl_orEmpty(css_dir));
				byte[] data = Io_mgr._.LoadFilBry(file);
				file_tbl.Insert(css_id, path, data);
			}
			conn.Txn_end();
		}
		catch (Exception e) {conn.Txn_cxl(); throw Err_.err_(e);}
	}
	public static void Get(Xowd_css_core_tbl core_tbl, Xowd_css_file_tbl file_tbl, Io_url css_dir) {Get(core_tbl, file_tbl, css_dir, Key_default);}
	public static void Get(Xowd_css_core_tbl core_tbl, Xowd_css_file_tbl file_tbl, Io_url css_dir, String key) {
		int css_id = core_tbl.Select_id_by_key(key); if (css_id == Xowd_css_core_tbl.Id_null) throw Err_.new_("skin:unknown key: {0}", key);
		Xowd_css_file_itm[] file_list = file_tbl.Select_by_owner(css_id);
		// Io_mgr._.DeleteDirDeep(css_dir); // NOTE: do not delete existing files; just overwrite;
		int len = file_list.length;
		for (int i = 0; i < len; ++i) {
			Xowd_css_file_itm file = file_list[i];
			Io_url file_url = Io_url_.new_fil_(css_dir.Gen_sub_path_for_os(file.Path()));
			Io_mgr._.SaveFilBry(file_url, file.Data());
		}
	}
	public static final String Key_default = "xowa.default";
}
