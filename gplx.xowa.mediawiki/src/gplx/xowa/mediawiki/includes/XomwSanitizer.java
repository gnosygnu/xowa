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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import gplx.core.brys.*; import gplx.core.btries.*; import gplx.core.encoders.*; import gplx.core.primitives.*; import gplx.langs.htmls.entitys.*;
import gplx.xowa.parsers.htmls.*;
import gplx.langs.htmls.*; import gplx.xowa.mediawiki.includes.htmls.*; import gplx.xowa.mediawiki.includes.parsers.*; import gplx.xowa.mediawiki.includes.utls.*;
public class XomwSanitizer {
	private final    Mwh_doc_wkr__atr_bldr atr_bldr = new Mwh_doc_wkr__atr_bldr();
	private final    Mwh_atr_parser atr_parser = new Mwh_atr_parser();
	private final    Xomw_regex_escape_invalid regex_clean_url = new Xomw_regex_escape_invalid();
	private final    Xomw_regex_find_domain regex_find_domain = new Xomw_regex_find_domain();
	private final    Xomw_regex_ipv6_brack regex_ipv6_brack = new Xomw_regex_ipv6_brack();
	private final    Bry_tmp tmp_host = new Bry_tmp();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private final    Btrie_rv trv = new Btrie_rv();
	private final    Xomw_regex_url_char_cbk__normalize normalize_cbk;
	private final    Xomw_regex_url_char_cbk__decode decode_cbk;

	private static Xomw_regex_url_char regex_url_char;
	private static Btrie_slim_mgr invalid_idn_trie;
	public XomwSanitizer() {
		this.normalize_cbk = new Xomw_regex_url_char_cbk__normalize(this);
		this.decode_cbk = new Xomw_regex_url_char_cbk__decode(this);
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
				( "\\s"
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
				( "\\xef\\xb8\\x80", "\\xef\\xb8\\x8f" // fe00-fe0f VARIATION SELECTOR-1-16
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

	// Merge two sets of HTML attributes.  Conflicting items in the second set
	// will override those in the first, except for 'class' attributes which
	// will be combined (if they're both strings).
	// XO.MW: XO does src += trg; MW does rv = src + trg;
	public void Merge_attributes(Xomw_atr_mgr src, Xomw_atr_mgr trg) {
		int trg_len = trg.Len();
		for (int i = 0; i < trg_len; i++) {
			Xomw_atr_itm trg_atr = trg.Get_at(i);
			// merge trg and src
			byte[] atr_cls = Gfh_atr_.Bry__class;
			if (Bry_.Eq(trg_atr.Key_bry(), atr_cls)) {
				Xomw_atr_itm src_atr = src.Get_by_or_null(atr_cls);
				if (src_atr != null) {
					// NOTE: need byte[]-creation is unavoidable b/c src_atr and trg_atr are non-null
					Merge_atrs_combine(tmp_bfr, src_atr.Val(), Byte_ascii.Space);
					tmp_bfr.Add_byte_space();
					Merge_atrs_combine(tmp_bfr, trg_atr.Val(), Byte_ascii.Space);
					src_atr.Val_(tmp_bfr.To_bry_and_clear());
					continue;
				}
			}
			src.Add_or_set(trg_atr);
		}
	}
	private void Merge_atrs_combine(Bry_bfr trg, byte[] src, byte sep) {
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
	public byte[] Clean_url(byte[] url) {
		// Normalize any HTML entities in input. They will be
		// re-escaped by makeExternalLink().			
		url = Decode_char_references(null, Bool_.Y, url, 0, url.length);

		// Escape any control characters introduced by the above step
		// XO.MW.REGEX: $url = preg_replace_callback('/[\][<>"\\x00-\\x20\\x7F\|]/', [ __CLASS__, 'cleanUrlCallback' ], $url);
		//   '[]<>"' | '00 -> 32' | 127
		if (regex_clean_url.Escape(tmp_bfr, url, 0, url.length))
			url = tmp_bfr.To_bry_and_clear();

		// XO.MW.REGEX: if (preg_match('!^([^:]+:)(//[^/]+)?(.*)$!iD', $url, $matches))
		if (regex_find_domain.Match(url, 0, url.length)) {
			// Characters that will be ignored in IDNs.
			// https://tools.ietf.org/html/rfc3454#section-3.1
			// Strip them before further processing so blacklists and such work.
			Php_preg_.Replace(tmp_host.Init(url, regex_find_domain.host_bgn, regex_find_domain.host_end), tmp_bfr, invalid_idn_trie, trv, Bry_.Empty);
			
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
	public void Fix_tag_attributes(Bry_bfr bfr, byte[] tag_name, byte[] atrs) {
		atr_bldr.Atrs__clear();
		atr_parser.Parse(atr_bldr, -1, -1, atrs, 0, atrs.length);
		int len = atr_bldr.Atrs__len();

		// PORTED: Sanitizer.php|safeEncodeTagAttributes
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
	public void Normalize_char_references(Xomw_parser_bfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		Normalize_char_references(bfr, Bool_.N, src, src_bgn, src_end);
	}
	public byte[] Normalize_char_references(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end) {
		return regex_url_char.Replace_by_cbk(bfr, lone_bfr, src, src_bgn, src_end, normalize_cbk);
	}
	public byte[] Decode_char_references(Bry_bfr bfr, boolean lone_bfr, byte[] src, int src_bgn, int src_end) {
		return regex_url_char.Replace_by_cbk(bfr, lone_bfr, src, src_bgn, src_end, decode_cbk);
	}

	public boolean Validate_codepoint(int codepoint) {
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
	// Encode an attribute value for HTML output.
	// XO.MW:SYNC:1.29; DATE:2017-02-03
	public static void Encode_attribute(Bry_bfr bfr, byte[] text) {
		// Whitespace is normalized during attribute decoding,
		// so if we've been passed non-spaces we must encode them
		// ahead of time or they won't be preserved.
		bfr.Add_bry_escape_xml(text, 0, text.length);
	}

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
		if (   src[host_bgn    ] != Byte_ascii.Slash
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
					if (   Bry_.Match(src, host_end, segs_bgn, Bry__host_end)
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
	private final    XomwSanitizer sanitizer;
	public Xomw_regex_url_char_cbk__normalize(XomwSanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}
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
		if (sanitizer.Validate_codepoint(point)) {
			bfr.Add_str_a7("&#").Add_int_variable(point).Add_byte_semic();
			return true;
		}
		return false;
	}
	public boolean When_hex(Bry_bfr bfr, byte[] name) {  // XO.MW:hexCharReference
		int point = Hex_utl_.Parse_or(name, -1);
		if (sanitizer.Validate_codepoint(point)) {
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
	private final    XomwSanitizer sanitizer;
	public Xomw_regex_url_char_cbk__decode(XomwSanitizer sanitizer) {
		this.sanitizer = sanitizer;
	}
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
		if (sanitizer.Validate_codepoint(point)) {
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
