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
import gplx.langs.phps.utls.*; import gplx.xowa.mws.htmls.*;
import gplx.langs.regxs.*;
public class Xomw_magiclinks_wkr {
	private final    Btrie_slim_mgr regex_trie = Btrie_slim_mgr.ci_a7(); // NOTE: must be ci to handle protocols; EX: "https:" and "HTTPS:"
	private final    Btrie_rv trv = new Btrie_rv();
	private static byte[] Tag__anch__rhs;
	private Xomw_regex_boundary regex_boundary;
	private Xomw_regex_url regex_url;
	private Xomw_linker linker;
	private byte[] page_title;

	private static final byte Regex__anch = 1, Regex__elem = 2, Regex__free = 3;
	public void Init_by_wiki(Xomw_linker linker, Xomw_regex_boundary regex_boundary, Xomw_regex_url regex_url) {
		this.linker = linker;
		this.regex_boundary = regex_boundary;
		this.regex_url = regex_url;
		regex_trie.Add_str_byte("<a", Regex__anch);
		regex_trie.Add_str_byte("<" , Regex__elem);
		
		Gfo_protocol_itm[] protocol_ary = Gfo_protocol_itm.Ary();
		int protocol_len = protocol_ary.length;
		for (int i = 0; i < protocol_len; i++) {
			Gfo_protocol_itm itm = protocol_ary[i];
			regex_trie.Add_bry_byte(itm.Text_bry(), Regex__free);
		}

		if (Tag__anch__rhs == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				Tag__anch__rhs = Bry_.new_a7("</a>");
			}
		}
	}

	// Replace special strings like "ISBN xxx" and "RFC xxx" with
	// magic external links.
	public void Do_magic_links(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr) {
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
		// NOTE: not handling RFC|PMID|ISBN b/c of upcoming obsolescence: https://www.mediawiki.org/wiki/Requests_for_comment/Future_of_magic_links
		//'!(?:                            // Start cases
		//	(<a[ \t\r\n>].*?</a>) |      // m[1]: Skip link text
		//	(<.*?>) |                    // m[2]: Skip stuff inside
		//									//       HTML elements' . "
		//	(\b(?i:$prots)($addr$urlChar*)) | // m[3]: Free external links
		//									// m[4]: Post-protocol path
		//	\b(?:RFC|PMID) $spaces       // m[5]: RFC or PMID, capture number
		//		([0-9]+)\b |
		//	\bISBN $spaces (            // m[6]: ISBN, capture number
		//		(?: 97[89] $spdash?)?   //  optional 13-digit ISBN prefix
		//		(?: [0-9]  $spdash?){9} //  9 digits with opt. delimiters
		//		[0-9Xx]                  //  check digit
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
			int old_pos = cur;
			int trv_pos = trv.Pos();
			int nxt_pos = trv_pos;
			boolean regex_valid = true;
			switch (regex_tid) {
				case Regex__anch:	// (<a[ \t\r\n>].*?</a>) |      // m[1]: Skip link text
					if (trv_pos < src_end) {
						// find ws in "[ \t\r\n>]"
						byte ws_byte = src[cur];
						switch (ws_byte) {
							case Byte_ascii.Space:
							case Byte_ascii.Tab:
							case Byte_ascii.Cr:
							case Byte_ascii.Nl:
								break;
							default:
								regex_valid = false;
								break;
						}
						if (regex_valid) {
							// find </a>
							nxt_pos++;
							int anch_end = Bry_find_.Find_fwd(src, Tag__anch__rhs, nxt_pos, src_end);
							if (anch_end == Bry_find_.Not_found) {
								regex_valid = false;
							}
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
					int elem_end = Bry_find_.Find_fwd(src, Byte_ascii.Angle_end, nxt_pos, src_end);
					if (elem_end == Bry_find_.Not_found)
						regex_valid = false;
					else
						cur = elem_end + 1;
					break;
				case Regex__free:
					if (regex_boundary.Is_boundary_prv(src, cur)) {
						int url_end = regex_url.Find_fwd_while(trv, src, nxt_pos, src_end);
						if (url_end == nxt_pos) {
							regex_valid = false;
						}
						else
							cur = url_end;
					}
					else
						regex_valid = false;
					break;
			}
			if (!regex_valid) {
				cur++;
			}
			else {
				if (regex_tid == Regex__free) {
					this.page_title = pctx.Page_title().Full_db();
                        dirty = true;
					bfr.Add_mid(src, prv, old_pos);
                        this.Make_free_external_link(bfr, Bry_.Mid(src, old_pos, cur), 0);
					prv = cur;
				}
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
//			byte[] trail = Bry_.Empty;

		// The characters '<' and '>' (which were escaped by
		// removeHTMLtags()) should not be included in
		// URLs, per RFC 2396.
		// Make &nbsp; terminate a URL as well (bug T84937)

//			$m2 = [];
//			if (preg_match(
//				'/&(lt|gt|nbsp|#x0*(3[CcEe]|[Aa]0)|#0*(60|62|160));/',
//				$url,
//				$m2,
//				PREG_OFFSET_CAPTURE
//			)) {
//				trail = substr($url, $m2[0][1]) . $trail;
//				$url = substr($url, 0, $m2[0][1]);
//			}

		// Move trailing punctuation to $trail
//			$sep = ',;\.:!?';
		// If there is no left bracket, then consider right brackets fair game too
//			if (strpos($url, '(') === false) {
//				$sep .= ')';
//			}

//			$urlRev = strrev($url);
//			$numSepChars = strspn($urlRev, $sep);
		// Don't break a trailing HTML entity by moving the ; into $trail
		// This is in hot code, so use substr_compare to avoid having to
		// create a new String Object for the comparison

//			if ($numSepChars && substr_compare($url, ";", -$numSepChars, 1) === 0) {
			// more optimization: instead of running preg_match with a $
			// anchor, which can be slow, do the match on the reversed
			// String starting at the desired offset.
			// un-reversed regexp is: /&([a-z]+|#x[\da-f]+|#\d+)$/i
//				if (preg_match('/\G([a-z]+|[\da-f]+x#|\d+#)&/i', $urlRev, $m2, 0, $numSepChars)) {
//					$numSepChars--;
//				}
//			}
//			if ($numSepChars) {
//				$trail = substr($url, -$numSepChars) . $trail;
//				$url = substr($url, 0, -$numSepChars);
//			}

		// Verify that we still have a real URL after trail removal, and
		// not just lone protocol
//			if (strlen($trail) >= $numPostProto) {
//				return $url . $trail;
//			}

//			$url = Sanitizer::cleanUrl($url);

		// Is this an external image?
		byte[] text = null; // $this->maybeMakeExternalImage($url);
		if (text == null) {
			// Not an image, make a link
			linker.Make_external_link(bfr, url
				, url	// $this->getConverterLanguage()->markNoConversion($url, true),
				, true, Bry_.new_a7("free")
				, new Xomwh_atr_mgr()	// $this->getExternalLinkAttribs($url)
				, page_title);
			// Register it in the output Object...
			// Replace unnecessary URL escape codes with their equivalent characters
//				$pasteurized = self::normalizeLinkUrl($url);
//				$this->mOutput->addExternalLink($pasteurized);
		}
//			return $text . $trail;
	}
}
