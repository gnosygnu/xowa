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
import gplx.gfui.ipts.*;
import gplx.gfui.kits.core.Gfui_clipboard;
import gplx.gfui.kits.core.Gfui_clipboard_;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
public class Swt_clipboard implements Gfui_clipboard {
	public Swt_clipboard(Display display) {
		this.display = display;
		clipboard = new Clipboard(display);
	}	Display display; Clipboard clipboard;
	public void Copy(String v) {
		if (String_.Len_eq_0(v)) return;		
		TextTransfer textTransfer = TextTransfer.getInstance();
		clipboard.setContents(new Object[]{v}, new Transfer[]{textTransfer});
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Gfui_clipboard_.Invk_copy)) 				Send_key(IptKey_.Ctrl, 'C');
		else if	(ctx.Match(k, Gfui_clipboard_.Invk_select_all)) 		Send_key(IptKey_.Ctrl, 'A');
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	@Override public void Rls() {clipboard.dispose();}
	int Xto_keycode(IptKey modifier) {
		switch (modifier.Val()) {
			case IptKey_.KeyCode_Ctrl: 		return SWT.CTRL; 
			case IptKey_.KeyCode_Alt: 		return SWT.ALT; 
			case IptKey_.KeyCode_Shift: 	return SWT.SHIFT;
			default:						return SWT.NONE;
		}
	}
	public void Send_key(IptKey mod, char key_press_char) {
		Event event = new Event();
		int modifier_key_code = Xto_keycode(mod);
		event.keyCode = modifier_key_code; event.type = SWT.KeyDown; display.post(event);
		event.keyCode = 0; event.character = key_press_char; display.post(event);
		event.type = SWT.KeyUp; display.post(event);
		event.keyCode = modifier_key_code; event.character = 0; display.post(event);		
	}
}
