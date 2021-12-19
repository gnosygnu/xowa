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
package gplx.xowa.specials.xowa.diags;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.xowa.*;
import gplx.core.ios.*;
import gplx.core.net.qargs.*;
import gplx.fsdb.meta.*;
class Xows_cmd__fs_check {
	public void Exec(BryWtr bfr, Xoa_app app, Xoa_url url, Gfo_qarg_mgr_old arg_hash) {
		byte[] dir_bry  = arg_hash.Get_val_bry_or(Arg_dir, null);
		if (dir_bry != null) {
			Write_dir(bfr, Io_url_.new_dir_(StringUtl.NewU8(dir_bry)));
			return;
		}
		byte[] wiki_bry = arg_hash.Get_val_bry_or(Arg_wiki, null);	if (wiki_bry == null) {Xoa_app_.Usr_dlg().Warn_many("", "", "special.cmd; no wiki: url=~{0}", url.Raw()); return;}
		Xow_wiki wiki = app.Wiki_mgri().Get_by_or_make_init_y(wiki_bry);
		Io_url wiki_dir = wiki.Fsys_mgr().Root_dir();
		Io_url file_dir = wiki.Fsys_mgr().File_dir();
		Write_dir(bfr, wiki_dir);
		Write_dir(bfr, file_dir);
		Write_dir(bfr, file_dir.GenSubDir(Fsm_mnt_tbl.Mnt_name_main));
		Write_dir(bfr, file_dir.GenSubDir(Fsm_mnt_tbl.Mnt_name_user));
		Write_dir(bfr, file_dir.GenSubDir("fsdb.update_00"));
	}
	private void Write_dir(BryWtr bfr, Io_url dir_url) {
		bfr.AddByteNl().AddStrA7("scanning: ").AddStrU8(dir_url.Raw()).AddByteNl();
		if (!Io_mgr.Instance.ExistsDir(dir_url)) return;
		IoItmDir dir_itm = Io_mgr.Instance.QueryDir_args(dir_url).Recur_(false).DirInclude_(true).ExecAsDir();
		IoItmList sub_itms = dir_itm.SubDirs(); int len = sub_itms.Len();
		for (int i = 0; i < len; ++i) {
			try {
				IoItmDir sub_itm = (IoItmDir)sub_itms.GetAt(i);
				bfr.AddStrA7("dir").AddBytePipe().AddStrU8(sub_itm.Name()).AddByteNl();
			}	catch (Exception e) {bfr.AddStrU8(ErrUtl.ToStrFull(e));}
		}
		sub_itms = dir_itm.SubFils(); len = sub_itms.Len();
		for (int i = 0; i < len; ++i) {
			try {
				IoItmFil sub_itm = (IoItmFil)sub_itms.GetAt(i);
				bfr.AddStrA7("fil").AddBytePipe().AddStrU8(sub_itm.Name()).AddBytePipe().AddLongVariable(sub_itm.Size()).AddBytePipe().AddDate(sub_itm.ModifiedTime()).AddByteNl();
			}	catch (Exception e) {bfr.AddStrU8(ErrUtl.ToStrFull(e));}
		}
	}
        public static final Xows_cmd__fs_check Instance = new Xows_cmd__fs_check(); Xows_cmd__fs_check() {}
	private static final byte[] Arg_wiki = BryUtl.NewA7("wiki"), Arg_dir = BryUtl.NewA7("dir");
}
