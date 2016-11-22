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
import gplx.xowa.addons.apps.updates.js.*;
class Xoa_update_controller implements Gfo_invk {
	public void Update_app(Xoa_app app, String src, String trg_str, long src_len) {
		Io_url trg = Io_url_.new_any_(trg_str);
		Xog_cbk_mgr cbk_mgr = app.Gui__cbk_mgr();
		Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xoa_update_special.Prototype.Special__meta().Ttl_bry());
		Xojs_wkr__download download_wkr = new Xojs_wkr__download(cbk_mgr, cbk_trg, "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__download_done), src, trg, src_len);
		download_wkr.Exec_async("app_updater");
	}
	private void On_download_done(GfoMsg m) {
		Xojs_wkr__download download_wkr = (Xojs_wkr__download)m.ReadObj("v");
		Io_url trg = download_wkr.Trg();
		Xojs_wkr__unzip unzip_wkr = new Xojs_wkr__unzip(download_wkr.Cbk_mgr(), download_wkr.Cbk_trg(), "xo.app_updater.download__prog", Gfo_invk_cmd.New_by_key(this, Invk__unzip_done), trg, trg.OwnerDir().GenSubDir(trg.NameOnly()), download_wkr.Prog_data_end());
		unzip_wkr.Exec_async("app_updater");
	}
	private void On_unzip_done(GfoMsg m) {
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__download_done))		On_download_done(m);
		else if	(ctx.Match(k, Invk__unzip_done))		On_unzip_done(m);
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__download_done = "download_done", Invk__unzip_done = "unzip_done";
}
