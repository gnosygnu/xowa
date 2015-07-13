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
package gplx.xowa.specials.xowa.diags; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.xowa.*;
import gplx.ios.*;
import gplx.fsdb.meta.*;
class Xows_cmd__fs_check {
	public void Exec(Bry_bfr bfr, Xoa_app app, Xoa_url url, Xoa_url_arg_hash arg_hash) {
		byte[] dir_bry  = arg_hash.Get_val_bry_or(Arg_dir, null);
		if (dir_bry != null) {
			Write_dir(bfr, Io_url_.new_dir_(String_.new_u8(dir_bry)));
			return;
		}
		byte[] wiki_bry = arg_hash.Get_val_bry_or(Arg_wiki, null);	if (wiki_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no wiki: url=~{0}", url.Raw()); return;}
		Xow_wiki wiki = app.Wiki_mgri().Get_by_key_or_make_init_y(wiki_bry);
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		Io_url file_dir = wiki.Fsys_mgr().File_dir();
		Write_dir(bfr, wiki_dir);
		Write_dir(bfr, file_dir);
		Write_dir(bfr, file_dir.GenSubDir(Fsm_mnt_tbl.Mnt_name_main));
		Write_dir(bfr, file_dir.GenSubDir(Fsm_mnt_tbl.Mnt_name_user));
		Write_dir(bfr, file_dir.GenSubDir("fsdb.update_00"));
	}
	private void Write_dir(Bry_bfr bfr, Io_url dir_url) {
		bfr.Add_byte_nl().Add_str_a7("scanning: ").Add_str_u8(dir_url.Raw()).Add_byte_nl();
		if (!Io_mgr.I.ExistsDir(dir_url)) return;
		IoItmDir dir_itm = Io_mgr.I.QueryDir_args(dir_url).Recur_(false).DirInclude_(true).ExecAsDir();
		IoItmList sub_itms = dir_itm.SubDirs(); int len = sub_itms.Count();
		for (int i = 0; i < len; ++i) {
			try {
				IoItmDir sub_itm = (IoItmDir)sub_itms.Get_at(i);
				bfr.Add_str_a7("dir").Add_byte_pipe().Add_str_u8(sub_itm.Name()).Add_byte_nl();
			}	catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx(e));}
		}
		sub_itms = dir_itm.SubFils(); len = sub_itms.Count();
		for (int i = 0; i < len; ++i) {
			try {
				IoItmFil sub_itm = (IoItmFil)sub_itms.Get_at(i);
				bfr.Add_str_a7("fil").Add_byte_pipe().Add_str_u8(sub_itm.Name()).Add_byte_pipe().Add_long_variable(sub_itm.Size()).Add_byte_pipe().Add_dte(sub_itm.ModifiedTime()).Add_byte_nl();
			}	catch (Exception e) {bfr.Add_str_u8(Err_.Message_gplx(e));}
		}
	}
        public static final Xows_cmd__fs_check I = new Xows_cmd__fs_check(); Xows_cmd__fs_check() {}
	private static final byte[] Arg_wiki = Bry_.new_a7("wiki"), Arg_dir = Bry_.new_a7("dir");
}
