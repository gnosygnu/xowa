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
package gplx.xowa.addons.wikis.searchs.searchers; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.addons.wikis.searchs.gui.htmlbars.*;
import gplx.xowa.addons.wikis.searchs.searchers.crts.*;
public class Srch_search_qry {
	public Srch_search_qry(byte tid, Srch_ns_mgr ns_mgr, Srch_search_phrase phrase, int slab_bgn, int slab_end) {
		this.Tid = tid;
		this.Ns_mgr = ns_mgr;
		this.Phrase = phrase;
		this.Slab_bgn = slab_bgn;
		this.Slab_end = slab_end;
	}
	public final    byte				Tid;
	public final    Srch_ns_mgr			Ns_mgr;
	public final    Srch_search_phrase	Phrase;
	public final    int					Slab_bgn;		// EX: 0
	public final    int					Slab_end;		// EX: 20

	public static final byte Tid_len = 4, Tid__url_bar = 0, Tid__suggest_box = 1, Tid__search_page = 2, Tid__android = 3;
	public static Srch_search_qry New__url_bar(Xow_wiki wiki, Srch_ns_mgr ns_mgr, Srch_crt_scanner_syms syms, boolean auto_wildcard, int max_results, byte[] search_orig) {
		return new Srch_search_qry(Tid__url_bar, ns_mgr, Srch_search_phrase.New(wiki.Case_mgr(), syms, auto_wildcard, search_orig), 0, max_results);
	}
	public static Srch_search_qry New__suggest_box(Xow_wiki wiki, Srch_ns_mgr ns_mgr, boolean auto_wildcard, int results_max, byte[] search_orig) {
		return new Srch_search_qry(Tid__suggest_box, ns_mgr, Srch_search_phrase.New(wiki.Case_mgr(), Srch_crt_scanner_syms.Dflt, auto_wildcard, search_orig), 0, results_max);
	}
	public static Srch_search_qry New__search_page(Xow_domain_itm[] domains, Xow_wiki wiki, Srch_ns_mgr ns_mgr, boolean auto_wildcard, byte[] search_orig, int slab_idx, int slab_len) {
		int slab_bgn = slab_idx * slab_len;
		int slab_end = slab_bgn + slab_len;
		return new Srch_search_qry(Tid__search_page, ns_mgr, Srch_search_phrase.New(wiki.Case_mgr(), Srch_crt_scanner_syms.Dflt, auto_wildcard, search_orig), slab_bgn, slab_end);
	}
	public static Srch_search_qry New__drd(Xow_wiki wiki, Srch_ns_mgr ns_mgr, byte[] search_orig, int slab_bgn, int slab_end) {
		return new Srch_search_qry(Tid__android, ns_mgr, Srch_search_phrase.New(wiki.Case_mgr(), Srch_crt_scanner_syms.Dflt, Bool_.Y, search_orig), slab_bgn, slab_end);
	}
}
