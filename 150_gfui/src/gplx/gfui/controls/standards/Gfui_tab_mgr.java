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
package gplx.gfui.controls.standards; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*;
public class Gfui_tab_mgr extends GfuiElemBase {
	public void Under_tab_mgr_(Gxw_tab_mgr v) {under = v;} private Gxw_tab_mgr under;
	public ColorAdp Btns_selected_background() {return under.Btns_selected_background();} public void Btns_selected_background_(ColorAdp v) {under.Btns_selected_background_(v);}
	public ColorAdp Btns_selected_foreground() {return under.Btns_selected_foreground();} public void Btns_selected_foreground_(ColorAdp v) {under.Btns_selected_foreground_(v);}
	public ColorAdp Btns_unselected_background() {return under.Btns_unselected_background();} public void Btns_unselected_background_(ColorAdp v) {under.Btns_unselected_background_(v);}
	public ColorAdp Btns_unselected_foreground() {return under.Btns_unselected_foreground();} public void Btns_unselected_foreground_(ColorAdp v) {under.Btns_unselected_foreground_(v);}
	public Gfui_tab_itm Tabs_add(Gfui_tab_itm_data tab_data) {
		Gxw_tab_itm tab_itm = under.Tabs_add(tab_data);
		return Gfui_tab_itm.kit_(this.Kit(), tab_data.Key(), tab_itm, new Keyval_hash());
	}
	public int Btns_height() {return under.Btns_height();} public void Btns_height_(int v) {under.Btns_height_(v);}
	public boolean Btns_place_on_top() {return under.Btns_place_on_top();} public void Btns_place_on_top_(boolean v) {under.Btns_place_on_top_(v);}
	public boolean Btns_curved() {return under.Btns_curved();} public void Btns_curved_(boolean v) {under.Btns_curved_(v);}
	public boolean Btns_close_visible_() {return under.Btns_close_visible();} public void Btns_close_visible_(boolean v) {under.Btns_close_visible_(v);}
	public boolean Btns_unselected_close_visible() {return under.Btns_unselected_close_visible();} public void Btns_unselected_close_visible_(boolean v) {under.Btns_unselected_close_visible_(v);}
	public void Tabs_select_by_idx(int idx) {under.Tabs_select_by_idx(idx);}
	public void Tabs_close_by_idx(int idx) {under.Tabs_close_by_idx(idx);}
	public void Tabs_switch(int src, int trg) {under.Tabs_switch(src, trg);}
	public static Gfui_tab_mgr kit_(Gfui_kit kit, String key, Gxw_tab_mgr under, Keyval_hash ctor_args) {
		Gfui_tab_mgr rv = new Gfui_tab_mgr();
		rv.ctor_kit_GfuiElemBase(kit, key, (GxwElem)under, ctor_args);
		rv.under = under;
		return rv;
	}
	public static final String
	  Evt_tab_selected	= "tab_selected"
	, Evt_tab_closed	= "tab_closed"
	, Evt_tab_switched	= "tab_switched"
	;
}
