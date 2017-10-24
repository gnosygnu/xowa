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
import gplx.Gfo_evt_mgr;
import gplx.Gfo_evt_mgr_owner;
import gplx.Gfo_evt_mgr_;
import gplx.GfoMsg;
import gplx.GfsCtx;
import gplx.Keyval_hash;
import gplx.String_;
import gplx.Tfds;
import gplx.core.threads.Thread_adp;
import gplx.core.threads.Thread_adp_;
import gplx.gfui.controls.gxws.GxwCbkHost;
import gplx.gfui.controls.gxws.GxwComboBox;
import gplx.gfui.controls.gxws.GxwCore_base;
import gplx.gfui.controls.gxws.GxwElem;
import gplx.gfui.controls.standards.GfuiComboBox;
import gplx.gfui.draws.ColorAdp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

class Swt_combo implements GxwElem, GxwComboBox, Swt_control, Gfo_evt_mgr_owner {
	private final Combo combo;
	public Swt_combo(Swt_control owner, Keyval_hash ctorArgs) {
		combo = new Combo(owner.Under_composite(), SWT.DROP_DOWN);
		core = new Swt_core__basic(combo);
		combo.addKeyListener(new Swt_lnr_key(this));
		combo.addMouseListener(new Swt_lnr_mouse(this));
		combo.addSelectionListener(new Swt_combo__selection_listener(this));
	}
	public ColorAdp Border_color() {return border_color;} public void Border_color_(ColorAdp v) {border_color = v;} private ColorAdp border_color;
	@Override public Gfo_evt_mgr Evt_mgr() {return ev_mgr;} private Gfo_evt_mgr ev_mgr; public void Evt_mgr_(Gfo_evt_mgr v) {ev_mgr = v;}
	@Override public Control Under_control() {return combo;}
	@Override public Control Under_menu_control() {return combo;}
	@Override public String TextVal() {return combo.getText();} @Override public void TextVal_set(String v) {combo.setText(v);}
	@Override public GxwCore_base Core() {return core;} GxwCore_base core;
	@Override public GxwCbkHost Host() {return host;} @Override public void Host_set(GxwCbkHost host) {this.host = host;} GxwCbkHost host;
	@Override public Composite Under_composite() {return null;}
	@Override public void EnableDoubleBuffering() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return null;}
	@Override public Object SelectedItm() {return null;}
	@Override public int SelBgn() {return combo.getSelection().x;} @Override public void SelBgn_set(int v) {combo.setSelection(new Point(combo.getSelection().y, v));}
	@Override public int SelLen() {return combo.getSelection().y;} @Override public void SelLen_set(int v) {combo.setSelection(new Point(v, combo.getSelection().x));}
	@Override public void Sel_(int bgn, int end) {combo.setSelection(new Point(bgn, end));}
	@Override public void SelectedItm_set(Object v) {}
	@Override public String[] DataSource_as_str_ary() {return String_.Ary_empty;}
	@Override public void DataSource_set(Object... ary) {combo.setItems((String[])ary);}
	@Override public String Text_fallback() {return "";} @Override public void Text_fallback_(String v) {}
	@Override public int List_sel_idx() {return -1;} @Override public void List_sel_idx_(int v) {}
	@Override public void Items__update(String[] ary) {}
	@Override public void Items__size_to_fit(int count) {}
	@Override public void Items__visible_rows_(int v) {}
	@Override public void Items__jump_len_(int v) {}
	@Override public void Margins_set(int left, int top, int right, int bot) {}
	public void Items__backcolor_(ColorAdp v) {}
	public void Items__forecolor_(ColorAdp v) {}
//	@Override public void DataSource_update(Object... ary) {
//		String[] src = (String[])ary;
//		int trg_len = combo.getItems().length;
//		int src_len = src.length;
//		for (int i = 0; i < trg_len; ++i) {
//			combo.setItem(i, i < src_len ? src[i] : "");
//		}
//	}
	@Override public boolean List_visible() {return combo.getListVisible();}
	@Override public void List_visible_(boolean v) {
		String prv_text = combo.getText();
		combo.setListVisible(v);
		String cur_text = combo.getText();
		while (!String_.Eq(cur_text, prv_text)) {	// NOTE: setting setListVisible to true may cause text to grab item from dropDown list; try to reset to original value; DATE:2016-03-14 
			Thread_adp_.Sleep(1);
			combo.setText(prv_text);
			cur_text = combo.getText();
		}
		int text_len = prv_text == null ? 0 : prv_text.length();
		combo.setSelection(new Point(text_len, text_len));
	}
}
class Swt_combo__selection_listener implements SelectionListener {
	private final Swt_combo combo;
	public Swt_combo__selection_listener(Swt_combo combo) {this.combo = combo;}
	@Override public void widgetSelected(SelectionEvent arg0) {
		Gfo_evt_mgr_.Pub(combo, GfuiComboBox.Evt__selected_changed);
	}	
	@Override public void widgetDefaultSelected(SelectionEvent arg0) {}
}
