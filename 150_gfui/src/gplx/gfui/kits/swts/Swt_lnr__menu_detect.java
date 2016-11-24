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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.Err_;
import gplx.Gfo_evt_mgr_;
import gplx.gfui.controls.elems.GfuiElem;
import gplx.gfui.controls.elems.GfuiElemKeys;
import gplx.gfui.kits.core.Swt_kit;

import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;

public class Swt_lnr__menu_detect implements MenuDetectListener {
	private final Swt_kit kit; private final GfuiElem elem;
	public Swt_lnr__menu_detect(Swt_kit kit, GfuiElem elem) {this.kit = kit; this.elem = elem;}
	@Override public void menuDetected(MenuDetectEvent arg0) {
		try {Gfo_evt_mgr_.Pub(elem, GfuiElemKeys.Evt_menu_detected);}
		catch (Exception e) {
			kit.Ask_ok("", "", "error during right-click; err=~{0}", Err_.Message_gplx_full(e));
		}
	}	
}
