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
package gplx.gfui; import gplx.*;
import gplx.core.type_xtns.*; import gplx.core.interfaces.*;
public class IptBnd_upDownRange implements InjectAble, GfoInvkAble, GfoEvObj {
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public void Inject(Object owner) {
		txtBox = GfuiTextBox_.cast(owner);
		txtBox.TextAlignH_center_();
		if (bndCfg == null) bndCfg = IptCfg_.new_("gplx.gfui.IptBnd_upDownRange");
		IptBnd_.cmd_to_(bndCfg, txtBox, this, Invk_TxtBox_dec, IptKey_.Down, IptMouseWheel_.Down);
		IptBnd_.cmd_to_(bndCfg, txtBox, this, Invk_TxtBox_inc, IptKey_.Up, IptMouseWheel_.Up);
		IptBnd_.cmd_to_(bndCfg, txtBox, this, Invk_TxtBox_exec, IptKey_.Enter, IptMouseBtn_.Middle);
		GfoEvMgr_.SubSame(fwd, evt, this);
	}	static IptCfg bndCfg;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_TxtBox_dec))		ExecCmd(cmd, curVal - 1);
		else if	(ctx.Match(k, Invk_TxtBox_inc))		ExecCmd(cmd, curVal + 1);
		else if	(ctx.Match(k, Invk_TxtBox_exec))	{
			Object valObj = IntClassXtn.Instance.ParseOrNull(txtBox.Text()); if (valObj == null) throw Err_.new_wo_type("invalid int", "text", txtBox.Text());
			ExecCmd(doIt, Int_.cast(valObj));
		}
		else if	(ctx.Match(k, evt))			WhenEvt(ctx, m);
		else	return GfoInvkAble_.Rv_unhandled;
		return GfoInvkAble_.Rv_handled;
	}	static final String Invk_TxtBox_dec = "txtBox_dec", Invk_TxtBox_inc = "txtBox_inc", Invk_TxtBox_exec = "txtBox_exec";
	public int Adj() {return adj;} public IptBnd_upDownRange Adj_(int v) {adj = v; return this;} int adj;
	void WhenEvt(GfsCtx ctx, GfoMsg m) {
		curVal = m.ReadInt(arg) + adj;
		txtBox.Text_(Int_.To_str(curVal));
	}
	void ExecCmd(String c, int val) {
		GfoInvkAble_.InvkCmd_val(src, c, val - adj);
	}
	int curVal;
	GfuiTextBox txtBox; GfoInvkAble src; GfoEvObj fwd; String cmd, evt, doIt, arg;
	public static IptBnd_upDownRange new_(GfoEvObj fwd, String cmd, String evt, String arg) {return exec_(fwd, cmd, evt, cmd, arg);}
	public static IptBnd_upDownRange exec_(GfoEvObj fwd, String cmd, String evt, String doIt, String arg) {
		IptBnd_upDownRange rv = new IptBnd_upDownRange();
		rv.fwd = fwd; rv.src = GfoInvkAble_.cast(fwd);
		rv.cmd = cmd; rv.evt = evt; rv.doIt = doIt; rv.arg = arg;
		return rv;
	}
}
