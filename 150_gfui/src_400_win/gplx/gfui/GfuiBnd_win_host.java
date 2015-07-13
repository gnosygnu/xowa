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
