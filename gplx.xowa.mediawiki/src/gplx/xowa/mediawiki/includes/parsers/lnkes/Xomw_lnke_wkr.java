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
package gplx.xowa.mediawiki.includes.parsers.lnkes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.core.btries.*; import gplx.core.primitives.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
/*	TODO.XO
	* P3: $langObj->formatNum( ++$this->mAutonumber );
	* P2: $this->getConverterLanguage()->markNoConversion( $text );
*/
public class Xomw_lnke_wkr {// THREAD.UNSAFE: caching for repeated calls
	private final    Bry_bfr tmp;
	private Btrie_slim_mgr protocol_trie; private final    Btrie_rv trv = new Btrie_rv();
	private int autonumber;
	private final    XomwParserIface parser;
	private final    XomwLinker linker;
	private final    XomwSanitizer sanitizer;
	private final    Xomw_atr_mgr attribs = new Xomw_atr_mgr();
	private Xomw_regex_url regex_url;
	private Xomw_regex_space regex_space;
	public Xomw_lnke_wkr(XomwParserIface parser, Bry_bfr tmp, XomwLinker linker, XomwSanitizer sanitizer) {
		this.parser = parser;
		this.tmp = tmp;
		this.linker = linker;
		this.sanitizer = sanitizer;

		if (angle_entities_trie == null) {
			synchronized (Type_adp_.ClassOf_obj(this)) {
				Link_type__free           = Bry_.new_a7("free");
				Link_type__text           = Bry_.new_a7("text");
				Link_type__autonumber     = Bry_.new_a7("autonumber");

				angle_entities_trie = Btrie_slim_mgr.cs().Add_many_str("&lt;", "&gt;");

				// REGEX:([^\]\\x00-\\x08\\x0a-\\x1F]*?); NOTE: val is key.length
				invalid_text_chars_trie = Btrie_slim_mgr.cs();
				New__trie_itm__by_len(invalid_text_chars_trie, Byte_ascii.Brack_end);
				for (int i = 0; i <= 8; i++) {		// x00-x08
					New__trie_itm__by_len(invalid_text_chars_trie, i);
				}
				for (int i = 10; i <= 31; i++) {	// x0a-x1F
					New__trie_itm__by_len(invalid_text_chars_trie, i);
				}
			}
		}
	}
	public void Init_by_wiki(Btrie_slim_mgr protocol_trie, Xomw_regex_url regex_url, Xomw_regex_space regex_space) {
		this.protocol_trie = protocol_trie;
		this.regex_url = regex_url;
		this.regex_space = regex_space;
	}
	// XO.MW:SYNC:1.29; DATE:2017-02-01
	public void replaceExternalLinks(XomwParserCtx pctx, XomwParserBfr pbfr) {
		// XO.PBFR
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src = src_bfr.Bfr();
		int src_bgn = 0;
		int src_end = src_bfr.Len();
		Bry_bfr bfr = pbfr.Trg();
		pbfr.Switch();

		int cur = src_bgn;
		this.autonumber = 1;

		// find regex
		int prv = 0;
		while (true) {
			// PORTED.BGN: $bits = preg_split( $this->mExtLinkBracketedRegex, $text, -1, PREG_SPLIT_DELIM_CAPTURE );

			// $this->mExtLinkBracketedRegex = '/\[(((?i)' . $this->mUrlProtocols . ')' .
			//	self::EXT_LINK_ADDR .
			//	self::EXT_LINK_URL_CLASS . '*)\p{Zs}*([^\]\\x00-\\x08\\x0a-\\x1F]*?)\]/Su';
			//
			// REGEX: "[" + "protocol" + "url-char"* + "space"* + "text"* + "]";
			//   protocol               -> ((?i)' . $this->mUrlProtocols . ')                 -> "http://", "HTTps://"
			//   url-char*              -> (EXT_LINK_ADDR . EXT_LINK_URL_CLASS*)              -> "255.255.255.255", "a.b.c"; NOTE: "http:///" is valid
			//   space*                 -> \p{Zs}*
			//   text                   -> ([^\]\\x00-\\x08\\x0a-\\x1F]*?)                    -> "abcd"
			// NOTE: /S=extra analysis of pattern /u = unicode support; REF.MW:http://php.net/manual/en/reference.pcre.pattern.modifiers.php

			// Simplified expression to match an IPv4 or IPv6 address, or
			// at least one character of a host name (embeds EXT_LINK_URL_CLASS)
			// static final EXT_LINK_ADDR = '(?:[0-9.]+|\\[(?i:[0-9a-f:.]+)\\]|[^][<>"\\x00-\\x20\\x7F\p{Zs}])';
			//
			// REGEX: "IPv4" | "IPv6" | "url-char"
			//   IPv4                   -> [0-9.]+                                            -> "255."
			//   IPv6                   -> \\[(?i:[0-9a-f:.]+)\\]                             -> "2001:"
			//   url-char               -> [^][<>"\\x00-\\x20\\x7F\p{Zs}]                     -> "abcde"

			// Constants needed for external link processing
			// Everything except bracket, space, or control characters
			// \p{Zs} is unicode 'separator, space' category. It covers the space 0x20
			// as well as U+3000 is IDEOGRAPHIC SPACE for T21052
			// static final EXT_LINK_URL_CLASS = '[^][<>"\\x00-\\x20\\x7F\p{Zs}]';
			//
			// REGEX: NOT [ "symbols" | "control" | "whitespace" ]
			//   symbols                -> ^][<>"
			//   control                -> \\x00-\\x20\\x7F
			//   whitespace             -> \p{Zs}

			// search for "["
			int lnke_bgn = Bry_find_.Find_fwd(src, Byte_ascii.Brack_bgn, cur, src_end);
			if (lnke_bgn == Bry_find_.Not_found) {
				bfr.Add_mid(src, cur, src_end);
				break;	// no more "["; stop
			}

			// check for protocol; EX: "https://"
			cur = lnke_bgn + 1;
			int url_bgn = cur;
			Object protocol_bry = protocol_trie.Match_at(trv, src, cur, src_end);
			if (protocol_bry == null) {
				bfr.Add_mid(src, prv, cur);
				prv = cur;
				continue;// unknown protocol; ignore "["
			}
			cur += ((byte[])protocol_bry).length;
			
			// check for one-or-more url chars; [^][<>"\\x00-\\x20\\x7F\p{Zs}]
			int domain_bgn = cur;
			cur = regex_url.Find_fwd_while(trv, src, domain_bgn, src_end);
			if (cur - domain_bgn == 0) {
				bfr.Add_mid(src, prv, cur);
				prv = cur;
				continue;	// no chars found; invalid; EX: "[https://"abcde"]"
			}
			int url_end = cur;

			// skip ws
			cur = regex_space.Find_fwd_while(trv, src, cur, src_end);

			// get text (if any)
			int text_bgn = -1, text_end = -1;
			while (true) {
				byte b = src[cur];
				Object invalid_text_char = invalid_text_chars_trie.Match_at_w_b0(trv, b, src, cur, src_end);
				if (invalid_text_char != null) break;
				if (text_bgn == -1) text_bgn = cur;
				cur += gplx.core.intls.Utf8_.Len_of_char_by_1st_byte(b);
				text_end = cur;
			}
			
			// check for "]"
			if (src[cur] != Byte_ascii.Brack_end) {
				bfr.Add_mid(src, prv, cur);
				prv = cur;
				continue;
			}
			cur++;
			// PORTED.END: $bits = preg_split( $this->mExtLinkBracketedRegex, $text, -1, PREG_SPLIT_DELIM_CAPTURE );

			// The characters '<' and '>' (which were escaped by
			// removeHTMLtags()) should not be included in
			// URLs, per RFC 2396.
			if (XophpPreg.match(angle_entities_trie, trv, src, url_bgn, url_end) != null) {
				int angle_bgn = trv.Match_bgn;
				text_bgn = angle_bgn;
				url_end = angle_bgn;
			}

			// If the link text is an image URL, replace it with an <img> tag
			// This happened by accident in the original parser, but some people used it extensively
			// XO.MW.UNSUPPORTED.NON-WMF: not supporting images from freefrom url; (EX: "http://a.org/image.png" -> "<img>"); haven't seen this used on WMF wikis
			// $img = $this->maybeMakeExternalImage( $text );
			// if ($img !== false) $text = $img;

			// XO.MW.SKIP: See "Have link text"
			//$dtrail = '';

			// Set linktype for CSS - if URL==text, link is essentially free
			boolean text_missing = text_bgn == -1;
			byte[] link_type = text_missing ? Link_type__free : Link_type__text;

			// No link text, e.g. [http://domain.tld/some.link]
			if (text_missing) {
				// Autonumber; EX: "[123]"
				tmp.Add_byte(Byte_ascii.Brack_bgn);
				tmp.Add_int_variable(autonumber++);	// TODO.XO:$langObj->formatNum( ++$this->mAutonumber );
				tmp.Add_byte(Byte_ascii.Brack_end);
				link_type = Link_type__autonumber;
			}
			else {
				// XO.MW.SKIP: skipped b/c MW splits $trail into $dtrail and $trail but does no extra logic with variables; just concatenates later; "$this->getExternalLinkAttribs( $url ) ) . $dtrail . $trail;"
				// Have link text, e.g. [http://domain.tld/some.link text]s
				// Check for trail
				// list( $dtrail, $trail ) = Linker::splitTrail( $trail );
			}

			// TODO.XO:
			// $text = $this->getConverterLanguage()->markNoConversion( $text );

			byte[] url = Bry_.Mid(src, url_bgn, url_end);
			url = sanitizer.cleanUrl(url);

			bfr.Add_mid(src, prv, lnke_bgn);
			prv = cur;
			// Use the encoded URL
			// This means that users can paste URLs directly into the text
			// Funny characters like � aren't valid in URLs anyway
			// This was changed in August 2004
			linker.makeExternalLink(bfr, url, Bry_.Mid(src, text_bgn, text_end), Bool_.N, link_type, parser.getExternalLinkAttribs(attribs), Bry_.Empty);

			// XO.MW.UNSUPPORTED.HOOK: registers link for processing by other extensions?
			// Register link in the output Object.
			// Replace unnecessary URL escape codes with the referenced character
			// This prevents spammers from hiding links from the filters
			// $pasteurized = self::normalizeLinkUrl( $url );
			// $this->mOutput->addExternalLink( $pasteurized );
		}
	}

	private static byte[] Link_type__free, Link_type__text, Link_type__autonumber;
	private static Btrie_slim_mgr angle_entities_trie;
	private static Btrie_slim_mgr invalid_text_chars_trie;
	private static void New__trie_itm__by_len(Btrie_slim_mgr mgr, int... ary) {
		mgr.Add_obj(Bry_.New_by_ints(ary), new Int_obj_val(ary.length));
	}
}
