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
package gplx.xowa.guis.menus.dom; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.menus.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*; import gplx.xowa.guis.cmds.*;
public class Xog_mnu_evt_mgr implements Gfo_evt_itm {
	private Ordered_hash itms = Ordered_hash_.New();
	public Xog_mnu_evt_mgr(Xog_mnu_base owner) {this.ev_mgr = new Gfo_evt_mgr(this);}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr;
	public void Sub(Gfui_mnu_itm mnu_itm) {
		itms.Add(mnu_itm.Uid(), mnu_itm);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Evt_selected_changed)) {
			int len = itms.Count();
			for (int i = 0; i < len; i++) {
				Gfui_mnu_itm itm = (Gfui_mnu_itm)itms.Get_at(i);
				itm.Selected_(m.ReadBool("v"));
			}
		}
		return this;
	}
	public static final String Evt_selected_changed = "selected_changed";
}
