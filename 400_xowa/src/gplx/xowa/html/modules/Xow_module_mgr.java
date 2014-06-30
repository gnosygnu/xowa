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
package gplx.xowa.html.modules; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*;
import gplx.xowa.html.modules.popups.*;
public class Xow_module_mgr {
	public Xow_module_mgr(Xow_wiki wiki) {this.popup_mgr = new Xow_popup_mgr(wiki);}
	public void Init_by_wiki(Xow_wiki wiki) {
		popup_mgr.Init_by_wiki(wiki);
	}
	public Xow_popup_mgr Popup_mgr() {return popup_mgr;} private Xow_popup_mgr popup_mgr;
}
