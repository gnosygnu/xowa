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
import gplx.json.*;
public class Wdata_hwtr_mgr {
	private final Bry_bfr bfr = Bry_bfr.reset_(Io_mgr.Len_mb);
	@gplx.Internal protected Wdata_fmtr__toc_div	Fmtr_toc() {return fmtr_toc;} private final Wdata_fmtr__toc_div fmtr_toc = new Wdata_fmtr__toc_div();
	@gplx.Internal protected Wdata_fmtr__json		Fmtr_json() {return fmtr_json;} private final Wdata_fmtr__json fmtr_json = new Wdata_fmtr__json();
	@gplx.Internal protected Wdata_fmtr__claim_tbl	Fmtr_claim() {return fmtr_claim;} private final Wdata_fmtr__claim_tbl fmtr_claim = new Wdata_fmtr__claim_tbl();
	private final Wdata_fmtr__langtext_tbl fmtr_label = new Wdata_fmtr__langtext_tbl();
	private final Wdata_fmtr__langtext_tbl fmtr_descr = new Wdata_fmtr__langtext_tbl();
	private final Wdata_fmtr__langtext_tbl fmtr_alias = new Wdata_fmtr__langtext_tbl();
	private final Wdata_fmtr__sitelink_tbl fmtr_slink = new Wdata_fmtr__sitelink_tbl();
	public Bry_fmtr Fmtr_main() {return fmtr_main;} private final Bry_fmtr fmtr_main = Bry_fmtr.new_("~{toc}~{claims}~{labels}~{aliases}~{descriptions}~{links}~{json}", "toc", "claims", "labels", "aliases", "descriptions", "links", "json");
	public Wdata_hwtr_msgs Msgs() {return msgs;} private Wdata_hwtr_msgs msgs;
	@gplx.Internal protected Wdata_lbl_mgr Lbl_mgr() {return lbl_mgr;} private final Wdata_lbl_mgr lbl_mgr = new Wdata_lbl_mgr();
	@gplx.Internal protected void Init_by_ctor(Json_parser jdoc_parser, Wdata_lbl_wkr lbl_wkr, Url_encoder href_encoder) {
		lbl_mgr.Wkr_(lbl_wkr);
		fmtr_toc.Init_by_ctor(href_encoder);
		fmtr_claim.Init_by_ctor(lbl_mgr);
		fmtr_label.Init_by_ctor(new Wdata_fmtr__langtext_row());
		fmtr_alias.Init_by_ctor(new Wdata_fmtr__alias_row());
		fmtr_descr.Init_by_ctor(new Wdata_fmtr__langtext_row());
		fmtr_slink.Init_by_ctor(lbl_mgr);
		fmtr_json.Init_by_ctor(jdoc_parser);
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		this.msgs = msgs;
		fmtr_claim.Init_by_lang(msgs, msgs.Claim_tbl_hdr(), msgs.Claim_col_hdr_prop_id(), msgs.Claim_col_hdr_prop_name(), msgs.Claim_col_hdr_val(), msgs.Claim_col_hdr_ref(), msgs.Claim_col_hdr_qual(), msgs.Claim_col_hdr_rank());
		fmtr_label.Init_by_lang(msgs.Label_tbl_hdr(), msgs.Label_col_hdr_lang(), msgs.Label_col_hdr_text());
		fmtr_alias.Init_by_lang(msgs.Alias_tbl_hdr(), msgs.Alias_col_hdr_lang(), msgs.Alias_col_hdr_text());
		fmtr_descr.Init_by_lang(msgs.Descr_tbl_hdr(), msgs.Descr_col_hdr_lang(), msgs.Descr_col_hdr_text());
		fmtr_slink.Init_by_lang(msgs);
		fmtr_json.Init_by_lang(msgs.Json_tbl_hdr());
	}
	public void Init_by_wdoc(Wdata_doc wdoc) {
		fmtr_toc  .Init_by_wdoc(wdoc);
		fmtr_claim.Init_by_wdoc(fmtr_toc, wdoc.Claim_list());
		fmtr_label.Init_by_wdoc(fmtr_toc, wdoc.Label_list());
		fmtr_alias.Init_by_wdoc(fmtr_toc, wdoc.Alias_list());
		fmtr_descr.Init_by_wdoc(fmtr_toc, wdoc.Descr_list());
		fmtr_slink.Init_by_wdoc(fmtr_toc, wdoc.Slink_list());
		fmtr_json.Init_by_wdoc (fmtr_toc, wdoc.Jdoc());
		lbl_mgr.Gather_labels(wdoc);
	}
	public byte[] Write(Wdata_doc wdoc) {
		fmtr_main.Bld_bfr_many(bfr, "", fmtr_claim, fmtr_label, fmtr_alias, fmtr_descr, fmtr_slink, fmtr_json);
		return bfr.XtoAryAndClear();
	}
}
