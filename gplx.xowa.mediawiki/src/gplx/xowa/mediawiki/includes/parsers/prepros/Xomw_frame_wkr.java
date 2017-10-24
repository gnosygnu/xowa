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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
//	public class Xomw_frame_wkr {	// THREAD.UNSAFE: caching for repeated calls
//		private final    Xomw_parser parser;
//		public Xomw_frame_wkr(Xomw_parser parser) {
//			this.parser = parser;
//		}
//		\\ Replace magic variables, templates, and template arguments
//		\\ with the appropriate text. Templates are substituted recursively,
//		\\ taking care to avoid infinite loops.
//		\\
//		\\ Note that the substitution depends on value of $mOutputType:
//		\\  self::OT_WIKI: only {{subst:}} templates
//		\\  self::OT_PREPROCESS: templates but not extension tags
//		\\  self::OT_HTML: all templates and extension tags
//		\\
//		\\ @param String $text The text to transform
//		\\ @param boolean|PPFrame $frame Object describing the arguments passed to the
//		\\   template. Arguments may also be provided as an associative array, as
//		\\   was the usual case before MW1.12. Providing arguments this way may be
//		\\   useful for extensions wishing to perform variable replacement
//		\\   explicitly.
//		\\ @param boolean $argsOnly Only do argument (triple-brace) expansion, not
//		\\   double-brace expansion.
//		\\ @return String
//		public function replaceVariables($text, $frame = false, $argsOnly = false) {
//			// Is there any text? Also, Prevent too big inclusions!
//			$textSize = strlen($text);
//			if ($textSize < 1 || $textSize > $this->mOptions->getMaxIncludeSize()) {
//				return $text;
//			}
//
//			if ($frame == false) {
//				$frame = $this->getPreprocessor()->newFrame();
//			} elseif (!($frame instanceof PPFrame)) {
//				wfDebug(__METHOD__ . " called using plain parameters instead of "
//					. "a PPFrame instance. Creating custom frame.\n");
//				$frame = $this->getPreprocessor()->newCustomFrame($frame);
//			}
//
//			$dom = $this->preprocessToDom($text);
//			$flags = $argsOnly ? PPFrame::NO_TEMPLATES : 0;
//			$text = $frame->expand($dom, $flags);
//
//			return $text;
//		}
//
//		\\ Clean up argument array - refactored in 1.9 so parserfunctions can use it, too.
//		public static function createAssocArgs($args) {
//			$assocArgs = [];
//			$index = 1;
//			foreach ($args as $arg) {
//				$eqpos = strpos($arg, '=');
//				if ($eqpos == false) {
//					$assocArgs[$index++] = $arg;
//				} else {
//					$name = trim(substr($arg, 0, $eqpos));
//					$value = trim(substr($arg, $eqpos + 1));
//					if ($value == false) {
//						$value = '';
//					}
//					if ($name != false) {
//						$assocArgs[$name] = $value;
//					}
//				}
//			}
//
//			return $assocArgs;
//		}

//		\\ Return the text of a template, after recursively
//		\\ replacing any variables or templates within the template.
//		\\
//		\\ @param array $piece The parts of the template
//		\\   $piece['title']: the title, i.e. the part before the |
//		\\   $piece['parts']: the parameter array
//		\\   $piece['lineStart']: whether the brace was at the start of a line
//		\\ @param PPFrame $frame The current frame, contains template arguments
//		\\ @throws Exception
//		\\ @return String The text of the template
//		public void Brace_substitution(Xomw_prepro_node__template piece, Xomw_frame_itm frame) {
//			// Flags
//
//			// $text has been filled
//			boolean found = false;
//			// wiki markup in $text should be escaped
//			boolean nowiki = false;
//			// $text is HTML, armour it against wikitext transformation
//			boolean is_html = false;
//			// Force interwiki transclusion to be done in raw mode not rendered
//			boolean force_raw_interwiki = false;
//			// $text is a DOM node needing expansion in a child frame
//			boolean is_child_obj = false;
//			// $text is a DOM node needing expansion in the current frame
//			boolean is_local_obj = false;
//
//			// Title Object, where $text came from
//			byte[] title = null;
//
//			// $part1 is the bit before the first |, and must contain only title characters.
//			// Various prefixes will be stripped from it later.
//			byte[] title_with_spaces = frame.Expand(piece.Title());
//			byte[] part1 = Bry_.Trim(title_with_spaces);
//			byte[] title_text = null;
//
//			// Original title text preserved for various purposes
//			byte[] originalTitle = part1;
//
//			// $args is a list of argument nodes, starting from index 0, not including $part1
//			// @todo FIXME: If piece['parts'] is null then the call to getLength()
//			// below won't work b/c this $args isn't an Object
//			Xomw_prepro_node__part[] args = (null == piece.Parts()) ? null : piece.Parts();
//
//			byte[] profile_section = null; // profile templates
//
//			Tfds.Write(nowiki, is_html, force_raw_interwiki, is_child_obj, is_local_obj, title, title_text, profile_section);
//			// SUBST
//			if (!found) {
//				String subst_match = null; // $this->mSubstWords->matchStartAndRemove($part1);
//				boolean literal = false;
//
//				// Possibilities for substMatch: "subst", "safesubst" or FALSE
//				// Decide whether to expand template or keep wikitext as-is.
//				if (parser.Output_type__wiki()) {
//					if (subst_match == null) {
//						literal = true;  // literal when in PST with no prefix
//					}
//					else {
//						literal = false; // expand when in PST with subst: or safesubst:
//					}
//				}
//				else {
//					if (subst_match == "subst") {
//						literal = true;  // literal when not in PST with plain subst:
//					}
//					else {
//						literal = false; // expand when not in PST with safesubst: or no prefix
//					}
//				}
//				if (literal) {
////					$text = $frame->virtualBracketedImplode('{{', '|', '}}', title_with_spaces, $args);
//					is_local_obj = true;
//					found = true;
//				}
//			}
//
//			// Variables
//			if (!found && args.length == 0) {
////				$id = $this->mVariables->matchStartToEnd($part1);
////				if ($id != false) {
////					$text = $this->getVariableValue($id, $frame);
////					if (MagicWord::getCacheTTL($id) > -1) {
////						$this->mOutput->updateCacheExpiry(MagicWord::getCacheTTL($id));
////					}
//					found = true;
////				}
//			}
//
//			// MSG, MSGNW and RAW
//			if (!found) {
//				// Check for MSGNW:
////				$mwMsgnw = MagicWord::get('msgnw');
////				if ($mwMsgnw->matchStartAndRemove($part1)) {
//					nowiki = true;
////				}
////				else {
//					// Remove obsolete MSG:
////					$mwMsg = MagicWord::get('msg');
////					$mwMsg->matchStartAndRemove($part1);
////				}
//
//				// Check for RAW:
////				$mwRaw = MagicWord::get('raw');
////				if ($mwRaw->matchStartAndRemove($part1)) {
////					force_raw_interwiki = true;
////				}
//			}

		// Parser functions
//			if (!found) {
//				$colonPos = strpos($part1, ':');
//				if ($colonPos != false) {
//					$func = substr($part1, 0, $colonPos);
//					$funcArgs = [ trim(substr($part1, $colonPos + 1)) ];
//					$argsLength = $args->getLength();
//					for ($i = 0; $i < $argsLength; $i++) {
//						$funcArgs[] = $args->item($i);
//					}
//					try {
//						$result = $this->callParserFunction($frame, $func, $funcArgs);
//					} catch (Exception $ex) {
//						throw $ex;
//					}

				// The interface for parser functions allows for extracting
				// flags into the local scope. Extract any forwarded flags
				// here.
//					extract($result);
//				}
//			}

		// Finish mangling title and then check for loops.
		// Set title to a Title Object and $title_text to the PDBK
//			if (!found) {
//				$ns = NS_TEMPLATE;
			// Split the title into page and subpage
//				$subpage = '';
//				$relative = $this->maybeDoSubpageLink($part1, $subpage);
//				if ($part1 != $relative) {
//					$part1 = $relative;
//					$ns = $this->mTitle->getNamespace();
//				}
//				title = Title::newFromText($part1, $ns);
//				if (title) {
//					$title_text = title->getPrefixedText();
//					// Check for language variants if the template is not found
//					if ($this->getConverterLanguage()->hasVariants() && title->getArticleID() == 0) {
//						$this->getConverterLanguage()->findVariantLink($part1, title, true);
//					}
//					// Do recursion depth check
//					$limit = $this->mOptions->getMaxTemplateDepth();
//					if ($frame->depth >= $limit) {
//						found = true;
//						$text = '<span class="error">'
//							. wfMessage('parser-template-recursion-depth-warning')
//								->numParams($limit)->inContentLanguage()->text()
//							. '</span>';
//					}
//				}
//			}

		// Load from database
//			if (!found && title) {
//				$profile_section = $this->mProfiler->scopedProfileIn(title->getPrefixedDBkey());
//				if (!title->isExternal()) {
//					if (title->isSpecialPage()
//						&& $this->mOptions->getAllowSpecialInclusion()
//						&& $this->ot['html']
//					) {
//						$specialPage = SpecialPageFactory::getPage(title->getDBkey());
//						// Pass the template arguments as URL parameters.
//						// "uselang" will have no effect since the Language Object
//						// is forced to the one defined in ParserOptions.
//						$pageArgs = [];
//						$argsLength = $args->getLength();
//						for ($i = 0; $i < $argsLength; $i++) {
//							$bits = $args->item($i)->splitArg();
//							if (strval($bits['index']) == '') {
//								$name = trim($frame->expand($bits['name'], PPFrame::STRIP_COMMENTS));
//								$value = trim($frame->expand($bits['value']));
//								$pageArgs[$name] = $value;
//							}
//						}
//
//						// Create a new context to execute the special page
//						$context = new RequestContext;
//						$context->setTitle(title);
//						$context->setRequest(new FauxRequest($pageArgs));
//						if ($specialPage && $specialPage->maxIncludeCacheTime() == 0) {
//							$context->setUser($this->getUser());
//						} else {
//							// If this page is cached, then we better not be per user.
//							$context->setUser(User::newFromName('127.0.0.1', false));
//						}
//						$context->setLanguage($this->mOptions->getUserLangObj());
//						$ret = SpecialPageFactory::capturePath(
//							title, $context, $this->getLinkRenderer());
//						if ($ret) {
//							$text = $context->getOutput()->getHTML();
//							$this->mOutput->addOutputPageMetadata($context->getOutput());
//							found = true;
//							is_html = true;
//							if ($specialPage && $specialPage->maxIncludeCacheTime() != false) {
//								$this->mOutput->updateRuntimeAdaptiveExpiry(
//									$specialPage->maxIncludeCacheTime()
//								);
//							}
//						}
//					} elseif (XomwNamespace::isNonincludable(title->getNamespace())) {
//						found = false; // access denied
//						wfDebug(__METHOD__ . ": template inclusion denied for " .
//							title->getPrefixedDBkey() . "\n");
//					} else {
//						list($text, title) = $this->getTemplateDom(title);
//						if ($text != false) {
//							found = true;
//							is_child_obj = true;
//						}
//					}
//
//					// If the title is valid but undisplayable, make a link to it
//					if (!found && ($this->ot['html'] || $this->ot['pre'])) {
//						$text = "[[:$title_text]]";
//						found = true;
//					}
//				} elseif (title->isTrans()) {
//					// Interwiki transclusion
//					if ($this->ot['html'] && !force_raw_interwiki) {
//						$text = $this->interwikiTransclude(title, 'render');
//						is_html = true;
//					} else {
//						$text = $this->interwikiTransclude(title, 'raw');
//						// Preprocess it like a template
//						$text = $this->preprocessToDom($text, self::PTD_FOR_INCLUSION);
//						is_child_obj = true;
//					}
//					found = true;
//				}
//
//				// Do infinite loop check
//				// This has to be done after redirect resolution to avoid infinite loops via redirects
//				if (!$frame->loopCheck(title)) {
//					found = true;
//					$text = '<span class="error">'
//						. wfMessage('parser-template-loop-warning', $title_text)->inContentLanguage()->text()
//						. '</span>';
//					wfDebug(__METHOD__ . ": template loop broken at '$title_text'\n");
//				}
//			}

		// If we haven't found text to substitute by now, we're done
		// Recover the source wikitext and return it
//			if (!found) {
//				$text = $frame->virtualBracketedImplode('{{', '|', '}}', title_with_spaces, $args);
//				if ($profile_section) {
//					$this->mProfiler->scopedProfileOut($profile_section);
//				}
//				return [ 'Object' => $text ];
//			}

		// Expand DOM-style return values in a child frame
//			if (is_child_obj) {
//				// Clean up argument array
//				$newFrame = $frame->newChild($args, title);
//
//				if (nowiki) {
//					$text = $newFrame->expand($text, PPFrame::RECOVER_ORIG);
//				} elseif ($title_text != false && $newFrame->isEmpty()) {
//					// Expansion is eligible for the empty-frame cache
//					$text = $newFrame->cachedExpand($title_text, $text);
//				} else {
//					// Uncached expansion
//					$text = $newFrame->expand($text);
//				}
//			}
//			if (is_local_obj && nowiki) {
//				$text = $frame->expand($text, PPFrame::RECOVER_ORIG);
//				is_local_obj = false;
//			}

//			if ($profile_section) {
//				$this->mProfiler->scopedProfileOut($profile_section);
//			}

		// Replace raw HTML by a placeholder
//			if (is_html) {
//				$text = $this->insertStripItem($text);
//			} elseif (nowiki && ($this->ot['html'] || $this->ot['pre'])) {
//				// Escape nowiki-style return values
//				$text = wfEscapeWikiText($text);
//			} elseif (is_string($text)
//				&& !$piece['lineStart']
//				&& preg_match('/^(?:{\\||:|;|#|\*)/', $text)
//			) {
//				// T2529: if the template begins with a table or block-level
//				// element, it should be treated as beginning a new line.
//				// This behavior is somewhat controversial.
//				$text = "\n" . $text;
//			}

//			if (is_string($text) && !$this->incrementIncludeSize('post-expand', strlen($text))) {
//				// Error, oversize inclusion
//				if ($title_text != false) {
//					// Make a working, properly escaped link if possible (T25588)
//					$text = "[[:$title_text]]";
//				} else {
//					// This will probably not be a working link, but at least it may
//					// provide some hint of where the problem is
//					preg_replace('/^:/', '', $originalTitle);
//					$text = "[[:$originalTitle]]";
//				}
//				$text .= $this->insertStripItem('<!-- WARNING: template omitted, '
//					. 'post-expand include size too large -->');
//				$this->limitationWarn('post-expand-template-inclusion');
//			}
//
//			if (is_local_obj) {
//				$ret = [ 'Object' => $text ];
//			} else {
//				$ret = [ 'text' => $text ];
//			}

//			return $ret;
//		}

//		\\ Triple brace replacement -- used for template arguments
//		public function argSubstitution($piece, $frame) {
//
//			$error = false;
//			$parts = $piece['parts'];
//			$nameWithSpaces = $frame->expand($piece['title']);
//			$argName = trim($nameWithSpaces);
//			$Object = false;
//			$text = $frame->getArgument($argName);
//			if ($text == false && $parts->getLength() > 0
//				&& ($this->ot['html']
//					|| $this->ot['pre']
//					|| ($this->ot['wiki'] && $frame->isTemplate())
//				)
//			) {
//				// No match in frame, use the supplied default
//				$Object = $parts->item(0)->getChildren();
//			}
//			if (!$this->incrementIncludeSize('arg', strlen($text))) {
//				$error = '<!-- WARNING: argument omitted, expansion size too large -->';
//				$this->limitationWarn('post-expand-template-argument');
//			}
//
//			if ($text == false && $Object == false) {
//				// No match anywhere
//				$Object = $frame->virtualBracketedImplode('{{{', '|', '}}}', $nameWithSpaces, $parts);
//			}
//			if ($error != false) {
//				$text .= $error;
//			}
//			if ($Object != false) {
//				$ret = [ 'Object' => $Object ];
//			} else {
//				$ret = [ 'text' => $text ];
//			}
//
//			return $ret;
//		}
//
//		/**
//		\\ Return the text to be used for a given extension tag.
//		\\ This is the ghost of strip().
//		\\
//		\\ @param array $params Associative array of parameters:
//		\\     name       PPNode for the tag name
//		\\     attr       PPNode for unparsed text where tag attributes are thought to be
//		\\     attributes Optional associative array of parsed attributes
//		\\     inner      Contents of extension element
//		\\     noClose    Original text did not have a close tag
//		\\ @param PPFrame $frame
//		\\
//		\\ @throws MWException
//		\\ @return String
//		\\/
//		public function extensionSubstitution($params, $frame) {
//			static $errorStr = '<span class="error">';
//			static $errorLen = 20;
//
//			$name = $frame->expand($params['name']);
//			if (substr($name, 0, $errorLen) == $errorStr) {
//				// Probably expansion depth or node count exceeded. Just punt the
//				// error up.
//				return $name;
//			}
//
//			$attrText = !isset($params['attr']) ? null : $frame->expand($params['attr']);
//			if (substr($attrText, 0, $errorLen) == $errorStr) {
//				// See above
//				return $attrText;
//			}
//
//			// We can't safely check if the expansion for $content resulted in an
//			// error, because the content could happen to be the error String
//			// (T149622).
//			$content = !isset($params['inner']) ? null : $frame->expand($params['inner']);
//
//			$marker = self::MARKER_PREFIX . "-$name-"
//				. sprintf('%08X', $this->mMarkerIndex++) . self::MARKER_SUFFIX;
//
//			$isFunctionTag = isset($this->mFunctionTagHooks[strtolower($name)]) &&
//				($this->ot['html'] || $this->ot['pre']);
//			if ($isFunctionTag) {
//				$markerType = 'none';
//			} else {
//				$markerType = 'general';
//			}
//			if ($this->ot['html'] || $isFunctionTag) {
//				$name = strtolower($name);
//				$attributes = Sanitizer::decodeTagAttributes($attrText);
//				if (isset($params['attributes'])) {
//					$attributes = $attributes + $params['attributes'];
//				}
//
//				if (isset($this->mTagHooks[$name])) {
//					// Workaround for PHP bug 35229 and similar
//					if (!is_callable($this->mTagHooks[$name])) {
//						throw new MWException("Tag hook for $name is not callable\n");
//					}
//					$output = call_user_func_array($this->mTagHooks[$name],
//						[ $content, $attributes, $this, $frame ]);
//				} elseif (isset($this->mFunctionTagHooks[$name])) {
//					list($callback,) = $this->mFunctionTagHooks[$name];
//					if (!is_callable($callback)) {
//						throw new MWException("Tag hook for $name is not callable\n");
//					}
//
//					$output = call_user_func_array($callback, [ &$this, $frame, $content, $attributes ]);
//				} else {
//					$output = '<span class="error">Invalid tag extension name: ' .
//						htmlspecialchars($name) . '</span>';
//				}
//
//				if (is_array($output)) {
//					// Extract flags to local scope (to override $markerType)
//					$flags = $output;
//					$output = $flags[0];
//					unset($flags[0]);
//					extract($flags);
//				}
//			} else {
//				if (is_null($attrText)) {
//					$attrText = '';
//				}
//				if (isset($params['attributes'])) {
//					foreach ($params['attributes'] as $attrName => $attrValue) {
//						$attrText .= ' ' . htmlspecialchars($attrName) . '="' .
//							htmlspecialchars($attrValue) . '"';
//					}
//				}
//				if ($content == null) {
//					$output = "<$name$attrText/>";
//				} else {
//					$close = is_null($params['close']) ? '' : $frame->expand($params['close']);
//					if (substr($close, 0, $errorLen) == $errorStr) {
//						// See above
//						return $close;
//					}
//					$output = "<$name$attrText>$content$close";
//				}
//			}
//
//			if ($markerType == 'none') {
//				return $output;
//			} elseif ($markerType == 'nowiki') {
//				$this->mStripState->addNoWiki($marker, $output);
//			} elseif ($markerType == 'general') {
//				$this->mStripState->addGeneral($marker, $output);
//			} else {
//				throw new MWException(__METHOD__ . ': invalid marker type');
//			}
//			return $marker;
//		}
//	}
