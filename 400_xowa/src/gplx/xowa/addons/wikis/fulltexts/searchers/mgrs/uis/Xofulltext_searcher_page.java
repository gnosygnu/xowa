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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*;
public class Xofulltext_searcher_page {
	public Xofulltext_searcher_page(int query_id, String wiki_domain, int page_id, String page_title, boolean expand_matches_section) {
		this.query_id = query_id;
		this.wiki_domain = wiki_domain;
		this.page_id = page_id;
		this.page_title = page_title;
		this.expand_matches_section = expand_matches_section;
	}
	public int Query_id() {return query_id;} private final    int query_id;
	public String Wiki_domain() {return wiki_domain;} private final    String wiki_domain;
	public int Page_id() {return page_id;} private final    int page_id;
	public String Page_title() {return page_title;} private final    String page_title;
	public boolean Expand_matches_section() {return expand_matches_section;} private final    boolean expand_matches_section;
}
