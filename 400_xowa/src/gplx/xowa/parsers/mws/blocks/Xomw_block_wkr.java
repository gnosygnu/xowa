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
package gplx.xowa.parsers.mws.blocks; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import gplx.langs.phps.*;
public class Xomw_block_wkr implements gplx.core.brys.Bry_split_wkr {// THREAD.UNSAFE: caching for repeated calls
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private byte[] last_prefix, last_section;
	private boolean line_start, dt_open, in_block_elem, para_stack, in_blockquote, in_pre = false;
	private int prefix_len;
	private int src_len;
	public byte[] Do_block_levels(byte[] src, boolean line_start) {
		this.src_len = src.length;
		this.line_start = line_start;
		// Parsing through the text line by line.  The main thing
		// happening here is handling of block-level elements p, pre,
		// and making lists from lines starting with * # : etc.
		this.last_prefix = Bry_.Empty;
		bfr.Clear();
		this.dt_open = this.in_block_elem = false;
		this.prefix_len = 0;
		this.para_stack = false;
		this.in_blockquote = false;

		// PORTED.SPLIT: $textLines = StringUtils::explode("\n", $text);
		Bry_split_.Split(src, 0, src_len, Byte_ascii.Nl, Bool_.N, this);

		while (prefix_len > 0) {
			// bfr .= this.closeList(prefix2[prefix_len - 1]);
			prefix_len--;
			if (prefix_len > 0) {
				bfr.Add_byte_nl();
			}
		}
		if (Bry_.Len_gt_0(last_section)) {
			bfr.Add_str_a7("</").Add(last_section).Add_str_a7(">");
			this.last_section = Bry_.Empty;
		}

		if (dt_open || in_block_elem || para_stack || in_blockquote || in_pre) {
		}
		return bfr.To_bry_and_clear();
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		// Fix up line_start
		if (!line_start) {
			bfr.Add_mid(src, itm_bgn, itm_end);
			line_start = true;
			return Bry_split_.Rv__ok;
		}

		// * = ul
		// # = ol
		// ; = dt
		// : = dd
		int last_prefix_len = last_prefix.length;
		boolean pre_close_match = false; //preg_match('/<\\/pre/i', $oLine);
		boolean pre_open_match = false;  //preg_match('/<pre/i', $oLine);
		byte[] prefix = null, prefix2 = null, t = null;
		// If not in a <pre> element, scan for and figure out what prefixes are there.
		if (!in_pre) {
			// Multiple prefixes may abut each other for nested lists.
			prefix_len = 0;// strspn($oLine, '*#:;');
			prefix = Php_str_.Substr(src, itm_bgn, prefix_len);

			// eh?
			// ; and : are both from definition-lists, so they're equivalent
			//  for the purposes of determining whether or not we need to open/close
			//  elements.
			prefix2 = Bry_.Replace(prefix, Byte_ascii.Semic, Byte_ascii.Colon);
			t = Bry_.Mid(src, itm_bgn + prefix_len, itm_end);
//				this.in_pre = (boolean)pre_open_match;
		}
		else {
			// Don't interpret any other prefixes in preformatted text
			prefix_len = 0;
			prefix = prefix2 = Bry_.Empty;
			t = Bry_.Mid(src, itm_bgn, itm_end);
		}

		// List generation
		byte[] term = null, t2 = null;
		int common_prefix_len = -1;
		if (prefix_len > 0 && Bry_.Eq(last_prefix, prefix2)) {
			// Same as the last item, so no need to deal with nesting or opening stuff
//				bfr .= this.nextItem(substr(prefix, -1));
			para_stack = false;

			if (prefix_len > 0 && prefix[prefix_len - 1] == Byte_ascii.Semic) {
				// The one nasty exception: definition lists work like this:
				// ; title : definition text
				// So we check for : in the remainder text to split up the
				// title and definition, without b0rking links.
				term = t2 = Bry_.Empty;
//					if (this.findColonNoLinks(t, term, t2) !== false) {
					t = t2;
					bfr.Add(term); // . this.nextItem(':');
//					}
			}
		}
		else if (prefix_len > 0 || last_prefix_len > 0) {
			// We need to open or close prefixes, or both.

			// Either open or close a level...
//				common_prefix_len = this.getCommon(prefix, last_prefix);
			para_stack = false;

			// Close all the prefixes which aren't shared.
			while (common_prefix_len < last_prefix_len) {
//					bfr .= this.closeList(last_prefix[last_prefix_len - 1]);
				last_prefix_len--;
			}
//
			// Continue the current prefix if appropriate.
			if (prefix_len <= common_prefix_len && common_prefix_len > 0) {
//					bfr .= this.nextItem(prefix[common_prefix_len - 1]);
			}

			// Open prefixes where appropriate.
			if (Bry_.Len_gt_0(last_prefix) && prefix_len > common_prefix_len) {
				bfr.Add_byte_nl();
			}
			while (prefix_len > common_prefix_len) {
//					$char = substr(prefix, common_prefix_len, 1);
//					bfr .= this.openList($char);
//
//					if (';' == $char) {
//						// @todo FIXME: This is dupe of code above
//						if (this.findColonNoLinks(t, term, t2) !== false) {
//							t = t2;
//							bfr .= term . this.nextItem(':');
//						}
//					}
				++common_prefix_len;
			}
			if (prefix_len == 0 && Bry_.Len_gt_0(last_prefix)) {
				bfr.Add_byte_nl();
			}
			last_prefix = prefix2;
		}

		// If we have no prefixes, go to paragraph mode.
		if (0 == prefix_len) {
			// No prefix (not in list)--go to paragraph mode
			// XXX: use a stack for nestable elements like span, table and div
			boolean open_match = false, close_match = false;
//				open_match = preg_match(
//					'/(?:<table|<h1|<h2|<h3|<h4|<h5|<h6|<pre|<tr|'
//						. '<p|<ul|<ol|<dl|<li|<\\/tr|<\\/td|<\\/th)/iS',
//					t
//				);
//				close_match = preg_match(
//					'/(?:<\\/table|<\\/h1|<\\/h2|<\\/h3|<\\/h4|<\\/h5|<\\/h6|'
//						. '<td|<th|<\\/?blockquote|<\\/?div|<hr|<\\/pre|<\\/p|<\\/mw:|'
//						. self::MARKER_PREFIX
//						. '-pre|<\\/li|<\\/ul|<\\/ol|<\\/dl|<\\/?center)/iS',
//					t
//				);

			if (open_match || close_match) {
				para_stack = false;
				// @todo bug 5718: paragraph closed
//					bfr .= this.closeParagraph();
				if (pre_open_match && !pre_close_match) {
					this.in_pre = true;
				}
//					$bqOffset = 0;
//					while (preg_match('/<(\\/?)blockquote[\s>]/i', t,
//						$bqMatch, PREG_OFFSET_CAPTURE, $bqOffset)
//					) {
//						in_blockquote = !$bqMatch[1][0]; // is this a close tag?
//						$bqOffset = $bqMatch[0][1] + strlen($bqMatch[0][0]);
//					}
				in_block_elem = !close_match;
			}
			else if (!in_block_elem && !this.in_pre) {
				if (    Byte_ascii.Space == t[0]
//						&& (last_section == 'pre' || trim(t) != '')
					&& !in_blockquote
				) {
					// pre
//						if (this.last_section !== 'pre') {
						para_stack = false;
//							bfr .= this.closeParagraph() . '<pre>';
//							this.last_section = 'pre';
//						}
					t = Bry_.Mid(t, 1);
				}
				else {
					// paragraph
//						if (trim(t) == '') {
						if (para_stack) {
//								bfr .= para_stack . '<br />';
							para_stack = false;
//								this.last_section = 'p';
						}
						else {
//								if (this.last_section !== 'p') {
//									bfr .= this.closeParagraph();
//									this.last_section = '';
//									para_stack = '<p>';
//								}
//								else {
//									para_stack = '</p><p>';
//								}
						}
//						}
//						else {
						if (para_stack) {
//								bfr .= para_stack;
							para_stack = false;
//								this.last_section = 'p';
						}
//							else if (this.last_section !== 'p') {
//								bfr .= this.closeParagraph() . '<p>';
//								this.last_section = 'p';
//							}
//						}
				}
			}
		}
		// somewhere above we forget to get out of pre block (bug 785)
		if (pre_close_match && this.in_pre) {
			this.in_pre = false;
		}
		if (para_stack == false) {
			bfr.Add(t);
			if (prefix_len == 0) {
				bfr.Add_byte_nl();
			}
		}

		if (last_prefix_len == -1 || common_prefix_len == -1) {
		}
		return Bry_split_.Rv__ok;
	}
}
