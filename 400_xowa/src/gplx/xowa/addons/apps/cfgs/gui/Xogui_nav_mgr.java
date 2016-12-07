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
package gplx.xowa.addons.apps.cfgs.gui; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import gplx.langs.mustaches.*;
public class Xogui_nav_mgr implements Mustache_doc_itm {		
	public Xogui_nav_itm[] Itms() {return itms;} private final    Xogui_nav_itm[] itms;
	public Xogui_nav_mgr(Xogui_nav_itm[] itms) {
		this.itms = itms;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String k) {
		if		(String_.Eq(k, "itms"))		return itms;
		return Mustache_doc_itm_.Ary__empty;
	}
}
