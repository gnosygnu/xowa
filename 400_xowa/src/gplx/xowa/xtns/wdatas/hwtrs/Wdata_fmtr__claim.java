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
import gplx.core.brys.fmtrs.*;
import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.apps.apis.xowa.html.*;
class Wdata_fmtr__claim_grp implements gplx.core.brys.Bfr_arg {
	private Wdata_fmtr__claim_tbl fmtr_tbl = new Wdata_fmtr__claim_tbl(); private boolean is_empty;
	private Xoapi_toggle_itm toggle_itm;
	private Wdata_toc_data toc_data;
	public void Init_by_ctor(Wdata_toc_data toc_data, Xoapi_toggle_mgr toggle_mgr, Wdata_lbl_mgr lbl_mgr) {this.toc_data = toc_data; this.toggle_itm = toggle_mgr.Get_or_new("wikidatawiki-claim"); fmtr_tbl.Init_by_ctor(lbl_mgr);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		toc_data.Orig_(msgs.Claim_tbl_hdr());
		toggle_itm.Init_msgs(msgs.Toggle_title_y(), msgs.Toggle_title_n());
		fmtr_tbl.Init_by_lang(msgs);
	}
	public void Init_by_wdoc(byte[] ttl, Ordered_hash list) {
		int list_count = list.Count();
		this.is_empty = list.Count() == 0; if (is_empty) return;
		toc_data.Make(list_count);
		fmtr_tbl.Init_by_wdoc(ttl, list);
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (is_empty) return;
		fmtr.Bld_bfr_many(bfr, toc_data.Href(), toc_data.Text(), toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), fmtr_tbl);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <h2 class='wb-section-heading' dir='auto' id='~{hdr_href}'>~{hdr_text}~{toggle_btn}</h2>"
	, "  <div class='wb-claimlistview'~{toggle_hdr}>"
	, "    <div class='wikibase-statementgrouplistview'>"
	, "      <div class='wikibase-listview'>~{tbls}"
	, "      </div>"
	, "    </div>"
	, "  </div>"
	), "hdr_href", "hdr_text", "toggle_btn", "toggle_hdr", "tbls");
}
class Wdata_fmtr__claim_tbl implements gplx.core.brys.Bfr_arg {
	private Wdata_fmtr__claim_row fmtr_row = new Wdata_fmtr__claim_row(); private Wdata_lbl_mgr lbl_mgr; 
	private Ordered_hash list; private byte[] ttl;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_mgr) {this.lbl_mgr = lbl_mgr; fmtr_row.Init_by_ctor(lbl_mgr);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		fmtr_row.Init_by_lang(msgs);
	}
	public void Init_by_wdoc(byte[] ttl, Ordered_hash list) {
		this.list = list;
		this.ttl = ttl;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_claim_grp grp = (Wdata_claim_grp)list.Get_at(i);
			if (grp.Len() == 0) continue;	// NOTE: group will be empty when claims are empty; EX: "claims": []; PAGE:wd.p:585; DATE:2014-10-03
			int pid = grp.Id();
			byte[] pid_lbl = lbl_mgr.Get_text__pid(pid);
			fmtr_row.Init_by_grp(ttl, grp);
			fmtr.Bld_bfr_many(bfr, pid, pid_lbl, fmtr_row);				
		}
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "            <div id='P~{pid}' class='wikibase-statementgroupview'>"
	, "              <div class='wikibase-statementgroupview-property'>"
	, "                <div class='wikibase-statementgroupview-property-label' dir='auto'>"
	, "                  P~{pid}&nbsp;-&nbsp;<a href='/wiki/Property:P~{pid}' title='Property:P~{pid}'>~{pid_lbl}</a>"
	, "                </div>"
	, "              </div>~{itms}"
	, "            </div>"
	), "pid", "pid_lbl", "itms");
}
class Wdata_fmtr__claim_row implements gplx.core.brys.Bfr_arg {
	private byte[] ttl;
	private Wdata_visitor__html_wtr claim_html_wtr = new Wdata_visitor__html_wtr();
	private Wdata_fmtr__qual_tbl fmtr_qual = new Wdata_fmtr__qual_tbl();
	private Wdata_fmtr__ref_tbl fmtr_ref = new Wdata_fmtr__ref_tbl();
	private Wdata_lbl_mgr lbl_mgr; private Wdata_hwtr_msgs msgs;
	private Wdata_claim_grp claim_grp; private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public void Init_by_ctor(Wdata_lbl_mgr lbl_mgr) {
		this.lbl_mgr = lbl_mgr;
		fmtr_qual.Init_by_ctor(lbl_mgr);
		fmtr_ref.Init_by_ctor(lbl_mgr);
	}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		this.msgs = msgs;
		fmtr_qual.Init_by_lang(msgs);
		fmtr_ref.Init_by_lang(msgs);
	}
	public void Init_by_grp(byte[] ttl, Wdata_claim_grp claim_grp) {
		this.ttl = ttl; this.claim_grp = claim_grp;
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = claim_grp.Len();
		claim_html_wtr.Init(ttl, tmp_bfr, msgs, lbl_mgr);
		for (int i = 0; i < len; ++i) {
			Wdata_claim_itm_core itm = claim_grp.Get_at(i);
			itm.Welcome(claim_html_wtr);
			byte[] val = tmp_bfr.To_bry_and_clear();
			fmtr_qual.Init_by_claim(ttl, itm);
			fmtr_ref.Init_by_claim(ttl, itm);
			row_fmtr.Bld_bfr_many(bfr, Wdata_dict_rank.Xto_str(itm.Rank_tid()), val, fmtr_qual, fmtr_ref);
		}
	}
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "          <div class='wikibase-statementlistview'>"
	, "            <div class='wikibase-statementlistview-listview'>"
	, "              <div class='wikibase-statementview wikibase-statement'>"
	, "                <div class='wikibase-statementview-rankselector'>"
	, "                  <div class='wikibase-rankselector ui-state-disabled'>"
	, "                    <span class='ui-icon ui-icon-rankselector wikibase-rankselector-~{rank_name}' title='~{rank_name} rank'/>"
	, "                  </div>"
	, "                </div>"
	, "                <div class='wikibase-statementview-mainsnak-container'>"	// omit -Q2$e8ba1188-4aec-9e37-a75e-f79466c1913e
	, "                  <div class='wikibase-statementview-mainsnak' dir='auto'>"
	, "                    <div class='wikibase-snakview'>"
	, "                      <div class='wikibase-snakview-property-container'>"
	, "                        <div class='wikibase-snakview-property' dir='auto'></div>"
	, "                      </div>"
	, "                      <div class='wikibase-snakview-value-container' dir='auto'>"
	, "                        <div class='wikibase-snakview-typeselector'></div>"
	, "                        <div class='wikibase-snakview-value wikibase-snakview-variation-valuesnak'>~{value}"
	, "                        </div>"
	, "                      </div>"
	, "                    </div>"
	, "                  </div>~{qualifiers}"
	, "                </div>~{references}"
	, "              </div>"
	, "            </div>"
	, "          </div>"
	), "rank_name", "value", "qualifiers", "references"
	);
}
class Wdata_fmtr__qual_tbl implements gplx.core.brys.Bfr_arg {
	private Wdata_fmtr__qual_row fmtr_row = new Wdata_fmtr__qual_row(); private Wdata_claim_itm_core claim;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_mgr) {fmtr_row.Init_by_ctor(lbl_mgr);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		fmtr_row.Init_by_lang(msgs);
	}
	public void Init_by_claim(byte[] ttl, Wdata_claim_itm_core claim) {
		this.claim = claim;
		fmtr_row.Init_by_grp(ttl, claim.Qualifiers());
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (claim.Qualifiers() == null || claim.Qualifiers().Len() == 0) return;
		fmtr.Bld_bfr_many(bfr, fmtr_row);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "                  <div class='wikibase-statementview-qualifiers'>"
	, "                    <div class='wikibase-listview'>~{itms}"
	, "                    </div>"
	, "                  </div>"
	), "itms");
}
class Wdata_fmtr__qual_row implements gplx.core.brys.Bfr_arg {
	private byte[] ttl; private Wdata_visitor__html_wtr claim_html_wtr = new Wdata_visitor__html_wtr();
	private Wdata_lbl_mgr lbl_mgr; private Wdata_hwtr_msgs msgs;
	private Wdata_claim_grp_list quals; private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	public void Init_by_ctor(Wdata_lbl_mgr lbl_mgr) {this.lbl_mgr = lbl_mgr;}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {this.msgs = msgs;}
	public void Init_by_grp(byte[] ttl, Wdata_claim_grp_list quals) {this.ttl = ttl; this.quals = quals;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = quals.Len();
		claim_html_wtr.Init(ttl, tmp_bfr, msgs, lbl_mgr);
		for (int i = 0; i < len; ++i) {
			Wdata_claim_grp grp = quals.Get_at(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_core itm = grp.Get_at(j);
				itm.Welcome(claim_html_wtr);
				byte[] val = tmp_bfr.To_bry_and_clear();
				row_fmtr.Bld_bfr_many(bfr, grp.Id(), lbl_mgr.Get_text__pid(grp.Id()), val);
			}
		}
	}
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "                      <div class='wikibase-snaklistview'>"
	, "                        <div class='wikibase-snaklistview-listview'>"
	, "                          <div class='wikibase-snakview'>"
	, "                            <div class='wikibase-snakview-property-container'>"
	, "                              <div class='wikibase-snakview-property' dir='auto'>"
	, "                                <a href='/wiki/Property:P~{pid}' title='Property:P~{pid}'>~{pid_lbl}</a>"
	, "                              </div>"
	, "                            </div>"
	, "                            <div class='wikibase-snakview-value-container' dir='auto'>"
	, "                              <div class='wikibase-snakview-typeselector'></div>"
	, "                              <div class='wikibase-snakview-value wikibase-snakview-variation-valuesnak'>~{value}"
	, "                              </div>"
	, "                            </div>"
	, "                          </div>"
	, "                        </div>"
	, "                      </div>"
	), "pid", "pid_lbl", "value"
	);
}
class Wdata_fmtr__ref_tbl implements gplx.core.brys.Bfr_arg {
	private Wdata_fmtr__ref_row fmtr_row = new Wdata_fmtr__ref_row(); private Wdata_claim_itm_core claim;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_mgr) {fmtr_row.Init_by_ctor(lbl_mgr);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		fmtr_row.Init_by_lang(msgs);
	}
	public void Init_by_claim(byte[] ttl, Wdata_claim_itm_core claim) {
		this.claim = claim;
		fmtr_row.Init_by_grp(ttl, claim.References());
	}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (claim.References() == null) return;
		fmtr.Bld_bfr_many(bfr, fmtr_row);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
//		, "                <div class='wb-statement-references-heading'>1 reference</div>"
	, "                <div class='wikibase-statementview-references'>"
	, "                  <div class='wikibase-listview'>"
	, "                    <div class='wikibase-referenceview'>"	// OMIT: wb-referenceview-8e7d51e38606193465d2a1e9d41ba490e06682a6
	, "                      <div class='wikibase-referenceview-heading'></div>"
	, "                      <div class='wikibase-referenceview-listview'>"
	, "                        <div class='wikibase-snaklistview'>"
	, "                          <div class='wikibase-snaklistview-listview'>~{itms}"
	, "                          </div>"
	, "                        </div>"
	, "                      </div>"
	, "                    </div>"
	, "                  </div>"
	, "                </div>"
	), "itms");
}
class Wdata_fmtr__ref_row implements gplx.core.brys.Bfr_arg {
	private byte[] ttl; private Wdata_visitor__html_wtr claim_html_wtr = new Wdata_visitor__html_wtr();
	private Wdata_lbl_mgr lbl_mgr; private Wdata_hwtr_msgs msgs;
	private Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	private Wdata_references_grp[] ref_grps;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_mgr) {this.lbl_mgr = lbl_mgr;}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {this.msgs = msgs;}
	public void Init_by_grp(byte[] ttl, Wdata_references_grp[] ref_grps) {this.ttl = ttl; this.ref_grps = ref_grps;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		int len = ref_grps.length;
		claim_html_wtr.Init(ttl, tmp_bfr, msgs, lbl_mgr);
		for (int i = 0; i < len; ++i) {
			Wdata_references_grp grp_itm = ref_grps[i];
			Wdata_claim_grp_list grp = grp_itm.References();
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_grp grp2 = grp.Get_at(j);
				int grp2_len = grp2.Len();
				for (int k = 0; k < grp2_len; ++k) {
					Wdata_claim_itm_core itm = grp2.Get_at(k);
					itm.Welcome(claim_html_wtr);
					byte[] val = tmp_bfr.To_bry_and_clear();
					row_fmtr.Bld_bfr_many(bfr, grp2.Id(), lbl_mgr.Get_text__pid(grp2.Id()), val);
				}
			}
		}
	}
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "                            <div class='wikibase-snakview'>"
	, "                              <div class='wikibase-snakview-property-container'>"
	, "                                <div class='wikibase-snakview-property' dir='auto'>"
	, "                                  <a href='/wiki/Property:~{pid}' title='Property:~{pid}'>~{pid_lbl}</a>"
	, "                                </div>"
	, "                              </div>"
	, "                              <div class='wikibase-snakview-value-container' dir='auto'>"
	, "                                <div class='wikibase-snakview-typeselector'></div>"
	, "                                <div class='wikibase-snakview-value wikibase-snakview-variation-valuesnak'>~{value}"
	, "                                </div>"
	, "                              </div>"
	, "                            </div>"
	), "pid", "pid_lbl", "value"
	);
}
