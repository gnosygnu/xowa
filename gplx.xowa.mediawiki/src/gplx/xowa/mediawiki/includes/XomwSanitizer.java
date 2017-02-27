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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.encoders.*; import gplx.core.primitives.*; import gplx.langs.htmls.entitys.*;
import gplx.xowa.parsers.htmls.*;
import gplx.langs.htmls.*; import gplx.xowa.mediawiki.includes.xohtml.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.xowa.mediawiki.includes.libs.*;
public class XomwSanitizer {
	private final    Mwh_doc_wkr__atr_bldr atr_bldr = new Mwh_doc_wkr__atr_bldr();
	private final    Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	private final    Xomw_regex_escape_invalid regex_clean_url = new Xomw_regex_escape_invalid();
	private final    Xomw_regex_find_domain regex_find_domain = new Xomw_regex_find_domain();
	private final    Xomw_regex_ipv6_brack regex_ipv6_brack = new Xomw_regex_ipv6_brack();
	private final    Bry_tmp tmp_host = new Bry_tmp();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Bry_bfr tmp_bfr_2 = Bry_bfr_.New();
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Xomw_regex_url_char_cbk__normalize normalize_cbk = new Xomw_regex_url_char_cbk__normalize();
	private final    Xomw_regex_url_char_cbk__decode decode_cbk = new Xomw_regex_url_char_cbk__decode();

	private static Xomw_regex_url_char regex_url_char;
	private static Btrie_slim_mgr invalid_idn_trie;
	public XomwSanitizer() {
		if (regex_url_char == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				regex_url_char = new Xomw_regex_url_char();

				// Characters that will be ignored in IDNs.
				// https://tools.ietf.org/html/rfc3454#section-3.1
				// $strip = "/
				//	 \\s|          // general whitespace
				//	 \xc2\xad|     // 00ad SOFT HYPHEN
				//	 \xe1\xa0\x86| // 1806 MONGOLIAN TODO SOFT HYPHEN
				//	 \xe2\x80\x8b| // 200b ZERO WIDTH SPACE
				//	 \xe2\x81\xa0| // 2060 WORD JOINER
				//	 \xef\xbb\xbf| // feff ZERO WIDTH NO-BREAK SPACE
				//	 \xcd\x8f|     // 034f COMBINING GRAPHEME JOINER
				//	 \xe1\xa0\x8b| // 180b MONGOLIAN FREE VARIATION SELECTOR ONE
				//	 \xe1\xa0\x8c| // 180c MONGOLIAN FREE VARIATION SELECTOR TWO
				//	 \xe1\xa0\x8d| // 180d MONGOLIAN FREE VARIATION SELECTOR THREE
				//	 \xe2\x80\x8c| // 200c ZERO WIDTH NON-JOINER
				//	 \xe2\x80\x8d| // 200d ZERO WIDTH JOINER
				//	 [\xef\xb8\x80-\xef\xb8\x8f] // fe00-fe0f VARIATION SELECTOR-1-16
				//	 /xuD";
				// XO.MW.REGEX:http://php.net/manual/en/reference.pcre.pattern.modifiers.php
				//   /x : ignore embedded ws
				//   /u : enabled pcre utf8
				//   /D : $ matches EOS, not NL
				invalid_idn_trie = Btrie_slim_mgr.cs()
				.Add_many_bry(new Xomw_regex_parser().Add_ary
				("\\s"
				, "\\xc2\\xad"      // 00ad SOFT HYPHEN
				, "\\xe1\\xa0\\x86" // 1806 MONGOLIAN TODO SOFT HYPHEN
				, "\\xe2\\x80\\x8b" // 200b ZERO WIDTH SPACE
				, "\\xe2\\x81\\xa0" // 2060 WORD JOINER
				, "\\xef\\xbb\\xbf" // feff ZERO WIDTH NO-BREAK SPACE
				, "\\xcd\\x8f"      // 034f COMBINING GRAPHEME JOINER
				, "\\xe1\\xa0\\x8b" // 180b MONGOLIAN FREE VARIATION SELECTOR ONE
				, "\\xe1\\xa0\\x8c" // 180c MONGOLIAN FREE VARIATION SELECTOR TWO
				, "\\xe1\\xa0\\x8d" // 180d MONGOLIAN FREE VARIATION SELECTOR THREE
				, "\\xe2\\x80\\x8c" // 200c ZERO WIDTH NON-JOINER
				, "\\xe2\\x80\\x8d" // 200d ZERO WIDTH JOINER
				)
				.Add_rng
				("\\xef\\xb8\\x80", "\\xef\\xb8\\x8f" // fe00-fe0f VARIATION SELECTOR-1-16
				)
				.Rslt());

				// assert static structs
				if (html_entities == null) {
					synchronized (Type_adp_.ClassOf_obj(this)) {
						html_entities = Html_entities_new();
					}
				}
			}
		}
	}

	/**
	* Regular expression to match various types of character references in
	* Sanitizer::normalizeCharReferences and Sanitizer::decodeCharReferences
	*/
	// XO.MW.MOVED:Xomw_regex_url_char
	//	static final CHAR_REFS_REGEX =
	//		'/&([A-Za-z0-9\x80-\xff]+);
	//		|&\#([0-9]+);
	//		|&\#[xX]([0-9A-Fa-f]+);
	//		|(&)/x';

//		/**
//		* Acceptable tag name charset from HTML5 parsing spec
//		* https://www.w3.org/TR/html5/syntax.html#tag-open-state
//		*/
//		static final ELEMENT_BITS_REGEX = '!^(/?)([A-Za-z][^\t\n\v />\0]*+)([^>]*?)(/?>)([^<]*)$!';
//
//		/**
//		* Blacklist for evil uris like javascript:
//		* WARNING: DO NOT use this in any place that actually requires blacklisting
//		* for security reasons. There are NUMEROUS[1] ways to bypass blacklisting, the
//		* only way to be secure from javascript: uri based xss vectors is to whitelist
//		* things that you know are safe and deny everything else.
//		* [1]: http://ha.ckers.org/xss.html
//		*/
//		static final EVIL_URI_PATTERN = '!(^|\s|\*/\s*)(javascript|vbscript)([^\w]|$)!i';
//		static final XMLNS_ATTRIBUTE_PATTERN = "/^xmlns:[:A-Z_a-z-.0-9]+$/";

	/**
	* List of all named character entities defined in HTML 4.01
	* https://www.w3.org/TR/html4/sgml/entities.html
	* As well as &apos; which is only defined starting in XHTML1.
	*/
	// XO.MW.MOVED:Html_entities_new
	// private static $htmlEntities = []

	/**
	* Character entity aliases accepted by MediaWiki
	*/
	// XO.MW.MOVED:Html_entities_new
	// private static $htmlEntityAliases = []

//		/**
//		* Lazy-initialised attributes regex, see getAttribsRegex()
//		*/
//		private static $attribsRegex;
//
//		/**
//		* Regular expression to match HTML/XML attribute pairs within a tag.
//		* Allows some... latitude. Based on,
//		* https://www.w3.org/TR/html5/syntax.html#before-attribute-value-state
//		* Used in Sanitizer::fixTagAttributes and Sanitizer::decodeTagAttributes
//		* @return String
//		*/
//		static function getAttribsRegex() {
//			if (self::$attribsRegex === null) {
//				$attribFirst = '[:A-Z_a-z0-9]';
//				$attrib = '[:A-Z_a-z-.0-9]';
//				$space = '[\x09\x0a\x0c\x0d\x20]';
//				self::$attribsRegex =
//					"/(?:^|$space)({$attribFirst}{$attrib}*)
//					($space*=$space*
//						(?:
//						# The attribute value: quoted or alone
//						\"([^\"]*)(?:\"|\$)
//						| '([^']*)(?:'|\$)
//						|  (((?!$space|>).)*)
//						)
//					)?(?=$space|\$)/sx";
//			}
//			return self::$attribsRegex;
//		}
//
//		/**
//		* Return the various lists of recognized tags
//		* @param array $extratags For any extra tags to include
//		* @param array $removetags For any tags (default or extra) to exclude
//		* @return array
//		*/
//		public static function getRecognizedTagData($extratags = [], $removetags = []) {
//			global $wgAllowImageTag;
//
//			static $htmlpairsStatic, $htmlsingle, $htmlsingleonly, $htmlnest, $tabletags,
//				$htmllist, $listtags, $htmlsingleallowed, $htmlelementsStatic, $staticInitialised;
//
//			// Base our staticInitialised variable off of the global config state so that if the globals
//			// are changed (like in the screwed up test system) we will re-initialise the settings.
//			$globalContext = $wgAllowImageTag;
//			if (!$staticInitialised || $staticInitialised != $globalContext) {
//				$htmlpairsStatic = [ # Tags that must be closed
//					'b', 'bdi', 'del', 'i', 'ins', 'u', 'font', 'big', 'small', 'sub', 'sup', 'h1',
//					'h2', 'h3', 'h4', 'h5', 'h6', 'cite', 'code', 'em', 's',
//					'strike', 'strong', 'tt', 'var', 'div', 'center',
//					'blockquote', 'ol', 'ul', 'dl', 'table', 'caption', 'pre',
//					'ruby', 'rb', 'rp', 'rt', 'rtc', 'p', 'span', 'abbr', 'dfn',
//					'kbd', 'samp', 'data', 'time', 'mark'
//				];
//				$htmlsingle = [
//					'br', 'wbr', 'hr', 'li', 'dt', 'dd', 'meta', 'link'
//				];
//
//				# Elements that cannot have close tags. This is (not coincidentally)
//				# also the list of tags for which the HTML 5 parsing algorithm
//				# requires you to "acknowledge the token's self-closing flag", i.e.
//				# a self-closing tag like <br/> is not an HTML 5 parse error only
//				# for this list.
//				$htmlsingleonly = [
//					'br', 'wbr', 'hr', 'meta', 'link'
//				];
//
//				$htmlnest = [ # Tags that can be nested--??
//					'table', 'tr', 'td', 'th', 'div', 'blockquote', 'ol', 'ul',
//					'li', 'dl', 'dt', 'dd', 'font', 'big', 'small', 'sub', 'sup', 'span',
//					'var', 'kbd', 'samp', 'em', 'strong', 'q', 'ruby', 'bdo'
//				];
//				$tabletags = [ # Can only appear inside table, we will close them
//					'td', 'th', 'tr',
//				];
//				$htmllist = [ # Tags used by list
//					'ul', 'ol',
//				];
//				$listtags = [ # Tags that can appear in a list
//					'li',
//				];
//
//				if ($wgAllowImageTag) {
//					$htmlsingle[] = 'img';
//					$htmlsingleonly[] = 'img';
//				}
//
//				$htmlsingleallowed = array_unique(array_merge($htmlsingle, $tabletags));
//				$htmlelementsStatic = array_unique(array_merge($htmlsingle, $htmlpairsStatic, $htmlnest));
//
//				# Convert them all to hashtables for faster lookup
//				$vars = [ 'htmlpairsStatic', 'htmlsingle', 'htmlsingleonly', 'htmlnest', 'tabletags',
//					'htmllist', 'listtags', 'htmlsingleallowed', 'htmlelementsStatic' ];
//				foreach ($vars as $var) {
//					$$var = array_flip($$var);
//				}
//				$staticInitialised = $globalContext;
//			}
//
//			# Populate $htmlpairs and $htmlelements with the $extratags and $removetags arrays
//			$extratags = array_flip($extratags);
//			$removetags = array_flip($removetags);
//			$htmlpairs = array_merge($extratags, $htmlpairsStatic);
//			$htmlelements = array_diff_key(array_merge($extratags, $htmlelementsStatic), $removetags);
//
//			return [
//				'htmlpairs' => $htmlpairs,
//				'htmlsingle' => $htmlsingle,
//				'htmlsingleonly' => $htmlsingleonly,
//				'htmlnest' => $htmlnest,
//				'tabletags' => $tabletags,
//				'htmllist' => $htmllist,
//				'listtags' => $listtags,
//				'htmlsingleallowed' => $htmlsingleallowed,
//				'htmlelements' => $htmlelements,
//			];
//		}
//
//		/**
//		* Cleans up HTML, removes dangerous tags and attributes, and
//		* removes HTML comments
//		* @param String $text
//		* @param callable $processCallback Callback to do any variable or parameter
//		*   replacements in HTML attribute values
//		* @param array|boolean $args Arguments for the processing callback
//		* @param array $extratags For any extra tags to include
//		* @param array $removetags For any tags (default or extra) to exclude
//		* @param callable $warnCallback (Deprecated) Callback allowing the
//		*   addition of a tracking category when bad input is encountered.
//		*   DO NOT ADD NEW PARAMETERS AFTER $warnCallback, since it will be
//		*   removed shortly.
//		* @return String
//		*/
//		public static function removeHTMLtags($text, $processCallback = null,
//			$args = [], $extratags = [], $removetags = [], $warnCallback = null
//		) {
//			extract(self::getRecognizedTagData($extratags, $removetags));
//
//			# Remove HTML comments
//			$text = Sanitizer::removeHTMLcomments($text);
//			$bits = explode('<', $text);
//			$text = str_replace('>', '&gt;', array_shift($bits));
//			if (!MWTidy::isEnabled()) {
//				$tagstack = $tablestack = [];
//				foreach ($bits as $x) {
//					$regs = [];
//					# $slash: Does the current element start with a '/'?
//					# $t: Current element name
//					# $params: String between element name and >
//					# $brace: Ending '>' or '/>'
//					# $rest: Everything until the next element of $bits
//					if (preg_match(self::ELEMENT_BITS_REGEX, $x, $regs)) {
//						list(/* $qbar */, $slash, $t, $params, $brace, $rest) = $regs;
//					} else {
//						$slash = $t = $params = $brace = $rest = null;
//					}
//
//					$badtag = false;
//					$t = strtolower($t);
//					if (isset($htmlelements[$t])) {
//						# Check our stack
//						if ($slash && isset($htmlsingleonly[$t])) {
//							$badtag = true;
//						} elseif ($slash) {
//							# Closing a tag... is it the one we just opened?
//							MediaWiki\suppressWarnings();
//							$ot = array_pop($tagstack);
//							MediaWiki\restoreWarnings();
//
//							if ($ot != $t) {
//								if (isset($htmlsingleallowed[$ot])) {
//									# Pop all elements with an optional close tag
//									# and see if we find a match below them
//									$optstack = [];
//									array_push($optstack, $ot);
//									MediaWiki\suppressWarnings();
//									$ot = array_pop($tagstack);
//									MediaWiki\restoreWarnings();
//									while ($ot != $t && isset($htmlsingleallowed[$ot])) {
//										array_push($optstack, $ot);
//										MediaWiki\suppressWarnings();
//										$ot = array_pop($tagstack);
//										MediaWiki\restoreWarnings();
//									}
//									if ($t != $ot) {
//										# No match. Push the optional elements back again
//										$badtag = true;
//										MediaWiki\suppressWarnings();
//										$ot = array_pop($optstack);
//										MediaWiki\restoreWarnings();
//										while ($ot) {
//											array_push($tagstack, $ot);
//											MediaWiki\suppressWarnings();
//											$ot = array_pop($optstack);
//											MediaWiki\restoreWarnings();
//										}
//									}
//								} else {
//									MediaWiki\suppressWarnings();
//									array_push($tagstack, $ot);
//									MediaWiki\restoreWarnings();
//
//									# <li> can be nested in <ul> or <ol>, skip those cases:
//									if (!isset($htmllist[$ot]) || !isset($listtags[$t])) {
//										$badtag = true;
//									}
//								}
//							} else {
//								if ($t == 'table') {
//									$tagstack = array_pop($tablestack);
//								}
//							}
//							$newparams = '';
//						} else {
//							# Keep track for later
//							if (isset($tabletags[$t]) && !in_array('table', $tagstack)) {
//								$badtag = true;
//							} elseif (in_array($t, $tagstack) && !isset($htmlnest[$t])) {
//								$badtag = true;
//							#  Is it a self closed htmlpair ? (bug 5487)
//							} elseif ($brace == '/>' && isset($htmlpairs[$t])) {
//								// Eventually we'll just remove the self-closing
//								// slash, in order to be consistent with HTML5
//								// semantics.
//								// $brace = '>';
//								// For now, let's just warn authors to clean up.
//								if (is_callable($warnCallback)) {
//									call_user_func_array($warnCallback, [ 'deprecated-self-close-category' ]);
//								}
//								$badtag = true;
//							} elseif (isset($htmlsingleonly[$t])) {
//								# Hack to force empty tag for unclosable elements
//								$brace = '/>';
//							} elseif (isset($htmlsingle[$t])) {
//								# Hack to not close $htmlsingle tags
//								$brace = null;
//								# Still need to push this optionally-closed tag to
//								# the tag stack so that we can match end tags
//								# instead of marking them as bad.
//								array_push($tagstack, $t);
//							} elseif (isset($tabletags[$t]) && in_array($t, $tagstack)) {
//								// New table tag but forgot to close the previous one
//								$text .= "</$t>";
//							} else {
//								if ($t == 'table') {
//									array_push($tablestack, $tagstack);
//									$tagstack = [];
//								}
//								array_push($tagstack, $t);
//							}
//
//							# Replace any variables or template parameters with
//							# plaintext results.
//							if (is_callable($processCallback)) {
//								call_user_func_array($processCallback, [ &$params, $args ]);
//							}
//
//							if (!Sanitizer::validateTag($params, $t)) {
//								$badtag = true;
//							}
//
//							# Strip non-approved attributes from the tag
//							$newparams = Sanitizer::fixTagAttributes($params, $t);
//						}
//						if (!$badtag) {
//							$rest = str_replace('>', '&gt;', $rest);
//							$close = ($brace == '/>' && !$slash) ? ' /' : '';
//							$text .= "<$slash$t$newparams$close>$rest";
//							continue;
//						}
//					}
//					$text .= '&lt;' . str_replace('>', '&gt;', $x);
//				}
//				# Close off any remaining tags
//				while (is_array($tagstack) && ($t = array_pop($tagstack))) {
//					$text .= "</$t>\n";
//					if ($t == 'table') {
//						$tagstack = array_pop($tablestack);
//					}
//				}
//			} else {
//				# this might be possible using tidy itself
//				foreach ($bits as $x) {
//					if (preg_match(self::ELEMENT_BITS_REGEX, $x, $regs)) {
//						list(/* $qbar */, $slash, $t, $params, $brace, $rest) = $regs;
//
//						$badtag = false;
//						$t = strtolower($t);
//						if (isset($htmlelements[$t])) {
//							if (is_callable($processCallback)) {
//								call_user_func_array($processCallback, [ &$params, $args ]);
//							}
//
//							if ($brace == '/>' && !(isset($htmlsingle[$t]) || isset($htmlsingleonly[$t]))) {
//								// Eventually we'll just remove the self-closing
//								// slash, in order to be consistent with HTML5
//								// semantics.
//								// $brace = '>';
//								// For now, let's just warn authors to clean up.
//								if (is_callable($warnCallback)) {
//									call_user_func_array($warnCallback, [ 'deprecated-self-close-category' ]);
//								}
//							}
//							if (!Sanitizer::validateTag($params, $t)) {
//								$badtag = true;
//							}
//
//							$newparams = Sanitizer::fixTagAttributes($params, $t);
//							if (!$badtag) {
//								if ($brace === '/>' && !isset($htmlsingleonly[$t])) {
//									# Interpret self-closing tags as empty tags even when
//									# HTML 5 would interpret them as start tags. Such input
//									# is commonly seen on Wikimedia wikis with this intention.
//									$brace = "></$t>";
//								}
//
//								$rest = str_replace('>', '&gt;', $rest);
//								$text .= "<$slash$t$newparams$brace$rest";
//								continue;
//							}
//						}
//					}
//					$text .= '&lt;' . str_replace('>', '&gt;', $x);
//				}
//			}
//			return $text;
//		}
//
//		/**
//		* Remove '<!--', '-->', and everything between.
//		* To avoid leaving blank lines, when a comment is both preceded
//		* and followed by a newline (ignoring spaces), trim leading and
//		* trailing spaces and one of the newlines.
//		*
//		* @param String $text
//		* @return String
//		*/
//		public static function removeHTMLcomments($text) {
//			while (($start = strpos($text, '<!--')) !== false) {
//				$end = strpos($text, '-->', $start + 4);
//				if ($end === false) {
//					# Unterminated comment; bail out
//					break;
//				}
//
//				$end += 3;
//
//				# Trim space and newline if the comment is both
//				# preceded and followed by a newline
//				$spaceStart = max($start - 1, 0);
//				$spaceLen = $end - $spaceStart;
//				while (substr($text, $spaceStart, 1) === ' ' && $spaceStart > 0) {
//					$spaceStart--;
//					$spaceLen++;
//				}
//				while (substr($text, $spaceStart + $spaceLen, 1) === ' ') {
//					$spaceLen++;
//				}
//				if (substr($text, $spaceStart, 1) === "\n"
//					&& substr($text, $spaceStart + $spaceLen, 1) === "\n") {
//					# Remove the comment, leading and trailing
//					# spaces, and leave only one newline.
//					$text = substr_replace($text, "\n", $spaceStart, $spaceLen + 1);
//				} else {
//					# Remove just the comment.
//					$text = substr_replace($text, '', $start, $end - $start);
//				}
//			}
//			return $text;
//		}
//
//		/**
//		* Takes attribute names and values for a tag and the tag name and
//		* validates that the tag is allowed to be present.
//		* This DOES NOT validate the attributes, nor does it validate the
//		* tags themselves. This method only handles the special circumstances
//		* where we may want to allow a tag within content but ONLY when it has
//		* specific attributes set.
//		*
//		* @param String $params
//		* @param String $element
//		* @return boolean
//		*/
//		static function validateTag($params, $element) {
//			$params = Sanitizer::decodeTagAttributes($params);
//
//			if ($element == 'meta' || $element == 'link') {
//				if (!isset($params['itemprop'])) {
//					// <meta> and <link> must have an itemprop="" otherwise they are not valid or safe in content
//					return false;
//				}
//				if ($element == 'meta' && !isset($params['content'])) {
//					// <meta> must have a content="" for the itemprop
//					return false;
//				}
//				if ($element == 'link' && !isset($params['href'])) {
//					// <link> must have an associated href=""
//					return false;
//				}
//			}
//
//			return true;
//		}
//
//		/**
//		* Take an array of attribute names and values and normalize or discard
//		* illegal values for the given element type.
//		*
//		* - Discards attributes not on a whitelist for the given element
//		* - Unsafe style attributes are discarded
//		* - Invalid id attributes are re-encoded
//		*
//		* @param array $attribs
//		* @param String $element
//		* @return array
//		*
//		* @todo Check for legal values where the DTD limits things.
//		* @todo Check for unique id attribute :P
//		*/
//		static function validateTagAttributes($attribs, $element) {
//			return Sanitizer::validateAttributes($attribs,
//				Sanitizer::attributeWhitelist($element));
//		}
//
//		/**
//		* Take an array of attribute names and values and normalize or discard
//		* illegal values for the given whitelist.
//		*
//		* - Discards attributes not on the given whitelist
//		* - Unsafe style attributes are discarded
//		* - Invalid id attributes are re-encoded
//		*
//		* @param array $attribs
//		* @param array $whitelist List of allowed attribute names
//		* @return array
//		*
//		* @todo Check for legal values where the DTD limits things.
//		* @todo Check for unique id attribute :P
//		*/
//		static function validateAttributes($attribs, $whitelist) {
//			$whitelist = array_flip($whitelist);
//			$hrefExp = '/^(' . wfUrlProtocols() . ')[^\s]+$/';
//
//			$out = [];
//			foreach ($attribs as $attribute => $value) {
//				# Allow XML namespace declaration to allow RDFa
//				if (preg_match(self::XMLNS_ATTRIBUTE_PATTERN, $attribute)) {
//					if (!preg_match(self::EVIL_URI_PATTERN, $value)) {
//						$out[$attribute] = $value;
//					}
//
//					continue;
//				}
//
//				# Allow any attribute beginning with "data-"
//				# However:
//				# * data-ooui is reserved for ooui
//				# * data-mw and data-parsoid are reserved for parsoid
//				# * data-mw-<name here> is reserved for extensions (or core) if
//				#   they need to communicate some data to the client and want to be
//				#   sure that it isn't coming from an untrusted user.
//				# * Ensure that the attribute is not namespaced by banning
//				#   colons.
//				if (!preg_match('/^data-(?!ooui|mw|parsoid)[^:]*$/i', $attribute)
//					&& !isset($whitelist[$attribute])
//				) {
//					continue;
//				}
//
//				# Strip javascript "expression" from stylesheets.
//				# http://msdn.microsoft.com/workshop/author/dhtml/overview/recalc.asp
//				if ($attribute == 'style') {
//					$value = Sanitizer::checkCss($value);
//				}
//
//				# Escape HTML id attributes
//				if ($attribute === 'id') {
//					$value = Sanitizer::escapeId($value, 'noninitial');
//				}
//
//				# Escape HTML id reference lists
//				if ($attribute === 'aria-describedby'
//					|| $attribute === 'aria-flowto'
//					|| $attribute === 'aria-labelledby'
//					|| $attribute === 'aria-owns'
//				) {
//					$value = Sanitizer::escapeIdReferenceList($value, 'noninitial');
//				}
//
//				// RDFa and microdata properties allow URLs, URIs and/or CURIs.
//				// Check them for sanity.
//				if ($attribute === 'rel' || $attribute === 'rev'
//					# RDFa
//					|| $attribute === 'about' || $attribute === 'property'
//					|| $attribute === 'resource' || $attribute === 'datatype'
//					|| $attribute === 'typeof'
//					# HTML5 microdata
//					|| $attribute === 'itemid' || $attribute === 'itemprop'
//					|| $attribute === 'itemref' || $attribute === 'itemscope'
//					|| $attribute === 'itemtype'
//				) {
//					// Paranoia. Allow "simple" values but suppress javascript
//					if (preg_match(self::EVIL_URI_PATTERN, $value)) {
//						continue;
//					}
//				}
//
//				# NOTE: even though elements using href/src are not allowed directly, supply
//				#       validation code that can be used by tag hook handlers, etc
//				if ($attribute === 'href' || $attribute === 'src') {
//					if (!preg_match($hrefExp, $value)) {
//						continue; // drop any href or src attributes not using an allowed protocol.
//						// NOTE: this also drops all relative URLs
//					}
//				}
//
//				// If this attribute was previously set, override it.
//				// Output should only have one attribute of each name.
//				$out[$attribute] = $value;
//			}
//
//			# itemtype, itemid, itemref don't make sense without itemscope
//			if (!array_key_exists('itemscope', $out)) {
//				unset($out['itemtype']);
//				unset($out['itemid']);
//				unset($out['itemref']);
//			}
//			# TODO: Strip itemprop if we aren't descendants of an itemscope or pointed to by an itemref.
//
//			return $out;
//		}

	/**
	* Merge two sets of HTML attributes.  Conflicting items in the second set
	* @Override will those in the first, except for 'class' attributes which
	* will be combined (if they're both strings).
	*
	* @todo implement merging for other attributes such as style
	* @param array $a
	* @param array $b
	* @return array
	*/
	// XO.MW.PORTED: XO does src += trg; MW does rv = src + trg;
	public void mergeAttributes(Xomw_atr_mgr src, Xomw_atr_mgr trg) {
		// loop trg and add to src; some additional logic for cls to merge;
		int trg_len = trg.Len();
		for (int i = 0; i < trg_len; i++) {
			Xomw_atr_itm trg_atr = trg.Get_at(i);

			// if cls, merge; EX: src.cls="a" trg.cls="b" -> src.cls="a b"
			byte[] atr_cls = Gfh_atr_.Bry__class;
			if (Bry_.Eq(trg_atr.Key_bry(), atr_cls)) {
				Xomw_atr_itm src_atr = src.Get_by_or_null(atr_cls);
				if (src_atr != null) {
					// NOTE: need byte[]-creation is unavoidable b/c src_atr and trg_atr are non-null
					mergeAttributesCombine(tmp_bfr, src_atr.Val(), Byte_ascii.Space);
					tmp_bfr.Add_byte_space();
					mergeAttributesCombine(tmp_bfr, trg_atr.Val(), Byte_ascii.Space);
					src_atr.Val_(tmp_bfr.To_bry_and_clear());
					continue;
				}
			}
			src.Add_or_set(trg_atr);
		}
	}
	// XO.MW.REGEX:
	// $classes = preg_split('/\s+/', "{$a['class']} {$b['class']}",
	//					-1, PREG_SPLIT_NO_EMPTY);
	//				$out['class'] = implode(' ', array_unique($classes));
	private static void mergeAttributesCombine(Bry_bfr trg, byte[] src, byte sep) {
		int src_len = src.length;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			if (b == sep) {
				// gobble ws; EX: "a   b"
				int space_bgn = i;
				int space_end = Bry_find_.Find_fwd_while(src, i, src_len, sep);
				i = space_end - 1;	// -1 b/c i++ above

				// ignore ws at BOS; EX: "  a"
				if (space_bgn == 0)
					continue;
				// ignore ws at EOS; EX: "a  "
				if (space_end == src_len)
					break;
			}
			trg.Add_byte(b);
		}
	}

//		/**
//		* Normalize CSS into a format we can easily search for hostile input
//		*  - decode character references
//		*  - decode escape sequences
//		*  - convert characters that IE6 interprets into ascii
//		*  - remove comments, unless the entire value is one single comment
//		* @param String $value the css String
//		* @return String normalized css
//		*/
//		public static function normalizeCss($value) {
//
//			// Decode character references like &#123;
//			$value = Sanitizer::decodeCharReferences($value);
//
//			// Decode escape sequences and line continuation
//			// See the grammar in the CSS 2 spec, appendix D.
//			// This has to be done AFTER decoding character references.
//			// This means it isn't possible for this function to return
//			// unsanitized escape sequences. It is possible to manufacture
//			// input that contains character references that decode to
//			// escape sequences that decode to character references, but
//			// it's OK for the return value to contain character references
//			// because the caller is supposed to escape those anyway.
//			static $decodeRegex;
//			if (!$decodeRegex) {
//				$space = '[\\x20\\t\\r\\n\\f]';
//				$nl = '(?:\\n|\\r\\n|\\r|\\f)';
//				$backslash = '\\\\';
//				$decodeRegex = "/ $backslash
//					(?:
//						($nl) |  # 1. Line continuation
//						([0-9A-Fa-f]{1,6})$space? |  # 2. character number
//						(.) | # 3. backslash cancelling special meaning
//						() | # 4. backslash at end of String
//					)/xu";
//			}
//			$value = preg_replace_callback($decodeRegex,
//				[ __CLASS__, 'cssDecodeCallback' ], $value);
//
//			// Normalize Halfwidth and Fullwidth Unicode block that IE6 might treat as ascii
//			$value = preg_replace_callback(
//				'/[！-［］-ｚ]/u', // U+FF01 to U+FF5A, excluding U+FF3C (bug 58088)
//				function ($matches) {
//					$cp = UtfNormal\Utils::utf8ToCodepoint($matches[0]);
//					if ($cp === false) {
//						return '';
//					}
//					return chr($cp - 65248); // ASCII range \x21-\x7A
//				},
//				$value
//			);
//
//			// Convert more characters IE6 might treat as ascii
//			// U+0280, U+0274, U+207F, U+029F, U+026A, U+207D, U+208D
//			$value = str_replace(
//				[ 'ʀ', 'ɴ', 'ⁿ', 'ʟ', 'ɪ', '⁽', '₍' ],
//				[ 'r', 'n', 'n', 'l', 'i', '(', '(' ],
//				$value
//			);
//
//			// Let the value through if it's nothing but a single comment, to
//			// allow other functions which may reject it to pass some error
//			// message through.
//			if (!preg_match('! ^ \s* /\* [^*\\/]* \*/ \s* $ !x', $value)) {
//				// Remove any comments; IE gets token splitting wrong
//				// This must be done AFTER decoding character references and
//				// escape sequences, because those steps can introduce comments
//				// This step cannot introduce character references or escape
//				// sequences, because it replaces comments with spaces rather
//				// than removing them completely.
//				$value = StringUtils::delimiterReplace('/*', '*/', ' ', $value);
//
//				// Remove anything after a comment-start token, to guard against
//				// incorrect client implementations.
//				$commentPos = strpos($value, '/*');
//				if ($commentPos !== false) {
//					$value = substr($value, 0, $commentPos);
//				}
//			}
//
//			// S followed by repeat, iteration, or prolonged sound marks,
//			// which IE will treat as "ss"
//			$value = preg_replace(
//				'/s(?:
//					\xE3\x80\xB1 | # U+3031
//					\xE3\x82\x9D | # U+309D
//					\xE3\x83\xBC | # U+30FC
//					\xE3\x83\xBD | # U+30FD
//					\xEF\xB9\xBC | # U+FE7C
//					\xEF\xB9\xBD | # U+FE7D
//					\xEF\xBD\xB0   # U+FF70
//				)/ix',
//				'ss',
//				$value
//			);
//
//			return $value;
//		}
//
//		/**
//		* Pick apart some CSS and check it for forbidden or unsafe structures.
//		* Returns a sanitized String. This sanitized String will have
//		* character references and escape sequences decoded and comments
//		* stripped (unless it is itself one valid comment, in which case the value
//		* will be passed through). If the input is just too evil, only a comment
//		* complaining about evilness will be returned.
//		*
//		* Currently URL references, 'expression', 'tps' are forbidden.
//		*
//		* NOTE: Despite the fact that character references are decoded, the
//		* returned String may contain character references given certain
//		* clever input strings. These character references must
//		* be escaped before the return value is embedded in HTML.
//		*
//		* @param String $value
//		* @return String
//		*/
//		static function checkCss($value) {
//			$value = self::normalizeCss($value);
//
//			// Reject problematic keywords and control characters
//			if (preg_match('/[\000-\010\013\016-\037\177]/', $value) ||
//				strpos($value, UtfNormal\Constants::UTF8_REPLACEMENT) !== false) {
//				return '/* invalid control char */';
//			} elseif (preg_match(
//				'! expression
//					| filter\s*:
//					| accelerator\s*:
//					| -o-link\s*:
//					| -o-link-source\s*:
//					| -o-replace\s*:
//					| url\s*\(
//					| image\s*\(
//					| image-set\s*\(
//					| attr\s*\([^)]+[\s,]+url
//				!ix', $value)) {
//				return '/* insecure input */';
//			}
//			return $value;
//		}
//
//		/**
//		* @param array $matches
//		* @return String
//		*/
//		static function cssDecodeCallback($matches) {
//			if ($matches[1] !== '') {
//				// Line continuation
//				return '';
//			} elseif ($matches[2] !== '') {
//				$char = UtfNormal\Utils::codepointToUtf8(hexdec($matches[2]));
//			} elseif ($matches[3] !== '') {
//				$char = $matches[3];
//			} else {
//				$char = '\\';
//			}
//			if ($char == "\n" || $char == '"' || $char == "'" || $char == '\\') {
//				// These characters need to be escaped in strings
//				// Clean up the escape sequence to avoid parsing errors by clients
//				return '\\' . dechex(ord($char)) . ' ';
//			} else {
//				// Decode unnecessary escape
//				return $char;
//			}
//		}

	/**
	* Take a tag soup fragment listing an HTML element's attributes
	* and normalize it to well-formed XML, discarding unwanted attributes.
	* Output is safe for further wikitext processing, with escaping of
	* values that could trigger problems.
	*
	* - Normalizes attribute names to lowercase
	* - Discards attributes not on a whitelist for the given element
	* - Turns broken or invalid entities into plaintext
	* - Double-quotes all attribute values
	* - Attributes without values are given the name as attribute
	* - Double attributes are discarded
	* - Unsafe style attributes are discarded
	* - Prepends space if there are attributes.
	* - (Optionally) Sorts attributes by name.
	*
	* @param String $text
	* @param String $element
	* @param boolean $sorted Whether to sort the attributes (default: false)
	* @return String
	*/
	public void fixTagAttributes(Bry_bfr bfr, byte[] element, byte[] text) {
		if (Bry_.Trim(text).length == 0) {
			return;
		}

//			$decoded = Sanitizer::decodeTagAttributes($text);
//			$stripped = Sanitizer::validateTagAttributes($decoded, $element);
//
//			if ($sorted) {
//				ksort($stripped);
//			}

		atr_bldr.Atrs__clear();
		atr_parser.Parse(atr_bldr, -1, -1, text, 0, text.length);
		int len = atr_bldr.Atrs__len();

		// XO.MW.PORTED: Sanitizer::safeEncodeTagAttributes($stripped)
		for (int i = 0; i < len; i++) {
			// $encAttribute = htmlspecialchars($attribute);
			// $encValue = Sanitizer::safeEncodeAttribute($value);
			// $attribs[] = "$encAttribute=\"$encValue\"";
			Mwh_atr_itm itm = atr_bldr.Atrs__get_at(i);
			bfr.Add_byte_space();	// "return count($attribs) ? ' ' . implode(' ', $attribs) : '';"
			bfr.Add_bry_escape_html(itm.Key_bry(), itm.Key_bgn(), itm.Key_end());
			bfr.Add_byte_eq().Add_byte_quote();
			bfr.Add(itm.Val_as_bry());	// TODO.XO:Sanitizer::encode
			bfr.Add_byte_quote();
		}
	}

//		/**
//		* Encode an attribute value for HTML output.
//		* @param String $text
//		* @return String HTML-encoded text fragment
//		*/
	public static void encodeAttribute(Bry_bfr bfr, byte[] text) {
		// XO.MW.PORTED: moved to Add_bry_escape_xml
		// $encValue = htmlspecialchars($text, ENT_QUOTES);

		// Whitespace is normalized during attribute decoding,
		// so if we've been passed non-spaces we must encode them
		// ahead of time or they won't be preserved.
		//	$encValue = strtr($encValue, [
		//		"\n" => '&#10;',
		//		"\r" => '&#13;',
		//		"\t" => '&#9;',
		//	]);
		bfr.Add_bry_escape_xml(text, 0, text.length);
	}

//		/**
//		* Encode an attribute value for HTML tags, with extra armoring
//		* against further wiki processing.
//		* @param String $text
//		* @return String HTML-encoded text fragment
//		*/
//		static function safeEncodeAttribute($text) {
//			$encValue = Sanitizer::encodeAttribute($text);
//
//			# Templates and links may be expanded in later parsing,
//			# creating invalid or dangerous output. Suppress this.
//			$encValue = strtr($encValue, [
//				'<'    => '&lt;',   // This should never happen,
//				'>'    => '&gt;',   // we've received invalid input
//				'"'    => '&quot;', // which should have been escaped.
//				'{'    => '&#123;',
//				'}'    => '&#125;', // prevent unpaired language conversion syntax
//				'['    => '&#91;',
//				"''"   => '&#39;&#39;',
//				'ISBN' => '&#73;SBN',
//				'RFC'  => '&#82;FC',
//				'PMID' => '&#80;MID',
//				'|'    => '&#124;',
//				'__'   => '&#95;_',
//			]);
//
//			# Stupid hack
//			$encValue = preg_replace_callback(
//				'/((?i)' . wfUrlProtocols() . ')/',
//				[ 'Sanitizer', 'armorLinksCallback' ],
//				$encValue);
//			return $encValue;
//		}
//
//		/**
//		* Given a value, escape it so that it can be used in an id attribute and
//		* return it.  This will use HTML5 validation if $wgExperimentalHtmlIds is
//		* true, allowing anything but ASCII whitespace.  Otherwise it will use
//		* HTML 4 rules, which means a narrow subset of ASCII, with bad characters
//		* escaped with lots of dots.
//		*
//		* To ensure we don't have to bother escaping anything, we also strip ', ",
//		* & even if $wgExperimentalIds is true.  TODO: Is this the best tactic?
//		* We also strip # because it upsets IE, and % because it could be
//		* ambiguous if it's part of something that looks like a percent escape
//		* (which don't work reliably in fragments cross-browser).
//		*
//		* @see https://www.w3.org/TR/html401/types.html#type-name Valid characters
//		*   in the id and name attributes
//		* @see https://www.w3.org/TR/html401/struct/links.html#h-12.2.3 Anchors with
//		*   the id attribute
//		* @see https://www.w3.org/TR/html5/dom.html#the-id-attribute
//		*   HTML5 definition of id attribute
//		*
//		* @param String $id Id to escape
//		* @param String|array $options String or array of strings (default is array()):
//		*   'noninitial': This is a non-initial fragment of an id, not a full id,
//		*       so don't pay attention if the first character isn't valid at the
//		*       beginning of an id.  Only matters if $wgExperimentalHtmlIds is
//		*       false.
//		*   'legacy': Behave the way the old HTML 4-based ID escaping worked even
//		*       if $wgExperimentalHtmlIds is used, so we can generate extra
//		*       anchors and links won't break.
//		* @return String
//		*/
//		static function escapeId($id, $options = []) {
//			global $wgExperimentalHtmlIds;
//			$options = (array)$options;
//
//			$id = Sanitizer::decodeCharReferences($id);
//
//			if ($wgExperimentalHtmlIds && !in_array('legacy', $options)) {
//				$id = preg_replace('/[ \t\n\r\f_\'"&#%]+/', '_', $id);
//				$id = trim($id, '_');
//				if ($id === '') {
//					// Must have been all whitespace to start with.
//					return '_';
//				} else {
//					return $id;
//				}
//			}
//
//			// HTML4-style escaping
//			static $replace = [
//				'%3A' => ':',
//				'%' => '.'
//			];
//
//			$id = urlencode(strtr($id, ' ', '_'));
//			$id = str_replace(array_keys($replace), array_values($replace), $id);
//
//			if (!preg_match('/^[a-zA-Z]/', $id) && !in_array('noninitial', $options)) {
//				// Initial character must be a letter!
//				$id = "x$id";
//			}
//			return $id;
//		}
//
//		/**
//		* Given a String containing a space delimited list of ids, escape each id
//		* to match ids escaped by the escapeId() function.
//		*
//		* @since 1.27
//		*
//		* @param String $referenceString Space delimited list of ids
//		* @param String|array $options String or array of strings (default is array()):
//		*   'noninitial': This is a non-initial fragment of an id, not a full id,
//		*       so don't pay attention if the first character isn't valid at the
//		*       beginning of an id.  Only matters if $wgExperimentalHtmlIds is
//		*       false.
//		*   'legacy': Behave the way the old HTML 4-based ID escaping worked even
//		*       if $wgExperimentalHtmlIds is used, so we can generate extra
//		*       anchors and links won't break.
//		* @return String
//		*/
//		static function escapeIdReferenceList($referenceString, $options = []) {
//			# Explode the space delimited list String into an array of tokens
//			$references = preg_split('/\s+/', "{$referenceString}", -1, PREG_SPLIT_NO_EMPTY);
//
//			# Escape each token as an id
//			foreach ($references as &$ref) {
//				$ref = Sanitizer::escapeId($ref, $options);
//			}
//
//			# Merge the array back to a space delimited list String
//			# If the array is empty, the result will be an empty String ('')
//			$referenceString = implode(' ', $references);
//
//			return $referenceString;
//		}
//
//		/**
//		* Given a value, escape it so that it can be used as a CSS class and
//		* return it.
//		*
//		* @todo For extra validity, input should be validated UTF-8.
//		*
//		* @see https://www.w3.org/TR/CSS21/syndata.html Valid characters/format
//		*
//		* @param String $class
//		* @return String
//		*/
//		static function escapeClass($class) {
//			// Convert ugly stuff to underscores and kill underscores in ugly places
//			return rtrim(preg_replace(
//				[ '/(^[0-9\\-])|[\\x00-\\x20!"#$%&\'()*+,.\\/:;<=>?@[\\]^`{|}~]|\\xC2\\xA0/', '/_+/' ],
//				'_',
//				$class), '_');
//		}
//
//		/**
//		* Given HTML input, escape with htmlspecialchars but un-escape entities.
//		* This allows (generally harmless) entities like &#160; to survive.
//		*
//		* @param String $html HTML to escape
//		* @return String Escaped input
//		*/
//		static function escapeHtmlAllowEntities($html) {
//			$html = Sanitizer::decodeCharReferences($html);
//			# It seems wise to escape ' as well as ", as a matter of course.  Can't
//			# hurt. Use ENT_SUBSTITUTE so that incorrectly truncated multibyte characters
//			# don't cause the entire String to disappear.
//			$html = htmlspecialchars($html, ENT_QUOTES | ENT_SUBSTITUTE);
//			return $html;
//		}
//
//		/**
//		* Regex replace callback for armoring links against further processing.
//		* @param array $matches
//		* @return String
//		*/
//		private static function armorLinksCallback($matches) {
//			return str_replace(':', '&#58;', $matches[1]);
//		}
//
//		/**
//		* Return an associative array of attribute names and values from
//		* a partial tag String. Attribute names are forced to lowercase,
//		* character references are decoded to UTF-8 text.
//		*
//		* @param String $text
//		* @return array
//		*/
//		public static function decodeTagAttributes($text) {
//			if (trim($text) == '') {
//				return [];
//			}
//
//			$attribs = [];
//			$pairs = [];
//			if (!preg_match_all(
//				self::getAttribsRegex(),
//				$text,
//				$pairs,
//				PREG_SET_ORDER)) {
//				return $attribs;
//			}
//
//			foreach ($pairs as $set) {
//				$attribute = strtolower($set[1]);
//				$value = Sanitizer::getTagAttributeCallback($set);
//
//				// Normalize whitespace
//				$value = preg_replace('/[\t\r\n ]+/', ' ', $value);
//				$value = trim($value);
//
//				// Decode character references
//				$attribs[$attribute] = Sanitizer::decodeCharReferences($value);
//			}
//			return $attribs;
//		}
//
//		/**
//		* Build a partial tag String from an associative array of attribute
//		* names and values as returned by decodeTagAttributes.
//		*
//		* @param array $assoc_array
//		* @return String
//		*/
//		public static function safeEncodeTagAttributes($assoc_array) {
//			$attribs = [];
//			foreach ($assoc_array as $attribute => $value) {
//				$encAttribute = htmlspecialchars($attribute);
//				$encValue = Sanitizer::safeEncodeAttribute($value);
//
//				$attribs[] = "$encAttribute=\"$encValue\"";
//			}
//			return count($attribs) ? ' ' . implode(' ', $attribs) : '';
//		}
//
//		/**
//		* Pick the appropriate attribute value from a match set from the
//		* attribs regex matches.
//		*
//		* @param array $set
//		* @throws MWException When tag conditions are not met.
//		* @return String
//		*/
//		private static function getTagAttributeCallback($set) {
//			if (isset($set[5])) {
//				# No quotes.
//				return $set[5];
//			} elseif (isset($set[4])) {
//				# Single-quoted
//				return $set[4];
//			} elseif (isset($set[3])) {
//				# Double-quoted
//				return $set[3];
//			} elseif (!isset($set[2])) {
//				# In XHTML, attributes must have a value so return an empty String.
//				# See "Empty attribute syntax",
//				# https://www.w3.org/TR/html5/syntax.html#syntax-attribute-name
//				return "";
//			} else {
//				throw new MWException("Tag conditions not met. This should never happen and is a bug.");
//			}
//		}

	/**
	* @param String $text
	* @return String
	*/
	private static Btrie_slim_mgr normalizeWhitespaceTrie = Btrie_slim_mgr.cs()
		// '/\r\n|[\x20\x0d\x0a\x09]/',
		.Add_many_str("\r\n", "\r", "\n", "\t")  // NOTE: skipping "x20" b/c replacement will be spaces
		;
	private final    Bry_tmp normalizeWhitespaceBry = new Bry_tmp();
	public byte[] normalizeWhitespace(byte[] text) {
		// XO.MW.REGEX
		//	return preg_replace(
		//		'/\r\n|[\x20\x0d\x0a\x09]/',
		//		' ',
		//		$text);
		normalizeWhitespaceBry.Init(text, 0, text.length);
		XophpPreg.replace(normalizeWhitespaceBry, tmp_bfr_2, normalizeWhitespaceTrie, trv, Byte_ascii.Space_bry);
		return normalizeWhitespaceBry.src;
	}

//		/**
//		* Normalizes whitespace in a section name, such as might be returned
//		* by Parser::stripSectionName(), for use in the id's that are used for
//		* section links.
//		*
//		* @param String $section
//		* @return String
//		*/
//		static function normalizeSectionNameWhitespace($section) {
//			return trim(preg_replace('/[ _]+/', ' ', $section));
//		}
//
//		/**
//		* Ensure that any entities and character references are legal
//		* for XML and XHTML specifically. Any stray bits will be
//		* &amp;-escaped to result in a valid text fragment.
//		*
//		* a. named char refs can only be &lt; &gt; &amp; &quot;, others are
//		*   numericized (this way we're well-formed even without a DTD)
//		* b. any numeric char refs must be legal chars, not invalid or forbidden
//		* c. use lower cased "&#x", not "&#X"
//		* d. fix or reject non-valid attributes
//		*
//		* @param String $text
//		* @return String
//		* @private
//		*/
	public void normalizeCharReferences(XomwParserBfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		normalizeCharReferences(bfr, Bool_.N, src, src_bgn, src_end);
	}
	public byte[] normalizeCharReferences(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end) {
		// XO.MW.REGEX:
		//	return preg_replace_callback(
		//		self::CHAR_REFS_REGEX,
		//		[ 'Sanitizer', 'normalizeCharReferencesCallback' ],
		//		$text);
		return regex_url_char.Replace_by_cbk(bfr, lone_bfr, src, src_bgn, src_end, normalize_cbk);
	}
	/**
	* @param String $matches
	* @return String
	*/
	// XO.MW.MOVED:
	// static function normalizeCharReferencesCallback($matches) {}

	/**
	* If the named entity is defined in the HTML 4.0/XHTML 1.0 DTD,
	* return the equivalent numeric entity reference (except for the core &lt;
	* &gt; &amp; &quot;). If the entity is a MediaWiki-specific alias, returns
	* the HTML equivalent. Otherwise, returns HTML-escaped text of
	* pseudo-entity source (eg &amp;foo;)
	*
	* @param String $name
	* @return String
	*/
	// XO.MW.MOVED:
	// static function normalizeEntity($name) {}

//		/**
//		* @param int $codepoint
//		* @return null|String
//		*/
//		static function decCharReference($codepoint) {
//			$point = intval($codepoint);
//			if (Sanitizer::validateCodepoint($point)) {
//				return sprintf('&#%d;', $point);
//			} else {
//				return null;
//			}
//		}
//
//		/**
//		* @param int $codepoint
//		* @return null|String
//		*/
//		static function hexCharReference($codepoint) {
//			$point = hexdec($codepoint);
//			if (Sanitizer::validateCodepoint($point)) {
//				return sprintf('&#x%x;', $point);
//			} else {
//				return null;
//			}
//		}

	/**
	* Returns true if a given Unicode codepoint is a valid character in
	* both HTML5 and XML.
	* @param int $codepoint
	* @return boolean
	*/
	public static boolean validateCodepoint(int codepoint) {
		// U+000C is valid in HTML5 but not allowed in XML.
		// U+000D is valid in XML but not allowed in HTML5.
		// U+007F - U+009F are disallowed in HTML5 (control characters).
		return  codepoint == 0x09
			||  codepoint == 0x0a
			|| (codepoint >= 0x20    && codepoint <= 0x7e)
			|| (codepoint >= 0xa0    && codepoint <= 0xd7ff)
			|| (codepoint >= 0xe000  && codepoint <= 0xfffd)
			|| (codepoint >= 0x10000 && codepoint <= 0x10ffff);
	}

//		/**
//		* Decode any character references, numeric or named entities,
//		* in the text and return a UTF-8 String.
//		*
//		* @param String $text
//		* @return String
//		*/
	public byte[] decodeCharReferences(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end) {
		// XO.MW.REGEX
		//	return preg_replace_callback(
		//		self::CHAR_REFS_REGEX,
		//		[ 'Sanitizer', 'decodeCharReferencesCallback' ],
		//		$text);
		return regex_url_char.Replace_by_cbk(bfr, lone_bfr, src, src_bgn, src_end, decode_cbk);
	}

//		/**
//		* Decode any character references, numeric or named entities,
//		* in the next and normalize the resulting String. (bug 14952)
//		*
//		* This is useful for page titles, not for text to be displayed,
//		* MediaWiki allows HTML entities to escape normalization as a feature.
//		*
//		* @param String $text Already normalized, containing entities
//		* @return String Still normalized, without entities
//		*/
//		public static function decodeCharReferencesAndNormalize($text) {
//			global $wgContLang;
//			$text = preg_replace_callback(
//				self::CHAR_REFS_REGEX,
//				[ 'Sanitizer', 'decodeCharReferencesCallback' ],
//				$text, /* limit */ -1, $count);
//
//			if ($count) {
//				return $wgContLang->normalize($text);
//			} else {
//				return $text;
//			}
//		}

	/**
	* @param String $matches
	* @return String
	*/
	// XO.MW.MOVED
	// static function decodeCharReferencesCallback($matches) {}

//		/**
//		* Return UTF-8 String for a codepoint if that is a valid
//		* character reference, otherwise U+FFFD REPLACEMENT CHARACTER.
//		* @param int $codepoint
//		* @return String
//		* @private
//		*/
//		static function decodeChar($codepoint) {
//			if (Sanitizer::validateCodepoint($codepoint)) {
//				return UtfNormal\Utils::codepointToUtf8($codepoint);
//			} else {
//				return UtfNormal\Constants::UTF8_REPLACEMENT;
//			}
//		}
//
//		/**
//		* If the named entity is defined in the HTML 4.0/XHTML 1.0 DTD,
//		* return the UTF-8 encoding of that character. Otherwise, returns
//		* pseudo-entity source (eg "&foo;")
//		*
//		* @param String $name
//		* @return String
//		*/
//		static function decodeEntity($name) {
//			if (isset(self::$htmlEntityAliases[$name])) {
//				$name = self::$htmlEntityAliases[$name];
//			}
//			if (isset(self::$htmlEntities[$name])) {
//				return UtfNormal\Utils::codepointToUtf8(self::$htmlEntities[$name]);
//			} else {
//				return "&$name;";
//			}
//		}
//
//		/**
//		* Fetch the whitelist of acceptable attributes for a given element name.
//		*
//		* @param String $element
//		* @return array
//		*/
//		static function attributeWhitelist($element) {
//			$list = Sanitizer::setupAttributeWhitelist();
//			return isset($list[$element])
//				? $list[$element]
//				: [];
//		}
//
//		/**
//		* Foreach array key (an allowed HTML element), return an array
//		* of allowed attributes
//		* @return array
//		*/
//		static function setupAttributeWhitelist() {
//			static $whitelist;
//
//			if ($whitelist !== null) {
//				return $whitelist;
//			}
//
//			$common = [
//				# HTML
//				'id',
//				'class',
//				'style',
//				'lang',
//				'dir',
//				'title',
//
//				# WAI-ARIA
//				'aria-describedby',
//				'aria-flowto',
//				'aria-label',
//				'aria-labelledby',
//				'aria-owns',
//				'role',
//
//				# RDFa
//				# These attributes are specified in section 9 of
//				# https://www.w3.org/TR/2008/REC-rdfa-syntax-20081014
//				'about',
//				'property',
//				'resource',
//				'datatype',
//				'typeof',
//
//				# Microdata. These are specified by
//				# https://html.spec.whatwg.org/multipage/microdata.html#the-microdata-model
//				'itemid',
//				'itemprop',
//				'itemref',
//				'itemscope',
//				'itemtype',
//			];
//
//			$block = array_merge($common, [ 'align' ]);
//			$tablealign = [ 'align', 'valign' ];
//			$tablecell = [
//				'abbr',
//				'axis',
//				'headers',
//				'scope',
//				'rowspan',
//				'colspan',
//				'nowrap', # deprecated
//				'width', # deprecated
//				'height', # deprecated
//				'bgcolor', # deprecated
//			];
//
//			# Numbers refer to sections in HTML 4.01 standard describing the element.
//			# See: https://www.w3.org/TR/html4/
//			$whitelist = [
//				# 7.5.4
//				'div'        => $block,
//				'center'     => $common, # deprecated
//				'span'       => $common,
//
//				# 7.5.5
//				'h1'         => $block,
//				'h2'         => $block,
//				'h3'         => $block,
//				'h4'         => $block,
//				'h5'         => $block,
//				'h6'         => $block,
//
//				# 7.5.6
//				# address
//
//				# 8.2.4
//				'bdo'        => $common,
//
//				# 9.2.1
//				'em'         => $common,
//				'strong'     => $common,
//				'cite'       => $common,
//				'dfn'        => $common,
//				'code'       => $common,
//				'samp'       => $common,
//				'kbd'        => $common,
//				'var'        => $common,
//				'abbr'       => $common,
//				# acronym
//
//				# 9.2.2
//				'blockquote' => array_merge($common, [ 'cite' ]),
//				'q'          => array_merge($common, [ 'cite' ]),
//
//				# 9.2.3
//				'sub'        => $common,
//				'sup'        => $common,
//
//				# 9.3.1
//				'p'          => $block,
//
//				# 9.3.2
//				'br'         => array_merge($common, [ 'clear' ]),
//
//				# https://www.w3.org/TR/html5/text-level-semantics.html#the-wbr-element
//				'wbr'        => $common,
//
//				# 9.3.4
//				'pre'        => array_merge($common, [ 'width' ]),
//
//				# 9.4
//				'ins'        => array_merge($common, [ 'cite', 'datetime' ]),
//				'del'        => array_merge($common, [ 'cite', 'datetime' ]),
//
//				# 10.2
//				'ul'         => array_merge($common, [ 'type' ]),
//				'ol'         => array_merge($common, [ 'type', 'start', 'reversed' ]),
//				'li'         => array_merge($common, [ 'type', 'value' ]),
//
//				# 10.3
//				'dl'         => $common,
//				'dd'         => $common,
//				'dt'         => $common,
//
//				# 11.2.1
//				'table'      => array_merge($common,
//									[ 'summary', 'width', 'border', 'frame',
//											'rules', 'cellspacing', 'cellpadding',
//											'align', 'bgcolor',
//									]),
//
//				# 11.2.2
//				'caption'    => $block,
//
//				# 11.2.3
//				'thead'      => $common,
//				'tfoot'      => $common,
//				'tbody'      => $common,
//
//				# 11.2.4
//				'colgroup'   => array_merge($common, [ 'span' ]),
//				'col'        => array_merge($common, [ 'span' ]),
//
//				# 11.2.5
//				'tr'         => array_merge($common, [ 'bgcolor' ], $tablealign),
//
//				# 11.2.6
//				'td'         => array_merge($common, $tablecell, $tablealign),
//				'th'         => array_merge($common, $tablecell, $tablealign),
//
//				# 12.2
//				# NOTE: <a> is not allowed directly, but the attrib
//				# whitelist is used from the Parser Object
//				'a'          => array_merge($common, [ 'href', 'rel', 'rev' ]), # rel/rev esp. for RDFa
//
//				# 13.2
//				# Not usually allowed, but may be used for extension-style hooks
//				# such as <math> when it is rasterized, or if $wgAllowImageTag is
//				# true
//				'img'        => array_merge($common, [ 'alt', 'src', 'width', 'height' ]),
//
//				# 15.2.1
//				'tt'         => $common,
//				'b'          => $common,
//				'i'          => $common,
//				'big'        => $common,
//				'small'      => $common,
//				'strike'     => $common,
//				's'          => $common,
//				'u'          => $common,
//
//				# 15.2.2
//				'font'       => array_merge($common, [ 'size', 'color', 'face' ]),
//				# basefont
//
//				# 15.3
//				'hr'         => array_merge($common, [ 'width' ]),
//
//				# HTML Ruby annotation text module, simple ruby only.
//				# https://www.w3.org/TR/html5/text-level-semantics.html#the-ruby-element
//				'ruby'       => $common,
//				# rbc
//				'rb'         => $common,
//				'rp'         => $common,
//				'rt'         => $common, # array_merge($common, array('rbspan')),
//				'rtc'         => $common,
//
//				# MathML root element, where used for extensions
//				# 'title' may not be 100% valid here; it's XHTML
//				# https://www.w3.org/TR/REC-MathML/
//				'math'       => [ 'class', 'style', 'id', 'title' ],
//
//				# HTML 5 section 4.6
//				'bdi' => $common,
//
//				# HTML5 elements, defined by:
//				# https://html.spec.whatwg.org/multipage/semantics.html#the-data-element
//				'data' => array_merge($common, [ 'value' ]),
//				'time' => array_merge($common, [ 'datetime' ]),
//				'mark' => $common,
//
//				// meta and link are only permitted by removeHTMLtags when Microdata
//				// is enabled so we don't bother adding a conditional to hide these
//				// Also meta and link are only valid in WikiText as Microdata elements
//				// (ie: validateTag rejects tags missing the attributes needed for Microdata)
//				// So we don't bother including $common attributes that have no purpose.
//				'meta' => [ 'itemprop', 'content' ],
//				'link' => [ 'itemprop', 'href' ],
//			];
//
//			return $whitelist;
//		}

	/**
	* Take a fragment of (potentially invalid) HTML and return
	* a version with any tags removed, encoded as plain text.
	*
	* Warning: this return value must be further escaped for literal
	* inclusion in HTML output as of 1.10!
	*
	* @param String $text HTML fragment
	* @return String
	*/
	public byte[] stripAllTags(byte[] text) {
		// Actual <tags>
		XomwStringUtils.delimiterReplace(tmp_bfr, Byte_ascii.Angle_bgn_bry, Byte_ascii.Angle_end_bry, Bry_.Empty, text);
		text = tmp_bfr.To_bry_and_clear();

		// Normalize &entities and whitespace
		text = decodeCharReferences(null, false, text, 0, text.length);
		text = normalizeWhitespace(text);

		return text;
	}

//		/**
//		* Hack up a private DOCTYPE with HTML's standard entity declarations.
//		* PHP 4 seemed to know these if you gave it an HTML doctype, but
//		* PHP 5.1 doesn't.
//		*
//		* Use for passing XHTML fragments to PHP's XML parsing functions
//		*
//		* @return String
//		*/
//		static function hackDocType() {
//			$out = "<!DOCTYPE html [\n";
//			foreach (self::$htmlEntities as $entity => $codepoint) {
//				$out .= "<!ENTITY $entity \"&#$codepoint;\">";
//			}
//			$out .= "]>\n";
//			return $out;
//		}

	/**
	* @param String $url
	* @return mixed|String
	*/
	public byte[] cleanUrl(byte[] url) {
		// Normalize any HTML entities in input. They will be
		// re-escaped by makeExternalLink().			
		url = decodeCharReferences(null, Bool_.Y, url, 0, url.length);

		// Escape any control characters introduced by the above step
		// XO.MW.REGEX: $url = preg_replace_callback('/[\][<>"\\x00-\\x20\\x7F\|]/', [ __CLASS__, 'cleanUrlCallback' ], $url);
		//   '[]<>"' | '00 -> 32' | 127
		if (regex_clean_url.Escape(tmp_bfr, url, 0, url.length))
			url = tmp_bfr.To_bry_and_clear();

		// Validate hostname portion
		// XO.MW.REGEX: if (preg_match('!^([^:]+:)(//[^/]+)?(.*)$!iD', $url, $matches))
		if (regex_find_domain.Match(url, 0, url.length)) {
			// Characters that will be ignored in IDNs.
			// https://tools.ietf.org/html/rfc3454#section-3.1
			// Strip them before further processing so blacklists and such work.
			// XO.MW.MOVED: see invalid_idn_trie
			XophpPreg.replace(tmp_host.Init(url, regex_find_domain.host_bgn, regex_find_domain.host_end), tmp_bfr, invalid_idn_trie, trv, Bry_.Empty);
			
			// IPv6 host names are bracketed with [].  Url-decode these.
			// if (substr_compare("//%5B", $host, 0, 5) === 0 &&
			//	preg_match('!^//%5B([0-9A-Fa-f:.]+)%5D((:\d+)?)$!', $host, $matches)
			//  XO.MW.REGEX:
			//    !^//%5B([0-9A-Fa-f:.]+)%5D((:\d+)?)$!
			//    "//%5B" + ("hex-dec" | [:.]) + "%5D" + numbers
			//    EX: [ABCD]:80:12
			if (regex_ipv6_brack.Match(tmp_host.src, tmp_host.src_bgn, tmp_host.src_end)) {
				tmp_bfr.Add_str_a7("//[").Add_mid(tmp_host.src, regex_ipv6_brack.host_bgn, regex_ipv6_brack.host_end)
					.Add_byte(Byte_ascii.Brack_end).Add_mid(tmp_host.src, regex_ipv6_brack.segs_bgn, regex_ipv6_brack.segs_end);
				tmp_host.Set_by_bfr(tmp_bfr);
			}

			// @todo FIXME: Validate hostnames here

			tmp_bfr.Add_mid(url, regex_find_domain.prot_bgn, regex_find_domain.prot_end);
			tmp_host.Add_to_bfr(tmp_bfr);
			tmp_bfr.Add_mid(url, regex_find_domain.rest_bgn, regex_find_domain.rest_end);
			return tmp_bfr.To_bry_and_clear();
		}
		else {
			return url;
		}
	}

//		/**
//		* @param array $matches
//		* @return String
//		*/
//		static function cleanUrlCallback($matches) {
//			return urlencode($matches[0]);
//		}
//
//		/**
//		* Does a String look like an e-mail address?
//		*
//		* This validates an email address using an HTML5 specification found at:
//		* http://www.whatwg.org/html/states-of-the-type-attribute.html#valid-e-mail-address
//		* Which as of 2011-01-24 says:
//		*
//		*   A valid e-mail address is a String that matches the ABNF production
//		*   1*(atext / ".") "@" ldh-str *("." ldh-str) where atext is defined
//		*   in RFC 5322 section 3.2.3, and ldh-str is defined in RFC 1034 section
//		*   3.5.
//		*
//		* This function is an implementation of the specification as requested in
//		* bug 22449.
//		*
//		* Client-side forms will use the same standard validation rules via JS or
//		* HTML 5 validation; additional restrictions can be enforced server-side
//		* by extensions via the 'isValidEmailAddr' hook.
//		*
//		* Note that this validation doesn't 100% match RFC 2822, but is believed
//		* to be liberal enough for wide use. Some invalid addresses will still
//		* pass validation here.
//		*
//		* @since 1.18
//		*
//		* @param String $addr E-mail address
//		* @return boolean
//		*/
//		public static function validateEmail($addr) {
//			$result = null;
//			if (!Hooks::run('isValidEmailAddr', [ $addr, &$result ])) {
//				return $result;
//			}
//
//			// Please note strings below are enclosed in brackets [], this make the
//			// hyphen "-" a range indicator. Hence it is double backslashed below.
//			// See bug 26948
//			$rfc5322_atext = "a-z0-9!#$%&'*+\\-\/=?^_`{|}~";
//			$rfc1034_ldh_str = "a-z0-9\\-";
//
//			$html5_email_regexp = "/
//			^                      # start of String
//			[$rfc5322_atext\\.]+    # user part which is liberal :p
//			@                      # 'apostrophe'
//			[$rfc1034_ldh_str]+       # First domain part
//			(\\.[$rfc1034_ldh_str]+)*  # Following part prefixed with a dot
//			$                      # End of String
//			/ix"; // case Insensitive, eXtended
//
//			return (boolean)preg_match($html5_email_regexp, $addr);
//		}

	public static Hash_adp_bry html_entities;
	private static Hash_adp_bry Html_entities_new() {
		Bry_bfr tmp = Bry_bfr_.New();
		Hash_adp_bry rv = Hash_adp_bry.cs();

		Html_entities_set(rv, Xomw_html_ent.Type__alias, 8207, "רלמ", "&rlm;");
		Html_entities_set(rv, Xomw_html_ent.Type__alias, 8207, "رلم", "&rlm;");

		Html_entities_set(rv, Xomw_html_ent.Type__char, 60, "lt", "&lt;");
		Html_entities_set(rv, Xomw_html_ent.Type__char, 62, "gt", "&gt;");
		Html_entities_set(rv, Xomw_html_ent.Type__char, 38, "amp", "&amp;");
		Html_entities_set(rv, Xomw_html_ent.Type__char, 34, "quot", "&quot;");

		// List of all named character entities defined in HTML 4.01
		// https://www.w3.org/TR/html4/sgml/entities.html
		// As well as &apos; which is only defined starting in XHTML1.
		Html_entities_set(rv, tmp, "Aacute"   , 193);
		Html_entities_set(rv, tmp, "aacute"   , 225);
		Html_entities_set(rv, tmp, "Acirc"    , 194);
		Html_entities_set(rv, tmp, "acirc"    , 226);
		Html_entities_set(rv, tmp, "acute"    , 180);
		Html_entities_set(rv, tmp, "AElig"    , 198);
		Html_entities_set(rv, tmp, "aelig"    , 230);
		Html_entities_set(rv, tmp, "Agrave"   , 192);
		Html_entities_set(rv, tmp, "agrave"   , 224);
		Html_entities_set(rv, tmp, "alefsym"  , 8501);
		Html_entities_set(rv, tmp, "Alpha"    , 913);
		Html_entities_set(rv, tmp, "alpha"    , 945);
		Html_entities_set(rv, tmp, "amp"      , 38);	// XO: identical to Type__char entry; note that Type__char should be evaluated first
		Html_entities_set(rv, tmp, "and"      , 8743);
		Html_entities_set(rv, tmp, "ang"      , 8736);
		Html_entities_set(rv, tmp, "apos"     , 39); // New in XHTML & HTML 5; avoid in output for compatibility with IE.
		Html_entities_set(rv, tmp, "Aring"    , 197);
		Html_entities_set(rv, tmp, "aring"    , 229);
		Html_entities_set(rv, tmp, "asymp"    , 8776);
		Html_entities_set(rv, tmp, "Atilde"   , 195);
		Html_entities_set(rv, tmp, "atilde"   , 227);
		Html_entities_set(rv, tmp, "Auml"     , 196);
		Html_entities_set(rv, tmp, "auml"     , 228);
		Html_entities_set(rv, tmp, "bdquo"    , 8222);
		Html_entities_set(rv, tmp, "Beta"     , 914);
		Html_entities_set(rv, tmp, "beta"     , 946);
		Html_entities_set(rv, tmp, "brvbar"   , 166);
		Html_entities_set(rv, tmp, "bull"     , 8226);
		Html_entities_set(rv, tmp, "cap"      , 8745);
		Html_entities_set(rv, tmp, "Ccedil"   , 199);
		Html_entities_set(rv, tmp, "ccedil"   , 231);
		Html_entities_set(rv, tmp, "cedil"    , 184);
		Html_entities_set(rv, tmp, "cent"     , 162);
		Html_entities_set(rv, tmp, "Chi"      , 935);
		Html_entities_set(rv, tmp, "chi"      , 967);
		Html_entities_set(rv, tmp, "circ"     , 710);
		Html_entities_set(rv, tmp, "clubs"    , 9827);
		Html_entities_set(rv, tmp, "cong"     , 8773);
		Html_entities_set(rv, tmp, "copy"     , 169);
		Html_entities_set(rv, tmp, "crarr"    , 8629);
		Html_entities_set(rv, tmp, "cup"      , 8746);
		Html_entities_set(rv, tmp, "curren"   , 164);
		Html_entities_set(rv, tmp, "dagger"   , 8224);
		Html_entities_set(rv, tmp, "Dagger"   , 8225);
		Html_entities_set(rv, tmp, "darr"     , 8595);
		Html_entities_set(rv, tmp, "dArr"     , 8659);
		Html_entities_set(rv, tmp, "deg"      , 176);
		Html_entities_set(rv, tmp, "Delta"    , 916);
		Html_entities_set(rv, tmp, "delta"    , 948);
		Html_entities_set(rv, tmp, "diams"    , 9830);
		Html_entities_set(rv, tmp, "divide"   , 247);
		Html_entities_set(rv, tmp, "Eacute"   , 201);
		Html_entities_set(rv, tmp, "eacute"   , 233);
		Html_entities_set(rv, tmp, "Ecirc"    , 202);
		Html_entities_set(rv, tmp, "ecirc"    , 234);
		Html_entities_set(rv, tmp, "Egrave"   , 200);
		Html_entities_set(rv, tmp, "egrave"   , 232);
		Html_entities_set(rv, tmp, "empty"    , 8709);
		Html_entities_set(rv, tmp, "emsp"     , 8195);
		Html_entities_set(rv, tmp, "ensp"     , 8194);
		Html_entities_set(rv, tmp, "Epsilon"  , 917);
		Html_entities_set(rv, tmp, "epsilon"  , 949);
		Html_entities_set(rv, tmp, "equiv"    , 8801);
		Html_entities_set(rv, tmp, "Eta"      , 919);
		Html_entities_set(rv, tmp, "eta"      , 951);
		Html_entities_set(rv, tmp, "ETH"      , 208);
		Html_entities_set(rv, tmp, "eth"      , 240);
		Html_entities_set(rv, tmp, "Euml"     , 203);
		Html_entities_set(rv, tmp, "euml"     , 235);
		Html_entities_set(rv, tmp, "euro"     , 8364);
		Html_entities_set(rv, tmp, "exist"    , 8707);
		Html_entities_set(rv, tmp, "fnof"     , 402);
		Html_entities_set(rv, tmp, "forall"   , 8704);
		Html_entities_set(rv, tmp, "frac12"   , 189);
		Html_entities_set(rv, tmp, "frac14"   , 188);
		Html_entities_set(rv, tmp, "frac34"   , 190);
		Html_entities_set(rv, tmp, "frasl"    , 8260);
		Html_entities_set(rv, tmp, "Gamma"    , 915);
		Html_entities_set(rv, tmp, "gamma"    , 947);
		Html_entities_set(rv, tmp, "ge"       , 8805);
		Html_entities_set(rv, tmp, "gt"       , 62);
		Html_entities_set(rv, tmp, "harr"     , 8596);
		Html_entities_set(rv, tmp, "hArr"     , 8660);
		Html_entities_set(rv, tmp, "hearts"   , 9829);
		Html_entities_set(rv, tmp, "hellip"   , 8230);
		Html_entities_set(rv, tmp, "Iacute"   , 205);
		Html_entities_set(rv, tmp, "iacute"   , 237);
		Html_entities_set(rv, tmp, "Icirc"    , 206);
		Html_entities_set(rv, tmp, "icirc"    , 238);
		Html_entities_set(rv, tmp, "iexcl"    , 161);
		Html_entities_set(rv, tmp, "Igrave"   , 204);
		Html_entities_set(rv, tmp, "igrave"   , 236);
		Html_entities_set(rv, tmp, "image"    , 8465);
		Html_entities_set(rv, tmp, "infin"    , 8734);
		Html_entities_set(rv, tmp, "int"      , 8747);
		Html_entities_set(rv, tmp, "Iota"     , 921);
		Html_entities_set(rv, tmp, "iota"     , 953);
		Html_entities_set(rv, tmp, "iquest"   , 191);
		Html_entities_set(rv, tmp, "isin"     , 8712);
		Html_entities_set(rv, tmp, "Iuml"     , 207);
		Html_entities_set(rv, tmp, "iuml"     , 239);
		Html_entities_set(rv, tmp, "Kappa"    , 922);
		Html_entities_set(rv, tmp, "kappa"    , 954);
		Html_entities_set(rv, tmp, "Lambda"   , 923);
		Html_entities_set(rv, tmp, "lambda"   , 955);
		Html_entities_set(rv, tmp, "lang"     , 9001);
		Html_entities_set(rv, tmp, "laquo"    , 171);
		Html_entities_set(rv, tmp, "larr"     , 8592);
		Html_entities_set(rv, tmp, "lArr"     , 8656);
		Html_entities_set(rv, tmp, "lceil"    , 8968);
		Html_entities_set(rv, tmp, "ldquo"    , 8220);
		Html_entities_set(rv, tmp, "le"       , 8804);
		Html_entities_set(rv, tmp, "lfloor"   , 8970);
		Html_entities_set(rv, tmp, "lowast"   , 8727);
		Html_entities_set(rv, tmp, "loz"      , 9674);
		Html_entities_set(rv, tmp, "lrm"      , 8206);
		Html_entities_set(rv, tmp, "lsaquo"   , 8249);
		Html_entities_set(rv, tmp, "lsquo"    , 8216);
		Html_entities_set(rv, tmp, "lt"       , 60);
		Html_entities_set(rv, tmp, "macr"     , 175);
		Html_entities_set(rv, tmp, "mdash"    , 8212);
		Html_entities_set(rv, tmp, "micro"    , 181);
		Html_entities_set(rv, tmp, "middot"   , 183);
		Html_entities_set(rv, tmp, "minus"    , 8722);
		Html_entities_set(rv, tmp, "Mu"       , 924);
		Html_entities_set(rv, tmp, "mu"       , 956);
		Html_entities_set(rv, tmp, "nabla"    , 8711);
		Html_entities_set(rv, tmp, "nbsp"     , 160);
		Html_entities_set(rv, tmp, "ndash"    , 8211);
		Html_entities_set(rv, tmp, "ne"       , 8800);
		Html_entities_set(rv, tmp, "ni"       , 8715);
		Html_entities_set(rv, tmp, "not"      , 172);
		Html_entities_set(rv, tmp, "notin"    , 8713);
		Html_entities_set(rv, tmp, "nsub"     , 8836);
		Html_entities_set(rv, tmp, "Ntilde"   , 209);
		Html_entities_set(rv, tmp, "ntilde"   , 241);
		Html_entities_set(rv, tmp, "Nu"       , 925);
		Html_entities_set(rv, tmp, "nu"       , 957);
		Html_entities_set(rv, tmp, "Oacute"   , 211);
		Html_entities_set(rv, tmp, "oacute"   , 243);
		Html_entities_set(rv, tmp, "Ocirc"    , 212);
		Html_entities_set(rv, tmp, "ocirc"    , 244);
		Html_entities_set(rv, tmp, "OElig"    , 338);
		Html_entities_set(rv, tmp, "oelig"    , 339);
		Html_entities_set(rv, tmp, "Ograve"   , 210);
		Html_entities_set(rv, tmp, "ograve"   , 242);
		Html_entities_set(rv, tmp, "oline"    , 8254);
		Html_entities_set(rv, tmp, "Omega"    , 937);
		Html_entities_set(rv, tmp, "omega"    , 969);
		Html_entities_set(rv, tmp, "Omicron"  , 927);
		Html_entities_set(rv, tmp, "omicron"  , 959);
		Html_entities_set(rv, tmp, "oplus"    , 8853);
		Html_entities_set(rv, tmp, "or"       , 8744);
		Html_entities_set(rv, tmp, "ordf"     , 170);
		Html_entities_set(rv, tmp, "ordm"     , 186);
		Html_entities_set(rv, tmp, "Oslash"   , 216);
		Html_entities_set(rv, tmp, "oslash"   , 248);
		Html_entities_set(rv, tmp, "Otilde"   , 213);
		Html_entities_set(rv, tmp, "otilde"   , 245);
		Html_entities_set(rv, tmp, "otimes"   , 8855);
		Html_entities_set(rv, tmp, "Ouml"     , 214);
		Html_entities_set(rv, tmp, "ouml"     , 246);
		Html_entities_set(rv, tmp, "para"     , 182);
		Html_entities_set(rv, tmp, "part"     , 8706);
		Html_entities_set(rv, tmp, "permil"   , 8240);
		Html_entities_set(rv, tmp, "perp"     , 8869);
		Html_entities_set(rv, tmp, "Phi"      , 934);
		Html_entities_set(rv, tmp, "phi"      , 966);
		Html_entities_set(rv, tmp, "Pi"       , 928);
		Html_entities_set(rv, tmp, "pi"       , 960);
		Html_entities_set(rv, tmp, "piv"      , 982);
		Html_entities_set(rv, tmp, "plusmn"   , 177);
		Html_entities_set(rv, tmp, "pound"    , 163);
		Html_entities_set(rv, tmp, "prime"    , 8242);
		Html_entities_set(rv, tmp, "Prime"    , 8243);
		Html_entities_set(rv, tmp, "prod"     , 8719);
		Html_entities_set(rv, tmp, "prop"     , 8733);
		Html_entities_set(rv, tmp, "Psi"      , 936);
		Html_entities_set(rv, tmp, "psi"      , 968);
		Html_entities_set(rv, tmp, "quot"     , 34);
		Html_entities_set(rv, tmp, "radic"    , 8730);
		Html_entities_set(rv, tmp, "rang"     , 9002);
		Html_entities_set(rv, tmp, "raquo"    , 187);
		Html_entities_set(rv, tmp, "rarr"     , 8594);
		Html_entities_set(rv, tmp, "rArr"     , 8658);
		Html_entities_set(rv, tmp, "rceil"    , 8969);
		Html_entities_set(rv, tmp, "rdquo"    , 8221);
		Html_entities_set(rv, tmp, "real"     , 8476);
		Html_entities_set(rv, tmp, "reg"      , 174);
		Html_entities_set(rv, tmp, "rfloor"   , 8971);
		Html_entities_set(rv, tmp, "Rho"      , 929);
		Html_entities_set(rv, tmp, "rho"      , 961);
		Html_entities_set(rv, tmp, "rlm"      , 8207);
		Html_entities_set(rv, tmp, "rsaquo"   , 8250);
		Html_entities_set(rv, tmp, "rsquo"    , 8217);
		Html_entities_set(rv, tmp, "sbquo"    , 8218);
		Html_entities_set(rv, tmp, "Scaron"   , 352);
		Html_entities_set(rv, tmp, "scaron"   , 353);
		Html_entities_set(rv, tmp, "sdot"     , 8901);
		Html_entities_set(rv, tmp, "sect"     , 167);
		Html_entities_set(rv, tmp, "shy"      , 173);
		Html_entities_set(rv, tmp, "Sigma"    , 931);
		Html_entities_set(rv, tmp, "sigma"    , 963);
		Html_entities_set(rv, tmp, "sigmaf"   , 962);
		Html_entities_set(rv, tmp, "sim"      , 8764);
		Html_entities_set(rv, tmp, "spades"   , 9824);
		Html_entities_set(rv, tmp, "sub"      , 8834);
		Html_entities_set(rv, tmp, "sube"     , 8838);
		Html_entities_set(rv, tmp, "sum"      , 8721);
		Html_entities_set(rv, tmp, "sup"      , 8835);
		Html_entities_set(rv, tmp, "sup1"     , 185);
		Html_entities_set(rv, tmp, "sup2"     , 178);
		Html_entities_set(rv, tmp, "sup3"     , 179);
		Html_entities_set(rv, tmp, "supe"     , 8839);
		Html_entities_set(rv, tmp, "szlig"    , 223);
		Html_entities_set(rv, tmp, "Tau"      , 932);
		Html_entities_set(rv, tmp, "tau"      , 964);
		Html_entities_set(rv, tmp, "there4"   , 8756);
		Html_entities_set(rv, tmp, "Theta"    , 920);
		Html_entities_set(rv, tmp, "theta"    , 952);
		Html_entities_set(rv, tmp, "thetasym" , 977);
		Html_entities_set(rv, tmp, "thinsp"   , 8201);
		Html_entities_set(rv, tmp, "THORN"    , 222);
		Html_entities_set(rv, tmp, "thorn"    , 254);
		Html_entities_set(rv, tmp, "tilde"    , 732);
		Html_entities_set(rv, tmp, "times"    , 215);
		Html_entities_set(rv, tmp, "trade"    , 8482);
		Html_entities_set(rv, tmp, "Uacute"   , 218);
		Html_entities_set(rv, tmp, "uacute"   , 250);
		Html_entities_set(rv, tmp, "uarr"     , 8593);
		Html_entities_set(rv, tmp, "uArr"     , 8657);
		Html_entities_set(rv, tmp, "Ucirc"    , 219);
		Html_entities_set(rv, tmp, "ucirc"    , 251);
		Html_entities_set(rv, tmp, "Ugrave"   , 217);
		Html_entities_set(rv, tmp, "ugrave"   , 249);
		Html_entities_set(rv, tmp, "uml"      , 168);
		Html_entities_set(rv, tmp, "upsih"    , 978);
		Html_entities_set(rv, tmp, "Upsilon"  , 933);
		Html_entities_set(rv, tmp, "upsilon"  , 965);
		Html_entities_set(rv, tmp, "Uuml"     , 220);
		Html_entities_set(rv, tmp, "uuml"     , 252);
		Html_entities_set(rv, tmp, "weierp"   , 8472);
		Html_entities_set(rv, tmp, "Xi"       , 926);
		Html_entities_set(rv, tmp, "xi"       , 958);
		Html_entities_set(rv, tmp, "Yacute"   , 221);
		Html_entities_set(rv, tmp, "yacute"   , 253);
		Html_entities_set(rv, tmp, "yen"      , 165);
		Html_entities_set(rv, tmp, "Yuml"     , 376);
		Html_entities_set(rv, tmp, "yuml"     , 255);
		Html_entities_set(rv, tmp, "Zeta"     , 918);
		Html_entities_set(rv, tmp, "zeta"     , 950);
		Html_entities_set(rv, tmp, "zwj"      , 8205);
		Html_entities_set(rv, tmp, "zwnj"     , 8204);
		return rv;
	}
	private static void Html_entities_set(Hash_adp_bry rv, Bry_bfr tmp, String name_str, int code) {
		byte[] html_bry = tmp.Add_str_a7("&#").Add_int_variable(code).Add_byte_semic().To_bry_and_clear();
		Html_entities_set(rv, Xomw_html_ent.Type__entity, code, name_str, html_bry);
	}
	private static void Html_entities_set(Hash_adp_bry rv, byte type, int code, String name_str, String html_str) {Html_entities_set(rv, type, code, name_str, Bry_.new_u8(html_str));}
	private static void Html_entities_set(Hash_adp_bry rv, byte type, int code, String name_str, byte[] html_bry) {
		byte[] name_bry = Bry_.new_u8(name_str);
		rv.Add_if_dupe_use_1st(name_bry, new Xomw_html_ent(type, code, name_bry, html_bry));	// Add_dupe needed b/c "lt" and co. are added early; ignore subsequent call
	}
}
class Xomw_html_ent {
	public Xomw_html_ent(byte type, int code, byte[] name, byte[] html) {
		this.type = type;
		this.code = code;
		this.name = name;
		this.html = html;
	}
	public final    byte type;
	public final    int code;
	public final    byte[] name;
	public final    byte[] html;
	public static final byte Type__null = 0, Type__alias = 1, Type__char = 2, Type__entity = 3;
}
class Xomw_regex_find_domain {
	public int prot_bgn;
	public int prot_end;
	public int host_bgn;
	public int host_end;
	public int rest_bgn;
	public int rest_end;
	public boolean Match(byte[] src, int src_bgn, int src_end) {
		// Validate hostname portion
		// XO.MW.REGEX: if (preg_match('!^([^:]+:)(//[^/]+)?(.*)$!iD', $url, $matches)) {
		//   ([^:]+:)(//[^/]+)?(.*) 
		//   "protocol" + "host" + "rest"
		//   "protocol" -> ([^:]+:)     EX: "https:"    anything not-colon up to colon
		//   "host"     -> (//[^/]+)?   EX: "//abc/"    anything not-slash up to slash
		//   "rest"     -> (.*)         EX: rest"
	    //   /i : case-insensitive
	    //   /D : $ matches EOS, not NL

		// find prot; EX: "https:"
		prot_bgn = src_bgn;
		prot_end = Bry_find_.Move_fwd(src, Byte_ascii.Colon, prot_bgn, src_end);
		// exit if not found
		if (prot_end == Bry_find_.Not_found) return false;

		// find host: EX: "//a.org"
		host_bgn = prot_end;
		int double_slash_end = host_bgn + 2;
		// exit if eos
		if (double_slash_end >= src_end) return false;
		// exit if not "//"
		if (  src[host_bgn    ] != Byte_ascii.Slash
			|| src[host_bgn + 1] != Byte_ascii.Slash
			) return false;
		host_end = Bry_find_.Find_fwd(src, Byte_ascii.Slash, double_slash_end, src_end);
		// exit if not found
		if (host_end == Bry_find_.Not_found) {
			host_end = src_end;
			rest_bgn = rest_end = -1;
		}
		// exit if only "//"
		if (host_end - host_bgn == 2) return false;

		// set rest
		rest_bgn = host_end;
		rest_end = src_end;
		return true;
	}
}
class Xomw_regex_escape_invalid {
	// [\][<>"\\x00-\\x20\\x7F\|]
	public boolean Escape(Bry_bfr bfr, byte[] src, int src_bgn, int src_end) {
		boolean dirty = false;
		int cur = src_bgn;
		int prv = cur;
		while (true) {
			// eos
			if (cur == src_end) {
				if (dirty) {
					bfr.Add_mid(src, prv, src_end);
				}
				break;
			}
			boolean match = false;
			byte b = src[cur];
			switch (b) {
				case Byte_ascii.Brack_bgn:
				case Byte_ascii.Brack_end:
				case Byte_ascii.Angle_bgn:
				case Byte_ascii.Angle_end:
				case Byte_ascii.Quote:
				case Byte_ascii.Pipe:
				case Byte_ascii.Delete:
					match = true;
					break;
				default:
					if (b >= 0 && b <= 32)
						match = true;
					break;
			}
			if (match) {
				bfr.Add_mid(src, prv, cur);
				gplx.langs.htmls.encoders.Gfo_url_encoder_.Php_urlencode.Encode(bfr, src, cur, cur + 1);
				dirty = true;
				cur++;
				prv = cur;
			}
			else
				cur++;
		}
		return dirty;
	}
}
class Xomw_regex_ipv6_brack {
	public int host_bgn;
	public int host_end;
	public int segs_bgn;
	public int segs_end;
	private final    byte[] 
	  Bry__host_bgn = Bry_.new_a7("//%5B")
	, Bry__host_end = Bry_.new_a7("%5D")
	;
	public boolean Match(byte[] src, int src_bgn, int src_end) {
		//	preg_match('!^//%5B([0-9A-Fa-f:.]+)%5D((:\d+)?)$!', $host, $matches)
		//  XO.MW.REGEX:
		//    !^//%5B([0-9A-Fa-f:.]+)%5D((:\d+)?)$!
		//    "//%5B" + ("hex-dec" | [:.]) + "%5D" + numbers
		//    EX: [ABCD]:80:12
		host_bgn = src_bgn + Bry__host_bgn.length;
		// exit if no match for "//%5B"
		if (!Bry_.Match(src, src_bgn, host_bgn, Bry__host_bgn)) return false;

		// skip all [0-9A-Fa-f:.]
		host_end = host_bgn;
		while (true) {
			// exit if eos
			if (host_end == src_end) return false;
			boolean done = false;
			byte b = src[host_end];
			switch (b) {
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E: case Byte_ascii.Ltr_F:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e: case Byte_ascii.Ltr_f:
				case Byte_ascii.Colon:
				case Byte_ascii.Dot:
					host_end++;
					break;
				case Byte_ascii.Percent:
					// matches "%5D"
					segs_bgn = host_end + Bry__host_end.length;
					if (  Bry_.Match(src, host_end, segs_bgn, Bry__host_end)
						&& host_end - host_bgn > 0) // host can't be 0-len; EX: "//%5B%5D"
						done = true;
					// exit if no match
					else {
						return false;
					}
					break;
				// exit if no match
				default: {
					return false;
				}
			}
			if (done) break;
		}
		// skip all (:\d+)
		segs_end = segs_bgn;
		while (true) {
			// stop if eos
			if (segs_end == src_end) return true;

			// check if ":"
			if (src[segs_end] == Byte_ascii.Colon) {
				int num_bgn = segs_end + 1;
				int num_end = Bry_find_.Find_fwd_while_num(src, num_bgn, src_end);
				// exit if no nums found; EX:"[ABC]:80:"
				if (num_end == num_bgn) {
					return false;
				}
				segs_end = num_end;
			}
			// exit if seg doesn't start with ":"
			else {
				return false;
			}
		}
	}
}
interface Xomw_regex_url_char_cbk {
	boolean When_ent(Bry_bfr bfr, byte[] name);
	boolean When_dec(Bry_bfr bfr, byte[] name);
	boolean When_hex(Bry_bfr bfr, byte[] name);
	boolean When_amp(Bry_bfr bfr);
}
class Xomw_regex_url_char_cbk__normalize implements Xomw_regex_url_char_cbk {
	// XO.MW.PORTED
	//	$ret = null;
	//	if ( $matches[1] != '' ) {
	//		$ret = Sanitizer::normalizeEntity( $matches[1] );
	//	} elseif ( $matches[2] != '' ) {
	//		$ret = Sanitizer::decCharReference( $matches[2] );
	//	} elseif ( $matches[3] != '' ) {
	//		$ret = Sanitizer::hexCharReference( $matches[3] );
	//	}
	//	if ( is_null( $ret ) ) {
	//		return htmlspecialchars( $matches[0] );
	//	} else {
	//		return $ret;
	//	}
	public boolean When_ent(Bry_bfr bfr, byte[] name) {  // XO.MW:normalizeEntity
		// If the named entity is defined in the HTML 4.0/XHTML 1.0 DTD,
		// return the equivalent numeric entity reference (except for the core &lt;
		// &gt; &amp; &quot;). If the entity is a MediaWiki-specific alias, returns
		// the HTML equivalent. Otherwise, returns HTML-escaped text of
		// pseudo-entity source (eg &amp;foo;)
		Object o = XomwSanitizer.html_entities.Get_by_bry(name);
		if (o == null) {
			bfr.Add_str_a7("&amp;").Add(name).Add_byte_semic();
			return false;
		}
		else {
			Xomw_html_ent entity = (Xomw_html_ent)o;
			bfr.Add(entity.html);
			return true;
		}
	}
	public boolean When_dec(Bry_bfr bfr, byte[] name) {  // XO.MW:decCharReference
		int point = Bry_.To_int_or(name, -1);
		if (XomwSanitizer.validateCodepoint(point)) {
			bfr.Add_str_a7("&#").Add_int_variable(point).Add_byte_semic();
			return true;
		}
		return false;
	}
	public boolean When_hex(Bry_bfr bfr, byte[] name) {  // XO.MW:hexCharReference
		int point = Hex_utl_.Parse_or(name, -1);
		if (XomwSanitizer.validateCodepoint(point)) {
			bfr.Add_str_a7("&#x");
			Hex_utl_.Write_bfr(bfr, Bool_.Y, point);	// sprintf('&#x%x;', $point)
			bfr.Add_byte_semic();
			return true;
		}
		return false;
	}
	public boolean When_amp(Bry_bfr bfr) {
		bfr.Add(Gfh_entity_.Amp_bry);       // transform "&" to "&amp;"
		return true;
	}
}
class Xomw_regex_url_char_cbk__decode implements Xomw_regex_url_char_cbk {
	//	static function decodeCharReferencesCallback($matches) {
	//		if ($matches[1] != '') {
	//			return Sanitizer::decodeEntity($matches[1]);
	//		} elseif ($matches[2] != '') {
	//			return Sanitizer::decodeChar(intval($matches[2]));
	//		} elseif ($matches[3] != '') {
	//			return Sanitizer::decodeChar(hexdec($matches[3]));
	//		}
	//		# Last case should be an ampersand by itself
	//		return $matches[0];
	//	}
	public boolean When_ent(Bry_bfr bfr, byte[] name) {// XO.MW:decodeEntity
		// If the named entity is defined in the HTML 4.0/XHTML 1.0 DTD,
		// return the UTF-8 encoding of that character. Otherwise, returns
		// pseudo-entity source (eg "&foo;")
		Object o = XomwSanitizer.html_entities.Get_by_bry(name);
		if (o == null) {
			bfr.Add_byte(Byte_ascii.Amp).Add(name).Add_byte_semic();
		}
		else {
			Xomw_html_ent entity = (Xomw_html_ent)o;
			bfr.Add(gplx.core.intls.Utf16_.Encode_int_to_bry(entity.code));
		}
		return true;
	}
	public boolean When_dec(Bry_bfr bfr, byte[] name) {
		return Decode_char(bfr, Bry_.To_int(name));
	}
	public boolean When_hex(Bry_bfr bfr, byte[] name) {
		return Decode_char(bfr, gplx.core.encoders.Hex_utl_.Parse_or(name, 0, name.length, -1));
	}
	public boolean When_amp(Bry_bfr bfr) {
		bfr.Add_byte(Byte_ascii.Amp);
		return true;
	}
	private boolean Decode_char(Bry_bfr bfr, int point) {// XO.MW:decodeChar
		// Return UTF-8 String for a codepoint if that is a valid
		// character reference, otherwise U+FFFD REPLACEMENT CHARACTER.
		if (XomwSanitizer.validateCodepoint(point)) {
			bfr.Add(gplx.core.intls.Utf16_.Encode_int_to_bry(point));
		}
		else {
			bfr.Add(Utf8_replacement_char);
		}
		return true;
	}
	private static final    byte[] Utf8_replacement_char = Bry_.New_by_ints(255, 253); // 0xfffd 
}
class Xomw_regex_url_char {
	// Regular expression to match various types of character references in
	// Sanitizer::normalizeCharReferences and Sanitizer::decodeCharReferences
	// static final CHAR_REFS_REGEX =
	//	'/&([A-Za-z0-9\x80-\xff]+);
	//	|&\#([0-9]+);
	//	|&\#[xX]([0-9A-Fa-f]+);
	//	|(&)/x';
	public Xomw_regex_url_char() {
		// assert static structs
		if (Normalize__dec == null) {
			synchronized (XomwSanitizer.class) {
				Normalize__dec = Bool_ary_bldr.New_u8().Set_rng(Byte_ascii.Num_0, Byte_ascii.Num_9).To_ary();
				Normalize__hex = Bool_ary_bldr.New_u8()
					.Set_rng(Byte_ascii.Num_0, Byte_ascii.Num_9)
					.Set_rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z)
					.Set_rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z)
					.To_ary();
				Normalize__ent = Bool_ary_bldr.New_u8()
					.Set_rng(Byte_ascii.Num_0, Byte_ascii.Num_9)
					.Set_rng(Byte_ascii.Ltr_A, Byte_ascii.Ltr_Z)
					.Set_rng(Byte_ascii.Ltr_a, Byte_ascii.Ltr_z)
					.Set_rng(128, 255)
					.To_ary();
			}
		}
	}
	public byte[] Replace_by_cbk(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end, Xomw_regex_url_char_cbk cbk) {
		// XO.BRY_BFR
		boolean dirty = false;
		int cur = src_bgn;
		boolean called_by_bry = bfr == null;

		while (true) {
			// search for "&"
			int find_bgn = Bry_find_.Find_fwd(src, Byte_ascii.Amp, cur);
			if (find_bgn == Bry_find_.Not_found) {	// "&" not found; exit
				if (dirty)
					bfr.Add_mid(src, cur, src_end);
				break;
			}
			int ent_bgn = find_bgn + 1;	// +1 to skip &

			// get regex; (a) dec (&#09;); (b) hex (&#xFF;); (c) entity (&alpha;);
			boolean[] regex = null;
			// check for #;
			if (ent_bgn < src_end && src[ent_bgn] == Byte_ascii.Hash) {
				ent_bgn++;
				if (ent_bgn < src_end) {
					byte nxt = src[ent_bgn];
					// check for x
					if (nxt == Byte_ascii.Ltr_X || nxt == Byte_ascii.Ltr_x) {
						ent_bgn++;
						regex = Normalize__hex;
					}
				}
				if (regex == null)
					regex = Normalize__dec;
			}
			else {
				regex = Normalize__ent;
			}

			// keep looping until invalid regex
			int ent_end = ent_bgn;
			int b = Byte_ascii.Null;
			for (int i = ent_bgn; i < src_end; i++) {
				b = src[i] & 0xFF; // PATCH.JAVA:need to convert to unsigned byte
				if (regex[b])
					ent_end++;
				else
					break;
			}

			// mark dirty; can optimize later by checking if "&lt;" already exists
			dirty = true;
			if (bfr == null) bfr = Bry_bfr_.New();
			bfr.Add_mid(src, cur, find_bgn); // add everything before &

			// invalid <- regex ended, but not at semic
			if (b != Byte_ascii.Semic) {
				cbk.When_amp(bfr);
				cur = find_bgn + 1;                 // position after "&"
				continue;
			}

			// do normalization
			byte[] name = Bry_.Mid(src, ent_bgn, ent_end);
			boolean ret = false;
			if      (regex == Normalize__ent) {
				cbk.When_ent(bfr, name);
				ret = true;
			}
			else if (regex == Normalize__dec) {
				ret = cbk.When_dec(bfr, name);
			}
			else if (regex == Normalize__hex) {
				ret = cbk.When_hex(bfr, name);
			}
			if (!ret) {
				cbk.When_amp(bfr);
				cur = find_bgn + 1;                 // position after "&"
				continue;
			}

			cur = ent_end + 1;	// +1 to position after ";"
		}

		// XO.BRY_BFR
		if (dirty) {
			if (called_by_bry)
				return bfr.To_bry_and_clear();
			else
				return Bry_.Empty;
		}
		else {
			if (called_by_bry) {
				if (src_bgn == 0 && src_end == src.length)
					return src;
				else
					return Bry_.Mid(src, src_bgn, src_end);
			}
			else {
				if (lone_bfr)
					bfr.Add_mid(src, src_bgn, src_end);
				return null;
			}
		}
	}
	private static boolean[] Normalize__dec, Normalize__hex, Normalize__ent; 
}
