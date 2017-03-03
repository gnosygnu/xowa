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
import gplx.core.threads.Thread_adp_;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.Gxw_tab_itm;
import gplx.gfui.controls.gxws.Gxw_tab_mgr;
import gplx.gfui.controls.standards.Gfui_tab_itm_data;
import gplx.gfui.controls.standards.Gfui_tab_mgr;
import gplx.gfui.draws.*;
import gplx.gfui.kits.core.GfuiInvkCmd;
import gplx.gfui.kits.core.Swt_kit;

public class Swt_tab_mgr implements Gxw_tab_mgr, Swt_control, FocusListener, Gfo_evt_mgr_owner {
	private GfuiInvkCmd cmd_sync;
//	private GfuiInvkCmd cmd_async;	// NOTE: async needed for some actions like responding to key_down and calling .setSelection; else app hangs; DATE:2014-04-30 
	public Swt_tab_mgr(Swt_kit kit, Swt_control owner_control, Keyval_hash ctorArgs) {
		this.kit = kit;
		tab_folder = new CTabFolder(owner_control.Under_composite(), SWT.BORDER);
		tab_folder.setBorderVisible(false);
		tab_folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		tab_folder.setSimple(true);
		tab_folder.addListener(SWT.Selection, new Swt_tab_mgr_lnr_selection(this));
		tab_folder.addKeyListener(new Swt_lnr_key(this));

		new Swt_tab_mgr_lnr_drag_drop(this, tab_folder);
		tab_folder.addCTabFolder2Listener(new Swt_tab_mgr_lnr_close(this));
		core = new Swt_core__basic(tab_folder);
//		cmd_async = kit.New_cmd_async(this);
		cmd_sync = kit.New_cmd_sync(this);
	}
	public Swt_kit Kit() {return kit;} private Swt_kit kit;
	public CTabFolder Under_ctabFolder() {return tab_folder;}
	@Override public Control Under_control() {return tab_folder;} private CTabFolder tab_folder;
	@Override public Composite Under_composite() {return tab_folder;}
	@Override public Control Under_menu_control() {return tab_folder;}
	public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr;
	public void Evt_mgr_(Gfo_evt_mgr v) {ev_mgr = v;}
	
	public ColorAdp     Btns_selected_foreground() {return btns_selected_foreground;} private ColorAdp btns_selected_foreground;
	public void         Btns_selected_foreground_(ColorAdp v) {btns_selected_foreground = v; tab_folder.setSelectionForeground(kit.New_color(v));}
	public ColorAdp     Btns_selected_background() {return btns_selected_background;} private ColorAdp btns_selected_background;
	public void         Btns_selected_background_(ColorAdp v) {btns_selected_background = v; tab_folder.setSelectionBackground(kit.New_color(v));}
	public ColorAdp     Btns_unselected_foreground() {return btns_unselected_foreground;} private ColorAdp btns_unselected_foreground;
	public void         Btns_unselected_foreground_(ColorAdp v) {btns_unselected_foreground = v; tab_folder.setForeground(kit.New_color(v));}
	public ColorAdp     Btns_unselected_background() {return btns_unselected_background;} private ColorAdp btns_unselected_background;
	public void         Btns_unselected_background_(ColorAdp v) {btns_unselected_background = v; tab_folder.setBackground(kit.New_color(v));}
	public boolean      Btns_curved() {return tab_folder.getSimple();}
	public void         Btns_curved_(boolean v) {tab_folder.setSimple(!v);}
	public boolean      Btns_place_on_top() {return tab_folder.getTabPosition() == SWT.TOP;} 
	public void         Btns_place_on_top_(boolean v) {tab_folder.setTabPosition(v ? SWT.TOP : SWT.BOTTOM); tab_folder.layout();}
	public int          Btns_height() {return tab_folder.getTabHeight();}
	public void         Btns_height_(int v) {tab_folder.setTabHeight(v); tab_folder.layout();}
	public boolean      Btns_close_visible() {return btns_close_visible;} private boolean btns_close_visible = true;
	public void         Btns_close_visible_(boolean v) {
		this.btns_close_visible = v;
		CTabItem[] itms = tab_folder.getItems();
		int len = itms.length;
		for (int i = 0; i < len; i++)
			itms[i].setShowClose(v);
	}
	public boolean      Btns_unselected_close_visible() {return tab_folder.getUnselectedCloseVisible();} 
	public void         Btns_unselected_close_visible_(boolean v) {tab_folder.setUnselectedCloseVisible(v);}
	
	@Override public Gxw_tab_itm Tabs_add(Gfui_tab_itm_data tab_data) {
		Swt_tab_itm rv = new Swt_tab_itm(this, kit, tab_folder, tab_data);
		rv.Under_CTabItem().setData(tab_data);
		CTabItem ctab_itm = rv.Under_CTabItem();
		ctab_itm.setShowClose(btns_close_visible);
		return rv;
	}
	@Override public void Tabs_close_by_idx(int i) {
		CTabItem itm = tab_folder.getItems()[i];
		Gfui_tab_itm_data tab_data = Get_tab_data(itm);
		CTabItem next_tab = Tabs_select_after_closing_itm(tab_data);	// NOTE: must calc next_tab before calling Pub_tab_closed; latter will recalc idx
		this.Tabs_select_by_itm(next_tab);								// NOTE: select tab before closing; DATE:2014-09-10
		Pub_tab_closed(tab_data.Key());									// NOTE: dispose does not call event for .close; must manually raise event;
		itm.dispose();
	}
	@Override public void Tabs_select_by_idx(int i) {
		if (i == Gfui_tab_itm_data.Idx_null) return;	// 0 tabs; return;
		msg_tabs_select_by_idx_swt.Clear();
		msg_tabs_select_by_idx_swt.Add("v", i);
		cmd_sync.Invk(GfsCtx.Instance, 0, Invk_tabs_select_by_idx_swt, msg_tabs_select_by_idx_swt);
	}	private GfoMsg msg_tabs_select_by_idx_swt = GfoMsg_.new_cast_(Invk_tabs_select_by_idx_swt);
	@Override public void Tabs_switch(int src, int trg) {Tabs_switch(tab_folder.getItem(src), tab_folder.getItem(trg));}
	public boolean Tabs_switch(CTabItem src_tab_itm, CTabItem trg_tab_itm) {
		Control temp_control = src_tab_itm.getControl();
		src_tab_itm.setControl(trg_tab_itm.getControl());
		trg_tab_itm.setControl(temp_control);
		
		String temp_str = src_tab_itm.getText();
		src_tab_itm.setText(trg_tab_itm.getText());
		trg_tab_itm.setText(temp_str);
		
		temp_str = src_tab_itm.getToolTipText();
		src_tab_itm.setToolTipText(trg_tab_itm.getToolTipText());
		trg_tab_itm.setToolTipText(temp_str);
		
		Gfui_tab_itm_data src_tab_data = Get_tab_data(src_tab_itm);
		Gfui_tab_itm_data trg_tab_data = Get_tab_data(trg_tab_itm);
		int src_tab_idx = src_tab_data.Idx(), trg_tab_idx = trg_tab_data.Idx();
		tab_folder.setSelection(trg_tab_itm);
		Gfo_evt_mgr_.Pub_vals(this, Gfui_tab_mgr.Evt_tab_switched, Keyval_.new_("src", src_tab_data.Key()), Keyval_.new_("trg", trg_tab_data.Key()));
		return src_tab_idx < trg_tab_idx;
	}
	public void Tabs_select_by_itm(CTabItem itm) {
		if (itm == null) return;	// 0 tabs; return;
		msg_tabs_select_by_itm_swt.Clear();
		msg_tabs_select_by_itm_swt.Add("v", itm);
		cmd_sync.Invk(GfsCtx.Instance, 0, Invk_tabs_select_by_itm_swt, msg_tabs_select_by_itm_swt);
	}	private GfoMsg msg_tabs_select_by_itm_swt = GfoMsg_.new_cast_(Invk_tabs_select_by_itm_swt);
	private void Tabs_select_by_idx_swt(int idx) {
		tab_folder.setSelection(idx);
		CTabItem itm = tab_folder.getItem(idx);
		Pub_tab_selected(Get_tab_key(itm));	// NOTE: setSelection does not call event for SWT.Selection; must manually raise event;
	}
	private void Tabs_select_by_itm_swt(CTabItem itm) {
		tab_folder.setSelection(itm);
		Pub_tab_selected(Get_tab_key(itm));	// NOTE: setSelection does not call event for SWT.Selection; must manually raise event;
	}
	public CTabItem Tabs_select_after_closing_itm(Gfui_tab_itm_data tab_data) {
		int next_idx = Gfui_tab_itm_data.Get_idx_after_closing(tab_data.Idx(), tab_folder.getItemCount());
		return next_idx == Gfui_tab_itm_data.Idx_null ? null : tab_folder.getItem(next_idx);
	}
	public void Pub_tab_selected(String key) {
		Gfo_evt_mgr_.Pub_obj(this, Gfui_tab_mgr.Evt_tab_selected, "key", key);		
	}
	public void Pub_tab_closed(String key) {
		Gfo_evt_mgr_.Pub_obj(this, Gfui_tab_mgr.Evt_tab_closed, "key", key);		
	}
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public String TextVal() {return "not implemented";}
	@Override public void TextVal_set(String v) {}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Invk_tabs_select_by_idx_swt))	Tabs_select_by_idx_swt(m.ReadInt("v"));
		else if	(String_.Eq(k, Invk_tabs_select_by_itm_swt))	Tabs_select_by_itm_swt((CTabItem)m.ReadObj("v", null));
		else												return Gfo_invk_.Rv_unhandled;
		return this;
	}
	@Override public void focusGained(FocusEvent arg0) {}
	@Override public void focusLost(FocusEvent arg0) {}
	private static String
	  Invk_tabs_select_by_idx_swt = "tabs_select_by_idx"
	, Invk_tabs_select_by_itm_swt = "tabs_select_by_itm"
	;
	public static Gfui_tab_itm_data Get_tab_data_by_obj(Object data) 	{return (Gfui_tab_itm_data)data;}
	public static Gfui_tab_itm_data Get_tab_data(CTabItem itm) 			{return (Gfui_tab_itm_data)itm.getData();}
	public static String Get_tab_key(CTabItem itm) 						{return ((Gfui_tab_itm_data)itm.getData()).Key();}
}
class Swt_tab_mgr_lnr_selection implements Listener {
	public Swt_tab_mgr_lnr_selection(Swt_tab_mgr tab_folder) {this.tab_folder = tab_folder;} private Swt_tab_mgr tab_folder;
	public void handleEvent(Event ev) {		
		tab_folder.Pub_tab_selected(Swt_tab_mgr.Get_tab_data_by_obj(ev.item.getData()).Key());
	}	
}
class Swt_tab_mgr_lnr_close extends CTabFolder2Adapter {	// handles close when tab x is clicked
	public Swt_tab_mgr_lnr_close(Swt_tab_mgr tab_folder) {this.tab_folder = tab_folder;} private Swt_tab_mgr tab_folder;
	@Override public void close(CTabFolderEvent ev) {
		Gfui_tab_itm_data tab_data = Swt_tab_mgr.Get_tab_data_by_obj(ev.item.getData());
		tab_folder.Tabs_close_by_idx(tab_data.Idx());
		ev.doit = false;	// mark ev handled, since Tabs_close_by_idx closes tab
	}
}
class Swt_tab_mgr_lnr_drag_drop implements Listener {
	private boolean dragging = false;
	private boolean drag_stop = false;
	private CTabItem drag_itm;
	private final Swt_tab_mgr tab_mgr; private final CTabFolder tab_folder; private final Display display;
	private Point prv_mouse;
	private Point dead_zone = null;
	public Swt_tab_mgr_lnr_drag_drop(Swt_tab_mgr tab_mgr, CTabFolder tab_folder) {
		this.tab_mgr = tab_mgr; this.tab_folder = tab_folder; this.display = tab_folder.getDisplay();
		tab_folder.addListener(SWT.DragDetect, this);
		tab_folder.addListener(SWT.MouseUp, this);
		tab_folder.addListener(SWT.MouseMove, this);
		tab_folder.addListener(SWT.MouseExit, this);
		tab_folder.addListener(SWT.MouseEnter, this);
	}
	private void Drag_drop_bgn(CTabItem itm) {
		dragging = true;
		drag_stop = false;
		drag_itm = itm;
		dead_zone = null;
	}
	private void Drag_drop_end() {
		tab_folder.setInsertMark(null, false);
		dragging = false;
		drag_stop = false;
		drag_itm = null;
	}
	public void handleEvent(Event e) {
		Point cur_mouse = e.type == SWT.DragDetect
			? tab_folder.toControl(display.getCursorLocation()) //see bug 43251
			: new Point(e.x, e.y)
			;
		switch (e.type) {
			case SWT.DragDetect: {
				CTabItem itm = tab_folder.getItem(cur_mouse);
				if (itm == null) return;
				this.Drag_drop_bgn(itm);
				break;
			}
			case SWT.MouseEnter:
				if (drag_stop) {
					dragging = e.button != 0;
					drag_stop = false;
				}
				break;
			case SWT.MouseExit:
				if (dragging)
					Drag_drop_end();
				break;
			case SWT.MouseUp: {
				if (!dragging) return;
				Drag_drop_end();
				break;
			}
			case SWT.MouseMove: {
				if (!dragging) return;
				CTabItem curr_itm = tab_folder.getItem(cur_mouse);
				if (curr_itm == null) {
					tab_folder.setInsertMark(null, false);
					return;
				}
				if (curr_itm == drag_itm) return;	// curr_itm is same as drag_itm; ignore
				int cur_mouse_x = cur_mouse.x;
				int prv_mouse_x = prv_mouse == null ? 0 : prv_mouse.x;
				prv_mouse = cur_mouse;				// set prv_mouse now b/c of early return below; note that cur_mouse_x and prv_mouse_x are cached above
				if (	dead_zone != null											// dead_zone exists
					&&	Int_.Between(cur_mouse_x, dead_zone.x, dead_zone.y)) {		// mouse is in dead_zone
					int drag_idx = Swt_tab_mgr.Get_tab_data(drag_itm).Idx();
					int curr_idx = Swt_tab_mgr.Get_tab_data(curr_itm).Idx();
					if 		(drag_idx > curr_idx && cur_mouse_x < prv_mouse_x) {}	// drag_itm is right of curr_itm, but mouse is moving  left (direction reversed); cancel
					else if (drag_idx < curr_idx && cur_mouse_x > prv_mouse_x) {}	// drag_itm is left  of curr_itm, but mouse is moving right (direction reversed); cancel
					else
						return;														// in dead zone, and still moving in original direction; return early								
				}
				boolean fwd = tab_mgr.Tabs_switch(drag_itm, curr_itm);
				drag_itm = curr_itm;
				Rectangle drag_rect = drag_itm.getBounds();
				dead_zone = Calc_dead_zone(fwd, cur_mouse_x, drag_rect.x, drag_rect.width);
				break;
			}
		}
	}
	public static Point Calc_dead_zone(boolean fwd, int mouse_x, int drag_l, int drag_w) {
		if (fwd) {														// drag_itm was moving fwd (moving right)
			if (mouse_x < drag_l) return new Point(mouse_x, drag_l);	// mouse_x < drag_l; create dead_zone until mouse_x reaches drag_l; occurs when moving drag is small_title and trg_itm is large_title 
		}
		else {															// drag_itm was moving bwd (moving left)
			int drag_r = drag_l + drag_w;
			if (mouse_x > drag_r) return new Point(drag_r, mouse_x);	// mouse_x > drag_r; create dead_zone until mouse_x reaches drag_r 
		}
		return null;
	}
}
