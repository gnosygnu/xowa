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
package gplx.langs.htmls;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.langs.html.HtmlEntityCodes;
import gplx.langs.htmls.encoders.Gfo_url_encoder;
import gplx.langs.htmls.encoders.Gfo_url_encoder_;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryBfrUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.errs.Err;
import gplx.types.errs.ErrUtl;
public class Gfh_utl {// TS:Gfo_url_encoder is TS
	private static final Gfo_url_encoder encoder_id = Gfo_url_encoder_.Id;
	public static String Encode_id_as_str(byte[] key) {return StringUtl.NewU8(Encode_id_as_bry(key));}
	public static byte[] Encode_id_as_bry(byte[] key) {
		BryWtr tmp_bfr = BryBfrUtl.Get();
		try {
			byte[] escaped = Escape_html_as_bry(tmp_bfr, key, BoolUtl.N, BoolUtl.N, BoolUtl.N, BoolUtl.Y, BoolUtl.Y);
			return encoder_id.Encode(escaped);
		} finally {tmp_bfr.MkrRls();}
	}
	public static String Escape_for_atr_val_as_str(BryWtr bfr, byte quote_byte, String s) {return StringUtl.NewU8(Escape_for_atr_val_as_bry(bfr, quote_byte, s));}
	public static byte[] Escape_for_atr_val_as_bry(BryWtr bfr, byte quote_byte, String s) {
		if (s == null) return null;
		return Escape_for_atr_val_as_bry(bfr, quote_byte, BryUtl.NewU8(s));
	}
	public static byte[] Escape_for_atr_val_as_bry(BryWtr bfr, byte quote_byte, byte[] bry) {
		if (bry == null) return null;
		boolean dirty = Escape_for_atr_val_as_bry(bfr, quote_byte, bry, 0, bry.length);
		return dirty ? bfr.ToBryAndClear() : bry;
	}
	public static boolean Escape_for_atr_val_as_bry(BryWtr bfr, byte quote_byte, byte[] src, int bgn, int end) {
		boolean dirty = false;
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			if (b == quote_byte) {
				if (!dirty) {
					bfr.AddMid(src, bgn, i);
					dirty = true;
				}
				switch (quote_byte) {
					case AsciiByte.Apos: 	bfr.Add(HtmlEntityCodes.AposNumBry); break;
					case AsciiByte.Quote: 	bfr.Add(HtmlEntityCodes.QuoteBry); break;
					default: 				throw ErrUtl.NewUnhandled(quote_byte);
				}
			}
			else {
				if (dirty)
					bfr.AddByte(b);
			}
		}
		return dirty;
	}
	public static String Escape_html_as_str(String v)						{return StringUtl.NewU8(Escape_html_as_bry(BryUtl.NewU8(v)));}
	public static byte[] Escape_html_as_bry(BryWtr tmp, byte[] bry)		{return Escape_html(false, tmp, bry, 0, bry.length, true, true, true, true, true);}
	public static byte[] Escape_html_as_bry(byte[] bry)						{
		BryWtr tmp_bfr = BryBfrUtl.Get();
		try {return Escape_html(false, tmp_bfr, bry, 0, bry.length, true, true, true, true, true);}
		finally {tmp_bfr.MkrRls();}
	}
	public static byte[] Escape_html_as_bry(byte[] bry, boolean lt, boolean gt, boolean amp, boolean quote, boolean apos) {
		BryWtr tmp_bfr = BryBfrUtl.Get();
		try {return Escape_html(false, tmp_bfr, bry, 0, bry.length, lt, gt, amp, quote, apos);}
		finally {tmp_bfr.MkrRls();}
	}
	public static byte[] Escape_html_as_bry(BryWtr bfr, byte[] bry, boolean lt, boolean gt, boolean amp, boolean quote, boolean apos)
																			{return Escape_html(false, bfr, bry, 0, bry.length, lt, gt, amp, quote, apos);}
	public static void Escape_html_to_bfr(BryWtr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		Escape_html(true, bfr, bry, bgn, end, escape_lt, escape_gt, escape_amp, escape_quote, escape_apos);
	}
	private static byte[] Escape_html(boolean write_to_bfr, BryWtr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		byte[] escaped = null;
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			switch (b) {
				case AsciiByte.Lt: 	if (escape_lt)		escaped = HtmlEntityCodes.LtBry; break;
				case AsciiByte.Gt: 	if (escape_gt)		escaped = HtmlEntityCodes.GtBry; break;
				case AsciiByte.Amp:	if (escape_amp)		escaped = HtmlEntityCodes.AmpBry; break;
				case AsciiByte.Quote:	if (escape_quote)	escaped = HtmlEntityCodes.QuoteBry; break;
				case AsciiByte.Apos:	if (escape_apos)	escaped = HtmlEntityCodes.AposNumBry; break;
				default:
					if (dirty || write_to_bfr)
						bfr.AddByte(b);
					continue;
			}
			// handle lt, gt, amp, quote; everything else handled by default: continue above
			if (escaped == null) {	// handle do-not-escape calls; EX: Escape(y, y, n, y);
				if (dirty || write_to_bfr)
					bfr.AddByte(b);
			}
			else {
				if (!dirty) {
					bfr.AddMid(bry, bgn, i);
					dirty = true;
				}
				bfr.Add(escaped);
				escaped = null;
			}
		}
		if (write_to_bfr)
			return null;
		else
			return dirty ? bfr.ToBryAndClear() : bry;
	}
	private static final Btrie_slim_mgr unescape_trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(HtmlEntityCodes.LtBry, AsciiByte.Lt)
	.Add_bry_byte(HtmlEntityCodes.GtBry, AsciiByte.Gt)
	.Add_bry_byte(HtmlEntityCodes.AmpBry, AsciiByte.Amp)
	.Add_bry_byte(HtmlEntityCodes.QuoteBry, AsciiByte.Quote)
	.Add_bry_byte(HtmlEntityCodes.AposNumBry, AsciiByte.Apos)
	;
	public static String Unescape_as_str(String src) {
		BryWtr bfr = BryWtr.NewAndReset(255);
		byte[] bry = BryUtl.NewU8(src);
		Unescape(BoolUtl.Y, bfr, bry, 0, bry.length, BoolUtl.Y, BoolUtl.Y, BoolUtl.Y, BoolUtl.Y, BoolUtl.Y);
		return bfr.ToStrAndClear();
	}
	public static byte[] Unescape(boolean write_to_bfr, BryWtr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		int pos = bgn;
		Btrie_rv trv = new Btrie_rv();
		while (pos < end) {
			byte b = bry[pos];
			Object o = unescape_trie.Match_at_w_b0(trv, b, bry, pos, end);
			if (o == null) {
				if (dirty || write_to_bfr)
					bfr.AddByte(b);
				++pos;
			}
			else {
				ByteVal unescaped_bval = (ByteVal)o;
				byte unescaped_byte = unescaped_bval.Val();
				boolean unescape = false;
				switch (unescaped_byte) {
					case AsciiByte.Lt: 	if (escape_lt)		unescape = true; break;
					case AsciiByte.Gt: 	if (escape_gt)		unescape = true; break;
					case AsciiByte.Amp:	if (escape_amp)		unescape = true; break;
					case AsciiByte.Quote:	if (escape_quote)	unescape = true; break;
					case AsciiByte.Apos:	if (escape_apos)	unescape = true; break;
				}
				if (unescape) {
					if (!dirty) {
						bfr.AddMid(bry, bgn, pos);
						dirty = true;
					}
					bfr.AddByte(unescaped_byte);
				}
				else {
					if (dirty || write_to_bfr)
						bfr.AddByte(b);
				}
				pos = trv.Pos();
			}
		}
		if (write_to_bfr)
			return null;
		else
			return dirty ? bfr.ToBryAndClear() : bry;
	}
	public static byte[] Del_comments(BryWtr bfr, byte[] src) {return Del_comments(bfr, src, 0, src.length);}
	public static byte[] Del_comments(BryWtr bfr, byte[] src, int pos, int end) {
		while (true) {
			if (pos >= end) break;
			int comm_bgn = BryFind.FindFwd(src, Gfh_tag_.Comm_bgn, pos);				// look for <!--
			if (comm_bgn == BryFind.NotFound) {										// <!-- not found;
				bfr.AddMid(src, pos, end);												// add everything between pos and <!--
				break;																	// stop checking
			}
			int comm_bgn_rhs = comm_bgn + Gfh_tag_.Comm_bgn_len;
			int comm_end = BryFind.FindFwd(src, Gfh_tag_.Comm_end, comm_bgn_rhs);	// look for -->
			if (comm_end == BryFind.NotFound) {										// --> not found
				bfr.AddMid(src, pos, comm_bgn);										// add everything between pos and comm_bgn; EX: "a<!--b->" must add "a"
				break;																	// stop checking
			}
			bfr.AddMid(src, pos, comm_bgn);											// add everything between pos and comm_bgn
			pos = comm_end + Gfh_tag_.Comm_end_len;										// reposition pos after comm_end
		}
		return bfr.ToBryAndClear();
	}
	public static String Replace_apos(String s) {return StringUtl.Replace(s, "'", "\"");}
	public static String Replace_apos_concat_lines(String... lines) {
		BryWtr bfr = BryWtr.New();
		int len = lines.length;
		for (int i = 0; i < len; ++i) {
			String line_str = lines[i];
			byte[] line_bry = BryUtl.NewU8(line_str);
			BryUtl.ReplaceAllDirect(line_bry, AsciiByte.Apos, AsciiByte.Quote, 0, line_bry.length);
			if (i != 0) bfr.AddByteNl();
			bfr.Add(line_bry);
		}
		return bfr.ToStrAndClear();
	}
	public static void Log(Exception e, String head, byte[] page_url, byte[] src, int pos) {
		Err err = ErrUtl.CastOrWrap(e); if (err.Logged()) return;
		String msg = StringUtl.Format("{0}; page={1} err={2} mid={3} trace={4}", head, page_url, ErrUtl.Message(e), BryUtlByWtr.EscapeWs(BryUtl.MidByLenSafe(src, pos, 255)), err.ToStrLog());
		Gfo_usr_dlg_.Instance.Warn_many("", "", msg);
	}
}
