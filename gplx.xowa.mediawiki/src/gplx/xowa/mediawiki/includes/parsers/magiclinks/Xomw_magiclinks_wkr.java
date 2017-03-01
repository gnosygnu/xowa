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
package gplx.xowa.mediawiki.includes.parsers.magiclinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.core.primitives.*; import gplx.core.btries.*; import gplx.core.net.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
import gplx.langs.regxs.*;
// TODO.XO: this->getConverterLanguage()->markNoConversion($url, true),
public class Xomw_magiclinks_wkr {
	private final    Btrie_slim_mgr regex_trie = Btrie_slim_mgr.ci_a7(); // NOTE: must be ci to handle protocols; EX: "https:" and "HTTPS:"
	private final    Btrie_rv trv = new Btrie_rv();
	private static byte[] Tag__anch__rhs;
	private boolean[] url_separators;
	private static Xomw_regex_link_interrupt regex_link_interrupt;
	private final    XomwParserIface parser;
	private final    Xomw_regex_boundary regex_boundary;
	private final    Xomw_regex_url regex_url;
	private final    XomwSanitizer sanitizer;
	private final    XomwLinker linker;
	private final    Xomw_atr_mgr atrs = new Xomw_atr_mgr();
	private byte[] page_title;

	private static final byte Regex__anch = 1, Regex__elem = 2, Regex__free = 3;
	public Xomw_magiclinks_wkr(XomwParserIface parser, XomwSanitizer sanitizer, XomwLinker linker, Xomw_regex_boundary regex_boundary, Xomw_regex_url regex_url) {
		this.parser = parser;
		this.sanitizer = sanitizer;
		this.linker = linker;
		this.regex_boundary = regex_boundary;
		this.regex_url = regex_url;

		// ',;\.:!?'
		url_separators = Bool_ary_bldr.New_u8()
			.Set_many(Byte_ascii.Comma,Byte_ascii.Semic, Byte_ascii.Dot, Byte_ascii.Colon, Byte_ascii.Bang, Byte_ascii.Question)
			.To_ary();

		if (Tag__anch__rhs == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				Tag__anch__rhs = Bry_.new_a7("</a>");
				regex_link_interrupt = new Xomw_regex_link_interrupt();
			}
		}
	}
	public void Init_by_wiki() {
		regex_trie.Add_str_byte("<a", Regex__anch);
		regex_trie.Add_str_byte("<" , Regex__elem);
		
		Gfo_protocol_itm[] protocol_ary = Gfo_protocol_itm.Ary();
		int protocol_len = protocol_ary.length;
		for (int i = 0; i < protocol_len; i++) {
			Gfo_protocol_itm itm = protocol_ary[i];
			regex_trie.Add_bry_byte(itm.Text_bry(), Regex__free);
		}
	}

	// Replace special strings like "ISBN xxx" and "RFC xxx" with
	// magic external links.
	public void doMagicLinks(XomwParserCtx pctx, XomwParserBfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();

		int cur = src_bgn;
		int prv = cur;
		boolean dirty = true;
		// PORTED.REGEX: handle below
		// XO.MW.UNSUPPORTED.OBSOLETE: not handling RFC|PMID|ISBN b/c of upcoming obsolescence: https://www.mediawiki.org/wiki/Requests_for_comment/Future_of_magic_links
		//'!(?:                                    // Start cases
		//	(<a[ \t\r\n>].*?</a>) |                // m[1]: Skip link text
		//	(<.*?>) |                              // m[2]: Skip stuff inside
		//                                         //       HTML elements' . "
		//	(\b(?i:$prots)($addr$urlChar*)) |      // m[3]: Free external links
		//                                         // m[4]: Post-protocol path
		//	\b(?:RFC|PMID) $spaces                 // m[5]: RFC or PMID, capture number
		//		([0-9]+)\b |
		//	\bISBN $spaces (                       // m[6]: ISBN, capture number
		//		(?: 97[89] $spdash?)?              //  optional 13-digit ISBN prefix
		//		(?: [0-9]  $spdash?){9}            //  9 digits with opt. delimiters
		//		[0-9Xx]                            //  check digit
		//	)\b
		while (true) {
			if (cur == src_end) {
				if (dirty)
					bfr.Add_mid(src, prv, src_end);
				break;
			}

			byte b = src[cur];
			Object o = regex_trie.Match_at_w_b0(trv, b, src, cur, src_end);
			// current byte doesn't look like magiclink; continue;
			if (o == null) {
				cur++;
				continue;
			}

			// looks like magiclink; do additional processing
			byte regex_tid = ((Byte_obj_val)o).Val();
			int hook_bgn = cur;
			int hook_end = trv.Pos();
			int tmp_pos = hook_end;
			boolean regex_valid = true;
			switch (regex_tid) {
				case Regex__anch:	// (<a[ \t\r\n>].*?</a>) |      // m[1]: Skip link text
					if (tmp_pos < src_end) {
						// find "[ \t\r\n>]" after "<a"; i.e.: don't match "<ab" or "<ac", etc..
						byte ws_byte = src[tmp_pos];
						switch (ws_byte) {
							// next char after "<a" is ws -> valid
							case Byte_ascii.Space:
							case Byte_ascii.Tab:
							case Byte_ascii.Cr:
							case Byte_ascii.Nl:
								break;
							// next char after "<a" is not ws -> invalid
							default:
								regex_valid = false;
								break;
						}
						if (regex_valid) {
							// find </a>
							tmp_pos++;
							int anch_end = Bry_find_.Find_fwd(src, Tag__anch__rhs, tmp_pos, src_end);
							// </a> not found -> invalid
							if (anch_end == Bry_find_.Not_found) {
								regex_valid = false;
							}
							// </a> found -> valid; set cur to after "</a>"
							else {
								cur = anch_end + Tag__anch__rhs.length;
							}
						}
					}
					else {
						regex_valid = false;
					}
					break;
				case Regex__elem: // (<.*?>) |                    // m[2]: Skip stuff inside
					// just find ">"
					tmp_pos = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, tmp_pos, src_end);
					// > not found -> invalid
					if (tmp_pos == Bry_find_.Not_found) {
						regex_valid = false;
					}
					// > found -> valid; set cur to after ">"
					else {
						cur = tmp_pos + 1;
					}
					break;
				case Regex__free:
					// make sure that protocol starts at word bound; EX: "ahttp://a.org" should be invalid
					if (regex_boundary.Is_boundary_prv(src, hook_bgn)) {
						// skip forward until invalid url char
						tmp_pos = regex_url.Find_fwd_while(trv, src, tmp_pos, src_end);
						// no url chars found -> invalid
						if (tmp_pos == hook_end) {
							regex_valid = false;
						}
						// url chars found -> valid; set cur to 1st invalid url-char;
						else {
							cur = tmp_pos;
						}
					}
					else
						regex_valid = false;
					break;
			}
			// regex is invalid; advance by 1 and continue;
			if (!regex_valid) {
				cur++;
			}
			// regex is valid
			else {
				// handle free
				if (regex_tid == Regex__free) {
					this.page_title = pctx.Page_title().getPrefixedDBkey();
                        dirty = true;
					bfr.Add_mid(src, prv, hook_bgn);
					byte[] url = Bry_.Mid(src, hook_bgn, cur);
					int num_post_proto = cur - hook_end; // get length of url without proto; EX: "http://a.org" should be 5 ("a.org")
                        this.Make_free_external_link(bfr, url, num_post_proto);
					prv = cur;
				}
				// "<a " and "<" just need to be ignored; note that they already update cur so noop
				else {
				}
			}
		}
		if (dirty) {
			pbfr.Switch();
		}
	}

	// Make a free external link, given a user-supplied URL
	public void Make_free_external_link(Bry_bfr bfr, byte[] url, int num_post_proto) {
		byte[] trail = Bry_.Empty;

		// The characters '<' and '>' (which were escaped by
		// removeHTMLtags()) should not be included in
		// URLs, per RFC 2396.
		// Make &nbsp; terminate a URL as well (bug T84937)
		int separator_bgn = regex_link_interrupt.Find(trv, url, 0, url.length);
		if (separator_bgn != Bry_find_.Not_found) {
			trail = Bry_.Mid(url, separator_bgn);
			url = Bry_.Mid(url, 0, separator_bgn);
		}

		// Move trailing punctuation to $trail
		int url_len = url.length;
		// If there is no left bracket, then consider right brackets fair game too
		// XO.MW: if (strpos($url, '(') === false) {$sep .= ')';}
		url_separators[Byte_ascii.Paren_end] = Bry_find_.Find_fwd(url, Byte_ascii.Paren_bgn, 0, url_len) == Bry_find_.Not_found;
		
		int num_sep_chars = XophpString.strspn_bwd__ary(url, url_separators, url_len, -1);
		// Don't break a trailing HTML entity by moving the ; into $trail
		// This is in hot code, so use substr_compare to avoid having to
		// create a new String Object for the comparison
		// XO.MW.NOTE: ignore semic if part of entity; EX: "http://a.org&apos;!."
		if (num_sep_chars > 0 && XophpString.substr_byte(url, -num_sep_chars) == Byte_ascii.Semic) {
			// more optimization: instead of running preg_match with a $
			// anchor, which can be slow, do the match on the reversed
			// String starting at the desired offset.
			// un-reversed regexp is: /&([a-z]+|#x[\da-f]+|#\d+)$/i
			// if (preg_match('/\G([a-z]+|[\da-f]+x#|\d+#)&/i', $urlRev, $m2, 0, num_sep_chars)) {
			if (Xomw_regex_html_entity.Match_bwd(url, url_len - num_sep_chars, 0)) {
				num_sep_chars--;
			}
		}

		if (num_sep_chars > 0) {
			trail = Bry_.Add(XophpString.substr(url, -num_sep_chars), trail);
			url = XophpString.substr(url, 0, -num_sep_chars);
		}

		// Verify that we still have a real URL after trail removal, and
		// not just lone protocol
		if (trail.length >= num_post_proto) {
			bfr.Add_bry_many(url, trail);
			return;
		}

		url = sanitizer.cleanUrl(url);

		// XO.MW.UNSUPPORTED.NON-WMF: not supporting images from freefrom url; (EX: "http://a.org/image.png" -> "<img>"); haven't seen this used on WMF wikis
		// Is this an external image?			
		byte[] text = null; // $this->maybeMakeExternalImage($url);
		if (text == null) {
			// Not an image, make a link
			linker.makeExternalLink(bfr, url
				, url	// $this->getConverterLanguage()->markNoConversion($url, true),
				, true, Bry_.new_a7("free")
				, parser.getExternalLinkAttribs(atrs)
				, page_title);

			// XO.MW.UNSUPPORTED.HOOK: registers link for processing by other extensions?
			// Register it in the output Object...
			// Replace unnecessary URL escape codes with their equivalent characters
			// $pasteurized = self::normalizeLinkUrl($url);
			// $this->mOutput->addExternalLink($pasteurized);
		}
		bfr.Add(trail);
	}
}
class Xomw_regex_html_entity {
	// if (preg_match('/\G([a-z]+|[\da-f]+x#|\d+#)&/i', $urlRev, $m2, 0, num_sep_chars)) {
	// REGEX: (letters | hex + "#" | dec + "x#") + "&"
	// \G means "stop if matching breaks"; so, using a reversed example, "http://&#amp;&#!lt;" will not match "&#amp;" b/c "&#!lt;" breaks match
	//   http://www.php.net/manual/en/regexp.reference.escape.php
	//   http://stackoverflow.com/questions/14897949/what-is-the-use-of-g-anchor-in-regex
	public static boolean Match_bwd(byte[] src, int src_bgn, int src_end) {
		int cur = src_bgn - 1;
		int numbers = 0;
		int letters = 0;
		while (cur >= src_end) {
			int b_bgn = gplx.core.intls.Utf8_.Get_prv_char_pos0_old(src, cur);
			switch (src[b_bgn]) {
				case Byte_ascii.Ltr_A: case Byte_ascii.Ltr_B: case Byte_ascii.Ltr_C: case Byte_ascii.Ltr_D: case Byte_ascii.Ltr_E:
				case Byte_ascii.Ltr_F: case Byte_ascii.Ltr_G: case Byte_ascii.Ltr_H: case Byte_ascii.Ltr_I: case Byte_ascii.Ltr_J:
				case Byte_ascii.Ltr_K: case Byte_ascii.Ltr_L: case Byte_ascii.Ltr_M: case Byte_ascii.Ltr_N: case Byte_ascii.Ltr_O:
				case Byte_ascii.Ltr_P: case Byte_ascii.Ltr_Q: case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_S: case Byte_ascii.Ltr_T:
				case Byte_ascii.Ltr_U: case Byte_ascii.Ltr_V: case Byte_ascii.Ltr_W: case Byte_ascii.Ltr_X: case Byte_ascii.Ltr_Y: case Byte_ascii.Ltr_Z:
				case Byte_ascii.Ltr_a: case Byte_ascii.Ltr_b: case Byte_ascii.Ltr_c: case Byte_ascii.Ltr_d: case Byte_ascii.Ltr_e:
				case Byte_ascii.Ltr_f: case Byte_ascii.Ltr_g: case Byte_ascii.Ltr_h: case Byte_ascii.Ltr_i: case Byte_ascii.Ltr_j:
				case Byte_ascii.Ltr_k: case Byte_ascii.Ltr_l: case Byte_ascii.Ltr_m: case Byte_ascii.Ltr_n: case Byte_ascii.Ltr_o:
				case Byte_ascii.Ltr_p: case Byte_ascii.Ltr_q: case Byte_ascii.Ltr_r: case Byte_ascii.Ltr_s: case Byte_ascii.Ltr_t:
				case Byte_ascii.Ltr_u: case Byte_ascii.Ltr_v: case Byte_ascii.Ltr_w: case Byte_ascii.Ltr_x: case Byte_ascii.Ltr_y: case Byte_ascii.Ltr_z:
					letters++;
					break;
				case Byte_ascii.Num_0: case Byte_ascii.Num_1: case Byte_ascii.Num_2: case Byte_ascii.Num_3: case Byte_ascii.Num_4:
				case Byte_ascii.Num_5: case Byte_ascii.Num_6: case Byte_ascii.Num_7: case Byte_ascii.Num_8: case Byte_ascii.Num_9:
					numbers++;
					break;
				case Byte_ascii.Hash:
					// next must be &; EX: "&#" and "&#x"
					int prv = cur - 1;
					if (prv >= src_end && src[prv] == Byte_ascii.Amp) {
						// if hex, num | ltr is fine
						byte hex_byte = src[cur + 1];
						if (hex_byte == Byte_ascii.Ltr_X || hex_byte == Byte_ascii.Ltr_x) {
							return numbers > 0 || letters > 1;	// 1 to ignore "x"
						}
						// if dec, no letters allowed
						else {
							return numbers > 0 && letters == 0;
						}
					}
					return false;
				case Byte_ascii.Amp:
					// if entity, no numbers
					return letters > 0 && numbers == 0;
				default:
					return false;
			}
			cur--;
		}
		return false;
	}
}
class Xomw_regex_link_interrupt {
	private static final byte Bgn__ent__lt = 0, Bgn__ent__gt = 1, Bgn__ent__nbsp = 2, Bgn__hex = 3, Bgn__dec = 4;
	private static final byte End__hex__lt = 0, End__hex__gt = 1, End__hex__nbsp = 2, End__dec__lt = 3, End__dec__gt = 4, End__dec__nbsp = 5;
	private final    Btrie_slim_mgr bgn_trie = Btrie_slim_mgr.cs();
	private final    Btrie_slim_mgr end_trie = Btrie_slim_mgr.ci_a7();
	public Xomw_regex_link_interrupt() {
		// MW.REGEX: &(lt|gt|nbsp|#x0*(3[CcEe]|[Aa]0)|#0*(60|62|160));
		bgn_trie.Add_str_byte("&lt;", Bgn__ent__lt);
		bgn_trie.Add_str_byte("&gt;", Bgn__ent__gt);
		bgn_trie.Add_str_byte("&nbsp;", Bgn__ent__nbsp);
		bgn_trie.Add_str_byte("&#x", Bgn__hex);	// 3C | 3E | A0
		bgn_trie.Add_str_byte("&#", Bgn__dec);	// 60 | 62 | 160

		end_trie.Add_str_byte("3c;", End__hex__lt);
		end_trie.Add_str_byte("3e;", End__hex__gt);
		end_trie.Add_str_byte("a0;", End__hex__nbsp);
		end_trie.Add_str_byte("60;", End__dec__lt);
		end_trie.Add_str_byte("62;", End__dec__gt);
		end_trie.Add_str_byte("160;", End__dec__nbsp);
	}
	public int Find(Btrie_rv trv, byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn;
		while (true) {
			if (pos >= src_end) break;
			byte b = src[pos];
			Object bgn_obj = bgn_trie.Match_at_w_b0(trv, b, src, pos, src_end);
			if (bgn_obj == null) {
				pos += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
				continue;
			}

			byte bgn_tid = ((Byte_obj_val)bgn_obj).Val();
			int end_pos = trv.Pos();
			boolean valid = false;
			switch (bgn_tid) {
				case Bgn__ent__lt:
				case Bgn__ent__gt:
				case Bgn__ent__nbsp:
					return pos;
				case Bgn__hex:
				case Bgn__dec:
					// match rest of sequence from above; EX: "3c;", "60;" etc.
					end_pos = Bry_find_.Find_fwd_while(src, end_pos, src_end, Byte_ascii.Num_0);
					Object end_obj = end_trie.Match_at(trv, src, end_pos, src_end);
					if (end_obj != null) {
						// make sure that hex-dec matches; EX: "&#x60;" and "&#3c;" are invalid
						byte end_tid = ((Byte_obj_val)end_obj).Val();
						if (   bgn_tid == Bgn__hex && Int_.Between(end_tid, End__hex__lt, End__hex__nbsp)
							|| bgn_tid == Bgn__dec && Int_.Between(end_tid, End__dec__lt, End__dec__nbsp)
							)
						return pos;
					}
					break;
			}
			if (valid)
				return pos;
			else
				pos += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
		}
		return Bry_find_.Not_found;
	}
//		/**
//		* Replace special strings like "ISBN xxx" and "RFC xxx" with
//		* magic external links.
//		*
//		* DML
//		* @private
//		*
//		* @param String $text
//		*
//		* @return String
//		*/
//		public function doMagicLinks($text) {
//			$prots = wfUrlProtocolsWithoutProtRel();
//			$urlChar = self::EXT_LINK_URL_CLASS;
//			$addr = self::EXT_LINK_ADDR;
//			$space = self::SPACE_NOT_NL; #  non-newline space
//			$spdash = "(?:-|$space)"; # a dash or a non-newline space
//			$spaces = "$space++"; # possessive match of 1 or more spaces
//			$text = preg_replace_callback(
//				'!(?:                            # Start cases
//					(<a[ \t\r\n>].*?</a>) |      # m[1]: Skip link text
//					(<.*?>) |                    # m[2]: Skip stuff inside
//												#       HTML elements' . "
//					(\b(?i:$prots)($addr$urlChar*)) | # m[3]: Free external links
//												# m[4]: Post-protocol path
//					\b(?:RFC|PMID) $spaces       # m[5]: RFC or PMID, capture number
//						([0-9]+)\b |
//					\bISBN $spaces (            # m[6]: ISBN, capture number
//						(?: 97[89] $spdash?)?   #  optional 13-digit ISBN prefix
//						(?: [0-9]  $spdash?){9} #  9 digits with opt. delimiters
//						[0-9Xx]                  #  check digit
//					)\b
//				)!xu", [ &$this, 'magicLinkCallback' ], $text);
//			return $text;
//		}
//
//		/**
//		* @throws MWException
//		* @param array $m
//		* @return HTML|String
//		*/
//		public function magicLinkCallback($m) {
//			if (isset($m[1]) && $m[1] !== '') {
//				# Skip anchor
//				return $m[0];
//			} elseif (isset($m[2]) && $m[2] !== '') {
//				# Skip HTML element
//				return $m[0];
//			} elseif (isset($m[3]) && $m[3] !== '') {
//				# Free external link
//				return this.makeFreeExternalLink($m[0], strlen($m[4]));
//			} elseif (isset($m[5]) && $m[5] !== '') {
//				# RFC or PMID
//				if (substr($m[0], 0, 3) === 'RFC') {
//					if (!this.mOptions->getMagicRFCLinks()) {
//						return $m[0];
//					}
//					$keyword = 'RFC';
//					$urlmsg = 'rfcurl';
//					$cssClass = 'mw-magiclink-rfc';
//					$trackingCat = 'magiclink-tracking-rfc';
//					$id = $m[5];
//				} elseif (substr($m[0], 0, 4) === 'PMID') {
//					if (!this.mOptions->getMagicPMIDLinks()) {
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
//				this.addTrackingCategory($trackingCat);
//				return Linker::makeExternalLink($url, "{$keyword} {$id}", true, $cssClass, [], this.mTitle);
//			} elseif (isset($m[6]) && $m[6] !== ''
//				&& this.mOptions->getMagicISBNLinks()
//			) {
//				# ISBN
//				$isbn = $m[6];
//				$space = self::SPACE_NOT_NL; #  non-newline space
//				$isbn = preg_replace("/$space/", ' ', $isbn);
//				$num = strtr($isbn, [
//					'-' => '',
//					' ' => '',
//					'x' => 'X',
//				]);
//				this.addTrackingCategory('magiclink-tracking-isbn');
//				return this.getLinkRenderer()->makeKnownLink(
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
//		}
}
