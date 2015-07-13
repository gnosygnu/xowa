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
public class TabPnlItm {
	public String Key() {return key;} private String key;
	public String Name() {return name;}
	public TabPnlItm Name_(String val) {
		name = val;
		TabBoxEvt_nameChange.Send(mgr, this);
		return this;
	}	String name;
	public int Idx() {return idx;}
	@gplx.Internal protected TabPnlItm Idx_(int val) {
		idx = val;
		return this;
	}	int idx;
	TabBoxMgr mgr;
	public static TabPnlItm new_(TabBoxMgr mgr, String key) {
		TabPnlItm rv = new TabPnlItm();
		rv.mgr = mgr; rv.key = key;
		return rv;
	}	TabPnlItm() {}
}
