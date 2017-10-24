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
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Swt_btn_no_border implements GxwElem, Swt_control {
	private ImageAdp btn_img; private Composite box_grp; private Label box_btn;
	public Swt_btn_no_border(Swt_control owner_control, Keyval_hash ctorArgs) {
		Composite owner = owner_control.Under_composite();		
		Make_btn_no_border(owner.getDisplay(), owner.getShell(), owner);
		this.core = new Swt_core__dual(box_grp, box_btn, 2, 2);
		box_btn.addKeyListener(new Swt_lnr_key(this));
		box_btn.addMouseListener(new Swt_lnr_mouse(this));
		box_btn.setCursor((Cursor)ctorArgs.Get_val_or_null("cursor"));
	}
	@Override public Control Under_control() {return box_grp;}
	@Override public Composite Under_composite() {return box_grp;}
	@Override public Control Under_menu_control() {return box_grp;}
	@Override public String TextVal() {return box_btn.getText();} @Override public void TextVal_set(String v) {box_btn.setText(v);}
	@Override public GxwCore_base Core() {return core;} private final Swt_core__base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} private GxwCbkHost host;
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, GfuiBtn.Invk_btn_img))	return btn_img;
		else if	(ctx.Match(k, GfuiBtn.Invk_btn_img_))	Btn_img_((ImageAdp)m.CastObj("v"));
		return null;
	}
	private void Btn_img_(ImageAdp v) {
		if (box_btn == null || v == null) return;
		SizeAdp size = core.Size();
		// HACK: nightmode
		// for day mode, resize needed b/c blurred images look better
		// for night mode, SWT introduces white pixels
		// so resize only if height is different knowing that (a) btns have height of 16px and (b) day=32px; night=16px
		// note can't use width, b/c search_exec_btn somehow goes from 16px to 20px
		if (	v.Size().Height() != size.Height()
			&& 	size.Width() != 0)	// WORKAROUND.SWT: width is 0 due to "need to specify height" workaround on 2017-03-28; however, can't set 0 width else SWT will throw error; DATE:2017-07-23
			v = v.Resize(size.Width(), size.Height());
		if ((v.Under() instanceof Image)) { // check needed else will fail when image doesn't exist; DATE:2017-06-03
			box_btn.setImage(Copy_w_transparency((Image)v.Under()));
		}
	}
	private Image Copy_w_transparency(Image src) {
      // set transparency
      ImageData imageData = src.getImageData();
      imageData.transparentPixel = imageData.getPixel(0, 0);

      // create new image with transparency set
      Image trg = new Image(box_grp.getDisplay(), imageData);
      src.dispose();
      return trg;
	}
	private void Make_btn_no_border(Display display, Shell shell, Composite owner) {
		box_grp = new Composite(owner, SWT.FLAT);
		box_btn = new Label(box_grp, SWT.FLAT);
		box_grp.setSize(16, 16);
		box_btn.setSize(16, 16);
		box_btn.addFocusListener(new Swt_clabel_lnr_focus(box_grp));
	}
}
