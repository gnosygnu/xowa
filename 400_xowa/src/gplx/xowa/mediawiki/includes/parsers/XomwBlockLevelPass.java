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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.core.btries.Btrie_rv;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.langs.htmls.Gfh_tag_;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.types.errs.ErrUtl;
import gplx.xowa.mediawiki.XophpPreg_;
import gplx.xowa.mediawiki.XophpString_;
/**
* This is the part of the wikitext parser which handles automatic paragraphs
* and conversion of start-of-line prefixes to HTML lists.
*/
public class XomwBlockLevelPass {
	private boolean DTopen = false;
	private boolean inPre = false;
	private int lastSection = LAST_SECTION_NONE;
	private boolean linestart;
//		private $text;
	private final BryWtr tmp = BryWtr.New();
	private final Btrie_rv trv = new Btrie_rv();
	private byte[] find_colon_no_links__before, find_colon_no_links__after;

	// State constants for the definition list colon extraction
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

	/**
	* Make lists from lines starting with ':', '*', '#', etc.
	*
	* @param String $text
	* @param boolean $linestart Whether or not this is at the start of a line.
	* @return String The lists rendered as HTML
	*/
//		public static function doBlockLevels($text, $linestart) {
//			$pass = new self($text, $linestart);
//			return $pass->execute();
//		}
	public void doBlockLevels(XomwParserCtx pctx, XomwParserBfr pbfr, boolean linestart) {
		this.linestart = linestart;
		execute(pctx, pbfr, linestart);
	}

//		/**
//		* Private constructor
//		*/
//		private function __construct($text, $linestart) {
//			$this->text = $text;
//			$this->linestart = $linestart;
//		}

	/**
	* If a pre or p is open, return the corresponding close tag and update
	* the state. If no tag is open, return an empty String.
	* @return String
	*/
	private byte[] closeParagraph() {
		byte[] result = BryUtl.Empty;
		if (this.lastSection != LAST_SECTION_NONE) {
			result = tmp.Add(lastSection == LAST_SECTION_PARA ? Gfh_tag_.P_rhs : Gfh_tag_.Pre_rhs).AddByteNl().ToBryAndClear(); // $result = '</' . $this->lastSection . ">\n";
		}
		this.inPre = false;
		this.lastSection = LAST_SECTION_NONE;
		return result;
	}

	/**
	* getCommon() returns the length of the longest common substring
	* of both arguments, starting at the beginning of both.
	*
	* @param String $st1
	* @param String $st2
	*
	* @return int
	*/
	// getCommon() returns the length of the longest common substring
	// of both arguments, starting at the beginning of both.
	private int getCommon(byte[] st1, byte[] st2) {
		int st1Len = st1.length, st2Len = st2.length;
		int shorter = st1Len < st2Len ? st1Len : st2Len;

		int i;
		for (i = 0; i < shorter; ++i) {
			if (st1[i] != st2[i]) {
				break;
			}
		}
		return i;
	}

	/**
	* Open the list item element identified by the prefix character.
	*
	* @param String $char
	*
	* @return String
	*/
	private byte[] openList(byte c) {
		byte[] result = this.closeParagraph();

		if      (c == AsciiByte.Star)
			result = BryUtl.Add(result, BryUtl.NewA7("<ul><li>"));
		else if (c == AsciiByte.Hash)
			result = BryUtl.Add(result, BryUtl.NewA7("<ol><li>"));
		else if (c == AsciiByte.Colon)
			result = BryUtl.Add(result, BryUtl.NewA7("<dl><dd>"));
		else if (c == AsciiByte.Semic) {
			result = BryUtl.Add(result, BryUtl.NewA7("<dl><dt>"));
			this.DTopen = true;
		}
		else {
			result = BryUtl.NewA7("<!-- ERR 1 -->");
		}

		return result;
	}

	/**
	* Close the current list item and open the next one.
	* @param String $char
	*
	* @return String
	*/
	private byte[] nextItem(byte c) {
		if (c == AsciiByte.Star || c == AsciiByte.Hash) {
			return BryUtl.NewA7("</li>\n<li>");
		}
		else if (c == AsciiByte.Colon || c == AsciiByte.Semic) {
			byte[] close = BryUtl.NewA7("</dd>\n");
			if (this.DTopen) {
				close = BryUtl.NewA7("</dt>\n");
			}
			if (c == AsciiByte.Semic) {
				this.DTopen = true;
				return BryUtl.Add(close, BryUtl.NewA7("<dt>"));
			}
			else {
				this.DTopen = false;
				return BryUtl.Add(close, BryUtl.NewA7("<dd>"));
			}
		}
		return BryUtl.NewA7("<!-- ERR 2 -->");
	}

	/**
	* Close the current list item identified by the prefix character.
	* @param String $char
	*
	* @return String
	*/
	private byte[] closeList(byte c) {
		byte[] text = null;
		if (c == AsciiByte.Star) {
			text = BryUtl.NewA7("</li></ul>");
		}
		else if (c == AsciiByte.Hash) {
			text = BryUtl.NewA7("</li></ol>");
		}
		else if (c == AsciiByte.Colon) {
			if (this.DTopen) {
				this.DTopen = false;
				text = BryUtl.NewA7("</dt></dl>");
			}
			else {
				text = BryUtl.NewA7("</dd></dl>");
			}
		}
		else {
			return BryUtl.NewA7("<!-- ERR 3 -->");
		}
		return text;
	}

	/**
	* Execute the pass.
	* @return String
	*/
	public void execute(XomwParserCtx pctx, XomwParserBfr pbfr, boolean linestart) {
		// XO.PBFR
		BryWtr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bry();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		BryWtr bfr = pbfr.Trg();
		pbfr.Switch();

		// XO.STATIC
		if (block_chars_ary == null) {
			synchronized (ClassUtl.TypeByObj(this)) {
				block_chars_ary = Block_chars_ary__new();
				openMatchTrie = Btrie_slim_mgr.ci_a7().Add_many_str
				( "<table", "<h1", "<h2", "<h3", "<h4", "<h5", "<h6", "<pre", "<tr"
				, "<p", "<ul", "<ol", "<dl", "<li", "</tr", "</td", "</th");
				closeMatchTrie = Btrie_slim_mgr.ci_a7().Add_many_str
				( "</table", "</h1", "</h2", "</h3", "</h4", "</h5", "</h6"
				, "<td", "<th", "<blockquote", "</blockquote", "<div", "</div", "<hr", "</pre", "</p", "</mw:"
				, XomwParser.MARKER_PREFIX_STR + "-pre"
				, "</li", "</ul", "</ol", "</dl", "<center", "</center");
				blockquoteTrie = Btrie_slim_mgr.ci_a7().Add_many_str("<blockquote", "</blockquote");
				pre_trie = Btrie_slim_mgr.ci_a7().Add_str_int("<pre", PRE_BGN).Add_str_int("</pre", PRE_END);
			}
		}

		// clear state
		this.inPre = false;
		this.lastSection = LAST_SECTION_NONE;
		byte[] prefix2 = null;
		bfr.Clear();

		// Parsing through the text line by line.  The main thing
		// happening here is handling of block-level elements p, pre,
		// and making lists from lines starting with * # : etc.
		byte[] lastPrefix = BryUtl.Empty;
		this.DTopen = false;
		boolean inBlockElem = false;
		int prefixLen = 0;
		byte pendingPTag = PARA_STACK_NONE;
		boolean inBlockquote = false;

		// PORTED.SPLIT: $textLines = StringUtils::explode("\n", $text);
		int lineBgn = src_bgn;
		while (lineBgn < src_end) {
			int lineEnd = BryFind.FindFwd(src, AsciiByte.Nl, lineBgn);
			if (lineEnd == BryFind.NotFound)
				lineEnd = src_end;

			// Fix up linestart
			if (!this.linestart) {
				bfr.AddMid(src, lineBgn, lineEnd);
				this.linestart = true;
				continue;
			}
			// * = ul
			// # = ol
			// ; = dt
			// : = dd
			int lastPrefixLen = lastPrefix.length;

			// PORTED.BGN: preCloseMatch = preg_match('/<\\/pre/i', $oLine); preOpenMatch = preg_match('/<pre/i', $oLine);
			int preCur = lineBgn;
			boolean preCloseMatch = false;
			boolean preOpenMatch = false;
			while (true) {
				if (preCur >= lineEnd)
					break;
				Object o = pre_trie.MatchAt(trv, src, preCur, lineEnd);
				if (o == null)
					preCur++;
				else {
					int pre_tid = IntUtl.Cast(o);
					if (pre_tid == PRE_BGN)
						preOpenMatch = true;
					else if (pre_tid == PRE_END)
						preCloseMatch = true;
					preCur = trv.Pos();
				}
			}
			// PORTED.END

			byte[] prefix = null, t = null;
			// If not in a <pre> element, scan for and figure out what prefixes are there.
			if (!this.inPre) {
				// Multiple prefixes may abut each other for nested lists.
				prefixLen = XophpString_.strspn_fwd__ary(src, block_chars_ary, lineBgn, lineEnd, lineEnd); // strspn($oLine, '*#:;');
				prefix = XophpString_.substr(src, lineBgn, prefixLen);

				// eh?
				// ; and : are both from definition-lists, so they're equivalent
				//  for the purposes of determining whether or not we need to open/close
				//  elements.
				// substr($inputLine, $prefixLength);
				prefix2 = BryUtl.Replace(prefix, AsciiByte.Semic, AsciiByte.Colon);
				t = BryLni.Mid(src, lineBgn + prefixLen, lineEnd);
				this.inPre = preOpenMatch;
			}
			else {
				// Don't interpret any other prefixes in preformatted text
				prefixLen = 0;
				prefix = prefix2 = BryUtl.Empty;
				t = BryLni.Mid(src, lineBgn, lineEnd);
			}

			// List generation
			byte[] term = null, t2 = null;
			int commonPrefixLen = -1;
			if (prefixLen > 0 && BryLni.Eq(lastPrefix, prefix2)) {
				// Same as the last item, so no need to deal with nesting or opening stuff
				bfr.Add(this.nextItem(XophpString_.substr_byte(prefix, -1)));
				pendingPTag = PARA_STACK_NONE;

				if (prefixLen > 0 && prefix[prefixLen - 1] == AsciiByte.Semic) {
					// The one nasty exception: definition lists work like this:
					// ; title : definition text
					// So we check for : in the remainder text to split up the
					// title and definition, without b0rking links.
					term = t2 = BryUtl.Empty;
					if (this.findColonNoLinks(t, term, t2) != BryFind.NotFound) {
						term = find_colon_no_links__before;
						t2   = find_colon_no_links__after;
						t = t2;
						bfr.Add(term).Add(nextItem(AsciiByte.Colon));
					}
				}
			}
			else if (prefixLen > 0 || lastPrefixLen > 0) {
				// We need to open or close prefixes, or both.

				// Either open or close a level...
				commonPrefixLen = this.getCommon(prefix, lastPrefix);
				pendingPTag = PARA_STACK_NONE;

				// Close all the prefixes which aren't shared.
				while (commonPrefixLen < lastPrefixLen) {
					bfr.Add(this.closeList(lastPrefix[lastPrefixLen - 1]));
					--lastPrefixLen;
				}

				// Continue the current prefix if appropriate.
				if (prefixLen <= commonPrefixLen && commonPrefixLen > 0) {
					bfr.Add(this.nextItem(prefix[commonPrefixLen - 1]));
				}

				// Open prefixes where appropriate.
				if (BryUtl.IsNotNullOrEmpty(lastPrefix) && prefixLen > commonPrefixLen) {
					bfr.AddByteNl();
				}
				while (prefixLen > commonPrefixLen) {
					byte c = XophpString_.substr_byte(prefix, commonPrefixLen, 1);
					bfr.Add(this.openList(c));

					if (c == AsciiByte.Semic) {
						// @todo FIXME: This is dupe of code above
						if (findColonNoLinks(t, term, t2) != BryFind.NotFound) {
							term = find_colon_no_links__before;
							t2   = find_colon_no_links__after;
							t = t2;
							bfr.Add(term).Add(nextItem(AsciiByte.Colon));
						}
					}
					++commonPrefixLen;
				}
				if (prefixLen == 0 && BryUtl.IsNotNullOrEmpty(lastPrefix)) {
					bfr.AddByteNl();
				}
				lastPrefix = prefix2;
			}

			// If we have no prefixes, go to paragraph mode.
			if (0 == prefixLen) {
				// No prefix (not in list)--go to paragraph mode
				// @todo consider using a stack for nestable elements like span, table and div
				int tLen = t.length;

				// XO.MW.PORTED.BGN:
				boolean openMatch = XophpPreg_.match(openMatchTrie, trv, t, 0, tLen) != null;
				boolean closeMatch = XophpPreg_.match(closeMatchTrie, trv, t, 0, tLen) != null;
				// XO.MW.PORTED.END
				if (openMatch || closeMatch) {
					pendingPTag = PARA_STACK_NONE;
					// @todo bug 5718: paragraph closed
					bfr.Add(this.closeParagraph());
					if (preOpenMatch && !preCloseMatch) {
						this.inPre = true;
					}
					int bqOffset = 0;
					// PORTED:preg_match('/<(\\/?)blockquote[\s>]/i', t, $bqMatch, PREG_OFFSET_CAPTURE, $bqOffset)
					while (true) {
						Object o = XophpPreg_.match(blockquoteTrie, trv, t, bqOffset, tLen);
						if (o == null) { // no more blockquotes found; exit
							break;
						}
						else {
							byte[] bq_bry = (byte[])o;
							inBlockquote = bq_bry[1] != AsciiByte.Slash; // is this a close tag?
							bqOffset = trv.Pos();
						}
					}
					// PORTED:END
					inBlockElem = !closeMatch;
				}
				else if (!inBlockElem && !this.inPre) {
					if (XophpString_.substr_byte(t, 0) == AsciiByte.Space
						&& (this.lastSection == LAST_SECTION_PRE || BryUtl.Trim(t) != BryUtl.Empty)
						&& !inBlockquote
					) {
						// pre
						if (this.lastSection != LAST_SECTION_PRE) {
							pendingPTag = PARA_STACK_NONE;
							bfr.Add(closeParagraph()).Add(Gfh_tag_.Pre_lhs);
							this.lastSection = LAST_SECTION_PRE;
						}
						t = BryLni.Mid(t, 1);
					}
					else {
						// paragraph
						if (BryUtl.Trim(t) == BryUtl.Empty) {
							if (pendingPTag != PARA_STACK_NONE) {
								ParaStackAdd(bfr, pendingPTag);
								bfr.AddStrA7("<br />");
								pendingPTag = PARA_STACK_NONE;
								this.lastSection = LAST_SECTION_PARA;
							}
							else {
								if (this.lastSection != LAST_SECTION_PARA) {
									bfr.Add(this.closeParagraph());
									this.lastSection = LAST_SECTION_NONE;
									pendingPTag = PARA_STACK_BGN;
								}
								else {
									pendingPTag = PARA_STACK_MID;
								}
							}
						}
						else {
							if (pendingPTag != PARA_STACK_NONE) {
								ParaStackAdd(bfr, pendingPTag);
								pendingPTag = PARA_STACK_NONE;
								this.lastSection = LAST_SECTION_PARA;
							}
							else if (lastSection != LAST_SECTION_PARA) {
								bfr.Add(this.closeParagraph()).Add(Gfh_tag_.P_lhs);
								this.lastSection = LAST_SECTION_PARA;
							}
						}
					}
				}
			}
			// somewhere above we forget to get out of pre block (bug 785)
			if (preCloseMatch && this.inPre) {
				this.inPre = false;
			}
			if (pendingPTag == PARA_STACK_NONE) {
				bfr.Add(t);
				if (prefixLen == 0) {
					bfr.AddByteNl();
				}
			}

			lineBgn = lineEnd + 1;
		}

		while (prefixLen > 0) {
			bfr.Add(this.closeList(prefix2[prefixLen - 1]));
			--prefixLen;
			if (prefixLen > 0) {
				bfr.AddByteNl();
			}
		}
		if (this.lastSection != LAST_SECTION_NONE) {
			bfr.Add(this.lastSection == LAST_SECTION_PARA ? Gfh_tag_.P_rhs : Gfh_tag_.Pre_rhs);
			this.lastSection = LAST_SECTION_NONE;
		}
	}

	/**
	* Split up a String on ':', ignoring any occurrences inside tags
	* to prevent illegal overlapping.
	*
	* @param String $str The String to split
	* @param String &$before Set to everything before the ':'
	* @param String &$after Set to everything after the ':'
	* @throws MWException
	* @return String The position of the ':', or false if none found
	*/
	private int findColonNoLinks(byte[] str, byte[] before, byte[] after) {
		int len = str.length;
		int colonPos = XophpString_.strpos(str, AsciiByte.Colon, 0, len);
		if (colonPos == BryFind.NotFound) {
			// Nothing to find!
			return BryFind.NotFound;
		}

		int ltPos = XophpString_.strpos(str, AsciiByte.AngleBgn, 0, len);
		if (ltPos == BryFind.NotFound || ltPos > colonPos) {
			// Easy; no tag nesting to worry about
			// XOMW: MW passes before / after by reference; XO: changes member and depends on callers to update
			find_colon_no_links__before = XophpString_.substr(str, 0, colonPos);
			find_colon_no_links__after = XophpString_.substr(str, colonPos + 1);
			return colonPos;
		}

		// Ugly state machine to walk through avoiding tags.
		int state = COLON_STATE_TEXT;
		int level = 0;
		for (int i = 0; i < len; i++) {
			byte c = str[i];

			switch (state) {
				case COLON_STATE_TEXT:
					switch (c) {
						case AsciiByte.AngleBgn:
							// Could be either a <start> tag or an </end> tag
							state = COLON_STATE_TAGSTART;
							break;
						case AsciiByte.Colon:
							if (level == 0) {
								// We found it!
								find_colon_no_links__before = XophpString_.substr(str, 0, i);
								find_colon_no_links__after = XophpString_.substr(str, i + 1);
								return i;
							}
							// Embedded in a tag; don't break it.
							break;
						default:
							// Skip ahead looking for something interesting
							colonPos = XophpString_.strpos(str, AsciiByte.Colon, i, len);
							if (colonPos == BryFind.NotFound) {
								// Nothing else interesting
								return BryFind.NotFound;
							}
							ltPos = XophpString_.strpos(str, AsciiByte.AngleBgn, i, len);
							if (level == 0) {
								if (ltPos == BryFind.NotFound || colonPos < ltPos) {
									// We found it!
									find_colon_no_links__before = XophpString_.substr(str, 0, colonPos);
									find_colon_no_links__after = XophpString_.substr(str, colonPos + 1);
									return i;
								}
							}
							if (ltPos == BryFind.NotFound) {
								// Nothing else interesting to find; abort!
								// We're nested, but there's no close tags left. Abort!
								i = len;	// break 2
								break;
							}
							// Skip ahead to next tag start
							i = ltPos;
							state = COLON_STATE_TAGSTART;
							break;
						}
					break;
				case COLON_STATE_TAG:
					// In a <tag>
					switch (c) {
						case AsciiByte.AngleEnd:
							level++;
							state = COLON_STATE_TEXT;
							break;
						case AsciiByte.Slash:
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
						case AsciiByte.Slash:
							state = COLON_STATE_CLOSETAG;
							break;
						case AsciiByte.Bang:
							state = COLON_STATE_COMMENT;
							break;
						case AsciiByte.AngleEnd:
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
					if (c == AsciiByte.AngleEnd) {
						level--;
						if (level < 0) {
							Gfo_usr_dlg_.Instance.Warn_many("", "", "Invalid input; too many close tags");
							return BryFind.NotFound;
						}
						state = COLON_STATE_TEXT;
					}
					break;
				case COLON_STATE_TAGSLASH:
					if (c == AsciiByte.AngleEnd) {
						// Yes, a self-closed tag <blah/>
						state = COLON_STATE_TEXT;
					}
					else {
						// Probably we're jumping the gun, and this is an attribute
						state = COLON_STATE_TAG;
					}
					break;
				case COLON_STATE_COMMENT:
					if (c == AsciiByte.Dash) {
						state = COLON_STATE_COMMENTDASH;
					}
					break;
				case COLON_STATE_COMMENTDASH:
					if (c == AsciiByte.Dash) {
						state = COLON_STATE_COMMENTDASHDASH;
					}
					else {
						state = COLON_STATE_COMMENT;
					}
					break;
				case COLON_STATE_COMMENTDASHDASH:
					if (c == AsciiByte.AngleBgn) {
						state = COLON_STATE_TEXT;
					}
					else {
						state = COLON_STATE_COMMENT;
					}
					break;
			default:
				throw ErrUtl.NewArgs("State machine error");
			}
		}
		if (level > 0) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "Invalid input; not enough close tags (level ~{0}, state ~{1})", level, state);
			return BryFind.NotFound;
		}
		return BryFind.NotFound;
	}

	private static final byte
	  LAST_SECTION_NONE = 0    // ''
	, LAST_SECTION_PARA = 1    // p
	, LAST_SECTION_PRE  = 2    // pre
	;
	private static final byte
	  PARA_STACK_NONE = 0	// false
	, PARA_STACK_BGN  = 1	// <p>
	, PARA_STACK_MID  = 2	// </p><p>
	;
	private static final int PRE_BGN = 0, PRE_END = 1;
	private static Btrie_slim_mgr pre_trie;
	private static boolean[] block_chars_ary; 
	private static boolean[] Block_chars_ary__new() {
		boolean[] rv = new boolean[256];
		rv[AsciiByte.Star] = true;
		rv[AsciiByte.Hash] = true;
		rv[AsciiByte.Colon] = true;
		rv[AsciiByte.Semic] = true;
		return rv;
	}
	private static Btrie_slim_mgr openMatchTrie, closeMatchTrie, blockquoteTrie;
	private static void ParaStackAdd(BryWtr bfr, int id) {
		switch (id) {
			case PARA_STACK_BGN: bfr.AddStrA7("<p>"); break;
			case PARA_STACK_MID: bfr.AddStrA7("</p><p>"); break;
			default:              throw ErrUtl.NewUnhandled(id);
		}
	}
}
