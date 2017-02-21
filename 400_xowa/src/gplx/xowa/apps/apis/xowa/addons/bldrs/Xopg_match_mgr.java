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
package gplx.xowa.apps.apis.xowa.addons.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.addons.*;
import gplx.xowa.apps.urls.*;
public class Xopg_match_mgr {
	private String scope_raw;
	private Ordered_hash wikis;
	private boolean wildcard_exists;
	private Xopg_match_wiki wildcard_wiki;
	public void Set(String v) {
		this.scope_raw = v;
		this.wikis = null;
		this.wildcard_exists = false;
		this.wildcard_wiki = null;
	}
	public boolean Match(Xow_wiki wiki, byte[] page_ttl) {
		if (wikis == null) Init(wiki.App());

		if (wildcard_exists) return true;
		if (wildcard_wiki != null) {
			if (wildcard_wiki.Has(page_ttl))
				return true;
		}
		Xopg_match_wiki match_wiki = (Xopg_match_wiki)wikis.Get_by(wiki.Domain_bry());
		if (match_wiki == null) return false;
		return match_wiki.Has(page_ttl);
	}
	private void Init(Xoa_app app) {
		this.wikis = Ordered_hash_.New_bry();
		String[] lines = String_.SplitLines_nl(scope_raw);
		Xow_url_parser url_parser = app.User().Wikii().Utl__url_parser();
		for (String line : lines) {
			if (String_.Eq(line, "*")) {
				wildcard_exists = true;
			}
			else {
				byte[] wiki_domain = null, page_db = null;
				boolean cur_is_wildcard_wiki = false;
				if (String_.Has_at_bgn(line, "*:")) {
					wiki_domain = Byte_ascii.Star_bry;
					page_db = Bry_.Mid(Bry_.new_u8(line), 2);
					cur_is_wildcard_wiki = true;
				}
				else {
					Xoa_url url = url_parser.Parse_by_urlbar_or_null(line);
					wiki_domain = url.Wiki_bry();
					page_db = url.Page_bry();
				}
				Xopg_match_wiki match_wiki = (Xopg_match_wiki)wikis.Get_by(wiki_domain);
				if (match_wiki == null) {
					match_wiki = new Xopg_match_wiki(wiki_domain);
					wikis.Add(wiki_domain, match_wiki);
					if (cur_is_wildcard_wiki) {
						wildcard_wiki = match_wiki;
					}
				}
				match_wiki.Add(page_db);
			}
		}
	}
}
class Xopg_match_wiki {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	private boolean wildcard_exists;
	public Xopg_match_wiki(byte[] domain_bry) {
		this.domain_bry = domain_bry;
	}
	public byte[] Domain_bry() {return domain_bry;} private final    byte[] domain_bry;
	public boolean Has(byte[] page_db) {
		return wildcard_exists ? true : hash.Has(page_db);
	}
	public void Add(byte[] page_db) {
		if (Bry_.Eq(page_db, Byte_ascii.Star_bry)) {
			wildcard_exists = true;
		}
		else {
			hash.Add_if_dupe_use_1st(page_db, page_db);
		}
	}
}
