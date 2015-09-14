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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.threads.*;
class Xoi_cmd_wiki_image_cfg extends Gfo_thread_cmd_replace implements Gfo_thread_cmd {	public Xoi_cmd_wiki_image_cfg(Xoae_app app, Io_url url) {this.app = app; super.Init(app.Usr_dlg(), app.Gui_mgr().Kit(), url);} private Xoae_app app;
	@Override public void Async_run() {
		super.Async_run();
		app.Cfg_mgr().Set_by_app("app.files.download.enabled", "y");
		app.Cfg_mgr().Db_save_txt();
	}
	static final String GRP_KEY = "xowa.thread.dump.image_cfg";
	public static final String KEY_dump = "wiki.image_cfg";
}
