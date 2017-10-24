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
package gplx.xowa.mediawiki.includes.title; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.languages.*;
import gplx.xowa.mediawiki.includes.interwiki.*;
public class XomwMediaWikiTitleCodec implements XomwTitleFormatter {
	private XomwMediaWikiServices mws;
	/**
	* @var Language
	*/
	private XomwLanguage language;

//		/**
//		* @var GenderCache
//		*/
//		protected $genderCache;

	/**
	* @var String[]
	*/
	private byte[][] localInterwikis;

//		/**
//		* @param Language language The language Object to use for localizing namespace names.
//		* @param GenderCache $genderCache The gender cache for generating gendered namespace names
//		* @param String[]|String localInterwikis
//		*/
//		public function __construct(Language language, GenderCache $genderCache,
//			localInterwikis = []
//		) {
//			$this.language = language;
//			$this.genderCache = $genderCache;
//			$this.localInterwikis = (array)localInterwikis;
//		}
	public XomwMediaWikiTitleCodec(XomwMediaWikiServices mws, XomwLanguage language, byte[][] localInterwikis) {
		this.mws = mws;
		this.language = language;
		this.localInterwikis = localInterwikis;
	}

	/**
	* @see TitleFormatter::getNamespaceName()
	*
	* @param int $namespace
	* @param String $text
	*
	* @throws InvalidArgumentException If the namespace is invalid
	* @return String
	*/
	public byte[] getNamespaceName(int ns, byte[] text) {
		byte[] name = null;
//			if ($this.language.needsGenderDistinction() &&
//				XomwNamespace::hasGenderDistinction($namespace)
//			) {
//
//				// NOTE: we are assuming here that the title text is a user name!
//				$gender = $this.genderCache.getGenderOf($text, __METHOD__);
//				$name = $this.language.getGenderNsText($namespace, $gender);
//			} else {
			name = language.getNsText(ns);
//			}
//
//			if ($name === false) {
//				throw new InvalidArgumentException('Unknown namespace ID: ' . $namespace);
//			}

		return name;
	}

//		/**
//		* @see TitleFormatter::formatTitle()
//		*
//		* @param int|boolean $namespace The namespace ID (or false, if the namespace should be ignored)
//		* @param String $text The page title. Should be valid. Only minimal normalization is applied.
//		*        Underscores will be replaced.
//		* @param String $fragment The fragment name (may be empty).
//		* @param String $interwiki The interwiki name (may be empty).
//		*
//		* @throws InvalidArgumentException If the namespace is invalid
//		* @return String
//		*/
//		public function formatTitle($namespace, $text, $fragment = '', $interwiki = '') {
//			if ($namespace !== false) {
//				$namespace = $this.getNamespaceName($namespace, $text);
//
//				if ($namespace !== '') {
//					$text = $namespace . ':' . $text;
//				}
//			}
//
//			if ($fragment !== '') {
//				$text = $text . '#' . $fragment;
//			}
//
//			if ($interwiki !== '') {
//				$text = $interwiki . ':' . $text;
//			}
//
//			$text = str_replace('_', ' ', $text);
//
//			return $text;
//		}
//
//		/**
//		* Parses the given text and constructs a TitleValue. Normalization
//		* is applied according to the rules appropriate for the form specified by $form.
//		*
//		* @param String $text The text to parse
//		* @param int $defaultNamespace Namespace to assume per default (usually NS_MAIN)
//		*
//		* @throws XomwMalformedTitleException
//		* @return TitleValue
//		*/
//		public function parseTitle($text, $defaultNamespace) {
//			// NOTE: this is an ugly cludge that allows this class to share the
//			// code for parsing with the old Title class. The parser code should
//			// be refactored to avoid this.
//			$parts = $this.splitTitleString($text, $defaultNamespace);
//
//			// Relative fragment links are not supported by TitleValue
//			if ($parts['dbkey'] === '') {
//				throw new XomwMalformedTitleException('title-invalid-empty', $text);
//			}
//
//			return new TitleValue(
//				$parts['namespace'],
//				$parts['dbkey'],
//				$parts['fragment'],
//				$parts['interwiki']
//			);
//		}
//
//		/**
//		* @see TitleFormatter::getText()
//		*
//		* @param LinkTarget $title
//		*
//		* @return String $title.getText()
//		*/
//		public function getText(LinkTarget $title) {
//			return $this.formatTitle(false, $title.getText(), '');
//		}
//
//		/**
//		* @see TitleFormatter::getText()
//		*
//		* @param LinkTarget $title
//		*
//		* @return String
//		*/
//		public function getPrefixedText(LinkTarget $title) {
//			return $this.formatTitle(
//				$title.getNamespace(),
//				$title.getText(),
//				'',
//				$title.getInterwiki()
//			);
//		}
//
//		/**
//		* @since 1.27
//		* @see TitleFormatter::getPrefixedDBkey()
//		* @param LinkTarget $target
//		* @return String
//		*/
//		public function getPrefixedDBkey(LinkTarget $target) {
//			$key = '';
//			if ($target.isExternal()) {
//				$key .= $target.getInterwiki() . ':';
//			}
//			// Try to get a namespace name, but fallback
//			// to empty String if it doesn't exist
//			try {
//				$nsName = $this.getNamespaceName(
//					$target.getNamespace(),
//					$target.getText()
//				);
//			} catch (InvalidArgumentException $e) {
//				$nsName = '';
//			}
//
//			if ($target.getNamespace() !== 0) {
//				$key .= $nsName . ':';
//			}
//
//			$key .= $target.getText();
//
//			return strtr($key, ' ', '_');
//		}
//
//		/**
//		* @see TitleFormatter::getText()
//		*
//		* @param LinkTarget $title
//		*
//		* @return String
//		*/
//		public function getFullText(LinkTarget $title) {
//			return $this.formatTitle(
//				$title.getNamespace(),
//				$title.getText(),
//				$title.getFragment(),
//				$title.getInterwiki()
//			);
//		}

	/**
	* Normalizes and splits a title String.
	*
	* This function removes illegal characters, splits off the interwiki and
	* namespace prefixes, sets the other forms, and canonicalizes
	* everything.
	*
	* @todo this method is only exposed as a temporary measure to ease refactoring.
	* It was copied with minimal changes from Title::secureAndSplit().
	*
	* @todo This method should be split up and an appropriate interface
	* defined for use by the Title class.
	*
	* @param String $text
	* @param int $defaultNamespace
	*
	* @throws XomwMalformedTitleException If $text is not a valid title String.
	* @return array A map with the fields 'interwiki', 'fragment', 'namespace',
	*         'user_case_dbkey', and 'dbkey'.
	*/
	private final    byte[][] tmpPrefixRegex = new byte[2][];
	public XomwMediaWikiTitleCodecParts splitTitleString(byte[] text, int defaultNamespace) {
		byte[] dbkey = XophpString.str_replace(Byte_ascii.Space, Byte_ascii.Underline, text);

		// Initialisation
		XomwMediaWikiTitleCodecParts parts = new XomwMediaWikiTitleCodecParts(dbkey, defaultNamespace);

		// Strip Unicode bidi override characters.
		// Sometimes they slip into cut-n-pasted page titles, where the
		// override chars get included in list displays.
//			dbkey = preg_replace('/\xE2\x80[\x8E\x8F\xAA-\xAE]/S', '', dbkey);

		// Clean up whitespace
		// Note: use of the /u option on preg_replace here will cause
		// input with invalid UTF-8 sequences to be nullified out in PHP 5.2.x,
		// conveniently disabling them.
//			dbkey = preg_replace(
//				'/[ _\xA0\x{1680}\x{180E}\x{2000}-\x{200A}\x{2028}\x{2029}\x{202F}\x{205F}\x{3000}]+/u',
//				'_',
//				dbkey
//			);
//			dbkey = trim(dbkey, '_');

//			if (strpos(dbkey, UtfNormal\Constants::UTF8_REPLACEMENT) !== false) {
//				// Contained illegal UTF-8 sequences or forbidden Unicode chars.
//				throw new XomwMalformedTitleException('title-invalid-utf8', text);
//			}

		parts.dbkey = dbkey;

		// Initial colon indicates main namespace rather than specified default
		// but should not create invalid {ns,title} pairs such as {0,Project:Foo}
//			if (dbkey !== '' && ':' == dbkey[0]) {
//				parts['namespace'] = NS_MAIN;
//				dbkey = substr(dbkey, 1); # remove the colon but continue processing
//				dbkey = trim(dbkey, '_'); # remove any subsequent whitespace
//			}

		if (dbkey == Bry_.Empty) {
			throw new XomwMalformedTitleException("title-invalid-empty", text);
		}

		// Namespace or interwiki prefix
		do {
			byte[][] m = tmpPrefixRegex;
			if (XomwRegexTitlePrefix.preg_match(m, dbkey)) {
				byte[] p = m[0];
				int ns = this.language.getNsIndex(p);
				if (ns != XophpUtility.NULL_INT) {
					// Ordinary namespace
					dbkey = m[1];
					parts.ns = ns;
					// For Talk:X pages, check if X has a "namespace" prefix
					if (ns == XomwDefines.NS_TALK && XomwRegexTitlePrefix.preg_match(m, dbkey)) {
						if (this.language.getNsIndex(m[0]) != XophpUtility.NULL_INT) {
							// Disallow Talk:File:x type titles...
							throw new XomwMalformedTitleException("title-invalid-talk-namespace", text);
						}
						else if (XomwInterwiki.isValidInterwiki(mws, m[0])) {
							// TODO: get rid of global state!
							// Disallow Talk:Interwiki:x type titles...
							throw new XomwMalformedTitleException("title-invalid-talk-namespace", text);
						}
					}
				}
				else if (XomwInterwiki.isValidInterwiki(mws, p)) {
					// Interwiki link
					dbkey = m[2];
					parts.interwiki = this.language.lc(p);

					// Redundant interwiki prefix to the local wiki
					boolean doAnotherNamespaceSplit = false;
					for (byte[] localIW : this.localInterwikis) {
						if (Bry_.Eq(parts.interwiki, localIW)) {
							if (Bry_.Len_eq_0(dbkey)) {
								// Empty self-links should point to the Main Page, to ensure
								// compatibility with cross-wiki transclusions and the like.
								XomwTitle mainPage = XomwTitle.newMainPage(mws.env);
								XomwMediaWikiTitleCodecParts rv = new XomwMediaWikiTitleCodecParts(mainPage.getDBkey(), mainPage.getNamespace());
								rv.interwiki = mainPage.getInterwiki();
								rv.local_interwiki = true;
								rv.fragment = mainPage.getFragment();
								// rv.ns = mainPage.getNamespace();
								// rv.dbkey = mainPage.getDBkey();
								rv.user_case_dbkey = mainPage.getUserCaseDBKey();
							}
							parts.interwiki = Bry_.Empty;
							// local interwikis should behave like initial-colon links
							parts.local_interwiki = true;

							// Do another namespace split...
							doAnotherNamespaceSplit = true;
							break;
						}
					}
					if (doAnotherNamespaceSplit)
						continue;

					// If there's an initial colon after the interwiki, that also
					// resets the default namespace
					if (dbkey != Bry_.Empty && dbkey[0] == Byte_ascii.Colon) {
						parts.ns = XomwDefines.NS_MAIN;
						dbkey = XophpString.substr(dbkey, 1);
					}
				}
				// If there's no recognized interwiki or namespace,
				// then let the colon expression be part of the title.
			}
			break;
		} while (true);

		byte[] fragment = XophpString.strstr(dbkey, Byte_ascii.Hash_bry);
		if (null != fragment) {
			parts.fragment = XophpString.str_replace(Byte_ascii.Underline, Byte_ascii.Space, XophpString.substr(fragment, 1));
			dbkey = XophpString.substr(dbkey, 0, XophpString.strlen(dbkey) - XophpString.strlen(fragment));
			// remove whitespace again: prevents "Foo_bar_#"
			// becoming "Foo_bar_"
			dbkey = Bry_.Replace(dbkey, Byte_ascii.Underline_bry, Bry_.Empty);
		}

		// Reject illegal characters.
//			$rxTc = self::getTitleInvalidRegex();
//			$matches = [];
//			if (preg_match($rxTc, dbkey, $matches)) {
//				throw new XomwMalformedTitleException('title-invalid-characters', text, [ $matches[0] ]);
//			}

		// Pages with "/./" or "/../" appearing in the URLs will often be un-
		// reachable due to the way web browsers deal with 'relative' URLs.
		// Also, they conflict with subpage syntax.  Forbid them explicitly.
//			if (
//				strpos(dbkey, '.') !== false &&
//				(
//					dbkey === '.' || dbkey === '..' ||
//					strpos(dbkey, './') === 0 ||
//					strpos(dbkey, '../') === 0 ||
//					strpos(dbkey, '/./') !== false ||
//					strpos(dbkey, '/../') !== false ||
//					substr(dbkey, -2) == '/.' ||
//					substr(dbkey, -3) == '/..'
//				)
//			) {
//				throw new XomwMalformedTitleException('title-invalid-relative', text);
//			}

		// Magic tilde sequences?
//			if (strpos(dbkey, '~~~') !== false) {
//				throw new XomwMalformedTitleException('title-invalid-magic-tilde', text);
//			}

		// Limit the size of titles to 255 bytes. This is typically the size of the
		// underlying database field. We make an exception for special pages, which
		// don't need to be stored in the database, and may edge over 255 bytes due
		// to subpage syntax for long titles, e.g. [[Special:Block/Long name]]
//			$maxLength = (parts['namespace'] != NS_SPECIAL) ? 255 : 512;
//			if (strlen(dbkey) > $maxLength) {
//				throw new XomwMalformedTitleException('title-invalid-too-long', text,
//					[ Message::numParam($maxLength) ]);
//			}

		// Normally, all wiki links are forced to have an initial capital letter so [[foo]]
		// and [[Foo]] point to the same place.  Don't force it for interwikis, since the
		// other site might be case-sensitive.
//			parts['user_case_dbkey'] = dbkey;
//			if (parts['interwiki'] === '') {
//				dbkey = Title::capitalize(dbkey, parts['namespace']);
//			}

		// Can't make a link to a namespace alone... "empty" local links can only be
		// self-links with a fragment identifier.
//			if (dbkey == '' && parts['interwiki'] === '') {
//				if (parts['namespace'] != NS_MAIN) {
//					throw new XomwMalformedTitleException('title-invalid-empty', text);
//				}
//			}

		// Allow IPv6 usernames to start with '::' by canonicalizing IPv6 titles.
		// IP names are not allowed for accounts, and can only be referring to
		// edits from the IP. Given '::' abbreviations and caps/lowercaps,
		// there are numerous ways to present the same IP. Having sp:contribs scan
		// them all is silly and having some show the edits and others not is
		// inconsistent. Same for talk/userpages. Keep them normalized instead.
//			if (parts['namespace'] == NS_USER || parts['namespace'] == NS_USER_TALK) {
//				dbkey = IP::sanitizeIP(dbkey);
//			}

		// Any remaining initial :s are illegal.
		if (dbkey != Bry_.Empty && Byte_ascii.Colon == dbkey[0]) {
			throw new XomwMalformedTitleException("title-invalid-leading-colon", text);
		}

		// Fill fields
		parts.dbkey = dbkey;

		return parts;
	}

//		/**
//		* Returns a simple regex that will match on characters and sequences invalid in titles.
//		* Note that this doesn't pick up many things that could be wrong with titles, but that
//		* replacing this regex with something valid will make many titles valid.
//		* Previously Title::getTitleInvalidRegex()
//		*
//		* @return String Regex String
//		* @since 1.25
//		*/
//		public static function getTitleInvalidRegex() {
//			static $rxTc = false;
//			if (!$rxTc) {
//				// Matching titles will be held as illegal.
//				$rxTc = '/' .
//					// Any character not allowed is forbidden...
//					'[^' . Title::legalChars() . ']' .
//					// URL percent encoding sequences interfere with the ability
//					// to round-trip titles -- you can't link to them consistently.
//					'|%[0-9A-Fa-f]{2}' .
//					// XML/HTML character references produce similar issues.
//					'|&[A-Za-z0-9\x80-\xff]+;' .
//					'|&#[0-9]+;' .
//					'|&#x[0-9A-Fa-f]+;' .
//					'/S';
//			}
//
//			return $rxTc;
//		}
}
class XomwRegexTitlePrefix {
	// $prefixRegexp = "/^(.+?)_*:_*(.*)$/S";
	//	 "(.+?)": greedy: same as .*
	//	 "_*"   : spaces; allows "Talk___:_A" to be (Talk) (A)
	//	 "(.*)" : gobble up rest;
	//	 "/S"   : analyze
	public static boolean preg_match(byte[][] rv, byte[] src) {
		int len = src.length;

		// look for colon
		int colon_pos = Bry_find_.Find_fwd(src, Byte_ascii.Colon, 0, len);

		// if no_colon, no match; just return bry;
		if (colon_pos == Bry_find_.Not_found) {
			rv[0] = src;
			rv[1] = null;
			return false;
		}

		// colon exists; strip any flanking underlines
		int ns_end = Bry_find_.Find_bwd_while_v2(src, colon_pos, 0, Byte_ascii.Underline);
		int ttl_bgn = Bry_find_.Find_fwd_while(src, colon_pos + 1, len, Byte_ascii.Underline);

		// split ns / title and return true
		rv[0] = Bry_.Mid(src, 0, ns_end);
		rv[1] = Bry_.Mid(src, ttl_bgn, len);
		return true;
	}
}
