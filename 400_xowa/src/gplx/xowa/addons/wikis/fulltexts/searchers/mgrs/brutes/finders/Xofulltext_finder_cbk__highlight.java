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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.brutes.finders;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
public class Xofulltext_finder_cbk__highlight implements Xofulltext_finder_cbk {
	private Xofulltext_searcher_ui ui;
	private Xow_wiki wiki;
	private int qry_id;
	private int page_id;
	private final BryWtr tmp_bfr = BryWtr.New();
	public int found;
	private boolean show_all_matches;
	public byte[] Page_ttl() {return page_ttl;} private byte[] page_ttl;
	public void Init(Xofulltext_searcher_ui ui, int qry_id, Xow_wiki wiki, int page_id, byte[] page_ttl, boolean show_all_matches) {
		this.ui = ui;
		this.qry_id = qry_id;
		this.wiki = wiki;
		this.page_id = page_id;
		this.page_ttl= page_ttl;
		this.show_all_matches = show_all_matches;
		found = 0;
	}
	public void Process_item_found(byte[] src, int hook_bgn, int hook_end, int word_bgn, int word_end, Xofulltext_word_node term) {
		// get snip bounds by finding flanking 50 chars and then expanding to word-bounds
		int snip_bgn = hook_bgn - 50;
		if (snip_bgn < 0)
			snip_bgn = 0;
		else {
			snip_bgn = BryFind.FindBwdWs(src, snip_bgn, 0) + 1;
		}
		int snip_end = hook_end + 50;
		if (snip_end >= src.length)
			snip_end = src.length;
		else {
			snip_end = BryFind.FindFwdUntilWs(src, snip_end, src.length);
			if (snip_end == BryFind.NotFound) { // when snip_end == src.length
				snip_end = src.length;
			}
		}

		// build snip
		Add_snip(tmp_bfr, src, snip_bgn, hook_bgn);
		tmp_bfr.AddStrA7("<span class='snip_highlight'>");
		Add_snip(tmp_bfr, src, hook_bgn, hook_end);
		tmp_bfr.AddStrA7("</span>");
		Add_snip(tmp_bfr, src, hook_end, snip_end);

		// send notification
		byte[] line_html = tmp_bfr.ToBryAndClear();
		ui.Send_line_add(true, show_all_matches, qry_id, wiki.Domain_bry(), page_id, found, line_html);
		found++;
	}
	private static final byte[] Angle_bgn_escaped = BryUtl.NewA7("&lt;");
	private void Add_snip(BryWtr bfr, byte[] src, int bgn, int end) {
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.AngleBgn:
					bfr.Add(Angle_bgn_escaped);
					break;
				case AsciiByte.Nl:
					bfr.Add(gplx.langs.htmls.Gfh_tag_.Br_inl);
					break;
				default:
					bfr.AddByte(b);
					break;
			}
		}
	}
	public void Process_page_done(byte[] src, Xofulltext_word_node tree_root) {}
}
