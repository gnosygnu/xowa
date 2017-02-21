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
package gplx.xowa.specials; import gplx.*; import gplx.xowa.*;
import gplx.xowa.xtns.wbases.specials.*;
public class Xoa_special_mgr implements Gfo_invk {
	private Wdata_itemByTitle_cfg wbase_cfg = new Wdata_itemByTitle_cfg();
	private Ordered_hash hash = Ordered_hash_.New();
	public Xoa_special_mgr() {
		hash.Add(Wdata_itemByTitle_cfg.Key, wbase_cfg);
	}
	public void Init_by_app(Xoae_app app) {
		wbase_cfg.Init_by_app(app);
	}
	public void Add(String key, Gfo_invk cfg)	{hash.Add(key, cfg);}
	public Gfo_invk Get_or_null(String key)		{return (Gfo_invk)hash.Get_by(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))		return Get_or_null(m.ReadStr("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
