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

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Hash_adp_bry;
import gplx.core.primitives.Byte_obj_val;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_parser_;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_owner1;
import gplx.xowa.parsers.xndes.Xop_xnde_tkn;
import gplx.xowa.xtns.Xox_xnde;
import gplx.xowa.xtns.Xox_xnde_;
public class Indicator_xnde implements Xox_xnde, Mwh_atr_itm_owner1 {
	private byte[] src;
	private Xop_xnde_tkn xnde;
	public String Name() {return name;} public void Name_(String v) {this.name = v;} private String name;
	public byte[] Html() {return html;} public void Html_(byte[] v) {this.html = v;} private byte[] html;
	public void Init_for_test(String name, byte[] html) {this.name = name; this.html = html;}	// TEST
	public byte[] GetHdumpSrc() {
		return Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
	}
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		if (xatr_id_obj == null) return;
		Byte_obj_val xatr_id = (Byte_obj_val)xatr_id_obj;
		switch (xatr_id.Val()) {
			case Xatr_name:		this.name = xatr.Val_as_str(); break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		this.xnde = xnde;
		this.src = src;
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		this.html = Xop_parser_.Parse_text_to_html(wiki, ctx, ctx.Page(), Bry_.Mid(src, xnde.Tag_open_end(), xnde.Tag_close_bgn()), false);
		Indicator_html_bldr html_bldr = ctx.Page().Html_data().Indicators();
		if (this.name != null) html_bldr.Add(this);	// NOTE: must do null-check b/c Add will use Name as key for hashtable
	}
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (this.name == null) bfr.Add_str_a7("Error: Page status indicators' name attribute must not be empty.");
	}
	private static final byte Xatr_name = 0;
	private static final    Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7()
	.Add_str_obj("name", Byte_obj_val.new_(Xatr_name));
}
