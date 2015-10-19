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
package gplx.xowa.wikis.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.guis.views.*;
public class Xopg_tab_data {
	public Xog_tab_itm	Tab()				{return tab;}			public void Tab_(Xog_tab_itm v) {this.tab = v;} private Xog_tab_itm tab;
	public boolean			Cancel_show()		{return cancel_show;}	public void Cancel_show_y_()	{this.cancel_show = true;} private boolean cancel_show;	// used for Special:Search
	public Xog_tab_close_mgr Close_mgr() {return close_mgr;} private final Xog_tab_close_mgr close_mgr = new Xog_tab_close_mgr();
	public void Clear() {
		this.cancel_show = false;
		this.tab = null;
		close_mgr.Clear();
	}
}
