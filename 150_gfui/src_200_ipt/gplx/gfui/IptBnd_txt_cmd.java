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
public class IptBnd_txt_cmd implements InjectAble, GfoInvkAble, GfoEvObj {
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public void Inject(Object owner) {
		txtBox = GfuiTextBox_.cast_(owner);
		txtBox.TextAlignH_center_();
		IptBnd_.cmd_to_(IptCfg_.Null, txtBox, this, TxtBox_exec, IptKey_.Enter);
		GfoEvMgr_.SubSame(fwd, setEvt, this);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, TxtBox_exec))	GfoInvkAble_.InvkCmd_val(src, setCmd, cls.ParseOrNull(txtBox.Text()));
		else if	(ctx.Match(k, setEvt))			{
			int v = m.ReadInt("v");
			txtBox.Text_(cls.XtoUi(v, ClassXtnPool.Format_null));
		}
		else	return GfoInvkAble_.Rv_unhandled;
		return GfoInvkAble_.Rv_handled;
	}	static final String TxtBox_exec = "TxtBox_exec";
	GfuiTextBox txtBox; GfoInvkAble src; GfoEvObj fwd; String setCmd, setEvt; ClassXtn cls;		
	public static IptBnd_txt_cmd new_(GfoEvObj fwd, String setCmd, String setEvt, ClassXtn cls) {
		IptBnd_txt_cmd rv = new IptBnd_txt_cmd();
		rv.fwd = fwd; rv.src = GfoInvkAble_.cast_(fwd);
		rv.setCmd = setCmd; rv.setEvt = setEvt; rv.cls = cls;
		return rv;
	}
}
