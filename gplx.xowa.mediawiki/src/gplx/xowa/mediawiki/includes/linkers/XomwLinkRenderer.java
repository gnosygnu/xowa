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
package gplx.xowa.mediawiki.includes.linkers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.langs.htmls.*;
import gplx.xowa.mediawiki.includes.xohtml.*;
/*	TODO.XO
	* P7: $html = HtmlArmor::getHtml($text);
	* P3: getLinkUrl [alternate urls? EX: mw/wiki/index.php/title?]
	* P2: titleFormatter->getPrefixedText [depends on redlinks]
	* P1: getLinkClasses [depends on redlinks]
*/
/**
* Class that generates HTML <a> links for pages.
*
* @see https://www.mediawiki.org/wiki/Manual:LinkRenderer
* @since 1.28
*/
public class XomwLinkRenderer {
	/**
	* Whether to force the pretty article path
	*
	* @var boolean
	*/
	private boolean forceArticlePath = false;

	/**
	* A PROTO_* constant or false
	*
	* @var String|boolean|int
	*/
	private boolean expandUrls = false;

	/**
	* @var int
	*/
	private int stubThreshold = 0;

	/**
	* @var TitleFormatter
	*/
//		private $titleFormatter;

	/**
	* @var LinkCache
	*/
//		private $linkCache;

	/**
	* Whether to run the legacy Linker hooks
	*
	* @var boolean
	*/
//		private boolean runLegacyBeginHook = true;

	private final    XomwHtmlTemp htmlTemp = new XomwHtmlTemp();
	private final    Xomw_atr_mgr attribs = new Xomw_atr_mgr();
	private final    List_adp tmp_merge_deleted = List_adp_.New();
	private final    XomwSanitizer sanitizer;
//		/**
//		* @param TitleFormatter $titleFormatter
//		* @param LinkCache $linkCache
//		*/
	public XomwLinkRenderer(XomwSanitizer sanitizer) {	// TitleFormatter $titleFormatter, LinkCache $linkCache
//			this.titleFormatter = $titleFormatter;
//			this.linkCache = $linkCache;
		this.sanitizer = sanitizer;
	}

	/**
	* @param boolean $force
	*/
	public void setForceArticlePath(boolean force) {
		this.forceArticlePath = force;
	}

	/**
	* @return boolean
	*/
	public boolean getForceArticlePath() {
		return this.forceArticlePath;
	}

	/**
	* @param String|boolean|int $expand A PROTO_* constant or false
	*/
	public void setExpandURLs(boolean expand) {
		this.expandUrls = expand;
	}

	/**
	* @return String|boolean|int a PROTO_* constant or false
	*/
	public boolean getExpandURLs() {
		return this.expandUrls;
	}

	/**
	* @param int $threshold
	*/
	public void setStubThreshold(int threshold) {
		this.stubThreshold = threshold;
	}

	/**
	* @return int
	*/
	public int getStubThreshold() {
		return this.stubThreshold;
	}

	/**
	* @param boolean $run
	*/
//		public void setRunLegacyBeginHook(boolean run) {
//			this.runLegacyBeginHook = run;
//		}

	/**
	* @param LinkTarget $target
	* @param String|HtmlArmor|null $text
	* @param array $extraAttribs
	* @param array $query
	* @return String
	*/
	public void makeLink(Bry_bfr bfr,
		XomwTitle target, byte[] text, Xomw_atr_mgr extraAttribs, Xomw_qry_mgr query) {
//			$title = Title::newFromLinkTarget($target);	// does db lookup?
		if (target.isKnown()) {
			this.makeKnownLink(bfr, target, text, extraAttribs, query);
		} else {
			this.makeBrokenLink(bfr, target, text, extraAttribs, query);
		}
	}

	/**
	* Get the options in the legacy format
	*
	* @param boolean $isKnown Whether the link is known or broken
	* @return array
	*/
//		private function getLegacyOptions($isKnown) {
//			$options = [ 'stubThreshold' => this.stubThreshold ];
//			if (this.forceArticlePath) {
//				$options[] = 'forcearticlepath';
//			}
//			if (this.expandUrls === PROTO_HTTP) {
//				$options[] = 'http';
//			} elseif (this.expandUrls === PROTO_HTTPS) {
//				$options[] = 'https';
//			}
//
//			$options[] = $isKnown ? 'known' : 'broken';
//
//			return $options;
//		}
//
//		private function runBeginHook(LinkTarget $target, &$text, &$extraAttribs, &$query, $isKnown) {
//			$ret = null;
//			if (!Hooks::run('HtmlPageLinkRendererBegin',
//				[ $this, $target, &$text, &$extraAttribs, &$query, &$ret ])
//			) {
//				return $ret;
//			}
//
//			// Now run the legacy hook
//			return this.runLegacyBeginHook($target, $text, $extraAttribs, $query, $isKnown);
//		}
//
//		private function runLegacyBeginHook(LinkTarget $target, &$text, &$extraAttribs, &$query,
//			$isKnown
//		) {
//			if (!this.runLegacyBeginHook || !Hooks::isRegistered('LinkBegin')) {
//				// Disabled, or nothing registered
//				return null;
//			}
//
//			$realOptions = $options = this.getLegacyOptions($isKnown);
//			$ret = null;
//			$dummy = new DummyLinker();
//			$title = Title::newFromLinkTarget($target);
//			if ($text !== null) {
//				$realHtml = $html = HtmlArmor::getHtml($text);
//			} else {
//				$realHtml = $html = null;
//			}
//			if (!Hooks::run('LinkBegin',
//				[ $dummy, $title, &$html, &$extraAttribs, &$query, &$options, &$ret ])
//			) {
//				return $ret;
//			}
//
//			if ($html !== null && $html !== $realHtml) {
//				// &$html was modified, so re-armor it as $text
//				$text = new HtmlArmor($html);
//			}
//
//			// Check if they changed any of the options, hopefully not!
//			if ($options !== $realOptions) {
//				$factory = MediaWikiServices::getInstance()->getLinkRendererFactory();
//				// They did, so create a separate instance and have that take over the rest
//				$newRenderer = $factory->createFromLegacyOptions($options);
//				// Don't recurse the hook...
//				$newRenderer->setRunLegacyBeginHook(false);
//				if (in_array('known', $options, true)) {
//					return $newRenderer->makeKnownLink($title, $text, $extraAttribs, $query);
//				} elseif (in_array('broken', $options, true)) {
//					return $newRenderer->makeBrokenLink($title, $text, $extraAttribs, $query);
//				} else {
//					return $newRenderer->makeLink($title, $text, $extraAttribs, $query);
//				}
//			}
//
//			return null;
//		}

	/**
	* If you have already looked up the proper CSS classes using LinkRenderer::getLinkClasses()
	* or some other method, use this to avoid looking it up again.
	*
	* @param LinkTarget $target
	* @param String|HtmlArmor|null $text
	* @param String $classes CSS classes to add
	* @param array $extraAttribs
	* @param array $query
	* @return String
	*/
	public void makePreloadedLink(Bry_bfr bfr,
		XomwTitle target, byte[] text, byte[] classes, Xomw_atr_mgr extraAttribs, Xomw_qry_mgr query) {
		// XO.MW.HOOK: this.runBeginHook --> 'HtmlPageLinkRendererBegin', 'LinkBegin'

		target = this.normalizeTarget(target);
		byte[] url = this.getLinkUrl(target, query);
		attribs.Clear();
		attribs.Add(Gfh_atr_.Bry__href, url);	// XO.MW: add url 1st; MW does attribs["url", url] + attribs + extra_attribs
		if (classes.length > 0)                 // XO.MW: do not bother adding if empty
			attribs.Add(Gfh_atr_.Bry__class, classes);
		byte[] prefixed_text = target.getPrefixedText();
		if (prefixed_text != Bry_.Empty) {
			attribs.Add(Gfh_atr_.Bry__title, prefixed_text);
		}

		this.mergeAttribs(attribs, extraAttribs); // XO.MW: changed to not always create another array

		if (text == null) {
			text = this.getLinkText(target);
		}

		this.buildAElement(bfr, target, text, attribs, true);
	}		

	/**
	* @param LinkTarget $target
	* @param String|HtmlArmor|null $text
	* @param array $extraAttribs
	* @param array $query
	* @return String
	*/
	public void makeKnownLink(Bry_bfr bfr,
		XomwTitle target, byte[] text, Xomw_atr_mgr extraAttribs, Xomw_qry_mgr query) {
		byte[] classes = Bry_.Empty;
		if (target.isExternal()) {
			classes = Bry__classes__extiw;
		}
		byte[] colour = this.getLinkClasses(target);
		if (colour != Bry_.Empty) {
			classes = Bry_.Add(classes, Byte_ascii.Space_bry, colour); // XO.MW: also does "$classes ? implode(' ', $classes) : '',"
		}

		this.makePreloadedLink(bfr, 
			target,
			text,
			classes,
			extraAttribs,
			query);
	}

	/**
	* @param LinkTarget $target
	* @param String|HtmlArmor|null $text
	* @param array $extraAttribs
	* @param array $query
	* @return String
	*/
	public void makeBrokenLink(Bry_bfr bfr,
		XomwTitle target, byte[] text, Xomw_atr_mgr extraAttribs, Xomw_qry_mgr query) {
		// XO.MW.HOOK: Run legacy hook

		// We don't want to include fragments for broken links, because they
		// generally make no sense.
		if (target.hasFragment()) {
			target = target.createFragmentTarget(Bry_.Empty);
		}
		target = this.normalizeTarget(target);

		if (!XophpUtility.isset(query.action) && target.getNamespace() != XomwDefines.NS_SPECIAL) {
			query.action = Bry__action__edit;
			query.redlink = 1;
		}

		byte[] url = this.getLinkUrl(target, query);
		attribs.Clear();
		attribs.Add(Gfh_atr_.Bry__href, url); // $attribs = ['href' => $url,] + this.mergeAttribs($attribs, $extraAttribs);
		attribs.Add(Gfh_atr_.Bry__class, Bry__class__new);

//			$prefixedText = this.titleFormatter->getPrefixedText($target);
//			if ($prefixedText !== '') {
//				// This ends up in parser cache!
//				$attribs['title'] = wfMessage('red-link-title', $prefixedText)
//					->inContentLanguage()
//					->text();
//			}

		this.mergeAttribs(attribs, extraAttribs);

		if (text == null) {
			text = this.getLinkText(target);
		}

		this.buildAElement(bfr, target, text, attribs, false);
	}

	/**
	* Builds the final <a> element
	*
	* @param LinkTarget $target
	* @param String|HtmlArmor $text
	* @param array $attribs
	* @param boolean $isKnown
	* @return null|String
	*/
	private void buildAElement(Bry_bfr bfr, XomwTitle target, byte[] text, Xomw_atr_mgr attribs, boolean isKnown) {
		// XO.MW.HOOK:HtmlPageLinkRendererEnd

		byte[] htmlBry = text;
//			$html = HtmlArmor::getHtml($text);

		// XO.MW.HOOK:LinkEnd

		XomwHtml.rawElement(bfr, htmlTemp, Gfh_tag_.Bry__a, attribs, htmlBry);
	}

	/**
	* @param LinkTarget $target
	* @return String non-escaped text
	*/
	// XO.MW:SYNC:1.29; DATE:2017-01-31
	private byte[] getLinkText(XomwTitle target) {
		byte[] prefixed_text = target.getPrefixedText();
		// If the target is just a fragment, with no title, we return the fragment
		// text.  Otherwise, we return the title text itself.
		if (prefixed_text == Bry_.Empty && target.hasFragment()) {
			return target.getFragment();
		}
		return prefixed_text;
	}

	private byte[] getLinkUrl(XomwTitle target, Xomw_qry_mgr query) {
		// TODO: Use a LinkTargetResolver service instead of Title
//			$title = Title::newFromLinkTarget($target);
//			if (this.forceArticlePath) {
//				$realQuery = $query;
//				$query = [];
//			}
//			else {
//				$realQuery = [];
//			}
		byte[] url = target.getLinkURL(query, false, this.expandUrls);

//			if (this.forceArticlePath && $realQuery) {
//				$url = wfAppendQuery($url, $realQuery);
//			}
		return url;
	}

	/**
	* Normalizes the provided target
	*
	* @todo move the code from Linker actually here
	* @param LinkTarget $target
	* @return LinkTarget
	*/
	private XomwTitle normalizeTarget(XomwTitle target) {
		return XomwLinker.normaliseSpecialPage(target);
	}

	/**
	* Merges two sets of attributes
	*
	* @param array $defaults
	* @param array $attribs
	*
	* @return array
	*/
	private void mergeAttribs(Xomw_atr_mgr defaults, Xomw_atr_mgr attribs) {
		// XO.MW: ignore; defaults is always non-null and empty; if attribs exists, it will be merged below
		// if (!$attribs) {
		//	return $defaults;
		// }

		// Merge the custom attribs with the default ones, and iterate
		// over that, deleting all "false" attributes.
		sanitizer.mergeAttributes(defaults, attribs);

		// XO.MW.PORTED.BGN:MW removes "false" values; XO removes "null" values
		//	foreach ($merged as $key => $val) {
		//		# A false value suppresses the attribute
		//		if ($val !== false) {
		//			$ret[$key] = $val;
		//		}
		//	}
		boolean deleted = false;
		int len = attribs.Len();
		for (int i = 0; i < len; i++) {
			Xomw_atr_itm trg_atr = attribs.Get_at(i);
			// A false value suppresses the attribute
			if (trg_atr.Val() == null) {
				tmp_merge_deleted.Add(trg_atr);
				deleted = true;
			}
		}
		if (deleted) {
			len = tmp_merge_deleted.Len();
			for (int i = 0; i < len; i++) {
				Xomw_atr_itm atr = (Xomw_atr_itm)attribs.Get_at(i);
				attribs.Del(atr.Key_bry());
			}
			tmp_merge_deleted.Clear();
		}
		// XO.MW.PORTED.END
	}

	/**
	* Return the CSS classes of a known link
	*
	* @param LinkTarget $target
	* @return String CSS class
	*/
	public byte[] getLinkClasses(XomwTitle target) {
		// Make sure the target is in the cache
//			$id = this.linkCache->addLinkObj($target);
//			if ($id == 0) {
//				// Doesn't exist
//				return '';
//			}

//			if (this.linkCache->getGoodLinkFieldObj($target, 'redirect')) {
			// Page is a redirect
//				return 'mw-redirect';
//			}
//			elseif (this.stubThreshold > 0 && XomwNamespace::isContent($target->getNamespace())
//				&& this.linkCache->getGoodLinkFieldObj($target, 'length') < this.stubThreshold
//			) {
			// Page is a stub
//				return 'stub';
//			}

		return Bry_.Empty;
	}
	private static final    byte[] 
	  Bry__classes__extiw = Bry_.new_a7("extiw")
	, Bry__class__new = Bry_.new_a7("new")
	, Bry__action__edit = Bry_.new_a7("edit")
	;
}
