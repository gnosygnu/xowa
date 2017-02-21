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
package gplx.xowa.bldrs.setups.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
public class Xoa_maint_wikis_mgr implements Gfo_invk {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public Xoa_maint_wikis_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	public int Len() {return hash.Count();}
	public Xowe_wiki Get_at(int i) {
		if (init) Init();
		byte[] domain = (byte[])hash.Get_at(i);
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(domain);
		wiki.Init_assert();
		return wiki;
	}
	public void Add(byte[] domain) {hash.Add_if_dupe_use_nth(domain, domain);}	// NOTE: must be Add_if_dupe_use_nth to replace existing wikis
	public void Init() {
		int len = this.Len();
		for (int i = 0; i < len; i++) {
			byte[] domain = (byte[])hash.Get_at(i);
			Xowe_wiki wiki = app.Wiki_mgr().Get_by_or_make(domain);
			wiki.Init_assert();
		}
		init = false;
	}
	private boolean init = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_len))		return this.Len();
		else if	(ctx.Match(k, Invk_get_at))		return this.Get_at(m.ReadInt("v"));
		else	return Gfo_invk_.Rv_unhandled;
//			return this;
	}	private static final String Invk_len = "len", Invk_get_at = "get_at";
}