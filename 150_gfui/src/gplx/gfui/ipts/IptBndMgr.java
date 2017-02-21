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
package gplx.gfui.ipts; import gplx.*; import gplx.gfui.*;
import gplx.core.interfaces.*;
public class IptBndMgr implements SrlAble {
	public IptEventType EventsToFwd() {return eventsToFwd;}
	public void EventsToFwd_set(IptEventType v) {eventsToFwd = v;} IptEventType eventsToFwd = IptEventType_.KeyDown;
	public void EventsToFwd_add(IptEventType v) {eventsToFwd = eventsToFwd.Add(v);}
	public boolean Has(IptEventType type) {return IptEventType_.Has(curTypes, type);}
	public void Clear() {hash.Clear(); curTypes = IptEventType_.None; ClearLists(); chainMgr.Clear();}
	public void Add(IptBnd bnd) {
		for (IptBndHash list : regy)
			if (IptEventType_.Has(bnd.EventTypes(), list.EventType()))
				list.Add(bnd);
		for (int i = 0; i < bnd.Ipts().Count(); i++) {
			IptArg arg = (IptArg)bnd.Ipts().Get_at(i);
			chainMgr.Add(arg);
		}
	}
	public List_adp Cfgs() {return cfgs;} List_adp cfgs = List_adp_.New();
	public void Cfgs_delAll() {
		List_adp del = List_adp_.New();
		for (int i = 0; i < cfgs.Count(); i++) {
			IptCfgPtr ptr = (IptCfgPtr)cfgs.Get_at(i);
			IptCfg cfg = IptCfgRegy.Instance.GetOrNew(ptr.CfgKey());
			cfg.Owners_del(ptr.CfgKey());
			for (IptBndHash list : regy) {
				for (int j = 0; j < list.Count(); j++) {
					IptBndListItm itmList = list.Get_at(j);
					for (int k = 0; k < itmList.Count(); k++) {
						IptBnd bnd = itmList.Get_at(k);							
						if (String_.Eq(ptr.BndKey(), bnd.Key())) {
							list.Del(bnd);
						}
					}
				}
			}
			del.Add(cfg);
		}
		for (int i = 0; i < del.Count(); i++) {
			IptCfg cfg = (IptCfg)del.Get_at(i);
			cfgs.Del(cfg);
		}
	}
	public void Change(String key, IptArg[] ary) {
		IptBnd old = null;
		for (IptBndHash list : regy) {
			for (int j = 0; j < list.Count(); j++) {
				IptBndListItm itmList = list.Get_at(j);
				for (int i = 0; i < itmList.Count(); i++) {
					IptBnd bnd = itmList.Get_at(i);
					if (String_.Eq(key, bnd.Key())) {
						old = bnd;
						break;
					}
				}
			}
		}
		if (old == null) return;
		this.Del(old);
		old.Ipts().Clear();
		if (ary == IptArg_.Ary_empty) return;	// "unbind"; exit after deleting; DATE:2014-05-13
		old.Ipts().Add_many((Object[])ary);
		this.Add(old);
	}
	public void Del_by_key(String key) {Del_by(true, key);}
	public void Del_by_ipt(IptArg ipt) {
		if (IptArg_.Is_null_or_none(ipt)) return;
		Del_by(false, ipt.Key());
	}
	private void Del_by(boolean del_by_key, String del_key) {
		int regy_len = regy.length;
		List_adp deleted = List_adp_.New();
		for (int i = 0; i < regy_len; i++) {
			IptBndHash list = regy[i];
			int list_len = list.Count();
			for (int j = 0; j < list_len; j++) {
				IptBndListItm bnds = list.Get_at(j);
				int bnds_len = bnds.Count();
				for (int k = 0; k < bnds_len; k++) {
					IptBnd itm_bnd = bnds.Get_at(k);
					if (del_by_key) {
						if (String_.Eq(del_key, itm_bnd.Key())) {
							deleted.Add(itm_bnd);
						}
					}
					else {
						if (itm_bnd.Ipts().Count() != 1) continue;	// only delete if bnd has 1 ipt; should only be called by xowa which does 1 bnd per ipt 
						IptArg itm_ipt = (IptArg)itm_bnd.Ipts().Get_at(0);
						if (String_.Eq(del_key, itm_ipt.Key()))
							deleted.Add(itm_bnd);
					}
				}
			}
		}
		int deleted_len = deleted.Count();
		for (int i = 0; i < deleted_len; i++) {
			IptBnd bnd = (IptBnd)deleted.Get_at(i);
			this.Del(bnd);
			bnd.Ipts().Clear();
		}
	}
	public void Del(IptBnd bnd) {
		for (IptBndHash list : regy) {
			if (IptEventType_.Has(bnd.EventTypes(), list.EventType())) {
				list.Del(bnd);
			}
		}
		for (int i = 0; i < bnd.Ipts().Count(); i++) {
			IptArg arg = (IptArg)bnd.Ipts().Get_at(i);
			chainMgr.Del(arg);
		}
	}
	public boolean Process(IptEventData evData) {
		IptBndHash list = regy[AryIdx(evData.EventType())];
		String key = evData.EventArg().Key();
		if (!String_.Eq(chainMgr.ActiveKey(), "")) key = chainMgr.ActiveKey() + key;
		IptBndListItm itm = list.Get_by(key);
		String chainP = "";
		if (evData.EventType() == IptEventType_.KeyDown) {
			chainP = chainMgr.Process(evData.EventArg());
			if (!String_.Eq(chainP, "") && itm == null)
				UsrDlg_.Instance.Note("cancelled... {0}", chainP);
		}
		if (itm == null) {
			return false;
		}
		return itm.Exec(evData);
	}		
	public Object Srl(GfoMsg owner) {
		GfoMsg m = GfoMsg_.srl_(owner, "mgr");
		for (int i = 0; i < hash.Count(); i++)
			((IptBnd)hash.Get_at(i)).Srl(m);
		return this;
	}
	IptArgChainMgr chainMgr = new IptArgChainMgr();
	Ordered_hash hash = Ordered_hash_.New(); IptEventType curTypes = IptEventType_.None;
	public static IptBndMgr new_() {return new IptBndMgr();}
	IptBndHash[] regy = new IptBndHash[8];
	IptBndMgr() {ClearLists();}
	void ClearLists(){
		MakeList(IptEventType_.KeyDown); MakeList(IptEventType_.KeyUp); MakeList(IptEventType_.KeyPress);
		MakeList(IptEventType_.MouseMove); MakeList(IptEventType_.MouseDown); MakeList(IptEventType_.MouseUp); MakeList(IptEventType_.MouseWheel); MakeList(IptEventType_.MousePress);
	}	void MakeList(IptEventType eventType) {regy[AryIdx(eventType)] = new IptBndHash(eventType);}
	static int AryIdx(IptEventType eventType) {
		int v = eventType.Val();
		if		(v == IptEventType_.KeyDown.Val())		return 0;
		else if (v == IptEventType_.KeyUp.Val())		return 1;
		else if (v == IptEventType_.KeyPress.Val())		return 2;
		else if (v == IptEventType_.MouseDown.Val())	return 3;
		else if (v == IptEventType_.MouseUp.Val())		return 4;
		else if (v == IptEventType_.MouseMove.Val())	return 5;
		else if (v == IptEventType_.MouseWheel.Val())	return 6;
		else if (v == IptEventType_.MousePress.Val())	return 7;
		else											throw Err_.new_unhandled(v);
	}
}
class IptBndHash implements SrlAble {
	private IptBndListItm wildcard_list;
	public IptEventType EventType() {return eventType;} IptEventType eventType;
	public int Count() {return hash.Count();}
	public IptBndListItm Get_by(String key) {return wildcard_list == null ? (IptBndListItm)hash.Get_by(key) : wildcard_list;}
	public IptBndListItm Get_at(int i) {return (IptBndListItm)hash.Get_at(i);}
	public void Add(IptBnd bnd) {
		for (int i = 0; i < bnd.Ipts().Count(); i++) {
			IptArg arg = (IptArg)bnd.Ipts().Get_at(i);
			if (!IptArg_.EventType_match(arg, eventType)) continue;	// bnd may have multiple ipts of different evTypes; only add bnd if evType matches
			if (String_.Eq(arg.Key(), IptArg_.Wildcard_key)) {
				if (wildcard_list == null) wildcard_list = new IptBndListItm(IptArg_.Wildcard_key);
				wildcard_list.Add(bnd);
			}
			else {
				IptBndListItm itm = (IptBndListItm)hash.Get_by(arg.Key());
				if (itm == null) {
					itm = new IptBndListItm(arg.Key());
					hash.Add(arg.Key(), itm);
				}
				itm.Add(bnd);
			}
		}
	}
	public void Del(IptBnd bnd) {
		for (int i = 0; i < bnd.Ipts().Count(); i++) {
			IptArg arg = (IptArg)bnd.Ipts().Get_at(i);
			if (!IptArg_.EventType_match(arg, eventType)) continue;	// bnd may have multiple ipts of different evTypes; only add bnd if evType matches
			hash.Del(arg.Key());
		}
	}
	public Object Srl(GfoMsg owner) {
		GfoMsg m = GfoMsg_.srl_(owner, "list").Add("eventType", eventType.Name());
		for (int i = 0; i < hash.Count(); i++)
			((IptBndListItm)hash.Get_at(i)).Srl(m);
		return this;
	}
	Ordered_hash hash = Ordered_hash_.New();
	public IptBndHash(IptEventType eventType) {this.eventType = eventType;}
}
class IptBndListItm implements SrlAble {
	public String IptKey() {return iptKey;} private String iptKey;
	public int Count() {return list.Count();}
	public IptBnd Get_at(int i)  {return (IptBnd)list.Get_at(i);}
	public void Add(IptBnd bnd) {list.Add_at(0, bnd);}
	public boolean Exec(IptEventData evData) {
		for (int i = 0; i < list.Count(); i++) {
			IptBnd bnd = (IptBnd)list.Get_at(i);
			try {bnd.Exec(evData);}
			catch (Exception exc) {
				UsrDlg_.Instance.Stop(UsrMsg.new_("Error while processing event").Add("bnd", SrlAble_.To_str(bnd)).Add("exc", Err_.Message_lang(exc)));
				return false;
			}
			if (evData.CancelIteration) break;
		}
		return true;
	}
	public Object Srl(GfoMsg owner) {
		GfoMsg m = GfoMsg_.srl_(owner, "itm").Add("iptKey", iptKey);
		for (int i = 0; i < list.Count(); i++)
			((IptBnd)list.Get_at(i)).Srl(m);
		return this;
	}
	List_adp list = List_adp_.New();
	public IptBndListItm(String iptKey) {this.iptKey = iptKey;}
}
class IptArgChainMgr {
	public void Clear() {regy.Clear();}
	public String Process(IptArg arg) {
//			if (String_.Eq(arg.Key(), "key_7")) return "";
		Hash_adp hash = (Hash_adp)active.Get_by(arg.Key());
		if (hash == null) {
			active = regy;
			String r = activeKey;
			activeKey = ""; 
			return r;
		}
		active = hash;
		activeKey = activeKey + arg.Key() + ",";
		UsrDlg_.Instance.Note("{0} pressed...", activeKey);
		return "";
	}
	public String ActiveKey() {return activeKey;}
	String activeKey = "";
	public IptArgChainMgr() {active = regy;}
	Hash_adp active;
	public void Add(IptArg arg) {
		if (arg.getClass() != IptKeyChain.class) return;
		IptKeyChain chain = (IptKeyChain)arg;
		Add_recur(regy, chain.Chained(), 0);
	}
	public void Del(IptArg arg) {
		if (arg.getClass() != IptKeyChain.class) return;
		IptKeyChain chain = (IptKeyChain)arg;
		Del_recur(regy, chain.Chained(), 0);
	}
	void Add_recur(Hash_adp cur, IptArg[] ary, int i) {
		if (i == ary.length - 1) return;	// -1 b/c last should not be registered; ex: key.a,key.b should register key.a only
		IptArg ipt = ary[i];
		Hash_adp next = (Hash_adp)cur.Get_by(ipt.Key());
		if (next == null) {
			next = Hash_adp_.New();
			cur.Add(ipt.Key(), next);
		}
		Add_recur(next, ary, i + 1);
	}// a,b,c
	void Del_recur(Hash_adp cur, IptArg[] ary, int i) {
		IptArg ipt = ary[i];
		if (i == ary.length - 1) {
			cur.Del(ipt.Key());
			return;	// -1 b/c last should not be registered; ex: key.a,key.b should register key.a only
		}
		Hash_adp next = (Hash_adp)cur.Get_by(ipt.Key());
		if (next == null) {
			return;
		}
		Del_recur(next, ary, i + 1);
		if (cur.Count() == 1)
			cur.Clear();
	}
	Hash_adp regy = Hash_adp_.New();
}
