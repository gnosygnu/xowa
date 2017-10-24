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
public class IptCfg_ {
	public static final    IptCfg Null = IptCfg_null.Instance;
	public static IptCfg new_(String key) {return IptCfgRegy.Instance.GetOrNew(key);}
}
class IptCfg_null implements IptCfg {
	public String CfgKey() {return "<<NULL KEY>>";}
	public IptCfgItm GetOrDefaultArgs(String bndKey, GfoMsg m, IptArg[] argAry) {return IptCfgItm.new_().Key_(bndKey).Ipt_(List_adp_.New_by_many((Object[])argAry)).Msg_(m);}
	public void Owners_add(String key, IptBndsOwner owner) {}
	public void Owners_del(String key) {}
	public Object NewByKey(Object o) {return this;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return Gfo_invk_.Rv_unhandled;}
	public static final    IptCfg_null Instance = new IptCfg_null(); IptCfg_null() {}
}
