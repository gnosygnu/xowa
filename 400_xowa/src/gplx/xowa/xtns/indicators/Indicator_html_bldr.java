/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.indicators;

import gplx.Bool_;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Int_;
import gplx.Ordered_hash;
import gplx.Ordered_hash_;
import gplx.String_;
import gplx.core.brys.fmtrs.Bry_fmtr;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xow_wiki;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.Xoh_page;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.htmls.hxtns.blobs.Hxtn_blob_tbl;
import gplx.xowa.htmls.hxtns.pages.Hxtn_page_mgr;
import gplx.xowa.parsers.Xop_parser_;

public class Indicator_html_bldr implements gplx.core.brys.Bfr_arg {
	private Indicator_html_bldr_itm bldr_itm = new Indicator_html_bldr_itm();
	private Ordered_hash list = Ordered_hash_.New();
	public void Enabled_(boolean v) {enabled = v;} private boolean enabled = Bool_.Y;
	public void Clear() {
		enabled = Bool_.Y;
		list.Clear();
	}
	public int Count() {return list.Count();}
	public boolean Has(String key) {return list.Has(key);}
	public void Add(Indicator_xnde xnde) {
		if (!enabled) return;				// do not add if disabled; called from <page>; PAGE:en.s:The_Parochial_System_(Wilberforce,_1838); DATE:2015-04-29
		list.Add_if_dupe_use_nth(xnde.Name(), xnde);	// Add_if_dupe_use_nth: 2nd indicator overwrites 1st; DATE:2015-04-29
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (list.Count() == 0) return;		// do not build html if no items; DATE:2015-04-29
		bldr_itm.Init(list);
		fmtr_grp.Bld_bfr_many(bfr, bldr_itm);
	}
	private static final Bry_fmtr
	  fmtr_grp = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <div class='mw-indicators'>~{itms}"
	, "  </div>"
	), "itms")
	;

	public void HxtnSave(Xowe_wiki wiki, Hxtn_page_mgr hxtnPageMgr, Xoae_page page, int pageId) {
		// exit if empty
		int len = list.Count();
		if (len == 0) return;

		// reparse html to generate xoimg attribute b/c indicators are parsed differently due to location above the "mw-content-text" div
		for (int i = 0; i < len; i++) {
			Indicator_xnde xnde = (Indicator_xnde)list.Get_at(i);
			byte[] html = Xop_parser_.Parse_text_to_html(wiki, wiki.Parser_mgr().Ctx(), Xoh_wtr_ctx.Hdump, page, xnde.GetHdumpSrc(), true);
			xnde.Html_(html);
		}

		// serialize and save to db
		byte[] indicators = IndicatorSerialCore.Save(list);
		hxtnPageMgr.Page_tbl__insert(pageId, Hxtn_page_mgr.Id__indicators, pageId);
		hxtnPageMgr.Blob_tbl__insert(Hxtn_blob_tbl.Blob_tid__wtxt, Hxtn_page_mgr.Id__indicators, pageId, indicators);
	}

	public void Deserialise(Xow_wiki wiki, Xoh_page hpg, byte[] data) {
		// exit if empty
		if (Bry_.Len_eq_0(data)) return;

		// deserialize data
		this.list = IndicatorSerialCore.Load(data);

		// reparse html to convert xoimg attribute to file
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Indicator_xnde xnde = (Indicator_xnde)list.Get_at(i);
			byte[] html = wiki.Html__hdump_mgr().Load_mgr().Make_mgr().Parse(xnde.Html(), wiki, hpg);
			xnde.Html_(html);
		}
	}
}
class Indicator_html_bldr_itm implements gplx.core.brys.Bfr_arg {
	private Ordered_hash list;
	public void Init(Ordered_hash list) {this.list = list;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int list_len = list.Count();
		for (int i = list_len - 1; i > -1; --i) {	// reverse order
			Indicator_xnde xnde = (Indicator_xnde)list.Get_at(i);
			fmtr_itm.Bld_bfr_many(bfr, xnde.Name(), xnde.Html());
		}
	}
	private static final Bry_fmtr fmtr_itm = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "    <div id='mw-indicator-~{name}' class='mw-indicator'>~{html}</div>"
	), "name", "html")
	;
}
