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
package gplx.xowa.parsers.mws.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.mws.*;
import gplx.core.btries.*;
public class Xomw_prepro_wkr {	// TS.UNSAFE:caching for repeated calls
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    List_adp comments_list = List_adp_.New();
	private final    Hash_adp_bry xmlish_elems = Hash_adp_bry.ci_a7();
	private final    Hash_adp_bry xmlish_allow_missing_end_tag = Hash_adp_bry.cs().Add_many_str("includeonly", "noinclude", "onlyinclude");
	private final    Hash_adp_bry no_more_closing_tag = Hash_adp_bry.cs();
	// private final    Btrie_slim_mgr search_dflt_trie = Btrie_slim_mgr.cs().Add_many_int(0, "[", "{", "<", "\n");	// $searchBase = "[{<\n";
	private final    Xomw_prepro_stack stack = new Xomw_prepro_stack();
	private Bry_bfr accum = Bry_bfr_.New();

	private final    Btrie_rv trv = new Btrie_rv();

	private static final    Btrie_slim_mgr cur_char_trie = Cur_char_trie__new();
	private static final    Hash_adp_bry 
	  ignored_tags__noinclude       = Hash_adp_bry.cs().Add_many_str("includeonly", "/includeonly")
	, ignored_elements__noinclude   = Hash_adp_bry.cs().Add_many_str("noinclude")
	, ignored_tags__includeonly     = Hash_adp_bry.cs().Add_many_str("noinclude", "/noinclude", "onlyinclude", "/onlyinclude")
	, ignored_elements__includeonly = Hash_adp_bry.cs().Add_many_str("includeonly");
	private static Btrie_slim_mgr Cur_char_trie__new() {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();
		String[] ary = new String[] {"|", "=", "<", "\n", "{", "[", "-{"};
		for (String str : ary) {
			byte[] bry = Bry_.new_a7(str);
			rv.Add_obj(bry, new Xomw_prepro_curchar_itm(bry));
		}
		return rv;
	}

	public byte[] Preprocess_to_xml(byte[] src, boolean for_inclusion) {
		xmlish_elems.Clear(); // TODO.XO: parser->getStripList();
		// PERF: xmlish_allow_missing_end_tag.Add_many_str("includeonly", "noinclude", "onlyinclude")
		boolean enable_only_include = false;

		Hash_adp_bry ignored_tags, ignored_elements;
		if (for_inclusion) {
			ignored_tags = ignored_tags__noinclude;
			ignored_elements = ignored_elements__noinclude;
			xmlish_elems.Add_many_str("noinclude");
			if (	Bry_.Has(src, Bry__only_include_bgn)
				&&	Bry_.Has(src, Bry__only_include_end)) {
				enable_only_include = true;
			}
		}
		else {
			ignored_tags = ignored_tags__includeonly;
			ignored_elements = ignored_elements__includeonly;
			xmlish_elems.Add_many_str("includeonly");
		}

		// $xmlishRegex = implode( '|', array_merge( $xmlishElements, $ignoredTags ) );
		// Use "A" modifier (anchored) instead of "^", because ^ doesn't work with an offset
		// $elementsRegex = "~($xmlishRegex)(?:\s|\/>|>)|(!--)~iA";

		stack.Clear();

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

		// XOWA-related init
		int src_len = src.length;
		int found = -1;

		Btrie_slim_mgr elements_trie = Btrie_slim_mgr.ci_a7();
		Btrie_slim_mgr elements_end_trie = Btrie_slim_mgr.ci_a7();

		byte[] cur_char = Bry_.Empty;
		byte[] cur_closing = Bry_.Empty;
		byte[] inner = null;

		while (true) {
			if (find_only_include) {
				// Ignore all input up to the next <onlyinclude>
				int start_pos = Bry_find_.Find_fwd(src, Bry__only_include_bgn, i, src_len);
				if (start_pos == Bry_find_.Not_found) {
					// Ignored section runs to the end
					accum.Add_str_a7("<ignore>").Add(htmlspecialchars(Bry_.Mid(src, i))).Add_str_a7("</ignore>");
					break;
				}
				int tag_end_pos = start_pos + Bry__only_include_bgn.length; // past-the-end
				accum.Add_str_a7("<ignore>").Add(htmlspecialchars(Bry_.Mid(src, i, tag_end_pos))).Add_str_a7("</ignore>");
				i = tag_end_pos;
				find_only_include = false;
			}

			Xomw_prepro_rule rule = null;
			if (fake_line_start) {
				found = Found__line_bgn;
				cur_char = Bry_.Empty;
			}
			else {
				// Find next opening brace, closing brace or pipe
				
				// $search = $searchBase;
				if (stack.top == null) {
					cur_closing = Bry_.Empty;
				}
				else {
					cur_closing = stack.top.close;
					// $search .= $currentClosing;
				}
				if (find_pipe) {
					// $search .= '|';
				}
				if (find_equals) {
					// First equals will be for the template
					// $search .= '=';
				}

				// Output literal section, advance input counter
				int literal_len = 0; 
				// NOTE: hard-coded translation of "strcspn(src, $search, i)"; no trie b/c of frequent additions / deletions
				boolean loop_stop = false;
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
					accum.Add(htmlspecialchars(Bry_.Mid(src, i, i + literal_len)));
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
					boolean match = false;
					if (cur_closing != Bry_.Empty) {
						if (Bry_.Match(src, i, i + cur_closing.length, cur_closing)) {
							match = true;
							found = Found__close;
							cur_char = cur_closing;
						}
					}
					else {
						Xomw_prepro_curchar_itm cur_char_itm = (Xomw_prepro_curchar_itm)cur_char_trie.Match_at(trv, src, i, src_len);
						if (cur_char_itm != null) {
							match = true;
							cur_char = cur_char_itm.bry;
							switch (cur_char_itm.type) {
								case Byte_ascii.Pipe:         found = Found__pipe; break;
								case Byte_ascii.Eq:           found = Found__equals; break;
								case Byte_ascii.Angle_bgn:    found = Found__angle; break;
								case Byte_ascii.Nl:           found = in_heading ? Found__line_end : Found__line_bgn; break;
								case Byte_ascii.Curly_bgn:   {found = Found__open; rule = rule_curly; break;}
								case Byte_ascii.Brack_bgn:   {found = Found__open; rule = rule_brack; break;}
								case Byte_ascii.Dash:        {found = Found__open; rule = rule_langv; break;}
							}
						}
					}
					if (!match) {
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

				// Determine element name; $elementsRegex = "~($xmlishRegex)(?:\s|\/>|>)|(!--)~iA"; EX: "(span|div)(?:\s|\/>|>)|(!--)
				Xomw_prepro_elem element = (Xomw_prepro_elem)elements_trie.Match_at(trv, src, i + 1, src_len);
				if (element == null) {
					// Element name missing or not listed
					accum.Add(Bry__escaped_lt);
					i++;
					continue;
				}

				// Handle comments
				if (element.type == Xomw_prepro_elem.Type__comment) {
					// To avoid leaving blank lines, when a sequence of space-separated comments is both preceded and followed by a newline
					// (ignoring spaces), then trim leading and trailing spaces and the trailing newline.

					// Find the end
					int comment_end_pos = Bry_find_.Find_fwd(src, Bry__comment_end, i + 4, src_len);
					if (comment_end_pos == Bry_find_.Not_found) {
						// Unclosed comment in input, runs to end
						accum.Add_str_a7("<comment>").Add(htmlspecialchars(Bry_.Mid(src, i))).Add_str_a7("</comment>");
						i = src_len;
					}
					else {
						// Search backwards for leading whitespace
						int ws_bgn = i > 0 ? i - Bry_find_.Find_bwd__while_space_or_tab(src, i, 0) : 0;

						// Search forwards for trailing whitespace
						// $wsEnd will be the position of the last space (or the '>' if there's none)
						int ws_end = comment_end_pos + 2 + Bry_find_.Find_fwd_while_space_or_tab(src, comment_end_pos + 3, src_len);

						// Keep looking forward as long as we're finding more comments.
						comments_list.Clear();
						comments_list.Add(new int[] {ws_bgn, ws_end});
						while (Bry_.Eq(src, ws_end + 1, ws_end + 5, Bry__comment_bgn)) {
							int cur_char_pos = Bry_find_.Find_fwd(src, Bry__comment_end, ws_end + 4);
							if (cur_char_pos == Bry_find_.Not_found) {
								break;
							}
							cur_char_pos = cur_char_pos + 2 + Bry_find_.Find_fwd_while_space_or_tab(src, cur_char_pos + 3, src_len);
							comments_list.Add(new int[] {ws_end + 1, cur_char_pos});
							ws_end = cur_char_pos;
						}

						// Eat the line if possible
						// TODO: This could theoretically be done if $wsStart == 0, i.e. for comments at the overall start.
						// That's not how Sanitizer::removeHTMLcomments() did it, but it's a possible beneficial b/c break.
						int comment_bgn_pos = -1;
						if (	ws_bgn > 0 
							&&	Bry_.Eq(src, ws_bgn - 1, ws_bgn    , Byte_ascii.Nl_bry)
							&&	Bry_.Eq(src, ws_end + 1, ws_end + 2, Byte_ascii.Nl_bry)
						) {
							// Remove leading whitespace from the end of the accumulator
							// Sanity check first though
							int ws_len = i - ws_bgn;
							byte[] accum_bry = accum.To_bry();
							if (   ws_len > 0
								&& Bry_find_.Find_fwd_while_space_or_tab(accum_bry, -ws_len, src_len) == ws_len
							) {
								accum.Clear().Add(Bry_.Mid(accum_bry, 0, -ws_len));
							}

							// Dump all but the last comment to the accumulator
							int comments_list_len = comments_list.Len();
							for (int j = 0; j < comments_list_len; j++) {
								int[] com = (int[])comments_list.Get_at(j);
								comment_bgn_pos = com[0];
								comment_end_pos = com[1] + 1;
								if (j == comments_list_len - 1) {
									break;
								}
								inner = Bry_.Mid(src, comment_bgn_pos, comment_end_pos);
								accum.Add_str_a7("<comment>").Add(htmlspecialchars(inner)).Add_str_a7("</comment>");
							}

							// Do a line-start run next time to look for headings after the comment
							fake_line_start = true;
						}
						else {
							// No line to eat, just take the comment itself
							comment_bgn_pos = i;
							comment_end_pos += 2;
						}

						if (stack.top != null) {
							Xomw_prepro_part part = stack.top.Get_current_part();
							if (!(part.comment_end != -1 && part.comment_end == ws_bgn - 1)) {
								part.visual_end = ws_bgn;
							}
							// Else comments abutting, no change in visual end
							part.comment_end = comment_end_pos;
						}
						i = comment_end_pos + 1;
						inner = Bry_.Mid(src, comment_bgn_pos, comment_end_pos + 1);
						accum.Add_str_a7("<comment>").Add(htmlspecialchars(inner)).Add_str_a7("</comment>");
						continue;
					}
				}

				byte[] name = element.name;
				int atr_bgn = i + name.length + 1;

				// Find end of tag
				int tag_end_pos = no_more_gt ? Bry_find_.Not_found : Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, atr_bgn);
				if (tag_end_pos == Bry_find_.Not_found) {
					// Infinite backtrack; Disable tag search to prevent worst-case O(N^2) performance
					no_more_gt = true;
					accum.Add(Bry__escaped_lt);
					i++;
					continue;
				}

				// Handle ignored tags
				if (ignored_tags.Has(name)) {
					accum.Add_str_a7("<ignore>").Add(htmlspecialchars(Bry_.Mid(src, i, tag_end_pos + 1))).Add_str_a7("</ignore>");
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
					// NOTE: translation of `preg_match( "/<\/" . preg_quote( $name, '/' ) . "\s*>/i",`
					Xomw_prepro_elem elem_end = null;
					int elem_end_lhs = -1, elem_end_rhs = -1;
					for (int j = tag_end_pos + 1; j < src_len; j++) {
						elem_end = (Xomw_prepro_elem)elements_end_trie.Match_at(trv, src, j, src_len);
						elem_end_lhs = elem_end_rhs = trv.Pos();

						// found a possible elem_end tag; validate "\s*>"
						if (elem_end != null) {
							elem_end_rhs = Bry_find_.Find_fwd_while(src, elem_end_rhs, src_len, Byte_ascii.Space);
							if (elem_end_rhs == src_len) {
								elem_end = null;
							}
							else {
								if (src[elem_end_rhs] == Byte_ascii.Gt) 
									elem_end_rhs = elem_end_rhs + 1;
								else
									elem_end = null;
							}
						}
						if (elem_end != null)
							break;
					}

					if (	!no_more_closing_tag.Has(name)
						&&	elem_end != null) {
						inner = Bry_.Mid(src, tag_end_pos + 1, elem_end_lhs);
						i = elem_end_rhs;
						tmp_bfr.Add_str_a7("<close>").Add(htmlspecialchars(Bry_.Mid(src, elem_end_lhs, elem_end_rhs))).Add_str_a7("</close>");
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
							accum.Add(htmlspecialchars(Bry_.Mid(src, tag_bgn_pos, tag_end_pos + 1)));
							// Cache results, otherwise we have O(N^2) performance for input like <foo><foo><foo>...
							no_more_closing_tag.Add_if_dupe_use_nth(name, name);
							continue;
						}
					}
				}

				// <includeonly> and <noinclude> just become <ignore> tags
				if (ignored_elements.Has(name)) {
					accum.Add_str_a7("<ignore>").Add(htmlspecialchars(Bry_.Mid(src, tag_bgn_pos, i))).Add_str_a7("</ignore>");
					continue;
				}

				accum.Add_str_a7("<ext>");
				byte[] atr_bry = atr_end <= atr_bgn ? Bry_.Empty : Bry_.Mid(src, atr_bgn, atr_end);
				accum.Add_str_a7("<name>").Add(name).Add_str_a7("</name>");
				// Note that the attr element contains the whitespace between name and attribute,
				// this is necessary for precise reconstruction during pre-save transform.
				accum.Add_str_a7("<attr>").Add(htmlspecialchars(atr_bry)).Add_str_a7("</attr>");
				if (inner != null) {
					accum.Add_str_a7("<inner>").Add(htmlspecialchars(inner)).Add_str_a7("</inner>");
				}
				accum.Add(close).Add_str_a7("</ext>");
			}
			else if (found == Found__line_bgn) {
				// Is this the start of a heading?; Line break belongs before the heading element in any case
				if (fake_line_start) {
					fake_line_start = false;
				} else {
					accum.Add(cur_char);
					i++;
				}

				int eq_end = Bry_find_.Find_fwd_while(src, i, i + 6, Byte_ascii.Eq);	// strspn( $src, '=', $i, 6 );					
				int count = i - eq_end;
				if (count == 1 && find_equals) {
					// DWIM: This looks kind of like a name/value separator.
					// Let's let the equals handler have it and break the potential heading.
					// This is heuristic, but AFAICT the methods for completely correct disambiguation are very complex.
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
				// Do this using the reversed String since the other solutions (end anchor, etc.) are inefficient.
				int search_bgn = Bry_find_.Find_bwd__while_space_or_tab(src, i, 0);
				if (part.comment_end != -1 && search_bgn -1 == part.comment_end) {
					// Comment found at line end; Search for equals signs before the comment
					search_bgn = part.visual_end;
					search_bgn -= Bry_find_.Find_bwd__while_space_or_tab(src, search_bgn, 0);
				}
				int count = piece.count;
				int eq_len = Bry_find_.Find_bwd_while(src, search_bgn, 0, Byte_ascii.Eq);

				byte[] element = Bry_.Empty;
				if (eq_len > 0) {
					if (search_bgn - eq_len == piece.start_pos) {
						// This is just a single String of equals signs on its own line
						// Replicate the doHeadings behavior /={count}(.+)={count}/
						// First find out how many equals signs there really are (don't stop at 6)
						count = eq_len;
						if (count < 3) {
							count = 0;
						} else {
							count = (count - 1) / 2;
							if (count > 6) count = 6;
						}
					} 
					else {
						if (eq_len < count)
							count = eq_len;
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
				// Note that we do NOT increment the input pointer. This is because the closing linebreak could be the opening linebreak of another heading.
				// Infinite loops are avoided because the next iteration MUST hit the heading open case above, which unconditionally increments the input pointer.
			}
			else if (found == Found__open) {
				// count opening brace characters
				int count = Bry_find_.Find_fwd_while(src, i, src_len, cur_char) - i;

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
					accum.Add(htmlspecialchars(Bry_.Repeat_bry(cur_char, count)));
				}
				i += count;
			}
			else if (found == Found__close) {
				Xomw_prepro_piece piece = stack.top;
				// lets check if there are enough characters for closing brace
				int count = Bry_find_.Find_fwd_while(src, i, src_len, cur_char) - i;
				int max_count = piece.count;
				if (count > max_count) count = max_count;

				// check for maximum matching characters (if there are 5 closing characters, we will probably need only 3 - depending on the rules)
				rule = Get_rule(piece.open);
				int matching_count = -1;
				if (count > rule.max) {
					// The specified maximum exists in the callback array, unless the caller has made an error
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
					accum.Add(htmlspecialchars(Bry_.Repeat_bry(cur_char, count)));
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
					// Create XML element; Note: $parts is already XML, does not need to be encoded further
					List_adp parts = piece.parts;
					byte[] title = ((Xomw_prepro_part)parts.Get_at(0)).bfr.To_bry_and_clear();
					parts.Del_at(0);

					// The invocation is at the start of the line if lineStart is set in the stack, and all opening brackets are used up.
					byte[] attr = null;
					if (max_count == matching_count && !piece.line_start) {
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
							byte[] part_bry = part.bfr.To_bry();
							byte[] arg_key = Bry_.Mid(part_bry, 0, part.Eqpos);
							byte[] arg_val = Bry_.Mid(part_bry, part.Eqpos + 1);
							tmp_bfr.Add_str_a7("<part><name>").Add(arg_key).Add_str_a7("</name>=<value>").Add(arg_val).Add_str_a7("</value></part>");
						}
						else {
							tmp_bfr.Add_str_a7("<part><name index=\"").Add_int_variable(arg_idx).Add_str_a7("\" /><value>{").Add(part.bfr.To_bry()).Add_str_a7("}</value></part>");
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
					piece.parts.Clear(); // piece.parts = [ new PPDPart ];
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
	private byte[] htmlspecialchars(byte[] bry) {
		return bry;
	}
	private Xomw_prepro_rule Get_rule(byte[] bry) {
		if		(Bry_.Eq(bry, rule_curly.bgn))   return rule_curly;
		else if	(Bry_.Eq(bry, rule_brack.bgn))   return rule_brack;
		else if	(Bry_.Eq(bry, rule_langv.bgn))   return rule_langv;
		else                                     throw Err_.new_unhandled(bry);
	}
	private static final    Xomw_prepro_rule 
	  rule_curly = new Xomw_prepro_rule(Bry_.new_a7("{"), Bry_.new_a7("}")  , 2, 3, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__tmpl, Xomw_prepro_rule.Name__targ})
	, rule_brack = new Xomw_prepro_rule(Bry_.new_a7("["), Bry_.new_a7("]")  , 2, 2, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__null})
	, rule_langv = new Xomw_prepro_rule(Bry_.new_a7("-{"), Bry_.new_a7("}-"), 1, 1, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__null})
	;
	private static final    byte[] 
	  Bry__only_include_bgn = Bry_.new_a7("<onlyinclude>")
	, Bry__only_include_end = Bry_.new_a7("</onlyinclude>")
	, Bry__comment_bgn = Bry_.new_a7("<!--")
	, Bry__comment_end = Bry_.new_a7("-->")
	, Bry__escaped_lt = Bry_.new_a7("&lt;")
	;
	private static final    int Len__only_include_end = Bry__only_include_end.length;
	private static final int 
	  Found__line_bgn = 0
	, Found__line_end = 1
	, Found__pipe = 2
	, Found__equals = 3
	, Found__angle = 4
	, Found__close = 5
	, Found__open = 6
	;
}
