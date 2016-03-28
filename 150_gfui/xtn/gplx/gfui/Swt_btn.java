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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
class Swt_btn implements GxwElem, Swt_control {
	private Button btn;
	public Swt_btn(Swt_control owner, Keyval_hash ctorArgs) {
		btn = new Button(owner.Under_composite(), SWT.FLAT | SWT.PUSH);
		core = new Swt_core_cmds(btn);
		btn.addKeyListener(new Swt_lnr_key(this));
		btn.addMouseListener(new Swt_lnr_mouse(this));
	}
	@Override public Control Under_control() {return btn;}
	@Override public Control Under_menu_control() {return btn;}
	@Override public String TextVal() {return btn.getText();} @Override public void TextVal_set(String v) {btn.setText(v);}
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public Composite Under_composite() {return null;}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return null;}
}
class Swt_btn_no_border implements GxwElem, Swt_control {
	private ImageAdp btn_img; private Composite box_grp; private Label box_btn;
	public Swt_btn_no_border(Swt_control owner_control, Keyval_hash ctorArgs) {
		Composite owner = owner_control.Under_composite();
		Make_btn_no_border(owner.getDisplay(), owner.getShell(), owner);
		this.core = new Swt_core_cmds(box_btn);
		box_btn.addKeyListener(new Swt_lnr_key(this));
		box_btn.addMouseListener(new Swt_lnr_mouse(this));
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
	void Btn_img_(ImageAdp v) {
		if (box_btn == null || v == null) return;
		SizeAdp size = core.Size();
		int dif = 6;
		box_btn.setImage((Image)v.Resize(size.Width() - dif, size.Height() - dif).Under());
	}
	void Make_btn_no_border(Display display, Shell shell, Control owner) {
		box_grp = new Composite(shell, SWT.FLAT);
		box_btn = new Label(shell, SWT.FLAT);
		box_btn.setSize(25, 25);
		box_btn.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		box_btn.addFocusListener(new Swt_clabel_lnr_focus(box_grp));
	}
}
class Swt_clabel_lnr_focus implements FocusListener {
	public Swt_clabel_lnr_focus(Control v) {this.surrogate = v;} Control surrogate;
	@Override public void focusGained(org.eclipse.swt.events.FocusEvent e) {
		surrogate.forceFocus();
	}
	@Override public void focusLost(org.eclipse.swt.events.FocusEvent arg0) {}
}