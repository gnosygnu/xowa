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
package gplx.xowa.addons.wikis.searchs.specials.htmls;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.fmts.itms.BryFmt;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.htmls.core.htmls.utls.*; import gplx.langs.htmls.*;
import gplx.xowa.addons.wikis.searchs.searchers.rslts.*;
public class Srch_html_row_bldr implements BryBfrArg {
	private final Xoh_lnki_bldr lnki_bldr;
	private Srch_rslt_list rslt_list; private int slab_bgn, slab_end;
	private final Object thread_lock = new Object();
	public Srch_html_row_bldr(Xoh_lnki_bldr lnki_bldr) {this.lnki_bldr = lnki_bldr;}
	public Srch_html_row_bldr Init(Srch_rslt_list rslt_list, int slab_bgn, int slab_end) {this.rslt_list = rslt_list; this.slab_bgn = slab_bgn; this.slab_end = slab_end; return this;}
	public void AddToBfr(BryWtr bfr) { // <a href="/wiki/A" title="A" class="xowa-visited">A</a>
		int rslts_len = rslt_list.Len();
		for (int i = slab_bgn; i < slab_end; ++i) {
			if (i >= rslts_len) return;
			Srch_rslt_row row = rslt_list.Get_at(i);
			Bld_html(bfr, row);
		}
	}
	public void Bld_html(BryWtr bfr, Srch_rslt_row row) {
		synchronized (thread_lock) {
			lnki_bldr.Href_(row.Wiki_bry, row.Page_ttl);
			lnki_bldr.Title_(row.Page_ttl.Full_txt());
			lnki_bldr.Caption_direct_(row.To_display(Srch_rslt_row.Display_type__special_page));
			fmtr.Bld_many(bfr, Gfh_utl.Encode_id_as_str(row.Key), row.Page_score, lnki_bldr.Bld_to_bry());
		}
	}
	public BryFmt Fmtr() {return fmtr;} private final BryFmt fmtr = BryFmt.Auto(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "  <tr id='~{page_key}'>"
	, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>~{page_len}"
	, "    </td>"
	, "    <td style='padding-left:5px; vertical-align:top;'>~{lnki}"	// SERVER:"<a href='"; DATE:2015-04-16
	, "    </td>"
	, "  </tr>"
	));
}
