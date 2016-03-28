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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
class Swt_text implements GxwTextFld, Swt_control {
	private Text text_box;
	public Swt_text(Swt_control owner_control, Keyval_hash ctorArgs) {
		int text_box_args = ctorArgs.Has(GfuiTextBox_.Ctor_Memo)
			? SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
			: SWT.NONE
			;
		text_box = new Text(owner_control.Under_composite(), text_box_args); 
		core = new Swt_core_cmds(text_box);
		text_box.addKeyListener(new Swt_lnr_key(this));
		text_box.addMouseListener(new Swt_lnr_mouse(this));
	}
	@Override public Control Under_control() {return text_box;}
	@Override public Composite Under_composite() {return null;}
	@Override public Control Under_menu_control() {return text_box;}
	@Override public int SelBgn() {return text_box.getCaretPosition();} 	@Override public void SelBgn_set(int v) {text_box.setSelection(v);}
	@Override public int SelLen() {return text_box.getSelectionCount();} @Override public void SelLen_set(int v) {text_box.setSelection(this.SelBgn(), this.SelBgn() + v);}	
	@Override public String TextVal() {return text_box.getText();} @Override public void TextVal_set(String v) {text_box.setText(v);}
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return null;}
	public void Margins_set(int left, int top, int right, int bot) {}
	@Override public boolean Border_on() {return false;} @Override public void Border_on_(boolean v) {} // SWT_TODO:borderWidth doesn't seem mutable
	@Override public void CreateControlIfNeeded() {}
	@Override public boolean OverrideTabKey() {return false;} @Override public void OverrideTabKey_(boolean v) {}
}
