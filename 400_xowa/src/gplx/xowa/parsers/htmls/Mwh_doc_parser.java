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
package gplx.xowa.parsers.htmls;
import gplx.Bry_;
import gplx.Bry_find_;
import gplx.Hash_adp_bry;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.AsciiByte;
import gplx.xowa.parsers.Xop_tkn_itm;
import gplx.xowa.parsers.Xop_tkn_mkr;
import gplx.xowa.parsers.amps.Xop_amp_mgr;
import gplx.xowa.parsers.amps.Xop_amp_mgr_rslt;
import gplx.xowa.parsers.xndes.Xop_xnde_tag;
public class Mwh_doc_parser {
	private final Mwh_doc_mgr dom_mgr = new Mwh_doc_mgr(16);
	private final Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	private final List_adp nde_stack = List_adp_.New();
	private final Xop_amp_mgr amp_mgr = Xop_amp_mgr.Instance; private final Xop_tkn_mkr tkn_mkr = new Xop_tkn_mkr();
	private byte[] src; private int src_end;
	private Mwh_doc_wkr wkr;
	private Hash_adp_bry nde_regy;
	private int txt_bgn, nde_uid;
	private Xop_xnde_tag cur_nde; private int cur_nde_tid;
	public void Parse(Mwh_doc_wkr wkr, byte[] src, int src_bgn, int src_end) {
		this.wkr = wkr; this.src = src; this.src_end = src_end;
		this.nde_regy = wkr.Nde_regy();

		// clear
		nde_stack.Clear();
		dom_mgr.Clear(); // must clear, or NegativeArraySizeException during mass_parse; DATE:2017-04-09

		int pos = txt_bgn = src_bgn;
		nde_uid = cur_nde_tid = -1;
		cur_nde = null;

		while (pos < src_end) {
			byte b = src[pos];
			switch (b) {
				case AsciiByte.AngleBgn:	// "<": possible nde start
					pos = Parse_nde(pos);
					break;
				case AsciiByte.Amp:		// "&": check for entity; EX: &nbsp; in sr-ec -> sr-el
					Xop_amp_mgr_rslt rv = amp_mgr.Parse_tkn(tkn_mkr, src, src_end, pos, pos + 1);
					Xop_tkn_itm rv_tkn = rv.Tkn();
					if (rv_tkn == null)
						++pos;
					else {
						wkr.On_txt_end(this, src, cur_nde_tid, txt_bgn, pos);
						wkr.On_entity_end(this, src, cur_nde_tid, rv_tkn.Src_bgn(), rv_tkn.Src_end());
						pos = rv_tkn.Src_end();
						txt_bgn = pos;
					}
					break;
				default:					// else, just increment
					++pos;
					break;
			}
		}
		if (src_end != txt_bgn) wkr.On_txt_end(this, src, cur_nde_tid, txt_bgn, pos);
	}
	private int Parse_nde(int pos) {
		int nde_end_tid = Nde_end_tid__invalid;
		boolean nde_is_head = true;
		int nde_bgn = pos;
		++pos;
		int name_bgn = pos;
		int name_end = pos;
		while (pos < src_end) {
			byte b = src[pos];
			switch (b) {
				// valid chars for name
				case AsciiByte.Ltr_A: case AsciiByte.Ltr_B: case AsciiByte.Ltr_C: case AsciiByte.Ltr_D: case AsciiByte.Ltr_E:
				case AsciiByte.Ltr_F: case AsciiByte.Ltr_G: case AsciiByte.Ltr_H: case AsciiByte.Ltr_I: case AsciiByte.Ltr_J:
				case AsciiByte.Ltr_K: case AsciiByte.Ltr_L: case AsciiByte.Ltr_M: case AsciiByte.Ltr_N: case AsciiByte.Ltr_O:
				case AsciiByte.Ltr_P: case AsciiByte.Ltr_Q: case AsciiByte.Ltr_R: case AsciiByte.Ltr_S: case AsciiByte.Ltr_T:
				case AsciiByte.Ltr_U: case AsciiByte.Ltr_V: case AsciiByte.Ltr_W: case AsciiByte.Ltr_X: case AsciiByte.Ltr_Y: case AsciiByte.Ltr_Z:
				case AsciiByte.Ltr_a: case AsciiByte.Ltr_b: case AsciiByte.Ltr_c: case AsciiByte.Ltr_d: case AsciiByte.Ltr_e:
				case AsciiByte.Ltr_f: case AsciiByte.Ltr_g: case AsciiByte.Ltr_h: case AsciiByte.Ltr_i: case AsciiByte.Ltr_j:
				case AsciiByte.Ltr_k: case AsciiByte.Ltr_l: case AsciiByte.Ltr_m: case AsciiByte.Ltr_n: case AsciiByte.Ltr_o:
				case AsciiByte.Ltr_p: case AsciiByte.Ltr_q: case AsciiByte.Ltr_r: case AsciiByte.Ltr_s: case AsciiByte.Ltr_t:
				case AsciiByte.Ltr_u: case AsciiByte.Ltr_v: case AsciiByte.Ltr_w: case AsciiByte.Ltr_x: case AsciiByte.Ltr_y: case AsciiByte.Ltr_z:
				case AsciiByte.Num0: case AsciiByte.Num1: case AsciiByte.Num2: case AsciiByte.Num3: case AsciiByte.Num4:
				case AsciiByte.Num5: case AsciiByte.Num6: case AsciiByte.Num7: case AsciiByte.Num8: case AsciiByte.Num9:
				case AsciiByte.Dot: case AsciiByte.Dash: case AsciiByte.Underline: case AsciiByte.Colon:	// XML allowed punctuation
				case AsciiByte.Dollar:// MW: handles <br$2>;
					++pos;
					break;
				// comment check
				case AsciiByte.Bang:
					boolean comment_found = false;
					if (name_bgn == pos && Bry_.Eq(src, pos + 1, pos + 3, Comment_bgn)) {
						int comment_end_pos = Bry_find_.Find_fwd(src, Comment_end, pos + 3);
						if (comment_end_pos != Bry_find_.Not_found) {
							nde_end_tid = Nde_end_tid__comment;
							pos = comment_end_pos + 3;
							comment_found = true;
						}
					}
					if (!comment_found)
						return pos;
					else
						break;
				// invalid char; not a node; treat as text; EX: "<!@#", "< /b>"
				default:
					return pos;
				// slash -> either "</b>"  or "<b/>"
				case AsciiByte.Slash:
					if (name_bgn == pos) {	// "</"; EX: "</b>"
						nde_is_head = false;
						++name_bgn;
						++pos;
						continue;
					}
					else {					// check for "/>"; NOTE: <pre/a>, <pre//> are allowed
						name_end = pos;
						++pos;
						if (pos == src_end) return pos;		// end of doc; treat as text; EX: "<b/EOS"
						if (src[pos] == AsciiByte.Gt) {
							nde_end_tid = Nde_end_tid__inline;
							++pos;
						}
						else
							nde_end_tid = Nde_end_tid__slash;
					}
					break;
				// stops "name"
				case AsciiByte.Gt:
					nde_end_tid = Nde_end_tid__gt;
					name_end = pos;
					++pos;
					break;
				case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
					nde_end_tid = Nde_end_tid__ws;
					name_end = pos;
					break;
				case AsciiByte.Backslash:	 // MW: allows "<br\>" -> "<br/>"
					nde_end_tid = Nde_end_tid__backslash;
					name_end = pos;
					break;
			}
			if (nde_end_tid != Nde_end_tid__invalid) break;
		}
		// get name
		Xop_xnde_tag nde_itm = null;
		if (nde_end_tid != Nde_end_tid__comment) {
			nde_itm = (Xop_xnde_tag)nde_regy.Get_by_mid(src, name_bgn, name_end);
			if (nde_itm == null) return pos;	// not a known nde; exit
		}
		if (txt_bgn != nde_bgn) {	// notify txt
			wkr.On_txt_end(this, src, cur_nde_tid, txt_bgn, nde_bgn);
			txt_bgn = pos;
		}
		if (nde_is_head) {
			wkr.On_nde_head_bgn(this, src, cur_nde_tid, name_bgn, name_end);
			switch (nde_end_tid) {
				case Nde_end_tid__comment:
					wkr.On_comment_end(this, src, cur_nde_tid, nde_bgn, pos);
					break;
				case Nde_end_tid__ws:
				case Nde_end_tid__slash:
				case Nde_end_tid__backslash:
					// look for ">" or "/>"
					int tmp_pos = pos, atrs_end = src_end, head_end = src_end;
					boolean loop = true;
					while (loop) {
						byte b = src[tmp_pos];
						switch (b) {
							// angle_end -> stop iterating
							case AsciiByte.AngleEnd:
								atrs_end = tmp_pos;
								head_end = tmp_pos + 1;
								nde_end_tid = Mwh_doc_parser.Nde_end_tid__gt;
								loop = false;
								break;
							// slash -> check for "/>" or " / "
							case AsciiByte.Slash:
								int nxt_pos = tmp_pos + 1;
								if		(nxt_pos == src_end) {
									nde_end_tid = Mwh_doc_parser.Nde_end_tid__invalid;
									loop = false;
								}
								else if (src[nxt_pos] == AsciiByte.AngleEnd) {
									atrs_end = tmp_pos;
									head_end = tmp_pos + 2;
									nde_end_tid = Mwh_doc_parser.Nde_end_tid__inline;
									loop = false;
								}				
								break;
						}
						if (loop) {
							++tmp_pos;
							if (tmp_pos == src_end) break;
						}
						else
							break;
					}
					atr_parser.Parse(wkr, nde_uid, cur_nde_tid, src, pos, atrs_end);
					pos = head_end;
					txt_bgn = head_end;
					break;
			}
			switch (nde_end_tid) {
				case Nde_end_tid__inline:
					wkr.On_nde_head_end(this, src, cur_nde_tid, nde_bgn, pos, BoolUtl.Y);
					txt_bgn = pos;
					break;
				case Nde_end_tid__gt:
					wkr.On_nde_head_end(this, src, cur_nde_tid, nde_bgn, pos, BoolUtl.N);
					txt_bgn = pos;
					if (	nde_itm != null
						&&	!nde_itm.Single_only_html()				// ignore <b>
						&&	(cur_nde == null || !cur_nde.Xtn())		// <pre> ignores inner
						) {
						if (cur_nde != null)
							nde_stack.Add(cur_nde);
						this.cur_nde = nde_itm;
						this.cur_nde_tid = nde_itm.Id();
					}
					break;
				case Nde_end_tid__ws:
				case Nde_end_tid__slash:
				case Nde_end_tid__backslash: break; // handled above
			}
			nde_uid = dom_mgr.Add(Mwh_doc_itm.Itm_tid__nde_head, nde_bgn, pos);
		}
		else {
			switch (nde_end_tid) {
				case Nde_end_tid__gt:
					wkr.On_nde_tail_end(this, src, cur_nde_tid, nde_bgn, pos);
					txt_bgn = pos;
					if (nde_itm.Id() == cur_nde_tid) {
						cur_nde = (Xop_xnde_tag)List_adp_.Pop_or(nde_stack, null);
						cur_nde_tid = cur_nde == null ? -1 : cur_nde.Id();
					}
					break;
			}
		}
		return pos;
	}
	public static final int Nde_end_tid__invalid = 0, Nde_end_tid__gt = 1, Nde_end_tid__ws = 2, Nde_end_tid__inline = 3, Nde_end_tid__slash = 4, Nde_end_tid__backslash = 5, Nde_end_tid__comment = 6;
	private static final byte[] Comment_bgn = Bry_.new_a7("--"), Comment_end = Bry_.new_a7("-->");
}
