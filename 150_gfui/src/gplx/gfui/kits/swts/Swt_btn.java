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
package gplx.gfui.kits.swts;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.GfsCtx;
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.types.commons.KeyValHash;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
public class Swt_btn implements GxwElem, Swt_control {
	private Button btn;
	public Swt_btn(Swt_control owner, KeyValHash ctorArgs) {
		btn = new Button(owner.Under_composite(), SWT.FLAT | SWT.PUSH);
		core = new Swt_core__basic(btn);
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
class Swt_clabel_lnr_focus implements FocusListener {
	public Swt_clabel_lnr_focus(Control v) {this.surrogate = v;} Control surrogate;
	@Override public void focusGained(org.eclipse.swt.events.FocusEvent e) {
		surrogate.forceFocus();
	}
	@Override public void focusLost(org.eclipse.swt.events.FocusEvent arg0) {}
}
