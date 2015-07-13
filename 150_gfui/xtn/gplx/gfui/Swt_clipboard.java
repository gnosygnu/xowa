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
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
class Swt_clipboard implements Gfui_clipboard {
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
		else	return GfoInvkAble_.Rv_unhandled;
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
