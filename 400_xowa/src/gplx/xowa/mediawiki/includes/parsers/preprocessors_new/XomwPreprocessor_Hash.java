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
package gplx.xowa.mediawiki.includes.parsers.preprocessors_new;
import gplx.frameworks.objects.New;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.utls.CharUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.SRC:1.33
import gplx.langs.regxs.*;

/**
* Differences from DOM schema:
*   * attribute nodes are children
*   * "<h>" nodes that aren"t at the top are replaced with <possible-h>
*
* Nodes are stored in a recursive array data structure. A node store is an
* array where each element may be either a scalar (representing a text node)
* or a "descriptor", which is a two-element array where the first element is
* the node name and the second element is the node store for the children.
*
* Attributes are represented as children that have a node name starting with
* "@", and a single text node child.
*
* @todo: Consider replacing descriptor arrays with objects of a new cl+ass.
* Benchmark and measure resulting memory impact.
*
* @ingroup Parser
*/
public class XomwPreprocessor_Hash extends XomwPreprocessor {
	/**
	* @var Parser
	*/
	public XomwParser parser;

	public static final String CACHE_PREFIX = "preprocess-hash";
	@New public static final int CACHE_VERSION = 2;

	public XomwPreprocessor_Hash(XomwParser parser) {
		this.parser = parser;
	}

	/**
	* @return PPFrame_Hash
	*/
	@Override public XomwPPFrame newFrame() {
//			return new XomwPPFrame_Hash(this);
		return null;
	}

	/**
	* @param array $args
	* @return PPCustomFrame_Hash
	*/
//		public XomwPPCustomFrame_Hash newCustomFrame(XophpArray args) {
//			return new XomwPPCustomFrame_Hash(this, args);
//		}

	/**
	* @param array $values
	* @return PPNode_Hash_Array
	*/
	@Override public XomwPPNode newPartNodeArray(XophpArray values) {
		XophpArray list = XophpArray.New();

//			foreach ($values as $k => $val) {
//				if (is_int($k)) {
//					$store = [ [ "part", [
//						[ "name", [ [ "@index", [ $k ] ] ] ],
//						[ "value", [ strval($val) ] ],
//					] ] ];
//				} else {
//					$store = [ [ "part", [
//						[ "name", [ strval($k) ] ],
//						"=",
//						[ "value", [ strval($val) ] ],
//					] ] ];
//				}
//
//				$list[] = new PPNode_Hash_Tree($store, 0);
//			}

		XomwPPNode_Hash_Array node = new XomwPPNode_Hash_Array(list);
		return node;
	}

	private static final Hash_adp strspn_hash__eq = XophpString_.strspn_hash("=");
	private static final Hash_adp strspn_hash__ws_tab = XophpString_.strspn_hash(" \t");
	/**
	* Preprocess some wikitext and return the document tree.
	*
	* @param String $text The text to parse
	* @param int $flags Bitwise combination of:
	*    Parser::PTD_FOR_INCLUSION    Handle "<noinclude>" and "<includeonly>" as if the text is being
	*                                 included. Default is to assume a direct page view.
	*
	* The generated DOM tree must depend only on the input text and the flags.
	* The DOM tree must be the same in OT_HTML and OT_WIKI mode, to avoid a regression of T6899.
	*
	* Any flag added to the $flags parameter here, or any other parameter liable to cause a
	* change in the DOM tree for a given text, must be passed through the section identifier
	* in the section edit link and thus back to extractSections().
	*
	* @throws MWException
	* @return PPNode_Hash_Tree
	*/
	@Override public XomwPPNode preprocessToObj(String text, int flags) { // flags = 0;
		// global wgDisableLangConversion;

//			tree = this.cacheGetTree(text, flags);
//			if (tree !== false) {
//				store = json_decode(tree);
//				if (is_array(store)) {
//					return new PPNode_Hash_Tree(store, 0);
//				}
//			}

		int forInclusion = flags & XomwParser.PTD_FOR_INCLUSION;

		XophpArray xmlishElements = this.parser.getStripList();
		XophpArray xmlishAllowMissingEndTag = XophpArray.New("includeonly", "noinclude", "onlyinclude");
		boolean enableOnlyinclude = false;
		XophpArray ignoredTags, ignoredElements;
		if (XophpInt_.is_true(forInclusion)) {
			ignoredTags = XophpArray.New("includeonly", "/includeonly");
			ignoredElements = XophpArray.New("noinclude");
			xmlishElements.Add("noinclude");
			if (XophpString_.strpos(text, "<onlyinclude>") != -1
				&& XophpString_.strpos(text, "</onlyinclude>") != -1
			) {
				enableOnlyinclude = true;
			}
		} else {
			ignoredTags = XophpArray.New("noinclude", "/noinclude", "onlyinclude", "/onlyinclude");
			ignoredElements = XophpArray.New("includeonly");
			xmlishElements.Add("includeonly");
		}
		String xmlishRegex = XophpArray.implode("|", XophpArray.array_merge(xmlishElements, ignoredTags));

		// Use "A" modifier (anchored) instead of "^", because ^ doesn"t work with an offset
		Regx_adp elementsRegex = XophpRegex_.Pattern("(" + xmlishRegex + ")(?:\\s|\\/>|>)|(!--)", XophpRegex_.MODIFIER_i | XophpRegex_.MODIFIER_A);

		XomwPPDStack_Hash stack = new XomwPPDStack_Hash(XomwPPDPart_Hash.Instance);

		String searchBase = "[{<\n";
		if (!XomwDefaultSettings.wgDisableLangConversion) {
			searchBase += "-";
		}

		// For fast reverse searches
		String revText = XophpString_.strrev(text);
		int lengthText = XophpString_.strlen(text);

		// Input pointer, starts out pointing to a pseudo-newline before the start
		int i = 0;
		// Current accumulator. See the doc comment for Preprocessor_Hash for the format.
		XophpArray accum = stack.getAccum(); // =&
		// True to find equals signs in arguments
		boolean findEquals = false;
		// True to take notice of pipe characters
		boolean findPipe = false;
		int headingIndex = 1;
		// True if i is inside a possible heading
		boolean inHeading = false;
		// True if there are no more greater-than (>) signs right of i
		boolean noMoreGT = false;
		// Map of tag name => true if there are no more closing tags of given type right of i
		XophpArray noMoreClosingTag = XophpArray.New();
		// True to ignore all input up to the next <onlyinclude>
		boolean findOnlyinclude = enableOnlyinclude;
		// Do a line-start run without outputting an LF character
		boolean fakeLineStart = true;

		while (true) {
			// // this.memCheck(); // XO.NOTE: commented out in MW

			int startPos = 0;
			if (findOnlyinclude) {
				// Ignore all input up to the next <onlyinclude>
				startPos = XophpString_.strpos(text, "<onlyinclude>", i);
				if (XophpInt_.is_false(startPos)) {
					// Ignored section runs to the end
					accum.Add(XophpArray.New("ignore", XophpArray.New(XophpString_.substr(text, i))));
					break;
				}
				int tagEndPos = startPos + XophpString_.strlen("<onlyinclude>"); // past-the-end
				accum.Add(XophpArray.New("ignore", XophpArray.New(XophpString_.substr(text, i, tagEndPos - i))));
				i = tagEndPos;
				findOnlyinclude = false;
			}

			String found = null, curChar = null;
			XophpArray rule = null;
			if (fakeLineStart) {
				found = "line-start";
				curChar = "";
			} else {
				// Find next opening brace, closing brace or pipe
				String search = searchBase;
				String currentClosing;
				if (XophpObject_.is_false(stack.top)) {
					currentClosing = "";
				} else {
					currentClosing = stack.top.close;
					search += currentClosing;
				}
				if (findPipe) {
					search += "|";
				}
				if (findEquals) {
					// First equals will be for the template
					search += "=";
				}
				rule = null;
				// Output literal section, advance input counter
				int literalLength = XophpString_.strcspn(text, XophpString_.strspn_hash(search), i);
				if (literalLength > 0) {
					XomwPreprocessor_Hash.addLiteral(accum, XophpString_.substr(text, i, literalLength));
					i += literalLength;
				}
				if (i >= lengthText) {
					if (XophpString_.eq(currentClosing, "\n")) {
						// Do a past-the-end run to finish off the heading
						curChar = "";
						found = "line-end";
					} else {
						// All done
						break;
					}
				} else {
					String curTwoChar = null;
					curChar = curTwoChar = CharUtl.ToStr(StringUtl.CharAt(text, i));
					if ((i + 1) < lengthText) {
						curTwoChar += CharUtl.ToStr(StringUtl.CharAt(text, i + 1));
					}
					if (StringUtl.Eq(curChar, "|")) {
						found = "pipe";
					} else if (StringUtl.Eq(curChar, "=")) {
						found = "equals";
					} else if (StringUtl.Eq(curChar, "<")) {
						found = "angle";
					} else if (StringUtl.Eq(curChar, "\n")) {
						if (inHeading) {
							found = "line-end";
						} else {
							found = "line-start";
						}
					} else if (StringUtl.Eq(curTwoChar, currentClosing)) {
						found = "close";
						curChar = curTwoChar;
					} else if (StringUtl.Eq(curChar, currentClosing)) {
						found = "close";
					} else if (XophpObject_.isset_obj(this.rules.Get_by(curTwoChar))) {
						curChar = curTwoChar;
						found = "open";
						rule = this.rules.Get_by_ary(curChar);
					} else if (XophpObject_.isset_obj(this.rules.Get_by(curChar))) {
						found = "open";
						rule = this.rules.Get_by_ary(curChar);
					} else {
						// Some versions of PHP have a strcspn which stops on
						// null characters; ignore these and continue.
						// We also may get "-" and "}" characters here which
						// don"t match -{ or currentClosing.  Add these to
						// output and continue.
						if (StringUtl.Eq(curChar, "-") || StringUtl.Eq(curChar, "}")) {
							XomwPreprocessor_Hash.addLiteral(accum, curChar);
						}
						++i;
						continue;
					}
				}
			}

			if (StringUtl.Eq(found, "angle")) {
				String inner, close;
				int attrEnd;
				XophpArray matches = XophpArray.New();
				// Handle </onlyinclude>
				if (enableOnlyinclude
					&& XophpString_.eq(XophpString_.substr(text, i, XophpString_.strlen("</onlyinclude>")), "</onlyinclude>")
				) {
					findOnlyinclude = true;
					continue;
				}

				// Determine element name
				if (!XophpRegex_.preg_match_bool(elementsRegex, text, matches, 0, i + 1)) {
					// Element name missing or not listed
					XomwPreprocessor_Hash.addLiteral(accum, "<");
					++i;
					continue;
				}
				// Handle comments
				if (XophpArray.isset(matches, 2) && StringUtl.Eq(matches.Get_at_str(2), "!--")) {
					// To avoid leaving blank lines, when a sequence of
					// space-separated comments is both preceded and followed by
					// a newline (ignoring spaces), then
					// trim leading and trailing spaces and the trailing newline.

					// Find the end
					int endPos = XophpString_.strpos(text, "-->", i + 4);
					if (XophpInt_.is_false(endPos)) {
						// Unclosed comment in input, runs to end
						inner = XophpString_.substr(text, i);
						accum.Add(XophpArray.New("comment", XophpArray.New(inner)));
						i = lengthText;
					} else {
						// Search backwards for leading whitespace
						int wsStart = XophpInt_.is_true(i) ? (i - XophpString_.strspn(revText, strspn_hash__ws_tab, lengthText - i)) : 0;

						// Search forwards for trailing whitespace
						// wsEnd will be the position of the last space (or the ">" if there"s none)
						int wsEnd = endPos + 2 + XophpString_.strspn(text, strspn_hash__ws_tab, endPos + 3);

						// Keep looking forward as long as we"re finding more
						// comments.
						XophpArray comments = XophpArray.New(XophpArray.New().Add(wsStart).Add(wsEnd));
						while (XophpString_.eq(XophpString_.substr(text, wsEnd + 1, 4), "<!--")) {
							int c = XophpString_.strpos(text, "-.", wsEnd + 4);
							if (XophpInt_.is_false(c)) {
								break;
							}
							c = c + 2 + XophpString_.strspn(text, strspn_hash__ws_tab, c + 3);
							comments.Add(XophpArray.New(wsEnd + 1).Add(c));
							wsEnd = c;
						}

						// Eat the line if possible
						// TODO: This could theoretically be done if wsStart == 0, i.e. for comments at
						// the overall start. That"s not how Sanitizer::removeHTMLcomments() did it, but
						// it"s a possible beneficial b/c break.
						if (wsStart > 0 && XophpString_.eq(XophpString_.substr(text, wsStart - 1, 1), "\n")
							&& XophpString_.eq(XophpString_.substr(text, wsEnd + 1, 1), "\n")
						) {
							// Remove leading whitespace from the end of the accumulator
							int wsLength = i - wsStart;
							int endIndex = XophpArray.count(accum) - 1;

							// Sanity check
							if (wsLength > 0
								&& endIndex >= 0
								&& XophpType_.is_string(accum.Get_at(endIndex))
								&& XophpString_.strspn(accum.Get_at_str(endIndex), strspn_hash__ws_tab, -wsLength) == wsLength
							) {
								accum.Set(endIndex, XophpString_.substr(accum.Get_at_str(endIndex), 0, -wsLength));
							}

							// Dump all but the last comment to the accumulator
							int commentsLen = comments.Len();
							for (int commentsIdx = 0; commentsIdx < commentsLen; commentsIdx++) {
								XophpArrayItm itm = comments.Get_at_itm(commentsIdx);
								int j = itm.KeyAsInt();
								XophpArray com = (XophpArray)itm.Val();
								startPos = com.Get_at_int(0);
								endPos = com.Get_at_int(1) + 1;
								if (j == (XophpArray.count(comments) - 1)) {
									break;
								}
								inner = XophpString_.substr(text, startPos, endPos - startPos);
								accum.Add(XophpArray.New("comment", XophpArray.New(inner)));
							}

							// Do a line-start run next time to look for headings after the comment
							fakeLineStart = true;
						} else {
							// No line to eat, just take the comment itself
							startPos = i;
							endPos += 2;
						}

						if (XophpObject_.is_true(stack.top)) {
							XomwPPDPart part = stack.top.getCurrentPart();
							if (!(XophpObject_.isset(part.commentEnd) && part.commentEnd == wsStart - 1)) {
								part.visualEnd = wsStart;
							}
							// Else comments abutting, no change in visual end
							part.commentEnd = endPos;
						}
						i = endPos + 1;
						inner = XophpString_.substr(text, startPos, endPos - startPos + 1);
						accum.Add(XophpArray.New("comment", XophpArray.New(inner)));
					}
					continue;
				}
				String name = matches.Get_at_str(1);
				String lowerName = XophpString_.strtolower(name);
				int attrStart = i + XophpString_.strlen(name) + 1;

				// Find end of tag
				int tagEndPos = noMoreGT ? XophpInt_.False : XophpString_.strpos(text, ">", attrStart);
				if (XophpInt_.is_false(tagEndPos)) {
					// Infinite backtrack
					// Disable tag search to prevent worst-case O(N^2) performance
					noMoreGT = true;
					XomwPreprocessor_Hash.addLiteral(accum, "<");
					++i;
					continue;
				}

				// Handle ignored tags
				if (XophpArray.in_array(lowerName, ignoredTags)) {
					accum.Add(XophpArray.New("ignore", XophpArray.New(XophpString_.substr(text, i, tagEndPos - i + 1))));
					i = tagEndPos + 1;
					continue;
				}

				int tagStartPos = i;
				if (XophpString_.Char_eq(text, tagEndPos - 1, "/")) {
					// Short end tag
					attrEnd = tagEndPos - 1;
					inner = null;
					i = tagEndPos + 1;
					close = null;
				} else {
					matches.Clear(); // XO
					attrEnd = tagEndPos;
					// Find closing tag
					if (
						!XophpObject_.isset_obj(noMoreClosingTag.Get_by(name)) &&
						XophpRegex_.preg_match_bool("</" + XophpRegex_.preg_quote(name, "/") + "\\s*>", XophpRegex_.MODIFIER_i,
							text, matches, XophpRegex_.PREG_OFFSET_CAPTURE, tagEndPos + 1)
					) {
						inner = XophpString_.substr(text, tagEndPos + 1, matches.Get_at_ary(0).Get_at_int(1) - tagEndPos - 1);
						i = matches.Get_at_ary(0).Get_at_int(1) + XophpString_.strlen(matches.Get_at_ary(0).Get_at_str(0));
						close = matches.Get_at_ary(0).Get_at_str(0);
					} else {
						// No end tag
						if (XophpArray.in_array(name, xmlishAllowMissingEndTag)) {
							// Let it run out to the end of the text.
							inner = XophpString_.substr(text, tagEndPos + 1);
							i = lengthText;
							close = null;
						} else {
							// Don"t match the tag, treat opening tag as literal and resume parsing.
							i = tagEndPos + 1;
							XomwPreprocessor_Hash.addLiteral(accum,
								XophpString_.substr(text, tagStartPos, tagEndPos + 1 - tagStartPos));
							// Cache results, otherwise we have O(N^2) performance for input like <foo><foo><foo>...
							noMoreClosingTag.Set(name, true);
							continue;
						}
					}
				}
				// <includeonly> and <noinclude> just become <ignore> tags
				if (XophpArray.in_array(lowerName, ignoredElements)) {
					accum.Add(XophpArray.New("ignore", XophpArray.New(XophpString_.substr(text, tagStartPos, i - tagStartPos))));
					continue;
				}

				String attr = null;
				if (attrEnd <= attrStart) {
					attr = "";
				} else {
					// Note that the attr element contains the whitespace between name and attribute,
					// this is necessary for precise reconstruction during pre-save transform.
					attr = XophpString_.substr(text, attrStart, attrEnd - attrStart);
				}

				XophpArray children = XophpArray.New(
					XophpArray.New("name", XophpArray.New(name)),
					XophpArray.New("attr", XophpArray.New(attr)));
				if (inner != null) {
					children.Add(XophpArray.New("inner", XophpArray.New(inner)));
				}
				if (close != null) {
					children.Add(XophpArray.New("close", XophpArray.New(close)));
				}
				accum.Add(XophpArray.New("ext", children));
			} else if (StringUtl.Eq(found, "line-start")) {
				// Is this the start of a heading?
				// Line break belongs before the heading element in any case
				if (fakeLineStart) {
					fakeLineStart = false;
				} else {
					XomwPreprocessor_Hash.addLiteral(accum, curChar);
					i++;
				}

				int count = XophpString_.strspn(text, strspn_hash__eq, i, 6);
				if (count == 1 && findEquals) {
					// DWIM: This looks kind of like a name/value separator.
					// Let"s let the equals handler have it and break the potential
					// heading. This is heuristic, but AFAICT the methods for
					// completely correct disambiguation are very complex.
				} else if (count > 0) {
					XophpArray piece = XophpArray.New()
						.Add("open", "\n")
						.Add("close", "\n")
						.Add("parts", XophpArray.New(new XomwPPDPart_Hash(XophpString_.str_repeat("=", count))))
						.Add("startPos", i)
						.Add("count", count);
					stack.push(piece);
					accum = stack.getAccum(); // =&
					XophpArray stackFlags = stack.getFlags();
					if (XophpArray.isset(stackFlags, "findEquals")) {
						findEquals = stackFlags.Get_by_bool("findEquals");
					}
					if (XophpArray.isset(stackFlags, "findPipe")) {
						findPipe = stackFlags.Get_by_bool("findPipe");
					}
					if (XophpArray.isset(stackFlags, "inHeading")) {
						inHeading = stackFlags.Get_by_bool("inHeading");
					}
					i += count;
				}
			} else if (found == "line-end") {
				XomwPPDStackElement piece = stack.top;
				// A heading must be open, otherwise \n wouldn"t have been in the search list
				// FIXME: Don"t use assert()
				// phpcs:ignore MediaWiki.Usage.ForbiddenFunctions.assert
				// assert(piece.open === "\n");
				XomwPPDPart part = piece.getCurrentPart();
				// Search back through the input to see if it has a proper close.
				// Do this using the reversed String since the other solutions
				// (end anchor, etc.) are inefficient.
				int wsLength = XophpString_.strspn(revText, strspn_hash__ws_tab, lengthText - i);
				int searchStart = i - wsLength;
				// XO.NOTE: MW says isset(part.commentEnd) b/c commentEnd can be null due to magic property
				if (XophpInt_.is_true(part.commentEnd) && searchStart - 1 == part.commentEnd) {
					// Comment found at line end
					// Search for equals signs before the comment
					searchStart = part.visualEnd;
					searchStart -= XophpString_.strspn(revText, strspn_hash__ws_tab, lengthText - searchStart);
				}
				XophpArray element;
				int count = piece.count;
				int equalsLength = XophpString_.strspn(revText, strspn_hash__eq, lengthText - searchStart);
				if (equalsLength > 0) {
					if (searchStart - equalsLength == piece.startPos) {
						// This is just a single String of equals signs on its own line
						// Replicate the doHeadings behavior /={count}(.+)={count}/
						// First find out how many equals signs there really are (don"t stop at 6)
						count = equalsLength;
						if (count < 3) {
							count = 0;
						} else {
							count = XophpMath_.min(6, XophpDouble_.intval((count - 1) / 2));
						}
					} else {
						count = XophpMath_.min(equalsLength, count);
					}
					if (count > 0) {
						// Normal match, output <h>
						element = XophpArray.New(XophpArray.New("possible-h",
							XophpArray.array_merge(
								XophpArray.New(
									XophpArray.New("@level", XophpArray.New(count)),
									XophpArray.New("@i", XophpArray.New(headingIndex++))
								),
								accum
							)
						));
					} else {
						// Single equals sign on its own line, count=0
						element = accum;
					}
				} else {
					// No match, no <h>, just pass down the inner text
					element = accum;
				}
				// Unwind the stack
				stack.pop();
				accum = stack.getAccum(); // =&
				XophpArray stackFlags = stack.getFlags();
				if (XophpArray.isset(stackFlags, "findEquals")) {
					findEquals = stackFlags.Get_by_bool("findEquals");
				}
				if (XophpArray.isset(stackFlags, "findPipe")) {
					findPipe = stackFlags.Get_by_bool("findPipe");
				}
				if (XophpArray.isset(stackFlags, "inHeading")) {
					inHeading = stackFlags.Get_by_bool("inHeading");
				}

				// Append the result to the enclosing accumulator
				XophpArray.array_splice(accum, XophpArray.count(accum), 0, element);

				// Note that we do NOT increment the input pointer.
				// This is because the closing linebreak could be the opening linebreak of
				// another heading. Infinite loops are avoided because the next iteration MUST
				// hit the heading open case above, which unconditionally increments the
				// input pointer.
			} else if (StringUtl.Eq(found, "open")) {
				// count opening brace characters
				int curLen = XophpString_.strlen(curChar);
				int count = (curLen > 1) ?
					// allow the final character to repeat
					XophpString_.strspn(text, XophpString_.strspn_hash(XophpString_.Char_as_str(curChar, curLen - 1)), i + 1) + 1 :
					XophpString_.strspn(text, XophpString_.strspn_hash(curChar), i);

				String savedPrefix = "";
				boolean lineStart = (i > 0 && XophpString_.Char_eq(text, i - 1, "\n"));

				if (StringUtl.Eq(curChar, "-{") && count > curLen) {
					// -{ => {{ transition because rightmost wins
					savedPrefix = "-";
					i++;
					curChar = "{";
					count--;
					rule = this.rules.Get_by_ary(curChar);
				}

				// we need to add to stack only if opening brace count is enough for one of the rules
				if (count >= rule.Get_by_int("min")) {
					// Add it to the stack
					XophpArray piece = XophpArray.New()
						.Add("open", curChar)
						.Add("close", rule.Get_by("end"))
						.Add("savedPrefix", savedPrefix)
						.Add("count", count)
						.Add("lineStart", lineStart)
					;

					stack.push(piece);
					accum = stack.getAccum(); // =&
					XophpArray stackFlags = stack.getFlags();
					if (XophpArray.isset(stackFlags, "findEquals")) {
						findEquals = stackFlags.Get_by_bool("findEquals");
					}
					if (XophpArray.isset(stackFlags, "findPipe")) {
						findPipe = stackFlags.Get_by_bool("findPipe");
					}
					if (XophpArray.isset(stackFlags, "inHeading")) {
						inHeading = stackFlags.Get_by_bool("inHeading");
					}
				} else {
					// Add literal brace(s)
					XomwPreprocessor_Hash.addLiteral(accum, savedPrefix + XophpString_.str_repeat(curChar, count));
				}
				i += count;
			} else if (StringUtl.Eq(found, "close")) {
				XomwPPDStackElement_Hash piece = (XomwPPDStackElement_Hash)stack.top;
				// lets check if there are enough characters for closing brace
				int maxCount = piece.count;
				if (StringUtl.Eq(piece.close, "}-") && StringUtl.Eq(curChar, "}")) {
					maxCount--; // don"t try to match closing "-" as a "}"
				}
				int curLen = XophpString_.strlen(curChar);
				int count = (curLen > 1) ? curLen :
					XophpString_.strspn(text, XophpString_.strspn_hash(curChar), i, maxCount);

				// check for maximum matching characters (if there are 5 closing
				// characters, we will probably need only 3 - depending on the rules)
				rule = this.rules.Get_by_ary(piece.open);
				int matchingCount;
				if (count > rule.Get_by_int("max")) {
					// The specified maximum exists in the callback array, unless the caller
					// has made an error
					matchingCount = rule.Get_by_int("max");
				} else {
					// Count is less than the maximum
					// Skip any gaps in the callback array to find the true largest match
					// Need to use array_key_exists not isset because the callback can be null
					matchingCount = count;
					while (matchingCount > 0 && !XophpArray.array_key_exists(matchingCount, rule.Get_by_ary("names"))) {
						--matchingCount;
					}
				}

				String endText;
				XophpArray element;
				if (matchingCount <= 0) {
					// No matching element found in callback array
					// Output a literal closing brace and continue
					endText = XophpString_.substr(text, i, count);
					XomwPreprocessor_Hash.addLiteral(accum, endText);
					i += count;
					continue;
				}
				String name = rule.Get_by_ary("names").Get_by_str(matchingCount);
				if (name == null) {
					// No element, just literal text
					endText = XophpString_.substr(text, i, matchingCount);
					element = piece.breakSyntax(matchingCount);
					XomwPreprocessor_Hash.addLiteral(element, endText);
				} else {
					// Create XML element
					XophpArray parts = piece.parts;
					XophpArray titleAccum = ((XomwPPDPart)parts.Get_at(0)).output;
					XophpArray.unset(parts, 0);

					XophpArray children = XophpArray.New();

					// The invocation is at the start of the line if lineStart is set in
					// the stack, and all opening brackets are used up.
					if (maxCount == matchingCount &&
							!XophpObject_.empty(piece.lineStart) &&
							XophpString_.strlen(piece.savedPrefix) == 0) {
						children.Add(XophpArray.New("@lineStart", XophpArray.New(1)));
					}
					XophpArray titleNode = XophpArray.New("title", titleAccum);
					children.Add(titleNode);
					int argIndex = 1;
					int parts_len = parts.Len();
					for (int j = 0; j < parts_len; j++) {
						XomwPPDPart_Hash part = (XomwPPDPart_Hash)parts.Get_at(j);
						if (XophpInt_.is_true(part.eqpos)) { // XO.NOTE: MW says isset(part.commentEnd) b/c commentEnd can be null due to magic property
							Object equalsNode = part.output.Get_at(part.eqpos);
							XophpArray nameNode = XophpArray.New("name", XophpArray.array_slice(part.output, 0, part.eqpos));
							XophpArray valueNode = XophpArray.New("value", XophpArray.array_slice(part.output, part.eqpos + 1));
							XophpArray partNode = XophpArray.New("part", XophpArray.New(nameNode, equalsNode, valueNode));
							children.Add(partNode);
						} else {
							XophpArray nameNode = XophpArray.New("name", XophpArray.New(XophpArray.New("@index", XophpArray.New(argIndex++))));
							XophpArray valueNode = XophpArray.New("value", part.output);
							XophpArray partNode = XophpArray.New("part", XophpArray.New(nameNode, valueNode));
							children.Add(partNode);
						}
					}
					element = XophpArray.New(XophpArray.New(name, children));
				}

				// Advance input pointer
				i += matchingCount;

				// Unwind the stack
				stack.pop();
				accum = stack.getAccum(); // =&

				// Re-add the old stack element if it still has unmatched opening characters remaining
				if (matchingCount < piece.count) {
					piece.parts = XophpArray.New(new XomwPPDPart_Hash(""));
					piece.count -= matchingCount;
					// do we still qualify for any callback with remaining count?
					int min = this.rules.Get_by_ary(piece.open).Get_by_int("min");
					if (piece.count >= min) {
						stack.push(piece);
						accum = stack.getAccum(); // =&
					} else if (piece.count == 1 && StringUtl.Eq(piece.open, "{") && StringUtl.Eq(piece.savedPrefix, "-")) {
						piece.savedPrefix = "";
						piece.open = "-{";
						piece.count = 2;
						piece.close = this.rules.Get_by_ary(piece.open).Get_by_str("end");
						stack.push(piece);
						accum = stack.getAccum(); // =&
					} else {
						String s = XophpString_.substr(piece.open, 0, -1);
						s += XophpString_.str_repeat(
							XophpString_.substr(piece.open, -1),
							piece.count - XophpString_.strlen(s)
						);
						XomwPreprocessor_Hash.addLiteral(accum, piece.savedPrefix + s);
					}
				} else if (!StringUtl.Eq(piece.savedPrefix, "")) {
					XomwPreprocessor_Hash.addLiteral(accum, piece.savedPrefix);
				}

				XophpArray stackFlags = stack.getFlags();
				if (XophpArray.isset(stackFlags, "findEquals")) {
					findEquals = stackFlags.Get_by_bool("findEquals");
				}
				if (XophpArray.isset(stackFlags, "findPipe")) {
					findPipe = stackFlags.Get_by_bool("findPipe");
				}
				if (XophpArray.isset(stackFlags, "inHeading")) {
					inHeading = stackFlags.Get_by_bool("inHeading");
				}

				// Add XML element to the enclosing accumulator
				XophpArray.array_splice(accum, XophpArray.count(accum), 0, element);
			} else if (StringUtl.Eq(found, "pipe")) {
				findEquals = true; // shortcut for getFlags()
				stack.addPart();
				accum = stack.getAccum(); // =&
				++i;
			} else if (StringUtl.Eq(found, "equals")) {
				findEquals = false; // shortcut for getFlags()
				accum.Add(XophpArray.New("equals", XophpArray.New("=")));
				stack.getCurrentPart().eqpos = XophpArray.count(accum) - 1;
				++i;
			}
		}

		// Output any remaining unclosed brackets
		XophpArray tempStack = stack.stack;
		int tempStackLen = tempStack.Len();
		for (int j = 0; j < tempStackLen; j++) {
			XomwPPDStackElement_Hash piece = (XomwPPDStackElement_Hash)tempStack.Get_at(j);
			XophpArray.array_splice(stack.rootAccum, XophpArray.count(stack.rootAccum), 0, piece.breakSyntax());
		}

		// Enable top-level headings
		XophpArray rootAccum = stack.rootAccum;
		int rootAccumLen = rootAccum.Len();
		for (int j = 0; j < rootAccumLen; j++) {
			XophpArray node = rootAccum.Get_at_ary_or_null(j); // stack.rootAccum as &node
			if (XophpArray.is_array(node) && StringUtl.Eq(node.Get_at_str(XomwPPNode_Hash_Tree.NAME), "possible-h")) {
				node.Set(XomwPPNode_Hash_Tree.NAME, "h");
			}
		}

		XophpArray rootStore = XophpArray.New(XophpArray.New("root", stack.rootAccum));
		XomwPPNode_Hash_Tree rootNode = new XomwPPNode_Hash_Tree(rootStore, 0);

		// Cache
//			tree = json_encode(rootStore, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
//			if (tree !== false) {
//				this.cacheSetTree(text, flags, tree);
//			}

		return rootNode;
	}

	private static void addLiteral(XophpArray accum, String text) {
		int n = XophpArray.count(accum);
		if (XophpInt_.is_true(n) && XophpType_.is_string(accum.Get_at(n - 1))) {
			accum.Concat_str(n - 1, text);
		} else {
			accum.Add(text);
		}
	}

	@Override public String preprocessToDbg(String src, boolean for_inclusion) {
		XomwPPNode_Hash_Tree node = (XomwPPNode_Hash_Tree)this.preprocessToObj(src, for_inclusion ? 1 : 0);
		return node.toString();
	}
}
