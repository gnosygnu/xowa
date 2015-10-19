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
package gplx.xowa.specials.search.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*; import gplx.xowa.specials.search.*;
import gplx.core.primitives.*;
import gplx.xowa.langs.*;
class Xows_text_parser__v1 {
	private Xol_lang_itm lang; private Bry_bfr bfr = Bry_bfr.new_(255);
	private final Ordered_hash list = Ordered_hash_.New_bry();
	public void Init(Xol_lang_itm lang) {this.lang = lang;}
	public void Parse(byte[] src, int src_len, int bgn, int end) {
		if (lang != null) {	// null lang passed in by searcher
			src = lang.Case_mgr().Case_build_lower(src);
			src_len = src.length;
		}
		int i = 0; boolean word_done = false;
		while (true) {
			if (word_done || i == src_len) {
				if (bfr.Len() > 0) {
					byte[] word = bfr.To_bry_and_clear();
					if (!list.Has(word)) list.Add(word, word);	// don't add same word twice; EX: Title of "Can Can" should only have "Can" in index
				}
				if (i == src_len) break;
				word_done = false;
			}
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Underline:	// underline is word-breaking; EX: A_B -> A, B
				case Byte_ascii.Space:		// should not occur, but just in case (only underscores)
				case Byte_ascii.Tab: case Byte_ascii.Nl: case Byte_ascii.Cr:	// should not occur in titles, but just in case

				case Byte_ascii.Dash:	// treat hypenated words separately
				case Byte_ascii.Dot:	// treat abbreviations as separate words; EX: A.B.C.
				case Byte_ascii.Bang: case Byte_ascii.Hash: case Byte_ascii.Dollar: case Byte_ascii.Percent:
				case Byte_ascii.Amp: case Byte_ascii.Paren_bgn: case Byte_ascii.Paren_end: case Byte_ascii.Star:
				case Byte_ascii.Comma: case Byte_ascii.Slash:
				case Byte_ascii.Colon: case Byte_ascii.Semic: case Byte_ascii.Gt:
				case Byte_ascii.Question: case Byte_ascii.At: case Byte_ascii.Brack_bgn: case Byte_ascii.Brack_end:
				case Byte_ascii.Pow: case Byte_ascii.Tick:
				case Byte_ascii.Curly_bgn: case Byte_ascii.Pipe: case Byte_ascii.Curly_end: case Byte_ascii.Tilde:
				case Byte_ascii.Quote:	case Byte_ascii.Apos: // FUTURE: apos will split "Earth's" to Earth and s; should remove latter
					++i;
					word_done = true;
					break;
				default:
					bfr.Add_byte(b);
					++i;
					break;
			}
		}
//			byte[][] rv = (byte[][])list.To_ary(typeof(byte[]));
//			list.Clear(); list.Resize_bounds(16);
//			return rv;
	}
}
