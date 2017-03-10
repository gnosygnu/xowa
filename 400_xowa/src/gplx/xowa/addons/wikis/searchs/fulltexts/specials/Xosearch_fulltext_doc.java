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
package gplx.xowa.addons.wikis.searchs.fulltexts.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.langs.mustaches.*;
public class Xosearch_fulltext_doc implements Mustache_doc_itm {
	private final    byte[] query;
	private final    boolean case_match, auto_wildcard_bgn, auto_wildcard_end, expand_matches_section, show_all_matches;
	private final    int max_pages_per_wiki;
	private final    String wikis, namespaces;
	public Xosearch_fulltext_doc
		( byte[] query, boolean case_match, boolean auto_wildcard_bgn, boolean auto_wildcard_end
		, boolean expand_matches_section, boolean show_all_matches
		, int max_pages_per_wiki
		, String wikis, String namespaces) {
		this.query = query;
		this.case_match = case_match;
		this.auto_wildcard_bgn = auto_wildcard_bgn;
		this.auto_wildcard_end = auto_wildcard_end;
		this.expand_matches_section = expand_matches_section;
		this.show_all_matches = show_all_matches;
		this.max_pages_per_wiki = max_pages_per_wiki;
		this.wikis = wikis;
		this.namespaces = namespaces;
	}
	public boolean Mustache__write(String key, Mustache_bfr bfr) {
		if		(String_.Eq(key, "wikis"))
			bfr.Add_str_u8(wikis);
		else if	(String_.Eq(key, "namespaces"))
			bfr.Add_str_u8(namespaces);
		else if	(String_.Eq(key, "max_pages_per_wiki"))
			bfr.Add_int(max_pages_per_wiki);
		else if	(String_.Eq(key, "query"))
			bfr.Add_bry(query);
		else
			return false;
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		if		(String_.Eq(key, "case_match"))
			return Mustache_doc_itm_.Ary__bool(case_match);
		else if	(String_.Eq(key, "auto_wildcard_bgn"))
			return Mustache_doc_itm_.Ary__bool(auto_wildcard_bgn);
		else if	(String_.Eq(key, "auto_wildcard_end"))
			return Mustache_doc_itm_.Ary__bool(auto_wildcard_end);
		else if	(String_.Eq(key, "expand_matches_section"))
			return Mustache_doc_itm_.Ary__bool(expand_matches_section);
		else if	(String_.Eq(key, "show_all_matches"))
			return Mustache_doc_itm_.Ary__bool(show_all_matches);
		return Mustache_doc_itm_.Ary__empty;
	}
}
