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
package gplx.xowa.addons.wikis.searchs.fulltexts.finders; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.fulltexts.*;
import gplx.xowa.guis.cbks.*;
public class Xosearch_finder_cbk__highlight implements Xosearch_finder_cbk {
	private final    Xog_cbk_trg cbk_trg;
	private final    Xoa_app app;
	private Xow_wiki wiki;
	private int page_id;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public int found;
	private int max_snips_per_page;
	public Xosearch_finder_cbk__highlight(Xoa_app app, Xog_cbk_trg cbk_trg) {
		this.app = app;
		this.cbk_trg = cbk_trg;
	}
	public void Init(Xow_wiki wiki, int page_id, int max_snips_per_page) {
		this.wiki = wiki;
		this.page_id = page_id;
		this.max_snips_per_page = max_snips_per_page;
		found = 0;
	}
	public void Process_item_found(byte[] src, int hook_bgn, int hook_end, int word_bgn, int word_end, Xosearch_word_node term) {
		++found;
		if (found <= max_snips_per_page) {
			// get snip bounds by finding flanking 50 chars and then expanding to word-bounds
			int snip_bgn = hook_bgn - 50;
			if (snip_bgn < 0)
				snip_bgn = 0;
			else {
				snip_bgn = Bry_find_.Find_bwd_ws(src, snip_bgn, 0) + 1;
			}
			int snip_end = hook_end + 50;
			if (snip_end >= src.length)
				snip_end = src.length;
			else {
				snip_end = Bry_find_.Find_fwd_until_ws(src, snip_end, src.length);
				if (snip_end == Bry_find_.Not_found) { // when snip_end == src.length
					snip_end = src.length;
				}
			}

			// build snip
			Add_snip(tmp_bfr, src, snip_bgn, hook_bgn);
			tmp_bfr.Add_str_a7("<span class='snip_highlight'>");
			Add_snip(tmp_bfr, src, hook_bgn, hook_end);
			tmp_bfr.Add_str_a7("</span>");
			Add_snip(tmp_bfr, src, hook_end, snip_end);

			// send notification
			app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__line__add__recv", gplx.core.gfobjs.Gfobj_nde.New()
				.Add_bry("wiki", wiki.Domain_bry())
				.Add_int("page_id", page_id)
				.Add_int("line", found)
				.Add_bry("html", tmp_bfr.To_bry_and_clear())
				);
		}
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.search_fulltext.results__page__update__recv", gplx.core.gfobjs.Gfobj_nde.New()
			.Add_bry("wiki", wiki.Domain_bry())
			.Add_int("page_id", page_id)
			.Add_int("found", found)
			);
	}
	private static final    byte[] Angle_bgn_escaped = Bry_.new_a7("&lt;");
	private void Add_snip(Bry_bfr bfr, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Angle_bgn:
					bfr.Add(Angle_bgn_escaped);
					break;
				case Byte_ascii.Nl:
					bfr.Add(gplx.langs.htmls.Gfh_tag_.Br_inl);
					break;
				default:
					bfr.Add_byte(b);
					break;
			}
		}
	}
	public void Process_page_done(byte[] src, Xosearch_word_node tree_root) {}
}
