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
import gplx.gfui.kits.core.Swt_kit;
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwTextFld;
import gplx.gfui.controls.standards.GfuiTextBox_;
import gplx.gfui.draws.ColorAdp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Swt_text_w_border implements GxwTextFld, Swt_control {
	protected final Swt_kit kit;
	private Composite text_host;
	private Composite text_margin;
	private Text text_elem;
	public Swt_text_w_border(Swt_kit kit, Swt_control owner_control, Color color, Keyval_hash ctorArgs) {
		this.kit = kit;
		Composite owner = owner_control.Under_composite();
		int text_elem_style = ctorArgs.Has(GfuiTextBox_.Ctor_Memo) ? SWT.MULTI | SWT.WRAP | SWT.V_SCROLL : SWT.FLAT;
		New_box_text_w_border(owner.getDisplay(), owner, text_elem_style, color);
		core = new Swt_core__frames(this, text_host, new Swt_frame_itm[]
		{ new Swt_frame_itm__manual(text_margin, 1, 1, -2, -2)
		, new Swt_frame_itm__center_v(text_elem, this)
		});
		this.margins_t = this.margins_b = Margin_v_dflt;
		text_elem.addKeyListener(new Swt_lnr_key(this));
		text_elem.addMouseListener(new Swt_lnr_mouse(this));
	}
	@Override public Control Under_control() {return text_host;}
	@Override public Composite Under_composite() {return null;}
	@Override public Control Under_menu_control() {return text_elem;}
	public Text Under_text() {return text_elem;}
	@Override public int SelBgn() {return text_elem.getCaretPosition();}  @Override public void SelBgn_set(int v) {text_elem.setSelection(v);}
	@Override public int SelLen() {return text_elem.getSelectionCount();} @Override public void SelLen_set(int v) {text_elem.setSelection(this.SelBgn(), this.SelBgn() + v);}	
	@Override public String TextVal() {return text_elem.getText();} @Override public void TextVal_set(String v) {text_elem.setText(v);}
	@Override public GxwCore_base Core() {return core;} Swt_core__frames core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return null;
	}
	public int Margins_l() {return margins_l;} public int margins_l;
	public int Margins_r() {return margins_r;} public int margins_r;
	public int Margins_t() {return margins_t;} public int margins_t; 
	public int Margins_b() {return margins_b;} public int margins_b;
	public void Margins_set(int left, int top, int right, int bot) {
		this.margins_l = left; this.margins_t = top; this.margins_r = right; this.margins_b = bot;
	}
	@Override public boolean Border_on() {return false;}
	@Override public void Border_on_(boolean v) {} // SWT_TODO:borderWidth doesn't seem mutable
	public ColorAdp Border_color() {return border_color;} private ColorAdp border_color;
	public void Border_color_(ColorAdp v) {this.border_color = v; text_host.setBackground(kit.New_color(v));} 
	@Override public void CreateControlIfNeeded() {}
	@Override public boolean OverrideTabKey() {return false;} @Override public void OverrideTabKey_(boolean v) {}
	private void New_box_text_w_border(Display display, Composite owner, int style, Color border_color) {
		text_host = new Composite(owner, SWT.FLAT);
		text_margin = new Composite(text_host, SWT.FLAT);
		text_elem = new Text(text_margin, style);
		text_elem .addTraverseListener(Swt_lnr_traverse_ignore_ctrl.Instance);	// do not allow ctrl+tab to change focus when pressed in text box; allows ctrl+tab to be used by other bindings; DATE:2014-04-30  
		text_host.setSize(20, 20);
		text_margin.setSize(25, 25);
	}
	public static final int Margin_v_dflt = 4;
}
class Swt_lnr_traverse_ignore_ctrl implements TraverseListener {
    public void keyTraversed(TraverseEvent e) {
  	  if (Swt_lnr_key.Has_ctrl(e.stateMask)) e.doit = false;
    }
    public static final Swt_lnr_traverse_ignore_ctrl Instance = new Swt_lnr_traverse_ignore_ctrl();	
}
