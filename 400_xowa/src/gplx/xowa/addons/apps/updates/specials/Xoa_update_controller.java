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
package gplx.xowa.addons.apps.updates.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.xowa.guis.cbks.*;
import gplx.core.gfobjs.*;
import gplx.xowa.addons.apps.updates.dbs.*; import gplx.xowa.addons.apps.updates.js.*;	
class Xoa_update_controller implements Gfo_invk {
	public void Update_app(Xoa_app app, String version_name) {
		// get app_version from db
		Io_url update_dir = app.Fsys_mgr().Root_dir().GenSubDir_nest("user", "app", "update");
		Io_url update_db_fil = update_dir.GenSubFil_nest("xoa_update.sqlite3");
		Xoa_update_db_mgr db_mgr = new Xoa_update_db_mgr(update_db_fil);
		Xoa_app_version_itm version_itm = db_mgr.Tbl__app_version().Select_by_version_or_null(version_name);

		// get src, trg, etc..
		String src = version_itm.Package_url();
		Io_url trg = update_dir.GenSubFil_nest("temp", version_itm.Name(), version_itm.Name() + ".zip");
		Io_mgr.Instance.DeleteDirDeep(trg.OwnerDir());
		long src_len = -1;

		// start download
		Xojs_wkr__download download_wkr = new Xojs_wkr__download
		( app.Gui__cbk_mgr(), Xog_cbk_trg.New(Xoa_update_special.Prototype.Special__meta().Ttl_bry())
		, "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__download_done), src, trg, src_len);
		download_wkr.Exec_async("app_updater");
	}
	private void On_download_done(GfoMsg m) {
		Xojs_wkr__download download_wkr = (Xojs_wkr__download)m.ReadObj("v");
		Io_url src = download_wkr.Trg();
		Io_url trg = src.OwnerDir().GenSubDir(src.NameOnly() + "_unzip");
		Xojs_wkr__unzip unzip_wkr = new Xojs_wkr__unzip(download_wkr.Cbk_mgr(), download_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__unzip_done), src, trg, -1);
		unzip_wkr.Exec_async("app_updater");
	}
	private void On_unzip_done(GfoMsg m) {
		Xojs_wkr__unzip unzip_wkr = (Xojs_wkr__unzip)m.ReadObj("v");
		Io_url src = unzip_wkr.Trg();
		Io_url trg = Io_url_.new_dir_("D:\\xowa_temp\\");
		Xojs_wkr__replace replace_wkr = new Xojs_wkr__replace(unzip_wkr.Cbk_mgr(), unzip_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__replace_done), src, trg);
		replace_wkr.Exec_async("app_updater");
	}
	private void On_replace_done(GfoMsg m) {
		Xojs_wkr__replace replace_wkr = (Xojs_wkr__replace)m.ReadObj("v");
		replace_wkr.Cbk_mgr().Send_json(replace_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfobj_nde.New().Add_bool("done", true));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__download_done))		On_download_done(m);
		else if	(ctx.Match(k, Invk__unzip_done))		On_unzip_done(m);
		else if	(ctx.Match(k, Invk__replace_done))		On_replace_done(m);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__download_done = "download_done", Invk__unzip_done = "unzip_done", Invk__replace_done = "replace_done";
}
