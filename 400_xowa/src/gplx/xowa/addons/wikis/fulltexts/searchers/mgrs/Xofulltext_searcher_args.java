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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*;
import gplx.langs.jsons.*;
public class Xofulltext_searcher_args {
	public boolean case_match;
	public boolean auto_wildcard_bgn;
	public boolean auto_wildcard_end;
	public boolean expand_matches_section;
	public boolean show_all_matches;
	public int max_pages_per_wiki;
	public byte[] wikis;
	public byte[] query;
	public String namespaces;
	public int query_id;
	public String page_guid;
	public static Xofulltext_searcher_args New_by_json(Json_nde args) {
		Xofulltext_searcher_args rv = new Xofulltext_searcher_args();
		rv.case_match = args.Get_as_bool_or("case_match", false);
		rv.auto_wildcard_bgn = args.Get_as_bool_or("auto_wildcard_bgn", false);
		rv.auto_wildcard_end = args.Get_as_bool_or("auto_wildcard_end", false);
		rv.expand_matches_section = args.Get_as_bool_or("expand_matches_section", false);
		rv.show_all_matches = args.Get_as_bool_or("show_all_matches", false);
		rv.max_pages_per_wiki = args.Get_as_int_or("max_pages_per_wiki", 25);
		rv.wikis = args.Get_as_bry("wikis");
		rv.query = args.Get_as_bry("query");
		rv.namespaces = args.Get_as_str("namespaces");
		rv.page_guid = args.Get_as_str("page_guid");
		return rv;
	}
}
