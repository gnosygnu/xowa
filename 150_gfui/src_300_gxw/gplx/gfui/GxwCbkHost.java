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
public interface GxwCbkHost {
	boolean KeyDownCbk(IptEvtDataKey data);
	boolean KeyHeldCbk(IptEvtDataKeyHeld data);
	boolean KeyUpCbk(IptEvtDataKey data);
	boolean MouseDownCbk(IptEvtDataMouse data);
	boolean MouseUpCbk(IptEvtDataMouse data);
	boolean MouseMoveCbk(IptEvtDataMouse data);
	boolean MouseWheelCbk(IptEvtDataMouse data);
	boolean PaintCbk(PaintArgs paint);
	boolean PaintBackgroundCbk(PaintArgs paint);
	boolean DisposeCbk();
	boolean SizeChangedCbk();
	boolean FocusGotCbk();
	boolean FocusLostCbk();
	boolean VisibleChangedCbk();
		//boolean WndProcCbk(WindowMessage windowMessage);
	}
