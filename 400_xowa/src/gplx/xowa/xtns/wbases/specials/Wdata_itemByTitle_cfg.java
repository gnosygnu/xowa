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
package gplx.xowa.xtns.wbases.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
public class Wdata_itemByTitle_cfg implements Gfo_invk {
	public byte[] Site_default() {return site_default;} private byte[] site_default = Bry_.new_a7("enwiki");
	public void Init_by_app(Xoae_app app) {
		app.Cfg().Bind_many_app(this, Cfg__site_default);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if	(ctx.Match(k, Cfg__site_default))		site_default = m.ReadBry("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Key = "itemByTitle";
	private static final String Cfg__site_default = "xowa.addon.wikibase.item_by_title.site_default";
}
