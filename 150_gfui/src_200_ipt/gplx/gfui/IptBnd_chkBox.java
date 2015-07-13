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
public class IptBnd_chkBox implements InjectAble, GfoEvObj {
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public void Inject(Object owner) {
		chkBox = GfuiChkBox_.cast_(owner);
		GfoEvMgr_.Sub(chkBox, "Check_end", this, setCmd);
		GfoEvMgr_.SubSame(fwd, setEvt, this);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, setCmd))
			GfoInvkAble_.InvkCmd_val(invkAble, setCmd, chkBox.Val());
		else if	(ctx.Match(k, setEvt)) {
			boolean v = m.ReadBool(msgArg);
			chkBox.Val_sync(v);
		}
		else	return GfoInvkAble_.Rv_unhandled;
		return GfoInvkAble_.Rv_handled;
	}
	GfoEvObj fwd; GfoInvkAble invkAble; String setCmd, setEvt, msgArg; GfuiChkBox chkBox;
	public static IptBnd_chkBox new_(GfoEvObj src, String setCmd, String setEvt, String msgArg) {
		IptBnd_chkBox rv = new IptBnd_chkBox();
		rv.fwd = src; rv.invkAble = GfoInvkAble_.cast_(src); rv.setCmd = setCmd; rv.setEvt = setEvt; rv.msgArg = msgArg;
		return rv;
	}
}
