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
public interface Gxw_tab_mgr extends GxwElem {
	ColorAdp Btns_selected_color(); void Btns_selected_color_(ColorAdp v);
	ColorAdp Btns_unselected_color(); void Btns_unselected_color_(ColorAdp v);
	int Btns_height(); void Btns_height_(int v);
	boolean Btns_place_on_top(); void Btns_place_on_top_(boolean v);
	boolean Btns_curved(); void Btns_curved_(boolean v);
	boolean Btns_close_visible(); void Btns_close_visible_(boolean v);
	boolean Btns_unselected_close_visible(); void Btns_unselected_close_visible_(boolean v);
	Gxw_tab_itm Tabs_add(Gfui_tab_itm_data tab_data);
	void Tabs_select_by_idx(int i);
	void Tabs_close_by_idx(int i);
	void Tabs_switch(int src, int trg);
}
