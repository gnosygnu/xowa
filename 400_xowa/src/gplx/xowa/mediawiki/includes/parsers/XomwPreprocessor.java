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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*;
import gplx.xowa.mediawiki.*;
import gplx.core.btries.*;
import gplx.xowa.mediawiki.includes.parsers.preprocessors.*;
/**
* @ingroup Parser
*/
public abstract class XomwPreprocessor {
	public abstract XomwParser Parser(); // XOWA: not in MW, but both Preprocessor_DOM and Preprocessor_Hash have the member variable

//		private static final int CACHE_VERSION = 1;

//		/**
//		* @var array Brace matching rules.
//		*/
//		protected $rules = [
//			'{' => [
//				'end' => '}',
//				'names' => [
//					2 => 'template',
//					3 => 'tplarg',
//				],
//				'min' => 2,
//				'max' => 3,
//			],
//			'[' => [
//				'end' => ']',
//				'names' => [ 2 => null ],
//				'min' => 2,
//				'max' => 2,
//			],
//			'-{' => [
//				'end' => '}-',
//				'names' => [ 1 => null ],
//				'min' => 1,
//				'max' => 1,
//			],
//		];
//
//		/**
//		* Store a document tree in the cache.
//		*
//		* @param String $text
//		* @param int $flags
//		*/
//		protected function cacheSetTree($text, $flags, $tree) {
//			$config = RequestContext::getMain()->getConfig();
//
//			$length = strlen($text);
//			$threshold = $config->get('PreprocessorCacheThreshold');
//			if ($threshold === false || $length < $threshold || $length > 1e6) {
//				return false;
//			}
//
//			$key = wfMemcKey(
//				defined('static::CACHE_PREFIX') ? static::CACHE_PREFIX : static::class,
//				md5($text), $flags);
//			$value = sprintf("%08d", static::CACHE_VERSION) . $tree;
//
//			$cache = ObjectCache::getInstance($config->get('MainCacheType'));
//			$cache->set($key, $value, 86400);
//
//			LoggerFactory::getInstance('Preprocessor')
//				->info("Cached preprocessor output (key: $key)");
//		}
//
//		/**
//		* Attempt to load a precomputed document tree for some given wikitext
//		* from the cache.
//		*
//		* @param String $text
//		* @param int $flags
//		* @return PPNode_Hash_Tree|boolean
//		*/
//		protected function cacheGetTree($text, $flags) {
//			$config = RequestContext::getMain()->getConfig();
//
//			$length = strlen($text);
//			$threshold = $config->get('PreprocessorCacheThreshold');
//			if ($threshold === false || $length < $threshold || $length > 1e6) {
//				return false;
//			}
//
//			$cache = ObjectCache::getInstance($config->get('MainCacheType'));
//
//			$key = wfMemcKey(
//				defined('static::CACHE_PREFIX') ? static::CACHE_PREFIX : static::class,
//				md5($text), $flags);
//
//			$value = $cache->get($key);
//			if (!$value) {
//				return false;
//			}
//
//			$version = intval(substr($value, 0, 8));
//			if ($version !== static::CACHE_VERSION) {
//				return false;
//			}
//
//			LoggerFactory::getInstance('Preprocessor')
//				->info("Loaded preprocessor output from cache (key: $key)");
//
//			return substr($value, 8);
//		}

	/**
	* Create a new top-level frame for expansion of a page
	*
	* @return PPFrame
	*/
	public abstract XomwPPFrame newFrame();

	/**
	* Create a new custom frame for programmatic use of parameter replacement
	* as used in some extensions.
	*
	* @param array $args
	*
	* @return PPFrame
	*/
	public abstract XomwPPFrame newCustomFrame(XomwPPFrame args);

//		/**
//		* Create a new custom node for programmatic use of parameter replacement
//		* as used in some extensions.
//		*
//		* @param array $values
//		*/
//		abstract public function newPartNodeArray($values);

	/**
	* Preprocess text to a PPNode
	*
	* @param String $text
	* @param int $flags
	*
	* @return PPNode
	*/
	public abstract XomwPPNode preprocessToObj(String text, int flags);

	private final List_adp comments_list = List_adp_.New();
	private final Btrie_slim_mgr elements_trie__y = Btrie_slim_mgr.ci_a7(), elements_trie__n = Btrie_slim_mgr.ci_a7();
	private final Hash_adp_bry xmlish_allow_missing_end_tag = Hash_adp_bry.cs().Add_many_str("includeonly", "noinclude", "onlyinclude");
	private final Hash_adp_bry no_more_closing_tag = Hash_adp_bry.cs();
	private final XomwPPDStack stack;
	private final Btrie_rv trv = new Btrie_rv();
	private Xomw_prepro_accum accum = null;

	public XomwPreprocessor() {
		this.stack = this.Factory__stack();
	}
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
		int ignored_tags_len = ignored_tags.Len();
		for (int j = 0; j < ignored_tags_len; j++) {
			byte[] bry = (byte[])ignored_tags.Get_at(j);
			String str = String_.new_u8(bry);
			Elements_trie__add(trie, Bool_.N, str, str);
		}
	}
	private static void Elements_trie__add(Btrie_slim_mgr trie, boolean type_is_comment, String hook, String name) {
		trie.Add_obj(hook, new Xomw_prepro_elem(type_is_comment ? Xomw_prepro_elem.Type__comment : Xomw_prepro_elem.Type__other, Bry_.new_a7(name)));
	}

	public abstract String preprocessToDbg(byte[] src, boolean for_inclusion);
	/**
	* @param String $text
	* @param int $flags
	* @return String
	*/
	protected Object preprocessToObj_base(byte[] src, boolean for_inclusion) {
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
		accum = this.Accum__set(stack.getAccum());
		this.preprocessToObj_root();

		// True to find equals signs in arguments
		boolean findEquals = false;

		// True to take notice of pipe characters
		boolean findPipe = false;
		int heading_index = 1;

		// True if $i is inside a possible heading
		boolean inHeading = false;

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
		String cur_closing = "";
		byte[] inner = null;
		Xomw_prepro_rule rule = null;

		while (true) {
			if (find_only_include) {
				// Ignore all input up to the next <onlyinclude>
				int start_pos = Bry_find_.Find_fwd(src, Bry__only_include_bgn, i, src_len);
				if (start_pos == Bry_find_.Not_found) {
					// Ignored section runs to the end
					this.preprocessToObj_ignore(src, i, src_len);
					break;
				}
				int tag_end_pos = start_pos + Bry__only_include_bgn.length; // past-the-end
				this.preprocessToObj_ignore(src, i, tag_end_pos);
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
					cur_closing = "";
				}
				else {
					cur_closing = stack.top.close;
					// RELIC.REGEX: $search .= $currentClosing;
				}
				if (findPipe) {
					// RELIC.REGEX: $search .= '|';
				}
				if (findEquals) {
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
						case Byte_ascii.Pipe:   // handle "findPipe"
							if (findPipe)   loop_stop = true;
							break;
						case Byte_ascii.Eq:     // handle "findEquals"
							if (findEquals) loop_stop = true;
							break;
						default:                // handle "cur_closing"; specified by piece.close and rule.close, so "\n", "}", "]" and "}-"
							if (String_.EqNot(cur_closing, "")) {
								byte cur_closing_0 = (byte)String_.CharAt(cur_closing, 0);
								if (b == cur_closing_0) {
									if (String_.Len(cur_closing) == 1) {	// handle "\n", "}", "]"
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
					this.preprocessToObj_literal(src, i, i + literal_len);
					i += literal_len;
				}
				if (i >= src_len) {
					if (String_.Eq(cur_closing, String_.Nl)) {
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
							case Byte_ascii.Nl:           found = inHeading ? Found__line_end : Found__line_bgn; break;

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
					this.preprocessToObj_literal(Byte_ascii.Lt_bry);
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
						this.preprocessToObj_comment(src, i, src_len);
						i = src_len;
					}
					else {
						// Search backwards for leading whitespace
						int ws_bgn = i > 0 ? i - XophpString_.strspn_bwd__space_or_tab(src, i, -1) : 0;

						// Search forwards for trailing whitespace
						// $wsEnd will be the position of the last space (or the '>' if there's none)
						int ws_end = end_pos + 2 + XophpString_.strspn_fwd__space_or_tab(src, end_pos + 3, -1, src_len);

						// Keep looking forward as long as we're finding more
						// comments.
						comments_list.Clear();
						comments_list.Add(new int[] {ws_bgn, ws_end});
						while (ws_end + 5 < src_len && Bry_.Eq(src, ws_end + 1, ws_end + 5, Bry__comment_bgn)) {
							int cur_char_pos = Bry_find_.Find_fwd(src, Bry__comment_end, ws_end + 4);
							if (cur_char_pos == Bry_find_.Not_found) {
								break;
							}
							cur_char_pos = cur_char_pos + 2 + XophpString_.strspn_fwd__space_or_tab(src, cur_char_pos + 3, -1, src_len);
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
							this.preprocessToObj_removeLeadingWhitespaceFromEnd(ws_len);

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
								this.preprocessToObj_comment(inner);
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
							XomwPPDPart part = stack.top.getCurrentPart();
							if (!(part.commentEnd != -1 && part.commentEnd == ws_bgn - 1)) {
								part.visualEnd = ws_bgn;
							}
							// Else comments abutting, no change in visual end
							part.commentEnd = end_pos;
						}
						i = end_pos + 1;
						inner = Bry_.Mid(src, bgn_pos, end_pos + 1);
						this.preprocessToObj_comment(inner);
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
					this.preprocessToObj_literal(Byte_ascii.Lt_bry);
					i++;
					continue;
				}

				// Handle ignored tags
				if (ignored_tags.Has(name)) {
					this.preprocessToObj_ignore(src, i, tag_end_pos + 1);
					i = tag_end_pos + 1;
					continue;
				}

				int tag_bgn_pos = i;
				int atr_end = -1;
				byte[] close = this.preprocessToObj_close_init();
				if (src[tag_end_pos - 1] == Byte_ascii.Slash) {
					atr_end = tag_end_pos - 1;
					inner = null;
					i = tag_end_pos + 1;
					close = this.preprocessToObj_close_init();
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
						close = this.preprocessToObj_close_make(src, elem_end_lhs, elem_end_rhs);
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
							this.preprocessToObj_literal(src, tag_bgn_pos, tag_end_pos + 1);
							// Cache results, otherwise we have O(N^2) performance for input like <foo><foo><foo>...
							no_more_closing_tag.AddIfDupeUseNth(name, name);
							continue;
						}
					}
				}

				// <includeonly> and <noinclude> just become <ignore> tags
				if (ignored_elements.Has(name)) {
					this.preprocessToObj_ignore(src, tag_bgn_pos, i);
					continue;
				}

				this.preprocessToObj_ext(src, name, atr_bgn, atr_end, inner, close);
			}
			else if (found == Found__line_bgn) {
				// Is this the start of a heading?
				// Line break belongs before the heading element in any case
				if (fake_line_start) {
					fake_line_start = false;
				} else {
					this.preprocessToObj_literal(cur_char);
					i++;
				}

				int count = XophpString_.strspn_fwd__byte(src, Byte_ascii.Eq, i, 6, src_len);
				if (count == 1 && findEquals) {	// EX: "{{a|\n=b=\n"
					// DWIM: This looks kind of like a name/value separator.
					// Let's let the equals handler have it and break the
					// potential heading. This is heuristic, but AFAICT the
					// methods for completely correct disambiguation are very
					// complex.
				}
				else if (count > 0) {
					XomwPPDStackElement piece = Factory__stack_element(Factory__part(), String_.Nl, String_.Nl, count, i, false);
					piece.addPart(XophpString_.str_repeat("=", count));
					stack.push(piece);
					accum = this.Accum__set(stack.getAccum());
					XomwPPDStackElementFlags flags = stack.getFlags();
					findPipe = flags.findPipe;
					findEquals = flags.findEquals;
					inHeading = flags.inHeading;
					i += count;
				}
			}
			else if (found == Found__line_end) {
				XomwPPDStackElement piece = stack.top;
				// A heading must be open, otherwise \n wouldn't have been in the search list
				if (!String_.Eq(piece.open, String_.Nl)) throw Err_.new_wo_type("assertion:piece must start with \\n");
				XomwPPDPart part = piece.getCurrentPart();

				// Search back through the input to see if it has a proper close.
				// Do this using the reversed String since the other solutions
				// (end anchor, etc.) are inefficient.
				int ws_len = XophpString_.strspn_bwd__space_or_tab(src, src_len - i, -1);
				int search_bgn = i - ws_len;

				if (part.commentEnd != -1 && search_bgn -1 == part.commentEnd) {
					// Comment found at line end
					// Search for equals signs before the comment
					search_bgn = part.visualEnd;
					search_bgn = Bry_find_.Find_bwd__while_space_or_tab(src, search_bgn, 0);
					search_bgn -= XophpString_.strspn_bwd__space_or_tab(src, search_bgn, -1);
				}
				int count = piece.count;
				int eq_len = XophpString_.strspn_bwd__byte(src, Byte_ascii.Eq, search_bgn, -1);

				Xomw_prepro_accum element = null;
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
						element = this.preprocessToObj_heading_init(count, heading_index);
						heading_index++;
					} else {
						// Single equals sign on its own line, count=0
						element = accum;
					}
				}
				else {
					// No match, no <h>, just pass down the inner src
					element = accum;
				}

				// Unwind the stack
				stack.pop();
				this.accum = this.Accum__set(stack.getAccum());
				
				XomwPPDStackElementFlags flags = stack.getFlags();
				findPipe = flags.findPipe;
				findEquals = flags.findEquals;
				inHeading = flags.inHeading;

				// Append the result to the enclosing accumulator
				this.preprocessToObj_heading_end(element);
				// Note that we do NOT increment the input pointer.
				// This is because the closing linebreak could be the opening linebreak of
				// another heading. Infinite loops are avoided because the next iteration MUST
				// hit the heading open case above, which unconditionally increments the
				// input pointer.
			}
			else if (found == Found__open) {
				// count opening brace characters
				int count = XophpString_.strspn_fwd__byte(src, cur_char[0], i, -1, src_len);	// NOTE: don't know how MediaWiki will handle "-{"

				// we need to add to stack only if opening brace count is enough for one of the rules
				if (count >= rule.min) {
					// Add it to the stack
					XomwPPDStackElement piece = Factory__stack_element(Factory__part(), String_.new_u8(cur_char), String_.new_u8(rule.end), count, -1, i > 0 && src[i - 1] == Byte_ascii.Nl);
					stack.push(piece);
					this.accum = this.Accum__set(stack.getAccum());
					XomwPPDStackElementFlags flags = stack.getFlags();
					findPipe = flags.findPipe;
					findEquals = flags.findEquals;
					inHeading = flags.inHeading;
				}
				else {
					// Add literal brace(s)
					for (int j = 0; j < count; j++)
						this.preprocessToObj_literal(cur_char);
				}
				i += count;
			}
			else if (found == Found__close) {
				XomwPPDStackElement piece = stack.top;
				// lets check if there are enough characters for closing brace
				int max_count = piece.count;
				int count = XophpString_.strspn_fwd__byte(src, cur_char[0], i, max_count, src_len);

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
						this.preprocessToObj_literal(cur_char);
					i += count;
					continue;
				}

				int name_type = rule.names[matching_count];
				Xomw_prepro_accum element = null;
				if (name_type == Xomw_prepro_rule.Name__null) {
					// No element, just literal text
					element = this.preprocessToObj_text(piece, rule.end, matching_count);
				}
				else {
					// Create XML element
					element = this.preprocessToObj_xml(piece, Xomw_prepro_rule.Name(name_type), max_count, matching_count);
				}

				// Advance input pointer
				i += matching_count;

				// Unwind the stack
				stack.pop();
				this.accum = this.Accum__set(stack.getAccum());

				// Re-add the old stack element if it still has unmatched opening characters remaining
				if (matching_count < piece.count) {
					piece.Parts__renew(); // PORTED: piece.parts = [ new PPDPart ];
					piece.count -= matching_count;

					// do we still qualify for any callback with remaining count?
					int min = Get_rule(piece.open).min;
					if (piece.count >= min) {
						stack.push(piece);
						this.accum = this.Accum__set(stack.getAccum());
					}
					else {
						this.preprocessToObj_literal(Bry_.new_u8(XophpString_.str_repeat(piece.open, piece.count)));
					}
				}

				XomwPPDStackElementFlags flags = stack.getFlags();
				findPipe = flags.findPipe;
				findEquals = flags.findEquals;
				inHeading = flags.inHeading;

				// Add XML element to the enclosing accumulator
				this.preprocessToObj_add_element(element);
			}
			else if (found == Found__pipe) {
				findEquals = true; // shortcut for getFlags()
				stack.addPart("");
				this.accum = this.Accum__set(stack.getAccum());
				i++;
			}
			else if (found == Found__equals) {
				findEquals = false; // shortcut for getFlags()
				this.preprocessToObj_equals(stack);
				i++;
			}
		}

		// Output any remaining unclosed brackets
		return this.preprocessToObj_term(stack);
	}

	private Xomw_prepro_rule Get_rule(String str) {return Get_rule(Bry_.new_u8(str));}
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
	private static final Xomw_prepro_rule
	  rule_curly = new Xomw_prepro_rule(Bry_.new_a7("{"), Bry_.new_a7("}")  , 2, 3, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__tmpl, Xomw_prepro_rule.Name__targ})
	, rule_brack = new Xomw_prepro_rule(Bry_.new_a7("["), Bry_.new_a7("]")  , 2, 2, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__null})
	, rule_langv = new Xomw_prepro_rule(Bry_.new_a7("-{"), Bry_.new_a7("}-"), 1, 1, new int[] {Xomw_prepro_rule.Name__invalid, Xomw_prepro_rule.Name__null})
	;
	private static final byte[]
	  Bry__only_include_bgn = Bry_.new_a7("<onlyinclude>")
	, Bry__only_include_end = Bry_.new_a7("</onlyinclude>")
	, Bry__comment_bgn  = Bry_.new_a7("<!--")
	, Bry__comment_end  = Bry_.new_a7("-->")
	, Bry__end_lhs      = Bry_.new_a7("</")
	;
	private static final int Len__only_include_end = Bry__only_include_end.length;
	private static final Btrie_slim_mgr cur_char_trie = Cur_char_trie__new();
	private static final Ordered_hash
	  ignored_tags_y     = Ordered_hash_.New_bry().Add_many_str("includeonly", "/includeonly")
	, ignored_tags_n     = Ordered_hash_.New_bry().Add_many_str("noinclude", "/noinclude", "onlyinclude", "/onlyinclude");
	private static final Hash_adp_bry
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

	protected abstract XomwPPDPart Factory__part();
	protected abstract XomwPPDStack Factory__stack();
	protected abstract XomwPPDStackElement Factory__stack_element(XomwPPDPart part_factory, String open, String close, int count, int start_pos, boolean lineStart);

	protected abstract Xomw_prepro_accum Accum__set(Xomw_prepro_accum accum);

	protected abstract void preprocessToObj_root();
	protected abstract void preprocessToObj_ignore(byte[] src, int bgn, int end);
	protected abstract void preprocessToObj_literal(byte[] src, int bgn, int end);
	protected void preprocessToObj_literal(byte[] src) {this.preprocessToObj_literal(src, 0, src.length);}
	protected abstract void preprocessToObj_comment(byte[] src, int bgn, int end);
	protected void preprocessToObj_comment(byte[] src) {this.preprocessToObj_comment(src, 0, src.length);}
	protected abstract byte[] preprocessToObj_close_init();
	protected abstract byte[] preprocessToObj_close_make(byte[] src, int bgn, int end);
	protected abstract void preprocessToObj_ext(byte[] src, byte[] name, int atr_bgn, int atr_end, byte[] inner, byte[] close);
	protected abstract Xomw_prepro_accum preprocessToObj_heading_init(int count, int heading_index);
	protected abstract void preprocessToObj_heading_end(Xomw_prepro_accum element);
	protected abstract void preprocessToObj_removeLeadingWhitespaceFromEnd(int ws_len);
	protected abstract Xomw_prepro_accum preprocessToObj_text(XomwPPDStackElement piece, byte[] rule_end, int matching_count);
	protected abstract Xomw_prepro_accum preprocessToObj_xml(XomwPPDStackElement piece, byte[] name_bry, int max_count, int matching_count);
	protected abstract void preprocessToObj_add_element(Xomw_prepro_accum element);
	protected abstract void preprocessToObj_equals(XomwPPDStack stack);
	protected abstract Object preprocessToObj_term(XomwPPDStack stack);
	public abstract XomwPreprocessor Make_new(XomwParser parser);
}
