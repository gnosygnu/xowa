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
package gplx;
import gplx.core.lists.*;
public class Gfo_evt_mgr {
	private final    Gfo_evt_mgr_owner sender; private Ordered_hash subsRegy, pubsRegy;
	public Gfo_evt_mgr(Gfo_evt_mgr_owner sender) {this.sender = sender;}
	@gplx.Internal protected void AddSub(Gfo_evt_mgr_owner pub, String pubEvt, Gfo_evt_itm sub, String subPrc) {
		GfoEvLnk lnk = new GfoEvLnk(pub, pubEvt, sub, subPrc);
		if (subsRegy == null) subsRegy = Ordered_hash_.New();
		AddInList(subsRegy, pubEvt, lnk);
		sub.Evt_mgr().AddPub(pubEvt, lnk);
	}
	@gplx.Internal protected void Lnk(Gfo_evt_mgr_owner pub) {
		if (pub.Evt_mgr().lnks == null) pub.Evt_mgr().lnks = List_adp_.New();
		pub.Evt_mgr().lnks.Add(this);
	}	List_adp lnks;
	void AddInList(Ordered_hash regy, String key, GfoEvLnk lnk) {
		GfoEvLnkList list = (GfoEvLnkList)regy.Get_by(key);
		if (list == null) {
			list = new GfoEvLnkList(key);
			regy.Add(key, list);
		}
		list.Add(lnk);
	}
	@gplx.Internal protected void AddPub(String pubEvt, GfoEvLnk lnk) {
		if (pubsRegy == null) pubsRegy = Ordered_hash_.New();
		AddInList(pubsRegy, pubEvt, lnk);
	}
	@gplx.Internal protected void Pub(GfsCtx ctx, String evt, GfoMsg m) {
		ctx.MsgSrc_(sender);
		GfoEvLnkList subs = subsRegy == null ? null : (GfoEvLnkList)subsRegy.Get_by(evt);
		if (subs != null) {
			for (int i = 0; i < subs.Count(); i++) {
				GfoEvLnk lnk = (GfoEvLnk)subs.Get_at(i);
				lnk.Sub().Invk(ctx, 0, lnk.SubPrc(), m);	// NOTE: itm.Key() needed for Subscribe_diff()
			}
		}
		if (lnks != null) {
			for (int i = 0; i < lnks.Count(); i++) {
				Gfo_evt_mgr lnk = (Gfo_evt_mgr)lnks.Get_at(i);
				lnk.Pub(ctx, evt, m);
			}
		}
	}
	@gplx.Internal protected void RlsSub(Gfo_evt_mgr_owner eobj) {
		RlsRegyObj(pubsRegy, eobj, true);
		RlsRegyObj(subsRegy, eobj, false);
	}
	@gplx.Internal protected void RlsPub(Gfo_evt_mgr_owner eobj) {
		RlsRegyObj(pubsRegy, eobj, true);
		RlsRegyObj(subsRegy, eobj, false);
	}
	@gplx.Internal protected void RlsRegyObj(Ordered_hash regy, Gfo_evt_mgr_owner eobj, boolean pub) {
		if (regy == null) return;
		List_adp delList = List_adp_.New();
		for (int i = 0; i < regy.Count(); i++) {
			GfoEvLnkList pubsList = (GfoEvLnkList)regy.Get_at(i);
			delList.Clear();
			for (int j = 0; j < pubsList.Count(); j++) {
				GfoEvLnk lnk = (GfoEvLnk)pubsList.Get_at(j);
				if (lnk.End(!pub) == eobj) delList.Add(lnk);
			}
			for (int j = 0; j < delList.Count(); j++) {
				GfoEvLnk del = (GfoEvLnk)delList.Get_at(j);
				del.End(pub).Evt_mgr().RlsLnk(!pub, pubsList.Key(), del.End(!pub));
				pubsList.Del(del);
			}
		}
	}
	@gplx.Internal protected void RlsLnk(boolean pubEnd, String key, Gfo_evt_mgr_owner endObj) {
		Ordered_hash regy = pubEnd ? pubsRegy : subsRegy;
		GfoEvLnkList list = (GfoEvLnkList)regy.Get_by(key);
		List_adp delList = List_adp_.New();
		for (int i = 0; i < list.Count(); i++) {
			GfoEvLnk lnk = (GfoEvLnk)list.Get_at(i);
			if (lnk.End(pubEnd) == endObj) delList.Add(lnk);
		}
		for (int i = 0; i < delList.Count(); i++) {
			GfoEvLnk lnk = (GfoEvLnk)delList.Get_at(i);
			list.Del(lnk);
		}
		delList.Clear();
	}
}
class GfoEvLnkList {
	public String Key() {return key;} private String key;
	public int Count() {return list.Count();}
	public void Add(GfoEvLnk lnk) {list.Add(lnk);}
	public void Del(GfoEvLnk lnk) {list.Del(lnk);}
	public GfoEvLnk Get_at(int i) {return (GfoEvLnk)list.Get_at(i);}
	public GfoEvLnkList(String key) {this.key = key;}
	List_adp list = List_adp_.New();
}
class GfoEvLnk {
	public Gfo_evt_mgr_owner Pub() {return pub;} Gfo_evt_mgr_owner pub;
	public String PubEvt() {return pubEvt;} private String pubEvt;
	public Gfo_evt_itm Sub() {return sub;} Gfo_evt_itm sub;
	public String SubPrc() {return subPrc;} private String subPrc;
	public Gfo_evt_mgr_owner End(boolean pubEnd) {return pubEnd ? pub : sub;}
	public GfoEvLnk(Gfo_evt_mgr_owner pub, String pubEvt, Gfo_evt_itm sub, String subPrc) {this.pub = pub; this.pubEvt = pubEvt; this.sub = sub; this.subPrc = subPrc;}
}
class GfoEvItm {
	public String Key() {return key;} private String key;
	public Gfo_invk InvkAble() {return invkAble;} Gfo_invk invkAble;
	public static GfoEvItm new_(Gfo_invk invkAble, String key) {
		GfoEvItm rv = new GfoEvItm();
		rv.invkAble = invkAble; rv.key = key;
		return rv;
	}
}
