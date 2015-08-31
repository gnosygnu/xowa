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
class GfuiBtnClickBnd implements InjectAble, GfoInvkAble {
	public void Inject(Object owner) {
		GfuiElem elem = GfuiElem_.cast(owner);
		IptBnd_.cmd_(IptCfg_.Null, elem, GfuiElemKeys.ActionExec_cmd, IptKey_.Enter, IptKey_.Space);
		IptBnd_.cmd_(IptCfg_.Null, elem, GfuiElemKeys.Focus_cmd, IptMouseBtn_.Left);
		IptBnd_.ipt_to_(IptCfg_.Null, elem, this, ExecMouseUp_cmd, IptEventType_.MouseUp, IptMouseBtn_.Left);
	}
	void ExecMouseUp(IptEventData iptData) {
		GfuiElem elem = GfuiElem_.cast(iptData.Sender());
		int x = iptData.MousePos().X(), y = iptData.MousePos().Y();
		SizeAdp buttonSize = elem.Size();
		if (	x >= 0 && x <= buttonSize.Width()
			&&	y >= 0 && y <= buttonSize.Height())
			elem.Click();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, ExecMouseUp_cmd))		ExecMouseUp(IptEventData.ctx_(ctx, m));
		else return GfoInvkAble_.Rv_unhandled;
		return this;
	}	static final String ExecMouseUp_cmd = "ExecMouseUp";
	public static final GfuiBtnClickBnd _ = new GfuiBtnClickBnd(); GfuiBtnClickBnd() {}
}
