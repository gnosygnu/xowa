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
import gplx.GfoMsg;
import gplx.GfsCtx;
import gplx.Keyval_hash;
import gplx.gfui.SizeAdp;
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.controls.standards.GfuiBtn;
import gplx.gfui.imgs.ImageAdp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Swt_btn_no_border implements GxwElem, Swt_control {
	private ImageAdp btn_img; private Composite box_grp; private Label box_btn;
	public Swt_btn_no_border(Swt_control owner_control, Keyval_hash ctorArgs) {
		Composite owner = owner_control.Under_composite();		
		Make_btn_no_border(owner.getDisplay(), owner.getShell(), owner);
		this.core = new Swt_core_cmds(box_btn);
		box_btn.addKeyListener(new Swt_lnr_key(this));
		box_btn.addMouseListener(new Swt_lnr_mouse(this));
		box_btn.setCursor((Cursor)ctorArgs.Get_val_or_null("cursor"));
	}
	@Override public Control Under_control() {return box_btn;}
	@Override public Control Under_menu_control() {return box_btn;}
	@Override public String TextVal() {return box_btn.getText();} @Override public void TextVal_set(String v) {box_btn.setText(v);}
	@Override public GxwCore_base Core() {return core;} private final Swt_core_cmds core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} private GxwCbkHost host;
	@Override public Composite Under_composite() {return null;}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, GfuiBtn.Invk_btn_img))	return btn_img;
		else if	(ctx.Match(k, GfuiBtn.Invk_btn_img_))	Btn_img_((ImageAdp)m.CastObj("v"));
		return null;
	}
	private void Btn_img_(ImageAdp v) {
		if (box_btn == null || v == null) return;
		SizeAdp size = core.Size();
		int dif = 6;
		box_btn.setImage((Image)v.Resize(size.Width() - dif, size.Height() - dif).Under());
	}
	private void Make_btn_no_border(Display display, Shell shell, Control owner) {
		box_grp = new Composite(shell, SWT.FLAT);
		box_btn = new Label(shell, SWT.FLAT);
		box_btn.setSize(25, 25);
		box_btn.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		box_btn.addFocusListener(new Swt_clabel_lnr_focus(box_grp));
	}
}
