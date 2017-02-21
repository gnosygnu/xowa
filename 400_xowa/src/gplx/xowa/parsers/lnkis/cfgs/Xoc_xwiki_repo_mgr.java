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
package gplx.xowa.parsers.lnkis.cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.lnkis.*;
public class Xoc_xwiki_repo_mgr implements Gfo_invk {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private Xowe_wiki wiki;
	public Xoc_xwiki_repo_mgr(Xowe_wiki wiki) {this.wiki = wiki;}
	public boolean Has(byte[] abrv) {
		Xoc_xwiki_repo_itm itm = (Xoc_xwiki_repo_itm)hash.Get_by(abrv);
		return itm != null;
	}
	public void Add_or_mod(byte[] abrv) {
		Xoc_xwiki_repo_itm itm = (Xoc_xwiki_repo_itm)hash.Get_by(abrv);
		if (itm == null) {
			itm = new Xoc_xwiki_repo_itm(abrv);
			hash.Add(abrv, itm);
			wiki.Cfg_parser_lnki_xwiki_repos_enabled_(true);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_add))			Add_or_mod(m.ReadBry("xwiki"));
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_add = "add";
}
class Xoc_xwiki_repo_itm {
	public Xoc_xwiki_repo_itm(byte[] abrv) {this.abrv = abrv;}
	public byte[] Abrv() {return abrv;} private byte[] abrv;
}
