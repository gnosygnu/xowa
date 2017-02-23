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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.btries.*;
import gplx.langs.htmls.*;
import gplx.xowa.mediawiki.includes.utls.*;
public class Xomw_block_level_pass {
	private final    Bry_bfr tmp = Bry_bfr_.New();
	private final    Btrie_rv trv = new Btrie_rv();
	private boolean in_pre, dt_open;
	private int last_section;
	private byte[] find_colon_no_links__before, find_colon_no_links__after;

	public void doBlockLevels(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr, boolean line_start) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		// XO.STATIC
		if (block_chars_ary == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				block_chars_ary = Block_chars_ary__new();
				open_match_trie = Btrie_slim_mgr.ci_a7().Add_many_str
				("<table", "<h1", "<h2", "<h3", "<h4", "<h5", "<h6", "<pre", "<tr", "<p", "<ul", "<ol", "<dl", "<li", "</tr", "</td", "</th");
				close_match_trie = Btrie_slim_mgr.ci_a7().Add_many_str
				( "</table", "</h1", "</h2", "</h3", "</h4", "</h5", "</h6", "<td", "<th", "<blockquote", "</blockquote", "<div", "</div", "<hr"
				, "</pre", "</p", "</mw:", XomwStripState.Str__marker_bgn + "-pre", "</li", "</ul", "</ol", "</dl", "<center", "</center");
				blockquote_trie = Btrie_slim_mgr.ci_a7().Add_many_str("<blockquote", "</blockquote");
				pre_trie = Btrie_slim_mgr.ci_a7().Add_str_int("<pre", Pre__bgn).Add_str_int("</pre", Pre__end);
			}
		}

		// Parsing through the text line by line.  The main thing
		// happening here is handling of block-level elements p, pre,
		// and making lists from lines starting with * # : etc.
		byte[] last_prefix = Bry_.Empty;
		bfr.Clear();
		this.dt_open = false;
		boolean in_block_elem = false;
		int prefix_len = 0;
		byte para_stack = Para_stack__none;
		boolean in_blockquote = false;
		this.in_pre = false;
		this.last_section = Last_section__none;
		byte[] prefix2 = null;

		// PORTED.SPLIT: $textLines = StringUtils::explode("\n", $text);
		int line_bgn = src_bgn;
		while (line_bgn < src_end) {
			int line_end = Bry_find_.Find_fwd(src, Byte_ascii.Nl, line_bgn);
			if (line_end == Bry_find_.Not_found)
				line_end = src_end;

			// Fix up line_start
			if (!line_start) {
				bfr.Add_mid(src, line_bgn, line_end);
				line_start = true;
				continue;
			}

			// * = ul
			// # = ol
			// ; = dt
			// : = dd
			int last_prefix_len = last_prefix.length;

			// PORTED: pre_close_match = preg_match('/<\\/pre/i', $oLine); pre_open_match = preg_match('/<pre/i', $oLine);
			int pre_cur = line_bgn;
			boolean pre_close_match = false;
			boolean pre_open_match = false;

			while (true) {
				if (pre_cur >= line_end)
					break;
				Object o = pre_trie.Match_at(trv, src, pre_cur, line_end);
				if (o == null)
					pre_cur++;
				else {
					int pre_tid = Int_.cast(o);
					if (pre_tid == Pre__bgn)
						pre_open_match = true;
					else if (pre_tid == Pre__end)
						pre_close_match = true;
					pre_cur = trv.Pos();
				}
			}

			byte[] prefix = null, t = null;
			// If not in a <pre> element, scan for and figure out what prefixes are there.
			if (!in_pre) {
				// Multiple prefixes may abut each other for nested lists.
				prefix_len = Php_str_.Strspn_fwd__ary(src, block_chars_ary, line_bgn, line_end, line_end); // strspn($oLine, '*#:;');
				prefix = Php_str_.Substr(src, line_bgn, prefix_len);

				// eh?
				// ; and : are both from definition-lists, so they're equivalent
				//  for the purposes of determining whether or not we need to open/close
				//  elements.
				// substr( $inputLine, $prefixLength );
				prefix2 = Bry_.Replace(prefix, Byte_ascii.Semic, Byte_ascii.Colon);
				t = Bry_.Mid(src, line_bgn + prefix_len, line_end);
				in_pre = pre_open_match;
			}
			else {
				// Don't interpret any other prefixes in preformatted text
				prefix_len = 0;
				prefix = prefix2 = Bry_.Empty;
				t = Bry_.Mid(src, line_bgn, line_end);
			}

			// List generation
			byte[] term = null, t2 = null;
			int common_prefix_len = -1;
			if (prefix_len > 0 && Bry_.Eq(last_prefix, prefix2)) {
				// Same as the last item, so no need to deal with nesting or opening stuff
				bfr.Add(Next_item(Php_str_.Substr_byte(prefix, -1)));
				para_stack = Para_stack__none;

				if (prefix_len > 0 && prefix[prefix_len - 1] == Byte_ascii.Semic) {
					// The one nasty exception: definition lists work like this:
					// ; title : definition text
					// So we check for : in the remainder text to split up the
					// title and definition, without b0rking links.
					term = t2 = Bry_.Empty;
					if (Find_colon_no_links(t, term, t2) != Bry_find_.Not_found) {
						term = find_colon_no_links__before;
						t2   = find_colon_no_links__after;
						t = t2;
						bfr.Add(term).Add(Next_item(Byte_ascii.Colon));
					}
				}
			}
			else if (prefix_len > 0 || last_prefix_len > 0) {
				// We need to open or close prefixes, or both.

				// Either open or close a level...
				common_prefix_len = Get_common(prefix, last_prefix);
				para_stack = Para_stack__none;

				// Close all the prefixes which aren't shared.
				while (common_prefix_len < last_prefix_len) {
					bfr.Add(Close_list(last_prefix[last_prefix_len - 1]));
					last_prefix_len--;
				}

				// Continue the current prefix if appropriate.
				if (prefix_len <= common_prefix_len && common_prefix_len > 0) {
					bfr.Add(Next_item(prefix[common_prefix_len - 1]));
				}

				// Open prefixes where appropriate.
				if (Bry_.Len_gt_0(last_prefix) && prefix_len > common_prefix_len) {
					bfr.Add_byte_nl();
				}
				while (prefix_len > common_prefix_len) {
					byte c = Php_str_.Substr_byte(prefix, common_prefix_len, 1);
					bfr.Add(Open_list(c));

					if (c == Byte_ascii.Semic) {
						// @todo FIXME: This is dupe of code above
						if (Find_colon_no_links(t, term, t2) != Bry_find_.Not_found) {
							term = find_colon_no_links__before;
							t2   = find_colon_no_links__after;
							t = t2;
							bfr.Add(term).Add(Next_item(Byte_ascii.Colon));
						}
					}
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
				int t_len = t.length;
				boolean open_match = Php_preg_.Match(open_match_trie, trv, t, 0, t_len) != null;
				boolean close_match = Php_preg_.Match(close_match_trie, trv, t, 0, t_len) != null;

				if (open_match || close_match) {
					para_stack = Para_stack__none;
					// @todo bug 5718: paragraph closed
					bfr.Add(Close_paragraph());
					if (pre_open_match && !pre_close_match) {
						in_pre = true;
					}
					int bq_offset = 0;
					// PORTED:preg_match('/<(\\/?)blockquote[\s>]/i', t, $bqMatch, PREG_OFFSET_CAPTURE, $bq_offset)
					while (true) {
						Object o = Php_preg_.Match(blockquote_trie, trv, t, bq_offset, t_len);
						if (o == null) { // no more blockquotes found; exit
							break;
						}
						else {
							byte[] bq_bry = (byte[])o;
							in_blockquote = bq_bry[1] != Byte_ascii.Slash; // is this a close tag?
							bq_offset = trv.Pos();
						}
					}
					in_block_elem = !close_match;
				}
				else if (!in_block_elem && !in_pre) {
					if (   Php_str_.Substr_byte(t, 0) == Byte_ascii.Space
						&& (last_section == Last_section__pre || Bry_.Trim(t) != Bry_.Empty)
						&& !in_blockquote
					) {
						// pre
						if (last_section != Last_section__pre) {
							para_stack = Para_stack__none;
							bfr.Add(Close_paragraph()).Add(Gfh_tag_.Pre_lhs);
							last_section = Last_section__pre;
						}
						t = Bry_.Mid(t, 1);
					}
					else {
						// paragraph
						if (Bry_.Trim(t) == Bry_.Empty) {
							if (para_stack != Para_stack__none) {
								Para_stack_bfr(bfr, para_stack);
								bfr.Add_str_a7("<br />");
								para_stack = Para_stack__none;
								last_section = Last_section__para;
							}
							else {
								if (last_section != Last_section__para) {
									bfr.Add(Close_paragraph());
									last_section = Last_section__none;
									para_stack = Para_stack__bgn;
								}
								else {
									para_stack = Para_stack__mid;
								}
							}
						}
						else {
							if (para_stack != Para_stack__none) {
								Para_stack_bfr(bfr, para_stack);
								para_stack = Para_stack__none;
								last_section = Last_section__para;
							}
							else if (last_section != Last_section__para) {
								bfr.Add(Close_paragraph()).Add(Gfh_tag_.P_lhs);
								this.last_section = Last_section__para;
							}
						}
					}
				}
			}
			// somewhere above we forget to get out of pre block (bug 785)
			if (pre_close_match && in_pre) {
				in_pre = false;
			}
			if (para_stack == Para_stack__none) {
				bfr.Add(t);
				if (prefix_len == 0) {
					bfr.Add_byte_nl();
				}
			}

			line_bgn = line_end + 1;
		}

		while (prefix_len > 0) {
			bfr.Add(Close_list(prefix2[prefix_len - 1]));
			prefix_len--;
			if (prefix_len > 0) {
				bfr.Add_byte_nl();
			}
		}
		if (last_section != Last_section__none) {
			bfr.Add(last_section == Last_section__para ? Gfh_tag_.P_rhs : Gfh_tag_.Pre_rhs);
			last_section = Last_section__none;
		}
	}
	// If a pre or p is open, return the corresponding close tag and update
	// the state. If no tag is open, return an empty String.
	public byte[] Close_paragraph() {
		byte[] result = Bry_.Empty;
		if (last_section != Last_section__none) {
			tmp.Add(last_section == Last_section__para ? Gfh_tag_.P_rhs : Gfh_tag_.Pre_rhs);
			result = tmp.Add_byte_nl().To_bry_and_clear();
		}
		in_pre = false;
		last_section = Last_section__none;
		return result;
	}

	// getCommon() returns the length of the longest common substring
	// of both arguments, starting at the beginning of both.
	private int Get_common(byte[] st1, byte[] st2) {
		int st1_len = st1.length, st2_len = st2.length;
		int shorter = st1_len < st2_len ? st1_len : st2_len;

		int i;
		for (i = 0; i < shorter; i++) {
			if (st1[i] != st2[i]) {
				break;
			}
		}
		return i;
	}

	// Open the list item element identified by the prefix character.
	private byte[] Open_list(byte c) {
		byte[] result = Close_paragraph();

		if      (c == Byte_ascii.Star)
			result = tmp.Add(result).Add_str_a7("<ul><li>").To_bry_and_clear();
		else if (c == Byte_ascii.Hash)
			result = tmp.Add(result).Add_str_a7("<ol><li>").To_bry_and_clear();
		else if (c == Byte_ascii.Hash)
			result = tmp.Add(result).Add_str_a7("<dl><dd>").To_bry_and_clear();
		else if (c == Byte_ascii.Semic) {
			result = tmp.Add(result).Add_str_a7("<dl><dt>").To_bry_and_clear();
			dt_open = true;
		}
		else
			result = tmp.Add_str_a7("<!-- ERR 1 -->").To_bry_and_clear();

		return result;
	}

	// Close the current list item and open the next one.
	private byte[] Next_item(byte c) {
		if (c == Byte_ascii.Star || c == Byte_ascii.Hash) {
			return tmp.Add_str_a7("</li>\n<li>").To_bry_and_clear();
		}
		else if (c == Byte_ascii.Colon || c == Byte_ascii.Semic) {
			byte[] close = tmp.Add_str_a7("</dd>\n").To_bry_and_clear();
			if (dt_open) {
				close = tmp.Add_str_a7("</dt>\n").To_bry_and_clear();
			}
			if (c == Byte_ascii.Semic) {
				dt_open = true;
				return tmp.Add(close).Add_str_a7("<dt>").To_bry_and_clear();
			}
			else {
				dt_open = false;
				return tmp.Add(close).Add_str_a7("<dd>").To_bry_and_clear();
			}
		}
		return tmp.Add_str_a7("<!-- ERR 2 -->").To_bry_and_clear();
	}

	// Close the current list item identified by the prefix character.
	private byte[] Close_list(byte c) {
		byte[] text = null;
		if (c == Byte_ascii.Star) {
			text = Bry_.new_a7("</li></ul>");
		}
		else if (c == Byte_ascii.Hash) {
			text = Bry_.new_a7("</li></ol>");
		}
		else if (c == Byte_ascii.Colon) {
			if (dt_open) {
				dt_open = false;
				text = Bry_.new_a7("</dt></dl>");
			}
			else {
				text = Bry_.new_a7("</dd></dl>");
			}
		}
		else {
			return Bry_.new_a7("<!-- ERR 3 -->");
		}
		return text;
	}

	// Split up a String on ':', ignoring any occurrences inside tags
	// to prevent illegal overlapping.
	private int Find_colon_no_links(byte[] str, byte[] before, byte[] after) {
		int len = str.length;
		int colon_pos = Php_str_.Strpos(str, Byte_ascii.Colon, 0, len);
		if (colon_pos == Bry_find_.Not_found) {
			// Nothing to find!
			return Bry_find_.Not_found;
		}

		int lt_pos = Php_str_.Strpos(str, Byte_ascii.Angle_bgn, 0, len);
		if (lt_pos == Bry_find_.Not_found || lt_pos > colon_pos) {
			// Easy; no tag nesting to worry about
			find_colon_no_links__before = Php_str_.Substr(str, 0, colon_pos);
			find_colon_no_links__after = Php_str_.Substr(str, colon_pos + 1);
			return colon_pos;
		}

		// Ugly state machine to walk through avoiding tags.
		int state = COLON_STATE_TEXT;
		int level = 0;
		for (int i = 0; i < len; i++) {
			byte c = str[i];

			switch (state) {
				case COLON_STATE_TEXT:
					switch (c) {
						case Byte_ascii.Angle_bgn:
							// Could be either a <start> tag or an </end> tag
							state = COLON_STATE_TAGSTART;
							break;
						case Byte_ascii.Colon:
							if (level == 0) {
								// We found it!
								find_colon_no_links__before = Php_str_.Substr(str, 0, i);
								find_colon_no_links__after = Php_str_.Substr(str, i + 1);
								return i;
							}
							// Embedded in a tag; don't break it.
							break;
						default:
							// Skip ahead looking for something interesting
							colon_pos = Php_str_.Strpos(str, Byte_ascii.Colon, i, len);
							if (colon_pos == Bry_find_.Not_found) {
								// Nothing else interesting
								return Bry_find_.Not_found;
							}
							lt_pos = Php_str_.Strpos(str, Byte_ascii.Angle_bgn, i, len);
							if (level == 0) {
								if (lt_pos == Bry_find_.Not_found || colon_pos < lt_pos) {
									// We found it!
									find_colon_no_links__before = Php_str_.Substr(str, 0, colon_pos);
									find_colon_no_links__after = Php_str_.Substr(str, colon_pos + 1);
									return i;
								}
							}
							if (lt_pos == Bry_find_.Not_found) {
								// Nothing else interesting to find; abort!
								// We're nested, but there's no close tags left. Abort!
								i = len;	// break 2
								break;
							}
							// Skip ahead to next tag start
							i = lt_pos;
							state = COLON_STATE_TAGSTART;
							break;
						}
					break;
				case COLON_STATE_TAG:
					// In a <tag>
					switch (c) {
						case Byte_ascii.Angle_bgn:
							level++;
							state = COLON_STATE_TEXT;
							break;
						case Byte_ascii.Slash:
							// Slash may be followed by >?
							state = COLON_STATE_TAGSLASH;
							break;
						default:
							// ignore
							break;
					}
					break;
				case COLON_STATE_TAGSTART:
					switch (c) {
						case Byte_ascii.Slash:
							state = COLON_STATE_CLOSETAG;
							break;
						case Byte_ascii.Bang:
							state = COLON_STATE_COMMENT;
							break;
						case Byte_ascii.Angle_bgn:
							// Illegal early close? This shouldn't happen D:
							state = COLON_STATE_TEXT;
							break;
						default:
							state = COLON_STATE_TAG;
							break;
					}
					break;
				case COLON_STATE_CLOSETAG:
					// In a </tag>
					if (c == Byte_ascii.Angle_bgn) {
						level--;
						if (level < 0) {
							Gfo_usr_dlg_.Instance.Warn_many("", "", "Invalid input; too many close tags");
							return Bry_find_.Not_found;
						}
						state = COLON_STATE_TEXT;
					}
					break;
				case COLON_STATE_TAGSLASH:
					if (c == Byte_ascii.Angle_bgn) {
						// Yes, a self-closed tag <blah/>
						state = COLON_STATE_TEXT;
					}
					else {
						// Probably we're jumping the gun, and this is an attribute
						state = COLON_STATE_TAG;
					}
					break;
				case COLON_STATE_COMMENT:
					if (c == Byte_ascii.Dash) {
						state = COLON_STATE_COMMENTDASH;
					}
					break;
				case COLON_STATE_COMMENTDASH:
					if (c == Byte_ascii.Dash) {
						state = COLON_STATE_COMMENTDASHDASH;
					}
					else {
						state = COLON_STATE_COMMENT;
					}
					break;
				case COLON_STATE_COMMENTDASHDASH:
					if (c == Byte_ascii.Angle_bgn) {
						state = COLON_STATE_TEXT;
					}
					else {
						state = COLON_STATE_COMMENT;
					}
					break;
			default:
				throw Err_.new_wo_type("State machine error");
			}
		}
		if (level > 0) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Invalid input; not enough close tags (level ~{0}, state ~{1})", level, state);
			return Bry_find_.Not_found;
		}
		return Bry_find_.Not_found;
	}
	private static final int
	  COLON_STATE_TEXT = 0
	, COLON_STATE_TAG = 1
	, COLON_STATE_TAGSTART = 2
	, COLON_STATE_CLOSETAG = 3
	, COLON_STATE_TAGSLASH = 4
	, COLON_STATE_COMMENT = 5
	, COLON_STATE_COMMENTDASH = 6
	, COLON_STATE_COMMENTDASHDASH = 7
	;
	private static final byte
	  Last_section__none = 0    // ''
	, Last_section__para = 1    // p
	, Last_section__pre  = 2    // pre
	;
	private static final byte
	  Para_stack__none = 0	// false
	, Para_stack__bgn  = 1	// <p>
	, Para_stack__mid  = 2	// </p><p>
	;
	private static final int Pre__bgn = 0, Pre__end = 1;
	private static Btrie_slim_mgr pre_trie;
	private static boolean[] block_chars_ary; 
	private static boolean[] Block_chars_ary__new() {
		boolean[] rv = new boolean[256];
		rv[Byte_ascii.Star] = true;
		rv[Byte_ascii.Hash] = true;
		rv[Byte_ascii.Colon] = true;
		rv[Byte_ascii.Semic] = true;
		return rv;
	}
	private static Btrie_slim_mgr open_match_trie, close_match_trie, blockquote_trie;
	private static void Para_stack_bfr(Bry_bfr bfr, int id) {
		switch (id) {
			case Para_stack__bgn: bfr.Add_str_a7("<p>"); break;
			case Para_stack__mid: bfr.Add_str_a7("</p><p>"); break;
			default:              throw Err_.new_unhandled_default(id);
		}
	}
}
