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
package gplx.core.stores; import gplx.*; import gplx.core.*;
import gplx.core.gfo_ndes.*;
public class DataRdr_mem extends DataRdr_base implements GfoNdeRdr {
	@Override public String NameOfNode() {return cur.Name();}
	public GfoNde UnderNde() {return cur;}
	@Override public int FieldCount() {return flds.Count();}
	@Override public String KeyAt(int i) {return flds.Get_at(i).Key();}
	@Override public Object ReadAt(int i) {return cur.ReadAt(i);}
	@Override public Object Read(String key) {
		int i = flds.Idx_of(key); if (i == List_adp_.Not_found) return null;
		return cur.ReadAt(i);
	}
	public boolean MoveNextPeer() {
		if (++peerPos >= peerList.Count()) {
			cur = null;
			return false;
		}
		cur = peerList.FetchAt_asGfoNde(peerPos);
		return true;
	}
	@Override public DataRdr Subs() {
		if (cur == null && peerList.Count() == 0) return DataRdr_.Null;
		return GfoNdeRdr_.peers_(Peers_get(), this.Parse());
	}
	public DataRdr Subs_byName(String name) {
		if (cur == null && peerList.Count() == 0) return DataRdr_.Null;
		String[] names = String_.Split(name, "/");
		GfoNdeList list = GfoNdeList_.new_();
		Subs_byName(list, names, 0, Peers_get());
		return GfoNdeRdr_.peers_(list, this.Parse());
	}		
	@Override public DataRdr Subs_byName_moveFirst(String name) {
		DataRdr subRdr = Subs_byName(name);
		boolean hasFirst = subRdr.MoveNextPeer();
		return (hasFirst) ? subRdr : DataRdr_.Null;
	}
	public String To_str() {return cur.To_str();}
	public void Rls() {this.cur = null; this.peerList = null;}
	@Override public SrlMgr SrlMgr_new(Object o) {return new DataRdr_mem();}
	GfoNdeList Peers_get() {
		boolean initialized = cur == null && peerPos == -1 && peerList.Count() > 0;	// initialized = no current, at bof, subs available
		return initialized ? peerList.FetchAt_asGfoNde(0).Subs() : cur.Subs();
	}
	void Subs_byName(GfoNdeList list, String[] names, int depth, GfoNdeList peers) {
		String name = names[depth];
		for (int i = 0; i < peers.Count(); i++) {
			GfoNde sub = peers.FetchAt_asGfoNde(i); if (sub == null) continue;
			if (!String_.Eq(name, sub.Name())) continue;
			if (depth == names.length - 1)
				list.Add(sub);
			else
				Subs_byName(list, names, depth + 1, sub.Subs());
		}				
	}

	GfoNde cur; GfoNdeList peerList; int peerPos = -1; GfoFldList flds;
	public static DataRdr_mem new_(GfoNde cur, GfoFldList flds, GfoNdeList peerList) {
		DataRdr_mem rv = new DataRdr_mem();
		rv.cur = cur; rv.peerList = peerList; rv.flds = flds;
		return rv;
	}	@gplx.Internal protected DataRdr_mem() {}
}
