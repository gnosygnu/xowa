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
import gplx.html.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.wikis.*;
class Wdata_fmtr__sitelink_tbl implements Bry_fmtr_arg {
	private Wdata_fmtr__sitelink_row fmtr_row = new Wdata_fmtr__sitelink_row();
	private Wdata_hwtr_msgs msgs; private byte[] col_hdr_lang, col_hdr_text, col_hdr_bdgs; private int list_len;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_regy) {fmtr_row.Init_by_ctor(lbl_regy);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {
		this.col_hdr_lang = msgs.Slink_col_hdr_site(); this.col_hdr_text = msgs.Slink_col_hdr_text(); this.col_hdr_bdgs = msgs.Slink_col_hdr_bdgs();
		this.msgs = msgs;
	}
	public void Init_by_wdoc(Wdata_fmtr__toc_div fmtr_toc, OrderedHash list) {
		this.list_len = list.Count(); if (list_len == 0) return;
		fmtr_toc.Add(msgs.Slink_tbl_hdr());
		fmtr_row.Init_by_page(list);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		if (list_len == 0) return;
		fmtr.Bld_bfr_many(bfr, msgs.Slink_tbl_hdr(), col_hdr_lang, col_hdr_text, col_hdr_bdgs, fmtr_row);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<h2>~{hdr}</h2>"
	, ""
	, "<table class='wikitable'>"
	, "  <tr>"
	, "    <th>~{site_lbl}</th>"
	, "    <th>~{link_lbl}</th>"
	, "    <th>~{bdgs_lbl}</th>"
	, "  </tr>~{rows}"
	, "</table>"
	), "hdr", "site_lbl", "link_lbl", "bdgs_lbl", "rows"
	);
}
class Wdata_fmtr__sitelink_row implements Wdata_fmtr__row {
	private Wdata_lbl_mgr lbl_regy; private OrderedHash list; private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Init_by_ctor(Wdata_lbl_mgr lbl_regy) {this.lbl_regy = lbl_regy;}
	public void Init_by_page(OrderedHash list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		lbl_regy.getClass();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.FetchAt(i);
			byte[] site = itm.Site();
			byte[] domain_bry = Xob_bz2_file.Parse__domain_name(site, 0, site.length);
			byte[][] badges_ary = itm.Badges();
			int badges_len = badges_ary.length;
			for (int j = 0; j < badges_len; ++j) {
				byte[] val = badges_ary[j];
				if (j != 0) tmp_bfr.Add(Html_tag_.Br_inl);
				tmp_bfr.Add(Html_utl.Escape_html_as_bry(val));
			}
			row_fmtr.Bld_bfr_many(bfr, site, domain_bry, itm.Name());
		}
	}
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr>"
	, "    <td><a href='/site/~{domain}/wiki/'><code>~{site}</code></a></td>"
	, "    <td><a href='/site/~{domain}/wiki/~{page}'>~{page}</a></td>"
	, "  </tr>"
	), "site", "domain", "page"
	);
}
//	class Wdata_slink_grp {
//		public Wdata_slink_grp(int tid, byte[] tbl_hdr) {this.tid = tid; this.tbl_hdr = tbl_hdr;}
//		public int Tid() {return tid;} private final int tid;
//		public byte[] Tbl_hdr() {return tbl_hdr;} private final byte[] tbl_hdr;
//		public OrderedHash Rows() {return rows;} private final OrderedHash rows = OrderedHash_.new_();
//		public static Wdata_slink_grp[] new_(OrderedHash list) {
//			ListAdp rv = ListAdp_.new_();
//			int list_len = list.Count();
//			for (int i = 0; i < list_len; ++i) {
//				Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.FetchAt(i);
//				byte[] site = itm.Site();
//				byte[] domain = Xob_bz2_file.Parse__domain_name(site, 0, site.length);
//				Xow_wiki_domain domain_tid = Xow_wiki_domain_.parse_by_domain(domain);
//				domain_tid.Tid
//			}
////			ListAdp list
////			int grps_len = 0;
////			OrderedHash[] grps = new OrderedHash[grps_len];
//			// sort by tid
//			// iterate and add to list
//			return (Wdata_slink_grp[])rv.XtoAryAndClear(typeof(ListAdp));
//		}
//		// , Tid_wikipedia =  2, Tid_wiktionary =  3, Tid_wikisource =  4, Tid_wikibooks =  5, Tid_wikiversity =  6, Tid_wikiquote = 7, Tid_wikinews = 8, Tid_wikivoyage = 9, Tid_commons   = 10
//	}
