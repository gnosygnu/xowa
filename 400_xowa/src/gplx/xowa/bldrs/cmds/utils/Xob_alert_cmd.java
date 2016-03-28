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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.gfui.*;
public class Xob_alert_cmd extends Xob_cmd__base implements Xob_cmd {
	public Xob_alert_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Xob_alert_cmd Msg_(String v) {this.msg = v; return this;} private String msg = "developer forgot to include message";
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_util_alert;}
	@Override public void Cmd_run() {
		Gfui_kit kit = app.Gui_mgr().Kit();
		if (kit.Tid() != Gfui_kit_.Swt_tid) return;
		kit.Ask_ok("", "", msg);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return GfoInvkAble_.Rv_unhandled;}
}
