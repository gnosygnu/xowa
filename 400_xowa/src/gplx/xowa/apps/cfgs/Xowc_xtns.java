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
package gplx.xowa.apps.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xowc_xtns implements Gfo_invk {
	private Hash_adp_bry hash = Hash_adp_bry.ci_a7();
	public Xowc_xtns() {hash.Add(Xowc_xtn_pages.Xtn_key, itm_pages);}
	public Xowc_xtn_pages Itm_pages() {return itm_pages;} private Xowc_xtn_pages itm_pages = new Xowc_xtn_pages();
	public Object Get_by_key(byte[] key) {return hash.Get_by_bry(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return (Gfo_invk)hash.Get_by_bry(m.ReadBry("v"));
		else return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
