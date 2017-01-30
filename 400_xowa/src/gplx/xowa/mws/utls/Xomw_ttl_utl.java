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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
public class Xomw_ttl_utl {
	// REF.MW: DefaultSettings.php
	// Allowed title characters -- regex character class
	// Don't change this unless you know what you're doing
	//
	// Problematic punctuation:
	//   -  []{}|#    Are needed for link syntax, never enable these
	//   -  <>        Causes problems with HTML escaping, don't use
	//   -  %         Enabled by default, minor problems with path to query rewrite rules, see below
	//   -  +         Enabled by default, but doesn't work with path to query rewrite rules,
	//                corrupted by apache
	//   -  ?         Enabled by default, but doesn't work with path to PATH_INFO rewrites
	//
	// All three of these punctuation problems can be avoided by using an alias,
	// instead of a rewrite rule of either variety.
	//
	// The problem with % is that when using a path to query rewrite rule, URLs are
	// double-unescaped: once by Apache's path conversion code, and again by PHP. So
	// %253F, for example, becomes "?". Our code does not double-escape to compensate
	// for this, indeed double escaping would break if the double-escaped title was
	// passed in the query String rather than the path. This is a minor security issue
	// because articles can be created such that they are hard to view or edit.
	//
	// In some rare cases you may wish to remove + for compatibility with old links.
	//
	// Theoretically 0x80-0x9F of ISO 8859-1 should be disallowed, but
	// this breaks interlanguage links
	// $wgLegalTitleChars = " %!\"$&'()*,\\-.\\/0-9:;=?@A-Z\\\\^_`a-z~\\x80-\\xFF+";
	//
	// REGEX: 
	//   without-backslash escaping  -->  \s%!"$&'()*,-./0-9:;=?@A-Z\^_`a-z~x80-xFF+
	//   rearranged
	//       letters                 --> 0-9A-Za-z
	//       unicode-chars           --> x80-xFF
	//       symbols                 --> \s%!"$&'()*,-./:;=?@\^_`~+"
	//   deliberately ignores
	//       control chars: 00-31,127
	//       []{}|#<>
	public static int Find_fwd_while_title(byte[] src, int src_bgn, int src_end, boolean[] valid) {
		int cur = src_bgn;
		while (true) {
			if (cur == src_end) break;
			byte b = src[cur];
			int b_len = gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
			if (b_len == 1) {         // ASCII
				if (valid[b & 0xFF])  // valid; EX: "a0A B&$"; PATCH.JAVA:need to convert to unsigned byte
					cur++;
				else                  // invalid; EX: "<title>"
					break;
			}
			else {                    // Multi-byte UTF8; NOTE: all sequences are valid
				cur += b_len;
			}
		}
		return cur;
	}
	private static boolean[] title_chars_valid;
	public static boolean[] Title_chars_valid() {
		if (title_chars_valid == null) {
			title_chars_valid = new boolean[128];
			// add num and alpha
			for (int i = Byte_ascii.Num_0; i <= Byte_ascii.Num_9; i++)
				title_chars_valid[i] = true;
			for (int i = Byte_ascii.Ltr_A; i <= Byte_ascii.Ltr_Z; i++)
				title_chars_valid[i] = true;
			for (int i = Byte_ascii.Ltr_a; i <= Byte_ascii.Ltr_z; i++)
				title_chars_valid[i] = true;

			// add symbols: \s%!"$&'()*,-./:;=?@\^_`~+"
			byte[] symbols = new byte[]
			{ Byte_ascii.Space
			, Byte_ascii.Percent
			, Byte_ascii.Bang
			, Byte_ascii.Quote
			, Byte_ascii.Amp
			, Byte_ascii.Apos
			, Byte_ascii.Paren_bgn
			, Byte_ascii.Paren_end
			, Byte_ascii.Star
			, Byte_ascii.Comma
			, Byte_ascii.Dash
			, Byte_ascii.Dot
			, Byte_ascii.Slash
			, Byte_ascii.Colon
			, Byte_ascii.Semic
			, Byte_ascii.Eq
			, Byte_ascii.Question
			, Byte_ascii.At
			, Byte_ascii.Backslash
			, Byte_ascii.Pow
			, Byte_ascii.Underline
			, Byte_ascii.Tick
			, Byte_ascii.Tilde
			, Byte_ascii.Plus
			};
			int symbols_len = symbols.length;
			for (int i = 0; i < symbols_len; i++)
				title_chars_valid[symbols[i]] = true;
		}
		return title_chars_valid;
	}
}
