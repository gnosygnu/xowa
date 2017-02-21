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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.draws.*; import gplx.gfui.ipts.*; import gplx.gfui.layouts.*; import gplx.gfui.controls.windows.*;
public class GfuiStatusBoxBnd implements Gfo_invk {
	public GfuiStatusBox Box() {return statusBox;} GfuiStatusBox statusBox = GfuiStatusBox_.new_("statusBox");
	void ShowTime() {
		statusBox.ExecUsrMsg(UsrMsgWkr_.Type_Note, UsrMsg.new_(Datetime_now.Get().XtoStr_gplx_long()));
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_ShowTime))			ShowTime();
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String Invk_ShowTime = "ShowTime";
	public static GfuiStatusBoxBnd gft_(GfuiWin owner) {
		GfuiStatusBoxBnd rv = new GfuiStatusBoxBnd();
		rv.ctor_GfuiStatusBoxBnd(owner);
		return rv;
	}
	void ctor_GfuiStatusBoxBnd(GfuiWin win) {
		IptBnd_.cmd_to_(IptCfg_.Null, win, this, GfuiStatusBoxBnd.Invk_ShowTime, IptKey_.add_(IptKey_.Ctrl, IptKey_.Shift, IptKey_.T));
		statusBox.Owner_(win).Visible_off_().BackColor_(ColorAdp_.Black).TextAlignH_right_().ForeColor_(ColorAdp_.Green);
		win.Lyt().SubLyts().Add(GftGrid.new_().Bands_dir_(DirInt.Bwd).Bands_add(GftBand.new_().Cells_num_(1).Len1_abs_(13)));
	}
}
