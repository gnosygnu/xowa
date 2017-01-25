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
public class Xomw_string_utl {
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
