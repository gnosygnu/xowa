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
import gplx.html.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.parsers.*;
import gplx.xowa.wikis.*;
interface Wdata_fmtr__row extends Bry_fmtr_arg {
	void Init_by_page(OrderedHash list);
}
class Wdata_fmtr__langtext_tbl implements Bry_fmtr_arg {
	private Wdata_fmtr__row fmtr_row;
	private byte[] tbl_hdr, col_hdr_lang, col_hdr_text; private int list_len;
	public void Init_by_ctor(Wdata_fmtr__row fmtr_row) {this.fmtr_row = fmtr_row;}
	public void Init_by_lang(byte[] tbl_hdr, byte[] col_hdr_lang, byte[] col_hdr_text) {			
		this.tbl_hdr = tbl_hdr; this.col_hdr_lang = col_hdr_lang; this.col_hdr_text = col_hdr_text;
	}
	public void Init_by_wdoc(Wdata_fmtr__toc_div fmtr_toc, OrderedHash list) {
		this.list_len = list.Count(); if (list_len == 0) return;
		fmtr_toc.Add(tbl_hdr);
		fmtr_row.Init_by_page(list);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		if (list_len == 0) return;
		fmtr.Bld_bfr_many(bfr, tbl_hdr, col_hdr_lang, col_hdr_text, fmtr_row);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<h2>~{hdr}</h2>"
	, ""
	, "<table class='wikitable'>"
	, "  <tr>"
	, "    <th>~{lang_lbl}</th>"
	, "    <th>~{text_lbl}</th>"
	, "  </tr>~{rows}"
	, "</table>"
	), "hdr", "lang_lbl", "text_lbl", "rows");
}
class Wdata_fmtr__langtext_row implements Wdata_fmtr__row {
	private OrderedHash list;
	public void Init_by_page(OrderedHash list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_langtext_itm itm = (Wdata_langtext_itm)list.FetchAt(i);
			row_fmtr.Bld_bfr_many(bfr, itm.Lang(), Html_utl.Escape_html_as_bry(itm.Text()));
		}
	}
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr>"
	, "    <td><code>~{lang}</code></td>"
	, "    <td>~{text}</td>"
	, "  </tr>"
	), "lang", "text");
}
class Wdata_fmtr__alias_row implements Wdata_fmtr__row {
	private OrderedHash list; private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Init_by_page(OrderedHash list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_alias_itm itm = (Wdata_alias_itm)list.FetchAt(i);
			byte[][] vals_ary = itm.Vals();
			int vals_len = vals_ary.length;
			for (int j = 0; j < vals_len; ++j) {
				byte[] val = vals_ary[j];
				if (j != 0) tmp_bfr.Add(Html_tag_.Br_inl);
				tmp_bfr.Add(Html_utl.Escape_html_as_bry(val));
			}
			row_fmtr.Bld_bfr_many(bfr, itm.Lang(), tmp_bfr.XtoAryAndClear());
		}
	}
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr>"
	, "    <td><code>~{lang}</code></td>"
	, "    <td>~{text}</td>"
	, "  </tr>"
	), "lang", "text");
}
class Wdata_fmtr__claim_tbl implements Bry_fmtr_arg {
	public Wdata_fmtr__claim_row Fmtr_row() {return fmtr_row;} private Wdata_fmtr__claim_row fmtr_row = new Wdata_fmtr__claim_row(); private int list_len;
	private byte[] tbl_hdr, col_hdr_prop_id, col_hdr_prop_name, col_hdr_val, col_hdr_ref, col_hdr_qual, col_hdr_rank;
	public void Init_by_ctor(Wdata_lbl_mgr lbl_regy) {fmtr_row.Init_by_ctor(lbl_regy);}
	public void Init_by_lang(Wdata_hwtr_msgs msgs, byte[] tbl_hdr, byte[] col_hdr_prop_id, byte[] col_hdr_prop_name, byte[] col_hdr_val, byte[] col_hdr_ref, byte[] col_hdr_qual, byte[] col_hdr_rank) {
		fmtr_row.Init_by_lang(msgs);
		this.tbl_hdr = tbl_hdr; this.col_hdr_prop_id = col_hdr_prop_id; this.col_hdr_prop_name = col_hdr_prop_name;
		this.col_hdr_val = col_hdr_val; this.col_hdr_ref = col_hdr_ref; this.col_hdr_qual = col_hdr_qual; this.col_hdr_rank = col_hdr_rank;
	}
	public void Init_by_wdoc(Wdata_fmtr__toc_div fmtr_toc, OrderedHash list) {
		this.list_len = list.Count(); if (list_len == 0) return;
		fmtr_toc.Add(tbl_hdr);
		fmtr_row.Init_by_page(list);
	}
	public void XferAry(Bry_bfr bfr, int idx) {
		if (list_len == 0) return;
		fmtr.Bld_bfr_many(bfr, tbl_hdr, col_hdr_prop_id, col_hdr_prop_name, col_hdr_val, col_hdr_ref, col_hdr_qual, col_hdr_rank, fmtr_row);
	}
	private Bry_fmtr fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "<h2>~{hdr}</h2>"
	, ""
	, "<table class='wikitable'>"
	, "  <tr>"
	, "    <th>~{prop_id}</th>"
	, "    <th>~{prop_name}</th>"
	, "    <th>~{val}</th>"
	, "    <th>~{ref}</th>"
	, "    <th>~{qual}</th>"
	, "    <th>~{rank}</th>"
	, "  </tr>~{rows}"
	, "</table>"
	), "hdr", "prop_id", "prop_name", "val", "ref", "qual", "rank", "rows");
}
class Wdata_fmtr__claim_row implements Wdata_fmtr__row {
	private Wdata_lbl_mgr lbl_regy; private Wdata_hwtr_msgs msgs;
	private OrderedHash list; private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public void Init_by_ctor(Wdata_lbl_mgr lbl_regy) {this.lbl_regy = lbl_regy;}
	public void Init_by_lang(Wdata_hwtr_msgs msgs) {this.msgs = msgs;}
	public void Init_by_page(OrderedHash list) {this.list = list;}
	public void XferAry(Bry_bfr bfr, int idx) {
		lbl_regy.getClass();
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Wdata_claim_grp grp = (Wdata_claim_grp)list.FetchAt(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j) {
				Wdata_claim_itm_core itm = grp.Get_at(j);
				byte[] val = Calc_val(itm);
				byte[] pid_name = lbl_regy.Pid_regy().Get_text_or_empty(itm.Pid());
				row_fmtr.Bld_bfr_many(bfr, itm.Pid(), pid_name, val, "", "", Wdata_dict_rank.Xto_str(itm.Rank_tid()));
			}
		}
	}
	public byte[] Calc_val(Wdata_claim_itm_core itm) {
		switch (itm.Val_tid()) {
			case Wdata_dict_val_tid.Tid_string:
				return ((Wdata_claim_itm_str)itm).Val_str();
			case Wdata_dict_val_tid.Tid_monolingualtext:
				Wdata_claim_itm_monolingualtext monolingual = (Wdata_claim_itm_monolingualtext)itm;
				tmp_bfr.Add_byte(Byte_ascii.Brack_bgn).Add(monolingual.Lang()).Add_byte(Byte_ascii.Brack_end).Add_byte(Byte_ascii.Space);
				tmp_bfr.Add(monolingual.Text());
				return tmp_bfr.XtoAryAndClear();
			case Wdata_dict_val_tid.Tid_time:
				Wdata_claim_itm_time time = (Wdata_claim_itm_time)itm;
				Wdata_date date = time.Time_as_date();
				tmp_bfr.Add_long_variable(date.Year());
				tmp_bfr.Add_byte(Byte_ascii.Dash)	.Add_int_fixed(date.Month()	, 2);
				tmp_bfr.Add_byte(Byte_ascii.Dash)	.Add_int_fixed(date.Day()	, 2);
				tmp_bfr.Add_byte(Byte_ascii.Space)	.Add_int_fixed(date.Hour()	, 2);
				tmp_bfr.Add_byte(Byte_ascii.Colon)	.Add_int_fixed(date.Minute(), 2);
				tmp_bfr.Add_byte(Byte_ascii.Colon)	.Add_int_fixed(date.Second(), 2);
				return tmp_bfr.XtoAryAndClear();
			case Wdata_dict_val_tid.Tid_quantity:
				Wdata_claim_itm_quantity quantity = (Wdata_claim_itm_quantity)itm;
				long amount = quantity.Amount_as_long();
				long ubound = quantity.Ubound_as_long();
				long lbound = quantity.Lbound_as_long();
				long udiff  = ubound - amount;
				long ldiff  = amount - lbound;
				tmp_bfr.Add(quantity.Amount()).Add_byte_space();
				if (udiff == ldiff) {	// delta is same in both directions; EX: amount=50 ubound=60 lbound=40 -> udiff == ldiff == 10
					if (udiff != 0)		// skip if 0
						tmp_bfr.Add(msgs.Sym_plusminus()).Add_long_variable(udiff);
				}
				else {					// delta is diff in both directions; EX: amount=50 ubound=60 lbound=30 -> udiff == 10, ldiff == 20
					if (udiff != 0)		// skip if 0
						tmp_bfr.Add(msgs.Sym_plus()).Add_long_variable(udiff);
					if (ldiff != 0) {	// skip if 0
						if (udiff != 0) tmp_bfr.Add(Time_plus_minus_spr);
						tmp_bfr.Add(msgs.Sym_minus()).Add_long_variable(ldiff);
					}
				}
				byte[] unit = quantity.Unit();
				if (!Bry_.Eq(unit, Wdata_claim_itm_quantity.Unit_1))
					tmp_bfr.Add_byte_space().Add(unit);
				return tmp_bfr.XtoAryAndClear();
			case Wdata_dict_val_tid.Tid_globecoordinate:
			case Wdata_dict_val_tid.Tid_bad:
				// Wdata_claim_itm_globecoordinate globecoordinate = (Wdata_claim_itm_globecoordinate)itm;
				break;
			case Wdata_dict_val_tid.Tid_entity:
				Wdata_claim_itm_entity entity = (Wdata_claim_itm_entity)itm;
				return lbl_regy.Qid_regy().Get_text_or_empty(entity.Entity_id());
			case Wdata_dict_val_tid.Tid_unknown:
				return Wdata_dict_val_tid.Bry_unknown;
		}
		return null;
	}	private static final byte[] Time_plus_minus_spr = Bry_.new_ascii_(" / ");
	private Bry_fmtr row_fmtr = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	, "  <tr>"
	, "    <td>~{prop_id}</td>"
	, "    <td>~{prop_name}</td>"
	, "    <td>~{val}</td>"
	, "    <td>~{ref}</td>"
	, "    <td>~{qual}</td>"
	, "    <td>~{rank}</td>"
	, "  </tr>"
	), "prop_id", "prop_name", "val", "ref", "qual", "rank"
	);
}
