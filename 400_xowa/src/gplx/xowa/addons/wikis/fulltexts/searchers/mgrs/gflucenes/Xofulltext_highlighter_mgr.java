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
package gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.gflucenes; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.searchers.*; import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.*;
import gplx.gflucene.core.*;
import gplx.gflucene.highlighters.*;
import gplx.gflucene.searchers.*;
import gplx.xowa.htmls.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.wikis.fulltexts.searchers.mgrs.uis.*;
import gplx.xowa.addons.wikis.fulltexts.core.*;
class Xofulltext_highlighter_mgr implements Gfo_invk {
	private final    Xofulltext_searcher_ui ui;
	private final    Xow_wiki wiki;
	private final    Xofulltext_args_qry searcher_args;
	private final    Xofulltext_args_wiki wiki_args;
	private final    Gflucene_analyzer_data analyzer_data;
	private final    Gflucene_searcher_qry searcher_data;
	private final    Gflucene_highlighter_mgr highlighter_mgr = new Gflucene_highlighter_mgr();
	private final    Xoh_page hpg = new Xoh_page();
	private final    Ordered_hash list;
	private final    Xofulltext_extractor extractor = new Xofulltext_extractor();
	public Xofulltext_highlighter_mgr(Xofulltext_searcher_ui ui, Xow_wiki wiki, Xofulltext_args_qry searcher_args, Xofulltext_args_wiki wiki_args, Gflucene_analyzer_data analyzer_data, Gflucene_searcher_qry searcher_data, Ordered_hash list) {
		this.ui = ui;
		this.wiki = wiki;
		this.searcher_args = searcher_args;
		this.wiki_args = wiki_args;
		this.analyzer_data = analyzer_data;
		this.searcher_data = searcher_data;
		this.list = list;
	}
	public void Highlight_list() {
		// init highlighter
		highlighter_mgr.Init(new Gflucene_index_data(analyzer_data, "")); // NOTE: index_dir not needed for highlighter

		// loop items
		int len = list.Len();
		for (int i = 0; i < len; i++) {
			if (searcher_args.Canceled()) return;
			Gflucene_doc_data item = (Gflucene_doc_data)list.Get_at(i);
			try {
				Highlight_item(item);
			} catch (Exception e) {
				Gfo_usr_dlg_.Instance.Warn_many("", "", "search.highlight: failed to highlight lines in page; page=~{0} err=~{1}", item.page_id, Err_.Message_gplx_log(e));
			}
		}

		// term highlighter
		highlighter_mgr.Term();
	}
	private void Highlight_item(Gflucene_doc_data item) {
		// init hpg
		Xoa_ttl page_ttl = wiki.Ttl_parse(item.page_full_db);
		Xoa_url page_url = wiki.Utl__url_parser().Parse(page_ttl.Full_db());
		hpg.Ctor_by_hview(wiki, page_url, page_ttl, item.page_id);

		// load db.html.html
		wiki.Html__hdump_mgr().Load_mgr().Load_by_xowh(hpg, page_ttl, false); // don't load categories for perf reasons
		byte[] html = hpg.Db().Html().Html_bry();
		html = extractor.Extract(html);
		item.body = String_.new_u8(html);

		// loop pages
		int page_id = item.page_id;
		Gflucene_highlighter_item[] lines = highlighter_mgr.Exec(searcher_data, item);
		for (Gflucene_highlighter_item line : lines) {
			ui.Send_line_add(true, wiki_args.show_all_snips, searcher_args.qry_id, wiki.Domain_bry(), page_id, line.num, Bry_.new_u8(line.text));
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__highlight))		this.Highlight_list();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final String Invk__highlight = "highlight";
}
