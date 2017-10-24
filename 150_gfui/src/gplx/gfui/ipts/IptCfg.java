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
public interface IptCfg extends Gfo_invk {
	String CfgKey();
	Object NewByKey(Object o);
	IptCfgItm GetOrDefaultArgs(String key, GfoMsg m, IptArg[] argAry);
	void Owners_add(String key, IptBndsOwner owner);
	void Owners_del(String key);
}
class IptCfg_base implements IptCfg {
	public String CfgKey() {return cfgKey;} private String cfgKey;
	public IptCfgItm GetOrDefaultArgs(String bndKey, GfoMsg defaultMsg, IptArg[] defaultArgs) {
		IptCfgItm rv = (IptCfgItm)hash.Get_by(bndKey);
		if (rv == null) {	// no cfg
			rv = IptCfgItm.new_().Key_(bndKey).Ipt_(List_adp_.New_by_many((Object[])defaultArgs)).Msg_(defaultMsg);
			hash.Add(bndKey, rv);
		}
		else {				// cfg exists
			if (rv.Msg() == null) rv.Msg_(defaultMsg); // no msg defined; use default
		}
		return rv;
	}
	public IptCfgItm Set(String bndKey, GfoMsg m, IptArg[] argAry) {
		IptCfgItm rv = GetOrDefaultArgs(bndKey, m, argAry);
		rv.Msg_(m); // always overwrite msg
		if (Dif(rv.Ipt(), argAry)) {
			rv.Ipt_(List_adp_.New_by_many((Object[])argAry));
			this.Change(bndKey, argAry);
		}
		return rv;
	}
	boolean Dif(List_adp lhs, IptArg[] rhs) {
		if (lhs.Count() != rhs.length) return true;
		for (int i = 0; i < rhs.length; i++) {
			IptArg lhsArg = (IptArg)lhs.Get_at(i);
			IptArg rhsArg = rhs[i];
			if (!lhsArg.Eq(rhsArg)) return true;
		}
		return false;
	}
	void Change(String bndKey, IptArg[] ary) {
		List_adp list = (List_adp)owners.Get_by(bndKey);
		if (list == null) return;
		for (int i = 0; i < list.Count(); i++) {
			IptBndsOwner owner = (IptBndsOwner)list.Get_at(i);
			owner.IptBnds().Change(bndKey, ary);
		}
	}
	public void Owners_del(String bndKey) {owners.Del(bndKey);}
	public void Owners_add(String bndKey, IptBndsOwner owner) {
		List_adp list = (List_adp)owners.Get_by(bndKey);
		if (list == null) {
			list = List_adp_.New();
			owners.Add(bndKey, list);
		}
		list.Add(owner);
		owner.IptBnds().Cfgs().Add(new IptCfgPtr(cfgKey, bndKey));
	}	Ordered_hash owners = Ordered_hash_.New();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.MatchIn(k, Invk_Add, Invk_set)) {
			String bndKey = m.ReadStr("bndKey");
			String iptStr = m.ReadStr("ipt");
			String cmd = m.ReadStrOr("cmd", "");
			if (ctx.Deny()) return this;
			Set(bndKey, gplx.gfml.GfmlDataNde.XtoMsgNoRoot(cmd), IptArg_.parse_ary_(iptStr));
		}
		return this;
	}	public static final    String Invk_Add = "Add", Invk_set = "set";
	public IptCfg_base(String cfgKey) {this.cfgKey = cfgKey;}
	Ordered_hash hash = Ordered_hash_.New();
	public Object NewByKey(Object o) {return new IptCfg_base((String)o);} @gplx.Internal protected static final    IptCfg HashProto = new IptCfg_base(); @gplx.Internal protected IptCfg_base() {}
}
class IptCfgPtr {
	public String CfgKey() {return cfgKey;} private String cfgKey;
	public String BndKey() {return bndKey;} private String bndKey;
	public IptCfgPtr(String cfgKey, String bndKey) {this.cfgKey = cfgKey; this.bndKey = bndKey;}
}
