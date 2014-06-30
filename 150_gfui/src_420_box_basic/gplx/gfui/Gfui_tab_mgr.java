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
public class Gfui_tab_mgr extends GfuiElemBase {
	public void Under_tab_mgr_(Gxw_tab_mgr v) {under = v;} private Gxw_tab_mgr under;
	public ColorAdp Btns_selected_color() {return under.Btns_selected_color();} public void Btns_selected_color_(ColorAdp v) {under.Btns_selected_color_(v);}
	public ColorAdp Btns_unselected_color() {return under.Btns_unselected_color();} public void Btns_unselected_color_(ColorAdp v) {under.Btns_unselected_color_(v);}
	public Gfui_tab_itm Tabs_add(Gfui_tab_itm_data tab_data) {
		Gxw_tab_itm tab_itm = under.Tabs_add(tab_data);
		return Gfui_tab_itm.kit_(this.Kit(), tab_data.Key(), tab_itm, KeyValHash.Empty);
	}
	public int Btns_height() {return under.Btns_height();} public void Btns_height_(int v) {under.Btns_height_(v);}
	public boolean Btns_place_on_top() {return under.Btns_place_on_top();} public void Btns_place_on_top_(boolean v) {under.Btns_place_on_top_(v);}
	public boolean Btns_curved() {return under.Btns_curved();} public void Btns_curved_(boolean v) {under.Btns_curved_(v);}
	public boolean Btns_close_visible_() {return under.Btns_close_visible();} public void Btns_close_visible_(boolean v) {under.Btns_close_visible_(v);}
	public boolean Btns_unselected_close_visible() {return under.Btns_unselected_close_visible();} public void Btns_unselected_close_visible_(boolean v) {under.Btns_unselected_close_visible_(v);}
	public void Tabs_select_by_idx(int idx) {under.Tabs_select_by_idx(idx);}
	public void Tabs_close_by_idx(int idx) {under.Tabs_close_by_idx(idx);}
	public void Tabs_switch(int src, int trg) {under.Tabs_switch(src, trg);}
	public static Gfui_tab_mgr kit_(Gfui_kit kit, String key, Gxw_tab_mgr under, KeyValHash ctor_args) {
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
