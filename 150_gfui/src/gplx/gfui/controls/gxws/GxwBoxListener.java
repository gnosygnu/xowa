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
package gplx.gfui.controls.gxws; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
public class GxwBoxListener implements ComponentListener, FocusListener {
	@Override public void componentShown(ComponentEvent e) 		{host.Host().VisibleChangedCbk();}
	@Override public void componentHidden(ComponentEvent e) 	{host.Host().VisibleChangedCbk();}
	@Override public void componentMoved(ComponentEvent e) 		{}
	@Override public void componentResized(ComponentEvent e) 	{host.Host().SizeChangedCbk();}
	@Override public void focusGained(FocusEvent e) 			{host.Host().FocusGotCbk();}
	@Override public void focusLost(FocusEvent e) 				{host.Host().FocusLostCbk();}
	public GxwBoxListener(GxwElem host) {this.host = host;} GxwElem host;
}
