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
package gplx.xowa.addons.wikis.pagebaks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*;
import gplx.xowa.specials.*;
import gplx.langs.htmls.encoders.*;
public class Pagebaks_addon implements Xoax_addon_itm {
	public static void On_page_saved(Xoae_app app, Xowe_wiki wiki, Xoa_ttl ttl, byte[] text) {
		// get vars
		if (!app.Cfg().Get_bool_app_or("xowa.wiki.edit.pagebaks.enabled", true)) return;

		try {
			// #save file
			// get file name; note encoding for wnt even on lnx systems just to be consistent
			Gfo_url_encoder encoder = Gfo_url_encoder_.New__fsys_wnt().Make();
			byte[] file_name = encoder.Encode(ttl.Full_db());

			// save file to backup dir; EX: /xowa/wiki/en.w/user/temp/save_backups/Earth/20170303_080102_123.txt
			Io_url bak_dir = wiki.Fsys_mgr().Root_dir().GenSubDir_nest("user", "temp", "page_backups", String_.new_u8(file_name));
			Io_url file_url = bak_dir.GenSubFil_ary(Datetime_now.Get().XtoStr_fmt("yyyyMMdd_HHmmss_fff"), ".txt");
			Io_mgr.Instance.SaveFilBry(file_url, text);

			// #prune dir
			// get files for pruning
			Io_url[] fils = Io_mgr.Instance.QueryDir_fils(bak_dir);
			Array_.Sort(fils);
			
			// calc files
			int num_files = fils.length;
			int max_files = app.Cfg().Get_int_app_or("xowa.wiki.edit.pagebaks.max_backups", 16);
			int cutoff = num_files - max_files;

			// do pruning
			for (int i = 0; i < cutoff; i++) {// EX: 3 files and 2 max; 1st file (index 0) needs to be deleted
				Io_mgr.Instance.DeleteFil(fils[i]);
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "failed to save page backup; wiki=~{0} ttl=~{1} err=~{2}", wiki.Domain_bry(), ttl.Full_db(), Err_.Message_gplx_log(e));
		}
	}

	public String Addon__key() {return "xowa.wikis.edits.pagebaks";}
}
