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
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class Swt_lbl implements GxwElem, Swt_control {
	private Label lbl;
	public Swt_lbl(Swt_control owner, Keyval_hash ctorArgs) {
		lbl = new Label(owner.Under_composite(), SWT.CENTER);
		core = new Swt_core__basic(lbl);
		lbl.addKeyListener(new Swt_lnr_key(this));
		lbl.addMouseListener(new Swt_lnr_mouse(this));
	}
	@Override public Control Under_control() {return lbl;}
	@Override public Control Under_menu_control() {return lbl;}
	@Override public String TextVal() {return lbl.getText();} @Override public void TextVal_set(String v) {
		lbl.setText(v);
		}
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public Composite Under_composite() {return null;}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return null;}
}
