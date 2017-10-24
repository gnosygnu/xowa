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
public class IptCfgRegy implements Gfo_invk {
	public void Clear() {hash.Clear();}
	public IptCfg GetOrNew(String k) {
		IptCfg rv = (IptCfg)hash.Get_by(k);
		if (rv == null) {
			rv = (IptCfg)IptCfg_base.HashProto.NewByKey(k);
			hash.Add(k, rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.MatchIn(k, Invk_Get, Invk_get)) {
			String key = m.ReadStr("key");
			if (ctx.Deny()) return this;
			return GetOrNew(key);
		}
		return this;
	}	public static final    String Invk_Get = "Get", Invk_get = "get";
	Ordered_hash hash = Ordered_hash_.New();
	public static final    IptCfgRegy Instance = new IptCfgRegy();
	public IptCfgRegy() {}
}
