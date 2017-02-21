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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.type_xtns.*; import gplx.core.interfaces.*;
import gplx.gfui.controls.standards.*;
public class IptBnd_upDownRange implements InjectAble, Gfo_invk, Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public void Inject(Object owner) {
		txtBox = GfuiTextBox_.cast(owner);
		txtBox.TextAlignH_center_();
		if (bndCfg == null) bndCfg = IptCfg_.new_("gplx.gfui.IptBnd_upDownRange");
		IptBnd_.cmd_to_(bndCfg, txtBox, this, Invk_TxtBox_dec, IptKey_.Down, IptMouseWheel_.Down);
		IptBnd_.cmd_to_(bndCfg, txtBox, this, Invk_TxtBox_inc, IptKey_.Up, IptMouseWheel_.Up);
		IptBnd_.cmd_to_(bndCfg, txtBox, this, Invk_TxtBox_exec, IptKey_.Enter, IptMouseBtn_.Middle);
		Gfo_evt_mgr_.Sub_same(fwd, evt, this);
	}	static IptCfg bndCfg;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_TxtBox_dec))		ExecCmd(cmd, curVal - 1);
		else if	(ctx.Match(k, Invk_TxtBox_inc))		ExecCmd(cmd, curVal + 1);
		else if	(ctx.Match(k, Invk_TxtBox_exec))	{
			Object valObj = IntClassXtn.Instance.ParseOrNull(txtBox.Text()); if (valObj == null) throw Err_.new_wo_type("invalid int", "text", txtBox.Text());
			ExecCmd(doIt, Int_.cast(valObj));
		}
		else if	(ctx.Match(k, evt))			WhenEvt(ctx, m);
		else	return Gfo_invk_.Rv_unhandled;
		return Gfo_invk_.Rv_handled;
	}	static final    String Invk_TxtBox_dec = "txtBox_dec", Invk_TxtBox_inc = "txtBox_inc", Invk_TxtBox_exec = "txtBox_exec";
	public int Adj() {return adj;} public IptBnd_upDownRange Adj_(int v) {adj = v; return this;} int adj;
	void WhenEvt(GfsCtx ctx, GfoMsg m) {
		curVal = m.ReadInt(arg) + adj;
		txtBox.Text_(Int_.To_str(curVal));
	}
	void ExecCmd(String c, int val) {
		Gfo_invk_.Invk_by_val(src, c, val - adj);
	}
	int curVal;
	GfuiTextBox txtBox; Gfo_invk src; Gfo_evt_itm fwd; String cmd, evt, doIt, arg;
	public static IptBnd_upDownRange new_(Gfo_evt_itm fwd, String cmd, String evt, String arg) {return exec_(fwd, cmd, evt, cmd, arg);}
	public static IptBnd_upDownRange exec_(Gfo_evt_itm fwd, String cmd, String evt, String doIt, String arg) {
		IptBnd_upDownRange rv = new IptBnd_upDownRange();
		rv.fwd = fwd; rv.src = (Gfo_invk)fwd;
		rv.cmd = cmd; rv.evt = evt; rv.doIt = doIt; rv.arg = arg;
		return rv;
	}
}
