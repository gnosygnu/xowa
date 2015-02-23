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
package gplx.xowa.bldrs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public class Xobu_poll_mgr implements GfoInvkAble {
	public Xobu_poll_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	public int Poll_interval() {return poll_interval;} private int poll_interval = 1000;
	private Io_url poll_file;
	public void Poll() {
		if (poll_file == null) poll_file = app.Fsys_mgr().Root_dir().GenSubFil("bldr_poll.gfs");
		if (!Io_mgr._.ExistsFil(poll_file)) return; // file doesn't exist
		String poll_text = Io_mgr._.LoadFilStr(poll_file);
		Io_mgr._.DeleteFil(poll_file);
		app.Usr_dlg().Note_many("", "", "poll file found: ~{0}", poll_file.Raw());
		app.Gfs_mgr().Run_str(poll_text);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_poll_interval_))		poll_interval = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_poll_file_))			poll_file = m.ReadIoUrl("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_poll_interval_ = "poll_interval_", Invk_poll_file_ = "poll_file_";
}
