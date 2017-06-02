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
package gplx.xowa.addons.parsers.mediawikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.parsers.*;
import gplx.xowa.parsers.utils.*;
class Xow_page_cache_wkr__embeddable implements gplx.xowa.wikis.caches.Xow_page_cache_wkr {
	private final    Xop_mediawiki_loader cbk;
	private final    Xop_redirect_mgr redirect_mgr;
	public Xow_page_cache_wkr__embeddable(Xowe_wiki wiki, Xop_mediawiki_loader cbk) {
		this.cbk = cbk;
		this.redirect_mgr = new Xop_redirect_mgr(wiki);
	}
	public byte[] Get_page_or_null(byte[] full_db) {
		byte[] wikitext = null;

		// loop to handle redirects; DATE:2017-05-29
		int loops = 0;
		while (loops++ < 5) {
			wikitext = Bry_.new_u8(cbk.LoadWikitext(String_.new_u8(full_db)));
			Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(wikitext);
			// not a redirect; exit loop
			if (redirect_ttl == null) {
				break;
			}
			// redirect; update title and continue;
			else {
				full_db = redirect_ttl.Full_db();
				continue;
			}
		}
		return wikitext;
	}
}
