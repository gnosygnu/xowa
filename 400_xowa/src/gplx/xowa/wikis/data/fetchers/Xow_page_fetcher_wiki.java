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
package gplx.xowa.wikis.data.fetchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
public class Xow_page_fetcher_wiki implements Xow_page_fetcher {
	public Xow_page_fetcher Wiki_(Xowe_wiki v) {this.wiki = v; return this;} private Xowe_wiki wiki;
	public void Clear() {}
	public byte[] Get_by(int ns_id, byte[] ttl_bry) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ns_id, ttl_bry);
		Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);	// go through data_mgr in case of redirects
		return page.Db().Page().Exists_n() ? null : page.Db().Text().Text_bry();
	}
}
