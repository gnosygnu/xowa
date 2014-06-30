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
public class Html_utl {
	public static byte[] Escape_for_atr_val_as_bry(String s, byte quote_byte) {
		Bry_bfr tmp_bfr = null;
		if (s == null) return null;
		byte[] bry = Bry_.new_utf8_(s);
		boolean dirty = false;
		int len = bry.length;
		for (int i = 0; i < len; i++) {
			byte b = bry[i];
			if (b == quote_byte) {
				if (!dirty) {
					tmp_bfr = Bry_bfr.reset_(256);
					tmp_bfr.Add_mid(bry, 0, i);
					dirty = true;
				}
				switch (quote_byte) {
					case Byte_ascii.Apos: 	tmp_bfr.Add(Html_consts.Apos); break;
					case Byte_ascii.Quote: 	tmp_bfr.Add(Html_consts.Quote); break;
					default: 				throw Err_.unhandled(quote_byte);
				}
			}
			else {
				if (dirty)
					tmp_bfr.Add_byte(b);
			}
		}
		return dirty ? tmp_bfr.XtoAryAndClear() : bry;
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
				case Byte_ascii.Lt: 	if (escape_lt)		escaped = Html_consts.Lt; break;
				case Byte_ascii.Gt: 	if (escape_gt)		escaped = Html_consts.Gt; break;
				case Byte_ascii.Amp:	if (escape_amp)		escaped = Html_consts.Amp; break;
				case Byte_ascii.Quote:	if (escape_quote)	escaped = Html_consts.Quote; break;
				case Byte_ascii.Apos:	if (escape_apos)	escaped = Html_consts.Apos; break;
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
			return dirty ? bfr.XtoAryAndClear() : bry;
	}

	private static final ByteTrieMgr_slim unescape_trie = ByteTrieMgr_slim.ci_ascii_()
	.Add_bry_bval(Html_consts.Lt		, Byte_ascii.Lt)
	.Add_bry_bval(Html_consts.Gt		, Byte_ascii.Gt)
	.Add_bry_bval(Html_consts.Amp		, Byte_ascii.Amp)
	.Add_bry_bval(Html_consts.Quote		, Byte_ascii.Quote)
	.Add_bry_bval(Html_consts.Apos		, Byte_ascii.Apos)
	;
	public static String Unescape_as_str(String src) {
		Bry_bfr bfr = Bry_bfr.reset_(255);
		byte[] bry = Bry_.new_utf8_(src);
		Unescape(Bool_.Y, bfr, bry, 0, bry.length, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y);
		return bfr.XtoStrAndClear();
	}
	public static byte[] Unescape(boolean write_to_bfr, Bry_bfr bfr, byte[] bry, int bgn, int end, boolean escape_lt, boolean escape_gt, boolean escape_amp, boolean escape_quote, boolean escape_apos) {
		if (bry == null) return null;
		boolean dirty = write_to_bfr ? true : false;	// if write_to_bfr, then mark true, else bfr.Add_mid(bry, 0, i); will write whole bry again
		int pos = bgn;
		while (pos < end) {
			byte b = bry[pos];
			Object o = unescape_trie.Match(b, bry, pos, end);
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
			return dirty ? bfr.XtoAryAndClear() : bry;
	}
	public static byte[] Del_comments(Bry_bfr bfr, byte[] src) {return Del_comments(bfr, src, 0, src.length);}
	public static byte[] Del_comments(Bry_bfr bfr, byte[] src, int pos, int end) {
		while (true) {
			if (pos >= end) break;
			int comm_bgn = Bry_finder.Find_fwd(src, Html_consts.Comm_bgn, pos);											// look for <!--
			if (comm_bgn == Bry_finder.Not_found) {																		// not found; consume rest
				bfr.Add_mid(src, pos, end);
				break;
			}
			int comm_end = Bry_finder.Find_fwd(src, Html_consts.Comm_end, comm_bgn + Html_consts.Comm_bgn_len);			// look for -->
			if (comm_end == Bry_finder.Not_found) {																		// not found; consume rest
				bfr.Add_mid(src, pos, end);
				break;
			}
			bfr.Add_mid(src, pos, comm_bgn);																					// add everything between pos and comm_bgn
			pos = comm_end + Html_consts.Comm_end_len;																			// reposition pos after comm_end
		}
		return bfr.XtoAryAndClear();
	}
}
