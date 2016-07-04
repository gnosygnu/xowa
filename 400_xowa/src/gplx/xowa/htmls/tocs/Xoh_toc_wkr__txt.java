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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.encoders.*;
import gplx.xowa.parsers.amps.*; import gplx.core.primitives.*;
class Xoh_toc_wkr__txt {
	private final    Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html();
	private final    Bry_bfr anch_bfr = Bry_bfr_.New(), text_bfr = Bry_bfr_.New();
	private final    Gfo_url_encoder anch_encoder = Gfo_url_encoder_.New__id();
	private final    Xop_amp_mgr amp_mgr = Xop_amp_mgr.Instance;
	private final    Hash_adp anch_hash = Hash_adp_bry.ci_u8(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
	private byte[] page_name = Bry_.Empty;
	public void Clear() {
		anch_bfr.Clear();
		text_bfr.Clear();
		anch_hash.Clear();
	}
	public void Calc_anch_text(Xoh_toc_itm rv, byte[] src) {	// text within hdr; EX: <h2>Abc</h2> -> Abc
		int end = src.length;
		src = Remove_comment(text_bfr, src, 0, end);
		end = src.length;
		tag_rdr.Init(page_name, src, 0, end);
		Calc_anch_text_recurse(src, 0, end);

		byte[] anch_bry = anch_bfr.To_bry_and_clear_and_trim(Bool_.Y, Bool_.Y, id_trim_ary);
		Int_obj_ref anch_idx_ref = (Int_obj_ref)anch_hash.Get_by(anch_bry);
		if (anch_idx_ref == null) {
			anch_hash.Add(anch_bry, Int_obj_ref.New(2));
		}
		else {
			int anch_idx = anch_idx_ref.Val();
			anch_bry = Bry_.Add(anch_bry, Byte_ascii.Underline_bry, Int_.To_bry(anch_idx));
			anch_idx_ref.Val_(anch_idx + 1);
		}
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
				case Gfh_tag_.Id__any:		// all other tags never print
				default:
					print_tag = false;
					break;
			}

			// get lhs / rhs vars
			byte[] lhs_bry = lhs.Name_bry();
			int lhs_end = lhs.Src_end();
			boolean lhs_is_pair = !lhs.Tag_is_inline();
			int rhs_bgn = -1, rhs_end = -1, new_pos = lhs_end;
			if (lhs_is_pair) {	// get rhs unless inline
				Gfh_tag rhs = tag_rdr.Tag__move_fwd_tail(tag_id);
				rhs_bgn = rhs.Src_bgn(); rhs_end = rhs.Src_end();
				new_pos = rhs_end;
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

	public static byte[] Remove_comment(Bry_bfr tmp, byte[] src, int bgn, int end) {
		boolean dirty = false, append_to_eos = true;
		int pos = bgn;
		while (true) {
			int comm_bgn = Bry_find_.Find_fwd(src, Gfh_tag_.Comm_bgn, pos, end);
			if (comm_bgn != -1) {	// comment found
				int tmp_pos = comm_bgn + Gfh_tag_.Comm_bgn_len;
				int comm_end = Bry_find_.Find_fwd(src, Gfh_tag_.Comm_end, tmp_pos, end);
				if (comm_end == -1) {	// dangling
					tmp.Add_mid(src, pos, comm_bgn);
					append_to_eos = false;
				}
				else {
					dirty = true;
					tmp.Add_mid(src, pos, comm_bgn);
					pos = comm_end + Gfh_tag_.Comm_end_len;
					continue;
				}
			}
			break;
		}
		if (dirty && append_to_eos) {
			tmp.Add_mid(src, pos, end);
		}
		return dirty ? tmp.To_bry_and_clear() : src;
	}
	private static final    byte[] id_trim_ary = Bry_.mask_(256, Byte_ascii.Underline_bry);
}
