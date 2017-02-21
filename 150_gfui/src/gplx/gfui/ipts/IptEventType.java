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
