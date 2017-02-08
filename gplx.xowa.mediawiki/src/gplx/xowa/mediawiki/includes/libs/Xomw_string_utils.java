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
package gplx.xowa.mediawiki.includes.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.btries.*;
public class Xomw_string_utils {
	// Explode a String, but ignore any instances of the separator inside
	// the given start and end delimiters, which may optionally nest.
	// The delimiters are literal strings, not regular expressions.
	// @param String bgn_delim Start delimiter
	// @param String end_delim End delimiter
	// @param String separator Separator String for the explode.
	// @param String subject Subject String to explode.
	// @param boolean nested True iff the delimiters are allowed to nest.
	// @return ArrayIterator
	// XO.MW: hard-coding (a) nested=true; (b) bgn="-{" end="}-" sep="|"
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	private static final byte Delimiter_explode__sep = 0, Delimiter_explode__bgn = 1, Delimiter_explode__end = 2;
	private static final    Btrie_slim_mgr delimiter_explode_trie = Btrie_slim_mgr.cs()
		.Add_str_byte("|" , Delimiter_explode__sep)
		.Add_str_byte("-{", Delimiter_explode__bgn)
		.Add_str_byte("}-", Delimiter_explode__end)
		;
	public static byte[][] Delimiter_explode(List_adp tmp, Btrie_rv trv, byte[] src) {
		int src_bgn = 0;
		int src_end = src.length;

		int depth = 0;
		int cur = src_bgn;
		int prv = cur;
		while (true) {
			// eos
			if (cur == src_end) {
				// add rest
				tmp.Add(Bry_.Mid(src, prv, src_end));
				break;
			}

			Object o = delimiter_explode_trie.Match_at(trv, src, cur, src_end);

			// regular char; continue;
			if (o == null) {
				cur++;
				continue;
			}

			// handle sep, bgn, end
			byte tid = ((gplx.core.primitives.Byte_obj_val)o).Val();
			switch (tid) {
				case Delimiter_explode__sep:
					if (depth == 0) {
						tmp.Add(Bry_.Mid(src, prv, cur));
						prv = cur + 1;
					}
					break;
				case Delimiter_explode__bgn:
					depth++;
					break;
				case Delimiter_explode__end:
					depth--;
					break;
			}
			cur = trv.Pos();
		}
		return (byte[][])tmp.To_ary_and_clear(byte[].class);
	}
	// More or less "markup-safe" str_replace()
	// Ignores any instances of the separator inside `<...>`
	public static void Replace_markup(byte[] src, int src_bgn, int src_end, byte[] find, byte[] repl) {	// REF:/includes/libs/StringUtils.php|replaceMarkup
		// PORTED: avoiding multiple regex calls / String creations
		// $placeholder = "\x00";

		// Remove placeholder instances
		// $text = str_replace( $placeholder, '', $text );

		// Replace instances of the separator inside HTML-like tags with the placeholder
		// $replacer = new DoubleReplacer( $search, $placeholder );
		// $cleaned = StringUtils::delimiterReplaceCallback( '<', '>', $replacer->cb(), $text );

		// Explode, then put the replaced separators back in
		// $cleaned = str_replace( $search, $replace, $cleaned );
		// $text = str_replace( $placeholder, $search, $cleaned );

		// if same length find / repl, do in-place replacement; EX: "!!"  -> "||"
		int find_len = find.length;
		int repl_len = repl.length;
		if (find_len != repl_len) throw Err_.new_wo_type("find and repl should be same length");

		byte find_0 = find[0];
		byte dlm_bgn = Byte_ascii.Angle_bgn;
		byte dlm_end = Byte_ascii.Angle_end;
		boolean repl_active = true;

		// loop every char in array
		for (int i = src_bgn; i < src_end; i++) {
			byte b = src[i];
			if (   b == find_0
				&& Bry_.Match(src, i + 1, i + find_len, find, 1, find_len)
				&& repl_active
				) {
				Bry_.Set(src, i, i + find_len, repl);
			}
			else if (b == dlm_bgn) {
				repl_active = false;
			}
			else if (b == dlm_end) {
				repl_active = true;
			}
		}
	}
}
