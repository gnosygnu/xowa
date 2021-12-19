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
package gplx.xowa.addons.htmls.tocs;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.langs.htmls.Gfh_atr_;
import gplx.langs.htmls.Gfh_tag_;
import gplx.langs.htmls.Gfh_utl;
import gplx.langs.htmls.docs.Gfh_tag;
import gplx.langs.htmls.docs.Gfh_tag_rdr;
import gplx.langs.htmls.encoders.Gfo_url_encoder;
import gplx.langs.htmls.encoders.Gfo_url_encoder_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.xowa.Xoa_url;
import gplx.xowa.htmls.core.htmls.tidy.Xow_tidy_mgr_interface;
import gplx.xowa.parsers.amps.Xop_amp_mgr;
class Xoh_toc_wkr__txt {
	private final Gfh_tag_rdr tag_rdr = Gfh_tag_rdr.New__html().Skip_ws_after_slash_y_();
	private final BryWtr anch_bfr = BryWtr.New(), text_bfr = BryWtr.New();
	private final Gfo_url_encoder anch_encoder = Gfo_url_encoder_.New__html_id().Make();
	private final Xop_amp_mgr amp_mgr = Xop_amp_mgr.Instance;
	private final Hash_adp anch_hash = Hash_adp_bry.ci_u8(gplx.xowa.langs.cases.Xol_case_mgr_.U8());
	private Xow_tidy_mgr_interface tidy_mgr;
	private byte[] page_url_bry;
	public void Clear() {
		anch_bfr.Clear();
		text_bfr.Clear();
		anch_hash.Clear();
	}
	public void Init(Xow_tidy_mgr_interface tidy_mgr, Xoa_url page_url) {
		this.tidy_mgr = tidy_mgr;
		this.page_url_bry = page_url == null ? BryUtl.NewA7("null_url") : page_url.To_bry();
	}
	public void Calc_anch_text(Xoh_toc_itm rv, byte[] src) {	// text within hdr; EX: <h2>Abc</h2> -> Abc
		int end = src.length;
		src = Gfh_utl.Del_comments(text_bfr, src, 0, end);
		end = src.length;
		tag_rdr.Init(page_url_bry, src, 0, end);
		try {
			if (!Calc_anch_text_recurse(src, 0, end)) {
				Gfo_usr_dlg_.Instance.Log_many("", "", "toc:invalid html; page=~{0} src=~{1}", page_url_bry, src);

				// tidy html; note reusing text_bfr as temp bfr
				text_bfr.Clear().Add(src);
				tidy_mgr.Exec_tidy(text_bfr, BoolUtl.N, page_url_bry);
				src = text_bfr.ToBryAndClear();
				end = src.length;
				tag_rdr.Init(page_url_bry, src, 0, end);

				// try to calc again; if fail, give up
				if (!Calc_anch_text_recurse(src, 0, end))
					throw ErrUtl.NewArgs("could not tidy html");
			}
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "toc:failed while generating anch_text; page=~{0} src=~{1} err=~{2}", page_url_bry, src, ErrUtl.ToStrLog(e));
			text_bfr.Clear().Add(src);
			anch_encoder.Encode(anch_bfr, src);
		}

		byte[] anch_bry = anch_bfr.ToBryAndClearAndTrim(BoolUtl.Y, BoolUtl.Y, Trim__id);
		if (anch_hash.Has(anch_bry)) {
			int anch_idx = 2;
			while (true) {	// NOTE: this is not big-O performant, but it mirrors MW; DATE:2016-07-09
				byte[] anch_tmp = BryUtl.Add(anch_bry, AsciiByte.UnderlineBry, IntUtl.ToBry(anch_idx++));
				if (!anch_hash.Has(anch_tmp)) {
					anch_bry = anch_tmp;
					break;
				}
			}
		}
		anch_hash.AddAsKeyAndVal(anch_bry);
		rv.Set__txt
		( anch_bry
		, text_bfr.ToBryAndClearAndTrim());	// NOTE: both id and text trim ends
	}
	private boolean Calc_anch_text_recurse(byte[] src, int pos, int end) {
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
				byte[] anch_bry = amp_mgr.Decode_as_bry(BryUtl.Trim(src, pos, txt_end, BoolUtl.Y, BoolUtl.Y, Trim__anch, BoolUtl.Y));	// trim \n else ".0a"; PAGE:en.w:List_of_U-boats_never_deployed DATE:2016-08-13
				anch_encoder.Encode(anch_bfr, anch_bry);
				text_bfr.AddMid(src, pos, txt_end);
			}

			// set print_tag tag; REF.MW:Parser.php!formatHeadings
			boolean print_tag = false;
			switch (tag_id) {
				case Gfh_tag_.Id__eos:		// eos; return;
					return true;
				case Gfh_tag_.Id__sup:		// always print tag; REF.MW:Parser.php!formatHeadings!"Allowed tags are"
				case Gfh_tag_.Id__sub:
				case Gfh_tag_.Id__i:
				case Gfh_tag_.Id__b:
				case Gfh_tag_.Id__bdi:
				case Gfh_tag_.Id__mark:// include mark; ISSUE#:542: DATE:2020-03-09
					print_tag = true;
					break;
				case Gfh_tag_.Id__span:		// print span only if it has a dir attribute
					span_dir = lhs.Atrs__get_as_bry(Gfh_atr_.Bry__dir);
					print_tag = BryUtl.IsNotNullOrEmpty(span_dir);
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
					Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown tag in toc: page=~{0} tag=~{1}", page_url_bry, lhs_bry);
					Gfh_tag rhs = tag_rdr.Tag__peek_fwd_tail(lhs_bry);
					if (rhs.Name_id() == Gfh_tag_.Id__eos) return false;
					rhs_bgn = rhs.Src_bgn(); rhs_end = rhs.Src_end();
					new_pos = rhs_end;
				}
				else {
					Gfh_tag rhs = tag_rdr.Tag__peek_fwd_tail(tag_id);
					if (rhs.Name_id() == Gfh_tag_.Id__eos) return false;
					rhs_bgn = rhs.Src_bgn(); rhs_end = rhs.Src_end();
					new_pos = rhs_end;
				}
			}

			// print "<tag></tag>"; also, recurse
			if (print_tag) {
				text_bfr.AddByte(AsciiByte.AngleBgn).Add(lhs_bry);
				if (span_dir != null)	// if span has dir, add it; EX: <span id='1' dir='rtl'> -> <span dir='rtl'>
					Gfh_atr_.Add(text_bfr, Gfh_atr_.Bry__dir, span_dir);					
				text_bfr.AddByte(AsciiByte.AngleEnd);	// only add name; do not add atrs; EX: <i id='1'> -> <i>
			}
			if (!Calc_anch_text_recurse(src, lhs_end, rhs_bgn)) return false;
			if (print_tag && lhs_is_pair)
				text_bfr.AddMid(src, rhs_bgn, rhs_end);

			// set new_pos
			pos = new_pos;
			tag_rdr.Src_rng_(new_pos, end);	// NOTE: must reinit pos and especially end
		}
		return true;
	}

	private static final byte[] Trim__id = BryUtl.Mask(256, AsciiByte.UnderlineBry), Trim__anch = BryUtl.Mask(256, AsciiByte.Tab, AsciiByte.Nl, AsciiByte.Cr);
}
