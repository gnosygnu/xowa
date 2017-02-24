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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.core.btries.*;
public class Xomw_prepro_wkr {	// THREAD.UNSAFE: caching for repeated calls
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    List_adp comments_list = List_adp_.New();
	private final    Btrie_slim_mgr elements_trie__y = Btrie_slim_mgr.ci_a7(), elements_trie__n = Btrie_slim_mgr.ci_a7();
	private final    Hash_adp_bry xmlish_allow_missing_end_tag = Hash_adp_bry.cs().Add_many_str("includeonly", "noinclude", "onlyinclude");
	private final    Hash_adp_bry no_more_closing_tag = Hash_adp_bry.cs();
	private final    Xomw_prepro_stack stack = new Xomw_prepro_stack();
	private final    Btrie_rv trv = new Btrie_rv();
	private Bry_bfr accum = Bry_bfr_.New();

	public void Init_by_wiki(String... xmlish_elems_ary) {
		Elements_trie__init_by_wiki(elements_trie__y, ignored_tags_y, xmlish_elems_ary, "noinclude");
		Elements_trie__init_by_wiki(elements_trie__n, ignored_tags_n, xmlish_elems_ary, "includeonly");
	}
	private void Elements_trie__init_by_wiki(Btrie_slim_mgr trie, Ordered_hash ignored_tags, String[] strip_list_ary, String xmlish_elem) {
		trie.Clear();
		Elements_trie__add(trie, Bool_.Y, "!--", "comment");
		// PORTED: $xmlishElements = parser->getStripList();
		for (String itm : strip_list_ary) {
			Elements_trie__add(trie, Bool_.N, itm, itm);
		}
		// PORTED: "$xmlishElements[] = 'noinclude';" or "$xmlishElements[] = 'includeonly';"
		Elements_trie__add(trie, Bool_.N, xmlish_elem, xmlish_elem);

		// PORTED: $xmlishRegex = implode( '|', array_merge( $xmlishElements, $ignoredTags ) );
		int ignored_tags_len = ignored_tags.Count();
		for (int j = 0; j < ignored_tags_len; j++) {
			byte[] bry = (byte[])ignored_tags.Get_at(j);
			String str = String_.new_u8(bry);
			Elements_trie__add(trie, Bool_.N, str, str);
		}
	}
	private static void Elements_trie__add(Btrie_slim_mgr trie, boolean type_is_comment, String hook, String name) {
		trie.Add_obj(hook, new Xomw_prepro_elem(type_is_comment ? Xomw_prepro_elem.Type__comment : Xomw_prepro_elem.Type__other, Bry_.new_a7(name)));
	}
	public byte[] Preprocess_to_xml(byte[] src, boolean for_inclusion) {
		// RELIC.PROC_VAR:     forInclusion = $flags & Parser::PTD_FOR_INCLUSION;
		// RELIC.INIT_BY_WIKI: $xmlishElements = parser->getStripList();
		// RELIC.CLASS_VAR:    $xmlishAllowMissingEndTag = [ 'includeonly', 'noinclude', 'onlyinclude' ];
		boolean enable_only_include = false;

		// PORTED: rewritten so that all add / del is done in INIT_BY_WIKI
		Ordered_hash ignored_tags;
		Hash_adp ignored_elements;
		Btrie_slim_mgr elements_trie;
		if (for_inclusion) {
			ignored_tags = ignored_tags_y;              // RELIC: $ignoredTags = [ 'includeonly', '/includeonly' ];
			ignored_elements = ignored_elements__y;     // RELIC: $ignoredElements = [ 'noinclude' ];
			// RELIC.INIT_BY_WIKI: $xmlishElements[] = 'noinclude';
			if (	Bry_.Has(src, Bry__only_include_bgn)
				&&	Bry_.Has(src, Bry__only_include_end)) {
				enable_only_include = true;
			}
			elements_trie = elements_trie__y;
		}
		else {
			ignored_tags = ignored_tags_n;              // $ignoredTags = [ 'noinclude', '/noinclude', 'onlyinclude', '/onlyinclude' ];
			ignored_elements = ignored_elements__n;     // $ignoredElements = [ 'includeonly' ];
			// RELIC.INIT_BY_WIKI: $xmlishElements[] = 'includeonly';
			elements_trie = elements_trie__n;
		}

		// RELIC.INIT_BY_WIKI: $xmlishRegex = implode( '|', array_merge( $xmlishElements, $ignoredTags ) );

		// RELIC.REGEX
		// Use "A" modifier (anchored) instead of "^", because ^ doesn't work with an offset
		// $elementsRegex = "~($xmlishRegex)(?:\s|\/>|>)|(!--)~iA";

		stack.Clear();

		// RELIC.REGEX:
		// $searchBase = "[{<\n"; # }

		// RELIC.BRY_FIND
		// For fast reverse searches
		// $revText = strrev( $text );
		// $lengthText = strlen( $text );

		// Input pointer, starts out pointing to a pseudo-newline before the start
		int i = 0;

		// Current accumulator
		accum = stack.Get_accum();
		accum.Add_str_a7("<root>");

		// True to find equals signs in arguments
		boolean find_equals = false;

		// True to take notice of pipe characters
		boolean find_pipe = false;
		int heading_index = 1;

		// True if $i is inside a possible heading
		boolean in_heading = false;

		// True if there are no more greater-than (>) signs right of $i
		boolean no_more_gt = false;

		// Map of tag name => true if there are no more closing tags of given type right of $i
		no_more_closing_tag.Clear();

		// True to ignore all input up to the next <onlyinclude>
		boolean find_only_include = enable_only_include;

		// Do a line-start run without outputting an LF character
		boolean fake_line_start = true;

		// XOWA: init
		int src_len = src.length;
		int found = -1;
		byte[] cur_char = Bry_.Empty;
		byte[] cur_closing = Bry_.Empty;
		byte[] inner = null;
		Xomw_prepro_rule rule = null;

		while (true) {
			if (find_only_include) {
				// Ignore all input up to the next <onlyinclude>
				int start_pos = Bry_find_.Find_fwd(src, Bry__only_include_bgn, i, src_len);
				if (start_pos == Bry_find_.Not_found) {
					// Ignored section runs to the end
					accum.Add_str_a7("<ignore>").Add_bry_escape_html(src, i, src_len).Add_str_a7("</ignore>");
					break;
				}
				int tag_end_pos = start_pos + Bry__only_include_bgn.length; // past-the-end
				accum.Add_str_a7("<ignore>").Add_bry_escape_html(src, i, tag_end_pos).Add_str_a7("</ignore>");
				i = tag_end_pos;
				find_only_include = false;
			}

			if (fake_line_start) {
				found = Found__line_bgn;
				cur_char = Bry_.Empty;
			}
			else {
				// Find next opening brace, closing brace or pipe		
				// RELIC.REGEX: $search = $searchBase;
				if (stack.top == null) {
					cur_closing = Bry_.Empty;
				}
				else {
					cur_closing = stack.top.close;
					// RELIC.REGEX: $search .= $currentClosing;
				}
				if (find_pipe) {
					// RELIC.REGEX: $search .= '|';
				}
				if (find_equals) {
					// First equals will be for the template
					// RELIC.REGEX: $search .= '=';
				}

				// Output literal section, advance input counter
				// PORTED: "$literalLength = strcspn(src, $search, i)"; NOTE: no trie b/c of frequent changes to $search
				int literal_len = 0; 
				boolean loop_stop = false;
				// loop chars until search_char is found
				for (int j = i; j < src_len; j++) {
					byte b = src[j];
					switch (b) {                // handle '$searchBase = "[{<\n";'
						case Byte_ascii.Brack_bgn:
						case Byte_ascii.Curly_bgn:
						case Byte_ascii.Angle_bgn:
						case Byte_ascii.Nl:
							loop_stop = true;
							break;
						case Byte_ascii.Pipe:   // handle "find_pipe"
							if (find_pipe)   loop_stop = true;
							break;
						case Byte_ascii.Eq:     // handle "find_equals"
							if (find_equals) loop_stop = true;
							break;
						default:                // handle "cur_closing"; specified by piece.close and rule.close, so "\n", "}", "]" and "}-"
							if (cur_closing != Bry_.Empty) {
								byte cur_closing_0 = cur_closing[0];
								if (b == cur_closing_0) {
									if (cur_closing.length == 1) {	// handle "\n", "}", "]"
										loop_stop = true;
									}
									else {// handle "}-"
										int nxt_idx = j + 1;
										if (nxt_idx < src_len && src[nxt_idx] == Byte_ascii.Dash)
											loop_stop = true;
									}
								}
							}
							break;
					}
					if (loop_stop)
						break;
					else
						literal_len++;
				}
				if (literal_len > 0) {
					accum.Add_bry_escape_html(src, i, i + literal_len);
					i += literal_len;
				}
				if (i >= src_len) {
					if (Bry_.Eq(cur_closing, Byte_ascii.Nl_bry)) {
						// Do a past-the-end run to finish off the heading
						cur_char = Bry_.Empty;
						found = Found__line_end;
					}
					else {
						// All done
						break;
					}
				}
				else {
					// PORTED: "if ( $curChar == '|' ) {", etc..
					Xomw_prepro_curchar_itm cur_char_itm = (Xomw_prepro_curchar_itm)cur_char_trie.Match_at(trv, src, i, src_len);
					if (cur_char_itm != null) {
						cur_char = cur_char_itm.bry;
						switch (cur_char_itm.type) {
							case Byte_ascii.Pipe:         found = Found__pipe; break;
							case Byte_ascii.Eq:           found = Found__equals; break;
							case Byte_ascii.Angle_bgn:    found = Found__angle; break;
							case Byte_ascii.Nl:           found = in_heading ? Found__line_end : Found__line_bgn; break;

							// PORTED: "elseif ( $curChar == $currentClosing )"
							case Byte_ascii.Curly_end:    found = Found__close; break;
							case Byte_ascii.Brack_end:    found = Found__close; break;
							case Byte_ascii.At:           found = Found__close; break;	// NOTE: At is type for "}-"

							// PORTED: "elseif ( isset( $this->rules[$curChar] ) )"
							case Byte_ascii.Curly_bgn:   {found = Found__open; rule = rule_curly; break;}
							case Byte_ascii.Brack_bgn:   {found = Found__open; rule = rule_brack; break;}
							case Byte_ascii.Dash:        {found = Found__open; rule = rule_langv; break;}
						}
					}
					else {
						i++;
						continue;
					}
				}
			}

			if (found == Found__angle) {
				// Handle </onlyinclude>
				if (	enable_only_include
					&&	Bry_.Eq(src, i, i + Len__only_include_end, Bry__only_include_end)) {
					find_only_include = true;
					continue;
				}

				// Determine element name
				// PORTED: $elementsRegex = "~($xmlishRegex)(?:\s|\/>|>)|(!--)~iA"; EX: "(pre|ref)(?:\s|\/>|>)|(!--)
				Xomw_prepro_elem element = (Xomw_prepro_elem)elements_trie.Match_at(trv, src, i + 1, src_len);
				if (element == null) {
					// Element name missing or not listed
					accum.Add(Bry__escaped_lt);
					i++;
					continue;
				}

				// Handle comments
				if (element.type == Xomw_prepro_elem.Type__comment) {
					// To avoid leaving blank lines, when a sequence of
					// space-separated comments is both preceded and followed by
					// a newline (ignoring spaces), then
					// trim leading and trailing spaces and the trailing newline.

					// Find the end
					int end_pos = Bry_find_.Find_fwd(src, Bry__comment_end, i + 4, src_len);
					if (end_pos == Bry_find_.Not_found) {
						// Unclosed comment in input, runs to end
						accum.Add_str_a7("<comment>").Add_bry_escape_html(src, i, src_len).Add_str_a7("</comment>");
						i = src_len;
					}
					else {
						// Search backwards for leading whitespace
						int ws_bgn = i > 0 ? i - XophpString.strspn_bwd__space_or_tab(src, i, -1) : 0;

						// Search forwards for trailing whitespace
						// $wsEnd will be the position of the last space (or the '>' if there's none)
						int ws_end = end_pos + 2 + XophpString.strspn_fwd__space_or_tab(src, end_pos + 3, -1, src_len);

						// Keep looking forward as long as we're finding more
						// comments.
						comments_list.Clear();
						comments_list.Add(new int[] {ws_bgn, ws_end});
						while (ws_end + 5 < src_len && Bry_.Eq(src, ws_end + 1, ws_end + 5, Bry__comment_bgn)) {
							int cur_char_pos = Bry_find_.Find_fwd(src, Bry__comment_end, ws_end + 4);
							if (cur_char_pos == Bry_find_.Not_found) {
								break;
							}
							cur_char_pos = cur_char_pos + 2 + XophpString.strspn_fwd__space_or_tab(src, cur_char_pos + 3, -1, src_len);
							comments_list.Add(new int[] {ws_end + 1, cur_char_pos});
							ws_end = cur_char_pos;
						}

						// Eat the line if possible
						// TODO: This could theoretically be done if $wsStart == 0, i.e. for comments at
						// the overall start. That's not how Sanitizer::removeHTMLcomments() did it, but
						// it's a possible beneficial b/c break.
						int bgn_pos = -1;
						if (	ws_bgn > 0 
							&&	Bry_.Eq(src, ws_bgn - 1, ws_bgn    , Byte_ascii.Nl_bry)
							&&	Bry_.Eq(src, ws_end + 1, ws_end + 2, Byte_ascii.Nl_bry)
						) {
							// Remove leading whitespace from the end of the accumulator
							// Sanity check first though
							int ws_len = i - ws_bgn;
							int accum_len = accum.Len();
							if (	ws_len > 0
								&&	XophpString.strspn_fwd__space_or_tab(accum.Bfr(), accum_len - ws_len, -1, accum_len) == ws_len) {
								accum.Del_by(ws_len);
							}

							// Dump all but the last comment to the accumulator
							int comments_list_len = comments_list.Len();
							for (int j = 0; j < comments_list_len; j++) {
								int[] com = (int[])comments_list.Get_at(j);
								bgn_pos = com[0];
								end_pos = com[1] + 1;
								if (j == comments_list_len - 1) {
									break;
								}
								inner = Bry_.Mid(src, bgn_pos, end_pos);
								accum.Add_str_a7("<comment>").Add_bry_escape_html(inner).Add_str_a7("</comment>");
							}

							// Do a line-start run next time to look for headings after the comment
							fake_line_start = true;
						}
						else {
							// No line to eat, just take the comment itself
							bgn_pos = i;
							end_pos += 2;
						}

						if (stack.top != null) {
							Xomw_prepro_part part = stack.top.Get_current_part();
							if (!(part.comment_end != -1 && part.comment_end == ws_bgn - 1)) {
								part.visual_end = ws_bgn;
							}
							// Else comments abutting, no change in visual end
							part.comment_end = end_pos;
						}
						i = end_pos + 1;
						inner = Bry_.Mid(src, bgn_pos, end_pos + 1);
						accum.Add_str_a7("<comment>").Add_bry_escape_html(inner).Add_str_a7("</comment>");
					}
					continue;
				}

				byte[] name = element.name;
				// RELIC.BTRIE_CI: $lowerName = strtolower( $name );
				int atr_bgn = i + name.length + 1;

				// Find end of tag
				int tag_end_pos = no_more_gt ? Bry_find_.Not_found : Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, atr_bgn);
				if (tag_end_pos == Bry_find_.Not_found) {
					// Infinite backtrack
					// Disable tag search to prevent worst-case O(N^2) performance
					no_more_gt = true;
					accum.Add(Bry__escaped_lt);
					i++;
					continue;
				}

				// Handle ignored tags
				if (ignored_tags.Has(name)) {
					accum.Add_str_a7("<ignore>").Add_bry_escape_html(src, i, tag_end_pos + 1).Add_str_a7("</ignore>");
					i = tag_end_pos + 1;
					continue;
				}

				int tag_bgn_pos = i;
				int atr_end = -1;
				byte[] close = null;
				if (src[tag_end_pos - 1] == Byte_ascii.Slash) {
					atr_end = tag_end_pos - 1;
					inner = null;
					i = tag_end_pos + 1;
					close = Bry_.Empty;
				}
				else {
					atr_end = tag_end_pos;
					// Find closing tag
					// PORTED: `preg_match( "/<\/" . preg_quote( $name, '/' ) . "\s*>/i",`
					boolean elem_end_found = false;
					int elem_end_lhs = -1, elem_end_rhs = -1;
					int elem_end_cur = tag_end_pos + 1;
					while (true) {
						// search for "</"
						elem_end_lhs = Bry_find_.Find_fwd(src, Bry__end_lhs, elem_end_cur, src_len);
						if (elem_end_lhs == Bry_find_.Not_found) {
							break;
						}

						// verify $name
						elem_end_cur = elem_end_lhs + 2;	// 2="</"
						int elem_end_tmp = elem_end_cur + name.length;
						if (!Bry_.Eq_ci_a7(name, src, elem_end_cur, elem_end_tmp)) {
							continue;
						}

						// verify "\s*>"
						elem_end_cur = elem_end_tmp;
						elem_end_cur = Bry_find_.Find_fwd_while(src, elem_end_cur, src_len, Byte_ascii.Space);
						if (elem_end_cur == src_len) {	// just "\s", but no ">"
							break;
						}
						if (src[elem_end_cur] == Byte_ascii.Gt) {
							elem_end_rhs = elem_end_cur + 1;
							elem_end_found = true;
							break;
						}
					}
					if (	!no_more_closing_tag.Has(name)
						&&	elem_end_found) {
						inner = Bry_.Mid(src, tag_end_pos + 1, elem_end_lhs);
						i = elem_end_rhs;
						tmp_bfr.Add_str_a7("<close>").Add_bry_escape_html(src, elem_end_lhs, elem_end_rhs).Add_str_a7("</close>");
						close = tmp_bfr.To_bry_and_clear();
					} 
					else {
						// No end tag
						if (xmlish_allow_missing_end_tag.Has(name)) {
							// Let it run out to the end of the src.
							inner = Bry_.Mid(src, tag_end_pos + 1);
							i = src_len;
							close = Bry_.Empty;
						}
						else {
							// Don't match the tag, treat opening tag as literal and resume parsing.
							i = tag_end_pos + 1;
							accum.Add_bry_escape_html(src, tag_bgn_pos, tag_end_pos + 1);
							// Cache results, otherwise we have O(N^2) performance for input like <foo><foo><foo>...
							no_more_closing_tag.Add_if_dupe_use_nth(name, name);
							continue;
						}
					}
				}

				// <includeonly> and <noinclude> just become <ignore> tags
				if (ignored_elements.Has(name)) {
					accum.Add_str_a7("<ignore>").Add_bry_escape_html(src, tag_bgn_pos, i).Add_str_a7("</ignore>");
					continue;
				}

				accum.Add_str_a7("<ext>");
				// PORTED:
				// if ( $attrEnd <= $attrStart ) {
				//	 $attr = '';
				// } else {
				//   $attr = substr( $text, $attrStart, $attrEnd - $attrStart );
				// }
				accum.Add_str_a7("<name>").Add(name).Add_str_a7("</name>");
				// Note that the attr element contains the whitespace between name and attribute,
				// this is necessary for precise reconstruction during pre-save transform.
				accum.Add_str_a7("<attr>");
				if (atr_end > atr_bgn)
					accum.Add_bry_escape_html(src, atr_bgn, atr_end);
				accum.Add_str_a7("</attr>");
				if (inner != null) {
					accum.Add_str_a7("<inner>").Add_bry_escape_html(inner).Add_str_a7("</inner>");
				}
				accum.Add(close).Add_str_a7("</ext>");
			}
			else if (found == Found__line_bgn) {
				// Is this the start of a heading?
				// Line break belongs before the heading element in any case
				if (fake_line_start) {
					fake_line_start = false;
				} else {
					accum.Add(cur_char);
					i++;
				}

				int count = XophpString.strspn_fwd__byte(src, Byte_ascii.Eq, i, 6, src_len);
				if (count == 1 && find_equals) {	// EX: "{{a|\n=b=\n"
					// DWIM: This looks kind of like a name/value separator.
					// Let's let the equals handler have it and break the
					// potential heading. This is heuristic, but AFAICT the
					// methods for completely correct disambiguation are very
					// complex.
				}
				else if (count > 0) {
					Xomw_prepro_piece piece = new Xomw_prepro_piece(Byte_ascii.Nl_bry, Byte_ascii.Nl_bry, count, i, false);
					piece.Add_part(Bry_.Repeat(Byte_ascii.Eq, count));
					stack.Push(piece);
					accum = stack.Get_accum();
					Xomw_prepro_flags flags = stack.Get_flags();
					find_pipe = flags.Find_pipe;
					find_equals = flags.Find_eq;
					in_heading = flags.In_heading;
					i += count;
				}
			}
			else if (found == Found__line_end) {
				Xomw_prepro_piece piece = stack.top;
				// A heading must be open, otherwise \n wouldn't have been in the search list
				if (!Bry_.Eq(piece.open, Byte_ascii.Nl_bry)) throw Err_.new_wo_type("assertion:piece must start with \\n");
				Xomw_prepro_part part = piece.Get_current_part();

				// Search back through the input to see if it has a proper close.
				// Do this using the reversed String since the other solutions
				// (end anchor, etc.) are inefficient.
				int ws_len = XophpString.strspn_bwd__space_or_tab(src, src_len - i, -1);
				int search_bgn = i - ws_len;

				if (part.comment_end != -1 && search_bgn -1 == part.comment_end) {
					// Comment found at line end
					// Search for equals signs before the comment
					search_bgn = part.visual_end;
					search_bgn = Bry_find_.Find_bwd__while_space_or_tab(src, search_bgn, 0);
					search_bgn -= XophpString.strspn_bwd__space_or_tab(src, search_bgn, -1);
				}
				int count = piece.count;
				int eq_len = XophpString.strspn_bwd__byte(src, Byte_ascii.Eq, search_bgn, -1);

				byte[] element = Bry_.Empty;
				if (eq_len > 0) {
					if (search_bgn - eq_len == piece.start_pos) {
						// This is just a single String of equals signs on its own line
						// Replicate the doHeadings behavior /={count}(.+)={count}/
						// First find out how many equals signs there really are (don't stop at 6)
						count = eq_len;
						if (count < 3) {
							count = 0;
						}
						else {
							count = (count - 1) / 2;
							if (count > 6) count = 6;
						}
					} 
					else {
						if (eq_len < count)	count = eq_len;	// PORTED: $count = min( $equalsLength, $count );
					}
					if (count > 0) {
						// Normal match, output <h>
						element = tmp_bfr.Add_str_a7("<h level=\"").Add_int_variable(count).Add_str_a7("\" i=\"").Add_int_variable(heading_index).Add_str_a7("\">").Add_bfr_and_preserve(accum).Add_str_a7("</h>").To_bry_and_clear();
						heading_index++;
					} else {
						// Single equals sign on its own line, count=0
						element = accum.To_bry();
					}
				}
				else {
					// No match, no <h>, just pass down the inner src
					element = accum.To_bry();
				}

				// Unwind the stack
				stack.Pop();
				accum = stack.Get_accum();
				
				Xomw_prepro_flags flags = stack.Get_flags();
				find_pipe = flags.Find_pipe;
				find_equals = flags.Find_eq;
				in_heading = flags.In_heading;

				// Append the result to the enclosing accumulator
				accum.Add(element);
				// Note that we do NOT increment the input pointer.
				// This is because the closing linebreak could be the opening linebreak of
				// another heading. Infinite loops are avoided because the next iteration MUST
				// hit the heading open case above, which unconditionally increments the
				// input pointer.
			}
			else if (found == Found__open) {
				// count opening brace characters
				int count = XophpString.strspn_fwd__byte(src, cur_char[0], i, -1, src_len);	// NOTE: don't know how MediaWiki will handle "-{"

				// we need to add to stack only if opening brace count is enough for one of the rules
				if (count >= rule.min) {
					// Add it to the stack
					Xomw_prepro_piece piece = new Xomw_prepro_piece(cur_char, rule.end, count, -1, i > 0 && src[i - 1] == Byte_ascii.Nl);

					stack.Push(piece);
					accum = stack.Get_accum();
					Xomw_prepro_flags flags = stack.Get_flags();
					find_pipe = flags.Find_pipe;
					find_equals = flags.Find_eq;
					in_heading = flags.In_heading;
				}
				else {
					// Add literal brace(s)
					for (int j = 0; j < count; j++)
						accum.Add_bry_escape_html(cur_char);
				}
				i += count;
			}
			else if (found == Found__close) {
				Xomw_prepro_piece piece = stack.top;
				// lets check if there are enough characters for closing brace
				int max_count = piece.count;
				int count = XophpString.strspn_fwd__byte(src, cur_char[0], i, max_count, src_len);

				// check for maximum matching characters (if there are 5 closing characters, we will probably need only 3 - depending on the rules)
				rule = Get_rule(piece.open);
				int matching_count = -1;
				if (count > rule.max) {
					// The specified maximum exists in the callback array, unless the caller
					// has made an error
					matching_count = rule.max;
				}
				else {
					// Count is less than the maximum
					// Skip any gaps in the callback array to find the true largest match
					// Need to use array_key_exists not isset because the callback can be null
					matching_count = count;
					while (matching_count > 0 && !rule.Names_exist(matching_count)) {
						matching_count--;
					}
				}

				if (matching_count <= 0) {
					// No matching element found in callback array
					// Output a literal closing brace and continue
					for (int j = 0; j < count; j++)
						accum.Add_bry_escape_html(cur_char);
					i += count;
					continue;
				}
				int name_type = rule.names[matching_count];
				byte[] element = null;
				if (name_type == Xomw_prepro_rule.Name__null) {
					// No element, just literal text
					tmp_bfr.Add(piece.Break_syntax(tmp_bfr, matching_count));
					element = tmp_bfr.Add(Bry_.Repeat_bry(rule.end, matching_count)).To_bry_and_clear();
				}
				else {
					// Create XML element
					// Note: $parts is already XML, does not need to be encoded further
					List_adp parts = piece.parts;
					byte[] title = ((Xomw_prepro_part)parts.Get_at(0)).bfr.To_bry_and_clear();
					parts.Del_at(0);

					// The invocation is at the start of the line if lineStart is set in
					// the stack, and all opening brackets are used up.
					byte[] attr = null;
					if (max_count == matching_count && piece.line_start) {	// RELIC:!empty( $piece->lineStart )
						attr = Bry_.new_a7(" lineStart=\"1\"");
					}
					else {
						attr = Bry_.Empty;
					}

					byte[] name_bry = Xomw_prepro_rule.Name(name_type);
					tmp_bfr.Add_str_a7("<").Add(name_bry).Add(attr).Add_str_a7(">");
					tmp_bfr.Add_str_a7("<title>").Add(title).Add_str_a7("</title>");

					int arg_idx = 1;
					int parts_len = parts.Len();
					for (int j = 0; j < parts_len; j++) {
						Xomw_prepro_part part = (Xomw_prepro_part)parts.Get_at(j);
						if (part.Eqpos != -1) {
							Bry_bfr part_bfr = part.bfr;
							byte[] part_bfr_bry = part_bfr.Bfr();
							tmp_bfr.Add_str_a7("<part><name>").Add_mid(part_bfr_bry, 0, part.Eqpos);
							tmp_bfr.Add_str_a7("</name>=<value>").Add_mid(part_bfr_bry, part.Eqpos + 1, part_bfr.Len());
							tmp_bfr.Add_str_a7("</value></part>");
						}
						else {
							tmp_bfr.Add_str_a7("<part><name index=\"").Add_int_variable(arg_idx).Add_str_a7("\" /><value>").Add(part.bfr.To_bry()).Add_str_a7("</value></part>");
							arg_idx++;
						}
					}
					element = tmp_bfr.Add_str_a7("</").Add(name_bry).Add_str_a7(">").To_bry_and_clear();
				}

				// Advance input pointer
				i += matching_count;

				// Unwind the stack
				stack.Pop();
				accum = stack.Get_accum();

				// Re-add the old stack element if it still has unmatched opening characters remaining
				if (matching_count < piece.count) {
					piece.Parts__renew(); // PORTED: piece.parts = [ new PPDPart ];
					piece.count -= matching_count;

					// do we still qualify for any callback with remaining count?
					int min = Get_rule(piece.open).min;
					if (piece.count >= min) {
						stack.Push(piece);
						accum = stack.Get_accum();
					}
					else {
						accum.Add(Bry_.Repeat_bry(piece.open, piece.count));
					}
				}

				Xomw_prepro_flags flags = stack.Get_flags();
				find_pipe = flags.Find_pipe;
				find_equals = flags.Find_eq;
				in_heading = flags.In_heading;

				// Add XML element to the enclosing accumulator
				accum.Add(element);
			}
			else if (found == Found__pipe) {
				find_equals = true; // shortcut for getFlags()
				stack.Add_part(Bry_.Empty);
				accum = stack.Get_accum();
				i++;
			}
			else if (found == Found__equals) {
				find_equals = false; // shortcut for getFlags()
				stack.Get_current_part().Eqpos = accum.Len();
				accum.Add_byte(Byte_ascii.Eq);
				i++;
			}
		}

		// Output any remaining unclosed brackets
		Bry_bfr root_accum = stack.Get_root_accum();
		int stack_len = stack.stack.Len();
		for (int j = 0; j < stack_len; j++) {
			Xomw_prepro_piece piece = (Xomw_prepro_piece)stack.stack.Get_at(j);
			root_accum.Add(piece.Break_syntax(tmp_bfr, -1));
		}
		root_accum.Add_str_a7("</root>");
		return root_accum.To_bry_and_clear();
	}
	private Xomw_prepro_rule Get_rule(byte[] bry) {
		if		(Bry_.Eq(bry, rule_curly.bgn))   return rule_curly;
		else if	(Bry_.Eq(bry, rule_brack.bgn))   return rule_brack;
		else if	(Bry_.Eq(bry, rule_langv.bgn))   return rule_langv;
		else                                     throw Err_.new_unhandled(bry);
	}
	private static final int 
	  Found__line_bgn = 0
	, Found__line_end = 1
	, Found__pipe = 2
	, Found__equals = 3
	, Found__angle = 4
	, Found__close = 5
	, Found__open = 6
	;
	private static final    Xomw_prepro_rule 
	  rule_curly = new Xomw_prepro_rule(Bry_.new_a7("{"), Bry_.new_a7("}")  , 2, 3, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__tmpl, Xomw_prepro_rule.Name__targ})
	, rule_brack = new Xomw_prepro_rule(Bry_.new_a7("["), Bry_.new_a7("]")  , 2, 2, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__null})
	, rule_langv = new Xomw_prepro_rule(Bry_.new_a7("-{"), Bry_.new_a7("}-"), 1, 1, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__null})
	;
	private static final    byte[] 
	  Bry__only_include_bgn = Bry_.new_a7("<onlyinclude>")
	, Bry__only_include_end = Bry_.new_a7("</onlyinclude>")
	, Bry__comment_bgn  = Bry_.new_a7("<!--")
	, Bry__comment_end  = Bry_.new_a7("-->")
	, Bry__escaped_lt   = Bry_.new_a7("&lt;")
	, Bry__end_lhs      = Bry_.new_a7("</")
	;
	private static final    int Len__only_include_end = Bry__only_include_end.length;
	private static final    Btrie_slim_mgr cur_char_trie = Cur_char_trie__new();
	private static final    Ordered_hash
	  ignored_tags_y     = Ordered_hash_.New_bry().Add_many_str("includeonly", "/includeonly")
	, ignored_tags_n     = Ordered_hash_.New_bry().Add_many_str("noinclude", "/noinclude", "onlyinclude", "/onlyinclude");
	private static final    Hash_adp_bry 
	  ignored_elements__y   = Hash_adp_bry.cs().Add_many_str("noinclude")
	, ignored_elements__n = Hash_adp_bry.cs().Add_many_str("includeonly");
	private static Btrie_slim_mgr Cur_char_trie__new() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();
		String[] ary = new String[] {"|", "=", "<", "\n", "{", "[", "-{", "}", "]"};
		for (String str : ary) {
			byte[] bry = Bry_.new_a7(str);
			rv.Add_obj(bry, new Xomw_prepro_curchar_itm(bry, bry[0]));
		}

		// handle "}-" separately
		byte[] langv_end = Bry_.new_a7("}-");
		rv.Add_obj(langv_end, new Xomw_prepro_curchar_itm(langv_end, Byte_ascii.At));
		return rv;
	}
}
