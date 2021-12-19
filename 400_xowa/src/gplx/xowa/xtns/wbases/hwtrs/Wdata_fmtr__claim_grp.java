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
package gplx.xowa.xtns.wbases.hwtrs;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.xtns.wbases.*;
import gplx.types.custom.brys.fmts.fmtrs.*;
import gplx.xowa.langs.*;
import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*;
import gplx.xowa.apps.apis.xowa.html.*;
class Wdata_fmtr__claim_grp implements BryBfrArg {
	private Wdata_fmtr__claim_tbl fmtr_tbl = new Wdata_fmtr__claim_tbl(); private boolean is_empty;
	private Xoapi_toggle_itm toggle_itm;
	private Wdata_toc_data toc_data;
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_toc_data toc_data, Xoapi_toggle_mgr toggle_mgr, Wdata_lbl_mgr lbl_mgr) {
		this.toc_data = toc_data; this.toggle_itm = toggle_mgr.Get_or_new("wikidatawiki-claim");
		fmtr_tbl.Init_by_ctor(wdata_mgr, lbl_mgr);
	}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {
		toc_data.Orig_(msgs.Claim_tbl_hdr());
		toggle_itm.Init_msgs(msgs.Toggle_title_y(), msgs.Toggle_title_n());
		fmtr_tbl.Init_by_lang(lang, msgs);
	}
	public void Init_by_wdoc(byte[] ttl, Ordered_hash list) {
		int list_count = list.Len();
		this.is_empty = list.Len() == 0; if (is_empty) return;
		toc_data.Make(list_count);
		fmtr_tbl.Init_by_wdoc(ttl, list);
	}
	public void AddToBfr(BryWtr bfr) {
		if (is_empty) return;
		fmtr.BldToBfrMany(bfr, toc_data.Href(), toc_data.Text(), toggle_itm.Html_toggle_btn(), toggle_itm.Html_toggle_hdr(), fmtr_tbl);
	}
	private BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
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
class Wdata_fmtr__claim_tbl implements BryBfrArg {
	private Wdata_fmtr__claim_row fmtr_row = new Wdata_fmtr__claim_row(); private Wdata_lbl_mgr lbl_mgr; 
	private Ordered_hash list; private byte[] ttl;
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_lbl_mgr lbl_mgr) {this.lbl_mgr = lbl_mgr; fmtr_row.Init_by_ctor(wdata_mgr, lbl_mgr);}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {
		fmtr_row.Init_by_lang(lang, msgs);
	}
	public void Init_by_wdoc(byte[] ttl, Ordered_hash list) {
		this.list = list;
		this.ttl = ttl;
	}
	public void AddToBfr(BryWtr bfr) {
		int len = list.Len();
		for (int i = 0; i < len; ++i) {
			Wbase_claim_grp grp = (Wbase_claim_grp)list.GetAt(i);
			if (grp.Len() == 0) continue;	// NOTE: group will be empty when claims are empty; EX: "claims": []; PAGE:wd.p:585; DATE:2014-10-03
			int pid = grp.Id();
			byte[] pid_lbl = lbl_mgr.Get_text__pid(pid);
			fmtr_row.Init_by_grp(ttl, grp);
			fmtr.BldToBfrMany(bfr, pid, pid_lbl, fmtr_row);
		}
	}
	private BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
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
class Wdata_fmtr__claim_row implements BryBfrArg {
	private byte[] ttl;
	private Wdata_visitor__html_wtr claim_html_wtr = new Wdata_visitor__html_wtr();
	private Wdata_fmtr__qual_tbl fmtr_qual = new Wdata_fmtr__qual_tbl();
	private Wdata_fmtr__ref_tbl fmtr_ref = new Wdata_fmtr__ref_tbl();
	private Wdata_wiki_mgr wdata_mgr; private Wdata_lbl_mgr lbl_mgr; private Wdata_hwtr_msgs msgs;
	private Xol_lang_itm lang;
	private Wbase_claim_grp claim_grp; private BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_lbl_mgr lbl_mgr) {
		this.lbl_mgr = lbl_mgr;
		this.wdata_mgr = wdata_mgr;
		fmtr_qual.Init_by_ctor(wdata_mgr, lbl_mgr);
		fmtr_ref.Init_by_ctor(wdata_mgr, lbl_mgr);
	}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {
		this.lang = lang;
		this.msgs = msgs;
		fmtr_qual.Init_by_lang(lang, msgs);
		fmtr_ref.Init_by_lang(lang, msgs);
	}
	public void Init_by_grp(byte[] ttl, Wbase_claim_grp claim_grp) {
		this.ttl = ttl; this.claim_grp = claim_grp;
	}
	public void AddToBfr(BryWtr bfr) {
		int len = claim_grp.Len();
		claim_html_wtr.Init(tmp_bfr, wdata_mgr, msgs, lbl_mgr, lang, ttl);
		for (int i = 0; i < len; ++i) {
			Wbase_claim_base itm = claim_grp.Get_at(i);
			itm.Welcome(claim_html_wtr);
			byte[] val = tmp_bfr.ToBryAndClear();
			fmtr_qual.Init_by_claim(ttl, itm);
			fmtr_ref.Init_by_claim(ttl, itm);
			row_fmtr.BldToBfrMany(bfr, Wbase_claim_rank_.Reg.Get_str_or_fail(itm.Rank_tid()), val, fmtr_qual, fmtr_ref);
		}
	}
	private BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
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
class Wdata_fmtr__qual_tbl implements BryBfrArg {
	private Wdata_fmtr__qual_row fmtr_row = new Wdata_fmtr__qual_row(); private Wbase_claim_base claim;
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_lbl_mgr lbl_mgr) {fmtr_row.Init_by_ctor(wdata_mgr, lbl_mgr);}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {
		fmtr_row.Init_by_lang(lang, msgs);
	}
	public void Init_by_claim(byte[] ttl, Wbase_claim_base claim) {
		this.claim = claim;
		fmtr_row.Init_by_grp(ttl, claim.Qualifiers());
	}
	public void AddToBfr(BryWtr bfr) {
		if (claim.Qualifiers() == null || claim.Qualifiers().Len() == 0) return;
		fmtr.BldToBfrMany(bfr, fmtr_row);
	}
	private BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "                  <div class='wikibase-statementview-qualifiers'>"
	, "                    <div class='wikibase-listview'>~{itms}"
	, "                    </div>"
	, "                  </div>"
	), "itms");
}
class Wdata_fmtr__qual_row implements BryBfrArg {
	private byte[] ttl; private Wdata_visitor__html_wtr claim_html_wtr = new Wdata_visitor__html_wtr();
	private Wdata_wiki_mgr wdata_mgr; private Wdata_lbl_mgr lbl_mgr; private Wdata_hwtr_msgs msgs;
	private Xol_lang_itm lang;
	private Wbase_claim_grp_list quals; private BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_lbl_mgr lbl_mgr) {this.wdata_mgr = wdata_mgr; this.lbl_mgr = lbl_mgr;}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {this.lang = lang; this.msgs = msgs;}
	public void Init_by_grp(byte[] ttl, Wbase_claim_grp_list quals) {this.ttl = ttl; this.quals = quals;}
	public void AddToBfr(BryWtr bfr) {
		int len = quals.Len();
		claim_html_wtr.Init(tmp_bfr, wdata_mgr, msgs, lbl_mgr, lang, ttl);
		for (int i = 0; i < len; ++i) {
			Wbase_claim_grp grp = quals.Get_at(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wbase_claim_base itm = grp.Get_at(j);
				itm.Welcome(claim_html_wtr);
				byte[] val = tmp_bfr.ToBryAndClear();
				row_fmtr.BldToBfrMany(bfr, grp.Id(), lbl_mgr.Get_text__pid(grp.Id()), val);
			}
		}
	}
	private BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
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
class Wdata_fmtr__ref_tbl implements BryBfrArg {
	private Wdata_fmtr__ref_row fmtr_row = new Wdata_fmtr__ref_row(); private Wbase_claim_base claim;
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_lbl_mgr lbl_mgr) {fmtr_row.Init_by_ctor(wdata_mgr, lbl_mgr);}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {
		fmtr_row.Init_by_lang(lang, msgs);
	}
	public void Init_by_claim(byte[] ttl, Wbase_claim_base claim) {
		this.claim = claim;
		fmtr_row.Init_by_grp(ttl, claim.References());
	}
	public void AddToBfr(BryWtr bfr) {
		if (claim.References() == null) return;
		fmtr.BldToBfrMany(bfr, fmtr_row);
	}
	private BryFmtr fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
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
class Wdata_fmtr__ref_row implements BryBfrArg {
	private byte[] ttl; private Wdata_visitor__html_wtr claim_html_wtr = new Wdata_visitor__html_wtr();
	private Wdata_wiki_mgr wdata_mgr; private Wdata_lbl_mgr lbl_mgr; private Wdata_hwtr_msgs msgs;
	private Xol_lang_itm lang;
	private BryWtr tmp_bfr = BryWtr.NewAndReset(255);
	private Wbase_references_grp[] ref_grps;
	public void Init_by_ctor(Wdata_wiki_mgr wdata_mgr, Wdata_lbl_mgr lbl_mgr) {this.wdata_mgr = wdata_mgr; this.lbl_mgr = lbl_mgr;}
	public void Init_by_lang(Xol_lang_itm lang, Wdata_hwtr_msgs msgs) {this.lang = lang; this.msgs = msgs;}
	public void Init_by_grp(byte[] ttl, Wbase_references_grp[] ref_grps) {this.ttl = ttl; this.ref_grps = ref_grps;}
	public void AddToBfr(BryWtr bfr) {
		int len = ref_grps.length;
		claim_html_wtr.Init(tmp_bfr, wdata_mgr, msgs, lbl_mgr, lang, ttl);
		for (int i = 0; i < len; ++i) {
			Wbase_references_grp grp_itm = ref_grps[i];
			Wbase_claim_grp_list grp = grp_itm.Snaks();
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wbase_claim_grp grp2 = grp.Get_at(j);
				int grp2_len = grp2.Len();
				for (int k = 0; k < grp2_len; ++k) {
					Wbase_claim_base itm = grp2.Get_at(k);
					itm.Welcome(claim_html_wtr);
					byte[] val = tmp_bfr.ToBryAndClear();
					row_fmtr.BldToBfrMany(bfr, grp2.Id(), lbl_mgr.Get_text__pid(grp2.Id()), val);
				}
			}
		}
	}
	private BryFmtr row_fmtr = BryFmtr.New(StringUtl.ConcatLinesNlSkipLast
	( ""
	, "                            <div class='wikibase-snakview'>"
	, "                              <div class='wikibase-snakview-property-container'>"
	, "                                <div class='wikibase-snakview-property' dir='auto'>"
	, "                                  <a href='/wiki/Property:~{pid}' title='Property:P~{pid}'>~{pid_lbl}</a>"
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
