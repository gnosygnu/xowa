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
package gplx.xowa.xtns.wdatas.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.langs.jsons.*; import gplx.xowa.html.*; import gplx.xowa.apis.xowa.html.*;
class Wdata_fmtr__json implements Bry_fmtr_arg {
	private final Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	private Xoapi_toggle_itm toggle_itm; private Wdata_toc_data toc_data; private Json_doc jdoc;
	public void Init_by_ctor(Wdata_toc_data toc_data, Xoapi_toggle_mgr toggle_mgr) {
		this.toc_data = toc_data.Itms_len_enable_n_();
		this.toggle_itm = toggle_mgr.Get_or_new("wikidatawiki-json").Html_toggle_hdr_cls_(Bry_.new_a7("overflow:auto;"));
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		toc_data.Orig_(msgs.Json_div_hdr());
		toggle_itm.Init_msgs(msgs.Toggle_title_y(), msgs.Toggle_title_n());
	}
	public void Init_by_wdoc(Json_doc jdoc) {			
		this.jdoc = jdoc;
		toc_data.Make(0);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		if (jdoc == null) return;	// TEST: wdoc doesn't have jdoc
		jdoc.Root_nde().Print_as_json(tmp_bfr, 0);
		fmtr.Bld_bfr_many(bfr, toc_data.Href(), toc_data.Text(), toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), tmp_bfr.Xto_bry_and_clear());
	}
	private final Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <h2 class='wb-section-heading' id='~{hdr_href}'>~{hdr_text}~{toggle_btn}</h2>"
	, "  <div class='visualClear'></div>"
	, "  <pre~{toggle_hdr}>~{json}"
	, "  </pre>"
	, "  </div>"
	), "hdr_href", "hdr_text", "toggle_btn", "toggle_hdr", "json");
}
