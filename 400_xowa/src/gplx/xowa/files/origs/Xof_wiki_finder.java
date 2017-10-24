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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
class Xof_wiki_finder {	// UNUSED
	private Xowe_wiki wiki_0, wiki_1;
	private Xowd_page_itm db_page = new Xowd_page_itm(); 
	public Xof_wiki_finder(Xowe_wiki wiki_0, Xowe_wiki wiki_1) {
		this.wiki_0 = wiki_0; this.wiki_1 = wiki_1;
	}
	public Xoae_page Get_page(int ns, byte[] ttl_bry) {
		Xoae_page rv = Get_page__by_wiki(wiki_0, ns, ttl_bry);
		if (rv.Db().Page().Exists_n())
			rv = Get_page__by_wiki(wiki_1, ns, ttl_bry);
		return rv;
	}
	private Xoae_page Get_page__by_wiki(Xowe_wiki wiki, int ns_id, byte[] ttl_bry) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ns_id, ttl_bry) ;
		Xoa_url url = Xoa_url.New(wiki, ttl);
		return wiki.Data_mgr().Load_page_and_parse(url, ttl);
	}
	private int qry_count, qry_count_max = 1000;
	public boolean Find_page(Xof_wiki_finder_itm itm, int ns_id, byte[] ttl_bry) {
		Xowe_wiki wiki = null;
		if (Find_page__by_wiki(db_page, wiki_0, ns_id, ttl_bry)) {
			wiki = wiki_0;
			itm.Orig_repo_id_(Byte_.Zero);
		}
		else {
			if (Find_page__by_wiki(db_page, wiki_1, ns_id, ttl_bry)) {
				wiki = wiki_1;
				itm.Orig_repo_id_((byte)1);
			}
			else
				return false;
		}
		itm.Orig_ttl_(ttl_bry);
		if (db_page.Redirected()) {
			Xoae_page page = Get_page__by_wiki(wiki, ns_id, ttl_bry);
			Xoa_ttl redirect_ttl = wiki.Redirect_mgr().Extract_redirect_loop(page.Db().Text().Text_bry());		
			itm.Orig_redirect_(redirect_ttl);			
			++qry_count;
			if (qry_count >= qry_count_max) {
				wiki.Appe().Reset_all();
				qry_count = 0;
			}
		}
		return true;
	}
	private boolean Find_page__by_wiki(Xowd_page_itm db_page, Xowe_wiki wiki, int ns_id, byte[] ttl_bry) {
		Xow_ns ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		wiki.Db_mgr().Load_mgr().Load_page(db_page, ns);
		return db_page.Exists();
	}	
}
