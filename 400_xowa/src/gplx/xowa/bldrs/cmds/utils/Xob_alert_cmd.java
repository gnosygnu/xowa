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
package gplx.xowa.bldrs.cmds.utils; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*;
public class Xob_alert_cmd extends Xob_cmd__base implements Xob_cmd {
	public Xob_alert_cmd(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	public Xob_alert_cmd Msg_(String v) {this.msg = v; return this;} private String msg = "no message specified";
	@Override public void Cmd_run() {
		Gfui_kit kit = app.Gui_mgr().Kit();
		if (kit.Tid() != Gfui_kit_.Swt_tid) return;
		kit.Ask_ok("", "", msg);
		Xoa_app_.Usr_dlg().Prog_many("", "", msg);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__text_))		this.msg = m.ReadStr("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk__text_ = "text_";

	public static final String BLDR_CMD_KEY = "ui.alert";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xob_alert_cmd(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xob_alert_cmd(bldr, wiki);}
}
