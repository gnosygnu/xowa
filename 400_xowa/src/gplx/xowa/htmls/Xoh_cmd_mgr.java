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
package gplx.xowa.htmls; import gplx.*; import gplx.xowa.*;
public class Xoh_cmd_mgr {
	public int Count() {return cmds.Count();}
	public void Clear() {cmds.Clear();}
	public void Add(Xoh_cmd_itm itm) {cmds.Add(itm);} List_adp cmds = List_adp_.New();
	public void Exec(Xoae_app app, Xoae_page page) {
		int len = cmds.Count();
		if (len == 0) return;
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		usr_dlg.Prog_one(GRP_KEY, "bgn", "cmds bgn: ~{0}", len);	// NOTE: this message will not show, but is needed for other messages to show; SWT swallowing 1st msg before showing others?; DATE:2013-04-25
		for (int i = 0; i < len; i++) {
			if (usr_dlg.Canceled()) {usr_dlg.Prog_none(GRP_KEY, "cmds.done", ""); app.Log_wtr().Queue_enabled_(false); return;}
			Xoh_cmd_itm itm = null;
			try {
				itm = (Xoh_cmd_itm)cmds.Get_at(i);
				itm.Hcmd_exec(app, usr_dlg, page);
				itm.Hcmd_write(app, usr_dlg, page);
			} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to execute html cmd", "name", itm == null ? "unknown" : itm.Hcmd_id());}
		}
		this.Clear();
	}
	static final String GRP_KEY = "xowa.html.cmd_mgr";
}
