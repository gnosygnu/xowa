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
