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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.parsers.amps.*; import gplx.core.primitives.*;
class Xoh_toc_wkr__txt {
	private final    Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html();
	private final    Bry_bfr anch_bfr = Bry_bfr_.New(), text_bfr = Bry_bfr_.New();
	private final    Gfo_url_encoder anch_encoder = Gfo_url_encoder_.New__id();
	private final    Xop_amp_mgr amp_mgr = Xop_amp_mgr.Instance;
	private final    Hash_adp anch_hash = Hash_adp_bry.ci_u8(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
	private byte[] page_name;
	public void Clear() {
		anch_bfr.Clear();
		text_bfr.Clear();
		anch_hash.Clear();
	}
	public void Init(byte[] page_name) {this.page_name = page_name;}
	public void Calc_anch_text(Xoh_toc_itm rv, byte[] src) {	// text within hdr; EX: <h2>Abc</h2> -> Abc
		int end = src.length;
		src = Gfh_utl.Del_comments(text_bfr, src, 0, end);
		end = src.length;
		tag_rdr.Init(page_name, src, 0, end);
		try {
			Calc_anch_text_recurse(src, 0, end);
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "toc:failed while generating anch_text; page=~{0} src=~{1} err=~{2}", page_name, Err_.Message_gplx_log(e));
			text_bfr.Clear().Add(src);
			anch_encoder.Encode(anch_bfr, src);
		}

		byte[] anch_bry = anch_bfr.To_bry_and_clear_and_trim(Bool_.Y, Bool_.Y, id_trim_ary);
		if (anch_hash.Has(anch_bry)) {
			int anch_idx = 2;
			while (true) {	// NOTE: this is not big-O performant, but it mirrors MW; DATE:2016-07-09
				byte[] anch_tmp = Bry_.Add(anch_bry, Byte_ascii.Underline_bry, Int_.To_bry(anch_idx++));
				if (!anch_hash.Has(anch_tmp)) {
					anch_bry = anch_tmp;
					break;
				}
			}
		}
		anch_hash.Add_as_key_and_val(anch_bry);
		rv.Set__txt
		( anch_bry
		, text_bfr.To_bry_and_clear_and_trim());	// NOTE: both id and text trim ends
	}
	private void Calc_anch_text_recurse(byte[] src, int pos, int end) {
		tag_rdr.Src_rng_(pos, end);
		while (pos < end) {
			Gfh_tag lhs = tag_rdr.Tag__move_fwd_head();
			int tag_id = lhs.Name_id();
			byte[] span_dir = null;

			// add any text before lhs;
			int txt_end = lhs.Src_bgn();
			switch (tag_id) {
				case Gfh_tag_.Id__eos:		txt_end = end; break;			// eos; print everything until end
			}
			
			// add any text before tag
			if (pos < txt_end) {
				byte[] anch_bry = amp_mgr.Decode_as_bry(Bry_.Mid(src, pos, txt_end));
				anch_encoder.Encode(anch_bfr, anch_bry);
				text_bfr.Add_mid(src, pos, txt_end);
			}

			// set print_tag tag; REF.MW:Parser.php!formatHeadings
			boolean print_tag = false;
			switch (tag_id) {
				case Gfh_tag_.Id__eos:		// eos; return;
					return;
				case Gfh_tag_.Id__sup:		// always print tag; REF.MW:Parser.php!formatHeadings!"Allowed tags are"
				case Gfh_tag_.Id__sub:
				case Gfh_tag_.Id__i:
				case Gfh_tag_.Id__b:
				case Gfh_tag_.Id__bdi:
					print_tag = true;
					break;
				case Gfh_tag_.Id__span:		// print span only if it has a dir attribute
					span_dir = lhs.Atrs__get_as_bry(Gfh_atr_.Bry__dir);
					print_tag = Bry_.Len_gt_0(span_dir);
					break;
				case Gfh_tag_.Id__comment:	// never print tag
				default:
					print_tag = false;
					break;
				case Gfh_tag_.Id__any:		// unknown tags print
					print_tag = true;
					break;
			}

			// get lhs / rhs vars
			byte[] lhs_bry = lhs.Name_bry();
			int lhs_end = lhs.Src_end();

			// ignore tags which are not closed by tidy as default; EX: <br> not <br>a</br> or <br/>
			boolean lhs_is_dangling = false;
			switch (tag_id) {
				case Gfh_tag_.Id__img: 
				case Gfh_tag_.Id__br: 
				case Gfh_tag_.Id__hr:
				case Gfh_tag_.Id__wbr:
					lhs_is_dangling = true;
					break;
			}
			boolean lhs_is_pair = !lhs.Tag_is_inline() && !lhs_is_dangling;
			int rhs_bgn = -1, rhs_end = -1, new_pos = lhs_end;
			if (lhs_is_pair) {			// get rhs unless inline
				if (tag_id == Gfh_tag_.Id__any) {
                        Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown tag: page=~{0} tag=~{1}", page_name, lhs_bry);
					Gfh_tag rhs = tag_rdr.Tag__move_fwd_tail(lhs_bry);
					rhs_bgn = rhs.Src_bgn(); rhs_end = rhs.Src_end();
					new_pos = rhs_end;
				}
				else {
					Gfh_tag rhs = tag_rdr.Tag__move_fwd_tail(tag_id);
					rhs_bgn = rhs.Src_bgn(); rhs_end = rhs.Src_end();
					new_pos = rhs_end;
				}
			}

			// print "<tag></tag>"; also, recurse
			if (print_tag) {
				text_bfr.Add_byte(Byte_ascii.Angle_bgn).Add(lhs_bry);
				if (span_dir != null)	// if span has dir, add it; EX: <span id='1' dir='rtl'> -> <span dir='rtl'>
					Gfh_atr_.Add(text_bfr, Gfh_atr_.Bry__dir, span_dir);					
				text_bfr.Add_byte(Byte_ascii.Angle_end);	// only add name; do not add atrs; EX: <i id='1'> -> <i>
			}
			Calc_anch_text_recurse(src, lhs_end, rhs_bgn);
			if (print_tag && lhs_is_pair)
				text_bfr.Add_mid(src, rhs_bgn, rhs_end);

			// set new_pos
			pos = new_pos;
			tag_rdr.Src_rng_(new_pos, end);	// NOTE: must reinit pos and especially end
		}
	}

	private static final    byte[] id_trim_ary = Bry_.mask_(256, Byte_ascii.Underline_bry);
}
