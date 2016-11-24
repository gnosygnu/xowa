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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.core.interfaces.*; import gplx.gfui.controls.elems.*;
public class DataBndr_whenEvt_execCmd implements InjectAble, Gfo_invk, Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {if (evt_mgr == null) evt_mgr = new Gfo_evt_mgr(this); return evt_mgr;} Gfo_evt_mgr evt_mgr;
	public DataBndr_whenEvt_execCmd WhenArg_(String v) {whenArg = v; return this;} private String whenArg = "v";
	public DataBndr_whenEvt_execCmd WhenEvt_(Gfo_evt_itm whenSrc, String whenEvt) {
		this.whenEvt = whenEvt;
		Gfo_evt_mgr_.Sub_same(whenSrc, whenEvt, this);
		return this;
	}
	public DataBndr_whenEvt_execCmd GetCmd_(Gfo_invk getInvk, String getCmd) {
		this.getInvk = getInvk;
		this.getCmd = getCmd;
		return this;
	}	Gfo_invk getInvk; String getCmd;
	public void Inject(Object owner) {
		setInvk = (Gfo_invk)owner;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, whenEvt)) {
			Object evtVal = m.CastObjOr(whenArg, "");
			Object getVal = getInvk.Invk(GfsCtx.Instance, 0, getCmd, GfoMsg_.new_cast_(getCmd).Add("v", evtVal));
			GfoMsg setMsg = GfoMsg_.new_cast_(setCmd).Add("v", Object_.Xto_str_strict_or_empty(getVal));
			setInvk.Invk(GfsCtx.Instance, 0, setCmd, setMsg);
			return Gfo_invk_.Rv_handled;
		}
		else	return Gfo_invk_.Rv_unhandled;
	}
	String whenEvt, setCmd; Gfo_invk setInvk;
	public static DataBndr_whenEvt_execCmd text_() {
		DataBndr_whenEvt_execCmd rv = new DataBndr_whenEvt_execCmd();
		rv.setCmd = GfuiElemKeys.Text_set;
		return rv;
	}
}
