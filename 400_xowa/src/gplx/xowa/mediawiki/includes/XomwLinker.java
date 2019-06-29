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
import gplx.core.btries.*;
import gplx.langs.htmls.*;
import gplx.xowa.mediawiki.includes.xohtml.*; import gplx.xowa.mediawiki.includes.linkers.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.xowa.mediawiki.includes.filerepo.file.*; import gplx.xowa.mediawiki.includes.media.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
/*	TODO.XO
	* thumb = $file->getUnscaledThumb(handlerParams);
	* P8: wfMessage
	* P7: titleFormatter->getPrefixedText
	* P7: $html = HtmlArmor::getHtml($text);
*/
public class XomwLinker {
//		private XomwEnv env;
	private final    Bry_bfr tmp = Bry_bfr_.New(), tmp_2 = Bry_bfr_.New();
	private final    Linker_rel_splitter splitter = new Linker_rel_splitter();
	private byte[] wg_title = null;
	private final    Btrie_rv trv = new Btrie_rv();
	private final    byte[][] split_trail_rv = new byte[2][];
	private Btrie_slim_mgr split_trail_trie;
	private final    Xomw_atr_mgr tmp_attribs = new Xomw_atr_mgr();
	private final    XomwHtmlTemp htmlTemp = new XomwHtmlTemp();

	private static final    byte[] Atr__class = Bry_.new_a7("class"), Atr__rel = Bry_.new_a7("rel"), Atr__href = Bry_.new_a7("href"), Rel__nofollow = Bry_.new_a7("nofollow");
	public static final    byte[] 
	  Align__frame__center = Bry_.new_a7("center")
	, Align__frame__none = Bry_.new_a7("none")
	, Align__frame__right = Bry_.new_a7("right")
	, Prefix__center = Bry_.new_a7("<div class=\"center\">")
	, Class__internal = Bry_.new_a7("internal")
	, Class__magnify = Bry_.new_a7("magnify")
	, Img_class__thumbborder = Bry_.new_a7("thumbborder")
	, Img_class__thumbimage = Bry_.new_a7("thumbimage")
	;
	private final    XomwLinkRenderer link_renderer;
	public XomwLinker(XomwLinkRenderer link_renderer) {
		this.link_renderer = link_renderer;
	}
	public void Init_by_wiki(XomwEnv env, Btrie_slim_mgr trie) {
//			this.env = env;
		this.split_trail_trie = trie;
	}
//		/**
//		* Flags for userToolLinks()
//		*/
//		static final TOOL_LINKS_NOBLOCK = 1;
//		static final TOOL_LINKS_EMAIL = 2;
//
//		/**
//		* Return the CSS colour of a known link
//		*
//		* @deprecated since 1.28, use LinkRenderer::getLinkClasses() instead
//		*
//		* @since 1.16.3
//		* @param LinkTarget $t
//		* @param int $threshold User defined threshold
//		* @return String CSS class
//		*/
//		public static function getLinkColour( LinkTarget $t, $threshold ) {
//			wfDeprecated( __METHOD__, '1.28' );
//			$services = MediaWikiServices::getInstance();
//			$linkRenderer = $services->getLinkRenderer();
//			if ( $threshold !== $linkRenderer->getStubThreshold() ) {
//				// Need to create a new instance with the right stub threshold...
//				$linkRenderer = $services->getLinkRendererFactory()->create();
//				$linkRenderer->setStubThreshold( $threshold );
//			}
//
//			return $linkRenderer->getLinkClasses( $t );
//		}
	// This function returns an HTML link to the given target.  It serves a few
	// purposes:
	//   1) If $target is a Title, the correct URL to link to will be figured
	//      out automatically.
	//   2) It automatically adds the usual classes for various types of link
	//      targets: "new" for red links, "stub" for short articles, etc.
	//   3) It escapes all attribute values safely so there's no risk of XSS.
	//   4) It provides a default tooltip if the target is a Title (the page
	//      name of the target).
	// link() replaces the old functions in the makeLink() family.
	//
	// @since 1.18 Method exists since 1.16 as non-static, made static in 1.18.
	// @deprecated since 1.28, use MediaWiki\Linker\LinkRenderer instead
	//
	// @param Title $target Can currently only be a Title, but this may
	//   change to support Images, literal URLs, etc.
	// @param String $html The HTML contents of the <a> element, i.e.,
	//   the link text.  This is raw HTML and will not be escaped.  If null,
	//   defaults to the prefixed text of the Title; or if the Title is just a
	//   fragment, the contents of the fragment.
	// @param array $customAttribs A key => value array of extra HTML attributes,
	//   such as title and class.  (href is ignored.)  Classes will be
	//   merged with the default classes, while other attributes will replace
	//   default attributes.  All passed attribute values will be HTML-escaped.
	//   A false attribute value means to suppress that attribute.
	// @param array $query The query String to append to the URL
	//   you're linking to, in key => value array form.  Query keys and values
	//   will be URL-encoded.
	// @param String|array $options String or array of strings:
	//     'known': Page is known to exist, so don't check if it does.
	//     'broken': Page is known not to exist, so don't check if it does.
	//     'noclasses': Don't add any classes automatically (includes "new",
	//       "stub", "mw-redirect", "extiw").  Only use the class attribute
	//       provided, if any, so you get a simple blue link with no funny i-
	//       cons.
	//     'forcearticlepath': Use the article path always, even with a querystring.
	//       Has compatibility issues on some setups, so avoid wherever possible.
	//     'http': Force a full URL with http:// as the scheme.
	//     'https': Force a full URL with https:// as the scheme.
	//     'stubThreshold' => (int): Stub threshold to use when determining link classes.
	// @return String HTML <a> attribute
	public void Link(Bry_bfr bfr, XomwTitle target, byte[] html, Xomw_atr_mgr custom_attribs, Xomw_qry_mgr query, Xomw_opt_mgr options) {
		// XO.MW.UNSUPPORTED:MW has different renderers -- presumably for forcing "https:" and others; XO only has one
		//if (options != null) {
		//	// Custom options, create new LinkRenderer
		//	if (!isset($options['stubThreshold'])) {
		//		$defaultLinkRenderer = $services->getLinkRenderer();
		//		$options['stubThreshold'] = $defaultLinkRenderer->getStubThreshold();
		//	}
		//	$linkRenderer = $services->getLinkRendererFactory()->createFromLegacyOptions($options);
		//}
		//else {
		//	$linkRenderer = $services->getLinkRenderer();
		//}

		byte[] text = null;
		if (html != null) {
			// $text = new HtmlArmor($html);
		}
		else {
			text = html; // null
		}
		if (options.known) {
			link_renderer.makeKnownLink(bfr, target, text, custom_attribs, query);
		}
		else if (options.broken) {
			link_renderer.makeBrokenLink(bfr, target, text, custom_attribs, query);
		}
		else if (options.no_classes) {
			link_renderer.makePreloadedLink(bfr, target, text, Bry_.Empty, custom_attribs, query);
		}
		else {
			link_renderer.makeLink(bfr, target, text, custom_attribs, query);
		}
	}

//		/**
//		* Identical to link(), except $options defaults to 'known'.
//		*
//		* @since 1.16.3
//		* @deprecated since 1.28, use MediaWiki\Linker\LinkRenderer instead
//		* @see Linker::link
//		* @return String
//		*/
//		public static function linkKnown(
//			$target, $html = null, $customAttribs = [],
//			$query = [], $options = [ 'known' ]
//		) {
//			return self::link( $target, $html, $customAttribs, $query, $options );
//		}

	/**
	* Make appropriate markup for a link to the current article. This is
	* currently rendered as the bold link text. The calling sequence is the
	* same as the other make*LinkObj static functions, despite $query not
	* being used.
	*
	* @since 1.16.3
	* @param Title $nt
	* @param String $html [optional]
	* @param String $query [optional]
	* @param String $trail [optional]
	* @param String $prefix [optional]
	*
	* @return String
	*/
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	public void makeSelfLinkObj(Bry_bfr bfr, XomwTitle nt, byte[] html, byte[] query, byte[] trail, byte[] prefix) {
		// MW.HOOK:SelfLinkBegin
		if (html == Bry_.Empty) {
			html = tmp.Add_bry_escape_html(nt.getPrefixedText()).To_bry_and_clear();
		}
		byte[] inside = Bry_.Empty;
		byte[][] split_trail = splitTrail(trail);
		inside = split_trail[0];
		trail = split_trail[1];
		bfr.Add_str_a7("<strong class=\"selflink\">");
		bfr.Add_bry_many(prefix, html, inside);
		bfr.Add_str_a7("</strong>");
		bfr.Add(trail);
	}

//		/**
//		* Get a message saying that an invalid title was encountered.
//		* This should be called after a method like Title::makeTitleSafe() returned
//		* a value indicating that the title Object is invalid.
//		*
//		* @param IContextSource $context Context to use to get the messages
//		* @param int $namespace Namespace number
//		* @param String $title Text of the title, without the namespace part
//		* @return String
//		*/
//		public static function getInvalidTitleDescription( IContextSource $context, $namespace, $title ) {
//			global $wgContLang;
//
//			// First we check whether the namespace exists or not.
//			if ( XomwNamespace::exists( $namespace ) ) {
//				if ( $namespace == NS_MAIN ) {
//					$name = $context->msg( 'blanknamespace' )->text();
//				} else {
//					$name = $wgContLang->getFormattedNsText( $namespace );
//				}
//				return $context->msg( 'invalidtitle-knownnamespace', $namespace, $name, $title )->text();
//			} else {
//				return $context->msg( 'invalidtitle-unknownnamespace', $namespace, $title )->text();
//			}
//		}
//
//		/**
//		* @since 1.16.3
//		* @param LinkTarget $target
//		* @return LinkTarget
//		*/
	public static XomwTitle normaliseSpecialPage(XomwTitle target) {
//			if (target.Ns().Id_is_special() && !target.Is_external()) {
//				list($name, $subpage) = SpecialPageFactory::resolveAlias($target->getDBkey());
//				if (!$name) {
//					return $target;
//				}
//				$ret = SpecialPage::getTitleValueFor($name, $subpage, $target->getFragment());
//				return $ret;
//			} 
//			else {
			return target;
//			}
	}

//		/**
//		* Returns the filename part of an url.
//		* Used as alternative text for external images.
//		*
//		* @param String $url
//		*
//		* @return String
//		*/
//		private static function fnamePart( $url ) {
//			$basename = strrchr( $url, '/' );
//			if ( false === $basename ) {
//				$basename = $url;
//			} else {
//				$basename = substr( $basename, 1 );
//			}
//			return $basename;
//		}
//
//		/**
//		* Return the code for images which were added via external links,
//		* via Parser::maybeMakeExternalImage().
//		*
//		* @since 1.16.3
//		* @param String $url
//		* @param String $alt
//		*
//		* @return String
//		*/
//		public static function makeExternalImage( $url, $alt = '' ) {
//			if ( $alt == '' ) {
//				$alt = self::fnamePart( $url );
//			}
//			$img = '';
//			$success = Hooks::run( 'LinkerMakeExternalImage', [ &$url, &$alt, &$img ] );
//			if ( !$success ) {
//				wfDebug( "Hook LinkerMakeExternalImage changed the output of external image "
//					. "with url {$url} and alt text {$alt} to {$img}\n", true );
//				return $img;
//			}
//			return Html::element( 'img',
//				[
//					'src' => $url,
//					'alt' => $alt ] );
//		}


	// Given parameters derived from [[Image:Foo|options...]], generate the
	// HTML that that syntax inserts in the page.
	//
	// @param Parser $parser
	// @param Title $title Title Object of the file (not the currently viewed page)
	// @param File $file File Object, or false if it doesn't exist
	// @param array frameParams Associative array of parameters external to the media handler.
	//     Boolean parameters are indicated by presence or absence, the value is arbitrary and
	//     will often be false.
	//          thumbnail       If present, downscale and frame
	//          manual_thumb     Image name to use as a thumbnail, instead of automatic scaling
	//          framed          Shows image in original size in a frame
	//          frameless       Downscale but don't frame
	//          upright         If present, tweak default sizes for portrait orientation
	//          upright_factor  Fudge factor for "upright" tweak (default 0.75)
	//          border          If present, show a border around the image
	//          align           Horizontal alignment (left, right, center, none)
	//          valign          Vertical alignment (baseline, sub, super, top, text-top, middle,
	//                          bottom, text-bottom)
	//          alt             Alternate text for image (i.e. alt attribute). Plain text.
	//          class           HTML for image classes. Plain text.
	//          caption         HTML for image caption.
	//          link-url        URL to link to
	//          link-title      Title Object to link to
	//          link-target     Value for the target attribute, only with link-url
	//          no-link         Boolean, suppress description link
	//
	// @param array handlerParams Associative array of media handler parameters, to be passed
	//       to transform(). Typical keys are "width" and "page".
	// @param String|boolean $time Timestamp of the file, set as false for current
	// @param String $query Query params for desc url
	// @param int|null width_option Used by the parser to remember the user preference thumbnailsize
	// @since 1.20
	// @return String HTML for an image, with links, wrappers, etc.
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	public void makeImageLink(Bry_bfr bfr, XomwEnv env, XomwParserCtx pctx, XomwParserIface parser, XomwTitle title, XomwFile file, Xomw_params_frame frameParams, Xomw_params_handler handlerParams, Object time, byte[] query, int widthOption) {
		// XO.MW.HOOK:ImageBeforeProduceHTML

		if (file != null && !file.allowInlineDisplay()) {
//				this.Link(bfr, title, Bry_.Empty, tmp_attribs, tmp_query, tmp_options);
			return;
		}

		// Clean up parameters
		int page = handlerParams.page;
		if (!XophpUtility.isset(frameParams.align)) {
			frameParams.align = Bry_.Empty;
		}
		if (!XophpUtility.isset(frameParams.alt)) {
			frameParams.alt = Bry_.Empty;
		}
		if (!XophpUtility.isset(frameParams.title)) {
			frameParams.title = Bry_.Empty;
		}
		if (!XophpUtility.isset(frameParams.cls)) {
			frameParams.cls = Bry_.Empty;
		}

		byte[] prefix = Bry_.Empty; byte[] postfix = Bry_.Empty;

		if (Bry_.Eq(Align__frame__center, frameParams.align)) {
			prefix = Prefix__center;
			postfix = Gfh_tag_.Div_rhs;
			frameParams.align = Align__frame__none;
		}
		if (file != null && !XophpUtility.isset(handlerParams.width)) {
			if (XophpUtility.isset(handlerParams.height) && file.isVectorized()) {
				// If its a vector image, and user only specifies height
				// we don't want it to be limited by its "normal" width.
				handlerParams.width = env.Global__wgSVGMaxSize;
			}
			else {
				handlerParams.width = file.getWidth(page);
			}

			if (   XophpUtility.isset(frameParams.thumbnail)
				|| XophpUtility.isset(frameParams.manualthumb)
				|| XophpUtility.isset(frameParams.framed)
				|| XophpUtility.isset(frameParams.frameless)
				|| !XophpUtility.istrue(handlerParams.width)
			) {
				if (widthOption == XophpUtility.NULL_INT) {	// XO.MW: MW does extra validation that widthOption is in array; ("!isset( $wgThumbLimits[$widthOption] )")
					widthOption = env.User__default__thumbsize;
				}

				// Reduce width for upright images when parameter 'upright' is used
				if (frameParams.upright == 0) {
					frameParams.upright = env.Global__wgThumbUpright;
				}

				// For caching health: If width scaled down due to upright
				// parameter, round to full __0 pixel to avoid the creation of a
				// lot of odd thumbs.
				int prefWidth = XophpUtility.isset(frameParams.upright) ?
					(int)XophpMath.round(widthOption * frameParams.upright, -1) :
					widthOption;

				// Use width which is smaller: real image width or user preference width
				// Unless image is scalable vector.
				if (handlerParams.height == XophpUtility.NULL_INT && handlerParams.width <= 0 ||
						prefWidth < handlerParams.width || file.isVectorized()) {
					handlerParams.width = prefWidth;
				}
			}
		}

		if (frameParams.thumbnail != null || frameParams.manualthumb != null
			|| frameParams.framed != null
		) {
			// Create a thumbnail. Alignment depends on the writing direction of
			// the page content language (right-aligned for LTR languages,
			// left-aligned for RTL languages)
			// If a thumbnail width has not been provided, it is set
			// to the default user option as specified in Language*.php
			if (frameParams.align == Bry_.Empty) {
				frameParams.align = env.Lang__align_end;
			}
			bfr.Add(prefix);
			makeThumbLink2(bfr, env, pctx, title, file, frameParams, handlerParams, time, query);
			bfr.Add(postfix);
			return;
		}

		if (file != null && frameParams.frameless != null) {
			int srcWidth = file.getWidth(page);
			// For "frameless" option: do not present an image bigger than the
			// source (for bitmap-style images). This is the same behavior as the
			// "thumb" option does it already.
			if (XophpUtility.istrue(srcWidth) && !file.mustRender() && handlerParams.width > srcWidth) {
				handlerParams.width = srcWidth;
			}
		}

		XomwMediaTransformOutput thumb = null;
		if (file != null && XophpUtility.isset(handlerParams.width)) {
			// Create a resized image, without the additional thumbnail features
			thumb = file.transform(handlerParams);
		}
		else {
			thumb = null;
		}

		byte[] s = null;
		if (thumb == null) {
//				$s = self::makeBrokenImageLinkObj($title, frameParams['title'], '', '', '', $time == true);
			s = Bry_.Empty;
		}
		else {
//				self::processResponsiveImages($file, $thumb, handlerParams);
			Xomw_params_mto prms = pctx.Linker__makeImageLink__prms.Clear();
			prms.alt = frameParams.alt;
			prms.title = frameParams.title;
			prms.valign = frameParams.valign;
			prms.img_cls = frameParams.cls;
			if (frameParams.border != null) {
				prms.img_cls = Xomw_params_frame.Cls_add(prms.img_cls, Img_class__thumbborder);
			}
			getImageLinkMTOParams(prms, frameParams, query, parser);

			thumb.toHtml(tmp, tmp_2, prms);
			s = tmp.To_bry_and_clear();
		}
		if (frameParams.align != Bry_.Empty) {
			tmp.Add_str_a7("<div class=\"float").Add(frameParams.align);
			tmp.Add(s);
			tmp.Add_str_a7("\">");
			tmp.Add_str_a7("</div>");
			s = tmp.To_bry_and_clear();
		}

		// XO.MW: "str_replace("\n", ' ', prefix . $s . postfix);"
		int rv_bgn = bfr.Len();
		bfr.Add(prefix);
		bfr.Add(s);
		bfr.Add(postfix);
		Bry_.Replace_all_direct(bfr.Bfr(), Byte_ascii.Nl, Byte_ascii.Space, rv_bgn, bfr.Len());	
	}
	// Get the link parameters for MediaTransformOutput::toHtml() from given
	// frame parameters supplied by the Parser.
	// @param array $frameParams The frame parameters
	// @param String $query An optional query String to add to description page links
	// @param Parser|null $parser
	// @return array
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	private static void getImageLinkMTOParams(Xomw_params_mto rv, Xomw_params_frame frameParams, byte[] query, XomwParserIface parser) {
		if (XophpUtility.isset(frameParams.link_url) && frameParams.link_url != Bry_.Empty) {
			rv.custom_url_link = frameParams.link_url;
			if (XophpUtility.isset(frameParams.link_target)) {
				rv.custom_target_link = frameParams.link_target;
			}
			if (parser != null) {
//					extLinkAttrs = parser->getExternalLinkAttribs(frameParams['link-url']);
//					foreach (extLinkAttrs as name => val) {
//						// Currently could include 'rel' and 'target'
//						rv['parser-extlink-' . name] = val;
//					}
			}
		}
		else if (XophpUtility.isset(frameParams.link_title) && frameParams.link_title != Bry_.Empty) {
//				rv.custom_title_link = Title::newFromLinkTarget(Normalize_speecial_page(frameParams.link_title));
		}
		else if (!XophpUtility.empty(frameParams.no_link)) {
			// No link
		}
		else {
			rv.desc_link = true;
			rv.desc_query = query;
		}
	}

//		// Make HTML for a thumbnail including image, border and caption
//		public static function makeThumbLinkObj(Title $title, $file, $label = '', $alt,
//			$align = 'right', $params = [], $framed = false, $manual_thumb = ""
//		) {
//			frameParams = [
//				'alt' => $alt,
//				'caption' => $label,
//				'align' => $align
//			];
//			if ($framed) {
//				frameParams['framed'] = true;
//			}
//			if ($manual_thumb) {
//				frameParams['manual_thumb'] = $manual_thumb;
//			}
//			return self::makeThumbLink2($title, $file, frameParams, $params);
//		}

	/**
	* @param Title $title
	* @param File $file
	* @param array $frameParams
	* @param array $handlerParams
	* @param boolean $time
	* @param String $query
	* @return String
	*/
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	private void makeThumbLink2(Bry_bfr bfr, XomwEnv env, XomwParserCtx pctx, XomwTitle title, XomwFile file, Xomw_params_frame frameParams, Xomw_params_handler handlerParams, Object time, byte[] query) {
		boolean exists = file != null && file.exists();

		int page = handlerParams.page;
		if (!XophpUtility.isset(frameParams.align)) {
			frameParams.align = Align__frame__right;
		}
		if (!XophpUtility.isset(frameParams.alt)) {
			frameParams.alt = Bry_.Empty;
		}
		if (!XophpUtility.isset(frameParams.title)) {
			frameParams.title = Bry_.Empty;
		}
		if (!XophpUtility.isset(frameParams.caption)) {
			frameParams.caption = Bry_.Empty;
		}

		if (XophpUtility.empty(handlerParams.width)) {
			// Reduce width for upright images when parameter 'upright' is used
			handlerParams.width = XophpUtility.isset(frameParams.upright) ? 130 : 180;
		}
		XomwMediaTransformOutput thumb = null;
		boolean noscale = false;
		boolean manualthumb = false;

		int outerWidth = 0;
		if (!exists) {
			outerWidth = handlerParams.width + 2;
		}
		else {
			if (XophpUtility.isset(frameParams.manualthumb)) {
				// Use manually specified thumbnail
//					$manual_title = Title::makeTitleSafe(NS_FILE, frameParams['manualthumb']);
//					if ($manual_title) {
//						$manual_img = wfFindFile($manual_title);
//						if ($manual_img) {
//							thumb = $manual_img->getUnscaledThumb(handlerParams);
//							manualthumb = true;
//						} else {
//							exists = false;
//						}
//					}
			}
			else if (XophpUtility.isset(frameParams.framed)) {
				// Use image dimensions, don't scale
//					thumb = $file->getUnscaledThumb(handlerParams);
				thumb = new XomwThumbnailImage(file, file.getUrl(), file.getUrl(), file.getWidth(), file.getHeight());
				noscale = true;
			}
			else {
				// Do not present an image bigger than the source, for bitmap-style images
				// This is a hack to maintain compatibility with arbitrary pre-1.10 behavior
				int srcWidth = file.getWidth(page);
				if (XophpUtility.istrue(srcWidth) && !file.mustRender() && handlerParams.width > srcWidth) {
					handlerParams.width = srcWidth;
				}
				thumb = file.transform(handlerParams);
			}

			if (thumb != null) {
				outerWidth = thumb.getWidth() + 2;
			}
			else {
				outerWidth = handlerParams.width + 2;
			}
		}

		// ThumbnailImage::toHtml() already adds page= onto the end of DjVu URLs
		// So we don't need to pass it here in $query. However, the URL for the
		// zoom icon still needs it, so we make a unique query for it. See bug 14771
		byte[] url = title.getLocalURL(query);
//			byte[] url = Bry_.Empty;
//			if ($page) {
//				$url = wfAppendQuery($url, [ 'page' => $page ]);
//			}
		if (manualthumb
			&& !XophpUtility.isset(frameParams.link_title)
			&& !XophpUtility.isset(frameParams.link_url)
			&& !XophpUtility.isset(frameParams.no_link)
		) {
			frameParams.link_url = url;
		}

		int rv_bgn = bfr.Len();
		bfr.Add_str_a7("<div class=\"thumb t").Add(frameParams.align)
			.Add_str_a7("\"><div class=\"thumbinner\" style=\"width:").Add_int_variable(outerWidth).Add_str_a7("px;\">");

		byte[] zoom_icon = Bry_.Empty;
		if (!exists) {
//				$s .= self::makeBrokenImageLinkObj($title, frameParams.title, '', '', '', $time == true);
			zoom_icon = Bry_.Empty;
		}
		else if (thumb == null) {
			// env.Msg_mgr().Get_by_id().Escaped();
//				$s .= wfMessage('thumbnail_error', '')->escaped();
			zoom_icon = Bry_.Empty;
		}
		else {
			if (!noscale && !manualthumb) {
//					self::processResponsiveImages($file, thumb, handlerParams);
			}
			Xomw_params_mto prms = pctx.Linker__makeImageLink__prms.Clear();
			prms.alt = frameParams.alt;
			prms.title = frameParams.title;
			prms.img_cls = Xomw_params_frame.Cls_add(frameParams.cls, Img_class__thumbimage);
			
			getImageLinkMTOParams(prms, frameParams, query, null);
			thumb.toHtml(bfr, tmp, prms);
			if (XophpUtility.isset(frameParams.framed)) {
				zoom_icon = Bry_.Empty;
			}
			else {
				XomwHtml.rawElement(tmp, htmlTemp, Gfh_tag_.Bry__a
					, tmp_attribs.Clear()
					.Add(Gfh_atr_.Bry__href , url)
					.Add(Gfh_atr_.Bry__class, Class__internal)
					.Add(Gfh_atr_.Bry__title, XomwGlobalFunctions.wfMessage(env, "thumbnail-more").text())
					, Bry_.Empty);
				byte[] zoom_anch = tmp.To_bry_and_clear();
				XomwHtml.rawElement(tmp, htmlTemp, Gfh_tag_.Bry__div, tmp_attribs.Clear().Add(Gfh_atr_.Bry__class, Class__magnify), zoom_anch);
				zoom_icon = tmp.To_bry_and_clear();
			}
		}
		bfr.Add_str_a7("  <div class=\"thumbcaption\">").Add(zoom_icon).Add(frameParams.caption).Add_str_a7("</div></div></div>");
		Bry_.Replace_all_direct(bfr.Bfr(), Byte_ascii.Nl, Byte_ascii.Space, rv_bgn, bfr.Len());	// XO.MW:str_replace("\n", ' ', $s);
	}
//		/**
//		* Process responsive images: add 1.5x and 2x subimages to the thumbnail, where
//		* applicable.
//		*
//		* @param File $file
//		* @param MediaTransformOutput $thumb
//		* @param array $hp Image parameters
//		*/
//		public static function processResponsiveImages( $file, $thumb, $hp ) {
//			global $wgResponsiveImages;
//			if ( $wgResponsiveImages && $thumb && !$thumb->isError() ) {
//				$hp15 = $hp;
//				$hp15['width'] = round( $hp['width'] * 1.5 );
//				$hp20 = $hp;
//				$hp20['width'] = $hp['width'] * 2;
//				if ( isset( $hp['height'] ) ) {
//					$hp15['height'] = round( $hp['height'] * 1.5 );
//					$hp20['height'] = $hp['height'] * 2;
//				}
//
//				$thumb15 = $file->transform( $hp15 );
//				$thumb20 = $file->transform( $hp20 );
//				if ( $thumb15 && !$thumb15->isError() && $thumb15->getUrl() !== $thumb->getUrl() ) {
//					$thumb->responsiveUrls['1.5'] = $thumb15->getUrl();
//				}
//				if ( $thumb20 && !$thumb20->isError() && $thumb20->getUrl() !== $thumb->getUrl() ) {
//					$thumb->responsiveUrls['2'] = $thumb20->getUrl();
//				}
//			}
//		}
//
//		/**
//		* Make a "broken" link to an image
//		*
//		* @since 1.16.3
//		* @param Title $title
//		* @param String $label Link label (plain text)
//		* @param String $query Query String
//		* @param String $unused1 Unused parameter kept for b/c
//		* @param String $unused2 Unused parameter kept for b/c
//		* @param boolean $time A file of a certain timestamp was requested
//		* @return String
//		*/
//		public static function makeBrokenImageLinkObj( $title, $label = '',
//			$query = '', $unused1 = '', $unused2 = '', $time = false
//		) {
//			if ( !$title instanceof Title ) {
//				wfWarn( __METHOD__ . ': Requires $title to be a Title Object.' );
//				return "<!-- ERROR -->" . htmlspecialchars( $label );
//			}
//
//			global $wgEnableUploads, $wgUploadMissingFileUrl, $wgUploadNavigationUrl;
//			if ( $label == '' ) {
//				$label = $title->getPrefixedText();
//			}
//			$encLabel = htmlspecialchars( $label );
//			$currentExists = $time ? ( wfFindFile( $title ) != false ) : false;
//
//			if ( ( $wgUploadMissingFileUrl || $wgUploadNavigationUrl || $wgEnableUploads )
//				&& !$currentExists
//			) {
//				$redir = RepoGroup::singleton()->getLocalRepo()->checkRedirect( $title );
//
//				if ( $redir ) {
//					// We already know it's a redirect, so mark it
//					// accordingly
//					return self::link(
//						$title,
//						$encLabel,
//						[ 'class' => 'mw-redirect' ],
//						wfCgiToArray( $query ),
//						[ 'known', 'noclasses' ]
//					);
//				}
//
//				$href = self::getUploadUrl( $title, $query );
//
//				return '<a href="' . htmlspecialchars( $href ) . '" class="new" title="' .
//					htmlspecialchars( $title->getPrefixedText(), ENT_QUOTES ) . '">' .
//					$encLabel . '</a>';
//			}
//
//			return self::link( $title, $encLabel, [], wfCgiToArray( $query ), [ 'known', 'noclasses' ] );
//		}
//
//		/**
//		* Get the URL to upload a certain file
//		*
//		* @since 1.16.3
//		* @param Title $destFile Title Object of the file to upload
//		* @param String $query Urlencoded query String to prepend
//		* @return String Urlencoded URL
//		*/
//		protected static function getUploadUrl( $destFile, $query = '' ) {
//			global $wgUploadMissingFileUrl, $wgUploadNavigationUrl;
//			$q = 'wpDestFile=' . $destFile->getPartialURL();
//			if ( $query != '' ) {
//				$q .= '&' . $query;
//			}
//
//			if ( $wgUploadMissingFileUrl ) {
//				return wfAppendQuery( $wgUploadMissingFileUrl, $q );
//			} elseif ( $wgUploadNavigationUrl ) {
//				return wfAppendQuery( $wgUploadNavigationUrl, $q );
//			} else {
//				$upload = SpecialPage::getTitleFor( 'Upload' );
//				return $upload->getLocalURL( $q );
//			}
//		}
//
//		/**
//		* Create a direct link to a given uploaded file.
//		*
//		* @since 1.16.3
//		* @param Title $title
//		* @param String $html Pre-sanitized HTML
//		* @param String $time MW timestamp of file creation time
//		* @return String HTML
//		*/
//		public static function makeMediaLinkObj( $title, $html = '', $time = false ) {
//			$img = wfFindFile( $title, [ 'time' => $time ] );
//			return self::makeMediaLinkFile( $title, $img, $html );
//		}
//
//		/**
//		* Create a direct link to a given uploaded file.
//		* This will make a broken link if $file is false.
//		*
//		* @since 1.16.3
//		* @param Title $title
//		* @param File|boolean $file File Object or false
//		* @param String $html Pre-sanitized HTML
//		* @return String HTML
//		*
//		* @todo Handle invalid or missing images better.
//		*/
//		public static function makeMediaLinkFile( Title $title, $file, $html = '' ) {
//			if ( $file && $file->exists() ) {
//				$url = $file->getUrl();
//				$class = '@gplx.Internal protected';
//			} else {
//				$url = self::getUploadUrl( $title );
//				$class = 'new';
//			}
//
//			$alt = $title->getText();
//			if ( $html == '' ) {
//				$html = $alt;
//			}
//
//			$ret = '';
//			$attribs = [
//				'href' => $url,
//				'class' => $class,
//				'title' => $alt
//			];
//
//			if ( !Hooks::run( 'LinkerMakeMediaLinkFile',
//				[ $title, $file, &$html, &$attribs, &$ret ] ) ) {
//				wfDebug( "Hook LinkerMakeMediaLinkFile changed the output of link "
//					. "with url {$url} and text {$html} to {$ret}\n", true );
//				return $ret;
//			}
//
//			return Html::rawElement( 'a', $attribs, $html );
//		}
//
//		/**
//		* Make a link to a special page given its name and, optionally,
//		* a message key from the link text.
//		* Usage example: Linker::specialLink( 'Recentchanges' )
//		*
//		* @since 1.16.3
//		* @param String $name
//		* @param String $key
//		* @return String
//		*/
//		public static function specialLink( $name, $key = '' ) {
//			if ( $key == '' ) {
//				$key = strtolower( $name );
//			}
//
//			return self::linkKnown( SpecialPage::getTitleFor( $name ), wfMessage( $key )->text() );
//		}

	/**
	* Make an external link
	* @since 1.16.3. $title added in 1.21
	* @param String $url URL to link to
	* @param String $text Text of link
	* @param boolean $escape Do we escape the link text?
	* @param String $linktype Type of external link. Gets added to the classes
	* @param array $attribs Array of extra attributes to <a>
	* @param Title|null $title Title Object used for title specific link attributes
	* @return String
	*/
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	public void makeExternalLink(Bry_bfr bfr, byte[] url, byte[] text, boolean escape, byte[] link_type, Xomw_atr_mgr attribs, byte[] title) {
		tmp.Add_str_a7("external");
		if (link_type != null) {
			tmp.Add_byte_space().Add(link_type);
		}
		Xomw_atr_itm cls_itm = attribs.Get_by_or_make(Atr__class);
		if (cls_itm.Val() != null) {
 				tmp.Add(cls_itm.Val());
		}
		cls_itm.Val_(tmp.To_bry_and_clear());

		if (escape) {
			text = tmp.Add_bry_escape_html(text).To_bry_and_clear();
		}

		if (title == null)
			title = wg_title;

		byte[] new_rel = getExternalLinkRel(url, title);
		Xomw_atr_itm cur_rel_atr = attribs.Get_by_or_make(Atr__rel);
		if (cur_rel_atr.Val() == null) {
			cur_rel_atr.Val_(new_rel);
		}
		else {
			// Merge the rel attributes.
			byte[] cur_rel = cur_rel_atr.Val();
			Bry_split_.Split(new_rel, 0, new_rel.length, Byte_ascii.Space, Bool_.N, splitter);	// $newRels = explode(' ', $newRel);
			Bry_split_.Split(cur_rel, 0, cur_rel.length, Byte_ascii.Space, Bool_.N, splitter);	// $oldRels = explode(' ', $attribs['rel']);
			cur_rel_atr.Val_(splitter.To_bry());		// $attribs['rel'] = implode(' ', $combined);				
		}
		// XO.MW.HOOK:LinkerMakeExternalLink
		attribs.Set(Atr__href, url);

		XomwHtml.rawElement(bfr, htmlTemp, Bry_.new_a7("a"), attribs, text);
	}
	// XO.MW: MW puts this function in Parser.php
	private byte[] getExternalLinkRel(byte[] url, byte[] title) {
		// global $wgNoFollowLinks, $wgNoFollowNsExceptions, $wgNoFollowDomainExceptions;
		// $ns = $title ? $title->getNamespace() : false;
		// if ($wgNoFollowLinks && !in_array($ns, $wgNoFollowNsExceptions)
		//	&& !wfMatchesDomainList($url, $wgNoFollowDomainExceptions)
		//) {
			return Rel__nofollow;
		// }
		// return null;
	}
//		/**
//		* Make user link (or user contributions for unregistered users)
//		* @param int $userId User id in database.
//		* @param String $userName User name in database.
//		* @param String $altUserName Text to display instead of the user name (optional)
//		* @return String HTML fragment
//		* @since 1.16.3. $altUserName was added in 1.19.
//		*/
//		public static function userLink( $userId, $userName, $altUserName = false ) {
//			$classes = 'mw-userlink';
//			if ( $userId == 0 ) {
//				$page = SpecialPage::getTitleFor( 'Contributions', $userName );
//				if ( $altUserName === false ) {
//					$altUserName = IP::prettifyIP( $userName );
//				}
//				$classes .= ' mw-anonuserlink'; // Separate link class for anons (bug 43179)
//			} else {
//				$page = Title::makeTitle( NS_USER, $userName );
//			}
//
//			// Wrap the output with <bdi> tags for directionality isolation
//			return self::link(
//				$page,
//				'<bdi>' . htmlspecialchars( $altUserName !== false ? $altUserName : $userName ) . '</bdi>',
//				[ 'class' => $classes ]
//			);
//		}
//
//		/**
//		* Generate standard user tool links (talk, contributions, block link, etc.)
//		*
//		* @since 1.16.3
//		* @param int $userId User identifier
//		* @param String $userText User name or IP address
//		* @param boolean $redContribsWhenNoEdits Should the contributions link be
//		*   red if the user has no edits?
//		* @param int $flags Customisation flags (e.g. Linker::TOOL_LINKS_NOBLOCK
//		*   and Linker::TOOL_LINKS_EMAIL).
//		* @param int $edits User edit count (optional, for performance)
//		* @return String HTML fragment
//		*/
//		public static function userToolLinks(
//			$userId, $userText, $redContribsWhenNoEdits = false, $flags = 0, $edits = null
//		) {
//			global $wgUser, $wgDisableAnonTalk, $wgLang;
//			$talkable = !( $wgDisableAnonTalk && 0 == $userId );
//			$blockable = !( $flags & self::TOOL_LINKS_NOBLOCK );
//			$addEmailLink = $flags & self::TOOL_LINKS_EMAIL && $userId;
//
//			$items = [];
//			if ( $talkable ) {
//				$items[] = self::userTalkLink( $userId, $userText );
//			}
//			if ( $userId ) {
//				// check if the user has an edit
//				$attribs = [];
//				if ( $redContribsWhenNoEdits ) {
//					if ( intval( $edits ) === 0 && $edits !== 0 ) {
//						$user = User::newFromId( $userId );
//						$edits = $user->getEditCount();
//					}
//					if ( $edits === 0 ) {
//						$attribs['class'] = 'new';
//					}
//				}
//				$contribsPage = SpecialPage::getTitleFor( 'Contributions', $userText );
//
//				$items[] = self::link( $contribsPage, wfMessage( 'contribslink' )->escaped(), $attribs );
//			}
//			if ( $blockable && $wgUser->isAllowed( 'block' ) ) {
//				$items[] = self::blockLink( $userId, $userText );
//			}
//
//			if ( $addEmailLink && $wgUser->canSendEmail() ) {
//				$items[] = self::emailLink( $userId, $userText );
//			}
//
//			Hooks::run( 'UserToolLinksEdit', [ $userId, $userText, &$items ] );
//
//			if ( $items ) {
//				return wfMessage( 'word-separator' )->escaped()
//					. '<span class="mw-usertoollinks">'
//					. wfMessage( 'parentheses' )->rawParams( $wgLang->pipeList( $items ) )->escaped()
//					. '</span>';
//			} else {
//				return '';
//			}
//		}
//
//		/**
//		* Alias for userToolLinks( $userId, $userText, true );
//		* @since 1.16.3
//		* @param int $userId User identifier
//		* @param String $userText User name or IP address
//		* @param int $edits User edit count (optional, for performance)
//		* @return String
//		*/
//		public static function userToolLinksRedContribs( $userId, $userText, $edits = null ) {
//			return self::userToolLinks( $userId, $userText, true, 0, $edits );
//		}
//
//		/**
//		* @since 1.16.3
//		* @param int $userId User id in database.
//		* @param String $userText User name in database.
//		* @return String HTML fragment with user talk link
//		*/
//		public static function userTalkLink( $userId, $userText ) {
//			$userTalkPage = Title::makeTitle( NS_USER_TALK, $userText );
//			$userTalkLink = self::link( $userTalkPage, wfMessage( 'talkpagelinktext' )->escaped() );
//			return $userTalkLink;
//		}
//
//		/**
//		* @since 1.16.3
//		* @param int $userId Userid
//		* @param String $userText User name in database.
//		* @return String HTML fragment with block link
//		*/
//		public static function blockLink( $userId, $userText ) {
//			$blockPage = SpecialPage::getTitleFor( 'Block', $userText );
//			$blockLink = self::link( $blockPage, wfMessage( 'blocklink' )->escaped() );
//			return $blockLink;
//		}
//
//		/**
//		* @param int $userId Userid
//		* @param String $userText User name in database.
//		* @return String HTML fragment with e-mail user link
//		*/
//		public static function emailLink( $userId, $userText ) {
//			$emailPage = SpecialPage::getTitleFor( 'Emailuser', $userText );
//			$emailLink = self::link( $emailPage, wfMessage( 'emaillink' )->escaped() );
//			return $emailLink;
//		}
//
//		/**
//		* Generate a user link if the current user is allowed to view it
//		* @since 1.16.3
//		* @param Revision $rev
//		* @param boolean $isPublic Show only if all users can see it
//		* @return String HTML fragment
//		*/
//		public static function revUserLink( $rev, $isPublic = false ) {
//			if ( $rev->isDeleted( Revision::DELETED_USER ) && $isPublic ) {
//				$link = wfMessage( 'rev-deleted-user' )->escaped();
//			} elseif ( $rev->userCan( Revision::DELETED_USER ) ) {
//				$link = self::userLink( $rev->getUser( Revision::FOR_THIS_USER ),
//					$rev->getUserText( Revision::FOR_THIS_USER ) );
//			} else {
//				$link = wfMessage( 'rev-deleted-user' )->escaped();
//			}
//			if ( $rev->isDeleted( Revision::DELETED_USER ) ) {
//				return '<span class="history-deleted">' . $link . '</span>';
//			}
//			return $link;
//		}
//
//		/**
//		* Generate a user tool link cluster if the current user is allowed to view it
//		* @since 1.16.3
//		* @param Revision $rev
//		* @param boolean $isPublic Show only if all users can see it
//		* @return String HTML
//		*/
//		public static function revUserTools( $rev, $isPublic = false ) {
//			if ( $rev->isDeleted( Revision::DELETED_USER ) && $isPublic ) {
//				$link = wfMessage( 'rev-deleted-user' )->escaped();
//			} elseif ( $rev->userCan( Revision::DELETED_USER ) ) {
//				$userId = $rev->getUser( Revision::FOR_THIS_USER );
//				$userText = $rev->getUserText( Revision::FOR_THIS_USER );
//				$link = self::userLink( $userId, $userText )
//					. self::userToolLinks( $userId, $userText );
//			} else {
//				$link = wfMessage( 'rev-deleted-user' )->escaped();
//			}
//			if ( $rev->isDeleted( Revision::DELETED_USER ) ) {
//				return ' <span class="history-deleted">' . $link . '</span>';
//			}
//			return $link;
//		}
//
//		/**
//		* This function is called by all recent changes variants, by the page history,
//		* and by the user contributions list. It is responsible for formatting edit
//		* summaries. It escapes any HTML in the summary, but adds some CSS to format
//		* auto-generated comments (from section editing) and formats [[wikilinks]].
//		*
//		* @author Erik Moeller <moeller@scireview.de>
//		* @since 1.16.3. $wikiId added in 1.26
//		*
//		* Note: there's not always a title to pass to this function.
//		* Since you can't set a default parameter for a reference, I've turned it
//		* temporarily to a value pass. Should be adjusted further. --brion
//		*
//		* @param String $comment
//		* @param Title|null $title Title Object (to generate link to the section in autocomment)
//		*  or null
//		* @param boolean $local Whether section links should refer to local page
//		* @param String|null $wikiId Id (as used by WikiMap) of the wiki to generate links to.
//		*  For use with external changes.
//		*
//		* @return mixed|String
//		*/
//		public static function formatComment(
//			$comment, $title = null, $local = false, $wikiId = null
//		) {
//			# Sanitize text a bit:
//			$comment = str_replace( "\n", " ", $comment );
//			# Allow HTML entities (for bug 13815)
//			$comment = Sanitizer::escapeHtmlAllowEntities( $comment );
//
//			# Render autocomments and make links:
//			$comment = self::formatAutocomments( $comment, $title, $local, $wikiId );
//			$comment = self::formatLinksInComment( $comment, $title, $local, $wikiId );
//
//			return $comment;
//		}
//
//		/**
//		* Converts autogenerated comments in edit summaries into section links.
//		*
//		* The pattern for autogen comments is / * foo * /, which makes for
//		* some nasty regex.
//		* We look for all comments, match any text before and after the comment,
//		* add a separator where needed and format the comment itself with CSS
//		* Called by Linker::formatComment.
//		*
//		* @param String $comment Comment text
//		* @param Title|null $title An optional title Object used to links to sections
//		* @param boolean $local Whether section links should refer to local page
//		* @param String|null $wikiId Id of the wiki to link to (if not the local wiki),
//		*  as used by WikiMap.
//		*
//		* @return String Formatted comment (wikitext)
//		*/
//		private static function formatAutocomments(
//			$comment, $title = null, $local = false, $wikiId = null
//		) {
//			// @todo $append here is something of a hack to preserve the status
//			// quo. Someone who knows more about bidi and such should decide
//			// (1) what sane rendering even *is* for an LTR edit summary on an RTL
//			// wiki, both when autocomments exist and when they don't, and
//			// (2) what markup will make that actually happen.
//			$append = '';
//			$comment = preg_replace_callback(
//				// To detect the presence of content before or after the
//				// auto-comment, we use capturing groups inside optional zero-width
//				// assertions. But older versions of PCRE can't directly make
//				// zero-width assertions optional, so wrap them in a non-capturing
//				// group.
//				'!(?:(?<=(.)))?/\*\s*(.*?)\s*\*/(?:(?=(.)))?!',
//				function ( $match ) use ( $title, $local, $wikiId, &$append ) {
//					global $wgLang;
//
//					// Ensure all match positions are defined
//					$match += [ '', '', '', '' ];
//
//					$pre = $match[1] !== '';
//					$auto = $match[2];
//					$post = $match[3] !== '';
//					$comment = null;
//
//					Hooks::run(
//						'FormatAutocomments',
//						[ &$comment, $pre, $auto, $post, $title, $local, $wikiId ]
//					);
//
//					if ( $comment === null ) {
//						$link = '';
//						if ( $title ) {
//							$section = $auto;
//							# Remove links that a user may have manually put in the autosummary
//							# This could be improved by copying as much of Parser::stripSectionName as desired.
//							$section = str_replace( '[[:', '', $section );
//							$section = str_replace( '[[', '', $section );
//							$section = str_replace( ']]', '', $section );
//
//							$section = Sanitizer::normalizeSectionNameWhitespace( $section ); # bug 22784
//							if ( $local ) {
//								$sectionTitle = Title::newFromText( '#' . $section );
//							} else {
//								$sectionTitle = Title::makeTitleSafe( $title->getNamespace(),
//									$title->getDBkey(), $section );
//							}
//							if ( $sectionTitle ) {
//								$link = Linker::makeCommentLink( $sectionTitle, $wgLang->getArrow(), $wikiId, 'noclasses' );
//							} else {
//								$link = '';
//							}
//						}
//						if ( $pre ) {
//							# written summary $presep autocomment (summary /* section */)
//							$pre = wfMessage( 'autocomment-prefix' )->inContentLanguage()->escaped();
//						}
//						if ( $post ) {
//							# autocomment $postsep written summary (/* section */ summary)
//							$auto .= wfMessage( 'colon-separator' )->inContentLanguage()->escaped();
//						}
//						$auto = '<span class="autocomment">' . $auto . '</span>';
//						$comment = $pre . $link . $wgLang->getDirMark()
//							. '<span dir="auto">' . $auto;
//						$append .= '</span>';
//					}
//					return $comment;
//				},
//				$comment
//			);
//			return $comment . $append;
//		}
//
//		/**
//		* Formats wiki links and media links in text; all other wiki formatting
//		* is ignored
//		*
//		* @since 1.16.3. $wikiId added in 1.26
//		* @todo FIXME: Doesn't handle sub-links as in image thumb texts like the main parser
//		*
//		* @param String $comment Text to format links in. WARNING! Since the output of this
//		*	function is html, $comment must be sanitized for use as html. You probably want
//		*	to pass $comment through Sanitizer::escapeHtmlAllowEntities() before calling
//		*	this function.
//		* @param Title|null $title An optional title Object used to links to sections
//		* @param boolean $local Whether section links should refer to local page
//		* @param String|null $wikiId Id of the wiki to link to (if not the local wiki),
//		*  as used by WikiMap.
//		*
//		* @return String
//		*/
//		public static function formatLinksInComment(
//			$comment, $title = null, $local = false, $wikiId = null
//		) {
//			return preg_replace_callback(
//				'/
//					\[\[
//					:? # ignore optional leading colon
//					([^\]|]+) # 1. link target; page names cannot include ] or |
//					(?:\|
//						# 2. link text
//						# Stop matching at ]] without relying on backtracking.
//						((?:]?[^\]])*+)
//					)?
//					\]\]
//					([^[]*) # 3. link trail (the text up until the next link)
//				/x',
//				function ( $match ) use ( $title, $local, $wikiId ) {
//					global $wgContLang;
//
//					$medians = '(?:' . preg_quote( XomwNamespace::getCanonicalName( NS_MEDIA ), '/' ) . '|';
//					$medians .= preg_quote( $wgContLang->getNsText( NS_MEDIA ), '/' ) . '):';
//
//					$comment = $match[0];
//
//					# fix up urlencoded title texts (copied from Parser::replaceInternalLinks)
//					if ( strpos( $match[1], '%' ) !== false ) {
//						$match[1] = strtr(
//							rawurldecode( $match[1] ),
//							[ '<' => '&lt;', '>' => '&gt;' ]
//						);
//					}
//
//					# Handle link renaming [[foo|text]] will show link as "text"
//					if ( $match[2] != "" ) {
//						$text = $match[2];
//					} else {
//						$text = $match[1];
//					}
//					$submatch = [];
//					$thelink = null;
//					if ( preg_match( '/^' . $medians . '(.*)$/i', $match[1], $submatch ) ) {
//						# Media link; trail not supported.
//						$linkRegexp = '/\[\[(.*?)\]\]/';
//						$title = Title::makeTitleSafe( NS_FILE, $submatch[1] );
//						if ( $title ) {
//							$thelink = Linker::makeMediaLinkObj( $title, $text );
//						}
//					} else {
//						# Other kind of link
//						# Make sure its target is non-empty
//						if ( isset( $match[1][0] ) && $match[1][0] == ':' ) {
//							$match[1] = substr( $match[1], 1 );
//						}
//						if ( $match[1] !== false && $match[1] !== '' ) {
//							if ( preg_match( $wgContLang->linkTrail(), $match[3], $submatch ) ) {
//								$trail = $submatch[1];
//							} else {
//								$trail = "";
//							}
//							$linkRegexp = '/\[\[(.*?)\]\]' . preg_quote( $trail, '/' ) . '/';
//							list( $inside, $trail ) = Linker::splitTrail( $trail );
//
//							$linkText = $text;
//							$linkTarget = Linker::normalizeSubpageLink( $title, $match[1], $linkText );
//
//							$target = Title::newFromText( $linkTarget );
//							if ( $target ) {
//								if ( $target->getText() == '' && !$target->isExternal()
//									&& !$local && $title
//								) {
//									$newTarget = clone $title;
//									$newTarget->setFragment( '#' . $target->getFragment() );
//									$target = $newTarget;
//								}
//
//								$thelink = Linker::makeCommentLink( $target, $linkText . $inside, $wikiId ) . $trail;
//							}
//						}
//					}
//					if ( $thelink ) {
//						// If the link is still valid, go ahead and replace it in!
//						$comment = preg_replace(
//							$linkRegexp,
//							StringUtils::escapeRegexReplacement( $thelink ),
//							$comment,
//							1
//						);
//					}
//
//					return $comment;
//				},
//				$comment
//			);
//		}
//
//		/**
//		* Generates a link to the given Title
//		*
//		* @note This is only public for technical reasons. It's not intended for use outside Linker.
//		*
//		* @param Title $title
//		* @param String $text
//		* @param String|null $wikiId Id of the wiki to link to (if not the local wiki),
//		*  as used by WikiMap.
//		* @param String|String[] $options See the $options parameter in Linker::link.
//		*
//		* @return String HTML link
//		*/
//		public static function makeCommentLink(
//			Title $title, $text, $wikiId = null, $options = []
//		) {
//			if ( $wikiId !== null && !$title->isExternal() ) {
//				$link = Linker::makeExternalLink(
//					WikiMap::getForeignURL(
//						$wikiId,
//						$title->getPrefixedText(),
//						$title->getFragment()
//					),
//					$text,
//					/* escape = */ false // Already escaped
//				);
//			} else {
//				$link = Linker::link( $title, $text, [], [], $options );
//			}
//
//			return $link;
//		}

	/**
	* @param Title $contextTitle
	* @param String $target
	* @param String $text
	* @return String
	*/
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	public void normalizeSubpageLink(XomwLinker_NormalizeSubpageLink rv, XomwTitle context_title, byte[] target, byte[] text) {
		// Valid link forms:
		// Foobar -- normal
		// :Foobar -- override special treatment of prefix (images, language links)
		// /Foobar -- convert to CurrentPage/Foobar
		// /Foobar/ -- convert to CurrentPage/Foobar, strip the initial and final / from text
		// ../ -- convert to CurrentPage, from CurrentPage/CurrentSubPage
		// ../Foobar -- convert to CurrentPage/Foobar,
		//              (from CurrentPage/CurrentSubPage)
		// ../Foobar/ -- convert to CurrentPage/Foobar, use 'Foobar' as text
		//              (from CurrentPage/CurrentSubPage)

		byte[] ret = target; // default return value is no change

		// Some namespaces don't allow subpages,
		// so only perform processing if subpages are allowed
		if (context_title != null) {// && context_title.Ns().Subpages_enabled()) {
			int hash = Bry_find_.Find_fwd(target, Byte_ascii.Hash);
			byte[] suffix = null;
			if (hash != Bry_find_.Not_found) {
				suffix = Bry_.Mid(target, hash);
				target = Bry_.Mid(target, 0, hash);
			}
			else {
				suffix = Bry_.Empty;
			}
			// bug 7425
			target = Bry_.Trim(target);
			// Look at the first character
			if (target != Bry_.Empty && target[0] == Byte_ascii.Slash) {
				// / at end means we don't want the slash to be shown
				int target_len = target.length;
				int trailing_slashes_bgn = Bry_find_.Find_bwd_while(target, target_len, 0, Byte_ascii.Slash) + 1;
				byte[] no_slash = null;
				if (trailing_slashes_bgn != target_len) {
					no_slash = target = Bry_.Mid(target, 1, trailing_slashes_bgn);
				}
				else {
					no_slash = Bry_.Mid(target, 1);
				}

				ret = Bry_.Add(context_title.getPrefixedText(), Byte_ascii.Slash_bry, Bry_.Trim(no_slash), suffix);
				if (text == Bry_.Empty) {
					text = Bry_.Add(target, suffix);
				} // this might be changed for ugliness reasons
			}
			else {
				// check for .. subpage backlinks
				int dot2_count = 0;
				byte[] dot2_stripped = target;
				while (Bry_.Match(dot2_stripped, 0, 3, Bry__dot2)) {
					++dot2_count;
					dot2_stripped = Bry_.Mid(dot2_stripped, 3);
				}
				if (dot2_count > 0) {
					byte[][] exploded = Bry_split_.Split(context_title.getPrefixedText(), Byte_ascii.Slash);
					int exploded_len = exploded.length;
					if (exploded_len > dot2_count) { // not allowed to go below top level page
						//	PORTED: ret = implode('/', array_slice($exploded, 0, -dot2_count));
						int implode_len = exploded_len - dot2_count;
						for (int i = 0; i < implode_len; i++) {
							if (i != 0) tmp.Add_byte(Byte_ascii.Slash);
							tmp.Add(exploded[i]);
						}
						// / at the end means don't show full path
						if (Bry_.Has_at_end(dot2_stripped, Byte_ascii.Slash)) {
							dot2_stripped = Bry_.Mid(dot2_stripped, 0, dot2_stripped.length - 1);
							if (text == Bry_.Empty) {
								text = Bry_.Add(dot2_stripped, suffix);
							}
						}
						dot2_stripped = Bry_.Trim(dot2_stripped);
						if (dot2_stripped != Bry_.Empty) {
							tmp.Add_bry_many(Byte_ascii.Slash_bry, dot2_stripped);
						}
						tmp.Add(suffix);
						ret = tmp.To_bry_and_clear();
					}
				}
			}
		}

		rv.Init(ret, text);
	}
//		/**
//		* Wrap a comment in standard punctuation and formatting if
//		* it's non-empty, otherwise return empty String.
//		*
//		* @since 1.16.3. $wikiId added in 1.26
//		* @param String $comment
//		* @param Title|null $title Title Object (to generate link to section in autocomment) or null
//		* @param boolean $local Whether section links should refer to local page
//		* @param String|null $wikiId Id (as used by WikiMap) of the wiki to generate links to.
//		*  For use with external changes.
//		*
//		* @return String
//		*/
//		public static function commentBlock(
//			$comment, $title = null, $local = false, $wikiId = null
//		) {
//			// '*' used to be the comment inserted by the software way back
//			// in antiquity in case none was provided, here for backwards
//			// compatibility, acc. to brion -�var
//			if ( $comment == '' || $comment == '*' ) {
//				return '';
//			} else {
//				$formatted = self::formatComment( $comment, $title, $local, $wikiId );
//				$formatted = wfMessage( 'parentheses' )->rawParams( $formatted )->escaped();
//				return " <span class=\"comment\">$formatted</span>";
//			}
//		}
//
//		/**
//		* Wrap and format the given revision's comment block, if the current
//		* user is allowed to view it.
//		*
//		* @since 1.16.3
//		* @param Revision $rev
//		* @param boolean $local Whether section links should refer to local page
//		* @param boolean $isPublic Show only if all users can see it
//		* @return String HTML fragment
//		*/
//		public static function revComment( Revision $rev, $local = false, $isPublic = false ) {
//			if ( $rev->getComment( Revision::RAW ) == "" ) {
//				return "";
//			}
//			if ( $rev->isDeleted( Revision::DELETED_COMMENT ) && $isPublic ) {
//				$block = " <span class=\"comment\">" . wfMessage( 'rev-deleted-comment' )->escaped() . "</span>";
//			} elseif ( $rev->userCan( Revision::DELETED_COMMENT ) ) {
//				$block = self::commentBlock( $rev->getComment( Revision::FOR_THIS_USER ),
//					$rev->getTitle(), $local );
//			} else {
//				$block = " <span class=\"comment\">" . wfMessage( 'rev-deleted-comment' )->escaped() . "</span>";
//			}
//			if ( $rev->isDeleted( Revision::DELETED_COMMENT ) ) {
//				return " <span class=\"history-deleted\">$block</span>";
//			}
//			return $block;
//		}
//
//		/**
//		* @since 1.16.3
//		* @param int $size
//		* @return String
//		*/
//		public static function formatRevisionSize( $size ) {
//			if ( $size == 0 ) {
//				$stxt = wfMessage( 'historyempty' )->escaped();
//			} else {
//				$stxt = wfMessage( 'nbytes' )->numParams( $size )->escaped();
//				$stxt = wfMessage( 'parentheses' )->rawParams( $stxt )->escaped();
//			}
//			return "<span class=\"history-size\">$stxt</span>";
//		}
//
//		/**
//		* Add another level to the Table of Contents
//		*
//		* @since 1.16.3
//		* @return String
//		*/
//		public static function tocIndent() {
//			return "\n<ul>";
//		}
//
//		/**
//		* Finish one or more sublevels on the Table of Contents
//		*
//		* @since 1.16.3
//		* @param int $level
//		* @return String
//		*/
//		public static function tocUnindent( $level ) {
//			return "</li>\n" . str_repeat( "</ul>\n</li>\n", $level > 0 ? $level : 0 );
//		}
//
//		/**
//		* parameter level defines if we are on an indentation level
//		*
//		* @since 1.16.3
//		* @param String $anchor
//		* @param String $tocline
//		* @param String $tocnumber
//		* @param String $level
//		* @param String|boolean $sectionIndex
//		* @return String
//		*/
//		public static function tocLine( $anchor, $tocline, $tocnumber, $level, $sectionIndex = false ) {
//			$classes = "toclevel-$level";
//			if ( $sectionIndex !== false ) {
//				$classes .= " tocsection-$sectionIndex";
//			}
//			return "\n<li class=\"$classes\"><a href=\"#" .
//				$anchor . '"><span class="tocnumber">' .
//				$tocnumber . '</span> <span class="toctext">' .
//				$tocline . '</span></a>';
//		}
//
//		/**
//		* End a Table Of Contents line.
//		* tocUnindent() will be used instead if we're ending a line below
//		* the new level.
//		* @since 1.16.3
//		* @return String
//		*/
//		public static function tocLineEnd() {
//			return "</li>\n";
//		}
//
//		/**
//		* Wraps the TOC in a table and provides the hide/collapse javascript.
//		*
//		* @since 1.16.3
//		* @param String $toc Html of the Table Of Contents
//		* @param String|Language|boolean $lang Language for the toc title, defaults to user language
//		* @return String Full html of the TOC
//		*/
//		public static function tocList( $toc, $lang = false ) {
//			$lang = wfGetLangObj( $lang );
//			$title = wfMessage( 'toc' )->inLanguage( $lang )->escaped();
//
//			return '<div id="toc" class="toc">'
//				. '<div id="toctitle"><h2>' . $title . "</h2></div>\n"
//				. $toc
//				. "</ul>\n</div>\n";
//		}
//
//		/**
//		* Generate a table of contents from a section tree.
//		*
//		* @since 1.16.3. $lang added in 1.17
//		* @param array $tree Return value of ParserOutput::getSections()
//		* @param String|Language|boolean $lang Language for the toc title, defaults to user language
//		* @return String HTML fragment
//		*/
//		public static function generateTOC( $tree, $lang = false ) {
//			$toc = '';
//			$lastLevel = 0;
//			foreach ( $tree as $section ) {
//				if ( $section['toclevel'] > $lastLevel ) {
//					$toc .= self::tocIndent();
//				} elseif ( $section['toclevel'] < $lastLevel ) {
//					$toc .= self::tocUnindent(
//						$lastLevel - $section['toclevel'] );
//				} else {
//					$toc .= self::tocLineEnd();
//				}
//
//				$toc .= self::tocLine( $section['anchor'],
//					$section['line'], $section['number'],
//					$section['toclevel'], $section['index'] );
//				$lastLevel = $section['toclevel'];
//			}
//			$toc .= self::tocLineEnd();
//			return self::tocList( $toc, $lang );
//		}
//
//		/**
//		* Create a headline for content
//		*
//		* @since 1.16.3
//		* @param int $level The level of the headline (1-6)
//		* @param String $attribs Any attributes for the headline, starting with
//		*   a space and ending with '>'
//		*   This *must* be at least '>' for no attribs
//		* @param String $anchor The anchor to give the headline (the bit after the #)
//		* @param String $html Html for the text of the header
//		* @param String $link HTML to add for the section edit link
//		* @param boolean|String $legacyAnchor A second, optional anchor to give for
//		*   backward compatibility (false to omit)
//		*
//		* @return String HTML headline
//		*/
//		public static function makeHeadline( $level, $attribs, $anchor, $html,
//			$link, $legacyAnchor = false
//		) {
//			$ret = "<h$level$attribs"
//				. "<span class=\"mw-headline\" id=\"$anchor\">$html</span>"
//				. $link
//				. "</h$level>";
//			if ( $legacyAnchor !== false ) {
//				$ret = "<div id=\"$legacyAnchor\"></div>$ret";
//			}
//			return $ret;
//		}

	/**
	* Split a link trail, return the "inside" portion and the remainder of the trail
	* as a two-element array
	* @param String $trail
	* @return array
	*/
	// XO.MW:SYNC:1.29; DATE:2017-02-08
	public byte[][] splitTrail(byte[] trail) {
		int cur = 0;
		int src_end = trail.length;
		while (true) {
			Object o = split_trail_trie.Match_at(trv, trail, cur, src_end);
			if (o == null) break;
			byte[] bry = (byte[])o;
			cur += bry.length;
		}

		if (cur == 0) { // no trail
			split_trail_rv[0] = null;
			split_trail_rv[1] = trail;
		}
		else {
			split_trail_rv[0] = Bry_.Mid(trail, 0, cur);
			split_trail_rv[1] = Bry_.Mid(trail, cur, src_end);
		}
		return split_trail_rv;
	}
//		// Create a direct link to a given uploaded file.
//		public static function makeMediaLinkObj($title, $html = '', $time = false) {
//			$img = wfFindFile($title, [ 'time' => $time ]);
//			return self::makeMediaLinkFile($title, $img, $html);
//		}
//
//		// Create a direct link to a given uploaded file.
//		// This will make a broken link if $file is false.
//		public static function makeMediaLinkFile(Title $title, $file, $html = '') {
//			if ($file && $file->exists()) {
//				$url = $file->getUrl();
//				$class = '@gplx.Internal protected';
//			} else {
//				$url = self::getUploadUrl($title);
//				$class = 'new';
//			}
//
//			$alt = $title->getText();
//			if ($html == '') {
//				$html = $alt;
//			}
//
//			$ret = '';
//			$attribs = [
//				'href' => $url,
//				'class' => $class,
//				'title' => $alt
//			];
//
//			if (!Hooks::run('LinkerMakeMediaLinkFile',
//				[ $title, $file, &$html, &$attribs, &$ret ])) {
//				wfDebug("Hook LinkerMakeMediaLinkFile changed the output of link "
//					. "with url {$url} and text {$html} to {$ret}\n", true);
//				return $ret;
//			}
//
//			return Html::rawElement('a', $attribs, $html);
//		}
//		/**
//		* Generate a rollback link for a given revision.  Currently it's the
//		* caller's responsibility to ensure that the revision is the top one. If
//		* it's not, of course, the user will get an error message.
//		*
//		* If the calling page is called with the parameter &bot=1, all rollback
//		* links also get that parameter. It causes the edit itself and the rollback
//		* to be marked as "bot" edits. Bot edits are hidden by default from recent
//		* changes, so this allows sysops to combat a busy vandal without bothering
//		* other users.
//		*
//		* If the option verify is set this function will return the link only in case the
//		* revision can be reverted. Please note that due to performance limitations
//		* it might be assumed that a user isn't the only contributor of a page while
//		* (s)he is, which will lead to useless rollback links. Furthermore this wont
//		* work if $wgShowRollbackEditCount is disabled, so this can only function
//		* as an additional check.
//		*
//		* If the option noBrackets is set the rollback link wont be enclosed in "[]".
//		*
//		* @since 1.16.3. $context added in 1.20. $options added in 1.21
//		*
//		* @param Revision $rev
//		* @param IContextSource $context Context to use or null for the main context.
//		* @param array $options
//		* @return String
//		*/
//		public static function generateRollback( $rev, IContextSource $context = null,
//			$options = [ 'verify' ]
//		) {
//			if ( $context === null ) {
//				$context = RequestContext::getMain();
//			}
//
//			$editCount = false;
//			if ( in_array( 'verify', $options, true ) ) {
//				$editCount = self::getRollbackEditCount( $rev, true );
//				if ( $editCount === false ) {
//					return '';
//				}
//			}
//
//			$inner = self::buildRollbackLink( $rev, $context, $editCount );
//
//			if ( !in_array( 'noBrackets', $options, true ) ) {
//				$inner = $context->msg( 'brackets' )->rawParams( $inner )->escaped();
//			}
//
//			return '<span class="mw-rollback-link">' . $inner . '</span>';
//		}
//
//		/**
//		* This function will return the number of revisions which a rollback
//		* would revert and, if $verify is set it will verify that a revision
//		* can be reverted (that the user isn't the only contributor and the
//		* revision we might rollback to isn't deleted). These checks can only
//		* function as an additional check as this function only checks against
//		* the last $wgShowRollbackEditCount edits.
//		*
//		* Returns null if $wgShowRollbackEditCount is disabled or false if $verify
//		* is set and the user is the only contributor of the page.
//		*
//		* @param Revision $rev
//		* @param boolean $verify Try to verify that this revision can really be rolled back
//		* @return int|boolean|null
//		*/
//		public static function getRollbackEditCount( $rev, $verify ) {
//			global $wgShowRollbackEditCount;
//			if ( !is_int( $wgShowRollbackEditCount ) || !$wgShowRollbackEditCount > 0 ) {
//				// Nothing has happened, indicate this by returning 'null'
//				return null;
//			}
//
//			$dbr = wfGetDB( DB_REPLICA );
//
//			// Up to the value of $wgShowRollbackEditCount revisions are counted
//			$res = $dbr->select(
//				'revision',
//				[ 'rev_user_text', 'rev_deleted' ],
//				// $rev->getPage() returns null sometimes
//				[ 'rev_page' => $rev->getTitle()->getArticleID() ],
//				__METHOD__,
//				[
//					'USE INDEX' => [ 'revision' => 'page_timestamp' ],
//					'ORDER BY' => 'rev_timestamp DESC',
//					'LIMIT' => $wgShowRollbackEditCount + 1
//				]
//			);
//
//			$editCount = 0;
//			$moreRevs = false;
//			foreach ( $res as $row ) {
//				if ( $rev->getUserText( Revision::RAW ) != $row->rev_user_text ) {
//					if ( $verify &&
//						( $row->rev_deleted & Revision::DELETED_TEXT
//							|| $row->rev_deleted & Revision::DELETED_USER
//					) ) {
//						// If the user or the text of the revision we might rollback
//						// to is deleted in some way we can't rollback. Similar to
//						// the sanity checks in WikiPage::commitRollback.
//						return false;
//					}
//					$moreRevs = true;
//					break;
//				}
//				$editCount++;
//			}
//
//			if ( $verify && $editCount <= $wgShowRollbackEditCount && !$moreRevs ) {
//				// We didn't find at least $wgShowRollbackEditCount revisions made by the current user
//				// and there weren't any other revisions. That means that the current user is the only
//				// editor, so we can't rollback
//				return false;
//			}
//			return $editCount;
//		}
//
//		/**
//		* Build a raw rollback link, useful for collections of "tool" links
//		*
//		* @since 1.16.3. $context added in 1.20. $editCount added in 1.21
//		* @param Revision $rev
//		* @param IContextSource|null $context Context to use or null for the main context.
//		* @param int $editCount Number of edits that would be reverted
//		* @return String HTML fragment
//		*/
//		public static function buildRollbackLink( $rev, IContextSource $context = null,
//			$editCount = false
//		) {
//			global $wgShowRollbackEditCount, $wgMiserMode;
//
//			// To config which pages are affected by miser mode
//			$disableRollbackEditCountSpecialPage = [ 'Recentchanges', 'Watchlist' ];
//
//			if ( $context === null ) {
//				$context = RequestContext::getMain();
//			}
//
//			$title = $rev->getTitle();
//			$query = [
//				'action' => 'rollback',
//				'from' => $rev->getUserText(),
//				'token' => $context->getUser()->getEditToken( 'rollback' ),
//			];
//			$attrs = [
//				'data-mw' => 'interface',
//				'title' => $context->msg( 'tooltip-rollback' )->text(),
//			];
//			$options = [ 'known', 'noclasses' ];
//
//			if ( $context->getRequest()->getBool( 'bot' ) ) {
//				$query['bot'] = '1';
//				$query['hidediff'] = '1'; // bug 15999
//			}
//
//			$disableRollbackEditCount = false;
//			if ( $wgMiserMode ) {
//				foreach ( $disableRollbackEditCountSpecialPage as $specialPage ) {
//					if ( $context->getTitle()->isSpecial( $specialPage ) ) {
//						$disableRollbackEditCount = true;
//						break;
//					}
//				}
//			}
//
//			if ( !$disableRollbackEditCount
//				&& is_int( $wgShowRollbackEditCount )
//				&& $wgShowRollbackEditCount > 0
//			) {
//				if ( !is_numeric( $editCount ) ) {
//					$editCount = self::getRollbackEditCount( $rev, false );
//				}
//
//				if ( $editCount > $wgShowRollbackEditCount ) {
//					$html = $context->msg( 'rollbacklinkcount-morethan' )
//						->numParams( $wgShowRollbackEditCount )->parse();
//				} else {
//					$html = $context->msg( 'rollbacklinkcount' )->numParams( $editCount )->parse();
//				}
//
//				return self::link( $title, $html, $attrs, $query, $options );
//			} else {
//				$html = $context->msg( 'rollbacklink' )->escaped();
//				return self::link( $title, $html, $attrs, $query, $options );
//			}
//		}
//
//		/**
//		* @deprecated since 1.28, use TemplatesOnThisPageFormatter directly
//		*
//		* Returns HTML for the "templates used on this page" list.
//		*
//		* Make an HTML list of templates, and then add a "More..." link at
//		* the bottom. If $more is null, do not add a "More..." link. If $more
//		* is a Title, make a link to that title and use it. If $more is a String,
//		* directly paste it in as the link (escaping needs to be done manually).
//		* Finally, if $more is a Message, call toString().
//		*
//		* @since 1.16.3. $more added in 1.21
//		* @param Title[] $templates Array of templates
//		* @param boolean $preview Whether this is for a preview
//		* @param boolean $section Whether this is for a section edit
//		* @param Title|Message|String|null $more An escaped link for "More..." of the templates
//		* @return String HTML output
//		*/
//		public static function formatTemplates( $templates, $preview = false,
//			$section = false, $more = null
//		) {
//			wfDeprecated( __METHOD__, '1.28' );
//
//			$type = false;
//			if ( $preview ) {
//				$type = 'preview';
//			} elseif ( $section ) {
//				$type = 'section';
//			}
//
//			if ( $more instanceof Message ) {
//				$more = $more->toString();
//			}
//
//			$formatter = new TemplatesOnThisPageFormatter(
//				RequestContext::getMain(),
//				MediaWikiServices::getInstance()->getLinkRenderer()
//			);
//			return $formatter->format( $templates, $type, $more );
//		}
//
//		/**
//		* Returns HTML for the "hidden categories on this page" list.
//		*
//		* @since 1.16.3
//		* @param array $hiddencats Array of hidden categories from Article::getHiddenCategories
//		*   or similar
//		* @return String HTML output
//		*/
//		public static function formatHiddenCategories( $hiddencats ) {
//
//			$outText = '';
//			if ( count( $hiddencats ) > 0 ) {
//				# Construct the HTML
//				$outText = '<div class="mw-hiddenCategoriesExplanation">';
//				$outText .= wfMessage( 'hiddencategories' )->numParams( count( $hiddencats ) )->parseAsBlock();
//				$outText .= "</div><ul>\n";
//
//				foreach ( $hiddencats as $titleObj ) {
//					# If it's hidden, it must exist - no need to check with a LinkBatch
//					$outText .= '<li>'
//						. self::link( $titleObj, null, [], [], 'known' )
//						. "</li>\n";
//				}
//				$outText .= '</ul>';
//			}
//			return $outText;
//		}
//
//		/**
//		* @deprecated since 1.28, use Language::formatSize() directly
//		*
//		* Format a size in bytes for output, using an appropriate
//		* unit (B, KB, MB or GB) according to the magnitude in question
//		*
//		* @since 1.16.3
//		* @param int $size Size to format
//		* @return String
//		*/
//		public static function formatSize( $size ) {
//			wfDeprecated( __METHOD__, '1.28' );
//
//			global $wgLang;
//			return htmlspecialchars( $wgLang->formatSize( $size ) );
//		}
//
//		/**
//		* Given the id of an interface element, constructs the appropriate title
//		* attribute from the system messages.  (Note, this is usually the id but
//		* isn't always, because sometimes the accesskey needs to go on a different
//		* element than the id, for reverse-compatibility, etc.)
//		*
//		* @since 1.16.3 $msgParams added in 1.27
//		* @param String $name Id of the element, minus prefixes.
//		* @param String|null $options Null or the String 'withaccess' to add an access-
//		*   key hint
//		* @param array $msgParams Parameters to pass to the message
//		*
//		* @return String Contents of the title attribute (which you must HTML-
//		*   escape), or false for no title attribute
//		*/
//		public static function titleAttrib( $name, $options = null, array $msgParams = [] ) {
//			$message = wfMessage( "tooltip-$name", $msgParams );
//			if ( !$message->exists() ) {
//				$tooltip = false;
//			} else {
//				$tooltip = $message->text();
//				# Compatibility: formerly some tooltips had [alt-.] hardcoded
//				$tooltip = preg_replace( "/ ?\[alt-.\]$/", '', $tooltip );
//				# Message equal to '-' means suppress it.
//				if ( $tooltip == '-' ) {
//					$tooltip = false;
//				}
//			}
//
//			if ( $options == 'withaccess' ) {
//				$accesskey = self::accesskey( $name );
//				if ( $accesskey !== false ) {
//					// Should be build the same as in jquery.accessKeyLabel.js
//					if ( $tooltip === false || $tooltip === '' ) {
//						$tooltip = wfMessage( 'brackets', $accesskey )->text();
//					} else {
//						$tooltip .= wfMessage( 'word-separator' )->text();
//						$tooltip .= wfMessage( 'brackets', $accesskey )->text();
//					}
//				}
//			}
//
//			return $tooltip;
//		}
//
//		public static $accesskeycache;
//
//		/**
//		* Given the id of an interface element, constructs the appropriate
//		* accesskey attribute from the system messages.  (Note, this is usually
//		* the id but isn't always, because sometimes the accesskey needs to go on
//		* a different element than the id, for reverse-compatibility, etc.)
//		*
//		* @since 1.16.3
//		* @param String $name Id of the element, minus prefixes.
//		* @return String Contents of the accesskey attribute (which you must HTML-
//		*   escape), or false for no accesskey attribute
//		*/
//		public static function accesskey( $name ) {
//			if ( isset( self::$accesskeycache[$name] ) ) {
//				return self::$accesskeycache[$name];
//			}
//
//			$message = wfMessage( "accesskey-$name" );
//
//			if ( !$message->exists() ) {
//				$accesskey = false;
//			} else {
//				$accesskey = $message->plain();
//				if ( $accesskey === '' || $accesskey === '-' ) {
//					# @todo FIXME: Per standard MW behavior, a value of '-' means to suppress the
//					# attribute, but this is broken for accesskey: that might be a useful
//					# value.
//					$accesskey = false;
//				}
//			}
//
//			self::$accesskeycache[$name] = $accesskey;
//			return self::$accesskeycache[$name];
//		}
//
//		/**
//		* Get a revision-deletion link, or disabled link, or nothing, depending
//		* on user permissions & the settings on the revision.
//		*
//		* Will use forward-compatible revision ID in the Special:RevDelete link
//		* if possible, otherwise the timestamp-based ID which may break after
//		* undeletion.
//		*
//		* @param User $user
//		* @param Revision $rev
//		* @param Title $title
//		* @return String HTML fragment
//		*/
//		public static function getRevDeleteLink( User $user, Revision $rev, Title $title ) {
//			$canHide = $user->isAllowed( 'deleterevision' );
//			if ( !$canHide && !( $rev->getVisibility() && $user->isAllowed( 'deletedhistory' ) ) ) {
//				return '';
//			}
//
//			if ( !$rev->userCan( Revision::DELETED_RESTRICTED, $user ) ) {
//				return Linker::revDeleteLinkDisabled( $canHide ); // revision was hidden from sysops
//			} else {
//				if ( $rev->getId() ) {
//					// RevDelete links using revision ID are stable across
//					// page deletion and undeletion; use when possible.
//					$query = [
//						'type' => 'revision',
//						'target' => $title->getPrefixedDBkey(),
//						'ids' => $rev->getId()
//					];
//				} else {
//					// Older deleted entries didn't save a revision ID.
//					// We have to refer to these by timestamp, ick!
//					$query = [
//						'type' => 'archive',
//						'target' => $title->getPrefixedDBkey(),
//						'ids' => $rev->getTimestamp()
//					];
//				}
//				return Linker::revDeleteLink( $query,
//					$rev->isDeleted( Revision::DELETED_RESTRICTED ), $canHide );
//			}
//		}
//
//		/**
//		* Creates a (show/hide) link for deleting revisions/log entries
//		*
//		* @param array $query Query parameters to be passed to link()
//		* @param boolean $restricted Set to true to use a "<strong>" instead of a "<span>"
//		* @param boolean $delete Set to true to use (show/hide) rather than (show)
//		*
//		* @return String HTML "<a>" link to Special:Revisiondelete, wrapped in a
//		* span to allow for customization of appearance with CSS
//		*/
//		public static function revDeleteLink( $query = [], $restricted = false, $delete = true ) {
//			$sp = SpecialPage::getTitleFor( 'Revisiondelete' );
//			$msgKey = $delete ? 'rev-delundel' : 'rev-showdeleted';
//			$html = wfMessage( $msgKey )->escaped();
//			$tag = $restricted ? 'strong' : 'span';
//			$link = self::link( $sp, $html, [], $query, [ 'known', 'noclasses' ] );
//			return Xml::tags(
//				$tag,
//				[ 'class' => 'mw-revdelundel-link' ],
//				wfMessage( 'parentheses' )->rawParams( $link )->escaped()
//			);
//		}
//
//		/**
//		* Creates a dead (show/hide) link for deleting revisions/log entries
//		*
//		* @since 1.16.3
//		* @param boolean $delete Set to true to use (show/hide) rather than (show)
//		*
//		* @return String HTML text wrapped in a span to allow for customization
//		* of appearance with CSS
//		*/
//		public static function revDeleteLinkDisabled( $delete = true ) {
//			$msgKey = $delete ? 'rev-delundel' : 'rev-showdeleted';
//			$html = wfMessage( $msgKey )->escaped();
//			$htmlParentheses = wfMessage( 'parentheses' )->rawParams( $html )->escaped();
//			return Xml::tags( 'span', [ 'class' => 'mw-revdelundel-link' ], $htmlParentheses );
//		}
//
//		/* Deprecated methods */
//
//		/**
//		* Returns the attributes for the tooltip and access key.
//		*
//		* @since 1.16.3. $msgParams introduced in 1.27
//		* @param String $name
//		* @param array $msgParams Params for constructing the message
//		*
//		* @return array
//		*/
//		public static function tooltipAndAccesskeyAttribs( $name, array $msgParams = [] ) {
//			# @todo FIXME: If Sanitizer::expandAttributes() treated "false" as "output
//			# no attribute" instead of "output '' as value for attribute", this
//			# would be three lines.
//			$attribs = [
//				'title' => self::titleAttrib( $name, 'withaccess', $msgParams ),
//				'accesskey' => self::accesskey( $name )
//			];
//			if ( $attribs['title'] === false ) {
//				unset( $attribs['title'] );
//			}
//			if ( $attribs['accesskey'] === false ) {
//				unset( $attribs['accesskey'] );
//			}
//			return $attribs;
//		}
//
//		/**
//		* Returns raw bits of HTML, use titleAttrib()
//		* @since 1.16.3
//		* @param String $name
//		* @param array|null $options
//		* @return null|String
//		*/
//		public static function tooltip( $name, $options = null ) {
//			# @todo FIXME: If Sanitizer::expandAttributes() treated "false" as "output
//			# no attribute" instead of "output '' as value for attribute", this
//			# would be two lines.
//			$tooltip = self::titleAttrib( $name, $options );
//			if ( $tooltip === false ) {
//				return '';
//			}
//			return Xml::expandAttributes( [
//				'title' => $tooltip
//			] );
//		}
	private static final    byte[] Bry__dot2 = Bry_.new_a7("../");
}
class Linker_rel_splitter implements gplx.core.brys.Bry_split_wkr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public int Split(byte[] src, int itm_bgn, int itm_end) {	// $combined = array_unique(array_merge($newRels, $oldRels));
		byte[] val = (byte[])hash.Get_by_mid(src, itm_bgn, itm_end);
		if (val == null) {
			val = Bry_.Mid(src, itm_bgn, itm_end);
			hash.Add_as_key_and_val(val);
			if (bfr.Len_gt_0()) bfr.Add_byte_space();
			bfr.Add(val);
		}
		return Bry_split_.Rv__ok;
	}
	public byte[] To_bry() {
		hash.Clear();
		return bfr.To_bry_and_clear();
	}
}
