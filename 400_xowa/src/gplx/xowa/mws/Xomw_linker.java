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
package gplx.xowa.mws; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
import gplx.xowa.mws.htmls.*;
import gplx.xowa.mws.linkers.*;
/*	TODO.XO
	* titleFormatter->gePrefixedTex
	* $html = HtmlArmor::getHtml($text);
	* Get_link_url
	* Get_link_classes
*/
public class Xomw_linker {
	private final    Bry_bfr tmp = Bry_bfr_.New();
	private final    Linker_rel_splitter splitter = new Linker_rel_splitter();
	private final    Xomw_html_utl html_utl = new Xomw_html_utl();
	private byte[] wg_title = null;
	private final    Btrie_rv trv = new Btrie_rv();
	private final    byte[][] split_trail_rv = new byte[2][];
	private Btrie_slim_mgr split_trail_trie;
	private static final    byte[] Atr__class = Bry_.new_a7("class"), Atr__rel = Bry_.new_a7("rel"), Atr__href = Bry_.new_a7("href"), Rel__nofollow = Bry_.new_a7("nofollow");
	private final    Xomw_link_renderer link_renderer;
	public Xomw_linker(Xomw_link_renderer link_renderer) {
		this.link_renderer = link_renderer;
	}
	public void Init_by_wiki(Btrie_slim_mgr trie) {
		this.split_trail_trie = trie;
	}
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
	public void Link(Bry_bfr bfr, Xoa_ttl target, byte[] html, Xomw_atr_mgr custom_attribs, Xomw_qry_mgr query, Xomw_opt_mgr options) {
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
			link_renderer.Make_known_link(bfr, target, text, custom_attribs, query);
		}
		else if (options.broken) {
//				return $linkRenderer->makeBrokenLink($target, $text, $customAttribs, $query);
		}
		else if (options.no_classes) {
			link_renderer.Make_preloaded_link(bfr, target, text, Bry_.Empty, custom_attribs, query);
		}
		else {
//				$linkRenderer->makeLink($target, $text, $customAttribs, $query);
		}
	}
	public void Make_self_link_obj(Bry_bfr bfr, Xoa_ttl nt, byte[] html, byte[] query, byte[] trail, byte[] prefix) {
		// MW.HOOK:SelfLinkBegin
		if (html == Bry_.Empty) {
			html = tmp.Add_bry_escape_html(nt.Get_prefixed_text()).To_bry_and_clear();
		}
		byte[] inside = Bry_.Empty;
		byte[][] split_trail = Split_trail(trail);
		inside = split_trail[0];
		trail = split_trail[1];
		bfr.Add_str_a7("<strong class=\"selflink\">");
		bfr.Add_bry_many(prefix, html, inside);
		bfr.Add_str_a7("</strong>");
		bfr.Add(trail);
	}
	public void Make_external_link(Bry_bfr bfr, byte[] url, byte[] text, boolean escape, byte[] link_type, Xomw_atr_mgr attribs, byte[] title) {
		tmp.Add_str_a7("external");
		if (link_type != null) {
			tmp.Add_byte_space().Add(link_type);
		}
		Xomw_atr_itm cls_itm = attribs.Get_by_or_make(Atr__class);
		if (cls_itm.Val() != null) {
 				tmp.Add(cls_itm.Val());
		}
		cls_itm.Val_(tmp.To_bry_and_clear());

		if (escape)
			text = tmp.Add_bry_escape_html(text).To_bry_and_clear();

		if (title == null)
			title = wg_title;

		byte[] new_rel = Get_external_link_rel(url, title);
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
		//$link = '';
		//$success = Hooks::run('LinkerMakeExternalLink',
		//	[ &$url, &$text, &$link, &$attribs, $linktype ]);
		//if (!$success) {
		//	wfDebug("Hook LinkerMakeExternalLink changed the output of link "
		//		. "with url {$url} and text {$text} to {$link}\n", true);
		//	return $link;
		//}
		attribs.Set(Atr__href, url);

		html_utl.Raw_element(bfr, Bry_.new_a7("a"), attribs, text);
	}
	private byte[] Get_external_link_rel(byte[] url, byte[] title) {
		// global $wgNoFollowLinks, $wgNoFollowNsExceptions, $wgNoFollowDomainExceptions;
		// $ns = $title ? $title->getNamespace() : false;
		// if ($wgNoFollowLinks && !in_array($ns, $wgNoFollowNsExceptions)
		//	&& !wfMatchesDomainList($url, $wgNoFollowDomainExceptions)
		//) {
			return Rel__nofollow;
		// }
		// return null;
	}
	public void Normalize_subpage_link(Xomw_linker__normalize_subpage_link rv, Xoa_ttl context_title, byte[] target, byte[] text) {
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
		if (context_title != null && context_title.Ns().Subpages_enabled()) {
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

				ret = Bry_.Add(context_title.Get_prefixed_text(), Byte_ascii.Slash_bry, Bry_.Trim(no_slash), suffix);
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
					byte[][] exploded = Bry_split_.Split(context_title.Get_prefixed_text(), Byte_ascii.Slash);
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
	public byte[][] Split_trail(byte[] trail) {
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
	public void Make_image(Bry_bfr bfr, Xoa_ttl title, byte[] options, boolean holders) {
		// Check if the options text is of the form "options|alt text"
		// Options are:
		//  * thumbnail  make a thumbnail with enlarge-icon and caption, alignment depends on lang
		//  * left       no resizing, just left align. label is used for alt= only
		//  * right      same, but right aligned
		//  * none       same, but not aligned
		//  * ___px      scale to ___ pixels width, no aligning. e.g. use in taxobox
		//  * center     center the image
		//  * frame      Keep original image size, no magnify-button.
		//  * framed     Same as "frame"
		//  * frameless  like 'thumb' but without a frame. Keeps user preferences for width
		//  * upright    reduce width for upright images, rounded to full __0 px
		//  * border     draw a 1px border around the image
		//  * alt        Text for HTML alt attribute (defaults to empty)
		//  * class      Set a class for img node
		//  * link       Set the target of the image link. Can be external, interwiki, or local
		// vertical-align values (no % or length right now):
		//  * baseline
		//  * sub
		//  * super
		//  * top
		//  * text-top
		//  * middle
		//  * bottom
		//  * text-bottom

		// Protect LanguageConverter markup when splitting into parts
//			$parts = StringUtils::delimiterExplode(
//				'-{', '}-', '|', $options, true /* allow nesting */
//			);

		// Give extensions a chance to select the file revision for us
//			$options = [];
//			$descQuery = false;
		// MW.HOOK:BeforeParserFetchFileAndTitle

		// Fetch and register the file (file title may be different via hooks)
//			list($file, $title) = $this->fetchFileAndTitle($title, $options);

		// Get parameter map
//			$handler = $file ? $file->getHandler() : false;

//			list($paramMap, $mwArray) = $this->getImageParams($handler);

//			if (!$file) {
//				$this->addTrackingCategory('broken-file-category');
//			}

		// Process the input parameters
//			$caption = '';
//			$params = [ 'frame' => [], 'handler' => [],
//				'horizAlign' => [], 'vertAlign' => [] ];
//			$seenformat = false;
//			foreach ($parts as $part) {
//				$part = trim($part);
//				list($magicName, $value) = $mwArray->matchVariableStartToEnd($part);
//				$validated = false;
//				if (isset($paramMap[$magicName])) {
//					list($type, $paramName) = $paramMap[$magicName];

				// Special case; width and height come in one variable together
//					if ($type === 'handler' && $paramName === 'width') {
//						$parsedWidthParam = $this->parseWidthParam($value);
//						if (isset($parsedWidthParam['width'])) {
//							$width = $parsedWidthParam['width'];
//							if ($handler->validateParam('width', $width)) {
//								$params[$type]['width'] = $width;
//								$validated = true;
//							}
//						}
//						if (isset($parsedWidthParam['height'])) {
//							$height = $parsedWidthParam['height'];
//							if ($handler->validateParam('height', $height)) {
//								$params[$type]['height'] = $height;
//								$validated = true;
//							}
//						}
					// else no validation -- T15436
//					} else {
//						if ($type === 'handler') {
//							// Validate handler parameter
//							$validated = $handler->validateParam($paramName, $value);
//						} else {
//							// Validate @gplx.Internal protected parameters
//							switch ($paramName) {
//							case 'manualthumb':
//							case 'alt':
//							case 'class':
							// @todo FIXME: Possibly check validity here for
							// manualthumb? downstream behavior seems odd with
							// missing manual thumbs.
//								$validated = true;
//								$value = $this->stripAltText($value, $holders);
//								break;
//							case 'link':
//								$chars = self::EXT_LINK_URL_CLASS;
//								$addr = self::EXT_LINK_ADDR;
//								$prots = $this->mUrlProtocols;
//								if ($value === '') {
//									$paramName = 'no-link';
//									$value = true;
//									$validated = true;
//								} else if (preg_match("/^((?i)$prots)/", $value)) {
//									if (preg_match("/^((?i)$prots)$addr$chars*$/u", $value, $m)) {
//										$paramName = 'link-url';
//										$this->mOutput->addExternalLink($value);
//										if ($this->mOptions->getExternalLinkTarget()) {
//											$params[$type]['link-target'] = $this->mOptions->getExternalLinkTarget();
//										}
//										$validated = true;
//									}
//								} else {
//									$linkTitle = Title::newFromText($value);
//									if ($linkTitle) {
//										$paramName = 'link-title';
//										$value = $linkTitle;
//										$this->mOutput->addLink($linkTitle);
//										$validated = true;
//									}
//								}
//								break;
//							case 'frameless':
//							case 'framed':
//							case 'thumbnail':
//								// use first appearing option, discard others.
//								$validated = !$seenformat;
//								$seenformat = true;
//								break;
//							default:
//								// Most other things appear to be empty or numeric...
//								$validated = ($value === false || is_numeric(trim($value)));
//							}
//						}

//						if ($validated) {
//							$params[$type][$paramName] = $value;
//						}
//					}
//				}
//				if (!$validated) {
//					$caption = $part;
//				}
//			}

		// Process alignment parameters
//			if ($params['horizAlign']) {
//				$params['frame']['align'] = key($params['horizAlign']);
//			}
//			if ($params['vertAlign']) {
//				$params['frame']['valign'] = key($params['vertAlign']);
//			}

//			$params['frame']['caption'] = $caption;

		// Will the image be presented in a frame, with the caption below?
//			$imageIsFramed = isset($params['frame']['frame'])
//				|| isset($params['frame']['framed'])
//				|| isset($params['frame']['thumbnail'])
//				|| isset($params['frame']['manualthumb']);

		// In the old days, [[Image:Foo|text...]] would set alt text.  Later it
		// came to also set the caption, ordinary text after the image -- which
		// makes no sense, because that just repeats the text multiple times in
		// screen readers.  It *also* came to set the title attribute.
		// Now that we have an alt attribute, we should not set the alt text to
		// equal the caption: that's worse than useless, it just repeats the
		// text.  This is the framed/thumbnail case.  If there's no caption, we
		// use the unnamed parameter for alt text as well, just for the time be-
		// ing, if the unnamed param is set and the alt param is not.
		// For the future, we need to figure out if we want to tweak this more,
		// e.g., introducing a title= parameter for the title; ignoring the un-
		// named parameter entirely for images without a caption; adding an ex-
		// plicit caption= parameter and preserving the old magic unnamed para-
		// meter for BC; ...
//			if ($imageIsFramed) { // Framed image
//				if ($caption === '' && !isset($params['frame']['alt'])) {
//					// No caption or alt text, add the filename as the alt text so
//					// that screen readers at least get some description of the image
//					$params['frame']['alt'] = $title->getText();
//				}
			// Do not set $params['frame']['title'] because tooltips don't make sense
			// for framed images
//			} else { // Inline image
//				if (!isset($params['frame']['alt'])) {
//					// No alt text, use the "caption" for the alt text
//					if ($caption !== '') {
//						$params['frame']['alt'] = $this->stripAltText($caption, $holders);
//					} else {
//						// No caption, fall back to using the filename for the
//						// alt text
//						$params['frame']['alt'] = $title->getText();
//					}
//				}
			// Use the "caption" for the tooltip text
//				$params['frame']['title'] = $this->stripAltText($caption, $holders);
//			}

		// MW.HOOK:ParserMakeImageParams

		// Linker does the rest
//			$time = isset($options['time']) ? $options['time'] : false;
//			$ret = Linker::makeImageLink($this, $title, $file, $params['frame'], $params['handler'],
//				$time, $descQuery, $this->mOptions->getThumbSize());

		// Give the handler a chance to modify the parser Object
//			if ($handler) {
//				$handler->parserTransformHook($this, $file);
//			}

//			return $ret;
	}
//		public function getImageParams($handler) {
//			if ($handler) {
//				$handlerClass = get_class($handler);
//			}
//			else {
//				$handlerClass = '';
//			}
//			if (!isset($this->mImageParams[$handlerClass])) {
			// Initialise static lists
//				static $internalParamNames = [
//					'horizAlign' => [ 'left', 'right', 'center', 'none' ],
//					'vertAlign' => [ 'baseline', 'sub', 'super', 'top', 'text-top', 'middle',
//						'bottom', 'text-bottom' ],
//					'frame' => [ 'thumbnail', 'manualthumb', 'framed', 'frameless',
//						'upright', 'border', 'link', 'alt', 'class' ],
//				];
//				static $internalParamMap;
//				if (!$internalParamMap) {
//					$internalParamMap = [];
//					foreach ($internalParamNames as $type => $names) {
//						foreach ($names as $name) {
//							$magicName = str_replace('-', '_', "img_$name");
//							$internalParamMap[$magicName] = [ $type, $name ];
//						}
//					}
//				}

			// Add handler params
//				$paramMap = $internalParamMap;
//				if ($handler) {
//					$handlerParamMap = $handler->getParamMap();
//					foreach ($handlerParamMap as $magic => $paramName) {
//						$paramMap[$magic] = [ 'handler', $paramName ];
//					}
//				}
//				$this->mImageParams[$handlerClass] = $paramMap;
//				$this->mImageParamsMagicArray[$handlerClass] = new MagicWordArray(array_keys($paramMap));
//			}
//			return [ $this->mImageParams[$handlerClass], $this->mImageParamsMagicArray[$handlerClass] ];
//		}
//		// Make HTML for a thumbnail including image, border and caption
//		public static function makeThumbLinkObj(Title $title, $file, $label = '', $alt,
//			$align = 'right', $params = [], $framed = false, $manualthumb = ""
//		) {
//			$frameParams = [
//				'alt' => $alt,
//				'caption' => $label,
//				'align' => $align
//			];
//			if ($framed) {
//				$frameParams['framed'] = true;
//			}
//			if ($manualthumb) {
//				$frameParams['manualthumb'] = $manualthumb;
//			}
//			return self::makeThumbLink2($title, $file, $frameParams, $params);
//		}

//		public static function makeThumbLink2(Title $title, $file, $frameParams = [],
//			$handlerParams = [], $time = false, $query = ""
//		) {
//			$exists = $file && $file->exists();
//
//			$page = isset($handlerParams['page']) ? $handlerParams['page'] : false;
//			if (!isset($frameParams['align'])) {
//				$frameParams['align'] = 'right';
//			}
//			if (!isset($frameParams['alt'])) {
//				$frameParams['alt'] = '';
//			}
//			if (!isset($frameParams['title'])) {
//				$frameParams['title'] = '';
//			}
//			if (!isset($frameParams['caption'])) {
//				$frameParams['caption'] = '';
//			}
//
//			if (empty($handlerParams['width'])) {
//				// Reduce width for upright images when parameter 'upright' is used
//				$handlerParams['width'] = isset($frameParams['upright']) ? 130 : 180;
//			}
//			$thumb = false;
//			$noscale = false;
//			$manualthumb = false;
//
//			if (!$exists) {
//				$outerWidth = $handlerParams['width'] + 2;
//			} else {
//				if (isset($frameParams['manualthumb'])) {
//					// Use manually specified thumbnail
//					$manual_title = Title::makeTitleSafe(NS_FILE, $frameParams['manualthumb']);
//					if ($manual_title) {
//						$manual_img = wfFindFile($manual_title);
//						if ($manual_img) {
//							$thumb = $manual_img->getUnscaledThumb($handlerParams);
//							$manualthumb = true;
//						} else {
//							$exists = false;
//						}
//					}
//				} else if (isset($frameParams['framed'])) {
//					// Use image dimensions, don't scale
//					$thumb = $file->getUnscaledThumb($handlerParams);
//					$noscale = true;
//				} else {
//					// Do not present an image bigger than the source, for bitmap-style images
//					// This is a hack to maintain compatibility with arbitrary pre-1.10 behavior
//					$srcWidth = $file->getWidth($page);
//					if ($srcWidth && !$file->mustRender() && $handlerParams['width'] > $srcWidth) {
//						$handlerParams['width'] = $srcWidth;
//					}
//					$thumb = $file->transform($handlerParams);
//				}
//
//				if ($thumb) {
//					$outerWidth = $thumb->getWidth() + 2;
//				} else {
//					$outerWidth = $handlerParams['width'] + 2;
//				}
//			}
//
//			// ThumbnailImage::toHtml() already adds page= onto the end of DjVu URLs
//			// So we don't need to pass it here in $query. However, the URL for the
//			// zoom icon still needs it, so we make a unique query for it. See bug 14771
//			$url = $title->getLocalURL($query);
//			if ($page) {
//				$url = wfAppendQuery($url, [ 'page' => $page ]);
//			}
//			if ($manualthumb
//				&& !isset($frameParams['link-title'])
//				&& !isset($frameParams['link-url'])
//				&& !isset($frameParams['no-link'])) {
//				$frameParams['link-url'] = $url;
//			}
//
//			$s = "<div class=\"thumb t{$frameParams['align']}\">"
//				. "<div class=\"thumbinner\" style=\"width:{$outerWidth}px;\">";
//
//			if (!$exists) {
//				$s .= self::makeBrokenImageLinkObj($title, $frameParams['title'], '', '', '', $time == true);
//				$zoomIcon = '';
//			} else if (!$thumb) {
//				$s .= wfMessage('thumbnail_error', '')->escaped();
//				$zoomIcon = '';
//			} else {
//				if (!$noscale && !$manualthumb) {
//					self::processResponsiveImages($file, $thumb, $handlerParams);
//				}
//				$params = [
//					'alt' => $frameParams['alt'],
//					'title' => $frameParams['title'],
//					'img-class' => (isset($frameParams['class']) && $frameParams['class'] !== ''
//						? $frameParams['class'] . ' '
//						: '') . 'thumbimage'
//				];
//				$params = self::getImageLinkMTOParams($frameParams, $query) + $params;
//				$s .= $thumb->toHtml($params);
//				if (isset($frameParams['framed'])) {
//					$zoomIcon = "";
//				} else {
//					$zoomIcon = Html::rawElement('div', [ 'class' => 'magnify' ],
//						Html::rawElement('a', [
//							'href' => $url,
//							'class' => '@gplx.Internal protected',
//							'title' => wfMessage('thumbnail-more')->text() ],
//							""));
//				}
//			}
//			$s .= '  <div class="thumbcaption">' . $zoomIcon . $frameParams['caption'] . "</div></div></div>";
//			return str_replace("\n", ' ', $s);
//		}
//		// Make a "broken" link to an image
//		public static function makeBrokenImageLinkObj($title, $label = '',
//			$query = '', $unused1 = '', $unused2 = '', $time = false
//		) {
//			if (!$title instanceof Title) {
//				wfWarn(__METHOD__ . ': Requires $title to be a Title Object.');
//				return "<!-- ERROR -->" . htmlspecialchars($label);
//			}
//
//			global $wgEnableUploads, $wgUploadMissingFileUrl, $wgUploadNavigationUrl;
//			if ($label == '') {
//				$label = $title->getPrefixedText();
//			}
//			$encLabel = htmlspecialchars($label);
//			$currentExists = $time ? (wfFindFile($title) != false) : false;
//
//			if (($wgUploadMissingFileUrl || $wgUploadNavigationUrl || $wgEnableUploads)
//				&& !$currentExists
//			) {
//				$redir = RepoGroup::singleton()->getLocalRepo()->checkRedirect($title);
//
//				if ($redir) {
//					// We already know it's a redirect, so mark it
//					// accordingly
//					return self::link(
//						$title,
//						$encLabel,
//						[ 'class' => 'mw-redirect' ],
//						wfCgiToArray($query),
//						[ 'known', 'noclasses' ]
//					);
//				}
//
//				$href = self::getUploadUrl($title, $query);
//
//				return '<a href="' . htmlspecialchars($href) . '" class="new" title="' .
//					htmlspecialchars($title->getPrefixedText(), ENT_QUOTES) . '">' .
//					$encLabel . '</a>';
//			}
//
//			return self::link($title, $encLabel, [], wfCgiToArray($query), [ 'known', 'noclasses' ]);
//		}
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
	public static Xoa_ttl Normalise_special_page(Xoa_ttl target) {			
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
