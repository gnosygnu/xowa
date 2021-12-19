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
package gplx.xowa.htmls.core.htmls;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.langs.html.HtmlEntityCodes;
import gplx.langs.htmls.entitys.Gfh_entity_itm;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.Xoa_app_;
import gplx.xowa.parsers.amps.Xop_amp_mgr;
import gplx.xowa.parsers.amps.Xop_amp_mgr_rslt;
import gplx.xowa.parsers.xndes.Xop_xnde_tag_;
public class Xoh_html_wtr_escaper {
	public static String Escape_str(Xop_amp_mgr amp_mgr, BryWtr tmp_bfr, String src) {
		return StringUtl.NewU8(Escape(amp_mgr, tmp_bfr, BryUtl.NewU8(src)));
	}
	public static byte[] Escape(Xop_amp_mgr amp_mgr, BryWtr tmp_bfr, byte[] src) {
		Escape(amp_mgr, tmp_bfr, src, 0, src.length, true, false);
		return tmp_bfr.ToBryAndClear();
	}
	public static void Escape(Xop_amp_mgr amp_mgr, BryWtr bfr, byte[] src, int bgn, int end, boolean interpret_amp, boolean nowiki_skip) {
		Btrie_slim_mgr amp_trie = amp_mgr.Amp_trie();
		Btrie_rv trv = new Btrie_rv();
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			switch (b) {
				case AsciiByte.Lt:
					if (nowiki_skip) {
						byte[] nowiki_name = Xop_xnde_tag_.Tag__nowiki.Name_bry();
						int nowiki_name_len = nowiki_name.length;
						if (BryLni.Eq(src, i + 1, i + 1 + nowiki_name_len, nowiki_name)) {	// <nowiki found;
							int end_gt = Escape_nowiki_skip(bfr, src, i, end, nowiki_name, nowiki_name_len);
							if (end_gt != BryFind.NotFound) {
								i = end_gt;
								continue;
							}
						}
					}
					bfr.Add(HtmlEntityCodes.LtBry);
					break;
				case AsciiByte.Gt:
					bfr.Add(HtmlEntityCodes.GtBry);
					break;
				case AsciiByte.Amp:
					if (interpret_amp) {
						int text_bgn = i + 1;	// i is &; i + 1 is first char after amp
						Object o = (text_bgn < end) ? amp_trie.MatchAt(trv, src, text_bgn, end) : null;	// check if this is a valid &; note must check that text_bgn < end or else arrayIndex error; occurs when src is just "&"; DATE:2013-12-19
						if (o == null)										// invalid; EX: "a&b"; "&bad;"; "&#letters;"; 
							bfr.Add(HtmlEntityCodes.AmpBry);					// escape & and continue
						else {												// is either (1) a name or (2) an ncr (hex/dec)
							Gfh_entity_itm itm = (Gfh_entity_itm)o;
							int match_pos = trv.Pos();
							int itm_tid = itm.Tid();
							switch (itm_tid) {
								case Gfh_entity_itm.Tid_name_std:
								case Gfh_entity_itm.Tid_name_xowa:		// name
									bfr.AddMid(src, i, match_pos);			// embed entire name
									i = match_pos - 1;
									break;
								case Gfh_entity_itm.Tid_num_dec:
								case Gfh_entity_itm.Tid_num_hex:			// ncr: dec/hex; escape if invalid
									Xop_amp_mgr_rslt rslt = new Xop_amp_mgr_rslt();
									boolean pass = amp_mgr.Parse_ncr(rslt, itm_tid == Gfh_entity_itm.Tid_num_hex, src, end, i, match_pos);
									if (pass) {								// parse worked; embed entire ncr; EX: "&#123;"
										int end_pos = rslt.Pos();
										bfr.AddMid(src, i, end_pos);
										i = end_pos - 1;
									}
									else									// parse failed; escape and continue; EX: "&#a!b;"
										bfr.Add(HtmlEntityCodes.AmpBry);
									break;
								default: throw ErrUtl.NewUnhandled(itm_tid);
							}
						}
					}
					else
						bfr.Add(HtmlEntityCodes.AmpBry);
					break;
				case AsciiByte.Quote:
					bfr.Add(HtmlEntityCodes.QuoteBry);
					break;
				default:
					bfr.AddByte(b);
					break;
			}
		}
	}
	private static int Escape_nowiki_skip(BryWtr bfr, byte[] src, int bgn, int end, byte[] nowiki_name, int nowiki_name_len) {
		try {
			boolean tag_is_bgn = true;
			int bgn_gt = -1, end_lt = -1, end_gt = -1;
			for (int i = bgn + nowiki_name_len; i < end; i++) {
				byte b = src[i];
				switch (b) {
					case AsciiByte.Gt:
						if	(tag_is_bgn)	{bgn_gt = i; tag_is_bgn = false;}
						else				return BryFind.NotFound;								// <nowiki>> found
						break;
					case AsciiByte.Lt:
						if (	tag_is_bgn															// <nowiki < found
							||	(i + nowiki_name_len + 2 > end) 									// not enough chars for "/nowiki>"
							||	src[i + 1] != AsciiByte.Slash 										// /
							||	!BryLni.Eq(src, i + 2, i + 2 + nowiki_name_len, nowiki_name)			//  nowiki
							||	src[i + 2 + nowiki_name_len] != AsciiByte.Gt						//        >
							)	return BryFind.NotFound;
						end_lt = i;
						end_gt = i + 2 + nowiki_name_len;
						i = end;
						break;
				}
			}
			if (end_gt == -1) return BryFind.NotFound;	// ">" of </nowiki> not found
			bfr.AddMid(src, bgn_gt + 1, end_lt);
			return end_gt;
		}
		catch (Exception e) {
			Xoa_app_.Usr_dlg().Warn_many("", "", "unknown error in escape.nowiki: ~{0} ~{1}", StringUtl.NewU8(src, bgn, end), ErrUtl.ToStrFull(e));
			return BryFind.NotFound;
		}
	}
}
