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
package gplx.xowa.mws.parsers.doubleunders; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import gplx.core.btries.*;
// TODO.CS: handle case sensitive keys; EX: __notoc__ should not match __NOTOC__ if cs is enabled for magic word
public class Xomw_doubleunder_wkr {
	private final    Btrie_slim_mgr trie = Btrie_slim_mgr.ci_u8();
	private final    Btrie_rv trv = new Btrie_rv();
	public Xomw_doubleunder_data data = new Xomw_doubleunder_data();
	public void Init_by_wiki() {
		// TODO.XO: pull from lang
		trie.Add_str_byte("__TOC__", Tid__toc);
		trie.Add_str_byte("__NOTOC__", Tid__no_toc);
		trie.Add_str_byte("__FORCETOC__", Tid__force_toc);
	}
	public void Do_double_underscore(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {	// REF.MW: text = preg_replace('/(^|\n)-----*/', '\\1<hr />', text);
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();

		data.Reset();
		// XO.MW: MW does TOC before others; XO does it at the same time

		// Now match and remove the rest of them
		// XO.MW.BGN: $this->mDoubleUnderscores = $mwa->matchAndRemove( $text );
		int cur = src_bgn;
		int prv = cur;
		boolean dirty = false;
		while (true) {
			if (cur == src_end) {
				if (dirty) {
					bfr.Add_mid(src, prv, src_end);
				}
				break;
			}

			byte b = src[cur];
			Object o = trie.Match_at_w_b0(trv, b, src, cur, src_end);
			if (o == null) {
				cur += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
				continue;
			}

			dirty = true;
			bfr.Add_mid(src, prv, cur);
			byte tid = ((gplx.core.primitives.Byte_obj_val)o).Val();
			switch (tid) {
				case Tid__toc:
					// The position of __TOC__ needs to be recorded
					boolean already_seen = !data.show_toc;
					data.toc = true;
					data.show_toc = true;
					data.force_toc_position = true;

					if (already_seen) { // Set a placeholder. At the end we'll fill it in with the TOC.
						bfr.Add_str_a7("<!--MWTOC-->");
					}
					else { // Only keep the first one. XO.MW:ignore by not adding anything to bfr
					}
					break;
				// XO.MW: MW adds boolean to hash_table; XO uses boolean props; note that "remove" is done by not adding to bfr
				case Tid__no_toc:                data.no_toc = true; break;
				case Tid__no_gallery:            data.no_gallery = true; break;
				case Tid__force_toc:             data.force_toc = true; break;
				case Tid__no_edit_section:       data.no_edit_section = true; break;
				case Tid__new_section_link:      data.new_section_link = true; break;
				case Tid__hidden_cat:            data.hidden_cat = true; break;
				case Tid__index:                 data.index = true; break;
				case Tid__no_index:              data.no_index = true; break;
				case Tid__static_redirect:       data.static_redirect = true; break;
				case Tid__no_title_convert:      data.no_title_convert = true; break;
				case Tid__no_content_convert:    data.no_content_convert = true; break;
				default:                         throw Err_.new_unhandled_default(tid);
			}
			cur = trv.Pos();
			prv = cur;
		}
		// XO.MW.END: $this->mDoubleUnderscores = $mwa->matchAndRemove( $text );

		if (data.no_toc && !data.force_toc_position) {
			data.show_toc = false;
		}

		// XO.MW.EDIT: hidden_cat, index, noindex are used to add to tracking category
		if (dirty)
			pbfr.Switch();
	}

	private static final byte 
	  Tid__no_toc               = 0
	, Tid__no_gallery           = 1
	, Tid__force_toc            = 2
	, Tid__toc                  = 3
	, Tid__no_edit_section      = 4
	, Tid__new_section_link     = 5
	, Tid__hidden_cat           = 6
	, Tid__index                = 7
	, Tid__no_index             = 8
	, Tid__static_redirect      = 9
	, Tid__no_title_convert     = 10
	, Tid__no_content_convert   = 11
	;
}
