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
public class TabBoxMgr implements GfoEvMgrOwner {
	public GfoEvMgr EvMgr() {if (evMgr == null) evMgr = GfoEvMgr.new_(this); return evMgr;} GfoEvMgr evMgr;
	public int Count() {return itms.Count();}
	public TabPnlItm Fetch(String k) {return (TabPnlItm)itms.Fetch(k);}
	public TabPnlItm FetchAt(int i) {return (TabPnlItm)itms.FetchAt(i);}
	public TabPnlItm CurTab() {return curTab;} TabPnlItm curTab;
	public TabPnlItm Add(String key, String name) {
		TabPnlItm itm = TabPnlItm.new_(this, key).Name_(name).Idx_(itms.Count());
		itms.Add(itm.Key(), itm);
		return itm;
	}
	public void DelAt(int i) {
		boolean isCur = i == curTab.Idx(), isLast = i == itms.Count() - 1;
		TabPnlItm itm = this.FetchAt(i);
		itms.Del(itm.Key());
		this.Reorder(i);
		if (isCur) {
			curTab = null; // must null out curTab since it has been deleted from itms; TODO: should raise Deleted event to delete btn,pnl,view; wait for rewrite of view
			if (isLast) i--; // last was dropped; select prev; otherwise re-select idx (effectively, whatever tab drops into slot)
			if (i >=0)
				this.Select(i);
		}
	}
	public void Select(int i) {Select((TabPnlItm)itms.FetchAt(i));}
	@gplx.Internal protected void MoveTo(int src, int trg) {itms.MoveTo(src, trg);}
	@gplx.Internal protected void Reorder(int bgn) {
		for (int i = bgn; i < itms.Count(); i++) {
			TabPnlItm itm = (TabPnlItm)itms.FetchAt(i);
			itm.Idx_(i);
		}
	}
	@gplx.Internal protected void Select(TabPnlItm newTab) {
		TabPnlItm oldTab = curTab;
		curTab = newTab;
		TabBoxEvt_tabSelect.Send(this, oldTab, newTab);
	}		
	OrderedHash itms = OrderedHash_.new_();
	@gplx.Internal protected static TabBoxMgr new_() {return new TabBoxMgr();} TabBoxMgr() {}
}
