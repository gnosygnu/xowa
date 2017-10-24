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
package gplx.xowa.xtns.relatedSites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.pages.skins.*;
public class Sites_regy_mgr implements Gfo_invk {
	private Hash_adp_bry hash = Hash_adp_bry.cs();
	private Xow_xwiki_mgr xwiki_mgr;
	public Sites_regy_mgr(Sites_xtn_mgr xtn_mgr) {this.xtn_mgr = xtn_mgr;}
	public void Init_by_wiki(Xowe_wiki wiki) {this.xwiki_mgr = wiki.Xwiki_mgr();}
	public Sites_xtn_mgr Xtn_mgr() {return xtn_mgr;} private Sites_xtn_mgr xtn_mgr;
	public void Set_many(String[] keys)	{
		int len = keys.length;
		for (int i = 0; i < len; ++i) {
			byte[] key = Bry_.new_u8(keys[i]);
			hash.Add_if_dupe_use_nth(key, key);
		}
	}
	public boolean Match(Xoae_page page, Xoa_ttl lnki_ttl) {
		byte[] xwiki_key = lnki_ttl.Wik_txt();
		Xow_xwiki_itm xwiki_itm = xwiki_mgr.Get_by_key(xwiki_key); if (xwiki_itm == null) return false;
		if (!hash.Has(xwiki_itm.Key_bry())) return false;
		Xopg_xtn_skin_mgr skin_mgr = page.Html_data().Xtn_skin_mgr();
		Sites_xtn_skin_itm skin_itm = (Sites_xtn_skin_itm)skin_mgr.Get_or_null(Sites_xtn_skin_itm.KEY);
		if (skin_itm == null) {
			skin_itm = new Sites_xtn_skin_itm(xtn_mgr.Html_bldr());
			skin_mgr.Add(skin_itm);
		}
		Sites_regy_itm sites_itm = new Sites_regy_itm(xwiki_itm, lnki_ttl);
		skin_itm.Add(sites_itm);
		return true;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set_many))		Set_many(m.ReadStrAry("v", "|"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_set_many = "set_many";
}
