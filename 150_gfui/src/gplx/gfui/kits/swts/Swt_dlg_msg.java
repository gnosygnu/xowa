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
import gplx.*;
import gplx.gfui.kits.*;
import gplx.gfui.kits.core.Gfui_dlg_msg;
import gplx.gfui.kits.core.Gfui_dlg_msg_;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;

public class Swt_dlg_msg implements Gfui_dlg_msg, Runnable {
	public Swt_dlg_msg(Shell shell)						{this.shell = shell;} Shell shell;
	public Gfui_dlg_msg Init_msg_(String v) 			{msg = v; return this;} String msg;
	public Gfui_dlg_msg Init_ico_(int v) 				{ico = Xto_swt_ico(v); return this;} int ico = -1;
	public Gfui_dlg_msg Init_btns_(int... ary) {
		int ary_len = ary.length;
		btns = -1;
		for (int i = 0; i < ary_len; i++) {
			int swt_btn = Xto_swt_btn(ary[i]);
			if (btns == -1) btns  = swt_btn;
			else			btns |= swt_btn;
		}
		return this;		
	}	int btns = -1;
	public int Ask_rslt;
	@Override public void run() {
		Ask_rslt = this.Ask();
	}
	public boolean Ask(int expd) {return Ask() == expd;}
	public int Ask() {
		int ctor_ico = ico == -1 ? SWT.ICON_INFORMATION : ico;
		int ctor_btn = btns == -1 ? SWT.OK : btns;
		MessageBox mb = new MessageBox(shell, ctor_ico | ctor_btn);
		if (msg != null) mb.setMessage(msg);
		int rv = mb.open();
		return Xto_gfui_btn(rv);
	}
	int Xto_swt_ico(int v) {
		switch (v) {
			case Gfui_dlg_msg_.Ico_error: 			return SWT.ICON_ERROR;
			case Gfui_dlg_msg_.Ico_information: 	return SWT.ICON_INFORMATION;
			case Gfui_dlg_msg_.Ico_question: 		return SWT.ICON_QUESTION;
			case Gfui_dlg_msg_.Ico_warning: 		return SWT.ICON_WARNING;
			case Gfui_dlg_msg_.Ico_working: 		return SWT.ICON_WORKING;
			default: 								throw Err_.new_unhandled(v);
		}		
	}
	int Xto_swt_btn(int v) {
		switch (v) {
			case Gfui_dlg_msg_.Btn_ok: 			return SWT.OK;
			case Gfui_dlg_msg_.Btn_yes: 		return SWT.YES;
			case Gfui_dlg_msg_.Btn_no: 			return SWT.NO;
			case Gfui_dlg_msg_.Btn_ignore: 		return SWT.IGNORE;
			case Gfui_dlg_msg_.Btn_abort: 		return SWT.ABORT;
			case Gfui_dlg_msg_.Btn_cancel: 		return SWT.CANCEL;
			default: 							throw Err_.new_unhandled(v);
		}		
	}
	int Xto_gfui_btn(int v) {
		switch (v) {
			case SWT.OK: 		return Gfui_dlg_msg_.Btn_ok;
			case SWT.YES: 		return Gfui_dlg_msg_.Btn_yes;
			case SWT.NO: 		return Gfui_dlg_msg_.Btn_no;
			case SWT.IGNORE: 	return Gfui_dlg_msg_.Btn_ignore;
			case SWT.ABORT: 	return Gfui_dlg_msg_.Btn_abort;
			case SWT.CANCEL: 	return Gfui_dlg_msg_.Btn_cancel;
			default: 							throw Err_.new_unhandled(v);
		}		
	}
}
