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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.ipts.*; import gplx.gfui.controls.elems.*;
public class GfuiBnd_win_host {
	public GfuiWin HosterWin() {return hosterWin;} GfuiWin hosterWin;
	public GfuiElem HosteeBox() {return hosteeBox;} GfuiElem hosteeBox;
	public GfuiWin OwnerWin()  {return ownerWin;} GfuiWin ownerWin;
	public static GfuiBnd_win_host new_(GfuiElem elemToHost, GfuiWin ownerForm) {
		GfuiBnd_win_host rv = new GfuiBnd_win_host();
		rv.ctor_(elemToHost, ownerForm);
		return rv;
	}
	void ctor_(GfuiElem elemToHost, GfuiWin ownerForm) {
		this.hosteeBox = elemToHost;
		this.ownerWin = ownerForm;
		this.hosterWin = GfuiWin_.toaster_("hosterWin_" + elemToHost.Key_of_GfuiElem(), ownerForm);

		hosterWin.IptBnds().EventsToFwd_set(IptEventType_.None);
		hosterWin.Size_(hosteeBox.Size());
		hosteeBox.Owner_(hosterWin);
		hosterWin.TaskbarVisible_(false);
		hosterWin.TaskbarParkingWindowFix(ownerForm);	// else ContextMenu shows up as WindowsFormsParkingWindow
	}
}	
