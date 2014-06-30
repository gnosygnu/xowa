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
package gplx;
import gplx.lists.*;
public class GfoEvMgr {
	@gplx.Internal protected void AddSub(GfoEvMgrOwner pub, String pubEvt, GfoEvObj sub, String subPrc) {
		GfoEvLnk lnk = new GfoEvLnk(pub, pubEvt, sub, subPrc);
		if (subsRegy == null) subsRegy = OrderedHash_.new_();
		AddInList(subsRegy, pubEvt, lnk);
		sub.EvMgr().AddPub(pubEvt, lnk);
	}
	@gplx.Internal protected void Lnk(GfoEvMgrOwner pub) {
		if (pub.EvMgr().lnks == null) pub.EvMgr().lnks = ListAdp_.new_();
		pub.EvMgr().lnks.Add(this);
	}	ListAdp lnks;
	void AddInList(OrderedHash regy, String key, GfoEvLnk lnk) {
		GfoEvLnkList list = (GfoEvLnkList)regy.Fetch(key);
		if (list == null) {
			list = new GfoEvLnkList(key);
			regy.Add(key, list);
		}
		list.Add(lnk);
	}
	@gplx.Internal protected void AddPub(String pubEvt, GfoEvLnk lnk) {
		if (pubsRegy == null) pubsRegy = OrderedHash_.new_();
		AddInList(pubsRegy, pubEvt, lnk);
	}
	@gplx.Internal protected void Pub(GfsCtx ctx, String evt, GfoMsg m) {
		ctx.MsgSrc_(sender);
		GfoEvLnkList subs = subsRegy == null ? null : (GfoEvLnkList)subsRegy.Fetch(evt);
		if (subs != null) {
			for (int i = 0; i < subs.Count(); i++) {
				GfoEvLnk lnk = (GfoEvLnk)subs.FetchAt(i);
				lnk.Sub().Invk(ctx, 0, lnk.SubPrc(), m);	// NOTE: itm.Key() needed for Subscribe_diff()
			}
		}
		if (lnks != null) {
			for (int i = 0; i < lnks.Count(); i++) {
				GfoEvMgr lnk = (GfoEvMgr)lnks.FetchAt(i);
				lnk.Pub(ctx, evt, m);
			}
		}
	}
	@gplx.Internal protected void RlsSub(GfoEvMgrOwner eobj) {
		RlsRegyObj(pubsRegy, eobj, true);
		RlsRegyObj(subsRegy, eobj, false);
	}
	@gplx.Internal protected void RlsPub(GfoEvMgrOwner eobj) {
		RlsRegyObj(pubsRegy, eobj, true);
		RlsRegyObj(subsRegy, eobj, false);
	}
	@gplx.Internal protected void RlsRegyObj(OrderedHash regy, GfoEvMgrOwner eobj, boolean pub) {
		if (regy == null) return;
		ListAdp delList = ListAdp_.new_();
		for (int i = 0; i < regy.Count(); i++) {
			GfoEvLnkList pubsList = (GfoEvLnkList)regy.FetchAt(i);
			delList.Clear();
			for (int j = 0; j < pubsList.Count(); j++) {
				GfoEvLnk lnk = (GfoEvLnk)pubsList.FetchAt(j);
				if (lnk.End(!pub) == eobj) delList.Add(lnk);
			}
			for (int j = 0; j < delList.Count(); j++) {
				GfoEvLnk del = (GfoEvLnk)delList.FetchAt(j);
				del.End(pub).EvMgr().RlsLnk(!pub, pubsList.Key(), del.End(!pub));
				pubsList.Del(del);
			}
		}
	}
	@gplx.Internal protected void RlsLnk(boolean pubEnd, String key, GfoEvMgrOwner endObj) {
		OrderedHash regy = pubEnd ? pubsRegy : subsRegy;
		GfoEvLnkList list = (GfoEvLnkList)regy.Fetch(key);
		ListAdp delList = ListAdp_.new_();
		for (int i = 0; i < list.Count(); i++) {
			GfoEvLnk lnk = (GfoEvLnk)list.FetchAt(i);
			if (lnk.End(pubEnd) == endObj) delList.Add(lnk);
		}
		for (int i = 0; i < delList.Count(); i++) {
			GfoEvLnk lnk = (GfoEvLnk)delList.FetchAt(i);
			list.Del(lnk);
		}
		delList.Clear();
	}

	Object sender; OrderedHash subsRegy, pubsRegy;
	public static GfoEvMgr new_(Object sender) {
		GfoEvMgr rv = new GfoEvMgr();
		rv.sender = sender;
		return rv;
	}	GfoEvMgr() {}
}
class GfoEvLnkList {
	public String Key() {return key;} private String key;
	public int Count() {return list.Count();}
	public void Add(GfoEvLnk lnk) {list.Add(lnk);}
	public void Del(GfoEvLnk lnk) {list.Del(lnk);}
	public GfoEvLnk FetchAt(int i) {return (GfoEvLnk)list.FetchAt(i);}
	public GfoEvLnkList(String key) {this.key = key;}
	ListAdp list = ListAdp_.new_();
}
class GfoEvLnk {
	public GfoEvMgrOwner Pub() {return pub;} GfoEvMgrOwner pub;
	public String PubEvt() {return pubEvt;} private String pubEvt;
	public GfoEvObj Sub() {return sub;} GfoEvObj sub;
	public String SubPrc() {return subPrc;} private String subPrc;
	public GfoEvMgrOwner End(boolean pubEnd) {return pubEnd ? pub : sub;}
	public GfoEvLnk(GfoEvMgrOwner pub, String pubEvt, GfoEvObj sub, String subPrc) {this.pub = pub; this.pubEvt = pubEvt; this.sub = sub; this.subPrc = subPrc;}
}
class GfoEvItm {
	public String Key() {return key;} private String key;
	public GfoInvkAble InvkAble() {return invkAble;} GfoInvkAble invkAble;
	public static GfoEvItm new_(GfoInvkAble invkAble, String key) {
		GfoEvItm rv = new GfoEvItm();
		rv.invkAble = invkAble; rv.key = key;
		return rv;
	}
}
