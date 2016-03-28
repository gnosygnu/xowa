/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.searchs.specials.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.specials.*;
import gplx.xowa.htmls.core.htmls.utls.*; import gplx.langs.htmls.*;
import gplx.xowa.addons.searchs.searchers.rslts.*;
public class Srch_html_row_bldr implements gplx.core.brys.Bfr_arg {
	private final    Xoh_lnki_bldr lnki_bldr;
	private Srch_rslt_list rslt_list; private int slab_bgn, slab_end;
	private final    Object thread_lock = new Object();
	public Srch_html_row_bldr(Xoh_lnki_bldr lnki_bldr) {this.lnki_bldr = lnki_bldr;}
	public Srch_html_row_bldr Init(Srch_rslt_list rslt_list, int slab_bgn, int slab_end) {this.rslt_list = rslt_list; this.slab_bgn = slab_bgn; this.slab_end = slab_end; return this;}
	public void Bfr_arg__add(Bry_bfr bfr) { // <a href="/wiki/A" title="A" class="xowa-visited">A</a>
		int rslts_len = rslt_list.Len();
		for (int i = slab_bgn; i < slab_end; ++i) {
			if (i >= rslts_len) return;
			Srch_rslt_row row = rslt_list.Get_at(i);
			Bld_html(bfr, row);
		}
	}
	public void Bld_html(Bry_bfr bfr, Srch_rslt_row row) {
		synchronized (thread_lock) {
			lnki_bldr.Href_(row.Wiki_bry, row.Page_ttl);
			lnki_bldr.Title_(row.Page_ttl.Full_txt_w_ttl_case());
			lnki_bldr.Caption_direct_(row.Page_ttl_display(Bool_.Y));
			fmtr.Bld_many(bfr, Gfh_utl.Encode_id_as_str(row.Key), row.Page_score, lnki_bldr.Bld_to_bry());
		}
	}
	public Bry_fmt Fmtr() {return fmtr;} private final    Bry_fmt fmtr = Bry_fmt.Auto(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr id='~{page_key}'>"
	, "    <td style='padding-right:5px; vertical-align:top; text-align:right;'>~{page_len}"
	, "    </td>"
	, "    <td style='padding-left:5px; vertical-align:top;'>~{lnki}"	// SERVER:"<a href='"; DATE:2015-04-16
	, "    </td>"
	, "  </tr>"
	));
}
