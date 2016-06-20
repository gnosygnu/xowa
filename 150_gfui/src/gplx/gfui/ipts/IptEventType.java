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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.gfui.controls.elems.*;
public class IptEventType {
	public int Val() {return val;} int val;
	public String Name() {return name;} private String name;
	public IptEventType Add(IptEventType comp) {return IptEventType_.add_(this, comp);}
	@Override public String toString() {return name;}
	@gplx.Internal protected IptEventType(int v, String s) {this.val = v; this.name = s;}
	public static Object HandleEvt(IptBndsOwner owner, GfsCtx ctx, GfoMsg m) {
		IptEventData iptData = IptEventData.ctx_(ctx, m);
		boolean processed = owner.IptBnds().Process(iptData);
		if (processed || IptEventMgr.StopFwd(iptData)) {	// NOTE: IptMsgs are single-dispatch;
		}
		else {
			Gfo_evt_mgr_.Pub_msg(owner, ctx, GfuiElemKeys.IptRcvd_evt, m);
		}
		return Gfo_invk_.Rv_handled;
	}
}
