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
public class Gfui_tab_itm extends GfuiElemBase {
	private Gxw_tab_itm under;
	public String Tab_name() {return under.Tab_name();} public void Tab_name_(String v) {under.Tab_name_(v);}
	public String Tab_tip_text() {return under.Tab_tip_text();} public void Tab_tip_text_(String v) {under.Tab_tip_text_(v);}
	public void Subs_add(GfuiElem elem) {under.Subs_add(elem);}
	public static Gfui_tab_itm kit_(Gfui_kit kit, String key, Gxw_tab_itm under, KeyValHash ctor_args) {
		Gfui_tab_itm rv = new Gfui_tab_itm();
		// rv.ctor_kit_GfuiElemBase(kit, key, (GxwElem)under, ctor_args);	// causes swt_tab_itm to break, since it's not a real Swt Control
		rv.under = under;
		return rv;
	}
}
