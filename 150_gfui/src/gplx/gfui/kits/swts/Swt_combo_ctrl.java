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
import gplx.*;
import gplx.core.envs.Op_sys;
import gplx.core.envs.Op_sys_;
import gplx.core.threads.Thread_adp_;
import gplx.gfui.controls.gxws.GxwComboBox;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.draws.ColorAdp;
import gplx.gfui.controls.standards.GfuiComboBox;
import gplx.gfui.kits.core.Swt_kit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class Swt_combo_ctrl extends Swt_text_w_border implements GxwElem, GxwComboBox, Swt_control, Gfo_evt_mgr_owner {	// REF: https://www.eclipse.org/forums/index.php/t/351029/; http://git.eclipse.org/c/platform/eclipse.platform.swt.git/tree/examples/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet320.java
	private final Text swt_text;
	private final Swt_combo_list list;
	public Swt_combo_ctrl(Swt_kit kit, Swt_control owner, Color color, Keyval_hash ctorArgs) {
		super(kit, owner, color, new Keyval_hash());
		Display display = owner.Under_control().getDisplay();
		Shell shell = owner.Under_control().getShell();
		this.swt_text = super.Under_text();
		this.list = new Swt_combo_list(display, shell, this);

		swt_text.addListener(SWT.KeyDown, new Swt_combo_text__key_down(list));
		swt_text.addListener(SWT.MouseUp, new Swt_combo_text__mouse_up(list));

		Table swt_list = list.Under_table_as_swt();
		swt_list.addListener(SWT.DefaultSelection, new Swt_combo_list__default_selection(this, list));
		swt_list.addListener(SWT.KeyDown, new Swt_combo_list__default_selection(this, list));
		swt_list.addListener(SWT.MouseDown, new Swt_combo_list__mouse_down(this, list));

		// listeners to hide list-box when focus is transfered, shell is moved, text-box is clicked, etc 
		Swt_combo_ctrl__list_hider_cmd list_hide_cmd = new Swt_combo_ctrl__list_hider_cmd(list);
		Swt_combo_ctrl__focus_out focus_out_lnr = new Swt_combo_ctrl__focus_out(display, this, list, list_hide_cmd);
		swt_text.addListener(SWT.FocusOut, focus_out_lnr);
		swt_list.addListener(SWT.FocusOut, focus_out_lnr);
		swt_text.addListener(SWT.FocusIn, new Swt_combo_text__focus_in(this, list));
		swt_list.addListener(SWT.FocusIn, new Swt_combo_ctrl__focus_in(display, list, list_hide_cmd));		
		shell.addListener(SWT.Move, new Swt_combo_shell__move(list));
	}
	@Override public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr; public void Evt_mgr_(Gfo_evt_mgr v) {ev_mgr = v;}
	@Override public Object SelectedItm() {return null;}
	@Override public void SelectedItm_set(Object v) {}
	@Override public void Sel_(int bgn, int end) 					{swt_text.setSelection(new Point(bgn, end));}
	@Override public String[] DataSource_as_str_ary() 				{return list.Items_str_ary();}
	@Override public void DataSource_set(Object... ary) 			{list.Items_((String[])ary);}
	@Override public String Text_fallback() 						{return text_fallback;} private String text_fallback = "";// preserve original-text when using cursor keys in list-box
	@Override public void Text_fallback_(String v) 					{text_fallback = v;}
	public void Text_fallback_restore() {
		if (String_.Len_eq_0(text_fallback)) return; // handle escape pressed after dropdown is visible, but down / up not pressed
		this.Text_(text_fallback);
		this.text_fallback = "";
	}
	@Override public void Items__update(String[] ary) 				{list.Items_(ary);}
	@Override public void Items__size_to_fit(int count) 			{list.Resize_shell(count);}
	@Override public int List_sel_idx() 							{return list.Sel_idx();}
	@Override public void List_sel_idx_(int v) 						{list.Sel_idx_(v);}
	@Override public boolean List_visible() 						{return list.Visible();}
	@Override public void List_visible_(boolean v) 					{list.Visible_(v);}
	
	@Override public void Items__visible_rows_(int v) 				{list.Visible_rows = v;}
	@Override public void Items__jump_len_(int v) 					{list.Jump_len = v;}
	public Rectangle Bounds() {return super.Under_control().getBounds();}
	public String Text() {return swt_text.getText();} public void Text_(String v) {swt_text.setText(v);}
	public void Sel_all() {
		String text_text = swt_text.getText();
		this.Sel_(0, String_.Len(text_text));
	}
	public void Items__backcolor_(ColorAdp v) {list.Under_table_as_swt().setBackground(kit.New_color(v));}
	public void Items__forecolor_(ColorAdp v) {list.Under_table_as_swt().setForeground(kit.New_color(v));}
}
class Swt_combo_list {
	private final Display display; private final Shell owner_shell;
	private final Swt_combo_ctrl ctrl;
	private final Shell shell;
	private final Table table;
	private int list_len;
	public int Jump_len = 5;
	public int Visible_rows = 10;
	public Swt_combo_list(Display display, Shell owner_shell, Swt_combo_ctrl ctrl) {
		this.display = display; this.owner_shell = owner_shell; this.ctrl = ctrl;
		this.shell = new Shell(display, SWT.ON_TOP);
		shell.setLayout(new FillLayout());
		this.table = new Table(shell, SWT.SINGLE);
	}
	public Table Under_table_as_swt() {return table;}
	public int Items_len() {return table.getItemCount();}
	public TableItem[] Items() {return table.getItems();}
	public TableItem Sel_itm(int i) {return table.getSelection()[i];}
	public void Items_text_(int idx, String v) {table.getItem(idx).setText(v);}
	public String[] Items_str_ary() {return items_str_ary;} private String[] items_str_ary = String_.Ary_empty;
	public void Items_(String[] new_ary) {
		int new_len = new_ary.length;
		if (new_len == 0) Visible_(Bool_.N);	// if new_ary is empty, then hide list box; else, brief flicker as items are removed 

		// remove all cur-items that are no longer needed b/c new_ary is smaller
		int cur_len = list_len;
		for (int i = new_len; i < cur_len; ++i) {
			table.remove(new_len);
		}

		// update new_ary;
		for (int i = 0; i < new_len; ++i) {
			TableItem item = null;
			if (i < cur_len)	// existing item; reuse it
				item = table.getItem(i);
			else				// no ite; create a new one
				item = new TableItem(table, SWT.NONE);
			String cur_text = item.getText();
			String new_text = new_ary[i];
			if (!String_.Eq(cur_text, new_text) && new_text != null) {
				item.setText(new_text);
			}
		}
		this.list_len = new_len;
		
		// resize list-shell to # of items
		int max_len = this.Visible_rows;
		if (	new_len == cur_len						// do not resize if same #
			|| 	new_len > max_len && cur_len > max_len	// do not resize if new_len and cur_len are both off-screen
			) {}
		this.items_str_ary = new_ary;
//		else
//			Resize_shell(list_len);
	}
	public int Sel_idx() {return table.getSelectionIndex();}
	public void Sel_idx_(int v) {table.setSelection(v);}
	public void Sel_idx_nudge(boolean fwd) 	{Sel_idx_adj(fwd, 1);}
	public void Sel_idx_jump(boolean fwd) 	{Sel_idx_adj(fwd, Jump_len);}
	private void Sel_idx_adj(boolean fwd, int adj) {
		if (!Visible()) Visible_(Bool_.Y);	// these are called by cursor keys; always make visible
		int cur_idx = table.getSelectionIndex();
		int new_idx = cur_idx;
		int idx_n = list_len - 1;
		if (fwd) {
			if (cur_idx == idx_n)
				new_idx = -1;
			else if (cur_idx == -1)
				new_idx = 0;
			else {
				new_idx = cur_idx + adj;
				if (new_idx >= idx_n)
					new_idx = idx_n;
			}
		}
		else {
			if (cur_idx == 0)
				new_idx = -1;
			else if (cur_idx == -1)
				new_idx = idx_n;
			else {
				new_idx = cur_idx - adj;
				if (new_idx < 0)
					new_idx = 0;
			}
		}
		Sel_idx_by_key(new_idx);
	}
	public void Sel_idx_by_key(int v) {
		table.setSelection(v);
		if (v == -1) {	// nothing selected; restore orig
			ctrl.Text_fallback_restore();
		} else {		// something selected; transfer selected item to text
			String sel_text = table.getItem(v).getText(); 
			ctrl.Text_(sel_text);
			ctrl.Sel_(sel_text.length(), sel_text.length());
		}
		Gfo_evt_mgr_.Pub(ctrl, GfuiComboBox.Evt__selected_changed);
	} 
	public boolean Visible() {return shell.isVisible();}
	public void Visible_(boolean v) {
		if (v && list_len == 0) return;	// never show if 0 items; occurs when users presses alt+down or down when in combo-box 
		shell.setVisible(v);
	}
	public void Resize_shell(int len) {
		Rectangle text_bounds = display.map(owner_shell, null, ctrl.Bounds());
		int adj = Op_sys.Cur().Tid() == Op_sys.Lnx.Tid() ? 9 : 2;	// NOTE: magic-numbers from gnosygnu's Windows-7, OS-X and openSUSE; Linux # is not correct for different number of items, but looks acceptable for 25;
		int max_len = this.Visible_rows;
		int height = (table.getItemHeight() * (len > max_len ? max_len : len)) + adj;
		if (height != shell.getSize().y) // only resize if height is different
			shell.setBounds(text_bounds.x, text_bounds.y + text_bounds.height - 1, text_bounds.width, height);		
	}
}
class Swt_combo_text__key_down implements Listener {			// for list-box, highlight item or toggle visiblility based on keys
	private final Swt_combo_list list;
	public Swt_combo_text__key_down(Swt_combo_list list) {
		this.list = list;
	}
	@Override public void handleEvent(Event event) {
		try {
			int mask = event.stateMask;
			boolean no_modifier = false;
			boolean ctrl_modifier = false;
			boolean alt_modifier = false;
			if 		((mask & SWT.ALT) 	== SWT.ALT) {alt_modifier = true;}
			else if	((mask & SWT.CTRL)	== SWT.CTRL){ctrl_modifier = true;}
			else if ((mask & SWT.SHIFT)	== SWT.SHIFT){}
			else
				no_modifier = true;
			switch (event.keyCode) {
				case SWT.ARROW_DOWN:
					if (event.stateMask == SWT.ALT) {
						list.Visible_(true);
					} else {
						list.Sel_idx_nudge(Bool_.Y);
					}
					event.doit = false;
					break;
				case SWT.ARROW_UP:
					if (event.stateMask == SWT.ALT) {
						list.Visible_(false);
					} else {
						list.Sel_idx_nudge(Bool_.N);
					}
					event.doit = false;
					break;
				case SWT.PAGE_DOWN:
					if (no_modifier) {
						list.Sel_idx_jump(Bool_.Y);
						event.doit = false;
					}
					else if (ctrl_modifier || alt_modifier) {
						list.Visible_(Bool_.N);
						event.doit = false;
					}					
					break;
				case SWT.PAGE_UP:
					if (no_modifier) {
						list.Sel_idx_jump(Bool_.N);
						event.doit = false;
					}
					else if (ctrl_modifier || alt_modifier) {
						list.Visible_(Bool_.N);
						event.doit = false;
					}					
					break;
				case SWT.CR:
					if (list.Visible() && list.Sel_idx() != -1) {
						list.Visible_(false);
					}
					break;
				case SWT.ESC:
					if (list.Visible()) {	// NOTE: must check if list is visible, else will need to press escape twice to restore url; DATE:2017-02-15 
						list.Sel_idx_by_key(-1);
						list.Visible_(false);
					}
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class Swt_combo_text__mouse_up implements Listener {			// if list-box is visible, left-click on text-box should hide list-box; note that left-click does not fire focus-in event; EX: focus html-box; click on url-box; focus-in event not fired
	private final Swt_combo_list list;
	public Swt_combo_text__mouse_up(Swt_combo_list list) {
		this.list = list;
	}
	@Override public void handleEvent(Event event) {
        if (event.button == 1) {	// left-click
        	if (list.Visible()) {
        		list.Visible_(false);
        	}
        }
	}
}
class Swt_combo_list__default_selection implements Listener {	// transfer list-box's selected to text-box
	private final Swt_combo_ctrl ctrl; private final Swt_combo_list list;
	public Swt_combo_list__default_selection(Swt_combo_ctrl ctrl, Swt_combo_list list) {
		this.ctrl = ctrl; this.list = list;
	}
	@Override public void handleEvent(Event arg0) {		
		ctrl.Text_(list.Sel_itm(0).getText());
		list.Visible_(false);
	}	
}
class Swt_combo_list__key_down implements Listener {			// hide list-box if escape pressed
	private final Swt_combo_ctrl ctrl; private final Swt_combo_list list;
	public Swt_combo_list__key_down(Swt_combo_ctrl ctrl, Swt_combo_list list) {
		this.ctrl = ctrl;
		this.list = list;
	}
	@Override public void handleEvent(Event event) {		
		if (event.keyCode == SWT.ESC) {
			ctrl.Text_fallback_restore();
			list.Visible_(false);
		}
	}
}
class Swt_combo_list__mouse_down implements Listener {			// left-click on list-box should transfer item to text-box and publish "accepted" event
	private final Swt_combo_ctrl ctrl;
	private final Swt_combo_list list;
	public Swt_combo_list__mouse_down(Swt_combo_ctrl ctrl, Swt_combo_list list) {
		this.ctrl = ctrl;
		this.list = list;
	}
	@Override public void handleEvent(Event event) {
		if (event.button == 1) {	// left-click
			ctrl.Text_(list.Sel_itm(0).getText());
			Gfo_evt_mgr_.Pub(ctrl, GfuiComboBox.Evt__selected_changed);
			Gfo_evt_mgr_.Pub(ctrl, GfuiComboBox.Evt__selected_accepted);
		}
	}
}
class Swt_combo_ctrl__focus_out implements Listener {			// run hide-cmd when list-box when text-box / list-box loses focus
	private final Display display;
	private final Control text_as_swt, list_as_swt;
	private final Swt_combo_ctrl__list_hider_cmd list_hide_cmd;
	public Swt_combo_ctrl__focus_out(Display display, Swt_combo_ctrl ctrl, Swt_combo_list list, Swt_combo_ctrl__list_hider_cmd list_hide_cmd) {
		this.list_hide_cmd = list_hide_cmd;
		this.display = display;
		this.text_as_swt = ctrl.Under_text();
		this.list_as_swt = list.Under_table_as_swt();
	}
	@Override public void handleEvent(Event arg0) {
		if (display.isDisposed()) return;
		Control control = display.getFocusControl();
		if (control == null || control == text_as_swt || control == list_as_swt) {
			list_hide_cmd.Active = true;
			display.asyncExec(list_hide_cmd);
		}
	}
}
class Swt_combo_ctrl__focus_in implements Listener {		// cancel hide-cmd if list-box gains focus
	private final Display display;
	private final Swt_combo_ctrl__list_hider_cmd list_hide_cmd;
	private final Control list_as_swt;
	
	public Swt_combo_ctrl__focus_in(Display display, Swt_combo_list list, Swt_combo_ctrl__list_hider_cmd list_hide_cmd) {
		this.display = display;
		this.list_as_swt = list.Under_table_as_swt();
		this.list_hide_cmd = list_hide_cmd;
	}
	@Override public void handleEvent(Event arg0) {
		if (display.isDisposed()) return;
		Control control = display.getFocusControl();
		if (control == list_as_swt)
			list_hide_cmd.Active = false;
	}
}
class Swt_combo_text__focus_in implements Listener {		// hide list-box when text-box is focused
	private final Swt_combo_ctrl ctrl; private final Swt_combo_list list;
	public Swt_combo_text__focus_in(Swt_combo_ctrl ctrl, Swt_combo_list list) {
		this.ctrl = ctrl; this.list = list;
	}
	@Override public void handleEvent(Event arg0) {
		if (list.Visible())
			list.Visible_(false);
		else
			ctrl.Sel_all();
	}
}
class Swt_combo_shell__move implements Listener {			// hide list-box when shell is moved
	private final Swt_combo_list list;
	public Swt_combo_shell__move(Swt_combo_list list) {this.list = list;}
	@Override public void handleEvent(Event arg0) {
		list.Visible_(false);
	}
}
class Swt_combo_ctrl__list_hider_cmd implements Runnable {	// hide list-box; can be "canceled"
	private final Swt_combo_list list;
	public boolean Active = true;
	public Swt_combo_ctrl__list_hider_cmd(Swt_combo_list list) {
		this.list = list;
	}
	@Override public void run() {
		if (Active)
			list.Visible_(false);
	}
}
