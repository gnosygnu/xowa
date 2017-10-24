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
package gplx.langs.htmls; import gplx.*; import gplx.langs.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.langs.htmls.encoders.*;
import gplx.langs.htmls.entitys.*;
public class Gfh_utl {// TS:Gfo_url_encoder is TS
	private static final    Gfo_url_encoder encoder_id = Gfo_url_encoder_.Id;
	public static String Encode_id_as_str(byte[] key) {return String_.new_u8(Encode_id_as_bry(key));}
	public static byte[] Encode_id_as_bry(byte[] key) {
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		try {
			byte[] escaped = Escape_html_as_bry(tmp_bfr, key, Bool_.N, Bool_.N, Bool_.N, Bool_.Y, Bool_.Y);
			return encoder_id.Encode(escaped);
		} finally {tmp_bfr.Mkr_rls();}
	}
	public static String Escape_for_atr_val_as_str(Bry_bfr bfr, byte quote_byte, String s) {return String_.new_u8(Escape_for_atr_val_as_bry(bfr, quote_byte, s));}
	public static byte[] Escape_for_atr_val_as_bry(Bry_bfr bfr, byte quote_byte, String s) {
		if (s == null) return null;
		return Escape_for_atr_val_as_bry(bfr, quote_byte, Bry_.new_u8(s));
	}
	public static byte[] Escape_for_atr_val_as_bry(Bry_bfr bfr, byte quote_byte, byte[] bry) {
		if (bry == null) return null;
		boolean dirty = Escape_for_atr_val_as_bry(bfr, quote_byte, bry, 0, bry.length);
		return dirty ? bfr.To_bry_and_clear() : bry;
	}
	public static boolean Escape_for_atr_val_as_bry(Bry_bfr bfr, byte quote_byte, byte[] src, int bgn, int end) {
		boolean dirty = false;
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			if (b == quote_byte) {
				if (!dirty) {
					bfr.Add_mid(src, bgn, i);
					dirty = true;
				}
				switch (quote_byte) {
					case Byte_ascii.Apos: 	bfr.Add(Gfh_entity_.Apos_num_bry); break;
					case Byte_ascii.Quote: 	bfr.Add(Gfh_entity_.Quote_bry); break;
					default: 				throw Err_.new_unhandled(quote_byte);
				}
			}
			else {
				if (dirty)
					bfr.Add_byte(b);
			}
		}
		return dirty;
	}
	public static String Escape_html_as_str(String v)						{return String_.new_u8(Escape_html_as_bry(Bry_.new_u8(v)));}
	public static byte[] Escape_html_as_bry(Bry_bfr tmp, byte[] bry)		{return Escape_html(false, tmp, bry, 0, bry.length, true, true, true, true, true);}
	public static byte[] Escape_html_as_bry(byte[] bry)						{
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		try {return Escape_html(false, tmp_bfr, bry, 0, bry.length, true, true, true, true, true);}
		finally {tmp_bfr.Mkr_rls();}
	}
	public static byte[] Escape_html_as_bry(byte[] bry, boolean lt, boolean gt, boolean amp, boolean quote, boolean apos) {
		Bry_bfr tmp_bfr = Bry_bfr_.Get();
		try {return Escape_html(false, tmp_bfr, bry, 0, bry.length, lt, gt, amp, quote, apos);}
		finally {tmp_bfr.Mkr_rls();}
	}
	public static byte[] Escape_html_as_bry(Bry_bfr bfr, byte[] bry, boolean lt, boolean gt, boolean amp, boolean quote, boolean apos)
																			{return Escape_html(false, bfr, bry, 0, bry.length, lt, gt, amp, quote, apos);}
	public static void Escape_html_to_bfr(Bry_bfr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		Escape_html(true, bfr, bry, bgn, end, escape_lt, escape_gt, escape_amp, escape_quote, escape_apos);
	}
	private static byte[] Escape_html(boolean write_to_bfr, Bry_bfr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		byte[] escaped = null;
		for (int i = bgn; i < end; i++) {
			byte b = bry[i];
			switch (b) {
				case Byte_ascii.Lt: 	if (escape_lt)		escaped = Gfh_entity_.Lt_bry; break;
				case Byte_ascii.Gt: 	if (escape_gt)		escaped = Gfh_entity_.Gt_bry; break;
				case Byte_ascii.Amp:	if (escape_amp)		escaped = Gfh_entity_.Amp_bry; break;
				case Byte_ascii.Quote:	if (escape_quote)	escaped = Gfh_entity_.Quote_bry; break;
				case Byte_ascii.Apos:	if (escape_apos)	escaped = Gfh_entity_.Apos_num_bry; break;
				default:
					if (dirty || write_to_bfr)
						bfr.Add_byte(b);
					continue;
			}
			// handle lt, gt, amp, quote; everything else handled by default: continue above
			if (escaped == null) {	// handle do-not-escape calls; EX: Escape(y, y, n, y);
				if (dirty || write_to_bfr)
					bfr.Add_byte(b);
			}
			else {
				if (!dirty) {
					bfr.Add_mid(bry, bgn, i);
					dirty = true;
				}
				bfr.Add(escaped);
				escaped = null;
			}
		}
		if (write_to_bfr)
			return null;
		else
			return dirty ? bfr.To_bry_and_clear() : bry;
	}
	private static final    Btrie_slim_mgr unescape_trie = Btrie_slim_mgr.ci_a7()
	.Add_bry_byte(Gfh_entity_.Lt_bry		, Byte_ascii.Lt)
	.Add_bry_byte(Gfh_entity_.Gt_bry		, Byte_ascii.Gt)
	.Add_bry_byte(Gfh_entity_.Amp_bry		, Byte_ascii.Amp)
	.Add_bry_byte(Gfh_entity_.Quote_bry	, Byte_ascii.Quote)
	.Add_bry_byte(Gfh_entity_.Apos_num_bry	, Byte_ascii.Apos)
	;
	public static String Unescape_as_str(String src) {
		Bry_bfr bfr = Bry_bfr_.Reset(255);
		byte[] bry = Bry_.new_u8(src);
		Unescape(Bool_.Y, bfr, bry, 0, bry.length, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y);
		return bfr.To_str_and_clear();
	}
	public static byte[] Unescape(boolean write_to_bfr, Bry_bfr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		int pos = bgn;
		Btrie_rv trv = new Btrie_rv();
		while (pos < end) {
			byte b = bry[pos];
			Object o = unescape_trie.Match_at_w_b0(trv, b, bry, pos, end);
			if (o == null) {
				if (dirty || write_to_bfr)
					bfr.Add_byte(b);
				++pos;
			}
			else {
				Byte_obj_val unescaped_bval = (Byte_obj_val)o;
				byte unescaped_byte = unescaped_bval.Val();
				boolean unescape = false;
				switch (unescaped_byte) {
					case Byte_ascii.Lt: 	if (escape_lt)		unescape = true; break;
					case Byte_ascii.Gt: 	if (escape_gt)		unescape = true; break;
					case Byte_ascii.Amp:	if (escape_amp)		unescape = true; break;
					case Byte_ascii.Quote:	if (escape_quote)	unescape = true; break;
					case Byte_ascii.Apos:	if (escape_apos)	unescape = true; break;
				}
				if (unescape) {
					if (!dirty) {
						bfr.Add_mid(bry, bgn, pos);
						dirty = true;
					}
					bfr.Add_byte(unescaped_byte);
				}
				else {
					if (dirty || write_to_bfr)
						bfr.Add_byte(b);
				}
				pos = trv.Pos();
			}
		}
		if (write_to_bfr)
			return null;
		else
			return dirty ? bfr.To_bry_and_clear() : bry;
	}
	public static byte[] Del_comments(Bry_bfr bfr, byte[] src) {return Del_comments(bfr, src, 0, src.length);}
	public static byte[] Del_comments(Bry_bfr bfr, byte[] src, int pos, int end) {
		while (true) {
			if (pos >= end) break;
			int comm_bgn = Bry_find_.Find_fwd(src, Gfh_tag_.Comm_bgn, pos);				// look for <!--
			if (comm_bgn == Bry_find_.Not_found) {										// <!-- not found; 
				bfr.Add_mid(src, pos, end);												// add everything between pos and <!--
				break;																	// stop checking
			}
			int comm_bgn_rhs = comm_bgn + Gfh_tag_.Comm_bgn_len;
			int comm_end = Bry_find_.Find_fwd(src, Gfh_tag_.Comm_end, comm_bgn_rhs);	// look for -->
			if (comm_end == Bry_find_.Not_found) {										// --> not found
				bfr.Add_mid(src, pos, comm_bgn);										// add everything between pos and comm_bgn; EX: "a<!--b->" must add "a"
				break;																	// stop checking
			}
			bfr.Add_mid(src, pos, comm_bgn);											// add everything between pos and comm_bgn
			pos = comm_end + Gfh_tag_.Comm_end_len;										// reposition pos after comm_end
		}
		return bfr.To_bry_and_clear();
	}
	public static String Replace_apos(String s) {return String_.Replace(s, "'", "\"");}
	public static String Replace_apos_concat_lines(String... lines) {
		Bry_bfr bfr = Bry_bfr_.New();
		int len = lines.length;
		for (int i = 0; i < len; ++i) {
			String line_str = lines[i];
			byte[] line_bry = Bry_.new_u8(line_str);
			Bry_.Replace_all_direct(line_bry, Byte_ascii.Apos, Byte_ascii.Quote, 0, line_bry.length);
			if (i != 0) bfr.Add_byte_nl();
			bfr.Add(line_bry);
		}
		return bfr.To_str_and_clear();
	}
	public static void Log(Exception e, String head, byte[] page_url, byte[] src, int pos) {
		Err err = Err_.Cast_or_make(e); if (err.Logged()) return;
		String msg = String_.Format("{0}; page={1} err={2} mid={3} trace={4}", head, page_url, Err_.Message_lang(e), Bry_.Escape_ws(Bry_.Mid_by_len_safe(src, pos, 255)), err.To_str__log());
		Gfo_usr_dlg_.Instance.Warn_many("", "", msg);
	}
}
