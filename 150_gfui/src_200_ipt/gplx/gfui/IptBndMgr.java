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
public class IptBndMgr implements SrlAble {
	@gplx.Internal protected IptEventType EventsToFwd() {return eventsToFwd;}
	public void EventsToFwd_set(IptEventType v) {eventsToFwd = v;} IptEventType eventsToFwd = IptEventType_.KeyDown;
	public void EventsToFwd_add(IptEventType v) {eventsToFwd = eventsToFwd.Add(v);}
	@gplx.Internal protected boolean Has(IptEventType type) {return IptEventType_.Has(curTypes, type);}
	public void Clear() {hash.Clear(); curTypes = IptEventType_.None; ClearLists(); chainMgr.Clear();}
	public void Add(IptBnd bnd) {
		for (IptBndHash list : regy)
			if (IptEventType_.Has(bnd.EventTypes(), list.EventType()))
				list.Add(bnd);
		for (int i = 0; i < bnd.Ipts().Count(); i++) {
			IptArg arg = (IptArg)bnd.Ipts().FetchAt(i);
			chainMgr.Add(arg);
		}
	}
	@gplx.Internal protected ListAdp Cfgs() {return cfgs;} ListAdp cfgs = ListAdp_.new_();
	@gplx.Internal protected void Cfgs_delAll() {
		ListAdp del = ListAdp_.new_();
		for (int i = 0; i < cfgs.Count(); i++) {
			IptCfgPtr ptr = (IptCfgPtr)cfgs.FetchAt(i);
			IptCfg cfg = IptCfgRegy._.GetOrNew(ptr.CfgKey());
			cfg.Owners_del(ptr.CfgKey());
			for (IptBndHash list : regy) {
				for (int j = 0; j < list.Count(); j++) {
					IptBndListItm itmList = list.Get_at(j);
					for (int k = 0; k < itmList.Count(); k++) {
						IptBnd bnd = itmList.FetchAt(k);							
						if (String_.Eq(ptr.BndKey(), bnd.Key())) {
							list.Del(bnd);
						}
					}
				}
			}
			del.Add(cfg);
		}
		for (int i = 0; i < del.Count(); i++) {
			IptCfg cfg = (IptCfg)del.FetchAt(i);
			cfgs.Del(cfg);
		}
	}
	public void Change(String key, IptArg[] ary) {
		IptBnd old = null;
		for (IptBndHash list : regy) {
			for (int j = 0; j < list.Count(); j++) {
				IptBndListItm itmList = list.Get_at(j);
				for (int i = 0; i < itmList.Count(); i++) {
					IptBnd bnd = itmList.FetchAt(i);
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
		old.Ipts().AddMany((Object[])ary);
		this.Add(old);
	}
	public void Del_by_key(String key) {Del_by(true, key);}
	public void Del_by_ipt(IptArg ipt) {
		if (IptArg_.Is_null_or_none(ipt)) return;
		Del_by(false, ipt.Key());
	}
	private void Del_by(boolean del_by_key, String del_key) {
		int regy_len = regy.length;
		ListAdp deleted = ListAdp_.new_();
		for (int i = 0; i < regy_len; i++) {
			IptBndHash list = regy[i];
			int list_len = list.Count();
			for (int j = 0; j < list_len; j++) {
				IptBndListItm bnds = list.Get_at(j);
				int bnds_len = bnds.Count();
				for (int k = 0; k < bnds_len; k++) {
					IptBnd itm_bnd = bnds.FetchAt(k);
					if (del_by_key) {
						if (String_.Eq(del_key, itm_bnd.Key())) {
							deleted.Add(itm_bnd);
						}
					}
					else {
						if (itm_bnd.Ipts().Count() != 1) continue;	// only delete if bnd has 1 ipt; should only be called by xowa which does 1 bnd per ipt 
						IptArg itm_ipt = (IptArg)itm_bnd.Ipts().FetchAt(0);
						if (String_.Eq(del_key, itm_ipt.Key()))
							deleted.Add(itm_bnd);
					}
				}
			}
		}
		int deleted_len = deleted.Count();
		for (int i = 0; i < deleted_len; i++) {
			IptBnd bnd = (IptBnd)deleted.FetchAt(i);
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
			IptArg arg = (IptArg)bnd.Ipts().FetchAt(i);
			chainMgr.Del(arg);
		}
	}
	@gplx.Internal protected boolean Process(IptEventData evData) {
		IptBndHash list = regy[AryIdx(evData.EventType())];
		String key = evData.EventArg().Key();
		if (!String_.Eq(chainMgr.ActiveKey(), "")) key = chainMgr.ActiveKey() + key;
		IptBndListItm itm = list.Get_by(key);
		String chainP = "";
		if (evData.EventType() == IptEventType_.KeyDown) {
			chainP = chainMgr.Process(evData.EventArg());
			if (!String_.Eq(chainP, "") && itm == null)
				UsrDlg_._.Note("cancelled... {0}", chainP);
		}
		if (itm == null) {
			return false;
		}
		return itm.Exec(evData);
	}		
	public Object Srl(GfoMsg owner) {
		GfoMsg m = GfoMsg_.srl_(owner, "mgr");
		for (int i = 0; i < hash.Count(); i++)
			((IptBnd)hash.FetchAt(i)).Srl(m);
		return this;
	}
	IptArgChainMgr chainMgr = new IptArgChainMgr();
	OrderedHash hash = OrderedHash_.new_(); IptEventType curTypes = IptEventType_.None;
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
		else											throw Err_.unhandled(v);
	}
}
class IptBndHash implements SrlAble {
	private IptBndListItm wildcard_list;
	public IptEventType EventType() {return eventType;} IptEventType eventType;
	public int Count() {return hash.Count();}
	public IptBndListItm Get_by(String key) {return wildcard_list == null ? (IptBndListItm)hash.Fetch(key) : wildcard_list;}
	public IptBndListItm Get_at(int i) {return (IptBndListItm)hash.FetchAt(i);}
	public void Add(IptBnd bnd) {
		for (int i = 0; i < bnd.Ipts().Count(); i++) {
			IptArg arg = (IptArg)bnd.Ipts().FetchAt(i);
			if (!IptArg_.EventType_match(arg, eventType)) continue;	// bnd may have multiple ipts of different evTypes; only add bnd if evType matches
			if (String_.Eq(arg.Key(), IptArg_.Wildcard_key)) {
				if (wildcard_list == null) wildcard_list = new IptBndListItm(IptArg_.Wildcard_key);
				wildcard_list.Add(bnd);
			}
			else {
				IptBndListItm itm = (IptBndListItm)hash.Fetch(arg.Key());
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
			IptArg arg = (IptArg)bnd.Ipts().FetchAt(i);
			if (!IptArg_.EventType_match(arg, eventType)) continue;	// bnd may have multiple ipts of different evTypes; only add bnd if evType matches
			hash.Del(arg.Key());
		}
	}
	public Object Srl(GfoMsg owner) {
		GfoMsg m = GfoMsg_.srl_(owner, "list").Add("eventType", eventType.Name());
		for (int i = 0; i < hash.Count(); i++)
			((IptBndListItm)hash.FetchAt(i)).Srl(m);
		return this;
	}
	OrderedHash hash = OrderedHash_.new_();
	public IptBndHash(IptEventType eventType) {this.eventType = eventType;}
}
class IptBndListItm implements SrlAble {
	public String IptKey() {return iptKey;} private String iptKey;
	public int Count() {return list.Count();}
	public IptBnd FetchAt(int i)  {return (IptBnd)list.FetchAt(i);}
	public void Add(IptBnd bnd) {list.AddAt(0, bnd);}
	public boolean Exec(IptEventData evData) {
		for (int i = 0; i < list.Count(); i++) {
			IptBnd bnd = (IptBnd)list.FetchAt(i);
			try {bnd.Exec(evData);}
			catch (Exception exc) {
				UsrDlg_._.Stop(UsrMsg.new_("Error while processing event").Add("bnd", SrlAble_.XtoStr(bnd)).Add("exc", Err_.Message_lang(exc)));
				return false;
			}
			if (evData.CancelIteration) break;
		}
		return true;
	}
	public Object Srl(GfoMsg owner) {
		GfoMsg m = GfoMsg_.srl_(owner, "itm").Add("iptKey", iptKey);
		for (int i = 0; i < list.Count(); i++)
			((IptBnd)list.FetchAt(i)).Srl(m);
		return this;
	}
	ListAdp list = ListAdp_.new_();
	public IptBndListItm(String iptKey) {this.iptKey = iptKey;}
}
class IptArgChainMgr {
	public void Clear() {regy.Clear();}
	public String Process(IptArg arg) {
//			if (String_.Eq(arg.Key(), "key_7")) return "";
		HashAdp hash = (HashAdp)active.Fetch(arg.Key());
		if (hash == null) {
			active = regy;
			String r = activeKey;
			activeKey = ""; 
			return r;
		}
		active = hash;
		activeKey = activeKey + arg.Key() + ",";
		UsrDlg_._.Note("{0} pressed...", activeKey);
		return "";
	}
	public String ActiveKey() {return activeKey;}
	String activeKey = "";
	public IptArgChainMgr() {active = regy;}
	HashAdp active;
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
	void Add_recur(HashAdp cur, IptArg[] ary, int i) {
		if (i == ary.length - 1) return;	// -1 b/c last should not be registered; ex: key.a,key.b should register key.a only
		IptArg ipt = ary[i];
		HashAdp next = (HashAdp)cur.Fetch(ipt.Key());
		if (next == null) {
			next = HashAdp_.new_();
			cur.Add(ipt.Key(), next);
		}
		Add_recur(next, ary, i + 1);
	}// a,b,c
	void Del_recur(HashAdp cur, IptArg[] ary, int i) {
		IptArg ipt = ary[i];
		if (i == ary.length - 1) {
			cur.Del(ipt.Key());
			return;	// -1 b/c last should not be registered; ex: key.a,key.b should register key.a only
		}
		HashAdp next = (HashAdp)cur.Fetch(ipt.Key());
		if (next == null) {
			return;
		}
		Del_recur(next, ary, i + 1);
		if (cur.Count() == 1)
			cur.Clear();
	}
	HashAdp regy = HashAdp_.new_();
}
