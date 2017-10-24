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
