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
	private Composite text_host;
	private Composite text_margin;
	private Text text_elem;
	public Swt_text_w_border(Swt_control owner_control, Color color, Keyval_hash ctorArgs) {
		Composite owner = owner_control.Under_composite();
		int text_elem_style = ctorArgs.Has(GfuiTextBox_.Ctor_Memo) ? SWT.MULTI | SWT.WRAP | SWT.V_SCROLL : SWT.FLAT;
		New_box_text_w_border(owner.getDisplay(), owner.getShell(), text_elem_style, color);
		core = new Swt_core_cmds_frames(text_host, new Swt_core_cmds_frames_itm[]
				{	new Swt_core_cmds_frames_itm_manual(text_margin, 1, 1, -2, -2)
				,	new Swt_core_cmds_frames_itm_center_v(text_elem, this)
				});
		text_elem.addKeyListener(new Swt_lnr_key(this));
		text_elem.addMouseListener(new Swt_lnr_mouse(this));
	}
	@Override public Control Under_control() {return text_host;}
	@Override public Composite Under_composite() {return null;}
	@Override public Control Under_menu_control() {return text_elem;}
	public Text Under_text() {return text_elem;}
	@Override public int SelBgn() {return text_elem.getCaretPosition();} 	@Override public void SelBgn_set(int v) {text_elem.setSelection(v);}
	@Override public int SelLen() {return text_elem.getSelectionCount();} @Override public void SelLen_set(int v) {text_elem.setSelection(this.SelBgn(), this.SelBgn() + v);}	
	@Override public String TextVal() {return text_elem.getText();} @Override public void TextVal_set(String v) {text_elem.setText(v);}
	@Override public GxwCore_base Core() {return core;} Swt_core_cmds_frames core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return null;
	}
	public int Margins_l() {return margins_l;} int margins_l;
	public int Margins_r() {return margins_r;} int margins_r;
	public int Margins_t() {return margins_t;} int margins_t;
	public int Margins_b() {return margins_b;} int margins_b;
	public void Margins_set(int left, int top, int right, int bot) {
		this.margins_l = left; this.margins_t = top; this.margins_r = right; this.margins_b = bot;
	}
	@Override public boolean Border_on() {return false;} @Override public void Border_on_(boolean v) {} // SWT_TODO:borderWidth doesn't seem mutable
	@Override public void CreateControlIfNeeded() {}
	@Override public boolean OverrideTabKey() {return false;} @Override public void OverrideTabKey_(boolean v) {}
	void New_box_text_w_border(Display display, Shell shell, int style, Color color) {
		text_host = new Composite(shell, SWT.FLAT);
		text_margin = new Composite(text_host, SWT.FLAT);
		text_elem = new Text(text_margin, style);
		text_elem .addTraverseListener(Swt_lnr_traverse_ignore_ctrl.Instance);	// do not allow ctrl+tab to change focus when pressed in text box; allows ctrl+tab to be used by other bindings; DATE:2014-04-30  
		text_host.setSize(20, 20);
		text_host.setBackground(color);
		text_margin.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		text_margin.setSize(20, 20);
		text_elem.setSize(20 - 2, 20 - 2);
		text_elem.setLocation(1, 1);
	}
}
class Swt_lnr_traverse_ignore_ctrl implements TraverseListener {
    public void keyTraversed(TraverseEvent e) {
  	  if (Swt_lnr_key.Has_ctrl(e.stateMask)) e.doit = false;
    }
    public static final Swt_lnr_traverse_ignore_ctrl Instance = new Swt_lnr_traverse_ignore_ctrl();	
}