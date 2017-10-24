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
package gplx.xowa.xtns.wbases.hwtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.jsons.*; import gplx.xowa.htmls.*; import gplx.xowa.apps.apis.xowa.html.*;
class Wdata_fmtr__json implements gplx.core.brys.Bfr_arg {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
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
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (jdoc == null) return;	// TEST: wdoc doesn't have jdoc
		jdoc.Root_nde().Print_as_json(tmp_bfr, 0);
		fmtr.Bld_bfr_many(bfr, toc_data.Href(), toc_data.Text(), toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), tmp_bfr.To_bry_and_clear());
	}
	private final    Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <h2 class='wb-section-heading' id='~{hdr_href}'>~{hdr_text}~{toggle_btn}</h2>"
	, "  <div class='visualClear'></div>"
	, "  <pre~{toggle_hdr}>~{json}"
	, "  </pre>"
	, "  </div>"
	), "hdr_href", "hdr_text", "toggle_btn", "toggle_hdr", "json");
}
