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
package gplx.xowa.mws.parsers.magiclinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.core.net.*;
import gplx.langs.phps.utls.*;
//	public class Xomw_magiclinks_wkr {
//		private final    Btrie_slim_mgr regex_trie = Btrie_slim_mgr.ci_a7(); // NOTE: must be ci to handle protocols; EX: "https:" and "HTTPS:"
//		private final    Btrie_rv trv = new Btrie_rv();
//		public Xomw_magiclinks_wkr() {
//		}
//		private static byte[] Tag__anch__rhs, Prefix__rfc, Prefix__pmid;
//
//		private static final byte Space__tab = 1, Space__nbsp_ent = 2, Space__nbsp_dec = 3, Space__nbsp_hex = 4;
//		private static Btrie_slim_mgr space_trie;
//		//	static final SPACE_NOT_NL = '(?:\t|&nbsp;|&\#0*160;|&\#[Xx]0*[Aa]0;|\p{Zs})';
////		public void Test() {
////			regex.Add("\t", Space__tab);
////			regex.Add("&nbsp;", Space__nbsp__ent);
////			regex.Add(Regex.Make("&#").Star("0").Add("160;"), Space__nbsp__dec);
////			regex.Add(Regex.Make("&#").Brack("X", "x").Star("0").Brack("A", "a").Add("0"), Space__nbsp__hex);
////		}
//		public int Find_fwd_space(byte[] src, int cur, int src_end) {
//			return -1;
//		}
//
//		private static final byte Regex__anch = 1, Regex__elem = 2, Regex__free = 3, Regex__rfc = 5, Regex__isbn = 6, Regex__pmid = 7;
//		public void Init_by_wiki() {
//			regex_trie.Add_str_byte("<a", Regex__anch);
//			regex_trie.Add_str_byte("<" , Regex__elem);
//			
//			Gfo_protocol_itm[] protocol_ary = Gfo_protocol_itm.Ary();
//			int protocol_len = protocol_ary.length;
//			for (int i = 0; i < protocol_len; i++) {
//				Gfo_protocol_itm itm = protocol_ary[i];
//				regex_trie.Add_bry_byte(itm.Key_w_colon_bry(), Regex__free);
//			}
//			regex_trie.Add_str_byte("RFC " , Regex__rfc);
//			regex_trie.Add_str_byte("PMID " , Regex__rfc);
//			regex_trie.Add_str_byte("ISBN ", Regex__rfc);
//
//			if (Tag__anch__rhs == null) {
//				synchronized (Type_adp_.ClassOf_obj(this)) {
//					Tag__anch__rhs = Bry_.new_a7("</a>");
//					Prefix__rfc = Bry_.new_a7("RFC");
//					Prefix__pmid = Bry_.new_a7("PMID");
//					space_trie = Btrie_slim_mgr.ci_a7()
//					.Add_str_byte("\t", Space__tab)
//					.Add_str_byte("&nbsp;", Space__nbsp_ent)
//					.Add_str_byte("&#", Space__nbsp_dec)
//					.Add_str_byte("&x", Space__nbsp_hex)
//					;
//				}
//			}
//		}
//
//		// Replace special strings like "ISBN xxx" and "RFC xxx" with
//		// magic external links.
//		public void Do_magic_links(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {
//			// XO.PBFR
//			Bry_bfr src_bfr = pbfr.Src();
//			byte[] src = src_bfr.Bfr();
//			int src_bgn = 0;
//			int src_end = src_bfr.Len();
//			Bry_bfr bfr = pbfr.Trg();
//
//			int cur = src_bgn;
//			int prv = cur;
//			boolean dirty = true;
//			while (true) {
//				if (cur == src_end) {
//					if (dirty)
//						bfr.Add_mid(src, prv, src_end);
//					break;
//				}
//
//				byte b = src[cur];
//				Object o = regex_trie.Match_at_w_b0(trv, b, src, cur, src_end);
//				// current byte doesn't look like magiclink; continue;
//				if (o == null) {
//					cur++;
//					continue;
//				}
//				// looks like magiclink; do additional processing
//				byte regex_tid = ((Byte_obj_ref)o).Val();
//				int trv_pos = trv.Pos();
//				int nxt_pos = trv_pos;
//				boolean regex_valid = true;
//				switch (regex_tid) {
//					case Regex__anch:	// (<a[ \t\r\n>].*?</a>) |      // m[1]: Skip link text
//						if (trv_pos < src_end) {
//							// find ws in "[ \t\r\n>]"
//							byte ws_byte = src[cur];
//							switch (ws_byte) {
//								case Byte_ascii.Space:
//								case Byte_ascii.Tab:
//								case Byte_ascii.Cr:
//								case Byte_ascii.Nl:
//									break;
//								default:
//									regex_valid = false;
//									break;
//							}
//							if (regex_valid) {
//								// find </a>
//								nxt_pos++;
//								int anch_end = Bry_find_.Find_fwd(src, Tag__anch__rhs, nxt_pos, src_end);
//								if (anch_end == Bry_find_.Not_found) {
//									regex_valid = false;
//								}
//								else {
//									cur = anch_end + Tag__anch__rhs.length;
//								}
//							}
//						}
//						else {
//							regex_valid = false;
//						}
//						break;
//					case Regex__elem: // (<.*?>) |                    // m[2]: Skip stuff inside
//						// just find ">"
//						int elem_end = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, nxt_pos, src_end);
//						if (elem_end == Bry_find_.Not_found)
//							regex_valid = false;
//						else
//							cur = elem_end + 1;
//						break;
//					case Regex__free:
//						// addr; urlchar
//						break;
//					case Regex__rfc:
//					case Regex__pmid:
//						// byte[] prefix = regex == Regex__rfc ? Prefix__rfc : Prefix__pmid;
//						// match previous for case sensitivity
////						if (Bry_.Eq(src, trv_pos - prefix.length - 1, trv_pos - 1, prefix)) {
////
////						}
////						else {
////							regex_valid = false;
////						}
//						break;
//				}
//				
////				'!(?:                            // Start cases
////					(<a[ \t\r\n>].*?</a>) |      // m[1]: Skip link text
////					(<.*?>) |                    // m[2]: Skip stuff inside
////												 //       HTML elements' . "
////					(\b(?i:$prots)($addr$urlChar*)) | // m[3]: Free external links
////												 // m[4]: Post-protocol path
////					\b(?:RFC|PMID) $spaces       // m[5]: RFC or PMID, capture number
////						([0-9]+)\b |
////					\bISBN $spaces (            // m[6]: ISBN, capture number
////						(?: 97[89] $spdash?)?   //  optional 13-digit ISBN prefix
////						(?: [0-9]  $spdash?){9} //  9 digits with opt. delimiters
////						[0-9Xx]                  //  check digit
////					)\b
//
//			}
//			if (dirty)
//				pbfr.Switch();

//			$prots = wfUrlProtocolsWithoutProtRel();
//			$urlChar = self::EXT_LINK_URL_CLASS;
//			$addr = self::EXT_LINK_ADDR;
//			$space = self::SPACE_NOT_NL; //  non-newline space
//			$spdash = "(?:-|$space)"; // a dash or a non-newline space
//			$spaces = "$space++"; // possessive match of 1 or more spaces
//			$text = preg_replace_callback(
//				'!(?:                            // Start cases
//					(<a[ \t\r\n>].*?</a>) |      // m[1]: Skip link text
//					(<.*?>) |                    // m[2]: Skip stuff inside
//												 //       HTML elements' . "
//					(\b(?i:$prots)($addr$urlChar*)) | // m[3]: Free external links
//												 // m[4]: Post-protocol path
//					\b(?:RFC|PMID) $spaces       // m[5]: RFC or PMID, capture number
//						([0-9]+)\b |
//					\bISBN $spaces (            // m[6]: ISBN, capture number
//						(?: 97[89] $spdash?)?   //  optional 13-digit ISBN prefix
//						(?: [0-9]  $spdash?){9} //  9 digits with opt. delimiters
//						[0-9Xx]                  //  check digit
//					)\b
//				)!xu", [ &$this, 'magicLinkCallback' ], $text);
//			return $text;
//		}

//		public function magicLinkCallback($m) {
//			if (isset($m[1]) && $m[1] !== '') {
//				// Skip anchor
//				return $m[0];
//			} else if (isset($m[2]) && $m[2] !== '') {
//				// Skip HTML element
//				return $m[0];
//			} else if (isset($m[3]) && $m[3] !== '') {
//				// Free external link
//				return $this->makeFreeExternalLink($m[0], strlen($m[4]));
//			} else if (isset($m[5]) && $m[5] !== '') {
//				// RFC or PMID
//				if (substr($m[0], 0, 3) === 'RFC') {
//					if (!$this->mOptions->getMagicRFCLinks()) {
//						return $m[0];
//					}
//					$keyword = 'RFC';
//					$urlmsg = 'rfcurl';
//					$cssClass = 'mw-magiclink-rfc';
//					$trackingCat = 'magiclink-tracking-rfc';
//					$id = $m[5];
//				} else if (substr($m[0], 0, 4) === 'PMID') {
//					if (!$this->mOptions->getMagicPMIDLinks()) {
//						return $m[0];
//					}
//					$keyword = 'PMID';
//					$urlmsg = 'pubmedurl';
//					$cssClass = 'mw-magiclink-pmid';
//					$trackingCat = 'magiclink-tracking-pmid';
//					$id = $m[5];
//				} else {
//					throw new MWException(__METHOD__ . ': unrecognised match type "' .
//						substr($m[0], 0, 20) . '"');
//				}
//				$url = wfMessage($urlmsg, $id)->inContentLanguage()->text();
//				$this->addTrackingCategory($trackingCat);
//				return Linker::makeExternalLink($url, "{$keyword} {$id}", true, $cssClass, [], $this->mTitle);
//			} else if (isset($m[6]) && $m[6] !== ''
//				&& $this->mOptions->getMagicISBNLinks()
//			) {
//				// ISBN
//				$isbn = $m[6];
//				$space = self::SPACE_NOT_NL; //  non-newline space
//				$isbn = preg_replace("/$space/", ' ', $isbn);
//				$num = strtr($isbn, [
//					'-' => '',
//					' ' => '',
//					'x' => 'X',
//				]);
//				$this->addTrackingCategory('magiclink-tracking-isbn');
//				return $this->getLinkRenderer()->makeKnownLink(
//					SpecialPage::getTitleFor('Booksources', $num),
//					"ISBN $isbn",
//					[
//						'class' => '@gplx.Internal protected mw-magiclink-isbn',
//						'title' => false // suppress title attribute
//					]
//				);
//			} else {
//				return $m[0];
//			}

		// Make a free external link, given a user-supplied URL
//			public void Make_free_external_link(byte[] url, int num_post_proto) {
//				byte[] trail = Bry_.Empty;

			// The characters '<' and '>' (which were escaped by
			// removeHTMLtags()) should not be included in
			// URLs, per RFC 2396.
			// Make &nbsp; terminate a URL as well (bug T84937)
//				$m2 = [];
//				if (preg_match(
//					'/&(lt|gt|nbsp|#x0*(3[CcEe]|[Aa]0)|#0*(60|62|160));/',
//					$url,
//					$m2,
//					PREG_OFFSET_CAPTURE
//				)) {
//					trail = substr($url, $m2[0][1]) . $trail;
//					$url = substr($url, 0, $m2[0][1]);
//				}

			// Move trailing punctuation to $trail
//				$sep = ',;\.:!?';
			// If there is no left bracket, then consider right brackets fair game too
//				if (strpos($url, '(') === false) {
//					$sep .= ')';
//				}

//				$urlRev = strrev($url);
//				$numSepChars = strspn($urlRev, $sep);
			// Don't break a trailing HTML entity by moving the ; into $trail
			// This is in hot code, so use substr_compare to avoid having to
			// create a new String Object for the comparison
//				if ($numSepChars && substr_compare($url, ";", -$numSepChars, 1) === 0) {
				// more optimization: instead of running preg_match with a $
				// anchor, which can be slow, do the match on the reversed
				// String starting at the desired offset.
				// un-reversed regexp is: /&([a-z]+|#x[\da-f]+|#\d+)$/i
//					if (preg_match('/\G([a-z]+|[\da-f]+x#|\d+#)&/i', $urlRev, $m2, 0, $numSepChars)) {
//						$numSepChars--;
//					}
//				}
//				if ($numSepChars) {
//					$trail = substr($url, -$numSepChars) . $trail;
//					$url = substr($url, 0, -$numSepChars);
//				}

			// Verify that we still have a real URL after trail removal, and
			// not just lone protocol
//				if (strlen($trail) >= $numPostProto) {
//					return $url . $trail;
//				}

//				$url = Sanitizer::cleanUrl($url);

			// Is this an external image?
//				$text = $this->maybeMakeExternalImage($url);
//				if ($text === false) {
				// Not an image, make a link
//					$text = Linker::makeExternalLink($url,
//						$this->getConverterLanguage()->markNoConversion($url, true),
//						true, 'free',
//						$this->getExternalLinkAttribs($url), $this->mTitle);
				// Register it in the output Object...
				// Replace unnecessary URL escape codes with their equivalent characters
//					$pasteurized = self::normalizeLinkUrl($url);
//					$this->mOutput->addExternalLink($pasteurized);
//				}
//				return $text . $trail;
//			}
//		}
//	}
