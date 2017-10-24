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
