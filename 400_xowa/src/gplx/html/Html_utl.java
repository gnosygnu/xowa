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
package gplx.html; import gplx.*;
import gplx.core.btries.*;
public class Html_utl {
	public static byte[] Escape_for_atr_val_as_bry(Bry_bfr tmp_bfr, byte quote_byte, String s) {
		if (s == null) return null;
		return Escape_for_atr_val_as_bry(tmp_bfr, quote_byte, Bry_.new_utf8_(s));
	}
	public static byte[] Escape_for_atr_val_as_bry(Bry_bfr tmp_bfr, byte quote_byte, byte[] bry) {
		if (bry == null) return null;
		boolean dirty = Escape_for_atr_val_as_bry(tmp_bfr, quote_byte, bry, 0, bry.length);
		return dirty ? tmp_bfr.Xto_bry_and_clear() : bry;
	}
	public static boolean Escape_for_atr_val_as_bry(Bry_bfr tmp_bfr, byte quote_byte, byte[] src, int bgn, int end) {
		boolean dirty = false;
		for (int i = bgn; i < end; i++) {
			byte b = src[i];
			if (b == quote_byte) {
				if (!dirty) {
					tmp_bfr.Add_mid(src, bgn, i);
					dirty = true;
				}
				switch (quote_byte) {
					case Byte_ascii.Apos: 	tmp_bfr.Add(Html_entity_.Apos_num_bry); break;
					case Byte_ascii.Quote: 	tmp_bfr.Add(Html_entity_.Quote_bry); break;
					default: 				throw Err_.unhandled(quote_byte);
				}
			}
			else {
				if (dirty)
					tmp_bfr.Add_byte(b);
			}
		}
		return dirty;
	}
	public static String Escape_html_as_str(String v)						{return String_.new_utf8_(Escape_html_as_bry(Bry_.new_utf8_(v)));}
	public static byte[] Escape_html_as_bry(Bry_bfr tmp, byte[] bry)		{return Escape_html(false, tmp, bry, 0, bry.length, true, true, true, true, true);}
	public static byte[] Escape_html_as_bry(byte[] bry)						{return Escape_html(false, Bry_bfr.new_(), bry, 0, bry.length, true, true, true, true, true);}
	public static byte[] Escape_html_as_bry(byte[] bry, boolean lt, boolean gt, boolean amp, boolean quote, boolean apos)
																			{return Escape_html(false, Bry_bfr.new_(), bry, 0, bry.length, lt, gt, amp, quote, apos);}
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
				case Byte_ascii.Lt: 	if (escape_lt)		escaped = Html_entity_.Lt_bry; break;
				case Byte_ascii.Gt: 	if (escape_gt)		escaped = Html_entity_.Gt_bry; break;
				case Byte_ascii.Amp:	if (escape_amp)		escaped = Html_entity_.Amp_bry; break;
				case Byte_ascii.Quote:	if (escape_quote)	escaped = Html_entity_.Quote_bry; break;
				case Byte_ascii.Apos:	if (escape_apos)	escaped = Html_entity_.Apos_num_bry; break;
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
			return dirty ? bfr.Xto_bry_and_clear() : bry;
	}

	private static final Btrie_slim_mgr unescape_trie = Btrie_slim_mgr.ci_ascii_()
	.Add_bry_bval(Html_entity_.Lt_bry		, Byte_ascii.Lt)
	.Add_bry_bval(Html_entity_.Gt_bry		, Byte_ascii.Gt)
	.Add_bry_bval(Html_entity_.Amp_bry		, Byte_ascii.Amp)
	.Add_bry_bval(Html_entity_.Quote_bry	, Byte_ascii.Quote)
	.Add_bry_bval(Html_entity_.Apos_num_bry	, Byte_ascii.Apos)
	;
	public static String Unescape_as_str(String src) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		byte[] bry = Bry_.new_utf8_(src);
		Unescape(Bool_.Y, bfr, bry, 0, bry.length, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y);
		return bfr.Xto_str_and_clear();
	}
	public static byte[] Unescape(boolean write_to_bfr, Bry_bfr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		int pos = bgn;
		while (pos < end) {
			byte b = bry[pos];
			Object o = unescape_trie.Match_bgn_w_byte(b, bry, pos, end);
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
				pos = unescape_trie.Match_pos();
			}
		}
		if (write_to_bfr)
			return null;
		else
			return dirty ? bfr.Xto_bry_and_clear() : bry;
	}
	public static byte[] Del_comments(Bry_bfr bfr, byte[] src) {return Del_comments(bfr, src, 0, src.length);}
	public static byte[] Del_comments(Bry_bfr bfr, byte[] src, int pos, int end) {
		while (true) {
			if (pos >= end) break;
			int comm_bgn = Bry_finder.Find_fwd(src, Html_tag_.Comm_bgn, pos);											// look for <!--
			if (comm_bgn == Bry_finder.Not_found) {																		// not found; consume rest
				bfr.Add_mid(src, pos, end);
				break;
			}
			int comm_end = Bry_finder.Find_fwd(src, Html_tag_.Comm_end, comm_bgn + Html_tag_.Comm_bgn_len);			// look for -->
			if (comm_end == Bry_finder.Not_found) {																		// not found; consume rest
				bfr.Add_mid(src, pos, end);
				break;
			}
			bfr.Add_mid(src, pos, comm_bgn);																					// add everything between pos and comm_bgn
			pos = comm_end + Html_tag_.Comm_end_len;																			// reposition pos after comm_end
		}
		return bfr.Xto_bry_and_clear();
	}
}
